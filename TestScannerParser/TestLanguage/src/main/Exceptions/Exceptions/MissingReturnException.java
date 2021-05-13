package Exceptions;

public class MissingReturnException extends Exception{
    public MissingReturnException(String errorMessage) {
        super(errorMessage);
    }
}
