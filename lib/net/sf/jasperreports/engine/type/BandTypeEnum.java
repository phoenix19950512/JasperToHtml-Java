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
public enum BandTypeEnum implements JREnum
{
	/**
	 *
	 */
	UNKNOWN((byte)0, "unknown"),

	/**
	 *
	 */
	BACKGROUND((byte)1, "background"),

	/**
	 *
	 */
	TITLE((byte)2, "title"),

	/**
	 *
	 */
	PAGE_HEADER((byte)3, "pageHeader"),

	/**
	 *
	 */
	COLUMN_HEADER((byte)4, "columnHeader"),

	/**
	 *
	 */
	GROUP_HEADER((byte)5, "groupHeader"),

	/**
	 *
	 */
	DETAIL((byte)6, "detail"),

	/**
	 *
	 */
	GROUP_FOOTER((byte)7, "groupFooter"),

	/**
	 *
	 */
	COLUMN_FOOTER((byte)8, "columnFooter"),

	/**
	 *
	 */
	PAGE_FOOTER((byte)9, "pageFooter"),

	/**
	 *
	 */
	LAST_PAGE_FOOTER((byte)10, "lastPageFooter"),

	/**
	 *
	 */
	SUMMARY((byte)11, "summary"),

	/**
	 *
	 */
	NO_DATA((byte)12, "noData");

	/**
	 *
	 */
	private final transient byte value;
	private final transient String name;

	private BandTypeEnum(byte value, String name)
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
	public static BandTypeEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static BandTypeEnum getByValue(Byte value)
	{
		return (BandTypeEnum)EnumUtil.getByValue(values(), value);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static BandTypeEnum getByValue(byte value)
	{
		return getByValue((Byte)value);
	}
}
