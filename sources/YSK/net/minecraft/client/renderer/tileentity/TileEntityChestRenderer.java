package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest>
{
    private static final ResourceLocation textureChristmas;
    private static final ResourceLocation textureTrappedDouble;
    private static final ResourceLocation textureTrapped;
    private ModelChest largeChest;
    private ModelChest simpleChest;
    private static final ResourceLocation textureNormal;
    private static final ResourceLocation textureChristmasDouble;
    private static final String[] I;
    private boolean isChristams;
    private static final ResourceLocation textureNormalDouble;
    
    public TileEntityChestRenderer() {
        this.simpleChest = new ModelChest();
        this.largeChest = new ModelLargeChest();
        final Calendar instance = Calendar.getInstance();
        if (instance.get("  ".length()) + " ".length() == (0x3 ^ 0xF) && instance.get(0x70 ^ 0x75) >= (0xA3 ^ 0xBB) && instance.get(0x3F ^ 0x3A) <= (0x4 ^ 0x1E)) {
            this.isChristams = (" ".length() != 0);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntityChest tileEntityChest, final double n, final double n2, final double n3, final float n4, final int n5) {
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(96 + 469 - 174 + 124);
        GlStateManager.depthMask(" ".length() != 0);
        int n6;
        if (!tileEntityChest.hasWorldObj()) {
            n6 = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            final Block blockType = tileEntityChest.getBlockType();
            n6 = tileEntityChest.getBlockMetadata();
            if (blockType instanceof BlockChest && n6 == 0) {
                ((BlockChest)blockType).checkForSurroundingChests(tileEntityChest.getWorld(), tileEntityChest.getPos(), tileEntityChest.getWorld().getBlockState(tileEntityChest.getPos()));
                n6 = tileEntityChest.getBlockMetadata();
            }
            tileEntityChest.checkForAdjacentChests();
        }
        if (tileEntityChest.adjacentChestZNeg == null && tileEntityChest.adjacentChestXNeg == null) {
            ModelChest modelChest;
            if (tileEntityChest.adjacentChestXPos == null && tileEntityChest.adjacentChestZPos == null) {
                modelChest = this.simpleChest;
                if (n5 >= 0) {
                    this.bindTexture(TileEntityChestRenderer.DESTROY_STAGES[n5]);
                    GlStateManager.matrixMode(3817 + 3964 - 3629 + 1738);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(4.0f, 4.0f, 1.0f);
                    GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
                    GlStateManager.matrixMode(1644 + 4636 - 4375 + 3983);
                    "".length();
                    if (2 == 3) {
                        throw null;
                    }
                }
                else if (tileEntityChest.getChestType() == " ".length()) {
                    this.bindTexture(TileEntityChestRenderer.textureTrapped);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (this.isChristams) {
                    this.bindTexture(TileEntityChestRenderer.textureChristmas);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    this.bindTexture(TileEntityChestRenderer.textureNormal);
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
            }
            else {
                modelChest = this.largeChest;
                if (n5 >= 0) {
                    this.bindTexture(TileEntityChestRenderer.DESTROY_STAGES[n5]);
                    GlStateManager.matrixMode(1111 + 2326 + 1320 + 1133);
                    GlStateManager.pushMatrix();
                    GlStateManager.scale(8.0f, 4.0f, 1.0f);
                    GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
                    GlStateManager.matrixMode(4726 + 3434 - 6255 + 3983);
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
                else if (tileEntityChest.getChestType() == " ".length()) {
                    this.bindTexture(TileEntityChestRenderer.textureTrappedDouble);
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else if (this.isChristams) {
                    this.bindTexture(TileEntityChestRenderer.textureChristmasDouble);
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
                else {
                    this.bindTexture(TileEntityChestRenderer.textureNormalDouble);
                }
            }
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            if (n5 < 0) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GlStateManager.translate((float)n, (float)n2 + 1.0f, (float)n3 + 1.0f);
            GlStateManager.scale(1.0f, -1.0f, -1.0f);
            GlStateManager.translate(0.5f, 0.5f, 0.5f);
            int n7 = "".length();
            if (n6 == "  ".length()) {
                n7 = 21 + 51 + 33 + 75;
            }
            if (n6 == "   ".length()) {
                n7 = "".length();
            }
            if (n6 == (0x23 ^ 0x27)) {
                n7 = (0x59 ^ 0x3);
            }
            if (n6 == (0x63 ^ 0x66)) {
                n7 = -(0x47 ^ 0x1D);
            }
            if (n6 == "  ".length() && tileEntityChest.adjacentChestXPos != null) {
                GlStateManager.translate(1.0f, 0.0f, 0.0f);
            }
            if (n6 == (0xC2 ^ 0xC7) && tileEntityChest.adjacentChestZPos != null) {
                GlStateManager.translate(0.0f, 0.0f, -1.0f);
            }
            GlStateManager.rotate(n7, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            float n8 = tileEntityChest.prevLidAngle + (tileEntityChest.lidAngle - tileEntityChest.prevLidAngle) * n4;
            if (tileEntityChest.adjacentChestZNeg != null) {
                final float n9 = tileEntityChest.adjacentChestZNeg.prevLidAngle + (tileEntityChest.adjacentChestZNeg.lidAngle - tileEntityChest.adjacentChestZNeg.prevLidAngle) * n4;
                if (n9 > n8) {
                    n8 = n9;
                }
            }
            if (tileEntityChest.adjacentChestXNeg != null) {
                final float n10 = tileEntityChest.adjacentChestXNeg.prevLidAngle + (tileEntityChest.adjacentChestXNeg.lidAngle - tileEntityChest.adjacentChestXNeg.prevLidAngle) * n4;
                if (n10 > n8) {
                    n8 = n10;
                }
            }
            final float n11 = 1.0f - n8;
            modelChest.chestLid.rotateAngleX = -((1.0f - n11 * n11 * n11) * 3.1415927f / 2.0f);
            modelChest.renderAll();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (n5 >= 0) {
                GlStateManager.matrixMode(4624 + 3487 - 2292 + 71);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(2981 + 482 - 2931 + 5356);
            }
        }
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityChest)tileEntity, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String[0x29 ^ 0x2F])["".length()] = I("\u0013\u000e4\u0016\u0010\u0015\u000e?M\u0000\t\u001f%\u0016\u001cH\b$\u0007\u0016\u0013D8\u0010\u0004\u0017\u001b)\u0006:\u0003\u00049\u0000\t\u0002E<\f\u0002", "gkLbe");
        TileEntityChestRenderer.I[" ".length()] = I("\u0007,\u001b<!\u0001,\u0010g1\u001d=\n<-\\*\u000b-'\u0007f\u0000 &\u001a:\u0017%5\u0000\u0016\u0007'!\u0011%\u0006f$\u001d.", "sIcHT");
        TileEntityChestRenderer.I["  ".length()] = I("\u0003-\u001a,\u0019\u0005-\u0011w\t\u0019<\u000b,\u0015X+\n=\u001f\u0003g\f7\u001e\u001a)\u000e\u0007\b\u0018=\u00004\tY8\f?", "wHbXl");
        TileEntityChestRenderer.I["   ".length()] = I("\f5;\u0007?\n50\\/\u0016$*\u00073W3+\u00169\f\u007f7\u0001+\b &\u0017d\b>$", "xPCsJ");
        TileEntityChestRenderer.I[0x68 ^ 0x6C] = I("9\u000b4\u00131?\u000b?H!#\u001a%\u0013=b\r$\u000279A/\u000f6$\u001d8\n%>@<\t#", "MnLgD");
        TileEntityChestRenderer.I[0xAC ^ 0xA9] = I("7\r\u0012><1\r\u0019e,-\u001c\u0003>0l\u000b\u0002/:7G\u0004%;.\t\u0006d9-\u000f", "ChjJI");
    }
    
    static {
        I();
        textureTrappedDouble = new ResourceLocation(TileEntityChestRenderer.I["".length()]);
        textureChristmasDouble = new ResourceLocation(TileEntityChestRenderer.I[" ".length()]);
        textureNormalDouble = new ResourceLocation(TileEntityChestRenderer.I["  ".length()]);
        textureTrapped = new ResourceLocation(TileEntityChestRenderer.I["   ".length()]);
        textureChristmas = new ResourceLocation(TileEntityChestRenderer.I[0xC1 ^ 0xC5]);
        textureNormal = new ResourceLocation(TileEntityChestRenderer.I[0x99 ^ 0x9C]);
    }
}
