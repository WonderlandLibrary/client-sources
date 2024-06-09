package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Properties;

public class ConnectedProperties
{
    public String HorizonCode_Horizon_È;
    public String Â;
    public int[] Ý;
    public String[] Ø­áŒŠá;
    public int Âµá€;
    public String[] Ó;
    public int à;
    public int Ø;
    public int[] áŒŠÆ;
    public BiomeGenBase[] áˆºÑ¢Õ;
    public int ÂµÈ;
    public int á;
    public int ˆÏ­;
    public boolean £á;
    public int Å;
    public int £à;
    public int[] µà;
    public int ˆà;
    public int[] ¥Æ;
    public int Ø­à;
    public TextureAtlasSprite[] µÕ;
    public TextureAtlasSprite[] Æ;
    public static final int Šáƒ = 0;
    public static final int Ï­Ðƒà = 1;
    public static final int áŒŠà = 2;
    public static final int ŠÄ = 3;
    public static final int Ñ¢á = 4;
    public static final int ŒÏ = 5;
    public static final int Çªà¢ = 6;
    public static final int Ê = 7;
    public static final int ÇŽÉ = 8;
    public static final int ˆá = 9;
    public static final int ÇŽÕ = 0;
    public static final int É = 1;
    public static final int áƒ = 2;
    public static final int á€ = 3;
    public static final int Õ = 128;
    public static final int à¢ = 1;
    public static final int ŠÂµà = 2;
    public static final int ¥à = 4;
    public static final int Âµà = 8;
    public static final int Ç = 16;
    public static final int È = 32;
    public static final int áŠ = 60;
    public static final int ˆáŠ = 63;
    public static final int áŒŠ = 128;
    public static final int £ÂµÄ = 1;
    public static final int Ø­Âµ = 2;
    public static final int Ä = 6;
    public static final int Ñ¢Â = 128;
    
    public ConnectedProperties(final Properties props, final String path) {
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
        this.Ý = null;
        this.Ø­áŒŠá = null;
        this.Âµá€ = 0;
        this.Ó = null;
        this.à = 0;
        this.Ø = 63;
        this.áŒŠÆ = null;
        this.áˆºÑ¢Õ = null;
        this.ÂµÈ = 0;
        this.á = 1024;
        this.ˆÏ­ = 0;
        this.£á = false;
        this.Å = 0;
        this.£à = 0;
        this.µà = null;
        this.ˆà = 1;
        this.¥Æ = null;
        this.Ø­à = 1;
        this.µÕ = null;
        this.Æ = null;
        this.HorizonCode_Horizon_È = Ý(path);
        this.Â = Ø­áŒŠá(path);
        this.Ý = Å(props.getProperty("matchBlocks"));
        this.Ø­áŒŠá = this.Â(props.getProperty("matchTiles"));
        this.Âµá€ = µà(props.getProperty("method"));
        this.Ó = this.à(props.getProperty("tiles"));
        this.à = ˆÏ­(props.getProperty("connect"));
        this.Ø = ÂµÈ(props.getProperty("faces"));
        this.áŒŠÆ = £á(props.getProperty("metadata"));
        this.áˆºÑ¢Õ = Âµá€(props.getProperty("biomes"));
        this.ÂµÈ = HorizonCode_Horizon_È(props.getProperty("minHeight"), -1);
        this.á = HorizonCode_Horizon_È(props.getProperty("maxHeight"), 1024);
        this.ˆÏ­ = Ø(props.getProperty("renderPass"));
        this.£á = áŒŠÆ(props.getProperty("innerSeams"));
        this.Å = Ø(props.getProperty("width"));
        this.£à = Ø(props.getProperty("height"));
        this.µà = £á(props.getProperty("weights"));
        this.ˆà = áˆºÑ¢Õ(props.getProperty("symmetry"));
    }
    
