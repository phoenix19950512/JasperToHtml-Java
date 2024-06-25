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

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.charts.JRXyzDataset;
import net.sf.jasperreports.charts.JRXyzSeries;
import net.sf.jasperreports.charts.util.DefaultXYZDataset;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.fill.JRCalculator;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.fill.JRFillChartDataset;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.util.Pair;

import org.jfree.data.general.Dataset;

/**
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 */
public class JRFillXyzDataset extends JRFillChartDataset implements JRXyzDataset {

	public static final String EXCEPTION_MESSAGE_KEY_SERIES_NULL_NAME = "charts.xyz.dataset.series.null.name";
	
	protected JRFillXyzSeries[] xyzSeries;

	private DefaultXYZDataset dataset;
	
	private Map<Comparable<?>, Map<Pair, JRPrintHyperlink>> itemHyperlinks;
	
	
	public JRFillXyzDataset(JRXyzDataset xyzDataset, JRFillObjectFactory factory)
	{
		super( xyzDataset, factory );
		
		JRXyzSeries[] srcXyzSeries = xyzDataset.getSeries();
		if(srcXyzSeries != null && srcXyzSeries.length > 0)
		{
			xyzSeries = new JRFillXyzSeries[srcXyzSeries.length];
			for(int i = 0; i < xyzSeries.length; i++)
			{
				xyzSeries[i] = (JRFillXyzSeries)factory.getXyzSeries( srcXyzSeries[i]);
			}
		}
	}
	
	@Override
	public JRXyzSeries[] getSeries(){
		return xyzSeries;
	}
	
	@Override
	protected void customInitialize()
	{
		dataset = new DefaultXYZDataset();
		itemHyperlinks = new HashMap<>();
	}
	
	@Override
	protected void customEvaluate( JRCalculator calculator ) throws JRExpressionEvalException 
	{
		if (xyzSeries != null && xyzSeries.length > 0)
		{
			for (int i = 0; i < xyzSeries.length; i++)
			{
				xyzSeries[i].evaluate( calculator );
			}
		}
	}
	
	@Override
	protected void customIncrement()
	{
		if (xyzSeries != null && xyzSeries .length > 0)
		{
			for (int i = 0; i < xyzSeries.length; i++)
			{
				JRFillXyzSeries crtXyzSeries = xyzSeries[i];
				
				Comparable<?> seriesName = crtXyzSeries.getSeries();
				if (seriesName == null)
				{
					throw 
						new JRRuntimeException(
							EXCEPTION_MESSAGE_KEY_SERIES_NULL_NAME,  
							(Object[])null 
							);
				}

				dataset.addValue(
					crtXyzSeries.getSeries(), 
					crtXyzSeries.getXValue(),
					crtXyzSeries.getYValue(),
					crtXyzSeries.getZValue()
					);
				
				if (crtXyzSeries.hasItemHyperlinks())
				{
					Map<Pair, JRPrintHyperlink> seriesLinks = itemHyperlinks.get(crtXyzSeries.getSeries());
					if (seriesLinks == null)
					{
						seriesLinks = new HashMap<>();
						itemHyperlinks.put(crtXyzSeries.getSeries(), seriesLinks);
					}
					Pair<Number,Number> xyKey = new Pair<>(crtXyzSeries.getXValue(), crtXyzSeries.getYValue());
					seriesLinks.put(xyKey, crtXyzSeries.getPrintItemHyperlink());
				}
			}
		}
	}
	
	@Override
	public Object getLabelGenerator()
	{
		return null;
	}

	@Override
	public Dataset getCustomDataset() {
		return dataset;
	}

	@Override
	public byte getDatasetType() {
		return JRChartDataset.XYZ_DATASET;
	}


	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}
	
	
	public boolean hasItemHyperlinks()
	{
		boolean foundLinks = false;
		if (xyzSeries != null && xyzSeries.length > 0)
		{
			for (int i = 0; i < xyzSeries.length && !foundLinks; i++)
			{
				JRFillXyzSeries serie = xyzSeries[i];
				foundLinks = serie.hasItemHyperlinks();
			}
		}
		return foundLinks;
	}

	
	public Map<Comparable<?>, Map<Pair, JRPrintHyperlink>> getItemHyperlinks()
	{
		return itemHyperlinks;
	}


	@Override
	public void validate(JRVerifier verifier)
	{
		verifier.verify(this);
	}

}
