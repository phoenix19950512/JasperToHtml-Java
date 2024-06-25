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

import java.io.IOException;
import java.io.ObjectInputStream;

import net.sf.jasperreports.charts.ChartCopyObjectFactory;
import net.sf.jasperreports.charts.JRItemLabel;
import net.sf.jasperreports.charts.JRPiePlot;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartPlot;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.base.JRBaseChartPlot;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRBasePiePlot extends JRBaseChartPlot implements JRPiePlot
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_CIRCULAR = "isCircular";

	public static final String PROPERTY_LABEL_FORMAT = "labelFormat";
	public static final String PROPERTY_LEGEND_LABEL_FORMAT = "legendLabelFormat";
	public static final String PROPERTY_ITEM_LABEL = "itemLabel";

	public static final String PROPERTY_SHOW_LABELS = "isShowLabels";
	/*
	 * README
	 * 
	 * Make sure that all fields are read in readObject().
	 */
	protected Boolean circular;
	protected String labelFormat;
	protected String legendLabelFormat;
	
	protected JRItemLabel itemLabel;
	protected Boolean showLabels;

	/**
	 *
	 */
	public JRBasePiePlot(JRChartPlot chartPlot, JRChart chart)
	{
		this(chartPlot, chart, ChartCopyBaseObjectFactory.instance());
	}

	protected JRBasePiePlot(JRChartPlot chartPlot, JRChart chart, ChartCopyObjectFactory copyObjectFactory)
	{
		super(chartPlot, chart);
		
		JRPiePlot piePlot = chartPlot instanceof JRPiePlot ? (JRPiePlot)chartPlot : null;
		
		if (piePlot == null)
		{
			itemLabel = copyObjectFactory.copyItemLabel(null, chart);
		}
		else
		{
			itemLabel = copyObjectFactory.copyItemLabel(piePlot.getItemLabel(), chart);
		}
	}


	/**
	 *
	 */
	public JRBasePiePlot(JRPiePlot piePlot, JRBaseObjectFactory factory)
	{
		super(piePlot, factory);
		circular = piePlot.getCircular();
		labelFormat = piePlot.getLabelFormat();
		legendLabelFormat = piePlot.getLegendLabelFormat();
		itemLabel = new JRBaseItemLabel(piePlot.getItemLabel(), factory);
		showLabels = piePlot.getShowLabels();
	}
	
	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
	}


	/**
	 * @return the circular
	 */
	@Override
	public Boolean getCircular() {
		return circular;
	}

	/**
	 * @param isCircular the isCircular to set
	 */
	public void setCircular(Boolean isCircular) {
		Boolean old = this.circular;
		this.circular = isCircular;
		getEventSupport().firePropertyChange(PROPERTY_CIRCULAR, old, this.circular);
	}


	/**
	 * @return the labelFormat
	 */
	@Override
	public String getLabelFormat() {
		return labelFormat;
	}


	/**
	 * @param labelFormat the labelFormat to set
	 */
	public void setLabelFormat(String labelFormat) {
		String old = this.labelFormat;
		this.labelFormat = labelFormat;
		getEventSupport().firePropertyChange(PROPERTY_LABEL_FORMAT, old, this.labelFormat);
	}

	
	/**
	 * @return the legendLabelFormat
	 */
	@Override
	public String getLegendLabelFormat() {
		return legendLabelFormat;
	}

	@Override
	public JRItemLabel getItemLabel()
	{
		return itemLabel;
	}
	
	/**
	 * @param legendLabelFormat the legendLabelFormat to set
	 */
	public void setLegendLabelFormat(String legendLabelFormat) {
		String old = this.legendLabelFormat;
		this.legendLabelFormat = legendLabelFormat;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND_LABEL_FORMAT, old, this.legendLabelFormat);
	}

	/**
	 * @param itemLabel the itemLabel to set
	 */
	public void setItemLabel(JRItemLabel itemLabel) {
		JRItemLabel old = this.itemLabel;
		this.itemLabel = itemLabel;
		getEventSupport().firePropertyChange(PROPERTY_ITEM_LABEL, old, this.itemLabel);
	}

	@Override
	public Boolean getShowLabels(){
		return showLabels;
	}

	/**
	 *
	 */
	public void setShowLabels( Boolean showLabels ){
		Boolean old = this.showLabels;
		this.showLabels = showLabels;
		getEventSupport().firePropertyChange(PROPERTY_SHOW_LABELS, old, this.showLabels);
	}

	@Override
	public Object clone(JRChart parentChart) 
	{
		JRBasePiePlot clone = (JRBasePiePlot)super.clone(parentChart);
		clone.itemLabel = itemLabel == null ? null : itemLabel.clone(parentChart);
		return clone;
	}

	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private boolean isCircular;//we need this field for fields.get("isCircular")
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		// this fixes a problem with JFreeChart that changed the default value of isCircular at some point.
		// look into Git history for details
		ObjectInputStream.GetField fields = in.readFields();
		//the following lines are required because above we called readFields(), not defaultReadObject()
		labelFormat = (String) fields.get("labelFormat", null);
		legendLabelFormat = (String) fields.get("legendLabelFormat", null);
		itemLabel = (JRItemLabel) fields.get("itemLabel", null);
		showLabels = (Boolean) fields.get("showLabels", null);
		
		PSEUDO_SERIAL_VERSION_UID = fields.get("PSEUDO_SERIAL_VERSION_UID", 0);
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_1_3)
		{
			boolean circularField = fields.get("isCircular", true);
			circular = circularField;
		}
		else
		{
			circular = (Boolean) fields.get("circular", null);
		}
	}
	
}
