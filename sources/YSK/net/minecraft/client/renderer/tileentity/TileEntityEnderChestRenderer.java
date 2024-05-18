package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer<TileEntityEnderChest>
{
    private static final String[] I;
    private ModelChest field_147521_c;
    private static final ResourceLocation ENDER_CHEST_TEXTURE;
    
    @Override
    public void renderTileEntityAt(final TileEntityEnderChest tileEntityEnderChest, final double n, final double n2, final double n3, final float n4, final int n5) {
        int n6 = "".length();
        if (tileEntityEnderChest.hasWorldObj()) {
            n6 = tileEntityEnderChest.getBlockMetadata();
        }
        if (n5 >= 0) {
            this.bindTexture(TileEntityEnderChestRenderer.DESTROY_STAGES[n5]);
            GlStateManager.matrixMode(3262 + 1431 - 501 + 1698);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0f, 4.0f, 1.0f);
            GlStateManager.translate(0.0625f, 0.0625f, 0.0625f);
            GlStateManager.matrixMode(2805 + 5602 - 8268 + 5749);
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            this.bindTexture(TileEntityEnderChestRenderer.ENDER_CHEST_TEXTURE);
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.translate((float)n, (float)n2 + 1.0f, (float)n3 + 1.0f);
        GlStateManager.scale(1.0f, -1.0f, -1.0f);
        GlStateManager.translate(0.5f, 0.5f, 0.5f);
        int n7 = "".length();
        if (n6 == "  ".length()) {
            n7 = 68 + 71 + 19 + 22;
        }
        if (n6 == "   ".length()) {
            n7 = "".length();
        }
        if (n6 == (0xE ^ 0xA)) {
            n7 = (0x42 ^ 0x18);
        }
        if (n6 == (0xC0 ^ 0xC5)) {
            n7 = -(0x47 ^ 0x1D);
        }
        GlStateManager.rotate(n7, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(-0.5f, -0.5f, -0.5f);
        final float n8 = 1.0f - (tileEntityEnderChest.prevLidAngle + (tileEntityEnderChest.lidAngle - tileEntityEnderChest.prevLidAngle) * n4);
        this.field_147521_c.chestLid.rotateAngleX = -((1.0f - n8 * n8 * n8) * 3.1415927f / 2.0f);
        this.field_147521_c.renderAll();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        if (n5 >= 0) {
            GlStateManager.matrixMode(1592 + 1570 + 1755 + 973);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(3905 + 1195 - 4613 + 5401);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityEnderChest)tileEntity, n, n2, n3, n4, n5);
    }
    
    public TileEntityEnderChestRenderer() {
        this.field_147521_c = new ModelChest();
    }
    
    static {
        I();
        ENDER_CHEST_TEXTURE = new ResourceLocation(TileEntityEnderChestRenderer.I["".length()]);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001c\u0006\u00161\u001c\u001a\u0006\u001dj\f\u0006\u0017\u00071\u0010G\u0000\u0006 \u001a\u001cL\u000b+\r\r\u0011@5\u0007\u000f", "hcnEi");
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
}
