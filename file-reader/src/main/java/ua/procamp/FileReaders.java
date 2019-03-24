package ua.procamp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * {@link FileReaders} privides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        Objects.requireNonNull(fileName);
        try (Stream<String> lines = getLinesStream(fileName)) {
            return lines.collect(joining("\n"));
        }
    }

    private static Stream<String> getLinesStream(String fileName) {
        URL resource = Optional.ofNullable(FileReaders.class.getClassLoader().getResource(fileName))
                .orElseThrow(() -> new FileReaderException("Resource not found"));
        try {
            Path path = Paths.get(resource.toURI());
            return Files.lines(path);
        } catch (URISyntaxException e) {
            throw new FileReaderException("Unable to get file URI", e);
        } catch (IOException e) {
            throw new FileReaderException("Unable to get lines stream", e);
        }
    }
}
