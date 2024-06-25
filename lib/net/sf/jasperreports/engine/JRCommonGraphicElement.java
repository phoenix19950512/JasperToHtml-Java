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
package net.sf.jasperreports.engine;

import net.sf.jasperreports.engine.type.FillEnum;


/**
 * An abstract representation of a report graphic element. It provides basic functionality for images, lines, rectangles
 * and ellipses.
 * <p/>
 * Graphic elements are the second major category of report elements. This category
 * includes lines, rectangles, ellipses and images. They all have some properties in common, which
 * are grouped under the attributes of the <code>&lt;graphicElement&gt;</code> tag.
 * <h3>Background Fill Style</h3>
 * The <code>fill</code> attribute specifies the style of the background of the graphic elements. The
 * only style currently supported is the solid fill style, which is also the default
 * (<code>fill="Solid"</code>). The {@link #getFillValue()} method can 
 * be used to access the fill setting.
 * <h3>Line Settings</h3>
 * Other common settings for graphic elements are the line width, line style and the line color. These are 
 * grouped together into the <code>&lt;pen&gt;</code> tag and can be accessed using the {@link #getLinePen()} 
 * method.
 * 
 * @see net.sf.jasperreports.engine.JRPen
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface JRCommonGraphicElement extends JRCommonElement, JRPenContainer
{


	/**
	 *
	 */
	public JRPen getLinePen();

	/**
	 * Indicates the fill type used for this element.
	 * @return a value representing one of the fill type constants in {@link FillEnum}
	 */
	public FillEnum getFillValue();
	
	/**
	 * Indicates the own fill type used for this element.
	 * @return a value representing one of the fill type constants in {@link FillEnum}
	 */
	public FillEnum getOwnFillValue();
	
	/**
	 * Sets the fill type used for this element.
	 * @param fillEnum a value representing one of the line direction constants in {@link FillEnum}
	 */
	public void setFill(FillEnum fillEnum);
	
}
