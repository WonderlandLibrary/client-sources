package HORIZON-6-0-SKIDPROTECTION;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.File;
import java.util.HashSet;
import java.util.Collection;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Arrays;
import java.util.List;
import java.util.IdentityHashMap;
import java.util.Map;

public class ConnectedTextures
{
    private static Map[] Â;
    private static ConnectedProperties[][] Ý;
    private static ConnectedProperties[][] Ø­áŒŠá;
    private static boolean Âµá€;
    private static final int Ó = 0;
    private static final int à = 1;
    private static final int Ø = 2;
    private static final int áŒŠÆ = 3;
    private static final int áˆºÑ¢Õ = 4;
    private static final int ÂµÈ = 5;
    private static final int á = 0;
    private static final int ˆÏ­ = 1;
    private static final int £á = 2;
    private static final String[] Å;
    private static final int[] £à;
    public static final IBlockState HorizonCode_Horizon_È;
    private static TextureAtlasSprite µà;
    
    static {
        ConnectedTextures.Â = null;
        ConnectedTextures.Ý = null;
        ConnectedTextures.Ø­áŒŠá = null;
        ConnectedTextures.Âµá€ = false;
        Å = new String[] { "", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        £à = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0 };
        HorizonCode_Horizon_È = Blocks.Â.¥à();
        ConnectedTextures.µà = null;
    }
    
    public static synchronized BakedQuad HorizonCode_Horizon_È(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final BakedQuad quad, final RenderEnv renderEnv) {
        final TextureAtlasSprite spriteIn = quad.HorizonCode_Horizon_È();
        if (spriteIn == null) {
            return quad;
        }
        final Block block = blockState.Ý();
        if (block instanceof BlockPane && spriteIn.áŒŠÆ().startsWith("minecraft:blocks/glass_pane_top")) {
            final IBlockState side = blockAccess.Â(blockPos.HorizonCode_Horizon_È(quad.Âµá€()));
            if (side == blockState) {
                return HorizonCode_Horizon_È(ConnectedTextures.µà, block, blockState, quad);
            }
        }
        final EnumFacing side2 = quad.Âµá€();
        final TextureAtlasSprite sprite = HorizonCode_Horizon_È(blockAccess, blockState, blockPos, side2, spriteIn, renderEnv);
        return (sprite == spriteIn) ? quad : HorizonCode_Horizon_È(sprite, block, blockState, quad);
    }
    
    private static BakedQuad HorizonCode_Horizon_È(final TextureAtlasSprite sprite, final Block block, final IBlockState blockState, final BakedQuad quadIn) {
        if (ConnectedTextures.Â == null) {
            return quadIn;
        }
        final int spriteIndex = sprite.£á();
        if (spriteIndex >= 0 && spriteIndex < ConnectedTextures.Â.length) {
            Object quadMap = ConnectedTextures.Â[spriteIndex];
            if (quadMap == null) {
                quadMap = new IdentityHashMap(1);
                ConnectedTextures.Â[spriteIndex] = (Map)quadMap;
            }
            BakedQuad quad = ((Map)quadMap).get(quadIn);
            if (quad == null) {
                quad = HorizonCode_Horizon_È(quadIn, sprite);
                ((Map)quadMap).put(quadIn, quad);
            }
            return quad;
        }
        return quadIn;
    }
    
    private static BakedQuad HorizonCode_Horizon_È(final BakedQuad quad, final TextureAtlasSprite sprite) {
        final int[] data = quad.Â().clone();
        final TextureAtlasSprite spriteFrom = quad.HorizonCode_Horizon_È();
        for (int bq = 0; bq < 4; ++bq) {
            HorizonCode_Horizon_È(data, bq, spriteFrom, sprite);
        }
        final BakedQuad var5 = new BakedQuad(data, quad.Ø­áŒŠá(), quad.Âµá€(), sprite);
        return var5;
    }
    
    private static void HorizonCode_Horizon_È(final int[] data, final int vertex, final TextureAtlasSprite spriteFrom, final TextureAtlasSprite spriteTo) {
        final int pos = 7 * vertex;
        final float u = Float.intBitsToFloat(data[pos + 4]);
        final float v = Float.intBitsToFloat(data[pos + 4 + 1]);
        final double su16 = spriteFrom.HorizonCode_Horizon_È(u);
        final double sv16 = spriteFrom.Â(v);
        data[pos + 4] = Float.floatToRawIntBits(spriteTo.HorizonCode_Horizon_È(su16));
        data[pos + 4 + 1] = Float.floatToRawIntBits(spriteTo.Â(sv16));
    }
    
