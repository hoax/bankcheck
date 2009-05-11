/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;

/**
 * Die Kontonummer ist einschlie�lich der Pr�fziffer 10-stellig, ggf. ist die
 * Kontonummer f�r die Pr�fzifferberechnung durch linksb�ndige Auff�llung mit
 * Nullen 10-stellig darzustellen. <br/>
 * 
 * <b>Variante 1:</b><br/>
 * 
 * Modulus 10, Gewichtung 2, 1, 2, 1, 2, 1, 2, 1, 2<br/>
 * 
 * Gewichtung und Berechnung erfolgen nach der Methode 00. <br/>
 * F�hrt die Berechnung nach Variante 1 zu einem Pr�fzifferfehler, so ist nach
 * Variante 2 zu pr�fen.<br/>
 * 
 * Testkontonummern (richtig): 3456789019, 5678901231, 6789012348<br/>
 * Testkontonummern (falsch): 3456789012, 1234567890 <br/>
 * 
 * <b>Variante 2</b>:<br/>
 * 
 * Modulus 11,Gewichtung 2, 3, 4, 5, 6, 7, 2, 3, 4 <br/>
 * 
 * Gewichtung und Berechnung erfolgen nach der Methode 04.<br/>
 * 
 * Testkontonummer (richtig): 3456789012 <br/>
 * Testkontonummern (falsch) 1234567890, 0123456789
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class ChecksumA2 extends AbstractChecksumValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see hx.bankcheck.accountvalidator.ChecksumValidator#validate(int[])
	 */
	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		setAlternative(0);
		if (new Checksum00().validate(accountNumber)) {
			return true;
		} else {
			setAlternative(1);
			return (new Checksum04().validate(accountNumber));
		}
	}

}
