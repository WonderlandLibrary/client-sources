package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Set;
import java.util.BitSet;

public class SetVisibility
{
    private static final int HorizonCode_Horizon_È;
    private final BitSet Â;
    private static final String Ý = "CL_00002448";
    
    static {
        HorizonCode_Horizon_È = EnumFacing.values().length;
    }
    
    public SetVisibility() {
        this.Â = new BitSet(SetVisibility.HorizonCode_Horizon_È * SetVisibility.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final Set p_178620_1_) {
        for (final EnumFacing var3 : p_178620_1_) {
            for (final EnumFacing var5 : p_178620_1_) {
                this.HorizonCode_Horizon_È(var3, var5, true);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final EnumFacing p_178619_1_, final EnumFacing p_178619_2_, final boolean p_178619_3_) {
        this.Â.set(p_178619_1_.ordinal() + p_178619_2_.ordinal() * SetVisibility.HorizonCode_Horizon_È, p_178619_3_);
        this.Â.set(p_178619_2_.ordinal() + p_178619_1_.ordinal() * SetVisibility.HorizonCode_Horizon_È, p_178619_3_);
    }
    
    public void HorizonCode_Horizon_È(final boolean p_178618_1_) {
        this.Â.set(0, this.Â.size(), p_178618_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final EnumFacing p_178621_1_, final EnumFacing p_178621_2_) {
        return this.Â.get(p_178621_1_.ordinal() + p_178621_2_.ordinal() * SetVisibility.HorizonCode_Horizon_È);
    }
    
    @Override
    public String toString() {
        final StringBuilder var1 = new StringBuilder();
        var1.append(' ');
        for (final EnumFacing var5 : EnumFacing.values()) {
            var1.append(' ').append(var5.toString().toUpperCase().charAt(0));
        }
        var1.append('\n');
        for (final EnumFacing var5 : EnumFacing.values()) {
            var1.append(var5.toString().toUpperCase().charAt(0));
            for (final EnumFacing var9 : EnumFacing.values()) {
                if (var5 == var9) {
                    var1.append("  ");
                }
                else {
                    final boolean var10 = this.HorizonCode_Horizon_È(var5, var9);
                    var1.append(' ').append(var10 ? 'Y' : 'n');
                }
            }
            var1.append('\n');
        }
        return var1.toString();
    }
}
