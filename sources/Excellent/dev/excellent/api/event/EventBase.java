package dev.excellent.api.event;

import dev.excellent.api.interfaces.event.IEvent;
import lombok.Data;

@Data
public abstract class EventBase implements IEvent {

    private Stage stage;

    @Override
    public boolean isPreCallback() {
        return stage == Stage.PRE;
    }

    @Override
    public boolean isPostCallback() {
        return stage == Stage.POST;
    }

    public enum Stage {
        PRE, POST
    }

}
