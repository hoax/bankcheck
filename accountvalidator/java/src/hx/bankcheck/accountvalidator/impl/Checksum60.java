/**
 * 
 */
package hx.bankcheck.accountvalidator.impl;


/**
 * Modulus 10, Gewichtung 2, 1, 2, 1, 2, 1, 2 <br/>
 * 
 * Die Berechnung erfolgt wie bei Verfahren 00. Es ist jedoch zu beachten, dass
 * die zweistellige Unterkontonummer (Stellen 1 und 2) nicht in das
 * Pr�fziffernverfahren mit einbezogen werden darf. Die f�r die Berechnung
 * relevante siebenstellige Grundnummer befindet sich in den Stellen 3 bis 9,
 * die Pr�fziffer in der Stelle 10.<br/>
 * 
 * @author Sascha D�mer (sdo@lmis.de) - LM Internet Services AG
 * @version 1.0
 * 
 */
public class Checksum60 extends Checksum00 {

	private static final int[] WEIGHTS = { 0, 0, 2, 1, 2, 1, 2, 1, 2 };
	
	public Checksum60() {
		super(WEIGHTS);
	}

}
