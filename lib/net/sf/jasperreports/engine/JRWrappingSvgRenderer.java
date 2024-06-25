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
package net.sf.jasperreports.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import net.sf.jasperreports.renderers.DimensionRenderable;
import net.sf.jasperreports.renderers.Graphics2DRenderable;
import net.sf.jasperreports.renderers.RenderToImageAwareRenderable;
import net.sf.jasperreports.renderers.WrappingRenderToImageDataRenderer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @deprecated Replaced by {@link WrappingRenderToImageDataRenderer}.
 */
public class JRWrappingSvgRenderer extends JRAbstractSvgRenderer implements Graphics2DRenderable, DimensionRenderable, RenderToImageAwareRenderable
{

	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/**
	 *
	 */
	private Renderable renderer;
	private Dimension2D elementDimension;
	private Color backcolor;

	
	/**
	 *
	 */
	public JRWrappingSvgRenderer(
		Renderable renderer, 
		Dimension2D elementDimension,
		Color backcolor
		)
	{
		this.renderer = renderer;
		this.elementDimension = elementDimension;
		this.backcolor = backcolor;
	}


	@Override
	public Dimension2D getDimension(JasperReportsContext jasperReportsContext)
	{
		Dimension2D imageDimension = null;
		try
		{
			// use original dimension if possible
			imageDimension = renderer.getDimension(jasperReportsContext);
		}
		catch (JRException e)
		{
			// ignore
		}
		
		if (imageDimension == null)
		{
			// fallback to element dimension
			imageDimension = elementDimension;
		}
		
		return imageDimension;
	}

	@Override
	public Color getBackcolor()
	{
		return backcolor;
	}

	@Override
	public void render(JasperReportsContext jasperReportsContext, Graphics2D grx, Rectangle2D rectangle) throws JRException
	{
		renderer.render(jasperReportsContext, grx, rectangle);
	}

	@Override
	public int getImageDataDPI(JasperReportsContext jasperReportsContext)
	{
		if (renderer instanceof JRAbstractSvgRenderer)
		{
			return ((JRAbstractSvgRenderer) renderer).getImageDataDPI(jasperReportsContext);
		}
		
		return super.getImageDataDPI(jasperReportsContext);
	}

	@Override
	public Graphics2D createGraphics(BufferedImage bi)
	{
		if (renderer instanceof JRAbstractSvgRenderer)
		{
			return ((JRAbstractSvgRenderer) renderer).createGraphics(bi);
		}
		
		return super.createGraphics(bi);
	}
}
