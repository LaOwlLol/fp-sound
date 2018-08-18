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
