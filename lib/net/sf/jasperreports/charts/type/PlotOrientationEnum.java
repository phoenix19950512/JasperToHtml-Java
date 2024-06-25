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

import org.jfree.chart.plot.PlotOrientation;

import net.sf.jasperreports.charts.util.ChartUtil;
import net.sf.jasperreports.engine.type.EnumUtil;
import net.sf.jasperreports.engine.type.NamedEnum;


/**
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public enum PlotOrientationEnum implements NamedEnum
{
	/**
	 *
	 */
	HORIZONTAL("Horizontal"),

	/**
	 *
	 */
	VERTICAL("Vertical");


	/**
	 *
	 */
	private final transient String name;

	private PlotOrientationEnum(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
 	/**
	 * @deprecated Replaced by {@link ChartUtil#getPlotOrientation(PlotOrientationEnum)}.
	 */
	public final PlotOrientation getOrientation()
	{
		return ChartUtil.getPlotOrientation(this);
	}
	
	/**
	 *
	 */
	public static PlotOrientationEnum getByName(String name)
	{
		return EnumUtil.getEnumByName(values(), name);
	}
	
	/**
	 * @deprecated Used only by deprecated serialized fields.
	 */
	public static PlotOrientationEnum getByValue(PlotOrientation orientation)
	{
		return ChartUtil.getPlotOrientation(orientation);
	}
}
