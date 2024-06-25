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
package net.sf.jasperreports.engine.xml;

import org.xml.sax.Attributes;

import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.renderers.ResourceRenderer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRPrintImageSourceFactory extends JRBaseFactory
{

	@Override
	public Object createObject(Attributes atts)
	{
		JRPrintImage printImage = (JRPrintImage)digester.peek();
		
  	 	boolean isLazy = 
  	 		printImage.getRenderer() instanceof ResourceRenderer
  	 		? ((ResourceRenderer)printImage.getRenderer()).isLazy()
  	 		: false;

		JRPrintImageSourceObject imageSource = new JRPrintImageSourceObject(isLazy);

		imageSource.setPrintImage(printImage);

		String isEmbedded = atts.getValue(JRXmlConstants.ATTRIBUTE_isEmbedded);
		if (isEmbedded != null && isEmbedded.length() > 0)
		{
			imageSource.setEmbedded(Boolean.valueOf(isEmbedded));
		}

		return imageSource;
	}
	

}
