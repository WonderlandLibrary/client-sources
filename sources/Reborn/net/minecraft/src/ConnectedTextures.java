package net.minecraft.src;

import java.io.*;
import java.util.zip.*;
import java.util.*;

public class ConnectedTextures
{
    private static ConnectedProperties[][] blockProperties;
    private static ConnectedProperties[][] tileProperties;
    private static boolean multipass;
    private static boolean defaultGlassTexture;
    private static final int BOTTOM = 0;
    private static final int TOP = 1;
    private static final int EAST = 2;
    private static final int WEST = 3;
    private static final int NORTH = 4;
    private static final int SOUTH = 5;
    private static final String[] propSuffixes;
    private static final int[] ctmIndexes;
    
    static {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.multipass = false;
        ConnectedTextures.defaultGlassTexture = false;
        propSuffixes = new String[] { "", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        ctmIndexes = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 0, 0, 0, 0, 0 };
    }
    
    public static void update(final RenderEngine var0) {
    }
    
    private static ConnectedProperties[][] readConnectedProperties(final String var0, final int var1, final RenderEngine var2, final int var3) {
        return null;
    }
    
    public static Icon getConnectedTexture(final IBlockAccess var0, final Block var1, final int var2, final int var3, final int var4, final int var5, final Icon var6) {
        if (var0 == null) {
            return var6;
        }
        final Icon var7 = getConnectedTextureSingle(var0, var1, var2, var3, var4, var5, var6, true);
        if (!ConnectedTextures.multipass) {
            return var7;
        }
        if (var7 == var6) {
            return var7;
        }
        Icon var8 = var7;
        for (int var9 = 0; var9 < 3; ++var9) {
            final Icon var10 = getConnectedTextureSingle(var0, var1, var2, var3, var4, var5, var8, false);
            if (var10 == var8) {
                break;
            }
            var8 = var10;
        }
        return var8;
    }
    
