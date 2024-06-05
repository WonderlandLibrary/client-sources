package net.minecraft.nbt;

import java.util.regex.*;
import com.google.common.base.*;
import com.google.common.collect.*;

static class Primitive extends Any
{
    private static final Pattern field_179265_c;
    private static final Pattern field_179263_d;
    private static final Pattern field_179264_e;
    private static final Pattern field_179261_f;
    private static final Pattern field_179262_g;
    private static final Pattern field_179267_h;
    private static final Pattern field_179268_i;
    private static final Splitter field_179266_j;
    protected String field_150493_b;
    
    public Primitive(final String p_i45139_1_, final String p_i45139_2_) {
        this.field_150490_a = p_i45139_1_;
        this.field_150493_b = p_i45139_2_;
    }
    
    @Override
    public NBTBase func_150489_a() {
        try {
            if (Primitive.field_179265_c.matcher(this.field_150493_b).matches()) {
                return new NBTTagDouble(Double.parseDouble(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179263_d.matcher(this.field_150493_b).matches()) {
                return new NBTTagFloat(Float.parseFloat(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179264_e.matcher(this.field_150493_b).matches()) {
                return new NBTTagByte(Byte.parseByte(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179261_f.matcher(this.field_150493_b).matches()) {
                return new NBTTagLong(Long.parseLong(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179262_g.matcher(this.field_150493_b).matches()) {
                return new NBTTagShort(Short.parseShort(this.field_150493_b.substring(0, this.field_150493_b.length() - 1)));
            }
            if (Primitive.field_179267_h.matcher(this.field_150493_b).matches()) {
                return new NBTTagInt(Integer.parseInt(this.field_150493_b));
            }
            if (Primitive.field_179268_i.matcher(this.field_150493_b).matches()) {
                return new NBTTagDouble(Double.parseDouble(this.field_150493_b));
            }
            if (this.field_150493_b.equalsIgnoreCase("true") || this.field_150493_b.equalsIgnoreCase("false")) {
                return new NBTTagByte((byte)(Boolean.parseBoolean(this.field_150493_b) ? 1 : 0));
            }
        }
        catch (NumberFormatException var13) {
            this.field_150493_b = this.field_150493_b.replaceAll("\\\\\"", "\"");
            return new NBTTagString(this.field_150493_b);
        }
        if (this.field_150493_b.startsWith("[") && this.field_150493_b.endsWith("]")) {
            final String var7 = this.field_150493_b.substring(1, this.field_150493_b.length() - 1);
            final String[] var8 = (String[])Iterables.toArray(Primitive.field_179266_j.split((CharSequence)var7), (Class)String.class);
            try {
                final int[] var9 = new int[var8.length];
                for (int var10 = 0; var10 < var8.length; ++var10) {
                    var9[var10] = Integer.parseInt(var8[var10].trim());
                }
                return new NBTTagIntArray(var9);
            }
            catch (NumberFormatException var14) {
                return new NBTTagString(this.field_150493_b);
            }
        }
        if (this.field_150493_b.startsWith("\"") && this.field_150493_b.endsWith("\"")) {
            this.field_150493_b = this.field_150493_b.substring(1, this.field_150493_b.length() - 1);
        }
        this.field_150493_b = this.field_150493_b.replaceAll("\\\\\"", "\"");
        final StringBuilder var11 = new StringBuilder();
        for (int var12 = 0; var12 < this.field_150493_b.length(); ++var12) {
            if (var12 < this.field_150493_b.length() - 1 && this.field_150493_b.charAt(var12) == '\\' && this.field_150493_b.charAt(var12 + 1) == '\\') {
                var11.append('\\');
                ++var12;
            }
            else {
                var11.append(this.field_150493_b.charAt(var12));
            }
        }
        return new NBTTagString(var11.toString());
    }
    
    static {
        field_179265_c = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
        field_179263_d = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
        field_179264_e = Pattern.compile("[-+]?[0-9]+[b|B]");
        field_179261_f = Pattern.compile("[-+]?[0-9]+[l|L]");
        field_179262_g = Pattern.compile("[-+]?[0-9]+[s|S]");
        field_179267_h = Pattern.compile("[-+]?[0-9]+");
        field_179268_i = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
        field_179266_j = Splitter.on(',').omitEmptyStrings();
    }
}
