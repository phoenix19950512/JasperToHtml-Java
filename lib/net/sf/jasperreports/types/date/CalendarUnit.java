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

import java.util.HashMap;
import java.util.Map;

/**
 * @author schubar
 */
public enum CalendarUnit 
{
	DAY("DAY"), WEEK("WEEK"), MONTH("MONTH"), QUARTER("QUARTER"), SEMI("SEMI"), YEAR("YEAR");

	private String unitName;

	private static Map<String, CalendarUnit> allUnits;

	static {
		allUnits = new HashMap<>();
		allUnits.put(DAY.toString(), DAY);
		allUnits.put(WEEK.toString(), WEEK);
		allUnits.put(MONTH.toString(), MONTH);
		allUnits.put(QUARTER.toString(), QUARTER);
		allUnits.put(SEMI.toString(), SEMI);
		allUnits.put(YEAR.toString(), YEAR);
	}

	private CalendarUnit(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitName() {
		return unitName;
	}

	public static CalendarUnit fromValue(final String unitName) {
		if (unitName == null) {
			return null;
		}

		return allUnits.get(unitName);
	}

	@Override
	public String toString() {
		return this.getUnitName();
	}
}
