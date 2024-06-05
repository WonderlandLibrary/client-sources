package net.minecraft.src;

import net.minecraft.client.*;

public class GuiIngameMenu extends GuiScreen
{
    private int updateCounter2;
    private int updateCounter;
    
    public GuiIngameMenu() {
        this.updateCounter2 = 0;
        this.updateCounter = 0;
    }
    
    @Override
    public void initGui() {
        this.updateCounter2 = 0;
        this.buttonList.clear();
        final byte var1 = -16;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + var1, StatCollector.translateToLocal("menu.returnToMenu")));
        if (!this.mc.isIntegratedServerRunning()) {
            this.buttonList.get(0).displayString = StatCollector.translateToLocal("menu.disconnect");
        }
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + var1, StatCollector.translateToLocal("menu.returnToGame")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + var1, 98, 20, StatCollector.translateToLocal("menu.options")));
        final GuiButton var2;
        this.buttonList.add(var2 = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + var1, 98, 20, StatCollector.translateToLocal("menu.shareToLan")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + var1, 98, 20, StatCollector.translateToLocal("gui.achievements")));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + var1, 98, 20, StatCollector.translateToLocal("gui.stats")));
        var2.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        switch (par1GuiButton.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 1: {
                par1GuiButton.enabled = false;
                this.mc.statFileWriter.readStat(StatList.leaveGameStat, 1);
                Minecraft.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                this.mc.displayGuiScreen(new GuiMainMenu());
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                this.mc.sndManager.resumeAllSounds();
                break;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
                break;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            }
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCounter;
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Game menu", this.width / 2, 40, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
