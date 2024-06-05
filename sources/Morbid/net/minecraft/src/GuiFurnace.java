package net.minecraft.src;

import org.lwjgl.opengl.*;

public class GuiFurnace extends GuiContainer
{
    private TileEntityFurnace furnaceInventory;
    
    public GuiFurnace(final InventoryPlayer par1InventoryPlayer, final TileEntityFurnace par2TileEntityFurnace) {
        super(new ContainerFurnace(par1InventoryPlayer, par2TileEntityFurnace));
        this.furnaceInventory = par2TileEntityFurnace;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        final String var3 = this.furnaceInventory.isInvNameLocalized() ? this.furnaceInventory.getInvName() : StatCollector.translateToLocal(this.furnaceInventory.getInvName());
        this.fontRenderer.drawString(var3, this.xSize / 2 - this.fontRenderer.getStringWidth(var3) / 2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/furnace.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        if (this.furnaceInventory.isBurning()) {
            final int var6 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var4 + 56, var5 + 36 + 12 - var6, 176, 12 - var6, 14, var6 + 2);
        }
        final int var6 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var4 + 79, var5 + 34, 176, 14, var6 + 1, 16);
    }
}
