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
package net.sf.jasperreports.export.type;

import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.NamedEnum;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public enum PdfVersionEnum implements NamedEnum
{
	/**
	 * 
	 */
	VERSION_1_2("2"),

	/**
	 * 
	 */
	VERSION_1_3("3"),

	/**
	 * 
	 */
	VERSION_1_4("4"),

	/**
	 * 
	 */
	VERSION_1_5("5"),

	/**
	 * 
	 */
	VERSION_1_6("6"),

	/**
	 * 
	 */
	VERSION_1_7("7");
	
	/**
	 *
	 */
	private final transient String name;

	private PdfVersionEnum(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	/**
	 *
	 */
	public static PdfVersionEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
}
