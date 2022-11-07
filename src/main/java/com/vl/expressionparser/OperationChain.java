package com.vl.expressionparser;

import java.util.*;

class OperationChain {
    final private static Map<String, Integer> OPERATORS_PRIORITIES;
    final private static int PARENTHESES_PRIORITY;

    static {
        int priority = 0;
        OPERATORS_PRIORITIES = new HashMap<>();
        OPERATORS_PRIORITIES.put("+", priority);
        OPERATORS_PRIORITIES.put("-", priority);
        OPERATORS_PRIORITIES.put("*", ++priority);
        OPERATORS_PRIORITIES.put("/", priority);
        OPERATORS_PRIORITIES.put("^", ++priority);
        PARENTHESES_PRIORITY = ++priority;
    }

    final private String operator;
    final private Operation value;
    final private int priority;

    public OperationChain(String operator, int priority) {
        this.operator = operator.toLowerCase(Locale.ROOT);
        this.value = null;
        this.priority = priority;
    }

    public OperationChain(Operation value) {
        this.operator = null;
        this.value = value;
        this.priority = -1;
    }

    public int getPriority() {
        return priority;
    }

    public String getOperator() {
        return operator;
    }

    public Operation getValue() {
        return value;
    }

    public static List<OperationChain> read(final String expression) throws MalformedExpressionException {
        return read(expression, 0);
    }

    private static List<OperationChain> read(final String expression, int priority) throws MalformedExpressionException {
        LinkedList<OperationChain> chains = new LinkedList<>();
        String literal = "";
        boolean negative = false;
        boolean parenthesesWereAsLiteral = false;
        for (int i = 0; i < expression.length(); i++) {
            final String c = expression.substring(i, i + 1);
            if (c.equals("(")) {
                final int closingAfter = expression.substring(i).indexOf(")");
                if (closingAfter == -1)
                    throw new MalformedExpressionException("\")\" expected");
                if (closingAfter == 1)
                    throw new MalformedExpressionException("Empty parentheses");
                final String chunk = expression.substring(i + 1, i + closingAfter);
                if (literal.length() == 0)
                    chains.addAll(read(chunk, PARENTHESES_PRIORITY + priority));
                else {
                    chains.add(new OperationChain(literal, PARENTHESES_PRIORITY + priority));
                    chains.addAll(read(chunk, PARENTHESES_PRIORITY + priority + 1));
                    literal = "";
                }
                i += closingAfter;
                parenthesesWereAsLiteral = true;
            } else if (OPERATORS_PRIORITIES.containsKey(c))
                if (literal.length() == 0 && !parenthesesWereAsLiteral)
                    if (c.equals("-"))
                        negative = !negative;
                    else
                        throw new MalformedExpressionException("Operating on empty");
                else {
                    if (parenthesesWereAsLiteral)
                        parenthesesWereAsLiteral = false;
                    else {
                        chains.add(new OperationChain(parseLiteral(literal, negative)));
                        literal = "";
                        negative = false;
                    }
                    chains.add(new OperationChain(c, OPERATORS_PRIORITIES.get(c) + priority));
                }
            else if (!c.equals(" "))
                literal = literal.concat(c);
        }
        if (literal.length() > 0)
            chains.add(new OperationChain(parseLiteral(literal, negative)));
        return chains;
    }

    private static Operation parseLiteral(String literal, boolean negative) {
        if (literal.matches("[0-9-.]*"))
            return new Value(Double.parseDouble(literal));
        else
            return new Variable(literal, negative);
    }
}

