package setup;

import javax.sound.sampled.*;
import java.util.Optional;

public class LineService {

    private static DataLine.Info preferredLineInfo = new DataLine.Info(
          SourceDataLine.class,
          FormatService.FORMAT_44100khz);

    public static Optional<SourceDataLine> SystemSourceDataLine() {
        try {
            return Optional.ofNullable((SourceDataLine)
                  AudioSystem.getLine(preferredLineInfo));
        }
        catch (LineUnavailableException e) {
            System.err.println("Faux Pas Sound Engine - No AudioSystem SourceDataLine available for preferred 44100khz");
            return Optional.empty();
        }
    }

    public static Optional<SourceDataLine> SystemSourceDataLine(AudioInputStream inputStream) {
        try {
            DataLine.Info lineInfo = new DataLine.Info( SourceDataLine.class, inputStream.getFormat());
            return Optional.ofNullable((SourceDataLine)
                  AudioSystem.getLine(lineInfo));
        }
        catch (LineUnavailableException e) {
            System.err.println("Faux Pas Sound Engine - No AudioSystem SourceDataLine available for requested" +
                  " AudioFormat " + inputStream.getFormat().getSampleRate() + "khz") ;
            return Optional.empty();
        }
    }

    public static String LineDetails(Line line) {
        return line.getLineInfo().toString();
    }

}
