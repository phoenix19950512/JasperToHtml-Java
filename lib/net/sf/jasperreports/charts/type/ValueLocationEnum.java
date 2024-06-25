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
package net.sf.jasperreports.charts.type;

import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.JREnum;


/**
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public enum ValueLocationEnum implements JREnum
{
	/**
	 * The value should not be displayed.
	 */
	NONE((byte)0, "none"),
	
	/**
	 * The value should be displayed to the left of the thermometer.
	 */
	LEFT((byte)1, "left"),
	
	/**
	 * The value should be displayed to the right of the thermometer.
	 */
	RIGHT((byte)2, "right"),
	
	/**
	 * The value should be displayed in the bulb of the thermometer.  When
	 * using this option make sure the font is small enough or the value short
	 * enough so the value fits in the bulb.
	 */
	BULB((byte)3, "bulb");
	

	/**
	 *
	 */
	private final transient byte value;
	private final transient String name;

	private ValueLocationEnum(byte value, String name)
	{
		this.value = value;
		this.name = name;
	}

	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	@Override
	public Byte getValueByte()
	{
		return value;
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	@Override
	public final byte getValue()
	{
		return value;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	/**
	 *
	 */
	public static ValueLocationEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static ValueLocationEnum getByValue(Byte value)
	{
		return (ValueLocationEnum)EnumUtil.getByValue(values(), value);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static ValueLocationEnum getByValue(byte value)
	{
		return getByValue((Byte)value);
	}
}
