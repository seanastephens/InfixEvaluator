package core;

import java.util.List;
import java.util.Stack;

/**
 * Provides a static method for evaluating postfix ordered Token Lists.
 * 
 * <p>
 * DEFICIENCIES: Additional testing is needed.
 * 
 * @author Sean Stephens, seanastephens@email.arizona.edu
 * 
 */
public class PostfixEvaluator {

	/**
	 * Attempts to return a numerical value equivalent to the given, post-fix
	 * expression order List of Tokens.
	 * 
	 * <p>
	 * --> USES INTEGER MATH <--
	 * 
	 * @param postfixExpression
	 *            - List of Tokens representing a postfix Expression.
	 * @return integer equivalent to given postfix expression Token List.
	 */
	/*
	 * Follows a standard algorithm for evaluating postfix expressions.
	 * 
	 * Proceed left to right throught the postfix expression. If the current
	 * character:
	 * 
	 * 1) is an operand: push it onto the stack.
	 * 
	 * 2) is an operator: pop the top two elements from the stack, apply the
	 * operator to them, and push the result on the stack.
	 * 
	 * Finally, return the value in the stack.
	 */
	public static int evaluate(List<Token> postfixExpression) {

		Stack<Token> stack = new Stack<Token>();

		for (int i = 0; i < postfixExpression.size(); i++) {
			Token current = postfixExpression.get(i);

			/*
			 * If the current character is an operand: push it onto the stack.
			 */
			if (current.isOperand()) {
				stack.push(new Token(current.toString()));

				/*
				 * Otherwise, the current character is an operator: pop the top
				 * two elements from the stack, apply the operator to them, and
				 * push the result on the stack.
				 */
			} else {
				Token secondOperand = stack.pop();
				Token firstOperand = stack.pop();

				int aInt = Integer.valueOf(secondOperand.toString());
				int bInt = Integer.valueOf(firstOperand.toString());
				int result = 0;

				if (current.toString().equals("+")) {
					result = bInt + aInt;
				} else if (current.toString().equals("*")) {
					result = bInt * aInt;
				} else {
					result = bInt / aInt;
				}

				stack.push(new Token(String.valueOf(result)));
			}
		}
		return Integer.parseInt(stack.pop().toString());
	}
}
