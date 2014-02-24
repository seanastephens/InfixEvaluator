import java.util.Stack;

/**
 * class provides methods for converting infix to postfix notation and for
 * evaluating postfix expressions.
 * 
 * <p>
 * DEFICIENCIES: class cannot evaluate expressions with constants or negative
 * numbers. Additional testing is needed. class does not support setting the
 * infix expression after construction. class cannot convert from postfix to
 * infix. class does not support prefix notation.
 * 
 * @author Sean Stephens - seanastephens@email.arizona.edu
 * 
 */
public class Converter {

	/**
	 * String constant consisting of all the characters that will be parsed to
	 * represent variables.
	 */
	public static final String VARIABLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz";

	/**
	 * String constant consisting of all the characters that will be parsed to
	 * represent numbers.
	 */
	public static final String NUMBERS = "0123456789";

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * operands.
	 */
	public static final String ALL_OPERANDS = NUMBERS + VARIABLES;

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * 1st precedence operators.
	 */
	public static final String HIGH_PREF_OPS = "*/";

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * 2nd precedence operators.
	 */
	public static final String LOW_PREF_OPS = "+-";

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * operators.
	 */
	public static final String ALL_OPERATORS = LOW_PREF_OPS + HIGH_PREF_OPS;

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * open-parentheses-like structures.
	 */
	public static final String OPEN_CHARS = "(";

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * close-parentheses-like structures.
	 */
	public static final String CLOSE_CHARS = ")";

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * parentheses-like structures.
	 */
	public static final String OPEN_CLOSE_CHARS = OPEN_CHARS + CLOSE_CHARS;

	/**
	 * String constant consisting of all the characters that will be parsed as
	 * operators and structure tokens.
	 */
	public static final String ALL_NON_OPERANDS = OPEN_CLOSE_CHARS
			+ ALL_OPERATORS;

	/**
	 * String constant consisting of all the characters that will not be
	 * filtered out of an expression passed to a Converter.
	 */
	public static final String ALL_VALID_CHARS = ALL_OPERANDS
			+ ALL_NON_OPERANDS;

	/**
	 * Private instance String used to represent the passed infix String.
	 */
	private String infix;

	/**
	 * Private instance String used to represent the generated postfix String.
	 */
	private String postfix;

	/*
	 * TODO: implement prefix operations
	 * 
	 * private String prefix;
	 */

	/**
	 * Creates a Converter with the given infix expression.
	 * 
	 * @param infix
	 *            - infix expression to do operations on.
	 */
	public Converter(String infix) {
		this.infix = infix;
		cleanInfix();
		insertStars();
		generatePostfix();
	}

	/*
	 * Private helper method that removes invalid characters from the infix
	 * expression.
	 */
	private void cleanInfix() {
		String cleanedInfix = "";

		/*
		 * If the character at index i in infix is a valid character, keep it.
		 */
		for (int i = 0; i < infix.length(); i++) {

			if (ALL_VALID_CHARS.contains(infix.substring(i, i + 1))) {

				cleanedInfix += infix.substring(i, i + 1);

			}
		}
		infix = cleanedInfix;
	}

	/*
	 * Private helper method that adds '*' between adjacent numbers or constants
	 * in the infix expression.
	 */
	private void insertStars() {

		for (int i = 0; i < infix.length() - 1; i++) {

			if (ALL_OPERANDS.contains(infix.substring(i, i + 1))
					&& ALL_OPERANDS.contains(infix.substring(i + 1, i + 2))) {

				infix = infix.substring(0, i + 1) + "*"
						+ infix.substring(i + 1, infix.length());
			}
		}
	}

