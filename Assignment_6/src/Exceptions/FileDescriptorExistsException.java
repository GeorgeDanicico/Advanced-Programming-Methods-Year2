package Exceptions;

public class FileDescriptorExistsException extends Exception{
    public FileDescriptorExistsException(String errMsg) {
        super(errMsg);
    }
}
