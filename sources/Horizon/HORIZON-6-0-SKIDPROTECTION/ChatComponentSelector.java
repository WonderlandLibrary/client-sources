package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class ChatComponentSelector extends ChatComponentStyle
{
    private final String Â;
    private static final String Ý = "CL_00002308";
    
    public ChatComponentSelector(final String p_i45996_1_) {
        this.Â = p_i45996_1_;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public String Ý() {
        return this.Â;
    }
    
    public ChatComponentSelector Â() {
        final ChatComponentSelector var1 = new ChatComponentSelector(this.Â);
        var1.HorizonCode_Horizon_È(this.à().á());
        for (final IChatComponent var3 : this.Ó()) {
            var1.HorizonCode_Horizon_È(var3.Âµá€());
        }
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChatComponentSelector)) {
            return false;
        }
        final ChatComponentSelector var2 = (ChatComponentSelector)p_equals_1_;
        return this.Â.equals(var2.Â) && super.equals(p_equals_1_);
    }
    
    @Override
    public String toString() {
        return "SelectorComponent{pattern='" + this.Â + '\'' + ", siblings=" + this.HorizonCode_Horizon_È + ", style=" + this.à() + '}';
    }
    
    @Override
    public IChatComponent Âµá€() {
        return this.Â();
    }
}
