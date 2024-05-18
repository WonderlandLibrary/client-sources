package net.minecraft.src;

import java.util.*;

public class ConnectedProperties
{
    public String name;
    public String basePath;
    public int[] matchBlocks;
    public String[] matchTiles;
    public int method;
    public String[] tiles;
    public int connect;
    public int faces;
    public int[] metadatas;
    public BiomeGenBase[] biomes;
    public int minHeight;
    public int maxHeight;
    public int renderPass;
    public boolean innerSeams;
    public int width;
    public int height;
    public int[] weights;
    public int symmetry;
    public int[] sumWeights;
    public int sumAllWeights;
    public Icon[] matchTileIcons;
    public Icon[] tileIcons;
    public static final int METHOD_NONE = 0;
    public static final int METHOD_CTM = 1;
    public static final int METHOD_HORIZONTAL = 2;
    public static final int METHOD_TOP = 3;
    public static final int METHOD_RANDOM = 4;
    public static final int METHOD_REPEAT = 5;
    public static final int METHOD_VERTICAL = 6;
    public static final int METHOD_FIXED = 7;
    public static final int CONNECT_NONE = 0;
    public static final int CONNECT_BLOCK = 1;
    public static final int CONNECT_TILE = 2;
    public static final int CONNECT_MATERIAL = 3;
    public static final int CONNECT_UNKNOWN = 128;
    public static final int FACE_BOTTOM = 1;
    public static final int FACE_TOP = 2;
    public static final int FACE_EAST = 4;
    public static final int FACE_WEST = 8;
    public static final int FACE_NORTH = 16;
    public static final int FACE_SOUTH = 32;
    public static final int FACE_SIDES = 60;
    public static final int FACE_ALL = 63;
    public static final int FACE_UNKNOWN = 128;
    public static final int SYMMETRY_NONE = 1;
    public static final int SYMMETRY_OPPOSITE = 2;
    public static final int SYMMETRY_ALL = 6;
    public static final int SYMMETRY_UNKNOWN = 128;
    
    public ConnectedProperties(final Properties var1, final String var2) {
        this.name = null;
        this.basePath = null;
        this.matchBlocks = null;
        this.matchTiles = null;
        this.method = 0;
        this.tiles = null;
        this.connect = 0;
        this.faces = 63;
        this.metadatas = null;
        this.biomes = null;
        this.minHeight = 0;
        this.maxHeight = 1024;
        this.renderPass = 0;
        this.innerSeams = false;
        this.width = 0;
        this.height = 0;
        this.weights = null;
        this.symmetry = 1;
        this.sumWeights = null;
        this.sumAllWeights = 0;
        this.matchTileIcons = null;
        this.tileIcons = null;
        this.name = parseName(var2);
        this.basePath = parseBasePath(var2);
        this.matchBlocks = parseInts(var1.getProperty("matchBlocks"));
        this.matchTiles = this.parseMatchTiles(var1.getProperty("matchTiles"));
        this.method = parseMethod(var1.getProperty("method"));
        this.tiles = this.parseTileNames(var1.getProperty("tiles"));
        this.connect = parseConnect(var1.getProperty("connect"));
        this.faces = parseFaces(var1.getProperty("faces"));
        this.metadatas = parseInts(var1.getProperty("metadata"));
        this.biomes = parseBiomes(var1.getProperty("biomes"));
        this.minHeight = parseInt(var1.getProperty("minHeight"), -1);
        this.maxHeight = parseInt(var1.getProperty("maxHeight"), 1024);
        this.renderPass = parseInt(var1.getProperty("renderPass"));
        this.innerSeams = parseBoolean(var1.getProperty("innerSeams"));
        this.width = parseInt(var1.getProperty("width"));
        this.height = parseInt(var1.getProperty("height"));
        this.weights = parseInts(var1.getProperty("weights"));
        this.symmetry = parseSymmetry(var1.getProperty("symmetry"));
    }
    
    private String[] parseMatchTiles(final String var1) {
        if (var1 == null) {
            return null;
        }
        final String[] var2 = Config.tokenize(var1, " ");
        for (int var3 = 0; var3 < var2.length; ++var3) {
            String var4 = var2[var3];
            if (var4.endsWith(".png")) {
                var4 = var4.substring(0, var4.length() - 4);
            }
            if (var4.startsWith("/ctm/")) {
                var4 = var4.substring(1);
            }
            var2[var3] = var4;
        }
        return var2;
    }
    
    private static String parseName(final String var0) {
        String var = var0;
        final int var2 = var0.lastIndexOf(47);
        if (var2 >= 0) {
            var = var0.substring(var2 + 1);
        }
        final int var3 = var.lastIndexOf(46);
        if (var3 >= 0) {
            var = var.substring(0, var3);
        }
        return var;
    }
    
