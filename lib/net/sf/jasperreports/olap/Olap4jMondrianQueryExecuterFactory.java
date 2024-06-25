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
package net.sf.jasperreports.olap;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.query.AbstractQueryExecuterFactory;
import net.sf.jasperreports.engine.query.JRQueryExecuter;


/**
 * @author swood
 */
public class Olap4jMondrianQueryExecuterFactory extends AbstractQueryExecuterFactory
{
	public final static String PARAMETER_JDBC_DRIVERS = "JdbcDrivers";
	public final static String PARAMETER_JDBC_URL = "JdbcUrl";
	public final static String PARAMETER_JDBC_USER = "JdbcUser";
	public final static String PARAMETER_JDBC_PASSWORD = "JdbcPassword";
	public final static String PARAMETER_CATALOG = "Catalog";
	
	private final static Object[] MONDRIAN_OLAP4J_BUILTIN_PARAMETERS = {
		PARAMETER_JDBC_DRIVERS,  "java.lang.String",
		PARAMETER_JDBC_URL,  "java.lang.String",
		PARAMETER_JDBC_USER,  "java.lang.String",
		PARAMETER_JDBC_PASSWORD,  "java.lang.String",
		PARAMETER_CATALOG,  "java.lang.String",
		};
	
	@Override
	public Object[] getBuiltinParameters()
	{
		return MONDRIAN_OLAP4J_BUILTIN_PARAMETERS;
	}

	@Override
	public JRQueryExecuter createQueryExecuter(
		JasperReportsContext jasperReportsContext, 
		JRDataset dataset, 
		Map<String,? extends JRValueParameter> parameters
		) throws JRException
	{
		return new JRMondrianQueryExecuter(jasperReportsContext, dataset, parameters);
	}

	@Override
	public boolean supportsQueryParameterType(String className)
	{
		return true;
	}
}