    private static TextureAtlasSprite HorizonCode_Horizon_È(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing side, final TextureAtlasSprite icon, final RenderEnv renderEnv) {
        final TextureAtlasSprite newIcon = HorizonCode_Horizon_È(blockAccess, blockState, blockPos, side, icon, true, renderEnv);
        if (!ConnectedTextures.Âµá€) {
            return newIcon;
        }
        if (newIcon == icon) {
            return newIcon;
        }
        TextureAtlasSprite mpIcon = newIcon;
        for (int i = 0; i < 3; ++i) {
            final TextureAtlasSprite newMpIcon = HorizonCode_Horizon_È(blockAccess, blockState, blockPos, side, mpIcon, false, renderEnv);
            if (newMpIcon == mpIcon) {
                break;
            }
            mpIcon = newMpIcon;
        }
        return mpIcon;
    }
    
    public static TextureAtlasSprite HorizonCode_Horizon_È(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing facing, final TextureAtlasSprite icon, final boolean checkBlocks, final RenderEnv renderEnv) {
        final Block block = blockState.Ý();
        if (ConnectedTextures.Ø­áŒŠá != null) {
            final int blockId = icon.£á();
            if (blockId >= 0 && blockId < ConnectedTextures.Ø­áŒŠá.length) {
                final ConnectedProperties[] cps = ConnectedTextures.Ø­áŒŠá[blockId];
                if (cps != null) {
                    final int metadata = renderEnv.Â();
                    final int side = HorizonCode_Horizon_È(facing);
                    for (int i = 0; i < cps.length; ++i) {
                        final ConnectedProperties cp = cps[i];
                        if (cp != null) {
                            final int newIcon = renderEnv.HorizonCode_Horizon_È();
                            if (cp.HorizonCode_Horizon_È(newIcon)) {
                                final TextureAtlasSprite newIcon2 = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos, side, icon, metadata, renderEnv);
                                if (newIcon2 != null) {
                                    return newIcon2;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (ConnectedTextures.Ý != null && checkBlocks) {
            final int blockId = renderEnv.HorizonCode_Horizon_È();
            if (blockId >= 0 && blockId < ConnectedTextures.Ý.length) {
                final ConnectedProperties[] cps = ConnectedTextures.Ý[blockId];
                if (cps != null) {
                    final int metadata = renderEnv.Â();
                    final int side = HorizonCode_Horizon_È(facing);
                    for (int i = 0; i < cps.length; ++i) {
                        final ConnectedProperties cp = cps[i];
                        if (cp != null && cp.HorizonCode_Horizon_È(icon)) {
                            final TextureAtlasSprite var16 = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos, side, icon, metadata, renderEnv);
                            if (var16 != null) {
                                return var16;
                            }
                        }
                    }
                }
            }
        }
        return icon;
    }
    
    private static int HorizonCode_Horizon_È(final EnumFacing facing) {
        if (facing == null) {
            return -1;
        }
        switch (ConnectedTextures.HorizonCode_Horizon_È.HorizonCode_Horizon_È[facing.ordinal()]) {
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
            default: {
                return -1;
            }
        }
    }
    
    private static EnumFacing Â(final int side) {
        switch (side) {
            case 0: {
                return EnumFacing.HorizonCode_Horizon_È;
            }
            case 1: {
                return EnumFacing.Â;
            }
            case 2: {
                return EnumFacing.Ý;
            }
            case 3: {
                return EnumFacing.Ø­áŒŠá;
            }
            case 4: {
                return EnumFacing.Âµá€;
            }
            case 5: {
                return EnumFacing.Ó;
            }
            default: {
                return EnumFacing.Â;
            }
        }
    }
    
    private static TextureAtlasSprite HorizonCode_Horizon_È(final ConnectedProperties cp, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int side, final TextureAtlasSprite icon, final int metadata, final RenderEnv renderEnv) {
        final int y = blockPos.Â();
        if (y < cp.ÂµÈ || y > cp.á) {
            return null;
        }
        if (cp.áˆºÑ¢Õ != null) {
            final BiomeGenBase vertAxis = blockAccess.Ý(blockPos);
            boolean metadataCheck = false;
            for (int block = 0; block < cp.áˆºÑ¢Õ.length; ++block) {
                final BiomeGenBase mds = cp.áˆºÑ¢Õ[block];
                if (vertAxis == mds) {
                    metadataCheck = true;
                    break;
                }
            }
            if (!metadataCheck) {
                return null;
            }
        }
        int var15 = 0;
        int var16 = metadata;
        final Block var17 = blockState.Ý();
        if (var17 instanceof BlockRotatedPillar) {
            var15 = Â(side, metadata);
            var16 = (metadata & 0x3);
        }
        if (var17 instanceof BlockQuartz) {
            var15 = Ý(side, metadata);
            if (var16 > 2) {
                var16 = 2;
            }
        }
        if (side >= 0 && cp.Ø != 63) {
            int var18 = side;
            if (var15 != 0) {
                var18 = HorizonCode_Horizon_È(side, var15);
            }
            if ((1 << var18 & cp.Ø) == 0x0) {
                return null;
            }
        }
        if (cp.áŒŠÆ != null) {
            final int[] var19 = cp.áŒŠÆ;
            boolean metadataFound = false;
            for (int i = 0; i < var19.length; ++i) {
                if (var19[i] == var16) {
                    metadataFound = true;
                    break;
                }
            }
            if (!metadataFound) {
                return null;
            }
        }
        switch (cp.Âµá€) {
            case 1: {
                return Â(cp, blockAccess, blockState, blockPos, side, icon, metadata, renderEnv);
            }
            case 2: {
                return HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos, var15, side, icon, metadata);
            }
            case 3: {
                return Âµá€(cp, blockAccess, blockState, blockPos, var15, side, icon, metadata);
            }
            case 4: {
                return HorizonCode_Horizon_È(cp, blockPos, side);
            }
            case 5: {
                return Â(cp, blockPos, side);
            }
            case 6: {
                return Â(cp, blockAccess, blockState, blockPos, var15, side, icon, metadata);
            }
            case 7: {
                return HorizonCode_Horizon_È(cp);
            }
            case 8: {
                return Ý(cp, blockAccess, blockState, blockPos, var15, side, icon, metadata);
            }
            case 9: {
                return Ø­áŒŠá(cp, blockAccess, blockState, blockPos, var15, side, icon, metadata);
            }
            default: {
                return null;
            }
        }
    }
    
    private static int HorizonCode_Horizon_È(final int side, final int vertAxis) {
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
                    default: {
                        return side;
                    }
                }
                break;
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
                    case 5: {
                        return 0;
                    }
                }
                break;
            }
            default: {
                return side;
            }
        }
    }
    
