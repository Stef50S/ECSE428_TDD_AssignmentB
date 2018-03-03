package ca.mcgill.ecse428.postalratecalculator.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPostalRateCalculator {
	
	private ByteArrayOutputStream specialOut; // Special stream to direct output
	private PrintStream ps;
	private PrintStream console; // Output console

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		specialOut = new ByteArrayOutputStream();
		ps = new PrintStream(specialOut);
		console = System.out;
		System.setOut(ps);
	}

	@AfterEach
	public void tearDown() throws Exception {
		System.setOut(console);
	}

	@Test
	public void noArguments() {
		PostalRateCalculator.main(null);
		assertEquals("Must provide arguments!", specialOut.toString());
	}
	
	@Test
	public void tooFewArguments() {
		String[] exInputs = new String[] {"example", "arguments", "being", "entered"};
		PostalRateCalculator.main(exInputs);
		assertEquals("Usage: FromPostalCode, ToPostalCode, Length (cm), Width (cm), Height (cm), Weight (kg), PostType", specialOut.toString());
	}
	
	@Test
	public void tooManyArguments() {
		String[] exInputs = new String[] {"too", "many", "arguments", "are", "filling", "up", "the", "command", "line"};
		PostalRateCalculator.main(exInputs);
		assertEquals("Usage: FromPostalCode, ToPostalCode, Length (cm), Width (cm), Height (cm), Weight (kg), PostType", specialOut.toString());
	}
	
	@Test
	public void negativeHeight() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "40.0", "-10.0", "7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be positive!", specialOut.toString());
	}
	
	@Test
	public void negativeWeight() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "40.0", "30.0", "-7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be positive!", specialOut.toString());
	}
	
	@Test
	public void negativeWidth() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "-40.0", "30.0", "7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be positive!", specialOut.toString());
	}
	
	@Test
	public void negativeLength() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "-50.0", "40.0", "30.0", "7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be positive!", specialOut.toString());
	}
	
	@Test
	public void heightNotNumber() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "40.0", "height", "7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be numbers!", specialOut.toString());
	}
	
	@Test
	public void weightNotNumber() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "40.0", "30.0", "weight", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be numbers!", specialOut.toString());
	}
	
	@Test
	public void lengthNotNumber() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "length", "40.0", "30.0", "8.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be numbers!", specialOut.toString());
	}
	
	@Test
	public void widthNotNumber() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "45.5", "width", "30.0", "8.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be numbers!", specialOut.toString());
	}
	
	@Test
	public void isLengthLongestSide() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "20.0", "30.0", "30.0", "8.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Length must be longest side of parcel!", specialOut.toString());
	}
	
	@Test
	public void exceedMaxLength() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "225.7", "40.0", "30.0", "7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Length must not exceed 200.0 cm!", specialOut.toString());
	}
	
	@Test
	public void belowMinWidth() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "0.05", "30.0", "7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be at least 0.10 cm!", specialOut.toString());
	}
	
	@Test
	public void belowMinLengthandHeight() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "0.07", "0.05", "0.05", "7.5", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Measurements must be at least 0.10 cm!", specialOut.toString());
	}
	
	@Test
	public void exceedMaxWeight() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "40.0", "30.0", "32.68", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Weight must not exceed 30.0 kg!", specialOut.toString());
	}
	
	@Test
	public void exceedGirthPlusLengthLimit() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "75.0", "75.0", "75.0", "12.46", "Regular"};
		PostalRateCalculator.main(sample);
		assertEquals("Length plus girth must not exceed 300.0 cm!", specialOut.toString());
	}
	
	@Test
	public void incorrectPostType() {
		String[] sample = new String[] {"H1S 1C4", "H1S 1C4", "50.0", "40.0", "30.0", "22.83", "Bob42"};
		PostalRateCalculator.main(sample);
		assertEquals("Post Type must be either: Regular, Xpress or Priority", specialOut.toString());
	}

}
