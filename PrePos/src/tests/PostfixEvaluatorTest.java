package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import core.InfixToPostfix;
import core.PostfixEvaluator;
import core.Token;

/**
 * This class provides JUnit test methods for InfixToPostfix.
 * 
 * @author Sean Stephens - seanastephens@email.arizona.edu
 * 
 */
public class PostfixEvaluatorTest {

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
		assertEquals("3",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	@Test
	public void testPlusSubtractDivMult() {
		String infix = "1+2-3/4*5";
		assertEquals("3",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	@Test
	public void testSubtractPlusMultDiv() {
		String infix = "1-2+3*4/5";
		assertEquals("-1",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	@Test
	public void testSubtractPlusDivMult() {

		String infix = "1-2+3/4*5";
		assertEquals("-1",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	@Test
	public void testMultiplePlus() {
		String infix = "1+1+1+1";
		assertEquals("4",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	@Test
	public void testMultipleSubtract() {
		String infix = "1-1-1-1";
		assertEquals("-2",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	@Test
	public void testMultipleMult() {
		String infix = "1*1*1*1";
		assertEquals("1",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	@Test
	public void testMultipleDiv() {
		String infix = "1/1/1/1";
		assertEquals("1",
				PostfixEvaluator.evaluate(InfixToPostfix.convert(infix)));
	}

	// TODO: Write tests that include parentheses.
}
