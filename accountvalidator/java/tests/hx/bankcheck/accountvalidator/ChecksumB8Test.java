package hx.bankcheck.accountvalidator;

import hx.bankcheck.accountvalidator.impl.ChecksumB8;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * Testclass for testing algorithm B8.
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class ChecksumB8Test extends TestCase {

	@Test
	public void testValidate() throws Throwable {

		ChecksumB8 checksum = new ChecksumB8();

		// Valid account numbers for alternative 1
		int[] validAccountNumberAlternative1_1 = { 0, 7, 3, 4, 1, 9, 2, 6, 5, 7 };
		int[] validAccountNumberAlternative1_2 = { 6, 9, 3, 2, 8, 7, 5, 2, 7, 4 };

		// Invalid account numbers for alternative 1
		int[] invalidAccountNumberAlternative1_1 = { 3, 1, 4, 5, 8, 6, 3, 0, 2,
				9 };
		int[] invalidAccountNumberAlternative1_2 = { 2, 9, 3, 8, 6, 9, 2, 5, 2,
				3 };
		int[] invalidAccountNumberAlternative1_3 = { 0, 1, 3, 2, 5, 7, 2, 9, 7,
				5 };

		// Valid account numbers for alternative 2
		int[] validAccountNumberAlternative2_1 = { 3, 1, 4, 5, 8, 6, 3, 0, 2, 9 };
		int[] validAccountNumberAlternative2_2 = { 2, 9, 3, 8, 6, 9, 2, 5, 2, 3 };

		// Invalid account numbers for alternative 2
		int[] invalidAccountNumberAlternative2_1 = { 0, 1, 3, 2, 5, 7, 2, 9, 7,
				5 };

		// Should be valid using alternative 1
		assertTrue((checksum.validate(validAccountNumberAlternative1_1))
				&& (checksum.getAlternative() == 0));
		assertTrue((checksum.validate(validAccountNumberAlternative1_2))
				&& (checksum.getAlternative() == 0));

		// Should be invalid using alternative 1
		assertFalse((checksum.validate(invalidAccountNumberAlternative1_1))
				&& (checksum.getAlternative() == 0));
		assertFalse((checksum.validate(invalidAccountNumberAlternative1_2))
				&& (checksum.getAlternative() == 0));
		assertFalse((checksum.validate(invalidAccountNumberAlternative1_3))
				&& (checksum.getAlternative() == 0));

		// Should be valid using alternative 2
		assertTrue((checksum.validate(validAccountNumberAlternative2_1))
				&& (checksum.getAlternative() == 1));
		assertTrue((checksum.validate(validAccountNumberAlternative2_2))
				&& (checksum.getAlternative() == 1));

		// Should be invalid using alternative 2
		assertFalse((checksum.validate(invalidAccountNumberAlternative2_1))
				&& (checksum.getAlternative() == 1));

	}
}