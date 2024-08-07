/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanDetected
implements GuiListExtended.IGuiListEntry {
    private final GuiMultiplayer screen;
    protected final Minecraft mc;
    protected final LanServerInfo serverData;
    private long lastClickTime;

    protected ServerListEntryLanDetected(GuiMultiplayer p_i47141_1_, LanServerInfo p_i47141_2_) {
        this.screen = p_i47141_1_;
        this.serverData = p_i47141_2_;
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
        this.mc.fontRendererObj.drawString(I18n.format("lanServer.title", new Object[0]), p_192634_2_ + 32 + 3, p_192634_3_ + 1, 0xFFFFFF);
        this.mc.fontRendererObj.drawString(this.serverData.getServerMotd(), p_192634_2_ + 32 + 3, p_192634_3_ + 12, 0x808080);
        if (this.mc.gameSettings.hideServerAddress) {
            this.mc.fontRendererObj.drawString(I18n.format("selectServer.hiddenAddress", new Object[0]), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + 11, 0x303030);
        } else {
            this.mc.fontRendererObj.drawString(this.serverData.getServerIpPort(), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + 11, 0x303030);
        }
    }

    @Override
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
        this.screen.selectServer(slotIndex);
        if (Minecraft.getSystemTime() - this.lastClickTime < 250L) {
            this.screen.connectToSelected();
        }
        this.lastClickTime = Minecraft.getSystemTime();
        return false;
    }

    @Override
    public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {
    }

    @Override
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
    }

    public LanServerInfo getServerData() {
        return this.serverData;
    }
}

