package HORIZON-6-0-SKIDPROTECTION;

import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StatBase
{
    public final String à;
    private final IChatComponent HorizonCode_Horizon_È;
    public boolean Ø;
    private final IStatType Â;
    private final IScoreObjectiveCriteria Ý;
    private Class Ø­áŒŠá;
    private static NumberFormat Âµá€;
    public static IStatType áŒŠÆ;
    private static DecimalFormat Ó;
    public static IStatType áˆºÑ¢Õ;
    public static IStatType ÂµÈ;
    public static IStatType á;
    private static final String ˆÏ­ = "CL_00001472";
    
    static {
        StatBase.Âµá€ = NumberFormat.getIntegerInstance(Locale.US);
        StatBase.áŒŠÆ = new IStatType() {
            private static final String HorizonCode_Horizon_È = "CL_00001473";
            
            @Override
            public String HorizonCode_Horizon_È(final int p_75843_1_) {
                return StatBase.Âµá€.format(p_75843_1_);
            }
        };
        StatBase.Ó = new DecimalFormat("########0.00");
        StatBase.áˆºÑ¢Õ = new IStatType() {
            private static final String HorizonCode_Horizon_È = "CL_00001474";
            
            @Override
            public String HorizonCode_Horizon_È(final int p_75843_1_) {
                final double var2 = p_75843_1_ / 20.0;
                final double var3 = var2 / 60.0;
                final double var4 = var3 / 60.0;
                final double var5 = var4 / 24.0;
                final double var6 = var5 / 365.0;
                return (var6 > 0.5) ? (String.valueOf(StatBase.Ó.format(var6)) + " y") : ((var5 > 0.5) ? (String.valueOf(StatBase.Ó.format(var5)) + " d") : ((var4 > 0.5) ? (String.valueOf(StatBase.Ó.format(var4)) + " h") : ((var3 > 0.5) ? (String.valueOf(StatBase.Ó.format(var3)) + " m") : (String.valueOf(var2) + " s"))));
            }
        };
        StatBase.ÂµÈ = new IStatType() {
            private static final String HorizonCode_Horizon_È = "CL_00001475";
            
            @Override
            public String HorizonCode_Horizon_È(final int p_75843_1_) {
                final double var2 = p_75843_1_ / 100.0;
                final double var3 = var2 / 1000.0;
                return (var3 > 0.5) ? (String.valueOf(StatBase.Ó.format(var3)) + " km") : ((var2 > 0.5) ? (String.valueOf(StatBase.Ó.format(var2)) + " m") : (String.valueOf(p_75843_1_) + " cm"));
            }
        };
        StatBase.á = new IStatType() {
            private static final String HorizonCode_Horizon_È = "CL_00001476";
            
            @Override
            public String HorizonCode_Horizon_È(final int p_75843_1_) {
                return StatBase.Ó.format(p_75843_1_ * 0.1);
            }
        };
    }
    
    public StatBase(final String p_i45307_1_, final IChatComponent p_i45307_2_, final IStatType p_i45307_3_) {
        this.à = p_i45307_1_;
        this.HorizonCode_Horizon_È = p_i45307_2_;
        this.Â = p_i45307_3_;
        this.Ý = new ObjectiveStat(this);
        IScoreObjectiveCriteria.HorizonCode_Horizon_È.put(this.Ý.HorizonCode_Horizon_È(), this.Ý);
    }
    
    public StatBase(final String p_i45308_1_, final IChatComponent p_i45308_2_) {
        this(p_i45308_1_, p_i45308_2_, StatBase.áŒŠÆ);
    }
    
    public StatBase áŒŠÆ() {
        this.Ø = true;
        return this;
    }
    
    public StatBase Ø() {
        if (StatList.HorizonCode_Horizon_È.containsKey(this.à)) {
            throw new RuntimeException("Duplicate stat id: \"" + StatList.HorizonCode_Horizon_È.get(this.à).HorizonCode_Horizon_È + "\" and \"" + this.HorizonCode_Horizon_È + "\" at id " + this.à);
        }
        StatList.Â.add(this);
        StatList.HorizonCode_Horizon_È.put(this.à, this);
        return this;
    }
    
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    public String HorizonCode_Horizon_È(final int p_75968_1_) {
        return this.Â.HorizonCode_Horizon_È(p_75968_1_);
    }
    
    public IChatComponent Âµá€() {
        final IChatComponent var1 = this.HorizonCode_Horizon_È.Âµá€();
        var1.à().HorizonCode_Horizon_È(EnumChatFormatting.Ø);
        var1.à().HorizonCode_Horizon_È(new HoverEvent(HoverEvent.HorizonCode_Horizon_È.Â, new ChatComponentText(this.à)));
        return var1;
    }
    
    public IChatComponent áˆºÑ¢Õ() {
        final IChatComponent var1 = this.Âµá€();
        final IChatComponent var2 = new ChatComponentText("[").HorizonCode_Horizon_È(var1).Â("]");
        var2.HorizonCode_Horizon_È(var1.à());
        return var2;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            final StatBase var2 = (StatBase)p_equals_1_;
            return this.à.equals(var2.à);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.à.hashCode();
    }
    
    @Override
    public String toString() {
        return "Stat{id=" + this.à + ", nameId=" + this.HorizonCode_Horizon_È + ", awardLocallyOnly=" + this.Ø + ", formatter=" + this.Â + ", objectiveCriteria=" + this.Ý + '}';
    }
    
    public IScoreObjectiveCriteria ÂµÈ() {
        return this.Ý;
    }
    
    public Class á() {
        return this.Ø­áŒŠá;
    }
    
    public StatBase Â(final Class p_150953_1_) {
        this.Ø­áŒŠá = p_150953_1_;
        return this;
    }
}
