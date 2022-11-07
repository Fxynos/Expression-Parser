package com.vl.expressionparser;

import java.util.Map;

class Value implements Operation {
    final private double value;

    public Value(double value) {
        this.value = value;
    }

    @Override
    public double get(Map<String, Double> variables) {
        return value;
    }
}
