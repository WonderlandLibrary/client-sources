package net.minecraft.client.audio;

import java.lang.reflect.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.gson.*;

public class SoundListSerializer implements JsonDeserializer<SoundList>
{
    private static final String[] I;
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0xBA ^ 0xAD])["".length()] = I("=*\u0007\u0013\u001d", "XDsad");
        SoundListSerializer.I[" ".length()] = I("\u001c=\u001b \u000e\r=", "nXkLo");
        SoundListSerializer.I["  ".length()] = I("\u000e%1*\r\u00026<", "mDEOj");
        SoundListSerializer.I["   ".length()] = I("\u0019%\u00121=9/D30$.\u0003?#)", "PKdPQ");
        SoundListSerializer.I[0x69 ^ 0x6D] = I("\u001a!\u001e7\b\u001a", "iNkYl");
        SoundListSerializer.I[0x31 ^ 0x34] = I("\u0015:;\u001e3\u0015", "fUNpW");
        SoundListSerializer.I[0x39 ^ 0x3F] = I(")\u001d\u000f\u000f\u0014", "Zrzap");
        SoundListSerializer.I[0x5E ^ 0x59] = I("\u001a:\u0014\u00012", "iUaoV");
        SoundListSerializer.I[0xA2 ^ 0xAA] = I("9\r(7", "WlERN");
        SoundListSerializer.I[0x2E ^ 0x27] = I("=\u0001\u0005\u0007", "IxubD");
        SoundListSerializer.I[0x8E ^ 0x84] = I("\u001a\u001d:\u0004", "ndJae");
        SoundListSerializer.I[0x97 ^ 0x9C] = I("\u0005\u0019\f\u0002;%\u0013Z\u0017.<\u0012", "LwzcW");
        SoundListSerializer.I[0x47 ^ 0x4B] = I(",\u0003\u001c0;?", "ZlpEV");
        SoundListSerializer.I[0x80 ^ 0x8D] = I(",>\b;#?", "ZQdNN");
        SoundListSerializer.I[0x87 ^ 0x89] = I("\u0006:\u000f'+&0Y0(#!\u0014#", "OTyFG");
        SoundListSerializer.I[0xAE ^ 0xA1] = I("#\u001a\u0015\b\u0005", "Ssakm");
        SoundListSerializer.I[0x7E ^ 0x6E] = I("\u0000\u001f;.\u0010", "pvOMx");
        SoundListSerializer.I[0x46 ^ 0x57] = I(">,$+:\u001e&r:?\u0003!:", "wBRJV");
        SoundListSerializer.I[0x53 ^ 0x41] = I("2\u0014\u0011?%1", "EqxXM");
        SoundListSerializer.I[0x2C ^ 0x3F] = I("--\u0000\u0017\u001c.", "ZHipt");
        SoundListSerializer.I[0x62 ^ 0x76] = I("*\u001c\u0001\u0018/\n\u0016W\u000e&\n\u0015\u001f\r", "crwyC");
        SoundListSerializer.I[0xAF ^ 0xBA] = I("\u001d\r#\u0013\u0019\u0003", "nyQvx");
        SoundListSerializer.I[0x2 ^ 0x14] = I("26\u00110#,", "ABcUB");
    }
    
    public SoundList deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, SoundListSerializer.I["".length()]);
        final SoundList list = new SoundList();
        list.setReplaceExisting(JsonUtils.getBoolean(jsonObject, SoundListSerializer.I[" ".length()], (boolean)("".length() != 0)));
        final SoundCategory category = SoundCategory.getCategory(JsonUtils.getString(jsonObject, SoundListSerializer.I["  ".length()], SoundCategory.MASTER.getCategoryName()));
        list.setSoundCategory(category);
        Validate.notNull((Object)category, SoundListSerializer.I["   ".length()], new Object["".length()]);
        if (jsonObject.has(SoundListSerializer.I[0x91 ^ 0x95])) {
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, SoundListSerializer.I[0xBD ^ 0xB8]);
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < jsonArray.size()) {
                final JsonElement value = jsonArray.get(i);
                final SoundList.SoundEntry soundEntry = new SoundList.SoundEntry();
                if (JsonUtils.isString(value)) {
                    soundEntry.setSoundEntryName(JsonUtils.getString(value, SoundListSerializer.I[0xB0 ^ 0xB6]));
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    final JsonObject jsonObject2 = JsonUtils.getJsonObject(value, SoundListSerializer.I[0xA0 ^ 0xA7]);
                    soundEntry.setSoundEntryName(JsonUtils.getString(jsonObject2, SoundListSerializer.I[0x41 ^ 0x49]));
                    if (jsonObject2.has(SoundListSerializer.I[0x4A ^ 0x43])) {
                        final SoundList.SoundEntry.Type type2 = SoundList.SoundEntry.Type.getType(JsonUtils.getString(jsonObject2, SoundListSerializer.I[0x54 ^ 0x5E]));
                        Validate.notNull((Object)type2, SoundListSerializer.I[0xCE ^ 0xC5], new Object["".length()]);
                        soundEntry.setSoundEntryType(type2);
                    }
                    if (jsonObject2.has(SoundListSerializer.I[0xAE ^ 0xA2])) {
                        final float float1 = JsonUtils.getFloat(jsonObject2, SoundListSerializer.I[0x5B ^ 0x56]);
                        int n;
                        if (float1 > 0.0f) {
                            n = " ".length();
                            "".length();
                            if (3 <= 1) {
                                throw null;
                            }
                        }
                        else {
                            n = "".length();
                        }
                        Validate.isTrue((boolean)(n != 0), SoundListSerializer.I[0x93 ^ 0x9D], new Object["".length()]);
                        soundEntry.setSoundEntryVolume(float1);
                    }
                    if (jsonObject2.has(SoundListSerializer.I[0x0 ^ 0xF])) {
                        final float float2 = JsonUtils.getFloat(jsonObject2, SoundListSerializer.I[0x16 ^ 0x6]);
                        int n2;
                        if (float2 > 0.0f) {
                            n2 = " ".length();
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                        }
                        else {
                            n2 = "".length();
                        }
                        Validate.isTrue((boolean)(n2 != 0), SoundListSerializer.I[0x80 ^ 0x91], new Object["".length()]);
                        soundEntry.setSoundEntryPitch(float2);
                    }
                    if (jsonObject2.has(SoundListSerializer.I[0x43 ^ 0x51])) {
                        final int int1 = JsonUtils.getInt(jsonObject2, SoundListSerializer.I[0xAE ^ 0xBD]);
                        int n3;
                        if (int1 > 0) {
                            n3 = " ".length();
                            "".length();
                            if (2 < 0) {
                                throw null;
                            }
                        }
                        else {
                            n3 = "".length();
                        }
                        Validate.isTrue((boolean)(n3 != 0), SoundListSerializer.I[0x5F ^ 0x4B], new Object["".length()]);
                        soundEntry.setSoundEntryWeight(int1);
                    }
                    if (jsonObject2.has(SoundListSerializer.I[0xAB ^ 0xBE])) {
                        soundEntry.setStreaming(JsonUtils.getBoolean(jsonObject2, SoundListSerializer.I[0x67 ^ 0x71]));
                    }
                }
                list.getSoundList().add(soundEntry);
                ++i;
            }
        }
        return list;
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
