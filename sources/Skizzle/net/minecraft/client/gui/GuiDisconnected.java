/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import skizzle.Client;
import skizzle.util.RandomHelper;

public class GuiDisconnected
extends GuiScreen {
    private String reason;
    private IChatComponent message;
    private List multilineMessage;
    private final GuiScreen parentScreen;
    private String ip;
    private int port;
    private int field_175353_i;
    private static final String __OBFID = "CL_00000693";

    public GuiDisconnected(GuiScreen p_i45020_1_, String p_i45020_2_, IChatComponent p_i45020_3_, String ip, int port) {
        this.parentScreen = p_i45020_1_;
        this.reason = I18n.format(p_i45020_2_, new Object[0]);
        this.message = p_i45020_3_;
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
        if (!(this.ip.equals("") && this.port == 0 || Client.ghostMode)) {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 24, I18n.format("Reconnect", new Object[0])));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 48, I18n.format("Reconnect with cracked alt", new Object[0])));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiConnecting(this.parentScreen, this.mc, new ServerData("", this.ip)));
        }
        if (button.id == 3) {
            this.mc.session = new Session(RandomHelper.randomName(), "", "", "mojang");
            this.mc.displayGuiScreen(new GuiConnecting(this.parentScreen, this.mc, new ServerData("", this.ip)));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 0xAAAAAA);
        int var4 = this.height / 2 - this.field_175353_i / 2;
        if (this.multilineMessage != null) {
            for (String var6 : this.multilineMessage) {
                this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 0xFFFFFF);
                var4 += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

