package net.minecraft.src;

import org.lwjgl.opengl.*;

public class GuiCrafting extends GuiContainer
{
    public GuiCrafting(final InventoryPlayer par1InventoryPlayer, final World par2World, final int par3, final int par4, final int par5) {
        super(new ContainerWorkbench(par1InventoryPlayer, par2World, par3, par4, par5));
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.crafting"), 28, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/crafting.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
    }
}
