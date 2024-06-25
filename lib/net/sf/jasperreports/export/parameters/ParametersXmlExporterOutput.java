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

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.export.FileXmlResourceHandler;
import net.sf.jasperreports.engine.export.XmlResourceHandler;
import net.sf.jasperreports.export.XmlExporterOutput;


/**
 * @deprecated To be removed.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class ParametersXmlExporterOutput extends ParametersWriterExporterOutput implements XmlExporterOutput
{
	private static final String DEFAULT_XML_ENCODING = "UTF-8";
	private static final String XML_FILES_SUFFIX = "_files";

	/**
	 * 
	 */
	private XmlResourceHandler imageHandler;
	private Boolean isEmbeddingImages;
	/**
	 * @deprecated To be removed.
	 */ 
	private File imagesDir;
	
	/**
	 * 
	 */
	public ParametersXmlExporterOutput(
		JasperReportsContext jasperReportsContext,
		Map<net.sf.jasperreports.engine.JRExporterParameter, Object> parameters,
		JasperPrint jasperPrint
		)
	{
		super(
			jasperReportsContext,
			parameters,
			jasperPrint
			);
		
		isEmbeddingImages = Boolean.TRUE;

		StringBuffer sb = (StringBuffer)parameters.get(net.sf.jasperreports.engine.JRExporterParameter.OUTPUT_STRING_BUFFER);
		if (sb == null)
		{
			Writer writer = (Writer)parameters.get(net.sf.jasperreports.engine.JRExporterParameter.OUTPUT_WRITER);
			if (writer == null)
			{
				OutputStream os = (OutputStream)parameters.get(net.sf.jasperreports.engine.JRExporterParameter.OUTPUT_STREAM);
				if (os == null)
				{
					File destFile = (File)parameters.get(net.sf.jasperreports.engine.JRExporterParameter.OUTPUT_FILE);
					if (destFile == null)
					{
						String fileName = (String)parameters.get(net.sf.jasperreports.engine.JRExporterParameter.OUTPUT_FILE_NAME);
						if (fileName != null)
						{
							destFile = new File(fileName);
						}
						else
						{
							throw 
								new JRRuntimeException(
									EXCEPTION_MESSAGE_KEY_NO_OUTPUT_SPECIFIED,
									(Object[])null);
						}
					}
					
					imagesDir = new File(destFile.getParent(), destFile.getName() + XML_FILES_SUFFIX);
					imageHandler = new FileXmlResourceHandler(imagesDir, imagesDir.getName() + "/{0}");

					Boolean isEmbeddingImagesParameter = (Boolean)parameters.get(net.sf.jasperreports.engine.export.JRXmlExporterParameter.IS_EMBEDDING_IMAGES);
					if (isEmbeddingImagesParameter == null)
					{
						isEmbeddingImagesParameter = Boolean.TRUE;
					}
					isEmbeddingImages = isEmbeddingImagesParameter;
				}
			}
		}
	}

	@Override
	protected void setEncoding()//FIXMEEXPORT why do we need override here?
	{
		encoding = (String)parameters.get(net.sf.jasperreports.engine.JRExporterParameter.CHARACTER_ENCODING);
		if (encoding == null)
		{
			encoding = DEFAULT_XML_ENCODING;
		}
	}
	
	@Override
	public XmlResourceHandler getImageHandler() 
	{
		return imageHandler;
	}

	@Override
	public Boolean isEmbeddingImages()
	{
		return isEmbeddingImages;
	}
	
	/**
	 * @deprecated To be removed. 
	 */
	public File getImagesDir()
	{
		return imagesDir;
	}
}
