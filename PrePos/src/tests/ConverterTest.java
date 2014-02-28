package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.Converter;

/**
 * This class provides JUnit test methods for Converter.
 * 
 * <p>
 * This test suite needs more tests in the following categories: general postfix
 * generation, handling parentheses in postfix generation, handling nested
 * parentheses, converting/generating expressions with negative numbers, and
 * converting/generating expressions with constants.
 * 
 * @author Sean Stephens - seanastephens@email.arizona.edu
 * 
 */
public class ConverterTest {

	@Test
	public void testConstructorAndGetterClearNonCharsAndInsertsStars() {
		Converter c = new Converter("1+1 * 2 a/b3");
		assertTrue(c.getInfix().equals("1+1*2*a/b*3"));
	}

	@Test
	public void testSetterClearsNonCharsAndInsertsStars() {
		Converter c = new Converter("");
		assertTrue(c.getInfix().equals(""));
		c.setNewInfix("1+1 * 2 a/b3");
		assertTrue(c.getInfix().equals("1+1*2*a/b*3"));
	}

	@Test
	public void testTwoOperandsAndOneOperator() {
		Converter c = new Converter("1+1");
		assertTrue(c.getPostfix().equals("11+"));
	}

	@Test
	public void testTwoOperandsAndOneOperatorWithSetter() {
		Converter c = new Converter("");
		c.setNewInfix("1+1");
		assertTrue(c.getPostfix().equals("11+"));
	}

	@Test
	public void testTheeOperatorsNoPrecedence() {
		Converter c = new Converter("1+2*3");
		assertTrue(c.getPostfix().equals("123*+"));
	}

	@Test
	public void testTheeOperatorsWithPrecedence() {
		Converter c = new Converter("1*2+3");
		assertTrue(c.getPostfix().equals("12*3+"));
	}

	@Test
	public void testSimpleParentheses() {
		Converter c = new Converter("1*(2+3)");
		assertTrue(c.getPostfix().equals("123+*"));
	}

	@Test
	public void testNestedParentheses() {
		Converter c = new Converter("1*(2+3/(4+5))");
		assertTrue(c.getPostfix().equals("12345+/+*"));
	}

	@Test
	public void testEvaluateWithOnlyNumbers() {
		Converter c = new Converter("1+1");
		assertTrue(c.evaluate().equals("2"));
	}

	@Test
	public void testBigEvaluateWithOnlyNumbers() {
		Converter c = new Converter("1+2*3+4/5+(6-4)");
		assertTrue("9".equals(c.evaluate()));
	}

	@Test
	public void testEvaluateUsingSetter() {
		Converter c = new Converter("");
		c.setNewInfix("1+2*3+4/5+(6-4)");
		assertTrue(c.evaluate().equals("9"));
		c.setNewInfix("1+1");
		assertTrue(c.evaluate().equals("2"));
	}

	@Test
	public void testDoesntBreakNumbersWithStars() {
		Converter c = new Converter("12AB");

		assertTrue(c.getInfix().equals("12*A*B"));

		c.setNewInfix("A12B");

		assertTrue(c.getInfix().equals("A*12*B"));

		c.setNewInfix("AB12");

		assertTrue(c.getInfix().equals("A*B*12"));

	}

	@Test
	public void testDoesntBreakLargeNumbersWithStars() {
		Converter c = new Converter("1234567890");

		assertTrue(c.getInfix().equals("1234567890"));
	}

	/*
	 * TODO: Fix evaluate function for large nums
	 */

	// Formatter off
	//@off
	/*
	@Test
	public void testEvaluateWithLargeNumbersNoBreakStars() {
		Converter c = new Converter("1234567890-1234567890");

		System.out.println(c.getInfix());
		System.out.println(c.getPostfix());
		System.out.println(c.evaluate());
		assertTrue(c.evaluate().equals("0"));
	}
	*/
	// Formatter on
	//@on
}
