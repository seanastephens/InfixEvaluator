package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Provides static methods for converting infix expressions to postfix
 * expressions. Does not handle alphabetic variables.
 * 
 * <p>
 * DEFICIENCIES: Additional testing is needed.
 * 
 * @author Sean Stephens, seanastephens@email.arizona.edu
 * 
 */
public class InfixToPostfix {
	/**
	 * String constant consisting of all the characters that will be parsed as
	 * operands.
	 */
	public static final String ALL_OPERANDS = "0123456789";

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
	 * Returns a post-fix expression order List of Tokens that is equivalent to
	 * the given infix expression.
	 * 
	 * @param infix
	 *            - expression to convert.
	 * @return equivalent postfix expression order Token List.
	 */
	public static List<Token> convert(String infix) {
		infix = removeBadChars(infix);
		List<Token> infixExpression = tokenizeInfixString(infix);
		infixExpression = cleanNegativeNumbers(infixExpression);
		List<Token> postfixExpression = generatePostfix(infixExpression);

		return postfixExpression;
	}

	/**
	 * Returns a post-fix expression String that is equivalent to the given
	 * infix expression.
	 * 
	 * @param infix
	 *            - expression to convert.
	 * @return equivalent postfix expression String.
	 */
	public static String convertToString(String infix) {
		infix = removeBadChars(infix);
		List<Token> infixExpression = tokenizeInfixString(infix);
		infixExpression = cleanNegativeNumbers(infixExpression);
		List<Token> postfixExpression = generatePostfix(infixExpression);

		String postfix = "";
		for (Token t : postfixExpression) {
			if (t.isOperand()) {
				if (Integer.parseInt(t.toString()) < 0
						|| Integer.parseInt(t.toString()) >= 10) {
					postfix += "(" + t.toString() + ")";
				} else {
					postfix += t.toString();
				}
			} else {
				postfix += t.toString();
			}
		}
		return postfix;
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
	private static String removeBadChars(String infix) {
		String cleanedInfix = "";

		for (int i = 0; i < infix.length(); i++) {
			if (ALL_VALID_CHARS.contains(infix.substring(i, i + 1))) {
				cleanedInfix += infix.substring(i, i + 1);
			}
		}

		return cleanedInfix;
	}

	/**
	 * Private helper method that converts a String representing an infix
	 * expression into a List of Tokens representing that same expression.
	 * 
	 * @param infix
	 *            - String representing infix expression.
	 * @return List of Tokens with equivalent value to infix.
	 */
	/*
	 * Uses the following algorithm for tokenizing Strings:
	 * 
	 * for all curChar in String expression:
	 * 
	 * 1) If curChar an operator, dump the queue into a new Token, then Tokenize
	 * the operator.
	 * 
	 * 2) If curChar is an operand, add it to the queue.
	 */
	private static List<Token> tokenizeInfixString(String infix) {
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
				 * Otherwise, curChar is an operand, queue it.
				 */
			} else {
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
	 * Private helper method that cleans up a lists of Tokens.
	 * 
	 * <p>
	 * This method applies the following rules, in order;
	 * 
	 * 1. Converts minus-operand pairs to plus-operand pairs. -2 --> +<-2>
	 * 
	 * 2. Removes parenthesis-plus pairs that occur from the previous rule:
	 * (+2... --> (2
	 * 
	 * 3. Removes parentheses around single operands: (2) --> 2
	 * 
	 * @param infix
	 *            - List of Tokens in infix format.
	 * @return List of Tokens in infix format with above processing applied.
	 */

	private static List<Token> cleanNegativeNumbers(List<Token> infix) {

		/*
		 * Converts minus-operand pairs to plus-operand pairs. -2 --> +<-2>
		 */
		for (int i = 0; i < infix.size() - 1; i++) {
			if (infix.get(i).toString().equals("-")
					&& infix.get(i + 1).isOperand()) {
				infix.remove(i);
				infix.set(i, new Token("-" + infix.get(i).toString()));
				infix.add(i, new Token("+"));
			}
		}

		/*
		 * Removes parenthesis-plus pairs that occur from the previous rule:
		 * (+2... --> (2
		 */
		for (int i = 0; i < infix.size() - 1; i++) {
			if (infix.get(i).isOpenParenthesis()
					&& infix.get(i + 1).toString().equals("+")) {
				infix.remove(i + 1);
			}
		}

		/*
		 * Handles an edge case of a single negative number.
		 */
		if (infix.get(0).toString().equals("+")) {
			infix.remove(0);
		}

		/*
		 * Removes parentheses around single operands: (2) --> 2
		 */
		for (int i = 0; i < infix.size() - 2; i++) {
			if (infix.get(i).isOpenParenthesis()
					&& infix.get(i + 2).isCloseParenthesis()) {
				infix.remove(i + 2);
				infix.remove(i);
			}
		}
		return infix;
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
	private static List<Token> generatePostfix(List<Token> infix) {
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
					while (!stack.peek().isOpenParenthesis()) {
						exp.add(new Token(stack.pop().toString()));
					}
					stack.pop(); // pop open-parenthesis

					/*
					 * If the current character is an operator:
					 */
				} else {

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
}
