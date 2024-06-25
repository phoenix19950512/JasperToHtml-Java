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

/*
 * Contributors:
 * Greg Hilton 
 */

package net.sf.jasperreports.engine.export;

import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JasperReportsContext;


/**
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public class JRXlsMetadataExporterNature extends JRXlsExporterNature
{

	/**
	 * 
	 */
	public JRXlsMetadataExporterNature(
		JasperReportsContext jasperReportsContext,
		ExporterFilter filter, 
		boolean isIgnoreGraphics, 
		boolean isIgnorePageMargins
		)
	{
		super(jasperReportsContext, filter, isIgnoreGraphics, isIgnorePageMargins);
	}

	@Override
	public boolean isToExport(JRPrintElement element)
	{
		return element.hasProperties() && element.getPropertiesMap().containsProperty(ExcelAbstractExporter.PROPERTY_COLUMN_NAME) && super.isToExport(element);
	}
}
