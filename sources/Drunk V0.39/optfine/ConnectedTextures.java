/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optfine.Config;
import optfine.ConnectedProperties;
import optfine.RenderEnv;
import optfine.ResourceUtils;

public class ConnectedTextures {
    private static Map[] spriteQuadMaps = null;
    private static ConnectedProperties[][] blockProperties = null;
    private static ConnectedProperties[][] tileProperties = null;
    private static boolean multipass = false;
    private static final int Y_NEG_DOWN = 0;
    private static final int Y_POS_UP = 1;
    private static final int Z_NEG_NORTH = 2;
    private static final int Z_POS_SOUTH = 3;
    private static final int X_NEG_WEST = 4;
    private static final int X_POS_EAST = 5;
    private static final int Y_AXIS = 0;
    private static final int Z_AXIS = 1;
    private static final int X_AXIS = 2;
    private static final String[] propSuffixes = new String[]{"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final int[] ctmIndexes = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0};
    public static final IBlockState AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
    private static TextureAtlasSprite emptySprite = null;

    public static synchronized BakedQuad getConnectedTexture(IBlockAccess p_getConnectedTexture_0_, IBlockState p_getConnectedTexture_1_, BlockPos p_getConnectedTexture_2_, BakedQuad p_getConnectedTexture_3_, RenderEnv p_getConnectedTexture_4_) {
        BakedQuad bakedQuad;
        IBlockState iblockstate;
        TextureAtlasSprite textureatlassprite = p_getConnectedTexture_3_.getSprite();
        if (textureatlassprite == null) {
            return p_getConnectedTexture_3_;
        }
        Block block = p_getConnectedTexture_1_.getBlock();
        EnumFacing enumfacing = p_getConnectedTexture_3_.getFace();
        if (block instanceof BlockPane && textureatlassprite.getIconName().startsWith("minecraft:blocks/glass_pane_top") && (iblockstate = p_getConnectedTexture_0_.getBlockState(p_getConnectedTexture_2_.offset(p_getConnectedTexture_3_.getFace()))) == p_getConnectedTexture_1_) {
            return ConnectedTextures.getQuad(emptySprite, block, p_getConnectedTexture_1_, p_getConnectedTexture_3_);
        }
        TextureAtlasSprite textureatlassprite1 = ConnectedTextures.getConnectedTextureMultiPass(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, enumfacing, textureatlassprite, p_getConnectedTexture_4_);
        if (textureatlassprite1 == textureatlassprite) {
            bakedQuad = p_getConnectedTexture_3_;
            return bakedQuad;
        }
        bakedQuad = ConnectedTextures.getQuad(textureatlassprite1, block, p_getConnectedTexture_1_, p_getConnectedTexture_3_);
        return bakedQuad;
    }

    private static BakedQuad getQuad(TextureAtlasSprite p_getQuad_0_, Block p_getQuad_1_, IBlockState p_getQuad_2_, BakedQuad p_getQuad_3_) {
        BakedQuad bakedquad;
        if (spriteQuadMaps == null) {
            return p_getQuad_3_;
        }
        int i = p_getQuad_0_.getIndexInMap();
        if (i < 0) return p_getQuad_3_;
        if (i >= spriteQuadMaps.length) return p_getQuad_3_;
        IdentityHashMap<BakedQuad, BakedQuad> map = spriteQuadMaps[i];
        if (map == null) {
            ConnectedTextures.spriteQuadMaps[i] = map = new IdentityHashMap<BakedQuad, BakedQuad>(1);
        }
        if ((bakedquad = (BakedQuad)map.get(p_getQuad_3_)) != null) return bakedquad;
        bakedquad = ConnectedTextures.makeSpriteQuad(p_getQuad_3_, p_getQuad_0_);
        map.put(p_getQuad_3_, bakedquad);
        return bakedquad;
    }

    private static BakedQuad makeSpriteQuad(BakedQuad p_makeSpriteQuad_0_, TextureAtlasSprite p_makeSpriteQuad_1_) {
        int[] aint = (int[])p_makeSpriteQuad_0_.getVertexData().clone();
        TextureAtlasSprite textureatlassprite = p_makeSpriteQuad_0_.getSprite();
        int i = 0;
        while (i < 4) {
            ConnectedTextures.fixVertex(aint, i, textureatlassprite, p_makeSpriteQuad_1_);
            ++i;
        }
        return new BakedQuad(aint, p_makeSpriteQuad_0_.getTintIndex(), p_makeSpriteQuad_0_.getFace(), p_makeSpriteQuad_1_);
    }

    private static void fixVertex(int[] p_fixVertex_0_, int p_fixVertex_1_, TextureAtlasSprite p_fixVertex_2_, TextureAtlasSprite p_fixVertex_3_) {
        int i = 7 * p_fixVertex_1_;
        float f = Float.intBitsToFloat(p_fixVertex_0_[i + 4]);
        float f1 = Float.intBitsToFloat(p_fixVertex_0_[i + 4 + 1]);
        double d0 = p_fixVertex_2_.getSpriteU16(f);
        double d1 = p_fixVertex_2_.getSpriteV16(f1);
        p_fixVertex_0_[i + 4] = Float.floatToRawIntBits(p_fixVertex_3_.getInterpolatedU(d0));
        p_fixVertex_0_[i + 4 + 1] = Float.floatToRawIntBits(p_fixVertex_3_.getInterpolatedV(d1));
    }

    private static TextureAtlasSprite getConnectedTextureMultiPass(IBlockAccess p_getConnectedTextureMultiPass_0_, IBlockState p_getConnectedTextureMultiPass_1_, BlockPos p_getConnectedTextureMultiPass_2_, EnumFacing p_getConnectedTextureMultiPass_3_, TextureAtlasSprite p_getConnectedTextureMultiPass_4_, RenderEnv p_getConnectedTextureMultiPass_5_) {
        TextureAtlasSprite textureatlassprite = ConnectedTextures.getConnectedTextureSingle(p_getConnectedTextureMultiPass_0_, p_getConnectedTextureMultiPass_1_, p_getConnectedTextureMultiPass_2_, p_getConnectedTextureMultiPass_3_, p_getConnectedTextureMultiPass_4_, true, p_getConnectedTextureMultiPass_5_);
        if (!multipass) {
            return textureatlassprite;
        }
        if (textureatlassprite == p_getConnectedTextureMultiPass_4_) {
            return textureatlassprite;
        }
        TextureAtlasSprite textureatlassprite1 = textureatlassprite;
        int i = 0;
        while (i < 3) {
            TextureAtlasSprite textureatlassprite2 = ConnectedTextures.getConnectedTextureSingle(p_getConnectedTextureMultiPass_0_, p_getConnectedTextureMultiPass_1_, p_getConnectedTextureMultiPass_2_, p_getConnectedTextureMultiPass_3_, textureatlassprite1, false, p_getConnectedTextureMultiPass_5_);
            if (textureatlassprite2 == textureatlassprite1) {
                return textureatlassprite1;
            }
            textureatlassprite1 = textureatlassprite2;
            ++i;
        }
        return textureatlassprite1;
    }

    public static TextureAtlasSprite getConnectedTextureSingle(IBlockAccess p_getConnectedTextureSingle_0_, IBlockState p_getConnectedTextureSingle_1_, BlockPos p_getConnectedTextureSingle_2_, EnumFacing p_getConnectedTextureSingle_3_, TextureAtlasSprite p_getConnectedTextureSingle_4_, boolean p_getConnectedTextureSingle_5_, RenderEnv p_getConnectedTextureSingle_6_) {
        ConnectedProperties[] aconnectedproperties;
        int i;
        Block block = p_getConnectedTextureSingle_1_.getBlock();
        if (tileProperties != null && (i = p_getConnectedTextureSingle_4_.getIndexInMap()) >= 0 && i < tileProperties.length && (aconnectedproperties = tileProperties[i]) != null) {
            int j = p_getConnectedTextureSingle_6_.getMetadata();
            int k = ConnectedTextures.getSide(p_getConnectedTextureSingle_3_);
            for (int l = 0; l < aconnectedproperties.length; ++l) {
                TextureAtlasSprite textureatlassprite;
                int i1;
                ConnectedProperties connectedproperties = aconnectedproperties[l];
                if (connectedproperties == null || !connectedproperties.matchesBlock(i1 = p_getConnectedTextureSingle_6_.getBlockId()) || (textureatlassprite = ConnectedTextures.getConnectedTexture(connectedproperties, p_getConnectedTextureSingle_0_, p_getConnectedTextureSingle_1_, p_getConnectedTextureSingle_2_, k, p_getConnectedTextureSingle_4_, j, p_getConnectedTextureSingle_6_)) == null) continue;
                return textureatlassprite;
            }
        }
        if (blockProperties == null) return p_getConnectedTextureSingle_4_;
        if (!p_getConnectedTextureSingle_5_) return p_getConnectedTextureSingle_4_;
        int j1 = p_getConnectedTextureSingle_6_.getBlockId();
        if (j1 < 0) return p_getConnectedTextureSingle_4_;
        if (j1 >= blockProperties.length) return p_getConnectedTextureSingle_4_;
        ConnectedProperties[] aconnectedproperties1 = blockProperties[j1];
        if (aconnectedproperties1 == null) return p_getConnectedTextureSingle_4_;
        int k1 = p_getConnectedTextureSingle_6_.getMetadata();
        int l1 = ConnectedTextures.getSide(p_getConnectedTextureSingle_3_);
        int i2 = 0;
        while (i2 < aconnectedproperties1.length) {
            TextureAtlasSprite textureatlassprite1;
            ConnectedProperties connectedproperties1 = aconnectedproperties1[i2];
            if (connectedproperties1 != null && connectedproperties1.matchesIcon(p_getConnectedTextureSingle_4_) && (textureatlassprite1 = ConnectedTextures.getConnectedTexture(connectedproperties1, p_getConnectedTextureSingle_0_, p_getConnectedTextureSingle_1_, p_getConnectedTextureSingle_2_, l1, p_getConnectedTextureSingle_4_, k1, p_getConnectedTextureSingle_6_)) != null) {
                return textureatlassprite1;
            }
            ++i2;
        }
        return p_getConnectedTextureSingle_4_;
    }

    public static int getSide(EnumFacing p_getSide_0_) {
        if (p_getSide_0_ == null) {
            return -1;
        }
        switch (1.$SwitchMap$net$minecraft$util$EnumFacing[p_getSide_0_.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 5;
            }
            case 4: {
                return 4;
            }
            case 5: {
                return 2;
            }
            case 6: {
                return 3;
            }
        }
        return -1;
    }

    private static EnumFacing getFacing(int p_getFacing_0_) {
        switch (p_getFacing_0_) {
            case 0: {
                return EnumFacing.DOWN;
            }
            case 1: {
                return EnumFacing.UP;
            }
            case 2: {
                return EnumFacing.NORTH;
            }
            case 3: {
                return EnumFacing.SOUTH;
            }
            case 4: {
                return EnumFacing.WEST;
            }
            case 5: {
                return EnumFacing.EAST;
            }
        }
        return EnumFacing.UP;
    }

    private static TextureAtlasSprite getConnectedTexture(ConnectedProperties p_getConnectedTexture_0_, IBlockAccess p_getConnectedTexture_1_, IBlockState p_getConnectedTexture_2_, BlockPos p_getConnectedTexture_3_, int p_getConnectedTexture_4_, TextureAtlasSprite p_getConnectedTexture_5_, int p_getConnectedTexture_6_, RenderEnv p_getConnectedTexture_7_) {
        int i = p_getConnectedTexture_3_.getY();
        if (i < p_getConnectedTexture_0_.minHeight) return null;
        if (i > p_getConnectedTexture_0_.maxHeight) return null;
        if (p_getConnectedTexture_0_.biomes != null) {
            BiomeGenBase biomegenbase = p_getConnectedTexture_1_.getBiomeGenForCoords(p_getConnectedTexture_3_);
            boolean flag = false;
            for (int j = 0; j < p_getConnectedTexture_0_.biomes.length; ++j) {
                BiomeGenBase biomegenbase1 = p_getConnectedTexture_0_.biomes[j];
                if (biomegenbase != biomegenbase1) continue;
                flag = true;
                break;
            }
            if (!flag) {
                return null;
            }
        }
        int l = 0;
        int i1 = p_getConnectedTexture_6_;
        Block block = p_getConnectedTexture_2_.getBlock();
        if (block instanceof BlockRotatedPillar) {
            l = ConnectedTextures.getWoodAxis(p_getConnectedTexture_4_, p_getConnectedTexture_6_);
            i1 = p_getConnectedTexture_6_ & 3;
        }
        if (block instanceof BlockQuartz) {
            l = ConnectedTextures.getQuartzAxis(p_getConnectedTexture_4_, p_getConnectedTexture_6_);
            if (i1 > 2) {
                i1 = 2;
            }
        }
        if (p_getConnectedTexture_4_ >= 0 && p_getConnectedTexture_0_.faces != 63) {
            int j1 = p_getConnectedTexture_4_;
            if (l != 0) {
                j1 = ConnectedTextures.fixSideByAxis(p_getConnectedTexture_4_, l);
            }
            if ((1 << j1 & p_getConnectedTexture_0_.faces) == 0) {
                return null;
            }
        }
        if (p_getConnectedTexture_0_.metadatas != null) {
            int[] aint = p_getConnectedTexture_0_.metadatas;
            boolean flag1 = false;
            for (int k = 0; k < aint.length; ++k) {
                if (aint[k] != i1) continue;
                flag1 = true;
                break;
            }
            if (!flag1) {
                return null;
            }
        }
        switch (p_getConnectedTexture_0_.method) {
            case 1: {
                return ConnectedTextures.getConnectedTextureCtm(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, p_getConnectedTexture_4_, p_getConnectedTexture_5_, p_getConnectedTexture_6_, p_getConnectedTexture_7_);
            }
            case 2: {
                return ConnectedTextures.getConnectedTextureHorizontal(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, l, p_getConnectedTexture_4_, p_getConnectedTexture_5_, p_getConnectedTexture_6_);
            }
            case 3: {
                return ConnectedTextures.getConnectedTextureTop(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, l, p_getConnectedTexture_4_, p_getConnectedTexture_5_, p_getConnectedTexture_6_);
            }
            case 4: {
                return ConnectedTextures.getConnectedTextureRandom(p_getConnectedTexture_0_, p_getConnectedTexture_3_, p_getConnectedTexture_4_);
            }
            case 5: {
                return ConnectedTextures.getConnectedTextureRepeat(p_getConnectedTexture_0_, p_getConnectedTexture_3_, p_getConnectedTexture_4_);
            }
            case 6: {
                return ConnectedTextures.getConnectedTextureVertical(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, l, p_getConnectedTexture_4_, p_getConnectedTexture_5_, p_getConnectedTexture_6_);
            }
            case 7: {
                return ConnectedTextures.getConnectedTextureFixed(p_getConnectedTexture_0_);
            }
            case 8: {
                return ConnectedTextures.getConnectedTextureHorizontalVertical(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, l, p_getConnectedTexture_4_, p_getConnectedTexture_5_, p_getConnectedTexture_6_);
            }
            case 9: {
                return ConnectedTextures.getConnectedTextureVerticalHorizontal(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, l, p_getConnectedTexture_4_, p_getConnectedTexture_5_, p_getConnectedTexture_6_);
            }
        }
        return null;
    }

    private static int fixSideByAxis(int p_fixSideByAxis_0_, int p_fixSideByAxis_1_) {
        switch (p_fixSideByAxis_1_) {
            case 0: {
                return p_fixSideByAxis_0_;
            }
            case 1: {
                switch (p_fixSideByAxis_0_) {
                    case 0: {
                        return 2;
                    }
                    case 1: {
                        return 3;
                    }
                    case 2: {
                        return 1;
                    }
                    case 3: {
                        return 0;
                    }
                }
                return p_fixSideByAxis_0_;
            }
            case 2: {
                switch (p_fixSideByAxis_0_) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    default: {
                        return p_fixSideByAxis_0_;
                    }
                    case 4: {
                        return 1;
                    }
                    case 5: 
                }
                return 0;
            }
        }
        return p_fixSideByAxis_0_;
    }

