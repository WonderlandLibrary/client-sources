package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
import me.superskidder.lune.values.Value;

/**
 * @author: QianXia
 * @description: Value被更改触发该事件
 * @create: 2020/12/26-14:08
 */
public class EventChangeValue extends Event {
    private Mode mode;
    private boolean cancelled;
    private String valueName;
    private Value<?> value;

    public EventChangeValue(Mode mode, String valueName, Value<?> value) {
        this.mode = mode;
        this.valueName = valueName;
        this.value = value;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Value<?> getValue() {
        return value;
    }

    public void setValue(Value<?> value) {
        this.value = value;
    }

    public enum Mode {
        BEFORE, AFTER
    }
}

