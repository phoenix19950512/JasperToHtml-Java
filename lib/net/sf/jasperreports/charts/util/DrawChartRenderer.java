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
package net.sf.jasperreports.charts.util;

import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.jfree.chart.JFreeChart;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintImageAreaHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.renderers.DimensionRenderable;
import net.sf.jasperreports.renderers.Graphics2DRenderable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @deprecated Replaced by {@link DrawChartRendererImpl}.
 */
public class DrawChartRenderer extends net.sf.jasperreports.engine.JRAbstractSvgRenderer implements net.sf.jasperreports.engine.ImageMapRenderable, Graphics2DRenderable, DimensionRenderable
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private JFreeChart chart;
	private ChartHyperlinkProvider chartHyperlinkProvider;
	
	public DrawChartRenderer(JFreeChart chart, ChartHyperlinkProvider chartHyperlinkProvider)
	{
		this.chart = chart;
		this.chartHyperlinkProvider = chartHyperlinkProvider;
	}

	@Override
	public Dimension2D getDimension(JasperReportsContext jasperReportsContext) 
	{
		return null;
	}
	
	@Override
	public void render(JasperReportsContext jasperReportsContext, Graphics2D grx, Rectangle2D rectangle) 
	{
		if (chart != null)
		{
			chart.draw(grx, rectangle);
		}
	}
	
	/**
	 * @deprecated To be removed.
	 */
	@Override
	public List<JRPrintImageAreaHyperlink> renderWithHyperlinks(Graphics2D grx, Rectangle2D rectangle) 
	{
		try
		{
			render(grx, rectangle);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
		
		return ChartUtil.getImageAreaHyperlinks(chart, chartHyperlinkProvider, grx, rectangle);
	}
	
	@Override
	public List<JRPrintImageAreaHyperlink> getImageAreaHyperlinks(Rectangle2D renderingArea) throws JRException
	{
		return ChartUtil.getImageAreaHyperlinks(chart, chartHyperlinkProvider, null, renderingArea);
	}

	@Override
	public boolean hasImageAreaHyperlinks()
	{
		return chartHyperlinkProvider != null && chartHyperlinkProvider.hasHyperlinks();
	}
}
