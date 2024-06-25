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
 * Represents the series for the Time Series dataset.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface JRTimeSeries extends JRCloneable
{
	
	/**
	 * @return a <code>java.lang.Comparable</code> object representing 
	 * the expression of the series name. Specifies the series to which 
	 * to add the current value pair when incrementing the dataset.
	 */
	public JRExpression getSeriesExpression();

	/**
	 * @return a <code>java.util.Date</code> expression from which the engine 
	 * will extract the corresponding time period depending on the value set 
	 * for the <code>timePeriod</code> attribute.
	 * @see JRTimeSeriesDataset#getTimePeriodValue()
	 */
	public JRExpression getTimePeriodExpression();

	/**
	 * @return a numeric expression representing the 
	 * value to associate with the corresponding time period value when 
	 * incrementing the current series of the dataset.
	 */
	public JRExpression getValueExpression();

	/**
	 * @return the label expression. If present, it helps 
	 * customize the item labels inside charts.
	 */
	public JRExpression getLabelExpression();

	
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
