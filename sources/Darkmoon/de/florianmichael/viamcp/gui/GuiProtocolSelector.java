/*
 * This file is part of ViaMCP - https://github.com/FlorianMichael/ViaMCP
 * Copyright (C) 2020-2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.viamcp.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.protocolinfo.ProtocolInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;

public class GuiProtocolSelector extends GuiScreen {
    private final GuiScreen parent;
    public SlotList list;

    public GuiProtocolSelector(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1, width / 2 - 100, height - 25, 200, 20, "Back"));
        list = new SlotList(mc, width, height, 32, height - 32);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        list.actionPerformed(guiButton);

        if (guiButton.id == 1) {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        list.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        list.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0, 2.0, 2.0);
        String title = ChatFormatting.BOLD + "ViaMCP";
        drawString(this.fontRenderer, title, (this.width - (this.fontRenderer.getStringWidth(title) * 2)) / 4, 5, -1);
        GlStateManager.popMatrix();

        drawString(this.fontRenderer, "by EnZaXD/Flori2007", 1, 1, -1);
        drawString(this.fontRenderer, "Discord: EnZaXD#6257", 1, 11, -1);

        final ProtocolInfo protocolInfo = ProtocolInfo.fromProtocolVersion(ViaLoadingBase.getInstance().getTargetVersion());

        final String versionTitle = "Version: " + ViaLoadingBase.getInstance().getTargetVersion().getName() + " - " + protocolInfo.getName();
        final String versionReleased = "Released: " + protocolInfo.getReleaseDate();

        final int fixedHeight = ((5 + this.fontRenderer.FONT_HEIGHT) * 2) + 2;

        drawString(this.fontRenderer, ChatFormatting.GRAY + (ChatFormatting.BOLD + "Version Information"), (width - this.fontRenderer.getStringWidth("Version Information")) / 2, fixedHeight, -1);
        drawString(this.fontRenderer, versionTitle, (width - this.fontRenderer.getStringWidth(versionTitle)) / 2, fixedHeight + this.fontRenderer.FONT_HEIGHT, -1);
        drawString(this.fontRenderer, versionReleased, (width - this.fontRenderer.getStringWidth(versionReleased)) / 2, fixedHeight + this.fontRenderer.FONT_HEIGHT * 2, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    class SlotList extends GuiSlot {

        public SlotList(Minecraft mc, int width, int height, int top, int bottom) {
            super(mc, width, height, top + 30, bottom, 18);
        }

        @Override
        protected int getSize() {
            return ViaLoadingBase.getProtocols().size();
        }

        @Override
        protected void elementClicked(int i, boolean b, int i1, int i2) {
            final ProtocolVersion protocolVersion = ViaLoadingBase.getProtocols().get(i);
            ViaLoadingBase.getInstance().reload(protocolVersion);
        }

        @Override
        protected boolean isSelected(int i) {
            return false;
        }

        @Override
        protected void drawBackground() {
            drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
            drawCenteredString(mc.fontRenderer,(ViaLoadingBase.getInstance().getTargetVersion().getIndex() == slotIndex ? ChatFormatting.GREEN.toString() + ChatFormatting.BOLD : ChatFormatting.GRAY.toString()) + ViaLoadingBase.getProtocols().get(slotIndex).getName(), width / 2, yPos + 2, -1);
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            drawCenteredString(mc.fontRenderer, "PVN: " + ViaLoadingBase.getProtocols().get(slotIndex).getVersion(), width, (yPos + 2) * 2 + 20, -1);
            GlStateManager.popMatrix();
        }
    }
}
