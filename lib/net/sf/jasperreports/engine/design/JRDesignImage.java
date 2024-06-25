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
package net.sf.jasperreports.engine.design;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.jasperreports.engine.JRAnchor;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultStyleProvider;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRHyperlinkHelper;
import net.sf.jasperreports.engine.JRHyperlinkParameter;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.base.JRBaseImage;
import net.sf.jasperreports.engine.base.JRBaseLineBox;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.HorizontalImageAlignEnum;
import net.sf.jasperreports.engine.type.HyperlinkTargetEnum;
import net.sf.jasperreports.engine.type.HyperlinkTypeEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;
import net.sf.jasperreports.engine.type.RotationEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.type.VerticalImageAlignEnum;
import net.sf.jasperreports.engine.util.JRCloneUtils;


/**
 * The actual implementation of a graphic element representing an image, used at design time.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRDesignImage extends JRDesignGraphicElement implements JRImage
{


	/**
	 *
	 */
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	/*
	 * Image properties
	 */
	
	public static final String PROPERTY_ANCHOR_NAME_EXPRESSION = "anchorNameExpression";
	
	public static final String PROPERTY_BOOKMARK_LEVEL = "bookmarkLevel";
	
	public static final String PROPERTY_BOOKMARK_LEVEL_EXPRESSION = "bookmarkLevelExpression";
	
	public static final String PROPERTY_EVALUATION_GROUP = "evaluationGroup";
	
	public static final String PROPERTY_EVALUATION_TIME = "evaluationTime";
	
	public static final String PROPERTY_EXPRESSION = "expression";
	
	/**
	 *
	 */
	protected ScaleImageEnum scaleImageValue;
	protected RotationEnum rotation;
	protected HorizontalImageAlignEnum horizontalImageAlign;
	protected VerticalImageAlignEnum verticalImageAlign;
	protected Boolean isUsingCache;
	protected boolean isLazy;
	protected OnErrorTypeEnum onErrorTypeValue = OnErrorTypeEnum.ERROR;
	protected EvaluationTimeEnum evaluationTimeValue = EvaluationTimeEnum.NOW;
	protected String linkType;
	protected String linkTarget;
	private List<JRHyperlinkParameter> hyperlinkParameters;

	/**
	 *
	 */
	protected JRLineBox lineBox;

	/**
	 *
	 */
	protected JRGroup evaluationGroup;
	protected JRExpression expression;
	protected JRExpression anchorNameExpression;
	protected JRExpression bookmarkLevelExpression;
	protected JRExpression hyperlinkReferenceExpression;
	protected JRExpression hyperlinkWhenExpression;
	protected JRExpression hyperlinkAnchorExpression;
	protected JRExpression hyperlinkPageExpression;
	private JRExpression hyperlinkTooltipExpression;

	/**
	 * The bookmark level for the anchor associated with this image.
	 * @see JRAnchor#getBookmarkLevel()
	 */
	protected int bookmarkLevel = JRAnchor.NO_BOOKMARK;


	/**
	 *
	 */
	public JRDesignImage(JRDefaultStyleProvider defaultStyleProvider)
	{
		super(defaultStyleProvider);
		
		hyperlinkParameters = new ArrayList<>();
		
		lineBox = new JRBaseLineBox(this);
	}
		

	@Override
	public ModeEnum getModeValue()
	{
		return getStyleResolver().getMode(this, ModeEnum.TRANSPARENT);
	}


	@Override
	public ScaleImageEnum getScaleImageValue()
	{
		return getStyleResolver().getScaleImageValue(this);
	}

	@Override
	public ScaleImageEnum getOwnScaleImageValue()
	{
		return this.scaleImageValue;
	}

	@Override
	public void setScaleImage(ScaleImageEnum scaleImageValue)
	{
		Object old = this.scaleImageValue;
		this.scaleImageValue = scaleImageValue;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_SCALE_IMAGE, old, this.scaleImageValue);
	}

	@Override
	public RotationEnum getRotation()
	{
		return getStyleResolver().getRotation(this);
	}

	@Override
	public RotationEnum getOwnRotation()
	{
		return this.rotation;
	}

	@Override
	public void setRotation(RotationEnum rotation)
	{
		Object old = this.rotation;
		this.rotation = rotation;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_ROTATION, old, this.rotation);
	}

	@Override
	public HorizontalImageAlignEnum getHorizontalImageAlign()
	{
		return getStyleResolver().getHorizontalImageAlign(this);
	}
		
	@Override
	public HorizontalImageAlignEnum getOwnHorizontalImageAlign()
	{
		return horizontalImageAlign;
	}
		
	@Override
	public void setHorizontalImageAlign(HorizontalImageAlignEnum horizontalImageAlign)
	{
		Object old = this.horizontalImageAlign;
		this.horizontalImageAlign = horizontalImageAlign;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_HORIZONTAL_IMAGE_ALIGNMENT, old, this.horizontalImageAlign);
	}

	@Override
	public VerticalImageAlignEnum getVerticalImageAlign()
	{
		return getStyleResolver().getVerticalImageAlign(this);
	}
		
	@Override
	public VerticalImageAlignEnum getOwnVerticalImageAlign()
	{
		return verticalImageAlign;
	}
		
	@Override
	public void setVerticalImageAlign(VerticalImageAlignEnum verticalImageAlign)
	{
		Object old = this.verticalImageAlign;
		this.verticalImageAlign = verticalImageAlign;
		getEventSupport().firePropertyChange(JRBaseStyle.PROPERTY_VERTICAL_IMAGE_ALIGNMENT, old, this.verticalImageAlign);
	}

	@Override
	public Boolean getUsingCache()
	{
		return isUsingCache;
	}

	@Override
	public EvaluationTimeEnum getEvaluationTimeValue()
	{
		return evaluationTimeValue;
	}
		
	@Override
	public JRLineBox getLineBox()
	{
		return lineBox;
	}

	/**
	 * @deprecated Replaced by {@link #getHyperlinkTypeValue()}.
	 */
	public byte getHyperlinkType()
	{
		return getHyperlinkTypeValue().getValue();
	}
		
	@Override
	public HyperlinkTypeEnum getHyperlinkTypeValue()
	{
		return JRHyperlinkHelper.getHyperlinkTypeValue(this);
	}
		
	/**
	 * @deprecated Replaced by {@link #getHyperlinkTargetValue()}.
	 */
	@Override
	public byte getHyperlinkTarget()
	{
		return getHyperlinkTargetValue().getValue();
	}
		
	@Override
	public HyperlinkTargetEnum getHyperlinkTargetValue()
	{
		return JRHyperlinkHelper.getHyperlinkTargetValue(this);
	}
		
	@Override
	public JRGroup getEvaluationGroup()
	{
		return evaluationGroup;
	}
		
	@Override
	public JRExpression getExpression()
	{
		return expression;
	}

	@Override
	public JRExpression getAnchorNameExpression()
	{
		return anchorNameExpression;
	}

	@Override
	public JRExpression getBookmarkLevelExpression()
	{
		return this.bookmarkLevelExpression;
	}

	@Override
	public JRExpression getHyperlinkReferenceExpression()
	{
		return hyperlinkReferenceExpression;
	}

	@Override
	public JRExpression getHyperlinkWhenExpression()
	{
		return hyperlinkWhenExpression;
	}

	@Override
	public JRExpression getHyperlinkAnchorExpression()
	{
		return hyperlinkAnchorExpression;
	}

	@Override
	public JRExpression getHyperlinkPageExpression()
	{
		return hyperlinkPageExpression;
	}


	@Override
	public void setUsingCache(Boolean isUsingCache)
	{
		Object old = this.isUsingCache;
		this.isUsingCache = isUsingCache;
		getEventSupport().firePropertyChange(JRBaseImage.PROPERTY_USING_CACHE, old, this.isUsingCache);
	}

	@Override
	public boolean isLazy()
	{
		return isLazy;
	}

	@Override
	public void setLazy(boolean isLazy)
	{
		boolean old = this.isLazy;
		this.isLazy = isLazy;
		getEventSupport().firePropertyChange(JRBaseImage.PROPERTY_LAZY, old, this.isLazy);
	}

	@Override
	public OnErrorTypeEnum getOnErrorTypeValue()
	{
		return this.onErrorTypeValue;
	}

	@Override
	public void setOnErrorType(OnErrorTypeEnum onErrorTypeValue)
	{
		OnErrorTypeEnum old = this.onErrorTypeValue;
		this.onErrorTypeValue = onErrorTypeValue;
		getEventSupport().firePropertyChange(JRBaseImage.PROPERTY_ON_ERROR_TYPE, old, this.onErrorTypeValue);
	}

	/**
	 * Sets the evaluation time for this image.
	 * 
	 */
	public void setEvaluationTime(EvaluationTimeEnum evaluationTimeValue)
	{
		Object old = this.evaluationTimeValue;
		this.evaluationTimeValue = evaluationTimeValue;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_TIME, old, this.evaluationTimeValue);
	}
		
	/**
	 * Sets the link type as a built-in hyperlink type.
	 * 
	 * @param hyperlinkType the built-in hyperlink type
	 * @see #getLinkType()
	 */
	public void setHyperlinkType(HyperlinkTypeEnum hyperlinkType)
	{
		setLinkType(JRHyperlinkHelper.getLinkType(hyperlinkType));
	}
		
	/**
	 *
	 */
	public void setHyperlinkTarget(HyperlinkTargetEnum hyperlinkTarget)
	{
		setLinkTarget(JRHyperlinkHelper.getLinkTarget(hyperlinkTarget));
	}
		
	/**
	 *
	 */
	public void setEvaluationGroup(JRGroup evaluationGroup)
	{
		Object old = this.evaluationGroup;
		this.evaluationGroup = evaluationGroup;
		getEventSupport().firePropertyChange(PROPERTY_EVALUATION_GROUP, old, this.evaluationGroup);
	}
		
	/**
	 *
	 */
	public void setExpression(JRExpression expression)
	{
		Object old = this.expression;
		this.expression = expression;
		getEventSupport().firePropertyChange(PROPERTY_EXPRESSION, old, this.expression);
	}

	/**
	 *
	 */
	public void setAnchorNameExpression(JRExpression anchorNameExpression)
	{
		Object old = this.anchorNameExpression;
		this.anchorNameExpression = anchorNameExpression;
		getEventSupport().firePropertyChange(PROPERTY_ANCHOR_NAME_EXPRESSION, old, this.anchorNameExpression);
	}

	/**
	 *
	 */
	public void setBookmarkLevelExpression(JRExpression bookmarkLevelExpression)
	{
		Object old = this.bookmarkLevelExpression;
		this.bookmarkLevelExpression = bookmarkLevelExpression;
		getEventSupport().firePropertyChange(PROPERTY_BOOKMARK_LEVEL_EXPRESSION, old, this.bookmarkLevelExpression);
	}

	/**
	 *
	 */
	public void setHyperlinkReferenceExpression(JRExpression hyperlinkReferenceExpression)
	{
		Object old = this.hyperlinkReferenceExpression;
		this.hyperlinkReferenceExpression = hyperlinkReferenceExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_REFERENCE_EXPRESSION, old, this.hyperlinkReferenceExpression);
	}

	/**
	 *
	 */
	public void setHyperlinkWhenExpression(JRExpression hyperlinkWhenExpression)
	{
		Object old = this.hyperlinkWhenExpression;
		this.hyperlinkWhenExpression = hyperlinkWhenExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_WHEN_EXPRESSION, old, this.hyperlinkWhenExpression);
	}

	/**
	 *
	 */
	public void setHyperlinkAnchorExpression(JRExpression hyperlinkAnchorExpression)
	{
		Object old = this.hyperlinkAnchorExpression;
		this.hyperlinkAnchorExpression = hyperlinkAnchorExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_ANCHOR_EXPRESSION, old, this.hyperlinkAnchorExpression);
	}

	/**
	 *
	 */
	public void setHyperlinkPageExpression(JRExpression hyperlinkPageExpression)
	{
		Object old = this.hyperlinkPageExpression;
		this.hyperlinkPageExpression = hyperlinkPageExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_PAGE_EXPRESSION, old, this.hyperlinkPageExpression);
	}
	
	@Override
	public void visit(JRVisitor visitor)
	{
		visitor.visitImage(this);
	}

	@Override
	public void collectExpressions(JRExpressionCollector collector)
	{
		collector.collect(this);
	}


	@Override
	public int getBookmarkLevel()
	{
		return bookmarkLevel;
	}


	/**
	 * Sets the boomark level for the anchor associated with this image.
	 * 
	 * @param bookmarkLevel the bookmark level (starting from 1)
	 * or {@link JRAnchor#NO_BOOKMARK NO_BOOKMARK} if no bookmark should be created 
	 */
	public void setBookmarkLevel(int bookmarkLevel)
	{
		int old = this.bookmarkLevel;
		this.bookmarkLevel = bookmarkLevel;
		getEventSupport().firePropertyChange(PROPERTY_BOOKMARK_LEVEL, old, this.bookmarkLevel);
	}

	@Override
	public Float getDefaultLineWidth() 
	{
		return JRPen.LINE_WIDTH_0;
	}

	@Override
	public String getLinkType()
	{
		return linkType;
	}

	@Override
	public String getLinkTarget()
	{
		return linkTarget;
	}


	/**
	 * Sets the hyperlink type.
	 * <p>
	 * The type can be one of the built-in types
	 * (Reference, LocalAnchor, LocalPage, RemoteAnchor, RemotePage),
	 * or can be an arbitrary type.
	 * </p>
	 * @param type the hyperlink type
	 */
	public void setLinkType(String type)
	{
		Object old = this.linkType;
		this.linkType = type;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_LINK_TYPE, old, this.linkType);
	}

	/**
	 * Sets the hyperlink target name.
	 * <p>
	 * The target name can be one of the built-in names
	 * (Self, Blank, Top, Parent),
	 * or can be an arbitrary name.
	 * </p>
	 * @param target the hyperlink target name
	 */
	public void setLinkTarget(String target)
	{
		Object old = this.linkTarget;
		this.linkTarget = target;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_LINK_TARGET, old, this.linkTarget);
	}

	@Override
	public JRHyperlinkParameter[] getHyperlinkParameters()
	{
		JRHyperlinkParameter[] parameters;
		if (hyperlinkParameters.isEmpty())
		{
			parameters = null;
		}
		else
		{
			parameters = new JRHyperlinkParameter[hyperlinkParameters.size()];
			hyperlinkParameters.toArray(parameters);
		}
		return parameters;
	}
	
	
	/**
	 * Returns the list of custom hyperlink parameters.
	 * 
	 * @return the list of custom hyperlink parameters
	 */
	public List<JRHyperlinkParameter> getHyperlinkParametersList()
	{
		return hyperlinkParameters;
	}
	
	
	/**
	 * Adds a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to add
	 */
	public void addHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		hyperlinkParameters.add(parameter);
		getEventSupport().fireCollectionElementAddedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
				parameter, hyperlinkParameters.size() - 1);
	}
	

	/**
	 * Removes a custom hyperlink parameter.
	 * 
	 * @param parameter the parameter to remove
	 */
	public void removeHyperlinkParameter(JRHyperlinkParameter parameter)
	{
		int idx = hyperlinkParameters.indexOf(parameter);
		if (idx >= 0)
		{
			hyperlinkParameters.remove(idx);
			getEventSupport().fireCollectionElementRemovedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
					parameter, idx);
		}
	}
	
	
	/**
	 * Removes a custom hyperlink parameter.
	 * <p>
	 * If multiple parameters having the specified name exist, all of them
	 * will be removed
	 * </p>
	 * 
	 * @param parameterName the parameter name
	 */
	public void removeHyperlinkParameter(String parameterName)
	{
		for (ListIterator<JRHyperlinkParameter> it = hyperlinkParameters.listIterator(); it.hasNext();)
		{
			JRHyperlinkParameter parameter = it.next();
			if (parameter.getName() != null && parameter.getName().equals(parameterName))
			{
				it.remove();
				getEventSupport().fireCollectionElementRemovedEvent(JRDesignHyperlink.PROPERTY_HYPERLINK_PARAMETERS, 
						parameter, it.nextIndex());
			}
		}
	}
	
	
	@Override
	public JRExpression getHyperlinkTooltipExpression()
	{
		return hyperlinkTooltipExpression;
	}

	
	/**
	 * Sets the expression which will be used to generate the hyperlink tooltip.
	 * 
	 * @param hyperlinkTooltipExpression the expression which will be used to generate the hyperlink tooltip
	 * @see #getHyperlinkTooltipExpression()
	 */
	public void setHyperlinkTooltipExpression(JRExpression hyperlinkTooltipExpression)
	{
		Object old = this.hyperlinkTooltipExpression;
		this.hyperlinkTooltipExpression = hyperlinkTooltipExpression;
		getEventSupport().firePropertyChange(JRDesignHyperlink.PROPERTY_HYPERLINK_TOOLTIP_EXPRESSION, old, this.hyperlinkTooltipExpression);
	}

	@Override
	public Object clone() 
	{
		JRDesignImage clone = (JRDesignImage)super.clone();
		clone.lineBox = lineBox.clone(clone);
		clone.hyperlinkParameters = JRCloneUtils.cloneList(hyperlinkParameters);
		clone.expression = JRCloneUtils.nullSafeClone(expression);
		clone.anchorNameExpression = JRCloneUtils.nullSafeClone(anchorNameExpression);
		clone.bookmarkLevelExpression = JRCloneUtils.nullSafeClone(bookmarkLevelExpression);
		clone.hyperlinkReferenceExpression = JRCloneUtils.nullSafeClone(hyperlinkReferenceExpression);
		clone.hyperlinkWhenExpression = JRCloneUtils.nullSafeClone(hyperlinkWhenExpression);
		clone.hyperlinkAnchorExpression = JRCloneUtils.nullSafeClone(hyperlinkAnchorExpression);
		clone.hyperlinkPageExpression = JRCloneUtils.nullSafeClone(hyperlinkPageExpression);
		clone.hyperlinkTooltipExpression = JRCloneUtils.nullSafeClone(hyperlinkTooltipExpression);
		return clone;
	}


	/*
	 * These fields are only for serialization backward compatibility.
	 */
	private int PSEUDO_SERIAL_VERSION_UID = JRConstants.PSEUDO_SERIAL_VERSION_UID; //NOPMD
	/**
	 * @deprecated
	 */
	private Byte horizontalAlignment;
	/**
	 * @deprecated
	 */
	private Byte verticalAlignment;
	/**
	 * @deprecated
	 */
	private net.sf.jasperreports.engine.type.HorizontalAlignEnum horizontalAlignmentValue;
	/**
	 * @deprecated
	 */
	private net.sf.jasperreports.engine.type.VerticalAlignEnum verticalAlignmentValue;
	/**
	 * @deprecated
	 */
	private byte hyperlinkType;
	/**
	 * @deprecated
	 */
	private byte hyperlinkTarget;
	/**
	 * @deprecated
	 */
	private Byte scaleImage;
	/**
	 * @deprecated
	 */
	private byte onErrorType;
	/**
	 * @deprecated
	 */
	private byte evaluationTime;
	
	@SuppressWarnings("deprecation")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();

		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_3_7_2)
		{
			horizontalAlignmentValue = net.sf.jasperreports.engine.type.HorizontalAlignEnum.getByValue(horizontalAlignment);
			verticalAlignmentValue = net.sf.jasperreports.engine.type.VerticalAlignEnum.getByValue(verticalAlignment);
			scaleImageValue = ScaleImageEnum.getByValue(scaleImage);
			onErrorTypeValue = OnErrorTypeEnum.getByValue(onErrorType);
			evaluationTimeValue = EvaluationTimeEnum.getByValue(evaluationTime);

			horizontalAlignment = null;
			verticalAlignment = null;
			scaleImage = null;
		}

		if (linkType == null)
		{
			 linkType = JRHyperlinkHelper.getLinkType(HyperlinkTypeEnum.getByValue(hyperlinkType));
		}

		if (linkTarget == null)
		{
			 linkTarget = JRHyperlinkHelper.getLinkTarget(HyperlinkTargetEnum.getByValue(hyperlinkTarget));
		}

		if (PSEUDO_SERIAL_VERSION_UID < JRConstants.PSEUDO_SERIAL_VERSION_UID_6_0_2)
		{
			horizontalImageAlign = net.sf.jasperreports.engine.type.HorizontalAlignEnum.getHorizontalImageAlignEnum(horizontalAlignmentValue);
			verticalImageAlign = net.sf.jasperreports.engine.type.VerticalAlignEnum.getVerticalImageAlignEnum(verticalAlignmentValue);

			horizontalAlignmentValue = null;
			verticalAlignmentValue = null;
		}
	}
}
