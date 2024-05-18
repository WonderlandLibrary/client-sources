package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;

public class GuiFurnace extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures;
    private final InventoryPlayer playerInventory;
    private IInventory tileFurnace;
    private static final String[] I;
    
    private int getBurnLeftScaled(final int n) {
        int field = this.tileFurnace.getField(" ".length());
        if (field == 0) {
            field = 60 + 100 - 42 + 82;
        }
        return this.tileFurnace.getField("".length()) * n / field;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiFurnace.furnaceGuiTextures);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        this.drawTexturedModalRect(n4, n5, "".length(), "".length(), this.xSize, this.ySize);
        if (TileEntityFurnace.isBurning(this.tileFurnace)) {
            final int burnLeftScaled = this.getBurnLeftScaled(0x7C ^ 0x71);
            this.drawTexturedModalRect(n4 + (0x11 ^ 0x29), n5 + (0xBE ^ 0x9A) + (0x6D ^ 0x61) - burnLeftScaled, 19 + 97 - 112 + 172, (0x48 ^ 0x44) - burnLeftScaled, 0x6C ^ 0x62, burnLeftScaled + " ".length());
        }
        this.drawTexturedModalRect(n4 + (0xFD ^ 0xB2), n5 + (0x2F ^ 0xD), 137 + 36 - 18 + 21, 0x5 ^ 0xB, this.getCookProgressScaled(0x9F ^ 0x87) + " ".length(), 0xC ^ 0x1C);
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.tileFurnace.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / "  ".length() - this.fontRendererObj.getStringWidth(unformattedText) / "  ".length(), 0x44 ^ 0x42, 3731213 + 1290383 - 3152951 + 2342107);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 0x70 ^ 0x78, this.ySize - (0xDC ^ 0xBC) + "  ".length(), 604772 + 2430799 - 974324 + 2149505);
    }
    
    public GuiFurnace(final InventoryPlayer playerInventory, final IInventory tileFurnace) {
        super(new ContainerFurnace(playerInventory, tileFurnace));
        this.playerInventory = playerInventory;
        this.tileFurnace = tileFurnace;
    }
    
    private int getCookProgressScaled(final int n) {
        final int field = this.tileFurnace.getField("  ".length());
        final int field2 = this.tileFurnace.getField("   ".length());
        int length;
        if (field2 != 0 && field != 0) {
            length = field * n / field2;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u00191\u0017\u001e;\u001f1\u001cE)\u0018=@\t!\u0003 \u000e\u0003 \b&@\f;\u001f:\u000e\t+C$\u0001\r", "mTojN");
    }
    
    static {
        I();
        furnaceGuiTextures = new ResourceLocation(GuiFurnace.I["".length()]);
    }
}
