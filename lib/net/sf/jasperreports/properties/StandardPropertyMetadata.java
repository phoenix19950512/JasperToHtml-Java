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
package net.sf.jasperreports.properties;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.annotations.properties.PropertyScope;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class StandardPropertyMetadata implements PropertyMetadata
{
	
	private String name;
	private String category;
	private String constantDeclarationClass;
	private String constantFieldName;
	private String label;
	private String description;
	private String defaultValue;
	private List<PropertyScope> scopes;
	private List<String> scopeQualifications;
	private String sinceVersion;
	private String valueType;
	private boolean deprecated;

	public StandardPropertyMetadata()
	{
		this.scopes = new ArrayList<>();
		this.scopeQualifications = new ArrayList<>();
	}

	@Override
	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	@Override
	public List<PropertyScope> getScopes()
	{
		return scopes;
	}

	public void setScopes(List<PropertyScope> scopes)
	{
		this.scopes = scopes;
	}
	
	public void addScope(PropertyScope scope)
	{
		this.scopes.add(scope);
	}

	@Override
	public String getSinceVersion()
	{
		return sinceVersion;
	}

	public void setSinceVersion(String sinceVersion)
	{
		this.sinceVersion = sinceVersion;
	}

	@Override
	public String getValueType()
	{
		return valueType;
	}

	public void setValueType(String valueType)
	{
		this.valueType = valueType;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String getConstantDeclarationClass()
	{
		return constantDeclarationClass;
	}

	public void setConstantDeclarationClass(String constantDeclarationClass)
	{
		this.constantDeclarationClass = constantDeclarationClass;
	}

	@Override
	public String getConstantFieldName()
	{
		return constantFieldName;
	}

	public void setConstantFieldName(String constantFieldName)
	{
		this.constantFieldName = constantFieldName;
	}

	@Override
	public List<String> getScopeQualifications()
	{
		return scopeQualifications;
	}

	public void setScopeQualifications(List<String> scopeQualifications)
	{
		this.scopeQualifications = scopeQualifications;
	}
	
	public void addScopeQualification(String scopeQualification)
	{
		this.scopeQualifications.add(scopeQualification);
	}

	@Override
	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	@Override
	public boolean isDeprecated()
	{
		return deprecated;
	}

	public void setDeprecated(boolean deprecated)
	{
		this.deprecated = deprecated;
	}

}
