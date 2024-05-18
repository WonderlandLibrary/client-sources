package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class PackMetadataSectionSerializer extends BaseMetadataSectionSerializer<PackMetadataSection> implements JsonSerializer<PackMetadataSection>
{
    private static final String[] I;
    
    public PackMetadataSection deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final IChatComponent chatComponent = (IChatComponent)jsonDeserializationContext.deserialize(asJsonObject.get(PackMetadataSectionSerializer.I["".length()]), (Type)IChatComponent.class);
        if (chatComponent == null) {
            throw new JsonParseException(PackMetadataSectionSerializer.I[" ".length()]);
        }
        return new PackMetadataSection(chatComponent, JsonUtils.getInt(asJsonObject, PackMetadataSectionSerializer.I["  ".length()]));
    }
    
    private static void I() {
        (I = new String[0x2A ^ 0x2C])["".length()] = I("2$\u001a\u0000'?1\u001d\n:8", "VAicU");
        PackMetadataSectionSerializer.I[" ".length()] = I("(\u001a$*\u0003\b\u0010}&\u0006\u0012\u0007;%\bA\u001078\f\u0013\u001d\"?\u0006\u000e\u001as", "atRKo");
        PackMetadataSectionSerializer.I["  ".length()] = I("#\u00191\u0012.5\u0017 \u0014\u0010'", "SxRyq");
        PackMetadataSectionSerializer.I["   ".length()] = I("\t\u0007%%3\u001f\t4#\r\r", "yfFNl");
        PackMetadataSectionSerializer.I[0x69 ^ 0x6D] = I("%\u001c)\u0017\u0002(\t.\u001d\u001f/", "AyZtp");
        PackMetadataSectionSerializer.I[0x9A ^ 0x9F] = I("*+\b?", "ZJkTj");
    }
    
    public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
        return this.serialize((PackMetadataSection)o, type, jsonSerializationContext);
    }
    
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    public JsonElement serialize(final PackMetadataSection packMetadataSection, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(PackMetadataSectionSerializer.I["   ".length()], (Number)packMetadataSection.getPackFormat());
        jsonObject.add(PackMetadataSectionSerializer.I[0x3 ^ 0x7], jsonSerializationContext.serialize((Object)packMetadataSection.getPackDescription()));
        return (JsonElement)jsonObject;
    }
    
    public String getSectionName() {
        return PackMetadataSectionSerializer.I[0x40 ^ 0x45];
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
