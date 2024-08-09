package src.Wiksi.command;

import src.Wiksi.command.impl.DispatchResult;

public interface CommandDispatcher {
    DispatchResult dispatch(String command);
}
