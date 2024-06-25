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
package net.sf.jasperreports.charts.base;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.jfree.chart.renderer.xy.XYBubbleRenderer;

import net.sf.jasperreports.charts.JRBubblePlot;
import net.sf.jasperreports.charts.type.ScaleTypeEnum;
import net.sf.jasperreports.charts.util.ChartUtil;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.util.JRCloneUtils;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 */
public class JRBaseBubblePlot extends JRBaseChartPlot implements JRBubblePlot {

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_SCALE_TYPE = "scaleType";
	
	protected JRExpression xAxisLabelExpression;
	protected JRFont xAxisLabelFont;
	protected Color xAxisLabelColor;
	protected JRFont xAxisTickLabelFont;
	protected Color xAxisTickLabelColor;
	protected String xAxisTickLabelMask;
	protected Boolean xAxisVerticalTickLabels;
	protected Color xAxisLineColor;

	protected JRExpression yAxisLabelExpression;
	protected JRFont yAxisLabelFont;
	protected Color yAxisLabelColor;
	protected JRFont yAxisTickLabelFont;
	protected Color yAxisTickLabelColor;
	protected String yAxisTickLabelMask;
	protected Boolean yAxisVerticalTickLabels;
	protected Color yAxisLineColor;
	
	protected JRExpression domainAxisMinValueExpression;
	protected JRExpression domainAxisMaxValueExpression;
	protected JRExpression rangeAxisMinValueExpression;
	protected JRExpression rangeAxisMaxValueExpression;
	protected ScaleTypeEnum scaleTypeValue;
	
	
	/**
	 * 
	 */
	public JRBaseBubblePlot(JRChartPlot plot, JRChart chart)
	{
		super(plot, chart);
		
		JRBubblePlot bubblePlot = plot instanceof JRBubblePlot ? (JRBubblePlot)plot : null;
		if (bubblePlot != null)
		{
			xAxisLabelFont = bubblePlot.getXAxisLabelFont();
			xAxisTickLabelFont = bubblePlot.getXAxisTickLabelFont();
			yAxisLabelFont = bubblePlot.getYAxisLabelFont();
			yAxisTickLabelFont = bubblePlot.getYAxisTickLabelFont();
		}
	}


	/**
	 * 
	 */
	public JRBaseBubblePlot(JRBubblePlot bubblePlot, JRBaseObjectFactory factory )
	{
		super( bubblePlot, factory );
		
		scaleTypeValue = bubblePlot.getScaleTypeValue();
		
		xAxisLabelExpression = factory.getExpression( bubblePlot.getXAxisLabelExpression() );
		xAxisLabelFont = factory.getFont(chart, bubblePlot.getXAxisLabelFont());
		xAxisLabelColor = bubblePlot.getOwnXAxisLabelColor();
		xAxisTickLabelFont = factory.getFont(chart, bubblePlot.getXAxisTickLabelFont());
		xAxisTickLabelColor = bubblePlot.getOwnXAxisTickLabelColor();
		xAxisTickLabelMask = bubblePlot.getXAxisTickLabelMask();
		xAxisVerticalTickLabels = bubblePlot.getXAxisVerticalTickLabels();
		xAxisLineColor = bubblePlot.getOwnXAxisLineColor();
		
		yAxisLabelExpression = factory.getExpression( bubblePlot.getYAxisLabelExpression() );
		yAxisLabelFont = factory.getFont(chart, bubblePlot.getYAxisLabelFont());
		yAxisLabelColor = bubblePlot.getOwnYAxisLabelColor();
		yAxisTickLabelFont = factory.getFont(chart, bubblePlot.getYAxisTickLabelFont());
		yAxisTickLabelColor = bubblePlot.getOwnYAxisTickLabelColor();
		yAxisTickLabelMask = bubblePlot.getYAxisTickLabelMask();
		yAxisVerticalTickLabels = bubblePlot.getYAxisVerticalTickLabels();
		yAxisLineColor = bubblePlot.getOwnYAxisLineColor();
		
		domainAxisMinValueExpression = factory.getExpression( bubblePlot.getDomainAxisMinValueExpression() );
		domainAxisMaxValueExpression = factory.getExpression( bubblePlot.getDomainAxisMaxValueExpression() );
		rangeAxisMinValueExpression = factory.getExpression( bubblePlot.getRangeAxisMinValueExpression() );
		rangeAxisMaxValueExpression = factory.getExpression( bubblePlot.getRangeAxisMaxValueExpression() );
	}
	
	@Override
	public JRExpression getXAxisLabelExpression(){
		return xAxisLabelExpression;
	}
	
	@Override
	public JRFont getXAxisLabelFont()
	{
		return xAxisLabelFont;
	}
	
