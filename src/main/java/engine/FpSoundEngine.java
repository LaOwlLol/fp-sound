package engine;


import setup.LineService;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class FpSoundEngine {

    private static final String ERROR_HEADER = "FpSoundEngine Error: ";
    private static final String ERROR_MSG_NOLINEPRESENT = "no SourceDataLine service present.";
    private final int BUFFER_SIZE = 128000;

    public void playSound(File soundFile) {

        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        }
        catch (UnsupportedAudioFileException e) {
            System.err.println(ERROR_HEADER+e.getMessage());
            return;
        }
        catch (IOException e) {
            System.err.println(ERROR_HEADER+e.getMessage());
            return;
        }

        AtomicReference<SourceDataLine> sourceLine = new AtomicReference<>();
        LineService.SystemSourceDataLine(audioStream).ifPresent(sourceLine::set);

        if (sourceLine.get() != null) {
            try {
                sourceLine.get().open(audioStream.getFormat());
            }
            catch (LineUnavailableException e) {
                System.err.println(ERROR_HEADER+e.getMessage());
                return;
            }

            sourceLine.get().start();

            int nBytesRead = 0;
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1) {
                try {
                    nBytesRead = audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    @SuppressWarnings("unused")
                    int nBytesWritten = sourceLine.get().write(abData, 0, nBytesRead);
                }
            }

            sourceLine.get().drain();
            sourceLine.get().close();
        }
        else {
            System.err.println(ERROR_HEADER+ERROR_MSG_NOLINEPRESENT);
            return;
        }


    }

}
