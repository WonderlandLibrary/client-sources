package wtf.shiyeno.modules.impl.player;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "NoCommands",
        type = Type.Player
)
public class NoCommands extends Function {
    public NoCommands() {
    }

    public void onEvent(Event event) {
    }
}