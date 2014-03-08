<html>
PostfixPrefix
=============

This project contains static java classes for converting infix expressions to postfix expressions, and for evaluation postifx expressions.

<p>
EXAMPLE:
```
String infix = "1+2*3";

String postfix = InfixToPostfix.convert(infix);   // "123*+"

PostfixEvaluator.evaluate(postfix);               // "7"
```
</html>
