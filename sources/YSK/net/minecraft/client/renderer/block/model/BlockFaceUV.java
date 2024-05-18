package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class BlockFaceUV
{
    public final int rotation;
    public float[] uvs;
    private static final String[] I;
    
    public float func_178348_a(final int n) {
        if (this.uvs == null) {
            throw new NullPointerException(BlockFaceUV.I["".length()]);
        }
        final int func_178347_d = this.func_178347_d(n);
        float n2;
        if (func_178347_d != 0 && func_178347_d != " ".length()) {
            n2 = this.uvs["  ".length()];
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n2 = this.uvs["".length()];
        }
        return n2;
    }
    
    static {
        I();
    }
    
    public void setUvs(final float[] uvs) {
        if (this.uvs == null) {
            this.uvs = uvs;
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u00108\u0012", "eNasR");
        BlockFaceUV.I[" ".length()] = I(";#\u0012", "NUauZ");
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public float func_178346_b(final int n) {
        if (this.uvs == null) {
            throw new NullPointerException(BlockFaceUV.I[" ".length()]);
        }
        final int func_178347_d = this.func_178347_d(n);
        float n2;
        if (func_178347_d != 0 && func_178347_d != "   ".length()) {
            n2 = this.uvs["   ".length()];
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n2 = this.uvs[" ".length()];
        }
        return n2;
    }
    
    public BlockFaceUV(final float[] uvs, final int rotation) {
        this.uvs = uvs;
        this.rotation = rotation;
    }
    
    private int func_178347_d(final int n) {
        return (n + this.rotation / (0xED ^ 0xB7)) % (0x69 ^ 0x6D);
    }
    
    public int func_178345_c(final int n) {
        return (n + ((0x8C ^ 0x88) - this.rotation / (0x3E ^ 0x64))) % (0xC6 ^ 0xC2);
    }
    
    static class Deserializer implements JsonDeserializer<BlockFaceUV>
    {
        private static final String[] I;
        
        private static void I() {
            (I = new String[0x7A ^ 0x72])["".length()] = I("4%2\u000b\u001a/%(", "FJFjn");
            Deserializer.I[" ".length()] = I(" \">'\u0005\u0000(h4\u0006\u001d-</\u0006\u0007l", "iLHFi");
            Deserializer.I["  ".length()] = I("S3\u001e\u001b9\u0017yQ\u00019\u001f,Q^xJe^_oCzCYgS4\u001d\u00028\u00040\u0015", "sUqnW");
            Deserializer.I["   ".length()] = I("\"\"", "WTXEW");
            Deserializer.I[0x6E ^ 0x6A] = I(";\u001b", "NmhKi");
            Deserializer.I[0x69 ^ 0x6C] = I("1\u0013\u001b?\u0007\u0000\u000e\u000fzPT\u001e\u001dz\u0012\u0015\u0007\u001e?\u0017XK\r5\u0011\u001a\u000fQz", "tkkZd");
            Deserializer.I[0x8B ^ 0x8D] = I("\u0001;)", "tMrQW");
            Deserializer.I[0xAB ^ 0xAC] = I("\u000e", "SLfUM");
        }
        
        public BlockFaceUV deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new BlockFaceUV(this.parseUV(asJsonObject), this.parseRotation(asJsonObject));
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
                if (1 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        protected int parseRotation(final JsonObject jsonObject) {
            final int int1 = JsonUtils.getInt(jsonObject, Deserializer.I["".length()], "".length());
            if (int1 >= 0 && int1 % (0x38 ^ 0x62) == 0 && int1 / (0xCB ^ 0x91) <= "   ".length()) {
                return int1;
            }
            throw new JsonParseException(Deserializer.I[" ".length()] + int1 + Deserializer.I["  ".length()]);
        }
        
        static {
            I();
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private float[] parseUV(final JsonObject jsonObject) {
            if (!jsonObject.has(Deserializer.I["   ".length()])) {
                return null;
            }
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, Deserializer.I[0x8F ^ 0x8B]);
            if (jsonArray.size() != (0x81 ^ 0x85)) {
                throw new JsonParseException(Deserializer.I[0x9 ^ 0xC] + jsonArray.size());
            }
            final float[] array = new float[0x39 ^ 0x3D];
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < array.length) {
                array[i] = JsonUtils.getFloat(jsonArray.get(i), Deserializer.I[0x60 ^ 0x66] + i + Deserializer.I[0x5D ^ 0x5A]);
                ++i;
            }
            return array;
        }
    }
}
