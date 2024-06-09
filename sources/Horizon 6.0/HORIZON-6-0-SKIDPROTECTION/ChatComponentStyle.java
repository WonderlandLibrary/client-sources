package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public abstract class ChatComponentStyle implements IChatComponent
{
    protected List HorizonCode_Horizon_È;
    private ChatStyle Â;
    private static final String Ý = "CL_00001257";
    
    public ChatComponentStyle() {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
    }
    
    @Override
    public IChatComponent HorizonCode_Horizon_È(final IChatComponent component) {
        component.à().HorizonCode_Horizon_È(this.à());
        this.HorizonCode_Horizon_È.add(component);
        return this;
    }
    
    @Override
    public List Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public IChatComponent Â(final String text) {
        return this.HorizonCode_Horizon_È(new ChatComponentText(text));
    }
    
    @Override
    public IChatComponent HorizonCode_Horizon_È(final ChatStyle style) {
        this.Â = style;
        for (final IChatComponent var3 : this.HorizonCode_Horizon_È) {
            var3.à().HorizonCode_Horizon_È(this.à());
        }
        return this;
    }
    
    @Override
    public ChatStyle à() {
        if (this.Â == null) {
            this.Â = new ChatStyle();
            for (final IChatComponent var2 : this.HorizonCode_Horizon_È) {
                var2.à().HorizonCode_Horizon_È(this.Â);
            }
        }
        return this.Â;
    }
    
    @Override
    public Iterator iterator() {
        return Iterators.concat((Iterator)Iterators.forArray((Object[])new ChatComponentStyle[] { this }), HorizonCode_Horizon_È(this.HorizonCode_Horizon_È));
    }
    
    @Override
    public final String Ø() {
        final StringBuilder var1 = new StringBuilder();
        for (final IChatComponent var3 : this) {
            var1.append(var3.Ý());
        }
        return var1.toString();
    }
    
    @Override
    public final String áŒŠÆ() {
        final StringBuilder var1 = new StringBuilder();
        for (final IChatComponent var3 : this) {
            var1.append(var3.à().ÂµÈ());
            var1.append(var3.Ý());
            var1.append(EnumChatFormatting.Æ);
        }
        return var1.toString();
    }
    
    public static Iterator HorizonCode_Horizon_È(final Iterable components) {
        Iterator var1 = Iterators.concat(Iterators.transform((Iterator)components.iterator(), (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00001258";
            
            public Iterator HorizonCode_Horizon_È(final IChatComponent p_apply_1_) {
                return p_apply_1_.iterator();
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((IChatComponent)p_apply_1_);
            }
        }));
        var1 = Iterators.transform(var1, (Function)new Function() {
            private static final String HorizonCode_Horizon_È = "CL_00001259";
            
            public IChatComponent HorizonCode_Horizon_È(final IChatComponent p_apply_1_) {
                final IChatComponent var2 = p_apply_1_.Âµá€();
                var2.HorizonCode_Horizon_È(var2.à().ˆÏ­());
                return var2;
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((IChatComponent)p_apply_1_);
            }
        });
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChatComponentStyle)) {
            return false;
        }
        final ChatComponentStyle var2 = (ChatComponentStyle)p_equals_1_;
        return this.HorizonCode_Horizon_È.equals(var2.HorizonCode_Horizon_È) && this.à().equals(var2.à());
    }
    
    @Override
    public int hashCode() {
        return 31 * this.Â.hashCode() + this.HorizonCode_Horizon_È.hashCode();
    }
    
    @Override
    public String toString() {
        return "BaseComponent{style=" + this.Â + ", siblings=" + this.HorizonCode_Horizon_È + '}';
    }
}
