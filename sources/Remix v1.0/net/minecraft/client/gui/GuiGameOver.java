package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback
{
    private int field_146347_a;
    private boolean field_146346_f;
    
    public GuiGameOver() {
        this.field_146346_f = false;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (GuiGameOver.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
            if (GuiGameOver.mc.isIntegratedServerRunning()) {
                this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
            }
            else {
                this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
            }
        }
        else {
            this.buttonList.add(new GuiButton(0, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
            this.buttonList.add(new GuiButton(1, GuiGameOver.width / 2 - 100, GuiGameOver.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
            if (GuiGameOver.mc.getSession() == null) {
                this.buttonList.get(1).enabled = false;
            }
        }
        for (final GuiButton var2 : this.buttonList) {
            var2.enabled = false;
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                GuiGameOver.mc.thePlayer.respawnPlayer();
                GuiGameOver.mc.displayGuiScreen(null);
                break;
            }
            case 1: {
                final GuiYesNo var2 = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
                GuiGameOver.mc.displayGuiScreen(var2);
                var2.setButtonDelay(20);
                break;
            }
        }
    }
    
    @Override
    public void confirmClicked(final boolean result, final int id) {
        if (result) {
            GuiGameOver.mc.theWorld.sendQuittingDisconnectingPacket();
            GuiGameOver.mc.loadWorld(null);
            GuiGameOver.mc.displayGuiScreen(new GuiMainMenu());
        }
        else {
            GuiGameOver.mc.thePlayer.respawnPlayer();
            GuiGameOver.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawGradientRect(0, 0, GuiGameOver.width, GuiGameOver.height, 1615855616, -1602211792);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        final boolean var4 = GuiGameOver.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
        final String var5 = var4 ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
        Gui.drawCenteredString(this.fontRendererObj, var5, GuiGameOver.width / 2 / 2, 30, 16777215);
        GlStateManager.popMatrix();
        if (var4) {
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), GuiGameOver.width / 2, 144, 16777215);
        }
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + GuiGameOver.mc.thePlayer.getScore(), GuiGameOver.width / 2, 100, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146347_a;
        if (this.field_146347_a == 20) {
            for (final GuiButton var2 : this.buttonList) {
                var2.enabled = true;
            }
        }
    }
}
