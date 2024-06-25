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
package net.sf.jasperreports.engine.export.ooxml;

import java.io.Writer;

import net.sf.jasperreports.engine.JasperReportsContext;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class XlsxDrawingHelper extends BaseHelper
{
	
	/**
	 *
	 */
	private XlsxDrawingRelsHelper drawingRelsHelper;

	/**
	 * 
	 */
	public XlsxDrawingHelper(JasperReportsContext jasperReportsContext, Writer writer, XlsxDrawingRelsHelper drawingRelsHelper)
	{
		super(jasperReportsContext, writer);
		
		this.drawingRelsHelper = drawingRelsHelper;
	}

	/**
	 *
	 */
	public void exportHeader()
	{
		write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		write("<xdr:wsDr\n");
		write(" xmlns:xdr=\"http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing\"\n");
		write(" xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"\n");
		write(" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">\n");
	}
	

	/**
	 *
	 */
	public void exportHyperlink(String href) 
	{
		write("<a:hlinkClick r:id=\"rIdLnk" + drawingRelsHelper.getHyperlink(href) + "\"/>\n");
	}


	/**
	 *
	 */
	public void exportFooter()
	{
		write("</xdr:wsDr>");		
	}

}
