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

import net.sf.jasperreports.engine.base.JRBoxPen;



/**
 * This is useful for drawing borders around text elements and images. Boxes can have borders and paddings, which can
 * have different width and color on each side of the element.
 * <p/>
 * Text elements, images, and charts are considered "box elements" because one can
 * surround them by a border that's customizable on each side. When defining the border
 * around such a box element, the user can control the width, style, and color of each of the
 * four sides of the element, as well as the padding (the amount of blank space to reserve
 * between the border of the element and its actual content).These properties are grouped 
 * into the <code>&lt;box&gt;</code> tag.
 * <h3>Border Line Settings</h3>
 * Border line settings such as line width, line style and the line color  
 * can be accessed using the {@link #getPen()} method and are grouped into the <code>pen</code> tag. 
 * <p/>
 * The attributes for specifying the border style for each side of the box are grouped into <code>topPen</code>,
 * <code>leftPen</code>, <code>bottomPen</code>, and <code>rightPen</code> tag. These can be used for overriding the
 * border style specified by the <code>pen</code> element mentioned previously. There is a getter method for each side 
 * pen element.
 * <h3>Box Padding</h3>
 * The amount of space to be left blank as margins within the bounds of a box element can
 * be controlled using either the <code>padding</code> attribute (providing the same amount of padding
 * on all four sides) or the individual attributes for each side: <code>topPadding</code>, 
 * <code>leftPadding</code>, <code>bottomPadding</code>, and <code>rightPadding</code>. Each padding 
 * attribute is accessed with a corresponding getter method.
 * 
 * @see net.sf.jasperreports.engine.JRPen
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface JRLineBox extends JRPenContainer
{

	/**
	 * 
	 */
	public JRBoxContainer getBoxContainer();

	/**
	 * 
	 */
	public JRLineBox clone(JRBoxContainer boxContainer);

	public void populateStyle();

	/**
	 * Gets the pen properties for the border.
	 */
	public JRBoxPen getPen();

	/**
	 *
	 */
	public void copyPen(JRBoxPen pen);

	/**
	 * Gets the pen properties for the top border.
	 */
	public JRBoxPen getTopPen();

	/**
	 *
	 */
	public void copyTopPen(JRBoxPen topPen);

	/**
	 * Gets the pen properties for the left border.
	 */
	public JRBoxPen getLeftPen();

	/**
	 *
	 */
	public void copyLeftPen(JRBoxPen leftPen);

	/**
	 * Gets the pen properties for the bottom border.
	 */
	public JRBoxPen getBottomPen();

	/**
	 *
	 */
	public void copyBottomPen(JRBoxPen bottomPen);

	/**
	 * Gets the pen properties for the right border.
	 */
	public JRBoxPen getRightPen();

	/**
	 *
	 */
	public void copyRightPen(JRBoxPen rightPen);

	
	/**
	 * Gets the default padding in pixels (can be overwritten by individual settings).
	 */
	public Integer getPadding();

	/**
	 *
	 */
	public Integer getOwnPadding();

	/**
	 * Sets the default padding in pixels (can be overwritten by individual settings).
	 */
	public void setPadding(Integer padding);

	/**
	 *
	 */
	public Integer getTopPadding();

	/**
	 *
	 */
	public Integer getOwnTopPadding();

	/**
	 *
	 */
	public void setTopPadding(Integer padding);

	/**
	 *
	 */
	public Integer getLeftPadding();

	/**
	 *
	 */
	public Integer getOwnLeftPadding();

	/**
	 *
	 */
	public void setLeftPadding(Integer padding);

	/**
	 *
	 */
	public Integer getBottomPadding();

	/**
	 *
	 */
	public Integer getOwnBottomPadding();

	/**
	 *
	 */
	public void setBottomPadding(Integer padding);

	/**
	 *
	 */
	public Integer getRightPadding();

	/**
	 *
	 */
	public Integer getOwnRightPadding();

	/**
	 *
	 */
	public void setRightPadding(Integer padding);
	
}
