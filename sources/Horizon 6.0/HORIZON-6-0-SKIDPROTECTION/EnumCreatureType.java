package HORIZON-6-0-SKIDPROTECTION;

public enum EnumCreatureType
{
    HorizonCode_Horizon_È("MONSTER", 0, "MONSTER", 0, (Class)IMob.class, 70, Material.HorizonCode_Horizon_È, false, false), 
    Â("CREATURE", 1, "CREATURE", 1, (Class)EntityAnimal.class, 10, Material.HorizonCode_Horizon_È, true, true), 
    Ý("AMBIENT", 2, "AMBIENT", 2, (Class)EntityAmbientCreature.class, 15, Material.HorizonCode_Horizon_È, true, false), 
    Ø­áŒŠá("WATER_CREATURE", 3, "WATER_CREATURE", 3, (Class)EntityWaterMob.class, 5, Material.Ø, true, false);
    
    private final Class Âµá€;
    private final int Ó;
    private final Material à;
    private final boolean Ø;
    private final boolean áŒŠÆ;
    private static final EnumCreatureType[] áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001551";
    
    static {
        á = new EnumCreatureType[] { EnumCreatureType.HorizonCode_Horizon_È, EnumCreatureType.Â, EnumCreatureType.Ý, EnumCreatureType.Ø­áŒŠá };
        áˆºÑ¢Õ = new EnumCreatureType[] { EnumCreatureType.HorizonCode_Horizon_È, EnumCreatureType.Â, EnumCreatureType.Ý, EnumCreatureType.Ø­áŒŠá };
    }
    
    private EnumCreatureType(final String s, final int n, final String p_i1596_1_, final int p_i1596_2_, final Class p_i1596_3_, final int p_i1596_4_, final Material p_i1596_5_, final boolean p_i1596_6_, final boolean p_i1596_7_) {
        this.Âµá€ = p_i1596_3_;
        this.Ó = p_i1596_4_;
        this.à = p_i1596_5_;
        this.Ø = p_i1596_6_;
        this.áŒŠÆ = p_i1596_7_;
    }
    
    public Class HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public int Â() {
        return this.Ó;
    }
    
    public boolean Ý() {
        return this.Ø;
    }
    
    public boolean Ø­áŒŠá() {
        return this.áŒŠÆ;
    }
}
