package net.minecraft.world;

public class WorldType
{
    private static final String[] I;
    public static final WorldType DEFAULT;
    public static final WorldType LARGE_BIOMES;
    private boolean hasNotificationData;
    public static final WorldType AMPLIFIED;
    private final int generatorVersion;
    public static final WorldType[] worldTypes;
    public static final WorldType DEFAULT_1_1;
    private boolean isWorldTypeVersioned;
    public static final WorldType CUSTOMIZED;
    public static final WorldType DEBUG_WORLD;
    public static final WorldType FLAT;
    private final int worldTypeId;
    private final String worldType;
    private boolean canBeCreated;
    
    private WorldType(final int n, final String s) {
        this(n, s, "".length());
    }
    
    public String getTranslateName() {
        return WorldType.I[0x44 ^ 0x43] + this.worldType;
    }
    
    public WorldType getWorldTypeForGeneratorVersion(final int n) {
        WorldType default_1_1;
        if (this == WorldType.DEFAULT && n == 0) {
            default_1_1 = WorldType.DEFAULT_1_1;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            default_1_1 = this;
        }
        return default_1_1;
    }
    
    public boolean getCanBeCreated() {
        return this.canBeCreated;
    }
    
    public boolean showWorldInfoNotice() {
        return this.hasNotificationData;
    }
    
    public static WorldType parseWorldType(final String s) {
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < WorldType.worldTypes.length) {
            if (WorldType.worldTypes[i] != null && WorldType.worldTypes[i].worldType.equalsIgnoreCase(s)) {
                return WorldType.worldTypes[i];
            }
            ++i;
        }
        return null;
    }
    
    public String func_151359_c() {
        return String.valueOf(this.getTranslateName()) + WorldType.I[0xBE ^ 0xB6];
    }
    
    static {
        I();
        worldTypes = new WorldType[0xBE ^ 0xAE];
        DEFAULT = new WorldType("".length(), WorldType.I["".length()], " ".length()).setVersioned();
        FLAT = new WorldType(" ".length(), WorldType.I[" ".length()]);
        LARGE_BIOMES = new WorldType("  ".length(), WorldType.I["  ".length()]);
        AMPLIFIED = new WorldType("   ".length(), WorldType.I["   ".length()]).setNotificationData();
        CUSTOMIZED = new WorldType(0xAA ^ 0xAE, WorldType.I[0x3 ^ 0x7]);
        DEBUG_WORLD = new WorldType(0x12 ^ 0x17, WorldType.I[0x54 ^ 0x51]);
        DEFAULT_1_1 = new WorldType(0x37 ^ 0x3F, WorldType.I[0xAF ^ 0xA9], "".length()).setCanBeCreated("".length() != 0);
    }
    
    public int getWorldTypeID() {
        return this.worldTypeId;
    }
    
    public boolean isVersioned() {
        return this.isWorldTypeVersioned;
    }
    
    public String getWorldTypeName() {
        return this.worldType;
    }
    
    private WorldType setNotificationData() {
        this.hasNotificationData = (" ".length() != 0);
        return this;
    }
    
    private WorldType setVersioned() {
        this.isWorldTypeVersioned = (" ".length() != 0);
        return this;
    }
    
    private WorldType setCanBeCreated(final boolean canBeCreated) {
        this.canBeCreated = canBeCreated;
        return this;
    }
    
    private WorldType(final int worldTypeId, final String worldType, final int generatorVersion) {
        this.worldType = worldType;
        this.generatorVersion = generatorVersion;
        this.canBeCreated = (" ".length() != 0);
        this.worldTypeId = worldTypeId;
        WorldType.worldTypes[worldTypeId] = this;
    }
    
    private static void I() {
        (I = new String[0x77 ^ 0x7E])["".length()] = I("\r&4,/\u00057", "iCRMZ");
        WorldType.I[" ".length()] = I(" \u000f\"\u0019", "FcCmW");
        WorldType.I["  ".length()] = I("+;\u0000\t5\u00053\u001d\u000354", "GZrnP");
        WorldType.I["   ".length()] = I("\u0000+\u0014\n\u0013\u0007/\u0001\u0002", "aFdfz");
        WorldType.I[0x8C ^ 0x88] = I("\u0002\u0011%\u0011\u001d\f\r,\u0000\u0016", "adVer");
        WorldType.I[0x3A ^ 0x3F] = I("\"\u0010\f\r \u0019\u0014\u0002\u0014\u0018$\u0019\u0001\u001b,\u0019\u0006\u001a\u00193#\u0006", "FunxG");
        WorldType.I[0x67 ^ 0x61] = I("\u001d\u00160\u000b \u0015\u0007\t[\nH", "ysVjU");
        WorldType.I[0x74 ^ 0x73] = I("1\u00047+$7\u00156<x", "VaYNV");
        WorldType.I[0xCA ^ 0xC2] = I("y+\u000f\u0005=", "WBacR");
    }
    
    public int getGeneratorVersion() {
        return this.generatorVersion;
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
}
