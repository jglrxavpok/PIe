import org.jglrxavpok.pie.io.Decimals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDecimal {

    @Test
    public void testReadSinglePIDigit() {
        Decimals piDecimals = new Decimals("pi_digits.txt");
        assertEquals(1, piDecimals.getDecimal(0));
        assertEquals(4, piDecimals.getDecimal(1));
    }

    @Test
    public void testReadNumberInE() {
        Decimals eDecimals = new Decimals("e_digits.txt");
        assertEquals(256, eDecimals.readNumber(1125, 3));
    }
}
