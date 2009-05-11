/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.ChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;
import hx.bankcheck.accountvalidator.utils.ChecksumUtils;

/**
 * Die Kontonummer ist einschlie�lich der Pr�fziffer 10-stellig, ggf. ist die
 * Kontonummer f�r die Pr�fzifferberechnung durch linksb�ndige Auff�llung mit
 * Nullen 10-stellig darzustellen. <br/>
 * 
 * Kontonummern mit zwei f�hrenden Nullen sind nach Variante 1 zu pr�fen. F�hrt
 * die Berechnung nach der Variante 1 zu einem Pr�fzifferfehler, ist die
 * Berechnung nach Variante 2 vorzunehmen. Kontonummern mit weniger oder mehr
 * als zwei f�hrenden Nullen sind ausschlie�lich nach der Variante 2 zu pr�fen. <br/>
 * 
 * <b>Variante 1: </b><br/>
 * 
 * Modulus 11, Gewichtung 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4 <br/>
 * 
 * Die Berechnung und m�gliche Ergebnisse entsprechen der Methode 52. <br/>
 * 
 * Testkontonummern (richtig) mit BLZ 130 511 72: 43001500, 48726458 <br/>
 * Testkontonummern (falsch) mit BLZ 130 511 72: 82335729, 29837521 <br/>
 * 
 * <b>Variante 2: </b><br/>
 * 
 * Modulus 11, Gewichtung 2, 3, 4, 5, 6, 7, 8, 9,3 <br/>
 * 
 * Die Berechnung und m�gliche Ergebnisse entsprechen der Methode 20.<br/>
 * 
 * Testkontonummern (richtig): 0082335729, 0734192657, 6932875274<br/>
 * Testkontonummern (falsch): 0132572975, 3038752371<br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class ChecksumC0 implements ChecksumValidator {

	// Weights from left to right
	private static final int[] WEIGHTS_ALTERNATIVE1 = { 4, 2, 1, 6, 3, 7, 9,
			10, 5, 8, 4, 2 };
	private static final int[] WEIGHTS_ALTERNATIVE2 = { 3, 9, 8, 7, 6, 5, 4, 3,
			2 };

	private int alternative = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hx.bankcheck.accountvalidator.ChecksumValidator#validate(int[],
	 * int[])
	 */
	@Override
	public boolean validate(int[] accountNumber, int[] bankNumber)
			throws ValidationException {
		int leadingNeutralDigits = ChecksumUtils
				.countNeutralLeadingDigits(accountNumber);
		if (leadingNeutralDigits == 2) {
			setAlternative(0);
			if(new Checksum52(WEIGHTS_ALTERNATIVE1).validate(accountNumber, bankNumber)){
				return true;
			}else{
				setAlternative(1);
				return new Checksum20(WEIGHTS_ALTERNATIVE2).validate(accountNumber);
			}
		}else{
			setAlternative(1);
			return new Checksum20(WEIGHTS_ALTERNATIVE2).validate(accountNumber);
		}
	}

	/**
	 * @param alternative
	 *            the alternative to set
	 */
	public void setAlternative(int alternative) {
		this.alternative = alternative;
	}

	@Override
	public int getAlternative() {
		return alternative;
	}

	@Override
	public boolean isException() {
		return false;
	}

}
