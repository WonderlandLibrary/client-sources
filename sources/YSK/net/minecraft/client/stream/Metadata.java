package net.minecraft.client.stream;

import java.util.*;
import com.google.gson.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public class Metadata
{
    private Map<String, String> payload;
    private static final String[] I;
    private final String name;
    private String description;
    private static final Gson field_152811_a;
    
    static {
        I();
        field_152811_a = new Gson();
    }
    
    public void func_152808_a(final String s, final String s2) {
        if (this.payload == null) {
            this.payload = (Map<String, String>)Maps.newHashMap();
        }
        if (this.payload.size() > (0x4E ^ 0x7C)) {
            throw new IllegalArgumentException(Metadata.I["".length()]);
        }
        if (s == null) {
            throw new IllegalArgumentException(Metadata.I[" ".length()]);
        }
        if (s.length() > 124 + 4 + 91 + 36) {
            throw new IllegalArgumentException(Metadata.I["  ".length()]);
        }
        if (s2 == null) {
            throw new IllegalArgumentException(Metadata.I["   ".length()]);
        }
        if (s2.length() > 106 + 247 - 115 + 17) {
            throw new IllegalArgumentException(Metadata.I[0xA3 ^ 0xA7]);
        }
        this.payload.put(s, s2);
    }
    
    public Metadata(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
    
    public void func_152807_a(final String description) {
        this.description = description;
    }
    
    public Metadata(final String s) {
        this(s, null);
    }
    
    private static void I() {
        (I = new String[0x62 ^ 0x6A])["".length()] = I("\u0005\u000b\u0013\u001b\u0011)\u001a\u0006Z\u0005)\u0017\u000b\u0015\u0014,N\u000e\tU.\u001b\u000b\u0016Yh\r\u0006\u0014\u001b'\u001aG\u001b\u0011,N\n\u0015\u0007-N\u0013\u0015U!\u001aF", "Hngzu");
        Metadata.I[" ".length()] = I("\u0004(\u0016\u0004-(9\u0003E9(4\u000e\n(-m\t\u00000i.\u0003\u000b'&9B\u0007,i#\u0017\t%h", "IMbeI");
        Metadata.I["  ".length()] = I(" 2\u0007#\u0012\f#\u0012b\u0006\f.\u001f-\u0017\tw\u0018'\u000fM>\u0000b\u0002\u00028S.\u0019\u00030R", "mWsBv");
        Metadata.I["   ".length()] = I("\u0000\f\u00125/,\u001d\u0007t;,\u0010\n;*)I\u00105'8\fF7*#\u0007\t k/\fF:>!\u0005G", "MifTK");
        Metadata.I[0x46 ^ 0x42] = I("\u001d\u0017\u0017&\b1\u0006\u0002g\u001c1\u000b\u000f(\r4R\u0015&\u0000%\u0017C.\u001fp\u0006\f(L<\u001d\r M", "PrcGl");
        Metadata.I[0x40 ^ 0x45] = I("\u0005\t7'", "khZBX");
        Metadata.I[0x6 ^ 0x0] = I("1.\u0014\u00114<;\u0013\u001b);", "UKgrF");
        Metadata.I[0x33 ^ 0x34] = I("\f\u0016\u0018\u0007", "hwlfn");
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add(Metadata.I[0xC6 ^ 0xC3], (Object)this.name).add(Metadata.I[0x5C ^ 0x5A], (Object)this.description).add(Metadata.I[0x97 ^ 0x90], (Object)this.func_152806_b()).toString();
    }
    
    public String func_152809_a() {
        String s;
        if (this.description == null) {
            s = this.name;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            s = this.description;
        }
        return s;
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String func_152806_b() {
        String json;
        if (this.payload != null && !this.payload.isEmpty()) {
            json = Metadata.field_152811_a.toJson((Object)this.payload);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            json = null;
        }
        return json;
    }
    
    public String func_152810_c() {
        return this.name;
    }
}
