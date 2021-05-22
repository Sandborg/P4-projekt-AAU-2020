package Exceptions;

public class WrongTypeException extends Exception{
    public WrongTypeException(String errorMessage) {
        super(errorMessage);
    }
}
