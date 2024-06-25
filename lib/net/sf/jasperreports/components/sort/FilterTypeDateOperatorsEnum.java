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
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public enum FilterTypeDateOperatorsEnum implements NamedEnum
{
	EQUALS("Equals"),
	
	IS_NOT_EQUAL_TO("Is not Equal to"),

	IS_BETWEEN("Is Between"),

	IS_NOT_BETWEEN("Is not between"),
	
	IS_ON_OR_BEFORE("Is On or Before"),
	
	IS_BEFORE("Is Before"),
	
	IS_ON_OR_AFTER("Is On or After"),
	
	IS_AFTER("Is After"),

	IS_NULL("Is Null"),

	IS_NOT_NULL("Is not Null");

	/**
	 *
	 */
	private final transient String name;

	private FilterTypeDateOperatorsEnum(String name)
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
	public static FilterTypeDateOperatorsEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
	
	/**
	 *
	 */
	public static FilterTypeDateOperatorsEnum getByEnumConstantName(String name)
	{
		return EnumUtil.getByConstantName(values(), name);
	}
}
