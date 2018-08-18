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

import setup.ErrorMessageService;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;

/**
 * A thread for reading data from an AudioInputStream while writing
 * the data to a SourceDataLine of an audio mixer.
 */
public class AudioProcessThread extends Thread {

    private final AudioInputStream audioStream;
    private final SourceDataLine sourceLine;

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
        int BUFFER_SIZE = 128000;
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
