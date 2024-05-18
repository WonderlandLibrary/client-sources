package net.minecraft.client.resources.data;

import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import com.google.gson.*;
import java.util.*;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer<AnimationMetadataSection> implements JsonSerializer<AnimationMetadataSection>
{
    private static final String[] I;
    
    static {
        I();
    }
    
    public String getSectionName() {
        return AnimationMetadataSectionSerializer.I[0x24 ^ 0x3F];
    }
    
    private AnimationFrame parseAnimationFrame(final int n, final JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            return new AnimationFrame(JsonUtils.getInt(jsonElement, AnimationMetadataSectionSerializer.I[0x60 ^ 0x6C] + n + AnimationMetadataSectionSerializer.I[0x1 ^ 0xC]));
        }
        if (jsonElement.isJsonObject()) {
            final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, AnimationMetadataSectionSerializer.I[0x7D ^ 0x73] + n + AnimationMetadataSectionSerializer.I[0xA1 ^ 0xAE]);
            final int int1 = JsonUtils.getInt(jsonObject, AnimationMetadataSectionSerializer.I[0x77 ^ 0x67], -" ".length());
            if (jsonObject.has(AnimationMetadataSectionSerializer.I[0xD5 ^ 0xC4])) {
                Validate.inclusiveBetween(1L, 2147483647L, (long)int1, AnimationMetadataSectionSerializer.I[0x34 ^ 0x26]);
            }
            final int int2 = JsonUtils.getInt(jsonObject, AnimationMetadataSectionSerializer.I[0xD6 ^ 0xC5]);
            Validate.inclusiveBetween(0L, 2147483647L, (long)int2, AnimationMetadataSectionSerializer.I[0x75 ^ 0x61]);
            return new AnimationFrame(int2, int1);
        }
        return null;
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((AnimationMetadataSection)o, type, jsonSerializationContext);
    }
    
    public JsonElement serialize(final AnimationMetadataSection animationMetadataSection, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(AnimationMetadataSectionSerializer.I[0xB ^ 0x1E], (Number)animationMetadataSection.getFrameTime());
        if (animationMetadataSection.getFrameWidth() != -" ".length()) {
            jsonObject.addProperty(AnimationMetadataSectionSerializer.I[0x5 ^ 0x13], (Number)animationMetadataSection.getFrameWidth());
        }
        if (animationMetadataSection.getFrameHeight() != -" ".length()) {
            jsonObject.addProperty(AnimationMetadataSectionSerializer.I[0x8F ^ 0x98], (Number)animationMetadataSection.getFrameHeight());
        }
        if (animationMetadataSection.getFrameCount() > 0) {
            final JsonArray jsonArray = new JsonArray();
            int i = "".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (i < animationMetadataSection.getFrameCount()) {
                if (animationMetadataSection.frameHasTime(i)) {
                    final JsonObject jsonObject2 = new JsonObject();
                    jsonObject2.addProperty(AnimationMetadataSectionSerializer.I[0x7C ^ 0x64], (Number)animationMetadataSection.getFrameIndex(i));
                    jsonObject2.addProperty(AnimationMetadataSectionSerializer.I[0x14 ^ 0xD], (Number)animationMetadataSection.getFrameTimeSingle(i));
                    jsonArray.add((JsonElement)jsonObject2);
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else {
                    jsonArray.add((JsonElement)new JsonPrimitive((Number)animationMetadataSection.getFrameIndex(i)));
                }
                ++i;
            }
            jsonObject.add(AnimationMetadataSectionSerializer.I[0x13 ^ 0x9], (JsonElement)jsonArray);
        }
        return (JsonElement)jsonObject;
    }
    
    private static void I() {
        (I = new String[0xA7 ^ 0xBB])["".length()] = I("\u0000?\u0003\u0004 \f.\u0016E7\b9\u0003\f+\u0003", "mZweD");
        AnimationMetadataSectionSerializer.I[" ".length()] = I("-02 ??+>(", "KBSMZ");
        AnimationMetadataSectionSerializer.I["  ".length()] = I("+,\u001d'\u0014\u000b&K\"\u001d\u0004#\u001e*\fB$\u0019'\u0015\u0007b\u001f/\u0015\u0007", "bBkFx");
        AnimationMetadataSectionSerializer.I["   ".length()] = I(".72'5;", "HESJP");
        AnimationMetadataSectionSerializer.I[0x35 ^ 0x31] = I(")#\u0000/\u001c<", "OQaBy");
        AnimationMetadataSectionSerializer.I[0x5E ^ 0x5B] = I(" 6\u0004+\u001c\u0000<R+\u001e\u00005\u0013>\u0019\u00066_t\u0016\u001b9\u001f/\u0003Sx\u00172\u0000\f;\u0006/\u0014I9\u00008\u0011\u0010tR=\u0011\u001ax", "iXrJp");
        AnimationMetadataSectionSerializer.I[0x3B ^ 0x3D] = I("3\u0003\f \r&", "UqmMh");
        AnimationMetadataSectionSerializer.I[0x34 ^ 0x33] = I("2<\u0000<\u000b", "EUdHc");
        AnimationMetadataSectionSerializer.I[0x52 ^ 0x5A] = I("\f\u0013$/!\u0010", "dvMHI");
        AnimationMetadataSectionSerializer.I[0x54 ^ 0x5D] = I("'\u001c9\u0019:\u0007\u0016o\u000f?\n\u0006'", "nrOxV");
        AnimationMetadataSectionSerializer.I[0x45 ^ 0x4F] = I("9\u0002\u0000\u0016 \u0019\bV\u001f)\u0019\u000b\u001e\u0003", "plvwL");
        AnimationMetadataSectionSerializer.I[0x74 ^ 0x7F] = I("\u001b$&):\u0002%>-<\u0017", "rJRLH");
        AnimationMetadataSectionSerializer.I[0xCC ^ 0xC0] = I("5*\u0004\u001b\u001c \u0003", "SXevy");
        AnimationMetadataSectionSerializer.I[0x6F ^ 0x62] = I(";", "fMvhX");
        AnimationMetadataSectionSerializer.I[0xB ^ 0x5] = I("#!\f+\u001f6\b", "ESmFz");
        AnimationMetadataSectionSerializer.I[0x5C ^ 0x53] = I("<", "aeJoO");
        AnimationMetadataSectionSerializer.I[0xD1 ^ 0xC1] = I("!;\u0017+", "URzNS");
        AnimationMetadataSectionSerializer.I[0x72 ^ 0x63] = I("21\u0015&", "FXxCQ");
        AnimationMetadataSectionSerializer.I[0x7E ^ 0x6C] = I("\n\u001f\u0013\u0000\u001b*\u0015E\u0007\u0005\"\u001c\u0000A\u0003*\u001c\u0000", "Cqeaw");
        AnimationMetadataSectionSerializer.I[0x51 ^ 0x42] = I("\u00188'5+", "qVCPS");
        AnimationMetadataSectionSerializer.I[0x82 ^ 0x96] = I("\u0002\u0014<2\u001b\"\u001ej5\u0005*\u0017/s\u001e%\u001e/+", "KzJSw");
        AnimationMetadataSectionSerializer.I[0x4F ^ 0x5A] = I("\u00168\u0000\u0001\u001f\u0004#\f\t", "pJalz");
        AnimationMetadataSectionSerializer.I[0xB8 ^ 0xAE] = I("\u0000\u001d2\u0017\u001c", "wtVct");
        AnimationMetadataSectionSerializer.I[0x6C ^ 0x7B] = I("!\u001c&?\u0003=", "IyOXk");
        AnimationMetadataSectionSerializer.I[0x2 ^ 0x1A] = I("\"\u001f/\u0016\u0014", "KqKsl");
        AnimationMetadataSectionSerializer.I[0x55 ^ 0x4C] = I("\u001d\b:\r", "iaWhy");
        AnimationMetadataSectionSerializer.I[0x3D ^ 0x27] = I("/\u00068\u0003\u0002:", "ItYng");
        AnimationMetadataSectionSerializer.I[0x6D ^ 0x76] = I("\u0000\u001c; 5\u0015\u001b=#", "arRMT");
    }
    
    public AnimationMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final ArrayList arrayList = Lists.newArrayList();
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, AnimationMetadataSectionSerializer.I["".length()]);
        final int int1 = JsonUtils.getInt(jsonObject, AnimationMetadataSectionSerializer.I[" ".length()], " ".length());
        if (int1 != " ".length()) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)int1, AnimationMetadataSectionSerializer.I["  ".length()]);
        }
        if (jsonObject.has(AnimationMetadataSectionSerializer.I["   ".length()])) {
            try {
                final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, AnimationMetadataSectionSerializer.I[0x65 ^ 0x61]);
                int i = "".length();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                while (i < jsonArray.size()) {
                    final AnimationFrame animationFrame = this.parseAnimationFrame(i, jsonArray.get(i));
                    if (animationFrame != null) {
                        arrayList.add(animationFrame);
                    }
                    ++i;
                }
                "".length();
                if (1 == 4) {
                    throw null;
                }
            }
            catch (ClassCastException ex) {
                throw new JsonParseException(AnimationMetadataSectionSerializer.I[0xB8 ^ 0xBD] + jsonObject.get(AnimationMetadataSectionSerializer.I[0x21 ^ 0x27]), (Throwable)ex);
            }
        }
        final int int2 = JsonUtils.getInt(jsonObject, AnimationMetadataSectionSerializer.I[0x91 ^ 0x96], -" ".length());
        final int int3 = JsonUtils.getInt(jsonObject, AnimationMetadataSectionSerializer.I[0x8A ^ 0x82], -" ".length());
        if (int2 != -" ".length()) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)int2, AnimationMetadataSectionSerializer.I[0x20 ^ 0x29]);
        }
        if (int3 != -" ".length()) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)int3, AnimationMetadataSectionSerializer.I[0x72 ^ 0x78]);
        }
        return new AnimationMetadataSection(arrayList, int2, int3, int1, JsonUtils.getBoolean(jsonObject, AnimationMetadataSectionSerializer.I[0x5F ^ 0x54], "".length() != 0));
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
}
