package net.minecraft.util;

import org.apache.commons.lang3.*;
import com.google.gson.*;

public class JsonUtils
{
    private static final String[] I;
    
    public static JsonObject getJsonObject(final JsonObject jsonObject, final String s, final JsonObject jsonObject2) {
        JsonObject jsonObject3;
        if (jsonObject.has(s)) {
            jsonObject3 = getJsonObject(jsonObject.get(s), s);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            jsonObject3 = jsonObject2;
        }
        return jsonObject3;
    }
    
    public static boolean isString(final JsonElement jsonElement) {
        int n;
        if (!jsonElement.isJsonPrimitive()) {
            n = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            n = (jsonElement.getAsJsonPrimitive().isString() ? 1 : 0);
        }
        return n != 0;
    }
    
    public static String getString(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        throw new JsonSyntaxException(JsonUtils.I["".length()] + s + JsonUtils.I[" ".length()] + toString(jsonElement));
    }
    
    public static float getFloat(final JsonObject jsonObject, final String s, final float n) {
        float float1;
        if (jsonObject.has(s)) {
            float1 = getFloat(jsonObject.get(s), s);
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            float1 = n;
        }
        return float1;
    }
    
    public static boolean isJsonPrimitive(final JsonObject jsonObject, final String s) {
        int n;
        if (!hasField(jsonObject, s)) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = (jsonObject.get(s).isJsonPrimitive() ? 1 : 0);
        }
        return n != 0;
    }
    
    public static boolean getBoolean(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getBoolean(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException(JsonUtils.I[0x3A ^ 0x3C] + s + JsonUtils.I[0x2B ^ 0x2C]);
    }
    
    public static String getString(final JsonObject jsonObject, final String s, final String s2) {
        String string;
        if (jsonObject.has(s)) {
            string = getString(jsonObject.get(s), s);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            string = s2;
        }
        return string;
    }
    
    public static String getString(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getString(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException(JsonUtils.I["  ".length()] + s + JsonUtils.I["   ".length()]);
    }
    
    public static JsonArray getJsonArray(final JsonObject jsonObject, final String s, final JsonArray jsonArray) {
        JsonArray jsonArray2;
        if (jsonObject.has(s)) {
            jsonArray2 = getJsonArray(jsonObject.get(s), s);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            jsonArray2 = jsonArray;
        }
        return jsonArray2;
    }
    
    public static int getInt(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsInt();
        }
        throw new JsonSyntaxException(JsonUtils.I[0xB0 ^ 0xBC] + s + JsonUtils.I[0x87 ^ 0x8A] + toString(jsonElement));
    }
    
    public static JsonObject getJsonObject(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonObject(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException(JsonUtils.I[0x61 ^ 0x73] + s + JsonUtils.I[0x36 ^ 0x25]);
    }
    
    public static JsonArray getJsonArray(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        }
        throw new JsonSyntaxException(JsonUtils.I[0x63 ^ 0x77] + s + JsonUtils.I[0x25 ^ 0x30] + toString(jsonElement));
    }
    
    public static boolean isBoolean(final JsonObject jsonObject, final String s) {
        int n;
        if (!isJsonPrimitive(jsonObject, s)) {
            n = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n = (jsonObject.getAsJsonPrimitive(s).isBoolean() ? 1 : 0);
        }
        return n != 0;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int getInt(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getInt(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException(JsonUtils.I[0xAF ^ 0xA1] + s + JsonUtils.I[0x52 ^ 0x5D]);
    }
    
    private static void I() {
        (I = new String[0x58 ^ 0x7B])["".length()] = I("\u0013=\u00197%\" \rr", "VEiRF");
        JsonUtils.I[" ".length()] = I("E,$B)\u0000x*B8\u0011*\"\f,Ix<\u00038E", "eXKbK");
        JsonUtils.I["  ".length()] = I("\u001c\u0019\u0019;-?\u0017J", "QpjHD");
        JsonUtils.I["   ".length()] = I("dI&\b\u0004-\n7\u0015\u0010h\u001d,P\u0012!\u0007'P\u0015h\u001a7\u0002\u001d&\u000e", "HiCpt");
        JsonUtils.I[0x67 ^ 0x63] = I("3\"\t\b\u0015\u0002?\u001dM", "vZymv");
        JsonUtils.I[0xBE ^ 0xBB] = I("S\u0015-A\u0000\u0016A#A \u001c\u000e.\u0004\u0003\u001dMb\u0016\u0003\u0000A", "saBab");
        JsonUtils.I[0x86 ^ 0x80] = I("\u001c*'\u0001\u001e?$t", "QCTrw");
        JsonUtils.I[0xA9 ^ 0xAE] = I("jU2\u0000\n#\u0016#\u001d\u001ef\u00018X\u001c/\u001b3X\u001bf78\u0017\u0016#\u00149", "FuWxz");
        JsonUtils.I[0x6F ^ 0x67] = I("\u0010\t\u0003\"\u0015!\u0014\u0017g", "UqsGv");
        JsonUtils.I[0x7D ^ 0x74] = I("s\u001b9f\u00136O7f7?\u000072]s\u001875Q", "SoVFq");
        JsonUtils.I[0x72 ^ 0x78] = I(";\n\u0018\u0014\u0019\u0018\u0004K", "vckgp");
        JsonUtils.I[0xE ^ 0x5] = I("MK\t=\u0007\u0004\b\u0018 \u0013A\u001f\u0003e\u0011\b\u0005\be\u0016A-\u0000*\u0016\u0015", "aklEw");
        JsonUtils.I[0x54 ^ 0x58] = I("\u0017\u0016*)\u0011&\u000b>l", "RnZLr");
        JsonUtils.I[0x28 ^ 0x25] = I("M 'K\u000b\bt)K \u0003 dK\u001e\f'h", "mTHki");
        JsonUtils.I[0xA1 ^ 0xAF] = I(" \u0004)<\u0001\u0003\nz", "mmZOh");
        JsonUtils.I[0x17 ^ 0x18] = I("ir\u000f\u0014> 1\u001e\t*e&\u0005L(,<\u000eL/e\u001b\u0004\u0018", "ERjlN");
        JsonUtils.I[0x8E ^ 0x9E] = I("+\u000b 7;\u001a\u00164r", "nsPRX");
        JsonUtils.I[0xBF ^ 0xAE] = I("Y>9j0\u001cj7j\u0018\n%8\u00050\u0013/5>~Y=79r", "yJVJR");
        JsonUtils.I[0x2C ^ 0x3E] = I("8\u0003\t\u001e1\u001b\rZ", "ujzmX");
        JsonUtils.I[0xE ^ 0x1D] = I("KH3*\u0004\u0002\u000b\"7\u0010G\u001c9r\u0012\u000e\u00062r\u0015G\"%=\u001a(\n<7\u0017\u0013", "ghVRt");
        JsonUtils.I[0x88 ^ 0x9C] = I("\u0007\u00196\u001f\b6\u0004\"Z", "BaFzk");
        JsonUtils.I[0x1E ^ 0xB] = I("J\u0007\ba\u001a\u000fS\u0006a2\u0019\u001c\t\u0000\n\u0018\u0012\u001emX\u001d\u0012\u0014a", "jsgAx");
        JsonUtils.I[0xA3 ^ 0xB5] = I("\u000f\u001e<6\u0001,\u0010o", "BwOEh");
        JsonUtils.I[0x50 ^ 0x47] = I("UP\u0010\b!\u001c\u0013\u0001\u00155Y\u0004\u001aP7\u0010\u001e\u0011P0Y:\u0006\u001f?8\u0002\u0007\u0011(", "ypupQ");
        JsonUtils.I[0x69 ^ 0x71] = I("A{[", "oUutU");
        JsonUtils.I[0x66 ^ 0x7F] = I("\u0001\u001b\u001c5hG\u0003\u0019*;\u0006\u0000\u0017p", "onpYH");
        JsonUtils.I[0x5A ^ 0x40] = I("\u0001\u00015(CG\u001e*+\rF", "otYDc");
        JsonUtils.I[0xA3 ^ 0xB8] = I("#\u0004X(\u00000\u000b\u0001iZ", "BjxIr");
        JsonUtils.I[0x3A ^ 0x26] = I("s", "ZkxVY");
        JsonUtils.I[0x7E ^ 0x63] = I("\u0010\nP\u0017\u0005\u001b\u0001\u0013\fGY", "qdpxg");
        JsonUtils.I[0x94 ^ 0x8A] = I("q", "XbPyu");
        JsonUtils.I[0x44 ^ 0x5B] = I("0H( \u001f3\r4uZ", "QhFUr");
        JsonUtils.I[0x24 ^ 0x4] = I("j", "CxyFD");
        JsonUtils.I[0x65 ^ 0x44] = I("9n\n\b>4+\t\tqp", "XNhgQ");
        JsonUtils.I[0x87 ^ 0xA5] = I("s", "ZPLdd");
    }
    
    public static float getFloat(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getFloat(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException(JsonUtils.I[0x16 ^ 0x1C] + s + JsonUtils.I[0xB3 ^ 0xB8]);
    }
    
    public static JsonArray getJsonArray(final JsonObject jsonObject, final String s) {
        if (jsonObject.has(s)) {
            return getJsonArray(jsonObject.get(s), s);
        }
        throw new JsonSyntaxException(JsonUtils.I[0x9B ^ 0x8D] + s + JsonUtils.I[0x2C ^ 0x3B]);
    }
    
    public static boolean hasField(final JsonObject jsonObject, final String s) {
        int n;
        if (jsonObject == null) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (jsonObject.get(s) != null) {
            n = " ".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public static boolean getBoolean(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBoolean();
        }
        throw new JsonSyntaxException(JsonUtils.I[0x8E ^ 0x8A] + s + JsonUtils.I[0x30 ^ 0x35] + toString(jsonElement));
    }
    
    public static boolean isJsonArray(final JsonObject jsonObject, final String s) {
        int n;
        if (!hasField(jsonObject, s)) {
            n = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n = (jsonObject.get(s).isJsonArray() ? 1 : 0);
        }
        return n != 0;
    }
    
    public static boolean isString(final JsonObject jsonObject, final String s) {
        int n;
        if (!isJsonPrimitive(jsonObject, s)) {
            n = "".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n = (jsonObject.getAsJsonPrimitive(s).isString() ? 1 : 0);
        }
        return n != 0;
    }
    
    public static boolean getBoolean(final JsonObject jsonObject, final String s, final boolean b) {
        boolean boolean1;
        if (jsonObject.has(s)) {
            boolean1 = getBoolean(jsonObject.get(s), s);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            boolean1 = b;
        }
        return boolean1;
    }
    
    static {
        I();
    }
    
    public static JsonObject getJsonObject(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        }
        throw new JsonSyntaxException(JsonUtils.I[0x7F ^ 0x6F] + s + JsonUtils.I[0x6E ^ 0x7F] + toString(jsonElement));
    }
    
    public static String toString(final JsonElement jsonElement) {
        final String abbreviateMiddle = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), JsonUtils.I[0x6C ^ 0x74], 0x9F ^ 0x95);
        if (jsonElement == null) {
            return JsonUtils.I[0x16 ^ 0xF];
        }
        if (jsonElement.isJsonNull()) {
            return JsonUtils.I[0x2 ^ 0x18];
        }
        if (jsonElement.isJsonArray()) {
            return JsonUtils.I[0xA ^ 0x11] + abbreviateMiddle + JsonUtils.I[0x1F ^ 0x3];
        }
        if (jsonElement.isJsonObject()) {
            return JsonUtils.I[0x56 ^ 0x4B] + abbreviateMiddle + JsonUtils.I[0x77 ^ 0x69];
        }
        if (jsonElement.isJsonPrimitive()) {
            final JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (asJsonPrimitive.isNumber()) {
                return JsonUtils.I[0xA9 ^ 0xB6] + abbreviateMiddle + JsonUtils.I[0x86 ^ 0xA6];
            }
            if (asJsonPrimitive.isBoolean()) {
                return JsonUtils.I[0x5D ^ 0x7C] + abbreviateMiddle + JsonUtils.I[0xA3 ^ 0x81];
            }
        }
        return abbreviateMiddle;
    }
    
    public static float getFloat(final JsonElement jsonElement, final String s) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsFloat();
        }
        throw new JsonSyntaxException(JsonUtils.I[0x58 ^ 0x50] + s + JsonUtils.I[0x6A ^ 0x63] + toString(jsonElement));
    }
    
    public static int getInt(final JsonObject jsonObject, final String s, final int n) {
        int int1;
        if (jsonObject.has(s)) {
            int1 = getInt(jsonObject.get(s), s);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            int1 = n;
        }
        return int1;
    }
}
