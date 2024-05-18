package HORIZON-6-0-SKIDPROTECTION;

public class WorldType
{
    public static final WorldType[] HorizonCode_Horizon_È;
    public static String Â;
    public static final WorldType Ý;
    public static final WorldType Ø­áŒŠá;
    public static final WorldType Âµá€;
    public static final WorldType Ó;
    public static final WorldType à;
    public static final WorldType Ø;
    public static final WorldType áŒŠÆ;
    private final int áˆºÑ¢Õ;
    private final String ÂµÈ;
    private final int á;
    private boolean ˆÏ­;
    private boolean £á;
    private boolean Å;
    private static final String £à = "CL_00000150";
    
    static {
        HorizonCode_Horizon_È = new WorldType[16];
        WorldType.Â = "dHJ1ZQ==";
        Ý = new WorldType(0, "default", 1).áŒŠÆ();
        Ø­áŒŠá = new WorldType(1, "flat");
        Âµá€ = new WorldType(2, "largeBiomes");
        Ó = new WorldType(3, "amplified").áˆºÑ¢Õ();
        à = new WorldType(4, "customized");
        Ø = new WorldType(5, "debug_all_block_states");
        áŒŠÆ = new WorldType(8, "default_1_1", 0).HorizonCode_Horizon_È(false);
    }
    
    private WorldType(final int id, final String name) {
        this(id, name, 0);
    }
    
    private WorldType(final int id, final String name, final int version) {
        this.ÂµÈ = name;
        this.á = version;
        this.ˆÏ­ = true;
        this.áˆºÑ¢Õ = id;
        WorldType.HorizonCode_Horizon_È[id] = this;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.ÂµÈ;
    }
    
    public String Â() {
        return "generator." + this.ÂµÈ;
    }
    
    public String Ý() {
        return String.valueOf(this.Â()) + ".info";
    }
    
    public int Ø­áŒŠá() {
        return this.á;
    }
    
    public WorldType HorizonCode_Horizon_È(final int version) {
        return (this == WorldType.Ý && version == 0) ? WorldType.áŒŠÆ : this;
    }
    
    private WorldType HorizonCode_Horizon_È(final boolean enable) {
        this.ˆÏ­ = enable;
        return this;
    }
    
    public boolean Âµá€() {
        return this.ˆÏ­;
    }
    
    private WorldType áŒŠÆ() {
        this.£á = true;
        return this;
    }
    
    public boolean Ó() {
        return this.£á;
    }
    
    public static WorldType HorizonCode_Horizon_È(final String type) {
        for (int var1 = 0; var1 < WorldType.HorizonCode_Horizon_È.length; ++var1) {
            if (WorldType.HorizonCode_Horizon_È[var1] != null && WorldType.HorizonCode_Horizon_È[var1].ÂµÈ.equalsIgnoreCase(type)) {
                return WorldType.HorizonCode_Horizon_È[var1];
            }
        }
        return null;
    }
    
    public int à() {
        return this.áˆºÑ¢Õ;
    }
    
    public boolean Ø() {
        return this.Å;
    }
    
    private WorldType áˆºÑ¢Õ() {
        this.Å = true;
        return this;
    }
}
