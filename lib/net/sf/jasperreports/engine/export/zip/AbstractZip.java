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
package net.sf.jasperreports.engine.export.zip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public abstract class AbstractZip
{

	/**
	 * 
	 */
	protected Map<String, ExportZipEntry> exportZipEntries = new HashMap<>();

	/**
	 *
	 */
	public abstract ExportZipEntry createEntry(String name);
	
	/**
	 *
	 */
	public void addEntry(ExportZipEntry entry)
	{
		// if several entries with the same name are added, the last one will be considered 
		exportZipEntries.put(entry.getName(), entry);
	}
	
	/**
	 *
	 */
	public void zipEntries(OutputStream os) throws IOException
	{
		ZipOutputStream zipos = new ZipOutputStream(os);
		zipos.setMethod(ZipOutputStream.DEFLATED);
		
		for (ExportZipEntry exportZipEntry : exportZipEntries.values()) 
		{
			ZipEntry zipEntry = new ZipEntry(exportZipEntry.getName());
			zipos.putNextEntry(zipEntry);
			exportZipEntry.writeData(zipos);
		}
		
		zipos.flush();
		zipos.finish();
	}
	
	/**
	 *
	 */
	public void dispose()
	{
		for (ExportZipEntry exportZipEntry : exportZipEntries.values()) 
		{
			exportZipEntry.dispose();
		}
	}
	
}
