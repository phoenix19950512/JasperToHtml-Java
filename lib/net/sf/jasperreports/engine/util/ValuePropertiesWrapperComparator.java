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
package net.sf.jasperreports.engine.util;

import java.util.Comparator;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class ValuePropertiesWrapperComparator implements Comparator<Object>
{

	private final Comparator<Object> valueComparator;
	
	public ValuePropertiesWrapperComparator(Comparator<Object> valueComparator)
	{
		this.valueComparator = valueComparator;
	}
	
	@Override
	public int compare(Object o1, Object o2)
	{
		Object value1 = ((ValuePropertiesWrapper) o1).getValue();
		Object value2 = ((ValuePropertiesWrapper) o2).getValue();
		return valueComparator.compare(value1, value2);
	}

}
