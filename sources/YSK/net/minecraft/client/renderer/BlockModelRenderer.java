package net.minecraft.client.renderer;

import net.minecraft.world.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.state.*;
import java.util.*;
import optfine.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;

public class BlockModelRenderer
{
    private static final String[] I;
    private static float aoLightValueOpaque;
    private static final String __OBFID;
    
    public boolean renderModelAmbientOcclusion(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        return this.renderModelAmbientOcclusion(blockAccess, bakedModel, block, blockAccess.getBlockState(blockPos), blockPos, worldRenderer, b);
    }
    
    private static void I() {
        (I = new String[0x0 ^ 0x4])["".length()] = I("\t\u001e\u0015VRzbxSSr", "JRJfb");
        BlockModelRenderer.I[" ".length()] = I("\u0006\u000f\u00192!>\u000b\u001e(*5J\b-+1\u0001J,+6\u000f\u0006", "RjjAD");
        BlockModelRenderer.I["  ".length()] = I("\n+>\u0005'h*>\u0002)$g3\u0003%& q\u0012);44\n-<\"5", "HGQfL");
        BlockModelRenderer.I["   ".length()] = I("\u001e5\n$?k\u0007,", "KFcJX");
    }
    
    private void renderModelStandardQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing enumFacing, int n, final boolean b, final WorldRenderer worldRenderer, final List list, final RenderEnv renderEnv) {
        final BitSet boundsFlags = renderEnv.getBoundsFlags();
        final IBlockState blockState = renderEnv.getBlockState();
        double n2 = blockPos.getX();
        double n3 = blockPos.getY();
        double n4 = blockPos.getZ();
        final Block.EnumOffsetType offsetType = block.getOffsetType();
        if (offsetType != Block.EnumOffsetType.NONE) {
            final long n5 = blockPos.getX() * (1385926 + 1468170 - 953575 + 1229350) ^ blockPos.getZ() * 116129781L;
            final long n6 = n5 * n5 * 42317861L + n5 * 11L;
            n2 += ((n6 >> (0x1C ^ 0xC) & 0xFL) / 15.0f - 0.5) * 0.5;
            n4 += ((n6 >> (0x78 ^ 0x60) & 0xFL) / 15.0f - 0.5) * 0.5;
            if (offsetType == Block.EnumOffsetType.XYZ) {
                n3 += ((n6 >> (0xBF ^ 0xAB) & 0xFL) / 15.0f - 1.0) * 0.2;
            }
        }
        final Iterator<BakedQuad> iterator = list.iterator();
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            BakedQuad bakedQuad = iterator.next();
            if (bakedQuad.getSprite() != null) {
                final BakedQuad bakedQuad2 = bakedQuad;
                if (Config.isConnectedTextures()) {
                    bakedQuad = ConnectedTextures.getConnectedTexture(blockAccess, blockState, blockPos, bakedQuad, renderEnv);
                }
                if (bakedQuad == bakedQuad2 && Config.isNaturalTextures()) {
                    bakedQuad = NaturalTextures.getNaturalTexture(blockPos, bakedQuad);
                }
            }
            if (b) {
                this.fillQuadBounds(block, bakedQuad.getVertexData(), bakedQuad.getFace(), null, boundsFlags);
                int n7;
                if (boundsFlags.get("".length())) {
                    n7 = block.getMixedBrightnessForBlock(blockAccess, blockPos.offset(bakedQuad.getFace()));
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else {
                    n7 = block.getMixedBrightnessForBlock(blockAccess, blockPos);
                }
                n = n7;
            }
            if (worldRenderer.isMultiTexture()) {
                worldRenderer.addVertexData(bakedQuad.getVertexDataSingle());
                worldRenderer.putSprite(bakedQuad.getSprite());
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                worldRenderer.addVertexData(bakedQuad.getVertexData());
            }
            worldRenderer.putBrightness4(n, n, n, n);
            final int colorMultiplier = CustomColorizer.getColorMultiplier(bakedQuad, block, blockAccess, blockPos, renderEnv);
            if (bakedQuad.hasTintIndex() || colorMultiplier >= 0) {
                int n8;
                if (colorMultiplier >= 0) {
                    n8 = colorMultiplier;
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
                else {
                    n8 = block.colorMultiplier(blockAccess, blockPos, bakedQuad.getTintIndex());
                }
                if (EntityRenderer.anaglyphEnable) {
                    n8 = TextureUtil.anaglyphColor(n8);
                }
                final float n9 = (n8 >> (0x41 ^ 0x51) & 46 + 219 - 137 + 127) / 255.0f;
                final float n10 = (n8 >> (0x47 ^ 0x4F) & 80 + 157 - 84 + 102) / 255.0f;
                final float n11 = (n8 & 92 + 14 - 69 + 218) / 255.0f;
                worldRenderer.putColorMultiplier(n9, n10, n11, 0xA3 ^ 0xA7);
                worldRenderer.putColorMultiplier(n9, n10, n11, "   ".length());
                worldRenderer.putColorMultiplier(n9, n10, n11, "  ".length());
                worldRenderer.putColorMultiplier(n9, n10, n11, " ".length());
            }
            worldRenderer.putPosition(n2, n3, n4);
        }
    }
    
    public boolean renderModelAmbientOcclusion(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        int n = "".length();
        RenderEnv renderEnv = null;
        final EnumFacing[] values;
        final int length = (values = EnumFacing.VALUES).length;
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing = values[i];
            List<BakedQuad> list = bakedModel.getFaceQuads(enumFacing);
            if (!list.isEmpty()) {
                final BlockPos offset = blockPos.offset(enumFacing);
                if (!b || block.shouldSideBeRendered(blockAccess, offset, enumFacing)) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
                    }
                    if (!renderEnv.isBreakingAnimation(list) && Config.isBetterGrass()) {
                        list = (List<BakedQuad>)BetterGrass.getFaceQuads(blockAccess, block, blockPos, enumFacing, list);
                    }
                    this.renderModelAmbientOcclusionQuads(blockAccess, block, blockPos, worldRenderer, list, renderEnv);
                    n = " ".length();
                }
            }
            ++i;
        }
        final List<BakedQuad> generalQuads = bakedModel.getGeneralQuads();
        if (generalQuads.size() > 0) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
            }
            this.renderModelAmbientOcclusionQuads(blockAccess, block, blockPos, worldRenderer, generalQuads, renderEnv);
            n = " ".length();
        }
        if (renderEnv != null && Config.isBetterSnow() && !renderEnv.isBreakingAnimation() && BetterSnow.shouldRender(blockAccess, block, blockState, blockPos)) {
            final IBakedModel modelSnowLayer = BetterSnow.getModelSnowLayer();
            final IBlockState stateSnowLayer = BetterSnow.getStateSnowLayer();
            this.renderModelAmbientOcclusion(blockAccess, modelSnowLayer, stateSnowLayer.getBlock(), stateSnowLayer, blockPos, worldRenderer, " ".length() != 0);
        }
        return n != 0;
    }
    
    public static void updateAoLightValue() {
        BlockModelRenderer.aoLightValueOpaque = 1.0f - Config.getAmbientOcclusionLevel() * 0.8f;
    }
    
    private void fillQuadBounds(final Block block, final int[] array, final EnumFacing enumFacing, final float[] array2, final BitSet set) {
        float min = 32.0f;
        float min2 = 32.0f;
        float min3 = 32.0f;
        float max = -32.0f;
        float max2 = -32.0f;
        float max3 = -32.0f;
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < (0x9D ^ 0x99)) {
            final float intBitsToFloat = Float.intBitsToFloat(array[i * (0x7F ^ 0x78)]);
            final float intBitsToFloat2 = Float.intBitsToFloat(array[i * (0x82 ^ 0x85) + " ".length()]);
            final float intBitsToFloat3 = Float.intBitsToFloat(array[i * (0xF ^ 0x8) + "  ".length()]);
            min = Math.min(min, intBitsToFloat);
            min2 = Math.min(min2, intBitsToFloat2);
            min3 = Math.min(min3, intBitsToFloat3);
            max = Math.max(max, intBitsToFloat);
            max2 = Math.max(max2, intBitsToFloat2);
            max3 = Math.max(max3, intBitsToFloat3);
            ++i;
        }
        if (array2 != null) {
            array2[EnumFacing.WEST.getIndex()] = min;
            array2[EnumFacing.EAST.getIndex()] = max;
            array2[EnumFacing.DOWN.getIndex()] = min2;
            array2[EnumFacing.UP.getIndex()] = max2;
            array2[EnumFacing.NORTH.getIndex()] = min3;
            array2[EnumFacing.SOUTH.getIndex()] = max3;
            array2[EnumFacing.WEST.getIndex() + EnumFacing.VALUES.length] = 1.0f - min;
            array2[EnumFacing.EAST.getIndex() + EnumFacing.VALUES.length] = 1.0f - max;
            array2[EnumFacing.DOWN.getIndex() + EnumFacing.VALUES.length] = 1.0f - min2;
            array2[EnumFacing.UP.getIndex() + EnumFacing.VALUES.length] = 1.0f - max2;
            array2[EnumFacing.NORTH.getIndex() + EnumFacing.VALUES.length] = 1.0f - min3;
            array2[EnumFacing.SOUTH.getIndex() + EnumFacing.VALUES.length] = 1.0f - max3;
        }
        switch (BlockModelRenderer$1.field_178290_a[enumFacing.ordinal()]) {
            case 1: {
                final int length = " ".length();
                int n;
                if (min < 1.0E-4f && min3 < 1.0E-4f && max > 0.9999f && max3 > 0.9999f) {
                    n = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    n = " ".length();
                }
                set.set(length, n != 0);
                final int length2 = "".length();
                int n2;
                if ((min2 < 1.0E-4f || block.isFullCube()) && min2 == max2) {
                    n2 = " ".length();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                set.set(length2, n2 != 0);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 2: {
                final int length3 = " ".length();
                int n3;
                if (min < 1.0E-4f && min3 < 1.0E-4f && max > 0.9999f && max3 > 0.9999f) {
                    n3 = "".length();
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                }
                else {
                    n3 = " ".length();
                }
                set.set(length3, n3 != 0);
                final int length4 = "".length();
                int n4;
                if ((max2 > 0.9999f || block.isFullCube()) && min2 == max2) {
                    n4 = " ".length();
                    "".length();
                    if (2 == -1) {
                        throw null;
                    }
                }
                else {
                    n4 = "".length();
                }
                set.set(length4, n4 != 0);
                "".length();
                if (1 < -1) {
                    throw null;
                }
                break;
            }
            case 3: {
                final int length5 = " ".length();
                int n5;
                if (min < 1.0E-4f && min2 < 1.0E-4f && max > 0.9999f && max2 > 0.9999f) {
                    n5 = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n5 = " ".length();
                }
                set.set(length5, n5 != 0);
                final int length6 = "".length();
                int n6;
                if ((min3 < 1.0E-4f || block.isFullCube()) && min3 == max3) {
                    n6 = " ".length();
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                }
                else {
                    n6 = "".length();
                }
                set.set(length6, n6 != 0);
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            case 4: {
                final int length7 = " ".length();
                int n7;
                if (min < 1.0E-4f && min2 < 1.0E-4f && max > 0.9999f && max2 > 0.9999f) {
                    n7 = "".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else {
                    n7 = " ".length();
                }
                set.set(length7, n7 != 0);
                final int length8 = "".length();
                int n8;
                if ((max3 > 0.9999f || block.isFullCube()) && min3 == max3) {
                    n8 = " ".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n8 = "".length();
                }
                set.set(length8, n8 != 0);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 5: {
                final int length9 = " ".length();
                int n9;
                if (min2 < 1.0E-4f && min3 < 1.0E-4f && max2 > 0.9999f && max3 > 0.9999f) {
                    n9 = "".length();
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                else {
                    n9 = " ".length();
                }
                set.set(length9, n9 != 0);
                final int length10 = "".length();
                int n10;
                if ((min < 1.0E-4f || block.isFullCube()) && min == max) {
                    n10 = " ".length();
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else {
                    n10 = "".length();
                }
                set.set(length10, n10 != 0);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                break;
            }
            case 6: {
                final int length11 = " ".length();
                int n11;
                if (min2 < 1.0E-4f && min3 < 1.0E-4f && max2 > 0.9999f && max3 > 0.9999f) {
                    n11 = "".length();
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                }
                else {
                    n11 = " ".length();
                }
                set.set(length11, n11 != 0);
                final int length12 = "".length();
                int n12;
                if ((max > 0.9999f || block.isFullCube()) && min == max) {
                    n12 = " ".length();
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
                else {
                    n12 = "".length();
                }
                set.set(length12, n12 != 0);
                break;
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean renderModelStandard(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        return this.renderModelStandard(blockAccess, bakedModel, block, blockAccess.getBlockState(blockPos), blockPos, worldRenderer, b);
    }
    
    public boolean renderModelStandard(final IBlockAccess blockAccess, final IBakedModel bakedModel, final Block block, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        int n = "".length();
        RenderEnv renderEnv = null;
        final EnumFacing[] values;
        final int length = (values = EnumFacing.VALUES).length;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing = values[i];
            List<BakedQuad> list = bakedModel.getFaceQuads(enumFacing);
            if (!list.isEmpty()) {
                final BlockPos offset = blockPos.offset(enumFacing);
                if (!b || block.shouldSideBeRendered(blockAccess, offset, enumFacing)) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
                    }
                    if (!renderEnv.isBreakingAnimation(list) && Config.isBetterGrass()) {
                        list = (List<BakedQuad>)BetterGrass.getFaceQuads(blockAccess, block, blockPos, enumFacing, list);
                    }
                    this.renderModelStandardQuads(blockAccess, block, blockPos, enumFacing, block.getMixedBrightnessForBlock(blockAccess, offset), "".length() != 0, worldRenderer, list, renderEnv);
                    n = " ".length();
                }
            }
            ++i;
        }
        final List<BakedQuad> generalQuads = bakedModel.getGeneralQuads();
        if (generalQuads.size() > 0) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.getInstance(blockAccess, blockState, blockPos);
            }
            this.renderModelStandardQuads(blockAccess, block, blockPos, null, -" ".length(), " ".length() != 0, worldRenderer, generalQuads, renderEnv);
            n = " ".length();
        }
        if (renderEnv != null && Config.isBetterSnow() && !renderEnv.isBreakingAnimation() && BetterSnow.shouldRender(blockAccess, block, blockState, blockPos) && BetterSnow.shouldRender(blockAccess, block, blockState, blockPos)) {
            final IBakedModel modelSnowLayer = BetterSnow.getModelSnowLayer();
            final IBlockState stateSnowLayer = BetterSnow.getStateSnowLayer();
            this.renderModelStandard(blockAccess, modelSnowLayer, stateSnowLayer.getBlock(), stateSnowLayer, blockPos, worldRenderer, " ".length() != 0);
        }
        return n != 0;
    }
    
    public boolean renderModel(final IBlockAccess blockAccess, final IBakedModel bakedModel, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer) {
        blockState.getBlock().setBlockBoundsBasedOnState(blockAccess, blockPos);
        return this.renderModel(blockAccess, bakedModel, blockState, blockPos, worldRenderer, " ".length() != 0);
    }
    
    public void renderModelBrightnessColor(final IBakedModel bakedModel, final float n, final float n2, final float n3, final float n4) {
        final EnumFacing[] values;
        final int length = (values = EnumFacing.VALUES).length;
        int i = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (i < length) {
            this.renderModelBrightnessColorQuads(n, n2, n3, n4, bakedModel.getFaceQuads(values[i]));
            ++i;
        }
        this.renderModelBrightnessColorQuads(n, n2, n3, n4, bakedModel.getGeneralQuads());
    }
    
    static {
        I();
        __OBFID = BlockModelRenderer.I["".length()];
        BlockModelRenderer.aoLightValueOpaque = 0.2f;
    }
    
    private void renderModelAmbientOcclusionQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final WorldRenderer worldRenderer, final List list, final RenderEnv renderEnv) {
        final float[] quadBounds = renderEnv.getQuadBounds();
        final BitSet boundsFlags = renderEnv.getBoundsFlags();
        final AmbientOcclusionFace aoFace = renderEnv.getAoFace();
        final IBlockState blockState = renderEnv.getBlockState();
        double n = blockPos.getX();
        double n2 = blockPos.getY();
        double n3 = blockPos.getZ();
        final Block.EnumOffsetType offsetType = block.getOffsetType();
        if (offsetType != Block.EnumOffsetType.NONE) {
            final long positionRandom = MathHelper.getPositionRandom(blockPos);
            n += ((positionRandom >> (0xD3 ^ 0xC3) & 0xFL) / 15.0f - 0.5) * 0.5;
            n3 += ((positionRandom >> (0x52 ^ 0x4A) & 0xFL) / 15.0f - 0.5) * 0.5;
            if (offsetType == Block.EnumOffsetType.XYZ) {
                n2 += ((positionRandom >> (0x6E ^ 0x7A) & 0xFL) / 15.0f - 1.0) * 0.2;
            }
        }
        final Iterator<BakedQuad> iterator = list.iterator();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            BakedQuad bakedQuad = iterator.next();
            if (bakedQuad.getSprite() != null) {
                final BakedQuad bakedQuad2 = bakedQuad;
                if (Config.isConnectedTextures()) {
                    bakedQuad = ConnectedTextures.getConnectedTexture(blockAccess, blockState, blockPos, bakedQuad, renderEnv);
                }
                if (bakedQuad == bakedQuad2 && Config.isNaturalTextures()) {
                    bakedQuad = NaturalTextures.getNaturalTexture(blockPos, bakedQuad);
                }
            }
            this.fillQuadBounds(block, bakedQuad.getVertexData(), bakedQuad.getFace(), quadBounds, boundsFlags);
            aoFace.updateVertexBrightness(blockAccess, block, blockPos, bakedQuad.getFace(), quadBounds, boundsFlags);
            if (worldRenderer.isMultiTexture()) {
                worldRenderer.addVertexData(bakedQuad.getVertexDataSingle());
                worldRenderer.putSprite(bakedQuad.getSprite());
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                worldRenderer.addVertexData(bakedQuad.getVertexData());
            }
            worldRenderer.putBrightness4(AmbientOcclusionFace.access$0(aoFace)["".length()], AmbientOcclusionFace.access$0(aoFace)[" ".length()], AmbientOcclusionFace.access$0(aoFace)["  ".length()], AmbientOcclusionFace.access$0(aoFace)["   ".length()]);
            final int colorMultiplier = CustomColorizer.getColorMultiplier(bakedQuad, block, blockAccess, blockPos, renderEnv);
            if (!bakedQuad.hasTintIndex() && colorMultiplier < 0) {
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)["".length()], AmbientOcclusionFace.access$1(aoFace)["".length()], AmbientOcclusionFace.access$1(aoFace)["".length()], 0x4D ^ 0x49);
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)[" ".length()], AmbientOcclusionFace.access$1(aoFace)[" ".length()], AmbientOcclusionFace.access$1(aoFace)[" ".length()], "   ".length());
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)["  ".length()], AmbientOcclusionFace.access$1(aoFace)["  ".length()], AmbientOcclusionFace.access$1(aoFace)["  ".length()], "  ".length());
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)["   ".length()], AmbientOcclusionFace.access$1(aoFace)["   ".length()], AmbientOcclusionFace.access$1(aoFace)["   ".length()], " ".length());
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                int n4;
                if (colorMultiplier >= 0) {
                    n4 = colorMultiplier;
                    "".length();
                    if (1 == -1) {
                        throw null;
                    }
                }
                else {
                    n4 = block.colorMultiplier(blockAccess, blockPos, bakedQuad.getTintIndex());
                }
                if (EntityRenderer.anaglyphEnable) {
                    n4 = TextureUtil.anaglyphColor(n4);
                }
                final float n5 = (n4 >> (0x9 ^ 0x19) & 13 + 142 - 2 + 102) / 255.0f;
                final float n6 = (n4 >> (0x60 ^ 0x68) & 237 + 182 - 206 + 42) / 255.0f;
                final float n7 = (n4 & 224 + 208 - 332 + 155) / 255.0f;
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)["".length()] * n5, AmbientOcclusionFace.access$1(aoFace)["".length()] * n6, AmbientOcclusionFace.access$1(aoFace)["".length()] * n7, 0x51 ^ 0x55);
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)[" ".length()] * n5, AmbientOcclusionFace.access$1(aoFace)[" ".length()] * n6, AmbientOcclusionFace.access$1(aoFace)[" ".length()] * n7, "   ".length());
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)["  ".length()] * n5, AmbientOcclusionFace.access$1(aoFace)["  ".length()] * n6, AmbientOcclusionFace.access$1(aoFace)["  ".length()] * n7, "  ".length());
                worldRenderer.putColorMultiplier(AmbientOcclusionFace.access$1(aoFace)["   ".length()] * n5, AmbientOcclusionFace.access$1(aoFace)["   ".length()] * n6, AmbientOcclusionFace.access$1(aoFace)["   ".length()] * n7, " ".length());
            }
            worldRenderer.putPosition(n, n2, n3);
        }
    }
    
    public void renderModelBrightness(final IBakedModel bakedModel, final IBlockState blockState, final float n, final boolean b) {
        final Block block = blockState.getBlock();
        block.setBlockBoundsForItemRender();
        GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
        int n2 = block.getRenderColor(block.getStateForEntityRender(blockState));
        if (EntityRenderer.anaglyphEnable) {
            n2 = TextureUtil.anaglyphColor(n2);
        }
        final float n3 = (n2 >> (0x14 ^ 0x4) & 88 + 52 - 36 + 151) / 255.0f;
        final float n4 = (n2 >> (0xB ^ 0x3) & 164 + 248 - 180 + 23) / 255.0f;
        final float n5 = (n2 & 218 + 94 - 70 + 13) / 255.0f;
        if (!b) {
            GlStateManager.color(n, n, n, 1.0f);
        }
        this.renderModelBrightnessColor(bakedModel, n, n3, n4, n5);
    }
    
    private void renderModelBrightnessColorQuads(final float n, final float n2, final float n3, final float n4, final List list) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final Iterator<BakedQuad> iterator = list.iterator();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BakedQuad bakedQuad = iterator.next();
            worldRenderer.begin(0x61 ^ 0x66, DefaultVertexFormats.ITEM);
            worldRenderer.addVertexData(bakedQuad.getVertexData());
            if (bakedQuad.hasTintIndex()) {
                worldRenderer.putColorRGB_F4(n2 * n, n3 * n, n4 * n);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                worldRenderer.putColorRGB_F4(n, n, n);
            }
            final Vec3i directionVec = bakedQuad.getFace().getDirectionVec();
            worldRenderer.putNormal(directionVec.getX(), directionVec.getY(), directionVec.getZ());
            instance.draw();
        }
    }
    
    public boolean renderModel(final IBlockAccess blockAccess, final IBakedModel bakedModel, final IBlockState blockState, final BlockPos blockPos, final WorldRenderer worldRenderer, final boolean b) {
        int n;
        if (Minecraft.isAmbientOcclusionEnabled() && blockState.getBlock().getLightValue() == 0 && bakedModel.isAmbientOcclusion()) {
            n = " ".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        try {
            final Block block = blockState.getBlock();
            boolean b2;
            if (n2 != 0) {
                b2 = this.renderModelAmbientOcclusion(blockAccess, bakedModel, block, blockState, blockPos, worldRenderer, b);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                b2 = this.renderModelStandard(blockAccess, bakedModel, block, blockState, blockPos, worldRenderer, b);
            }
            return b2;
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, BlockModelRenderer.I[" ".length()]);
            final CrashReportCategory category = crashReport.makeCategory(BlockModelRenderer.I["  ".length()]);
            CrashReportCategory.addBlockInfo(category, blockPos, blockState);
            category.addCrashSection(BlockModelRenderer.I["   ".length()], (boolean)(n2 != 0));
            throw new ReportedException(crashReport);
        }
    }
    
    public static float fixAoLightValue(final float n) {
        float aoLightValueOpaque;
        if (n == 0.2f) {
            aoLightValueOpaque = BlockModelRenderer.aoLightValueOpaque;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            aoLightValueOpaque = n;
        }
        return aoLightValueOpaque;
    }
    
    public static class AmbientOcclusionFace
    {
        private static final String __OBFID;
        private static final String[] I;
        private final int[] vertexBrightness;
        private final float[] vertexColorMultiplier;
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0001\u000f\u001dfyrspcxw", "BCBVI");
        }
        
        static float[] access$1(final AmbientOcclusionFace ambientOcclusionFace) {
            return ambientOcclusionFace.vertexColorMultiplier;
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
                if (-1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static int[] access$0(final AmbientOcclusionFace ambientOcclusionFace) {
            return ambientOcclusionFace.vertexBrightness;
        }
        
        private int getVertexBrightness(final int n, final int n2, final int n3, final int n4, final float n5, final float n6, final float n7, final float n8) {
            return ((int)((n >> (0x3B ^ 0x2B) & 208 + 169 - 287 + 165) * n5 + (n2 >> (0x76 ^ 0x66) & 87 + 232 - 163 + 99) * n6 + (n3 >> (0x27 ^ 0x37) & 135 + 115 - 151 + 156) * n7 + (n4 >> (0x14 ^ 0x4) & 141 + 185 - 199 + 128) * n8) & 91 + 29 - 93 + 228) << (0xA5 ^ 0xB5) | ((int)((n & 227 + 241 - 241 + 28) * n5 + (n2 & 31 + 51 - 40 + 213) * n6 + (n3 & 186 + 145 - 101 + 25) * n7 + (n4 & 213 + 45 - 228 + 225) * n8) & 91 + 53 - 108 + 219);
        }
        
        private int getAoBrightness(int n, int n2, int n3, final int n4) {
            if (n == 0) {
                n = n4;
            }
            if (n2 == 0) {
                n2 = n4;
            }
            if (n3 == 0) {
                n3 = n4;
            }
            return n + n2 + n3 + n4 >> "  ".length() & 16484641 + 1867777 - 14241618 + 12601135;
        }
        
        public void updateVertexBrightness(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing enumFacing, final float[] array, final BitSet set) {
            BlockPos offset;
            if (set.get("".length())) {
                offset = blockPos.offset(enumFacing);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                offset = blockPos;
            }
            final BlockPos blockPos2 = offset;
            final EnumNeighborInfo neighbourInfo = EnumNeighborInfo.getNeighbourInfo(enumFacing);
            final BlockPos offset2 = blockPos2.offset(neighbourInfo.field_178276_g["".length()]);
            final BlockPos offset3 = blockPos2.offset(neighbourInfo.field_178276_g[" ".length()]);
            final BlockPos offset4 = blockPos2.offset(neighbourInfo.field_178276_g["  ".length()]);
            final BlockPos offset5 = blockPos2.offset(neighbourInfo.field_178276_g["   ".length()]);
            final int mixedBrightnessForBlock = block.getMixedBrightnessForBlock(blockAccess, offset2);
            final int mixedBrightnessForBlock2 = block.getMixedBrightnessForBlock(blockAccess, offset3);
            final int mixedBrightnessForBlock3 = block.getMixedBrightnessForBlock(blockAccess, offset4);
            final int mixedBrightnessForBlock4 = block.getMixedBrightnessForBlock(blockAccess, offset5);
            final float fixAoLightValue = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset2).getBlock().getAmbientOcclusionLightValue());
            final float fixAoLightValue2 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset3).getBlock().getAmbientOcclusionLightValue());
            final float fixAoLightValue3 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset4).getBlock().getAmbientOcclusionLightValue());
            final float fixAoLightValue4 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset5).getBlock().getAmbientOcclusionLightValue());
            final boolean translucent = blockAccess.getBlockState(offset2.offset(enumFacing)).getBlock().isTranslucent();
            final boolean translucent2 = blockAccess.getBlockState(offset3.offset(enumFacing)).getBlock().isTranslucent();
            final boolean translucent3 = blockAccess.getBlockState(offset4.offset(enumFacing)).getBlock().isTranslucent();
            final boolean translucent4 = blockAccess.getBlockState(offset5.offset(enumFacing)).getBlock().isTranslucent();
            float fixAoLightValue5;
            int mixedBrightnessForBlock5;
            if (!translucent3 && !translucent) {
                fixAoLightValue5 = fixAoLightValue;
                mixedBrightnessForBlock5 = mixedBrightnessForBlock;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                final BlockPos offset6 = offset2.offset(neighbourInfo.field_178276_g["  ".length()]);
                fixAoLightValue5 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset6).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock5 = block.getMixedBrightnessForBlock(blockAccess, offset6);
            }
            float fixAoLightValue6;
            int mixedBrightnessForBlock6;
            if (!translucent4 && !translucent) {
                fixAoLightValue6 = fixAoLightValue;
                mixedBrightnessForBlock6 = mixedBrightnessForBlock;
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                final BlockPos offset7 = offset2.offset(neighbourInfo.field_178276_g["   ".length()]);
                fixAoLightValue6 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset7).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock6 = block.getMixedBrightnessForBlock(blockAccess, offset7);
            }
            float fixAoLightValue7;
            int mixedBrightnessForBlock7;
            if (!translucent3 && !translucent2) {
                fixAoLightValue7 = fixAoLightValue2;
                mixedBrightnessForBlock7 = mixedBrightnessForBlock2;
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                final BlockPos offset8 = offset3.offset(neighbourInfo.field_178276_g["  ".length()]);
                fixAoLightValue7 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset8).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock7 = block.getMixedBrightnessForBlock(blockAccess, offset8);
            }
            float fixAoLightValue8;
            int mixedBrightnessForBlock8;
            if (!translucent4 && !translucent2) {
                fixAoLightValue8 = fixAoLightValue2;
                mixedBrightnessForBlock8 = mixedBrightnessForBlock2;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                final BlockPos offset9 = offset3.offset(neighbourInfo.field_178276_g["   ".length()]);
                fixAoLightValue8 = BlockModelRenderer.fixAoLightValue(blockAccess.getBlockState(offset9).getBlock().getAmbientOcclusionLightValue());
                mixedBrightnessForBlock8 = block.getMixedBrightnessForBlock(blockAccess, offset9);
            }
            int n = block.getMixedBrightnessForBlock(blockAccess, blockPos);
            if (set.get("".length()) || !blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock().isOpaqueCube()) {
                n = block.getMixedBrightnessForBlock(blockAccess, blockPos.offset(enumFacing));
            }
            float n2;
            if (set.get("".length())) {
                n2 = blockAccess.getBlockState(blockPos2).getBlock().getAmbientOcclusionLightValue();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n2 = blockAccess.getBlockState(blockPos).getBlock().getAmbientOcclusionLightValue();
            }
            final float fixAoLightValue9 = BlockModelRenderer.fixAoLightValue(n2);
            final VertexTranslations vertexTranslations = VertexTranslations.getVertexTranslations(enumFacing);
            if (set.get(" ".length()) && neighbourInfo.field_178289_i) {
                final float n3 = (fixAoLightValue4 + fixAoLightValue + fixAoLightValue6 + fixAoLightValue9) * 0.25f;
                final float n4 = (fixAoLightValue3 + fixAoLightValue + fixAoLightValue5 + fixAoLightValue9) * 0.25f;
                final float n5 = (fixAoLightValue3 + fixAoLightValue2 + fixAoLightValue7 + fixAoLightValue9) * 0.25f;
                final float n6 = (fixAoLightValue4 + fixAoLightValue2 + fixAoLightValue8 + fixAoLightValue9) * 0.25f;
                final float n7 = array[neighbourInfo.field_178286_j["".length()].field_178229_m] * array[neighbourInfo.field_178286_j[" ".length()].field_178229_m];
                final float n8 = array[neighbourInfo.field_178286_j["  ".length()].field_178229_m] * array[neighbourInfo.field_178286_j["   ".length()].field_178229_m];
                final float n9 = array[neighbourInfo.field_178286_j[0xBC ^ 0xB8].field_178229_m] * array[neighbourInfo.field_178286_j[0x58 ^ 0x5D].field_178229_m];
                final float n10 = array[neighbourInfo.field_178286_j[0xA7 ^ 0xA1].field_178229_m] * array[neighbourInfo.field_178286_j[0x27 ^ 0x20].field_178229_m];
                final float n11 = array[neighbourInfo.field_178287_k["".length()].field_178229_m] * array[neighbourInfo.field_178287_k[" ".length()].field_178229_m];
                final float n12 = array[neighbourInfo.field_178287_k["  ".length()].field_178229_m] * array[neighbourInfo.field_178287_k["   ".length()].field_178229_m];
                final float n13 = array[neighbourInfo.field_178287_k[0x3C ^ 0x38].field_178229_m] * array[neighbourInfo.field_178287_k[0x38 ^ 0x3D].field_178229_m];
                final float n14 = array[neighbourInfo.field_178287_k[0xB0 ^ 0xB6].field_178229_m] * array[neighbourInfo.field_178287_k[0x6F ^ 0x68].field_178229_m];
                final float n15 = array[neighbourInfo.field_178284_l["".length()].field_178229_m] * array[neighbourInfo.field_178284_l[" ".length()].field_178229_m];
                final float n16 = array[neighbourInfo.field_178284_l["  ".length()].field_178229_m] * array[neighbourInfo.field_178284_l["   ".length()].field_178229_m];
                final float n17 = array[neighbourInfo.field_178284_l[0x41 ^ 0x45].field_178229_m] * array[neighbourInfo.field_178284_l[0x6D ^ 0x68].field_178229_m];
                final float n18 = array[neighbourInfo.field_178284_l[0x44 ^ 0x42].field_178229_m] * array[neighbourInfo.field_178284_l[0x12 ^ 0x15].field_178229_m];
                final float n19 = array[neighbourInfo.field_178285_m["".length()].field_178229_m] * array[neighbourInfo.field_178285_m[" ".length()].field_178229_m];
                final float n20 = array[neighbourInfo.field_178285_m["  ".length()].field_178229_m] * array[neighbourInfo.field_178285_m["   ".length()].field_178229_m];
                final float n21 = array[neighbourInfo.field_178285_m[0x17 ^ 0x13].field_178229_m] * array[neighbourInfo.field_178285_m[0x17 ^ 0x12].field_178229_m];
                final float n22 = array[neighbourInfo.field_178285_m[0x89 ^ 0x8F].field_178229_m] * array[neighbourInfo.field_178285_m[0xB ^ 0xC].field_178229_m];
                this.vertexColorMultiplier[VertexTranslations.access$2(vertexTranslations)] = n3 * n7 + n4 * n8 + n5 * n9 + n6 * n10;
                this.vertexColorMultiplier[VertexTranslations.access$3(vertexTranslations)] = n3 * n11 + n4 * n12 + n5 * n13 + n6 * n14;
                this.vertexColorMultiplier[VertexTranslations.access$4(vertexTranslations)] = n3 * n15 + n4 * n16 + n5 * n17 + n6 * n18;
                this.vertexColorMultiplier[VertexTranslations.access$5(vertexTranslations)] = n3 * n19 + n4 * n20 + n5 * n21 + n6 * n22;
                final int aoBrightness = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock, mixedBrightnessForBlock6, n);
                final int aoBrightness2 = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock, mixedBrightnessForBlock5, n);
                final int aoBrightness3 = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock2, mixedBrightnessForBlock7, n);
                final int aoBrightness4 = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock2, mixedBrightnessForBlock8, n);
                this.vertexBrightness[VertexTranslations.access$2(vertexTranslations)] = this.getVertexBrightness(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n7, n8, n9, n10);
                this.vertexBrightness[VertexTranslations.access$3(vertexTranslations)] = this.getVertexBrightness(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n11, n12, n13, n14);
                this.vertexBrightness[VertexTranslations.access$4(vertexTranslations)] = this.getVertexBrightness(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n15, n16, n17, n18);
                this.vertexBrightness[VertexTranslations.access$5(vertexTranslations)] = this.getVertexBrightness(aoBrightness, aoBrightness2, aoBrightness3, aoBrightness4, n19, n20, n21, n22);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                final float n23 = (fixAoLightValue4 + fixAoLightValue + fixAoLightValue6 + fixAoLightValue9) * 0.25f;
                final float n24 = (fixAoLightValue3 + fixAoLightValue + fixAoLightValue5 + fixAoLightValue9) * 0.25f;
                final float n25 = (fixAoLightValue3 + fixAoLightValue2 + fixAoLightValue7 + fixAoLightValue9) * 0.25f;
                final float n26 = (fixAoLightValue4 + fixAoLightValue2 + fixAoLightValue8 + fixAoLightValue9) * 0.25f;
                this.vertexBrightness[VertexTranslations.access$2(vertexTranslations)] = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock, mixedBrightnessForBlock6, n);
                this.vertexBrightness[VertexTranslations.access$3(vertexTranslations)] = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock, mixedBrightnessForBlock5, n);
                this.vertexBrightness[VertexTranslations.access$4(vertexTranslations)] = this.getAoBrightness(mixedBrightnessForBlock3, mixedBrightnessForBlock2, mixedBrightnessForBlock7, n);
                this.vertexBrightness[VertexTranslations.access$5(vertexTranslations)] = this.getAoBrightness(mixedBrightnessForBlock4, mixedBrightnessForBlock2, mixedBrightnessForBlock8, n);
                this.vertexColorMultiplier[VertexTranslations.access$2(vertexTranslations)] = n23;
                this.vertexColorMultiplier[VertexTranslations.access$3(vertexTranslations)] = n24;
                this.vertexColorMultiplier[VertexTranslations.access$4(vertexTranslations)] = n25;
                this.vertexColorMultiplier[VertexTranslations.access$5(vertexTranslations)] = n26;
            }
        }
        
        public AmbientOcclusionFace() {
            this.vertexColorMultiplier = new float[0xAC ^ 0xA8];
            this.vertexBrightness = new int[0x8 ^ 0xC];
        }
        
        public AmbientOcclusionFace(final BlockModelRenderer blockModelRenderer) {
            this.vertexColorMultiplier = new float[0x98 ^ 0x9C];
            this.vertexBrightness = new int[0x12 ^ 0x16];
        }
        
        static {
            I();
            __OBFID = AmbientOcclusionFace.I["".length()];
        }
    }
    
    public enum EnumNeighborInfo
    {
        private static final EnumNeighborInfo[] field_178282_n;
        protected final Orientation[] field_178284_l;
        private static final EnumNeighborInfo[] ENUM$VALUES;
        
        UP(s3, length3, s4, length4, array2, 1.0f, (boolean)("".length() != 0), new Orientation["".length()], new Orientation["".length()], new Orientation["".length()], new Orientation["".length()]), 
        WEST(s9, n3, s10, n4, array13, n5, (boolean)(length11 != 0), array14, array15, array16, array17);
        
        protected final Orientation[] field_178287_k;
        protected final boolean field_178289_i;
        private static final String[] I;
        protected final float field_178288_h;
        
        DOWN(s, length, s2, length2, array, 0.5f, (boolean)("".length() != 0), new Orientation["".length()], new Orientation["".length()], new Orientation["".length()], new Orientation["".length()]);
        
        protected final EnumFacing[] field_178276_g;
        
        EAST(s11, n6, s12, n7, array18, n8, (boolean)(length12 != 0), array19, array20, array21, array22);
        
        private static final EnumNeighborInfo[] $VALUES;
        
        NORTH(s5, length5, s6, length6, array3, n, (boolean)(length7 != 0), array4, array5, array6, array7);
        
        private static final String __OBFID;
        protected final Orientation[] field_178285_m;
        
        SOUTH(s7, length8, s8, length9, array8, n2, (boolean)(length10 != 0), array9, array10, array11, array12);
        
        protected final Orientation[] field_178286_j;
        
        public static EnumNeighborInfo getNeighbourInfo(final EnumFacing enumFacing) {
            return EnumNeighborInfo.field_178282_n[enumFacing.getIndex()];
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
        
        private EnumNeighborInfo(final String s, final int n, final String s2, final int n2, final EnumFacing[] field_178276_g, final float field_178288_h, final boolean field_178289_i, final Orientation[] field_178286_j, final Orientation[] field_178287_k, final Orientation[] field_178284_l, final Orientation[] field_178285_m) {
            this.field_178276_g = field_178276_g;
            this.field_178288_h = field_178288_h;
            this.field_178289_i = field_178289_i;
            this.field_178286_j = field_178286_j;
            this.field_178287_k = field_178287_k;
            this.field_178284_l = field_178284_l;
            this.field_178285_m = field_178285_m;
        }
        
        static {
            I();
            __OBFID = EnumNeighborInfo.I["".length()];
            final String s = EnumNeighborInfo.I[" ".length()];
            final int length = "".length();
            final String s2 = EnumNeighborInfo.I["  ".length()];
            final int length2 = "".length();
            final EnumFacing[] array = new EnumFacing[0x87 ^ 0x83];
            array["".length()] = EnumFacing.WEST;
            array[" ".length()] = EnumFacing.EAST;
            array["  ".length()] = EnumFacing.NORTH;
            array["   ".length()] = EnumFacing.SOUTH;
            final String s3 = EnumNeighborInfo.I["   ".length()];
            final int length3 = " ".length();
            final String s4 = EnumNeighborInfo.I[0x76 ^ 0x72];
            final int length4 = " ".length();
            final EnumFacing[] array2 = new EnumFacing[0x27 ^ 0x23];
            array2["".length()] = EnumFacing.EAST;
            array2[" ".length()] = EnumFacing.WEST;
            array2["  ".length()] = EnumFacing.NORTH;
            array2["   ".length()] = EnumFacing.SOUTH;
            final String s5 = EnumNeighborInfo.I[0x1 ^ 0x4];
            final int length5 = "  ".length();
            final String s6 = EnumNeighborInfo.I[0x7C ^ 0x7A];
            final int length6 = "  ".length();
            final EnumFacing[] array3 = new EnumFacing[0xBC ^ 0xB8];
            array3["".length()] = EnumFacing.UP;
            array3[" ".length()] = EnumFacing.DOWN;
            array3["  ".length()] = EnumFacing.EAST;
            array3["   ".length()] = EnumFacing.WEST;
            final float n = 0.8f;
            final int length7 = " ".length();
            final Orientation[] array4 = new Orientation[0x15 ^ 0x1D];
            array4["".length()] = Orientation.UP;
            array4[" ".length()] = Orientation.FLIP_WEST;
            array4["  ".length()] = Orientation.UP;
            array4["   ".length()] = Orientation.WEST;
            array4[0x3B ^ 0x3F] = Orientation.FLIP_UP;
            array4[0x98 ^ 0x9D] = Orientation.WEST;
            array4[0x2 ^ 0x4] = Orientation.FLIP_UP;
            array4[0xD ^ 0xA] = Orientation.FLIP_WEST;
            final Orientation[] array5 = new Orientation[0x22 ^ 0x2A];
            array5["".length()] = Orientation.UP;
            array5[" ".length()] = Orientation.FLIP_EAST;
            array5["  ".length()] = Orientation.UP;
            array5["   ".length()] = Orientation.EAST;
            array5[0xF ^ 0xB] = Orientation.FLIP_UP;
            array5[0x9D ^ 0x98] = Orientation.EAST;
            array5[0xBF ^ 0xB9] = Orientation.FLIP_UP;
            array5[0x43 ^ 0x44] = Orientation.FLIP_EAST;
            final Orientation[] array6 = new Orientation[0x0 ^ 0x8];
            array6["".length()] = Orientation.DOWN;
            array6[" ".length()] = Orientation.FLIP_EAST;
            array6["  ".length()] = Orientation.DOWN;
            array6["   ".length()] = Orientation.EAST;
            array6[0xB3 ^ 0xB7] = Orientation.FLIP_DOWN;
            array6[0xA8 ^ 0xAD] = Orientation.EAST;
            array6[0x5B ^ 0x5D] = Orientation.FLIP_DOWN;
            array6[0x7 ^ 0x0] = Orientation.FLIP_EAST;
            final Orientation[] array7 = new Orientation[0x86 ^ 0x8E];
            array7["".length()] = Orientation.DOWN;
            array7[" ".length()] = Orientation.FLIP_WEST;
            array7["  ".length()] = Orientation.DOWN;
            array7["   ".length()] = Orientation.WEST;
            array7[0x32 ^ 0x36] = Orientation.FLIP_DOWN;
            array7[0x5E ^ 0x5B] = Orientation.WEST;
            array7[0x55 ^ 0x53] = Orientation.FLIP_DOWN;
            array7[0x3A ^ 0x3D] = Orientation.FLIP_WEST;
            final String s7 = EnumNeighborInfo.I[0x45 ^ 0x42];
            final int length8 = "   ".length();
            final String s8 = EnumNeighborInfo.I[0x2 ^ 0xA];
            final int length9 = "   ".length();
            final EnumFacing[] array8 = new EnumFacing[0x6F ^ 0x6B];
            array8["".length()] = EnumFacing.WEST;
            array8[" ".length()] = EnumFacing.EAST;
            array8["  ".length()] = EnumFacing.DOWN;
            array8["   ".length()] = EnumFacing.UP;
            final float n2 = 0.8f;
            final int length10 = " ".length();
            final Orientation[] array9 = new Orientation[0x61 ^ 0x69];
            array9["".length()] = Orientation.UP;
            array9[" ".length()] = Orientation.FLIP_WEST;
            array9["  ".length()] = Orientation.FLIP_UP;
            array9["   ".length()] = Orientation.FLIP_WEST;
            array9[0x85 ^ 0x81] = Orientation.FLIP_UP;
            array9[0x66 ^ 0x63] = Orientation.WEST;
            array9[0x74 ^ 0x72] = Orientation.UP;
            array9[0x96 ^ 0x91] = Orientation.WEST;
            final Orientation[] array10 = new Orientation[0x56 ^ 0x5E];
            array10["".length()] = Orientation.DOWN;
            array10[" ".length()] = Orientation.FLIP_WEST;
            array10["  ".length()] = Orientation.FLIP_DOWN;
            array10["   ".length()] = Orientation.FLIP_WEST;
            array10[0x80 ^ 0x84] = Orientation.FLIP_DOWN;
            array10[0xC4 ^ 0xC1] = Orientation.WEST;
            array10[0x8D ^ 0x8B] = Orientation.DOWN;
            array10[0x26 ^ 0x21] = Orientation.WEST;
            final Orientation[] array11 = new Orientation[0x98 ^ 0x90];
            array11["".length()] = Orientation.DOWN;
            array11[" ".length()] = Orientation.FLIP_EAST;
            array11["  ".length()] = Orientation.FLIP_DOWN;
            array11["   ".length()] = Orientation.FLIP_EAST;
            array11[0x7 ^ 0x3] = Orientation.FLIP_DOWN;
            array11[0x1E ^ 0x1B] = Orientation.EAST;
            array11[0xC7 ^ 0xC1] = Orientation.DOWN;
            array11[0x3D ^ 0x3A] = Orientation.EAST;
            final Orientation[] array12 = new Orientation[0x19 ^ 0x11];
            array12["".length()] = Orientation.UP;
            array12[" ".length()] = Orientation.FLIP_EAST;
            array12["  ".length()] = Orientation.FLIP_UP;
            array12["   ".length()] = Orientation.FLIP_EAST;
            array12[0x2B ^ 0x2F] = Orientation.FLIP_UP;
            array12[0x3A ^ 0x3F] = Orientation.EAST;
            array12[0xAD ^ 0xAB] = Orientation.UP;
            array12[0x69 ^ 0x6E] = Orientation.EAST;
            final String s9 = EnumNeighborInfo.I[0x53 ^ 0x5A];
            final int n3 = 0x59 ^ 0x5D;
            final String s10 = EnumNeighborInfo.I[0x44 ^ 0x4E];
            final int n4 = 0xAE ^ 0xAA;
            final EnumFacing[] array13 = new EnumFacing[0x67 ^ 0x63];
            array13["".length()] = EnumFacing.UP;
            array13[" ".length()] = EnumFacing.DOWN;
            array13["  ".length()] = EnumFacing.NORTH;
            array13["   ".length()] = EnumFacing.SOUTH;
            final float n5 = 0.6f;
            final int length11 = " ".length();
            final Orientation[] array14 = new Orientation[0x6E ^ 0x66];
            array14["".length()] = Orientation.UP;
            array14[" ".length()] = Orientation.SOUTH;
            array14["  ".length()] = Orientation.UP;
            array14["   ".length()] = Orientation.FLIP_SOUTH;
            array14[0x79 ^ 0x7D] = Orientation.FLIP_UP;
            array14[0x71 ^ 0x74] = Orientation.FLIP_SOUTH;
            array14[0x5F ^ 0x59] = Orientation.FLIP_UP;
            array14[0x78 ^ 0x7F] = Orientation.SOUTH;
            final Orientation[] array15 = new Orientation[0xCA ^ 0xC2];
            array15["".length()] = Orientation.UP;
            array15[" ".length()] = Orientation.NORTH;
            array15["  ".length()] = Orientation.UP;
            array15["   ".length()] = Orientation.FLIP_NORTH;
            array15[0x45 ^ 0x41] = Orientation.FLIP_UP;
            array15[0x80 ^ 0x85] = Orientation.FLIP_NORTH;
            array15[0xD ^ 0xB] = Orientation.FLIP_UP;
            array15[0x5C ^ 0x5B] = Orientation.NORTH;
            final Orientation[] array16 = new Orientation[0x39 ^ 0x31];
            array16["".length()] = Orientation.DOWN;
            array16[" ".length()] = Orientation.NORTH;
            array16["  ".length()] = Orientation.DOWN;
            array16["   ".length()] = Orientation.FLIP_NORTH;
            array16[0x60 ^ 0x64] = Orientation.FLIP_DOWN;
            array16[0x49 ^ 0x4C] = Orientation.FLIP_NORTH;
            array16[0x34 ^ 0x32] = Orientation.FLIP_DOWN;
            array16[0x97 ^ 0x90] = Orientation.NORTH;
            final Orientation[] array17 = new Orientation[0x20 ^ 0x28];
            array17["".length()] = Orientation.DOWN;
            array17[" ".length()] = Orientation.SOUTH;
            array17["  ".length()] = Orientation.DOWN;
            array17["   ".length()] = Orientation.FLIP_SOUTH;
            array17[0xA1 ^ 0xA5] = Orientation.FLIP_DOWN;
            array17[0x55 ^ 0x50] = Orientation.FLIP_SOUTH;
            array17[0x14 ^ 0x12] = Orientation.FLIP_DOWN;
            array17[0x3D ^ 0x3A] = Orientation.SOUTH;
            final String s11 = EnumNeighborInfo.I[0xAE ^ 0xA5];
            final int n6 = 0xA3 ^ 0xA6;
            final String s12 = EnumNeighborInfo.I[0x5C ^ 0x50];
            final int n7 = 0x43 ^ 0x46;
            final EnumFacing[] array18 = new EnumFacing[0x29 ^ 0x2D];
            array18["".length()] = EnumFacing.DOWN;
            array18[" ".length()] = EnumFacing.UP;
            array18["  ".length()] = EnumFacing.NORTH;
            array18["   ".length()] = EnumFacing.SOUTH;
            final float n8 = 0.6f;
            final int length12 = " ".length();
            final Orientation[] array19 = new Orientation[0xA8 ^ 0xA0];
            array19["".length()] = Orientation.FLIP_DOWN;
            array19[" ".length()] = Orientation.SOUTH;
            array19["  ".length()] = Orientation.FLIP_DOWN;
            array19["   ".length()] = Orientation.FLIP_SOUTH;
            array19[0x93 ^ 0x97] = Orientation.DOWN;
            array19[0xAF ^ 0xAA] = Orientation.FLIP_SOUTH;
            array19[0x6D ^ 0x6B] = Orientation.DOWN;
            array19[0xB1 ^ 0xB6] = Orientation.SOUTH;
            final Orientation[] array20 = new Orientation[0x6F ^ 0x67];
            array20["".length()] = Orientation.FLIP_DOWN;
            array20[" ".length()] = Orientation.NORTH;
            array20["  ".length()] = Orientation.FLIP_DOWN;
            array20["   ".length()] = Orientation.FLIP_NORTH;
            array20[0x0 ^ 0x4] = Orientation.DOWN;
            array20[0x5A ^ 0x5F] = Orientation.FLIP_NORTH;
            array20[0x3E ^ 0x38] = Orientation.DOWN;
            array20[0x7E ^ 0x79] = Orientation.NORTH;
            final Orientation[] array21 = new Orientation[0x70 ^ 0x78];
            array21["".length()] = Orientation.FLIP_UP;
            array21[" ".length()] = Orientation.NORTH;
            array21["  ".length()] = Orientation.FLIP_UP;
            array21["   ".length()] = Orientation.FLIP_NORTH;
            array21[0x66 ^ 0x62] = Orientation.UP;
            array21[0x9B ^ 0x9E] = Orientation.FLIP_NORTH;
            array21[0x74 ^ 0x72] = Orientation.UP;
            array21[0x64 ^ 0x63] = Orientation.NORTH;
            final Orientation[] array22 = new Orientation[0xBF ^ 0xB7];
            array22["".length()] = Orientation.FLIP_UP;
            array22[" ".length()] = Orientation.SOUTH;
            array22["  ".length()] = Orientation.FLIP_UP;
            array22["   ".length()] = Orientation.FLIP_SOUTH;
            array22[0xC5 ^ 0xC1] = Orientation.UP;
            array22[0xA1 ^ 0xA4] = Orientation.FLIP_SOUTH;
            array22[0x58 ^ 0x5E] = Orientation.UP;
            array22[0xBD ^ 0xBA] = Orientation.SOUTH;
            final EnumNeighborInfo[] enum$VALUES = new EnumNeighborInfo[0x4 ^ 0x2];
            enum$VALUES["".length()] = EnumNeighborInfo.DOWN;
            enum$VALUES[" ".length()] = EnumNeighborInfo.UP;
            enum$VALUES["  ".length()] = EnumNeighborInfo.NORTH;
            enum$VALUES["   ".length()] = EnumNeighborInfo.SOUTH;
            enum$VALUES[0x38 ^ 0x3C] = EnumNeighborInfo.WEST;
            enum$VALUES[0x5F ^ 0x5A] = EnumNeighborInfo.EAST;
            ENUM$VALUES = enum$VALUES;
            field_178282_n = new EnumNeighborInfo[0x1B ^ 0x1D];
            final EnumNeighborInfo[] $values = new EnumNeighborInfo[0xC2 ^ 0xC4];
            $values["".length()] = EnumNeighborInfo.DOWN;
            $values[" ".length()] = EnumNeighborInfo.UP;
            $values["  ".length()] = EnumNeighborInfo.NORTH;
            $values["   ".length()] = EnumNeighborInfo.SOUTH;
            $values[0x2A ^ 0x2E] = EnumNeighborInfo.WEST;
            $values[0x38 ^ 0x3D] = EnumNeighborInfo.EAST;
            $VALUES = $values;
            EnumNeighborInfo.field_178282_n[EnumFacing.DOWN.getIndex()] = EnumNeighborInfo.DOWN;
            EnumNeighborInfo.field_178282_n[EnumFacing.UP.getIndex()] = EnumNeighborInfo.UP;
            EnumNeighborInfo.field_178282_n[EnumFacing.NORTH.getIndex()] = EnumNeighborInfo.NORTH;
            EnumNeighborInfo.field_178282_n[EnumFacing.SOUTH.getIndex()] = EnumNeighborInfo.SOUTH;
            EnumNeighborInfo.field_178282_n[EnumFacing.WEST.getIndex()] = EnumNeighborInfo.WEST;
            EnumNeighborInfo.field_178282_n[EnumFacing.EAST.getIndex()] = EnumNeighborInfo.EAST;
        }
        
        private static void I() {
            (I = new String[0x8C ^ 0x81])["".length()] = I("-6\u000e}^^Jcx_X", "nzQMn");
            EnumNeighborInfo.I[" ".length()] = I("\u0014\u0003\u0002\u0019", "PLUWW");
            EnumNeighborInfo.I["  ".length()] = I("3\u0007\u0015\u001f", "wHBQb");
            EnumNeighborInfo.I["   ".length()] = I("\u00009", "UisHt");
            EnumNeighborInfo.I[0xBC ^ 0xB8] = I("\u001f?", "JouNF");
            EnumNeighborInfo.I[0x79 ^ 0x7C] = I(",\u0006*=?", "bIxiw");
            EnumNeighborInfo.I[0x41 ^ 0x47] = I("/\u001f#-!", "aPqyi");
            EnumNeighborInfo.I[0x36 ^ 0x31] = I("\u0016\u0005\u001a\u001b\u0012", "EJOOZ");
            EnumNeighborInfo.I[0x7E ^ 0x76] = I("\u001c\b#$\f", "OGvpD");
            EnumNeighborInfo.I[0x87 ^ 0x8E] = I("!7\u000b7", "vrXcB");
            EnumNeighborInfo.I[0x3E ^ 0x34] = I("1.\u0019\u0000", "fkJTr");
            EnumNeighborInfo.I[0xCA ^ 0xC1] = I("\u000f4\u00183", "JuKgR");
            EnumNeighborInfo.I[0x9E ^ 0x92] = I("-2\t0", "hsZdM");
        }
    }
    
    public enum Orientation
    {
        FLIP_DOWN(Orientation.I[0x69 ^ 0x64], 0x65 ^ 0x63, Orientation.I[0x21 ^ 0x2F], 0x9E ^ 0x98, EnumFacing.DOWN, (boolean)(" ".length() != 0)), 
        FLIP_SOUTH(Orientation.I[0x94 ^ 0x87], 0x3C ^ 0x35, Orientation.I[0xB4 ^ 0xA0], 0x6 ^ 0xF, EnumFacing.SOUTH, (boolean)(" ".length() != 0));
        
        private static final String[] I;
        
        FLIP_UP(Orientation.I[0x58 ^ 0x57], 0xAB ^ 0xAC, Orientation.I[0xB1 ^ 0xA1], 0x54 ^ 0x53, EnumFacing.UP, (boolean)(" ".length() != 0)), 
        NORTH(Orientation.I[0xA0 ^ 0xA5], "  ".length(), Orientation.I[0x30 ^ 0x36], "  ".length(), EnumFacing.NORTH, (boolean)("".length() != 0));
        
        protected final int field_178229_m;
        
        SOUTH(Orientation.I[0x8D ^ 0x8A], "   ".length(), Orientation.I[0x45 ^ 0x4D], "   ".length(), EnumFacing.SOUTH, (boolean)("".length() != 0)), 
        DOWN(Orientation.I[" ".length()], "".length(), Orientation.I["  ".length()], "".length(), EnumFacing.DOWN, (boolean)("".length() != 0)), 
        UP(Orientation.I["   ".length()], " ".length(), Orientation.I[0xBF ^ 0xBB], " ".length(), EnumFacing.UP, (boolean)("".length() != 0)), 
        FLIP_EAST(Orientation.I[0x45 ^ 0x52], 0x44 ^ 0x4F, Orientation.I[0x67 ^ 0x7F], 0x4F ^ 0x44, EnumFacing.EAST, (boolean)(" ".length() != 0)), 
        WEST(Orientation.I[0xCC ^ 0xC5], 0x64 ^ 0x60, Orientation.I[0x89 ^ 0x83], 0x1F ^ 0x1B, EnumFacing.WEST, (boolean)("".length() != 0)), 
        EAST(Orientation.I[0xBE ^ 0xB5], 0x7F ^ 0x7A, Orientation.I[0x7F ^ 0x73], 0x4D ^ 0x48, EnumFacing.EAST, (boolean)("".length() != 0)), 
        FLIP_NORTH(Orientation.I[0x40 ^ 0x51], 0x71 ^ 0x79, Orientation.I[0xAF ^ 0xBD], 0x82 ^ 0x8A, EnumFacing.NORTH, (boolean)(" ".length() != 0)), 
        FLIP_WEST(Orientation.I[0x5E ^ 0x4B], 0x67 ^ 0x6D, Orientation.I[0x88 ^ 0x9E], 0xB3 ^ 0xB9, EnumFacing.WEST, (boolean)(" ".length() != 0));
        
        private static final Orientation[] $VALUES;
        private static final String __OBFID;
        private static final Orientation[] ENUM$VALUES;
        
        static {
            I();
            __OBFID = Orientation.I["".length()];
            final Orientation[] enum$VALUES = new Orientation[0x88 ^ 0x84];
            enum$VALUES["".length()] = Orientation.DOWN;
            enum$VALUES[" ".length()] = Orientation.UP;
            enum$VALUES["  ".length()] = Orientation.NORTH;
            enum$VALUES["   ".length()] = Orientation.SOUTH;
            enum$VALUES[0x30 ^ 0x34] = Orientation.WEST;
            enum$VALUES[0x10 ^ 0x15] = Orientation.EAST;
            enum$VALUES[0x99 ^ 0x9F] = Orientation.FLIP_DOWN;
            enum$VALUES[0x27 ^ 0x20] = Orientation.FLIP_UP;
            enum$VALUES[0xB0 ^ 0xB8] = Orientation.FLIP_NORTH;
            enum$VALUES[0x35 ^ 0x3C] = Orientation.FLIP_SOUTH;
            enum$VALUES[0x45 ^ 0x4F] = Orientation.FLIP_WEST;
            enum$VALUES[0x52 ^ 0x59] = Orientation.FLIP_EAST;
            ENUM$VALUES = enum$VALUES;
            final Orientation[] $values = new Orientation[0x9F ^ 0x93];
            $values["".length()] = Orientation.DOWN;
            $values[" ".length()] = Orientation.UP;
            $values["  ".length()] = Orientation.NORTH;
            $values["   ".length()] = Orientation.SOUTH;
            $values[0x62 ^ 0x66] = Orientation.WEST;
            $values[0x4 ^ 0x1] = Orientation.EAST;
            $values[0x3F ^ 0x39] = Orientation.FLIP_DOWN;
            $values[0x0 ^ 0x7] = Orientation.FLIP_UP;
            $values[0xCA ^ 0xC2] = Orientation.FLIP_NORTH;
            $values[0x54 ^ 0x5D] = Orientation.FLIP_SOUTH;
            $values[0x63 ^ 0x69] = Orientation.FLIP_WEST;
            $values[0x2 ^ 0x9] = Orientation.FLIP_EAST;
            $VALUES = $values;
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x51 ^ 0x48])["".length()] = I("\u00008:}xsDWxyp", "CteMH");
            Orientation.I[" ".length()] = I("#\u0015\u001c\u001e", "gZKPp");
            Orientation.I["  ".length()] = I("\u0013,\u000e\"", "WcYla");
            Orientation.I["   ".length()] = I("\u0019 ", "LpCil");
            Orientation.I[0x4A ^ 0x4E] = I("\u001b\b", "NXdqw");
            Orientation.I[0x1F ^ 0x1A] = I("!%\u0014\u0019!", "ojFMi");
            Orientation.I[0x3B ^ 0x3D] = I("\f+'\u001e!", "BduJi");
            Orientation.I[0xAB ^ 0xAC] = I("\u0019\u0006<><", "JIijt");
            Orientation.I[0x45 ^ 0x4D] = I("=8\u001a\u0010.", "nwODf");
            Orientation.I[0x8A ^ 0x83] = I("5\b\u0003\u0000", "bMPTc");
            Orientation.I[0x21 ^ 0x2B] = I("3713", "drbgK");
            Orientation.I[0x6 ^ 0xD] = I("?0\u0012\u001b", "zqAOh");
            Orientation.I[0xA ^ 0x6] = I("\u0014 9<", "Qajhk");
            Orientation.I[0x82 ^ 0x8F] = I("?\"#\u0018\u001e=!=\u0006", "ynjHA");
            Orientation.I[0x20 ^ 0x2E] = I(")8.%\r+;0;", "otguR");
            Orientation.I[0x46 ^ 0x49] = I("\u000f(%\u0003,\u001c4", "IdlSs");
            Orientation.I[0x2 ^ 0x12] = I("5\u001f\u0011\u0013\u0016&\u0003", "sSXCI");
            Orientation.I[0x3B ^ 0x2A] = I("4'(\b4<$3\f#", "rkaXk");
            Orientation.I[0x78 ^ 0x6A] = I("\u000e\u001e\b\u0005\u001c\u0006\u001d\u0013\u0001\u000b", "HRAUC");
            Orientation.I[0x5C ^ 0x4F] = I("4\u001f\u000f&\u001c!\u001c\u0013\"\u000b", "rSFvC");
            Orientation.I[0x77 ^ 0x63] = I(",<' %9?;$2", "jpnpz");
            Orientation.I[0x5D ^ 0x48] = I("3*\u001c8+\"#\u0006<", "ufUht");
            Orientation.I[0x53 ^ 0x45] = I("\u000f\u000b>1+\u001e\u0002$5", "IGwat");
            Orientation.I[0x17 ^ 0x0] = I("\u001e\u0019:\u00139\u001d\u0014 \u0017", "XUsCf");
            Orientation.I[0x26 ^ 0x3E] = I("24\u0018\u0017\t19\u0002\u0013", "txQGV");
        }
        
        private Orientation(final String s, final int n, final String s2, final int n2, final EnumFacing enumFacing, final boolean b) {
            final int index = enumFacing.getIndex();
            int n3;
            if (b) {
                n3 = EnumFacing.values().length;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            this.field_178229_m = index + n3;
        }
    }
    
    enum VertexTranslations
    {
        private static final String[] I;
        private static final VertexTranslations[] ENUM$VALUES;
        private final int field_178200_h;
        private static final String __OBFID;
        
        NORTH(VertexTranslations.I[0xBB ^ 0xBE], "  ".length(), VertexTranslations.I[0x34 ^ 0x32], "  ".length(), "   ".length(), "".length(), " ".length(), "  ".length());
        
        private final int field_178201_i;
        
        EAST(VertexTranslations.I[0xBD ^ 0xB6], 0xB3 ^ 0xB6, VertexTranslations.I[0x5F ^ 0x53], 0x11 ^ 0x14, " ".length(), "  ".length(), "   ".length(), "".length());
        
        private static final VertexTranslations[] $VALUES;
        private static final VertexTranslations[] field_178199_k;
        
        WEST(VertexTranslations.I[0xBB ^ 0xB2], 0x4F ^ 0x4B, VertexTranslations.I[0x62 ^ 0x68], 0x7D ^ 0x79, "   ".length(), "".length(), " ".length(), "  ".length()), 
        UP(VertexTranslations.I["   ".length()], " ".length(), VertexTranslations.I[0x86 ^ 0x82], " ".length(), "  ".length(), "   ".length(), "".length(), " ".length());
        
        private final int field_178198_j;
        
        SOUTH(VertexTranslations.I[0x1C ^ 0x1B], "   ".length(), VertexTranslations.I[0x36 ^ 0x3E], "   ".length(), "".length(), " ".length(), "  ".length(), "   ".length()), 
        DOWN(VertexTranslations.I[" ".length()], "".length(), VertexTranslations.I["  ".length()], "".length(), "".length(), " ".length(), "  ".length(), "   ".length());
        
        private final int field_178191_g;
        
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
                if (3 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static int access$4(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178201_i;
        }
        
        private VertexTranslations(final String s, final int n, final String s2, final int n2, final int field_178191_g, final int field_178200_h, final int field_178201_i, final int field_178198_j) {
            this.field_178191_g = field_178191_g;
            this.field_178200_h = field_178200_h;
            this.field_178201_i = field_178201_i;
            this.field_178198_j = field_178198_j;
        }
        
        static int access$5(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178198_j;
        }
        
        private static void I() {
            (I = new String[0x5 ^ 0x8])["".length()] = I("3\u00047rS@xZwRD", "pHhBc");
            VertexTranslations.I[" ".length()] = I(",\u000b<;", "hDkuc");
            VertexTranslations.I["  ".length()] = I("\u0015\n\u0016\u001f", "QEAQX");
            VertexTranslations.I["   ".length()] = I("\u00197", "LgdwP");
            VertexTranslations.I[0x62 ^ 0x66] = I("<\t", "iYJgK");
            VertexTranslations.I[0x8B ^ 0x8E] = I("-\b\u0015\u001e-", "cGGJe");
            VertexTranslations.I[0x5D ^ 0x5B] = I(",\f*\u0016-", "bCxBe");
            VertexTranslations.I[0x35 ^ 0x32] = I("\u0002'\u00179\u0000", "QhBmH");
            VertexTranslations.I[0xAF ^ 0xA7] = I("\u0018\u00056\"\u0001", "KJcvI");
            VertexTranslations.I[0x17 ^ 0x1E] = I("\u0006.4 ", "Qkgtl");
            VertexTranslations.I[0xB0 ^ 0xBA] = I("\u000e\u000b#5", "YNpad");
            VertexTranslations.I[0x1A ^ 0x11] = I("\u000e \u0017\u001c", "KaDHz");
            VertexTranslations.I[0x48 ^ 0x44] = I(" \u001b\u0006\u0004", "eZUPj");
        }
        
        static {
            I();
            __OBFID = VertexTranslations.I["".length()];
            final VertexTranslations[] enum$VALUES = new VertexTranslations[0x89 ^ 0x8F];
            enum$VALUES["".length()] = VertexTranslations.DOWN;
            enum$VALUES[" ".length()] = VertexTranslations.UP;
            enum$VALUES["  ".length()] = VertexTranslations.NORTH;
            enum$VALUES["   ".length()] = VertexTranslations.SOUTH;
            enum$VALUES[0x71 ^ 0x75] = VertexTranslations.WEST;
            enum$VALUES[0x9B ^ 0x9E] = VertexTranslations.EAST;
            ENUM$VALUES = enum$VALUES;
            field_178199_k = new VertexTranslations[0x9F ^ 0x99];
            final VertexTranslations[] $values = new VertexTranslations[0x43 ^ 0x45];
            $values["".length()] = VertexTranslations.DOWN;
            $values[" ".length()] = VertexTranslations.UP;
            $values["  ".length()] = VertexTranslations.NORTH;
            $values["   ".length()] = VertexTranslations.SOUTH;
            $values[0x70 ^ 0x74] = VertexTranslations.WEST;
            $values[0x38 ^ 0x3D] = VertexTranslations.EAST;
            $VALUES = $values;
            VertexTranslations.field_178199_k[EnumFacing.DOWN.getIndex()] = VertexTranslations.DOWN;
            VertexTranslations.field_178199_k[EnumFacing.UP.getIndex()] = VertexTranslations.UP;
            VertexTranslations.field_178199_k[EnumFacing.NORTH.getIndex()] = VertexTranslations.NORTH;
            VertexTranslations.field_178199_k[EnumFacing.SOUTH.getIndex()] = VertexTranslations.SOUTH;
            VertexTranslations.field_178199_k[EnumFacing.WEST.getIndex()] = VertexTranslations.WEST;
            VertexTranslations.field_178199_k[EnumFacing.EAST.getIndex()] = VertexTranslations.EAST;
        }
        
        public static VertexTranslations getVertexTranslations(final EnumFacing enumFacing) {
            return VertexTranslations.field_178199_k[enumFacing.getIndex()];
        }
        
        static int access$2(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178191_g;
        }
        
        static int access$3(final VertexTranslations vertexTranslations) {
            return vertexTranslations.field_178200_h;
        }
    }
    
    static final class BlockModelRenderer$1
    {
        private static final String[] I;
        static final int[] field_178290_a;
        private static final String __OBFID;
        
        static {
            I();
            __OBFID = BlockModelRenderer$1.I["".length()];
            field_178290_a = new int[EnumFacing.values().length];
            try {
                BlockModelRenderer$1.field_178290_a[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockModelRenderer$1.field_178290_a[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockModelRenderer$1.field_178290_a[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockModelRenderer$1.field_178290_a[EnumFacing.SOUTH.ordinal()] = (0xBD ^ 0xB9);
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockModelRenderer$1.field_178290_a[EnumFacing.WEST.ordinal()] = (0x9B ^ 0x9E);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockModelRenderer$1.field_178290_a[EnumFacing.EAST.ordinal()] = (0x89 ^ 0x8F);
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u0005'\u0005Awv[hDvq", "FkZqG");
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
                if (3 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
