package com.boomer.client.event.events.input;

import com.boomer.client.event.Event;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author Xen for BoomerWare
 * @since 8/8/2019
 **/
public class GuiOpenEvent extends Event {
    private GuiScreen guiScreen;

    public GuiOpenEvent(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }
}
