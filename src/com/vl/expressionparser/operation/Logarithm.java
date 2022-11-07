package com.vl.expressionparser.operation;

import java.util.Map;

public class Logarithm implements Operation {
    private final Operation operation;

    public Logarithm(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double get(Map<String, Double> variables) {
        return Math.log(operation.get(variables));
    }
}
