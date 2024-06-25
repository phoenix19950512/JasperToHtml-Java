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

import java.awt.Color;

import net.sf.jasperreports.charts.JRLinePlot;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.fill.JRFillChartPlot;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 */
public class JRFillLinePlot extends JRFillChartPlot implements JRLinePlot 
{
	
	/**
	 *
	 */
	protected JRFont categoryAxisLabelFont;
	protected Color categoryAxisLabelColor;
	protected JRFont categoryAxisTickLabelFont;
	protected Color categoryAxisTickLabelColor;
	protected Color categoryAxisLineColor;

	protected JRFont valueAxisLabelFont;
	protected Color valueAxisLabelColor;
	protected JRFont valueAxisTickLabelFont;
	protected Color valueAxisTickLabelColor;
	protected Color valueAxisLineColor;

	
	/**
	 *
	 */
	public JRFillLinePlot( JRLinePlot plot, JRFillObjectFactory factory )
	{
		super( plot, factory );

		categoryAxisLabelFont = factory.getFont(plot.getChart(), plot.getCategoryAxisLabelFont()); 
		categoryAxisLabelColor = plot.getOwnCategoryAxisLabelColor();
		categoryAxisTickLabelFont = factory.getFont(plot.getChart(), plot.getCategoryAxisTickLabelFont());
		categoryAxisTickLabelColor = plot.getOwnCategoryAxisTickLabelColor();
		categoryAxisLineColor = plot.getOwnCategoryAxisLineColor();
		
		valueAxisLabelFont = factory.getFont(plot.getChart(), plot.getValueAxisLabelFont());
		valueAxisLabelColor = plot.getOwnValueAxisLabelColor();
		valueAxisTickLabelFont = factory.getFont(plot.getChart(), plot.getValueAxisTickLabelFont());
		valueAxisTickLabelColor = plot.getOwnValueAxisTickLabelColor();
		valueAxisLineColor = plot.getOwnValueAxisLineColor();
	}
	
	@Override
	public JRExpression getCategoryAxisLabelExpression()
	{
		return ((JRLinePlot)parent).getCategoryAxisLabelExpression();
	}

	@Override
	public JRFont getCategoryAxisLabelFont()
	{
		return categoryAxisLabelFont;
	}

	@Override
	public Color getCategoryAxisLabelColor()
	{
		return getStyleResolver().getCategoryAxisLabelColor(this, this);
	}

	@Override
	public Color getOwnCategoryAxisLabelColor()
	{
		return categoryAxisLabelColor;
	}

	/**
	 *
	 */
	public void setCategoryAxisLabelColor(Color color)
	{
	}

	@Override
	public JRFont getCategoryAxisTickLabelFont()
	{
		return categoryAxisTickLabelFont;
	}

	@Override
	public Color getCategoryAxisTickLabelColor()
	{
		return getStyleResolver().getCategoryAxisTickLabelColor(this, this);
	}

	@Override
	public Color getOwnCategoryAxisTickLabelColor()
	{
		return categoryAxisTickLabelColor;
	}

	/**
	 *
	 */
	public void setCategoryAxisTickLabelColor(Color color)
	{
	}

	@Override
	public String getCategoryAxisTickLabelMask()
	{
		return ((JRLinePlot)parent).getCategoryAxisTickLabelMask();
	}

	@Override
	public Boolean getCategoryAxisVerticalTickLabels()
	{
		return ((JRLinePlot)parent).getCategoryAxisVerticalTickLabels();
	}

	@Override
	public Double getCategoryAxisTickLabelRotation()
	{
		return ((JRLinePlot)parent).getCategoryAxisTickLabelRotation();
	}

	@Override
	public void setCategoryAxisTickLabelRotation(Double labelRotation)
	{
	}

	@Override
	public Color getCategoryAxisLineColor()
	{
		return getStyleResolver().getCategoryAxisLineColor(this, this);
	}

	@Override
	public Color getOwnCategoryAxisLineColor()
	{
		return categoryAxisLineColor;
	}

	/**
	 *
	 */
	public void setCategoryAxisLineColor(Color color)
	{
	}

	@Override
	public JRExpression getValueAxisLabelExpression()
	{
		return ((JRLinePlot)parent).getValueAxisLabelExpression();
	}

	@Override
	public JRExpression getDomainAxisMinValueExpression()
	{
		return ((JRLinePlot)parent).getDomainAxisMinValueExpression();
	}

	@Override
	public JRExpression getDomainAxisMaxValueExpression()
	{
		return ((JRLinePlot)parent).getDomainAxisMaxValueExpression();
	}

	@Override
	public JRExpression getRangeAxisMinValueExpression()
	{
		return ((JRLinePlot)parent).getRangeAxisMinValueExpression();
	}

	@Override
	public JRExpression getRangeAxisMaxValueExpression()
	{
		return ((JRLinePlot)parent).getRangeAxisMaxValueExpression();
	}

	@Override
	public JRFont getValueAxisLabelFont()
	{
		return valueAxisLabelFont;
	}

	@Override
	public Color getValueAxisLabelColor()
	{
		return getStyleResolver().getValueAxisLabelColor(this, this);
	}

	@Override
	public Color getOwnValueAxisLabelColor()
	{
		return valueAxisLabelColor;
	}

	/**
	 *
	 */
	public void setValueAxisLabelColor(Color color)
	{
	}

	@Override
	public JRFont getValueAxisTickLabelFont()
	{
		return valueAxisTickLabelFont;
	}

	@Override
	public Color getValueAxisTickLabelColor()
	{
		return getStyleResolver().getValueAxisTickLabelColor(this, this);
	}

	@Override
	public Color getOwnValueAxisTickLabelColor()
	{
		return valueAxisTickLabelColor;
	}

	/**
	 *
	 */
	public void setValueAxisTickLabelColor(Color color)
	{
	}

	@Override
	public String getValueAxisTickLabelMask()
	{
		return ((JRLinePlot)parent).getValueAxisTickLabelMask();
	}

	@Override
	public Boolean getValueAxisVerticalTickLabels()
	{
		return ((JRLinePlot)parent).getValueAxisVerticalTickLabels();
	}

	@Override
	public Color getValueAxisLineColor()
	{
		return getStyleResolver().getValueAxisLineColor(this, this);
	}

	@Override
	public Color getOwnValueAxisLineColor()
	{
		return valueAxisLineColor;
	}

	/**
	 *
	 */
	public void setValueAxisLineColor(Color color)
	{
	}

	@Override
	public Boolean getShowShapes(){
		return ((JRLinePlot)parent).getShowShapes();
	}
	
	@Override
	public void setShowShapes( Boolean value ){
	}
	
	@Override
	public Boolean getShowLines(){
		return ((JRLinePlot)parent).getShowLines();
	}
	
	@Override
	public void setShowLines( Boolean value ){
	}
	
}
