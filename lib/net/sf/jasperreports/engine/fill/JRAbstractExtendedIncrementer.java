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
package net.sf.jasperreports.engine.fill;

import net.sf.jasperreports.engine.JRException;

/**
 * Base class for extended incrementers.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public abstract class JRAbstractExtendedIncrementer implements JRExtendedIncrementer
{

	/**
	 * This implementation simply calls {@link JRExtendedIncrementer#increment(JRCalculable, Object, AbstractValueProvider) increment(JRCalculable, Object, AbstractValueProvider)}.
	 */
	@Override
	public Object increment(JRFillVariable variable, Object expressionValue, AbstractValueProvider valueProvider) throws JRException
	{
		return increment((JRCalculable) variable, expressionValue, valueProvider);
	}

	/**
	 * This implementation calls {@link JRExtendedIncrementer#increment(JRCalculable, Object, AbstractValueProvider) increment(calculable, calculableValue.getValue(), valueProvider)}.
	 */
	@Override
	public Object combine(JRCalculable calculable, JRCalculable calculableValue, AbstractValueProvider valueProvider) throws JRException
	{
		return increment(calculable, calculableValue.getValue(), valueProvider);
	}

	/**
	 * This implementation returns <code>true</code>.
	 * 
	 * Incrementer implementations can override this to specify that
	 * <code>null</code> values should not be ignored.
	 */
	@Override
	public boolean ignoresNullValues()
	{
		return true;
	}

}
