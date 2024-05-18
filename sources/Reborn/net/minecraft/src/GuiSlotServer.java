package net.minecraft.src;

import org.lwjgl.opengl.*;
import net.minecraft.client.*;

class GuiSlotServer extends GuiSlot
{
    final GuiMultiplayer parentGui;
    
    public GuiSlotServer(final GuiMultiplayer par1GuiMultiplayer) {
        super(par1GuiMultiplayer.mc, par1GuiMultiplayer.width, par1GuiMultiplayer.height, 32, par1GuiMultiplayer.height - 64, 36);
        this.parentGui = par1GuiMultiplayer;
    }
    
    @Override
    protected int getSize() {
        return GuiMultiplayer.getInternetServerList(this.parentGui).countServers() + GuiMultiplayer.getListOfLanServers(this.parentGui).size() + 1;
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
        if (par1 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers() + GuiMultiplayer.getListOfLanServers(this.parentGui).size()) {
            final int var3 = GuiMultiplayer.getSelectedServer(this.parentGui);
            GuiMultiplayer.getAndSetSelectedServer(this.parentGui, par1);
            final ServerData var4 = (GuiMultiplayer.getInternetServerList(this.parentGui).countServers() > par1) ? GuiMultiplayer.getInternetServerList(this.parentGui).getServerData(par1) : null;
            final boolean var5 = GuiMultiplayer.getSelectedServer(this.parentGui) >= 0 && GuiMultiplayer.getSelectedServer(this.parentGui) < this.getSize() && (var4 == null || var4.field_82821_f == 61);
            final boolean var6 = GuiMultiplayer.getSelectedServer(this.parentGui) < GuiMultiplayer.getInternetServerList(this.parentGui).countServers();
            GuiMultiplayer.getButtonSelect(this.parentGui).enabled = var5;
            GuiMultiplayer.getButtonEdit(this.parentGui).enabled = var6;
            GuiMultiplayer.getButtonDelete(this.parentGui).enabled = var6;
            if (par2 && var5) {
                GuiMultiplayer.func_74008_b(this.parentGui, par1);
            }
            else if (var6 && GuiScreen.isShiftKeyDown() && var3 >= 0 && var3 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers()) {
                GuiMultiplayer.getInternetServerList(this.parentGui).swapServers(var3, GuiMultiplayer.getSelectedServer(this.parentGui));
            }
        }
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return par1 == GuiMultiplayer.getSelectedServer(this.parentGui);
    }
    
    @Override
    protected int getContentHeight() {
        return this.getSize() * 36;
    }
    
    @Override
    protected void drawBackground() {
        this.parentGui.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        if (par1 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers()) {
            this.func_77247_d(par1, par2, par3, par4, par5Tessellator);
        }
        else if (par1 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers() + GuiMultiplayer.getListOfLanServers(this.parentGui).size()) {
            this.func_77248_b(par1, par2, par3, par4, par5Tessellator);
        }
        else {
            this.func_77249_c(par1, par2, par3, par4, par5Tessellator);
        }
    }
    
