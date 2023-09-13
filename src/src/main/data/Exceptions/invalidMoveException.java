package main.data.Exceptions;

public class invalidMoveException extends Throwable {
    public invalidMoveException(String description) {
        super(description);
    }
}
