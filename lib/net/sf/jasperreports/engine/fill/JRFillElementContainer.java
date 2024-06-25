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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRConditionalStyle;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintElementContainer;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.util.StyleUtil;

/**
 * Abstract implementation of an element container filler.
 * <p>
 * This is the base for band, frame and crosstab cell fillers.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 */
public abstract class JRFillElementContainer extends JRFillElementGroup implements FillContainerContext
{
	protected JRBaseFiller filler;
	
	private JRFillElement[] ySortedElements;
	private JRFillElement[] stretchElements;
	private JRFillElement[] bandBottomElements;
	private JRFillElement[] removableElements;
	
	protected boolean willOverflowWithElements;
	protected boolean willOverflowWithWhiteSpace;
	protected boolean isOverflow;
	protected boolean currentOverflowWithElements;
	protected boolean currentOverflowWithWhiteSpace;
	private boolean currentOverflowAllowed;
	
	private int stretchHeight;
	private int firstY;
	protected boolean atLeastOneElementIsToPrint;
	
	protected final JRFillExpressionEvaluator expressionEvaluator;
	
	protected JRFillElement[] deepElements;

	/**
	 *
	 */
	protected Set<JRStyle> stylesToEvaluate = new HashSet<>();
	protected Map<JRStyle,JRStyle> evaluatedStyles = new HashMap<>();
	
	protected boolean hasPrintWhenOverflowElement;
	
	private final boolean legacyElementStretchEnabled;

	
	protected JRFillElementContainer(JRBaseFiller filler, JRElementGroup container, JRFillObjectFactory factory)
	{
		super(container, factory);
		
		expressionEvaluator = factory.getExpressionEvaluator();
		initDeepElements();
		
		this.filler = filler;
		
		@SuppressWarnings("deprecation")
		boolean depFlag = filler.getFillContext().isLegacyElementStretchEnabled();
		legacyElementStretchEnabled = depFlag; 
	}
	
	protected JRFillElementContainer(JRFillElementContainer container, JRFillCloneFactory factory)
	{
		super(container, factory);
		
		expressionEvaluator = container.expressionEvaluator;
		initDeepElements();
		
		this.filler = container.filler;
		
		@SuppressWarnings("deprecation")
		boolean depFlag = filler.getFillContext().isLegacyElementStretchEnabled();
		legacyElementStretchEnabled = depFlag; 
	}


	protected void initDeepElements()
	{
		if (elements == null)
		{
			deepElements = new JRFillElement[0];
		}
		else
		{
			List<JRFillElement> deepElementsList = new ArrayList<>(elements.length);
			collectDeepElements(elements, deepElementsList);
			deepElements = new JRFillElement[deepElementsList.size()];
			deepElementsList.toArray(deepElements);
		}
	}

	private static void collectDeepElements(JRElement[] elements, List<JRFillElement> deepElementsList)
	{
		for (int i = 0; i < elements.length; i++)
		{
			JRElement element = elements[i];
			deepElementsList.add((JRFillElement)element);
			
			if (element instanceof JRFillFrame)
			{
				JRFrame frame = (JRFrame) element;
				collectDeepElements(frame.getElements(), deepElementsList);
			}
		}
	}

