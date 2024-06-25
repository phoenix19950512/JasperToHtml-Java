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
package net.sf.jasperreports.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.ReportContext;
import net.sf.jasperreports.web.servlets.JasperPrintAccessor;



/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class WebReportContext implements ReportContext
{
	/**
	 *
	 */
	private static final String SESSION_ATTRIBUTE_REPORT_CONTEXT_ID_PREFIX = "net.sf.jasperreports.web.report.context_";
	public static final String REQUEST_PARAMETER_REPORT_CONTEXT_ID = "jr_ctxid";

	/**
	 * @deprecated Replaced by {@link JasperPrintAccessor#REPORT_CONTEXT_PARAMETER_JASPER_PRINT_ACCESSOR}.
	 */
	public static final String REPORT_CONTEXT_PARAMETER_JASPER_PRINT_ACCESSOR = JasperPrintAccessor.REPORT_CONTEXT_PARAMETER_JASPER_PRINT_ACCESSOR;
	
	public static final String APPLICATION_CONTEXT_PATH = "net.sf.jasperreports.web.app.context.path";
	
	/**
	 *
	 */
	//private ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<HttpServletRequest>();//FIXMEJIVE
	private Map<String, String> requestParameters = new HashMap<>();
	private Map<String, Object> parameterValues;
	private String id;
	
	/**
	 *
	 */
	public static final WebReportContext getInstance(HttpServletRequest request)
	{
		return getInstance(request, true);
	}

	public static final WebReportContext getInstance(HttpServletRequest request, String reportContextId) {
		return getInstance(request, reportContextId, true);
	}

	public static final WebReportContext getInstance(HttpServletRequest request, boolean create) {
		return getInstance(request, null, create);
	}

	/**
	 *
	 */
	public static final WebReportContext getInstance(HttpServletRequest request, String reportContextId, boolean create)
	{
		WebReportContext webReportContext = null;

		if (reportContextId == null) {
			reportContextId = request.getParameter(REQUEST_PARAMETER_REPORT_CONTEXT_ID);
		}

		if (reportContextId != null)
		{
			webReportContext = (WebReportContext)request.getSession().getAttribute(getSessionAttributeName(reportContextId));
		}

		if (webReportContext == null && create)
		{
			webReportContext = new WebReportContext();
			request.getSession().setAttribute(webReportContext.getSessionAttributeName(), webReportContext); //FIXMEJIVE use FIFO accessor
		}
		
		if (webReportContext != null)
		{
			//webReportContext.setRequest(request);
			webReportContext.setParameterValue(APPLICATION_CONTEXT_PATH, request.getContextPath());
			webReportContext.setParameterValue(JRParameter.REPORT_CONTEXT, webReportContext);
		}
		
		return webReportContext;
	}

	/**
	 *
	 */
	private WebReportContext()
	{
		parameterValues = new HashMap<>();
		id = UUID.randomUUID().toString();
//		parameterValues.put(JRParameter.REPORT_CONTEXT, this);
	}

	@Override
	public String getId()
	{
		return id;
	}

	/**
	 *
	 *
	public void setRequest(HttpServletRequest request)
	{
		//threadLocalRequest.set(request);
		requestParameters.clear();
		for(@SuppressWarnings("unchecked") Enumeration<String> params = request.getParameterNames(); 
				params.hasMoreElements(); )
		{
			String param = params.nextElement();
			String value = request.getParameter(param);// do getValues here?
			requestParameters.put(param, value);
		}
	}

	/**
	 *
	 */
	public String getSessionAttributeName()
	{
		return getSessionAttributeName(getId());
	}

	@Override
	public Object getParameterValue(String parameterName)
	{
		String requestParameterValue = requestParameters.get(parameterName);
		if (requestParameterValue != null)
		{
			return requestParameterValue;
		}
		
		return parameterValues.get(parameterName);
	}

	@Override
	public boolean containsParameter(String parameterName)
	{
		String requestParameterValue = requestParameters.get(parameterName);
		if (requestParameterValue != null)
		{
			return true;
		}
		
		return parameterValues.containsKey(parameterName);
	}

	@Override
	public void setParameterValue(String parameterName, Object value)
	{
		parameterValues.put(parameterName, value);
	}

	/**
	 *
	 */
	public void setParameterValues(Map<String, Object> newValues)
	{
		parameterValues.putAll(newValues);
	}

	@Override
	public Map<String, Object> getParameterValues()
	{
		return parameterValues;
	}

	/**
	 *
	 */
	private static final String getSessionAttributeName(String id)
	{
		return SESSION_ATTRIBUTE_REPORT_CONTEXT_ID_PREFIX + id;
	}

	@Override
	public Object removeParameterValue(String parameterName)
	{
		return parameterValues.remove(parameterName);
	}

	@Override
	public void clearParameterValues()
	{
		parameterValues.clear();
	}
}
