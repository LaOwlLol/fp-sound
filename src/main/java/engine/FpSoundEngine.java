package engine;


import setup.ErrorMessageService;
import setup.FormatService;
import setup.LineService;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class FpSoundEngine {

    public void playSound(File soundFile) {

        FormatService.AudioInputStream(soundFile).ifPresent( audioStream ->
            LineService.SystemSourceDataLine(audioStream).ifPresent(sourceLine ->
                new AudioProcessThread(audioStream, sourceLine).start()
            )
        );

    }

}