	/**
	 * @deprecated To be removed.
	 */
	protected final void _initElements()
	{
		hasPrintWhenOverflowElement = false;
		
		if (elements != null && elements.length > 0)
		{
			List<JRFillElement> sortedElemsList = new ArrayList<>();
			List<JRFillElement> stretchElemsList = new ArrayList<>();
			List<JRFillElement> bandBottomElemsList = new ArrayList<>();
			List<JRFillElement> removableElemsList = new ArrayList<>();
			
			topElementInGroup = null;
			bottomElementInGroup = null;

			for (JRFillElement element : elements)
			{
				sortedElemsList.add(element);
				
				if (element.getPositionTypeValue() == PositionTypeEnum.FIX_RELATIVE_TO_BOTTOM)
				{
					bandBottomElemsList.add(element);
				}

				if (element.getStretchTypeValue() != StretchTypeEnum.NO_STRETCH)
				{
					stretchElemsList.add(element);
				}
				
				if (element.isRemoveLineWhenBlank())
				{
					removableElemsList.add(element);
				}
				
				if (element.isPrintWhenDetailOverflows())
				{
					hasPrintWhenOverflowElement = true;
				}

				if (
					topElementInGroup == null ||
					(
					element.getY() + element.getHeight() <
					topElementInGroup.getY() + topElementInGroup.getHeight())
					)
				{
					topElementInGroup = element;
				}

				if (
					bottomElementInGroup == null ||
					(
					element.getY() + element.getHeight() >
					bottomElementInGroup.getY() + bottomElementInGroup.getHeight())
					)
				{
					bottomElementInGroup = element;
				}
			}

			/*   */
			Collections.sort(sortedElemsList, new JRYComparator());
			ySortedElements = new JRFillElement[elements.length];
			sortedElemsList.toArray(ySortedElements);

			/*   */
			stretchElements = new JRFillElement[stretchElemsList.size()];
			stretchElemsList.toArray(stretchElements);

			/*   */
			bandBottomElements = new JRFillElement[bandBottomElemsList.size()];
			bandBottomElemsList.toArray(bandBottomElements);

			/*   */
			removableElements = new JRFillElement[removableElemsList.size()];
			removableElemsList.toArray(removableElements);
		}
		
		/*   */
		setDependentElements();
	}

	protected final void initElements()
	{
		if (isLegacyElementStretchEnabled())
		{
			_initElements();
			return;
		}
		
		hasPrintWhenOverflowElement = false;
		
		if (elements != null && elements.length > 0)
		{
			List<JRFillElement> stretchElemsList = new ArrayList<>();
			List<JRFillElement> bandBottomElemsList = new ArrayList<>();
			List<JRFillElement> removableElemsList = new ArrayList<>();
			
			JRYComparator yComparator = new JRYComparator();

			/*   */
			ySortedElements = Arrays.copyOf(elements, elements.length);
			Arrays.sort(ySortedElements, yComparator);

			topElementInGroup = null;
			bottomElementInGroup = null;

			for (JRFillElement element : ySortedElements)
			{
				if (element.getPositionTypeValue() == PositionTypeEnum.FIX_RELATIVE_TO_BOTTOM)
				{
					bandBottomElemsList.add(element);
				}

				if (element.getStretchTypeValue() != StretchTypeEnum.NO_STRETCH)
				{
					stretchElemsList.add(element);
				}
				
				if (element.isRemoveLineWhenBlank())
				{
					removableElemsList.add(element);
				}
				
				if (element.isPrintWhenDetailOverflows())
				{
					hasPrintWhenOverflowElement = true;
				}

				if (
					topElementInGroup == null ||
					(
					element.getY() + element.getHeight() <
					topElementInGroup.getY() + topElementInGroup.getHeight())
					)
				{
					topElementInGroup = element;
				}

				if (
					bottomElementInGroup == null ||
					(
					element.getY() + element.getHeight() >
					bottomElementInGroup.getY() + bottomElementInGroup.getHeight())
					)
				{
					bottomElementInGroup = element;
				}
			}

			/*   */
			stretchElements = new JRFillElement[stretchElemsList.size()];
			stretchElemsList.toArray(stretchElements);

			/*   */
			bandBottomElements = new JRFillElement[bandBottomElemsList.size()];
			bandBottomElemsList.toArray(bandBottomElements);

			/*   */
			removableElements = new JRFillElement[removableElemsList.size()];
			removableElemsList.toArray(removableElements);
		}
		
		/*   */
		setDependentElements();
	}

