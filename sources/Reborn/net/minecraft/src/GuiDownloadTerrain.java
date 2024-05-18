package net.minecraft.src;

public class GuiDownloadTerrain extends GuiScreen
{
    private NetClientHandler netHandler;
    private int updateCounter;
    
    public GuiDownloadTerrain(final NetClientHandler par1NetClientHandler) {
        this.updateCounter = 0;
        this.netHandler = par1NetClientHandler;
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
    }
    
    @Override
    public void updateScreen() {
        ++this.updateCounter;
        if (this.updateCounter % 20 == 0) {
            this.netHandler.addToSendQueue(new Packet0KeepAlive());
        }
        if (this.netHandler != null) {
            this.netHandler.processReadPackets();
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawBackground(0);
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
