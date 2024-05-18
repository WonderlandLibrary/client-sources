package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.gson.*;

public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer<FontMetadataSection>
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0x83 ^ 0x9A])["".length()] = I("\u000f,1\u0011\u001b\u000f05\u0011\t", "lDPcz");
        FontMetadataSectionSerializer.I[" ".length()] = I("/\u0004\u001504/\u0018\u00110&", "LltBU");
        FontMetadataSectionSerializer.I["  ".length()] = I("\u0001\u001c\u001a\u0014+!\u0016L\u0013(&\u0006AK$ \u0013\u001e\u0014$<\u0017\u001e\u0006}h\u0017\u0014\u0005\"+\u0006\t\u0011g'\u0010\u0006\u0010$<^L\u0002&;R", "HrluG");
        FontMetadataSectionSerializer.I["   ".length()] = I("\u000e#\u000f$\r\u000e?\u000b$\u001f", "mKnVl");
        FontMetadataSectionSerializer.I[0x57 ^ 0x53] = I("-'\u000f=,-;\u000b=>", "NOnOM");
        FontMetadataSectionSerializer.I[0x3A ^ 0x3F] = I("\b\u0012<-\u0019\u0000\u0003", "lwZLl");
        FontMetadataSectionSerializer.I[0xB4 ^ 0xB2] = I("\n\u0004\u000e\u0013\u0000\u0002\u0015", "nahru");
        FontMetadataSectionSerializer.I[0x73 ^ 0x74] = I("%\u0007%9;\u0005\rs>8\u0002\u001d~f4\u0004\b!94\u0018\f!+zR\r6>6\u0019\u0005'bw\t\u0011#=4\u0018\f7x8\u000e\u00036;#@I$9$L", "liSXW");
        FontMetadataSectionSerializer.I[0x48 ^ 0x40] = I("\u000262.,\n'", "fSTOY");
        FontMetadataSectionSerializer.I[0x9F ^ 0x96] = I("-1\u00072\u0012% ", "ITaSg");
        FontMetadataSectionSerializer.I[0x36 ^ 0x3C] = I("<\u001f\f\u0003*", "KvhwB");
        FontMetadataSectionSerializer.I[0x73 ^ 0x78] = I("\n\u001c\u001f\u0010\u0003*\u0016I\u0015\n%\u0013\u001c\u001d\u001bc\u0005\u0000\u0015\u001b+", "Criqo");
        FontMetadataSectionSerializer.I[0x42 ^ 0x4E] = I("?\u0014\r\u0005\u0000\"\u0003", "Ldlfi");
        FontMetadataSectionSerializer.I[0x4D ^ 0x40] = I("%\u0017\u0002\u000e\u0014\u0005\u001dT\u000b\u001d\n\u0018\u0001\u0003\fL\n\u0004\u000e\u001b\u0005\u0017\u0013", "lytox");
        FontMetadataSectionSerializer.I[0x8D ^ 0x83] = I("\n\u0000\u000e ", "fehTp");
        FontMetadataSectionSerializer.I[0x88 ^ 0x87] = I("\u0006\u0003\u0018\u0018-&\tN\u001d$)\f\u001b\u00155o\u0001\u000b\u001f5", "OmnyA");
        FontMetadataSectionSerializer.I[0xB4 ^ 0xA4] = I("\u001a-*\u0003'\u001a1.\u00035\"", "yEKqF");
        FontMetadataSectionSerializer.I[0x1C ^ 0xD] = I(":", "gcawf");
        FontMetadataSectionSerializer.I[0x8 ^ 0x1A] = I("%\")\u001b\u000e", "RKMof");
        FontMetadataSectionSerializer.I[0x72 ^ 0x61] = I("\b\u0007\u0015-)(\rC;,%\u001d\u000b", "AicLE");
        FontMetadataSectionSerializer.I[0x82 ^ 0x96] = I(":5;:;'\"", "IEZYR");
        FontMetadataSectionSerializer.I[0x6C ^ 0x79] = I("!4\u0000\u001b\u0006\u0001>V\t\u001a\t9\u001f\u0014\r", "hZvzj");
        FontMetadataSectionSerializer.I[0xB3 ^ 0xA5] = I("\u000b7-\u001a", "gRKnW");
        FontMetadataSectionSerializer.I[0x2E ^ 0x39] = I("'\u001f..\u0005\u0007\u0015x#\f\b\u0005", "nqXOi");
        FontMetadataSectionSerializer.I[0x5D ^ 0x45] = I("1\u001c\u0019\u0006", "Wswrz");
    }
    
    @Override
    public String getSectionName() {
        return FontMetadataSectionSerializer.I[0x99 ^ 0x81];
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    public FontMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final float[] array = new float[232 + 64 - 53 + 13];
        final float[] array2 = new float[56 + 39 - 53 + 214];
        final float[] array3 = new float[113 + 98 - 133 + 178];
        float float1 = 1.0f;
        float float2 = 0.0f;
        float float3 = 0.0f;
        if (asJsonObject.has(FontMetadataSectionSerializer.I["".length()])) {
            if (!asJsonObject.get(FontMetadataSectionSerializer.I[" ".length()]).isJsonObject()) {
                throw new JsonParseException(FontMetadataSectionSerializer.I["  ".length()] + asJsonObject.get(FontMetadataSectionSerializer.I["   ".length()]));
            }
            final JsonObject asJsonObject2 = asJsonObject.getAsJsonObject(FontMetadataSectionSerializer.I[0x97 ^ 0x93]);
            if (asJsonObject2.has(FontMetadataSectionSerializer.I[0xB9 ^ 0xBC])) {
                if (!asJsonObject2.get(FontMetadataSectionSerializer.I[0x6B ^ 0x6D]).isJsonObject()) {
                    throw new JsonParseException(FontMetadataSectionSerializer.I[0x82 ^ 0x85] + asJsonObject2.get(FontMetadataSectionSerializer.I[0x24 ^ 0x2C]));
                }
                final JsonObject asJsonObject3 = asJsonObject2.getAsJsonObject(FontMetadataSectionSerializer.I[0x54 ^ 0x5D]);
                float1 = JsonUtils.getFloat(asJsonObject3, FontMetadataSectionSerializer.I[0x2B ^ 0x21], float1);
                Validate.inclusiveBetween(0.0, 3.4028234663852886E38, (double)float1, FontMetadataSectionSerializer.I[0x1 ^ 0xA]);
                float2 = JsonUtils.getFloat(asJsonObject3, FontMetadataSectionSerializer.I[0x43 ^ 0x4F], float2);
                Validate.inclusiveBetween(0.0, 3.4028234663852886E38, (double)float2, FontMetadataSectionSerializer.I[0x9C ^ 0x91]);
                float3 = JsonUtils.getFloat(asJsonObject3, FontMetadataSectionSerializer.I[0xA8 ^ 0xA6], float2);
                Validate.inclusiveBetween(0.0, 3.4028234663852886E38, (double)float3, FontMetadataSectionSerializer.I[0x76 ^ 0x79]);
            }
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < 121 + 12 - 0 + 123) {
                final JsonElement value = asJsonObject2.get(Integer.toString(i));
                float float4 = float1;
                float float5 = float2;
                float float6 = float3;
                if (value != null) {
                    final JsonObject jsonObject = JsonUtils.getJsonObject(value, FontMetadataSectionSerializer.I[0x5B ^ 0x4B] + i + FontMetadataSectionSerializer.I[0x90 ^ 0x81]);
                    float4 = JsonUtils.getFloat(jsonObject, FontMetadataSectionSerializer.I[0x87 ^ 0x95], float1);
                    Validate.inclusiveBetween(0.0, 3.4028234663852886E38, (double)float4, FontMetadataSectionSerializer.I[0xB ^ 0x18]);
                    float5 = JsonUtils.getFloat(jsonObject, FontMetadataSectionSerializer.I[0x8D ^ 0x99], float2);
                    Validate.inclusiveBetween(0.0, 3.4028234663852886E38, (double)float5, FontMetadataSectionSerializer.I[0x90 ^ 0x85]);
                    float6 = JsonUtils.getFloat(jsonObject, FontMetadataSectionSerializer.I[0x32 ^ 0x24], float3);
                    Validate.inclusiveBetween(0.0, 3.4028234663852886E38, (double)float6, FontMetadataSectionSerializer.I[0x76 ^ 0x61]);
                }
                array[i] = float4;
                array2[i] = float5;
                array3[i] = float6;
                ++i;
            }
        }
        return new FontMetadataSection(array, array3, array2);
    }
}