	/**
	 *
	 */
	private void setDependentElements()
	{
		if (ySortedElements != null && ySortedElements.length > 0)
		{
			for(int i = 0; i < ySortedElements.length - 1; i++)
			{
				JRFillElement iElem = ySortedElements[i];
				boolean isBreakElem = iElem instanceof JRFillBreak;

				for(int j = i + 1; j < ySortedElements.length; j++)
				{
					JRFillElement jElem = ySortedElements[j];
					
					int left = Math.min(iElem.getX(), jElem.getX());
					int right = Math.max(iElem.getX() + iElem.getWidth(), jElem.getX() + jElem.getWidth());
					
					if (
						((isBreakElem && jElem.getPositionTypeValue() == PositionTypeEnum.FIX_RELATIVE_TO_TOP) || jElem.getPositionTypeValue() == PositionTypeEnum.FLOAT) &&
						iElem.getY() + iElem.getHeight() <= jElem.getY() &&
						iElem.getWidth() + jElem.getWidth() > right - left // FIXME band bottom elements should not have dependent elements
						)
					{
						iElem.addDependantElement(jElem);
					}
				}

				/*
				if (iElem.getParent().getElementGroup() != null) //parent might be null
				{
					iElem.setGroupElements(
						iElem.getParent().getElementGroup().getElements()
						);
				}
				*/
			}
		}
	}

	
	/**
	 *
	 */
	protected void evaluate(byte evaluation) throws JRException
	{
		//evaluatePrintWhenExpression(evaluation);

		//if (
		//	(isPrintWhenExpressionNull() ||
		//	(!isPrintWhenExpressionNull() && 
		//	isPrintWhenTrue()))
		//	)
		//{
			JRElement[] allElements = getElements();
			if (allElements != null && allElements.length > 0)
			{
				for(int i = 0; i < allElements.length; i++)
				{
					JRFillElement element = (JRFillElement)allElements[i];
					element.setCurrentEvaluation(evaluation);
					element.evaluate(evaluation);
				}
			}
		//}
	}


	/**
	 *
	 */
	protected void resetElements()
	{
		if (ySortedElements != null && ySortedElements.length > 0)
		{
			for(int i = 0; i < ySortedElements.length; i++)
			{
				JRFillElement element = ySortedElements[i];

				element.reset();
				
				if (!isOverflow)
				{
					element.setAlreadyPrinted(false);
				}
			}
		}
	}
	

	/**
	 * Indicates whether the elements in this container will overflow.
	 * 
	 * @return whether this container will overflow
	 */
	public boolean willOverflow()
	{
		return willOverflowWithElements || willOverflowWithWhiteSpace;
	}


	protected void initFill()
	{
		isOverflow = willOverflow();
		firstY = 0;
		atLeastOneElementIsToPrint = false;
	}


