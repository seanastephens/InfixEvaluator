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
	Token FIVE = new Token("5");
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

	// TODO: Write tests that include parentheses.
}
