package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Iterables;
import java.util.Iterator;
import java.util.Collection;
import java.util.Map;
import com.google.common.base.Function;
import com.google.common.base.Joiner;

public abstract class BlockStateBase implements IBlockState
{
    private static final Joiner HorizonCode_Horizon_È;
    private static final Function Â;
    private static final String Ý = "CL_00002032";
    
    static {
        HorizonCode_Horizon_È = Joiner.on(',');
        Â = (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00002031";
            
            public String HorizonCode_Horizon_È(final Map.Entry p_177225_1_) {
                if (p_177225_1_ == null) {
                    return "<NULL>";
                }
                final IProperty var2 = p_177225_1_.getKey();
                return String.valueOf(var2.HorizonCode_Horizon_È()) + "=" + var2.HorizonCode_Horizon_È((Comparable)p_177225_1_.getValue());
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Map.Entry)p_apply_1_);
            }
        };
    }
    
    @Override
    public IBlockState Â(final IProperty property) {
        return this.HorizonCode_Horizon_È(property, (Comparable)HorizonCode_Horizon_È(property.Â(), this.HorizonCode_Horizon_È(property)));
    }
    
    protected static Object HorizonCode_Horizon_È(final Collection values, final Object currentValue) {
        final Iterator var2 = values.iterator();
        while (var2.hasNext()) {
            if (var2.next().equals(currentValue)) {
                if (var2.hasNext()) {
                    return var2.next();
                }
                return values.iterator().next();
            }
        }
        return var2.next();
    }
    
    @Override
    public String toString() {
        final StringBuilder var1 = new StringBuilder();
        var1.append(Block.HorizonCode_Horizon_È.Â(this.Ý()));
        if (!this.Â().isEmpty()) {
            var1.append("[");
            BlockStateBase.HorizonCode_Horizon_È.appendTo(var1, Iterables.transform((Iterable)this.Â().entrySet(), BlockStateBase.Â));
            var1.append("]");
        }
        return var1.toString();
    }
}