	/*
	 * Private helper method that converts the instance infix expression into a
	 * postfix expression.
	 * 
	 * <p> Derived from http://csis.pace.edu/~wolf/CS122/infix-postfix.htm. The
	 * general procedure for converting an infix expression to a postfix
	 * expression consists of using a stack and the following rules:
	 * 
	 * <p> Proceed left to right through the infix expression. If the next
	 * character: <p> 1) is an operand: add it to the end of the postfix
	 * expression. <p> 2) is an operator: if the stack is empty, push the
	 * operator onto the stack. If the stack is not empty and the current
	 * operator has lower precedence than the operator on the top of the stack
	 * pop, pop the last element of the stack to the postfix expression, and
	 * push the current character. If the stack is not empty and the current
	 * operator has the same precedence as the one on top of the stack, push the
	 * current charcater onto the stack. <p> 3) is a open parenthesis: push it
	 * onto the stack. <p> 4) is a close parenthesis: pop from the stack onto
	 * the postfix expression until we reach an open parenthesis. Pop the open
	 * parenthesis, but do not add it to the postfix expression.
	 * 
	 * <p> Finally, pop any remaining characters from the stack.
	 */
	private void generatePostfix() {
		Stack<String> stack = new Stack<String>();
		postfix = "";

		for (int i = 0; i < infix.length(); i++) {

			String curChar = infix.substring(i, i + 1);

			/*
			 * If the current character is an operand: add it to the end of the
			 * postfix expression.
			 */
			if (ALL_OPERANDS.contains(curChar)) {
				postfix += curChar;

			} else if (ALL_NON_OPERANDS.contains(curChar)) {

				/*
				 * If the current character is a open parenthesis: push it onto
				 * the stack.
				 */
				if (OPEN_CHARS.contains(curChar)) {
					stack.push(curChar);

					/*
					 * If the current character is a close parenthesis: pop from
					 * the stack onto the postfix expression until we reach an
					 * open parenthesis. Pop the open parenthesis, but do not
					 * add it to the postfix expression.
					 */
				} else if (CLOSE_CHARS.contains(curChar)) {
					while (!stack.isEmpty()
							&& !OPEN_CHARS.contains(stack.peek())) {
						postfix += stack.pop();
					}
					stack.pop(); // pop open-parenthesis

					/*
					 * If the current character is an operator:
					 */
				} else if (ALL_OPERATORS.contains(curChar)) {

					/*
					 * If the stack is empty, push the operator onto the stack.
					 */
					if (stack.isEmpty()) {
						stack.push(curChar);

						/*
						 * If the stack is not empty and the current operator
						 * has lower precedence than the operator on the top of
						 * the stack pop, pop the last element of the stack to
						 * the postfix expression, and push the current
						 * character.
						 */
					} else if (LOW_PREF_OPS.contains(curChar)
							&& HIGH_PREF_OPS.contains(stack.peek())) {
						postfix += stack.pop();
						stack.push(curChar);

						/*
						 * If the stack is not empty and the current operator
						 * has the same precedence as the one on top of the
						 * stack, push the current charcater onto the stack.
						 */
					} else {
						stack.push(curChar);
					}
				}
			}
		}

		/*
		 * Pop any remaining characters from the stack.
		 */
		while (!stack.isEmpty()) {
			postfix += stack.pop();
		}
	}

	/**
	 * Returns the instance infix expression.
	 * 
	 * @return instance infix expression.
	 */
	public String getInfix() {
		return infix;
	}

	/**
	 * Returns the instance postfix expression, generated from the instance
	 * infix expression.
	 * 
	 * @return instance postfix expression.
	 */
	public String getPostfix() {
		return postfix;
	}

	/**
	 * Attempts to return a numerical value equivalent to the instance
	 * infix/postfix expression.
	 * 
	 * <p>
	 * --> USES INTEGER MATH <--
	 * 
	 * <p>
	 * Behavior not defined for expressions with negative numbers, non-numerical
	 * constants, and non-integer numbers.
	 * 
	 * @return number equivalent to instance postfix expression.
	 */
	/*
	 * Follows a standard algorithm for evaluating postfix expressions.
	 * 
	 * <p> Proceed left to right throught the postfix expression. If the current
	 * character:
	 * 
	 * <p> 1) is an operand: push it onto the stack.
	 * 
	 * <p> 2) is an operator: pop the top two elements from the stack, apply the
	 * operator to them, and push the result on the stack.
	 * 
	 * <p> Finally, return the value in the stack.
	 */
	public String evaluate() {

		Stack<String> stack = new Stack<String>();

		for (int i = 0; i < postfix.length(); i++) {
			String curChar = postfix.substring(i, i + 1);

			/*
			 * If the current character is an operand: push it onto the stack.
			 */
			if (ALL_OPERANDS.contains(curChar)) {
				stack.push(curChar);

				/*
				 * If the current character is an operator: pop the top two
				 * elements from the stack, apply the operator to them, and push
				 * the result on the stack.
				 */
			} else if (ALL_OPERATORS.contains(curChar)) {
				String secondOperand = stack.pop();
				String firstOperand = stack.pop();

				/*
				 * If they are both numbers, we know how to deal with them.
				 */
				if (NUMBERS.contains(secondOperand)
						&& NUMBERS.contains(firstOperand)) {
					int aInt = Integer.valueOf(secondOperand);
					int bInt = Integer.valueOf(firstOperand);
					int result = 0;

					if (curChar.equals("+")) {
						result = bInt + aInt;
					} else if (curChar.equals("-")) {
						result = bInt - aInt;
					} else if (curChar.equals("*")) {
						result = bInt * aInt;
					} else if (curChar.equals("/")) {
						result = bInt / aInt;
					}

					stack.push(String.valueOf(result));
				} else {
					/*
					 * TODO: Deal with non-numbers in evaluate. Figure out a
					 * better way to deal with non-number constants. For now, we
					 * just push them back on the stack and it clogs up the
					 * whole alg.
					 */
					stack.push(secondOperand + curChar + firstOperand);
				}
			}
		}

		/*
		 * Finally, return the value in the stack.
		 * 
		 * TODO: figure out stacks with non-numbers remaining.
		 */
		return stack.pop();
	}
}
