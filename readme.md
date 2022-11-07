## Example of Usage
~~~
Operation func = Parser.decompose("7 - (5 * x) - sin(PI/2)"); // throws MalformedExpressionException
Map<String, Double> vars = new HashMap<>();
vars.put("x", 2.0);
vars.put("PI", Math.PI);
double result = func.get(vars);
~~~
## Description
### Classes
- <code>Parser</code> class provides transformation from <code>String</code> expression to <code>Operation</code> instance via method <code>static Operation decompose(String expression) throws MalformedExpressionException</code>.
<code>MalformedExpressionException</code> occurs if expression couldn't be parsed. For some malformed expression behaviour is undefined, it may lead to runtime exceptions.
- <code>Operation</code> class allow to get value of expression for certain values of used in it variables via method <code>double get(Map<String, Double> vars)</code>. You can pass null, if there's no variables in expression, 
otherwise you must put all used variables.
### Expressions
Next operators can be used in expressions:
- "+", "-", "*", "/", "^"
- log(), cos(), sin(), sqr(), sqrt()