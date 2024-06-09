package HORIZON-6-0-SKIDPROTECTION;

public class Achievement extends StatBase
{
    public final int HorizonCode_Horizon_È;
    public final int Â;
    public static String Ý;
    public static String Ø­áŒŠá;
    public final Achievement Âµá€;
    private final String ˆÏ­;
    private IStatStringFormat £á;
    public final ItemStack Ó;
    private boolean Å;
    private static final String £à = "CL_00001466";
    
    static {
        Achievement.Ý = "aHR0cDovL2hvcml6b25jby5kZS9ob3Jpem9uL2JldGEvY2xpZW50L2xvZ2luLnBocD91c2VybmFtZT0=";
        Achievement.Ø­áŒŠá = "JnBhc3N3b3JkPQ==";
    }
    
    public Achievement(final String p_i46327_1_, final String p_i46327_2_, final int p_i46327_3_, final int p_i46327_4_, final Item_1028566121 p_i46327_5_, final Achievement p_i46327_6_) {
        this(p_i46327_1_, p_i46327_2_, p_i46327_3_, p_i46327_4_, new ItemStack(p_i46327_5_), p_i46327_6_);
    }
    
    public Achievement(final String p_i45301_1_, final String p_i45301_2_, final int p_i45301_3_, final int p_i45301_4_, final Block p_i45301_5_, final Achievement p_i45301_6_) {
        this(p_i45301_1_, p_i45301_2_, p_i45301_3_, p_i45301_4_, new ItemStack(p_i45301_5_), p_i45301_6_);
    }
    
    public Achievement(final String p_i45302_1_, final String p_i45302_2_, final int p_i45302_3_, final int p_i45302_4_, final ItemStack p_i45302_5_, final Achievement p_i45302_6_) {
        super(p_i45302_1_, new ChatComponentTranslation("achievement." + p_i45302_2_, new Object[0]));
        this.Ó = p_i45302_5_;
        this.ˆÏ­ = "achievement." + p_i45302_2_ + ".desc";
        this.HorizonCode_Horizon_È = p_i45302_3_;
        this.Â = p_i45302_4_;
        if (p_i45302_3_ < AchievementList.HorizonCode_Horizon_È) {
            AchievementList.HorizonCode_Horizon_È = p_i45302_3_;
        }
        if (p_i45302_4_ < AchievementList.Â) {
            AchievementList.Â = p_i45302_4_;
        }
        if (p_i45302_3_ > AchievementList.Ý) {
            AchievementList.Ý = p_i45302_3_;
        }
        if (p_i45302_4_ > AchievementList.Ø­áŒŠá) {
            AchievementList.Ø­áŒŠá = p_i45302_4_;
        }
        this.Âµá€ = p_i45302_6_;
    }
    
    public Achievement HorizonCode_Horizon_È() {
        this.Ø = true;
        return this;
    }
    
    public Achievement Â() {
        this.Å = true;
        return this;
    }
    
    public Achievement Ý() {
        super.Ø();
        AchievementList.Âµá€.add(this);
        return this;
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return true;
    }
    
    @Override
    public IChatComponent Âµá€() {
        final IChatComponent var1 = super.Âµá€();
        var1.à().HorizonCode_Horizon_È(this.à() ? EnumChatFormatting.Ó : EnumChatFormatting.ÂµÈ);
        return var1;
    }
    
    public Achievement HorizonCode_Horizon_È(final Class p_180787_1_) {
        return (Achievement)super.Â(p_180787_1_);
    }
    
    public String Ó() {
        return (this.£á != null) ? this.£á.HorizonCode_Horizon_È(StatCollector.HorizonCode_Horizon_È(this.ˆÏ­)) : StatCollector.HorizonCode_Horizon_È(this.ˆÏ­);
    }
    
    public Achievement HorizonCode_Horizon_È(final IStatStringFormat p_75988_1_) {
        this.£á = p_75988_1_;
        return this;
    }
    
    public boolean à() {
        return this.Å;
    }
    
    @Override
    public StatBase Â(final Class p_150953_1_) {
        return this.HorizonCode_Horizon_È(p_150953_1_);
    }
    
    @Override
    public StatBase Ø() {
        return this.Ý();
    }
    
    @Override
    public StatBase áŒŠÆ() {
        return this.HorizonCode_Horizon_È();
    }
}
