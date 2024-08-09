package dev.luvbeeq.baritone.api.event.events;

import dev.luvbeeq.baritone.api.event.events.type.EventState;

import java.util.function.BiFunction;

public final class TickEvent {

    private static int overallTickCount;

    private final EventState state;
    private final Type type;
    private final int count;

    public TickEvent(EventState state, Type type, int count) {
        this.state = state;
        this.type = type;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Type getType() {
        return type;
    }

    public EventState getState() {
        return state;
    }

    public static synchronized BiFunction<EventState, Type, TickEvent> createNextProvider() {
        final int count = overallTickCount++;
        return (state, type) -> new TickEvent(state, type, count);
    }

    public enum Type {
        /**
         * When guarantees can be made about
         * the game state and in-game variables.
         */
        IN,
        /**
         * No guarantees can be made about the game state.
         * This probably means we are at the main menu.
         */
        OUT,
    }
}
