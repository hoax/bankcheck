package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.ChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.IllegalAccountNumberException;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;
import hx.bankcheck.accountvalidator.utils.ChecksumUtils;

/**
 * Modulus 11, Gewichtung 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4 <br/>
 * 
 * Zur Berechnung der Prüfziffer muss zunächst aus der angegebenen Bankleitzahl
 * und der angegebenen achtstelligen Kontonummer die zugehörige Kontonummer des
 * ESER-Altsystems (maximal 12-stellig) ermittelt werden.<br/>
 * 
 * Die einzelnen Stellen dieser Alt-Kontonummer sind von rechts nach links mit
 * den Ziffern 2, 4, 8, 5, 10, 9, 7, 3, 6, 1, 2, 4 zu multiplizieren. Dabei ist
 * für die Prüfziffer, die sich immer an der 6. Stelle von links der
 * Alt-Kontonummer befindet, 0 zu setzen.<br/>
 * 
 * Die jeweiligen Produkte werden addiert und die Summe durch 11 dividiert. Zum
 * Divisionsrest (ggf. auch 0) ist das Gewicht oder ein Vielfaches des Gewichtes
 * über der Prüfziffer zu addieren. Die Summe wird durch 11 dividiert; der
 * Divisionsrest muss 10 lauten. Die Prüfziffer ist der verwendete Faktor des
 * Gewichtes. Kann bei der Division kein Rest 10 erreicht werden, ist die
 * Konto-Nr. nicht verwendbar.
 * 
 * Bildung der Konto-Nr. des ESER-Altsystems aus angegebener Bankleitzahl und
 * Konto-Nr.: <br/>
 * 
 * BLZ Konto-Nr. XXX5XXXX XPXXXXXX (P = Prüfziffer) Kontonummer des Altsystems:
 * XXXX-XP-XXXXX (XXXX = variable Länge, da evtl. vorlaufende Nullen elimi-niert
 * werden) <br/>
 * 
 * Bei 10-stelligen, mit 9 beginnenden Kontonummern ist die Prüfziffer nach
 * Verfahren 20 zu berechnen.<br/>
 * 
 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class Checksum52 implements ChecksumValidator {
	
	// Weights from left to right
	private static final int[] WEIGHTS = { 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	private int checksumDigitIndex = -1;
	private int[] weights;

	public Checksum52(){
		this(WEIGHTS);
	}
	
	public Checksum52(int[] weights){
		this.weights=weights;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see hx.bankcheck.accountvalidator.ChecksumValidator#validate(int[],
	 * int[])
	 */
	@Override
	public boolean validate(int[] accountNumber, int[] bankNumber)
			throws ValidationException {
		if (accountNumber[0] == 9) {
			return new Checksum20().validate(accountNumber);
		} else {
			int[] eserAccountNumber = generateEserAccountNumber(accountNumber,
					bankNumber);
			int[] tmpAccountNumber = resetChecksumDigit(eserAccountNumber, 6);
			if (getChecksumDigitIndex() != -1) {
				return eserAccountNumber[getChecksumDigitIndex()] == calcChecksum(tmpAccountNumber);
			} else {
				throw new ValidationException(
						"Checksum couldn't be checked, because position of checksum digit is not valid!");
			}
		}
	}

	protected int calcChecksum(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += accountNumber[i] * weights[i];
		}
		int offcut = sum % 11;
		for (int i = 0; i < 11; i++) {
			if ((offcut + (i * weights[getChecksumDigitIndex()]) % 11) == 10) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Generates the account number for the ESER system.
	 * 
	 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
	 * @version 1.0
	 * 
	 * @param accountNumber
	 *            The account number.
	 * @param bankNumber
	 *            The bank number.
	 * @return The account number for the ESER system
	 * @throws ValidationException
	 */
	public int[] generateEserAccountNumber(int[] accountNumber, int[] bankNumber)
			throws IllegalAccountNumberException {
		long eserAccountNumber = 0l;
		if ((accountNumber[0] != 0) || (accountNumber[1] != 0)
				|| (accountNumber[2] == 0)) {
			throw new IllegalAccountNumberException(
					"Account number for generating old ESER-System account number need to have 8 relevant digits. First two digits have to be 0, third digit has to be between 1 and 9.");
		} else {
			// First 4 digits are the last 4 digits of the bank number
			for (int i = 0; i < 4; i++) {
				eserAccountNumber *= 10;
				eserAccountNumber += bankNumber[(bankNumber.length) - (4 - i)];
			}

			// ESER account number at 4,5 = first two digits of the account
			// number
			// ESER account number is filled up with the rest of the account
			// number. Leading digits which are 0, are skipped.
			boolean foundDigitNotZero = false;
			for (int i = 2; i < accountNumber.length; i++) {
				if ((i == 2) || (i == 3)
						|| ((accountNumber[i] == 0) && (foundDigitNotZero))
						|| (accountNumber[i] != 0)) {
					eserAccountNumber *= 10;
					eserAccountNumber += accountNumber[i];
					if ((i != 2) && (i != 3)) {
						foundDigitNotZero = true;
					}
				}
			}

		}

		return ChecksumUtils.parseEserAccountNumber(eserAccountNumber);
	}

	/**
	 * Resets the checksum digit at the given position to 0.
	 * 
	 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
	 * @version 1.0
	 * 
	 * @param accountNumber
	 * @param checksumDigitPositon
	 * @return
	 */
	public int[] resetChecksumDigit(int[] accountNumber,
			int checksumDigitPositon) {
		int[] tmpAccountNumber = accountNumber.clone();
		int j = 0;
		for (int i = 0; i < tmpAccountNumber.length; i++) {
			if ((tmpAccountNumber[i] != 0) && (j >= 0)) {
				j++;
			}
			if (j == checksumDigitPositon) {
				tmpAccountNumber[i] = 0;
				setChecksumDigitIndex(i);
			}
		}
		return tmpAccountNumber;
	}

	/**
	 * @param checksumDigitIndex
	 *            the checksumDigitIndex to set
	 */
	public void setChecksumDigitIndex(int checksumDigitIndex) {
		this.checksumDigitIndex = checksumDigitIndex;
	}

	/**
	 * @return the checksumDigitIndex
	 */
	public int getChecksumDigitIndex() {
		return checksumDigitIndex;
	}

	/**
	 * @param weights the weights to set
	 */
	public void setWeights(int[] weights) {
		this.weights = weights;
	}

	/**
	 * @return the weights
	 */
	public int[] getWeights() {
		return weights;
	}

	@Override
	public int getAlternative() {
		return 0;
	}

	@Override
	public boolean isException() {
		return false;
	}

}
