package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.IllegalFormatException;
import com.google.common.collect.Lists;
import java.util.regex.Pattern;
import java.util.List;

public class ChatComponentTranslation extends ChatComponentStyle
{
    private final String Ø­áŒŠá;
    private final Object[] Âµá€;
    private final Object Ó;
    private long à;
    List Â;
    public static final Pattern Ý;
    private static final String Ø = "CL_00001270";
    
    static {
        Ý = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    }
    
    public ChatComponentTranslation(final String translationKey, final Object... args) {
        this.Ó = new Object();
        this.à = -1L;
        this.Â = Lists.newArrayList();
        this.Ø­áŒŠá = translationKey;
        this.Âµá€ = args;
        for (final Object var6 : args) {
            if (var6 instanceof IChatComponent) {
                ((IChatComponent)var6).à().HorizonCode_Horizon_È(this.à());
            }
        }
    }
    
    synchronized void HorizonCode_Horizon_È() {
        final Object var1 = this.Ó;
        synchronized (this.Ó) {
            final long var2 = StatCollector.HorizonCode_Horizon_È();
            if (var2 == this.à) {
                // monitorexit(this.\u00d3)
                return;
            }
            this.à = var2;
            this.Â.clear();
        }
        // monitorexit(this.\u00d3)
        try {
            this.HorizonCode_Horizon_È(StatCollector.HorizonCode_Horizon_È(this.Ø­áŒŠá));
        }
        catch (ChatComponentTranslationFormatException var3) {
            this.Â.clear();
            try {
                this.HorizonCode_Horizon_È(StatCollector.Â(this.Ø­áŒŠá));
            }
            catch (ChatComponentTranslationFormatException var4) {
                throw var3;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final String format) {
        final boolean var2 = false;
        final Matcher var3 = ChatComponentTranslation.Ý.matcher(format);
        int var4 = 0;
        int var5 = 0;
        try {
            while (var3.find(var5)) {
                final int var6 = var3.start();
                final int var7 = var3.end();
                if (var6 > var5) {
                    final ChatComponentText var8 = new ChatComponentText(String.format(format.substring(var5, var6), new Object[0]));
                    var8.à().HorizonCode_Horizon_È(this.à());
                    this.Â.add(var8);
                }
                final String var9 = var3.group(2);
                final String var10 = format.substring(var6, var7);
                if ("%".equals(var9) && "%%".equals(var10)) {
                    final ChatComponentText var11 = new ChatComponentText("%");
                    var11.à().HorizonCode_Horizon_È(this.à());
                    this.Â.add(var11);
                }
                else {
                    if (!"s".equals(var9)) {
                        throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + var10 + "'");
                    }
                    final String var12 = var3.group(1);
                    final int var13 = (var12 != null) ? (Integer.parseInt(var12) - 1) : var4++;
                    if (var13 < this.Âµá€.length) {
                        this.Â.add(this.HorizonCode_Horizon_È(var13));
                    }
                }
                var5 = var7;
            }
            if (var5 < format.length()) {
                final ChatComponentText var14 = new ChatComponentText(String.format(format.substring(var5), new Object[0]));
                var14.à().HorizonCode_Horizon_È(this.à());
                this.Â.add(var14);
            }
        }
        catch (IllegalFormatException var15) {
            throw new ChatComponentTranslationFormatException(this, var15);
        }
    }
    
    private IChatComponent HorizonCode_Horizon_È(final int index) {
        if (index >= this.Âµá€.length) {
            throw new ChatComponentTranslationFormatException(this, index);
        }
        final Object var2 = this.Âµá€[index];
        Object var3;
        if (var2 instanceof IChatComponent) {
            var3 = var2;
        }
        else {
            var3 = new ChatComponentText((var2 == null) ? "null" : var2.toString());
            ((IChatComponent)var3).à().HorizonCode_Horizon_È(this.à());
        }
        return (IChatComponent)var3;
    }
    
    @Override
    public IChatComponent HorizonCode_Horizon_È(final ChatStyle style) {
        super.HorizonCode_Horizon_È(style);
        for (final Object var5 : this.Âµá€) {
            if (var5 instanceof IChatComponent) {
                ((IChatComponent)var5).à().HorizonCode_Horizon_È(this.à());
            }
        }
        if (this.à > -1L) {
            for (final IChatComponent var7 : this.Â) {
                var7.à().HorizonCode_Horizon_È(style);
            }
        }
        return this;
    }
    
    @Override
    public Iterator iterator() {
        this.HorizonCode_Horizon_È();
        return Iterators.concat(ChatComponentStyle.HorizonCode_Horizon_È(this.Â), ChatComponentStyle.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È));
    }
    
    @Override
    public String Ý() {
        this.HorizonCode_Horizon_È();
        final StringBuilder var1 = new StringBuilder();
        for (final IChatComponent var3 : this.Â) {
            var1.append(var3.Ý());
        }
        return var1.toString();
    }
    
    public ChatComponentTranslation Â() {
        final Object[] var1 = new Object[this.Âµá€.length];
        for (int var2 = 0; var2 < this.Âµá€.length; ++var2) {
            if (this.Âµá€[var2] instanceof IChatComponent) {
                var1[var2] = ((IChatComponent)this.Âµá€[var2]).Âµá€();
            }
            else {
                var1[var2] = this.Âµá€[var2];
            }
        }
        final ChatComponentTranslation var3 = new ChatComponentTranslation(this.Ø­áŒŠá, var1);
        var3.HorizonCode_Horizon_È(this.à().á());
        for (final IChatComponent var5 : this.Ó()) {
            var3.HorizonCode_Horizon_È(var5.Âµá€());
        }
        return var3;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChatComponentTranslation)) {
            return false;
        }
        final ChatComponentTranslation var2 = (ChatComponentTranslation)p_equals_1_;
        return Arrays.equals(this.Âµá€, var2.Âµá€) && this.Ø­áŒŠá.equals(var2.Ø­áŒŠá) && super.equals(p_equals_1_);
    }
    
    @Override
    public int hashCode() {
        int var1 = super.hashCode();
        var1 = 31 * var1 + this.Ø­áŒŠá.hashCode();
        var1 = 31 * var1 + Arrays.hashCode(this.Âµá€);
        return var1;
    }
    
    @Override
    public String toString() {
        return "TranslatableComponent{key='" + this.Ø­áŒŠá + '\'' + ", args=" + Arrays.toString(this.Âµá€) + ", siblings=" + this.HorizonCode_Horizon_È + ", style=" + this.à() + '}';
    }
    
    public String Ø­áŒŠá() {
        return this.Ø­áŒŠá;
    }
    
    public Object[] áˆºÑ¢Õ() {
        return this.Âµá€;
    }
}
