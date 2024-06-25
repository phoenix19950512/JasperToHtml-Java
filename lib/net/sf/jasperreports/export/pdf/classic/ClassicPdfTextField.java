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
package net.sf.jasperreports.export.pdf.classic;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.TextField;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.export.type.PdfFieldTypeEnum;
import net.sf.jasperreports.export.pdf.PdfTextField;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public class ClassicPdfTextField extends ClassicPdfField implements PdfTextField
{

	private TextField textField;
	private PdfFieldTypeEnum fieldType;
	
	public ClassicPdfTextField(ClassicPdfProducer pdfProducer, TextField textField, PdfFieldTypeEnum fieldType)
	{
		super(pdfProducer, textField);
		this.textField = textField;
		this.fieldType = fieldType;
	}

	@Override
	public void setEdit()
	{
		textField.setOptions(textField.getOptions() | TextField.EDIT);
	}

	@Override
	public void setMultiline()
	{
		textField.setOptions(textField.getOptions() | TextField.MULTILINE);
	}

	@Override
	public void add()
	{
		try
		{
			PdfFormField field = 
				fieldType == PdfFieldTypeEnum.COMBO 
				? textField.getComboField() 
				: (fieldType == PdfFieldTypeEnum.LIST 
					? textField.getListField()
					: textField.getTextField());

			pdfProducer.getPdfWriter().addAnnotation(field);
		}
		catch (IOException | DocumentException e)
		{
			throw new JRRuntimeException(e);
		}
	}

}
