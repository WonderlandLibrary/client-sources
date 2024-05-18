package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Iterables;
import com.google.common.base.Splitter;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;

public class JsonToNBT
{
    private static final Logger HorizonCode_Horizon_È;
    private static final Pattern Â;
    private static final String Ý = "CL_00001232";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = Pattern.compile("\\[[-+\\d|,\\s]+\\]");
    }
    
    public static NBTTagCompound HorizonCode_Horizon_È(String p_180713_0_) throws NBTException {
        p_180713_0_ = p_180713_0_.trim();
        if (!p_180713_0_.startsWith("{")) {
            throw new NBTException("Invalid tag encountered, expected '{' as first char.");
        }
        if (Â(p_180713_0_) != 1) {
            throw new NBTException("Encountered multiple top tags, only one expected");
        }
        return (NBTTagCompound)HorizonCode_Horizon_È("tag", p_180713_0_).HorizonCode_Horizon_È();
    }
    
    static int Â(final String p_150310_0_) throws NBTException {
        int var1 = 0;
        boolean var2 = false;
        final Stack var3 = new Stack();
        for (int var4 = 0; var4 < p_150310_0_.length(); ++var4) {
            final char var5 = p_150310_0_.charAt(var4);
            if (var5 == '\"') {
                if (Â(p_150310_0_, var4)) {
                    if (!var2) {
                        throw new NBTException("Illegal use of \\\": " + p_150310_0_);
                    }
                }
                else {
                    var2 = !var2;
                }
            }
            else if (!var2) {
                if (var5 != '{' && var5 != '[') {
                    if (var5 == '}' && (var3.isEmpty() || var3.pop() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + p_150310_0_);
                    }
                    if (var5 == ']' && (var3.isEmpty() || var3.pop() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + p_150310_0_);
                    }
                }
                else {
                    if (var3.isEmpty()) {
                        ++var1;
                    }
                    var3.push(var5);
                }
            }
        }
        if (var2) {
            throw new NBTException("Unbalanced quotation: " + p_150310_0_);
        }
        if (!var3.isEmpty()) {
            throw new NBTException("Unbalanced brackets: " + p_150310_0_);
        }
        if (var1 == 0 && !p_150310_0_.isEmpty()) {
            var1 = 1;
        }
        return var1;
    }
    
    static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String... p_179272_0_) throws NBTException {
        return HorizonCode_Horizon_È(p_179272_0_[0], p_179272_0_[1]);
    }
    
    static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_150316_0_, String p_150316_1_) throws NBTException {
        p_150316_1_ = p_150316_1_.trim();
        if (p_150316_1_.startsWith("{")) {
            p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
            final Â var5 = new Â(p_150316_0_);
            while (p_150316_1_.length() > 0) {
                final String var6 = Â(p_150316_1_, true);
                if (var6.length() > 0) {
                    final boolean var7 = false;
                    var5.Â.add(HorizonCode_Horizon_È(var6, var7));
                }
                if (p_150316_1_.length() < var6.length() + 1) {
                    break;
                }
                final char var8 = p_150316_1_.charAt(var6.length());
                if (var8 != ',' && var8 != '{' && var8 != '}' && var8 != '[' && var8 != ']') {
                    throw new NBTException("Unexpected token '" + var8 + "' at: " + p_150316_1_.substring(var6.length()));
                }
                p_150316_1_ = p_150316_1_.substring(var6.length() + 1);
            }
            return var5;
        }
        if (p_150316_1_.startsWith("[") && !JsonToNBT.Â.matcher(p_150316_1_).matches()) {
            p_150316_1_ = p_150316_1_.substring(1, p_150316_1_.length() - 1);
            final Ý var9 = new Ý(p_150316_0_);
            while (p_150316_1_.length() > 0) {
                final String var6 = Â(p_150316_1_, false);
                if (var6.length() > 0) {
                    final boolean var7 = true;
                    var9.Â.add(HorizonCode_Horizon_È(var6, var7));
                }
                if (p_150316_1_.length() < var6.length() + 1) {
                    break;
                }
                final char var8 = p_150316_1_.charAt(var6.length());
                if (var8 != ',' && var8 != '{' && var8 != '}' && var8 != '[' && var8 != ']') {
                    throw new NBTException("Unexpected token '" + var8 + "' at: " + p_150316_1_.substring(var6.length()));
                }
                p_150316_1_ = p_150316_1_.substring(var6.length() + 1);
            }
            return var9;
        }
        return new Ø­áŒŠá(p_150316_0_, p_150316_1_);
    }
    
    private static HorizonCode_Horizon_È HorizonCode_Horizon_È(final String p_179270_0_, final boolean p_179270_1_) throws NBTException {
        final String var2 = Ý(p_179270_0_, p_179270_1_);
        final String var3 = Ø­áŒŠá(p_179270_0_, p_179270_1_);
        return HorizonCode_Horizon_È(new String[] { var2, var3 });
    }
    
    private static String Â(final String p_150314_0_, final boolean p_150314_1_) throws NBTException {
        int var2 = HorizonCode_Horizon_È(p_150314_0_, ':');
        final int var3 = HorizonCode_Horizon_È(p_150314_0_, ',');
        if (p_150314_1_) {
            if (var2 == -1) {
                throw new NBTException("Unable to locate name/value separator for string: " + p_150314_0_);
            }
            if (var3 != -1 && var3 < var2) {
                throw new NBTException("Name error at: " + p_150314_0_);
            }
        }
        else if (var2 == -1 || var2 > var3) {
            var2 = -1;
        }
        return HorizonCode_Horizon_È(p_150314_0_, var2);
    }
    
    private static String HorizonCode_Horizon_È(final String p_179269_0_, final int p_179269_1_) throws NBTException {
        final Stack var2 = new Stack();
        int var3 = p_179269_1_ + 1;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        int var7 = 0;
        while (var3 < p_179269_0_.length()) {
            final char var8 = p_179269_0_.charAt(var3);
            if (var8 == '\"') {
                if (Â(p_179269_0_, var3)) {
                    if (!var4) {
                        throw new NBTException("Illegal use of \\\": " + p_179269_0_);
                    }
                }
                else {
                    var4 = !var4;
                    if (var4 && !var6) {
                        var5 = true;
                    }
                    if (!var4) {
                        var7 = var3;
                    }
                }
            }
            else if (!var4) {
                if (var8 != '{' && var8 != '[') {
                    if (var8 == '}' && (var2.isEmpty() || var2.pop() != '{')) {
                        throw new NBTException("Unbalanced curly brackets {}: " + p_179269_0_);
                    }
                    if (var8 == ']' && (var2.isEmpty() || var2.pop() != '[')) {
                        throw new NBTException("Unbalanced square brackets []: " + p_179269_0_);
                    }
                    if (var8 == ',' && var2.isEmpty()) {
                        return p_179269_0_.substring(0, var3);
                    }
                }
                else {
                    var2.push(var8);
                }
            }
            if (!Character.isWhitespace(var8)) {
                if (!var4 && var5 && var7 != var3) {
                    return p_179269_0_.substring(0, var7 + 1);
                }
                var6 = true;
            }
            ++var3;
        }
        return p_179269_0_.substring(0, var3);
    }
    
    private static String Ý(String p_150313_0_, final boolean p_150313_1_) throws NBTException {
        if (p_150313_1_) {
            p_150313_0_ = p_150313_0_.trim();
            if (p_150313_0_.startsWith("{") || p_150313_0_.startsWith("[")) {
                return "";
            }
        }
        final int var2 = HorizonCode_Horizon_È(p_150313_0_, ':');
        if (var2 != -1) {
            return p_150313_0_.substring(0, var2).trim();
        }
        if (p_150313_1_) {
            return "";
        }
        throw new NBTException("Unable to locate name/value separator for string: " + p_150313_0_);
    }
    
    private static String Ø­áŒŠá(String p_150311_0_, final boolean p_150311_1_) throws NBTException {
        if (p_150311_1_) {
            p_150311_0_ = p_150311_0_.trim();
            if (p_150311_0_.startsWith("{") || p_150311_0_.startsWith("[")) {
                return p_150311_0_;
            }
        }
        final int var2 = HorizonCode_Horizon_È(p_150311_0_, ':');
        if (var2 != -1) {
            return p_150311_0_.substring(var2 + 1).trim();
        }
        if (p_150311_1_) {
            return p_150311_0_;
        }
        throw new NBTException("Unable to locate name/value separator for string: " + p_150311_0_);
    }
    
    private static int HorizonCode_Horizon_È(final String p_150312_0_, final char p_150312_1_) {
        int var2 = 0;
        boolean var3 = true;
        while (var2 < p_150312_0_.length()) {
            final char var4 = p_150312_0_.charAt(var2);
            if (var4 == '\"') {
                if (!Â(p_150312_0_, var2)) {
                    var3 = !var3;
                }
            }
            else if (var3) {
                if (var4 == p_150312_1_) {
                    return var2;
                }
                if (var4 == '{' || var4 == '[') {
                    return -1;
                }
            }
            ++var2;
        }
        return -1;
    }
    
    private static boolean Â(final String p_179271_0_, final int p_179271_1_) {
        return p_179271_1_ > 0 && p_179271_0_.charAt(p_179271_1_ - 1) == '\\' && !Â(p_179271_0_, p_179271_1_ - 1);
    }
    
    abstract static class HorizonCode_Horizon_È
    {
        protected String HorizonCode_Horizon_È;
        private static final String Â = "CL_00001233";
        
        public abstract NBTBase HorizonCode_Horizon_È();
    }
    
    static class Â extends HorizonCode_Horizon_È
    {
        protected List Â;
        private static final String Ý = "CL_00001234";
        
        public Â(final String p_i45137_1_) {
            this.Â = Lists.newArrayList();
            this.HorizonCode_Horizon_È = p_i45137_1_;
        }
        
        @Override
        public NBTBase HorizonCode_Horizon_È() {
            final NBTTagCompound var1 = new NBTTagCompound();
            for (final HorizonCode_Horizon_È var3 : this.Â) {
                var1.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È, var3.HorizonCode_Horizon_È());
            }
            return var1;
        }
    }
    
    static class Ý extends HorizonCode_Horizon_È
    {
        protected List Â;
        private static final String Ý = "CL_00001235";
        
        public Ý(final String p_i45138_1_) {
            this.Â = Lists.newArrayList();
            this.HorizonCode_Horizon_È = p_i45138_1_;
        }
        
        @Override
        public NBTBase HorizonCode_Horizon_È() {
            final NBTTagList var1 = new NBTTagList();
            for (final HorizonCode_Horizon_È var3 : this.Â) {
                var1.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È());
            }
            return var1;
        }
    }
    
    static class Ø­áŒŠá extends HorizonCode_Horizon_È
    {
        private static final Pattern Ý;
        private static final Pattern Ø­áŒŠá;
        private static final Pattern Âµá€;
        private static final Pattern Ó;
        private static final Pattern à;
        private static final Pattern Ø;
        private static final Pattern áŒŠÆ;
        private static final Splitter áˆºÑ¢Õ;
        protected String Â;
        private static final String ÂµÈ = "CL_00001236";
        
        static {
            Ý = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
            Ø­áŒŠá = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
            Âµá€ = Pattern.compile("[-+]?[0-9]+[b|B]");
            Ó = Pattern.compile("[-+]?[0-9]+[l|L]");
            à = Pattern.compile("[-+]?[0-9]+[s|S]");
            Ø = Pattern.compile("[-+]?[0-9]+");
            áŒŠÆ = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
            áˆºÑ¢Õ = Splitter.on(',').omitEmptyStrings();
        }
        
        public Ø­áŒŠá(final String p_i45139_1_, final String p_i45139_2_) {
            this.HorizonCode_Horizon_È = p_i45139_1_;
            this.Â = p_i45139_2_;
        }
        
        @Override
        public NBTBase HorizonCode_Horizon_È() {
            try {
                if (JsonToNBT.Ø­áŒŠá.Ý.matcher(this.Â).matches()) {
                    return new NBTTagDouble(Double.parseDouble(this.Â.substring(0, this.Â.length() - 1)));
                }
                if (JsonToNBT.Ø­áŒŠá.Ø­áŒŠá.matcher(this.Â).matches()) {
                    return new NBTTagFloat(Float.parseFloat(this.Â.substring(0, this.Â.length() - 1)));
                }
                if (JsonToNBT.Ø­áŒŠá.Âµá€.matcher(this.Â).matches()) {
                    return new NBTTagByte(Byte.parseByte(this.Â.substring(0, this.Â.length() - 1)));
                }
                if (JsonToNBT.Ø­áŒŠá.Ó.matcher(this.Â).matches()) {
                    return new NBTTagLong(Long.parseLong(this.Â.substring(0, this.Â.length() - 1)));
                }
                if (JsonToNBT.Ø­áŒŠá.à.matcher(this.Â).matches()) {
                    return new NBTTagShort(Short.parseShort(this.Â.substring(0, this.Â.length() - 1)));
                }
                if (JsonToNBT.Ø­áŒŠá.Ø.matcher(this.Â).matches()) {
                    return new NBTTagInt(Integer.parseInt(this.Â));
                }
                if (JsonToNBT.Ø­áŒŠá.áŒŠÆ.matcher(this.Â).matches()) {
                    return new NBTTagDouble(Double.parseDouble(this.Â));
                }
                if (this.Â.equalsIgnoreCase("true") || this.Â.equalsIgnoreCase("false")) {
                    return new NBTTagByte((byte)(Boolean.parseBoolean(this.Â) ? 1 : 0));
                }
            }
            catch (NumberFormatException var13) {
                this.Â = this.Â.replaceAll("\\\\\"", "\"");
                return new NBTTagString(this.Â);
            }
            if (this.Â.startsWith("[") && this.Â.endsWith("]")) {
                final String var7 = this.Â.substring(1, this.Â.length() - 1);
                final String[] var8 = (String[])Iterables.toArray(JsonToNBT.Ø­áŒŠá.áˆºÑ¢Õ.split((CharSequence)var7), (Class)String.class);
                try {
                    final int[] var9 = new int[var8.length];
                    for (int var10 = 0; var10 < var8.length; ++var10) {
                        var9[var10] = Integer.parseInt(var8[var10].trim());
                    }
                    return new NBTTagIntArray(var9);
                }
                catch (NumberFormatException var14) {
                    return new NBTTagString(this.Â);
                }
            }
            if (this.Â.startsWith("\"") && this.Â.endsWith("\"")) {
                this.Â = this.Â.substring(1, this.Â.length() - 1);
            }
            this.Â = this.Â.replaceAll("\\\\\"", "\"");
            final StringBuilder var11 = new StringBuilder();
            for (int var12 = 0; var12 < this.Â.length(); ++var12) {
                if (var12 < this.Â.length() - 1 && this.Â.charAt(var12) == '\\' && this.Â.charAt(var12 + 1) == '\\') {
                    var11.append('\\');
                    ++var12;
                }
                else {
                    var11.append(this.Â.charAt(var12));
                }
            }
            return new NBTTagString(var11.toString());
        }
    }
}
