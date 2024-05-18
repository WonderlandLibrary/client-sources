package net.minecraft.client.util;

import java.util.*;
import com.google.common.collect.*;
import java.io.*;
import org.apache.commons.lang3.*;

public class JsonException extends IOException
{
    private final List<Entry> field_151383_a;
    private final String field_151382_b;
    private static final String[] I;
    
    public JsonException(final String field_151382_b) {
        (this.field_151383_a = (List<Entry>)Lists.newArrayList()).add(new Entry(null));
        this.field_151382_b = field_151382_b;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0011\u0000\u001f)?1\nI", "XniHS");
        JsonException.I[" ".length()] = I("sv", "IVgXM");
        JsonException.I["  ".length()] = I(")0<\fG\u00016$I\u0001\u0000,>\r", "oYPig");
    }
    
    public static JsonException func_151379_a(final Exception ex) {
        if (ex instanceof JsonException) {
            return (JsonException)ex;
        }
        String message = ex.getMessage();
        if (ex instanceof FileNotFoundException) {
            message = JsonException.I["  ".length()];
        }
        return new JsonException(message, ex);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public void func_151381_b(final String s) {
        Entry.access$2(this.field_151383_a.get("".length()), s);
        this.field_151383_a.add("".length(), new Entry(null));
    }
    
    public JsonException(final String field_151382_b, final Throwable t) {
        super(t);
        (this.field_151383_a = (List<Entry>)Lists.newArrayList()).add(new Entry(null));
        this.field_151382_b = field_151382_b;
    }
    
    public void func_151380_a(final String s) {
        Entry.access$1(this.field_151383_a.get("".length()), s);
    }
    
    @Override
    public String getMessage() {
        return JsonException.I["".length()] + this.field_151383_a.get(this.field_151383_a.size() - " ".length()).toString() + JsonException.I[" ".length()] + this.field_151382_b;
    }
    
    public static class Entry
    {
        private String field_151376_a;
        private final List<String> field_151375_b;
        private static final String[] I;
        
        private Entry() {
            this.field_151376_a = null;
            this.field_151375_b = (List<String>)Lists.newArrayList();
        }
        
        static {
            I();
        }
        
        @Override
        public String toString() {
            String s;
            if (this.field_151376_a != null) {
                if (!this.field_151375_b.isEmpty()) {
                    s = String.valueOf(this.field_151376_a) + Entry.I[" ".length()] + this.func_151372_b();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    s = this.field_151376_a;
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
            }
            else if (!this.field_151375_b.isEmpty()) {
                s = Entry.I["  ".length()] + this.func_151372_b();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                s = Entry.I["   ".length()];
            }
            return s;
        }
        
        private void func_151373_a(final String s) {
            this.field_151375_b.add("".length(), s);
        }
        
        static void access$1(final Entry entry, final String s) {
            entry.func_151373_a(s);
        }
        
        static void access$2(final Entry entry, final String field_151376_a) {
            entry.field_151376_a = field_151376_a;
        }
        
        private static void I() {
            (I = new String[0x37 ^ 0x33])["".length()] = I("ku", "FKIVm");
            Entry.I[" ".length()] = I("m", "MifbZ");
            Entry.I["  ".length()] = I("^2<\u00126\u0019\u0010<Y>\u001f\u000b7Px", "vgRyX");
            Entry.I["   ".length()] = I("C<-\u0013\u0018\u0004\u001e-X\u0010\u0002\u0005&Q", "kiCxv");
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        Entry(final Entry entry) {
            this();
        }
        
        public String func_151372_b() {
            return StringUtils.join((Iterable)this.field_151375_b, Entry.I["".length()]);
        }
    }
}
