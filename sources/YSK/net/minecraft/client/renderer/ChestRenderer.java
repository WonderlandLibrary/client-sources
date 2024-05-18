package net.minecraft.client.renderer;

import net.minecraft.block.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.item.*;

public class ChestRenderer
{
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void renderChestBrightness(final Block block, final float n) {
        GlStateManager.color(n, n, n, 1.0f);
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        TileEntityItemStackRenderer.instance.renderByItem(new ItemStack(block));
    }
}
