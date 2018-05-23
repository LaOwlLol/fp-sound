package setup;

import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class MixerService {

    public static final AudioFormat FORMAT = new AudioFormat(
          AudioFormat.Encoding.PCM_SIGNED, //linear signed PCM
          44100, //44.1kHz sampling rate
          16, //16-bit
          2, //2 channels fool
          4, //frame size 4 bytes (16-bit, 2 channel)
          44100, //same as sampling rate
          false //little-endian
    );

    private static final String INFO_LABEL = ": ";
    private static final String INFO_DELIMITER = " - ";

    public static Stream<String> MixerDetails(Stream<Mixer> mixers) {
        return mixers.map( mixer -> "Name" + INFO_LABEL + mixer.getMixerInfo().getName() + INFO_DELIMITER +
              "Ver" + INFO_LABEL + mixer.getMixerInfo().getVersion() + INFO_DELIMITER +
              "Desc" + INFO_LABEL + mixer.getMixerInfo().getDescription());
    }

    public static String LineDetails(Line line) {
        return line.getLineInfo().toString();
    }

    public static Stream<Mixer> AppropriateMixers() {
        return SystemMixers().filter(mixer ->
              mixer.isLineSupported(new DataLine.Info(SourceDataLine.class, FORMAT))
        );
    }

    public static Optional<Port> SpeakerPort() {
        if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
            try {
                return Optional.ofNullable((Port) AudioSystem.getLine(Port.Info.SPEAKER));
            }
            catch (LineUnavailableException e) {
                System.err.println("Faux Pas Sound Engine - No Speaker Port Available");
                return Optional.empty();
            }
        }

        System.err.println("Faux Pas Sound Engine - No Speaker Port Supported");
        return Optional.empty();
    }

    public static Optional<Port> LineOutPort() {
        if (AudioSystem.isLineSupported(Port.Info.LINE_OUT)) {
            try {
                return Optional.ofNullable((Port) AudioSystem.getLine(Port.Info.SPEAKER));
            }
            catch (LineUnavailableException e) {
                System.err.println("Faux Pas Sound Engine - No Line Out Port Available");
                return Optional.empty();
            }
        }

        System.err.println("Faux Pas Sound Engine - No Line Out Port Supported");
        return Optional.empty();
    }

    public static Stream<Mixer> SystemMixers() {
        return Arrays.stream(AudioSystem.getMixerInfo()).map(mixerInfo -> AudioSystem.getMixer(mixerInfo));
    }

}
