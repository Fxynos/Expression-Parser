package com.vl.expressionparser.operation;

public class Quotient extends PrimitiveOperation {
    public Quotient(Operation... operations) {
        super(operations);
    }

    @Override
    public double get(double value1, double value2) {
        return value1 / value2;
    }
}
