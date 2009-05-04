package hx.bankcheck.accountvalidator;

import static org.junit.Assert.assertArrayEquals;
import hx.bankcheck.accountvalidator.utils.ChecksumUtils;
import junit.framework.TestCase;

import org.junit.Test;

public class ChecksumUtilsTest extends TestCase {

	@Test
	public void testGetFilledAcountNumber() {
		int[] orignalAccountNumber = { 1, 2, 3, 4, 5, 6 };
		int sizeOfAccountNumber = 10;
		int[] actuals = ChecksumUtils.getFilledAcountNumber(
				sizeOfAccountNumber, orignalAccountNumber);
		int[] expecteds = { 0, 0, 0, 0, 1, 2, 3, 4, 5, 6 };

		assertArrayEquals(expecteds, actuals);

		actuals = ChecksumUtils.getFilledAcountNumber(
				orignalAccountNumber.length, orignalAccountNumber);

		assertArrayEquals(orignalAccountNumber, actuals);

		actuals = ChecksumUtils.getFilledAcountNumber(0, orignalAccountNumber);

		assertArrayEquals(orignalAccountNumber, actuals);

	}

	@Test
	public void testQs() {
		int a = 0; // should return 0
		int b = 2; // should return 2
		int c = 13; // should return 4
		int d = 93; // should return 12
		int e = 1234567; // should return 28

		assertTrue(ChecksumUtils.qs(a) == 0);
		assertTrue(ChecksumUtils.qs(b) == 2);
		assertTrue(ChecksumUtils.qs(c) == 4);
		assertTrue(ChecksumUtils.qs(d) == 12);
		assertTrue(ChecksumUtils.qs(e) == 28);

		assertTrue(ChecksumUtils.qs(-a) == -0);
		assertTrue(ChecksumUtils.qs(-b) == -2);
		assertTrue(ChecksumUtils.qs(-c) == -4);
		assertTrue(ChecksumUtils.qs(-d) == -12);
		assertTrue(ChecksumUtils.qs(-e) == -28);

		ChecksumUtils.qs(Integer.MAX_VALUE);
		ChecksumUtils.qs(-Integer.MAX_VALUE);

	}
	
	@Test
	public void testparseAccountNumber(){
		Long a=new Long("0000000012");
		Long b=new Long("0123456789");
		Long c=new Long("12345");
		
		int[] a_expected={1,2};
		int[] b_expected={1,2,3,4,5,6,7,8,9};
		int[] c_expected={1,2,3,4,5};
		
		assertArrayEquals(a_expected, ChecksumUtils.parseAccountNumber(a.longValue()));
		assertArrayEquals(b_expected, ChecksumUtils.parseAccountNumber(b.longValue()));
		assertArrayEquals(c_expected, ChecksumUtils.parseAccountNumber(c.longValue()));
	}
	
}
