// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.client.gui;

import java.util.Iterator;
import java.awt.Color;
import net.minecraft.client.multiplayer.GuiConnecting;
import intent.AquaDev.aqua.Aqua;
import org.lwjgl.opengl.GL11;
import java.io.IOException;
import net.minecraft.client.resources.I18n;
import java.util.List;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected extends GuiScreen
{
    private String reason;
    private IChatComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;
    
    public GuiDisconnected(final GuiScreen screen, final String reasonLocalizationKey, final IChatComponent chatComp) {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), GuiDisconnected.width - 50);
        final int size = this.multilineMessage.size();
        final FontRenderer fontRendererObj = this.fontRendererObj;
        this.field_175353_i = size * FontRenderer.FONT_HEIGHT;
        final List<GuiButton> buttonList = this.buttonList;
        final int buttonId = 0;
        final int x = GuiDisconnected.width / 2 - 100;
        final int n = GuiDisconnected.height / 2 + this.field_175353_i / 2;
        final FontRenderer fontRendererObj2 = this.fontRendererObj;
        buttonList.add(new GuiButton(buttonId, x, n + FontRenderer.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (!GL11.glGetString(7937).contains("AMD") && !GL11.glGetString(7937).contains("RX ")) {
            Aqua.INSTANCE.shaderBackgroundMM.renderShader();
        }
        else {
            this.drawDefaultBackground();
        }
        if (GuiConnecting.sentinelChecker) {
            final FontRenderer fontRendererObj = this.mc.fontRendererObj;
            final String text = "Staff Ban!";
            final int x = GuiDisconnected.width / 2 - 22;
            final int n = GuiDisconnected.height / 2 - this.field_175353_i / 2;
            final FontRenderer fontRendererObj2 = this.fontRendererObj;
            fontRendererObj.drawString(text, x, n - FontRenderer.FONT_HEIGHT * 2, Color.red.getRGB());
        }
        else {
            final FontRenderer fontRendererObj3 = this.fontRendererObj;
            final String reason = this.reason;
            final int x2 = GuiDisconnected.width / 2;
            final int n2 = GuiDisconnected.height / 2 - this.field_175353_i / 2;
            final FontRenderer fontRendererObj4 = this.fontRendererObj;
            this.drawCenteredString(fontRendererObj3, reason, x2, n2 - FontRenderer.FONT_HEIGHT * 2, 11184810);
        }
        int i = GuiDisconnected.height / 2 - this.field_175353_i / 2;
        if (this.multilineMessage != null) {
            for (final String s : this.multilineMessage) {
                this.drawCenteredString(this.fontRendererObj, s, GuiDisconnected.width / 2, i, 16777215);
                final int n3 = i;
                final FontRenderer fontRendererObj5 = this.fontRendererObj;
                i = n3 + FontRenderer.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