    public static Icon getConnectedTextureSingle(final IBlockAccess var0, final Block var1, final int var2, final int var3, final int var4, final int var5, final Icon var6, final boolean var7) {
        if (!(var6 instanceof TextureStitched)) {
            return var6;
        }
        final TextureStitched var8 = (TextureStitched)var6;
        final int var9 = var8.getIndexInMap();
        int var10 = -1;
        if (ConnectedTextures.tileProperties != null && Tessellator.instance.defaultTexture && var9 >= 0 && var9 < ConnectedTextures.tileProperties.length) {
            final ConnectedProperties[] var11 = ConnectedTextures.tileProperties[var9];
            if (var11 != null) {
                if (var10 < 0) {
                    var10 = var0.getBlockMetadata(var2, var3, var4);
                }
                final Icon var12 = getConnectedTexture(var11, var0, var1, var2, var3, var4, var5, var8, var10);
                if (var12 != null) {
                    return var12;
                }
            }
        }
        if (ConnectedTextures.blockProperties != null && var7) {
            final int var13 = var1.blockID;
            if (var13 >= 0 && var13 < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] var14 = ConnectedTextures.blockProperties[var13];
                if (var14 != null) {
                    if (var10 < 0) {
                        var10 = var0.getBlockMetadata(var2, var3, var4);
                    }
                    final Icon var15 = getConnectedTexture(var14, var0, var1, var2, var3, var4, var5, var8, var10);
                    if (var15 != null) {
                        return var15;
                    }
                }
            }
        }
        return var6;
    }
    
    public static ConnectedProperties getConnectedProperties(final IBlockAccess var0, final Block var1, final int var2, final int var3, final int var4, final int var5, final Icon var6) {
        if (var0 == null) {
            return null;
        }
        if (!(var6 instanceof TextureStitched)) {
            return null;
        }
        final TextureStitched var7 = (TextureStitched)var6;
        final int var8 = var7.getIndexInMap();
        int var9 = -1;
        if (ConnectedTextures.tileProperties != null && Tessellator.instance.defaultTexture && var8 >= 0 && var8 < ConnectedTextures.tileProperties.length) {
            final ConnectedProperties[] var10 = ConnectedTextures.tileProperties[var8];
            if (var10 != null) {
                if (var9 < 0) {
                    var9 = var0.getBlockMetadata(var2, var3, var4);
                }
                final ConnectedProperties var11 = getConnectedProperties(var10, var0, var1, var2, var3, var4, var5, var7, var9);
                if (var11 != null) {
                    return var11;
                }
            }
        }
        if (ConnectedTextures.blockProperties != null) {
            final int var12 = var1.blockID;
            if (var12 >= 0 && var12 < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] var13 = ConnectedTextures.blockProperties[var12];
                if (var13 != null) {
                    if (var9 < 0) {
                        var9 = var0.getBlockMetadata(var2, var3, var4);
                    }
                    final ConnectedProperties var14 = getConnectedProperties(var13, var0, var1, var2, var3, var4, var5, var7, var9);
                    if (var14 != null) {
                        return var14;
                    }
                }
            }
        }
        return null;
    }
    
    private static Icon getConnectedTexture(final ConnectedProperties[] var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        for (int var9 = 0; var9 < var0.length; ++var9) {
            final ConnectedProperties var10 = var0[var9];
            if (var10 != null) {
                final Icon var11 = getConnectedTexture(var10, var1, var2, var3, var4, var5, var6, var7, var8);
                if (var11 != null) {
                    return var11;
                }
            }
        }
        return null;
    }
    
    private static ConnectedProperties getConnectedProperties(final ConnectedProperties[] var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        for (int var9 = 0; var9 < var0.length; ++var9) {
            final ConnectedProperties var10 = var0[var9];
            if (var10 != null) {
                final Icon var11 = getConnectedTexture(var10, var1, var2, var3, var4, var5, var6, var7, var8);
                if (var11 != null) {
                    return var10;
                }
            }
        }
        return null;
    }
    
    private static Icon getConnectedTexture(final ConnectedProperties var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        if (var4 < var0.minHeight || var4 > var0.maxHeight) {
            return null;
        }
        if (var0.biomes != null) {
            final BiomeGenBase var9 = var1.getBiomeGenForCoords(var3, var5);
            boolean var10 = false;
            for (int var11 = 0; var11 < var0.biomes.length; ++var11) {
                final BiomeGenBase var12 = var0.biomes[var11];
                if (var9 == var12) {
                    var10 = true;
                    break;
                }
            }
            if (!var10) {
                return null;
            }
        }
        final boolean var13 = var2 instanceof BlockLog;
        if (var6 >= 0 && var0.faces != 63) {
            int var14 = var6;
            if (var13) {
                var14 = fixWoodSide(var1, var3, var4, var5, var6, var8);
            }
            if ((1 << var14 & var0.faces) == 0x0) {
                return null;
            }
        }
        int var14 = var8;
        if (var13) {
            var14 = (var8 & 0x3);
        }
        if (var0.metadatas != null) {
            final int[] var15 = var0.metadatas;
            boolean var16 = false;
            for (int var17 = 0; var17 < var15.length; ++var17) {
                if (var15[var17] == var14) {
                    var16 = true;
                    break;
                }
            }
            if (!var16) {
                return null;
            }
        }
        switch (var0.method) {
            case 1: {
                return getConnectedTextureCtm(var0, var1, var2, var3, var4, var5, var6, var7, var8);
            }
            case 2: {
                return getConnectedTextureHorizontal(var0, var1, var2, var3, var4, var5, var6, var7, var8);
            }
            case 3: {
                return getConnectedTextureTop(var0, var1, var2, var3, var4, var5, var6, var7, var8);
            }
            case 4: {
                return getConnectedTextureRandom(var0, var3, var4, var5, var6);
            }
            case 5: {
                return getConnectedTextureRepeat(var0, var3, var4, var5, var6);
            }
            case 6: {
                return getConnectedTextureVertical(var0, var1, var2, var3, var4, var5, var6, var7, var8);
            }
            case 7: {
                return getConnectedTextureFixed(var0);
            }
            default: {
                return null;
            }
        }
    }
    
    private static int fixWoodSide(final IBlockAccess var0, final int var1, final int var2, final int var3, final int var4, final int var5) {
        final int var6 = (var5 & 0xC) >> 2;
        switch (var6) {
            case 0: {
                return var4;
            }
            case 1: {
                switch (var4) {
                    case 0: {
                        return 4;
                    }
                    case 1: {
                        return 5;
                    }
                    default: {
                        return var4;
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
            case 2: {
                switch (var4) {
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
                        return var4;
                    }
                }
                break;
            }
            case 3: {
                return 2;
            }
            default: {
                return var4;
            }
        }
    }
    
    private static Icon getConnectedTextureRandom(final ConnectedProperties var0, final int var1, final int var2, final int var3, final int var4) {
        if (var0.tileIcons.length == 1) {
            return var0.tileIcons[0];
        }
        final int var5 = var4 / var0.symmetry * var0.symmetry;
        final int var6 = Config.getRandom(var1, var2, var3, var5) & Integer.MAX_VALUE;
        int var7 = 0;
        if (var0.weights == null) {
            var7 = var6 % var0.tileIcons.length;
        }
        else {
            final int var8 = var6 % var0.sumAllWeights;
            final int[] var9 = var0.sumWeights;
            for (int var10 = 0; var10 < var9.length; ++var10) {
                if (var8 < var9[var10]) {
                    var7 = var10;
                    break;
                }
            }
        }
        return var0.tileIcons[var7];
    }
    
    private static Icon getConnectedTextureFixed(final ConnectedProperties var0) {
        return var0.tileIcons[0];
    }
    
    private static Icon getConnectedTextureRepeat(final ConnectedProperties var0, final int var1, final int var2, final int var3, final int var4) {
        if (var0.tileIcons.length == 1) {
            return var0.tileIcons[0];
        }
        int var5 = 0;
        int var6 = 0;
        switch (var4) {
            case 0: {
                var5 = var1;
                var6 = var3;
                break;
            }
            case 1: {
                var5 = var1;
                var6 = var3;
                break;
            }
            case 2: {
                var5 = -var1 - 1;
                var6 = -var2;
                break;
            }
            case 3: {
                var5 = var1;
                var6 = -var2;
                break;
            }
            case 4: {
                var5 = var3;
                var6 = -var2;
                break;
            }
            case 5: {
                var5 = -var3 - 1;
                var6 = -var2;
                break;
            }
        }
        var5 %= var0.width;
        var6 %= var0.height;
        if (var5 < 0) {
            var5 += var0.width;
        }
        if (var6 < 0) {
            var6 += var0.height;
        }
        final int var7 = var6 * var0.width + var5;
        return var0.tileIcons[var7];
    }
    
    private static Icon getConnectedTextureCtm(final ConnectedProperties var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        final boolean[] var9 = new boolean[6];
        switch (var6) {
            case 0:
            case 1: {
                var9[0] = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                break;
            }
            case 2: {
                var9[0] = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
                break;
            }
            case 3: {
                var9[0] = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
                break;
            }
            case 4: {
                var9[0] = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
                break;
            }
            case 5: {
                var9[0] = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                var9[1] = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                var9[2] = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
                var9[3] = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
                break;
            }
        }
        byte var10 = 0;
        if (var9[0] & !var9[1] & !var9[2] & !var9[3]) {
            var10 = 3;
        }
        else if (!var9[0] & var9[1] & !var9[2] & !var9[3]) {
            var10 = 1;
        }
        else if (!var9[0] & !var9[1] & var9[2] & !var9[3]) {
            var10 = 12;
        }
        else if (!var9[0] & !var9[1] & !var9[2] & var9[3]) {
            var10 = 36;
        }
        else if (var9[0] & var9[1] & !var9[2] & !var9[3]) {
            var10 = 2;
        }
        else if (!var9[0] & !var9[1] & var9[2] & var9[3]) {
            var10 = 24;
        }
        else if (var9[0] & !var9[1] & var9[2] & !var9[3]) {
            var10 = 15;
        }
        else if (var9[0] & !var9[1] & !var9[2] & var9[3]) {
            var10 = 39;
        }
        else if (!var9[0] & var9[1] & var9[2] & !var9[3]) {
            var10 = 13;
        }
        else if (!var9[0] & var9[1] & !var9[2] & var9[3]) {
            var10 = 37;
        }
        else if (!var9[0] & var9[1] & var9[2] & var9[3]) {
            var10 = 25;
        }
        else if (var9[0] & !var9[1] & var9[2] & var9[3]) {
            var10 = 27;
        }
        else if (var9[0] & var9[1] & !var9[2] & var9[3]) {
            var10 = 38;
        }
        else if (var9[0] & var9[1] & var9[2] & !var9[3]) {
            var10 = 14;
        }
        else if (var9[0] & var9[1] & var9[2] & var9[3]) {
            var10 = 26;
        }
        if (!Config.isConnectedTexturesFancy()) {
            return var0.tileIcons[var10];
        }
        final boolean[] var11 = new boolean[6];
        switch (var6) {
            case 0:
            case 1: {
                var11[0] = !isNeighbour(var0, var1, var2, var3 + 1, var4, var5 + 1, var6, var7, var8);
                var11[1] = !isNeighbour(var0, var1, var2, var3 - 1, var4, var5 + 1, var6, var7, var8);
                var11[2] = !isNeighbour(var0, var1, var2, var3 + 1, var4, var5 - 1, var6, var7, var8);
                var11[3] = !isNeighbour(var0, var1, var2, var3 - 1, var4, var5 - 1, var6, var7, var8);
                break;
            }
            case 2: {
                var11[0] = !isNeighbour(var0, var1, var2, var3 - 1, var4 - 1, var5, var6, var7, var8);
                var11[1] = !isNeighbour(var0, var1, var2, var3 + 1, var4 - 1, var5, var6, var7, var8);
                var11[2] = !isNeighbour(var0, var1, var2, var3 - 1, var4 + 1, var5, var6, var7, var8);
                var11[3] = !isNeighbour(var0, var1, var2, var3 + 1, var4 + 1, var5, var6, var7, var8);
                break;
            }
            case 3: {
                var11[0] = !isNeighbour(var0, var1, var2, var3 + 1, var4 - 1, var5, var6, var7, var8);
                var11[1] = !isNeighbour(var0, var1, var2, var3 - 1, var4 - 1, var5, var6, var7, var8);
                var11[2] = !isNeighbour(var0, var1, var2, var3 + 1, var4 + 1, var5, var6, var7, var8);
                var11[3] = !isNeighbour(var0, var1, var2, var3 - 1, var4 + 1, var5, var6, var7, var8);
                break;
            }
            case 4: {
                var11[0] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 + 1, var6, var7, var8);
                var11[1] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 - 1, var6, var7, var8);
                var11[2] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 + 1, var6, var7, var8);
                var11[3] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 - 1, var6, var7, var8);
                break;
            }
            case 5: {
                var11[0] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 - 1, var6, var7, var8);
                var11[1] = !isNeighbour(var0, var1, var2, var3, var4 - 1, var5 + 1, var6, var7, var8);
                var11[2] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 - 1, var6, var7, var8);
                var11[3] = !isNeighbour(var0, var1, var2, var3, var4 + 1, var5 + 1, var6, var7, var8);
                break;
            }
        }
        if (var10 == 13 && var11[0]) {
            var10 = 4;
        }
        if (var10 == 15 && var11[1]) {
            var10 = 5;
        }
        if (var10 == 37 && var11[2]) {
            var10 = 16;
        }
        if (var10 == 39 && var11[3]) {
            var10 = 17;
        }
        if (var10 == 14 && var11[0] && var11[1]) {
            var10 = 7;
        }
        if (var10 == 25 && var11[0] && var11[2]) {
            var10 = 6;
        }
        if (var10 == 27 && var11[3] && var11[1]) {
            var10 = 19;
        }
        if (var10 == 38 && var11[3] && var11[2]) {
            var10 = 18;
        }
        if (var10 == 14 && !var11[0] && var11[1]) {
            var10 = 31;
        }
        if (var10 == 25 && var11[0] && !var11[2]) {
            var10 = 30;
        }
        if (var10 == 27 && !var11[3] && var11[1]) {
            var10 = 41;
        }
        if (var10 == 38 && var11[3] && !var11[2]) {
            var10 = 40;
        }
        if (var10 == 14 && var11[0] && !var11[1]) {
            var10 = 29;
        }
        if (var10 == 25 && !var11[0] && var11[2]) {
            var10 = 28;
        }
        if (var10 == 27 && var11[3] && !var11[1]) {
            var10 = 43;
        }
        if (var10 == 38 && !var11[3] && var11[2]) {
            var10 = 42;
        }
        if (var10 == 26 && var11[0] && var11[1] && var11[2] && var11[3]) {
            var10 = 46;
        }
        if (var10 == 26 && !var11[0] && var11[1] && var11[2] && var11[3]) {
            var10 = 9;
        }
        if (var10 == 26 && var11[0] && !var11[1] && var11[2] && var11[3]) {
            var10 = 21;
        }
        if (var10 == 26 && var11[0] && var11[1] && !var11[2] && var11[3]) {
            var10 = 8;
        }
        if (var10 == 26 && var11[0] && var11[1] && var11[2] && !var11[3]) {
            var10 = 20;
        }
        if (var10 == 26 && var11[0] && var11[1] && !var11[2] && !var11[3]) {
            var10 = 11;
        }
        if (var10 == 26 && !var11[0] && !var11[1] && var11[2] && var11[3]) {
            var10 = 22;
        }
        if (var10 == 26 && !var11[0] && var11[1] && !var11[2] && var11[3]) {
            var10 = 23;
        }
        if (var10 == 26 && var11[0] && !var11[1] && var11[2] && !var11[3]) {
            var10 = 10;
        }
        if (var10 == 26 && var11[0] && !var11[1] && !var11[2] && var11[3]) {
            var10 = 34;
        }
        if (var10 == 26 && !var11[0] && var11[1] && var11[2] && !var11[3]) {
            var10 = 35;
        }
        if (var10 == 26 && var11[0] && !var11[1] && !var11[2] && !var11[3]) {
            var10 = 32;
        }
        if (var10 == 26 && !var11[0] && var11[1] && !var11[2] && !var11[3]) {
            var10 = 33;
        }
        if (var10 == 26 && !var11[0] && !var11[1] && var11[2] && !var11[3]) {
            var10 = 44;
        }
        if (var10 == 26 && !var11[0] && !var11[1] && !var11[2] && var11[3]) {
            var10 = 45;
        }
        return var0.tileIcons[var10];
    }
    
    private static boolean isNeighbour(final ConnectedProperties var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        final int var9 = var1.getBlockId(var3, var4, var5);
        if (var0.connect == 2) {
            final Block var10 = Block.blocksList[var9];
            if (var10 == null) {
                return false;
            }
            final Icon var11 = var10.getBlockTexture(var1, var3, var4, var5, var6);
            return var11 == var7;
        }
        else {
            if (var0.connect == 3) {
                final Block var10 = Block.blocksList[var9];
                return var10 != null && var10.blockMaterial == var2.blockMaterial;
            }
            return var9 == var2.blockID && var1.getBlockMetadata(var3, var4, var5) == var8;
        }
    }
    
    private static Icon getConnectedTextureHorizontal(final ConnectedProperties var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        if (var6 != 0 && var6 != 1) {
            boolean var9 = false;
            boolean var10 = false;
            switch (var6) {
                case 2: {
                    var9 = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                    break;
                }
                case 3: {
                    var9 = isNeighbour(var0, var1, var2, var3 - 1, var4, var5, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3 + 1, var4, var5, var6, var7, var8);
                    break;
                }
                case 4: {
                    var9 = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                    break;
                }
                case 5: {
                    var9 = isNeighbour(var0, var1, var2, var3, var4, var5 + 1, var6, var7, var8);
                    var10 = isNeighbour(var0, var1, var2, var3, var4, var5 - 1, var6, var7, var8);
                    break;
                }
            }
            final boolean var11 = true;
            byte var12;
            if (var9) {
                if (var10) {
                    var12 = 1;
                }
                else {
                    var12 = 2;
                }
            }
            else if (var10) {
                var12 = 0;
            }
            else {
                var12 = 3;
            }
            return var0.tileIcons[var12];
        }
        return null;
    }
    
    private static Icon getConnectedTextureVertical(final ConnectedProperties var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        if (var6 != 0 && var6 != 1) {
            final boolean var9 = isNeighbour(var0, var1, var2, var3, var4 - 1, var5, var6, var7, var8);
            final boolean var10 = isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8);
            final boolean var11 = true;
            byte var12;
            if (var9) {
                if (var10) {
                    var12 = 1;
                }
                else {
                    var12 = 2;
                }
            }
            else if (var10) {
                var12 = 0;
            }
            else {
                var12 = 3;
            }
            return var0.tileIcons[var12];
        }
        return null;
    }
    
    private static Icon getConnectedTextureTop(final ConnectedProperties var0, final IBlockAccess var1, final Block var2, final int var3, final int var4, final int var5, final int var6, final Icon var7, final int var8) {
        return (var6 != 0 && var6 != 1) ? (isNeighbour(var0, var1, var2, var3, var4 + 1, var5, var6, var7, var8) ? var0.tileIcons[0] : null) : null;
    }
    
    public static boolean isConnectedGlassPanes() {
        return Config.isConnectedTextures() && ConnectedTextures.defaultGlassTexture;
    }
    
    private static boolean getMatchingCtmPng(final RenderEngine var0) {
        return false;
    }
    
    private static ConnectedProperties makeDefaultProperties(final String var0, final RenderEngine var1) {
        return null;
    }
    
    public static void updateIcons(final TextureMap var0) {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.defaultGlassTexture = false;
        final RenderEngine var = Config.getRenderEngine();
        if (var != null) {
            final ITexturePack var2 = var.getTexturePack().getSelectedTexturePack();
            if (var2 != null) {
                final boolean var3 = var2.func_98138_b("/textures/blocks/glass.png", false);
                ConnectedTextures.defaultGlassTexture = !var3;
                final String[] var4 = collectFiles(var2, "ctm/", ".properties");
                Arrays.sort(var4);
                final ArrayList var5 = new ArrayList();
                final ArrayList var6 = new ArrayList();
                for (int var7 = 0; var7 < var4.length; ++var7) {
                    final String var8 = var4[var7];
                    Config.dbg("ConnectedTextures: " + var8);
                    try {
                        final String var9 = "/" + var8;
                        final InputStream var10 = var2.getResourceAsStream(var9);
                        if (var10 == null) {
                            Config.dbg("ConnectedTextures file not found: " + var8);
                        }
                        else {
                            final Properties var11 = new Properties();
                            var11.load(var10);
                            final ConnectedProperties var12 = new ConnectedProperties(var11, var8);
                            if (var12.isValid(var9)) {
                                var12.updateIcons(var0);
                                addToTileList(var12, var5);
                                addToBlockList(var12, var6);
                            }
                        }
                    }
                    catch (FileNotFoundException var14) {
                        Config.dbg("ConnectedTextures file not found: " + var8);
                    }
                    catch (IOException var13) {
                        var13.printStackTrace();
                    }
                }
                ConnectedTextures.blockProperties = propertyListToArray(var6);
                ConnectedTextures.tileProperties = propertyListToArray(var5);
                ConnectedTextures.multipass = detectMultipass();
                Config.dbg("Multipass connected textures: " + ConnectedTextures.multipass);
            }
        }
    }
    
    private static boolean detectMultipass() {
        final ArrayList var0 = new ArrayList();
        for (int var2 = 0; var2 < ConnectedTextures.tileProperties.length; ++var2) {
            final ConnectedProperties[] var3 = ConnectedTextures.tileProperties[var2];
            if (var3 != null) {
                var0.addAll(Arrays.asList(var3));
            }
        }
        for (int var2 = 0; var2 < ConnectedTextures.blockProperties.length; ++var2) {
            final ConnectedProperties[] var3 = ConnectedTextures.blockProperties[var2];
            if (var3 != null) {
                var0.addAll(Arrays.asList(var3));
            }
        }
        final ConnectedProperties[] var4 = var0.toArray(new ConnectedProperties[var0.size()]);
        final HashSet var5 = new HashSet();
        final HashSet var6 = new HashSet();
        for (int var7 = 0; var7 < var4.length; ++var7) {
            final ConnectedProperties var8 = var4[var7];
            if (var8.matchTileIcons != null) {
                var5.addAll(Arrays.asList(var8.matchTileIcons));
            }
            if (var8.tileIcons != null) {
                var6.addAll(Arrays.asList(var8.tileIcons));
            }
        }
        var5.retainAll(var6);
        return !var5.isEmpty();
    }
    
    private static ConnectedProperties[][] propertyListToArray(final List var0) {
        final ConnectedProperties[][] var = new ConnectedProperties[var0.size()][];
        for (int var2 = 0; var2 < var0.size(); ++var2) {
            final List var3 = var0.get(var2);
            if (var3 != null) {
                final ConnectedProperties[] var4 = var3.toArray(new ConnectedProperties[var3.size()]);
                var[var2] = var4;
            }
        }
        return var;
    }
    
    private static void addToTileList(final ConnectedProperties var0, final List var1) {
        if (var0.matchTileIcons != null) {
            for (int var2 = 0; var2 < var0.matchTileIcons.length; ++var2) {
                final Icon var3 = var0.matchTileIcons[var2];
                if (!(var3 instanceof TextureStitched)) {
                    Config.dbg("Icon is not TextureStitched: " + var3 + ", name: " + var3.getIconName());
                }
                else {
                    final TextureStitched var4 = (TextureStitched)var3;
                    final int var5 = var4.getIndexInMap();
                    if (var5 < 0) {
                        Config.dbg("Invalid tile ID: " + var5 + ", icon: " + var4.getIconName());
                    }
                    else {
                        addToList(var0, var1, var5);
                    }
                }
            }
        }
    }
    
    private static void addToBlockList(final ConnectedProperties var0, final List var1) {
        if (var0.matchBlocks != null) {
            for (int var2 = 0; var2 < var0.matchBlocks.length; ++var2) {
                final int var3 = var0.matchBlocks[var2];
                if (var3 < 0) {
                    Config.dbg("Invalid block ID: " + var3);
                }
                else {
                    addToList(var0, var1, var3);
                }
            }
        }
    }
    
    private static void addToList(final ConnectedProperties var0, final List var1, final int var2) {
        while (var2 >= var1.size()) {
            var1.add(null);
        }
        Object var3 = var1.get(var2);
        if (var3 == null) {
            var3 = new ArrayList();
            var1.set(var2, var3);
        }
        ((List)var3).add(var0);
    }
    
    private static String[] collectFiles(final ITexturePack var0, final String var1, final String var2) {
        if (!(var0 instanceof TexturePackImplementation)) {
            return new String[0];
        }
        final TexturePackImplementation var3 = (TexturePackImplementation)var0;
        if (var3 instanceof TexturePackDefault) {
            return collectFilesDefault(var3);
        }
        final File var4 = var3.texturePackFile;
        return (var4 == null) ? new String[0] : (var4.isDirectory() ? collectFilesFolder(var4, "", var1, var2) : (var4.isFile() ? collectFilesZIP(var4, var1, var2) : new String[0]));
    }
    
    private static String[] collectFilesDefault(final TexturePackImplementation var0) {
        final ArrayList var = new ArrayList();
        final String[] var2 = { "ctm/default/bookshelf.properties", "ctm/default/glass.properties", "ctm/default/glasspane.properties", "ctm/default/sandstone.properties" };
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final String var4 = var2[var3];
            if (var0.func_98140_c("/" + var4)) {
                var.add(var4);
            }
        }
        final String[] var5 = var.toArray(new String[var.size()]);
        return var5;
    }
    
    private static String[] collectFilesFolder(final File var0, final String var1, final String var2, final String var3) {
        final ArrayList var4 = new ArrayList();
        final File[] var5 = var0.listFiles();
        if (var5 == null) {
            return new String[0];
        }
        for (int var6 = 0; var6 < var5.length; ++var6) {
            final File var7 = var5[var6];
            if (var7.isFile()) {
                final String var8 = String.valueOf(var1) + var7.getName();
                if (var8.startsWith(var2) && var8.endsWith(var3)) {
                    var4.add(var8);
                }
            }
            else if (var7.isDirectory()) {
                final String var8 = String.valueOf(var1) + var7.getName() + "/";
                final String[] var9 = collectFilesFolder(var7, var8, var2, var3);
                for (int var10 = 0; var10 < var9.length; ++var10) {
                    final String var11 = var9[var10];
                    var4.add(var11);
                }
            }
        }
        final String[] var12 = var4.toArray(new String[var4.size()]);
        return var12;
    }
    
    private static String[] collectFilesZIP(final File var0, final String var1, final String var2) {
        final ArrayList var3 = new ArrayList();
        try {
            final ZipFile var4 = new ZipFile(var0);
            final Enumeration var5 = var4.entries();
            while (var5.hasMoreElements()) {
                final ZipEntry var6 = var5.nextElement();
                final String var7 = var6.getName();
                if (var7.startsWith(var1) && var7.endsWith(var2)) {
                    var3.add(var7);
                }
            }
            var4.close();
            final String[] var8 = var3.toArray(new String[var3.size()]);
            return var8;
        }
        catch (IOException var9) {
            var9.printStackTrace();
            return new String[0];
        }
    }
    
    public static int getPaneTextureIndex(final boolean var0, final boolean var1, final boolean var2, final boolean var3) {
        return (var1 && var0) ? (var2 ? (var3 ? 34 : 50) : (var3 ? 18 : 2)) : ((var1 && !var0) ? (var2 ? (var3 ? 35 : 51) : (var3 ? 19 : 3)) : ((!var1 && var0) ? (var2 ? (var3 ? 33 : 49) : (var3 ? 17 : 1)) : (var2 ? (var3 ? 32 : 48) : (var3 ? 16 : 0))));
    }
    
    public static int getReversePaneTextureIndex(final int var0) {
        final int var = var0 % 16;
        return (var == 1) ? (var0 + 2) : ((var == 3) ? (var0 - 2) : var0);
    }
    
    public static Icon getCtmTexture(final ConnectedProperties var0, final int var1, final Icon var2) {
        if (var0.method != 1) {
            return var2;
        }
        if (var1 >= 0 && var1 < ConnectedTextures.ctmIndexes.length) {
            final int var3 = ConnectedTextures.ctmIndexes[var1];
            final Icon[] var4 = var0.tileIcons;
            return (var3 >= 0 && var3 < var4.length) ? var4[var3] : var2;
        }
        return var2;
    }
}
