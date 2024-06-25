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
package net.sf.jasperreports.export.parameters;

import java.util.Map;

/**
 * @deprecated To be removed.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public interface ParameterResolver
{
	/**
	 * 
	 */
	public String getStringParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property);
	
	/**
	 * 
	 */
	public String[] getStringArrayParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String propertyPrefix);

	/**
	 * 
	 */
	public String getStringParameterOrDefault(net.sf.jasperreports.engine.JRExporterParameter parameter, String property);
	
	/**
	 * 
	 */
	public boolean getBooleanParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property, boolean defaultValue);
	
	/**
	 * 
	 */
	public int getIntegerParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property, int defaultValue);

	/**
	 * 
	 */
	public float getFloatParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property, float defaultValue);

	/**
	 * 
	 */
	public Character getCharacterParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property);
	
	/**
	 * 
	 */
	public Map<String,String> getMapParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property);
}
