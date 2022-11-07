package com.vl.expressionparser.operation;

import java.util.Map;

public interface Operation {
    /**
     * @param variables Pass all variables as {@link Map} of their values. You can pass {@code null} if there is no variables in expression
     * @return double - expression value for certain variables values
     */
    double get(Map<String, Double> variables);
}
