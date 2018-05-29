package engine;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioDirectoryTest {

    @Test
    public void testKeys() {
        if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"))) {
            AudioDirectory ad = new AudioDirectory(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"));

            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp"))) {
                assert (!ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp.wa"))) {
                assert (!ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp.wa")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp.wav"))) {
                assert (ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp.wav")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp.wave"))) {
                assert (ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp.wave")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/test.wav"))) {
                assert (ad.keys().anyMatch(key -> key.equalsIgnoreCase("test.wav")));
            }

        }
    }

    @Test
    public void testGetAudio() {
        if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"))) {
            AudioDirectory ad = new AudioDirectory(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"));

            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp"))) {
                assert (!ad.get("harp").isPresent());
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/test.wav"))) {
                assert (ad.get("test.wav").isPresent());
            }
        }
    }

}
