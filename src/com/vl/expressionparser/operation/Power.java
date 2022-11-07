package com.vl.expressionparser.operation;

public class Power extends PrimitiveOperation {
    public Power(Operation value, Operation power) {
        super(value, power);
    }

    @Override
    public double get(double value1, double value2) {
        return Math.pow(value1, value2);
    }
}
