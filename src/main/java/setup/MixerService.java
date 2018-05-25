package setup;

import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class MixerService {

    private static final String INFO_LABEL = ": ";
    private static final String INFO_DELIMITER = " - ";

    public static Stream<String> MixerDetails(Stream<Mixer> mixers) {
        return mixers.map( mixer -> "Name" + INFO_LABEL + mixer.getMixerInfo().getName() + INFO_DELIMITER +
              "Ver" + INFO_LABEL + mixer.getMixerInfo().getVersion() + INFO_DELIMITER +
              "Desc" + INFO_LABEL + mixer.getMixerInfo().getDescription());
    }

    public static Stream<Mixer> AppropriateMixers() {
        return SystemMixers().filter(mixer ->
              mixer.isLineSupported(new DataLine.Info(SourceDataLine.class, FormatService.FORMAT_44100khz))
        );
    }

    public static Stream<Mixer> SystemMixers() {
        return Arrays.stream(AudioSystem.getMixerInfo()).map(mixerInfo -> AudioSystem.getMixer(mixerInfo));
    }

}
