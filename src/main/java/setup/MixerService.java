package setup;

import javax.sound.sampled.AudioSystem;
import java.util.Arrays;
import java.util.stream.Stream;

public class MixerService {

    private static final String MIXERINFO_DELIM = " ";

    public static Stream<String> ListMixers() {
        return Arrays.stream(AudioSystem.getMixerInfo()).map( mixer -> {
            return new StringBuilder().append(mixer.getName()).append(MIXERINFO_DELIM)
                  .append(mixer.getVersion()).append(MIXERINFO_DELIM)
                  .append(mixer.getDescription()).toString();
        } );
    }

}
