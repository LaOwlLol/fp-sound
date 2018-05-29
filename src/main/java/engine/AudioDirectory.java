package engine;

import setup.ErrorMessageService;
import setup.FormatService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Caches the audio files in a directory.
 */
public class AudioDirectory {

    private Map<String, File> namesToFiles;

    /**
     * Construct a AudioDirectory object by filtering the audio files of a directory
     * mapping the filtered file names to file objects.
     *
     * @param audioDirectory Path to a directory containing audio files.
     */
    public AudioDirectory(Path audioDirectory) {
        this.namesToFiles = new HashMap<>();

        try (Stream<Path> list = Files.list(audioDirectory))  {
            list.map(Path::toFile).filter( file ->
                file.getName().toLowerCase().endsWith(FormatService.AIF_EXTENSION) ||
                      file.getName().toLowerCase().endsWith(FormatService.AIFF_EXTENSION) ||
                      file.getName().toLowerCase().endsWith(FormatService.AU_EXTENSION) ||
                      file.getName().toLowerCase().endsWith(FormatService.WAVE_EXTENSION) ||
                      file.getName().toLowerCase().endsWith(FormatService.WAV_EXTENSION))
                            .forEach(acceptedFile -> namesToFiles.put(acceptedFile.getName(), acceptedFile));
        }
        catch (IOException e) {
            System.err.println(ErrorMessageService.ERROR_HEADER+e.getMessage());
        }
    }

    /**
     * Get all names of files in the directory.
     * @return Stream of file names in this directory.
     */
    public Stream<String> keys() {
        return namesToFiles.keySet().stream();
    }

    /**
     * Get all the audio files in the directory.
     * @return Stream of files in this directory.
     */
    public Stream<File> audio() {
        return namesToFiles.values().stream();
    }

    /**
     * Get the file with given name.
     * @param fileName Name of file to retrieve.
     * @return Optional to file request or null if it's not in the directory.
     */
    public Optional<File> get(String fileName) {
        return Optional.ofNullable(namesToFiles.get(fileName));
    }
}
