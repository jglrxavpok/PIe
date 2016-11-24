import org.jglrxavpok.pie.PIeParser;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestParser {

    @Test
    public void numberParsing() {
        PIeParser parser = new PIeParser();
        assertArrayEquals(new int[] {256, 1}, parser.parse("e1125,3 π0"));
    }
}