    private void func_77248_b(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final LanServer var6 = GuiMultiplayer.getListOfLanServers(this.parentGui).get(par1 - GuiMultiplayer.getInternetServerList(this.parentGui).countServers());
        this.parentGui.drawString(this.parentGui.fontRenderer, StatCollector.translateToLocal("lanServer.title"), par2 + 2, par3 + 1, 16777215);
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.getServerMotd(), par2 + 2, par3 + 12, 8421504);
        if (this.parentGui.mc.gameSettings.hideServerAddress) {
            this.parentGui.drawString(this.parentGui.fontRenderer, StatCollector.translateToLocal("selectServer.hiddenAddress"), par2 + 2, par3 + 12 + 11, 3158064);
        }
        else {
            this.parentGui.drawString(this.parentGui.fontRenderer, var6.getServerIpPort(), par2 + 2, par3 + 12 + 11, 3158064);
        }
    }
    
    private void func_77249_c(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        this.parentGui.drawCenteredString(this.parentGui.fontRenderer, StatCollector.translateToLocal("lanServer.scanning"), this.parentGui.width / 2, par3 + 1, 16777215);
        String var6 = null;
        switch (GuiMultiplayer.getTicksOpened(this.parentGui) / 3 % 4) {
            default: {
                var6 = "O o o";
                break;
            }
            case 1:
            case 3: {
                var6 = "o O o";
                break;
            }
            case 2: {
                var6 = "o o O";
                break;
            }
        }
        this.parentGui.drawCenteredString(this.parentGui.fontRenderer, var6, this.parentGui.width / 2, par3 + 12, 8421504);
    }
    
    private void func_77247_d(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final ServerData var6 = GuiMultiplayer.getInternetServerList(this.parentGui).getServerData(par1);
        synchronized (GuiMultiplayer.getLock()) {
            if (GuiMultiplayer.getThreadsPending() < 5 && !var6.field_78841_f) {
                var6.field_78841_f = true;
                var6.pingToServer = -2L;
                var6.serverMOTD = "";
                var6.populationInfo = "";
                GuiMultiplayer.increaseThreadsPending();
                new ThreadPollServers(this, var6).start();
            }
        }
        // monitorexit(GuiMultiplayer.getLock())
        final boolean var7 = var6.field_82821_f > 61;
        final boolean var8 = var6.field_82821_f < 61;
        final boolean var9 = var7 || var8;
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.serverName, par2 + 2, par3 + 1, 16777215);
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.serverMOTD, par2 + 2, par3 + 12, 8421504);
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.populationInfo, par2 + 215 - this.parentGui.fontRenderer.getStringWidth(var6.populationInfo), par3 + 12, 8421504);
        if (var9) {
            final String var10 = EnumChatFormatting.DARK_RED + var6.gameVersion;
            this.parentGui.drawString(this.parentGui.fontRenderer, var10, par2 + 200 - this.parentGui.fontRenderer.getStringWidth(var10), par3 + 1, 8421504);
        }
        if (!this.parentGui.mc.gameSettings.hideServerAddress && !var6.isHidingAddress()) {
            this.parentGui.drawString(this.parentGui.fontRenderer, var6.serverIP, par2 + 2, par3 + 12 + 11, 3158064);
        }
        else {
            this.parentGui.drawString(this.parentGui.fontRenderer, StatCollector.translateToLocal("selectServer.hiddenAddress"), par2 + 2, par3 + 12 + 11, 3158064);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.parentGui.mc.renderEngine.bindTexture("/gui/icons.png");
        byte var11 = 0;
        final boolean var12 = false;
        String var13 = "";
        int var14;
        if (var9) {
            var13 = (var7 ? "Client out of date!" : "Server out of date!");
            var14 = 5;
        }
        else if (var6.field_78841_f && var6.pingToServer != -2L) {
            if (var6.pingToServer < 0L) {
                var14 = 5;
            }
            else if (var6.pingToServer < 150L) {
                var14 = 0;
            }
            else if (var6.pingToServer < 300L) {
                var14 = 1;
            }
            else if (var6.pingToServer < 600L) {
                var14 = 2;
            }
            else if (var6.pingToServer < 1000L) {
                var14 = 3;
            }
            else {
                var14 = 4;
            }
            if (var6.pingToServer < 0L) {
                var13 = "(no connection)";
            }
            else {
                var13 = String.valueOf(var6.pingToServer) + "ms";
            }
        }
        else {
            var11 = 1;
            var14 = (int)(Minecraft.getSystemTime() / 100L + par1 * 2 & 0x7L);
            if (var14 > 4) {
                var14 = 8 - var14;
            }
            var13 = "Polling..";
        }
        this.parentGui.drawTexturedModalRect(par2 + 205, par3, 0 + var11 * 10, 176 + var14 * 8, 10, 8);
        final byte var15 = 4;
        if (this.mouseX >= par2 + 205 - var15 && this.mouseY >= par3 - var15 && this.mouseX <= par2 + 205 + 10 + var15 && this.mouseY <= par3 + 8 + var15) {
            GuiMultiplayer.getAndSetLagTooltip(this.parentGui, var13);
        }
    }
}
