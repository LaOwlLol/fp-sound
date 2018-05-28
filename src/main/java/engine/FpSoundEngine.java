package engine;

import setup.FormatService;
import setup.LineService;

import java.io.File;

/**
 * Controller for Java Sound API
 */
public class FpSoundEngine {

    /**
     * Push audio data for a file to the AudioSystem.
     * @param soundFile File to push to AudioSystem.
     *  (required to be, wav, au, or aiff with appropriate AudioFormat supported by Java
     *                  see https://docs.oracle.com/javase/tutorial/sound/converters.html for more )
     */
    public static void PlaySound(File soundFile) {
        FormatService.AudioInputStream(soundFile).ifPresent( audioStream ->
            LineService.SystemSourceDataLine(audioStream.getFormat()).ifPresent(sourceLine ->
                new AudioProcessThread(audioStream, sourceLine).start()
            )
        );
    }

}