    private static int Â(final int side, final int metadata) {
        final int orient = (metadata & 0xC) >> 2;
        switch (orient) {
            case 1: {
                return 2;
            }
            case 2: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static int Ý(final int side, final int metadata) {
        switch (metadata) {
            case 3: {
                return 2;
            }
            case 4: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }
    
    private static TextureAtlasSprite HorizonCode_Horizon_È(final ConnectedProperties cp, final BlockPos blockPos, final int side) {
        if (cp.Æ.length == 1) {
            return cp.Æ[0];
        }
        final int face = side / cp.ˆà * cp.ˆà;
        final int rand = Config.HorizonCode_Horizon_È(blockPos, face) & Integer.MAX_VALUE;
        int index = 0;
        if (cp.µà == null) {
            index = rand % cp.Æ.length;
        }
        else {
            final int randWeight = rand % cp.Ø­à;
            final int[] sumWeights = cp.¥Æ;
            for (int i = 0; i < sumWeights.length; ++i) {
                if (randWeight < sumWeights[i]) {
                    index = i;
                    break;
                }
            }
        }
        return cp.Æ[index];
    }
    
    private static TextureAtlasSprite HorizonCode_Horizon_È(final ConnectedProperties cp) {
        return cp.Æ[0];
    }
    
    private static TextureAtlasSprite Â(final ConnectedProperties cp, final BlockPos blockPos, final int side) {
        if (cp.Æ.length == 1) {
            return cp.Æ[0];
        }
        final int x = blockPos.HorizonCode_Horizon_È();
        final int y = blockPos.Â();
        final int z = blockPos.Ý();
        int nx = 0;
        int ny = 0;
        switch (side) {
            case 0: {
                nx = x;
                ny = z;
                break;
            }
            case 1: {
                nx = x;
                ny = z;
                break;
            }
            case 2: {
                nx = -x - 1;
                ny = -y;
                break;
            }
            case 3: {
                nx = x;
                ny = -y;
                break;
            }
            case 4: {
                nx = z;
                ny = -y;
                break;
            }
            case 5: {
                nx = -z - 1;
                ny = -y;
                break;
            }
        }
        nx %= cp.Å;
        ny %= cp.£à;
        if (nx < 0) {
            nx += cp.Å;
        }
        if (ny < 0) {
            ny += cp.£à;
        }
        final int index = ny * cp.Å + nx;
        return cp.Æ[index];
    }
    
    private static TextureAtlasSprite Â(final ConnectedProperties cp, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int side, final TextureAtlasSprite icon, final int metadata, final RenderEnv renderEnv) {
        final boolean[] borders = renderEnv.áŒŠÆ();
        switch (side) {
            case 0: {
                borders[0] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                borders[1] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                borders[2] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                borders[3] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                break;
            }
            case 1: {
                borders[0] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                borders[1] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                borders[2] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                borders[3] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                break;
            }
            case 2: {
                borders[0] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                borders[1] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                borders[2] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                borders[3] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                break;
            }
            case 3: {
                borders[0] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                borders[1] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                borders[2] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                borders[3] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                break;
            }
            case 4: {
                borders[0] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                borders[1] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                borders[2] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                borders[3] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                break;
            }
            case 5: {
                borders[0] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                borders[1] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                borders[2] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                borders[3] = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                break;
            }
        }
        byte index = 0;
        if (borders[0] & !borders[1] & !borders[2] & !borders[3]) {
            index = 3;
        }
        else if (!borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 1;
        }
        else if (!borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 12;
        }
        else if (!borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 36;
        }
        else if (borders[0] & borders[1] & !borders[2] & !borders[3]) {
            index = 2;
        }
        else if (!borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 24;
        }
        else if (borders[0] & !borders[1] & borders[2] & !borders[3]) {
            index = 15;
        }
        else if (borders[0] & !borders[1] & !borders[2] & borders[3]) {
            index = 39;
        }
        else if (!borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 13;
        }
        else if (!borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 37;
        }
        else if (!borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 25;
        }
        else if (borders[0] & !borders[1] & borders[2] & borders[3]) {
            index = 27;
        }
        else if (borders[0] & borders[1] & !borders[2] & borders[3]) {
            index = 38;
        }
        else if (borders[0] & borders[1] & borders[2] & !borders[3]) {
            index = 14;
        }
        else if (borders[0] & borders[1] & borders[2] & borders[3]) {
            index = 26;
        }
        if (index == 0) {
            return cp.Æ[index];
        }
        if (!Config.ŠÓ()) {
            return cp.Æ[index];
        }
        switch (side) {
            case 0: {
                borders[0] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().Ó(), side, icon, metadata);
                borders[1] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().Ó(), side, icon, metadata);
                borders[2] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().à(), side, icon, metadata);
                borders[3] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().à(), side, icon, metadata);
                break;
            }
            case 1: {
                borders[0] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().à(), side, icon, metadata);
                borders[1] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().à(), side, icon, metadata);
                borders[2] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().Ó(), side, icon, metadata);
                borders[3] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().Ó(), side, icon, metadata);
                break;
            }
            case 2: {
                borders[0] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().Âµá€(), side, icon, metadata);
                borders[1] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().Âµá€(), side, icon, metadata);
                borders[2] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().Ø­áŒŠá(), side, icon, metadata);
                borders[3] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().Ø­áŒŠá(), side, icon, metadata);
                break;
            }
            case 3: {
                borders[0] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().Âµá€(), side, icon, metadata);
                borders[1] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().Âµá€(), side, icon, metadata);
                borders[2] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ().Ø­áŒŠá(), side, icon, metadata);
                borders[3] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø().Ø­áŒŠá(), side, icon, metadata);
                break;
            }
            case 4: {
                borders[0] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€().à(), side, icon, metadata);
                borders[1] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€().Ó(), side, icon, metadata);
                borders[2] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá().à(), side, icon, metadata);
                borders[3] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá().Ó(), side, icon, metadata);
                break;
            }
            case 5: {
                borders[0] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€().Ó(), side, icon, metadata);
                borders[1] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€().à(), side, icon, metadata);
                borders[2] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá().Ó(), side, icon, metadata);
                borders[3] = !HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá().à(), side, icon, metadata);
                break;
            }
        }
        if (index == 13 && borders[0]) {
            index = 4;
        }
        else if (index == 15 && borders[1]) {
            index = 5;
        }
        else if (index == 37 && borders[2]) {
            index = 16;
        }
        else if (index == 39 && borders[3]) {
            index = 17;
        }
        else if (index == 14 && borders[0] && borders[1]) {
            index = 7;
        }
        else if (index == 25 && borders[0] && borders[2]) {
            index = 6;
        }
        else if (index == 27 && borders[3] && borders[1]) {
            index = 19;
        }
        else if (index == 38 && borders[3] && borders[2]) {
            index = 18;
        }
        else if (index == 14 && !borders[0] && borders[1]) {
            index = 31;
        }
        else if (index == 25 && borders[0] && !borders[2]) {
            index = 30;
        }
        else if (index == 27 && !borders[3] && borders[1]) {
            index = 41;
        }
        else if (index == 38 && borders[3] && !borders[2]) {
            index = 40;
        }
        else if (index == 14 && borders[0] && !borders[1]) {
            index = 29;
        }
        else if (index == 25 && !borders[0] && borders[2]) {
            index = 28;
        }
        else if (index == 27 && borders[3] && !borders[1]) {
            index = 43;
        }
        else if (index == 38 && !borders[3] && borders[2]) {
            index = 42;
        }
        else if (index == 26 && borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 46;
        }
        else if (index == 26 && !borders[0] && borders[1] && borders[2] && borders[3]) {
            index = 9;
        }
        else if (index == 26 && borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 21;
        }
        else if (index == 26 && borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 8;
        }
        else if (index == 26 && borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 20;
        }
        else if (index == 26 && borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 11;
        }
        else if (index == 26 && !borders[0] && !borders[1] && borders[2] && borders[3]) {
            index = 22;
        }
        else if (index == 26 && !borders[0] && borders[1] && !borders[2] && borders[3]) {
            index = 23;
        }
        else if (index == 26 && borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 10;
        }
        else if (index == 26 && borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 34;
        }
        else if (index == 26 && !borders[0] && borders[1] && borders[2] && !borders[3]) {
            index = 35;
        }
        else if (index == 26 && borders[0] && !borders[1] && !borders[2] && !borders[3]) {
            index = 32;
        }
        else if (index == 26 && !borders[0] && borders[1] && !borders[2] && !borders[3]) {
            index = 33;
        }
        else if (index == 26 && !borders[0] && !borders[1] && borders[2] && !borders[3]) {
            index = 44;
        }
        else if (index == 26 && !borders[0] && !borders[1] && !borders[2] && borders[3]) {
            index = 45;
        }
        return cp.Æ[index];
    }
    
    private static boolean HorizonCode_Horizon_È(final ConnectedProperties cp, final IBlockAccess iblockaccess, final IBlockState blockState, final BlockPos blockPos, final int side, final TextureAtlasSprite icon, final int metadata) {
        final IBlockState neighbourState = iblockaccess.Â(blockPos);
        if (cp.à != 2) {
            return (cp.à == 3) ? (neighbourState != null && neighbourState != ConnectedTextures.HorizonCode_Horizon_È && neighbourState.Ý().Ó() == blockState.Ý().Ó()) : (neighbourState == blockState);
        }
        if (neighbourState == null) {
            return false;
        }
        if (neighbourState == ConnectedTextures.HorizonCode_Horizon_È) {
            return false;
        }
        final TextureAtlasSprite neighbourIcon = HorizonCode_Horizon_È(neighbourState, side);
        return neighbourIcon == icon;
    }
    
    private static TextureAtlasSprite HorizonCode_Horizon_È(final IBlockState neighbourState, final int side) {
        final IBakedModel model = Minecraft.áŒŠà().Ô().HorizonCode_Horizon_È().Â(neighbourState);
        if (model == null) {
            return null;
        }
        final EnumFacing facing = Â(side);
        final List quads = model.HorizonCode_Horizon_È(facing);
        if (quads.size() > 0) {
            final BakedQuad var8 = quads.get(0);
            return var8.HorizonCode_Horizon_È();
        }
        final List quadsGeneral = model.HorizonCode_Horizon_È();
        for (int i = 0; i < quadsGeneral.size(); ++i) {
            final BakedQuad quad = quadsGeneral.get(i);
            if (quad.Âµá€() == facing) {
                return quad.HorizonCode_Horizon_È();
            }
        }
        return null;
    }
    
    private static TextureAtlasSprite HorizonCode_Horizon_È(final ConnectedProperties cp, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis, final int side, final TextureAtlasSprite icon, final int metadata) {
        boolean left = false;
        boolean right = false;
        Label_0634: {
            switch (vertAxis) {
                case 0: {
                    switch (side) {
                        case 0:
                        case 1: {
                            return null;
                        }
                        case 2: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                            break;
                        }
                        case 3: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                            break;
                        }
                        case 4: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                            break;
                        }
                        case 5: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                            break;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (side) {
                        case 0: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                            break;
                        }
                        case 1: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                            break;
                        }
                        case 2:
                        case 3: {
                            return null;
                        }
                        case 4: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                            break;
                        }
                        case 5: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (side) {
                        case 0: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                            break Label_0634;
                        }
                        case 1: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                            break Label_0634;
                        }
                        case 2: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                            break Label_0634;
                        }
                        case 3: {
                            left = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                            right = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                            break Label_0634;
                        }
                        case 4:
                        case 5: {
                            return null;
                        }
                    }
                    break;
                }
            }
        }
        final boolean index = true;
        byte index2;
        if (left) {
            if (right) {
                index2 = 1;
            }
            else {
                index2 = 2;
            }
        }
        else if (right) {
            index2 = 0;
        }
        else {
            index2 = 3;
        }
        return cp.Æ[index2];
    }
    
    private static TextureAtlasSprite Â(final ConnectedProperties cp, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis, final int side, final TextureAtlasSprite icon, final int metadata) {
        boolean bottom = false;
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                bottom = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Âµá€(), side, icon, metadata);
                top = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                bottom = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                top = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ó(), side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                bottom = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø(), side, icon, metadata);
                top = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                break;
            }
        }
        final boolean index = true;
        byte index2;
        if (bottom) {
            if (top) {
                index2 = 1;
            }
            else {
                index2 = 2;
            }
        }
        else if (top) {
            index2 = 0;
        }
        else {
            index2 = 3;
        }
        return cp.Æ[index2];
    }
    
    private static TextureAtlasSprite Ý(final ConnectedProperties cp, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis, final int side, final TextureAtlasSprite icon, final int metadata) {
        final TextureAtlasSprite[] tileIcons = cp.Æ;
        final TextureAtlasSprite iconH = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconH != null && iconH != icon && iconH != tileIcons[3]) {
            return iconH;
        }
        final TextureAtlasSprite iconV = Â(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        return (iconV == tileIcons[0]) ? tileIcons[4] : ((iconV == tileIcons[1]) ? tileIcons[5] : ((iconV == tileIcons[2]) ? tileIcons[6] : iconV));
    }
    
    private static TextureAtlasSprite Ø­áŒŠá(final ConnectedProperties cp, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis, final int side, final TextureAtlasSprite icon, final int metadata) {
        final TextureAtlasSprite[] tileIcons = cp.Æ;
        final TextureAtlasSprite iconV = Â(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        if (iconV != null && iconV != icon && iconV != tileIcons[3]) {
            return iconV;
        }
        final TextureAtlasSprite iconH = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos, vertAxis, side, icon, metadata);
        return (iconH == tileIcons[0]) ? tileIcons[4] : ((iconH == tileIcons[1]) ? tileIcons[5] : ((iconH == tileIcons[2]) ? tileIcons[6] : iconH));
    }
    
    private static TextureAtlasSprite Âµá€(final ConnectedProperties cp, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int vertAxis, final int side, final TextureAtlasSprite icon, final int metadata) {
        boolean top = false;
        switch (vertAxis) {
            case 0: {
                if (side == 1 || side == 0) {
                    return null;
                }
                top = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.Ø­áŒŠá(), side, icon, metadata);
                break;
            }
            case 1: {
                if (side == 3 || side == 2) {
                    return null;
                }
                top = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.à(), side, icon, metadata);
                break;
            }
            case 2: {
                if (side == 5 || side == 4) {
                    return null;
                }
                top = HorizonCode_Horizon_È(cp, blockAccess, blockState, blockPos.áŒŠÆ(), side, icon, metadata);
                break;
            }
        }
        return top ? cp.Æ[0] : null;
    }
    
    public static void HorizonCode_Horizon_È(final TextureMap textureMap) {
        ConnectedTextures.Ý = null;
        ConnectedTextures.Ø­áŒŠá = null;
        if (Config.ÇªÔ()) {
            final IResourcePack[] rps = Config.áŒŠ();
            for (int locEmpty = rps.length - 1; locEmpty >= 0; --locEmpty) {
                final IResourcePack rp = rps[locEmpty];
                HorizonCode_Horizon_È(textureMap, rp);
            }
            HorizonCode_Horizon_È(textureMap, Config.Ø­Âµ());
            final ResourceLocation_1975012498 var4 = new ResourceLocation_1975012498("mcpatcher/ctm/default/empty");
            ConnectedTextures.µà = textureMap.HorizonCode_Horizon_È(var4);
            ConnectedTextures.Â = new Map[textureMap.à() + 1];
        }
    }
    
    private static void Â(final TextureMap textureMap) {
    }
    
    public static void HorizonCode_Horizon_È(final TextureMap textureMap, final IResourcePack rp) {
        final String[] names = HorizonCode_Horizon_È(rp, "mcpatcher/ctm/", ".properties");
        Arrays.sort(names);
        final List tileList = HorizonCode_Horizon_È(ConnectedTextures.Ø­áŒŠá);
        final List blockList = HorizonCode_Horizon_È(ConnectedTextures.Ý);
        for (int i = 0; i < names.length; ++i) {
            final String name = names[i];
            Config.HorizonCode_Horizon_È("ConnectedTextures: " + name);
            try {
                final ResourceLocation_1975012498 e = new ResourceLocation_1975012498(name);
                final InputStream in = rp.HorizonCode_Horizon_È(e);
                if (in == null) {
                    Config.Â("ConnectedTextures file not found: " + name);
                }
                else {
                    final Properties props = new Properties();
                    props.load(in);
                    final ConnectedProperties cp = new ConnectedProperties(props, name);
                    if (cp.HorizonCode_Horizon_È(name)) {
                        cp.HorizonCode_Horizon_È(textureMap);
                        HorizonCode_Horizon_È(cp, tileList);
                        Â(cp, blockList);
                    }
                }
            }
            catch (FileNotFoundException var13) {
                Config.Â("ConnectedTextures file not found: " + name);
            }
            catch (IOException var12) {
                var12.printStackTrace();
            }
        }
        ConnectedTextures.Ý = HorizonCode_Horizon_È(blockList);
        ConnectedTextures.Ø­áŒŠá = HorizonCode_Horizon_È(tileList);
        ConnectedTextures.Âµá€ = HorizonCode_Horizon_È();
        Config.HorizonCode_Horizon_È("Multipass connected textures: " + ConnectedTextures.Âµá€);
    }
    
    private static List HorizonCode_Horizon_È(final ConnectedProperties[][] propsArr) {
        final ArrayList list = new ArrayList();
        if (propsArr != null) {
            for (int i = 0; i < propsArr.length; ++i) {
                final ConnectedProperties[] props = propsArr[i];
                ArrayList propList = null;
                if (props != null) {
                    propList = new ArrayList((Collection<? extends E>)Arrays.asList(props));
                }
                list.add(propList);
            }
        }
        return list;
    }
    
    private static boolean HorizonCode_Horizon_È() {
        final ArrayList propList = new ArrayList();
        for (int props = 0; props < ConnectedTextures.Ø­áŒŠá.length; ++props) {
            final ConnectedProperties[] matchIconSet = ConnectedTextures.Ø­áŒŠá[props];
            if (matchIconSet != null) {
                propList.addAll(Arrays.asList(matchIconSet));
            }
        }
        for (int props = 0; props < ConnectedTextures.Ý.length; ++props) {
            final ConnectedProperties[] matchIconSet = ConnectedTextures.Ý[props];
            if (matchIconSet != null) {
                propList.addAll(Arrays.asList(matchIconSet));
            }
        }
        final ConnectedProperties[] var6 = propList.toArray(new ConnectedProperties[propList.size()]);
        final HashSet var7 = new HashSet();
        final HashSet tileIconSet = new HashSet();
        for (int i = 0; i < var6.length; ++i) {
            final ConnectedProperties cp = var6[i];
            if (cp.µÕ != null) {
                var7.addAll(Arrays.asList(cp.µÕ));
            }
            if (cp.Æ != null) {
                tileIconSet.addAll(Arrays.asList(cp.Æ));
            }
        }
        var7.retainAll(tileIconSet);
        return !var7.isEmpty();
    }
    
    private static ConnectedProperties[][] HorizonCode_Horizon_È(final List list) {
        final ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            final List subList = list.get(i);
            if (subList != null) {
                final ConnectedProperties[] subArr = subList.toArray(new ConnectedProperties[subList.size()]);
                propArr[i] = subArr;
            }
        }
        return propArr;
    }
    
    private static void HorizonCode_Horizon_È(final ConnectedProperties cp, final List tileList) {
        if (cp.µÕ != null) {
            for (int i = 0; i < cp.µÕ.length; ++i) {
                final TextureAtlasSprite icon = cp.µÕ[i];
                if (!(icon instanceof TextureAtlasSprite)) {
                    Config.Â("TextureAtlasSprite is not TextureAtlasSprite: " + icon + ", name: " + icon.áŒŠÆ());
                }
                else {
                    final int tileId = icon.£á();
                    if (tileId < 0) {
                        Config.Â("Invalid tile ID: " + tileId + ", icon: " + icon.áŒŠÆ());
                    }
                    else {
                        HorizonCode_Horizon_È(cp, tileList, tileId);
                    }
                }
            }
        }
    }
    
    private static void Â(final ConnectedProperties cp, final List blockList) {
        if (cp.Ý != null) {
            for (int i = 0; i < cp.Ý.length; ++i) {
                final int blockId = cp.Ý[i];
                if (blockId < 0) {
                    Config.Â("Invalid block ID: " + blockId);
                }
                else {
                    HorizonCode_Horizon_È(cp, blockList, blockId);
                }
            }
        }
    }
    
    private static void HorizonCode_Horizon_È(final ConnectedProperties cp, final List list, final int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        Object subList = list.get(id);
        if (subList == null) {
            subList = new ArrayList();
            list.set(id, subList);
        }
        ((List)subList).add(cp);
    }
    
    private static String[] HorizonCode_Horizon_È(final IResourcePack rp, final String prefix, final String suffix) {
        if (rp instanceof DefaultResourcePack) {
            return HorizonCode_Horizon_È(rp);
        }
        if (!(rp instanceof AbstractResourcePack)) {
            return new String[0];
        }
        final AbstractResourcePack arp = (AbstractResourcePack)rp;
        final File tpFile = ResourceUtils.HorizonCode_Horizon_È(arp);
        return (tpFile == null) ? new String[0] : (tpFile.isDirectory() ? HorizonCode_Horizon_È(tpFile, "", prefix, suffix) : (tpFile.isFile() ? HorizonCode_Horizon_È(tpFile, prefix, suffix) : new String[0]));
    }
    
    private static String[] HorizonCode_Horizon_È(final IResourcePack rp) {
        final ArrayList list = new ArrayList();
        final String[] names = Â();
        for (int nameArr = 0; nameArr < names.length; ++nameArr) {
            final String name = names[nameArr];
            final ResourceLocation_1975012498 loc = new ResourceLocation_1975012498(name);
            if (rp.Â(loc)) {
                list.add(name);
            }
        }
        final String[] var6 = list.toArray(new String[list.size()]);
        return var6;
    }
    
    private static String[] Â() {
        final ArrayList list = new ArrayList();
        final String defPath = "mcpatcher/ctm/default/";
        if (Config.Ø­áŒŠá(new ResourceLocation_1975012498("textures/blocks/glass.png"))) {
            list.add(String.valueOf(defPath) + "glass.properties");
            list.add(String.valueOf(defPath) + "glasspane.properties");
        }
        if (Config.Ø­áŒŠá(new ResourceLocation_1975012498("textures/blocks/bookshelf.png"))) {
            list.add(String.valueOf(defPath) + "bookshelf.properties");
        }
        if (Config.Ø­áŒŠá(new ResourceLocation_1975012498("textures/blocks/sandstone_normal.png"))) {
            list.add(String.valueOf(defPath) + "sandstone.properties");
        }
        final String[] colors = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
        for (int paths = 0; paths < colors.length; ++paths) {
            final String color = colors[paths];
            if (Config.Ø­áŒŠá(new ResourceLocation_1975012498("textures/blocks/glass_" + color + ".png"))) {
                list.add(String.valueOf(defPath) + paths + "_glass_" + color + "/glass_" + color + ".properties");
                list.add(String.valueOf(defPath) + paths + "_glass_" + color + "/glass_pane_" + color + ".properties");
            }
        }
        final String[] var5 = list.toArray(new String[list.size()]);
        return var5;
    }
    
    private static String[] HorizonCode_Horizon_È(final File tpFile, final String basePath, final String prefix, final String suffix) {
        final ArrayList list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        final File[] files = tpFile.listFiles();
        if (files == null) {
            return new String[0];
        }
        for (int names = 0; names < files.length; ++names) {
            final File file = files[names];
            if (file.isFile()) {
                String dirPath = String.valueOf(basePath) + file.getName();
                if (dirPath.startsWith(prefixAssets)) {
                    dirPath = dirPath.substring(prefixAssets.length());
                    if (dirPath.startsWith(prefix) && dirPath.endsWith(suffix)) {
                        list.add(dirPath);
                    }
                }
            }
            else if (file.isDirectory()) {
                final String dirPath = String.valueOf(basePath) + file.getName() + "/";
                final String[] names2 = HorizonCode_Horizon_È(file, dirPath, prefix, suffix);
                for (int n = 0; n < names2.length; ++n) {
                    final String name = names2[n];
                    list.add(name);
                }
            }
        }
        final String[] var13 = list.toArray(new String[list.size()]);
        return var13;
    }
    
    private static String[] HorizonCode_Horizon_È(final File tpFile, final String prefix, final String suffix) {
        final ArrayList list = new ArrayList();
        final String prefixAssets = "assets/minecraft/";
        try {
            final ZipFile e = new ZipFile(tpFile);
            final Enumeration en = e.entries();
            while (en.hasMoreElements()) {
                final ZipEntry names = en.nextElement();
                String name = names.getName();
                if (name.startsWith(prefixAssets)) {
                    name = name.substring(prefixAssets.length());
                    if (!name.startsWith(prefix) || !name.endsWith(suffix)) {
                        continue;
                    }
                    list.add(name);
                }
            }
            e.close();
            final String[] names2 = list.toArray(new String[list.size()]);
            return names2;
        }
        catch (IOException var9) {
            var9.printStackTrace();
            return new String[0];
        }
    }
    
    public static int HorizonCode_Horizon_È(final boolean linkP, final boolean linkN, final boolean linkYp, final boolean linkYn) {
        return (linkN && linkP) ? (linkYp ? (linkYn ? 34 : 50) : (linkYn ? 18 : 2)) : ((linkN && !linkP) ? (linkYp ? (linkYn ? 35 : 51) : (linkYn ? 19 : 3)) : ((!linkN && linkP) ? (linkYp ? (linkYn ? 33 : 49) : (linkYn ? 17 : 1)) : (linkYp ? (linkYn ? 32 : 48) : (linkYn ? 16 : 0))));
    }
    
    public static int HorizonCode_Horizon_È(final int texNum) {
        final int col = texNum % 16;
        return (col == 1) ? (texNum + 2) : ((col == 3) ? (texNum - 2) : texNum);
    }
    
    public static TextureAtlasSprite HorizonCode_Horizon_È(final ConnectedProperties cp, final int ctmIndex, final TextureAtlasSprite icon) {
        if (cp.Âµá€ != 1) {
            return icon;
        }
        if (ctmIndex >= 0 && ctmIndex < ConnectedTextures.£à.length) {
            final int index = ConnectedTextures.£à[ctmIndex];
            final TextureAtlasSprite[] ctmIcons = cp.Æ;
            return (index >= 0 && index < ctmIcons.length) ? ctmIcons[index] : icon;
        }
        return icon;
    }
    
    static class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                ConnectedTextures.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                ConnectedTextures.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                ConnectedTextures.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                ConnectedTextures.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                ConnectedTextures.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                ConnectedTextures.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
