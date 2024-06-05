package optfine;

import net.minecraft.world.biome.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

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
    public TextureAtlasSprite[] matchTileIcons;
    public TextureAtlasSprite[] tileIcons;
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
    
    public ConnectedProperties(final Properties p_i29_1_, final String p_i29_2_) {
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
        this.sumAllWeights = 1;
        this.matchTileIcons = null;
        this.tileIcons = null;
        this.name = parseName(p_i29_2_);
        this.basePath = parseBasePath(p_i29_2_);
        final String s = p_i29_1_.getProperty("matchBlocks");
        final IBlockState iblockstate = this.parseBlockState(s);
        if (iblockstate != null) {
            this.matchBlocks = new int[] { Block.getIdFromBlock(iblockstate.getBlock()) };
            this.metadatas = new int[] { iblockstate.getBlock().getMetaFromState(iblockstate) };
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = parseBlockIds(s);
        }
        if (this.metadatas == null) {
            this.metadatas = parseInts(p_i29_1_.getProperty("metadata"));
        }
        this.matchTiles = this.parseMatchTiles(p_i29_1_.getProperty("matchTiles"));
        this.method = parseMethod(p_i29_1_.getProperty("method"));
        this.tiles = this.parseTileNames(p_i29_1_.getProperty("tiles"));
        this.connect = parseConnect(p_i29_1_.getProperty("connect"));
        this.faces = parseFaces(p_i29_1_.getProperty("faces"));
        this.biomes = parseBiomes(p_i29_1_.getProperty("biomes"));
        this.minHeight = parseInt(p_i29_1_.getProperty("minHeight"), -1);
        this.maxHeight = parseInt(p_i29_1_.getProperty("maxHeight"), 1024);
        this.renderPass = parseInt(p_i29_1_.getProperty("renderPass"));
        this.innerSeams = parseBoolean(p_i29_1_.getProperty("innerSeams"));
        this.width = parseInt(p_i29_1_.getProperty("width"));
        this.height = parseInt(p_i29_1_.getProperty("height"));
        this.weights = parseInts(p_i29_1_.getProperty("weights"));
        this.symmetry = parseSymmetry(p_i29_1_.getProperty("symmetry"));
    }
    
    private String[] parseMatchTiles(final String p_parseMatchTiles_1_) {
        if (p_parseMatchTiles_1_ == null) {
            return null;
        }
        final String[] astring = Config.tokenize(p_parseMatchTiles_1_, " ");
        for (int i = 0; i < astring.length; ++i) {
            String s = astring[i];
            if (s.endsWith(".png")) {
                s = s.substring(0, s.length() - 4);
            }
            s = TextureUtils.fixResourcePath(s, this.basePath);
            astring[i] = s;
        }
        return astring;
    }
    
    private static String parseName(final String p_parseName_0_) {
        String s = p_parseName_0_;
        final int i = p_parseName_0_.lastIndexOf(47);
        if (i >= 0) {
            s = p_parseName_0_.substring(i + 1);
        }
        final int j = s.lastIndexOf(46);
        if (j >= 0) {
            s = s.substring(0, j);
        }
        return s;
    }
    
    private static String parseBasePath(final String p_parseBasePath_0_) {
        final int i = p_parseBasePath_0_.lastIndexOf(47);
        return (i < 0) ? "" : p_parseBasePath_0_.substring(0, i);
    }
    
    private static BiomeGenBase[] parseBiomes(final String p_parseBiomes_0_) {
        if (p_parseBiomes_0_ == null) {
            return null;
        }
        final String[] astring = Config.tokenize(p_parseBiomes_0_, " ");
        final List list = new ArrayList();
        for (int i = 0; i < astring.length; ++i) {
            final String s = astring[i];
            final BiomeGenBase biomegenbase = findBiome(s);
            if (biomegenbase == null) {
                Config.warn("Biome not found: " + s);
            }
            else {
                list.add(biomegenbase);
            }
        }
        final BiomeGenBase[] abiomegenbase = list.toArray(new BiomeGenBase[list.size()]);
        return abiomegenbase;
    }
    
    private static BiomeGenBase findBiome(String p_findBiome_0_) {
        p_findBiome_0_ = p_findBiome_0_.toLowerCase();
        final BiomeGenBase[] abiomegenbase = BiomeGenBase.getBiomeGenArray();
        for (int i = 0; i < abiomegenbase.length; ++i) {
            final BiomeGenBase biomegenbase = abiomegenbase[i];
            if (biomegenbase != null) {
                final String s = biomegenbase.biomeName.replace(" ", "").toLowerCase();
                if (s.equals(p_findBiome_0_)) {
                    return biomegenbase;
                }
            }
        }
        return null;
    }
    
    private String[] parseTileNames(final String p_parseTileNames_1_) {
        if (p_parseTileNames_1_ == null) {
            return null;
        }
        final List list = new ArrayList();
        final String[] astring = Config.tokenize(p_parseTileNames_1_, " ,");
        for (int i = 0; i < astring.length; ++i) {
            final String s = astring[i];
            if (s.contains("-")) {
                final String[] astring2 = Config.tokenize(s, "-");
                if (astring2.length == 2) {
                    final int j = Config.parseInt(astring2[0], -1);
                    final int k = Config.parseInt(astring2[1], -1);
                    if (j >= 0 && k >= 0) {
                        if (j > k) {
                            Config.warn("Invalid interval: " + s + ", when parsing: " + p_parseTileNames_1_);
                            continue;
                        }
                        for (int l = j; l <= k; ++l) {
                            list.add(String.valueOf(l));
                        }
                        continue;
                    }
                }
            }
            list.add(s);
        }
        final String[] astring3 = list.toArray(new String[list.size()]);
        for (int i2 = 0; i2 < astring3.length; ++i2) {
            String s2 = astring3[i2];
            s2 = TextureUtils.fixResourcePath(s2, this.basePath);
            if (!s2.startsWith(this.basePath) && !s2.startsWith("textures/") && !s2.startsWith("mcpatcher/")) {
                s2 = this.basePath + "/" + s2;
            }
            if (s2.endsWith(".png")) {
                s2 = s2.substring(0, s2.length() - 4);
            }
            final String s3 = "textures/blocks/";
            if (s2.startsWith(s3)) {
                s2 = s2.substring(s3.length());
            }
            if (s2.startsWith("/")) {
                s2 = s2.substring(1);
            }
            astring3[i2] = s2;
        }
        return astring3;
    }
    
    private static int parseInt(final String p_parseInt_0_) {
        if (p_parseInt_0_ == null) {
            return -1;
        }
        final int i = Config.parseInt(p_parseInt_0_, -1);
        if (i < 0) {
            Config.warn("Invalid number: " + p_parseInt_0_);
        }
        return i;
    }
    
    private static int parseInt(final String p_parseInt_0_, final int p_parseInt_1_) {
        if (p_parseInt_0_ == null) {
            return p_parseInt_1_;
        }
        final int i = Config.parseInt(p_parseInt_0_, -1);
        if (i < 0) {
            Config.warn("Invalid number: " + p_parseInt_0_);
            return p_parseInt_1_;
        }
        return i;
    }
    
    private static boolean parseBoolean(final String p_parseBoolean_0_) {
        return p_parseBoolean_0_ != null && p_parseBoolean_0_.toLowerCase().equals("true");
    }
    
    private static int parseSymmetry(final String p_parseSymmetry_0_) {
        if (p_parseSymmetry_0_ == null) {
            return 1;
        }
        if (p_parseSymmetry_0_.equals("opposite")) {
            return 2;
        }
        if (p_parseSymmetry_0_.equals("all")) {
            return 6;
        }
        Config.warn("Unknown symmetry: " + p_parseSymmetry_0_);
        return 1;
    }
    
    private static int parseFaces(final String p_parseFaces_0_) {
        if (p_parseFaces_0_ == null) {
            return 63;
        }
        final String[] astring = Config.tokenize(p_parseFaces_0_, " ,");
        int i = 0;
        for (int j = 0; j < astring.length; ++j) {
            final String s = astring[j];
            final int k = parseFace(s);
            i |= k;
        }
        return i;
    }
    
    private static int parseFace(String p_parseFace_0_) {
        p_parseFace_0_ = p_parseFace_0_.toLowerCase();
        if (p_parseFace_0_.equals("bottom") || p_parseFace_0_.equals("down")) {
            return 1;
        }
        if (p_parseFace_0_.equals("top") || p_parseFace_0_.equals("up")) {
            return 2;
        }
        if (p_parseFace_0_.equals("north")) {
            return 4;
        }
        if (p_parseFace_0_.equals("south")) {
            return 8;
        }
        if (p_parseFace_0_.equals("east")) {
            return 32;
        }
        if (p_parseFace_0_.equals("west")) {
            return 16;
        }
        if (p_parseFace_0_.equals("sides")) {
            return 60;
        }
        if (p_parseFace_0_.equals("all")) {
            return 63;
        }
        Config.warn("Unknown face: " + p_parseFace_0_);
        return 128;
    }
    
    private static int parseConnect(final String p_parseConnect_0_) {
        if (p_parseConnect_0_ == null) {
            return 0;
        }
        if (p_parseConnect_0_.equals("block")) {
            return 1;
        }
        if (p_parseConnect_0_.equals("tile")) {
            return 2;
        }
        if (p_parseConnect_0_.equals("material")) {
            return 3;
        }
        Config.warn("Unknown connect: " + p_parseConnect_0_);
        return 128;
    }
    
    private static int[] parseInts(final String p_parseInts_0_) {
        if (p_parseInts_0_ == null) {
            return null;
        }
        final List list = new ArrayList();
        final String[] astring = Config.tokenize(p_parseInts_0_, " ,");
        for (int i = 0; i < astring.length; ++i) {
            final String s = astring[i];
            if (s.contains("-")) {
                final String[] astring2 = Config.tokenize(s, "-");
                if (astring2.length != 2) {
                    Config.warn("Invalid interval: " + s + ", when parsing: " + p_parseInts_0_);
                }
                else {
                    final int k = Config.parseInt(astring2[0], -1);
                    final int l = Config.parseInt(astring2[1], -1);
                    if (k >= 0 && l >= 0 && k <= l) {
                        for (int i2 = k; i2 <= l; ++i2) {
                            list.add(i2);
                        }
                    }
                    else {
                        Config.warn("Invalid interval: " + s + ", when parsing: " + p_parseInts_0_);
                    }
                }
            }
            else {
                final int j = Config.parseInt(s, -1);
                if (j < 0) {
                    Config.warn("Invalid number: " + s + ", when parsing: " + p_parseInts_0_);
                }
                else {
                    list.add(j);
                }
            }
        }
        final int[] aint = new int[list.size()];
        for (int j2 = 0; j2 < aint.length; ++j2) {
            aint[j2] = list.get(j2);
        }
        return aint;
    }
    
    private static int[] parseBlockIds(final String p_parseBlockIds_0_) {
        if (p_parseBlockIds_0_ == null) {
            return null;
        }
        final List list = new ArrayList();
        final String[] astring = Config.tokenize(p_parseBlockIds_0_, " ,");
        for (int i = 0; i < astring.length; ++i) {
            final String s = astring[i];
            if (s.contains("-")) {
                final String[] astring2 = Config.tokenize(s, "-");
                if (astring2.length != 2) {
                    Config.warn("Invalid interval: " + s + ", when parsing: " + p_parseBlockIds_0_);
                }
                else {
                    final int k = parseBlockId(astring2[0]);
                    final int l = parseBlockId(astring2[1]);
                    if (k >= 0 && l >= 0 && k <= l) {
                        for (int i2 = k; i2 <= l; ++i2) {
                            list.add(i2);
                        }
                    }
                    else {
                        Config.warn("Invalid interval: " + s + ", when parsing: " + p_parseBlockIds_0_);
                    }
                }
            }
            else {
                final int j = parseBlockId(s);
                if (j < 0) {
                    Config.warn("Invalid block ID: " + s + ", when parsing: " + p_parseBlockIds_0_);
                }
                else {
                    list.add(j);
                }
            }
        }
        final int[] aint = new int[list.size()];
        for (int j2 = 0; j2 < aint.length; ++j2) {
            aint[j2] = list.get(j2);
        }
        return aint;
    }
    
    private static int parseBlockId(final String p_parseBlockId_0_) {
        final int i = Config.parseInt(p_parseBlockId_0_, -1);
        if (i >= 0) {
            return i;
        }
        final Block block = Block.getBlockFromName(p_parseBlockId_0_);
        return (block != null) ? Block.getIdFromBlock(block) : -1;
    }
    
    private IBlockState parseBlockState(final String p_parseBlockState_1_) {
        if (p_parseBlockState_1_ == null) {
            return null;
        }
        final String[] astring = Config.tokenize(p_parseBlockState_1_, ":");
        if (astring.length < 2) {
            return null;
        }
        final String s = astring[0];
        final String s2 = astring[1];
        final String s3 = s + ":" + s2;
        final Block block = Block.getBlockFromName(s3);
        if (block == null) {
            return null;
        }
        int i = -1;
        IBlockState iblockstate = null;
        for (int j = 2; j < astring.length; ++j) {
            final String s4 = astring[j];
            if (s4.length() >= 1) {
                if (Character.isDigit(s4.charAt(0))) {
                    if (s4.indexOf(45) < 0 && s4.indexOf(44) < 0) {
                        final int k = Config.parseInt(s4, -1);
                        if (k >= 0) {
                            i = k;
                        }
                    }
                }
                else {
                    final String[] astring2 = Config.tokenize(s4, "=");
                    if (astring2.length >= 2) {
                        final String s5 = astring2[0];
                        final String s6 = astring2[1];
                        if (s6.indexOf(44) < 0) {
                            if (iblockstate == null) {
                                iblockstate = block.getDefaultState();
                            }
                            final Collection collection = iblockstate.getPropertyNames();
                            final IProperty iproperty = getProperty(s5, collection);
                            if (iproperty == null) {
                                final String s7 = "\"";
                                Config.warn("Block " + s7 + s3 + s7 + " has no property " + s7 + s5 + s7);
                            }
                            else {
                                final Class oclass = iproperty.getValueClass();
                                Object object = ConnectedParser.parseValue(s6, oclass);
                                if (object == null) {
                                    final Collection collection2 = iproperty.getAllowedValues();
                                    object = ConnectedParser.getPropertyValue(s6, collection2);
                                }
                                if (object == null) {
                                    Config.warn("Invalid value: " + s6 + ", for property: " + iproperty);
                                }
                                else if (!(object instanceof Comparable)) {
                                    Config.warn("Value is not Comparable: " + s6 + ", for property: " + iproperty);
                                }
                                else {
                                    final Comparable comparable = (Comparable)object;
                                    iblockstate = iblockstate.withProperty((IProperty<Comparable>)iproperty, comparable);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (iblockstate != null) {
            return iblockstate;
        }
        if (i < 0) {
            return null;
        }
        return block.getStateFromMeta(i);
    }
    
    public static IProperty getProperty(final String p_getProperty_0_, final Collection p_getProperty_1_) {
        for (final Object iproperty : p_getProperty_1_) {
            if (p_getProperty_0_.equals(((IProperty)iproperty).getName())) {
                return (IProperty)iproperty;
            }
        }
        return null;
    }
    
    private static int parseMethod(final String p_parseMethod_0_) {
        if (p_parseMethod_0_ == null) {
            return 1;
        }
        if (p_parseMethod_0_.equals("ctm") || p_parseMethod_0_.equals("glass")) {
            return 1;
        }
        if (p_parseMethod_0_.equals("horizontal") || p_parseMethod_0_.equals("bookshelf")) {
            return 2;
        }
        if (p_parseMethod_0_.equals("vertical")) {
            return 6;
        }
        if (p_parseMethod_0_.equals("top")) {
            return 3;
        }
        if (p_parseMethod_0_.equals("random")) {
            return 4;
        }
        if (p_parseMethod_0_.equals("repeat")) {
            return 5;
        }
        if (p_parseMethod_0_.equals("fixed")) {
            return 7;
        }
        if (p_parseMethod_0_.equals("horizontal+vertical") || p_parseMethod_0_.equals("h+v")) {
            return 8;
        }
        if (!p_parseMethod_0_.equals("vertical+horizontal") && !p_parseMethod_0_.equals("v+h")) {
            Config.warn("Unknown method: " + p_parseMethod_0_);
            return 0;
        }
        return 9;
    }
    
    public boolean isValid(final String p_isValid_1_) {
        if (this.name == null || this.name.length() <= 0) {
            Config.warn("No name found: " + p_isValid_1_);
            return false;
        }
        if (this.basePath == null) {
            Config.warn("No base path found: " + p_isValid_1_);
            return false;
        }
        if (this.matchBlocks == null) {
            this.matchBlocks = this.detectMatchBlocks();
        }
        if (this.matchTiles == null && this.matchBlocks == null) {
            this.matchTiles = this.detectMatchTiles();
        }
        if (this.matchBlocks == null && this.matchTiles == null) {
            Config.warn("No matchBlocks or matchTiles specified: " + p_isValid_1_);
            return false;
        }
        if (this.method == 0) {
            Config.warn("No method: " + p_isValid_1_);
            return false;
        }
        if (this.tiles == null || this.tiles.length <= 0) {
            Config.warn("No tiles specified: " + p_isValid_1_);
            return false;
        }
        if (this.connect == 0) {
            this.connect = this.detectConnect();
        }
        if (this.connect == 128) {
            Config.warn("Invalid connect in: " + p_isValid_1_);
            return false;
        }
        if (this.renderPass > 0) {
            Config.warn("Render pass not supported: " + this.renderPass);
            return false;
        }
        if ((this.faces & 0x80) != 0x0) {
            Config.warn("Invalid faces in: " + p_isValid_1_);
            return false;
        }
        if ((this.symmetry & 0x80) != 0x0) {
            Config.warn("Invalid symmetry in: " + p_isValid_1_);
            return false;
        }
        switch (this.method) {
            case 1: {
                return this.isValidCtm(p_isValid_1_);
            }
            case 2: {
                return this.isValidHorizontal(p_isValid_1_);
            }
            case 3: {
                return this.isValidTop(p_isValid_1_);
            }
            case 4: {
                return this.isValidRandom(p_isValid_1_);
            }
            case 5: {
                return this.isValidRepeat(p_isValid_1_);
            }
            case 6: {
                return this.isValidVertical(p_isValid_1_);
            }
            case 7: {
                return this.isValidFixed(p_isValid_1_);
            }
            case 8: {
                return this.isValidHorizontalVertical(p_isValid_1_);
            }
            case 9: {
                return this.isValidVerticalHorizontal(p_isValid_1_);
            }
            default: {
                Config.warn("Unknown method: " + p_isValid_1_);
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
        int j;
        int i;
        for (i = (j = "block".length()); j < this.name.length(); ++j) {
            final char c0 = this.name.charAt(j);
            if (c0 < '0') {
                break;
            }
            if (c0 > '9') {
                break;
            }
        }
        if (j == i) {
            return null;
        }
        final String s = this.name.substring(i, j);
        final int k = Config.parseInt(s, -1);
        return (int[])((k < 0) ? null : new int[] { k });
    }
    
    private String[] detectMatchTiles() {
        final TextureAtlasSprite textureatlassprite = getIcon(this.name);
        return (String[])((textureatlassprite == null) ? null : new String[] { this.name });
    }
    
    private static TextureAtlasSprite getIcon(final String p_getIcon_0_) {
        final TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe(p_getIcon_0_);
        if (textureatlassprite != null) {
            return textureatlassprite;
        }
        textureatlassprite = texturemap.getSpriteSafe("blocks/" + p_getIcon_0_);
        return textureatlassprite;
    }
    
    private boolean isValidCtm(final String p_isValidCtm_1_) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("0-11 16-27 32-43 48-58");
        }
        if (this.tiles.length < 47) {
            Config.warn("Invalid tiles, must be at least 47: " + p_isValidCtm_1_);
            return false;
        }
        return true;
    }
    
    private boolean isValidHorizontal(final String p_isValidHorizontal_1_) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("12-15");
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + p_isValidHorizontal_1_);
            return false;
        }
        return true;
    }
    
    private boolean isValidVertical(final String p_isValidVertical_1_) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical: " + p_isValidVertical_1_);
            return false;
        }
        if (this.tiles.length != 4) {
            Config.warn("Invalid tiles, must be exactly 4: " + p_isValidVertical_1_);
            return false;
        }
        return true;
    }
    
    private boolean isValidHorizontalVertical(final String p_isValidHorizontalVertical_1_) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for horizontal+vertical: " + p_isValidHorizontalVertical_1_);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + p_isValidHorizontalVertical_1_);
            return false;
        }
        return true;
    }
    
    private boolean isValidVerticalHorizontal(final String p_isValidVerticalHorizontal_1_) {
        if (this.tiles == null) {
            Config.warn("No tiles defined for vertical+horizontal: " + p_isValidVerticalHorizontal_1_);
            return false;
        }
        if (this.tiles.length != 7) {
            Config.warn("Invalid tiles, must be exactly 7: " + p_isValidVerticalHorizontal_1_);
            return false;
        }
        return true;
    }
    
    private boolean isValidRandom(final String p_isValidRandom_1_) {
        if (this.tiles != null && this.tiles.length > 0) {
            if (this.weights != null) {
                if (this.weights.length > this.tiles.length) {
                    Config.warn("More weights defined than tiles, trimming weights: " + p_isValidRandom_1_);
                    final int[] aint = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, aint, 0, aint.length);
                    this.weights = aint;
                }
                if (this.weights.length < this.tiles.length) {
                    Config.warn("Less weights defined than tiles, expanding weights: " + p_isValidRandom_1_);
                    final int[] aint2 = new int[this.tiles.length];
                    System.arraycopy(this.weights, 0, aint2, 0, this.weights.length);
                    final int i = ConnectedUtils.getAverage(this.weights);
                    for (int j = this.weights.length; j < aint2.length; ++j) {
                        aint2[j] = i;
                    }
                    this.weights = aint2;
                }
                this.sumWeights = new int[this.weights.length];
                int k = 0;
                for (int l = 0; l < this.weights.length; ++l) {
                    k += this.weights[l];
                    this.sumWeights[l] = k;
                }
                this.sumAllWeights = k;
                if (this.sumAllWeights <= 0) {
                    Config.warn("Invalid sum of all weights: " + k);
                    this.sumAllWeights = 1;
                }
            }
            return true;
        }
        Config.warn("Tiles not defined: " + p_isValidRandom_1_);
        return false;
    }
    
    private boolean isValidRepeat(final String p_isValidRepeat_1_) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + p_isValidRepeat_1_);
            return false;
        }
        if (this.width <= 0 || this.width > 16) {
            Config.warn("Invalid width: " + p_isValidRepeat_1_);
            return false;
        }
        if (this.height <= 0 || this.height > 16) {
            Config.warn("Invalid height: " + p_isValidRepeat_1_);
            return false;
        }
        if (this.tiles.length != this.width * this.height) {
            Config.warn("Number of tiles does not equal width x height: " + p_isValidRepeat_1_);
            return false;
        }
        return true;
    }
    
    private boolean isValidFixed(final String p_isValidFixed_1_) {
        if (this.tiles == null) {
            Config.warn("Tiles not defined: " + p_isValidFixed_1_);
            return false;
        }
        if (this.tiles.length != 1) {
            Config.warn("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        return true;
    }
    
    private boolean isValidTop(final String p_isValidTop_1_) {
        if (this.tiles == null) {
            this.tiles = this.parseTileNames("66");
        }
        if (this.tiles.length != 1) {
            Config.warn("Invalid tiles, must be exactly 1: " + p_isValidTop_1_);
            return false;
        }
        return true;
    }
    
    public void updateIcons(final TextureMap p_updateIcons_1_) {
        if (this.matchTiles != null) {
            this.matchTileIcons = registerIcons(this.matchTiles, p_updateIcons_1_);
        }
        if (this.tiles != null) {
            this.tileIcons = registerIcons(this.tiles, p_updateIcons_1_);
        }
    }
    
    private static TextureAtlasSprite[] registerIcons(final String[] p_registerIcons_0_, final TextureMap p_registerIcons_1_) {
        if (p_registerIcons_0_ == null) {
            return null;
        }
        final List list = new ArrayList();
        for (int i = 0; i < p_registerIcons_0_.length; ++i) {
            final String s = p_registerIcons_0_[i];
            final ResourceLocation resourcelocation = new ResourceLocation(s);
            final String s2 = resourcelocation.getResourceDomain();
            String s3 = resourcelocation.getResourcePath();
            if (!s3.contains("/")) {
                s3 = "textures/blocks/" + s3;
            }
            final String s4 = s3 + ".png";
            final ResourceLocation resourcelocation2 = new ResourceLocation(s2, s4);
            final boolean flag = Config.hasResource(resourcelocation2);
            if (!flag) {
                Config.warn("File not found: " + s4);
            }
            final String s5 = "textures/";
            String s6 = s3;
            if (s3.startsWith(s5)) {
                s6 = s3.substring(s5.length());
            }
            final ResourceLocation resourcelocation3 = new ResourceLocation(s2, s6);
            final TextureAtlasSprite textureatlassprite = p_registerIcons_1_.registerSprite(resourcelocation3);
            list.add(textureatlassprite);
        }
        final TextureAtlasSprite[] atextureatlassprite = list.toArray(new TextureAtlasSprite[list.size()]);
        return atextureatlassprite;
    }
    
    public boolean matchesBlock(final int p_matchesBlock_1_) {
        if (this.matchBlocks != null && this.matchBlocks.length > 0) {
            for (int i = 0; i < this.matchBlocks.length; ++i) {
                final int j = this.matchBlocks[i];
                if (j == p_matchesBlock_1_) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    public boolean matchesIcon(final TextureAtlasSprite p_matchesIcon_1_) {
        if (this.matchTileIcons != null && this.matchTileIcons.length > 0) {
            for (int i = 0; i < this.matchTileIcons.length; ++i) {
                if (this.matchTileIcons[i] == p_matchesIcon_1_) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString(this.matchTiles);
    }
}
