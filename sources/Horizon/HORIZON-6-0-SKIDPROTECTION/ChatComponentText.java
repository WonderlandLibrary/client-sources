package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class ChatComponentText extends ChatComponentStyle
{
    private final String Â;
    private static final String Ý = "CL_00001269";
    
    public ChatComponentText(final String msg) {
        this.Â = msg;
    }
    
    public String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    @Override
    public String Ý() {
        return this.Â;
    }
    
    public ChatComponentText Â() {
        final ChatComponentText var1 = new ChatComponentText(this.Â);
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
        if (!(p_equals_1_ instanceof ChatComponentText)) {
            return false;
        }
        final ChatComponentText var2 = (ChatComponentText)p_equals_1_;
        return this.Â.equals(var2.HorizonCode_Horizon_È()) && super.equals(p_equals_1_);
    }
    
    @Override
    public String toString() {
        return "TextComponent{text='" + this.Â + '\'' + ", siblings=" + this.HorizonCode_Horizon_È + ", style=" + this.à() + '}';
    }
}
