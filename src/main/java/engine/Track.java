package engine;

import fauxpas.event.ProduceConsumeEvent;
import fauxpas.eventqueue.SingleQueue;
import setup.ErrorMessageService;
import setup.FormatService;
import setup.LineService;
import setup.LineStatus;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * A track wrap the async process of processing audio data onto a audio source line.
 *
 * A Track can be constructed anytime and kept for multiple PlaySound calls.
 *
 * It's important to call clean up on a track to properly shut down it's execution thread.
 */
public class Track {

    private SingleQueue trackQueue;
    private LineStatus status;

    public Track() {
        this.trackQueue = new SingleQueue();
        this.status = new LineStatus();
    }

    /**
     * Enqueue an audio file for playback.
     * If no audio is being process the play back should start asap.
     * If there is currently audio data being processed on the track the new request will be queued.
     * @param soundFile audio file data to enqueue.
     */
    public void PlaySound(File soundFile) {
        FormatService.AudioInputStream(soundFile).ifPresent(audioStream ->
            LineService.SystemSourceDataLine(audioStream.getFormat()).ifPresent(sourceLine -> {
                sourceLine.addLineListener(this.status);
                trackQueue.enqueue(new ProduceConsumeEvent<AudioInputStream>(() -> audioStream, (audio) -> processAudioOnLine(audio, sourceLine)));
            })
        );
    }

    /**
     * Processes audio data.
     * @param stream data to push to line
     * @param line output line.
     */
    private static void processAudioOnLine(AudioInputStream stream, SourceDataLine line) {
        try {
            line.open(stream.getFormat());
        }
        catch (LineUnavailableException e) {
            System.err.println(ErrorMessageService.ERROR_HEADER +e.getMessage());
            return;
        }
        line.start();

        int nBytesRead = 0;

        int BUFFER_SIZE = 128000;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = stream.read(abData, 0, abData.length);
            } catch (IOException e) {
                System.err.println(ErrorMessageService.ERROR_HEADER +e.getMessage());
            }
            if (nBytesRead >= 0) {
                int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
        }

        line.drain();
        line.stop();
        line.close();
    }

    /**
     * Answer to: Does the track have a live output line?
     * @return true if the track status is not closed.
     */
    public boolean isAlive() {
        return !this.status.getType().equalsIgnoreCase("close");
    }

    /**
     * Is the track processing audio data?
     * @return true if the track status is start.
     */
    public boolean isPlaying() {
        return this.status.getType().equalsIgnoreCase("start");
    }

    /**
     * Tracks contain their own running thread that must be cleaned up.
     */
    public void cleanup() {
        this.trackQueue.cleanup();
    }

}
