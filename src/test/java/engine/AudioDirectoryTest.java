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

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioDirectoryTest {

    @Test
    public void testKeys() {
        if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"))) {
            AudioDirectory ad = new AudioDirectory(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"));

            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp"))) {
                assert (!ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp.wa"))) {
                assert (!ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp.wa")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp.wav"))) {
                assert (ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp.wav")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp.wave"))) {
                assert (ad.keys().anyMatch(key -> key.equalsIgnoreCase("harp.wave")));
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/test.wav"))) {
                assert (ad.keys().anyMatch(key -> key.equalsIgnoreCase("test.wav")));
            }

        }
    }

    @Test
    public void testGetAudio() {
        if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"))) {
            AudioDirectory ad = new AudioDirectory(Paths.get(System.getProperty("user.home"), "/fpAudio/Test"));

            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/harp"))) {
                assert (!ad.get("harp").isPresent());
            }
            if (Files.exists(Paths.get(System.getProperty("user.home"), "/fpAudio/Test/test.wav"))) {
                assert (ad.get("test.wav").isPresent());
            }
        }
    }

}
