package net.minecraft.src;

class GuiBeaconButtonPower extends GuiBeaconButton
{
    private final int field_82261_l;
    private final int field_82262_m;
    final GuiBeacon beaconGui;
    
    public GuiBeaconButtonPower(final GuiBeacon par1GuiBeacon, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super(par2, par3, par4, "/gui/inventory.png", 0 + Potion.potionTypes[par5].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[par5].getStatusIconIndex() / 8 * 18);
        this.beaconGui = par1GuiBeacon;
        this.field_82261_l = par5;
        this.field_82262_m = par6;
    }
    
    @Override
    public void func_82251_b(final int par1, final int par2) {
        String var3 = StatCollector.translateToLocal(Potion.potionTypes[this.field_82261_l].getName());
        if (this.field_82262_m >= 3 && this.field_82261_l != Potion.regeneration.id) {
            var3 = String.valueOf(var3) + " II";
        }
        this.beaconGui.drawCreativeTabHoveringText(var3, par1, par2);
    }
}
