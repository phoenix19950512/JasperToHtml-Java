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
package net.sf.jasperreports.export.pdf.classic;

import com.lowagie.text.Image;

import net.sf.jasperreports.export.pdf.PdfImage;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class ClassicImage implements PdfImage
{

	private Image image;

	public ClassicImage(Image image)
	{
		this.image = image;
	}
	
	public Image getImage()
	{
		return image;
	}

	@Override
	public float getPlainWidth()
	{
		return image.getPlainWidth();
	}

	@Override
	public float getPlainHeight()
	{
		return image.getPlainHeight();
	}

	@Override
	public float getScaledWidth()
	{
		return image.getScaledWidth();
	}

	@Override
	public float getScaledHeight()
	{
		return image.getScaledHeight();
	}

	@Override
	public void scaleAbsolute(int width, int height)
	{
		image.scaleAbsolute(width, height);
	}

	@Override
	public void scaleToFit(int width, int height)
	{
		image.scaleToFit(width, height);
	}

	@Override
	public void scalePercent(float percent)
	{
		image.scalePercent(percent);
	}

	@Override
	public void setRotationDegrees(int degrees)
	{
		image.setRotationDegrees(degrees);
	}

}
