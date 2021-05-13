package Exceptions;

public class WrongParamsException extends Exception{
    public WrongParamsException(String errorMessage) {
        super(errorMessage);
    }
}
