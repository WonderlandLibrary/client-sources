package net.minecraft.src;

class GuiBeaconButtonCancel extends GuiBeaconButton
{
    final GuiBeacon beaconGui;
    
    public GuiBeaconButtonCancel(final GuiBeacon par1, final int par2, final int par3, final int par4) {
        super(par2, par3, par4, "/gui/beacon.png", 112, 220);
        this.beaconGui = par1;
    }
    
    @Override
    public void func_82251_b(final int par1, final int par2) {
        this.beaconGui.drawCreativeTabHoveringText(StatCollector.translateToLocal("gui.cancel"), par1, par2);
    }
}
