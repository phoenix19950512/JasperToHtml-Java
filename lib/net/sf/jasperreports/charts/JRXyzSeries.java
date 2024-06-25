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
package net.sf.jasperreports.charts;

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 */
public interface JRXyzSeries extends JRCloneable 
{
	/**
	 * @return a <code>java.lang.Comparable</code> object that identifies 
	 * a certain data series in the overall dataset. It can return different values, 
	 * which will result in the dataset containing multiple series even when a single 
	 * <code>&lt;xyzSeries&gt;</code> tag is used inside the <code>&lt;xyzDataset&gt;</code> tag. 
	 */
	public JRExpression getSeriesExpression();
	
	/**
	 * @return a numeric expression representing the X value from the (x, y, z) item 
	 * that will be added to the current data series.
	 */
	public JRExpression getXValueExpression();
	
	/**
	 * @return a numeric expression representing the Y value from the (x, y, z) item 
	 * that will be added to the current data series.
	 */
	public JRExpression getYValueExpression();
	
	/**
	 * @return a numeric expression representing the Z value from the (x, y, z) item 
	 * that will be added to the current data series.
	 */
	public JRExpression getZValueExpression();

	
	/**
	 * Returns the hyperlink specification for chart items.
	 * <p>
	 * The hyperlink will be evaluated for every chart item and a image map will be created for the chart.
	 * </p>
	 * 
	 * @return hyperlink specification for chart items
	 */
	public JRHyperlink getItemHyperlink();
	
}
