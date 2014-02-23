<html>
PostfixPrefix
=============

Java Class for converting postfix, infix, and prefix expressions, and evaluating them.

<p>
EXAMPLE:
```
Converter c = new Converter("1+2*3");

c.getInfix();           // "1+2*3"

c.getPostfix();         // "123*+"

c.evaluate();           // "7"
```
</html>
