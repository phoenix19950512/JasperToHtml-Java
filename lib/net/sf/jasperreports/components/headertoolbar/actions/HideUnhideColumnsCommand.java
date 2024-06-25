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
package net.sf.jasperreports.components.headertoolbar.actions;

import java.util.List;

import net.sf.jasperreports.components.table.BaseColumn;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.components.table.util.TableUtil;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.web.commands.Command;
import net.sf.jasperreports.web.commands.CommandException;
import net.sf.jasperreports.web.commands.CommandStack;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class HideUnhideColumnsCommand implements Command {
	
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	private StandardTable table;
	private HideUnhideColumnData columnData;
	private CommandStack individualResizeCommandStack;
	
	public HideUnhideColumnsCommand(StandardTable table, HideUnhideColumnData columnData) {
		this.table = table;
		this.columnData = columnData;
		individualResizeCommandStack = new CommandStack();
	}


	@Override
	public void execute() throws CommandException {
		List<BaseColumn> tableColumns = TableUtil.getAllColumns(table);
		int[] columnIndexes = columnData.getColumnIndexes();
		
		if (columnIndexes != null) {
			for(int colIndex: columnIndexes){
				individualResizeCommandStack.execute(new HideUnhideColumnCommand((StandardColumn)tableColumns.get(colIndex), columnData.getHide()));
			}
		}
	}


	@Override
	public void undo() {
		individualResizeCommandStack.undoAll();
	}


	@Override
	public void redo() {
		individualResizeCommandStack.redoAll();
	}

}
