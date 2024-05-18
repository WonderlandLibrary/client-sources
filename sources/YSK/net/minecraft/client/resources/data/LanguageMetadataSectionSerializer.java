package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import com.google.gson.*;
import java.util.*;

public class LanguageMetadataSectionSerializer extends BaseMetadataSectionSerializer<LanguageMetadataSection>
{
    private static final String[] I;
    
    @Override
    public String getSectionName() {
        return LanguageMetadataSectionSerializer.I[0xAD ^ 0xA7];
    }
    
    public LanguageMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final HashSet hashSet = Sets.newHashSet();
        final Iterator iterator = asJsonObject.entrySet().iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, V> entry = iterator.next();
            final String s = entry.getKey();
            final JsonObject jsonObject = JsonUtils.getJsonObject((JsonElement)entry.getValue(), LanguageMetadataSectionSerializer.I["".length()]);
            final String string = JsonUtils.getString(jsonObject, LanguageMetadataSectionSerializer.I[" ".length()]);
            final String string2 = JsonUtils.getString(jsonObject, LanguageMetadataSectionSerializer.I["  ".length()]);
            final boolean boolean1 = JsonUtils.getBoolean(jsonObject, LanguageMetadataSectionSerializer.I["   ".length()], "".length() != 0);
            if (string.isEmpty()) {
                throw new JsonParseException(LanguageMetadataSectionSerializer.I[0xC5 ^ 0xC1] + s + LanguageMetadataSectionSerializer.I[0x79 ^ 0x7C]);
            }
            if (string2.isEmpty()) {
                throw new JsonParseException(LanguageMetadataSectionSerializer.I[0x28 ^ 0x2E] + s + LanguageMetadataSectionSerializer.I[0x1C ^ 0x1B]);
            }
            if (!hashSet.add(new Language(s, string, string2, boolean1))) {
                throw new JsonParseException(LanguageMetadataSectionSerializer.I[0xA1 ^ 0xA9] + s + LanguageMetadataSectionSerializer.I[0x9A ^ 0x93]);
            }
        }
        return new LanguageMetadataSection(hashSet);
    }
    
    private static void I() {
        (I = new String[0x9F ^ 0x94])["".length()] = I("?+\f\r\u00012-\u0007", "SJbjt");
        LanguageMetadataSectionSerializer.I[" ".length()] = I("1,\u001d\u00136-", "CIzzY");
        LanguageMetadataSectionSerializer.I["  ".length()] = I("#/=\u0012", "MNPws");
        LanguageMetadataSectionSerializer.I["   ".length()] = I("\u0011\u0002%&5\u0016\b5&(\u001d\n-", "skAOG");
        LanguageMetadataSectionSerializer.I[0xC0 ^ 0xC4] = I("\u000e\r1 8.\u0007g-5)\u00042 3\"Nyf", "GcGAT");
        LanguageMetadataSectionSerializer.I[0x73 ^ 0x76] = I("LiM\u0007\u0007\f-\u001c\u001bXK!\u001e\u0005\u0016\u0012d\u0005\u0014\u000e\u001e!", "kDsub");
        LanguageMetadataSectionSerializer.I[0x65 ^ 0x63] = I("<\r\u0010'#\u001c\u0007F*.\u001b\u0004\u0013'(\u0010NXa", "ucfFO");
        LanguageMetadataSectionSerializer.I[0x7 ^ 0x0] = I("Ie}*#\u0003-yd'\u000387=b\u0018)/1'", "nHCDB");
        LanguageMetadataSectionSerializer.I[0x7 ^ 0xF] = I("(\u0000\t\u000b\u0000\u000f\u0014\r\u0002I\u0000\u0014\u0017\u0000\u001c\r\u0012\u001cJWK", "luygi");
        LanguageMetadataSectionSerializer.I[0x2B ^ 0x22] = I("eM\b\"\t+\u0003\t#", "BmlGo");
        LanguageMetadataSectionSerializer.I[0x48 ^ 0x42] = I("\u001d\b\u0002\u0011\u0000\u0010\u000e\t", "qilvu");
    }
    
    static {
        I();
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
