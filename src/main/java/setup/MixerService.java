package setup;

import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Provides access to streams of available system Mixers and Mixer's details.
 */
public class MixerService {

    private static final String INFO_LABEL = ": ";
    private static final String INFO_DELIMITER = " - ";

    /**
     * Get a stream of details (properties in Mixer.Info) form a stream of Mixers.
     * @param mixers A stream of mixer objects to turn into details.
     * @return A stream of Strings describing the Mixer provided.
     */
    public static Stream<String> MixerDetails(Stream<Mixer> mixers) {
        return mixers.map( mixer -> "Name" + INFO_LABEL + mixer.getMixerInfo().getName() + INFO_DELIMITER +
              "Ver" + INFO_LABEL + mixer.getMixerInfo().getVersion() + INFO_DELIMITER +
              "Desc" + INFO_LABEL + mixer.getMixerInfo().getDescription());
    }

    /**
     * Filter system mixers into a stream of supported Mixers.
     * @return A stream if mixers that support the preferred AudioFormat (have SourceDataLine).
     */
    public static Stream<Mixer> AppropriateMixers() {
        return SystemMixers().filter(mixer ->
              mixer.isLineSupported(new DataLine.Info(SourceDataLine.class, FormatService.PREFERRED_FORMAT))
        );
    }

    /**
     * Get all mixers available to the Java Audio System.
     * @return A stream of Mixers.
     */
    public static Stream<Mixer> SystemMixers() {
        return Arrays.stream(AudioSystem.getMixerInfo()).map(AudioSystem::getMixer);
    }

}