    private String[] Â(final String str) {
        if (str == null) {
            return null;
        }
        final String[] names = Config.HorizonCode_Horizon_È(str, " ");
        for (int i = 0; i < names.length; ++i) {
            String iconName = names[i];
            if (iconName.endsWith(".png")) {
                iconName = iconName.substring(0, iconName.length() - 4);
            }
            iconName = TextureUtils.HorizonCode_Horizon_È(iconName, this.Â);
            names[i] = iconName;
        }
        return names;
    }
    
    private static String Ý(final String path) {
        String str = path;
        final int pos = path.lastIndexOf(47);
        if (pos >= 0) {
            str = path.substring(pos + 1);
        }
        final int pos2 = str.lastIndexOf(46);
        if (pos2 >= 0) {
            str = str.substring(0, pos2);
        }
        return str;
    }
    
    private static String Ø­áŒŠá(final String path) {
        final int pos = path.lastIndexOf(47);
        return (pos < 0) ? "" : path.substring(0, pos);
    }
    
    private static BiomeGenBase[] Âµá€(final String str) {
        if (str == null) {
            return null;
        }
        final String[] biomeNames = Config.HorizonCode_Horizon_È(str, " ");
        final ArrayList list = new ArrayList();
        for (int biomeArr = 0; biomeArr < biomeNames.length; ++biomeArr) {
            final String biomeName = biomeNames[biomeArr];
            final BiomeGenBase biome = Ó(biomeName);
            if (biome == null) {
                Config.Â("Biome not found: " + biomeName);
            }
            else {
                list.add(biome);
            }
        }
        final BiomeGenBase[] var6 = list.toArray(new BiomeGenBase[list.size()]);
        return var6;
    }
    
    private static BiomeGenBase Ó(String biomeName) {
        biomeName = biomeName.toLowerCase();
        final BiomeGenBase[] biomeList = BiomeGenBase.£á();
        for (int i = 0; i < biomeList.length; ++i) {
            final BiomeGenBase biome = biomeList[i];
            if (biome != null) {
                final String name = biome.£Ï.replace(" ", "").toLowerCase();
                if (name.equals(biomeName)) {
                    return biome;
                }
            }
        }
        return null;
    }
    
