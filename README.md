<html>
PostfixPrefix
=============

This project contains static java classes for converting infix expressions to postfix expressions, and for evaluating postifx expressions.

<p>
EXAMPLE:
```
String infix = "1+2*3";

List<Token> postfix = InfixToPostfix.convert(infix);   // [<1>, <2>, <3>, <*>, <+>]

PostfixEvaluator.evaluate(postfix);                    // "7"

```
</html>
