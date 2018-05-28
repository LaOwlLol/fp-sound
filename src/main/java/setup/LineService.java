package setup;

import javax.sound.sampled.*;
import java.util.Optional;

public class LineService {

    /**
     * Helper Method for retrieving a line for the audio format of a audio data stream..
     * Used by FpSoundEngine to get the line of an audio Mixer to push input stream data to.
     * See: FpSoundEngine.PlaySound(File)
     * @param inputFormat The AudioFormat to retrieve a line for.
     * @return Optional SourceDataLine for the AudioFormat, otherwise an empty optional (on LineUnavailableException).
     */
    public static Optional<SourceDataLine> SystemSourceDataLine(AudioFormat inputFormat) {
        try {
            DataLine.Info lineInfo = new DataLine.Info( SourceDataLine.class, inputFormat);
            return Optional.ofNullable((SourceDataLine)
                  AudioSystem.getLine(lineInfo));
        }
        catch (LineUnavailableException e) {
            System.err.println(ErrorMessageService.ERROR_HEADER+e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Get details for a line.
     * @param line The Line to return details for.
     * @return  Line.Info as a String.
     */
    public static String LineDetails(Line line) {
        return line.getLineInfo().toString();
    }

}
