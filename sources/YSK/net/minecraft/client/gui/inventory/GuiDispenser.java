package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

public class GuiDispenser extends GuiContainer
{
    private static final String[] I;
    private static final ResourceLocation dispenserGuiTextures;
    public IInventory dispenserInventory;
    private final InventoryPlayer playerInventory;
    
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(".#\u001e'\u0013(#\u0015|\u0001//I0\t42\u0007:\b?4I7\u000f)6\u0003=\u0015?4H#\b=", "ZFfSf");
    }
    
    public GuiDispenser(final InventoryPlayer playerInventory, final IInventory dispenserInventory) {
        super(new ContainerDispenser(playerInventory, dispenserInventory));
        this.playerInventory = playerInventory;
        this.dispenserInventory = dispenserInventory;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.dispenserInventory.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / "  ".length() - this.fontRendererObj.getStringWidth(unformattedText) / "  ".length(), 0xC4 ^ 0xC2, 329509 + 3337646 - 132671 + 676268);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 0xB2 ^ 0xBA, this.ySize - (0x31 ^ 0x51) + "  ".length(), 1077291 + 1893288 - 1870840 + 3111013);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiDispenser.dispenserGuiTextures);
        this.drawTexturedModalRect((this.width - this.xSize) / "  ".length(), (this.height - this.ySize) / "  ".length(), "".length(), "".length(), this.xSize, this.ySize);
    }
    
    static {
        I();
        dispenserGuiTextures = new ResourceLocation(GuiDispenser.I["".length()]);
    }
}
