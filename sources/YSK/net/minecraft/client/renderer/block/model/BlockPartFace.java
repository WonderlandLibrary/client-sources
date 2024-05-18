package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class BlockPartFace
{
    public final BlockFaceUV blockFaceUV;
    public final int tintIndex;
    public final EnumFacing cullFace;
    public final String texture;
    public static final EnumFacing FACING_DEFAULT;
    
    static {
        FACING_DEFAULT = null;
    }
    
    public BlockPartFace(final EnumFacing cullFace, final int tintIndex, final String texture, final BlockFaceUV blockFaceUV) {
        this.cullFace = cullFace;
        this.tintIndex = tintIndex;
        this.texture = texture;
        this.blockFaceUV = blockFaceUV;
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
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static class Deserializer implements JsonDeserializer<BlockPartFace>
    {
        private static final String[] I;
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private EnumFacing parseCullFace(final JsonObject jsonObject) {
            return EnumFacing.byName(JsonUtils.getString(jsonObject, Deserializer.I["  ".length()], Deserializer.I["   ".length()]));
        }
        
        static {
            I();
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public BlockPartFace deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new BlockPartFace(this.parseCullFace(asJsonObject), this.parseTintIndex(asJsonObject), this.parseTexture(asJsonObject), (BlockFaceUV)jsonDeserializationContext.deserialize((JsonElement)asJsonObject, (Type)BlockFaceUV.class));
        }
        
        protected int parseTintIndex(final JsonObject jsonObject) {
            return JsonUtils.getInt(jsonObject, Deserializer.I["".length()], -" ".length());
        }
        
        private String parseTexture(final JsonObject jsonObject) {
            return JsonUtils.getString(jsonObject, Deserializer.I[" ".length()]);
        }
        
        private static void I() {
            (I = new String[0x7E ^ 0x7A])["".length()] = I("\u001a*\u001e6+\u0000'\u0015:", "nCpBB");
            Deserializer.I[" ".length()] = I(">).<\u00038)", "JLVHv");
            Deserializer.I["  ".length()] = I("\u0000$\u0006)-\u00022\u000f", "cQjEK");
            Deserializer.I["   ".length()] = I("", "PmnTp");
        }
    }
}
