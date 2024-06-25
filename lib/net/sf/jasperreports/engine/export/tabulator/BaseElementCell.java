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
package net.sf.jasperreports.engine.export.tabulator;

import net.sf.jasperreports.engine.export.PrintElementIndex;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public abstract class BaseElementCell implements Cell
{
	protected FrameCell parent;

	protected PrintElementIndex parentIndex;
	protected int elementIndex;

	public BaseElementCell(FrameCell parent, PrintElementIndex parentIndex, int elementIndex)
	{
		this.parent = parent;
		this.parentIndex = parentIndex;
		this.elementIndex = elementIndex;
	}

	@Override
	public FrameCell getParent()
	{
		return parent;
	}

	public void setParent(FrameCell parent)
	{
		this.parent = parent;
	}

	public PrintElementIndex getParentIndex()
	{
		return parentIndex;
	}

	public int getElementIndex()
	{
		return elementIndex;
	}

}
