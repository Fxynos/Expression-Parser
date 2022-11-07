package com.vl.expressionparser.operation;

import java.util.Map;

public class Cosinus implements Operation {
    final private Operation operation;

    public Cosinus(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double get(Map<String, Double> variables) {
        return Math.cos(operation.get(variables));
    }
}