    private static String parseBasePath(final String var0) {
        final int var = var0.lastIndexOf(47);
        return (var < 0) ? "" : var0.substring(0, var);
    }
    
    private static BiomeGenBase[] parseBiomes(final String var0) {
        if (var0 == null) {
            return null;
        }
        final String[] var = Config.tokenize(var0, " ");
        final ArrayList var2 = new ArrayList();
        for (int var3 = 0; var3 < var.length; ++var3) {
            final String var4 = var[var3];
            final BiomeGenBase var5 = findBiome(var4);
            if (var5 == null) {
                Config.dbg("Biome not found: " + var4);
            }
            else {
                var2.add(var5);
            }
        }
        final BiomeGenBase[] var6 = var2.toArray(new BiomeGenBase[var2.size()]);
        return var6;
    }
    
    private static BiomeGenBase findBiome(String var0) {
        var0 = var0.toLowerCase();
        for (int var = 0; var < BiomeGenBase.biomeList.length; ++var) {
            final BiomeGenBase var2 = BiomeGenBase.biomeList[var];
            if (var2 != null) {
                final String var3 = var2.biomeName.replace(" ", "").toLowerCase();
                if (var3.equals(var0)) {
                    return var2;
                }
            }
        }
        return null;
    }
    
    private String[] parseTileNames(final String var1) {
        if (var1 == null) {
            return null;
        }
        final ArrayList var2 = new ArrayList();
        final String[] var3 = Config.tokenize(var1, " ,");
        for (int var4 = 0; var4 < var3.length; ++var4) {
            final String var5 = var3[var4];
            if (var5.contains("-")) {
                final String[] var6 = Config.tokenize(var5, "-");
                if (var6.length == 2) {
                    final int var7 = Config.parseInt(var6[0], -1);
                    final int var8 = Config.parseInt(var6[1], -1);
                    if (var7 >= 0 && var8 >= 0) {
                        if (var7 <= var8) {
                            for (int var9 = var7; var9 <= var8; ++var9) {
                                var2.add(String.valueOf(var9));
                            }
                            continue;
                        }
                        Config.dbg("Invalid interval: " + var5 + ", when parsing: " + var1);
                        continue;
                    }
                }
            }
            var2.add(var5);
        }
        final String[] var10 = var2.toArray(new String[var2.size()]);
        for (int var11 = 0; var11 < var10.length; ++var11) {
            String var12 = var10[var11];
            if (!var12.startsWith("/")) {
                var12 = String.valueOf(this.basePath) + "/" + var12;
            }
            if (var12.endsWith(".png")) {
                var12 = var12.substring(0, var12.length() - 4);
            }
            final String var13 = "/textures/blocks/";
            if (var12.startsWith(var13)) {
                var12 = var12.substring(var13.length());
            }
            if (var12.startsWith("/")) {
                var12 = var12.substring(1);
            }
            var10[var11] = var12;
        }
        return var10;
    }
    
    private static int parseInt(final String var0) {
        if (var0 == null) {
            return -1;
        }
        final int var = Config.parseInt(var0, -1);
        if (var < 0) {
            Config.dbg("Invalid number: " + var0);
        }
        return var;
    }
    
    private static int parseInt(final String var0, final int var1) {
        if (var0 == null) {
            return var1;
        }
        final int var2 = Config.parseInt(var0, -1);
        if (var2 < 0) {
            Config.dbg("Invalid number: " + var0);
            return var1;
        }
        return var2;
    }
    
    private static boolean parseBoolean(final String var0) {
        return var0 != null && var0.toLowerCase().equals("true");
    }
    
    private static int parseSymmetry(final String var0) {
        if (var0 == null) {
            return 1;
        }
        if (var0.equals("opposite")) {
            return 2;
        }
        if (var0.equals("all")) {
            return 6;
        }
        Config.dbg("Unknown symmetry: " + var0);
        return 1;
    }
    
    private static int parseFaces(final String var0) {
        if (var0 == null) {
            return 63;
        }
        final String[] var = Config.tokenize(var0, " ,");
        int var2 = 0;
        for (int var3 = 0; var3 < var.length; ++var3) {
            final String var4 = var[var3];
            final int var5 = parseFace(var4);
            var2 |= var5;
        }
        return var2;
    }
    
    private static int parseFace(final String var0) {
        if (var0.equals("bottom")) {
            return 1;
        }
        if (var0.equals("top")) {
            return 2;
        }
        if (var0.equals("north")) {
            return 4;
        }
        if (var0.equals("south")) {
            return 8;
        }
        if (var0.equals("east")) {
            return 32;
        }
        if (var0.equals("west")) {
            return 16;
        }
        if (var0.equals("sides")) {
            return 60;
        }
        if (var0.equals("all")) {
            return 63;
        }
        Config.dbg("Unknown face: " + var0);
        return 128;
    }
    
