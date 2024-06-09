// 
// Decompiled by Procyon v0.5.36
// 

package viamcp.gui;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.renderer.GlStateManager;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiCredits extends GuiScreen
{
    private GuiScreen parent;
    
    public GuiCredits(final GuiScreen parent) {
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 25, 200, 20, "Back"));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(this.parent);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0, 2.0, 2.0);
        final String title = EnumChatFormatting.BOLD + "Credits";
        this.drawString(this.fontRendererObj, title, (this.width - this.fontRendererObj.getStringWidth(title) * 2) / 4, 5, -1);
        GlStateManager.popMatrix();
        final int fixedHeight = (5 + this.fontRendererObj.FONT_HEIGHT) * 2 + 2;
        final String viaVerTeam = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("ViaVersion Team").toString();
        final String florMich = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("FlorianMichael").toString();
        final String laVache = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("LaVache-FR").toString();
        final String hideri = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("Hiderichan / Foreheadchan").toString();
        final String contactInfo = new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.BOLD).append("Contact Info").toString();
        this.drawString(this.fontRendererObj, viaVerTeam, (this.width - this.fontRendererObj.getStringWidth(viaVerTeam)) / 2, fixedHeight, -1);
        this.drawString(this.fontRendererObj, "ViaVersion", (this.width - this.fontRendererObj.getStringWidth("ViaVersion")) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT, -1);
        this.drawString(this.fontRendererObj, "ViaBackwards", (this.width - this.fontRendererObj.getStringWidth("ViaBackwards")) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 2, -1);
        this.drawString(this.fontRendererObj, "ViaRewind", (this.width - this.fontRendererObj.getStringWidth("ViaRewind")) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 3, -1);
        this.drawString(this.fontRendererObj, florMich, (this.width - this.fontRendererObj.getStringWidth(florMich)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 5, -1);
        this.drawString(this.fontRendererObj, "ViaForge", (this.width - this.fontRendererObj.getStringWidth("ViaForge")) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 6, -1);
        this.drawString(this.fontRendererObj, laVache, (this.width - this.fontRendererObj.getStringWidth(laVache)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 8, -1);
        this.drawString(this.fontRendererObj, "Original ViaMCP", (this.width - this.fontRendererObj.getStringWidth("Original ViaMCP")) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 9, -1);
        this.drawString(this.fontRendererObj, hideri, (this.width - this.fontRendererObj.getStringWidth(hideri)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 11, -1);
        this.drawString(this.fontRendererObj, "ViaMCP Reborn", (this.width - this.fontRendererObj.getStringWidth("ViaMCP Reborn")) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 12, -1);
        this.drawString(this.fontRendererObj, contactInfo, (this.width - this.fontRendererObj.getStringWidth(contactInfo)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 14, -1);
        this.drawString(this.fontRendererObj, "Discord: Hideri#9003", (this.width - this.fontRendererObj.getStringWidth("Discord: Hideri#9003")) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 15, -1);
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/ViaVersion/ViaVersion)", this.width + this.fontRendererObj.getStringWidth("ViaVersion "), (fixedHeight + this.fontRendererObj.FONT_HEIGHT) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/ViaVersion/ViaBackward)", this.width + this.fontRendererObj.getStringWidth("ViaBackwards "), (fixedHeight + this.fontRendererObj.FONT_HEIGHT * 2) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/ViaVersion/ViaRewind)", this.width + this.fontRendererObj.getStringWidth("ViaRewind "), (fixedHeight + this.fontRendererObj.FONT_HEIGHT * 3) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/FlorianMichael/ViaForge)", this.width + this.fontRendererObj.getStringWidth("ViaForge "), (fixedHeight + this.fontRendererObj.FONT_HEIGHT * 6) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/LaVache-FR/ViaMCP)", this.width + this.fontRendererObj.getStringWidth("Original ViaMCP "), (fixedHeight + this.fontRendererObj.FONT_HEIGHT * 9) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        this.drawString(this.fontRendererObj, EnumChatFormatting.GRAY + "(https://github.com/Foreheadchann/ViaMCP-Reborn)", this.width + this.fontRendererObj.getStringWidth("ViaMCP Reborn "), (fixedHeight + this.fontRendererObj.FONT_HEIGHT * 12) * 2 + this.fontRendererObj.FONT_HEIGHT / 2, -1);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
