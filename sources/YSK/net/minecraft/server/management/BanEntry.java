package net.minecraft.server.management;

import java.util.*;
import com.google.gson.*;
import java.text.*;

public abstract class BanEntry<T> extends UserListEntry<T>
{
    private static final String[] I;
    protected final String reason;
    public static final SimpleDateFormat dateFormat;
    protected final Date banEndDate;
    protected final String bannedBy;
    protected final Date banStartDate;
    
    @Override
    boolean hasBanExpired() {
        int n;
        if (this.banEndDate == null) {
            n = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = (this.banEndDate.before(new Date()) ? 1 : 0);
        }
        return n != 0;
    }
    
    private static void I() {
        (I = new String[0x15 ^ 0x7])["".length()] = I(";57#E\u000f\u0001c>\fb\u0004\u0006`\u0005/v=)H\u0018", "BLNZh");
        BanEntry.I[" ".length()] = I("~\u0019(>\u00009;(|", "VLFUn");
        BanEntry.I["  ".length()] = I("\t\u000b \u001d4/J,\nq*\u0004n\u001c!.\u0018/\u0007>9D", "KjNsQ");
        BanEntry.I["   ".length()] = I("33\u0013\u001305%", "PAvrD");
        BanEntry.I[0x47 ^ 0x43] = I(";\u0006\u0004 \u0015=\u0010", "XtaAa");
        BanEntry.I[0x91 ^ 0x94] = I("#\u001e3=\u001b5", "PqFOx");
        BanEntry.I[0x97 ^ 0x91] = I("!;94\u00197", "RTLFz");
        BanEntry.I[0x6 ^ 0x1] = I("A\u000f\u0004%\u001c\u0006-\u0004g", "iZjNr");
        BanEntry.I[0x31 ^ 0x39] = I("-4(\u001b\"-?", "HLXrP");
        BanEntry.I[0x74 ^ 0x7D] = I("\u001f \u001e&5\u001f+", "zXnOG");
        BanEntry.I[0x67 ^ 0x6D] = I("\u0001&\r>\u0006\u001d", "sClMi");
        BanEntry.I[0xAD ^ 0xA6] = I("4\u001f\u0013<\n(", "FzrOe");
        BanEntry.I[0xA8 ^ 0xA4] = I("\r\u0013\u000b<0+R\u0007+u.\u001cE=%*\u0000\u0004&:=\\", "OreRU");
        BanEntry.I[0xB6 ^ 0xBB] = I("\u0001?\u000625\u0007)", "bMcSA");
        BanEntry.I[0x16 ^ 0x18] = I("\u0019\u001b!\u0011\"\u000f", "jtTcA");
        BanEntry.I[0xBD ^ 0xB2] = I(". \u0013\u001b$.+", "KXcrV");
        BanEntry.I[0x79 ^ 0x69] = I("\u0014\u00051/9\u0017\u0018", "rjCJO");
        BanEntry.I[0x8F ^ 0x9E] = I("\b-\r?\u001b\u0014", "zHlLt");
    }
    
    public Date getBanEndDate() {
        return this.banEndDate;
    }
    
    public BanEntry(final T t, final Date date, final String s, final Date banEndDate, final String s2) {
        super(t);
        Date banStartDate;
        if (date == null) {
            banStartDate = new Date();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            banStartDate = date;
        }
        this.banStartDate = banStartDate;
        String bannedBy;
        if (s == null) {
            bannedBy = BanEntry.I[" ".length()];
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            bannedBy = s;
        }
        this.bannedBy = bannedBy;
        this.banEndDate = banEndDate;
        String reason;
        if (s2 == null) {
            reason = BanEntry.I["  ".length()];
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            reason = s2;
        }
        this.reason = reason;
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        jsonObject.addProperty(BanEntry.I[0xAB ^ 0xA6], BanEntry.dateFormat.format(this.banStartDate));
        jsonObject.addProperty(BanEntry.I[0xA9 ^ 0xA7], this.bannedBy);
        final String s = BanEntry.I[0x6B ^ 0x64];
        String format;
        if (this.banEndDate == null) {
            format = BanEntry.I[0x61 ^ 0x71];
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            format = BanEntry.dateFormat.format(this.banEndDate);
        }
        jsonObject.addProperty(s, format);
        jsonObject.addProperty(BanEntry.I[0x1D ^ 0xC], this.reason);
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected BanEntry(final T t, final JsonObject jsonObject) {
        super(t, jsonObject);
        Date banStartDate;
        try {
            Date parse;
            if (jsonObject.has(BanEntry.I["   ".length()])) {
                parse = BanEntry.dateFormat.parse(jsonObject.get(BanEntry.I[0xC ^ 0x8]).getAsString());
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                parse = new Date();
            }
            banStartDate = parse;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        catch (ParseException ex) {
            banStartDate = new Date();
        }
        this.banStartDate = banStartDate;
        String asString;
        if (jsonObject.has(BanEntry.I[0x5 ^ 0x0])) {
            asString = jsonObject.get(BanEntry.I[0x1D ^ 0x1B]).getAsString();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            asString = BanEntry.I[0xA5 ^ 0xA2];
        }
        this.bannedBy = asString;
        Date banEndDate;
        try {
            Date parse2;
            if (jsonObject.has(BanEntry.I[0x7D ^ 0x75])) {
                parse2 = BanEntry.dateFormat.parse(jsonObject.get(BanEntry.I[0xD ^ 0x4]).getAsString());
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                parse2 = null;
            }
            banEndDate = parse2;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (ParseException ex2) {
            banEndDate = null;
        }
        this.banEndDate = banEndDate;
        String asString2;
        if (jsonObject.has(BanEntry.I[0x66 ^ 0x6C])) {
            asString2 = jsonObject.get(BanEntry.I[0x2F ^ 0x24]).getAsString();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            asString2 = BanEntry.I[0x23 ^ 0x2F];
        }
        this.reason = asString2;
    }
    
    static {
        I();
        dateFormat = new SimpleDateFormat(BanEntry.I["".length()]);
    }
    
    public String getBanReason() {
        return this.reason;
    }
}
