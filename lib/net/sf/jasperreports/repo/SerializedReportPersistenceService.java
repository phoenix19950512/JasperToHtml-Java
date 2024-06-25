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
package net.sf.jasperreports.repo;

import net.sf.jasperreports.engine.JasperReport;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class SerializedReportPersistenceService extends SerializedObjectPersistenceService
{

	@Override
	public Resource load(String uri, RepositoryService repositoryService)
	{
		return load(null, uri, repositoryService);
	}
	
	@Override
	public Resource load(RepositoryContext context, String uri, RepositoryService repositoryService)
	{
		ReportResource reportResource = null;
		
		SerializableResource resource = (SerializableResource)super.load(context, uri, repositoryService);
		
		if (resource != null)
		{
			reportResource = new ReportResource();
			reportResource.setReport((JasperReport)resource.getValue());
		}
		
		return reportResource;
	}
}
