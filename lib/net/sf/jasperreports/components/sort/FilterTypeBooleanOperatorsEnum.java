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
package net.sf.jasperreports.components.sort;

import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.NamedEnum;


/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public enum FilterTypeBooleanOperatorsEnum implements NamedEnum
{
	IS_TRUE("Is true"),
	
	IS_NOT_TRUE("Is not true"),

	IS_FALSE("Is false"),

	IS_NOT_FALSE("Is not false"),

	IS_NULL("Is Null"),

	IS_NOT_NULL("Is not Null");
	
	/**
	 *
	 */
	private final transient String name;

	private FilterTypeBooleanOperatorsEnum(String name)
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
	public static FilterTypeBooleanOperatorsEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
	
	/**
	 *
	 */
	public static FilterTypeBooleanOperatorsEnum getByEnumConstantName(String name)
	{
		return EnumUtil.getByConstantName(values(), name);
	}
}
