package com.vl.expressionparser;


import java.util.*;

/**
 * The Library provides parsing and calculation of String expressions.
 *
 * @author Vl
 * @version 0.1
 * @since 06.11.2022
 */
final public class Parser {
    /**
     * Factory method for transformation from String expression to executable Operation.
     * You must pass all variables to {@link Operation#get(Map)} as map of certain values. If there is no variables in expression, you can pass {@code null}.
     * Behaviour is undefined for some malformed expressions.
     *
     * @param expression Expression may contain next operators: cos(), sin(), log(), sqr(), sqrt() and +,-,*,/,^.
     *                   Yoy also can use variables like "x", "var" etc. Floating point numbers must be presented as {@code 0.5}.
     *                   Expression can contain line breaks and spaces.
     *                   If you need to use math constants you can pass it as variable.
     * @return Operation - object represents function
     * @throws MalformedExpressionException if expression is incorrect
     */
    public static Operation decompose(final String expression) throws MalformedExpressionException {
        return decompose(OperationChain.read(expression.trim().replace("\n", "").replace(",", ".")));
    }

    private static Operation decompose(List<OperationChain> operations) {
        if (operations.size() == 1)
            return operations.get(0).getValue();
        if (operations.size() == 0)
            return null;
        ListIterator<OperationChain> iterator = operations.listIterator();
        int
                operatorPos = -1,
                operatorPriority = 0;
        while (iterator.hasNext()) {
            final OperationChain chain = iterator.next();
            if (chain.getOperator() != null && (chain.getPriority() <= operatorPriority || operatorPos == -1)) {
                operatorPos = iterator.previousIndex();
                operatorPriority = chain.getPriority();
            }
        }
        Operation
                operation1 = decompose(operations.subList(0, operatorPos)),
                operation2 = decompose(operations.subList(operatorPos + 1, operations.size()));
        return switch (operations.get(operatorPos).getOperator()) {
            case "+" -> new Addition(operation1, operation2);
            case "-" -> new Subtraction(operation1, operation2);
            case "*" -> new Product(operation1, operation2);
            case "/" -> new Quotient(operation1, operation2);
            case "^" -> new Power(operation1, operation2);
            case "log" -> new Logarithm(operation2);
            case "sqr" -> new Power(operation2, new Value(2.0));
            case "sqrt" -> new Power(operation2, new Value(0.5));
            case "cos" -> new Cosinus(operation2);
            case "sin" -> new Sinus(operation2);
            default -> throw new RuntimeException("Unknown operation");
        };
    }

    private Parser() {}
}

class Sinus implements Operation {
    final private Operation operation;

    public Sinus(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double get(Map<String, Double> variables) {
        return Math.sin(operation.get(variables));
    }
}

class Cosinus implements Operation {
    final private Operation operation;

    public Cosinus(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double get(Map<String, Double> variables) {
        return Math.cos(operation.get(variables));
    }
}

class Logarithm implements Operation {
    private final Operation operation;

    public Logarithm(Operation operation) {
        this.operation = operation;
    }

    @Override
    public double get(Map<String, Double> variables) {
        return Math.log(operation.get(variables));
    }
}

abstract class PrimitiveOperation implements Operation {
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

class Addition extends PrimitiveOperation {
    public Addition(Operation... operations) {
        super(operations);
    }

    @Override
    public double get(double value1, double value2) {
        return value1 + value2;
    }
}

class Subtraction extends PrimitiveOperation {
    public Subtraction(Operation... operations) {
        super(operations);
    }

    @Override
    public double get(double value1, double value2) {
        return value1 - value2;
    }
}

class Product extends PrimitiveOperation {
    public Product(Operation... operations) {
        super(operations);
    }

    @Override
    public double get(double value1, double value2) {
        return value1 * value2;
    }
}

class Quotient extends PrimitiveOperation {
    public Quotient(Operation... operations) {
        super(operations);
    }

    @Override
    public double get(double value1, double value2) {
        return value1 / value2;
    }
}

class Power extends PrimitiveOperation {
    public Power(Operation value, Operation power) {
        super(value, power);
    }

    @Override
    public double get(double value1, double value2) {
        return Math.pow(value1, value2);
    }
}
