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
package net.sf.jasperreports.components.barcode4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectInputStream.GetField;
import java.io.Serializable;

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.design.events.JRPropertyChangeSupport;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.util.Designated;
import net.sf.jasperreports.engine.util.JRCloneUtils;

/**
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public abstract class BarcodeComponent implements Component, Serializable, JRCloneable, JRChangeEventsSupport, Designated
{
	// used as prefix for specific barcode component properties
	public static final String PROPERTY_PREFIX = 
		JRPropertiesUtil.PROPERTY_PREFIX + "components.barcode4j.";

	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
	public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
	public static final String PROPERTY_CODE_EXPRESSION = "codeExpression";
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private transient JRPropertyChangeSupport eventSupport;
	
	private EvaluationTimeEnum evaluationTimeValue;
	private String evaluationGroup;
	
	private JRExpression codeExpression;

	public BarcodeComponent()
	{
	}
	
	public EvaluationTimeEnum getEvaluationTimeValue()
	{
		return evaluationTimeValue == null ? EvaluationTimeEnum.NOW : evaluationTimeValue;
	}

	public void setEvaluationTimeValue(EvaluationTimeEnum evaluationTimeValue)
	{
		Object old = this.evaluationTimeValue;
		this.evaluationTimeValue = evaluationTimeValue;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, 
				old, this.evaluationTimeValue);
	}

	public String getEvaluationGroup()
	{
		return evaluationGroup;
	}

	public void setEvaluationGroup(String evaluationGroup)
	{
		Object old = this.evaluationGroup;
		this.evaluationGroup = evaluationGroup;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_GROUP, 
				old, this.evaluationGroup);
	}

	public JRExpression getCodeExpression()
	{
		return codeExpression;
	}

	public void setCodeExpression(JRExpression codeExpression)
	{
		Object old = this.codeExpression;
		this.codeExpression = codeExpression;
		getEventSupport().firePropertyChange(PROPERTY_CODE_EXPRESSION, 
				old, this.codeExpression);
	}

	@Override
	public Object clone()
	{
		BarcodeComponent clone = null;
		try
		{
			clone = (BarcodeComponent) super.clone();
		} 
		catch (CloneNotSupportedException e)
		{
			// never
			throw new JRRuntimeException(e);
		}
		clone.codeExpression = JRCloneUtils.nullSafeClone(codeExpression);
		clone.eventSupport = null;
		return clone;
	}
	
	protected BarcodeComponent cloneObject() //FIXMENOW where is this method coming from?
	{
		BarcodeComponent clone = null;
		try
		{
			clone = (BarcodeComponent)super.clone();
			clone.eventSupport = null;
		}
		catch (CloneNotSupportedException e)
		{
			// never
			throw new JRRuntimeException(e);
		}
		return clone;
	}
	
	public abstract void receive(BarcodeVisitor visitor);

	@Override
	public JRPropertyChangeSupport getEventSupport()
	{
		synchronized (this)
		{
			if (eventSupport == null)
			{
				eventSupport = new JRPropertyChangeSupport(this);
			}
		}
		
		return eventSupport;
	}

	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	
	@SuppressWarnings("deprecation")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		GetField fields = in.readFields();
		this.PSEUDO_SERIAL_VERSION_UID = fields.get("PSEUDO_SERIAL_VERSION_UID", 0);
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2)
		{
			byte evaluationTime = fields.get("evaluationTime", (byte) 0);
			this.evaluationTimeValue = EvaluationTimeEnum.getByValue(evaluationTime);
		}
		else
		{
			this.evaluationTimeValue = (EvaluationTimeEnum) fields.get("evaluationTimeValue", null);
		}
		this.evaluationGroup = (String) fields.get("evaluationGroup", null);
		this.codeExpression = (JRExpression) fields.get("codeExpression", null);
		
		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_6_0_2
				&& this instanceof Barcode4jComponent)
		{
			//up to 6.0.0 this class had several fields that were moved to Barcode4jComponent in 6.0.2.
			//copying the values to the Barcode4jComponent fields.
			Barcode4jComponent barcode4jComponent = (Barcode4jComponent) this;
			barcode4jComponent.copyBarcodeComponentFields(fields);
		}
	}
}