    private static int getWoodAxis(int p_getWoodAxis_0_, int p_getWoodAxis_1_) {
        int i = (p_getWoodAxis_1_ & 0xC) >> 2;
        switch (i) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
        }
        return 0;
    }

    private static int getQuartzAxis(int p_getQuartzAxis_0_, int p_getQuartzAxis_1_) {
        switch (p_getQuartzAxis_1_) {
            case 3: {
                return 2;
            }
            case 4: {
                return 1;
            }
        }
        return 0;
    }

    private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties p_getConnectedTextureRandom_0_, BlockPos p_getConnectedTextureRandom_1_, int p_getConnectedTextureRandom_2_) {
        if (p_getConnectedTextureRandom_0_.tileIcons.length == 1) {
            return p_getConnectedTextureRandom_0_.tileIcons[0];
        }
        int i = p_getConnectedTextureRandom_2_ / p_getConnectedTextureRandom_0_.symmetry * p_getConnectedTextureRandom_0_.symmetry;
        int j = Config.getRandom(p_getConnectedTextureRandom_1_, i) & Integer.MAX_VALUE;
        int k = 0;
        if (p_getConnectedTextureRandom_0_.weights == null) {
            k = j % p_getConnectedTextureRandom_0_.tileIcons.length;
            return p_getConnectedTextureRandom_0_.tileIcons[k];
        }
        int l = j % p_getConnectedTextureRandom_0_.sumAllWeights;
        int[] aint = p_getConnectedTextureRandom_0_.sumWeights;
        int i1 = 0;
        while (i1 < aint.length) {
            if (l < aint[i1]) {
                k = i1;
                return p_getConnectedTextureRandom_0_.tileIcons[k];
            }
            ++i1;
        }
        return p_getConnectedTextureRandom_0_.tileIcons[k];
    }

    private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties p_getConnectedTextureFixed_0_) {
        return p_getConnectedTextureFixed_0_.tileIcons[0];
    }

    private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties p_getConnectedTextureRepeat_0_, BlockPos p_getConnectedTextureRepeat_1_, int p_getConnectedTextureRepeat_2_) {
        if (p_getConnectedTextureRepeat_0_.tileIcons.length == 1) {
            return p_getConnectedTextureRepeat_0_.tileIcons[0];
        }
        int i = p_getConnectedTextureRepeat_1_.getX();
        int j = p_getConnectedTextureRepeat_1_.getY();
        int k = p_getConnectedTextureRepeat_1_.getZ();
        int l = 0;
        int i1 = 0;
        switch (p_getConnectedTextureRepeat_2_) {
            case 0: {
                l = i;
                i1 = k;
                break;
            }
            case 1: {
                l = i;
                i1 = k;
                break;
            }
            case 2: {
                l = -i - 1;
                i1 = -j;
                break;
            }
            case 3: {
                l = i;
                i1 = -j;
                break;
            }
            case 4: {
                l = k;
                i1 = -j;
                break;
            }
            case 5: {
                l = -k - 1;
                i1 = -j;
                break;
            }
        }
        i1 %= p_getConnectedTextureRepeat_0_.height;
        if ((l %= p_getConnectedTextureRepeat_0_.width) < 0) {
            l += p_getConnectedTextureRepeat_0_.width;
        }
        if (i1 < 0) {
            i1 += p_getConnectedTextureRepeat_0_.height;
        }
        int j1 = i1 * p_getConnectedTextureRepeat_0_.width + l;
        return p_getConnectedTextureRepeat_0_.tileIcons[j1];
    }

    private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties p_getConnectedTextureCtm_0_, IBlockAccess p_getConnectedTextureCtm_1_, IBlockState p_getConnectedTextureCtm_2_, BlockPos p_getConnectedTextureCtm_3_, int p_getConnectedTextureCtm_4_, TextureAtlasSprite p_getConnectedTextureCtm_5_, int p_getConnectedTextureCtm_6_, RenderEnv p_getConnectedTextureCtm_7_) {
        boolean[] aboolean = p_getConnectedTextureCtm_7_.getBorderFlags();
        switch (p_getConnectedTextureCtm_4_) {
            case 0: {
                aboolean[0] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 1: {
                aboolean[0] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 2: {
                aboolean[0] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 3: {
                aboolean[0] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 4: {
                aboolean[0] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 5: {
                aboolean[0] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
        }
        int i = 0;
        if (aboolean[0] & !aboolean[1] & !aboolean[2] & !aboolean[3]) {
            i = 3;
        } else if (!aboolean[0] & aboolean[1] & !aboolean[2] & !aboolean[3]) {
            i = 1;
        } else if (!aboolean[0] & !aboolean[1] & aboolean[2] & !aboolean[3]) {
            i = 12;
        } else if (!aboolean[0] & !aboolean[1] & !aboolean[2] & aboolean[3]) {
            i = 36;
        } else if (aboolean[0] & aboolean[1] & !aboolean[2] & !aboolean[3]) {
            i = 2;
        } else if (!aboolean[0] & !aboolean[1] & aboolean[2] & aboolean[3]) {
            i = 24;
        } else if (aboolean[0] & !aboolean[1] & aboolean[2] & !aboolean[3]) {
            i = 15;
        } else if (aboolean[0] & !aboolean[1] & !aboolean[2] & aboolean[3]) {
            i = 39;
        } else if (!aboolean[0] & aboolean[1] & aboolean[2] & !aboolean[3]) {
            i = 13;
        } else if (!aboolean[0] & aboolean[1] & !aboolean[2] & aboolean[3]) {
            i = 37;
        } else if (!aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3]) {
            i = 25;
        } else if (aboolean[0] & !aboolean[1] & aboolean[2] & aboolean[3]) {
            i = 27;
        } else if (aboolean[0] & aboolean[1] & !aboolean[2] & aboolean[3]) {
            i = 38;
        } else if (aboolean[0] & aboolean[1] & aboolean[2] & !aboolean[3]) {
            i = 14;
        } else if (aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3]) {
            i = 26;
        }
        if (i == 0) {
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (!Config.isConnectedTexturesFancy()) {
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        switch (p_getConnectedTextureCtm_4_) {
            case 0: {
                aboolean[0] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 1: {
                aboolean[0] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 2: {
                aboolean[0] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 3: {
                aboolean[0] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().down(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.east().up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.west().up(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 4: {
                aboolean[0] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
            case 5: {
                aboolean[0] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[1] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.down().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[2] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up().north(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                aboolean[3] = !ConnectedTextures.isNeighbour(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_.up().south(), p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_);
                break;
            }
        }
        if (i == 13 && aboolean[0]) {
            i = 4;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 15 && aboolean[1]) {
            i = 5;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 37 && aboolean[2]) {
            i = 16;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 39 && aboolean[3]) {
            i = 17;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 14 && aboolean[0] && aboolean[1]) {
            i = 7;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 25 && aboolean[0] && aboolean[2]) {
            i = 6;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 27 && aboolean[3] && aboolean[1]) {
            i = 19;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 38 && aboolean[3] && aboolean[2]) {
            i = 18;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 14 && !aboolean[0] && aboolean[1]) {
            i = 31;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 25 && aboolean[0] && !aboolean[2]) {
            i = 30;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 27 && !aboolean[3] && aboolean[1]) {
            i = 41;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 38 && aboolean[3] && !aboolean[2]) {
            i = 40;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 14 && aboolean[0] && !aboolean[1]) {
            i = 29;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 25 && !aboolean[0] && aboolean[2]) {
            i = 28;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 27 && aboolean[3] && !aboolean[1]) {
            i = 43;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 38 && !aboolean[3] && aboolean[2]) {
            i = 42;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
            i = 46;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
            i = 9;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
            i = 21;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
            i = 8;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
            i = 20;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
            i = 11;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
            i = 22;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
            i = 23;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
            i = 10;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
            i = 34;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
            i = 35;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
            i = 32;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
            i = 33;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
            i = 44;
            return p_getConnectedTextureCtm_0_.tileIcons[i];
        }
        if (i != 26) return p_getConnectedTextureCtm_0_.tileIcons[i];
        if (aboolean[0]) return p_getConnectedTextureCtm_0_.tileIcons[i];
        if (aboolean[1]) return p_getConnectedTextureCtm_0_.tileIcons[i];
        if (aboolean[2]) return p_getConnectedTextureCtm_0_.tileIcons[i];
        if (!aboolean[3]) return p_getConnectedTextureCtm_0_.tileIcons[i];
        i = 45;
        return p_getConnectedTextureCtm_0_.tileIcons[i];
    }

    private static boolean isNeighbour(ConnectedProperties p_isNeighbour_0_, IBlockAccess p_isNeighbour_1_, IBlockState p_isNeighbour_2_, BlockPos p_isNeighbour_3_, int p_isNeighbour_4_, TextureAtlasSprite p_isNeighbour_5_, int p_isNeighbour_6_) {
        IBlockState iblockstate = p_isNeighbour_1_.getBlockState(p_isNeighbour_3_);
        if (p_isNeighbour_2_ == iblockstate) {
            return true;
        }
        if (p_isNeighbour_0_.connect == 2) {
            if (iblockstate == null) {
                return false;
            }
            if (iblockstate == AIR_DEFAULT_STATE) {
                return false;
            }
            TextureAtlasSprite textureatlassprite = ConnectedTextures.getNeighbourIcon(p_isNeighbour_1_, p_isNeighbour_3_, iblockstate, p_isNeighbour_4_);
            if (textureatlassprite != p_isNeighbour_5_) return false;
            return true;
        }
        if (p_isNeighbour_0_.connect != 3) {
            return false;
        }
        if (iblockstate == null) {
            return false;
        }
        if (iblockstate == AIR_DEFAULT_STATE) {
            return false;
        }
        if (iblockstate.getBlock().getMaterial() != p_isNeighbour_2_.getBlock().getMaterial()) return false;
        return true;
    }

    private static TextureAtlasSprite getNeighbourIcon(IBlockAccess p_getNeighbourIcon_0_, BlockPos p_getNeighbourIcon_1_, IBlockState p_getNeighbourIcon_2_, int p_getNeighbourIcon_3_) {
        p_getNeighbourIcon_2_ = p_getNeighbourIcon_2_.getBlock().getActualState(p_getNeighbourIcon_2_, p_getNeighbourIcon_0_, p_getNeighbourIcon_1_);
        IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(p_getNeighbourIcon_2_);
        if (ibakedmodel == null) {
            return null;
        }
        EnumFacing enumfacing = ConnectedTextures.getFacing(p_getNeighbourIcon_3_);
        List<BakedQuad> list = ibakedmodel.getFaceQuads(enumfacing);
        if (list.size() > 0) {
            BakedQuad bakedquad1 = list.get(0);
            return bakedquad1.getSprite();
        }
        List<BakedQuad> list1 = ibakedmodel.getGeneralQuads();
        int i = 0;
        while (i < list1.size()) {
            BakedQuad bakedquad = list1.get(i);
            if (bakedquad.getFace() == enumfacing) {
                return bakedquad.getSprite();
            }
            ++i;
        }
        return null;
    }

    private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties p_getConnectedTextureHorizontal_0_, IBlockAccess p_getConnectedTextureHorizontal_1_, IBlockState p_getConnectedTextureHorizontal_2_, BlockPos p_getConnectedTextureHorizontal_3_, int p_getConnectedTextureHorizontal_4_, int p_getConnectedTextureHorizontal_5_, TextureAtlasSprite p_getConnectedTextureHorizontal_6_, int p_getConnectedTextureHorizontal_7_) {
        boolean flag = false;
        boolean flag1 = false;
        switch (p_getConnectedTextureHorizontal_4_) {
            case 0: {
                switch (p_getConnectedTextureHorizontal_5_) {
                    case 0: 
                    case 1: {
                        return null;
                    }
                    case 2: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 3: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 4: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 5: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (p_getConnectedTextureHorizontal_5_) {
                    case 0: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 1: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 2: 
                    case 3: {
                        return null;
                    }
                    case 4: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 5: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch (p_getConnectedTextureHorizontal_5_) {
                    case 0: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 1: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 2: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 3: {
                        flag = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
                        break;
                    }
                    case 4: 
                    case 5: {
                        return null;
                    }
                }
                break;
            }
        }
        int i = 3;
        if (flag) {
            if (flag1) {
                i = 1;
                return p_getConnectedTextureHorizontal_0_.tileIcons[i];
            }
            i = 2;
            return p_getConnectedTextureHorizontal_0_.tileIcons[i];
        }
        if (flag1) {
            i = 0;
            return p_getConnectedTextureHorizontal_0_.tileIcons[i];
        }
        i = 3;
        return p_getConnectedTextureHorizontal_0_.tileIcons[i];
    }

    private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties p_getConnectedTextureVertical_0_, IBlockAccess p_getConnectedTextureVertical_1_, IBlockState p_getConnectedTextureVertical_2_, BlockPos p_getConnectedTextureVertical_3_, int p_getConnectedTextureVertical_4_, int p_getConnectedTextureVertical_5_, TextureAtlasSprite p_getConnectedTextureVertical_6_, int p_getConnectedTextureVertical_7_) {
        boolean flag = false;
        boolean flag1 = false;
        switch (p_getConnectedTextureVertical_4_) {
            case 0: {
                if (p_getConnectedTextureVertical_5_ == 1) return null;
                if (p_getConnectedTextureVertical_5_ == 0) {
                    return null;
                }
                flag = ConnectedTextures.isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                break;
            }
            case 1: {
                if (p_getConnectedTextureVertical_5_ == 3) return null;
                if (p_getConnectedTextureVertical_5_ == 2) {
                    return null;
                }
                flag = ConnectedTextures.isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.south(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.north(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                break;
            }
            case 2: {
                if (p_getConnectedTextureVertical_5_ == 5) return null;
                if (p_getConnectedTextureVertical_5_ == 4) {
                    return null;
                }
                flag = ConnectedTextures.isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.west(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                flag1 = ConnectedTextures.isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.east(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
                break;
            }
        }
        int i = 3;
        if (flag) {
            if (flag1) {
                i = 1;
                return p_getConnectedTextureVertical_0_.tileIcons[i];
            }
            i = 2;
            return p_getConnectedTextureVertical_0_.tileIcons[i];
        }
        if (flag1) {
            i = 0;
            return p_getConnectedTextureVertical_0_.tileIcons[i];
        }
        i = 3;
        return p_getConnectedTextureVertical_0_.tileIcons[i];
    }

    private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties p_getConnectedTextureHorizontalVertical_0_, IBlockAccess p_getConnectedTextureHorizontalVertical_1_, IBlockState p_getConnectedTextureHorizontalVertical_2_, BlockPos p_getConnectedTextureHorizontalVertical_3_, int p_getConnectedTextureHorizontalVertical_4_, int p_getConnectedTextureHorizontalVertical_5_, TextureAtlasSprite p_getConnectedTextureHorizontalVertical_6_, int p_getConnectedTextureHorizontalVertical_7_) {
        TextureAtlasSprite textureAtlasSprite;
        TextureAtlasSprite[] atextureatlassprite = p_getConnectedTextureHorizontalVertical_0_.tileIcons;
        TextureAtlasSprite textureatlassprite = ConnectedTextures.getConnectedTextureHorizontal(p_getConnectedTextureHorizontalVertical_0_, p_getConnectedTextureHorizontalVertical_1_, p_getConnectedTextureHorizontalVertical_2_, p_getConnectedTextureHorizontalVertical_3_, p_getConnectedTextureHorizontalVertical_4_, p_getConnectedTextureHorizontalVertical_5_, p_getConnectedTextureHorizontalVertical_6_, p_getConnectedTextureHorizontalVertical_7_);
        if (textureatlassprite != null && textureatlassprite != p_getConnectedTextureHorizontalVertical_6_ && textureatlassprite != atextureatlassprite[3]) {
            return textureatlassprite;
        }
        TextureAtlasSprite textureatlassprite1 = ConnectedTextures.getConnectedTextureVertical(p_getConnectedTextureHorizontalVertical_0_, p_getConnectedTextureHorizontalVertical_1_, p_getConnectedTextureHorizontalVertical_2_, p_getConnectedTextureHorizontalVertical_3_, p_getConnectedTextureHorizontalVertical_4_, p_getConnectedTextureHorizontalVertical_5_, p_getConnectedTextureHorizontalVertical_6_, p_getConnectedTextureHorizontalVertical_7_);
        if (textureatlassprite1 == atextureatlassprite[0]) {
            textureAtlasSprite = atextureatlassprite[4];
            return textureAtlasSprite;
        }
        if (textureatlassprite1 == atextureatlassprite[1]) {
            textureAtlasSprite = atextureatlassprite[5];
            return textureAtlasSprite;
        }
        if (textureatlassprite1 == atextureatlassprite[2]) {
            textureAtlasSprite = atextureatlassprite[6];
            return textureAtlasSprite;
        }
        textureAtlasSprite = textureatlassprite1;
        return textureAtlasSprite;
    }

    private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties p_getConnectedTextureVerticalHorizontal_0_, IBlockAccess p_getConnectedTextureVerticalHorizontal_1_, IBlockState p_getConnectedTextureVerticalHorizontal_2_, BlockPos p_getConnectedTextureVerticalHorizontal_3_, int p_getConnectedTextureVerticalHorizontal_4_, int p_getConnectedTextureVerticalHorizontal_5_, TextureAtlasSprite p_getConnectedTextureVerticalHorizontal_6_, int p_getConnectedTextureVerticalHorizontal_7_) {
        TextureAtlasSprite textureAtlasSprite;
        TextureAtlasSprite[] atextureatlassprite = p_getConnectedTextureVerticalHorizontal_0_.tileIcons;
        TextureAtlasSprite textureatlassprite = ConnectedTextures.getConnectedTextureVertical(p_getConnectedTextureVerticalHorizontal_0_, p_getConnectedTextureVerticalHorizontal_1_, p_getConnectedTextureVerticalHorizontal_2_, p_getConnectedTextureVerticalHorizontal_3_, p_getConnectedTextureVerticalHorizontal_4_, p_getConnectedTextureVerticalHorizontal_5_, p_getConnectedTextureVerticalHorizontal_6_, p_getConnectedTextureVerticalHorizontal_7_);
        if (textureatlassprite != null && textureatlassprite != p_getConnectedTextureVerticalHorizontal_6_ && textureatlassprite != atextureatlassprite[3]) {
            return textureatlassprite;
        }
        TextureAtlasSprite textureatlassprite1 = ConnectedTextures.getConnectedTextureHorizontal(p_getConnectedTextureVerticalHorizontal_0_, p_getConnectedTextureVerticalHorizontal_1_, p_getConnectedTextureVerticalHorizontal_2_, p_getConnectedTextureVerticalHorizontal_3_, p_getConnectedTextureVerticalHorizontal_4_, p_getConnectedTextureVerticalHorizontal_5_, p_getConnectedTextureVerticalHorizontal_6_, p_getConnectedTextureVerticalHorizontal_7_);
        if (textureatlassprite1 == atextureatlassprite[0]) {
            textureAtlasSprite = atextureatlassprite[4];
            return textureAtlasSprite;
        }
        if (textureatlassprite1 == atextureatlassprite[1]) {
            textureAtlasSprite = atextureatlassprite[5];
            return textureAtlasSprite;
        }
        if (textureatlassprite1 == atextureatlassprite[2]) {
            textureAtlasSprite = atextureatlassprite[6];
            return textureAtlasSprite;
        }
        textureAtlasSprite = textureatlassprite1;
        return textureAtlasSprite;
    }

    private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties p_getConnectedTextureTop_0_, IBlockAccess p_getConnectedTextureTop_1_, IBlockState p_getConnectedTextureTop_2_, BlockPos p_getConnectedTextureTop_3_, int p_getConnectedTextureTop_4_, int p_getConnectedTextureTop_5_, TextureAtlasSprite p_getConnectedTextureTop_6_, int p_getConnectedTextureTop_7_) {
        boolean flag = false;
        switch (p_getConnectedTextureTop_4_) {
            case 0: {
                if (p_getConnectedTextureTop_5_ == 1) return null;
                if (p_getConnectedTextureTop_5_ == 0) {
                    return null;
                }
                flag = ConnectedTextures.isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.up(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
                break;
            }
            case 1: {
                if (p_getConnectedTextureTop_5_ == 3) return null;
                if (p_getConnectedTextureTop_5_ == 2) {
                    return null;
                }
                flag = ConnectedTextures.isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.south(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
                break;
            }
            case 2: {
                if (p_getConnectedTextureTop_5_ == 5) return null;
                if (p_getConnectedTextureTop_5_ == 4) {
                    return null;
                }
                flag = ConnectedTextures.isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.east(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
                break;
            }
        }
        if (!flag) return null;
        return p_getConnectedTextureTop_0_.tileIcons[0];
    }

    public static void updateIcons(TextureMap p_updateIcons_0_) {
        blockProperties = null;
        tileProperties = null;
        if (!Config.isConnectedTextures()) return;
        IResourcePack[] airesourcepack = Config.getResourcePacks();
        int i = airesourcepack.length - 1;
        while (true) {
            if (i < 0) {
                ConnectedTextures.updateIcons(p_updateIcons_0_, Config.getDefaultResourcePack());
                ResourceLocation resourcelocation = new ResourceLocation("mcpatcher/ctm/default/empty");
                emptySprite = p_updateIcons_0_.registerSprite(resourcelocation);
                spriteQuadMaps = new Map[p_updateIcons_0_.getCountRegisteredSprites() + 1];
                return;
            }
            IResourcePack iresourcepack = airesourcepack[i];
            ConnectedTextures.updateIcons(p_updateIcons_0_, iresourcepack);
            --i;
        }
    }

    private static void updateIconEmpty(TextureMap p_updateIconEmpty_0_) {
    }

    public static void updateIcons(TextureMap p_updateIcons_0_, IResourcePack p_updateIcons_1_) {
        String[] astring = ConnectedTextures.collectFiles(p_updateIcons_1_, "mcpatcher/ctm/", ".properties");
        Arrays.sort(astring);
        List list = ConnectedTextures.makePropertyList(tileProperties);
        List list1 = ConnectedTextures.makePropertyList(blockProperties);
        int i = 0;
        while (true) {
            if (i >= astring.length) {
                blockProperties = ConnectedTextures.propertyListToArray(list1);
                tileProperties = ConnectedTextures.propertyListToArray(list);
                multipass = ConnectedTextures.detectMultipass();
                Config.dbg("Multipass connected textures: " + multipass);
                return;
            }
            String s = astring[i];
            Config.dbg("ConnectedTextures: " + s);
            try {
                ResourceLocation resourcelocation = new ResourceLocation(s);
                InputStream inputstream = p_updateIcons_1_.getInputStream(resourcelocation);
                if (inputstream == null) {
                    Config.warn("ConnectedTextures file not found: " + s);
                } else {
                    Properties properties = new Properties();
                    properties.load(inputstream);
                    ConnectedProperties connectedproperties = new ConnectedProperties(properties, s);
                    if (connectedproperties.isValid(s)) {
                        connectedproperties.updateIcons(p_updateIcons_0_);
                        ConnectedTextures.addToTileList(connectedproperties, list);
                        ConnectedTextures.addToBlockList(connectedproperties, list1);
                    }
                }
            }
            catch (FileNotFoundException var11) {
                Config.warn("ConnectedTextures file not found: " + s);
            }
            catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
            ++i;
        }
    }

    private static List makePropertyList(ConnectedProperties[][] p_makePropertyList_0_) {
        ArrayList<ArrayList<ConnectedProperties>> list = new ArrayList<ArrayList<ConnectedProperties>>();
        if (p_makePropertyList_0_ == null) return list;
        int i = 0;
        while (i < p_makePropertyList_0_.length) {
            ConnectedProperties[] aconnectedproperties = p_makePropertyList_0_[i];
            ArrayList<ConnectedProperties> list1 = null;
            if (aconnectedproperties != null) {
                list1 = new ArrayList<ConnectedProperties>(Arrays.asList(aconnectedproperties));
            }
            list.add(list1);
            ++i;
        }
        return list;
    }

    private static boolean detectMultipass() {
        ArrayList<ConnectedProperties> list = new ArrayList<ConnectedProperties>();
        for (int i = 0; i < tileProperties.length; ++i) {
            ConnectedProperties[] aconnectedproperties = tileProperties[i];
            if (aconnectedproperties == null) continue;
            list.addAll(Arrays.asList(aconnectedproperties));
        }
        for (int k = 0; k < blockProperties.length; ++k) {
            ConnectedProperties[] aconnectedproperties2 = blockProperties[k];
            if (aconnectedproperties2 == null) continue;
            list.addAll(Arrays.asList(aconnectedproperties2));
        }
        ConnectedProperties[] aconnectedproperties1 = list.toArray(new ConnectedProperties[list.size()]);
        HashSet<TextureAtlasSprite> set1 = new HashSet<TextureAtlasSprite>();
        HashSet<TextureAtlasSprite> set = new HashSet<TextureAtlasSprite>();
        for (int j = 0; j < aconnectedproperties1.length; ++j) {
            ConnectedProperties connectedproperties = aconnectedproperties1[j];
            if (connectedproperties.matchTileIcons != null) {
                set1.addAll(Arrays.asList(connectedproperties.matchTileIcons));
            }
            if (connectedproperties.tileIcons == null) continue;
            set.addAll(Arrays.asList(connectedproperties.tileIcons));
        }
        set1.retainAll(set);
        if (set1.isEmpty()) return false;
        return true;
    }

    private static ConnectedProperties[][] propertyListToArray(List p_propertyListToArray_0_) {
        ConnectedProperties[][] aconnectedproperties = new ConnectedProperties[p_propertyListToArray_0_.size()][];
        int i = 0;
        while (i < p_propertyListToArray_0_.size()) {
            List list = (List)p_propertyListToArray_0_.get(i);
            if (list != null) {
                ConnectedProperties[] aconnectedproperties1 = list.toArray(new ConnectedProperties[list.size()]);
                aconnectedproperties[i] = aconnectedproperties1;
            }
            ++i;
        }
        return aconnectedproperties;
    }

    private static void addToTileList(ConnectedProperties p_addToTileList_0_, List p_addToTileList_1_) {
        if (p_addToTileList_0_.matchTileIcons == null) return;
        int i = 0;
        while (i < p_addToTileList_0_.matchTileIcons.length) {
            TextureAtlasSprite textureatlassprite = p_addToTileList_0_.matchTileIcons[i];
            if (!(textureatlassprite instanceof TextureAtlasSprite)) {
                Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + textureatlassprite + ", name: " + textureatlassprite.getIconName());
            } else {
                int j = textureatlassprite.getIndexInMap();
                if (j < 0) {
                    Config.warn("Invalid tile ID: " + j + ", icon: " + textureatlassprite.getIconName());
                } else {
                    ConnectedTextures.addToList(p_addToTileList_0_, p_addToTileList_1_, j);
                }
            }
            ++i;
        }
    }

    private static void addToBlockList(ConnectedProperties p_addToBlockList_0_, List p_addToBlockList_1_) {
        if (p_addToBlockList_0_.matchBlocks == null) return;
        int i = 0;
        while (i < p_addToBlockList_0_.matchBlocks.length) {
            int j = p_addToBlockList_0_.matchBlocks[i];
            if (j < 0) {
                Config.warn("Invalid block ID: " + j);
            } else {
                ConnectedTextures.addToList(p_addToBlockList_0_, p_addToBlockList_1_, j);
            }
            ++i;
        }
    }

    private static void addToList(ConnectedProperties p_addToList_0_, List p_addToList_1_, int p_addToList_2_) {
        while (p_addToList_2_ >= p_addToList_1_.size()) {
            p_addToList_1_.add(null);
        }
        ArrayList<ConnectedProperties> list = (ArrayList<ConnectedProperties>)p_addToList_1_.get(p_addToList_2_);
        if (list == null) {
            list = new ArrayList<ConnectedProperties>();
            p_addToList_1_.set(p_addToList_2_, list);
        }
        list.add(p_addToList_0_);
    }

    private static String[] collectFiles(IResourcePack p_collectFiles_0_, String p_collectFiles_1_, String p_collectFiles_2_) {
        String[] stringArray;
        if (p_collectFiles_0_ instanceof DefaultResourcePack) {
            return ConnectedTextures.collectFilesDefault(p_collectFiles_0_);
        }
        if (!(p_collectFiles_0_ instanceof AbstractResourcePack)) {
            return new String[0];
        }
        AbstractResourcePack abstractresourcepack = (AbstractResourcePack)p_collectFiles_0_;
        File file1 = ResourceUtils.getResourcePackFile(abstractresourcepack);
        if (file1 == null) {
            stringArray = new String[]{};
            return stringArray;
        }
        if (file1.isDirectory()) {
            stringArray = ConnectedTextures.collectFilesFolder(file1, "", p_collectFiles_1_, p_collectFiles_2_);
            return stringArray;
        }
        if (file1.isFile()) {
            stringArray = ConnectedTextures.collectFilesZIP(file1, p_collectFiles_1_, p_collectFiles_2_);
            return stringArray;
        }
        stringArray = new String[]{};
        return stringArray;
    }

    private static String[] collectFilesDefault(IResourcePack p_collectFilesDefault_0_) {
        ArrayList<String> list = new ArrayList<String>();
        String[] astring = ConnectedTextures.getDefaultCtmPaths();
        int i = 0;
        while (i < astring.length) {
            String s = astring[i];
            ResourceLocation resourcelocation = new ResourceLocation(s);
            if (p_collectFilesDefault_0_.resourceExists(resourcelocation)) {
                list.add(s);
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }

    private static String[] getDefaultCtmPaths() {
        ArrayList<String> list = new ArrayList<String>();
        String s = "mcpatcher/ctm/default/";
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
            list.add(s + "glass.properties");
            list.add(s + "glasspane.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
            list.add(s + "bookshelf.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
            list.add(s + "sandstone.properties");
        }
        String[] astring = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
        int i = 0;
        while (i < astring.length) {
            String s1 = astring[i];
            if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + s1 + ".png"))) {
                list.add(s + i + "_glass_" + s1 + "/glass_" + s1 + ".properties");
                list.add(s + i + "_glass_" + s1 + "/glass_pane_" + s1 + ".properties");
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }

    private static String[] collectFilesFolder(File p_collectFilesFolder_0_, String p_collectFilesFolder_1_, String p_collectFilesFolder_2_, String p_collectFilesFolder_3_) {
        ArrayList<String> list = new ArrayList<String>();
        String s = "assets/minecraft/";
        File[] afile = p_collectFilesFolder_0_.listFiles();
        if (afile == null) {
            return new String[0];
        }
        int i = 0;
        while (i < afile.length) {
            File file1 = afile[i];
            if (file1.isFile()) {
                String s3 = p_collectFilesFolder_1_ + file1.getName();
                if (s3.startsWith(s) && (s3 = s3.substring(s.length())).startsWith(p_collectFilesFolder_2_) && s3.endsWith(p_collectFilesFolder_3_)) {
                    list.add(s3);
                }
            } else if (file1.isDirectory()) {
                String s1 = p_collectFilesFolder_1_ + file1.getName() + "/";
                String[] astring = ConnectedTextures.collectFilesFolder(file1, s1, p_collectFilesFolder_2_, p_collectFilesFolder_3_);
                for (int j = 0; j < astring.length; ++j) {
                    String s2 = astring[j];
                    list.add(s2);
                }
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }

    private static String[] collectFilesZIP(File p_collectFilesZIP_0_, String p_collectFilesZIP_1_, String p_collectFilesZIP_2_) {
        ArrayList<String> list = new ArrayList<String>();
        String s = "assets/minecraft/";
        try {
            ZipFile zipfile = new ZipFile(p_collectFilesZIP_0_);
            Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
            while (true) {
                if (!enumeration.hasMoreElements()) {
                    zipfile.close();
                    return list.toArray(new String[list.size()]);
                }
                ZipEntry zipentry = enumeration.nextElement();
                String s1 = zipentry.getName();
                if (!s1.startsWith(s) || !(s1 = s1.substring(s.length())).startsWith(p_collectFilesZIP_1_) || !s1.endsWith(p_collectFilesZIP_2_)) continue;
                list.add(s1);
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            return new String[0];
        }
    }

    public static int getPaneTextureIndex(boolean p_getPaneTextureIndex_0_, boolean p_getPaneTextureIndex_1_, boolean p_getPaneTextureIndex_2_, boolean p_getPaneTextureIndex_3_) {
        if (p_getPaneTextureIndex_1_ && p_getPaneTextureIndex_0_) {
            if (p_getPaneTextureIndex_2_) {
                if (!p_getPaneTextureIndex_3_) return 50;
                return 34;
            }
            if (!p_getPaneTextureIndex_3_) return 2;
            return 18;
        }
        if (p_getPaneTextureIndex_1_ && !p_getPaneTextureIndex_0_) {
            if (p_getPaneTextureIndex_2_) {
                if (!p_getPaneTextureIndex_3_) return 51;
                return 35;
            }
            if (!p_getPaneTextureIndex_3_) return 3;
            return 19;
        }
        if (!p_getPaneTextureIndex_1_ && p_getPaneTextureIndex_0_) {
            if (p_getPaneTextureIndex_2_) {
                if (!p_getPaneTextureIndex_3_) return 49;
                return 33;
            }
            if (!p_getPaneTextureIndex_3_) return 1;
            return 17;
        }
        if (p_getPaneTextureIndex_2_) {
            if (!p_getPaneTextureIndex_3_) return 48;
            return 32;
        }
        if (!p_getPaneTextureIndex_3_) return 0;
        return 16;
    }

    public static int getReversePaneTextureIndex(int p_getReversePaneTextureIndex_0_) {
        int n;
        int i = p_getReversePaneTextureIndex_0_ % 16;
        if (i == 1) {
            n = p_getReversePaneTextureIndex_0_ + 2;
            return n;
        }
        if (i == 3) {
            n = p_getReversePaneTextureIndex_0_ - 2;
            return n;
        }
        n = p_getReversePaneTextureIndex_0_;
        return n;
    }

    public static TextureAtlasSprite getCtmTexture(ConnectedProperties p_getCtmTexture_0_, int p_getCtmTexture_1_, TextureAtlasSprite p_getCtmTexture_2_) {
        TextureAtlasSprite textureAtlasSprite;
        if (p_getCtmTexture_0_.method != 1) {
            return p_getCtmTexture_2_;
        }
        if (p_getCtmTexture_1_ < 0) return p_getCtmTexture_2_;
        if (p_getCtmTexture_1_ >= ctmIndexes.length) return p_getCtmTexture_2_;
        int i = ctmIndexes[p_getCtmTexture_1_];
        TextureAtlasSprite[] atextureatlassprite = p_getCtmTexture_0_.tileIcons;
        if (i >= 0 && i < atextureatlassprite.length) {
            textureAtlasSprite = atextureatlassprite[i];
            return textureAtlasSprite;
        }
        textureAtlasSprite = p_getCtmTexture_2_;
        return textureAtlasSprite;
    }
}

