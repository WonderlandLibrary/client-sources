/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.Config;
import optifine.ConnectedProperties;
import optifine.MatchBlock;
import optifine.RenderEnv;
import optifine.ResUtils;

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
    private static final int[] ctmIndexes;
    public static final IBlockState AIR_DEFAULT_STATE;
    private static TextureAtlasSprite emptySprite;

    static {
        int[] arrn = new int[64];
        arrn[1] = 1;
        arrn[2] = 2;
        arrn[3] = 3;
        arrn[4] = 4;
        arrn[5] = 5;
        arrn[6] = 6;
        arrn[7] = 7;
        arrn[8] = 8;
        arrn[9] = 9;
        arrn[10] = 10;
        arrn[11] = 11;
        arrn[16] = 12;
        arrn[17] = 13;
        arrn[18] = 14;
        arrn[19] = 15;
        arrn[20] = 16;
        arrn[21] = 17;
        arrn[22] = 18;
        arrn[23] = 19;
        arrn[24] = 20;
        arrn[25] = 21;
        arrn[26] = 22;
        arrn[27] = 23;
        arrn[32] = 24;
        arrn[33] = 25;
        arrn[34] = 26;
        arrn[35] = 27;
        arrn[36] = 28;
        arrn[37] = 29;
        arrn[38] = 30;
        arrn[39] = 31;
        arrn[40] = 32;
        arrn[41] = 33;
        arrn[42] = 34;
        arrn[43] = 35;
        arrn[48] = 36;
        arrn[49] = 37;
        arrn[50] = 38;
        arrn[51] = 39;
        arrn[52] = 40;
        arrn[53] = 41;
        arrn[54] = 42;
        arrn[55] = 43;
        arrn[56] = 44;
        arrn[57] = 45;
        arrn[58] = 46;
        ctmIndexes = arrn;
        AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
        emptySprite = null;
    }

    public static synchronized BakedQuad getConnectedTexture(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, BakedQuad quad, RenderEnv renderEnv) {
        IBlockState sprite;
        TextureAtlasSprite spriteIn = quad.getSprite();
        if (spriteIn == null) {
            return quad;
        }
        Block block = blockState.getBlock();
        EnumFacing side = quad.getFace();
        if (block instanceof BlockPane && spriteIn.getIconName().startsWith("minecraft:blocks/glass_pane_top") && (sprite = blockAccess.getBlockState(blockPos.offset(quad.getFace()))) == blockState) {
            return ConnectedTextures.getQuad(emptySprite, block, blockState, quad);
        }
        TextureAtlasSprite sprite1 = ConnectedTextures.getConnectedTextureMultiPass(blockAccess, blockState, blockPos, side, spriteIn, renderEnv);
        return sprite1 == spriteIn ? quad : ConnectedTextures.getQuad(sprite1, block, blockState, quad);
    }

    private static BakedQuad getQuad(TextureAtlasSprite sprite, Block block, IBlockState blockState, BakedQuad quadIn) {
        if (spriteQuadMaps == null) {
            return quadIn;
        }
        int spriteIndex = sprite.getIndexInMap();
        if (spriteIndex >= 0 && spriteIndex < spriteQuadMaps.length) {
            BakedQuad quad;
            IdentityHashMap quadMap = spriteQuadMaps[spriteIndex];
            if (quadMap == null) {
                quadMap = new IdentityHashMap(1);
                ConnectedTextures.spriteQuadMaps[spriteIndex] = quadMap;
            }
            if ((quad = (BakedQuad)quadMap.get(quadIn)) == null) {
                quad = ConnectedTextures.makeSpriteQuad(quadIn, sprite);
                ((Map)quadMap).put(quadIn, quad);
            }
            return quad;
        }
        return quadIn;
    }

    private static BakedQuad makeSpriteQuad(BakedQuad quad, TextureAtlasSprite sprite) {
        int[] data = (int[])quad.func_178209_a().clone();
        TextureAtlasSprite spriteFrom = quad.getSprite();
        for (int bq2 = 0; bq2 < 4; ++bq2) {
            ConnectedTextures.fixVertex(data, bq2, spriteFrom, sprite);
        }
        BakedQuad var5 = new BakedQuad(data, quad.func_178211_c(), quad.getFace(), sprite);
        return var5;
    }

    private static void fixVertex(int[] data, int vertex, TextureAtlasSprite spriteFrom, TextureAtlasSprite spriteTo) {
        int mul = data.length / 4;
        int pos = mul * vertex;
        float u2 = Float.intBitsToFloat(data[pos + 4]);
        float v2 = Float.intBitsToFloat(data[pos + 4 + 1]);
        double su16 = spriteFrom.getSpriteU16(u2);
        double sv16 = spriteFrom.getSpriteV16(v2);
        data[pos + 4] = Float.floatToRawIntBits(spriteTo.getInterpolatedU(su16));
        data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.getInterpolatedV(sv16));
    }

    private static TextureAtlasSprite getConnectedTextureMultiPass(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing side, TextureAtlasSprite icon, RenderEnv renderEnv) {
        TextureAtlasSprite newIcon = ConnectedTextures.getConnectedTextureSingle(blockAccess, blockState, blockPos, side, icon, true, renderEnv);
        if (!multipass) {
            return newIcon;
        }
        if (newIcon == icon) {
            return newIcon;
        }
        TextureAtlasSprite mpIcon = newIcon;
        for (int i2 = 0; i2 < 3; ++i2) {
            TextureAtlasSprite newMpIcon = ConnectedTextures.getConnectedTextureSingle(blockAccess, blockState, blockPos, side, mpIcon, false, renderEnv);
            if (newMpIcon == mpIcon) break;
            mpIcon = newMpIcon;
        }
        return mpIcon;
    }

    public static TextureAtlasSprite getConnectedTextureSingle(IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, EnumFacing facing, TextureAtlasSprite icon, boolean checkBlocks, RenderEnv renderEnv) {
        int i2;
        ConnectedProperties cp2;
        int side;
        int blockId;
        ConnectedProperties[] cps;
        TextureAtlasSprite newIcon;
        Block block = blockState.getBlock();
        if (!(blockState instanceof BlockStateBase)) {
            return icon;
        }
        BlockStateBase blockStateBase = (BlockStateBase)blockState;
        if (tileProperties != null && (blockId = icon.getIndexInMap()) >= 0 && blockId < tileProperties.length && (cps = tileProperties[blockId]) != null) {
            side = ConnectedTextures.getSide(facing);
            for (i2 = 0; i2 < cps.length; ++i2) {
                cp2 = cps[i2];
                if (cp2 == null || !cp2.matchesBlockId(blockStateBase.getBlockId()) || (newIcon = ConnectedTextures.getConnectedTexture(cp2, blockAccess, blockStateBase, blockPos, side, icon, renderEnv)) == null) continue;
                return newIcon;
            }
        }
        if (blockProperties != null && checkBlocks && (blockId = renderEnv.getBlockId()) >= 0 && blockId < blockProperties.length && (cps = blockProperties[blockId]) != null) {
            side = ConnectedTextures.getSide(facing);
            for (i2 = 0; i2 < cps.length; ++i2) {
                cp2 = cps[i2];
                if (cp2 == null || !cp2.matchesIcon(icon) || (newIcon = ConnectedTextures.getConnectedTexture(cp2, blockAccess, blockStateBase, blockPos, side, icon, renderEnv)) == null) continue;
                return newIcon;
            }
        }
        return icon;
    }

    public static int getSide(EnumFacing facing) {
        if (facing == null) {
            return -1;
        }
        switch (NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[facing.ordinal()]) {
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

    private static EnumFacing getFacing(int side) {
        switch (side) {
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

    private static TextureAtlasSprite getConnectedTexture(ConnectedProperties cp2, IBlockAccess blockAccess, BlockStateBase blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, RenderEnv renderEnv) {
        int y2;
        int metadata;
        int vertAxis = 0;
        int metadataCheck = metadata = blockState.getMetadata();
        Block block = blockState.getBlock();
        if (block instanceof BlockRotatedPillar) {
            vertAxis = ConnectedTextures.getWoodAxis(side, metadata);
            if (cp2.getMetadataMax() <= 3) {
                metadataCheck = metadata & 3;
            }
        }
        if (block instanceof BlockQuartz) {
            vertAxis = ConnectedTextures.getQuartzAxis(side, metadata);
            if (cp2.getMetadataMax() <= 2 && metadataCheck > 2) {
                metadataCheck = 2;
            }
        }
        if (!cp2.matchesBlock(blockState.getBlockId(), metadataCheck)) {
            return null;
        }
        if (side >= 0 && cp2.faces != 63) {
            y2 = side;
            if (vertAxis != 0) {
                y2 = ConnectedTextures.fixSideByAxis(side, vertAxis);
            }
            if ((1 << y2 & cp2.faces) == 0) {
                return null;
            }
        }
        if ((y2 = blockPos.getY()) >= cp2.minHeight && y2 <= cp2.maxHeight) {
            BiomeGenBase blockBiome;
            if (cp2.biomes != null && !cp2.matchesBiome(blockBiome = blockAccess.getBiomeGenForCoords(blockPos))) {
                return null;
            }
            switch (cp2.method) {
                case 1: {
                    return ConnectedTextures.getConnectedTextureCtm(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata, renderEnv);
                }
                case 2: {
                    return ConnectedTextures.getConnectedTextureHorizontal(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
                }
                case 3: {
                    return ConnectedTextures.getConnectedTextureTop(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
                }
                case 4: {
                    return ConnectedTextures.getConnectedTextureRandom(cp2, blockPos, side);
                }
                case 5: {
                    return ConnectedTextures.getConnectedTextureRepeat(cp2, blockPos, side);
                }
                case 6: {
                    return ConnectedTextures.getConnectedTextureVertical(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
                }
                case 7: {
                    return ConnectedTextures.getConnectedTextureFixed(cp2);
                }
                case 8: {
                    return ConnectedTextures.getConnectedTextureHorizontalVertical(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
                }
                case 9: {
                    return ConnectedTextures.getConnectedTextureVerticalHorizontal(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
                }
            }
            return null;
        }
        return null;
    }

    private static int fixSideByAxis(int side, int vertAxis) {
        switch (vertAxis) {
            case 0: {
                return side;
            }
            case 1: {
                switch (side) {
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
                return side;
            }
            case 2: {
                switch (side) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    default: {
                        return side;
                    }
                    case 4: {
                        return 1;
                    }
                    case 5: 
                }
                return 0;
            }
        }
        return side;
    }

    private static int getWoodAxis(int side, int metadata) {
        int orient = (metadata & 12) >> 2;
        switch (orient) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
        }
        return 0;
    }

    private static int getQuartzAxis(int side, int metadata) {
        switch (metadata) {
            case 3: {
                return 2;
            }
            case 4: {
                return 1;
            }
        }
        return 0;
    }

    private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties cp2, BlockPos blockPos, int side) {
        if (cp2.tileIcons.length == 1) {
            return cp2.tileIcons[0];
        }
        int face = side / cp2.symmetry * cp2.symmetry;
        int rand = Config.getRandom(blockPos, face) & Integer.MAX_VALUE;
        int index = 0;
        if (cp2.weights == null) {
            index = rand % cp2.tileIcons.length;
        } else {
            int randWeight = rand % cp2.sumAllWeights;
            int[] sumWeights = cp2.sumWeights;
            for (int i2 = 0; i2 < sumWeights.length; ++i2) {
                if (randWeight >= sumWeights[i2]) continue;
                index = i2;
                break;
            }
        }
        return cp2.tileIcons[index];
    }

    private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties cp2) {
        return cp2.tileIcons[0];
    }

    private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties cp2, BlockPos blockPos, int side) {
        if (cp2.tileIcons.length == 1) {
            return cp2.tileIcons[0];
        }
        int x2 = blockPos.getX();
        int y2 = blockPos.getY();
        int z2 = blockPos.getZ();
        int nx2 = 0;
        int ny = 0;
        switch (side) {
            case 0: {
                nx2 = x2;
                ny = z2;
                break;
            }
            case 1: {
                nx2 = x2;
                ny = z2;
                break;
            }
            case 2: {
                nx2 = -x2 - 1;
                ny = -y2;
                break;
            }
            case 3: {
                nx2 = x2;
                ny = -y2;
                break;
            }
            case 4: {
                nx2 = z2;
                ny = -y2;
                break;
            }
            case 5: {
                nx2 = -z2 - 1;
                ny = -y2;
            }
        }
        ny %= cp2.height;
        if ((nx2 %= cp2.width) < 0) {
            nx2 += cp2.width;
        }
        if (ny < 0) {
            ny += cp2.height;
        }
        int index = ny * cp2.width + nx2;
        return cp2.tileIcons[index];
    }

    private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties cp2, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata, RenderEnv renderEnv) {
        boolean[] borders = renderEnv.getBorderFlags();
        switch (side) {
            case 0: {
                borders[0] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                break;
            }
            case 1: {
                borders[0] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                break;
            }
            case 2: {
                borders[0] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                break;
            }
            case 3: {
                borders[0] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                break;
            }
            case 4: {
                borders[0] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                break;
            }
            case 5: {
                borders[0] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                borders[1] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                borders[2] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                borders[3] = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
            }
        }
        int index = 0;
        if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
            index = 3;
        } else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 1;
        } else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 12;
        } else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 36;
        } else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 2;
        } else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 24;
        } else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 15;
        } else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 39;
        } else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 13;
        } else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 37;
        } else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 25;
        } else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 27;
        } else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 38;
        } else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 14;
        } else if (borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 26;
        }
        if (index == 0) {
            return cp2.tileIcons[index];
        }
        if (!Config.isConnectedTexturesFancy()) {
            return cp2.tileIcons[index];
        }
        switch (side) {
            case 0: {
                borders[0] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetNorth(), side, icon, metadata);
                borders[1] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetNorth(), side, icon, metadata);
                borders[2] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetSouth(), side, icon, metadata);
                borders[3] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetSouth(), side, icon, metadata);
                break;
            }
            case 1: {
                borders[0] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetSouth(), side, icon, metadata);
                borders[1] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetSouth(), side, icon, metadata);
                borders[2] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetNorth(), side, icon, metadata);
                borders[3] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetNorth(), side, icon, metadata);
                break;
            }
            case 2: {
                borders[0] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetDown(), side, icon, metadata);
                borders[1] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetDown(), side, icon, metadata);
                borders[2] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetUp(), side, icon, metadata);
                borders[3] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetUp(), side, icon, metadata);
                break;
            }
            case 3: {
                borders[0] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetDown(), side, icon, metadata);
                borders[1] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetDown(), side, icon, metadata);
                borders[2] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast().offsetUp(), side, icon, metadata);
                borders[3] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest().offsetUp(), side, icon, metadata);
                break;
            }
            case 4: {
                borders[0] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown().offsetSouth(), side, icon, metadata);
                borders[1] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown().offsetNorth(), side, icon, metadata);
                borders[2] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp().offsetSouth(), side, icon, metadata);
                borders[3] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp().offsetNorth(), side, icon, metadata);
                break;
            }
            case 5: {
                borders[0] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown().offsetNorth(), side, icon, metadata);
                borders[1] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown().offsetSouth(), side, icon, metadata);
                borders[2] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp().offsetNorth(), side, icon, metadata);
                borders[3] = !ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp().offsetSouth(), side, icon, metadata);
            }
        }
        if (index == 13 && borders[0]) {
            index = 4;
        } else if (index == 15 && borders[1]) {
            index = 5;
        } else if (index == 37 && borders[2]) {
            index = 16;
        } else if (index == 39 && borders[3]) {
            index = 17;
        } else if (index == 14 && borders[0] && borders[1]) {
            index = 7;
        } else if (index == 25 && borders[0] && borders[2]) {
            index = 6;
        } else if (index == 27 && borders[3] && borders[1]) {
            index = 19;
        } else if (index == 38 && borders[3] && borders[2]) {
            index = 18;
        } else if (index == 14 && !borders[0] && borders[1]) {
            index = 31;
        } else if (index == 25 && borders[0] && !borders[2]) {
            index = 30;
        } else if (index == 27 && !borders[3] && borders[1]) {
            index = 41;
        } else if (index == 38 && borders[3] && !borders[2]) {
            index = 40;
        } else if (index == 14 && borders[0] && !borders[1]) {
            index = 29;
        } else if (index == 25 && !borders[0] && borders[2]) {
            index = 28;
        } else if (index == 27 && borders[3] && !borders[1]) {
            index = 43;
        } else if (index == 38 && !borders[3] && borders[2]) {
            index = 42;
        } else if (index == 26 && borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 46;
        } else if (index == 26 && !borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 9;
        } else if (index == 26 && borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 21;
        } else if (index == 26 && borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 8;
        } else if (index == 26 && borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 20;
        } else if (index == 26 && borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 11;
        } else if (index == 26 && !borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 22;
        } else if (index == 26 && !borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 23;
        } else if (index == 26 && borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 10;
        } else if (index == 26 && borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 34;
        } else if (index == 26 && !borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 35;
        } else if (index == 26 && borders[0] && !borders[1] && !borders[2] && !borders[3]) {
            index = 32;
        } else if (index == 26 && !borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 33;
        } else if (index == 26 && !borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 44;
        } else if (index == 26 && !borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 45;
        }
        return cp2.tileIcons[index];
    }

    private static boolean isNeighbour(ConnectedProperties cp2, IBlockAccess iblockaccess, IBlockState blockState, BlockPos blockPos, int side, TextureAtlasSprite icon, int metadata) {
        IBlockState neighbourState = iblockaccess.getBlockState(blockPos);
        if (blockState == neighbourState) {
            return true;
        }
        if (cp2.connect == 2) {
            if (neighbourState == null) {
                return false;
            }
            if (neighbourState == AIR_DEFAULT_STATE) {
                return false;
            }
            TextureAtlasSprite neighbourIcon = ConnectedTextures.getNeighbourIcon(iblockaccess, blockPos, neighbourState, side);
            return neighbourIcon == icon;
        }
        return cp2.connect == 3 ? (neighbourState == null ? false : (neighbourState == AIR_DEFAULT_STATE ? false : neighbourState.getBlock().getMaterial() == blockState.getBlock().getMaterial())) : false;
    }

    private static TextureAtlasSprite getNeighbourIcon(IBlockAccess iblockaccess, BlockPos blockPos, IBlockState neighbourState, int side) {
        neighbourState = neighbourState.getBlock().getActualState(neighbourState, iblockaccess, blockPos);
        IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().func_175023_a().func_178125_b(neighbourState);
        if (model == null) {
            return null;
        }
        EnumFacing facing = ConnectedTextures.getFacing(side);
        List quads = model.func_177551_a(facing);
        if (quads.size() > 0) {
            BakedQuad var10 = (BakedQuad)quads.get(0);
            return var10.getSprite();
        }
        List quadsGeneral = model.func_177550_a();
        for (int i2 = 0; i2 < quadsGeneral.size(); ++i2) {
            BakedQuad quad = (BakedQuad)quadsGeneral.get(i2);
            if (quad.getFace() != facing) continue;
            return quad.getSprite();
        }
        return null;
    }

    private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties cp2, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
        boolean left = false;
        boolean right = false;
        block0 : switch (vertAxis) {
            case 0: {
                switch (side) {
                    case 0: 
                    case 1: {
                        return null;
                    }
                    case 2: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                        break block0;
                    }
                    case 3: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                        break block0;
                    }
                    case 4: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                        break block0;
                    }
                    case 5: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                    }
                }
                break;
            }
            case 1: {
                switch (side) {
                    case 0: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                        break block0;
                    }
                    case 1: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
                        break block0;
                    }
                    case 2: 
                    case 3: {
                        return null;
                    }
                    case 4: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                        break block0;
                    }
                    case 5: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                    }
                }
                break;
            }
            case 2: {
                switch (side) {
                    case 0: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                        break block0;
                    }
                    case 1: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                        break block0;
                    }
                    case 2: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                        break block0;
                    }
                    case 3: {
                        left = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                        right = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                        break block0;
                    }
                    case 4: 
                    case 5: {
                        return null;
                    }
                }
            }
        }
        boolean index = true;
        int index1 = left ? (right ? 1 : 2) : (right ? 0 : 3);
        return cp2.tileIcons[index1];
    }

    private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties cp2, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
        boolean bottom = false;
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                bottom = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetDown(), side, icon, metadata);
                top = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                bottom = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                top = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetNorth(), side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                bottom = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetWest(), side, icon, metadata);
                top = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
            }
        }
        boolean index = true;
        int index1 = bottom ? (top ? 1 : 2) : (top ? 0 : 3);
        return cp2.tileIcons[index1];
    }

    private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties cp2, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
        TextureAtlasSprite[] tileIcons = cp2.tileIcons;
        TextureAtlasSprite iconH = ConnectedTextures.getConnectedTextureHorizontal(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
            return iconH;
        }
        TextureAtlasSprite iconV = ConnectedTextures.getConnectedTextureVertical(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        return iconV == tileIcons[0] ? tileIcons[4] : (iconV == tileIcons[1] ? tileIcons[5] : (iconV == tileIcons[2] ? tileIcons[6] : iconV));
    }

    private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties cp2, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
        TextureAtlasSprite[] tileIcons = cp2.tileIcons;
        TextureAtlasSprite iconV = ConnectedTextures.getConnectedTextureVertical(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
            return iconV;
        }
        TextureAtlasSprite iconH = ConnectedTextures.getConnectedTextureHorizontal(cp2, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        return iconH == tileIcons[0] ? tileIcons[4] : (iconH == tileIcons[1] ? tileIcons[5] : (iconH == tileIcons[2] ? tileIcons[6] : iconH));
    }

    private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties cp2, IBlockAccess blockAccess, IBlockState blockState, BlockPos blockPos, int vertAxis, int side, TextureAtlasSprite icon, int metadata) {
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                top = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetUp(), side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                top = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetSouth(), side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                top = ConnectedTextures.isNeighbour(cp2, blockAccess, blockState, blockPos.offsetEast(), side, icon, metadata);
            }
        }
        return top ? cp2.tileIcons[0] : null;
    }

    public static void updateIcons(TextureMap textureMap) {
        blockProperties = null;
        tileProperties = null;
        spriteQuadMaps = null;
        if (Config.isConnectedTextures()) {
            IResourcePack[] rps = Config.getResourcePacks();
            for (int locEmpty = rps.length - 1; locEmpty >= 0; --locEmpty) {
                IResourcePack rp2 = rps[locEmpty];
                ConnectedTextures.updateIcons(textureMap, rp2);
            }
            ConnectedTextures.updateIcons(textureMap, Config.getDefaultResourcePack());
            ResourceLocation var4 = new ResourceLocation("mcpatcher/ctm/default/empty");
            emptySprite = textureMap.func_174942_a(var4);
            spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + 1];
            if (blockProperties.length <= 0) {
                blockProperties = null;
            }
            if (tileProperties.length <= 0) {
                tileProperties = null;
            }
        }
    }

    private static void updateIconEmpty(TextureMap textureMap) {
    }

    public static void updateIcons(TextureMap textureMap, IResourcePack rp2) {
        Object[] names = ResUtils.collectFiles(rp2, "mcpatcher/ctm/", ".properties", ConnectedTextures.getDefaultCtmPaths());
        Arrays.sort(names);
        List tileList = ConnectedTextures.makePropertyList(tileProperties);
        List blockList = ConnectedTextures.makePropertyList(blockProperties);
        for (int i2 = 0; i2 < names.length; ++i2) {
            Object name = names[i2];
            Config.dbg("ConnectedTextures: " + (String)name);
            try {
                ResourceLocation e2 = new ResourceLocation((String)name);
                InputStream in2 = rp2.getInputStream(e2);
                if (in2 == null) {
                    Config.warn("ConnectedTextures file not found: " + (String)name);
                    continue;
                }
                Properties props = new Properties();
                props.load(in2);
                ConnectedProperties cp2 = new ConnectedProperties(props, (String)name);
                if (!cp2.isValid((String)name)) continue;
                cp2.updateIcons(textureMap);
                ConnectedTextures.addToTileList(cp2, tileList);
                ConnectedTextures.addToBlockList(cp2, blockList);
                continue;
            }
            catch (FileNotFoundException var11) {
                Config.warn("ConnectedTextures file not found: " + (String)name);
                continue;
            }
            catch (Exception var12) {
                var12.printStackTrace();
            }
        }
        blockProperties = ConnectedTextures.propertyListToArray(blockList);
        tileProperties = ConnectedTextures.propertyListToArray(tileList);
        multipass = ConnectedTextures.detectMultipass();
        Config.dbg("Multipass connected textures: " + multipass);
    }

    private static List makePropertyList(ConnectedProperties[][] propsArr) {
        ArrayList<ArrayList<ConnectedProperties>> list = new ArrayList<ArrayList<ConnectedProperties>>();
        if (propsArr != null) {
            for (int i2 = 0; i2 < propsArr.length; ++i2) {
                ConnectedProperties[] props = propsArr[i2];
                ArrayList<ConnectedProperties> propList = null;
                if (props != null) {
                    propList = new ArrayList<ConnectedProperties>(Arrays.asList(props));
                }
                list.add(propList);
            }
        }
        return list;
    }

    private static boolean detectMultipass() {
        ConnectedProperties[] matchIconSet;
        int props;
        ArrayList<ConnectedProperties> propList = new ArrayList<ConnectedProperties>();
        for (props = 0; props < tileProperties.length; ++props) {
            matchIconSet = tileProperties[props];
            if (matchIconSet == null) continue;
            propList.addAll(Arrays.asList(matchIconSet));
        }
        for (props = 0; props < blockProperties.length; ++props) {
            matchIconSet = blockProperties[props];
            if (matchIconSet == null) continue;
            propList.addAll(Arrays.asList(matchIconSet));
        }
        ConnectedProperties[] var6 = propList.toArray(new ConnectedProperties[propList.size()]);
        HashSet<TextureAtlasSprite> var7 = new HashSet<TextureAtlasSprite>();
        HashSet<TextureAtlasSprite> tileIconSet = new HashSet<TextureAtlasSprite>();
        for (int i2 = 0; i2 < var6.length; ++i2) {
            ConnectedProperties cp2 = var6[i2];
            if (cp2.matchTileIcons != null) {
                var7.addAll(Arrays.asList(cp2.matchTileIcons));
            }
            if (cp2.tileIcons == null) continue;
            tileIconSet.addAll(Arrays.asList(cp2.tileIcons));
        }
        var7.retainAll(tileIconSet);
        return !var7.isEmpty();
    }

    private static ConnectedProperties[][] propertyListToArray(List list) {
        ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
        for (int i2 = 0; i2 < list.size(); ++i2) {
            List subList = (List)list.get(i2);
            if (subList == null) continue;
            ConnectedProperties[] subArr = subList.toArray(new ConnectedProperties[subList.size()]);
            propArr[i2] = subArr;
        }
        return propArr;
    }

    private static void addToTileList(ConnectedProperties cp2, List tileList) {
        if (cp2.matchTileIcons != null) {
            for (int i2 = 0; i2 < cp2.matchTileIcons.length; ++i2) {
                TextureAtlasSprite icon = cp2.matchTileIcons[i2];
                if (!(icon instanceof TextureAtlasSprite)) {
                    Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + icon + ", name: " + icon.getIconName());
                    continue;
                }
                int tileId = icon.getIndexInMap();
                if (tileId < 0) {
                    Config.warn("Invalid tile ID: " + tileId + ", icon: " + icon.getIconName());
                    continue;
                }
                ConnectedTextures.addToList(cp2, tileList, tileId);
            }
        }
    }

    private static void addToBlockList(ConnectedProperties cp2, List blockList) {
        if (cp2.matchBlocks != null) {
            for (int i2 = 0; i2 < cp2.matchBlocks.length; ++i2) {
                int blockId = cp2.matchBlocks[i2].getBlockId();
                if (blockId < 0) {
                    Config.warn("Invalid block ID: " + blockId);
                    continue;
                }
                ConnectedTextures.addToList(cp2, blockList, blockId);
            }
        }
    }

    private static void addToList(ConnectedProperties cp2, List list, int id2) {
        while (id2 >= list.size()) {
            list.add(null);
        }
        ArrayList<ConnectedProperties> subList = (ArrayList<ConnectedProperties>)list.get(id2);
        if (subList == null) {
            subList = new ArrayList<ConnectedProperties>();
            list.set(id2, subList);
        }
        subList.add(cp2);
    }

    private static String[] getDefaultCtmPaths() {
        ArrayList<String> list = new ArrayList<String>();
        String defPath = "mcpatcher/ctm/default/";
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
            list.add(String.valueOf(defPath) + "glass.properties");
            list.add(String.valueOf(defPath) + "glasspane.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
            list.add(String.valueOf(defPath) + "bookshelf.properties");
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
            list.add(String.valueOf(defPath) + "sandstone.properties");
        }
        String[] colors = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
        for (int paths = 0; paths < colors.length; ++paths) {
            String color = colors[paths];
            if (!Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + color + ".png"))) continue;
            list.add(String.valueOf(defPath) + paths + "_glass_" + color + "/glass_" + color + ".properties");
            list.add(String.valueOf(defPath) + paths + "_glass_" + color + "/glass_pane_" + color + ".properties");
        }
        String[] var5 = list.toArray(new String[list.size()]);
        return var5;
    }

    public static int getPaneTextureIndex(boolean linkP, boolean linkN, boolean linkYp, boolean linkYn) {
        return linkN && linkP ? (linkYp ? (linkYn ? 34 : 50) : (linkYn ? 18 : 2)) : (linkN && !linkP ? (linkYp ? (linkYn ? 35 : 51) : (linkYn ? 19 : 3)) : (!linkN && linkP ? (linkYp ? (linkYn ? 33 : 49) : (linkYn ? 17 : 1)) : (linkYp ? (linkYn ? 32 : 48) : (linkYn ? 16 : 0))));
    }

    public static int getReversePaneTextureIndex(int texNum) {
        int col = texNum % 16;
        return col == 1 ? texNum + 2 : (col == 3 ? texNum - 2 : texNum);
    }

    public static TextureAtlasSprite getCtmTexture(ConnectedProperties cp2, int ctmIndex, TextureAtlasSprite icon) {
        if (cp2.method != 1) {
            return icon;
        }
        if (ctmIndex >= 0 && ctmIndex < ctmIndexes.length) {
            int index = ctmIndexes[ctmIndex];
            TextureAtlasSprite[] ctmIcons = cp2.tileIcons;
            return index >= 0 && index < ctmIcons.length ? ctmIcons[index] : icon;
        }
        return icon;
    }

    static class NamelessClass379831726 {
        static final int[] $SwitchMap$net$minecraft$util$EnumFacing = new int[EnumFacing.values().length];

        static {
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.NORTH.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                NamelessClass379831726.$SwitchMap$net$minecraft$util$EnumFacing[EnumFacing.SOUTH.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }

        NamelessClass379831726() {
        }
    }

}

