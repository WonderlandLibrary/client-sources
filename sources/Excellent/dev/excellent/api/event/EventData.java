package dev.excellent.api.event;

import dev.excellent.api.interfaces.event.Listener;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventData<T> {
    private Object parent;
    private Listener<T> callback;
}
