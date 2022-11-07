package com.vl.expressionparser;

import java.util.Map;

class Variable implements Operation {
    private final String variable;
    private final boolean negative;

    public Variable(String variable) {
        this(variable, false);
    }

    public Variable(String variable, boolean negative) {
        this.variable = variable;
        this.negative = negative;
    }

    @Override
    public double get(Map<String, Double> variables) {
        final Double value = variables.get(variable);
        if (value == null)
            throw new RuntimeException("Unknown variable: ".concat(variable));
        return negative ? -value : value;
    }
}
