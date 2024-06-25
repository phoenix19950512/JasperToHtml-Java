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
package net.sf.jasperreports.types.date;

import java.util.Date;

import net.sf.jasperreports.engine.query.ClauseFunctionParameterHandler;
import net.sf.jasperreports.engine.query.JRQueryClauseContext;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class DateRangeParameterHandler implements ClauseFunctionParameterHandler
{
	final JRQueryClauseContext queryContext;
	final String parameterName;
	final DateRange value;
	final boolean useRangeStart;
	
	public DateRangeParameterHandler(JRQueryClauseContext queryContext,
			String parameterName, DateRange value, boolean useRangeStart)
	{
		this.queryContext = queryContext;
		this.parameterName = parameterName;
		this.value = value;
		this.useRangeStart = useRangeStart;
	}

	@Override
	public boolean hasValue()
	{
		if (value == null)
		{
			return false;
		}
		
		Date dateValue = dateValue();
		return dateValue != null;
	}

	protected Date dateValue()
	{
		return useRangeStart ? value.getStart() : value.getEnd();
	}

	@Override
	public void addQueryParameter()
	{
		Date dateValue = dateValue();
		queryContext.addQueryParameter(null, dateValue); 
	}

}