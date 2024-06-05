package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.achievement.*;
import me.satisfactory.base.gui.*;

public class GuiIngameMenu extends GuiScreen
{
    private int field_146445_a;
    private int field_146444_f;
    
    @Override
    public void initGui() {
        this.field_146445_a = 0;
        this.buttonList.clear();
        final int i = -16;
        final int j = 98;
        this.buttonList.add(new GuiButton(1, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));
        if (!GuiIngameMenu.mc.isIntegratedServerRunning()) {
            this.buttonList.get(0).displayString = I18n.format("menu.disconnect", new Object[0]);
        }
        this.buttonList.add(new GuiButton(1337, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 48 + i + 24, "BungeeExploit"));
        this.buttonList.add(new GuiButton(4, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
        final GuiButton guibutton;
        this.buttonList.add(guibutton = new GuiButton(7, GuiIngameMenu.width / 2 + 2, GuiIngameMenu.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        this.buttonList.add(new GuiButton(5, GuiIngameMenu.width / 2 - 100, GuiIngameMenu.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, GuiIngameMenu.width / 2 + 2, GuiIngameMenu.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
        guibutton.enabled = (GuiIngameMenu.mc.isSingleplayer() && !GuiIngameMenu.mc.getIntegratedServer().getPublic());
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiOptions(this, GuiIngameMenu.mc.gameSettings));
                break;
            }
            case 1: {
                button.enabled = false;
                GuiIngameMenu.mc.theWorld.sendQuittingDisconnectingPacket();
                GuiIngameMenu.mc.loadWorld(null);
                GuiIngameMenu.mc.displayGuiScreen(new GuiMainMenu());
            }
            case 2:
            case 3:
            case 4: {
                GuiIngameMenu.mc.displayGuiScreen(null);
                GuiIngameMenu.mc.setIngameFocus();
                break;
            }
            case 5: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiAchievements(this, GuiIngameMenu.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 6: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiStats(this, GuiIngameMenu.mc.thePlayer.getStatFileWriter()));
                break;
            }
            case 7: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            }
            case 1337: {
                GuiIngameMenu.mc.displayGuiScreen(new GuiBungeeCord(this));
                break;
            }
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146444_f;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), GuiIngameMenu.width / 2, 40, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
