/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;

/**
 * Modulus 11, Gewichtung 2, 3, 4, 5, 6, 7 <br/>
 * 
 * Die Kontonummer ist durch linksb�ndige Nullenauff�llung 10-stellig
 * darzustellen. Die 10. Stelle ist per Definition die Pr�fziffer. Die f�r die
 * Berechnung relevanten Stellen werden von rechts nach links mit den Ziffern 2,
 * 3, 4, 5, 6, 7 multipliziert. Die weitere Berechnung und die m�glichen
 * Ergebnisse entsprechen dem Verfahren 32.<br/>
 * 
 * 
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10) <br/>
 * Kontonr.: x x x x x x x x x P<br/>
 * Gewichtung: 7 6 5 4 3 2 <br/>
 * 
 * Testkontonummern: 0646440, 1359100 <br/>
 * 
 * <b>Ausnahme:</b> <br/>
 * 
 * Ist nach linksb�ndiger Auff�llung mit Nullen auf 10 Stellen die 3. Stelle der
 * Kontonummer = 9 (Sachkonten), so erfolgt die Berechnung gem�� der Ausnahme in
 * Methode 51 mit den gleichen Ergebnissen und Testkontonummern.<br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class Checksum81 extends AbstractChecksumValidator {

	private static final int[] WEIGHTS = { 0, 0, 0, 7, 6, 5, 4, 3, 2 };

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hx.bankcheck.accountvalidator.AbstractChecksumValidator#validate(int[])
	 */
	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		if (accountNumber[2] == 9) {
			setException(true);
			return new Checksum51().validate(accountNumber);
		} else {
			return new Checksum32(WEIGHTS).validate(accountNumber);
		}
	}

}
