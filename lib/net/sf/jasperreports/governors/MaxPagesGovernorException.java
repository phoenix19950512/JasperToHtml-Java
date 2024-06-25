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
package net.sf.jasperreports.governors;

import net.sf.jasperreports.engine.JRConstants;



/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class MaxPagesGovernorException extends GovernorException
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	public static final String EXCEPTION_MESSAGE_KEY_MAX_PAGES_LIMIT_EXCEEDED = "governors.max.pages.limit.exceeded";

	private int maxPages;
	
	/**
	 *
	 */
	public MaxPagesGovernorException(String reportName, int maxPages)
	{
		super(
			EXCEPTION_MESSAGE_KEY_MAX_PAGES_LIMIT_EXCEEDED,
			new Object[]{reportName, maxPages});
		this.maxPages = maxPages;
	}
	
	/**
	 *
	 */
	public int getMaxPages()
	{
		return maxPages;
	}
	
}