    private String[] à(final String str) {
        if (str == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] iconStrs = Config.HorizonCode_Horizon_È(str, " ,");
        for (int names = 0; names < iconStrs.length; ++names) {
            final String i = iconStrs[names];
            if (i.contains("-")) {
                final String[] iconName = Config.HorizonCode_Horizon_È(i, "-");
                if (iconName.length == 2) {
                    final int pathBlocks = Config.HorizonCode_Horizon_È(iconName[0], -1);
                    final int max = Config.HorizonCode_Horizon_È(iconName[1], -1);
                    if (pathBlocks >= 0 && max >= 0) {
                        if (pathBlocks <= max) {
                            for (int n = pathBlocks; n <= max; ++n) {
                                list.add(String.valueOf(n));
                            }
                            continue;
                        }
                        Config.Â("Invalid interval: " + i + ", when parsing: " + str);
                        continue;
                    }
                }
            }
            list.add(i);
        }
        final String[] var10 = list.toArray(new String[list.size()]);
        for (int var11 = 0; var11 < var10.length; ++var11) {
            String var12 = var10[var11];
            var12 = TextureUtils.HorizonCode_Horizon_È(var12, this.Â);
            if (!var12.startsWith(this.Â) && !var12.startsWith("textures/") && !var12.startsWith("mcpatcher/")) {
                var12 = String.valueOf(this.Â) + "/" + var12;
            }
            if (var12.endsWith(".png")) {
                var12 = var12.substring(0, var12.length() - 4);
            }
            final String var13 = "textures/blocks/";
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
    
    private static int Ø(final String str) {
        if (str == null) {
            return -1;
        }
        final int num = Config.HorizonCode_Horizon_È(str, -1);
        if (num < 0) {
            Config.Â("Invalid number: " + str);
        }
        return num;
    }
    
    private static int HorizonCode_Horizon_È(final String str, final int defVal) {
        if (str == null) {
            return defVal;
        }
        final int num = Config.HorizonCode_Horizon_È(str, -1);
        if (num < 0) {
            Config.Â("Invalid number: " + str);
            return defVal;
        }
        return num;
    }
    
    private static boolean áŒŠÆ(final String str) {
        return str != null && str.toLowerCase().equals("true");
    }
    
    private static int áˆºÑ¢Õ(final String str) {
        if (str == null) {
            return 1;
        }
        if (str.equals("opposite")) {
            return 2;
        }
        if (str.equals("all")) {
            return 6;
        }
        Config.Â("Unknown symmetry: " + str);
        return 1;
    }
    
    private static int ÂµÈ(final String str) {
        if (str == null) {
            return 63;
        }
        final String[] faceStrs = Config.HorizonCode_Horizon_È(str, " ,");
        int facesMask = 0;
        for (int i = 0; i < faceStrs.length; ++i) {
            final String faceStr = faceStrs[i];
            final int faceMask = á(faceStr);
            facesMask |= faceMask;
        }
        return facesMask;
    }
    
    private static int á(String str) {
        str = str.toLowerCase();
        if (str.equals("bottom") || str.equals("down")) {
            return 1;
        }
        if (str.equals("top") || str.equals("up")) {
            return 2;
        }
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
        Config.Â("Unknown face: " + str);
        return 128;
    }
    
    private static int ˆÏ­(final String str) {
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
        Config.Â("Unknown connect: " + str);
        return 128;
    }
    
    private static int[] £á(final String str) {
        if (str == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] intStrs = Config.HorizonCode_Horizon_È(str, " ,");
        for (int ints = 0; ints < intStrs.length; ++ints) {
            final String i = intStrs[ints];
            if (i.contains("-")) {
                final String[] val = Config.HorizonCode_Horizon_È(i, "-");
                if (val.length != 2) {
                    Config.Â("Invalid interval: " + i + ", when parsing: " + str);
                }
                else {
                    final int min = Config.HorizonCode_Horizon_È(val[0], -1);
                    final int max = Config.HorizonCode_Horizon_È(val[1], -1);
                    if (min >= 0 && max >= 0 && min <= max) {
                        for (int n = min; n <= max; ++n) {
                            list.add(n);
                        }
                    }
                    else {
                        Config.Â("Invalid interval: " + i + ", when parsing: " + str);
                    }
                }
            }
            else {
                final int var11 = Config.HorizonCode_Horizon_È(i, -1);
                if (var11 < 0) {
                    Config.Â("Invalid number: " + i + ", when parsing: " + str);
                }
                else {
                    list.add(var11);
                }
            }
        }
        final int[] var12 = new int[list.size()];
        for (int var13 = 0; var13 < var12.length; ++var13) {
            var12[var13] = list.get(var13);
        }
        return var12;
    }
    
    private static int[] Å(final String str) {
        if (str == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] intStrs = Config.HorizonCode_Horizon_È(str, " ,");
        for (int ints = 0; ints < intStrs.length; ++ints) {
            final String i = intStrs[ints];
            if (i.contains("-")) {
                final String[] val = Config.HorizonCode_Horizon_È(i, "-");
                if (val.length != 2) {
                    Config.Â("Invalid interval: " + i + ", when parsing: " + str);
                }
                else {
                    final int min = £à(val[0]);
                    final int max = £à(val[1]);
                    if (min >= 0 && max >= 0 && min <= max) {
                        for (int n = min; n <= max; ++n) {
                            list.add(n);
                        }
                    }
                    else {
                        Config.Â("Invalid interval: " + i + ", when parsing: " + str);
                    }
                }
            }
            else {
                final int var11 = £à(i);
                if (var11 < 0) {
                    Config.Â("Invalid block ID: " + i + ", when parsing: " + str);
                }
                else {
                    list.add(var11);
                }
            }
        }
        final int[] var12 = new int[list.size()];
        for (int var13 = 0; var13 < var12.length; ++var13) {
            var12[var13] = list.get(var13);
        }
        return var12;
    }
    
    private static int £à(final String blockStr) {
        final int val = Config.HorizonCode_Horizon_È(blockStr, -1);
        if (val >= 0) {
            return val;
        }
        final Block block = Block.HorizonCode_Horizon_È(blockStr);
        return (block != null) ? Block.HorizonCode_Horizon_È(block) : -1;
    }
    
    private static int µà(final String str) {
        if (str == null) {
            return 1;
        }
        if (str.equals("ctm") || str.equals("glass")) {
            return 1;
        }
        if (str.equals("horizontal") || str.equals("bookshelf")) {
            return 2;
        }
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
        if (str.equals("horizontal+vertical") || str.equals("h+v")) {
            return 8;
        }
        if (!str.equals("vertical+horizontal") && !str.equals("v+h")) {
            Config.Â("Unknown method: " + str);
            return 0;
        }
        return 9;
    }
    
    public boolean HorizonCode_Horizon_È(final String path) {
        if (this.HorizonCode_Horizon_È == null || this.HorizonCode_Horizon_È.length() <= 0) {
            Config.Â("No name found: " + path);
            return false;
        }
        if (this.Â == null) {
            Config.Â("No base path found: " + path);
            return false;
        }
        if (this.Ý == null) {
            this.Ý = this.Â();
        }
        if (this.Ø­áŒŠá == null && this.Ý == null) {
            this.Ø­áŒŠá = this.Ý();
        }
        if (this.Ý == null && this.Ø­áŒŠá == null) {
            Config.Â("No matchBlocks or matchTiles specified: " + path);
            return false;
        }
        if (this.Âµá€ == 0) {
            Config.Â("No method: " + path);
            return false;
        }
        if (this.Ó == null || this.Ó.length <= 0) {
            Config.Â("No tiles specified: " + path);
            return false;
        }
        if (this.à == 0) {
            this.à = this.HorizonCode_Horizon_È();
        }
        if (this.à == 128) {
            Config.Â("Invalid connect in: " + path);
            return false;
        }
        if (this.ˆÏ­ > 0) {
            Config.Â("Render pass not supported: " + this.ˆÏ­);
            return false;
        }
        if ((this.Ø & 0x80) != 0x0) {
            Config.Â("Invalid faces in: " + path);
            return false;
        }
        if ((this.ˆà & 0x80) != 0x0) {
            Config.Â("Invalid symmetry in: " + path);
            return false;
        }
        switch (this.Âµá€) {
            case 1: {
                return this.¥Æ(path);
            }
            case 2: {
                return this.Ø­à(path);
            }
            case 3: {
                return this.Ñ¢á(path);
            }
            case 4: {
                return this.Ï­Ðƒà(path);
            }
            case 5: {
                return this.áŒŠà(path);
            }
            case 6: {
                return this.µÕ(path);
            }
            case 7: {
                return this.ŠÄ(path);
            }
            case 8: {
                return this.Æ(path);
            }
            case 9: {
                return this.Šáƒ(path);
            }
            default: {
                Config.Â("Unknown method: " + path);
                return false;
            }
        }
    }
    
    private int HorizonCode_Horizon_È() {
        return (this.Ý != null) ? 1 : ((this.Ø­áŒŠá != null) ? 2 : 128);
    }
    
    private int[] Â() {
        if (!this.HorizonCode_Horizon_È.startsWith("block")) {
            return null;
        }
        int pos;
        int startPos;
        for (startPos = (pos = "block".length()); pos < this.HorizonCode_Horizon_È.length(); ++pos) {
            final char idStr = this.HorizonCode_Horizon_È.charAt(pos);
            if (idStr < '0') {
                break;
            }
            if (idStr > '9') {
                break;
            }
        }
        if (pos == startPos) {
            return null;
        }
        final String var5 = this.HorizonCode_Horizon_È.substring(startPos, pos);
        final int id = Config.HorizonCode_Horizon_È(var5, -1);
        final int[] array;
        if (id >= 0) {
            array = new int[] { id };
        }
        return array;
    }
    
    private String[] Ý() {
        final TextureAtlasSprite icon = ˆà(this.HorizonCode_Horizon_È);
        final String[] array;
        if (icon != null) {
            array = new String[] { this.HorizonCode_Horizon_È };
        }
        return array;
    }
    
    private static TextureAtlasSprite ˆà(final String iconName) {
        final TextureMap textureMapBlocks = Minecraft.áŒŠà().áŠ();
        TextureAtlasSprite icon = textureMapBlocks.Ý(iconName);
        if (icon != null) {
            return icon;
        }
        icon = textureMapBlocks.Ý("blocks/" + iconName);
        return icon;
    }
    
    private boolean ¥Æ(final String path) {
        if (this.Ó == null) {
            this.Ó = this.à("0-11 16-27 32-43 48-58");
        }
        if (this.Ó.length < 47) {
            Config.Â("Invalid tiles, must be at least 47: " + path);
            return false;
        }
        return true;
    }
    
    private boolean Ø­à(final String path) {
        if (this.Ó == null) {
            this.Ó = this.à("12-15");
        }
        if (this.Ó.length != 4) {
            Config.Â("Invalid tiles, must be exactly 4: " + path);
            return false;
        }
        return true;
    }
    
    private boolean µÕ(final String path) {
        if (this.Ó == null) {
            Config.Â("No tiles defined for vertical: " + path);
            return false;
        }
        if (this.Ó.length != 4) {
            Config.Â("Invalid tiles, must be exactly 4: " + path);
            return false;
        }
        return true;
    }
    
    private boolean Æ(final String path) {
        if (this.Ó == null) {
            Config.Â("No tiles defined for horizontal+vertical: " + path);
            return false;
        }
        if (this.Ó.length != 7) {
            Config.Â("Invalid tiles, must be exactly 7: " + path);
            return false;
        }
        return true;
    }
    
    private boolean Šáƒ(final String path) {
        if (this.Ó == null) {
            Config.Â("No tiles defined for vertical+horizontal: " + path);
            return false;
        }
        if (this.Ó.length != 7) {
            Config.Â("Invalid tiles, must be exactly 7: " + path);
            return false;
        }
        return true;
    }
    
    private boolean Ï­Ðƒà(final String path) {
        if (this.Ó != null && this.Ó.length > 0) {
            if (this.µà != null) {
                if (this.µà.length > this.Ó.length) {
                    Config.Â("More weights defined than tiles, trimming weights: " + path);
                    final int[] sum = new int[this.Ó.length];
                    System.arraycopy(this.µà, 0, sum, 0, sum.length);
                    this.µà = sum;
                }
                if (this.µà.length < this.Ó.length) {
                    Config.Â("Less weights defined than tiles, expanding weights: " + path);
                    final int[] sum = new int[this.Ó.length];
                    System.arraycopy(this.µà, 0, sum, 0, this.µà.length);
                    final int i = this.HorizonCode_Horizon_È(this.µà);
                    for (int i2 = this.µà.length; i2 < sum.length; ++i2) {
                        sum[i2] = i;
                    }
                    this.µà = sum;
                }
                this.¥Æ = new int[this.µà.length];
                int var5 = 0;
                for (int i = 0; i < this.µà.length; ++i) {
                    var5 += this.µà[i];
                    this.¥Æ[i] = var5;
                }
                this.Ø­à = var5;
                if (this.Ø­à <= 0) {
                    Config.Â("Invalid sum of all weights: " + var5);
                    this.Ø­à = 1;
                }
            }
            return true;
        }
        Config.Â("Tiles not defined: " + path);
        return false;
    }
    
    private int HorizonCode_Horizon_È(final int[] vals) {
        if (vals.length <= 0) {
            return 0;
        }
        int sum = 0;
        for (int avg = 0; avg < vals.length; ++avg) {
            final int val = vals[avg];
            sum += val;
        }
        int avg = sum / vals.length;
        return avg;
    }
    
    private boolean áŒŠà(final String path) {
        if (this.Ó == null) {
            Config.Â("Tiles not defined: " + path);
            return false;
        }
        if (this.Å <= 0 || this.Å > 16) {
            Config.Â("Invalid width: " + path);
            return false;
        }
        if (this.£à <= 0 || this.£à > 16) {
            Config.Â("Invalid height: " + path);
            return false;
        }
        if (this.Ó.length != this.Å * this.£à) {
            Config.Â("Number of tiles does not equal width x height: " + path);
            return false;
        }
        return true;
    }
    
    private boolean ŠÄ(final String path) {
        if (this.Ó == null) {
            Config.Â("Tiles not defined: " + path);
            return false;
        }
        if (this.Ó.length != 1) {
            Config.Â("Number of tiles should be 1 for method: fixed.");
            return false;
        }
        return true;
    }
    
    private boolean Ñ¢á(final String path) {
        if (this.Ó == null) {
            this.Ó = this.à("66");
        }
        if (this.Ó.length != 1) {
            Config.Â("Invalid tiles, must be exactly 1: " + path);
            return false;
        }
        return true;
    }
    
    public void HorizonCode_Horizon_È(final TextureMap textureMap) {
        if (this.Ø­áŒŠá != null) {
            this.µÕ = HorizonCode_Horizon_È(this.Ø­áŒŠá, textureMap);
        }
        if (this.Ó != null) {
            this.Æ = HorizonCode_Horizon_È(this.Ó, textureMap);
        }
    }
    
    private static TextureAtlasSprite[] HorizonCode_Horizon_È(final String[] tileNames, final TextureMap textureMap) {
        if (tileNames == null) {
            return null;
        }
        final ArrayList iconList = new ArrayList();
        for (int icons = 0; icons < tileNames.length; ++icons) {
            final String iconName = tileNames[icons];
            final ResourceLocation_1975012498 resLoc = new ResourceLocation_1975012498(iconName);
            final String domain = resLoc.Ý();
            String path = resLoc.Â();
            if (!path.contains("/")) {
                path = "textures/blocks/" + path;
            }
            final String filePath = String.valueOf(path) + ".png";
            final ResourceLocation_1975012498 locFile = new ResourceLocation_1975012498(domain, filePath);
            final boolean exists = Config.Ý(locFile);
            if (!exists) {
                Config.Â("File not found: " + filePath);
            }
            final String prefixTextures = "textures/";
            String pathSprite = path;
            if (path.startsWith(prefixTextures)) {
                pathSprite = path.substring(prefixTextures.length());
            }
            final ResourceLocation_1975012498 locSprite = new ResourceLocation_1975012498(domain, pathSprite);
            final TextureAtlasSprite icon = textureMap.HorizonCode_Horizon_È(locSprite);
            iconList.add(icon);
        }
        final TextureAtlasSprite[] var15 = iconList.toArray(new TextureAtlasSprite[iconList.size()]);
        return var15;
    }
    
    public boolean HorizonCode_Horizon_È(final int blockId) {
        if (this.Ý != null && this.Ý.length > 0) {
            for (int i = 0; i < this.Ý.length; ++i) {
                final int matchId = this.Ý[i];
                if (matchId == blockId) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    public boolean HorizonCode_Horizon_È(final TextureAtlasSprite icon) {
        if (this.µÕ != null && this.µÕ.length > 0) {
            for (int i = 0; i < this.µÕ.length; ++i) {
                if (this.µÕ[i] == icon) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "CTM name: " + this.HorizonCode_Horizon_È + ", basePath: " + this.Â + ", matchBlocks: " + Config.HorizonCode_Horizon_È(this.Ý) + ", matchTiles: " + Config.HorizonCode_Horizon_È(this.Ø­áŒŠá);
    }
}
