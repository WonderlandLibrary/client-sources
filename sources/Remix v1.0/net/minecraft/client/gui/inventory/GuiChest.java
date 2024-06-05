package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiChest extends GuiContainer
{
    private static final ResourceLocation field_147017_u;
    public IInventory lowerChestInventory;
    private IInventory upperChestInventory;
    private int inventoryRows;
    
    public GuiChest(final IInventory p_i46315_1_, final IInventory p_i46315_2_) {
        super(new ContainerChest(p_i46315_1_, p_i46315_2_, Minecraft.getMinecraft().thePlayer));
        this.upperChestInventory = p_i46315_1_;
        this.lowerChestInventory = p_i46315_2_;
        this.allowUserInput = false;
        final short var3 = 222;
        final int var4 = var3 - 108;
        this.inventoryRows = p_i46315_2_.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiChest.mc.getTextureManager().bindTexture(GuiChest.field_147017_u);
        final int var4 = (GuiChest.width - this.xSize) / 2;
        final int var5 = (GuiChest.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var4, var5 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
    
    static {
        field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
    }
}
