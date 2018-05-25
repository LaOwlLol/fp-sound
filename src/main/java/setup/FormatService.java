package setup;

import javax.sound.sampled.AudioFormat;

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

}
