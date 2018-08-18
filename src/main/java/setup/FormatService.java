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

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Provides Preferred AudioFormat and AudioInputStream for an audio File, and acceptable audio file extentions.
 */
public class FormatService {

    public static final String WAV_EXTENSION = ".wav";
    public static final String WAVE_EXTENSION = ".wave";
    public static final String AU_EXTENSION = ".au";
    public static final String AIF_EXTENSION = ".aif";
    public static final String AIFF_EXTENSION = ".aiff";

    /**
     * Provides a default (or preferred) audio format.
     * 44100 or 44.1kHz sampling rate
     * 16-bit sampleSizeinBits
     * 2 channels stereo
     * frame size 4 bytes (16-bit, 2 channel)
     * frame rate 44100, same as sampling rate
     * bigEndian false
     */
    public static final AudioFormat PREFERRED_FORMAT = new AudioFormat(
          AudioFormat.Encoding.PCM_SIGNED, //linear signed PCM
          44100, //44.1kHz sampling rate
          16, //16-bit
          2, //2 channels fool
          4, //frame size 4 bytes (16-bit, 2 channel)
          44100, //same as sampling rate
          false //little-endian
    );

    /**
     * Helper Method for retrieving an audio data stream for a audio file.
     * Used by FpSoundEngine to get a data stream to push to a audio Mixer's SourceDataLine.
     * See: FpSoundEngine.PlaySound(File)
     * @param soundFile Audio file to retrieve an input stream for.
     * @return Optional AudioInputStream for the given file's data,
     *          otherwise an empty optional (on IO or UnsupportedAudioFormat Exceptions(
     */
    public static Optional<AudioInputStream> AudioInputStream(File soundFile) {

        try {
            return Optional.ofNullable(AudioSystem.getAudioInputStream(soundFile));
        }
        catch (UnsupportedAudioFileException | IOException e) {
            System.err.println(ErrorMessageService.ERROR_HEADER+e.getMessage());
            return Optional.empty();
        }

    }
}
