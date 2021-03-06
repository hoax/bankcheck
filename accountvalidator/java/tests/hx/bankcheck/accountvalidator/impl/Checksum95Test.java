package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumTest;
import hx.bankcheck.accountvalidator.ChecksumValidator;

/**
 * Testclass for testing algorithm 95.
 * 
 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class Checksum95Test extends AbstractChecksumTest {

	@Override
	public void testValidate() throws Throwable {

		ChecksumValidator validator = new Checksum95();

		// Valid account numbers
		int[] validAccountNumber1 = { 0, 0, 6, 8, 0, 0, 7, 0, 0, 3 };
		int[] validAccountNumber2 = { 0, 8, 4, 7, 3, 2, 1, 7, 5, 0 };
		int[] validAccountNumber3 = { 6, 4, 5, 0, 0, 6, 0, 4, 9, 4 };
		int[] validAccountNumber4 = { 6, 4, 5, 4, 0, 0, 0, 0, 0, 3 };

		// Should be valid
		assertTrue(validator.validate(validAccountNumber1, null));
		assertTrue(validator.validate(validAccountNumber2, null));
		assertTrue(validator.validate(validAccountNumber3, null));
		assertTrue(validator.validate(validAccountNumber4, null));

		// Testing exceptions

		checkRangeOfAccountNumbers(1, 1999999l, null, 0, true, validator, 10000);
		checkRangeOfAccountNumbers(9000000, 25999999, null, 0, true, validator,
				10000);
		checkRangeOfAccountNumbers(396000000, 499999999, null, 0, true,
				validator, 10000);
		checkRangeOfAccountNumbers(700000000, 799999999, null, 0, true,
				validator, 10000);

	}
}
