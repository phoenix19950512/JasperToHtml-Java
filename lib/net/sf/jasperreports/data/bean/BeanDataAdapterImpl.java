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
package net.sf.jasperreports.data.bean;

import com.fasterxml.jackson.annotation.JsonRootName;

import net.sf.jasperreports.data.AbstractClasspathAwareDataAdapter;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */

@JsonRootName(value = "beanDataAdapter")
public class BeanDataAdapterImpl extends AbstractClasspathAwareDataAdapter implements BeanDataAdapter 
{
	private boolean isUseFieldDescription;
	private String factoryClass;
	private String methodName;

	@Override
	public boolean isUseFieldDescription() {
		return isUseFieldDescription;
	}

	@Override
	public void setUseFieldDescription(boolean isUseFieldDescription) {
		this.isUseFieldDescription = isUseFieldDescription;
	}

	@Override
	public String getFactoryClass() {
		return factoryClass;
	}

	@Override
	public void setFactoryClass(String factoryClass) {
		this.factoryClass = factoryClass;
	}

	@Override
	public String getMethodName() {
		return methodName;
	}

	@Override
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
