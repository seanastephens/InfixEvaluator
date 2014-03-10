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
	private boolean isHighPrecedence;

	public Token(String exp) throws TokenException {
		if (exp.length() == 0) {
			throw new TokenException(
					"Tried to construct Token with empty symbol.");
		}
		this.exp = exp;
		if ("*/+-".contains(exp)) {
			isOperator = true;
			if ("*/".contains(exp)) {
				isHighPrecedence = true;
			} else {
				isHighPrecedence = false;
			}
		} else {
			isOperator = false;
		}

		if ("(".equals(exp)) {
			isOpenParenthesis = true;
		} else {
			isOpenParenthesis = false;
		}
		if (")".equals(exp)) {
			isCloseParenthesis = true;
		} else {
			isCloseParenthesis = false;
		}

		if (!isOperator && !isOpenParenthesis && !isCloseParenthesis) {
			for (int i = 0; i < exp.length(); i++) {
				if (!(NUMBERS + "-").contains(exp.substring(i, i + 1))) {
					throw new TokenException(
							"Tried to construct a Token with an invalid character sequence : "
									+ exp);
				}
			}
			isOperand = true;
		} else {
			isOperand = false;
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
		return !isHighPrecedence;
	}

	public boolean isHighPrecedence() {
		return isHighPrecedence;
	}
}