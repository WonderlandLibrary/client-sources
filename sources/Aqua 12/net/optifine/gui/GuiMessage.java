// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.gui;

import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import java.io.IOException;
import net.minecraft.src.Config;
import net.minecraft.client.gui.GuiButton;
import java.util.Collection;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.resources.I18n;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class GuiMessage extends GuiScreen
{
    private GuiScreen parentScreen;
    private String messageLine1;
    private String messageLine2;
    private final List<String> listLines2;
    protected String confirmButtonText;
    private int ticksUntilEnable;
    
    public GuiMessage(final GuiScreen parentScreen, final String line1, final String line2) {
        this.listLines2 = (List<String>)Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.messageLine1 = line1;
        this.messageLine2 = line2;
        this.confirmButtonText = I18n.format("gui.done", new Object[0]);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiOptionButton(0, GuiMessage.width / 2 - 74, GuiMessage.height / 6 + 96, this.confirmButtonText));
        this.listLines2.clear();
        this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, GuiMessage.width - 50));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        Config.getMinecraft().displayGuiScreen(this.parentScreen);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.messageLine1, GuiMessage.width / 2, 70, 16777215);
        int i = 90;
        for (final String s : this.listLines2) {
            this.drawCenteredString(this.fontRendererObj, s, GuiMessage.width / 2, i, 16777215);
            final int n = i;
            final FontRenderer fontRendererObj = this.fontRendererObj;
            i = n + FontRenderer.FONT_HEIGHT;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void setButtonDelay(final int ticksUntilEnable) {
        this.ticksUntilEnable = ticksUntilEnable;
        for (final GuiButton guibutton : this.buttonList) {
            guibutton.enabled = false;
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final int ticksUntilEnable = this.ticksUntilEnable - 1;
        this.ticksUntilEnable = ticksUntilEnable;
        if (ticksUntilEnable == 0) {
            for (final GuiButton guibutton : this.buttonList) {
                guibutton.enabled = true;
            }
        }
    }
}
