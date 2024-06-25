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
public enum AxisPositionEnum implements JREnum
{
	/**
	 * Position the axis to the left of a VERTICAL chart or on the top
	 * of a HORIZONTAL chart.
	 */
	LEFT_OR_TOP((byte)1, "leftOrTop"),
	
	/**
	 * Position the axis to the right of a VERTICAL chart or on the bottom
	 * of a HORIZONTAL chart.
	 */
	RIGHT_OR_BOTTOM((byte)2, "rightOrBottom");
	
	/**
	 *
	 */
	private final transient byte value;
	private final transient String name;

	private AxisPositionEnum(byte value, String name)
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
	public static AxisPositionEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static AxisPositionEnum getByValue(Byte value)
	{
		return (AxisPositionEnum)EnumUtil.getByValue(values(), value);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static AxisPositionEnum getByValue(byte value)
	{
		return getByValue((Byte)value);
	}
}
