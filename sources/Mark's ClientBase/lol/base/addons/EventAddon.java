package lol.base.addons;

import lol.base.BaseClient;
import lol.base.radbus.State;
import lol.base.radbus.Type;

public class EventAddon {

    public boolean cancelled;
    public State state;
    public Type type;

    public EventAddon() {
        cancelled = false;
        state = State.PRE;
    }

    public void call() {
        cancelled = false;
        BaseClient.get().eventManager.publish(this);
    }

}
