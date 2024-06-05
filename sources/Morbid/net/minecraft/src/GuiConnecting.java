package net.minecraft.src;

import net.minecraft.client.*;

public class GuiConnecting extends GuiScreen
{
    private NetClientHandler clientHandler;
    private boolean cancelled;
    private final GuiScreen field_98098_c;
    
    public GuiConnecting(final GuiScreen par1GuiScreen, final Minecraft par2Minecraft, final ServerData par3ServerData) {
        this.cancelled = false;
        this.mc = par2Minecraft;
        this.field_98098_c = par1GuiScreen;
        final ServerAddress var4 = ServerAddress.func_78860_a(par3ServerData.serverIP);
        par2Minecraft.loadWorld(null);
        par2Minecraft.setServerData(par3ServerData);
        this.spawnNewServerThread(var4.getIP(), var4.getPort());
    }
    
    public GuiConnecting(final GuiScreen par1GuiScreen, final Minecraft par2Minecraft, final String par3Str, final int par4) {
        this.cancelled = false;
        this.mc = par2Minecraft;
        this.field_98098_c = par1GuiScreen;
        par2Minecraft.loadWorld(null);
        this.spawnNewServerThread(par3Str, par4);
    }
    
    private void spawnNewServerThread(final String par1Str, final int par2) {
        this.mc.getLogAgent().logInfo("Connecting to " + par1Str + ", " + par2);
        new ThreadConnectToServer(this, par1Str, par2).start();
    }
    
    @Override
    public void updateScreen() {
        if (this.clientHandler != null) {
            this.clientHandler.processReadPackets();
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0) {
            this.cancelled = true;
            if (this.clientHandler != null) {
                this.clientHandler.disconnect();
            }
            this.mc.displayGuiScreen(this.field_98098_c);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        final StringTranslate var4 = StringTranslate.getInstance();
        if (this.clientHandler == null) {
            this.drawCenteredString(this.fontRenderer, var4.translateKey("connect.connecting"), this.width / 2, this.height / 2 - 50, 16777215);
            this.drawCenteredString(this.fontRenderer, "", this.width / 2, this.height / 2 - 10, 16777215);
        }
        else {
            this.drawCenteredString(this.fontRenderer, var4.translateKey("connect.authorizing"), this.width / 2, this.height / 2 - 50, 16777215);
            this.drawCenteredString(this.fontRenderer, this.clientHandler.field_72560_a, this.width / 2, this.height / 2 - 10, 16777215);
        }
        super.drawScreen(par1, par2, par3);
    }
    
    static NetClientHandler setNetClientHandler(final GuiConnecting par0GuiConnecting, final NetClientHandler par1NetClientHandler) {
        return par0GuiConnecting.clientHandler = par1NetClientHandler;
    }
    
    static Minecraft func_74256_a(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.mc;
    }
    
    static boolean isCancelled(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.cancelled;
    }
    
    static Minecraft func_74254_c(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.mc;
    }
    
    static NetClientHandler getNetClientHandler(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.clientHandler;
    }
    
    static GuiScreen func_98097_e(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.field_98098_c;
    }
    
    static Minecraft func_74250_f(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.mc;
    }
    
    static Minecraft func_74251_g(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.mc;
    }
    
    static Minecraft func_98096_h(final GuiConnecting par0GuiConnecting) {
        return par0GuiConnecting.mc;
    }
}
