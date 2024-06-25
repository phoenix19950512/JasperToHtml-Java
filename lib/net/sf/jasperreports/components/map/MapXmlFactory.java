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
package net.sf.jasperreports.components.map;

import org.xml.sax.Attributes;

import net.sf.jasperreports.components.items.ItemXmlFactory;
import net.sf.jasperreports.components.map.type.MapImageTypeEnum;
import net.sf.jasperreports.components.map.type.MapScaleEnum;
import net.sf.jasperreports.components.map.type.MapTypeEnum;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;
import net.sf.jasperreports.engine.xml.JRBaseFactory;
import net.sf.jasperreports.engine.xml.JRXmlConstants;


/**
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public class MapXmlFactory extends JRBaseFactory
{
	/**
	 *
	 */
	public static final String ATTRIBUTE_mapType = "mapType";
	public static final String ATTRIBUTE_mapScale = "mapScale";
	public static final String ATTRIBUTE_imageType = "imageType";
	public static final String ATTRIBUTE_onErrorType = "onErrorType";
	public static final String ATTRIBUTE_markerClustering = "markerClustering";
	public static final String ATTRIBUTE_markerSpidering = "markerSpidering";
	/**
	 * @deprecated Replaced by {@link ItemXmlFactory#ELEMENT_item}.
	 */
	public static final String ELEMENT_item = ItemXmlFactory.ELEMENT_item;
	public static final String ELEMENT_markerData = "markerData";
	public static final String ELEMENT_legendItem = "legendItem";
	public static final String ELEMENT_resetMapItem = "resetMapItem";
	public static final String ELEMENT_seriesNameExpression = "seriesNameExpression";
	public static final String ELEMENT_markerClusteringExpression = "markerClusteringExpression";
	public static final String ELEMENT_markerSpideringExpression = "markerSpideringExpression";
	public static final String ELEMENT_legendIconExpression = "legendIconExpression";
	/**
	 * @deprecated Replaced by {@link ItemXmlFactory#ELEMENT_itemProperty}.
	 */
	public static final String ELEMENT_itemProperty = ItemXmlFactory.ELEMENT_itemProperty;
	public static final String ELEMENT_pathStyle = "pathStyle";
	public static final String ELEMENT_pathData = "pathData";
	/**
	 * @deprecated Replaced by {@link #ELEMENT_item}.
	 */
	public static final String ELEMENT_marker = "marker";
	/**
	 * @deprecated Replaced by {@link #ELEMENT_markerData}.
	 */
	public static final String ELEMENT_markerDataset = "markerDataset";
	/**
	 * @deprecated Replaced by {@link #ELEMENT_itemProperty}.
	 */
	public static final String ELEMENT_markerProperty = "markerProperty";

	@Override
	public Object createObject(Attributes atts)
	{
		StandardMapComponent map = new StandardMapComponent();
		
		EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_evaluationTime));
		if (evaluationTime != null)
		{
			map.setEvaluationTime(evaluationTime);
		}

		if (map.getEvaluationTime() == EvaluationTimeEnum.GROUP)
		{
			String groupName = atts.getValue(JRXmlConstants.ATTRIBUTE_evaluationGroup);
			map.setEvaluationGroup(groupName);
		}
		
		MapTypeEnum mapType = MapTypeEnum.getByName(atts.getValue(ATTRIBUTE_mapType));
		if(mapType != null)
		{
			map.setMapType(mapType);
		}
		MapScaleEnum mapScale = MapScaleEnum.getByName(atts.getValue(ATTRIBUTE_mapScale));
		if(mapScale != null)
		{
			map.setMapScale(mapScale);
		}
		MapImageTypeEnum imageType = MapImageTypeEnum.getByName(atts.getValue(ATTRIBUTE_imageType));
		if(imageType != null)
		{
			map.setImageType(imageType);
		}
		OnErrorTypeEnum onErrorType = OnErrorTypeEnum.getByName(atts.getValue(ATTRIBUTE_onErrorType));
		if(onErrorType != null)
		{
			map.setOnErrorType(onErrorType);
		}
		Boolean markerClustering = Boolean.valueOf(atts.getValue(ATTRIBUTE_markerClustering));
		if (markerClustering != null) {
			map.setMarkerClustering(markerClustering);
		}
		Boolean markerSpidering = Boolean.valueOf(atts.getValue(ATTRIBUTE_markerSpidering));
		if (markerSpidering != null) {
			map.setMarkerSpidering(markerSpidering);
		}

		return map;
	}
}
