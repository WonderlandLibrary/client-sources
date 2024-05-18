package optfine;

import com.google.gson.*;

public class Json
{
    private static final String[] I;
    
    public static int[] parseIntArray(final JsonElement jsonElement, final int n, final int[] array) {
        if (jsonElement == null) {
            return array;
        }
        final JsonArray asJsonArray = jsonElement.getAsJsonArray();
        if (asJsonArray.size() != n) {
            throw new JsonParseException(Json.I["   ".length()] + asJsonArray.size() + Json.I[0x72 ^ 0x76] + n + Json.I[0x8 ^ 0xD] + asJsonArray);
        }
        final int[] array2 = new int[asJsonArray.size()];
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < array2.length) {
            array2[i] = asJsonArray.get(i).getAsInt();
            ++i;
        }
        return array2;
    }
    
    public static float[] parseFloatArray(final JsonElement jsonElement, final int n, final float[] array) {
        if (jsonElement == null) {
            return array;
        }
        final JsonArray asJsonArray = jsonElement.getAsJsonArray();
        if (asJsonArray.size() != n) {
            throw new JsonParseException(Json.I["".length()] + asJsonArray.size() + Json.I[" ".length()] + n + Json.I["  ".length()] + asJsonArray);
        }
        final float[] array2 = new float[asJsonArray.size()];
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < array2.length) {
            array2[i] = asJsonArray.get(i).getAsFloat();
            ++i;
        }
        return array2;
    }
    
    static {
        I();
    }
    
    public static float getFloat(final JsonObject jsonObject, final String s, final float n) {
        final JsonElement value = jsonObject.get(s);
        float asFloat;
        if (value == null) {
            asFloat = n;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            asFloat = value.getAsFloat();
        }
        return asFloat;
    }
    
    public static boolean getBoolean(final JsonObject jsonObject, final String s, final boolean b) {
        final JsonElement value = jsonObject.get(s);
        boolean asBoolean;
        if (value == null) {
            asBoolean = b;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            asBoolean = value.getAsBoolean();
        }
        return asBoolean;
    }
    
    private static void I() {
        (I = new String[0x7 ^ 0x1])["".length()] = I("-\u0013=\u0003(Z\u0000 \u001f.\u0003A>\b!\u001d\u0015:Wo", "zaRmO");
        Json.I[" ".length()] = I("JD\u0014\u00196\u0013\b\u0003Q;\u0003^G", "fdgqY");
        Json.I["  ".length()] = I("hK\u0015\u001c\n%\u0012NN", "Dktnx");
        Json.I["   ".length()] = I("8\u001b\u001c\u001b\u0002O\b\u0001\u0007\u0004\u0016I\u001f\u0010\u000b\b\u001d\u001bOE", "oisue");
        Json.I[0xC4 ^ 0xC0] = I("\u007fm\u0014&\u0007&!\u0003n\n6wG", "SMgNh");
        Json.I[0xC6 ^ 0xC3] = I("bY-=</\u0000vo", "NyLON");
    }
    
    public static int[] parseIntArray(final JsonElement jsonElement, final int n) {
        return parseIntArray(jsonElement, n, null);
    }
    
    public static String getString(final JsonObject jsonObject, final String s, final String s2) {
        final JsonElement value = jsonObject.get(s);
        String asString;
        if (value == null) {
            asString = s2;
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            asString = value.getAsString();
        }
        return asString;
    }
    
    public static float[] parseFloatArray(final JsonElement jsonElement, final int n) {
        return parseFloatArray(jsonElement, n, null);
    }
    
    public static String getString(final JsonObject jsonObject, final String s) {
        return getString(jsonObject, s, null);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
