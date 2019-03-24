package ua.procamp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
public class FileStats {

    private final Map<Character, Long> charactersMap;
    private final char mostPopularCharacter;

    private FileStats(String fileName) {
        this.charactersMap = getCharactersMap(getLinesStream(fileName));
        this.mostPopularCharacter = findMostPopularCharacter(this.charactersMap);
    }

    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName) {
        return new FileStats(fileName);
    }

    private static Stream<String> getLinesStream(String fileName) {
        URL resource = Optional.ofNullable(FileStats.class.getClassLoader().getResource(fileName))
                .orElseThrow(() -> new FileStatsException("Resource not found"));
        try {
            Path path = Paths.get(resource.toURI());
            return Files.lines(path);
        } catch (URISyntaxException e) {
            throw new FileStatsException("Unable to get file URI", e);
        } catch (IOException e) {
            throw new FileStatsException("Unable to get lines stream", e);
        }
    }

    private Map<Character, Long> getCharactersMap(Stream<String> lines) {
        return lines
                .flatMapToInt(String::chars)
                .mapToObj(i -> (char) i)
                .filter(c -> c != ' ')
                .collect(groupingBy(identity(), counting()));
    }

    private char findMostPopularCharacter(Map<Character, Long> charactersMap) {
        return charactersMap.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new FileStatsException("File is empty"));
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        return this.charactersMap.get(character).intValue();
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        return this.mostPopularCharacter;
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        return this.charactersMap.containsKey(character);
    }
}
