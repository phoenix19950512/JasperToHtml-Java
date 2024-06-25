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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRPropertiesUtil.PropertySuffix;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReportsContext;


/**
 * @deprecated To be removed.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class ParameterOverriddenResolver implements ParameterResolver
{
	private final JRPropertiesUtil propertiesUtil;
	private final JasperPrint jasperPrint;
	private final Map<net.sf.jasperreports.engine.JRExporterParameter, Object> parameters;
	

	/**
	 *
	 */
	public ParameterOverriddenResolver(
		JasperReportsContext jasperReportsContext,
		JasperPrint jasperPrint,
		Map<net.sf.jasperreports.engine.JRExporterParameter, Object> parameters
		)
	{
		this.propertiesUtil = JRPropertiesUtil.getInstance(jasperReportsContext);
		this.jasperPrint = jasperPrint;
		this.parameters = parameters;
	}
	
	
	@Override
	public String getStringParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property)
	{
		String value;
		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null && hintsMap.containsProperty(property))
		{
			value = hintsMap.getProperty(property);
		}
		else
		{
			value = (String) parameters.get(parameter);
			
			if (value == null)
			{
				value = getPropertiesUtil().getProperty(property);
			}
		}
		return value;
	}

	@Override
	public String[] getStringArrayParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String propertyPrefix)
	{
		String[] values = (String[])parameters.get(parameter);

		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null)
		{
			List<PropertySuffix> properties = JRPropertiesUtil.getProperties(hintsMap, propertyPrefix);
			if (properties != null && !properties.isEmpty())
			{
				values = new String[properties.size()];
				for(int i = 0; i < values.length; i++)
				{
					values[i] = properties.get(i).getValue();
				}
			}
		}

		return values;
	}

	
	@Override
	public Map<String, String> getMapParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String  propertyPrefix)
	{
		Map<String, String> values = (Map<String, String>)parameters.get(parameter);
		
		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null)
		{
			List<PropertySuffix> properties = JRPropertiesUtil.getProperties(hintsMap, propertyPrefix);
			if (properties != null && !properties.isEmpty())
			{
				values = new HashMap<>();
				for(PropertySuffix property : properties)
				{
					values.put(property.getSuffix(), property.getValue());
				}
			}
		}

		return values;
	}
	
	
	
	@Override
	public String getStringParameterOrDefault(net.sf.jasperreports.engine.JRExporterParameter parameter, String property)
	{
		String value;
		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null && hintsMap.containsProperty(property))
		{
			value = hintsMap.getProperty(property);
		}
		else
		{
			value = (String) parameters.get(parameter);
		}
		
		if (value == null)
		{
			value = getPropertiesUtil().getProperty(property);
		}
		
		return value;
	}

	@Override
	public boolean getBooleanParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property, boolean defaultValue)
	{
		boolean value;
		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null && hintsMap.containsProperty(property))
		{
			String prop = hintsMap.getProperty(property);
			if (prop == null)
			{
				value = getPropertiesUtil().getBooleanProperty(property);
			}
			else
			{
				value = JRPropertiesUtil.asBoolean(prop);
			}
		}
		else
		{
			Boolean param = (Boolean) parameters.get(parameter);
			if (param == null)
			{
				value = getPropertiesUtil().getBooleanProperty(property);
			}
			else
			{
				value = param;
			}
		}
		return value;
	}

	@Override
	public int getIntegerParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property, int defaultValue)
	{
		int value;
		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null && hintsMap.containsProperty(property))
		{
			String prop = hintsMap.getProperty(property);
			if (prop == null)
			{
				value = getPropertiesUtil().getIntegerProperty(property);
			}
			else
			{
				value = JRPropertiesUtil.asInteger(prop);
			}
		}
		else
		{
			Integer param = (Integer) parameters.get(parameter);
			if (param == null)
			{
				value = getPropertiesUtil().getIntegerProperty(property);
			}
			else
			{
				value = param;
			}
		}
		return value;
	}
	
	@Override
	public float getFloatParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property, float defaultValue)
	{
		float value;
		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null && hintsMap.containsProperty(property))
		{
			String prop = hintsMap.getProperty(property);
			if (prop == null)
			{
				value = getPropertiesUtil().getFloatProperty(property);
			}
			else
			{
				value = JRPropertiesUtil.asFloat(prop);
			}
		}
		else
		{
			Float param = (Float) parameters.get(parameter);
			if (param == null)
			{
				value = getPropertiesUtil().getFloatProperty(property);
			}
			else
			{
				value = param;
			}
		}
		return value;
	}
	
	@Override
	public Character getCharacterParameter(net.sf.jasperreports.engine.JRExporterParameter parameter, String property)
	{
		Character value;
		JRPropertiesMap hintsMap = jasperPrint.getPropertiesMap();
		if (hintsMap != null && hintsMap.containsProperty(property))
		{
			String prop = hintsMap.getProperty(property);
			value = JRPropertiesUtil.asCharacter(prop);
		}
		else
		{
			value = (Character) parameters.get(parameter);
			
			if (value == null)
			{
				value = getPropertiesUtil().getCharacterProperty(property);
			}
		}
		return value;
	}
	
	/**
	 *
	 */
	private JRPropertiesUtil getPropertiesUtil()
	{
		return propertiesUtil;
	}
}
