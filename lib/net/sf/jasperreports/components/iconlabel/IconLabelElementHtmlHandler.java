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
package net.sf.jasperreports.components.iconlabel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBasePrintFrame;
import net.sf.jasperreports.engine.export.GenericElementHtmlHandler;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterContext;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class IconLabelElementHtmlHandler implements GenericElementHtmlHandler
{
	private static final IconLabelElementHtmlHandler INSTANCE = new IconLabelElementHtmlHandler();
	
	public static IconLabelElementHtmlHandler getInstance()
	{
		return INSTANCE;
	}

	@Override
	public String getHtmlFragment(JRHtmlExporterContext context, JRGenericPrintElement element)
	{
		JRPrintText labelPrintText = (JRPrintText)element.getParameterValue(IconLabelElement.PARAMETER_LABEL_TEXT_ELEMENT);
		if (labelPrintText == null)
		{
			return null;
		}

		JRBasePrintFrame frame = new JRBasePrintFrame(element.getDefaultStyleProvider());
		frame.setX(element.getX());
		frame.setY(element.getY());
		frame.setWidth(element.getWidth());
		frame.setHeight(element.getHeight());
		frame.setStyle(element.getStyle());
		frame.setBackcolor(element.getBackcolor());
		frame.setForecolor(element.getForecolor());
		frame.setMode(element.getModeValue());
		JRLineBox lineBox = (JRLineBox)element.getParameterValue(IconLabelElement.PARAMETER_LINE_BOX);
		if (lineBox != null)
		{
			frame.copyBox(lineBox);
		}
		
		frame.addElement(labelPrintText);
		
		JRPrintText iconPrintText = (JRPrintText)element.getParameterValue(IconLabelElement.PARAMETER_ICON_TEXT_ELEMENT);
		if (iconPrintText != null)
		{
			frame.addElement(iconPrintText);
		}

		HtmlExporter htmlExporter = (HtmlExporter)context.getExporterRef();
		List<JRPrintElement> elements = new ArrayList<>();
		elements.add(frame);

		try
		{
			htmlExporter.exportElements(elements);
		}
		catch (IOException e)
		{
			throw new JRRuntimeException(e);
		}
		
		return "";
	}

	@Override
	public boolean toExport(JRGenericPrintElement element) 
	{
		return true;
	}
}
