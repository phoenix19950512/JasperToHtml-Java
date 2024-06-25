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
package net.sf.jasperreports.engine.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;

import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.TextAdjustEnum;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRTextFieldFactory extends JRBaseFactory
{
	private static final Log log = LogFactory.getLog(JRTextFieldFactory.class);

	/**
	 *
	 */
	public JRDesignTextField getTextField()
	{
		JasperDesign jasperDesign = (JasperDesign)digester.peek(digester.getCount() - 2);

		return new JRDesignTextField(jasperDesign);
	}

	@Override
	public Object createObject(Attributes atts)
	{
		JRXmlLoader xmlLoader = (JRXmlLoader)digester.peek(digester.getCount() - 1);

		JRDesignTextField textField = getTextField();

		String isStretchWithOverflow = atts.getValue(JRXmlConstants.ATTRIBUTE_isStretchWithOverflow);
		if (isStretchWithOverflow != null && isStretchWithOverflow.length() > 0)
		{
			if (log.isWarnEnabled())
			{
				log.warn("The 'isStretchWithOverflow' attribute is deprecated. Use the 'textAdjust' attribute instead.");
			}
				
			textField.setTextAdjust(Boolean.valueOf(isStretchWithOverflow) ? TextAdjustEnum.STRETCH_HEIGHT : TextAdjustEnum.CUT_TEXT);
		}
		TextAdjustEnum textAdjust = TextAdjustEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_textAdjust));
		if (textAdjust != null)
		{
			textField.setTextAdjust(textAdjust);
		}

		EvaluationTimeEnum evaluationTime = EvaluationTimeEnum.getByName(atts.getValue(JRXmlConstants.ATTRIBUTE_evaluationTime));
		if (evaluationTime != null)
		{
			textField.setEvaluationTime(evaluationTime);
		}
		if (textField.getEvaluationTimeValue() == EvaluationTimeEnum.GROUP)
		{
			xmlLoader.addGroupReference(new TextFieldEvaluationGroupReference(textField));
			
			String groupName = atts.getValue(JRXmlConstants.ATTRIBUTE_evaluationGroup);
			if (groupName != null)
			{
				JRDesignGroup group = new JRDesignGroup();
				group.setName(groupName);
				textField.setEvaluationGroup(group);
			}
		}
		
		textField.setPattern(atts.getValue(JRXmlConstants.ATTRIBUTE_pattern));

		String isBlankWhenNull = atts.getValue(JRXmlConstants.ATTRIBUTE_isBlankWhenNull);
		if (isBlankWhenNull != null && isBlankWhenNull.length() > 0)
		{
			textField.setBlankWhenNull(Boolean.valueOf(isBlankWhenNull));
		}

		textField.setLinkType(atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkType));
		textField.setLinkTarget(atts.getValue(JRXmlConstants.ATTRIBUTE_hyperlinkTarget));
		
		String bookmarkLevelAttr = atts.getValue(JRXmlConstants.ATTRIBUTE_bookmarkLevel);
		if (bookmarkLevelAttr != null)
		{
			textField.setBookmarkLevel(Integer.parseInt(bookmarkLevelAttr));
		}

		return textField;
	}
	

}
