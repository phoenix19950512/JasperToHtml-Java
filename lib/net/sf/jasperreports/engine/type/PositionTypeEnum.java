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
package net.sf.jasperreports.engine.type;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public enum PositionTypeEnum implements JREnum
{
	/**
	 * The element will float in its parent section if it is pushed downwards by other elements fount above it.
	 * It will try to conserve the distance between it and the neighboring elements placed immediately above.
	 */
	FLOAT((byte)1, "Float"),

	/**
	 * The element will simply ignore what happens to the other section elements and tries to
	 * conserve the y offset measured from the top of its parent report section.
	 */
	FIX_RELATIVE_TO_TOP((byte)2, "FixRelativeToTop"),

	/**
	 * If the height of the parent report section is affected by elements that stretch, the current element will try to
	 * conserve the original distance between its bottom margin and the bottom of the band.
	 */
	FIX_RELATIVE_TO_BOTTOM((byte)3, "FixRelativeToBottom");

	/**
	 *
	 */
	private final transient byte value;
	private final transient String name;

	private PositionTypeEnum(byte value, String name)
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
	public static PositionTypeEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static PositionTypeEnum getByValue(Byte value)
	{
		return (PositionTypeEnum)EnumUtil.getByValue(values(), value);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static PositionTypeEnum getByValue(byte value)
	{
		return getByValue((Byte)value);
	}
}
