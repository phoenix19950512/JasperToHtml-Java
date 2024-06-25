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
package net.sf.jasperreports.engine;

import net.sf.jasperreports.engine.type.CalculationEnum;

/**
 * A value copied from a subdataset or from an expression into a variable of the parent report.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public interface CommonReturnValue extends JRCloneable
{

	/**
	 * Returns the name of the report variable where the value should be copied.
	 * 
	 * @return the name of the report variable where the value should be copied.
	 */
	public String getToVariable();

	/**
	 * Returns the calculation type.
	 * <p>
	 * When copying the value from, a formula can be applied such that sum,
	 * maximum, average and so on can be computed.
	 * 
	 * @return the calculation type.
	 */
	public CalculationEnum getCalculation();
	
	/**
	 * Returns the incrementer factory class name.
	 * <p>
	 * The factory will be used to increment the value of the report variable
	 * with the returned value.
	 * 
	 * @return the incrementer factory class name.
	 */
	public String getIncrementerFactoryClassName();
}
