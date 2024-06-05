package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class GuiDisconnected extends GuiScreen
{
    private final GuiScreen parentScreen;
    private String reason;
    private IChatComponent message;
    private List multilineMessage;
    private int field_175353_i;
    
    public GuiDisconnected(final GuiScreen p_i45020_1_, final String p_i45020_2_, final IChatComponent p_i45020_3_) {
        this.parentScreen = p_i45020_1_;
        this.reason = I18n.format(p_i45020_2_, new Object[0]);
        this.message = p_i45020_3_;
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), GuiDisconnected.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, GuiDisconnected.width / 2 - 100, GuiDisconnected.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            GuiDisconnected.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 2) {}
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, this.reason, GuiDisconnected.width / 2, GuiDisconnected.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int var4 = GuiDisconnected.height / 2 - this.field_175353_i / 2;
        if (this.multilineMessage != null) {
            for (final String var6 : this.multilineMessage) {
                Gui.drawCenteredString(this.fontRendererObj, var6, GuiDisconnected.width / 2, var4, 16777215);
                var4 += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
