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
package net.sf.jasperreports.olap.olap4j;

import org.olap4j.Cell;

import net.sf.jasperreports.olap.result.JROlapCell;


/**
 * @author swood
 */
public class Olap4jCell implements JROlapCell
{

	private String formattedValue;
	private Object value;
	
	public Olap4jCell(Cell cell)
	{
		this.value = cell.getValue();
		this.formattedValue = cell.getFormattedValue();
	}

	@Override
	public String getFormattedValue()
	{
		return formattedValue;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public boolean isError()
	{
		return false;//FIXME cell.isError
	}

	@Override
	public boolean isNull()
	{
		return value == null;//FIXME cell.isNull
	}

}
