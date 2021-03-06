package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumTest;
import hx.bankcheck.accountvalidator.ChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;
import hx.bankcheck.accountvalidator.impl.Checksum09;

import java.util.Arrays;

/**
 * 
 * @author Tobias Mayer (bankcheck@tobiasm.de)
 *
 *
 * $Id$
 */
public class Checksum09Test extends AbstractChecksumTest {
	
	@Override
	public void testValidate() throws ValidationException {
		ChecksumValidator cs = new Checksum09();
		
		//TODO Zufallszahlen statt fortlaufende Zahlen
		// Check all numbers < 100000, all would take too long.
		for(int i=0; i<100000l; i++) {
			int[] n = new int[10];
			int x = i;
			for(int c=0; c<10; c++) {
				n[9-c] = x % 10;
				x = x / 10;
			}
			assertTrue("Check failed for " + Arrays.toString(n), cs.validate(n,null));
		}
	}
	
}