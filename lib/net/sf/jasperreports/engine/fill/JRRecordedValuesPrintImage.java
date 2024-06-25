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

import java.io.IOException;
import java.util.Set;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.virtualization.VirtualizationInput;
import net.sf.jasperreports.engine.virtualization.VirtualizationOutput;


/**
 * Print image implementation that supports recorded values.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class JRRecordedValuesPrintImage extends JRTemplatePrintImage implements JRRecordedValuesPrintElement
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private JRRecordedValues recordedValues;

	public JRRecordedValuesPrintImage()
	{
	}
	
	/**
	 * 
	 * @param image
	 * @param originator
	 */
	public JRRecordedValuesPrintImage(JRTemplateImage image, PrintElementOriginator originator)
	{
		super(image, originator);
	}

	@Override
	public JRRecordedValues getRecordedValues()
	{
		return recordedValues;
	}

	@Override
	public void deleteRecordedValues()
	{
		recordedValues = null;
	}

	@Override
	public void initRecordedValues(Set<JREvaluationTime> evaluationTimes)
	{
		recordedValues = new JRRecordedValues(evaluationTimes);
	}

	@Override
	public void writeVirtualized(VirtualizationOutput out) throws IOException
	{
		super.writeVirtualized(out);
		
		out.writeJRObject(recordedValues);
	}

	@Override
	public void readVirtualized(VirtualizationInput in) throws IOException
	{
		super.readVirtualized(in);
		
		recordedValues = (JRRecordedValues) in.readJRObject();
	}
}
