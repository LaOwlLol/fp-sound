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
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Provides access to streams of available system Mixers and Mixer's details.
 */
public class MixerService {

    private static final String INFO_LABEL = ": ";
    private static final String INFO_DELIMITER = " - ";

    /**
     * Get a stream of details (properties in Mixer.Info) form a stream of Mixers.
     * @param mixers A stream of mixer objects to turn into details.
     * @return A stream of Strings describing the Mixer provided.
     */
    public static Stream<String> MixerDetails(Stream<Mixer> mixers) {
        return mixers.map( mixer -> "Name" + INFO_LABEL + mixer.getMixerInfo().getName() + INFO_DELIMITER +
              "Ver" + INFO_LABEL + mixer.getMixerInfo().getVersion() + INFO_DELIMITER +
              "Desc" + INFO_LABEL + mixer.getMixerInfo().getDescription());
    }

    /**
     * Filter system mixers into a stream of supported Mixers.
     * @return A stream if mixers that support the preferred AudioFormat (have SourceDataLine).
     */
    public static Stream<Mixer> AppropriateMixers() {
        return SystemMixers().filter(mixer ->
              mixer.isLineSupported(new DataLine.Info(SourceDataLine.class, FormatService.PREFERRED_FORMAT))
        );
    }

    /**
     * Get all mixers available to the Java Audio System.
     * @return A stream of Mixers.
     */
    public static Stream<Mixer> SystemMixers() {
        return Arrays.stream(AudioSystem.getMixerInfo()).map(AudioSystem::getMixer);
    }

}
