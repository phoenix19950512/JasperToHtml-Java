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
package net.sf.jasperreports.data.hibernate;

import com.fasterxml.jackson.annotation.JsonRootName;

import net.sf.jasperreports.data.AbstractClasspathAwareDataAdapter;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */

@JsonRootName(value = "hibernateDataAdapter")
public class HibernateDataAdapterImpl extends AbstractClasspathAwareDataAdapter implements
		HibernateDataAdapter {
	private String xmlFile;
	private String propertiesFile;
	private boolean useAnnotation;

	@Override
	public String getXMLFileName() {
		return xmlFile;
	}

	@Override
	public void setXMLFileName(String fileName) {
		this.xmlFile = fileName;
	}

	@Override
	public String getPropertiesFileName() {
		return propertiesFile;
	}

	@Override
	public void setPropertiesFileName(String fileName) {
		this.propertiesFile = fileName;
	}

	@Override
	public boolean isUseAnnotation() {
		return useAnnotation;
	}

	@Override
	public void setUseAnnotation(boolean useAnnotation) {
		this.useAnnotation = useAnnotation;
	}

}
