package com.vl.expressionparser;

import com.vl.expressionparser.operation.*;
import com.vl.expressionparser.operation.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * The Library provides parsing and calculation of String expressions.
 *
 * @author Vl
 * @version 0.1 BETA
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

