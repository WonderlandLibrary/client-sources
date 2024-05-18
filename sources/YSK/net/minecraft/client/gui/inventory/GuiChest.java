package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class GuiChest extends GuiContainer
{
    private static final ResourceLocation CHEST_GUI_TEXTURE;
    private int inventoryRows;
    private IInventory upperChestInventory;
    private static final String[] I;
    private IInventory lowerChestInventory;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("&(4\u001b\u001e (?@\f'$c\f\u0004<9-\u0006\u00057?c\b\u000e<(>\u0006\b\rxxA\u001b<*", "RMLok");
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 0x79 ^ 0x71, 0x7C ^ 0x7A, 2816727 + 91749 + 804647 + 497629);
        this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 0x3B ^ 0x33, this.ySize - (0xD1 ^ 0xB1) + "  ".length(), 826652 + 4207862 - 4652609 + 3828847);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiChest.CHEST_GUI_TEXTURE);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        this.drawTexturedModalRect(n4, n5, "".length(), "".length(), this.xSize, this.inventoryRows * (0x1 ^ 0x13) + (0x6F ^ 0x7E));
        this.drawTexturedModalRect(n4, n5 + this.inventoryRows * (0x57 ^ 0x45) + (0x49 ^ 0x58), "".length(), 0xC6 ^ 0xB8, this.xSize, 0xED ^ 0x8D);
    }
    
    static {
        I();
        CHEST_GUI_TEXTURE = new ResourceLocation(GuiChest.I["".length()]);
    }
    
    public GuiChest(final IInventory upperChestInventory, final IInventory lowerChestInventory) {
        super(new ContainerChest(upperChestInventory, lowerChestInventory, Minecraft.getMinecraft().thePlayer));
        this.upperChestInventory = upperChestInventory;
        this.lowerChestInventory = lowerChestInventory;
        this.allowUserInput = ("".length() != 0);
        final int n = 129 + 47 + 23 + 23 - (0x58 ^ 0x34);
        this.inventoryRows = lowerChestInventory.getSizeInventory() / (0x10 ^ 0x19);
        this.ySize = n + this.inventoryRows * (0x40 ^ 0x52);
    }
}
