/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.exceptions.ValidationException;

/**
 * Modulus 10, Gewichtung 2, 1, 2, 1, 2, 1, 2 <br/>
 * 
 * Die Kontonummer ist 10-stellig. Die Berechnung erfolgt wie bei Verfahren 00. <br/>
 * 
 * Es ist jedoch zu beachten, dass die zweistellige Unterkontonummer (Stellen 9
 * und 10) nicht in das Pr�fzifferberechnungsverfahren mit einbezogen werden
 * darf. Die f�r die Berechnung relevante siebenstellige Stammnummer befindet
 * sich in den Stellen 1 bis 7, die Pr�fziffer in der Stelle 8.
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class Checksum67 extends Checksum00 {

	private static int[] WEIGHTS = { 2, 1, 2, 1, 2, 1, 2, 0, 0, 0 };

	public Checksum67() {
		super(WEIGHTS);
	}

	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		return (accountNumber[7] == calcChecksum(accountNumber));
	}

}
