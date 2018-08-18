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