	/**
	 * @deprecated To be removed.
	 */
	protected void _prepareElements(
		int availableHeight,
		boolean isOverflowAllowed
		) throws JRException
	{
		currentOverflowWithElements = false;
		currentOverflowWithWhiteSpace = false;
		currentOverflowAllowed = isOverflowAllowed;

		int calculatedStretchHeight = getContainerHeight();

		firstY = isOverflow ? getActualContainerHeight() : 0;
		atLeastOneElementIsToPrint = false;
		boolean isFirstYFound = false;

		if (ySortedElements != null && ySortedElements.length > 0)
		{
			for(int i = 0; i < ySortedElements.length; i++)
			{
				JRFillElement element = ySortedElements[i];

				currentOverflowWithElements = 
					element.prepare(
						availableHeight + getElementFirstY(element),
						isOverflow
						) 
					|| currentOverflowWithElements;

				element._moveDependantElements();

				if (element.isToPrint())
				{
					if (isOverflow)
					{
						if (element.isReprinted())
						{
							firstY = 0;
						}
						else if (!isFirstYFound)
						{
							firstY = element.getY();
						}
						isFirstYFound = true;
					}

					atLeastOneElementIsToPrint = true;

					int spaceToBottom = getContainerHeight() - element.getY() - element.getHeight();
					if (spaceToBottom < 0)
					{
						spaceToBottom = 0;
					}
					
					if (calculatedStretchHeight < element.getRelativeY() + element.getStretchHeight() + spaceToBottom)
					{
						calculatedStretchHeight = element.getRelativeY() + element.getStretchHeight() + spaceToBottom;
					}
				}
			}
		}
		
		if (calculatedStretchHeight > availableHeight + firstY)
		{
			currentOverflowWithWhiteSpace = true;
		}
		
		// stretchHeight includes firstY, which is subtracted in fillElements
		if (currentOverflowWithElements || currentOverflowWithWhiteSpace)
		{
			stretchHeight = availableHeight + firstY;
		}
		else
		{
			stretchHeight = calculatedStretchHeight;
		}

		willOverflowWithElements = currentOverflowWithElements && isOverflowAllowed;
		willOverflowWithWhiteSpace = currentOverflowWithWhiteSpace && isOverflowAllowed;
	}

	
	/**
	 *
	 */
	protected void prepareElements(
		int availableHeight,
		boolean isOverflowAllowed
		) throws JRException
	{
		if (isLegacyElementStretchEnabled())
		{
			_prepareElements(availableHeight, isOverflowAllowed);
			return;
		}
		
		currentOverflowWithElements = false;
		currentOverflowWithWhiteSpace = false;
		currentOverflowAllowed = isOverflowAllowed;

		firstY = isOverflow ? getActualContainerHeight() : 0;
		atLeastOneElementIsToPrint = false;
		boolean isFirstYFound = false;

		if (ySortedElements != null && ySortedElements.length > 0)
		{
			for (JRFillElement element : ySortedElements)
			{
				currentOverflowWithElements = 
					element.prepare(
						availableHeight + getElementFirstY(element),
						isOverflow
						) 
					|| currentOverflowWithElements;
				
				// it does not seem to make sense for elements that do not print because of their isToPrint() returning false,
				// to push other dependent elements, but it was always like that; furthermore, such disappearing elements are pushed by 
				// other elements and also participate in white space collapse later on, so it is somewhat fair to also allow them 
				// to push others 
				element.moveDependantElements();

				if (element.isToPrint())
				{
					if (isOverflow)
					{
						if (element.isReprinted())
						{
							firstY = 0;
						}
						else if (!isFirstYFound)
						{
							firstY = element.getY();
						}
						isFirstYFound = true;
					}

					atLeastOneElementIsToPrint = true;
				}
			}
		}
		
		// normally, we should add firstY here, because it says below stretchHeight contains firstY; 
		// this initialization matters when band overflows with white space and no element is rendered on the next page;
		// but we don't add it because, historically, bands did not preserve the white space when overflowing, unlike frames for example,
		// which re-render at their design height even when overflowing with white space
		stretchHeight = getContainerHeight();// + firstY;

		// certain elements have stretched to their natural height, while others have been moved in the process;
		// we are now ready to calculate the stretch height of the current container, so that we can use that for
		// moving elements to bottom, before attempting to remove blank elements;
		// ATTENTION: this calculation needed to be in a separate ySortedElement loop as the above one, because
		// it needs to take into consideration the displacement of dependent elements made after each element prepare
		prepareStretchHeight(availableHeight, isOverflowAllowed);
		
		moveBandBottomElements();
		
		// removing blank elements and thus collapsing white space performs both
		// element height shrinking and relative Y repositioning, also changing the current
		// container stretch height
		removeBlankElements();

		// we are first stretching elements relative to group, because they do not need container height and might
		// actually cause the container to stretch further
		stretchElementsToElementGroup();

		// recalculating container stretch height to account for element group stretching, just before triggering
		// container related stretch
		prepareStretchHeight(availableHeight, isOverflowAllowed);

		moveBandBottomElements();

		// container based element stretching is the last one to be performed
		stretchElementsToContainer();
	}

	/**
	 *
	 */
	protected void prepareStretchHeight(
		int availableHeight,
		boolean isOverflowAllowed
		) throws JRException
	{
		int calculatedStretchHeight = calculateStretchHeight();
		
		if (calculatedStretchHeight > availableHeight + firstY)
		{
			currentOverflowWithWhiteSpace = true;
		}
		
		// stretchHeight includes firstY, which is subtracted in fillElements
		if (currentOverflowWithElements || currentOverflowWithWhiteSpace)
		{
			stretchHeight = availableHeight + firstY;
		}
		else
		{
			stretchHeight = calculatedStretchHeight;
		}

		willOverflowWithElements = currentOverflowWithElements && isOverflowAllowed;
		willOverflowWithWhiteSpace = currentOverflowWithWhiteSpace && isOverflowAllowed;
	}

	/**
	 *
	 */
	protected int calculateStretchHeight() throws JRException
	{
		int calculatedStretchHeight = -1;

		if (ySortedElements != null && ySortedElements.length > 0)
		{
			int containerHeight = getContainerHeight();

			for (JRFillElement element : ySortedElements)
			{
				if (element.isToPrint())
				{
					int spaceToBottom = containerHeight - (element.getY() + element.getHeight()) - element.getCollapsedHeightBelow();
					if (spaceToBottom < 0)
					{
						spaceToBottom = 0;
					}
					
					if (calculatedStretchHeight < element.getRelativeY() + element.getStretchHeight() + spaceToBottom)
					{
						calculatedStretchHeight = element.getRelativeY() + element.getStretchHeight() + spaceToBottom;
					}
				}
			}
		}
		
		if (calculatedStretchHeight < 0)
		{
			// there was no element printing; so trying to preserve stretchHeight
			calculatedStretchHeight = stretchHeight;
		}
		
		return calculatedStretchHeight;
	}

