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
package net.sf.jasperreports.util;

import java.io.IOException;
import java.io.Writer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class StringBuilderWriter extends Writer
{
	private final StringBuilder sb;
	
	public StringBuilderWriter(StringBuilder sb)
	{
		super();
		this.sb = sb;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException 
	{
		sb.append(cbuf, off, len);
	}

	@Override
	public void write(int c) throws IOException
	{
		sb.append((char) c);
	}

	@Override
	public void write(String str) throws IOException
	{
		sb.append(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException
	{
		sb.append(str.substring(off, off + len));
	}

	@Override
	public Writer append(char c) throws IOException
	{
		sb.append(c);
		return this;
	}

	@Override
	public void close() throws IOException
	{
	}

	@Override
	public void flush() throws IOException 
	{
	}
}