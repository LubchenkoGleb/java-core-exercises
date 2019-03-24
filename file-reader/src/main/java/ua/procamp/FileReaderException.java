package ua.procamp;

public class FileReaderException extends RuntimeException {
    public FileReaderException(String message, Exception e) {
        super(message, e);
    }

    public FileReaderException(String message) {
        super(message);
    }
}