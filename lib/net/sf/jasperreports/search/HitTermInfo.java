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
package net.sf.jasperreports.search;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRRuntimeException;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class HitTermInfo implements JRCloneable, Serializable {
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private int position;
	private int start;
	private int end;
	private String value;
	private String pageNo;

	public HitTermInfo(int position, int start, int end, String value) {
		this.position = position;
		this.start = start;
		this.end = end;
		this.value = value;
	}

	public int getPosition() {
		return position;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getEnd() {
		return end;
	}

	public String getValue() {
		return value;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageNo() {
		return pageNo;
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// never
			throw new JRRuntimeException(e);
		}
	}
}
