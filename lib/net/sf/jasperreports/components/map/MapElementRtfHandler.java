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
package net.sf.jasperreports.components.map;

import net.sf.jasperreports.components.map.imageprovider.DefaultMapElementImageProvider;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.export.GenericElementRtfHandler;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporterContext;

/**
 * @author Sanda Zaharia (shertage@users.sourceforge.net)
 */
public class MapElementRtfHandler implements GenericElementRtfHandler
{
	private static final MapElementRtfHandler INSTANCE = new MapElementRtfHandler();
	
	public static MapElementRtfHandler getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public void exportElement(
		JRRtfExporterContext exporterContext,
		JRGenericPrintElement element
		)
	{
		try
		{
			JRRtfExporter exporter = (JRRtfExporter)exporterContext.getExporterRef();
			JRPrintImage mapImage = DefaultMapElementImageProvider
					.getInstance()
					.getImage(exporterContext.getJasperReportsContext(), element);

			exporter.exportImage(mapImage);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean toExport(JRGenericPrintElement element) {
		return true;
	}

}
