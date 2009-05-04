package hx.bankcheck.accountvalidator;

/**
 * Die Kontonummer ist immer 10-stellig, ggf. ist die Kontonummer durch
 * linksb�ndige Auff�llung mit Nullen 10-stellig darzustellen.Die Stelle 10 der
 * Kontonummer ist per Definition die Pr�fziffer. Kontonummern, die nach
 * Durchf�hrung der unten n�her aufgef�hrten Berechnungs-methoden nicht zu einem
 * richtigen Ergebnis f�hren, sind nicht g�ltig.<br/>
 * 
 * Die f�r die Berechnung relevante Kundennummer (K) befindet sich bei der
 * Methode A in den Stellen 4 bis 9 der Kontonummer und bei den Methoden B bis E
 * in den Stellen 5 bis 9.<br/> *
 * 
 * <b>Ausnahme:</b><br/>
 * 
 * Ist nach linksb�ndigem Auff�llen mit Nullen auf 10 Stellen die 3. Stelle der
 * Kontonummer = 9 (Sachkonten) befindet sich die f�r die Berechnung relevante
 * Sachkontonummer (S) in den Stellen 3 bis 9. Diese Kontonummern sind
 * ausschlie�lich nach Methode F zu pr�fen.<br/>
 * 
 * <b>Kundenkonten</b><br/>
 * Kundenkonten haben im Gegensatz zu Sachkonten an der Stelle 3 nicht die
 * Ziffer 9 stehen.<br/>
 * 
 * Ergibt die Berechnung der Pr�fziffer nach dem Verfahren A einen
 * Pr�fzifferfehler, so sind weitere Berechnungen mit den Methoden B bis
 * Evorzunehmen. Kundenkontonummern, die nach Durchf�hrung aller
 * Berechnungsmethoden A bis E nicht zu einem richtigen Ergebnis f�hren, sind
 * nicht g�ltig.<br/>
 * 
 * <b>Methode A: </b><br/>
 * Modulus 11, Gewichtung 2, 3, 4, 5, 6, 7<br/>
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10) <br/>
 * Kontonr.: x x x K K K K K K P <br/>
 * Gewichtung: 7 6 5 4 3 2 <br/>
 * 
 * Die Berechnung und m�gliche Ergebnisse entsprechen dem Verfahren 06.
 * 
 * Testkontonummern:<br/>
 * 
 * richtig: 0001975641, 0001988654<br/>
 * falsch: 0001924592 <br/>
 * falsch: 0000654321 (testbar nach Methode C)<br/>
 * 
 * <b>Methode B </b><br/>
 * 
 * Modulus 11, Gewichtung 2, 3, 4,5, 6 <br/>
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10)<br/>
 * Kontonr.: x x x x K K K K K P<br/>
 * Gewichtung: 6 5 4 3 2<br/>
 * 
 * Die Berechnung und die m�glichen Ergebnisse entsprechen dem Verfahren 06.<br/>
 * 
 * Testkontonummern:<br/>
 * 
 * richtig: 0000863530, 0000784451<br/>
 * falsch: 0000901568 <br/>
 * falsch: 0000997664 (testbar nach Methode C)<br/>
 * falsch: 0000863536 (testbar nach Methode D)
 * 
 * <b>Methode C </b><br/>
 * 
 * Modulus 7, Gewichtung 2, 3, 4, 5, 6<br/>
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10) Kontonr.: x x x x K K K K K P<br/>
 * Gewichtung: 6 5 4 3 2 <br/>
 * 
 * Die einzelnen Stellen der Kontonummer sind von rechts nach links mit den
 * Gewichten zu multiplizieren. Die jeweiligen Prdukte werden addiert. Die Summe
 * der Produkte ist durch 7 zu dividieren. Der verbleibende Rest wird vom
 * Divisor (7) subtrahiert. Das Ergebnis ist die Pr�fziffer. Verbleibt kein
 * Rest, ist die Pr�fziffer 0. <br/>
 * 
 * Kontonummern, die in der Stelle 10 die Werte 7, 8 oder 9 haben, sind nach
 * dieser Methode nicht g�ltig.<br/>
 * 
 * Testkontonummern:<br/>
 * richtig: 0000654321, 0000824491<br/>
 * falsch: 0000820487 <br/>
 * falsch: 0000820484 (testbar nach Methode D)<br/>
 * falsch: 0000654328 (testbar nach Methode E) <br/>
 * 
 * <b>Methode D</b><br/>
 * 
 * Modulus 9, Gewichtung 2, 3, 4, 5, 6 <br/>
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10)<br/>
 * Kontonr.: x x x x K K K K K P<br/>
 * Gewichtung: 6 5 4 3 2<br/>
 * 
 * Die einzelnen Stellen der Kontonummer sind von rechts nach links mit den
 * Gewichten zu multiplizieren. Die jeweiligen Produkte werden addiert. Die
 * Summe der Produkte ist durch 9 zu dividieren. Der verbleibende Rest wird vom
 * Divisor (9) subtrahiert. Das Ergebnis ist die Pr�fziffer. Verbleibt kein
 * Rest, ist die Pr�fziffer 0.<br/>
 * 
 * Kontonummern, die an der Stelle 10 den Wert 9 haben sind nach dieser Methode
 * nicht g�ltig.<br/>
 * 
 * Testkontonummern: <br/>
 * 
 * richtig: 0000677747, 0000840507 <br/>
 * falsch: 0000726393 <br/>
 * falsch: 0000677742 (testbar nach Methode E) <br/>
 * falsch: 0000726391 (testbar nach Methode E) <br/>
 * 
 * <b>Methode E</b><br/>
 * 
 * Modulus 10, Gewichtung 2, 1, 2, 1, 2 <br/>
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10)<br/>
 * Kontonr.: x x x x K K K K K P<br/>
 * Gewichtung: 2 1 2 1 2 <br/>
 * 
 * Die einzelnen Stellen der Kontonummer sind von rechts nach links mit den
 * Gewichten zu multiplizieren. Die jeweiligen Produkte werden addiert. Die
 * Summe der Produkte ist durch 10 zu dividieren. Der verbleibende Rest wird vom
 * Divisor (10) subtrahiert. Das Ergebnis ist die Pr�fziffer. Verbleibt kein
 * Rest, ist die Pr�fziffer 0.<br/>
 * 
 * Testkontonummern: <br/>
 * 
 * richtig: 0000996663, 0000666034<br/>
 * falsch: 0000924591 <br/>
 * 
 * <b>Sachkonten </b><br/>
 * 
 * Sachkonten haben im Gegensatz zu Kundenkonten an der Stelle 3 die Ziffer 9
 * stehen.<br/>
 * 
 * <b>Methode F</b><br/>
 * 
 * Modulus 11, Gewichtung 2, 3, 4, 5, 6, 7, 8 <br/>
 * Stellennr.: 1 2 3 4 5 6 7 8 9 A (A = 10) <br/>
 * Kontonr.: x x S S S S S S S P <br/>
 * Gewichtung: 8 7 6 5 4 3 2 <br/>
 * 
 * Die Berechnung und die m�glichen Ergebnisse entsprechen dem Verfahren 06. Es
 * ist jedoch die vorgenannte Gewichtung zu beachten.
 * 
 * Testkontonummern: <br/>
 * 
 * richtig: 0099100002<br/>
 * falsch: 0099100007<br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class Checksum90 implements IAccountChecksum {

	// Weights from left to right
	private static final int[] WEIGTHS_A = { 7, 6, 5, 4, 3, 2 };
	private static final int[] WEIGTHS_B = { 6, 5, 4, 3, 2 };
	private static final int[] WEIGTHS_C = { 6, 5, 4, 3, 2 };
	private static final int[] WEIGTHS_D = { 6, 5, 4, 3, 2 };
	private static final int[] WEIGTHS_E = { 2, 1, 2, 1, 2 };
	private static final int[] WEIGTHS_F = { 8, 7, 6, 5, 4, 3, 2 };

	private int methodFlag = 0;

	/**
	 * @see hx.bankcheck.accountvalidator.IAccountChecksum
	 */
	@Override
	public int calcChecksum(int[] accountNumber) {
		if (accountNumber[2] == 9) {
			methodFlag = 5;
			return calcChecksumMethodF(accountNumber);
		} else {
			switch (methodFlag) {
			case 0:
				return calcChecksumMethodA(accountNumber); // Method A
			case 1:
				return calcChecksumMethodB(accountNumber); // Method B
			case 2:
				return calcChecksumMethodC(accountNumber); // Method C
			case 3:
				return calcChecksumMethodD(accountNumber); // Method D
			case 4:
				return calcChecksumMethodE(accountNumber); // Method E
			default:
				return -1;
			}
		}
	}

	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		methodFlag = 0;
		int[] filledAccountNumber = ChecksumUtils.getFilledAcountNumber(10,
				accountNumber);
		while (methodFlag < 6) {
			if (accountNumber[accountNumber.length - 1] == calcChecksum(filledAccountNumber)) {
				return true;
			}
			methodFlag++;
		}
		return false;
	}

	private int calcChecksumMethodA(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < 6; i++) {
			sum += accountNumber[i + 3] * WEIGTHS_A[i];
		}
		return (sum % 11 == 1) || (sum % 11 == 0) ? 0 : (11 - sum % 11);
	}

	private int calcChecksumMethodB(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			sum += accountNumber[i + 4] * WEIGTHS_B[i];
		}
		return (sum % 11 == 1) || (sum % 11 == 0) ? 0 : (11 - sum % 11);
	}

	private int calcChecksumMethodC(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			sum += accountNumber[i + 4] * WEIGTHS_C[i];
		}
		return (sum % 7 == 0) ? 0 : (7 - sum % 7);
	}

	private int calcChecksumMethodD(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			sum += accountNumber[i + 4] * WEIGTHS_D[i];
		}
		return (sum % 9 == 0) ? 0 : (9 - sum % 9);
	}

	private int calcChecksumMethodE(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			sum += accountNumber[i + 4] * WEIGTHS_E[i];
		}
		return (sum % 10 == 0) ? 0 : (10 - sum % 10);
	}

	private int calcChecksumMethodF(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < 7; i++) {
			sum += accountNumber[i + 2] * WEIGTHS_F[i];
		}
		return (sum % 11 == 1) || (sum % 11 == 0) ? 0 : (11 - sum % 11);
	}

	public int getMethodFlag() {
		return this.methodFlag;
	}

}
