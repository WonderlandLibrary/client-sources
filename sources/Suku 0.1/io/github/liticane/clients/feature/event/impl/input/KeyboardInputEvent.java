package io.github.liticane.clients.feature.event.impl.input;

import io.github.liticane.clients.feature.event.Event;
import net.minecraft.client.gui.GuiScreen;

public final class KeyboardInputEvent extends Event {
    private final int keyCode;
    private final GuiScreen guiScreen;

    public KeyboardInputEvent(int keyCode, GuiScreen guiScreen) {
        this.keyCode = keyCode;
        this.guiScreen = guiScreen;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }
}
