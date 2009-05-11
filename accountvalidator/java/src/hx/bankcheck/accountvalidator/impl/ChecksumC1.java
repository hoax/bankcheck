/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;

import hx.bankcheck.accountvalidator.AbstractChecksumValidator;
import hx.bankcheck.accountvalidator.exceptions.ValidationException;
import hx.bankcheck.accountvalidator.utils.ChecksumUtils;

/**
 * Die Kontonummer ist einschlie�lich der Pr�fziffer 10-stellig, ggf. ist die
 * Kontonummer f�r die Pr�fzifferberechnung durch linksb�ndige Auff�llung mit
 * Nullen 10-stellig darzustellen.<br/>
 * 
 * Kontonummern, die an der 1. Stelle der 10-stelligen Kontonummer einen Wert
 * ungleich �5� beinhalten, sind nach der Variante 1 zu pr�fen. Kontonummern,
 * die an der 1. Stelle der 10-stelligen Kontonummer den Wert �5�beinhalten,
 * sind nach der Variante 2 zu pr�fen.<br/>
 * 
 * Variante 1: <br/>
 * 
 * Modulus 11, Gewichtung 1, 2, 1, 2, 1, 2<br/>
 * 
 * Die Berechnung und m�gliche Ergebnisse entsprechen der Methode 17. <br/>
 * 
 * F�hrt die Berechnung nach der Variante 1 zu einem Pr�fzifferfehler, so ist
 * die Kontonummer falsch.<br/>
 * 
 * Testkontonummern (richtig): 0446786040, 0478046940, 0701625830, 0701625840,
 * 0882095630<br/>
 * Testkontonummern (falsch): 0446786240, 0478046340, 0701625730, 0701625440,
 * 0882095130 <br/>
 * 
 * Variante 2: <br/>
 * 
 * Modulus 11, Gewichtung 1, 2, 1, 2, 1, 2<br/>
 * 
 * Die Kontonummer ist 10-stellig mit folgendem Aufbau: KNNNNNNNNP <br/>
 * K = Kontoartziffer <br/>
 * N = laufende Nummer <br/>
 * P = Pr�fziffer <br/>
 * 
 * F�r die Berechnung flie�en die Stellen 1 bis 9 ein. Stelle 10 ist die
 * ermittelte Pr�fziffer. Die Stellen 1 bis 9 sind von links nach rechts mit den
 * Ziffern 1, 2, 1, 2, 1, 2, 1, 2, 1 zu multiplizieren. Die jeweiligen Produkte
 * sind zu addieren, nachdem aus eventuell zweistelligen Produkten der 2., 4.,
 * 6. und 8. Stelle die Quersumme gebildet wurde. Von der Summe ist der Wert
 * �1�zu subtrahieren. Das Ergebnis ist dann durch 11 zu dividieren. Der
 * verbleibende Rest wird von 10 subtrahiert. Das Ergebnis ist die Pr�fziffer.
 * Verbleibt nach der Division durch 11 kein Rest, ist die Pr�fziffer 0. <br/>
 * 
 * Beispiel: <br/>
 * 
 * Stellen-Nr.: K N N N N N N N N P<br/>
 * Konto-Nr.: 5 4 3 2 1 1 2 3 4 9<br/>
 * Gewichtung: 1 2 1 2 1 2 1 2 1<br/>
 * 
 * 5 + 8 + 3 + 4 + 1 + 2 + 2 + 6 + 4 = 35 <br/>
 * 
 * 35 - 1 = 34<br/>
 * 
 * 34 : 11 = 3, Rest 1 10 - 1 = 9 (Pr�fziffer)<br/>
 * 
 * Testkontonummern richtig: 5432112349, 5543223456, 5654334563, 5765445670,
 * 5876556788<br/>
 * 
 * Testkontonummern falsch: 5432112341, 5543223458, 5654334565, 5765445672,
 * 5876556780<br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class ChecksumC1 extends AbstractChecksumValidator {
	
	// Weights from left to right
	private static final int[] WEIGHTS_ALTERNATIVE2 = { 1, 2, 1, 2, 1, 2, 1, 2,
			1 };
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hx.bankcheck.accountvalidator.AbstractChecksumValidator#validate(int[])
	 */
	@Override
	public boolean validate(int[] accountNumber) throws ValidationException {
		if (accountNumber[0] != 5) {
			setAlternative(0);
			return new Checksum17().validate(accountNumber);
		} else {
			setAlternative(1);
			return accountNumber[9]==calcChecksum(accountNumber);
		}
	}

	private int calcChecksum(int[] accountNumber) {
		int sum = 0;
		for (int i = 0; i < WEIGHTS_ALTERNATIVE2.length; i++) {
			if (((i+1) % 2) == 0) {
				sum += ChecksumUtils.qs(accountNumber[i]
						* WEIGHTS_ALTERNATIVE2[i]);
			} else {
				sum += accountNumber[i] * WEIGHTS_ALTERNATIVE2[i];
			}
		}
		return (((sum - 1) % 11) == 0) ? 0 : (10 - (sum - 1) % 11);
	}
}
