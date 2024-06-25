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
package net.sf.jasperreports.export.pdf;

import java.awt.Color;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.export.type.PdfFieldBorderStyleEnum;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public interface PdfField
{

	void setBorderWidth(float width);

	void setBackgroundColor(Color color);

	void setTextColor(Color color);
	
	void setAlignment(PdfTextAlignment alignment);

	void setBorderColor(Color color);

	void setBorderStyle(PdfFieldBorderStyleEnum borderStyle);
	
	void setReadOnly();

	void setText(String value);

	void setFont(Map<Attribute,Object> attributes, Locale locale);
	
	void setFontSize(float fontsize);

	void setRotation(int rotation);

	void setVisible();
	
}
