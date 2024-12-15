package com.alan.clients.event.impl.input;

import com.alan.clients.event.CancellableEvent;
import com.alan.clients.event.Event;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.script.api.wrapper.impl.event.impl.ScriptKeyboardInputEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;
@Getter
@AllArgsConstructor
public final class KeyboardInputEvent extends CancellableEvent {

    private final int keyCode;
    private final char character;
    private final GuiScreen guiScreen;

    @Override
    public ScriptEvent<? extends Event> getScriptEvent() {
        return new ScriptKeyboardInputEvent(this);
    }
}
