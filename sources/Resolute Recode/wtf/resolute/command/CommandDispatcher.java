package wtf.resolute.command;

import wtf.resolute.command.impl.DispatchResult;

public interface CommandDispatcher {
    DispatchResult dispatch(String command);
}
