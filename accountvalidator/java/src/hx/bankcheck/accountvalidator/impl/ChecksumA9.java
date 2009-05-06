package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;

/**
 * 
 * Die Kontonummer ist einschlie�lich der Pr�fziffer 10-stellig, ggf. ist die
 * Kontonummer f�r die Pr�fzifferberechnung durch linksb�ndige Auff�llung mit
 * Nullen 10-stellig darzustellen.
 * 
 * <b>Variante 1: </b><br/>
 * 
 * Modulus 10, Gewichtung 3, 7, 1, 3, 7, 1, 3, 7, 1<br/>
 * 
 * Gewichtung und Berechnung erfolgen nach der Methode 01. F�hrt die Berechnung
 * nach Variante 1 zu einem Pr�fzifferfehler, so ist nach Variante 2 zu pr�fen.<br/>
 * 
 * Testkontonummern (richtig): 5043608, 86725<br/>
 * Testkontonummern (falsch): 504360, 822035, 32577083, 86724 <br/>
 * 
 * <b>Variante 2:</b><br/>
 * Modulus 11, Gewichtung 2, 3, 4, 5, 6, 7, 2, 3, 4
 * 
 * Gewichtung und Berechnung erfolgen nach der Methode 06. <br/>
 * 
 * Testkontonummern (richtig): 504360, 822035, 32577083 <br/>
 * Testkontonummern (falsch): 86724, 292497, 30767208<br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class ChecksumA9 extends AbstractChecksumValidator {

	private int alternative = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hx.bankcheck.accountvalidator.ChecksumValidator#validate(int[])
	 */
	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		setAlternative(0);
		if (new Checksum01().validate(accountNumber)) {
			return true;
		} else {
			setAlternative(1);
			return new Checksum06().validate(accountNumber);
		}
	}

	/**
	 * @param alternative
	 *            the alternative to set
	 */
	public void setAlternative(int alternative) {
		this.alternative = alternative;
	}

	/**
	 * @return the alternative
	 */
	public int getAlternative() {
		return alternative;
	}

}
