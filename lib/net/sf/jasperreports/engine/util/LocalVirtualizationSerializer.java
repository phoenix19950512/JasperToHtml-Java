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
package net.sf.jasperreports.engine.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRVirtualizationContext;
import net.sf.jasperreports.engine.virtualization.LocalVirtualizationInput;
import net.sf.jasperreports.engine.virtualization.LocalVirtualizationOutput;
import net.sf.jasperreports.engine.virtualization.VirtualizationInput;
import net.sf.jasperreports.engine.virtualization.VirtualizationOutput;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class LocalVirtualizationSerializer extends VirtualizationSerializer
{

	protected static final int CLASSLOADER_IDX_NOT_SET = -1;

	protected static boolean isAncestorClassLoader(ClassLoader loader)
	{
		for (
				ClassLoader ancestor = JRAbstractLRUVirtualizer.class.getClassLoader();
				ancestor != null;
				ancestor = ancestor.getParent())
		{
			if (ancestor.equals(loader))
			{
				return true;
			}
		}
		return false;
	}

	protected final Map<ClassLoader,Integer> classLoadersIndexes = new HashMap<>();
	protected final List<ClassLoader> classLoadersList = new ArrayList<>();
	
	protected final Map<Class<?>, Integer> classIndexes = new HashMap<>();
	protected final List<Class<?>> classes = new ArrayList<>();

	public LocalVirtualizationSerializer()
	{
	}
	
	public int getClassloaderIdx(Class<?> clazz)
	{
		ClassLoader classLoader = clazz.getClassLoader();
		int loaderIdx;
		if (clazz.isPrimitive()
				|| classLoader == null
				|| isAncestorClassLoader(classLoader))
		{
			loaderIdx = CLASSLOADER_IDX_NOT_SET;
		}
		else
		{
			Integer idx = classLoadersIndexes.get(classLoader);
			if (idx == null)
			{
				idx = classLoadersList.size();
				classLoadersIndexes.put(classLoader, idx);
				classLoadersList.add(classLoader);
			}
			loaderIdx = idx;
		}
		return loaderIdx;
	}
	
	public Class<?> resolveClass(ObjectStreamClass desc, int loaderIdx) throws ClassNotFoundException
	{
		if (loaderIdx == CLASSLOADER_IDX_NOT_SET)
		{
			return null;
		}

		ClassLoader loader = classLoadersList.get(loaderIdx);
		Class<?> clazz = Class.forName(desc.getName(), false, loader);
		return clazz;
	}
	
	public int getClassDescriptorIdx(Class<?> clazz)
	{
		Integer classIdx = classIndexes.get(clazz);
		if (classIdx == null)
		{
			classIdx = classIndexes.size();
			classIndexes.put(clazz, classIdx);
			classes.add(clazz);
		}
		return classIdx;
	}
	
	public Class<?> getClassForDescriptorIdx(int descriptorIdx)
	{
		return classes.get(descriptorIdx);
	}

	protected VirtualizationOutput createOutput(JRVirtualizationContext context, OutputStream out) throws IOException
	{
		return new LocalVirtualizationOutput(out, this, context);
	}

	protected VirtualizationInput createInput(JRVirtualizationContext context, InputStream in) throws IOException
	{
		return new LocalVirtualizationInput(in, this, context);
	}

}
