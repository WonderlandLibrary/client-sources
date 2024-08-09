package wtf.shiyeno.modules.impl.util;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "NoCommands",
        type = Type.Util
)
public class NoCommands extends Function {
    public NoCommands() {
    }

    public void onEvent(Event event) {
    }
}