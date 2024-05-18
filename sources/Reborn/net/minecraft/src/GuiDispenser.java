package net.minecraft.src;

import org.lwjgl.opengl.*;

public class GuiDispenser extends GuiContainer
{
    public TileEntityDispenser field_94078_r;
    
    public GuiDispenser(final InventoryPlayer par1InventoryPlayer, final TileEntityDispenser par2TileEntityDispenser) {
        super(new ContainerDispenser(par1InventoryPlayer, par2TileEntityDispenser));
        this.field_94078_r = par2TileEntityDispenser;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        final String var3 = this.field_94078_r.isInvNameLocalized() ? this.field_94078_r.getInvName() : StatCollector.translateToLocal(this.field_94078_r.getInvName());
        this.fontRenderer.drawString(var3, this.xSize / 2 - this.fontRenderer.getStringWidth(var3) / 2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/trap.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
    }
}
