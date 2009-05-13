/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;

/**
 * Modulus 11, Gewichtung 2, 3, 4, 5, 6, 7, 2, 3, 4 <br/>
 * 
 * Die Stellen 1 bis 9 der Kontonummer werden von rechts nach links mit den
 * Ziffern 2, 3, 4, 5, 6, 7, 2, 3, 4 multipliziert. Die jeweiligen Produkte
 * werden addiert und die Summe durch 11 dividiert. Der Rest wird von 11
 * abgezogen, das Ergebnis ist die Pr�fziffer. Pr�fziffer ist die 10. Stelle der
 * Kontonummer. <br/>
 * 
 * Beispiel 1) <br/>
 * 
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10) <br/>
 * Kontonr.: 0 2 9 0 5 4 5 0 0 P <br/>
 * Gewichtung: 4 3 2 7 6 5 4 3 2 0 + 6 + 18 + 0 + 30 + 20 + 20 + 0 + 0 = 94 : 11
 * = 8, Rest 6 11 - 6 = 5 <br/>
 * Die Pr�fziffer ist 5 <br/>
 * 
 * Bei dem Ergebnis 10 oder 11 ist die Kontonummer ung�ltig. <br/>
 * 
 * Beispiel 2) <br/>
 * 
 * Beginnt eine 10-stellige Kontonummer mit 9, so wird beim Ergebnis 10 die
 * Pr�fziffer = 7 und beim Ergebnis 11 die Pr�fziffer = 8 gesetzt. <br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class Checksum56 extends AbstractChecksumValidator {

	private static final int[] WEIGHTS = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hx.bankcheck.accountvalidator.AbstractChecksumValidator#validate(int[])
	 */
	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		return accountNumber[9] == calcChecksum(accountNumber);
	}

	private int calcChecksum(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < WEIGHTS.length; i++) {
			sum += accountNumber[i] * WEIGHTS[i];
		}
		if (accountNumber[0] == 9) {
			return ((sum % 11 == 0) ? 8 : ((sum % 11 == 1) ? 7
					: (11 - sum % 11)));
		} else {
			return ((sum % 11 == 0) || (sum % 11 == 1)) ? -1 : (11 - sum % 11);
		}
	}

}