	@Override
	public Color getXAxisLabelColor()
	{
		return getStyleResolver().getXAxisLabelColor(this, this);
	}
		
	@Override
	public Color getOwnXAxisLabelColor()
	{
		return xAxisLabelColor;
	}
		
	@Override
	public JRFont getXAxisTickLabelFont()
	{
		return xAxisTickLabelFont;
	}
	
	@Override
	public Color getXAxisTickLabelColor()
	{
		return getStyleResolver().getXAxisTickLabelColor(this, this);
	}

	@Override
	public Color getOwnXAxisTickLabelColor()
	{
		return xAxisTickLabelColor;
	}

	@Override
	public String getXAxisTickLabelMask()
	{
		return xAxisTickLabelMask;
	}

	@Override
	public Boolean getXAxisVerticalTickLabels()
	{
		return xAxisVerticalTickLabels;
	}

	@Override
	public Color getXAxisLineColor()
	{
		return getStyleResolver().getXAxisLineColor(this, this);
	}

	@Override
	public Color getOwnXAxisLineColor()
	{
		return xAxisLineColor;
	}

	@Override
	public JRExpression getYAxisLabelExpression(){
		return yAxisLabelExpression;
	}
	
	@Override
	public JRFont getYAxisLabelFont()
	{
		return yAxisLabelFont;
	}
	
	@Override
	public Color getYAxisLabelColor()
	{
		return getStyleResolver().getYAxisLabelColor(this, this);
	}
	
	@Override
	public Color getOwnYAxisLabelColor()
	{
		return yAxisLabelColor;
	}
	
	@Override
	public JRFont getYAxisTickLabelFont()
	{
		return yAxisTickLabelFont;
	}
	
	@Override
	public Color getYAxisTickLabelColor()
	{
		return getStyleResolver().getYAxisTickLabelColor(this, this);
	}
	
	@Override
	public Color getOwnYAxisTickLabelColor()
	{
		return yAxisTickLabelColor;
	}
	
	@Override
	public String getYAxisTickLabelMask()
	{
		return yAxisTickLabelMask;
	}

	@Override
	public Boolean getYAxisVerticalTickLabels()
	{
		return yAxisVerticalTickLabels;
	}

	@Override
	public Color getYAxisLineColor()
	{
		return getStyleResolver().getYAxisLineColor(this, this);
	}
	
	@Override
	public Color getOwnYAxisLineColor()
	{
		return yAxisLineColor;
	}
	
	@Override
	public ScaleTypeEnum getScaleTypeValue(){
		return scaleTypeValue;
	}
	
	@Override
	public JRExpression getDomainAxisMinValueExpression(){
		return domainAxisMinValueExpression;
	}

	@Override
	public JRExpression getDomainAxisMaxValueExpression(){
		return domainAxisMaxValueExpression;
	}

	@Override
	public JRExpression getRangeAxisMinValueExpression(){
		return rangeAxisMinValueExpression;
	}

	@Override
	public JRExpression getRangeAxisMaxValueExpression(){
		return rangeAxisMaxValueExpression;
	}

	@Override
	public void setScaleType( ScaleTypeEnum scaleTypeValue ){
		ScaleTypeEnum old = this.scaleTypeValue;
		this.scaleTypeValue = scaleTypeValue;
		getEventSupport().firePropertyChange(PROPERTY_SCALE_TYPE, old, this.scaleTypeValue);
	}

	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}

	@Override
	public Object clone(JRChart parentChart) 
	{
		JRBaseBubblePlot clone = (JRBaseBubblePlot)super.clone(parentChart);
		clone.xAxisLabelExpression = JRCloneUtils.nullSafeClone(xAxisLabelExpression);
		clone.yAxisLabelExpression = JRCloneUtils.nullSafeClone(yAxisLabelExpression);
		clone.domainAxisMinValueExpression = JRCloneUtils.nullSafeClone(domainAxisMinValueExpression);
		clone.domainAxisMaxValueExpression = JRCloneUtils.nullSafeClone(domainAxisMaxValueExpression);
		clone.rangeAxisMinValueExpression = JRCloneUtils.nullSafeClone(rangeAxisMinValueExpression);
		clone.rangeAxisMaxValueExpression = JRCloneUtils.nullSafeClone(rangeAxisMaxValueExpression);
		return clone;
	}
	
	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private int scaleType = XYBubbleRenderer.SCALE_ON_RANGE_AXIS;
	/**
	 * @deprecated
	 */
	private Integer scaleTypeInteger;
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_1_0)
		{
			scaleTypeValue = ChartUtil.getScaleType(scaleType);
		}
		else if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2)
		{
			scaleTypeValue = ChartUtil.getScaleType(scaleTypeInteger);
			scaleTypeInteger = null;
		}
	}
	
}