    private static int parseConnect(final String var0) {
        if (var0 == null) {
            return 0;
        }
        if (var0.equals("block")) {
            return 1;
        }
        if (var0.equals("tile")) {
            return 2;
        }
        if (var0.equals("material")) {
            return 3;
        }
        Config.dbg("Unknown connect: " + var0);
        return 128;
    }
    
    private static int[] parseInts(final String var0) {
        if (var0 == null) {
            return null;
        }
        final ArrayList var = new ArrayList();
        final String[] var2 = Config.tokenize(var0, " ,");
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final String var4 = var2[var3];
            if (var4.contains("-")) {
                final String[] var5 = Config.tokenize(var4, "-");
                if (var5.length != 2) {
                    Config.dbg("Invalid interval: " + var4 + ", when parsing: " + var0);
                }
                else {
                    final int var6 = Config.parseInt(var5[0], -1);
                    final int var7 = Config.parseInt(var5[1], -1);
                    if (var6 >= 0 && var7 >= 0 && var6 <= var7) {
                        for (int var8 = var6; var8 <= var7; ++var8) {
                            var.add(var8);
                        }
                    }
                    else {
                        Config.dbg("Invalid interval: " + var4 + ", when parsing: " + var0);
                    }
                }
            }
            else {
                final int var9 = Config.parseInt(var4, -1);
                if (var9 < 0) {
                    Config.dbg("Invalid number: " + var4 + ", when parsing: " + var0);
                }
                else {
                    var.add(var9);
                }
            }
        }
        final int[] var10 = new int[var.size()];
        for (int var11 = 0; var11 < var10.length; ++var11) {
            var10[var11] = var.get(var11);
        }
        return var10;
    }
    
    private static int parseMethod(final String var0) {
        if (var0 == null) {
            return 1;
        }
        if (var0.equals("ctm")) {
            return 1;
        }
        if (var0.equals("horizontal")) {
            return 2;
        }
        if (var0.equals("vertical")) {
            return 6;
        }
        if (var0.equals("top")) {
            return 3;
        }
        if (var0.equals("random")) {
            return 4;
        }
        if (var0.equals("repeat")) {
            return 5;
        }
        if (var0.equals("fixed")) {
            return 7;
        }
        Config.dbg("Unknown method: " + var0);
        return 0;
    }
    
    public boolean isValid(final String var1) {
        if (this.name == null || this.name.length() <= 0) {
            Config.dbg("No name found: " + var1);
            return false;
        }
        if (this.basePath == null) {
            Config.dbg("No base path found: " + var1);
            return false;
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
        }
        if (this.matchTiles == null && this.matchBlocks == null) {
            this.matchTiles = this.detectMatchTiles();
        }
        if (this.matchBlocks == null && this.matchTiles == null) {
            Config.dbg("No matchBlocks or matchTiles specified: " + var1);
            return false;
        }
        if (this.method == 0) {
            Config.dbg("No method: " + var1);
            return false;
        }
        if (this.tiles == null || this.tiles.length <= 0) {
            Config.dbg("No tiles specified: " + var1);
            return false;
        }
        if (this.connect == 0) {
            this.connect = this.detectConnect();
        }
        if (this.connect == 128) {
            Config.dbg("Invalid connect in: " + var1);
            return false;
        }
        if (this.renderPass > 0) {
            Config.dbg("Render pass not supported: " + this.renderPass);
            return false;
        }
        if ((this.faces & 0x80) != 0x0) {
            Config.dbg("Invalid faces in: " + var1);
            return false;
        }
        if ((this.symmetry & 0x80) != 0x0) {
            Config.dbg("Invalid symmetry in: " + var1);
            return false;
        }
        switch (this.method) {
            case 1: {
                return this.isValidCtm(var1);
            }
            case 2: {
                return this.isValidHorizontal(var1);
            }
            case 3: {
                return this.isValidTop(var1);
            }
            case 4: {
                return this.isValidRandom(var1);
            }
            case 5: {
                return this.isValidRepeat(var1);
            }
            case 6: {
                return this.isValidVertical(var1);
            }
            case 7: {
                return this.isValidFixed(var1);
            }
            default: {
                Config.dbg("Unknown method: " + var1);
                return false;
            }
        }
    }
    
    private int detectConnect() {
        return (this.matchBlocks != null) ? 1 : ((this.matchTiles != null) ? 2 : 128);
    }
    
    private int[] detectMatchBlocks() {
        if (!this.name.startsWith("block")) {
            return null;
        }
        int var2;
        int var1;
        for (var1 = (var2 = "block".length()); var2 < this.name.length(); ++var2) {
            final char var3 = this.name.charAt(var2);
            if (var3 < '0') {
                break;
            }
            if (var3 > '9') {
                break;
            }
        }
        if (var2 == var1) {
            return null;
        }
        final String var4 = this.name.substring(var1, var2);
        final int var5 = Config.parseInt(var4, -1);
        final int[] array;
        if (var5 >= 0) {
            array = new int[] { var5 };
        }
        return array;
    }
    
    private String[] detectMatchTiles() {
        final Icon var1 = getIcon(this.name);
        final String[] array;
        if (var1 != null) {
            array = new String[] { this.name };
        }
        return array;
    }
    
    private static Icon getIcon(final String var0) {
        final RenderEngine var = Config.getRenderEngine();
        return (var == null) ? null : var.textureMapBlocks.getIconSafe(var0);
    }
    
    private boolean isValidCtm(final String var1) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }
        if (this.tiles.length < 47) {
            Config.dbg("Invalid tiles, must be at least 47: " + var1);
            return false;
        }
        return true;
    }
    
    private boolean isValidHorizontal(final String var1) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("12-15");
        }
        if (this.tiles.length != 4) {
            Config.dbg("Invalid tiles, must be exactly 4: " + var1);
            return false;
        }
        return true;
    }
    
    private boolean isValidVertical(final String var1) {
        if (this.tiles == null) {
            Config.dbg("No tiles defined for vertical: " + var1);
            return false;
        }
        if (this.tiles.length != 4) {
            Config.dbg("Invalid tiles, must be exactly 4: " + var1);
            return false;
        }
        return true;
    }
    
    private boolean isValidRandom(final String var1) {
        if (this.tiles != null && this.tiles.length > 0) {
            if (this.weights != null && this.weights.length != this.tiles.length) {
                Config.dbg("Number of weights must equal the number of tiles: " + var1);
                this.weights = null;
            }
            if (this.weights != null) {
                this.sumWeights = new int[this.weights.length];
                int var2 = 0;
                for (int var3 = 0; var3 < this.weights.length; ++var3) {
                    var2 += this.weights[var3];
                    this.sumWeights[var3] = var2;
                }
                this.sumAllWeights = var2;
            }
            return true;
        }
        Config.dbg("Tiles not defined: " + var1);
        return false;
    }
    
    private boolean isValidRepeat(final String var1) {
        if (this.tiles == null) {
            Config.dbg("Tiles not defined: " + var1);
            return false;
        }
        if (this.width <= 0 || this.width > 16) {
            Config.dbg("Invalid width: " + var1);
            return false;
        }
        if (this.height <= 0 || this.height > 16) {
            Config.dbg("Invalid height: " + var1);
            return false;
        }
        if (this.tiles.length != this.width * this.height) {
            Config.dbg("Number of tiles does not equal width x height: " + var1);
            return false;
        }
        return true;
    }
    
    private boolean isValidFixed(final String var1) {
        if (this.tiles == null) {
            Config.dbg("Tiles not defined: " + var1);
            return false;
        }
        if (this.tiles.length != 1) {
            Config.dbg("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        return true;
    }
    
    private boolean isValidTop(final String var1) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("66");
        }
        if (this.tiles.length != 1) {
            Config.dbg("Invalid tiles, must be exactly 1: " + var1);
            return false;
        }
        return true;
    }
    
    public void updateIcons(final TextureMap var1) {
        if (this.matchTiles != null) {
            this.matchTileIcons = registerIcons(this.matchTiles, var1);
        }
        if (this.tiles != null) {
            this.tileIcons = registerIcons(this.tiles, var1);
        }
    }
    
    private static Icon[] registerIcons(final String[] var0, final TextureMap var1) {
        if (var0 == null) {
            return null;
        }
        final ITexturePack var2 = Config.getRenderEngine().getTexturePack().getSelectedTexturePack();
        final ArrayList var3 = new ArrayList();
        for (int var4 = 0; var4 < var0.length; ++var4) {
            String var6;
            final String var5 = var6 = var0[var4];
            if (!var5.contains("/")) {
                var6 = "textures/blocks/" + var5;
            }
            final String var7 = "/" + var6 + ".png";
            final boolean var8 = var2.func_98138_b(var7, true);
            if (!var8) {
                Config.dbg("File not found: " + var7);
            }
            final Icon var9 = var1.registerIcon(var5);
            var3.add(var9);
        }
        final Icon[] var10 = var3.toArray(new Icon[var3.size()]);
        return var10;
    }
    
    @Override
    public String toString() {
        return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString(this.matchTiles);
    }
}
