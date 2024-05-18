package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.*;

public class GuiBrewingStand extends GuiContainer
{
    private IInventory tileBrewingStand;
    private final InventoryPlayer playerInventory;
    private static final ResourceLocation brewingStandGuiTextures;
    private static final String[] I;
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.tileBrewingStand.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / "  ".length() - this.fontRendererObj.getStringWidth(unformattedText) / "  ".length(), 0x38 ^ 0x3E, 3144434 + 3391175 - 6425326 + 4100469);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 0x81 ^ 0x89, this.ySize - (0xC8 ^ 0xA8) + "  ".length(), 2814857 + 32457 - 2518039 + 3881477);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiBrewingStand.brewingStandGuiTextures);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        this.drawTexturedModalRect(n4, n5, "".length(), "".length(), this.xSize, this.ySize);
        final int field = this.tileBrewingStand.getField("".length());
        if (field > 0) {
            int length = (int)(28.0f * (1.0f - field / 400.0f));
            if (length > 0) {
                this.drawTexturedModalRect(n4 + (0x6A ^ 0xB), n5 + (0x28 ^ 0x38), 30 + 112 - 5 + 39, "".length(), 0x9A ^ 0x93, length);
            }
            switch (field / "  ".length() % (0x41 ^ 0x46)) {
                case 0: {
                    length = (0x73 ^ 0x6E);
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    length = (0x75 ^ 0x6D);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    length = (0x51 ^ 0x45);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    length = (0x5F ^ 0x4F);
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    length = (0xB4 ^ 0xBF);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    length = (0x98 ^ 0x9E);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    length = "".length();
                    break;
                }
            }
            if (length > 0) {
                this.drawTexturedModalRect(n4 + (0xCA ^ 0x8B), n5 + (0x4 ^ 0xA) + (0xA2 ^ 0xBF) - length, 118 + 128 - 94 + 33, (0x7D ^ 0x60) - length, 0x8A ^ 0x86, length);
            }
        }
    }
    
    public GuiBrewingStand(final InventoryPlayer playerInventory, final IInventory tileBrewingStand) {
        super(new ContainerBrewingStand(playerInventory, tileBrewingStand));
        this.playerInventory = playerInventory;
        this.tileBrewingStand = tileBrewingStand;
    }
    
    static {
        I();
        brewingStandGuiTextures = new ResourceLocation(GuiBrewingStand.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001f3\u00146\u0018\u00193\u001fm\n\u001e?C!\u0002\u0005\"\r+\u0003\u000e$C \u001f\u000e!\u0005,\n4%\u0018#\u0003\u000fx\u001c,\n", "kVlBm");
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
}
