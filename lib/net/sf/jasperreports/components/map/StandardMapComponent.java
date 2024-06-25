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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.items.ItemData;
import net.sf.jasperreports.components.items.StandardItem;
import net.sf.jasperreports.components.items.StandardItemData;
import net.sf.jasperreports.components.map.type.MapImageTypeEnum;
import net.sf.jasperreports.components.map.type.MapScaleEnum;
import net.sf.jasperreports.components.map.type.MapTypeEnum;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;
import net.sf.jasperreports.engine.util.JRCloneUtils;

/**
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class StandardMapComponent implements MapComponent, Serializable, JRChangeEventsSupport
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String PROPERTY_LATITUDE_EXPRESSION = "latitudeExpression";
	public static final String PROPERTY_LONGITUDE_EXPRESSION = "longitudeExpression";
	public static final String PROPERTY_ADDRESS_EXPRESSION = "addressExpression";
	public static final String PROPERTY_ZOOM_EXPRESSION = "zoomExpression";
	public static final String PROPERTY_LANGUAGE_EXPRESSION = "languageExpression";
	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
	public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
	public static final String PROPERTY_MAP_TYPE = "mapType";
	public static final String PROPERTY_MAP_SCALE = "mapScale";
	public static final String PROPERTY_IMAGE_TYPE = "imageType";
	public static final String PROPERTY_ON_ERROR_TYPE = "onErrorType";
	public static final String PROPERTY_MARKER_CLUSTERING = "markerClustering";
	public static final String PROPERTY_MARKER_SPIDERING = "markerSpidering";
	public static final String PROPERTY_MARKER_DATA_LIST = "markerDataList";
	public static final String PROPERTY_PATH_STYLE_LIST = "pathStyleList";
	public static final String PROPERTY_PATH_DATA_LIST = "pathDataList";
	
	/**
	 * @deprecated Replaced by {@link #PROPERTY_MARKER_DATA}.
	 */
	public static final String PROPERTY_MARKER_DATASET = "markerDataset";

	/**
	 * @deprecated Replaced by {@link #PROPERTY_MARKER_DATA_LIST}.
	 */
	public static final String PROPERTY_MARKER_DATA = "markerData";
	public static final String PROPERTY_LEGEND = "legend";
	public static final String PROPERTY_RESET_MAP = "resetMap";

	private JRExpression latitudeExpression;
	private JRExpression longitudeExpression;
	private JRExpression addressExpression;
	private JRExpression zoomExpression;
	private JRExpression languageExpression;
	private EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.NOW;
	private String evaluationGroup;
	private MapTypeEnum mapType;
	private MapScaleEnum mapScale;
	private MapImageTypeEnum imageType;

	private OnErrorTypeEnum onErrorType;
	private Boolean markerClustering;
	private Boolean markerSpidering;
	private List<MarkerItemData> markerItemDataList = new ArrayList<>();
	private List<ItemData> pathStyleList = new ArrayList<>();
	private List<ItemData> pathDataList = new ArrayList<>();

	private Item legend;

	private Item resetMap;

	private transient JRPropertyChangeSupport eventSupport;

	public StandardMapComponent()
	{
	}

	public StandardMapComponent(MapComponent map, JRBaseObjectFactory objectFactory)
	{
		this.latitudeExpression = objectFactory.getExpression(map.getLatitudeExpression());
		this.longitudeExpression = objectFactory.getExpression(map.getLongitudeExpression());
		this.addressExpression = objectFactory.getExpression(map.getAddressExpression());
		this.zoomExpression = objectFactory.getExpression(map.getZoomExpression());
		this.languageExpression = objectFactory.getExpression(map.getLanguageExpression());
		this.evaluationTime = map.getEvaluationTime();
		this.evaluationGroup = map.getEvaluationGroup();
		this.mapType = map.getMapType();
		this.mapScale = map.getMapScale();
		this.imageType = map.getImageType();
		this.markerClustering = map.getMarkerClustering();
		this.markerSpidering = map.getMarkerSpidering();

		Item legendItem = map.getLegendItem();
		if (legendItem != null) {
			this.legend = new StandardItem(StandardItemData.getCompiledProperties(legendItem.getProperties(), objectFactory));
		}

		Item resetMapItem = map.getResetMapItem();
		if (resetMapItem != null) {
			this.resetMap =new StandardItem(StandardItemData.getCompiledProperties(resetMapItem.getProperties(), objectFactory));
		}

		List<MarkerItemData> markerList = map.getMarkerItemDataList();
		if(markerList != null && markerList.size() > 0)
		{
			this.markerItemDataList = new ArrayList<>();
			for(MarkerItemData markerData : markerList){
				this.markerItemDataList.add(new StandardMarkerItemData(markerData, objectFactory));
			}
		} 
		this.onErrorType = map.getOnErrorType();
		List<ItemData> styleList = map.getPathStyleList();
		if(styleList != null && styleList.size() > 0)
		{
			this.pathStyleList = new ArrayList<>();
			for(ItemData pathStyle : styleList){
				pathStyleList.add(new StandardItemData(pathStyle, objectFactory));
			}
		}
		List<ItemData> pathList = map.getPathDataList();
		if(pathList != null && pathList.size() > 0)
		{
			this.pathDataList = new ArrayList<>();
			for(ItemData pathData : pathList){
				pathDataList.add(new StandardItemData(pathData, objectFactory));
			}
		}
	}
	
	@Override
	public JRExpression getLatitudeExpression()
	{
		return latitudeExpression;
	}

	public void setLatitudeExpression(JRExpression latitudeExpression)
	{
		Object old = this.latitudeExpression;
		this.latitudeExpression = latitudeExpression;
		getEventSupport().firePropertyChange(PROPERTY_LATITUDE_EXPRESSION, old, this.latitudeExpression);
	}

	@Override
	public JRExpression getLongitudeExpression()
	{
		return longitudeExpression;
	}
	
	public void setLongitudeExpression(JRExpression longitudeExpression)
	{
		Object old = this.longitudeExpression;
		this.longitudeExpression = longitudeExpression;
		getEventSupport().firePropertyChange(PROPERTY_LONGITUDE_EXPRESSION, old, this.longitudeExpression);
	}
	
	@Override
	public JRExpression getAddressExpression()
	{
		return addressExpression;
	}

	public void setAddressExpression(JRExpression addressExpression)
	{
		Object old = this.addressExpression;
		this.addressExpression = addressExpression;
		getEventSupport().firePropertyChange(PROPERTY_ADDRESS_EXPRESSION, old, this.addressExpression);
	}

	@Override
	public JRExpression getZoomExpression()
	{
		return zoomExpression;
	}
	
	public void setZoomExpression(JRExpression zoomExpression)
	{
		Object old = this.zoomExpression;
		this.zoomExpression = zoomExpression;
		getEventSupport().firePropertyChange(PROPERTY_ZOOM_EXPRESSION, old, this.zoomExpression);
	}
	
	@Override
	public JRExpression getLanguageExpression()
	{
		return languageExpression;
	}

	public void setLanguageExpression(JRExpression languageExpression)
	{
		Object old = this.languageExpression;
		this.languageExpression = languageExpression;
		getEventSupport().firePropertyChange(PROPERTY_LANGUAGE_EXPRESSION, old, this.languageExpression);
	}

	@Override
	public EvaluationTimeEnum getEvaluationTime()
	{
		return evaluationTime;
	}

	public void setEvaluationTime(EvaluationTimeEnum evaluationTimeValue)
	{
		Object old = this.evaluationTime;
		this.evaluationTime = evaluationTimeValue;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, old, this.evaluationTime);
	}

	@Override
	public String getEvaluationGroup()
	{
		return evaluationGroup;
	}

	public void setEvaluationGroup(String evaluationGroup)
	{
		Object old = this.evaluationGroup;
		this.evaluationGroup = evaluationGroup;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_GROUP, 
				old, this.evaluationGroup);
	}
	
	@Override
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}
	
	@Override
	public Object clone()
	{
		StandardMapComponent clone = null;
		try
		{
			clone = (StandardMapComponent) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// never
			throw new JRRuntimeException(e);
		}
		clone.latitudeExpression = JRCloneUtils.nullSafeClone(latitudeExpression);
		clone.longitudeExpression = JRCloneUtils.nullSafeClone(longitudeExpression);
		clone.addressExpression = JRCloneUtils.nullSafeClone(addressExpression);
		clone.zoomExpression = JRCloneUtils.nullSafeClone(zoomExpression);
		clone.languageExpression = JRCloneUtils.nullSafeClone(languageExpression);
		clone.markerItemDataList = JRCloneUtils.cloneList(markerItemDataList);
		clone.pathStyleList = JRCloneUtils.cloneList(pathStyleList);
		clone.pathDataList = JRCloneUtils.cloneList(pathDataList);
		clone.eventSupport = null;
		return clone;
	}

	@Override
	public MapTypeEnum getMapType() {
		return mapType;
	}

	public void setMapType(MapTypeEnum mapType) {
		Object old = this.mapType;
		this.mapType = mapType;
		getEventSupport().firePropertyChange(PROPERTY_MAP_TYPE, old, this.mapType);
	}

	@Override
	public MapScaleEnum getMapScale() {
		return mapScale;
	}
	
	public void setMapScale(MapScaleEnum mapScale) {
		Object old = this.mapScale;
		this.mapScale = mapScale;
		getEventSupport().firePropertyChange(PROPERTY_MAP_SCALE, old, this.mapScale);
	}
	
	@Override
	public MapImageTypeEnum getImageType() {
		return imageType;
	}

	public void setImageType(MapImageTypeEnum imageType) {
		Object old = this.imageType;
		this.imageType = imageType;
		getEventSupport().firePropertyChange(PROPERTY_IMAGE_TYPE, old, this.imageType);
	}

	/**
	 * @deprecated Replaced by {@link #getMarkerDataList()}.
	 */
	@Override
	public ItemData getMarkerData() {
		return !markerItemDataList.isEmpty() ? markerItemDataList.get(0) : null;
	}

	/**
	 * @deprecated Replaced by {@link #addMarkerData(ItemData)}.
	 */
	public void setMarkerData(ItemData markerData) {
		addMarkerData(markerData);
	}


	@Override
	public OnErrorTypeEnum getOnErrorType() {
		return onErrorType;
	}

	public void setOnErrorType(OnErrorTypeEnum onErrorType) {
		Object old = this.onErrorType;
		this.onErrorType = onErrorType;
		getEventSupport().firePropertyChange(PROPERTY_ON_ERROR_TYPE, old, this.onErrorType);
	}

	@Override
	public Boolean getMarkerClustering() {
		return markerClustering;
	}

	public void setMarkerClustering(Boolean markerClustering) {
		Object old = this.markerClustering;
		this.markerClustering = markerClustering;
		getEventSupport().firePropertyChange(PROPERTY_MARKER_CLUSTERING, old, this.markerClustering);
	}

	@Override
	public Boolean getMarkerSpidering() {
		return markerSpidering;
	}

	public void setMarkerSpidering(Boolean markerSpidering) {
		Object old = this.markerSpidering;
		this.markerSpidering = markerSpidering;
		getEventSupport().firePropertyChange(PROPERTY_MARKER_SPIDERING, old, this.markerSpidering);
	}

	/**
	 * @deprecated Replaced by {@link #getMarkerData()}.
	 */
	@Override
	public MarkerDataset getMarkerDataset() {
		return markerDataset; //FIXMEMAP make dummy marker dataset
	}

	/**
	 * @deprecated Replaced by {@link #setMarkerData(ItemData)}.
	 */
	public void setMarkerDataset(MarkerDataset markerDataset) {
		setMarkerData(StandardMarkerDataset.getItemData(markerDataset));
	}

	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private MarkerDataset markerDataset;
	/**
	 * @deprecated Replaced by {@link #markerDataList}.
	 */
	private ItemData markerData;
	/**
	 * @deprecated Replaced by {@link #markerItemDataList}.
	 */
	private List<ItemData> markerDataList;
	
	@SuppressWarnings("deprecation")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_5_0_4)
		{
			if (markerDataset != null)
			{
				markerData = StandardMarkerDataset.getItemData(markerDataset);
			}
			markerDataset = null;
		}

		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_5_5_2)
		{
			if (markerData != null)
			{
				this.markerDataList = new ArrayList<ItemData>();
				this.markerDataList.add(markerData);
			}
			markerData = null;
		}

		if (markerDataList != null && markerItemDataList == null) // equivalent of a 6_20_2 pseudo serial uuid test for which no constant was created at the time of the 6.20.2 release
		{
			this.markerItemDataList = new ArrayList<>(markerDataList.size());
			for (ItemData itemData : markerDataList)
			{
				if (itemData instanceof MarkerItemData)
				{
					// objects serialized prior to 5.0.4 would have markerDataset above converted to markerData and thus contain MarkerItemData items
					// see StandardMarkerDataset.getItemData(markerDataset), which started to instantiate MarkerStandardItemData right after 6.20.6
					this.markerItemDataList.add((MarkerItemData)itemData);
				}
				else
				{
					// objects serialized after 5.0.4 would have ItemData and not MarkerItemData object in them
					// see StandardMarkerDataset.getItemData(markerDataset)
					StandardMarkerItemData markerItemData = new StandardMarkerItemData();
					List<Item> items = itemData.getItems();
					if (items != null)
					{
						for (Item item : items)
						{
							markerItemData.addItem(item);
						}
					}
					markerItemData.setDataset(itemData.getDataset());
					this.markerItemDataList.add(markerItemData);
				}
			}

			markerDataList = null;
		}
	}

	@Override
	public List<ItemData> getPathStyleList() {
		return this.pathStyleList;
	}
	
	/**
	 *
	 */
	public void addPathStyle(ItemData pathStyle)
	{
		pathStyleList.add(pathStyle);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_PATH_STYLE_LIST, pathStyle, pathStyleList.size() - 1);
	}
	
	/**
	 *
	 */
	public void addPathStyle(int index, ItemData pathStyle)
	{
		if(index >=0 && index < pathStyleList.size())
			pathStyleList.add(index, pathStyle);
		else{
			pathStyleList.add(pathStyle);
			index = pathStyleList.size() - 1;
		}
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_PATH_STYLE_LIST, pathStyleList, index);
	}
	
	/**
	 *
	 */
	public ItemData removePathStyle(ItemData pathStyle)
	{
		if (pathStyle != null)
		{
			int idx = pathStyleList.indexOf(pathStyle);
			if (idx >= 0)
			{
				pathStyleList.remove(idx);
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_PATH_STYLE_LIST, pathStyle, idx);
			}
		}
		return pathStyle;
	}

	/**
	 * @deprecated Replaced by {@link #getMarkerItemDataList()}
	 */
	@Override
	public List<ItemData> getMarkerDataList() {
		List<ItemData> markerDataList = new ArrayList<>();
		for (MarkerItemData markerItemData: markerItemDataList) {
			markerDataList.add(markerItemData);
		}
		return markerDataList;
	}

	@Override
	public List<MarkerItemData> getMarkerItemDataList() {
		return markerItemDataList;
	}

	/**
	 * @deprecated Replaced by {@link #addMarkerItemData(MarkerItemData)}.
	 */
	public void addMarkerData(ItemData markerData)
	{
		addMarkerItemData((MarkerItemData) markerData);
	}

	/**
	 *
	 */
	public void addMarkerItemData(MarkerItemData markerItemData)
	{
		markerItemDataList.add(markerItemData);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_MARKER_DATA_LIST, markerItemData, markerItemDataList.size() - 1);
	}

	/**
	 * @deprecated Replaced by {@link #addMarkerItemData(int, MarkerItemData)}.
	 */
	public void addMarkerData(int index, ItemData markerData)
	{
		addMarkerItemData(index, (MarkerItemData)markerData);
	}

	public void addMarkerItemData(int index, MarkerItemData markerData)
	{
		if(index >=0 && index < markerItemDataList.size())
			markerItemDataList.add(index, markerData);
		else{
			markerItemDataList.add(markerData);
			index = markerItemDataList.size() - 1;
		}
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_MARKER_DATA_LIST, markerItemDataList, index);
	}
	
	/**
	 * @deprecated Replaced by {@link #removeMarkerItemData(MarkerItemData)}
	 */
	public ItemData removeMarkerData(ItemData markerData)
	{
		if (markerData != null)
		{
			int idx = markerItemDataList.indexOf(markerData);
			if (idx >= 0)
			{
				markerItemDataList.remove(idx);
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_MARKER_DATA_LIST, markerData, idx);
			}
		}
		return markerData;
	}

	public MarkerItemData removeMarkerItemData(MarkerItemData markerItemData)
	{
		if (markerItemData != null)
		{
			int idx = markerItemDataList.indexOf(markerItemData);
			if (idx >= 0)
			{
				markerItemDataList.remove(idx);
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_MARKER_DATA_LIST, markerItemData, idx);
			}
		}
		return markerItemData;
	}

	@Override
	public Item getLegendItem() {
		return legend;
	}

	public void setLegend(Item legend) {
		Object old = this.legend;
		this.legend = legend;
		getEventSupport().firePropertyChange(PROPERTY_LEGEND, old, this.legend);
	}

	@Override
	public Item getResetMapItem() {
		return resetMap;
	}

	public void setResetMap(Item resetMap) {
		Object old = this.resetMap;
		this.resetMap = resetMap;
		getEventSupport().firePropertyChange(PROPERTY_RESET_MAP, old, this.resetMap);
	}

	@Override
	public List<ItemData> getPathDataList() {
		return this.pathDataList;
	}
	
	/**
	 *
	 */
	public void addPathData(ItemData pathData)
	{
		pathDataList.add(pathData);
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_PATH_DATA_LIST, pathData, pathDataList.size() - 1);
	}
	
	/**
	 *
	 */
	public void addPathData(int index, ItemData pathData)
	{
		if(index >=0 && index < pathDataList.size())
			pathDataList.add(index, pathData);
		else{
			pathDataList.add(pathData);
			index = pathDataList.size() - 1;
		}
		getEventSupport().fireCollectionElementAddedEvent(PROPERTY_PATH_DATA_LIST, pathDataList, index);
	}

	/**
	 *
	 */
	public ItemData removePathData(ItemData pathData)
	{
		if (pathData != null)
		{
			int idx = pathDataList.indexOf(pathData);
			if (idx >= 0)
			{
				pathDataList.remove(idx);
				getEventSupport().fireCollectionElementRemovedEvent(PROPERTY_PATH_DATA_LIST, pathData, idx);
			}
		}
		return pathData;
	}
	
}
