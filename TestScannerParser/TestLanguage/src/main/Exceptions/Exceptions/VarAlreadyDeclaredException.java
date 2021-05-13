package Exceptions;

public class VarAlreadyDeclaredException extends Exception {
    public VarAlreadyDeclaredException(String errorMessage) {
            super(errorMessage);
    }
}