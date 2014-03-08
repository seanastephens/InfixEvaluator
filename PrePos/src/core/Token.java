package core;

public class Token {

	/**
	 * String constant consisting of all the characters that will be parsed to
	 * represent numbers.
	 */
	public static final String NUMBERS = "0123456789";

	private String exp;
	private boolean isOperator;
	private boolean isOperand;
	private boolean isOpenParenthesis;
	private boolean isCloseParenthesis;
	private boolean isLowPrecedence;
	private boolean isHighPrecedence;
	private boolean isNumber;

	public Token(String exp) {
		if ("".equals(exp)) {
			throw new EmptyTokenException();
		}
		this.exp = exp;
		if ("*/+-".contains(exp)) {
			isOperator = true;
			if ("*/".contains(exp)) {
				isLowPrecedence = false;
				isHighPrecedence = true;
			} else {
				isLowPrecedence = true;
				isHighPrecedence = false;
			}
		} else {
			isOperator = false;
		}

		isOperand = true;
		if (isOperator) {
			isOperand = false;
			isNumber = false;
		} else {
			for (int i = 0; i < exp.length(); i++) {
				if (!(NUMBERS + "-").contains(exp.substring(i, i + 1))) {
					isOperand = false;
				}
			}

			if (isOperand) {
				isNumber = true;
				for (int i = 0; i < exp.length(); i++) {
					if (!(NUMBERS + "-").contains(exp.substring(i, i + 1))) {
						isNumber = false;
					}
				}
			}
		}

		if ("(".contains(exp)) {
			isOpenParenthesis = true;
		} else {
			isOpenParenthesis = false;
		}

		if (")".contains(exp)) {
			isCloseParenthesis = true;
		} else {
			isCloseParenthesis = false;
		}
	}

	public String toString() {
		return exp;
	}

	public boolean isOperator() {
		return isOperator;
	}

	public boolean isOperand() {
		return isOperand;
	}

	public boolean isOpenParenthesis() {
		return isOpenParenthesis;
	}

	public boolean isCloseParenthesis() {
		return isCloseParenthesis;
	}

	public boolean isLowPrecedence() {
		return isLowPrecedence;
	}

	public boolean isHighPrecedence() {
		return isHighPrecedence;
	}

	public boolean isNumber() {
		return isNumber;
	}
}