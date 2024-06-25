/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2023 Cloud Software Group, Inc. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Contributors:
 * Ryan Johnson - delscovich@users.sourceforge.net
 * Carlton Moore - cmoore79@users.sourceforge.net
 */
package net.sf.jasperreports.engine.convert;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRReportTemplate;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRStyleContainer;
import net.sf.jasperreports.engine.JRTemplate;
import net.sf.jasperreports.engine.JRTemplateReference;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.base.JRBasePrintFrame;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.component.ComponentsEnvironment;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fill.JRFiller;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.PrintOrderEnum;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.engine.util.JRDataUtils;
import net.sf.jasperreports.engine.util.JRExpressionUtil;
import net.sf.jasperreports.engine.xml.JRXmlTemplateLoader;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class ReportConverter 
{

	private static final Log log = LogFactory.getLog(ReportConverter.class);
	public static final Color GRID_LINE_COLOR = new Color(170, 170, 255);
	public static final String EXCEPTION_MESSAGE_KEY_CIRCULAR_DEPENDENCY_FOUND = "convert.report.converter.circular.dependency.found";
	
	private final JasperReportsContext jasperReportsContext;
	private final ComponentsEnvironment componentsEnvironment;
	private final JRReport report;
	private final Locale locale;
	private final TimeZone timezone;
	private JasperPrint jasperPrint;
	private JRPrintPage page;
	int pageWidth;
	private int offsetY;
	private int upColumnHeader;
	private int downColumnHeader;
	private int upDetails;
	private int downDetails;
	private int upColumnFooter;
	private int downColumnFooter;
	
	/**
	 * List containing page elements in a given order 
	 */
	private List<JRPrintElement> pageElements = new ArrayList<>();
	
	protected Map<String, JRStyle> stylesMap;

	
	/**
	 *
	 */
	public ReportConverter(JasperReportsContext jasperReportsContext, JRReport report, boolean ignoreContent)
	{
		this.jasperReportsContext = jasperReportsContext;
		this.componentsEnvironment = ComponentsEnvironment.getInstance(jasperReportsContext);
		this.report = report;
		this.locale = readLocale();//allow to pass this explicitly?
		this.timezone = readTimeZone();
		
		if (report instanceof JasperDesign)
		{
			((JasperDesign)report).preprocess();
		}
		
		convert(ignoreContent);
	}

	private Locale readLocale()
	{
		//duplicates code from JRFillDataset.defaultLocale
		String localeCode = JRPropertiesUtil.getInstance(jasperReportsContext).getProperty(report, 
				JRFiller.PROPERTY_DEFAULT_LOCALE);
		Locale locale = (localeCode == null || localeCode.isEmpty()) 
				? Locale.getDefault()
				: JRDataUtils.getLocale(localeCode);
		return locale;
	}
	
	private TimeZone readTimeZone()
	{
		//duplicates code from JRFillDataset.defaultTimeZone
		String timezoneId = JRPropertiesUtil.getInstance(jasperReportsContext).getProperty(report, 
				JRFiller.PROPERTY_DEFAULT_TIMEZONE);
		TimeZone timezone = (timezoneId == null || timezoneId.isEmpty()) 
				? TimeZone.getDefault()
				: JRDataUtils.getTimeZone(timezoneId);
		return timezone;
	}
	
	/**
	 *
	 */
	protected void convert(boolean ignoreContent)
	{
		jasperPrint = new JasperPrint();
		
		jasperPrint.setFormatFactoryClass(report.getFormatFactoryClass());
		//FIXME locale and timezone settings jasperprint object
		//jasperPrint.setLocaleCode(JRDataUtils.getLocaleCode(Locale.getDefault()));
		//jasperPrint.setTimeZoneId(JRDataUtils.getTimeZoneId(TimeZone.getDefault()));
		//FIXMEFONT the locale is important for font
		//jasperPrint.setLocaleCode(report.getProperty(JRPropertiesUtil.PROPERTY_PREFIX + "locale"));
		//JRStyledTextAttributeSelector.setLocale(locale);
		jasperPrint.setName(report.getName());
		jasperPrint.setOrientation(report.getOrientationValue());
		jasperPrint.setPageWidth(report.getPageWidth());
		jasperPrint.setPageHeight(report.getPageHeight());
		jasperPrint.setTopMargin(report.getTopMargin());
		jasperPrint.setLeftMargin(report.getLeftMargin());
		jasperPrint.setBottomMargin(report.getBottomMargin());
		jasperPrint.setRightMargin(report.getRightMargin());
		
		JRPropertiesUtil.getInstance(jasperReportsContext).transferProperties(report, jasperPrint, JasperPrint.PROPERTIES_PRINT_TRANSFER_PREFIX);

		setStyles(report);

		if (!ignoreContent)
		{
			pageWidth = report.getPageWidth();
			page = new JRBasePrintPage();
			
			offsetY = report.getTopMargin();

			addBand(report.getBackground());
			addBand(report.getTitle());
			addBand(report.getPageHeader());
			upColumnHeader = offsetY;
			addBand(report.getColumnHeader(), true);
			downColumnHeader = offsetY;

			boolean isColumnGroupBands = report.getPrintOrderValue() == PrintOrderEnum.VERTICAL;
			
			JRGroup[] groups = report.getGroups();
			if (groups != null)
			{
				for (int i = 0; i < groups.length ; i++)
				{
					addSection(groups[i].getGroupHeaderSection(), isColumnGroupBands);
				}
			}
			
			upDetails = offsetY;
			addSection(report.getDetailSection(), true);
			downDetails = offsetY;

			if (groups != null)
			{
				for (int i = 0; i < groups.length ; i++)
				{
					addSection(groups[i].getGroupFooterSection(), isColumnGroupBands);
				}
			}
			
			upColumnFooter = offsetY;
			addBand(report.getColumnFooter(), true);
			downColumnFooter = offsetY;
			addBand(report.getPageFooter());
			addBand(report.getLastPageFooter());
			addBand(report.getSummary());
			addBand(report.getNoData());
			
			jasperPrint.setPageHeight(offsetY + report.getBottomMargin());
			
			// column dotted delimitation 
			int colX = report.getLeftMargin();
			for(int i = 0; i < report.getColumnCount(); i++)
			{
				addColumnSeparator(colX);
				colX += report.getColumnWidth();
				addColumnSeparator(colX);
				colX += report.getColumnSpacing();
			}

			// page dotted contour line
			addHorizontalGridLine(0, report.getTopMargin(), pageWidth);
			addHorizontalGridLine(0, offsetY, pageWidth);
			addVerticalGridLine(report.getLeftMargin(), 0, jasperPrint.getPageHeight());
			addVerticalGridLine(pageWidth - report.getRightMargin(), 0, jasperPrint.getPageHeight());

			page.setElements(pageElements);
			jasperPrint.addPage(page);
		}
	}

	protected void setStyles(JRReport report)
	{
		//styleFactory = new StyleFactory();
		stylesMap = new LinkedMap<>();
		
		loadReportStyles(report);
		
		try
		{
			for (Iterator<JRStyle> it = stylesMap.values().iterator(); it.hasNext();)
			{
				JRStyle style = it.next();
				jasperPrint.addStyle(style);
			}
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}

		JRStyle reportDefault = report.getDefaultStyle();
		JRStyle printDefault = null;
		if (reportDefault == null)
		{
			//search for the last default style
			for (Iterator<JRStyle> it = stylesMap.values().iterator(); it.hasNext();)
			{
				JRStyle style = it.next();
				if (style.isDefault())
				{
					printDefault = style;
				}
			}
		}
		else
		{
			printDefault = reportDefault;
		}
		
		if (printDefault != null)
		{
			jasperPrint.setDefaultStyle(printDefault);
		}		
	}

	protected void loadReportStyles(JRReport report)
	{
		JRReportTemplate[] templates = report.getTemplates();
		if (templates != null)
		{
			Set<String> loadedLocations = new HashSet<>();
			for (int i = 0; i < templates.length; i++)
			{
				loadReportTemplateStyles(templates[i], loadedLocations);
			}
		}
		
		collectStyles(report.getStyles());
	}

	protected void loadReportTemplateStyles(JRReportTemplate template, Set<String> loadedLocations)
	{
		JRExpression sourceExpression = template.getSourceExpression();
		if (sourceExpression != null)
		{
			String location = JRExpressionUtil.getSimpleExpressionText(sourceExpression);
			if (location == null)
			{
				log.warn("Template source expression " + sourceExpression.getText() 
						+ "cannot be evaluated; some styles might remain unresolved.");
			}
			else
			{
				HashSet<String> parentLocations = new HashSet<>();
				loadTemplateStyles(location, loadedLocations, parentLocations);
			}
		}
	}

	protected void loadTemplateStyles(String location, Set<String> loadedLocations, Set<String> parentLocations)
	{
		if (!parentLocations.add(location))
		{
			throw 
				new JRRuntimeException(
					EXCEPTION_MESSAGE_KEY_CIRCULAR_DEPENDENCY_FOUND,  
					new Object[]{location} 
					);
		}
		
		if (!loadedLocations.add(location))
		{
			//already loaded
			return;
		}
		
		JRTemplate template;
		try
		{
			template = JRXmlTemplateLoader.getInstance(getJasperReportsContext()).loadTemplate(location);//TODO lucianc repository context
		}
		catch (Exception e)
		{
			log.warn("Could not load template from location " + location 
					+ "; some styles might remain unresolved.");
			return;
		}
		
		JRTemplateReference[] includedTemplates = template.getIncludedTemplates();
		if (includedTemplates != null)
		{
			for (int i = 0; i < includedTemplates.length; i++)
			{
				JRTemplateReference reference = includedTemplates[i];
				loadTemplateStyles(reference.getLocation(), loadedLocations, parentLocations);
			}
		}
		
		collectStyles(template.getStyles());
	}

	protected void collectStyles(JRStyle[] styles)
	{
		if (styles != null)
		{
			for (int i = 0; i < styles.length; i++)
			{
				JRStyle style = styles[i];
				stylesMap.put(style.getName(), style);
			}
		}
	}

	/**
	 *
	 */
	private void addSection(JRSection section, boolean isColumnSection)
	{
		if (section != null)
		{
			JRBand[] bands = section.getBands();
			if (bands != null && bands.length > 0)
			{
				for(int i = 0; i< bands.length; i++)
				{
					addBand(bands[i], isColumnSection);
				}
			}
		}
	}

	/**
	 *
	 */
	private void addBand(JRBand band)
	{
		addBand(band, false);
	}

	/**
	 *
	 */
	private void addBand(JRBand band, boolean isColumnBand)
	{
		if (band != null)
		{
			JRBasePrintFrame frame = new JRBasePrintFrame(null);
			frame.setX(
				isColumnBand && report.getColumnDirection() == RunDirectionEnum.RTL 
					? report.getPageWidth() - report.getRightMargin() - report.getColumnWidth() 
					: report.getLeftMargin()
				);
			frame.setY(offsetY);
			frame.setWidth(
				isColumnBand
				? report.getColumnWidth()
				: report.getPageWidth() - report.getLeftMargin() - report.getRightMargin()
				);
			frame.setHeight(band.getHeight());
			
			band.visit(new ConvertVisitor(this, frame));
			
			pageElements.add(frame);
			
			offsetY += band.getHeight();
			addBandSeparator(offsetY);
		}
	}
	
	/**
	 *
	 */
	private void addBandSeparator(int bandY)
	{
		addHorizontalGridLine(0, bandY, pageWidth);
	}
	
	/**
	 *
	 */
	private void addColumnSeparator(int colX)
	{
		if (report.getPrintOrderValue() == PrintOrderEnum.HORIZONTAL)
		{
			if (downColumnHeader > upColumnHeader)
			{
				addVerticalGridLine(colX, upColumnHeader, downColumnHeader - upColumnHeader);
			}

			if (downDetails > upDetails)
			{
				addVerticalGridLine(colX, upDetails, downDetails - upDetails);
			}

			if (downColumnFooter > upColumnFooter)
			{
				addVerticalGridLine(colX, upColumnFooter, downColumnFooter - upColumnFooter);
			}
		}
		else //vertical printOrder
		{
			if (downColumnFooter > upColumnHeader)
			{
				addVerticalGridLine(colX, upColumnHeader, downColumnFooter - upColumnHeader);
			}
		}
	}
	
	/**
	 *
	 */
	private void addHorizontalGridLine(int x, int y, int width)
	{
		JRPrintFrame printFrame = new JRBasePrintFrame(getDefaultStyleProvider());
		printFrame.setX(x);
		printFrame.setY(y);
		printFrame.setWidth(width);
		printFrame.setHeight(1);
		printFrame.getLineBox().getPen().setLineWidth((Float)0f);
		printFrame.getLineBox().getPen().setLineStyle(LineStyleEnum.SOLID);
		printFrame.getLineBox().getTopPen().setLineWidth((Float)0.1f);
		printFrame.getLineBox().getTopPen().setLineStyle(LineStyleEnum.DASHED);
		printFrame.getLineBox().getTopPen().setLineColor(GRID_LINE_COLOR);
		pageElements.add(0, printFrame);
	}
	
	/**
	 *
	 */
	private void addVerticalGridLine(int x, int y, int height)
	{
		JRPrintFrame printFrame = new JRBasePrintFrame(getDefaultStyleProvider());
		printFrame.setX(x);
		printFrame.setY(y);
		printFrame.setWidth(1);
		printFrame.setHeight(height);
		printFrame.getLineBox().getPen().setLineWidth((Float)0f);
		printFrame.getLineBox().getPen().setLineStyle(LineStyleEnum.SOLID);
		printFrame.getLineBox().getLeftPen().setLineWidth((Float)0.1f);
		printFrame.getLineBox().getLeftPen().setLineStyle(LineStyleEnum.DASHED);
		printFrame.getLineBox().getLeftPen().setLineColor(GRID_LINE_COLOR);
		pageElements.add(0, printFrame);
	}
	
	/**
	 * 
	 */
	public JRStyle resolveStyle(JRStyleContainer originalContainer)
	{
		JRStyle originalStyle = originalContainer.getStyle();
		String nameReference = originalContainer.getStyleNameReference();
		JRStyle style;
		if (originalStyle != null)
		{
			style = originalStyle;
		}
		else if (nameReference != null)
		{
			style = stylesMap.get(nameReference);
			if (style == null)
			{
				log.warn("Style " + nameReference + " could not be resolved.");
			}
		}
		else
		{
			style = null;
		}
		return style;
	}	


	/**
	 * 
	 */	
	public JasperReportsContext getJasperReportsContext()
	{
		return jasperReportsContext;
	}

	public ComponentsEnvironment getComponentsEnvironment()
	{
		return componentsEnvironment;
	}
	
	
	/**
	 * 
	 */	
	public JRReport getReport()
	{
		return report;
	}
	
	
	/**
	 * 
	 */	
	public JasperPrint getJasperPrint()
	{
		return jasperPrint;
	}
	
	
	/**
	 * 
	 */	
	public JRDefaultStyleProvider getDefaultStyleProvider()
	{
		//return jasperPrint.getDefaultStyleProvider();
		return report;
	}

	
	public void copyBaseAttributes(JRElement source, JRPrintElement converted)
	{
		converted.setUUID(source.getUUID());
		converted.setX(source.getX());
		converted.setY(source.getY());
		converted.setWidth(source.getWidth());			
		converted.setHeight(source.getHeight());
		converted.setBackcolor(source.getOwnBackcolor());
		converted.setForecolor(source.getOwnForecolor());
		//printElement.setKey(element.getKey());
		converted.setMode(source.getOwnModeValue());
		converted.setStyle(resolveStyle(source));
	}

	public Locale getLocale()
	{
		return locale;
	}

	public TimeZone getTimeZone()
	{
		return timezone;
	}
	
}
