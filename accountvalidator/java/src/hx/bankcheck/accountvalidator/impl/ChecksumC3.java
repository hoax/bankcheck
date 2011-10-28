/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;

/**
 * Die Kontonummer ist einschlie�lich der Pr�fziffer 10-stellig, ggf. ist die
 * Kontonummer f�r die Pr�fzifferberechnung durch linksb�ndige Auff�llung mit
 * Nullen 10-stellig darzustellen. Die 10. Stelle der Kontonummer ist die
 * Pr�fziffer. <br/>
 * 
 * Kontonummern, die an der 1. Stelle der 10-stelligen Kontonummer einen Wert
 * ungleich �9� beinhalten, sind nach der Variante 1 zu pr�fen. Kontonummern,
 * die an der 1. Stelle der 10-stelligen Kontonummer den Wert �9�beinhalten,
 * sind nach der Variante 2 zu pr�fen. <br/>
 * 
 * <b>Variante 1:</b> <br/>
 * 
 * Modulus 10, Gewichtung 2, 1, 2, 1, 2, 1, 2, 1, 2 <br/>
 * 
 * Die Berechnung und m�gliche Ergebnisse entsprechen der Methode 00. <br/>
 * 
 * Testkontonummern (richtig): 9294182, 4431276, 19919 <br/>
 * Testkontonummern (falsch): 17002, 123451, 122448 <br/>
 * 
 * <b>Variante 2:</b> <br/>
 * 
 * Modulus 11, Gewichtung 2, 3, 4, 5, 6, 0, 0, 0, 0 <br/>
 * 
 * Die Berechnung und m�gliche Ergebnisse entsprechen der Methode 58. <br/>
 * 
 * Testkontonummern (richtig): 9000420530, 9000010006, 9000577650 <br/>
 * Testkontonummern (falsch): 9000734028, 9000733227, 9000731120 <br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class ChecksumC3 extends AbstractChecksumValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hx.bankcheck.accountvalidator.AbstractChecksumValidator#validate(int[])
	 */
	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		if(accountNumber[0]!=9){
			setAlternative(0);
			return new Checksum00().validate(accountNumber);
		}else{
			setAlternative(1);
			return new Checksum58().validate(accountNumber);
		}
	}

}