	/**
	 *
	 */
	public boolean isLegacyElementStretchEnabled()
	{
		return legacyElementStretchEnabled;
	}

	@Override
	public boolean isCurrentOverflow()
	{
		return currentOverflowWithElements || currentOverflowWithWhiteSpace;
	}

	@Override
	public boolean isCurrentOverflowAllowed()
	{
		return currentOverflowAllowed;
	}
	
	private int getElementFirstY(JRFillElement element)
	{
		int elemFirstY;
		if (!isOverflow || hasPrintWhenOverflowElement)
		{
			elemFirstY = 0;
		}
		else if (element.getY() >= firstY)
		{
			elemFirstY = firstY;
		}
		else
		{
			elemFirstY = element.getY();
		}
		return elemFirstY;
	}

	/**
	 * @deprecated To be removed.
	 */
	protected void _setStretchHeight(int stretchHeight)
	{
		if (stretchHeight > this.stretchHeight)
		{
			this.stretchHeight = stretchHeight;
		}
	}

	/**
	 * This method is deprecated and is going to be removed. 
	 * Not marked as deprecated to avoid deprecation warnings.
	 */
	@SuppressWarnings("deprecation")
	protected void stretchElements()
	{
		if (stretchElements != null && stretchElements.length > 0)
		{
			for(int i = 0; i < stretchElements.length; i++)
			{
				JRFillElement element = stretchElements[i];
				
				element._stretchElement(stretchHeight - getContainerHeight());//TODO subtract firstY?
				
				element._moveDependantElements();
			}
		}
		
		if (ySortedElements != null && ySortedElements.length > 0)
		{
			for(int i = 0; i < ySortedElements.length; i++)
			{
				JRFillElement element = ySortedElements[i];

				element.stretchHeightFinal();
			}
		}
	}

	protected void setStretchHeight(int stretchHeight)
	{
		if (isLegacyElementStretchEnabled())
		{
			_setStretchHeight(stretchHeight);
			return;
		}
		
		this.stretchHeight = stretchHeight;
	}

	/**
	 *
	 */
	protected void stretchElementsToElementGroup()
	{
		if (stretchElements != null && stretchElements.length > 0)
		{
			for (int i = 0; i < stretchElements.length; i++)
			{
				JRFillElement element = stretchElements[i];

				if (element.isToPrint())
				{
					boolean applied = element.stretchElementToElementGroup();
					
					if (applied)
					{
						element.moveDependantElements();
					}
				}
			}
		}
	}

	/**
	 *
	 */
	protected void stretchElementsToContainer()
	{
		if (stretchElements != null && stretchElements.length > 0)
		{
			int containerStretch = stretchHeight - getContainerHeight();
			
			for (int i = 0; i < stretchElements.length; i++)
			{
				JRFillElement element = stretchElements[i];

				if (element.isToPrint())
				{
					boolean applied = element.stretchElementToContainer(containerStretch);
					
					if (applied)
					{
						element.moveDependantElements();
					}
				}
			}
		}
	}

	
	protected int getStretchHeight()
	{
		return stretchHeight;
	}

	
	/**
	 *
	 */
	protected void moveBandBottomElements()
	{
		//if (!willOverflow)
		//{
			if (bandBottomElements != null && bandBottomElements.length > 0)
			{
				for (int i = 0; i < bandBottomElements.length; i++)
				{
					JRFillElement element = bandBottomElements[i];

					if (element.isToPrint())
					{
						// band bottom elements do not print if there will be an overflow
						if (currentOverflowWithElements || currentOverflowWithWhiteSpace)
						{
							currentOverflowWithElements = true;
						}
						
						element.setToPrint(!((currentOverflowWithElements || willOverflowWithWhiteSpace) && currentOverflowAllowed));// don't use willOverflow() method as it is overridden at least in bands
					}
					
					if (element.isToPrint())
					{
						element.setRelativeY(
							element.getY() + stretchHeight - getActualContainerHeight()
							);
					}
				}
			}
		//}
	}


