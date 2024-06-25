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
package net.sf.jasperreports.engine.json.expression.filter;

import net.sf.jasperreports.engine.json.JRJsonNode;
import net.sf.jasperreports.engine.json.expression.filter.evaluation.FilterExpressionEvaluatorVisitor;

/**
 * @author Narcis Marcu (narcism@users.sourceforge.net)
 */
public class NotFilterExpression implements FilterExpression {
    private FilterExpression filterExpression;

    public NotFilterExpression(FilterExpression filterExpression) {
        this.filterExpression = filterExpression;
    }

    @Override
    public boolean evaluate(JRJsonNode jsonNode, FilterExpressionEvaluatorVisitor evaluator) {
        return evaluator.evaluateNotFilter(this, jsonNode);
    }

    public FilterExpression getFilterExpression() {
        return filterExpression;
    }

    @Override
    public String toString() {
        return "not( " + filterExpression + " )";
    }
}
