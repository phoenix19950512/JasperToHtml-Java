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
package net.sf.jasperreports.swing;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.renderers.RenderersCache;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRDesignViewerPanel extends JRViewerPanel
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public JRDesignViewerPanel(JRViewerController viewerContext)
	{
		super(viewerContext);
	}

	@Override
	protected JRGraphics2DExporter getGraphics2DExporter() throws JRException
	{
		return 
			new JRGraphics2DExporter(viewerContext.getJasperReportsContext())
			{
				@Override
				protected void initReport()
				{
					super.initReport();
					drawVisitor.setClip(true);//FIXMENOW thick border of margin elements is clipped
				}
				@Override
				protected RenderersCache getRenderersCache()
				{
					return viewerContext.getRenderersCache();
				}
			};
	}
}
