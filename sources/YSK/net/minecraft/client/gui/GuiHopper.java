package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class GuiHopper extends GuiContainer
{
    private static final ResourceLocation HOPPER_GUI_TEXTURE;
    private IInventory playerInventory;
    private IInventory hopperInventory;
    private static final String[] I;
    
    static {
        I();
        HOPPER_GUI_TEXTURE = new ResourceLocation(GuiHopper.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("-\u001d \u0019\u0010+\u001d+B\u0002,\u0011w\u000e\n7\f9\u0004\u000b<\nw\u0005\n)\b=\u001fK)\u0016?", "YxXme");
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiHopper.HOPPER_GUI_TEXTURE);
        this.drawTexturedModalRect((this.width - this.xSize) / "  ".length(), (this.height - this.ySize) / "  ".length(), "".length(), "".length(), this.xSize, this.ySize);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.hopperInventory.getDisplayName().getUnformattedText(), 0x52 ^ 0x5A, 0xAC ^ 0xAA, 3014637 + 1017074 - 366526 + 545567);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 0xA4 ^ 0xAC, this.ySize - (0x14 ^ 0x74) + "  ".length(), 172602 + 982635 + 81900 + 2973615);
    }
    
    public GuiHopper(final InventoryPlayer playerInventory, final IInventory hopperInventory) {
        super(new ContainerHopper(playerInventory, hopperInventory, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInventory;
        this.hopperInventory = hopperInventory;
        this.allowUserInput = ("".length() != 0);
        this.ySize = 111 + 130 - 186 + 78;
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
