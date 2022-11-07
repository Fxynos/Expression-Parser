package com.vl.expressionparser.operation;

public class Product extends PrimitiveOperation {
    public Product(Operation... operations) {
        super(operations);
    }

    @Override
    public double get(double value1, double value2) {
        return value1 * value2;
    }
}
