package Exceptions;

public class FileNotExistsException extends Exception{
    public FileNotExistsException(String errMsg) {
        super(errMsg);
    }
}
