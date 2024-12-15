package com.alan.clients.script.api.wrapper.impl.event.impl;

import com.alan.clients.event.impl.motion.PostMotionEvent;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;

public class ScriptPostMotionEvent extends ScriptEvent<PostMotionEvent> {

    public ScriptPostMotionEvent(final PostMotionEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public String getHandlerName() {
        return "onPostMotion";
    }
}
