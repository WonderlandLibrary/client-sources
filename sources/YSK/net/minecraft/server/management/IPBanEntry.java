package net.minecraft.server.management;

import com.google.gson.*;
import java.util.*;

public class IPBanEntry extends BanEntry<String>
{
    private static final String[] I;
    
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public IPBanEntry(final JsonObject jsonObject) {
        super(getIPFromJson(jsonObject), jsonObject);
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty(IPBanEntry.I["  ".length()], (String)this.getValue());
            super.onSerialization(jsonObject);
        }
    }
    
    public IPBanEntry(final String s) {
        this(s, null, null, null, null);
    }
    
    public IPBanEntry(final String s, final Date date, final String s2, final Date date2, final String s3) {
        super(s, date, s2, date2, s3);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u001a\u001b", "skTOA");
        IPBanEntry.I[" ".length()] = I("0:", "YJVsT");
        IPBanEntry.I["  ".length()] = I("&*", "OZCcb");
    }
    
    private static String getIPFromJson(final JsonObject jsonObject) {
        String asString;
        if (jsonObject.has(IPBanEntry.I["".length()])) {
            asString = jsonObject.get(IPBanEntry.I[" ".length()]).getAsString();
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            asString = null;
        }
        return asString;
    }
}
