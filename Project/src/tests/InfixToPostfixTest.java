package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.InfixToPostfix;
import core.Token;

/**
 * This class provides JUnit test methods for InfixToPostfix.
 * 
 * @author Sean Stephens - seanastephens@email.arizona.edu
 * 
 */
public class InfixToPostfixTest {

	Token ONE = new Token("1");
	Token MONE = new Token("-1");
	Token TWO = new Token("2");
	Token MTWO = new Token("-2");
	Token THREE = new Token("3");
	Token MTHREE = new Token("-3");
	Token FOUR = new Token("4");
	Token MFOUR = new Token("-4");
	Token FIVE = new Token("5");
	Token MFIVE = new Token("-5");
	Token PLUS = new Token("+");
	Token MINUS = new Token("-");
	Token MULT = new Token("*");
	Token DIV = new Token("/");

	@Test
	public void testPlusSubtractMultDiv() {
		String infix = "1+2-3*4/5";
		String postfix = "12(-3)45/*++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(TWO);
		expectedExpression.add(MTHREE);
		expectedExpression.add(FOUR);
		expectedExpression.add(FIVE);
		expectedExpression.add(DIV);
		expectedExpression.add(MULT);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testPlusSubtractDivMult() {
		String infix = "1+2-3/4*5";
		String postfix = "12(-3)45*/++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(TWO);
		expectedExpression.add(MTHREE);
		expectedExpression.add(FOUR);
		expectedExpression.add(FIVE);
		expectedExpression.add(MULT);
		expectedExpression.add(DIV);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testSubtractPlusMultDiv() {
		String infix = "1-2+3*4/5";
		String postfix = "1(-2)345/*++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(MTWO);
		expectedExpression.add(THREE);
		expectedExpression.add(FOUR);
		expectedExpression.add(FIVE);
		expectedExpression.add(DIV);
		expectedExpression.add(MULT);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testSubtractPlusDivMult() {

		String infix = "1-2+3/4*5";
		String postfix = "1(-2)345*/++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(MTWO);
		expectedExpression.add(THREE);
		expectedExpression.add(FOUR);
		expectedExpression.add(FIVE);
		expectedExpression.add(MULT);
		expectedExpression.add(DIV);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testMultiplePlus() {
		String infix = "1+1+1+1";
		String postfix = "1111+++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testMultipleSubtract() {
		String infix = "1-1-1-1";
		String postfix = "1(-1)(-1)(-1)+++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(MONE);
		expectedExpression.add(MONE);
		expectedExpression.add(MONE);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testMultipleMult() {
		String infix = "1*1*1*1";
		String postfix = "1111***";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(MULT);
		expectedExpression.add(MULT);
		expectedExpression.add(MULT);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testMultipleDiv() {
		String infix = "1/1/1/1";
		String postfix = "1111///";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(ONE);
		expectedExpression.add(DIV);
		expectedExpression.add(DIV);
		expectedExpression.add(DIV);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}

	@Test
	public void testParenthesesAffectOrderOfOps() {
		String infix = "1+2*3";
		String postfix = "123*+";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(TWO);
		expectedExpression.add(THREE);
		expectedExpression.add(MULT);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}

		String infix2 = "(1+2)*3";
		String postfix2 = "12+3*";

		assertEquals(postfix2, InfixToPostfix.convertToString(infix2));

		List<Token> expectedExpression2 = new ArrayList<Token>();
		expectedExpression2.add(ONE);
		expectedExpression2.add(TWO);
		expectedExpression2.add(PLUS);
		expectedExpression2.add(THREE);
		expectedExpression2.add(MULT);

		List<Token> actualExpression2 = InfixToPostfix.convert(infix2);

		assertEquals(expectedExpression2.size(), actualExpression2.size());

		for (int i = 0; i < expectedExpression2.size(); i++) {
			assertEquals(expectedExpression2.get(i).toString(),
					actualExpression2.get(i).toString());
		}
	}

	@Test
	public void testNestedParentheses() {
		String infix = "1+2*3-4*3+2*3-2";
		String postfix = "123*(-4)3*23*(-2)++++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(ONE);
		expectedExpression.add(TWO);
		expectedExpression.add(THREE);
		expectedExpression.add(MULT);
		expectedExpression.add(MFOUR);
		expectedExpression.add(THREE);
		expectedExpression.add(MULT);
		expectedExpression.add(TWO);
		expectedExpression.add(THREE);
		expectedExpression.add(MULT);
		expectedExpression.add(MTWO);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}

		String infix2 = "(1+2*(3-4)*(3+2))*3-2";
		String postfix2 = "123(-4)+32+**+3*(-2)+";

		assertEquals(postfix2, InfixToPostfix.convertToString(infix2));

		List<Token> expectedExpression2 = new ArrayList<Token>();
		expectedExpression2.add(ONE);
		expectedExpression2.add(TWO);
		expectedExpression2.add(THREE);
		expectedExpression2.add(MFOUR);
		expectedExpression2.add(PLUS);
		expectedExpression2.add(THREE);
		expectedExpression2.add(TWO);
		expectedExpression2.add(PLUS);
		expectedExpression2.add(MULT);
		expectedExpression2.add(MULT);
		expectedExpression2.add(PLUS);
		expectedExpression2.add(THREE);
		expectedExpression2.add(MULT);
		expectedExpression2.add(MTWO);
		expectedExpression2.add(PLUS);

		List<Token> actualExpression2 = InfixToPostfix.convert(infix2);

		assertEquals(expectedExpression2.size(), actualExpression2.size());

		for (int i = 0; i < expectedExpression2.size(); i++) {
			assertEquals(expectedExpression2.get(i).toString(),
					actualExpression2.get(i).toString());
		}
	}

	@Test
	public void testWrapsNegatives() {
		String infix = "-2";
		String postfix = "(-2)";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));
	}

	@Test
	public void testCleansParentheses() {
		String infix = "((-2))";
		String postfix = "(-2)";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));
	}

	@Test
	public void testCleansDoublePlus() {
		String infix = "1+(-2)";
		String postfix = "1(-2)+";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));
	}

	@Test
	public void testCanHandleSpaces() {
		String infix = "1 + ( -2 )";
		String postfix = "1(-2)+";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));
	}

	@Test
	public void testCanHandleTwoDigitNumbers() {
		String infix = "1111+2222-3333";
		String postfix = "(1111)(2222)(-3333)++";

		assertEquals(postfix, InfixToPostfix.convertToString(infix));

		List<Token> expectedExpression = new ArrayList<Token>();
		expectedExpression.add(new Token("1111"));
		expectedExpression.add(new Token("2222"));
		expectedExpression.add(new Token("-3333"));
		expectedExpression.add(PLUS);
		expectedExpression.add(PLUS);

		List<Token> actualExpression = InfixToPostfix.convert(infix);

		assertEquals(expectedExpression.size(), actualExpression.size());

		for (int i = 0; i < expectedExpression.size(); i++) {
			assertEquals(expectedExpression.get(i).toString(), actualExpression
					.get(i).toString());
		}
	}
}
