package com.vl.expressionparser.operation;

import java.util.Map;

public class Sinus implements Operation {
    final private Operation operation;

    public Sinus(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double get(Map<String, Double> variables) {
        return Math.sin(operation.get(variables));
    }
}