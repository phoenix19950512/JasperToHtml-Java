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
package net.sf.jasperreports.data.cache;

import java.io.IOException;
import java.io.Serializable;

import net.sf.jasperreports.engine.JRConstants;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class ObjectArrayValues implements ColumnValues, Serializable
{

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private Object[] values;
	
	public ObjectArrayValues(Object[] values)
	{
		this.values = values;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeInt(values.length);
		for (int i = 0; i < values.length; i++)
		{
			// TODO lucianc investigate when writeUnshared would help here
			out.writeObject(values[i]);
		}
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		int size = in.readInt();
		values = new Object[size];
		for (int i = 0; i < size; i++)
		{
			values[i] = in.readObject();
		}
	}
	
	@Override
	public int size()
	{
		return values.length;
	}

	@Override
	public ColumnValuesIterator iterator()
	{
		return new ValuesIterator();
	}

	protected class ValuesIterator extends IndexColumnValueIterator
	{
		public ValuesIterator()
		{
			super(values.length);
		}

		@Override
		public Object get()
		{
			return values[currentIndex];
		}
	}
}
