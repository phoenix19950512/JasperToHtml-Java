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
package net.sf.jasperreports.charts;

import net.sf.jasperreports.charts.type.TimePeriodEnum;
import net.sf.jasperreports.engine.JRChartDataset;

/**
 * This dataset wraps one or multiple time series. A time series consists of 
 * (time period, numeric value) pairs. The Time Series dataset can be used with 
 * Times Series and XY Bar charts. 
 * 
 * @author Flavius Sana (flavius_sana@users.sourceforge.net)
 */
public interface JRTimeSeriesDataset extends JRChartDataset {
	
	/**
	 * @return an array of {@link JRTimeSeries} objects representing the 
	 * series for the Time Series chart
	 * @see JRTimeSeries
	 */
	public JRTimeSeries[] getSeries();

	/**
	 * @deprecated Replaced by {@link #getTimePeriodValue()}.
	 */
	public Class<?> getTimePeriod();

	/**
	 * @deprecated Replaced by {@link #setTimePeriod(TimePeriodEnum)}.
	 */
	public void setTimePeriod(Class<?> timePeriod);
	
	/**
	 * 
	 * @return the time period. Specifies the type of the data series inside 
	 * the dataset. Time series can contain numeric values associated with 
	 * days, months, years, or other predefined time periods.
	 * @see net.sf.jasperreports.charts.type.TimePeriodEnum
	 */
	public TimePeriodEnum getTimePeriodValue();

	/**
	 * 
	 * @param timePeriod the time period associated with 
	 * days, months, years, or other predefined time periods.
	 * @see net.sf.jasperreports.charts.type.TimePeriodEnum
	 */
	public void setTimePeriod(TimePeriodEnum timePeriod);

}
