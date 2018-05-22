package setup;

import javax.sound.sampled.*;
import java.util.Arrays;
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

    private static final String INFO_BREAK = ": ";
    private static final String INFO_DELIMITER = " - ";

    public static Stream<String> ListMixers() {
         return Arrays.stream(AudioSystem.getMixerInfo()).map(
               mixerInfo -> {
                   Mixer mixer = AudioSystem.getMixer(mixerInfo);

                   return mixerInfo.getName() + INFO_DELIMITER +
                         "Supports SourceDataLines" + INFO_BREAK +
                         mixer.isLineSupported(new DataLine.Info(SourceDataLine.class, FORMAT)) + INFO_DELIMITER +
                         "Max supported lines" + INFO_BREAK +
                         mixer.getMaxLines(new DataLine.Info(SourceDataLine.class, FORMAT)) + INFO_DELIMITER;
               }
         );

    }

}
