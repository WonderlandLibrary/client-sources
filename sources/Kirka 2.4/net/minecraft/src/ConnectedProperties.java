/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import java.util.ArrayList;
import java.util.Properties;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.src.Config;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public class ConnectedProperties {
    public String name = null;
    public String basePath = null;
    public int[] matchBlocks = null;
    public String[] matchTiles = null;
    public int method = 0;
    public String[] tiles = null;
    public int connect = 0;
    public int faces = 63;
    public int[] metadatas = null;
    public BiomeGenBase[] biomes = null;
    public int minHeight = 0;
    public int maxHeight = 1024;
    public int renderPass = 0;
    public boolean innerSeams = false;
    public int width = 0;
    public int height = 0;
    public int[] weights = null;
    public int symmetry = 1;
    public int[] sumWeights = null;
    public int sumAllWeights = 1;
    public TextureAtlasSprite[] matchTileIcons = null;
    public TextureAtlasSprite[] tileIcons = null;
    public static final int METHOD_NONE = 0;
    public static final int METHOD_CTM = 1;
    public static final int METHOD_HORIZONTAL = 2;
    public static final int METHOD_TOP = 3;
    public static final int METHOD_RANDOM = 4;
    public static final int METHOD_REPEAT = 5;
    public static final int METHOD_VERTICAL = 6;
    public static final int METHOD_FIXED = 7;
    public static final int METHOD_HORIZONTAL_VERTICAL = 8;
    public static final int METHOD_VERTICAL_HORIZONTAL = 9;
    public static final int CONNECT_NONE = 0;
    public static final int CONNECT_BLOCK = 1;
    public static final int CONNECT_TILE = 2;
    public static final int CONNECT_MATERIAL = 3;
    public static final int CONNECT_UNKNOWN = 128;
    public static final int FACE_BOTTOM = 1;
    public static final int FACE_TOP = 2;
    public static final int FACE_NORTH = 4;
    public static final int FACE_SOUTH = 8;
    public static final int FACE_WEST = 16;
    public static final int FACE_EAST = 32;
    public static final int FACE_SIDES = 60;
    public static final int FACE_ALL = 63;
    public static final int FACE_UNKNOWN = 128;
    public static final int SYMMETRY_NONE = 1;
    public static final int SYMMETRY_OPPOSITE = 2;
    public static final int SYMMETRY_ALL = 6;
    public static final int SYMMETRY_UNKNOWN = 128;

    public ConnectedProperties(Properties props, String path) {
        this.name = ConnectedProperties.parseName(path);
        this.basePath = ConnectedProperties.parseBasePath(path);
        this.matchBlocks = ConnectedProperties.parseBlockIds(props.getProperty("matchBlocks"));
        this.matchTiles = this.parseMatchTiles(props.getProperty("matchTiles"));
        this.method = ConnectedProperties.parseMethod(props.getProperty("method"));
        this.tiles = this.parseTileNames(props.getProperty("tiles"));
        this.connect = ConnectedProperties.parseConnect(props.getProperty("connect"));
        this.faces = ConnectedProperties.parseFaces(props.getProperty("faces"));
        this.metadatas = ConnectedProperties.parseInts(props.getProperty("metadata"));
        this.biomes = ConnectedProperties.parseBiomes(props.getProperty("biomes"));
        this.minHeight = ConnectedProperties.parseInt(props.getProperty("minHeight"), -1);
        this.maxHeight = ConnectedProperties.parseInt(props.getProperty("maxHeight"), 1024);
        this.renderPass = ConnectedProperties.parseInt(props.getProperty("renderPass"));
        this.innerSeams = ConnectedProperties.parseBoolean(props.getProperty("innerSeams"));
        this.width = ConnectedProperties.parseInt(props.getProperty("width"));
        this.height = ConnectedProperties.parseInt(props.getProperty("height"));
        this.weights = ConnectedProperties.parseInts(props.getProperty("weights"));
        this.symmetry = ConnectedProperties.parseSymmetry(props.getProperty("symmetry"));
    }

    private String[] parseMatchTiles(String str) {
        if (str == null) {
            return null;
        }
        String[] names = Config.tokenize(str, " ");
        for (int i = 0; i < names.length; ++i) {
            String iconName = names[i];
            if (iconName.endsWith(".png")) {
                iconName = iconName.substring(0, iconName.length() - 4);
            }
            names[i] = iconName = TextureUtils.fixResourcePath(iconName, this.basePath);
        }
        return names;
    }

    private static String parseName(String path) {
        int pos2;
        String str = path;
        int pos = path.lastIndexOf(47);
        if (pos >= 0) {
            str = path.substring(pos + 1);
        }
        if ((pos2 = str.lastIndexOf(46)) >= 0) {
            str = str.substring(0, pos2);
        }
        return str;
    }

    private static String parseBasePath(String path) {
        int pos = path.lastIndexOf(47);
        return pos < 0 ? "" : path.substring(0, pos);
    }

    private static BiomeGenBase[] parseBiomes(String str) {
        if (str == null) {
            return null;
        }
        String[] biomeNames = Config.tokenize(str, " ");
        ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();
        for (int biomeArr = 0; biomeArr < biomeNames.length; ++biomeArr) {
            String biomeName = biomeNames[biomeArr];
            BiomeGenBase biome = ConnectedProperties.findBiome(biomeName);
            if (biome == null) {
                Config.warn("Biome not found: " + biomeName);
                continue;
            }
            list.add(biome);
        }
        BiomeGenBase[] var6 = list.toArray(new BiomeGenBase[list.size()]);
        return var6;
    }

    private static BiomeGenBase findBiome(String biomeName) {
        biomeName = biomeName.toLowerCase();
        BiomeGenBase[] biomeList = BiomeGenBase.getBiomeGenArray();
        for (int i = 0; i < biomeList.length; ++i) {
            String name;
            BiomeGenBase biome = biomeList[i];
            if (biome == null || !(name = biome.biomeName.replace(" ", "").toLowerCase()).equals(biomeName)) continue;
            return biome;
        }
        return null;
    }

    private String[] parseTileNames(String str) {
        if (str == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        String[] iconStrs = Config.tokenize(str, " ,");
        for (int names = 0; names < iconStrs.length; ++names) {
            String[] iconName;
            String i = iconStrs[names];
            if (i.contains("-") && (iconName = Config.tokenize(i, "-")).length == 2) {
                int pathBlocks = Config.parseInt(iconName[0], -1);
                int max = Config.parseInt(iconName[1], -1);
                if (pathBlocks >= 0 && max >= 0) {
                    if (pathBlocks <= max) {
                        for (int n = pathBlocks; n <= max; ++n) {
                            list.add(String.valueOf(n));
                        }
                        continue;
                    }
                    Config.warn("Invalid interval: " + i + ", when parsing: " + str);
                    continue;
                }
            }
            list.add(i);
        }
        String[] var10 = list.toArray(new String[list.size()]);
        for (int var11 = 0; var11 < var10.length; ++var11) {
            String var13;
            String var12 = var10[var11];
            if (!((var12 = TextureUtils.fixResourcePath(var12, this.basePath)).startsWith(this.basePath) || var12.startsWith("textures/") || var12.startsWith("mcpatcher/"))) {
                var12 = String.valueOf(this.basePath) + "/" + var12;
            }
            if (var12.endsWith(".png")) {
                var12 = var12.substring(0, var12.length() - 4);
            }
            if (var12.startsWith(var13 = "textures/blocks/")) {
                var12 = var12.substring(var13.length());
            }
            if (var12.startsWith("/")) {
                var12 = var12.substring(1);
            }
            var10[var11] = var12;
        }
        return var10;
    }

    private static int parseInt(String str) {
        if (str == null) {
            return -1;
        }
        int num = Config.parseInt(str, -1);
        if (num < 0) {
            Config.warn("Invalid number: " + str);
        }
        return num;
    }

    private static int parseInt(String str, int defVal) {
        if (str == null) {
            return defVal;
        }
        int num = Config.parseInt(str, -1);
        if (num < 0) {
            Config.warn("Invalid number: " + str);
            return defVal;
        }
        return num;
    }

    private static boolean parseBoolean(String str) {
        return str == null ? false : str.toLowerCase().equals("true");
    }

    private static int parseSymmetry(String str) {
        if (str == null) {
            return 1;
        }
        if (str.equals("opposite")) {
            return 2;
        }
        if (str.equals("all")) {
            return 6;
        }
        Config.warn("Unknown symmetry: " + str);
        return 1;
    }

    private static int parseFaces(String str) {
        if (str == null) {
            return 63;
        }
        String[] faceStrs = Config.tokenize(str, " ,");
        int facesMask = 0;
        for (int i = 0; i < faceStrs.length; ++i) {
            String faceStr = faceStrs[i];
            int faceMask = ConnectedProperties.parseFace(faceStr);
            facesMask |= faceMask;
        }
        return facesMask;
    }

    private static int parseFace(String str) {
        if (!(str = str.toLowerCase()).equals("bottom") && !str.equals("down")) {
            if (!str.equals("top") && !str.equals("up")) {
                if (str.equals("north")) {
                    return 4;
                }
                if (str.equals("south")) {
                    return 8;
                }
                if (str.equals("east")) {
                    return 32;
                }
                if (str.equals("west")) {
                    return 16;
                }
                if (str.equals("sides")) {
                    return 60;
                }
                if (str.equals("all")) {
                    return 63;
                }
                Config.warn("Unknown face: " + str);
                return 128;
            }
            return 2;
        }
        return 1;
    }

    private static int parseConnect(String str) {
        if (str == null) {
            return 0;
        }
        if (str.equals("block")) {
            return 1;
        }
        if (str.equals("tile")) {
            return 2;
        }
        if (str.equals("material")) {
            return 3;
        }
        Config.warn("Unknown connect: " + str);
        return 128;
    }

    private static int[] parseInts(String str) {
        if (str == null) {
            return null;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        String[] intStrs = Config.tokenize(str, " ,");
        for (int ints = 0; ints < intStrs.length; ++ints) {
            String i = intStrs[ints];
            if (i.contains("-")) {
                String[] val = Config.tokenize(i, "-");
                if (val.length != 2) {
                    Config.warn("Invalid interval: " + i + ", when parsing: " + str);
                    continue;
                }
                int min = Config.parseInt(val[0], -1);
                int max = Config.parseInt(val[1], -1);
                if (min >= 0 && max >= 0 && min <= max) {
                    for (int n = min; n <= max; ++n) {
                        list.add(n);
                    }
                    continue;
                }
                Config.warn("Invalid interval: " + i + ", when parsing: " + str);
                continue;
            }
            int var11 = Config.parseInt(i, -1);
            if (var11 < 0) {
                Config.warn("Invalid number: " + i + ", when parsing: " + str);
                continue;
            }
            list.add(var11);
        }
        int[] var9 = new int[list.size()];
        for (int var10 = 0; var10 < var9.length; ++var10) {
            var9[var10] = (Integer)list.get(var10);
        }
        return var9;
    }

    private static int[] parseBlockIds(String str) {
        if (str == null) {
            return null;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        String[] intStrs = Config.tokenize(str, " ,");
        for (int ints = 0; ints < intStrs.length; ++ints) {
            String i = intStrs[ints];
            if (i.contains("-")) {
                String[] val = Config.tokenize(i, "-");
                if (val.length != 2) {
                    Config.warn("Invalid interval: " + i + ", when parsing: " + str);
                    continue;
                }
                int min = ConnectedProperties.parseBlockId(val[0]);
                int max = ConnectedProperties.parseBlockId(val[1]);
                if (min >= 0 && max >= 0 && min <= max) {
                    for (int n = min; n <= max; ++n) {
                        list.add(n);
                    }
                    continue;
                }
                Config.warn("Invalid interval: " + i + ", when parsing: " + str);
                continue;
            }
            int var11 = ConnectedProperties.parseBlockId(i);
            if (var11 < 0) {
                Config.warn("Invalid block ID: " + i + ", when parsing: " + str);
                continue;
            }
            list.add(var11);
        }
        int[] var9 = new int[list.size()];
        for (int var10 = 0; var10 < var9.length; ++var10) {
            var9[var10] = (Integer)list.get(var10);
        }
        return var9;
    }

    private static int parseBlockId(String blockStr) {
        int val = Config.parseInt(blockStr, -1);
        if (val >= 0) {
            return val;
        }
        Block block = Block.getBlockFromName(blockStr);
        return block != null ? Block.getIdFromBlock(block) : -1;
    }

    private static int parseMethod(String str) {
        if (str == null) {
            return 1;
        }
        if (!str.equals("ctm") && !str.equals("glass")) {
            if (!str.equals("horizontal") && !str.equals("bookshelf")) {
                if (str.equals("vertical")) {
                    return 6;
                }
                if (str.equals("top")) {
                    return 3;
                }
                if (str.equals("random")) {
                    return 4;
                }
                if (str.equals("repeat")) {
                    return 5;
                }
                if (str.equals("fixed")) {
                    return 7;
                }
                if (!str.equals("horizontal+vertical") && !str.equals("h+v")) {
                    if (!str.equals("vertical+horizontal") && !str.equals("v+h")) {
                        Config.warn("Unknown method: " + str);
                        return 0;
                    }
                    return 9;
                }
                return 8;
            }
            return 2;
        }
        return 1;
    }

    public boolean isValid(String path) {
        if (this.name != null && this.name.length() > 0) {
            if (this.basePath == null) {
                Config.warn("No base path found: " + path);
                return false;
            }
            if (this.matchBlocks == null) {
                this.matchBlocks = this.detectMatchBlocks();
            }
            if (this.matchTiles == null && this.matchBlocks == null) {
                this.matchTiles = this.detectMatchTiles();
            }
            if (this.matchBlocks == null && this.matchTiles == null) {
                Config.warn("No matchBlocks or matchTiles specified: " + path);
                return false;
            }
            if (this.method == 0) {
                Config.warn("No method: " + path);
                return false;
            }
            if (this.tiles != null && this.tiles.length > 0) {
                if (this.connect == 0) {
                    this.connect = this.detectConnect();
                }
                if (this.connect == 128) {
                    Config.warn("Invalid connect in: " + path);
                    return false;
                }
                if (this.renderPass > 0) {
                    Config.warn("Render pass not supported: " + this.renderPass);
                    return false;
                }
                if ((this.faces & 128) != 0) {
                    Config.warn("Invalid faces in: " + path);
                    return false;
                }
                if ((this.symmetry & 128) != 0) {
                    Config.warn("Invalid symmetry in: " + path);
                    return false;
                }
                switch (this.method) {
                    case 1: {
                        return this.isValidCtm(path);
                    }
                    case 2: {
                        return this.isValidHorizontal(path);
                    }
                    case 3: {
                        return this.isValidTop(path);
                    }
                    case 4: {
                        return this.isValidRandom(path);
                    }
                    case 5: {
                        return this.isValidRepeat(path);
                    }
                    case 6: {
                        return this.isValidVertical(path);
                    }
                    case 7: {
                        return this.isValidFixed(path);
                    }
                    case 8: {
                        return this.isValidHorizontalVertical(path);
                    }
                    case 9: {
                        return this.isValidVerticalHorizontal(path);
                    }
                }
                Config.warn("Unknown method: " + path);
                return false;
            }
            Config.warn("No tiles specified: " + path);
            return false;
        }
        Config.warn("No name found: " + path);
        return false;
    }

    private int detectConnect() {
        return this.matchBlocks != null ? 1 : (this.matchTiles != null ? 2 : 128);
    }

    private int[] detectMatchBlocks() {
        int[] arrn;
        int startPos;
        int pos;
        if (!this.name.startsWith("block")) {
            return null;
        }
        for (pos = startPos = "block".length(); pos < this.name.length(); ++pos) {
            char idStr = this.name.charAt(pos);
            if (idStr < '0' || idStr > '9') break;
        }
        if (pos == startPos) {
            return null;
        }
        String var5 = this.name.substring(startPos, pos);
        int id = Config.parseInt(var5, -1);
        if (id < 0) {
            arrn = null;
        } else {
            int[] arrn2 = new int[1];
            arrn = arrn2;
            arrn2[0] = id;
        }
        return arrn;
    }

    private String[] detectMatchTiles() {
        String[] arrstring;
        TextureAtlasSprite icon = ConnectedProperties.getIcon(this.name);
        if (icon == null) {
            arrstring = null;
        } else {
            String[] arrstring2 = new String[1];
            arrstring = arrstring2;
            arrstring2[0] = this.name;
        }
        return arrstring;
    }

    private static TextureAtlasSprite getIcon(String iconName) {
        TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite icon = textureMapBlocks.getSpriteSafe(iconName);
        if (icon != null) {
            return icon;
        }
        icon = textureMapBlocks.getSpriteSafe("blocks/" + iconName);
        return icon;
    }

    private boolean isValidCtm(String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }
        if (this.tiles.length < 47) {
            Config.warn("Invalid tiles, must be at least 47: " + path);
            return false;
        }
        return true;
    }

    private boolean isValidHorizontal(String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("12-15");
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + path);
            return false;
        }
        return true;
    }

    private boolean isValidVertical(String path) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical: " + path);
            return false;
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + path);
            return false;
        }
        return true;
    }

    private boolean isValidHorizontalVertical(String path) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for horizontal+vertical: " + path);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + path);
            return false;
        }
        return true;
    }

    private boolean isValidVerticalHorizontal(String path) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical+horizontal: " + path);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + path);
            return false;
        }
        return true;
    }

    private boolean isValidRandom(String path) {
        if (this.tiles != null && this.tiles.length > 0) {
            if (this.weights != null) {
                int i;
                int[] sum;
                if (this.weights.length > this.tiles.length) {
                    Config.warn("More weights defined than tiles, trimming weights: " + path);
                    sum = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, sum, 0, sum.length);
                    this.weights = sum;
                }
                if (this.weights.length < this.tiles.length) {
                    Config.warn("Less weights defined than tiles, expanding weights: " + path);
                    sum = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, sum, 0, this.weights.length);
                    i = this.getAverage(this.weights);
                    for (int i1 = this.weights.length; i1 < sum.length; ++i1) {
                        sum[i1] = i;
                    }
                    this.weights = sum;
                }
                this.sumWeights = new int[this.weights.length];
                int var5 = 0;
                for (i = 0; i < this.weights.length; ++i) {
                    this.sumWeights[i] = var5 += this.weights[i];
                }
                this.sumAllWeights = var5;
                if (this.sumAllWeights <= 0) {
                    Config.warn("Invalid sum of all weights: " + var5);
                    this.sumAllWeights = 1;
                }
            }
            return true;
        }
        Config.warn("Tiles not defined: " + path);
        return false;
    }

    private int getAverage(int[] vals) {
        int avg;
        if (vals.length <= 0) {
            return 0;
        }
        int sum = 0;
        for (avg = 0; avg < vals.length; ++avg) {
            int val = vals[avg];
            sum += val;
        }
        avg = sum / vals.length;
        return avg;
    }

    private boolean isValidRepeat(String path) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + path);
            return false;
        }
        if (this.width > 0 && this.width <= 16) {
            if (this.height > 0 && this.height <= 16) {
                if (this.tiles.length != this.width * this.height) {
                    Config.warn("Number of tiles does not equal width x height: " + path);
                    return false;
                }
                return true;
            }
            Config.warn("Invalid height: " + path);
            return false;
        }
        Config.warn("Invalid width: " + path);
        return false;
    }

    private boolean isValidFixed(String path) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + path);
            return false;
        }
        if (this.tiles.length != 1) {
            Config.warn("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        return true;
    }

    private boolean isValidTop(String path) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("66");
        }
        if (this.tiles.length != 1) {
            Config.warn("Invalid tiles, must be exactly 1: " + path);
            return false;
        }
        return true;
    }

    public void updateIcons(TextureMap textureMap) {
        if (this.matchTiles != null) {
            this.matchTileIcons = ConnectedProperties.registerIcons(this.matchTiles, textureMap);
        }
        if (this.tiles != null) {
            this.tileIcons = ConnectedProperties.registerIcons(this.tiles, textureMap);
        }
    }

    private static TextureAtlasSprite[] registerIcons(String[] tileNames, TextureMap textureMap) {
        if (tileNames == null) {
            return null;
        }
        ArrayList<TextureAtlasSprite> iconList = new ArrayList<TextureAtlasSprite>();
        for (int icons = 0; icons < tileNames.length; ++icons) {
            boolean exists;
            String filePath;
            ResourceLocation locFile;
            String iconName = tileNames[icons];
            ResourceLocation resLoc = new ResourceLocation(iconName);
            String domain = resLoc.getResourceDomain();
            String path = resLoc.getResourcePath();
            if (!path.contains("/")) {
                path = "textures/blocks/" + path;
            }
            if (!(exists = Config.hasResource(locFile = new ResourceLocation(domain, filePath = String.valueOf(path) + ".png")))) {
                Config.warn("File not found: " + filePath);
            }
            String prefixTextures = "textures/";
            String pathSprite = path;
            if (path.startsWith(prefixTextures)) {
                pathSprite = path.substring(prefixTextures.length());
            }
            ResourceLocation locSprite = new ResourceLocation(domain, pathSprite);
            TextureAtlasSprite icon = textureMap.func_174942_a(locSprite);
            iconList.add(icon);
        }
        TextureAtlasSprite[] var15 = iconList.toArray(new TextureAtlasSprite[iconList.size()]);
        return var15;
    }

    public boolean matchesBlock(int blockId) {
        if (this.matchBlocks != null && this.matchBlocks.length > 0) {
            for (int i = 0; i < this.matchBlocks.length; ++i) {
                int matchId = this.matchBlocks[i];
                if (matchId != blockId) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean matchesIcon(TextureAtlasSprite icon) {
        if (this.matchTileIcons != null && this.matchTileIcons.length > 0) {
            for (int i = 0; i < this.matchTileIcons.length; ++i) {
                if (this.matchTileIcons[i] != icon) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public String toString() {
        return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString(this.matchTiles);
    }
}

