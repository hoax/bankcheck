package hx.bankcheck.accountvalidator.utils;

import hx.bankcheck.accountvalidator.exceptions.IllegalAccountNumberException;

public class ChecksumUtils {

	/**
	 * Calculates the crossfoot of a number
	 * 
	 * @param number
	 * @return
	 */
	public static int qs(int number) {
		int qs = 0;

		while (number != 0) {
			qs = qs + (number % 10);
			number = number / 10;
		}

		return qs;
	}

	/**
	 * Fills up the account number by adding '0' to the left until size is
	 * reached.
	 * 
	 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
	 * @version 1.0
	 * 
	 * @param sizeOfAccountNumber
	 *            new size of the account number
	 * @param orignalAccountNumber
	 *            account number to be filled
	 * @return array containing the new account number
	 */
	public static int[] getFilledAcountNumber(int sizeOfAccountNumber,
			int[] orignalAccountNumber) {
		if (sizeOfAccountNumber > orignalAccountNumber.length) {
			int[] filledAccountNumber = new int[sizeOfAccountNumber];
			int offset = sizeOfAccountNumber - orignalAccountNumber.length;
			for (int i = 0; i < orignalAccountNumber.length; i++) {
				filledAccountNumber[i + offset] = orignalAccountNumber[i];
			}
			return filledAccountNumber;
		} else
			return orignalAccountNumber;
	}

	/**
	 * Parses the given account number as long value for comparing it.
	 * 
	 * @author Tobias Mayer (bankcheck@tobiasm.de)
	 * 
	 * @param acccountNumber
	 *            account number to parse
	 * @return long value of the given account number
	 */
	public static long parseLong(int[] accountNumber) {
		long l = 0l;
		for (int i = 0; i < accountNumber.length; i++) {
			l *= 10;
			l += accountNumber[i];
		}
		return l;
	}

	/**
	 * Parses the given account number as int[].
	 * 
	 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
	 * @version 1.0
	 * 
	 * @param accountNumberAsLong
	 *            account number to parse
	 * @return int[] containing the account number
	 * @throws IllegalAccountNumberException
	 *             thrown when accountNumber has too much digits
	 */
	public static int[] parseAccountNumber(long accountNumberAsLong)
			throws IllegalAccountNumberException {
		int[] result = new int[10];
		int pos = 0;
		do {
			int intValue = (int) (accountNumberAsLong % 10);
			result[9 - pos] = intValue;
			accountNumberAsLong /= 10;
			pos++;
		} while (accountNumberAsLong != 0 && pos < 10);

		if (accountNumberAsLong > 0)
			throw new IllegalAccountNumberException(
					"Accountnumber has more than 10 digits");

		return result;
	}

	/**
	 * Parses the given BLZ as int[].
	 * 
	 * @param blzAsLong
	 *            blz to parse
	 * @return int[] containing the blz
	 */
	public static int[] parseBlz(long blzAsLong) {
		int[] result = new int[8];
		int pos = 0;
		do {
			int intValue = (int) (blzAsLong % 10);
			result[7 - pos] = intValue;
			blzAsLong /= 10;
			pos++;
		} while (blzAsLong != 0 && pos < 8);

		return result;
	}

	/**
	 * Parses the given ESER system account number as int[].
	 * 
	 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
	 * @version 1.0
	 * 
	 * @param accountNumberAsLong
	 *            account number to parse
	 * @return int[] containing the account number
	 * @throws IllegalAccountNumberException
	 *             thrown when accountNumber has too much digits
	 */
	public static int[] parseEserAccountNumber(long accountNumberAsLong)
			throws IllegalAccountNumberException {
		int[] result = new int[12];
		int pos = 0;
		do {
			int intValue = (int) (accountNumberAsLong % 10);
			result[11 - pos] = intValue;
			accountNumberAsLong /= 10;
			pos++;
		} while (accountNumberAsLong != 0 && pos < 12);

		if (accountNumberAsLong > 0)
			throw new IllegalAccountNumberException(
					"ESER account number has more than 12 digits");

		return result;
	}

	/**
	 * Returns the number of neutral(=0) leading digits.
	 * 
	 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
	 * @version 1.0
	 * 
	 * @param accountNumber
	 * @return
	 */
	public static int countNeutralLeadingDigits(int[] accountNumber) {
		int c = 0;
		int i = 0;
		while (accountNumber[i] == 0) {
			c++;
			i++;
		}
		return c;
	}

	/**
	 * Returns the difference to the next half decade (5,15,25,35,...)
	 * 
	 * @author Sascha Dömer (sdo@lmis.de) - LM Internet Services AG
	 * @version 1.0
	 * 
	 * @param number
	 *            The Number
	 * @return The difference
	 */
	public static int getDiffToHalfDecade(int number) {
		return ((number % 10) <= 5) ? (5 - (number % 10))
				: (10 - ((number % 10) % 5));
	}

	/**
	 * Calculates the sum of the product made by multiplying each field of
	 * accountnumber with the weight at the same index.
	 * <pre><code>sum = account[start] * weight[start] + account[start+1] * weight[start+1] + ... + account[end] * weight[end]</code></pre>
	 * @author Tobias Mayer (bankcheck@tobiasm.de)
	 * 
	 * @param accountNumber
	 * @param weights
	 * @return sum of products
	 */
	public static int calcWeightedSum(int[] accountNumber, int[] weights, int start, int end) {
		int sum = 0;
		for(int i=start; i<=end; i++) {
			sum += accountNumber[i] * weights[i];
		}
		
		return sum;
	}
}
