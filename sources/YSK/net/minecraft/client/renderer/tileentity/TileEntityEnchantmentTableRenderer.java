package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentTable>
{
    private ModelBook field_147541_c;
    private static final String[] I;
    private static final ResourceLocation TEXTURE_BOOK;
    
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        TEXTURE_BOOK = new ResourceLocation(TileEntityEnchantmentTableRenderer.I["".length()]);
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityEnchantmentTable)tileEntity, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0013#\u001f\u001c\u0002\u0015#\u0014G\u0012\t2\u000e\u001c\u000eH#\t\u000b\u001f\u0006(\u0013\u0001\u0019\u0000\u0019\u0013\t\u0015\u000b#8\n\u0018\b-I\u0018\u0019\u0000", "gFghw");
    }
    
    public TileEntityEnchantmentTableRenderer() {
        this.field_147541_c = new ModelBook();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntityEnchantmentTable tileEntityEnchantmentTable, final double n, final double n2, final double n3, final float n4, final int n5) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n + 0.5f, (float)n2 + 0.75f, (float)n3 + 0.5f);
        final float n6 = tileEntityEnchantmentTable.tickCount + n4;
        GlStateManager.translate(0.0f, 0.1f + MathHelper.sin(n6 * 0.1f) * 0.01f, 0.0f);
        float n7 = tileEntityEnchantmentTable.bookRotation - tileEntityEnchantmentTable.bookRotationPrev;
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (n7 >= 3.1415927f) {
            n7 -= 6.2831855f;
        }
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (n7 < -3.1415927f) {
            n7 += 6.2831855f;
        }
        GlStateManager.rotate(-(tileEntityEnchantmentTable.bookRotationPrev + n7 * n4) * 180.0f / 3.1415927f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(80.0f, 0.0f, 0.0f, 1.0f);
        this.bindTexture(TileEntityEnchantmentTableRenderer.TEXTURE_BOOK);
        final float n8 = tileEntityEnchantmentTable.pageFlipPrev + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * n4 + 0.25f;
        final float n9 = tileEntityEnchantmentTable.pageFlipPrev + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * n4 + 0.75f;
        float n10 = (n8 - MathHelper.truncateDoubleToInt(n8)) * 1.6f - 0.3f;
        float n11 = (n9 - MathHelper.truncateDoubleToInt(n9)) * 1.6f - 0.3f;
        if (n10 < 0.0f) {
            n10 = 0.0f;
        }
        if (n11 < 0.0f) {
            n11 = 0.0f;
        }
        if (n10 > 1.0f) {
            n10 = 1.0f;
        }
        if (n11 > 1.0f) {
            n11 = 1.0f;
        }
        final float n12 = tileEntityEnchantmentTable.bookSpreadPrev + (tileEntityEnchantmentTable.bookSpread - tileEntityEnchantmentTable.bookSpreadPrev) * n4;
        GlStateManager.enableCull();
        this.field_147541_c.render(null, n6, n10, n11, n12, 0.0f, 0.0625f);
        GlStateManager.popMatrix();
    }
}
