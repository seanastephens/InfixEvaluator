package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
	 * Private instance list of tokens used to represent the passed infix
	 * expression.
	 */
	private List<Token> infixExpression;
	/**
	 * Private instance list of tokens used to represent the generated postfix
	 * expression.
	 */
	private List<Token> postfixExpression;

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
		/*
		 * Out-sourced infix setup to remove duplicate code.
		 */
		setNewInfix(infix);
	}

	/**
	 * Changes the instance infix expression to the given infix String.
	 * 
	 * @param infix
	 *            - new instance infix expression.
	 */
	public void setNewInfix(String infix) {
		infix = removeBadChars(infix);
		infix = insertStars(infix);
		infixExpression = tokenizeInfixString(infix);
		postfixExpression = generatePostfix(infixExpression);

	}

	/**
	 * Private helper method that removes bad characters from a String.
	 * 
	 * <p>
	 * Bad characters are defined as those characters not in ALL_VALID_CHARS.
	 * 
	 * @param infix
	 *            - String to be sanitized.
	 * @return 'cleaned' String.
	 */
	private String removeBadChars(String infix) {
		String cleanedInfix = "";

		for (int i = 0; i < infix.length(); i++) {
			if (ALL_VALID_CHARS.contains(infix.substring(i, i + 1))) {
				cleanedInfix += infix.substring(i, i + 1);
			}
		}

		return cleanedInfix;

	}

	/**
	 * Private helper method that adds '*' between adjacent numbers or constants
	 * in the infix expression.
	 */
	private String insertStars(String infix) {

		for (int i = 0; i < infix.length() - 1; i++) {

			/*
			 * Checks the combinations of characters that we should insert a
			 * mult symbol between: 1A, A1, AB.
			 */
			if (NUMBERS.contains(infix.substring(i, i + 1))
					&& VARIABLES.contains(infix.substring(i + 1, i + 2))
					|| VARIABLES.contains(infix.substring(i, i + 1))
					&& NUMBERS.contains(infix.substring(i + 1, i + 2))
					|| VARIABLES.contains(infix.substring(i, i + 1))
					&& VARIABLES.contains(infix.substring(i + 1, i + 2))) {

				infix = infix.substring(0, i + 1) + "*"
						+ infix.substring(i + 1, infix.length());
			}
		}

		return infix;
	}

	/**
	 * Private helper method that converts a String representing an infix
	 * expression into a List of Tokens representing that same expression.
	 * 
	 * <p>
	 * PRECONDITION: if insertStars() has not been run on the input for this
	 * method, the list returned by this method will contain invalid operand
	 * tokens.
	 * 
	 * @param infix
	 *            - String representing infix expression.
	 * @return List of Tokens equivalent to passed String.
	 */
	/*
	 * Uses the following algorithm for tokenizing Strings:
	 * 
	 * for all curChar in String expression:
	 * 
	 * 1) If curChar not an operand, dump the queue into a new Token, then
	 * Tokenize the operator.
	 * 
	 * 2) If curChar is an operand, add it to the queue.
	 */
	private List<Token> tokenizeInfixString(String infix) {
		Queue<String> queue = new LinkedList<String>();
		List<Token> exp = new ArrayList<Token>();

		for (int i = 0; i < infix.length(); i++) {
			String curChar = infix.substring(i, i + 1);

			/*
			 * If curChar not an operand, clear the queue into a new Token, then
			 * Tokenize the operator.
			 */
			if (ALL_NON_OPERANDS.contains(curChar)) {
				String temp = "";
				while (!queue.isEmpty()) {
					temp += queue.remove();
				}
				if (temp.length() > 0) {
					exp.add(new Token(temp));
				}
				exp.add(new Token(curChar));

				/*
				 * If curChar is an operand, queue it.
				 */
			} else if (ALL_OPERANDS.contains(curChar)) {
				queue.add(curChar);
			}
		}

		/*
		 * Clear queue if any chars remain
		 */
		String temp = "";
		while (!queue.isEmpty()) {
			temp += queue.remove();
		}
		if (temp.length() > 0) {
			exp.add(new Token(temp));
		}

		return exp;
	}

	/**
	 * Private helper method that converts the instance infix expression into a
	 * postfix expression.
	 */
	/*
	 * Derived from http://csis.pace.edu/~wolf/CS122/infix-postfix.htm. The
	 * general procedure for converting an infix expression to a postfix
	 * expression consists of using a stack and the following rules:
	 * 
	 * Proceed left to right through the infix expression. If the next
	 * character:
	 * 
	 * 1) is an operand: add it to the end of the postfix expression.
	 * 
	 * 2) is an operator: if the stack is empty, push the operator onto the
	 * stack. If the stack is not empty and the current operator has lower
	 * precedence than the operator on the top of the stack pop, pop the last
	 * element of the stack to the postfix expression, and push the current
	 * character. If the stack is not empty and the current operator has the
	 * same precedence as the one on top of the stack, push the current
	 * charcater onto the stack.
	 * 
	 * 3) is a open parenthesis: push it onto the stack.
	 * 
	 * 4) is a close parenthesis: pop from the stack onto the postfix expression
	 * until we reach an open parenthesis. Pop the open parenthesis, but do not
	 * add it to the postfix expression.
	 * 
	 * Finally, pop any remaining characters from the stack.
	 */
	private List<Token> generatePostfix(List<Token> infix) {
		Stack<Token> stack = new Stack<Token>();
		List<Token> exp = new ArrayList<Token>();

		for (int i = 0; i < infix.size(); i++) {

			Token current = infix.get(i);

			/*
			 * If the current character is an operand: add it to the end of the
			 * postfix expression.
			 */
			if (current.isOperand()) {
				exp.add(new Token(current.toString()));

			} else {

				/*
				 * If the current character is a open parenthesis: push it onto
				 * the stack.
				 */
				if (current.isOpenParenthesis()) {
					stack.push(new Token(current.toString()));

					/*
					 * If the current character is a close parenthesis: pop from
					 * the stack onto the postfix expression until we reach an
					 * open parenthesis. Pop the open parenthesis, but do not
					 * add it to the postfix expression.
					 */
				} else if (current.isCloseParenthesis()) {
					while (!stack.isEmpty()
							&& !stack.peek().isOpenParenthesis()) {
						exp.add(new Token(stack.pop().toString()));
					}
					stack.pop(); // pop open-parenthesis

					/*
					 * If the current character is an operator:
					 */
				} else if (current.isOperator()) {

					/*
					 * If the stack is empty, push the operator onto the stack.
					 */
					if (stack.isEmpty()) {
						stack.push(new Token(current.toString()));

						/*
						 * If the stack is not empty and the current operator
						 * has lower precedence than the operator on the top of
						 * the stack pop, pop the last element of the stack to
						 * the postfix expression, and push the current
						 * character.
						 */
					} else if (current.isLowPrecedence()
							&& stack.peek().isHighPrecedence()) {
						exp.add(new Token(stack.pop().toString()));
						stack.push(new Token(current.toString()));

						/*
						 * If the stack is not empty and the current operator
						 * has the same precedence as the one on top of the
						 * stack, push the current character onto the stack.
						 */
					} else {
						stack.push(new Token(current.toString()));
					}
				}
			}
		}

		/*
		 * Pop any remaining characters from the stack.
		 */

		while (!stack.isEmpty()) {
			exp.add(new Token(stack.pop().toString()));
		}

		return exp;
	}

	/**
	 * Returns the instance infix expression.
	 * 
	 * @return instance infix expression.
	 */
	public String getInfix() {
		String temp = "";
		for (int i = 0; i < infixExpression.size(); i++) {
			temp += infixExpression.get(i).toString();
		}
		return temp;
	}

	/**
	 * Returns the instance postfix expression, generated from the instance
	 * infix expression.
	 * 
	 * @return instance postfix expression.
	 */
	public String getPostfix() {
		String temp = "";
		for (int i = 0; i < postfixExpression.size(); i++) {
			temp += postfixExpression.get(i).toString();
		}
		return temp;
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
	public String evaluate() {

		Stack<Token> stack = new Stack<Token>();

		for (int i = 0; i < postfixExpression.size(); i++) {
			Token current = postfixExpression.get(i);

			/*
			 * If the current character is an operand: push it onto the stack.
			 */
			if (current.isOperand()) {
				stack.push(new Token(current.toString()));

				/*
				 * If the current character is an operator: pop the top two
				 * elements from the stack, apply the operator to them, and push
				 * the result on the stack.
				 */
			} else if (current.isOperator()) {
				Token secondOperand = stack.pop();
				Token firstOperand = stack.pop();

				/*
				 * If they are both numbers, we know how to deal with them.
				 */
				if (secondOperand.isNumber() && firstOperand.isNumber()) {
					int aInt = Integer.valueOf(secondOperand.toString());
					int bInt = Integer.valueOf(firstOperand.toString());
					int result = 0;

					if (current.toString().equals("+")) {
						result = bInt + aInt;
					} else if (current.toString().equals("-")) {
						result = bInt - aInt;
					} else if (current.toString().equals("*")) {
						result = bInt * aInt;
					} else if (current.toString().equals("/")) {
						result = bInt / aInt;
					}

					stack.push(new Token(String.valueOf(result)));
				} else {
					/*
					 * TODO: Deal with non-numbers in evaluate. Figure out a
					 * better way to deal with non-number constants. For now, we
					 * just push them back on the stack and it clogs up the
					 * whole alg.
					 */
					stack.push(new Token(secondOperand + current.toString()
							+ firstOperand));
				}
			}
		}

		/*
		 * Finally, return the value in the stack.
		 * 
		 * TODO: figure out stacks with non-numbers remaining.
		 */
		return stack.pop().toString();
	}
}
