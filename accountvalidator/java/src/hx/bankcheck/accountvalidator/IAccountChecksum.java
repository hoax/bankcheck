package hx.bankcheck.accountvalidator;

public interface IAccountChecksum {

	/**
	 * Checks an account number for correctness
	 * @param accountNumber The 10-Digit accountNumber, right aligned.
	 * @return
	 */
	public boolean validate(int[] accountNumber) throws ValidationException;
	
	/**
	 * Calculates the correct checksum for the given number
	 */
	public int calcChecksum(int[] accountNumber);
	
}
