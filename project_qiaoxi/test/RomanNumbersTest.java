
            import static org.junit.Assert.*;
            import org.junit.*;

            public class RomanNumbersTest {


            @Test
            public void dieZahlEinsErgibtI() {
            RomanNumbers a = new RomanNumbers();
            String berechnet = a.roman(1);
            String erwartet = "I";
            assertEquals(erwartet, berechnet);
            }
            }
        