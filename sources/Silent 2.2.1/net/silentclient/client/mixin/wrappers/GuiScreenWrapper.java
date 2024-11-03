package net.silentclient.client.mixin.wrappers;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenWrapper {
    private final GuiScreen screen;


    public GuiScreenWrapper(GuiScreen screen) {
        this.screen = screen;
    }

    public GuiScreen getScreen() {
        return screen;
    }
}
