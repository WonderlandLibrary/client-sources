package dev.excellent.api.event.impl.player;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LookVecEvent extends Event {
    private float yaw;
    private float pitch;

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Elytra extends Event {
        private float pitch;
    }
}

