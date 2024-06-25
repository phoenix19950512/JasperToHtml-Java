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
package net.sf.jasperreports.charts.fill;

import net.sf.jasperreports.charts.JRItemLabel;
import net.sf.jasperreports.charts.JRPie3DPlot;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRFillPie3DPlot extends JRFillChartPlot implements JRPie3DPlot
{


	/**
	 *
	 */
	public JRFillPie3DPlot(
		JRPie3DPlot pie3DPlot, 
		JRFillObjectFactory factory
		)
	{
		super(pie3DPlot, factory);
	}
		

	@Override
	public Double getDepthFactorDouble()
	{
		return ((JRPie3DPlot)parent).getDepthFactorDouble();
	}
	
	@Override
	public Boolean getCircular()
	{
		return ((JRPie3DPlot)parent).getCircular();
	}
	
	@Override
	public String getLabelFormat()
	{
		return ((JRPie3DPlot)parent).getLabelFormat();
	}
	
	@Override
	public String getLegendLabelFormat()
	{
		return ((JRPie3DPlot)parent).getLegendLabelFormat();
	}
	
	@Override
	public JRItemLabel getItemLabel()
	{
		return ((JRPie3DPlot)parent).getItemLabel();
	}

	@Override
	public Boolean getShowLabels()
	{
		return ((JRPie3DPlot)parent).getShowLabels();
	}

}
