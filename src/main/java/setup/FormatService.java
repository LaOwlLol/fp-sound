package setup;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FormatService {

    public static final AudioFormat FORMAT_44100khz = new AudioFormat(
          AudioFormat.Encoding.PCM_SIGNED, //linear signed PCM
          44100, //44.1kHz sampling rate
          16, //16-bit
          2, //2 channels fool
          4, //frame size 4 bytes (16-bit, 2 channel)
          44100, //same as sampling rate
          false //little-endian
    );

    public static final AudioFormat FORMAT_22050khz = new AudioFormat(
          AudioFormat.Encoding.PCM_SIGNED, //linear signed PCM
          22050, //22.1kHz sampling rate
          16, //16-bit
          2, //2 channels fool
          4, //frame size 4 bytes (16-bit, 2 channel)
          44100, //same as sampling rate
          false //little-endian
    );

    public static Optional<AudioInputStream> AudioInputStream(File soundFile) {

        try {
            return Optional.ofNullable(AudioSystem.getAudioInputStream(soundFile));
        }
        catch (UnsupportedAudioFileException e) {
            System.err.println(ErrorMessageService.ERROR_HEADER+e.getMessage());
            return Optional.empty();
        }
        catch (IOException e) {
            System.err.println(ErrorMessageService.ERROR_HEADER+e.getMessage());
            return Optional.empty();
        }

    }
}
