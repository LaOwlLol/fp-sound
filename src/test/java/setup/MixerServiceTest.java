package setup;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MixerServiceTest {


    @Test public void testSystemMixers() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        MixerService.MixerDetails(MixerService.SystemMixers()).forEach(System.out::println);
        assert(!outContent.toString().isEmpty());
        assert(errContent.toString().isEmpty());

        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test public void testAppropriateMixers() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        MixerService.MixerDetails(MixerService.AppropriateMixers()).forEach(System.out::println);
        assert(!outContent.toString().isEmpty());
        assert(errContent.toString().isEmpty());

        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test public void testSpeakerPort() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        MixerService.SpeakerPort().ifPresent(line -> {
            System.out.println(MixerService.LineDetails(line));
            assert(!outContent.toString().isEmpty());
            assert(errContent.toString().isEmpty());
            }
        );

        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test public void testLineOutPort() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        MixerService.LineOutPort().ifPresent(line -> {
                  System.out.println(MixerService.LineDetails(line));
                  assert(!outContent.toString().isEmpty());
                  assert(errContent.toString().isEmpty());
              }
        );

        System.setOut(System.out);
        System.setErr(System.err);
    }

}
