package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import optfine.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.properties.*;

public class BlockFluidRenderer
{
    private static final String[] I;
    private static final String __OBFID;
    private TextureAtlasSprite[] atlasSpritesLava;
    private TextureAtlasSprite[] atlasSpritesWater;
    
    public boolean renderFluid(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer) {
        final BlockLiquid blockLiquid = (BlockLiquid)blockState.getBlock();
        blockLiquid.setBlockBoundsBasedOnState(blockAccess, blockPos);
        TextureAtlasSprite[] array;
        if (blockLiquid.getMaterial() == Material.lava) {
            array = this.atlasSpritesLava;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            array = this.atlasSpritesWater;
        }
        final TextureAtlasSprite[] array2 = array;
        final int fluidColor = CustomColorizer.getFluidColor(blockLiquid, blockAccess, blockPos);
        final float n = (fluidColor >> (0xC ^ 0x1C) & 138 + 133 - 63 + 47) / 255.0f;
        final float n2 = (fluidColor >> (0x5B ^ 0x53) & 38 + 152 - 58 + 123) / 255.0f;
        final float n3 = (fluidColor & 153 + 210 - 327 + 219) / 255.0f;
        final boolean shouldSideBeRendered = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.up(), EnumFacing.UP);
        final boolean shouldSideBeRendered2 = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.down(), EnumFacing.DOWN);
        final boolean[] borderFlags = RenderEnv.getInstance(blockAccess, blockState, blockPos).getBorderFlags();
        borderFlags["".length()] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.north(), EnumFacing.NORTH);
        borderFlags[" ".length()] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.south(), EnumFacing.SOUTH);
        borderFlags["  ".length()] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.west(), EnumFacing.WEST);
        borderFlags["   ".length()] = blockLiquid.shouldSideBeRendered(blockAccess, blockPos.east(), EnumFacing.EAST);
        if (!shouldSideBeRendered && !shouldSideBeRendered2 && !borderFlags["".length()] && !borderFlags[" ".length()] && !borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            return "".length() != 0;
        }
        int n4 = "".length();
        final float n5 = 0.5f;
        final float n6 = 1.0f;
        final float n7 = 0.8f;
        final float n8 = 0.6f;
        final Material material = blockLiquid.getMaterial();
        float fluidHeight = this.getFluidHeight(blockAccess, blockPos, material);
        float fluidHeight2 = this.getFluidHeight(blockAccess, blockPos.south(), material);
        float fluidHeight3 = this.getFluidHeight(blockAccess, blockPos.east().south(), material);
        float fluidHeight4 = this.getFluidHeight(blockAccess, blockPos.east(), material);
        final double n9 = blockPos.getX();
        final double n10 = blockPos.getY();
        final double n11 = blockPos.getZ();
        final float n12 = 0.001f;
        if (shouldSideBeRendered) {
            n4 = " ".length();
            TextureAtlasSprite sprite = array2["".length()];
            final float n13 = (float)BlockLiquid.getFlowDirection(blockAccess, blockPos, material);
            if (n13 > -999.0f) {
                sprite = array2[" ".length()];
            }
            worldRenderer.setSprite(sprite);
            fluidHeight -= n12;
            fluidHeight2 -= n12;
            fluidHeight3 -= n12;
            fluidHeight4 -= n12;
            float n14;
            float n15;
            float interpolatedU;
            float n16;
            float n17;
            float interpolatedV;
            float interpolatedU2;
            float interpolatedV2;
            if (n13 < -999.0f) {
                n14 = sprite.getInterpolatedU(0.0);
                n15 = sprite.getInterpolatedV(0.0);
                interpolatedU = n14;
                n16 = sprite.getInterpolatedV(16.0);
                n17 = sprite.getInterpolatedU(16.0);
                interpolatedV = n16;
                interpolatedU2 = n17;
                interpolatedV2 = n15;
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            else {
                final float n18 = MathHelper.sin(n13) * 0.25f;
                final float n19 = MathHelper.cos(n13) * 0.25f;
                n14 = sprite.getInterpolatedU(8.0f + (-n19 - n18) * 16.0f);
                n15 = sprite.getInterpolatedV(8.0f + (-n19 + n18) * 16.0f);
                interpolatedU = sprite.getInterpolatedU(8.0f + (-n19 + n18) * 16.0f);
                n16 = sprite.getInterpolatedV(8.0f + (n19 + n18) * 16.0f);
                n17 = sprite.getInterpolatedU(8.0f + (n19 + n18) * 16.0f);
                interpolatedV = sprite.getInterpolatedV(8.0f + (n19 - n18) * 16.0f);
                interpolatedU2 = sprite.getInterpolatedU(8.0f + (n19 - n18) * 16.0f);
                interpolatedV2 = sprite.getInterpolatedV(8.0f + (-n19 - n18) * 16.0f);
            }
            final int mixedBrightnessForBlock = blockLiquid.getMixedBrightnessForBlock(blockAccess, blockPos);
            final int n20 = mixedBrightnessForBlock >> (0xB1 ^ 0xA1) & 45365 + 1154 - 36534 + 55550;
            final int n21 = mixedBrightnessForBlock & 55480 + 53854 - 98841 + 55042;
            final float n22 = n6 * n;
            final float n23 = n6 * n2;
            final float n24 = n6 * n3;
            worldRenderer.pos(n9 + 0.0, n10 + fluidHeight, n11 + 0.0).color(n22, n23, n24, 1.0f).tex(n14, n15).lightmap(n20, n21).endVertex();
            worldRenderer.pos(n9 + 0.0, n10 + fluidHeight2, n11 + 1.0).color(n22, n23, n24, 1.0f).tex(interpolatedU, n16).lightmap(n20, n21).endVertex();
            worldRenderer.pos(n9 + 1.0, n10 + fluidHeight3, n11 + 1.0).color(n22, n23, n24, 1.0f).tex(n17, interpolatedV).lightmap(n20, n21).endVertex();
            worldRenderer.pos(n9 + 1.0, n10 + fluidHeight4, n11 + 0.0).color(n22, n23, n24, 1.0f).tex(interpolatedU2, interpolatedV2).lightmap(n20, n21).endVertex();
            if (blockLiquid.func_176364_g(blockAccess, blockPos.up())) {
                worldRenderer.pos(n9 + 0.0, n10 + fluidHeight, n11 + 0.0).color(n22, n23, n24, 1.0f).tex(n14, n15).lightmap(n20, n21).endVertex();
                worldRenderer.pos(n9 + 1.0, n10 + fluidHeight4, n11 + 0.0).color(n22, n23, n24, 1.0f).tex(interpolatedU2, interpolatedV2).lightmap(n20, n21).endVertex();
                worldRenderer.pos(n9 + 1.0, n10 + fluidHeight3, n11 + 1.0).color(n22, n23, n24, 1.0f).tex(n17, interpolatedV).lightmap(n20, n21).endVertex();
                worldRenderer.pos(n9 + 0.0, n10 + fluidHeight2, n11 + 1.0).color(n22, n23, n24, 1.0f).tex(interpolatedU, n16).lightmap(n20, n21).endVertex();
            }
        }
        if (shouldSideBeRendered2) {
            final float minU = array2["".length()].getMinU();
            final float maxU = array2["".length()].getMaxU();
            final float minV = array2["".length()].getMinV();
            final float maxV = array2["".length()].getMaxV();
            final int mixedBrightnessForBlock2 = blockLiquid.getMixedBrightnessForBlock(blockAccess, blockPos.down());
            final int n25 = mixedBrightnessForBlock2 >> (0xE ^ 0x1E) & 50898 + 6778 - 36091 + 43950;
            final int n26 = mixedBrightnessForBlock2 & 54892 + 26500 - 42971 + 27114;
            worldRenderer.pos(n9, n10, n11 + 1.0).color(n5, n5, n5, 1.0f).tex(minU, maxV).lightmap(n25, n26).endVertex();
            worldRenderer.pos(n9, n10, n11).color(n5, n5, n5, 1.0f).tex(minU, minV).lightmap(n25, n26).endVertex();
            worldRenderer.pos(n9 + 1.0, n10, n11).color(n5, n5, n5, 1.0f).tex(maxU, minV).lightmap(n25, n26).endVertex();
            worldRenderer.pos(n9 + 1.0, n10, n11 + 1.0).color(n5, n5, n5, 1.0f).tex(maxU, maxV).lightmap(n25, n26).endVertex();
            n4 = " ".length();
        }
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < (0x5D ^ 0x59)) {
            int length = "".length();
            int length2 = "".length();
            if (i == 0) {
                --length2;
            }
            if (i == " ".length()) {
                ++length2;
            }
            if (i == "  ".length()) {
                --length;
            }
            if (i == "   ".length()) {
                ++length;
            }
            final BlockPos add = blockPos.add(length, "".length(), length2);
            final TextureAtlasSprite sprite2 = array2[" ".length()];
            worldRenderer.setSprite(sprite2);
            if (borderFlags[i]) {
                float n27;
                float n28;
                double n29;
                double n30;
                double n31;
                double n32;
                if (i == 0) {
                    n27 = fluidHeight;
                    n28 = fluidHeight4;
                    n29 = n9;
                    n30 = n9 + 1.0;
                    n31 = n11 + n12;
                    n32 = n11 + n12;
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else if (i == " ".length()) {
                    n27 = fluidHeight3;
                    n28 = fluidHeight2;
                    n29 = n9 + 1.0;
                    n30 = n9;
                    n31 = n11 + 1.0 - n12;
                    n32 = n11 + 1.0 - n12;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (i == "  ".length()) {
                    n27 = fluidHeight2;
                    n28 = fluidHeight;
                    n29 = n9 + n12;
                    n30 = n9 + n12;
                    n31 = n11 + 1.0;
                    n32 = n11;
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else {
                    n27 = fluidHeight4;
                    n28 = fluidHeight3;
                    n29 = n9 + 1.0 - n12;
                    n30 = n9 + 1.0 - n12;
                    n31 = n11;
                    n32 = n11 + 1.0;
                }
                n4 = " ".length();
                final float interpolatedU3 = sprite2.getInterpolatedU(0.0);
                final float interpolatedU4 = sprite2.getInterpolatedU(8.0);
                final float interpolatedV3 = sprite2.getInterpolatedV((1.0f - n27) * 16.0f * 0.5f);
                final float interpolatedV4 = sprite2.getInterpolatedV((1.0f - n28) * 16.0f * 0.5f);
                final float interpolatedV5 = sprite2.getInterpolatedV(8.0);
                final int mixedBrightnessForBlock3 = blockLiquid.getMixedBrightnessForBlock(blockAccess, add);
                final int n33 = mixedBrightnessForBlock3 >> (0x25 ^ 0x35) & 35071 + 8427 + 20046 + 1991;
                final int n34 = mixedBrightnessForBlock3 & 6420 + 30612 + 23523 + 4980;
                float n35;
                if (i < "  ".length()) {
                    n35 = n7;
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else {
                    n35 = n8;
                }
                final float n36 = n35;
                final float n37 = n6 * n36 * n;
                final float n38 = n6 * n36 * n2;
                final float n39 = n6 * n36 * n3;
                worldRenderer.pos(n29, n10 + n27, n31).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV3).lightmap(n33, n34).endVertex();
                worldRenderer.pos(n30, n10 + n28, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV4).lightmap(n33, n34).endVertex();
                worldRenderer.pos(n30, n10 + 0.0, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV5).lightmap(n33, n34).endVertex();
                worldRenderer.pos(n29, n10 + 0.0, n31).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV5).lightmap(n33, n34).endVertex();
                worldRenderer.pos(n29, n10 + 0.0, n31).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV5).lightmap(n33, n34).endVertex();
                worldRenderer.pos(n30, n10 + 0.0, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV5).lightmap(n33, n34).endVertex();
                worldRenderer.pos(n30, n10 + n28, n32).color(n37, n38, n39, 1.0f).tex(interpolatedU4, interpolatedV4).lightmap(n33, n34).endVertex();
                worldRenderer.pos(n29, n10 + n27, n31).color(n37, n38, n39, 1.0f).tex(interpolatedU3, interpolatedV3).lightmap(n33, n34).endVertex();
            }
            ++i;
        }
        worldRenderer.setSprite(null);
        return n4 != 0;
    }
    
    protected void initAtlasSprites() {
        final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        this.atlasSpritesLava["".length()] = textureMapBlocks.getAtlasSprite(BlockFluidRenderer.I["".length()]);
        this.atlasSpritesLava[" ".length()] = textureMapBlocks.getAtlasSprite(BlockFluidRenderer.I[" ".length()]);
        this.atlasSpritesWater["".length()] = textureMapBlocks.getAtlasSprite(BlockFluidRenderer.I["  ".length()]);
        this.atlasSpritesWater[" ".length()] = textureMapBlocks.getAtlasSprite(BlockFluidRenderer.I["   ".length()]);
    }
    
    private float getFluidHeight(final IBlockAccess blockAccess, final BlockPos blockPos, final Material material) {
        int length = "".length();
        float n = 0.0f;
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < (0x1B ^ 0x1F)) {
            final BlockPos add = blockPos.add(-(i & " ".length()), "".length(), -(i >> " ".length() & " ".length()));
            if (blockAccess.getBlockState(add.up()).getBlock().getMaterial() == material) {
                return 1.0f;
            }
            final IBlockState blockState = blockAccess.getBlockState(add);
            final Material material2 = blockState.getBlock().getMaterial();
            if (material2 != material) {
                if (!material2.isSolid()) {
                    ++n;
                    ++length;
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
            }
            else {
                final int intValue = blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL);
                if (intValue >= (0x42 ^ 0x4A) || intValue == 0) {
                    n += BlockLiquid.getLiquidHeightPercent(intValue) * 10.0f;
                    length += 10;
                }
                n += BlockLiquid.getLiquidHeightPercent(intValue);
                ++length;
            }
            ++i;
        }
        return 1.0f - n / length;
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
            if (2 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BlockFluidRenderer() {
        this.atlasSpritesLava = new TextureAtlasSprite["  ".length()];
        this.atlasSpritesWater = new TextureAtlasSprite["  ".length()];
        this.initAtlasSprites();
    }
    
    static {
        I();
        __OBFID = BlockFluidRenderer.I[0x4 ^ 0x0];
    }
    
    private static void I() {
        (I = new String[0x7E ^ 0x7B])["".length()] = I("*\u000086\u001a5\b0'C%\u000590\u00124F:2\u000f&6%'\u0010+\u0005", "GiVSy");
        BlockFluidRenderer.I[" ".length()] = I("\u0018\u0002\u001e \u0005\u0007\n\u00161\\\u0017\u0007\u001f&\r\u0006D\u001c$\u0010\u00144\u0016)\t\u0002", "ukpEf");
        BlockFluidRenderer.I["  ".length()] = I("9\u000f\u00171\u0013&\u0007\u001f J6\n\u00167\u001b'I\u000e5\u00041\u0014&'\u0004=\n\u0015", "TfyTp");
        BlockFluidRenderer.I["   ".length()] = I("\u0007.97\r\u0018&1&T\b+81\u0005\u0019h 3\u001a\u000f5\b4\u0002\u00050", "jGWRn");
        BlockFluidRenderer.I[0xB ^ 0xF] = I("%\r\u0017]]VqzX\\_", "fAHmm");
    }
}
