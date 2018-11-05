/*
 * fp-sound version 1.0 - A 'bare bones' sound API for Java
 *     Copyright (C) 2018  Nate Gillard https://github.com/LaOwlLol
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package engine;

import fauxpas.event.Event;
import fauxpas.eventqueue.EventQueue;
import setup.ErrorMessageService;
import setup.FormatService;
import setup.LineService;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.File;
import java.io.IOException;

/**
 * Controller for Java Sound API
 */
public class FpSoundEngine {

    private EventQueue engineQueue;

    public FpSoundEngine() {
        this.engineQueue = new EventQueue();
    }

    /**
     * Push audio data for a file to the AudioSystem.
     * @param soundFile File to push to AudioSystem.
     *  (required to be, wav, au, or aiff with appropriate AudioFormat supported by Java
     *                  see https://docs.oracle.com/javase/tutorial/sound/converters.html for more )
     */
    public void PlaySound(File soundFile) {
        FormatService.AudioInputStream(soundFile).ifPresent( audioStream ->
            LineService.SystemSourceDataLine(audioStream.getFormat()).ifPresent(sourceLine ->
                engineQueue.enqueue( new Event<>( () -> audioStream, (audio) -> processAudioOnLine(audio, sourceLine) ) )
            )
        );
    }

    private void processAudioOnLine(AudioInputStream stream, SourceDataLine line) {
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
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = line.write(abData, 0, nBytesRead);
            }
        }

        line.drain();
        line.close();
    }
}
