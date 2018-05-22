package setup;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.util.Arrays;
import java.util.stream.Stream;

public class MixerService {

    private static final String INFO_DELIMITER = " - ";

    public static Stream<String> ListMixers() {
        return Arrays.stream(AudioSystem.getMixerInfo()).map( mixer ->
              mixer.getName() + INFO_DELIMITER +
              mixer.getVersion() + INFO_DELIMITER +
              mixer.getDescription());
    }

}
