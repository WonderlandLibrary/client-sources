package net.minecraft.client.renderer.block.model;

import org.lwjgl.util.vector.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class ItemTransformVec3f
{
    public final Vector3f translation;
    public final Vector3f scale;
    public static final ItemTransformVec3f DEFAULT;
    public final Vector3f rotation;
    
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemTransformVec3f(final Vector3f vector3f, final Vector3f vector3f2, final Vector3f vector3f3) {
        this.rotation = new Vector3f((ReadableVector3f)vector3f);
        this.translation = new Vector3f((ReadableVector3f)vector3f2);
        this.scale = new Vector3f((ReadableVector3f)vector3f3);
    }
    
    static {
        DEFAULT = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    @Override
    public int hashCode() {
        return (0x53 ^ 0x4C) * ((0x13 ^ 0xC) * this.rotation.hashCode() + this.translation.hashCode()) + this.scale.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (this.getClass() != o.getClass()) {
            return "".length() != 0;
        }
        final ItemTransformVec3f itemTransformVec3f = (ItemTransformVec3f)o;
        int n;
        if (!this.rotation.equals((Object)itemTransformVec3f.rotation)) {
            n = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else if (!this.scale.equals((Object)itemTransformVec3f.scale)) {
            n = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n = (this.translation.equals((Object)itemTransformVec3f.translation) ? 1 : 0);
        }
        return n != 0;
    }
    
    static class Deserializer implements JsonDeserializer<ItemTransformVec3f>
    {
        private static final String[] I;
        private static final Vector3f TRANSLATION_DEFAULT;
        private static final Vector3f ROTATION_DEFAULT;
        private static final Vector3f SCALE_DEFAULT;
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        public ItemTransformVec3f deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Vector3f vector3f = this.parseVector3f(asJsonObject, Deserializer.I["".length()], Deserializer.ROTATION_DEFAULT);
            final Vector3f vector3f2 = this.parseVector3f(asJsonObject, Deserializer.I[" ".length()], Deserializer.TRANSLATION_DEFAULT);
            vector3f2.scale(0.0625f);
            vector3f2.x = MathHelper.clamp_float(vector3f2.x, -1.5f, 1.5f);
            vector3f2.y = MathHelper.clamp_float(vector3f2.y, -1.5f, 1.5f);
            vector3f2.z = MathHelper.clamp_float(vector3f2.z, -1.5f, 1.5f);
            final Vector3f vector3f3 = this.parseVector3f(asJsonObject, Deserializer.I["  ".length()], Deserializer.SCALE_DEFAULT);
            vector3f3.x = MathHelper.clamp_float(vector3f3.x, -4.0f, 4.0f);
            vector3f3.y = MathHelper.clamp_float(vector3f3.y, -4.0f, 4.0f);
            vector3f3.z = MathHelper.clamp_float(vector3f3.z, -4.0f, 4.0f);
            return new ItemTransformVec3f(vector3f, vector3f2, vector3f3);
        }
        
        static {
            I();
            ROTATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
            TRANSLATION_DEFAULT = new Vector3f(0.0f, 0.0f, 0.0f);
            SCALE_DEFAULT = new Vector3f(1.0f, 1.0f, 1.0f);
        }
        
        private Vector3f parseVector3f(final JsonObject jsonObject, final String s, final Vector3f vector3f) {
            if (!jsonObject.has(s)) {
                return vector3f;
            }
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, s);
            if (jsonArray.size() != "   ".length()) {
                throw new JsonParseException(Deserializer.I["   ".length()] + s + Deserializer.I[0x20 ^ 0x24] + jsonArray.size());
            }
            final float[] array = new float["   ".length()];
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < array.length) {
                array[i] = JsonUtils.getFloat(jsonArray.get(i), String.valueOf(s) + Deserializer.I[0x78 ^ 0x7D] + i + Deserializer.I[0x6B ^ 0x6D]);
                ++i;
            }
            return new Vector3f(array["".length()], array[" ".length()], array["  ".length()]);
        }
        
        private static void I() {
            (I = new String[0xBE ^ 0xB9])["".length()] = I("+.\u0016\u000b50.\f", "YAbjA");
            Deserializer.I[" ".length()] = I("!\u0014\u0014#:9\u0007\u0001$&;", "UfuMI");
            Deserializer.I["  ".length()] = I("4\f;\u001b\u0017", "GoZwr");
            Deserializer.I["   ".length()] = I("-\u0016\u001e$\n\u001c\u000b\naZH", "hnnAi");
            Deserializer.I[0x81 ^ 0x85] = I("o\u0004\u000f$:*\u0001Bh) \u0007\u0000,uo", "OrnHO");
            Deserializer.I[0x3 ^ 0x6] = I("6", "mXqvE");
            Deserializer.I[0x0 ^ 0x6] = I("5", "hYWaW");
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
                if (1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
