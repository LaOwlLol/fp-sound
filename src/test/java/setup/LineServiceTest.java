package setup;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LineServiceTest {
    @Test public void testSystemSourceDataLine() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        LineService.SystemSourceDataLine(FormatService.PREFERRED_FORMAT).ifPresent(line -> {
                  System.out.println(LineService.LineDetails(line));
                  assert(!outContent.toString().isEmpty());
                  assert(errContent.toString().isEmpty());
              }
        );

        System.setOut(System.out);
        System.setErr(System.err);
    }
}
