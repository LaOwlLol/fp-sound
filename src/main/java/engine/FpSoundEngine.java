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

import fauxpas.event.ProduceConsumeEvent;
import fauxpas.eventqueue.SharedQueuePool;
import fauxpas.eventqueue.SingleQueue;
import setup.ErrorMessageService;
import setup.FormatService;
import setup.LineService;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;

/**
 * Controller for Java Sound API
 */
public class FpSoundEngine {

    private SharedQueuePool engine;
    private ArrayList<Track> tracks;

    public FpSoundEngine() {
        this.engine = new SharedQueuePool();
    }

    /**
     * Simple play sound.
     * @param soundFile File to play.
     *  (required to be, wav, au, or aiff with appropriate AudioFormat supported by Java
     *                  see https://docs.oracle.com/javase/tutorial/sound/converters.html for more )
     */
    public void PlaySound(File soundFile) {
        Track t = new Track();
        t.PlaySound(soundFile);
        tracks.add(t);
        monitorTrack(t);
    }

    private void monitorTrack(Track subject) {
        this.engine.enqueue(new Runnable() {
            @Override
            public void run() {
                boolean playing = false;
                while (playing) {
                    playing = subject.isAlive();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.err.println(ErrorMessageService.ERROR_HEADER +e.getMessage());
                    }
                }
                tracks.remove(subject);
                subject.cleanup();
            }
        });
    }

    /**
     *  Shut down all tracks and shut down the engine proper.
     *
     *  Proper use of the engine requires call this at the end of your program.
     *  This can be done on the close method of an JavaFX Application.
     */
    public void shutDownEngine() {
        tracks.forEach(Track::cleanup);
        this.engine.cleanup();
    }
}
