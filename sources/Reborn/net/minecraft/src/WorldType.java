package net.minecraft.src;

public class WorldType
{
    public static final WorldType[] worldTypes;
    public static final WorldType DEFAULT;
    public static final WorldType FLAT;
    public static final WorldType LARGE_BIOMES;
    public static final WorldType DEFAULT_1_1;
    private final int worldTypeId;
    private final String worldType;
    private final int generatorVersion;
    private boolean canBeCreated;
    private boolean isWorldTypeVersioned;
    
    static {
        worldTypes = new WorldType[16];
        DEFAULT = new WorldType(0, "default", 1).setVersioned();
        FLAT = new WorldType(1, "flat");
        LARGE_BIOMES = new WorldType(2, "largeBiomes");
        DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
    }
    
    private WorldType(final int par1, final String par2Str) {
        this(par1, par2Str, 0);
    }
    
    private WorldType(final int par1, final String par2Str, final int par3) {
        this.worldType = par2Str;
        this.generatorVersion = par3;
        this.canBeCreated = true;
        this.worldTypeId = par1;
        WorldType.worldTypes[par1] = this;
    }
    
    public String getWorldTypeName() {
        return this.worldType;
    }
    
    public String getTranslateName() {
        return "generator." + this.worldType;
    }
    
    public int getGeneratorVersion() {
        return this.generatorVersion;
    }
    
    public WorldType getWorldTypeForGeneratorVersion(final int par1) {
        return (this == WorldType.DEFAULT && par1 == 0) ? WorldType.DEFAULT_1_1 : this;
    }
    
    private WorldType setCanBeCreated(final boolean par1) {
        this.canBeCreated = par1;
        return this;
    }
    
    public boolean getCanBeCreated() {
        return this.canBeCreated;
    }
    
    private WorldType setVersioned() {
        this.isWorldTypeVersioned = true;
        return this;
    }
    
    public boolean isVersioned() {
        return this.isWorldTypeVersioned;
    }
    
    public static WorldType parseWorldType(final String par0Str) {
        for (int var1 = 0; var1 < WorldType.worldTypes.length; ++var1) {
            if (WorldType.worldTypes[var1] != null && WorldType.worldTypes[var1].worldType.equalsIgnoreCase(par0Str)) {
                return WorldType.worldTypes[var1];
            }
        }
        return null;
    }
    
    public int getWorldTypeID() {
        return this.worldTypeId;
    }
}
