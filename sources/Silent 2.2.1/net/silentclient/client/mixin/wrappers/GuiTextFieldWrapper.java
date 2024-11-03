package net.silentclient.client.mixin.wrappers;

import net.minecraft.client.gui.GuiTextField;

public class GuiTextFieldWrapper {
    private final GuiTextField guiTextField;

    public GuiTextFieldWrapper(GuiTextField guiTextField) {
        this.guiTextField = guiTextField;
    }

    public GuiTextField getGuiTextField() {
        return guiTextField;
    }
}
