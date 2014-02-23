import java.util.Stack;

/**
 * This class provides methods for converting infix to postfix notation and for
 * evaluating postfix expressions.
 * 
 * <p>
 * DEFICIENCIES: This class cannot evaluate expressions with constants or
 * negative numbers. Additional testing is needed. This class does not support
 * setting the infix expression after construction. This class cannot convert
 * from postfix to infix. This class does not support prefix notation.
 * 
 * @author Sean Stephens - seanastephens@email.arizona.edu
 * 
 */
public class Converter {

	private static String vars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz";
	private static String constants = "0123456789";
	private static String operands = constants + vars;
	private static String hiOps = "*/";
	private static String loOps = "+-";
	private static String ops = loOps + hiOps;
	private static String openPar = "(";
	private static String closePar = ")";
	private static String par = openPar + closePar;
	private static String notNum = par + ops;
	private static String valid = operands + notNum;

	private String postfix;
	private String infix;

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

	/**
	 * Private helper method that removes invalid characters from the infix
	 * expression.
	 */
	private void cleanInfix() {
		String temp = "";
		for (int i = 0; i < infix.length(); i++) {
			if (valid.contains(infix.substring(i, i + 1))) {
				temp += this.infix.substring(i, i + 1);
			}
		}
		this.infix = temp;
	}

	/**
	 * Private helper method that adds '*' between adjacent numbers or constants
	 * in the infix expression.
	 */
	private void insertStars() {
		for (int i = 0; i < this.infix.length() - 1; i++) {
			if (operands.contains(this.infix.substring(i, i + 1))
					&& operands.contains(this.infix.substring(i + 1, i + 2))) {
				this.infix = this.infix.substring(0, i + 1) + "*"
						+ this.infix.substring(i + 1, this.infix.length());
			}
		}

	}

	/**
	 * Private helper method that converts the instance infix expression into a
	 * postfix expression.
	 */
	private void generatePostfix() {
		Stack<String> stack = new Stack<String>();
		this.postfix = "";

		for (int i = 0; i < this.infix.length(); i++) {
			String curChar = this.infix.substring(i, i + 1);
			if (operands.contains(curChar)) {
				this.postfix += curChar;

			} else if (notNum.contains(curChar)) {

				if (openPar.contains(curChar)) {
					stack.push(curChar);
				} else if (closePar.contains(curChar)) {
					while (!stack.isEmpty() && !openPar.contains(stack.peek())) {
						this.postfix += stack.pop();
					}
					stack.pop();
				} else if (ops.contains(curChar)) {
					if (stack.isEmpty()) {
						stack.push(curChar);
					} else if (loOps.contains(curChar)
							&& hiOps.contains(stack.peek())) {
						this.postfix += stack.pop();
						stack.push(curChar);
					} else {
						stack.push(curChar);
					}
				}
			}
		}

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
		return this.infix;
	}

	/**
	 * Returns the instance postfix expression, generated from the instance
	 * infix expression.
	 * 
	 * @return instance postfix expression.
	 */
	public String getPostfix() {
		return this.postfix;
	}

	/**
	 * Attempts to return a numerical value equivalent to the instance
	 * infix/postfix expression.
	 * 
	 * <p>
	 * Behavior not defined for expressions with negative numbers, non-numerical
	 * constants, and non-integer numbers.
	 * 
	 * @return number equivalent to instance posfix expression.
	 */
	public String evaluate() {

		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < this.postfix.length(); i++) {
			String curChar = this.postfix.substring(i, i + 1);
			if (operands.contains(curChar)) {
				stack.push(curChar);
			} else if (ops.contains(curChar)) {
				String a = stack.pop();
				String b = stack.pop();
				if (constants.contains(a) && constants.contains(b)) {
					int aInt = Integer.valueOf(a);
					int bInt = Integer.valueOf(b);
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
					stack.push(a + curChar + b);
				}
			}
		}
		String temp = "";
		while (!stack.isEmpty()) {
			temp += stack.pop();
		}
		return temp;
	}
}