	/**
	 * @deprecated To be removed.
	 */
	protected void _removeBlankElements()
	{
		JRElement[] remElems = removableElements;
		if (remElems != null && remElems.length > 0)
		{
			JRElement[] elems = ySortedElements;
			
			for(int i = 0; i < remElems.length; i++)
			{
				JRFillElement iElem = (JRFillElement)remElems[i];

				int blankHeight;
				if (iElem.isToPrint())
				{
					blankHeight = iElem.getHeight() - iElem.getStretchHeight();
				}
				else
				{
					blankHeight = iElem.getHeight();//FIXME subreports that stretch and then don't print, will not remove all space
				}
				
				if (
					blankHeight > 0 && 
					iElem.getRelativeY() + iElem.getStretchHeight() <= stretchHeight &&
					iElem.getRelativeY() >= firstY
					)
				{
					int blankY = iElem.getRelativeY() + iElem.getHeight() - blankHeight;
					boolean isToRemove = true;
					
					for(int j = 0; j < elems.length; j++)
					{
						JRFillElement jElem = (JRFillElement)elems[j];
						
						if (iElem != jElem && jElem.isToPrint())
						{
							int top = 
								Math.min(blankY, jElem.getRelativeY());
							int bottom = 
								Math.max(
									blankY + blankHeight, 
									jElem.getRelativeY() + jElem.getStretchHeight()
									);
							
							if (blankHeight + jElem.getStretchHeight() > bottom - top)
							{
								isToRemove = false;
								break;
							}
						}
					}
					
					if (isToRemove)
					{
						for(int j = 0; j < elems.length; j++)
						{
							JRFillElement jElem = (JRFillElement)elems[j];
							
							if (jElem.getRelativeY() >= blankY + blankHeight)
							{
								jElem.setRelativeY(jElem.getRelativeY() - blankHeight);
							}
						}
						
						stretchHeight = stretchHeight - blankHeight;
					}
				}
			}
		}
	}


	/**
	 *
	 */
	protected void removeBlankElements()
	{
		if (isLegacyElementStretchEnabled())
		{
			_removeBlankElements();
			return;
		}
		
		if (removableElements != null && removableElements.length > 0)
		{
			for (JRFillElement remElem : removableElements)
			{
				int blankHeight;
				if (remElem.isToPrint())
				{
					blankHeight = remElem.getHeight() - remElem.getStretchHeight();
				}
				else
				{
					blankHeight = remElem.getHeight();//FIXME subreports that stretch and then don't print, will not remove all space
				}
				
				if (
					blankHeight > 0 && 
					remElem.getRelativeY() + remElem.getStretchHeight() <= stretchHeight &&
					remElem.getRelativeY() >= firstY
					)
				{
					int blankY = remElem.getRelativeY() + remElem.getHeight() - blankHeight;
					boolean isToRemove = true;
					
					for (JRFillElement jElem : ySortedElements)
					{
						if (remElem != jElem && jElem.isToPrint())
						{
							int top = 
								Math.min(blankY, jElem.getRelativeY());
							int bottom = 
								Math.max(
									blankY + blankHeight, 
									jElem.getRelativeY() + jElem.getStretchHeight()
									);
							
							if (blankHeight + jElem.getStretchHeight() > bottom - top)
							{
								isToRemove = false;
								break;
							}
						}
					}
					
					if (isToRemove)
					{
						for (JRFillElement jElem : ySortedElements)
						{
							if (jElem.getRelativeY() + jElem.getStretchHeight() <= blankY)
							{
								jElem.setCollapsedHeightBelow(jElem.getCollapsedHeightBelow() + blankHeight);
							}

							if (jElem.getRelativeY() >= blankY + blankHeight)
							{
								jElem.setCollapsedHeightAbove(jElem.getCollapsedHeightAbove() + blankHeight);
								jElem.setRelativeY(jElem.getRelativeY() - blankHeight);
							}
						}
						
						stretchHeight = stretchHeight - blankHeight;
					}
				}
			}
		}
	}


