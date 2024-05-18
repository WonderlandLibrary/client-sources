package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer<TextureMetadataSection>
{
    private static final String[] I;
    
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x62 ^ 0x69])["".length()] = I("\u0013?-\u0004", "qSXvw");
        TextureMetadataSectionSerializer.I[" ".length()] = I("+\u0003\u000f\u0003\u0002", "Honnr");
        TextureMetadataSectionSerializer.I["  ".length()] = I("&\u001d\u00189#;\u0007", "KthTB");
        TextureMetadataSectionSerializer.I["   ".length()] = I("*\u0011\u001a\u000427\u000b", "GxjiS");
        TextureMetadataSectionSerializer.I[0x0 ^ 0x4] = I("\r:!6?-0w#6< \"%6ij:>#)5'zm", "DTWWS");
        TextureMetadataSectionSerializer.I[0x3E ^ 0x3B] = I("jy&\u0000=5:7\u001d)p76\u0015/5+oX:1*c", "PYCxM");
        TextureMetadataSectionSerializer.I[0x3A ^ 0x3C] = I("8;8\u0017\b\u00181n\u0002\u0001\t!;\u0004\u0001\\k#\u001f\u0014\u001c4>[Z", "qUNvd");
        TextureMetadataSectionSerializer.I[0xC4 ^ 0xC3] = I("^l+<=\u0001/:!)D\";)/\u0001>bd:\u0005?n", "dLNDM");
        TextureMetadataSectionSerializer.I[0xB4 ^ 0xBC] = I("= \u001d\u000e\u000f\u001d*K\u001b\u0006\f:\u001e\u001d\u0006Yp\u0006\u0006\u0013\u0019/\u001b\u001cYT+\u0013\u001f\u0006\u0017:\u000e\u000bC\u0015<\u0019\u000e\u001aXn\u001c\u000e\u0010T", "tNkoc");
        TextureMetadataSectionSerializer.I[0x49 ^ 0x40] = I("( \u0006\u001c15:", "EIvqP");
        TextureMetadataSectionSerializer.I[0x33 ^ 0x39] = I("$ 2!1\" ", "PEJUD");
    }
    
    @Override
    public String getSectionName() {
        return TextureMetadataSectionSerializer.I[0x47 ^ 0x4D];
    }
    
    public TextureMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final boolean boolean1 = JsonUtils.getBoolean(asJsonObject, TextureMetadataSectionSerializer.I["".length()], "".length() != 0);
        final boolean boolean2 = JsonUtils.getBoolean(asJsonObject, TextureMetadataSectionSerializer.I[" ".length()], "".length() != 0);
        final ArrayList arrayList = Lists.newArrayList();
        if (asJsonObject.has(TextureMetadataSectionSerializer.I["  ".length()])) {
            try {
                final JsonArray asJsonArray = asJsonObject.getAsJsonArray(TextureMetadataSectionSerializer.I["   ".length()]);
                int i = "".length();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                while (i < asJsonArray.size()) {
                    final JsonElement value = asJsonArray.get(i);
                    Label_0265: {
                        if (value.isJsonPrimitive()) {
                            try {
                                arrayList.add(value.getAsInt());
                                "".length();
                                if (true != true) {
                                    throw null;
                                }
                                break Label_0265;
                            }
                            catch (NumberFormatException ex) {
                                throw new JsonParseException(TextureMetadataSectionSerializer.I[0xA8 ^ 0xAC] + i + TextureMetadataSectionSerializer.I[0x53 ^ 0x56] + value, (Throwable)ex);
                            }
                        }
                        if (value.isJsonObject()) {
                            throw new JsonParseException(TextureMetadataSectionSerializer.I[0x94 ^ 0x92] + i + TextureMetadataSectionSerializer.I[0x6 ^ 0x1] + value);
                        }
                    }
                    ++i;
                }
                "".length();
                if (4 == 2) {
                    throw null;
                }
            }
            catch (ClassCastException ex2) {
                throw new JsonParseException(TextureMetadataSectionSerializer.I[0xB3 ^ 0xBB] + asJsonObject.get(TextureMetadataSectionSerializer.I[0x17 ^ 0x1E]), (Throwable)ex2);
            }
        }
        return new TextureMetadataSection(boolean1, boolean2, arrayList);
    }
}
