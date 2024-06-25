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
package net.sf.jasperreports.components.barcode4j;

import net.sf.jasperreports.engine.JRConstants;

import org.krysalis.barcode4j.impl.datamatrix.SymbolShapeHint;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class DataMatrixComponent extends Barcode4jComponent
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final String PROPERTY_SHAPE = "shape";
	public static final String PROPERTY_MIN_SYMBOL_WIDTH = "minSymbolWidth";
	public static final String PROPERTY_MAX_SYMBOL_WIDTH = "maxSymbolWidth";
	public static final String PROPERTY_MIN_SYMBOL_HEIGHT = "minSymbolHeight";
	public static final String PROPERTY_MAX_SYMBOL_HEIGHT = "maxSymbolHeight";

	private String shape;
	private Integer minSymbolWidth;
	private Integer maxSymbolWidth;
	private Integer minSymbolHeight;
	private Integer maxSymbolHeight;
	
	@Override
	public void receive(BarcodeVisitor visitor)
	{
		visitor.visitDataMatrix(this);
	}

	public String getShape()
	{
		return shape;
	}

	public void setShape(String shape)
	{
		Object old = this.shape;
		this.shape = shape;
		getEventSupport().firePropertyChange(PROPERTY_SHAPE, old, this.shape);
	}

	public void setShape(SymbolShapeHint shape)
	{
		setShape(shape == null ? null : shape.getName());
	}

	public Integer getMinSymbolWidth() 
	{
		return minSymbolWidth;
	}
	
	public void setMinSymbolWidth(Integer minSymbolWidth) 
	{
		Object old = this.minSymbolWidth;
		this.minSymbolWidth = minSymbolWidth;
		getEventSupport().firePropertyChange(PROPERTY_MIN_SYMBOL_WIDTH, old, this.minSymbolWidth);
	}
	
	public Integer getMaxSymbolWidth() 
	{
		return maxSymbolWidth;
	}

	public void setMaxSymbolWidth(Integer maxSymbolWidth) 
	{
		Object old = this.maxSymbolWidth;
		this.maxSymbolWidth = maxSymbolWidth;
		getEventSupport().firePropertyChange(PROPERTY_MAX_SYMBOL_WIDTH, old, this.maxSymbolWidth);
	}

	public Integer getMinSymbolHeight() 
	{
		return minSymbolHeight;
	}
	
	public void setMinSymbolHeight(Integer minSymbolHeight) 
	{
		Object old = this.minSymbolHeight;
		this.minSymbolHeight = minSymbolHeight;
		getEventSupport().firePropertyChange(PROPERTY_MIN_SYMBOL_HEIGHT, old, this.minSymbolHeight);
	}
	
	public Integer getMaxSymbolHeight() 
	{
		return maxSymbolHeight;
	}
	
	public void setMaxSymbolHeight(Integer maxSymbolHeight) 
	{
		Object old = this.maxSymbolHeight;
		this.maxSymbolHeight = maxSymbolHeight;
		getEventSupport().firePropertyChange(PROPERTY_MAX_SYMBOL_HEIGHT, old, this.maxSymbolHeight);
	}
	
}