	/**
	 * Fills the elements from this container into a print element container.
	 * 
	 * @param printContainer the print element container
	 * @throws JRException
	 */
	public void fillElements(JRPrintElementContainer printContainer) throws JRException
	{
		//int maxStretch = 0;
		//int stretch = 0;
		int maxWidth = 0;
		JRElement[] allElements = getElements();
		if (allElements != null && allElements.length > 0)
		{
			for(int i = 0; i < allElements.length; i++)
			{
				JRFillElement element = (JRFillElement)allElements[i];
				
				element.setRelativeY(element.getRelativeY() - firstY);

				if (element.getRelativeY() + element.getStretchHeight() > stretchHeight - firstY)
				{
					element.setToPrint(false);
				}
				
				element.setAlreadyPrinted(element.isToPrint() || element.isAlreadyPrinted());
				
				if (element.isToPrint())
				{
					JRPrintElement printElement = element.fill();
					//printElement.setY(printElement.getY() - firstY);

					if (printElement != null)
					{
						//FIXME not all elements affect height
						//stretch = printElement.getY() + firstY + printElement.getHeight() - element.getY() - element.getHeight();
						//if (stretch > maxStretch)
						//{
						//	maxStretch = stretch;
						//}
						printContainer.addElement(printElement);
						if (printElement.getX() + printElement.getWidth() > maxWidth)
						{
							maxWidth = printElement.getX() + printElement.getWidth();
						}
					}
					
					if (element instanceof JRFillSubreport)
					{
						JRFillSubreport subreport = (JRFillSubreport)element;
						
						List<JRStyle> styles = subreport.subreportFiller.getJasperPrint().getStylesList();
						for(int j = 0; j < styles.size(); j++)
						{
							filler.addPrintStyle(styles.get(j));
						}
						
						List<JROrigin> origins = subreport.subreportFiller.getJasperPrint().getOriginsList();
						for(int j = 0; j < origins.size(); j++)
						{
							filler.getJasperPrint().addOrigin(origins.get(j));
						}
						
						Collection<JRPrintElement> printElements = subreport.getPrintElements();
						addSubElements(printContainer, element, printElements);
						if (subreport.getX() + subreport.getPrintContentsWidth() > maxWidth)
						{
							maxWidth = subreport.getX() + subreport.getPrintContentsWidth();
						}
						
						subreport.subreportPageFilled();
					}
					
					// crosstabs do not return a fill() element
					if (element instanceof JRFillCrosstab)
					{
						JRFillCrosstab crosstab = (JRFillCrosstab) element;
						List<? extends JRPrintElement> printElements = crosstab.getPrintElements();
						addSubElements(printContainer, element, printElements);
						if (crosstab.getX() + crosstab.getPrintElementsWidth() > maxWidth)
						{
							maxWidth = crosstab.getX() + crosstab.getPrintElementsWidth();
						}
					}
				}
			}
		}
		
		//printBand.setHeight(getHeight() + maxStretch - firstY);
		printContainer.setHeight(stretchHeight - firstY);
		printContainer.setContentsWidth(maxWidth);
	}


	protected void addSubElements(JRPrintElementContainer printContainer, JRFillElement element, 
			Collection<? extends JRPrintElement> printElements)
	{
		if (printContainer instanceof OffsetElementsContainer)
		{
			// adding the subelements as whole lists to bands so that we don't need
			// another virtualized list at print band level
			((OffsetElementsContainer) printContainer).addOffsetElements(printElements, 
					element.getX(), element.getRelativeY());
		}
		else
		{
			if (printElements != null && printElements.size() > 0)
			{
				for(Iterator<? extends JRPrintElement> it = printElements.iterator(); it.hasNext();)
				{
					JRPrintElement printElement =it.next();
					printElement.setX(element.getX() + printElement.getX());
					printElement.setY(element.getRelativeY() + printElement.getY());
					printContainer.addElement(printElement);
				}
			}
		}
	}

	
	/**
	 *
	 */
	protected void rewind() throws JRException
	{
		if (ySortedElements != null && ySortedElements.length > 0)
		{
			for(int i = 0; i < ySortedElements.length; i++)
			{
				JRFillElement element = ySortedElements[i];

				element.rewind();

				element.setAlreadyPrinted(false);
			}
		}
		
		willOverflowWithElements = false;
		willOverflowWithWhiteSpace = false;
	}
	
	protected int getFirstY()
	{
		return firstY;
	}

	
	/**
	 * Returns the actual height of the element container.
	 * Some element containers such as frames have a larger calculated container height, resulting from content being placed beyond container declared height.
	 * 
	 * @return the height of the element container
	 */
	protected abstract int getActualContainerHeight();


