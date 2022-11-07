package com.vl.expressionparser.operation;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Map;

public abstract class PrimitiveOperation implements Operation {
    private final ArrayDeque<Operation> queue;

    public PrimitiveOperation(Operation... queue) {
        this.queue = new ArrayDeque<>(Arrays.asList(queue));
    }

    @Override
    public double get(Map<String, Double> variables) {
        double value = 0;
        boolean first = true;
        for (Operation e : queue) {
            if (first) {
                first = false;
                value = e.get(variables);
            } else
                value = get(value, e.get(variables));
        }
        return value;
    }

    public abstract double get(double value1, double value2);
}
