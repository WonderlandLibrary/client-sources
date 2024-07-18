package com.alan.clients.event.impl.input;

import com.alan.clients.Client;
import com.alan.clients.event.CancellableEvent;
import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptChatInputEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public final class ChatInputEvent extends CancellableEvent {
    private String message;

    public static void implementation(String message) {
        Client.INSTANCE.getEventBus().handle(new ChatInputEvent(message));
    }

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptChatInputEvent(this);
    }
}