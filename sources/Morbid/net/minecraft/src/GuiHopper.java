package net.minecraft.src;

import org.lwjgl.opengl.*;

public class GuiHopper extends GuiContainer
{
    private IInventory field_94081_r;
    private IInventory field_94080_s;
    
    public GuiHopper(final InventoryPlayer par1InventoryPlayer, final IInventory par2IInventory) {
        super(new ContainerHopper(par1InventoryPlayer, par2IInventory));
        this.field_94081_r = par1InventoryPlayer;
        this.field_94080_s = par2IInventory;
        this.allowUserInput = false;
        this.ySize = 133;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        this.fontRenderer.drawString(this.field_94080_s.isInvNameLocalized() ? this.field_94080_s.getInvName() : StatCollector.translateToLocal(this.field_94080_s.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.field_94081_r.isInvNameLocalized() ? this.field_94081_r.getInvName() : StatCollector.translateToLocal(this.field_94081_r.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/hopper.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
    }
}
