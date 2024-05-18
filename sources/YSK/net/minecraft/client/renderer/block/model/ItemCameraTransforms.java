package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class ItemCameraTransforms
{
    public static float field_181693_e;
    public static float field_181692_d;
    public static final ItemCameraTransforms DEFAULT;
    public final ItemTransformVec3f ground;
    public final ItemTransformVec3f gui;
    public static float field_181695_g;
    public static float field_181696_h;
    public final ItemTransformVec3f fixed;
    public final ItemTransformVec3f head;
    public final ItemTransformVec3f thirdPerson;
    public static float field_181690_b;
    private static int[] $SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType;
    public final ItemTransformVec3f firstPerson;
    public static float field_181698_j;
    public static float field_181694_f;
    public static float field_181691_c;
    public static float field_181697_i;
    
    public ItemTransformVec3f getTransform(final TransformType transformType) {
        switch ($SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType()[transformType.ordinal()]) {
            case 2: {
                return this.thirdPerson;
            }
            case 3: {
                return this.firstPerson;
            }
            case 4: {
                return this.head;
            }
            case 5: {
                return this.gui;
            }
            case 6: {
                return this.ground;
            }
            case 7: {
                return this.fixed;
            }
            default: {
                return ItemTransformVec3f.DEFAULT;
            }
        }
    }
    
    static {
        DEFAULT = new ItemCameraTransforms();
        ItemCameraTransforms.field_181690_b = 0.0f;
        ItemCameraTransforms.field_181691_c = 0.0f;
        ItemCameraTransforms.field_181692_d = 0.0f;
        ItemCameraTransforms.field_181693_e = 0.0f;
        ItemCameraTransforms.field_181694_f = 0.0f;
        ItemCameraTransforms.field_181695_g = 0.0f;
        ItemCameraTransforms.field_181696_h = 0.0f;
        ItemCameraTransforms.field_181697_i = 0.0f;
        ItemCameraTransforms.field_181698_j = 0.0f;
    }
    
    public boolean func_181687_c(final TransformType transformType) {
        int n;
        if (this.getTransform(transformType).equals(ItemTransformVec3f.DEFAULT)) {
            n = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public ItemCameraTransforms(final ItemCameraTransforms itemCameraTransforms) {
        this.thirdPerson = itemCameraTransforms.thirdPerson;
        this.firstPerson = itemCameraTransforms.firstPerson;
        this.head = itemCameraTransforms.head;
        this.gui = itemCameraTransforms.gui;
        this.ground = itemCameraTransforms.ground;
        this.fixed = itemCameraTransforms.fixed;
    }
    
    public ItemCameraTransforms(final ItemTransformVec3f thirdPerson, final ItemTransformVec3f firstPerson, final ItemTransformVec3f head, final ItemTransformVec3f gui, final ItemTransformVec3f ground, final ItemTransformVec3f fixed) {
        this.thirdPerson = thirdPerson;
        this.firstPerson = firstPerson;
        this.head = head;
        this.gui = gui;
        this.ground = ground;
        this.fixed = fixed;
    }
    
    public void applyTransform(final TransformType transformType) {
        final ItemTransformVec3f transform = this.getTransform(transformType);
        if (transform != ItemTransformVec3f.DEFAULT) {
            GlStateManager.translate(transform.translation.x + ItemCameraTransforms.field_181690_b, transform.translation.y + ItemCameraTransforms.field_181691_c, transform.translation.z + ItemCameraTransforms.field_181692_d);
            GlStateManager.rotate(transform.rotation.y + ItemCameraTransforms.field_181694_f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(transform.rotation.x + ItemCameraTransforms.field_181693_e, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(transform.rotation.z + ItemCameraTransforms.field_181695_g, 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(transform.scale.x + ItemCameraTransforms.field_181696_h, transform.scale.y + ItemCameraTransforms.field_181697_i, transform.scale.z + ItemCameraTransforms.field_181698_j);
        }
    }
    
    private ItemCameraTransforms() {
        this(ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType() {
        final int[] $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType = ItemCameraTransforms.$SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType;
        if ($switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType != null) {
            return $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType;
        }
        final int[] $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2 = new int[TransformType.values().length];
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2[TransformType.FIRST_PERSON.ordinal()] = "   ".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2[TransformType.FIXED.ordinal()] = (0xB2 ^ 0xB5);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2[TransformType.GROUND.ordinal()] = (0x65 ^ 0x63);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2[TransformType.GUI.ordinal()] = (0x1A ^ 0x1F);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2[TransformType.HEAD.ordinal()] = (0x54 ^ 0x50);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2[TransformType.NONE.ordinal()] = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2[TransformType.THIRD_PERSON.ordinal()] = "  ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        return ItemCameraTransforms.$SWITCH_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType = $switch_TABLE$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType2;
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static class Deserializer implements JsonDeserializer<ItemCameraTransforms>
    {
        private static final String[] I;
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
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
                if (2 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private ItemTransformVec3f func_181683_a(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject, final String s) {
            ItemTransformVec3f default1;
            if (jsonObject.has(s)) {
                default1 = (ItemTransformVec3f)jsonDeserializationContext.deserialize(jsonObject.get(s), (Type)ItemTransformVec3f.class);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                default1 = ItemTransformVec3f.DEFAULT;
            }
            return default1;
        }
        
        private static void I() {
            (I = new String[0x1E ^ 0x18])["".length()] = I("?\u001c\"4';\u001195,%", "KtKFC");
            Deserializer.I[" ".length()] = I("(<*\u0004;>0*\u0004  ", "NUXwO");
            Deserializer.I["  ".length()] = I("\u0019\u0015'1", "qpFUW");
            Deserializer.I["   ".length()] = I("\u0001=\u000b", "fHbHH");
            Deserializer.I[0x3A ^ 0x3E] = I("\b\u0017\u0015\u0010\f\u000b", "oezeb");
            Deserializer.I[0x12 ^ 0x17] = I("2%\"\u0000\u000e", "TLZej");
        }
        
        public ItemCameraTransforms deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new ItemCameraTransforms(this.func_181683_a(jsonDeserializationContext, asJsonObject, Deserializer.I["".length()]), this.func_181683_a(jsonDeserializationContext, asJsonObject, Deserializer.I[" ".length()]), this.func_181683_a(jsonDeserializationContext, asJsonObject, Deserializer.I["  ".length()]), this.func_181683_a(jsonDeserializationContext, asJsonObject, Deserializer.I["   ".length()]), this.func_181683_a(jsonDeserializationContext, asJsonObject, Deserializer.I[0x6E ^ 0x6A]), this.func_181683_a(jsonDeserializationContext, asJsonObject, Deserializer.I[0x91 ^ 0x94]));
        }
    }
    
    public enum TransformType
    {
        GUI(TransformType.I[0x58 ^ 0x5C], 0xD ^ 0x9), 
        FIXED(TransformType.I[0x1B ^ 0x1D], 0x4B ^ 0x4D), 
        GROUND(TransformType.I[0x4A ^ 0x4F], 0x2C ^ 0x29), 
        HEAD(TransformType.I["   ".length()], "   ".length()), 
        FIRST_PERSON(TransformType.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
        THIRD_PERSON(TransformType.I[" ".length()], " ".length());
        
        private static final TransformType[] ENUM$VALUES;
        
        NONE(TransformType.I["".length()], "".length());
        
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
                if (1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private TransformType(final String s, final int n) {
        }
        
        private static void I() {
            (I = new String[0xAC ^ 0xAB])["".length()] = I("\t\u0004\r\u001c", "GKCYb");
            TransformType.I[" ".length()] = I("\u0017\u001f\u0007\u00064\u001c\u0007\u000b\u0006#\f\u0019", "CWNTp");
            TransformType.I["  ".length()] = I("\u001c\u001b\u001d\t\u0001\u0005\u0002\n\b\u0006\u0015\u001c", "ZROZU");
            TransformType.I["   ".length()] = I("\u0011\u0001.\u001c", "YDoXP");
            TransformType.I[0x96 ^ 0x92] = I(".=\u0011", "ihXbP");
            TransformType.I[0x27 ^ 0x22] = I("\u0010\n\u0015\u000f\u0007\u0013", "WXZZI");
            TransformType.I[0x73 ^ 0x75] = I(">\u0002/\u0014 ", "xKwQd");
        }
        
        static {
            I();
            final TransformType[] enum$VALUES = new TransformType[0x1 ^ 0x6];
            enum$VALUES["".length()] = TransformType.NONE;
            enum$VALUES[" ".length()] = TransformType.THIRD_PERSON;
            enum$VALUES["  ".length()] = TransformType.FIRST_PERSON;
            enum$VALUES["   ".length()] = TransformType.HEAD;
            enum$VALUES[0xB9 ^ 0xBD] = TransformType.GUI;
            enum$VALUES[0x1 ^ 0x4] = TransformType.GROUND;
            enum$VALUES[0xBA ^ 0xBC] = TransformType.FIXED;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
