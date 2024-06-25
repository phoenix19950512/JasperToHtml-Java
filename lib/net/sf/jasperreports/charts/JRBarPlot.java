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



/**
 * Type of plot used to render Bar, Stacked Bar, and XY Bar charts. 
 * It is a {@link net.sf.jasperreports.charts.JRCategoryPlot JRCategoryPlot} that exposes 
 * specific settings for showing/hiding axis labels, tick marks and tick labels.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface JRBarPlot extends JRCategoryPlot
{
	
	/**
	 * @return a flag that specifies whether the tick marks are to be shown or not
	 */
	public Boolean getShowTickMarks();

	/**
	 * Sets a flag that specifies whether the tick marks are to be shown or not
	 * @param isShowTickMarks the show tick marks flag
	 */
	public void setShowTickMarks(Boolean isShowTickMarks);
		
	/**
	 * @return a flag that specifies whether the tick labels are to be shown or not
	 */
	public Boolean getShowTickLabels();
	
	/**
	 * @return a {@link net.sf.jasperreports.charts.JRItemLabel JRItemLabel} object 
	 * representing the item label
	 */
	public JRItemLabel getItemLabel();
	
	/**
	 * Sets a flag that specifies whether the tick labels are to be shown or not
	 * @param isShowTickLabels the show tick labels flag
	 */
	public void setShowTickLabels(Boolean isShowTickLabels);

	/**
	 * @return a flag that specifies whether the labels are to be shown or not
	 */
	public Boolean getShowLabels();
	
	/**
	 * Sets a flag that specifies whether the labels are to be shown or not
	 * @param isShowLabels the show labels flag
	 */
	public void setShowLabels( Boolean isShowLabels );

}
