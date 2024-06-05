package net.minecraft.src;

import org.lwjgl.opengl.*;

public class GuiBrewingStand extends GuiContainer
{
    private TileEntityBrewingStand brewingStand;
    
    public GuiBrewingStand(final InventoryPlayer par1InventoryPlayer, final TileEntityBrewingStand par2TileEntityBrewingStand) {
        super(new ContainerBrewingStand(par1InventoryPlayer, par2TileEntityBrewingStand));
        this.brewingStand = par2TileEntityBrewingStand;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        final String var3 = this.brewingStand.isInvNameLocalized() ? this.brewingStand.getInvName() : StatCollector.translateToLocal(this.brewingStand.getInvName());
        this.fontRenderer.drawString(var3, this.xSize / 2 - this.fontRenderer.getStringWidth(var3) / 2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/alchemy.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        final int var6 = this.brewingStand.getBrewTime();
        if (var6 > 0) {
            int var7 = (int)(28.0f * (1.0f - var6 / 400.0f));
            if (var7 > 0) {
                this.drawTexturedModalRect(var4 + 97, var5 + 16, 176, 0, 9, var7);
            }
            final int var8 = var6 / 2 % 7;
            switch (var8) {
                case 0: {
                    var7 = 29;
                    break;
                }
                case 1: {
                    var7 = 24;
                    break;
                }
                case 2: {
                    var7 = 20;
                    break;
                }
                case 3: {
                    var7 = 16;
                    break;
                }
                case 4: {
                    var7 = 11;
                    break;
                }
                case 5: {
                    var7 = 6;
                    break;
                }
                case 6: {
                    var7 = 0;
                    break;
                }
            }
            if (var7 > 0) {
                this.drawTexturedModalRect(var4 + 65, var5 + 14 + 29 - var7, 185, 29 - var7, 12, var7);
            }
        }
    }
}
