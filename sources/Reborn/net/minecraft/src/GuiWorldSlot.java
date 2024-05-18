package net.minecraft.src;

import java.util.*;

class GuiWorldSlot extends GuiSlot
{
    final GuiSelectWorld parentWorldGui;
    
    public GuiWorldSlot(final GuiSelectWorld par1GuiSelectWorld) {
        super(par1GuiSelectWorld.mc, par1GuiSelectWorld.width, par1GuiSelectWorld.height, 32, par1GuiSelectWorld.height - 64, 36);
        this.parentWorldGui = par1GuiSelectWorld;
    }
    
    @Override
    protected int getSize() {
        return GuiSelectWorld.getSize(this.parentWorldGui).size();
    }
    
    @Override
    protected void elementClicked(final int par1, final boolean par2) {
        GuiSelectWorld.onElementSelected(this.parentWorldGui, par1);
        final boolean var3 = GuiSelectWorld.getSelectedWorld(this.parentWorldGui) >= 0 && GuiSelectWorld.getSelectedWorld(this.parentWorldGui) < this.getSize();
        GuiSelectWorld.getSelectButton(this.parentWorldGui).enabled = var3;
        GuiSelectWorld.getRenameButton(this.parentWorldGui).enabled = var3;
        GuiSelectWorld.getDeleteButton(this.parentWorldGui).enabled = var3;
        GuiSelectWorld.func_82312_f(this.parentWorldGui).enabled = var3;
        if (par2 && var3) {
            this.parentWorldGui.selectWorld(par1);
        }
    }
    
    @Override
    protected boolean isSelected(final int par1) {
        return par1 == GuiSelectWorld.getSelectedWorld(this.parentWorldGui);
    }
    
    @Override
    protected int getContentHeight() {
        return GuiSelectWorld.getSize(this.parentWorldGui).size() * 36;
    }
    
    @Override
    protected void drawBackground() {
        this.parentWorldGui.drawDefaultBackground();
    }
    
    @Override
    protected void drawSlot(final int par1, final int par2, final int par3, final int par4, final Tessellator par5Tessellator) {
        final SaveFormatComparator var6 = GuiSelectWorld.getSize(this.parentWorldGui).get(par1);
        String var7 = var6.getDisplayName();
        if (var7 == null || MathHelper.stringNullOrLengthZero(var7)) {
            var7 = String.valueOf(GuiSelectWorld.func_82313_g(this.parentWorldGui)) + " " + (par1 + 1);
        }
        String var8 = var6.getFileName();
        var8 = String.valueOf(var8) + " (" + GuiSelectWorld.func_82315_h(this.parentWorldGui).format(new Date(var6.getLastTimePlayed()));
        var8 = String.valueOf(var8) + ")";
        String var9 = "";
        if (var6.requiresConversion()) {
            var9 = String.valueOf(GuiSelectWorld.func_82311_i(this.parentWorldGui)) + " " + var9;
        }
        else {
            var9 = GuiSelectWorld.func_82314_j(this.parentWorldGui)[var6.getEnumGameType().getID()];
            if (var6.isHardcoreModeEnabled()) {
                var9 = EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("gameMode.hardcore") + EnumChatFormatting.RESET;
            }
            if (var6.getCheatsEnabled()) {
                var9 = String.valueOf(var9) + ", " + StatCollector.translateToLocal("selectWorld.cheats");
            }
        }
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, var7, par2 + 2, par3 + 1, 16777215);
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, var8, par2 + 2, par3 + 12, 8421504);
        this.parentWorldGui.drawString(this.parentWorldGui.fontRenderer, var9, par2 + 2, par3 + 12 + 10, 8421504);
    }
}
