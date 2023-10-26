package main.logic.ai;

import main.data.Exceptions.invalidMoveException;

public abstract class Strategy {
    public abstract int execute(Object ... params) throws invalidMoveException;
}
