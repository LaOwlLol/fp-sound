package engine;

import setup.ErrorMessageService;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;

/**
 * A thread for reading data from an AudioInputStream while writing
 * the data to a SouceDataLine of an audio mixer.
 */
public class AudioProcessThread extends Thread {

    private final int BUFFER_SIZE = 128000;
    private AudioInputStream audioStream;
    private SourceDataLine sourceLine;

    /**
     *
     * @param stream Audio data to read from.
     * @param line Line to an audio mixer to write to.
     */
    public AudioProcessThread(AudioInputStream stream, SourceDataLine line) {
        super();
        this.audioStream = stream;
        this.sourceLine = line;
    }

    /**
     * Loop while audioStream has bytes to read and write them them to sourceLine.
     * This thread opens and closes the sourceLine.
     * (potentially blocking until audioStream is consumed).
     */
    @Override
    public void run() {
        try {
            sourceLine.open(audioStream.getFormat());
        }
        catch (LineUnavailableException e) {
            System.err.println(ErrorMessageService.ERROR_HEADER +e.getMessage());
            return;
        }
        sourceLine.start();

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
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }
}
