package optfine;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.block.*;
import java.util.zip.*;
import net.minecraft.client.renderer.texture.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.client.resources.*;

public class ConnectedTextures
{
    private static final int X_AXIS;
    private static Map[] spriteQuadMaps;
    private static final int[] ctmIndexes;
    private static final int Z_AXIS;
    private static boolean multipass;
    public static final IBlockState AIR_DEFAULT_STATE;
    private static final int X_POS_EAST;
    private static TextureAtlasSprite emptySprite;
    private static final int Z_POS_SOUTH;
    private static final String[] propSuffixes;
    private static final int Y_AXIS;
    private static ConnectedProperties[][] tileProperties;
    private static final int Y_NEG_DOWN;
    private static final String[] I;
    private static final int Y_POS_UP;
    private static ConnectedProperties[][] blockProperties;
    private static final int X_NEG_WEST;
    private static final int Z_NEG_NORTH;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    
    private static TextureAtlasSprite getConnectedTextureMultiPass(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final RenderEnv renderEnv) {
        final TextureAtlasSprite connectedTextureSingle = getConnectedTextureSingle(blockAccess, blockState, blockPos, enumFacing, textureAtlasSprite, " ".length() != 0, renderEnv);
        if (!ConnectedTextures.multipass) {
            return connectedTextureSingle;
        }
        if (connectedTextureSingle == textureAtlasSprite) {
            return connectedTextureSingle;
        }
        TextureAtlasSprite textureAtlasSprite2 = connectedTextureSingle;
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < "   ".length()) {
            final TextureAtlasSprite connectedTextureSingle2 = getConnectedTextureSingle(blockAccess, blockState, blockPos, enumFacing, textureAtlasSprite2, "".length() != 0, renderEnv);
            if (connectedTextureSingle2 == textureAtlasSprite2) {
                "".length();
                if (2 == 1) {
                    throw null;
                }
                break;
            }
            else {
                textureAtlasSprite2 = connectedTextureSingle2;
                ++i;
            }
        }
        return textureAtlasSprite2;
    }
    
    private static void addToBlockList(final ConnectedProperties connectedProperties, final List list) {
        if (connectedProperties.matchBlocks != null) {
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < connectedProperties.matchBlocks.length) {
                final int n = connectedProperties.matchBlocks[i];
                if (n < 0) {
                    Config.warn(ConnectedTextures.I[0xAE ^ 0x89] + n);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    addToList(connectedProperties, list, n);
                }
                ++i;
            }
        }
    }
    
    private static int fixSideByAxis(final int n, final int n2) {
        switch (n2) {
            case 0: {
                return n;
            }
            case 1: {
                switch (n) {
                    case 0: {
                        return "  ".length();
                    }
                    case 1: {
                        return "   ".length();
                    }
                    case 2: {
                        return " ".length();
                    }
                    case 3: {
                        return "".length();
                    }
                    default: {
                        return n;
                    }
                }
                break;
            }
            case 2: {
                switch (n) {
                    case 0: {
                        return 0x87 ^ 0x83;
                    }
                    case 1: {
                        return 0x21 ^ 0x24;
                    }
                    default: {
                        return n;
                    }
                    case 4: {
                        return " ".length();
                    }
                    case 5: {
                        return "".length();
                    }
                }
                break;
            }
            default: {
                return n;
            }
        }
    }
    
    private static void I() {
        (I = new String[0x77 ^ 0x3B])["".length()] = I("", "lfJgo");
        ConnectedTextures.I[" ".length()] = I("\n", "khxCP");
        ConnectedTextures.I["  ".length()] = I("(", "JDsFE");
        ConnectedTextures.I["   ".length()] = I("/", "LFwbF");
        ConnectedTextures.I[0x1A ^ 0x1E] = I("\u0005", "afoXI");
        ConnectedTextures.I[0xA4 ^ 0xA1] = I(".", "Kgypk");
        ConnectedTextures.I[0x81 ^ 0x87] = I("\u001e", "xMsAu");
        ConnectedTextures.I[0xBB ^ 0xBC] = I("\u001e", "yKOGR");
        ConnectedTextures.I[0x5D ^ 0x55] = I("\u0004", "lPFMU");
        ConnectedTextures.I[0x79 ^ 0x70] = I("\u0005", "llLae");
        ConnectedTextures.I[0x58 ^ 0x52] = I("$", "NXYIh");
        ConnectedTextures.I[0x44 ^ 0x4F] = I(" ", "KWWFJ");
        ConnectedTextures.I[0x53 ^ 0x5F] = I("\u0019", "uVYdk");
        ConnectedTextures.I[0xCB ^ 0xC6] = I("+", "FVXdC");
        ConnectedTextures.I[0x89 ^ 0x87] = I("<", "RYzwZ");
        ConnectedTextures.I[0x3D ^ 0x32] = I("\u0002", "mrUvx");
        ConnectedTextures.I[0x7A ^ 0x6A] = I("3", "CfniC");
        ConnectedTextures.I[0x16 ^ 0x7] = I("0", "ATaFk");
        ConnectedTextures.I[0x62 ^ 0x70] = I("3", "AaYja");
        ConnectedTextures.I[0x66 ^ 0x75] = I("7", "DtGHl");
        ConnectedTextures.I[0xB ^ 0x1F] = I("<", "HVMmb");
        ConnectedTextures.I[0x68 ^ 0x7D] = I("7", "BExlO");
        ConnectedTextures.I[0x1 ^ 0x17] = I("<", "JUdIm");
        ConnectedTextures.I[0x57 ^ 0x40] = I("\r", "zYybC");
        ConnectedTextures.I[0x46 ^ 0x5E] = I("\u0012", "jmlnm");
        ConnectedTextures.I[0xDF ^ 0xC6] = I("\u0018", "ahSEa");
        ConnectedTextures.I[0x6D ^ 0x77] = I("\u0015", "oGSBR");
        ConnectedTextures.I[0x2 ^ 0x19] = I("\f\u0010\b\u000b&\u0013\u0018\u0000\u001a\u007f\u0003\u0015\t\r.\u0012V\u0001\u0002$\u0012\n9\u001e$\u000f\u001c9\u001a*\u0011", "ayfnE");
        ConnectedTextures.I[0x1 ^ 0x1D] = I("\u0001\" 75\u000f)5$n\u000f5=y%\t'1#-\u0018n5;1\u00188", "lAPVA");
        ConnectedTextures.I[0x66 ^ 0x7B] = I("4\u000e\u0005\f,:\u0005\u0010\u001fw:\u0019\u0018B", "YmumX");
        ConnectedTextures.I[0x8F ^ 0x91] = I("j &\u000b(!\" \r=7", "DPTdX");
        ConnectedTextures.I[0x1F ^ 0x0] = I("5\u0016%=-\u0015\r.7\u001c\u0013\u0001?&:\u0013\nqs", "vyKSH");
        ConnectedTextures.I[0xBA ^ 0x9A] = I("-\u0003674\r\u0018==\u0005\u000b\u0014,,#\u000b\u001fx?8\u0002\tx7>\u001aL>6$\u0000\bby", "nlXYQ");
        ConnectedTextures.I[0xE4 ^ 0xC5] = I("\u001a\n\u0001=*:\u0011\n7\u001b<\u001d\u001b&=<\u0016O5&5\u0000O= -E\t<:7\u0001Us", "YeoSO");
        ConnectedTextures.I[0x76 ^ 0x54] = I("\u0017\u0019\u0003%\u000e*\r\u001c\"G9\u0003\u0001?\u00029\u0018\n5G.\t\u0017%\u0012(\t\u001ckG", "ZloQg");
        ConnectedTextures.I[0x3A ^ 0x19] = I("!2>\u0010\u0007\u00072\u0007\u0010\u001e\u0014$\u0015\u0014\u0000\u001c##D\u001b\u0006w(\u000b\u0006U\u0003#\u001c\u0006\u0000%#%\u0006\u0019657\u0002\u0007>2\u0001HU", "uWFdr");
        ConnectedTextures.I[0x4 ^ 0x20] = I("[q\u0006$:\u0012kH", "wQhEW");
        ConnectedTextures.I[0xAB ^ 0x8E] = I("\u0000!4.\u001f +b;\u001a%*b\u00067so", "IOBOs");
        ConnectedTextures.I[0xB0 ^ 0x96] = I("or\n1!-hC", "CRcRN");
        ConnectedTextures.I[0x79 ^ 0x5E] = I("\u0007\u0018$/\u0004'\u0012r,\u0004!\u00159n!\nLr", "NvRNh");
        ConnectedTextures.I[0x2E ^ 0x6] = I("", "RaPKj");
        ConnectedTextures.I[0x80 ^ 0xA9] = I("<\n\n\u0013\u00072\u0001\u001f\u0000\\2\u001d\u0017]\u00174\u000f\u001b\u0007\u001f%F", "Qizrs");
        ConnectedTextures.I[0x55 ^ 0x7F] = I("#'\"\r\u0007%')V\u0010;-9\u0012\u0001x%6\u0018\u0001$l*\u0017\u0015", "WBZyr");
        ConnectedTextures.I[0x45 ^ 0x6E] = I("1\u0018\u0005\u0014\nx\u0004\u0016\b\t3\u0006\u0010\u000e\u001c%", "Vtdgy");
        ConnectedTextures.I[0x6 ^ 0x2A] = I("7\u0002\u001b9+ \u000f\u0014/v \u001c\u0015:=\"\u001a\u0013/+", "PnzJX");
        ConnectedTextures.I[0x1D ^ 0x30] = I("%'\u0019\u000e\u0005#'\u0012U\u0012=-\u0002\u0011\u0003~ \u000e\u0015\u001b\"*\u0004\u0016\u0016\u007f2\u000f\u001d", "QBazp");
        ConnectedTextures.I[0x63 ^ 0x4D] = I("\u0011&\u0001$5\u001b,\u0002)h\u0003;\u0001?#\u0001=\u0007*5", "sInOF");
        ConnectedTextures.I[0x71 ^ 0x5E] = I("-\u0007 \u001f&+\u0007+D15\r;\u0000 v\u00119\u00057*\u00167\u00056\u0006\f7\u0019>8\u000ev\u001b=>", "YbXkS");
        ConnectedTextures.I[0xB5 ^ 0x85] = I("\u0005(\u00047\u001e\u0002&\u00046C\u0006;\u0005#\b\u0004=\u00036\u001e", "vIjSm");
        ConnectedTextures.I[0x91 ^ 0xA0] = I(".<=\u0000\u001d", "YTTtx");
        ConnectedTextures.I[0x9B ^ 0xA9] = I("\n686\u0016\u0000", "eDYXq");
        ConnectedTextures.I[0x9 ^ 0x3A] = I("\u0018\u0000?\u0002\b\u0001\u0000", "uaXgf");
        ConnectedTextures.I[0xBC ^ 0x88] = I("\u001d*\u000b\u00107.!\u0000\r&", "qClxC");
        ConnectedTextures.I[0x3E ^ 0xB] = I("2\"\u000f\u0016\u0003<", "KGczl");
        ConnectedTextures.I[0x2E ^ 0x18] = I("/\f\u001f\u0004", "Cerao");
        ConnectedTextures.I[0x16 ^ 0x21] = I("\u001c=-3", "lTCXJ");
        ConnectedTextures.I[0x25 ^ 0x1D] = I("\u0013\u0011\u0013\u0018", "tcraf");
        ConnectedTextures.I[0xA0 ^ 0x99] = I("\u001489\u00103\u0015", "gQUfV");
        ConnectedTextures.I[0x8C ^ 0xB6] = I("5 \u0000\n", "VYadO");
        ConnectedTextures.I[0xA1 ^ 0x9A] = I("\u0018$\u0019#5\r", "hQkSY");
        ConnectedTextures.I[0x14 ^ 0x28] = I("\u0016\u0001<0", "tmIUi");
        ConnectedTextures.I[0x71 ^ 0x4C] = I("-\u001f\u00036\u0005", "OmlAk");
        ConnectedTextures.I[0x88 ^ 0xB6] = I("1+27\u001d", "VYWRs");
        ConnectedTextures.I[0xB1 ^ 0x8E] = I("'\u0010'", "UuCfg");
        ConnectedTextures.I[0xD4 ^ 0x94] = I("\u0006\u001a/1\b", "dvNRc");
        ConnectedTextures.I[0x36 ^ 0x77] = I("\u0016#\u001e\u0017\u001c\u0010#\u0015L\u000b\u000e)\u0005\b\u001aM!\n\u0002\u001a\u0011\u0019", "bFfci");
        ConnectedTextures.I[0xDE ^ 0x9C] = I("y:\u001f ", "WJqGG");
        ConnectedTextures.I[0x6F ^ 0x2C] = I("\u0010>\u000e\u0019?<\u0006", "OYbxL");
        ConnectedTextures.I[0x56 ^ 0x12] = I("v09\u001b\u001d*\b", "YWUzn");
        ConnectedTextures.I[0x4C ^ 0x9] = I("V\u0007\u00028>\u001d\u0005\u0004>+\u000b", "xwpWN");
        ConnectedTextures.I[0x5D ^ 0x1B] = I("\u0019\u0003\u0004#;5;", "FdhBH");
        ConnectedTextures.I[0x6D ^ 0x2A] = I("B&88\u0010\u001e\u001e$8\r\b\u001e", "mATYc");
        ConnectedTextures.I[0x64 ^ 0x2C] = I("e*5\r\u001d.(3\u000b\b8", "KZGbm");
        ConnectedTextures.I[0x6D ^ 0x24] = I("\u0004\u0018\u0014\u0004\u0019\u0016D\n\b\u0003\u0000\b\u0015\u0000\u000b\u0011D", "ekgam");
        ConnectedTextures.I[0x28 ^ 0x62] = I("n", "AwAsv");
        ConnectedTextures.I[0x1C ^ 0x57] = I("\u0010\u001c2\u00062\u0002@,\n(\u0014\f3\u0002 \u0005@", "qoAcF");
    }
    
    private static String[] collectFilesFolder(final File file, final String s, final String s2, final String s3) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s4 = ConnectedTextures.I[0x31 ^ 0x78];
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return new String["".length()];
        }
        int i = "".length();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (i < listFiles.length) {
            final File file2 = listFiles[i];
            if (file2.isFile()) {
                final String string = String.valueOf(s) + file2.getName();
                if (string.startsWith(s4)) {
                    final String substring = string.substring(s4.length());
                    if (substring.startsWith(s2) && substring.endsWith(s3)) {
                        list.add(substring);
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                }
            }
            else if (file2.isDirectory()) {
                final String[] collectFilesFolder = collectFilesFolder(file2, String.valueOf(s) + file2.getName() + ConnectedTextures.I[0x8A ^ 0xC0], s2, s3);
                int j = "".length();
                "".length();
                if (4 == 1) {
                    throw null;
                }
                while (j < collectFilesFolder.length) {
                    list.add(collectFilesFolder[j]);
                    ++j;
                }
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static String[] collectFilesDefault(final IResourcePack resourcePack) {
        final ArrayList<String> list = new ArrayList<String>();
        final String[] defaultCtmPaths = getDefaultCtmPaths();
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < defaultCtmPaths.length) {
            final String s = defaultCtmPaths[i];
            if (resourcePack.resourceExists(new ResourceLocation(s))) {
                list.add(s);
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static void fixVertex(final int[] array, final int n, final TextureAtlasSprite textureAtlasSprite, final TextureAtlasSprite textureAtlasSprite2) {
        final int n2 = (0x7A ^ 0x7D) * n;
        final float intBitsToFloat = Float.intBitsToFloat(array[n2 + (0x85 ^ 0x81)]);
        final float intBitsToFloat2 = Float.intBitsToFloat(array[n2 + (0xA8 ^ 0xAC) + " ".length()]);
        final double spriteU16 = textureAtlasSprite.getSpriteU16(intBitsToFloat);
        final double spriteV16 = textureAtlasSprite.getSpriteV16(intBitsToFloat2);
        array[n2 + (0x18 ^ 0x1C)] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedU(spriteU16));
        array[n2 + (0x81 ^ 0x85) + " ".length()] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedV(spriteV16));
    }
    
    private static void addToTileList(final ConnectedProperties connectedProperties, final List list) {
        if (connectedProperties.matchTileIcons != null) {
            int i = "".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
            while (i < connectedProperties.matchTileIcons.length) {
                final TextureAtlasSprite textureAtlasSprite = connectedProperties.matchTileIcons[i];
                if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
                    Config.warn(ConnectedTextures.I[0x61 ^ 0x42] + textureAtlasSprite + ConnectedTextures.I[0x25 ^ 0x1] + textureAtlasSprite.getIconName());
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else {
                    final int indexInMap = textureAtlasSprite.getIndexInMap();
                    if (indexInMap < 0) {
                        Config.warn(ConnectedTextures.I[0xF ^ 0x2A] + indexInMap + ConnectedTextures.I[0xE5 ^ 0xC3] + textureAtlasSprite.getIconName());
                        "".length();
                        if (4 == 2) {
                            throw null;
                        }
                    }
                    else {
                        addToList(connectedProperties, list, indexInMap);
                    }
                }
                ++i;
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTexture(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final TextureAtlasSprite textureAtlasSprite, final int n2, final RenderEnv renderEnv) {
        final int y = blockPos.getY();
        if (y < connectedProperties.minHeight || y > connectedProperties.maxHeight) {
            return null;
        }
        if (connectedProperties.biomes != null) {
            final BiomeGenBase biomeGenForCoords = blockAccess.getBiomeGenForCoords(blockPos);
            int n3 = "".length();
            int i = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (i < connectedProperties.biomes.length) {
                if (biomeGenForCoords == connectedProperties.biomes[i]) {
                    n3 = " ".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            if (n3 == 0) {
                return null;
            }
        }
        int n4 = "".length();
        int length = n2;
        final Block block = blockState.getBlock();
        if (block instanceof BlockRotatedPillar) {
            n4 = getWoodAxis(n, n2);
            length = (n2 & "   ".length());
        }
        if (block instanceof BlockQuartz) {
            n4 = getQuartzAxis(n, n2);
            if (length > "  ".length()) {
                length = "  ".length();
            }
        }
        if (n >= 0 && connectedProperties.faces != (0x18 ^ 0x27)) {
            int fixSideByAxis = n;
            if (n4 != 0) {
                fixSideByAxis = fixSideByAxis(n, n4);
            }
            if ((" ".length() << fixSideByAxis & connectedProperties.faces) == 0x0) {
                return null;
            }
        }
        if (connectedProperties.metadatas != null) {
            final int[] metadatas = connectedProperties.metadatas;
            int n5 = "".length();
            int j = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (j < metadatas.length) {
                if (metadatas[j] == length) {
                    n5 = " ".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                }
            }
            if (n5 == 0) {
                return null;
            }
        }
        switch (connectedProperties.method) {
            case 1: {
                return getConnectedTextureCtm(connectedProperties, blockAccess, blockState, blockPos, n, textureAtlasSprite, n2, renderEnv);
            }
            case 2: {
                return getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, n4, n, textureAtlasSprite, n2);
            }
            case 3: {
                return getConnectedTextureTop(connectedProperties, blockAccess, blockState, blockPos, n4, n, textureAtlasSprite, n2);
            }
            case 4: {
                return getConnectedTextureRandom(connectedProperties, blockPos, n);
            }
            case 5: {
                return getConnectedTextureRepeat(connectedProperties, blockPos, n);
            }
            case 6: {
                return getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, n4, n, textureAtlasSprite, n2);
            }
            case 7: {
                return getConnectedTextureFixed(connectedProperties);
            }
            case 8: {
                return getConnectedTextureHorizontalVertical(connectedProperties, blockAccess, blockState, blockPos, n4, n, textureAtlasSprite, n2);
            }
            case 9: {
                return getConnectedTextureVerticalHorizontal(connectedProperties, blockAccess, blockState, blockPos, n4, n, textureAtlasSprite, n2);
            }
            default: {
                return null;
            }
        }
    }
    
    public static synchronized BakedQuad getConnectedTexture(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final BakedQuad bakedQuad, final RenderEnv renderEnv) {
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        if (sprite == null) {
            return bakedQuad;
        }
        final Block block = blockState.getBlock();
        final EnumFacing face = bakedQuad.getFace();
        if (block instanceof BlockPane && sprite.getIconName().startsWith(ConnectedTextures.I[0x36 ^ 0x2D]) && blockAccess.getBlockState(blockPos.offset(bakedQuad.getFace())) == blockState) {
            return getQuad(ConnectedTextures.emptySprite, block, blockState, bakedQuad);
        }
        final TextureAtlasSprite connectedTextureMultiPass = getConnectedTextureMultiPass(blockAccess, blockState, blockPos, face, sprite, renderEnv);
        BakedQuad quad;
        if (connectedTextureMultiPass == sprite) {
            quad = bakedQuad;
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            quad = getQuad(connectedTextureMultiPass, block, blockState, bakedQuad);
        }
        return quad;
    }
    
    private static String[] collectFilesZIP(final File file, final String s, final String s2) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s3 = ConnectedTextures.I[0x89 ^ 0xC2];
        try {
            final ZipFile zipFile = new ZipFile(file);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (entries.hasMoreElements()) {
                final String name = ((ZipEntry)entries.nextElement()).getName();
                if (name.startsWith(s3)) {
                    final String substring = name.substring(s3.length());
                    if (!substring.startsWith(s) || !substring.endsWith(s2)) {
                        continue;
                    }
                    list.add(substring);
                }
            }
            zipFile.close();
            return list.toArray(new String[list.size()]);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return new String["".length()];
        }
    }
    
    public static void updateIcons(final TextureMap textureMap) {
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        if (Config.isConnectedTextures()) {
            final IResourcePack[] resourcePacks = Config.getResourcePacks();
            int i = resourcePacks.length - " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (i >= 0) {
                updateIcons(textureMap, resourcePacks[i]);
                --i;
            }
            updateIcons(textureMap, Config.getDefaultResourcePack());
            ConnectedTextures.emptySprite = textureMap.registerSprite(new ResourceLocation(ConnectedTextures.I[0x73 ^ 0x6F]));
            ConnectedTextures.spriteQuadMaps = new Map[textureMap.getCountRegisteredSprites() + " ".length()];
        }
    }
    
    public static TextureAtlasSprite getCtmTexture(final ConnectedProperties connectedProperties, final int n, final TextureAtlasSprite textureAtlasSprite) {
        if (connectedProperties.method != " ".length()) {
            return textureAtlasSprite;
        }
        if (n >= 0 && n < ConnectedTextures.ctmIndexes.length) {
            final int n2 = ConnectedTextures.ctmIndexes[n];
            final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
            TextureAtlasSprite textureAtlasSprite2;
            if (n2 >= 0 && n2 < tileIcons.length) {
                textureAtlasSprite2 = tileIcons[n2];
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                textureAtlasSprite2 = textureAtlasSprite;
            }
            return textureAtlasSprite2;
        }
        return textureAtlasSprite;
    }
    
    private static TextureAtlasSprite getConnectedTextureRandom(final ConnectedProperties connectedProperties, final BlockPos blockPos, final int n) {
        if (connectedProperties.tileIcons.length == " ".length()) {
            return connectedProperties.tileIcons["".length()];
        }
        final int n2 = Config.getRandom(blockPos, n / connectedProperties.symmetry * connectedProperties.symmetry) & 2143535749 + 916906842 - 1649076870 + 736117926;
        int length = "".length();
        if (connectedProperties.weights == null) {
            length = n2 % connectedProperties.tileIcons.length;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            final int n3 = n2 % connectedProperties.sumAllWeights;
            final int[] sumWeights = connectedProperties.sumWeights;
            int i = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (i < sumWeights.length) {
                if (n3 < sumWeights[i]) {
                    length = i;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        return connectedProperties.tileIcons[length];
    }
    
    private static int getQuartzAxis(final int n, final int n2) {
        switch (n2) {
            case 3: {
                return "  ".length();
            }
            case 4: {
                return " ".length();
            }
            default: {
                return "".length();
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureFixed(final ConnectedProperties connectedProperties) {
        return connectedProperties.tileIcons["".length()];
    }
    
    public static int getPaneTextureIndex(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        int n;
        if (b2 && b) {
            if (b3) {
                if (b4) {
                    n = (0x53 ^ 0x71);
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else {
                    n = (0x3E ^ 0xC);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
            }
            else if (b4) {
                n = (0x46 ^ 0x54);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n = "  ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else if (b2 && !b) {
            if (b3) {
                if (b4) {
                    n = (0xBA ^ 0x99);
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
                else {
                    n = (0xB ^ 0x38);
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
            }
            else if (b4) {
                n = (0x70 ^ 0x63);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                n = "   ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
        }
        else if (!b2 && b) {
            if (b3) {
                if (b4) {
                    n = (0x20 ^ 0x1);
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                else {
                    n = (0x5A ^ 0x6B);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            else if (b4) {
                n = (0x47 ^ 0x56);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                n = " ".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        else if (b3) {
            if (b4) {
                n = (0x18 ^ 0x38);
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                n = (0x2B ^ 0x1B);
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
        }
        else if (b4) {
            n = (0x62 ^ 0x72);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    private static boolean detectMultipass() {
        final ArrayList list = new ArrayList();
        int i = "".length();
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (i < ConnectedTextures.tileProperties.length) {
            final ConnectedProperties[] array = ConnectedTextures.tileProperties[i];
            if (array != null) {
                list.addAll(Arrays.asList(array));
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (j < ConnectedTextures.blockProperties.length) {
            final ConnectedProperties[] array2 = ConnectedTextures.blockProperties[j];
            if (array2 != null) {
                list.addAll(Arrays.asList(array2));
            }
            ++j;
        }
        final ConnectedProperties[] array3 = (ConnectedProperties[])list.toArray(new ConnectedProperties[list.size()]);
        final HashSet set = new HashSet();
        final HashSet set2 = new HashSet();
        int k = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (k < array3.length) {
            final ConnectedProperties connectedProperties = array3[k];
            if (connectedProperties.matchTileIcons != null) {
                set.addAll(Arrays.asList(connectedProperties.matchTileIcons));
            }
            if (connectedProperties.tileIcons != null) {
                set2.addAll(Arrays.asList(connectedProperties.tileIcons));
            }
            ++k;
        }
        set.retainAll(set2);
        int n;
        if (set.isEmpty()) {
            n = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    private static int getWoodAxis(final int n, final int n2) {
        switch ((n2 & (0xB0 ^ 0xBC)) >> "  ".length()) {
            case 1: {
                return "  ".length();
            }
            case 2: {
                return " ".length();
            }
            default: {
                return "".length();
            }
        }
    }
    
    private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
        final TextureAtlasSprite connectedTextureVertical = getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        if (connectedTextureVertical != null && connectedTextureVertical != textureAtlasSprite && connectedTextureVertical != tileIcons["   ".length()]) {
            return connectedTextureVertical;
        }
        final TextureAtlasSprite connectedTextureHorizontal = getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        TextureAtlasSprite textureAtlasSprite2;
        if (connectedTextureHorizontal == tileIcons["".length()]) {
            textureAtlasSprite2 = tileIcons[0x7D ^ 0x79];
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (connectedTextureHorizontal == tileIcons[" ".length()]) {
            textureAtlasSprite2 = tileIcons[0x5A ^ 0x5F];
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (connectedTextureHorizontal == tileIcons["  ".length()]) {
            textureAtlasSprite2 = tileIcons[0x8C ^ 0x8A];
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            textureAtlasSprite2 = connectedTextureHorizontal;
        }
        return textureAtlasSprite2;
    }
    
    public static void updateIcons(final TextureMap textureMap, final IResourcePack resourcePack) {
        final String[] collectFiles = collectFiles(resourcePack, ConnectedTextures.I[0x99 ^ 0x84], ConnectedTextures.I[0x3D ^ 0x23]);
        Arrays.sort(collectFiles);
        final List propertyList = makePropertyList(ConnectedTextures.tileProperties);
        final List propertyList2 = makePropertyList(ConnectedTextures.blockProperties);
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < collectFiles.length) {
            final String s = collectFiles[i];
            Config.dbg(ConnectedTextures.I[0x6A ^ 0x75] + s);
            try {
                final InputStream inputStream = resourcePack.getInputStream(new ResourceLocation(s));
                if (inputStream == null) {
                    Config.warn(ConnectedTextures.I[0x48 ^ 0x68] + s);
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
                else {
                    final Properties properties = new Properties();
                    properties.load(inputStream);
                    final ConnectedProperties connectedProperties = new ConnectedProperties(properties, s);
                    if (connectedProperties.isValid(s)) {
                        connectedProperties.updateIcons(textureMap);
                        addToTileList(connectedProperties, propertyList);
                        addToBlockList(connectedProperties, propertyList2);
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                    }
                }
            }
            catch (FileNotFoundException ex2) {
                Config.warn(ConnectedTextures.I[0x41 ^ 0x60] + s);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            ++i;
        }
        ConnectedTextures.blockProperties = propertyListToArray(propertyList2);
        ConnectedTextures.tileProperties = propertyListToArray(propertyList);
        ConnectedTextures.multipass = detectMultipass();
        Config.dbg(ConnectedTextures.I[0x7F ^ 0x5D] + ConnectedTextures.multipass);
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static boolean isNeighbour(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final TextureAtlasSprite textureAtlasSprite, final int n2) {
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos);
        if (blockState == blockState2) {
            return " ".length() != 0;
        }
        if (connectedProperties.connect != "  ".length()) {
            int n3;
            if (connectedProperties.connect == "   ".length()) {
                if (blockState2 == null) {
                    n3 = "".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (blockState2 == ConnectedTextures.AIR_DEFAULT_STATE) {
                    n3 = "".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (blockState2.getBlock().getMaterial() == blockState.getBlock().getMaterial()) {
                    n3 = " ".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            else {
                n3 = "".length();
            }
            return n3 != 0;
        }
        if (blockState2 == null) {
            return "".length() != 0;
        }
        if (blockState2 == ConnectedTextures.AIR_DEFAULT_STATE) {
            return "".length() != 0;
        }
        if (getNeighbourIcon(blockAccess, blockPos, blockState2, n) == textureAtlasSprite) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static TextureAtlasSprite getConnectedTextureSingle(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final EnumFacing enumFacing, final TextureAtlasSprite textureAtlasSprite, final boolean b, final RenderEnv renderEnv) {
        blockState.getBlock();
        if (ConnectedTextures.tileProperties != null) {
            final int indexInMap = textureAtlasSprite.getIndexInMap();
            if (indexInMap >= 0 && indexInMap < ConnectedTextures.tileProperties.length) {
                final ConnectedProperties[] array = ConnectedTextures.tileProperties[indexInMap];
                if (array != null) {
                    final int metadata = renderEnv.getMetadata();
                    final int side = getSide(enumFacing);
                    int i = "".length();
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                    while (i < array.length) {
                        final ConnectedProperties connectedProperties = array[i];
                        if (connectedProperties != null && connectedProperties.matchesBlock(renderEnv.getBlockId())) {
                            final TextureAtlasSprite connectedTexture = getConnectedTexture(connectedProperties, blockAccess, blockState, blockPos, side, textureAtlasSprite, metadata, renderEnv);
                            if (connectedTexture != null) {
                                return connectedTexture;
                            }
                        }
                        ++i;
                    }
                }
            }
        }
        if (ConnectedTextures.blockProperties != null && b) {
            final int blockId = renderEnv.getBlockId();
            if (blockId >= 0 && blockId < ConnectedTextures.blockProperties.length) {
                final ConnectedProperties[] array2 = ConnectedTextures.blockProperties[blockId];
                if (array2 != null) {
                    final int metadata2 = renderEnv.getMetadata();
                    final int side2 = getSide(enumFacing);
                    int j = "".length();
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    while (j < array2.length) {
                        final ConnectedProperties connectedProperties2 = array2[j];
                        if (connectedProperties2 != null && connectedProperties2.matchesIcon(textureAtlasSprite)) {
                            final TextureAtlasSprite connectedTexture2 = getConnectedTexture(connectedProperties2, blockAccess, blockState, blockPos, side2, textureAtlasSprite, metadata2, renderEnv);
                            if (connectedTexture2 != null) {
                                return connectedTexture2;
                            }
                        }
                        ++j;
                    }
                }
            }
        }
        return textureAtlasSprite;
    }
    
    private static String[] getDefaultCtmPaths() {
        final ArrayList<String> list = new ArrayList<String>();
        final String s = ConnectedTextures.I[0x35 ^ 0x1C];
        if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.I[0x41 ^ 0x6B]))) {
            list.add(String.valueOf(s) + ConnectedTextures.I[0xEF ^ 0xC4]);
            list.add(String.valueOf(s) + ConnectedTextures.I[0x3D ^ 0x11]);
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.I[0x27 ^ 0xA]))) {
            list.add(String.valueOf(s) + ConnectedTextures.I[0x3C ^ 0x12]);
        }
        if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.I[0xBF ^ 0x90]))) {
            list.add(String.valueOf(s) + ConnectedTextures.I[0xA2 ^ 0x92]);
        }
        final String[] array = new String[0x4 ^ 0x14];
        array["".length()] = ConnectedTextures.I[0x42 ^ 0x73];
        array[" ".length()] = ConnectedTextures.I[0x14 ^ 0x26];
        array["  ".length()] = ConnectedTextures.I[0xA3 ^ 0x90];
        array["   ".length()] = ConnectedTextures.I[0xD ^ 0x39];
        array[0xB0 ^ 0xB4] = ConnectedTextures.I[0x4A ^ 0x7F];
        array[0x86 ^ 0x83] = ConnectedTextures.I[0x92 ^ 0xA4];
        array[0xD ^ 0xB] = ConnectedTextures.I[0xAB ^ 0x9C];
        array[0x2D ^ 0x2A] = ConnectedTextures.I[0x56 ^ 0x6E];
        array[0x2E ^ 0x26] = ConnectedTextures.I[0x2E ^ 0x17];
        array[0x39 ^ 0x30] = ConnectedTextures.I[0x72 ^ 0x48];
        array[0x85 ^ 0x8F] = ConnectedTextures.I[0x9 ^ 0x32];
        array[0x88 ^ 0x83] = ConnectedTextures.I[0x36 ^ 0xA];
        array[0xB2 ^ 0xBE] = ConnectedTextures.I[0xBF ^ 0x82];
        array[0x82 ^ 0x8F] = ConnectedTextures.I[0x83 ^ 0xBD];
        array[0x76 ^ 0x78] = ConnectedTextures.I[0x58 ^ 0x67];
        array[0x66 ^ 0x69] = ConnectedTextures.I[0x22 ^ 0x62];
        final String[] array2 = array;
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < array2.length) {
            final String s2 = array2[i];
            if (Config.isFromDefaultResourcePack(new ResourceLocation(ConnectedTextures.I[0xFF ^ 0xBE] + s2 + ConnectedTextures.I[0x81 ^ 0xC3]))) {
                list.add(String.valueOf(s) + i + ConnectedTextures.I[0x84 ^ 0xC7] + s2 + ConnectedTextures.I[0xC1 ^ 0x85] + s2 + ConnectedTextures.I[0x35 ^ 0x70]);
                list.add(String.valueOf(s) + i + ConnectedTextures.I[0x1A ^ 0x5C] + s2 + ConnectedTextures.I[0x6A ^ 0x2D] + s2 + ConnectedTextures.I[0xE9 ^ 0xA1]);
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static void updateIconEmpty(final TextureMap textureMap) {
    }
    
    private static void addToList(final ConnectedProperties connectedProperties, final List list, final int i) {
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i >= list.size()) {
            list.add(null);
        }
        List<ConnectedProperties> list2 = list.get(i);
        if (list2 == null) {
            list2 = new ArrayList<ConnectedProperties>();
            list.set(i, list2);
        }
        list2.add(connectedProperties);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = ConnectedTextures.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xC2 ^ 0xC4);
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x4B ^ 0x4F);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x8C ^ 0x89);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return ConnectedTextures.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    private static TextureAtlasSprite getConnectedTextureTop(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        int n4 = "".length();
        switch (n) {
            case 0: {
                if (n2 == " ".length() || n2 == 0) {
                    return null;
                }
                n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3) ? 1 : 0);
                "".length();
                if (4 < 1) {
                    throw null;
                }
                break;
            }
            case 1: {
                if (n2 == "   ".length() || n2 == "  ".length()) {
                    return null;
                }
                n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3) ? 1 : 0);
                "".length();
                if (0 < 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                if (n2 == (0x65 ^ 0x60) || n2 == (0x9C ^ 0x98)) {
                    return null;
                }
                n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3) ? 1 : 0);
                break;
            }
        }
        if (n4 != 0) {
            return connectedProperties.tileIcons["".length()];
        }
        return null;
    }
    
    public static int getReversePaneTextureIndex(final int n) {
        final int n2 = n % (0x50 ^ 0x40);
        int n3;
        if (n2 == " ".length()) {
            n3 = n + "  ".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (n2 == "   ".length()) {
            n3 = n - "  ".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n3 = n;
        }
        return n3;
    }
    
    private static TextureAtlasSprite getNeighbourIcon(final IBlockAccess blockAccess, final BlockPos blockPos, IBlockState actualState, final int n) {
        actualState = actualState.getBlock().getActualState(actualState, blockAccess, blockPos);
        final IBakedModel modelForState = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(actualState);
        if (modelForState == null) {
            return null;
        }
        final EnumFacing facing = getFacing(n);
        final List<BakedQuad> faceQuads = modelForState.getFaceQuads(facing);
        if (faceQuads.size() > 0) {
            return faceQuads.get("".length()).getSprite();
        }
        final List<BakedQuad> generalQuads = modelForState.getGeneralQuads();
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < generalQuads.size()) {
            final BakedQuad bakedQuad = generalQuads.get(i);
            if (bakedQuad.getFace() == facing) {
                return bakedQuad.getSprite();
            }
            ++i;
        }
        return null;
    }
    
    private static TextureAtlasSprite getConnectedTextureHorizontal(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        int n4 = "".length();
        int n5 = "".length();
        Label_0762: {
            switch (n) {
                case 0: {
                    switch (n2) {
                        case 0:
                        case 1: {
                            return null;
                        }
                        case 2: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 3: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (false) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 4: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (0 >= 4) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 5: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            break;
                        }
                    }
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    switch (n2) {
                        case 0: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (0 >= 2) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 1: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (4 <= 3) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 2:
                        case 3: {
                            return null;
                        }
                        case 4: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (0 == 3) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 5: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            break;
                        }
                    }
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    switch (n2) {
                        case 0: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (0 < -1) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 1: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (3 >= 4) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 2: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (-1 >= 4) {
                                throw null;
                            }
                            break Label_0762;
                        }
                        case 3: {
                            n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3) ? 1 : 0);
                            "".length();
                            if (-1 == 4) {
                                throw null;
                            }
                            break Label_0762;
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
        "   ".length();
        int n6;
        if (n4 != 0) {
            if (n5 != 0) {
                n6 = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n6 = "  ".length();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
        }
        else if (n5 != 0) {
            n6 = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            n6 = "   ".length();
        }
        return connectedProperties.tileIcons[n6];
    }
    
    private static TextureAtlasSprite getConnectedTextureVertical(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        int n4 = "".length();
        int n5 = "".length();
        switch (n) {
            case 0: {
                if (n2 == " ".length() || n2 == 0) {
                    return null;
                }
                n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n2, textureAtlasSprite, n3) ? 1 : 0);
                n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n2, textureAtlasSprite, n3) ? 1 : 0);
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 1: {
                if (n2 == "   ".length() || n2 == "  ".length()) {
                    return null;
                }
                n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n2, textureAtlasSprite, n3) ? 1 : 0);
                n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n2, textureAtlasSprite, n3) ? 1 : 0);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                if (n2 == (0x66 ^ 0x63) || n2 == (0xBB ^ 0xBF)) {
                    return null;
                }
                n4 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n2, textureAtlasSprite, n3) ? 1 : 0);
                n5 = (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n2, textureAtlasSprite, n3) ? 1 : 0);
                break;
            }
        }
        "   ".length();
        int n6;
        if (n4 != 0) {
            if (n5 != 0) {
                n6 = " ".length();
                "".length();
                if (4 == 2) {
                    throw null;
                }
            }
            else {
                n6 = "  ".length();
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
        }
        else if (n5 != 0) {
            n6 = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n6 = "   ".length();
        }
        return connectedProperties.tileIcons[n6];
    }
    
    private static TextureAtlasSprite getConnectedTextureRepeat(final ConnectedProperties connectedProperties, final BlockPos blockPos, final int n) {
        if (connectedProperties.tileIcons.length == " ".length()) {
            return connectedProperties.tileIcons["".length()];
        }
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        int length = "".length();
        int length2 = "".length();
        switch (n) {
            case 0: {
                length = x;
                length2 = z;
                "".length();
                if (1 < 1) {
                    throw null;
                }
                break;
            }
            case 1: {
                length = x;
                length2 = z;
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
                break;
            }
            case 2: {
                length = -x - " ".length();
                length2 = -y;
                "".length();
                if (2 == 4) {
                    throw null;
                }
                break;
            }
            case 3: {
                length = x;
                length2 = -y;
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 4: {
                length = z;
                length2 = -y;
                "".length();
                if (4 == 2) {
                    throw null;
                }
                break;
            }
            case 5: {
                length = -z - " ".length();
                length2 = -y;
                break;
            }
        }
        int n2 = length % connectedProperties.width;
        int n3 = length2 % connectedProperties.height;
        if (n2 < 0) {
            n2 += connectedProperties.width;
        }
        if (n3 < 0) {
            n3 += connectedProperties.height;
        }
        return connectedProperties.tileIcons[n3 * connectedProperties.width + n2];
    }
    
    private static List makePropertyList(final ConnectedProperties[][] array) {
        final ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        if (array != null) {
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < array.length) {
                final ConnectedProperties[] array2 = array[i];
                ArrayList list2 = null;
                if (array2 != null) {
                    list2 = new ArrayList(Arrays.asList(array2));
                }
                list.add(list2);
                ++i;
            }
        }
        return list;
    }
    
    public static int getSide(final EnumFacing enumFacing) {
        if (enumFacing == null) {
            return -" ".length();
        }
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 1: {
                return "".length();
            }
            case 2: {
                return " ".length();
            }
            case 6: {
                return 0x84 ^ 0x81;
            }
            case 5: {
                return 0x96 ^ 0x92;
            }
            case 3: {
                return "  ".length();
            }
            case 4: {
                return "   ".length();
            }
            default: {
                return -" ".length();
            }
        }
    }
    
    static {
        I();
        Z_AXIS = " ".length();
        Y_NEG_DOWN = "".length();
        Y_POS_UP = " ".length();
        Z_NEG_NORTH = "  ".length();
        X_NEG_WEST = (0x8F ^ 0x8B);
        X_POS_EAST = (0xE ^ 0xB);
        X_AXIS = "  ".length();
        Z_POS_SOUTH = "   ".length();
        Y_AXIS = "".length();
        ConnectedTextures.spriteQuadMaps = null;
        ConnectedTextures.blockProperties = null;
        ConnectedTextures.tileProperties = null;
        ConnectedTextures.multipass = ("".length() != 0);
        final String[] propSuffixes2 = new String[0x1A ^ 0x1];
        propSuffixes2["".length()] = ConnectedTextures.I["".length()];
        propSuffixes2[" ".length()] = ConnectedTextures.I[" ".length()];
        propSuffixes2["  ".length()] = ConnectedTextures.I["  ".length()];
        propSuffixes2["   ".length()] = ConnectedTextures.I["   ".length()];
        propSuffixes2[0xBC ^ 0xB8] = ConnectedTextures.I[0x56 ^ 0x52];
        propSuffixes2[0x94 ^ 0x91] = ConnectedTextures.I[0x31 ^ 0x34];
        propSuffixes2[0x68 ^ 0x6E] = ConnectedTextures.I[0xB1 ^ 0xB7];
        propSuffixes2[0x34 ^ 0x33] = ConnectedTextures.I[0x82 ^ 0x85];
        propSuffixes2[0x61 ^ 0x69] = ConnectedTextures.I[0x5E ^ 0x56];
        propSuffixes2[0x70 ^ 0x79] = ConnectedTextures.I[0xA8 ^ 0xA1];
        propSuffixes2[0xAF ^ 0xA5] = ConnectedTextures.I[0x6F ^ 0x65];
        propSuffixes2[0x76 ^ 0x7D] = ConnectedTextures.I[0x0 ^ 0xB];
        propSuffixes2[0x2D ^ 0x21] = ConnectedTextures.I[0x3A ^ 0x36];
        propSuffixes2[0x8B ^ 0x86] = ConnectedTextures.I[0x36 ^ 0x3B];
        propSuffixes2[0x6A ^ 0x64] = ConnectedTextures.I[0x25 ^ 0x2B];
        propSuffixes2[0x91 ^ 0x9E] = ConnectedTextures.I[0x4A ^ 0x45];
        propSuffixes2[0x6E ^ 0x7E] = ConnectedTextures.I[0x7A ^ 0x6A];
        propSuffixes2[0xA4 ^ 0xB5] = ConnectedTextures.I[0x73 ^ 0x62];
        propSuffixes2[0x8A ^ 0x98] = ConnectedTextures.I[0x32 ^ 0x20];
        propSuffixes2[0xD3 ^ 0xC0] = ConnectedTextures.I[0x47 ^ 0x54];
        propSuffixes2[0x5 ^ 0x11] = ConnectedTextures.I[0x9E ^ 0x8A];
        propSuffixes2[0xA7 ^ 0xB2] = ConnectedTextures.I[0xB7 ^ 0xA2];
        propSuffixes2[0x28 ^ 0x3E] = ConnectedTextures.I[0x30 ^ 0x26];
        propSuffixes2[0x5A ^ 0x4D] = ConnectedTextures.I[0x7C ^ 0x6B];
        propSuffixes2[0xA4 ^ 0xBC] = ConnectedTextures.I[0xD8 ^ 0xC0];
        propSuffixes2[0xB5 ^ 0xAC] = ConnectedTextures.I[0x61 ^ 0x78];
        propSuffixes2[0x8F ^ 0x95] = ConnectedTextures.I[0x5A ^ 0x40];
        propSuffixes = propSuffixes2;
        final int[] ctmIndexes2 = new int[0x58 ^ 0x18];
        ctmIndexes2[" ".length()] = " ".length();
        ctmIndexes2["  ".length()] = "  ".length();
        ctmIndexes2["   ".length()] = "   ".length();
        ctmIndexes2[0x86 ^ 0x82] = (0xA2 ^ 0xA6);
        ctmIndexes2[0xC ^ 0x9] = (0xB0 ^ 0xB5);
        ctmIndexes2[0x55 ^ 0x53] = (0xBF ^ 0xB9);
        ctmIndexes2[0x82 ^ 0x85] = (0x86 ^ 0x81);
        ctmIndexes2[0x8 ^ 0x0] = (0xB ^ 0x3);
        ctmIndexes2[0x65 ^ 0x6C] = (0xA1 ^ 0xA8);
        ctmIndexes2[0x86 ^ 0x8C] = (0xB9 ^ 0xB3);
        ctmIndexes2[0x48 ^ 0x43] = (0x7C ^ 0x77);
        ctmIndexes2[0xA8 ^ 0xB8] = (0x9A ^ 0x96);
        ctmIndexes2[0x71 ^ 0x60] = (0x9E ^ 0x93);
        ctmIndexes2[0x44 ^ 0x56] = (0x9D ^ 0x93);
        ctmIndexes2[0x1F ^ 0xC] = (0x9A ^ 0x95);
        ctmIndexes2[0x58 ^ 0x4C] = (0x70 ^ 0x60);
        ctmIndexes2[0x37 ^ 0x22] = (0x9C ^ 0x8D);
        ctmIndexes2[0x30 ^ 0x26] = (0x2B ^ 0x39);
        ctmIndexes2[0xA3 ^ 0xB4] = (0x4 ^ 0x17);
        ctmIndexes2[0x15 ^ 0xD] = (0x4B ^ 0x5F);
        ctmIndexes2[0xA5 ^ 0xBC] = (0xB3 ^ 0xA6);
        ctmIndexes2[0xB9 ^ 0xA3] = (0x1B ^ 0xD);
        ctmIndexes2[0x43 ^ 0x58] = (0xB ^ 0x1C);
        ctmIndexes2[0x28 ^ 0x8] = (0x92 ^ 0x8A);
        ctmIndexes2[0x86 ^ 0xA7] = (0x4A ^ 0x53);
        ctmIndexes2[0x8C ^ 0xAE] = (0x4E ^ 0x54);
        ctmIndexes2[0x23 ^ 0x0] = (0x0 ^ 0x1B);
        ctmIndexes2[0x95 ^ 0xB1] = (0x1E ^ 0x2);
        ctmIndexes2[0x9C ^ 0xB9] = (0x3D ^ 0x20);
        ctmIndexes2[0x2D ^ 0xB] = (0x45 ^ 0x5B);
        ctmIndexes2[0x5A ^ 0x7D] = (0xA8 ^ 0xB7);
        ctmIndexes2[0x84 ^ 0xAC] = (0x40 ^ 0x60);
        ctmIndexes2[0x76 ^ 0x5F] = (0x17 ^ 0x36);
        ctmIndexes2[0x7C ^ 0x56] = (0x89 ^ 0xAB);
        ctmIndexes2[0x90 ^ 0xBB] = (0x46 ^ 0x65);
        ctmIndexes2[0x8E ^ 0xBE] = (0xBF ^ 0x9B);
        ctmIndexes2[0x55 ^ 0x64] = (0x50 ^ 0x75);
        ctmIndexes2[0x5C ^ 0x6E] = (0x43 ^ 0x65);
        ctmIndexes2[0x3B ^ 0x8] = (0x7C ^ 0x5B);
        ctmIndexes2[0xB4 ^ 0x80] = (0x81 ^ 0xA9);
        ctmIndexes2[0xF2 ^ 0xC7] = (0x31 ^ 0x18);
        ctmIndexes2[0xAB ^ 0x9D] = (0x4C ^ 0x66);
        ctmIndexes2[0x1D ^ 0x2A] = (0x93 ^ 0xB8);
        ctmIndexes2[0x2C ^ 0x14] = (0xB6 ^ 0x9A);
        ctmIndexes2[0x18 ^ 0x21] = (0x78 ^ 0x55);
        ctmIndexes2[0xB4 ^ 0x8E] = (0x37 ^ 0x19);
        ctmIndexes = ctmIndexes2;
        AIR_DEFAULT_STATE = Blocks.air.getDefaultState();
        ConnectedTextures.emptySprite = null;
    }
    
    private static EnumFacing getFacing(final int n) {
        switch (n) {
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
            default: {
                return EnumFacing.UP;
            }
        }
    }
    
    private static BakedQuad getQuad(final TextureAtlasSprite textureAtlasSprite, final Block block, final IBlockState blockState, final BakedQuad bakedQuad) {
        if (ConnectedTextures.spriteQuadMaps == null) {
            return bakedQuad;
        }
        final int indexInMap = textureAtlasSprite.getIndexInMap();
        if (indexInMap >= 0 && indexInMap < ConnectedTextures.spriteQuadMaps.length) {
            Map<Object, BakedQuad> map = (Map<Object, BakedQuad>)ConnectedTextures.spriteQuadMaps[indexInMap];
            if (map == null) {
                map = (Map<Object, BakedQuad>)new IdentityHashMap<BakedQuad, BakedQuad>(" ".length());
                ConnectedTextures.spriteQuadMaps[indexInMap] = map;
            }
            BakedQuad spriteQuad = map.get(bakedQuad);
            if (spriteQuad == null) {
                spriteQuad = makeSpriteQuad(bakedQuad, textureAtlasSprite);
                map.put(bakedQuad, spriteQuad);
            }
            return spriteQuad;
        }
        return bakedQuad;
    }
    
    private static ConnectedProperties[][] propertyListToArray(final List list) {
        final ConnectedProperties[][] array = new ConnectedProperties[list.size()][];
        int i = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i < list.size()) {
            final List list2 = list.get(i);
            if (list2 != null) {
                array[i] = (ConnectedProperties[])list2.toArray(new ConnectedProperties[list2.size()]);
            }
            ++i;
        }
        return array;
    }
    
    private static BakedQuad makeSpriteQuad(final BakedQuad bakedQuad, final TextureAtlasSprite textureAtlasSprite) {
        final int[] array = bakedQuad.getVertexData().clone();
        final TextureAtlasSprite sprite = bakedQuad.getSprite();
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < (0x64 ^ 0x60)) {
            fixVertex(array, i, sprite, textureAtlasSprite);
            ++i;
        }
        return new BakedQuad(array, bakedQuad.getTintIndex(), bakedQuad.getFace(), textureAtlasSprite);
    }
    
    private static TextureAtlasSprite getConnectedTextureCtm(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final TextureAtlasSprite textureAtlasSprite, final int n2, final RenderEnv renderEnv) {
        final boolean[] borderFlags = renderEnv.getBorderFlags();
        switch (n) {
            case 0: {
                borderFlags["".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n, textureAtlasSprite, n2);
                borderFlags[" ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n, textureAtlasSprite, n2);
                borderFlags["  ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n, textureAtlasSprite, n2);
                borderFlags["   ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n, textureAtlasSprite, n2);
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 1: {
                borderFlags["".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n, textureAtlasSprite, n2);
                borderFlags[" ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n, textureAtlasSprite, n2);
                borderFlags["  ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n, textureAtlasSprite, n2);
                borderFlags["   ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n, textureAtlasSprite, n2);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 2: {
                borderFlags["".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n, textureAtlasSprite, n2);
                borderFlags[" ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n, textureAtlasSprite, n2);
                borderFlags["  ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n, textureAtlasSprite, n2);
                borderFlags["   ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n, textureAtlasSprite, n2);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                break;
            }
            case 3: {
                borderFlags["".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west(), n, textureAtlasSprite, n2);
                borderFlags[" ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east(), n, textureAtlasSprite, n2);
                borderFlags["  ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n, textureAtlasSprite, n2);
                borderFlags["   ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n, textureAtlasSprite, n2);
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 4: {
                borderFlags["".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n, textureAtlasSprite, n2);
                borderFlags[" ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n, textureAtlasSprite, n2);
                borderFlags["  ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n, textureAtlasSprite, n2);
                borderFlags["   ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n, textureAtlasSprite, n2);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                break;
            }
            case 5: {
                borderFlags["".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.south(), n, textureAtlasSprite, n2);
                borderFlags[" ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.north(), n, textureAtlasSprite, n2);
                borderFlags["  ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down(), n, textureAtlasSprite, n2);
                borderFlags["   ".length()] = isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up(), n, textureAtlasSprite, n2);
                break;
            }
        }
        int n3 = "".length();
        final boolean b = borderFlags["".length()];
        int n4;
        if (borderFlags[" ".length()]) {
            n4 = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        final boolean b2 = ((b ? 1 : 0) & n4) != 0x0;
        int n5;
        if (borderFlags["  ".length()]) {
            n5 = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n5 = " ".length();
        }
        final boolean b3 = ((b2 ? 1 : 0) & n5) != 0x0;
        int n6;
        if (borderFlags["   ".length()]) {
            n6 = "".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            n6 = " ".length();
        }
        if (((b3 ? 1 : 0) & n6) != 0x0) {
            n3 = "   ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            int n7;
            if (borderFlags["".length()]) {
                n7 = "".length();
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else {
                n7 = " ".length();
            }
            final boolean b4 = (n7 & (borderFlags[" ".length()] ? 1 : 0)) != 0x0;
            int n8;
            if (borderFlags["  ".length()]) {
                n8 = "".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                n8 = " ".length();
            }
            final boolean b5 = ((b4 ? 1 : 0) & n8) != 0x0;
            int n9;
            if (borderFlags["   ".length()]) {
                n9 = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                n9 = " ".length();
            }
            if (((b5 ? 1 : 0) & n9) != 0x0) {
                n3 = " ".length();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                int n10;
                if (borderFlags["".length()]) {
                    n10 = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n10 = " ".length();
                }
                int n11;
                if (borderFlags[" ".length()]) {
                    n11 = "".length();
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                else {
                    n11 = " ".length();
                }
                final int n12 = n10 & n11 & (borderFlags["  ".length()] ? 1 : 0);
                int n13;
                if (borderFlags["   ".length()]) {
                    n13 = "".length();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    n13 = " ".length();
                }
                if ((n12 & n13) != 0x0) {
                    n3 = (0x25 ^ 0x29);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    int n14;
                    if (borderFlags["".length()]) {
                        n14 = "".length();
                        "".length();
                        if (2 < 1) {
                            throw null;
                        }
                    }
                    else {
                        n14 = " ".length();
                    }
                    int n15;
                    if (borderFlags[" ".length()]) {
                        n15 = "".length();
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                    }
                    else {
                        n15 = " ".length();
                    }
                    final int n16 = n14 & n15;
                    int n17;
                    if (borderFlags["  ".length()]) {
                        n17 = "".length();
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                    }
                    else {
                        n17 = " ".length();
                    }
                    if ((n16 & n17 & (borderFlags["   ".length()] ? 1 : 0)) != 0x0) {
                        n3 = (0x66 ^ 0x42);
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        final boolean b6 = borderFlags["".length()] & borderFlags[" ".length()];
                        int n18;
                        if (borderFlags["  ".length()]) {
                            n18 = "".length();
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            n18 = " ".length();
                        }
                        final boolean b7 = ((b6 ? 1 : 0) & n18) != 0x0;
                        int n19;
                        if (borderFlags["   ".length()]) {
                            n19 = "".length();
                            "".length();
                            if (3 == 4) {
                                throw null;
                            }
                        }
                        else {
                            n19 = " ".length();
                        }
                        if (((b7 ? 1 : 0) & n19) != 0x0) {
                            n3 = "  ".length();
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                        }
                        else {
                            int n20;
                            if (borderFlags["".length()]) {
                                n20 = "".length();
                                "".length();
                                if (3 == 0) {
                                    throw null;
                                }
                            }
                            else {
                                n20 = " ".length();
                            }
                            int n21;
                            if (borderFlags[" ".length()]) {
                                n21 = "".length();
                                "".length();
                                if (1 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                n21 = " ".length();
                            }
                            if ((n20 & n21 & (borderFlags["  ".length()] ? 1 : 0) & (borderFlags["   ".length()] ? 1 : 0)) != 0x0) {
                                n3 = (0x74 ^ 0x6C);
                                "".length();
                                if (0 >= 4) {
                                    throw null;
                                }
                            }
                            else {
                                final boolean b8 = borderFlags["".length()];
                                int n22;
                                if (borderFlags[" ".length()]) {
                                    n22 = "".length();
                                    "".length();
                                    if (-1 >= 0) {
                                        throw null;
                                    }
                                }
                                else {
                                    n22 = " ".length();
                                }
                                final boolean b9 = ((b8 ? 1 : 0) & n22 & (borderFlags["  ".length()] ? 1 : 0)) != 0x0;
                                int n23;
                                if (borderFlags["   ".length()]) {
                                    n23 = "".length();
                                    "".length();
                                    if (3 < 3) {
                                        throw null;
                                    }
                                }
                                else {
                                    n23 = " ".length();
                                }
                                if (((b9 ? 1 : 0) & n23) != 0x0) {
                                    n3 = (0x4E ^ 0x41);
                                    "".length();
                                    if (4 <= 3) {
                                        throw null;
                                    }
                                }
                                else {
                                    final boolean b10 = borderFlags["".length()];
                                    int n24;
                                    if (borderFlags[" ".length()]) {
                                        n24 = "".length();
                                        "".length();
                                        if (3 <= -1) {
                                            throw null;
                                        }
                                    }
                                    else {
                                        n24 = " ".length();
                                    }
                                    final boolean b11 = ((b10 ? 1 : 0) & n24) != 0x0;
                                    int n25;
                                    if (borderFlags["  ".length()]) {
                                        n25 = "".length();
                                        "".length();
                                        if (2 >= 3) {
                                            throw null;
                                        }
                                    }
                                    else {
                                        n25 = " ".length();
                                    }
                                    if (((b11 ? 1 : 0) & n25 & (borderFlags["   ".length()] ? 1 : 0)) != 0x0) {
                                        n3 = (0x8E ^ 0xA9);
                                        "".length();
                                        if (-1 != -1) {
                                            throw null;
                                        }
                                    }
                                    else {
                                        int n26;
                                        if (borderFlags["".length()]) {
                                            n26 = "".length();
                                            "".length();
                                            if (2 <= 0) {
                                                throw null;
                                            }
                                        }
                                        else {
                                            n26 = " ".length();
                                        }
                                        final boolean b12 = (n26 & (borderFlags[" ".length()] ? 1 : 0) & (borderFlags["  ".length()] ? 1 : 0)) != 0x0;
                                        int n27;
                                        if (borderFlags["   ".length()]) {
                                            n27 = "".length();
                                            "".length();
                                            if (1 == 3) {
                                                throw null;
                                            }
                                        }
                                        else {
                                            n27 = " ".length();
                                        }
                                        if (((b12 ? 1 : 0) & n27) != 0x0) {
                                            n3 = (0x69 ^ 0x64);
                                            "".length();
                                            if (2 >= 3) {
                                                throw null;
                                            }
                                        }
                                        else {
                                            int n28;
                                            if (borderFlags["".length()]) {
                                                n28 = "".length();
                                                "".length();
                                                if (1 <= 0) {
                                                    throw null;
                                                }
                                            }
                                            else {
                                                n28 = " ".length();
                                            }
                                            final boolean b13 = (n28 & (borderFlags[" ".length()] ? 1 : 0)) != 0x0;
                                            int n29;
                                            if (borderFlags["  ".length()]) {
                                                n29 = "".length();
                                                "".length();
                                                if (!true) {
                                                    throw null;
                                                }
                                            }
                                            else {
                                                n29 = " ".length();
                                            }
                                            if (((b13 ? 1 : 0) & n29 & (borderFlags["   ".length()] ? 1 : 0)) != 0x0) {
                                                n3 = (0xA0 ^ 0x85);
                                                "".length();
                                                if (2 == 1) {
                                                    throw null;
                                                }
                                            }
                                            else {
                                                int n30;
                                                if (borderFlags["".length()]) {
                                                    n30 = "".length();
                                                    "".length();
                                                    if (3 <= 0) {
                                                        throw null;
                                                    }
                                                }
                                                else {
                                                    n30 = " ".length();
                                                }
                                                if ((n30 & (borderFlags[" ".length()] ? 1 : 0) & (borderFlags["  ".length()] ? 1 : 0) & (borderFlags["   ".length()] ? 1 : 0)) != 0x0) {
                                                    n3 = (0x7D ^ 0x64);
                                                    "".length();
                                                    if (1 >= 2) {
                                                        throw null;
                                                    }
                                                }
                                                else {
                                                    final boolean b14 = borderFlags["".length()];
                                                    int n31;
                                                    if (borderFlags[" ".length()]) {
                                                        n31 = "".length();
                                                        "".length();
                                                        if (2 <= 0) {
                                                            throw null;
                                                        }
                                                    }
                                                    else {
                                                        n31 = " ".length();
                                                    }
                                                    if (((b14 ? 1 : 0) & n31 & (borderFlags["  ".length()] ? 1 : 0) & (borderFlags["   ".length()] ? 1 : 0)) != 0x0) {
                                                        n3 = (0x94 ^ 0x8F);
                                                        "".length();
                                                        if (1 == -1) {
                                                            throw null;
                                                        }
                                                    }
                                                    else {
                                                        final boolean b15 = borderFlags["".length()] & borderFlags[" ".length()];
                                                        int n32;
                                                        if (borderFlags["  ".length()]) {
                                                            n32 = "".length();
                                                            "".length();
                                                            if (3 == 1) {
                                                                throw null;
                                                            }
                                                        }
                                                        else {
                                                            n32 = " ".length();
                                                        }
                                                        if (((b15 ? 1 : 0) & n32 & (borderFlags["   ".length()] ? 1 : 0)) != 0x0) {
                                                            n3 = (0x45 ^ 0x63);
                                                            "".length();
                                                            if (0 == 3) {
                                                                throw null;
                                                            }
                                                        }
                                                        else {
                                                            final boolean b16 = borderFlags["".length()] & borderFlags[" ".length()] & borderFlags["  ".length()];
                                                            int n33;
                                                            if (borderFlags["   ".length()]) {
                                                                n33 = "".length();
                                                                "".length();
                                                                if (3 <= -1) {
                                                                    throw null;
                                                                }
                                                            }
                                                            else {
                                                                n33 = " ".length();
                                                            }
                                                            if (((b16 ? 1 : 0) & n33) != 0x0) {
                                                                n3 = (0x82 ^ 0x8C);
                                                                "".length();
                                                                if (0 >= 4) {
                                                                    throw null;
                                                                }
                                                            }
                                                            else if (borderFlags["".length()] & borderFlags[" ".length()] & borderFlags["  ".length()] & borderFlags["   ".length()]) {
                                                                n3 = (0x5B ^ 0x41);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (n3 == 0) {
            return connectedProperties.tileIcons[n3];
        }
        if (!Config.isConnectedTexturesFancy()) {
            return connectedProperties.tileIcons[n3];
        }
        switch (n) {
            case 0: {
                final boolean[] array = borderFlags;
                final int length = "".length();
                int n34;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().north(), n, textureAtlasSprite, n2)) {
                    n34 = "".length();
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                }
                else {
                    n34 = " ".length();
                }
                array[length] = (n34 != 0);
                final boolean[] array2 = borderFlags;
                final int length2 = " ".length();
                int n35;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().north(), n, textureAtlasSprite, n2)) {
                    n35 = "".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    n35 = " ".length();
                }
                array2[length2] = (n35 != 0);
                final boolean[] array3 = borderFlags;
                final int length3 = "  ".length();
                int n36;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().south(), n, textureAtlasSprite, n2)) {
                    n36 = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n36 = " ".length();
                }
                array3[length3] = (n36 != 0);
                final boolean[] array4 = borderFlags;
                final int length4 = "   ".length();
                int n37;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().south(), n, textureAtlasSprite, n2)) {
                    n37 = "".length();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else {
                    n37 = " ".length();
                }
                array4[length4] = (n37 != 0);
                "".length();
                if (2 < 2) {
                    throw null;
                }
                break;
            }
            case 1: {
                final boolean[] array5 = borderFlags;
                final int length5 = "".length();
                int n38;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().south(), n, textureAtlasSprite, n2)) {
                    n38 = "".length();
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else {
                    n38 = " ".length();
                }
                array5[length5] = (n38 != 0);
                final boolean[] array6 = borderFlags;
                final int length6 = " ".length();
                int n39;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().south(), n, textureAtlasSprite, n2)) {
                    n39 = "".length();
                    "".length();
                    if (-1 == 3) {
                        throw null;
                    }
                }
                else {
                    n39 = " ".length();
                }
                array6[length6] = (n39 != 0);
                final boolean[] array7 = borderFlags;
                final int length7 = "  ".length();
                int n40;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().north(), n, textureAtlasSprite, n2)) {
                    n40 = "".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else {
                    n40 = " ".length();
                }
                array7[length7] = (n40 != 0);
                final boolean[] array8 = borderFlags;
                final int length8 = "   ".length();
                int n41;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().north(), n, textureAtlasSprite, n2)) {
                    n41 = "".length();
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                }
                else {
                    n41 = " ".length();
                }
                array8[length8] = (n41 != 0);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                final boolean[] array9 = borderFlags;
                final int length9 = "".length();
                int n42;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().down(), n, textureAtlasSprite, n2)) {
                    n42 = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n42 = " ".length();
                }
                array9[length9] = (n42 != 0);
                final boolean[] array10 = borderFlags;
                final int length10 = " ".length();
                int n43;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().down(), n, textureAtlasSprite, n2)) {
                    n43 = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n43 = " ".length();
                }
                array10[length10] = (n43 != 0);
                final boolean[] array11 = borderFlags;
                final int length11 = "  ".length();
                int n44;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().up(), n, textureAtlasSprite, n2)) {
                    n44 = "".length();
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
                else {
                    n44 = " ".length();
                }
                array11[length11] = (n44 != 0);
                final boolean[] array12 = borderFlags;
                final int length12 = "   ".length();
                int n45;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().up(), n, textureAtlasSprite, n2)) {
                    n45 = "".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else {
                    n45 = " ".length();
                }
                array12[length12] = (n45 != 0);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 3: {
                final boolean[] array13 = borderFlags;
                final int length13 = "".length();
                int n46;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().down(), n, textureAtlasSprite, n2)) {
                    n46 = "".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else {
                    n46 = " ".length();
                }
                array13[length13] = (n46 != 0);
                final boolean[] array14 = borderFlags;
                final int length14 = " ".length();
                int n47;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().down(), n, textureAtlasSprite, n2)) {
                    n47 = "".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    n47 = " ".length();
                }
                array14[length14] = (n47 != 0);
                final boolean[] array15 = borderFlags;
                final int length15 = "  ".length();
                int n48;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.east().up(), n, textureAtlasSprite, n2)) {
                    n48 = "".length();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else {
                    n48 = " ".length();
                }
                array15[length15] = (n48 != 0);
                final boolean[] array16 = borderFlags;
                final int length16 = "   ".length();
                int n49;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.west().up(), n, textureAtlasSprite, n2)) {
                    n49 = "".length();
                    "".length();
                    if (2 == -1) {
                        throw null;
                    }
                }
                else {
                    n49 = " ".length();
                }
                array16[length16] = (n49 != 0);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                final boolean[] array17 = borderFlags;
                final int length17 = "".length();
                int n50;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down().south(), n, textureAtlasSprite, n2)) {
                    n50 = "".length();
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else {
                    n50 = " ".length();
                }
                array17[length17] = (n50 != 0);
                final boolean[] array18 = borderFlags;
                final int length18 = " ".length();
                int n51;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down().north(), n, textureAtlasSprite, n2)) {
                    n51 = "".length();
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                }
                else {
                    n51 = " ".length();
                }
                array18[length18] = (n51 != 0);
                final boolean[] array19 = borderFlags;
                final int length19 = "  ".length();
                int n52;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up().south(), n, textureAtlasSprite, n2)) {
                    n52 = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n52 = " ".length();
                }
                array19[length19] = (n52 != 0);
                final boolean[] array20 = borderFlags;
                final int length20 = "   ".length();
                int n53;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up().north(), n, textureAtlasSprite, n2)) {
                    n53 = "".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else {
                    n53 = " ".length();
                }
                array20[length20] = (n53 != 0);
                "".length();
                if (1 == 2) {
                    throw null;
                }
                break;
            }
            case 5: {
                final boolean[] array21 = borderFlags;
                final int length21 = "".length();
                int n54;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down().north(), n, textureAtlasSprite, n2)) {
                    n54 = "".length();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else {
                    n54 = " ".length();
                }
                array21[length21] = (n54 != 0);
                final boolean[] array22 = borderFlags;
                final int length22 = " ".length();
                int n55;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.down().south(), n, textureAtlasSprite, n2)) {
                    n55 = "".length();
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else {
                    n55 = " ".length();
                }
                array22[length22] = (n55 != 0);
                final boolean[] array23 = borderFlags;
                final int length23 = "  ".length();
                int n56;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up().north(), n, textureAtlasSprite, n2)) {
                    n56 = "".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    n56 = " ".length();
                }
                array23[length23] = (n56 != 0);
                final boolean[] array24 = borderFlags;
                final int length24 = "   ".length();
                int n57;
                if (isNeighbour(connectedProperties, blockAccess, blockState, blockPos.up().south(), n, textureAtlasSprite, n2)) {
                    n57 = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n57 = " ".length();
                }
                array24[length24] = (n57 != 0);
                break;
            }
        }
        if (n3 == (0xCE ^ 0xC3) && borderFlags["".length()]) {
            n3 = (0xBA ^ 0xBE);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (n3 == (0x64 ^ 0x6B) && borderFlags[" ".length()]) {
            n3 = (0x8F ^ 0x8A);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else if (n3 == (0x45 ^ 0x60) && borderFlags["  ".length()]) {
            n3 = (0x44 ^ 0x54);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (n3 == (0xAA ^ 0x8D) && borderFlags["   ".length()]) {
            n3 = (0x36 ^ 0x27);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (n3 == (0xAA ^ 0xA4) && borderFlags["".length()] && borderFlags[" ".length()]) {
            n3 = (0x7C ^ 0x7B);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else if (n3 == (0x46 ^ 0x5F) && borderFlags["".length()] && borderFlags["  ".length()]) {
            n3 = (0xA0 ^ 0xA6);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (n3 == (0xD ^ 0x16) && borderFlags["   ".length()] && borderFlags[" ".length()]) {
            n3 = (0x43 ^ 0x50);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (n3 == (0x91 ^ 0xB7) && borderFlags["   ".length()] && borderFlags["  ".length()]) {
            n3 = (0x15 ^ 0x7);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n3 == (0x7 ^ 0x9) && !borderFlags["".length()] && borderFlags[" ".length()]) {
            n3 = (0x5B ^ 0x44);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (n3 == (0x48 ^ 0x51) && borderFlags["".length()] && !borderFlags["  ".length()]) {
            n3 = (0x75 ^ 0x6B);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n3 == (0x9C ^ 0x87) && !borderFlags["   ".length()] && borderFlags[" ".length()]) {
            n3 = (0x16 ^ 0x3F);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (n3 == (0x5F ^ 0x79) && borderFlags["   ".length()] && !borderFlags["  ".length()]) {
            n3 = (0xE9 ^ 0xC1);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (n3 == (0x1F ^ 0x11) && borderFlags["".length()] && !borderFlags[" ".length()]) {
            n3 = (0x26 ^ 0x3B);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (n3 == (0x3B ^ 0x22) && !borderFlags["".length()] && borderFlags["  ".length()]) {
            n3 = (0x20 ^ 0x3C);
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else if (n3 == (0xB1 ^ 0xAA) && borderFlags["   ".length()] && !borderFlags[" ".length()]) {
            n3 = (0xB ^ 0x20);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n3 == (0x2B ^ 0xD) && !borderFlags["   ".length()] && borderFlags["  ".length()]) {
            n3 = (0x2F ^ 0x5);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (n3 == (0x16 ^ 0xC) && borderFlags["".length()] && borderFlags[" ".length()] && borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0x49 ^ 0x67);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (n3 == (0x37 ^ 0x2D) && !borderFlags["".length()] && borderFlags[" ".length()] && borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0xC9 ^ 0xC0);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if (n3 == (0xD8 ^ 0xC2) && borderFlags["".length()] && !borderFlags[" ".length()] && borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0x10 ^ 0x5);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (n3 == (0x6D ^ 0x77) && borderFlags["".length()] && borderFlags[" ".length()] && !borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0x7D ^ 0x75);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (n3 == (0x76 ^ 0x6C) && borderFlags["".length()] && borderFlags[" ".length()] && borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            n3 = (0x2B ^ 0x3F);
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else if (n3 == (0x42 ^ 0x58) && borderFlags["".length()] && borderFlags[" ".length()] && !borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            n3 = (0x65 ^ 0x6E);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (n3 == (0x3 ^ 0x19) && !borderFlags["".length()] && !borderFlags[" ".length()] && borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0x7B ^ 0x6D);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n3 == (0x4C ^ 0x56) && !borderFlags["".length()] && borderFlags[" ".length()] && !borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0x17 ^ 0x0);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (n3 == (0x55 ^ 0x4F) && borderFlags["".length()] && !borderFlags[" ".length()] && borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            n3 = (0x26 ^ 0x2C);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (n3 == (0x82 ^ 0x98) && borderFlags["".length()] && !borderFlags[" ".length()] && !borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0xA ^ 0x28);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (n3 == (0x47 ^ 0x5D) && !borderFlags["".length()] && borderFlags[" ".length()] && borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            n3 = (0x50 ^ 0x73);
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else if (n3 == (0x30 ^ 0x2A) && borderFlags["".length()] && !borderFlags[" ".length()] && !borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            n3 = (0xBB ^ 0x9B);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else if (n3 == (0x70 ^ 0x6A) && !borderFlags["".length()] && borderFlags[" ".length()] && !borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            n3 = (0xE0 ^ 0xC1);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else if (n3 == (0x8A ^ 0x90) && !borderFlags["".length()] && !borderFlags[" ".length()] && borderFlags["  ".length()] && !borderFlags["   ".length()]) {
            n3 = (0xB5 ^ 0x99);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (n3 == (0x9D ^ 0x87) && !borderFlags["".length()] && !borderFlags[" ".length()] && !borderFlags["  ".length()] && borderFlags["   ".length()]) {
            n3 = (0x6C ^ 0x41);
        }
        return connectedProperties.tileIcons[n3];
    }
    
    private static TextureAtlasSprite getConnectedTextureHorizontalVertical(final ConnectedProperties connectedProperties, final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos, final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3) {
        final TextureAtlasSprite[] tileIcons = connectedProperties.tileIcons;
        final TextureAtlasSprite connectedTextureHorizontal = getConnectedTextureHorizontal(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        if (connectedTextureHorizontal != null && connectedTextureHorizontal != textureAtlasSprite && connectedTextureHorizontal != tileIcons["   ".length()]) {
            return connectedTextureHorizontal;
        }
        final TextureAtlasSprite connectedTextureVertical = getConnectedTextureVertical(connectedProperties, blockAccess, blockState, blockPos, n, n2, textureAtlasSprite, n3);
        TextureAtlasSprite textureAtlasSprite2;
        if (connectedTextureVertical == tileIcons["".length()]) {
            textureAtlasSprite2 = tileIcons[0x73 ^ 0x77];
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else if (connectedTextureVertical == tileIcons[" ".length()]) {
            textureAtlasSprite2 = tileIcons[0x21 ^ 0x24];
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (connectedTextureVertical == tileIcons["  ".length()]) {
            textureAtlasSprite2 = tileIcons[0x44 ^ 0x42];
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            textureAtlasSprite2 = connectedTextureVertical;
        }
        return textureAtlasSprite2;
    }
    
    private static String[] collectFiles(final IResourcePack resourcePack, final String s, final String s2) {
        if (resourcePack instanceof DefaultResourcePack) {
            return collectFilesDefault(resourcePack);
        }
        if (!(resourcePack instanceof AbstractResourcePack)) {
            return new String["".length()];
        }
        final File resourcePackFile = ResourceUtils.getResourcePackFile((AbstractResourcePack)resourcePack);
        String[] array;
        if (resourcePackFile == null) {
            array = new String["".length()];
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (resourcePackFile.isDirectory()) {
            array = collectFilesFolder(resourcePackFile, ConnectedTextures.I[0x2A ^ 0x2], s, s2);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else if (resourcePackFile.isFile()) {
            array = collectFilesZIP(resourcePackFile, s, s2);
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            array = new String["".length()];
        }
        return array;
    }
}
