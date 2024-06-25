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
package net.sf.jasperreports.engine.xml;

import java.awt.Color;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

import net.sf.jasperreports.engine.JRCommonText;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.type.FillEnum;
import net.sf.jasperreports.engine.type.HorizontalImageAlignEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.LineSpacingEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.PenEnum;
import net.sf.jasperreports.engine.type.RotationEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.type.VerticalImageAlignEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.engine.util.JRColorUtil;
import net.sf.jasperreports.engine.util.JRPenUtil;

/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 */
public abstract class JRAbstractStyleFactory extends JRBaseFactory
{
	private static final Log log = LogFactory.getLog(JRAbstractStyleFactory.class);

	@Override
	public Object createObject(Attributes atts)
	{
		JRDesignStyle style = new JRDesignStyle(getDefaultStyleProvider());

		// get style name
		style.setName(atts.getValue(JRXmlConstants.ATTRIBUTE_name));

		String isDefault = atts.getValue(JRXmlConstants.ATTRIBUTE_isDefault);
		if (isDefault != null && isDefault.length() > 0)
		{
			style.setDefault(Boolean.valueOf(isDefault));
		}

		// get parent style
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_style) != null)
		{
			setParentStyle(style, atts.getValue(JRXmlConstants.ATTRIBUTE_style));
		}

		// set common style attributes
		setCommonStyle(style, atts);

		return style;
	}

	/**
	 *
	 */
	protected JRDefaultStyleProvider getDefaultStyleProvider()
	{
		return null;
	}

	/**
	 *
	 */
	protected void setCommonStyle(JRStyle style, Attributes atts)
	{
		// get JRElement attributes
		ModeEnum mode = ModeEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_mode));
		if (mode != null)
		{
			style.setMode(mode);
		}

		String forecolor = atts.getValue(JRXmlConstants.ATTRIBUTE_forecolor);
		style.setForecolor(JRColorUtil.getColor(forecolor, null));

		String backcolor = atts.getValue(JRXmlConstants.ATTRIBUTE_backcolor);
		style.setBackcolor(JRColorUtil.getColor(backcolor, null));

		// get graphic element attributes
		PenEnum pen = PenEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_pen));
		if (pen != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'pen' attribute is deprecated. Use the <pen> tag instead.");
			}
				
			JRPenUtil.setLinePenFromPen(pen, style.getLinePen());
		}
		

		FillEnum fill = FillEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_fill));
		if(fill != null)
		{
			style.setFill(fill);
		}


		// get rectangle attributes
		String radius = atts.getValue(JRXmlConstants.ATTRIBUTE_radius);
		if (radius != null && radius.length() > 0)
		{
			style.setRadius(Integer.valueOf(radius));
		}

		// get image attributes
		ScaleImageEnum scaleImage = ScaleImageEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_scaleImage));
		if (scaleImage != null)
		{
			style.setScaleImage(scaleImage);
		}

		HorizontalTextAlignEnum horizontalTextAlign = HorizontalTextAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_hAlign));
		if (horizontalTextAlign != null)
		{
			style.setHorizontalTextAlign(horizontalTextAlign);
		}
		horizontalTextAlign = HorizontalTextAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_hTextAlign));
		if (horizontalTextAlign != null)
		{
			style.setHorizontalTextAlign(horizontalTextAlign);
		}

		VerticalTextAlignEnum verticalTextAlign = VerticalTextAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_vAlign));
		if (verticalTextAlign != null)
		{
			style.setVerticalTextAlign(verticalTextAlign);
		}
		verticalTextAlign = VerticalTextAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_vTextAlign));
		if (verticalTextAlign != null)
		{
			style.setVerticalTextAlign(verticalTextAlign);
		}

		HorizontalImageAlignEnum horizontalImageAlign = HorizontalImageAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_hAlign));
		if (horizontalImageAlign != null)
		{
			style.setHorizontalImageAlign(horizontalImageAlign);
		}
		horizontalImageAlign = HorizontalImageAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_hImageAlign));
		if (horizontalImageAlign != null)
		{
			style.setHorizontalImageAlign(horizontalImageAlign);
		}

		VerticalImageAlignEnum verticalImageAlign = VerticalImageAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_vAlign));
		if (verticalImageAlign != null)
		{
			style.setVerticalImageAlign(verticalImageAlign);
		}
		verticalImageAlign = VerticalImageAlignEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_vImageAlign));
		if (verticalImageAlign != null)
		{
			style.setVerticalImageAlign(verticalImageAlign);
		}

		// get box attributes
		PenEnum border = PenEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_border));
		if (border != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'border' attribute is deprecated. Use the <pen> tag instead.");
			}
			JRPenUtil.setLinePenFromPen(border, style.getLineBox().getPen());
		}

		Color borderColor = JRColorUtil.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_borderColor), null);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'borderColor' attribute is deprecated. Use the <pen> tag instead.");
			}
			style.getLineBox().getPen().setLineColor(borderColor);
		}

		String padding = atts.getValue(JRXmlConstants.ATTRIBUTE_padding);
		if (padding != null && padding.length() > 0)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'padding' attribute is deprecated. Use the <box> tag instead.");
			}
			style.getLineBox().setPadding(Integer.valueOf(padding));
		}

		border = PenEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_topBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'topBorder' attribute is deprecated. Use the <pen> tag instead.");
			}
			JRPenUtil.setLinePenFromPen(border, style.getLineBox().getTopPen());
		}

		borderColor = JRColorUtil.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_topBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'topBorderColor' attribute is deprecated. Use the <pen> tag instead.");
			}
			style.getLineBox().getTopPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_topPadding);
		if (padding != null && padding.length() > 0)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'topPadding' attribute is deprecated. Use the <box> tag instead.");
			}	
			style.getLineBox().setTopPadding(Integer.valueOf(padding));
		}

		border = PenEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_leftBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'leftBorder' attribute is deprecated. Use the <pen> tag instead.");
			}
			JRPenUtil.setLinePenFromPen(border, style.getLineBox().getLeftPen());
		}

		borderColor = JRColorUtil.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_leftBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'leftBorderColor' attribute is deprecated. Use the <pen> tag instead.");
			}	
			style.getLineBox().getLeftPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_leftPadding);
		if (padding != null && padding.length() > 0)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'leftPadding' attribute is deprecated. Use the <box> tag instead.");
			}
			style.getLineBox().setLeftPadding(Integer.valueOf(padding));
		}

		border = PenEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_bottomBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'bottomBorder' attribute is deprecated. Use the <pen> tag instead.");
			}
			JRPenUtil.setLinePenFromPen(border, style.getLineBox().getBottomPen());
		}

		borderColor = JRColorUtil.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_bottomBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'bottomBorderColor' attribute is deprecated. Use the <pen> tag instead.");
			}
			style.getLineBox().getBottomPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_bottomPadding);
		if (padding != null && padding.length() > 0)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'bottomPadding' attribute is deprecated. Use the <box> tag instead.");
			}	
			style.getLineBox().setBottomPadding(Integer.valueOf(padding));
		}

		border = PenEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_rightBorder));
		if (border != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'rightBorder' attribute is deprecated. Use the <pen> tag instead.");
			}
			JRPenUtil.setLinePenFromPen(border, style.getLineBox().getRightPen());
		}

		borderColor = JRColorUtil.getColor(atts.getValue(JRXmlConstants.ATTRIBUTE_rightBorderColor), Color.black);
		if (borderColor != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'rightBorderColor' attribute is deprecated. Use the <pen> tag instead.");
			}
			style.getLineBox().getRightPen().setLineColor(borderColor);
		}

		padding = atts.getValue(JRXmlConstants.ATTRIBUTE_rightPadding);
		if (padding != null && padding.length() > 0)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'rightPadding' attribute is deprecated. Use the <box> tag instead.");
			}
			style.getLineBox().setRightPadding(Integer.valueOf(padding));
		}


		RotationEnum rotation = RotationEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_rotation));
		if (rotation != null)
		{
			style.setRotation(rotation);
		}

		LineSpacingEnum lineSpacing = LineSpacingEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_lineSpacing));
		if (lineSpacing != null)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'lineSpacing' attribute is deprecated. Use the <paragraph> tag instead.");
			}
			style.getParagraph().setLineSpacing(lineSpacing);
		}

		style.setMarkup(atts.getValue(JRXmlConstants.ATTRIBUTE_markup));

		String isStyledText = atts.getValue(JRXmlConstants.ATTRIBUTE_isStyledText);
		if (isStyledText != null && isStyledText.length() > 0)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'isStyledText' attribute is deprecated. Use the 'markup' attribute instead.");
			}
			style.setMarkup(Boolean.valueOf(isStyledText) ? JRCommonText.MARKUP_STYLED_TEXT : JRCommonText.MARKUP_NONE);
		}

		style.setPattern(atts.getValue(JRXmlConstants.ATTRIBUTE_pattern));

		String isBlankWhenNull = atts.getValue(JRXmlConstants.ATTRIBUTE_isBlankWhenNull);
		if (isBlankWhenNull != null && isBlankWhenNull.length() > 0)
		{
			style.setBlankWhenNull(Boolean.valueOf(isBlankWhenNull));
		}

		if (atts.getValue(JRXmlConstants.ATTRIBUTE_fontName) != null)
		{
			style.setFontName(atts.getValue(JRXmlConstants.ATTRIBUTE_fontName));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_isBold) != null)
		{
			style.setBold(Boolean.valueOf(atts.getValue(JRXmlConstants.ATTRIBUTE_isBold)));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_isItalic) != null)
		{
			style.setItalic(Boolean.valueOf(atts.getValue(JRXmlConstants.ATTRIBUTE_isItalic)));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_isUnderline) != null)
		{
			style.setUnderline(Boolean.valueOf(atts.getValue(JRXmlConstants.ATTRIBUTE_isUnderline)));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_isStrikeThrough) != null)
		{
			style.setStrikeThrough(Boolean.valueOf(atts.getValue(JRXmlConstants.ATTRIBUTE_isStrikeThrough)));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_fontSize) != null)
		{
			style.setFontSize(Float.valueOf(atts.getValue(JRXmlConstants.ATTRIBUTE_fontSize)));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_pdfFontName) != null)
		{
			style.setPdfFontName(atts.getValue(JRXmlConstants.ATTRIBUTE_pdfFontName));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_pdfEncoding) != null)
		{
			style.setPdfEncoding(atts.getValue(JRXmlConstants.ATTRIBUTE_pdfEncoding));
		}
		if (atts.getValue(JRXmlConstants.ATTRIBUTE_isPdfEmbedded) != null)
		{
			style.setPdfEmbedded(Boolean.valueOf(atts.getValue(JRXmlConstants.ATTRIBUTE_isPdfEmbedded)));
		}
	}
	
	protected abstract void setParentStyle(JRDesignStyle currentStyle, String parentStyleName);
	
}
