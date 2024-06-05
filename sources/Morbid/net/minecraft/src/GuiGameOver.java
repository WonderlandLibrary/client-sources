package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.opengl.*;

public class GuiGameOver extends GuiScreen
{
    private int cooldownTimer;
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
            if (this.mc.isIntegratedServerRunning()) {
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, StatCollector.translateToLocal("deathScreen.deleteWorld")));
            }
            else {
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, StatCollector.translateToLocal("deathScreen.leaveServer")));
            }
        }
        else {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72, StatCollector.translateToLocal("deathScreen.respawn")));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96, StatCollector.translateToLocal("deathScreen.titleScreen")));
            if (this.mc.session == null) {
                this.buttonList.get(1).enabled = false;
            }
        }
        for (final GuiButton var3 : this.buttonList) {
            var3.enabled = false;
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        switch (par1GuiButton.id) {
            case 1: {
                Minecraft.thePlayer.respawnPlayer();
                this.mc.displayGuiScreen(null);
                break;
            }
            case 2: {
                Minecraft.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                this.mc.displayGuiScreen(new GuiMainMenu());
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        final boolean var4 = Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled();
        final String var5 = var4 ? StatCollector.translateToLocal("deathScreen.title.hardcore") : StatCollector.translateToLocal("deathScreen.title");
        this.drawCenteredString(this.fontRenderer, var5, this.width / 2 / 2, 30, 16777215);
        GL11.glPopMatrix();
        if (var4) {
            this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("deathScreen.hardcoreInfo"), this.width / 2, 144, 16777215);
        }
        this.drawCenteredString(this.fontRenderer, String.valueOf(StatCollector.translateToLocal("deathScreen.score")) + ": " + EnumChatFormatting.YELLOW + Minecraft.thePlayer.getScore(), this.width / 2, 100, 16777215);
        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.cooldownTimer;
        if (this.cooldownTimer == 20) {
            for (final GuiButton var3 : this.buttonList) {
                var3.enabled = true;
            }
        }
    }
}
