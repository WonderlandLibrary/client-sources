package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;

public class GuiCrafting extends GuiContainer
{
    private static final String[] I;
    private static final ResourceLocation craftingTableGuiTextures;
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiCrafting.craftingTableGuiTextures);
        this.drawTexturedModalRect((this.width - this.xSize) / "  ".length(), (this.height - this.ySize) / "  ".length(), "".length(), "".length(), this.xSize, this.ySize);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(I18n.format(GuiCrafting.I[" ".length()], new Object["".length()]), 0x10 ^ 0xC, 0xC4 ^ 0xC2, 3650380 + 4144511 - 4764347 + 1180208);
        this.fontRendererObj.drawString(I18n.format(GuiCrafting.I["  ".length()], new Object["".length()]), 0x34 ^ 0x3C, this.ySize - (0x25 ^ 0x45) + "  ".length(), 1727506 + 4110338 - 3726441 + 2099349);
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiCrafting(final InventoryPlayer inventoryPlayer, final World world) {
        this(inventoryPlayer, world, BlockPos.ORIGIN);
    }
    
    public GuiCrafting(final InventoryPlayer inventoryPlayer, final World world, final BlockPos blockPos) {
        super(new ContainerWorkbench(inventoryPlayer, world, blockPos));
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("&\u0006:\u0016\u0017 \u00061M\u0005'\nm\u0001\r<\u0017#\u000b\f7\u0011m\u0001\u00103\u00056\u000b\f5<6\u0003\u0000>\u0006l\u0012\f5", "RcBbb");
        GuiCrafting.I[" ".length()] = I("$\u0003 \u0018;.\u0002+\u001et$\u001e/\n..\u0002)", "GlNlZ");
        GuiCrafting.I["  ".length()] = I("+\u0018\u00019\u001b!\u0019\n?T!\u0019\u0019(\u0014<\u0018\u001d4", "HwoMz");
    }
    
    static {
        I();
        craftingTableGuiTextures = new ResourceLocation(GuiCrafting.I["".length()]);
    }
}
