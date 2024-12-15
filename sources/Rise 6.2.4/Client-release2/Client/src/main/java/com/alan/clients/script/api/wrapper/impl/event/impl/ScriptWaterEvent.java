package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.motion.WaterEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
public class ScriptWaterEvent extends ScriptEvent<WaterEvent> {

    public ScriptWaterEvent(final WaterEvent wrappedEvent) {
        super(wrappedEvent);
    }

    public void setWater(final boolean water) {
        this.wrapped.setWater(water);
    }

    public boolean isWater() {
        return this.wrapped.isWater();
    }

    @Override
    public String getHandlerName() {
        return "onWater";
    }
}
