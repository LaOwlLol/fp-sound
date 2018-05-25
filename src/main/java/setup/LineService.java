package setup;

import javax.sound.sampled.*;
import javax.swing.text.html.Option;
import java.util.Optional;

public class LineService {

    private static DataLine.Info prefferedLineInfo = new DataLine.Info(
          SourceDataLine.class,
          FormatService.FORMAT_44100khz);

    public static Optional<SourceDataLine> SystemSourceDataLine() {
        try {
            return Optional.ofNullable((SourceDataLine)
                  AudioSystem.getLine(prefferedLineInfo));
        }
        catch (LineUnavailableException e) {
            System.err.println("Faux Pas Sound Engine - No 44100khz SourceDataLine Available");
            return Optional.empty();
        }
    }

    public static String LineDetails(Line line) {
        return line.getLineInfo().toString();
    }

}
