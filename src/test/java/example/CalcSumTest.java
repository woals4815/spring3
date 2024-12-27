package example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CalcSumTest {
    private CalcSum calcSum = new CalcSum();
    @Test
    public void testSum() throws IOException {
        assertEquals(10, calcSum.getSum(
                "src/main/resources/numbers.txt"
        ));
    }
    @Test
    public void testConcat() throws IOException {
        assertEquals("1234", calcSum.concat("src/main/resources/numbers.txt"));
    }
}