	/**
	 * Returns the height of the element container.
	 * 
	 * @return the height of the element container
	 */
	protected abstract int getContainerHeight();


	/**
	 * Find all styles containing conditional styles which are referenced by elements in this band.
	 */
	protected void initConditionalStyles()
	{
		filler.addDefaultStyleListener(new JRBaseFiller.DefaultStyleListener(){
			@Override
			public void defaultStyleSet(JRStyle style)
			{
				collectConditionalStyle(style);
			}
		});
		
		for (int i = 0; i < deepElements.length; i++)
		{
			JRStyle style = deepElements[i].initStyle;
			collectConditionalStyle(style);
		}
		
		if (deepElements.length > 0)
		{
			for(int i = 0; i < deepElements.length; i++)
			{
				deepElements[i].setConditionalStylesContainer(this);
			}
		}
	}

	protected void collectConditionalStyle(JRStyle style)
	{
		if (style != null)// && style.getConditionalStyles() != null)
		{
			stylesToEvaluate.add(style);
		}
	}


	protected void evaluateConditionalStyles(byte evaluation) throws JRException
	{
		for (Iterator<JRStyle> it = stylesToEvaluate.iterator(); it.hasNext();) 
		{
			evaluateConditionalStyle(it.next(), evaluation);
		}
	}


	protected JRStyle evaluateConditionalStyle(JRStyle initialStyle, byte evaluation) throws JRException
	{
		JRStyle consolidatedStyle = initialStyle;

		StringBuilder code = new StringBuilder();
		List<JRStyle> condStylesToApply = new ArrayList<>();
		
		boolean anyTrue = buildConsolidatedStyle(initialStyle, evaluation, code, condStylesToApply);
		
		if (anyTrue)
		{
			String consolidatedStyleName = initialStyle.getName() + "|" + code.toString();
			consolidatedStyle = filler.getJasperPrint().getStylesMap().get(consolidatedStyleName);
			if (consolidatedStyle == null)
			{
				JRBaseStyle style = new JRBaseStyle(initialStyle.getDefaultStyleProvider(), consolidatedStyleName);
				for (int j = condStylesToApply.size() - 1; j >= 0; j--)
				{
					StyleUtil.appendStyle(style, condStylesToApply.get(j));
				}

				// deduplicate to previously created identical instances
				style = filler.fillContext.deduplicate(style);
				filler.addPrintStyle(style);
				
				consolidatedStyle = style;
			}
		}

		evaluatedStyles.put(initialStyle, consolidatedStyle);
		
		return consolidatedStyle;
	}


	protected boolean buildConsolidatedStyle(JRStyle style, byte evaluation, StringBuilder code, List<JRStyle> condStylesToApply) throws JRException
	{
		boolean anyTrue = false;
		
		JRConditionalStyle[] conditionalStyles = style.getConditionalStyles();
		if (conditionalStyles != null && conditionalStyles.length > 0)
		{
			for (int j = 0; j < conditionalStyles.length; j++) 
			{
				JRConditionalStyle conditionalStyle = conditionalStyles[j];
				Boolean expressionValue = 
					(Boolean) expressionEvaluator.evaluate(
						conditionalStyle.getConditionExpression(),
						evaluation
						);
				
				boolean condition;
				if (expressionValue == null)
				{
					condition = false;
				}
				else
				{
					condition = expressionValue;
				}
				
				code.append(condition ? '1' : '0');
				anyTrue = anyTrue | condition;

				if (condition)
				{
					condStylesToApply.add(conditionalStyle);
				}
			}
		}

		condStylesToApply.add(style);
		
		if (style.getStyle() != null)
		{
			anyTrue = anyTrue | buildConsolidatedStyle(style.getStyle(), evaluation, code, condStylesToApply);
		}
		return anyTrue;
	}


	public JRStyle getEvaluatedConditionalStyle(JRStyle parentStyle)
	{
		return evaluatedStyles.get(parentStyle);
	}
	
	protected final void setElementOriginProvider(JROriginProvider originProvider)
	{
		if (originProvider != null)
		{
			for (int i = 0; i < deepElements.length; i++)
			{
				deepElements[i].setOriginProvider(originProvider);
			}
		}
	}
}
