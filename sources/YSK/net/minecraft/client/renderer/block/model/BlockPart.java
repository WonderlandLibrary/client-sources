package net.minecraft.client.renderer.block.model;

import org.lwjgl.util.vector.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockPart
{
    public final Vector3f positionTo;
    public final boolean shade;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public final Map<EnumFacing, BlockPartFace> mapFaces;
    public final BlockPartRotation partRotation;
    public final Vector3f positionFrom;
    
    private void setDefaultUvs() {
        final Iterator<Map.Entry<EnumFacing, BlockPartFace>> iterator = this.mapFaces.entrySet().iterator();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<EnumFacing, BlockPartFace> entry = iterator.next();
            entry.getValue().blockFaceUV.setUvs(this.getFaceUvs(entry.getKey()));
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockPart.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x21 ^ 0x27);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x39 ^ 0x3D);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x58 ^ 0x5D);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockPart.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
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
    
    public BlockPart(final Vector3f positionFrom, final Vector3f positionTo, final Map<EnumFacing, BlockPartFace> mapFaces, final BlockPartRotation partRotation, final boolean shade) {
        this.positionFrom = positionFrom;
        this.positionTo = positionTo;
        this.mapFaces = mapFaces;
        this.partRotation = partRotation;
        this.shade = shade;
        this.setDefaultUvs();
    }
    
    private float[] getFaceUvs(final EnumFacing enumFacing) {
        float[] array2 = null;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 1:
            case 2: {
                final float[] array = new float[0x9C ^ 0x98];
                array["".length()] = this.positionFrom.x;
                array[" ".length()] = this.positionFrom.z;
                array["  ".length()] = this.positionTo.x;
                array["   ".length()] = this.positionTo.z;
                array2 = array;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 3:
            case 4: {
                final float[] array3 = new float[0x8C ^ 0x88];
                array3["".length()] = this.positionFrom.x;
                array3[" ".length()] = 16.0f - this.positionTo.y;
                array3["  ".length()] = this.positionTo.x;
                array3["   ".length()] = 16.0f - this.positionFrom.y;
                array2 = array3;
                "".length();
                if (0 == -1) {
                    throw null;
                }
                break;
            }
            case 5:
            case 6: {
                final float[] array4 = new float[0x88 ^ 0x8C];
                array4["".length()] = this.positionFrom.z;
                array4[" ".length()] = 16.0f - this.positionTo.y;
                array4["  ".length()] = this.positionTo.z;
                array4["   ".length()] = 16.0f - this.positionFrom.y;
                array2 = array4;
                "".length();
                if (3 < 0) {
                    throw null;
                }
                break;
            }
            default: {
                throw new NullPointerException();
            }
        }
        return array2;
    }
    
    static class Deserializer implements JsonDeserializer<BlockPart>
    {
        private static final String[] I;
        
        private EnumFacing parseEnumFacing(final String s) {
            final EnumFacing byName = EnumFacing.byName(s);
            if (byName == null) {
                throw new JsonParseException(Deserializer.I[0x31 ^ 0x3E] + s);
            }
            return byName;
        }
        
        private Vector3f parsePosition(final JsonObject jsonObject, final String s) {
            final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, s);
            if (jsonArray.size() != "   ".length()) {
                throw new JsonParseException(Deserializer.I[0xB4 ^ 0xA0] + s + Deserializer.I[0x74 ^ 0x61] + jsonArray.size());
            }
            final float[] array = new float["   ".length()];
            int i = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (i < array.length) {
                array[i] = JsonUtils.getFloat(jsonArray.get(i), String.valueOf(s) + Deserializer.I[0x71 ^ 0x67] + i + Deserializer.I[0x23 ^ 0x34]);
                ++i;
            }
            return new Vector3f(array["".length()], array[" ".length()], array["  ".length()]);
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
                if (0 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private Vector3f parsePositionFrom(final JsonObject jsonObject) {
            final Vector3f position = this.parsePosition(jsonObject, Deserializer.I[0x1 ^ 0x13]);
            if (position.x >= -16.0f && position.y >= -16.0f && position.z >= -16.0f && position.x <= 32.0f && position.y <= 32.0f && position.z <= 32.0f) {
                return position;
            }
            throw new JsonParseException(Deserializer.I[0x52 ^ 0x41] + position);
        }
        
        private Vector3f parsePositionTo(final JsonObject jsonObject) {
            final Vector3f position = this.parsePosition(jsonObject, Deserializer.I[0x62 ^ 0x72]);
            if (position.x >= -16.0f && position.y >= -16.0f && position.z >= -16.0f && position.x <= 32.0f && position.y <= 32.0f && position.z <= 32.0f) {
                return position;
            }
            throw new JsonParseException(Deserializer.I[0x7 ^ 0x16] + position);
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private BlockPartRotation parseRotation(final JsonObject jsonObject) {
            BlockPartRotation blockPartRotation = null;
            if (jsonObject.has(Deserializer.I[0x17 ^ 0x13])) {
                final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonObject, Deserializer.I[0x38 ^ 0x3D]);
                final Vector3f position = this.parsePosition(jsonObject2, Deserializer.I[0xA8 ^ 0xAE]);
                position.scale(0.0625f);
                blockPartRotation = new BlockPartRotation(position, this.parseAxis(jsonObject2), this.parseAngle(jsonObject2), JsonUtils.getBoolean(jsonObject2, Deserializer.I[0x48 ^ 0x4F], "".length() != 0));
            }
            return blockPartRotation;
        }
        
        static {
            I();
        }
        
        private EnumFacing.Axis parseAxis(final JsonObject jsonObject) {
            final String string = JsonUtils.getString(jsonObject, Deserializer.I[0x69 ^ 0x62]);
            final EnumFacing.Axis byName = EnumFacing.Axis.byName(string.toLowerCase());
            if (byName == null) {
                throw new JsonParseException(Deserializer.I[0xC9 ^ 0xC5] + string);
            }
            return byName;
        }
        
        private Map<EnumFacing, BlockPartFace> parseFaces(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final EnumMap enumMap = Maps.newEnumMap((Class)EnumFacing.class);
            final Iterator<Map.Entry<String, V>> iterator = JsonUtils.getJsonObject(jsonObject, Deserializer.I[0x14 ^ 0x1A]).entrySet().iterator();
            "".length();
            if (2 < 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Map.Entry<String, V> entry = iterator.next();
                enumMap.put(this.parseEnumFacing(entry.getKey()), (BlockPartFace)jsonDeserializationContext.deserialize((JsonElement)entry.getValue(), (Type)BlockPartFace.class));
            }
            return (Map<EnumFacing, BlockPartFace>)enumMap;
        }
        
        private static void I() {
            (I = new String[0x25 ^ 0x3D])["".length()] = I("8?27=", "KWSSX");
            Deserializer.I[" ".length()] = I("\u001c-'-?", "oEFIZ");
            Deserializer.I["  ".length()] = I("\u001c38\u001f\u0015-.,Z\u00051*,\u001fV-$h\u0018\u0013y*h8\u00196'-\u001b\u0018", "YKHzv");
            Deserializer.I["   ".length()] = I("\u0016\u0000; !", "ehZDD");
            Deserializer.I[0x76 ^ 0x72] = I("'\u000e\u0007,\u0006<\u000e\u001d", "UasMr");
            Deserializer.I[0x70 ^ 0x75] = I("\u0006*:\f!\u001d* ", "tENmU");
            Deserializer.I[0xB ^ 0xD] = I("\u0003*\u0005\n\u0006\u0002", "lXlmo");
            Deserializer.I[0x65 ^ 0x62] = I("'\u0002'\u0005/9\u0002", "UgTfN");
            Deserializer.I[0xAA ^ 0xA2] = I(";!\u0012\u001c2", "ZOupW");
            Deserializer.I[0xB6 ^ 0xBF] = I("\r6\f\u00195-<Z\n609\u000e\u00116*x", "DXzxY");
            Deserializer.I[0xB2 ^ 0xB8] = I("w<\n\u0014\u001e3vE\u000e\u001e;#ELDbuHSByoJQ_ehKT_coE\u0000\u001c;5\u0012\u0004\u0014", "WZeap");
            Deserializer.I[0x8A ^ 0x81] = I("\u001b\u001b/)", "zcFZv");
            Deserializer.I[0xA7 ^ 0xAB] = I(":7 \u000f\u0006\u001a=v\u001c\u0005\u00078\"\u0007\u0005\u001dy7\u0016\u0003\u0000cv", "sYVnj");
            Deserializer.I[0x7A ^ 0x77] = I(".\u0013\u0019\u0013%\u001f\u000e\rV$\u000e\u001f\u001e\u0013#\u0005KXV'\u0005\u000fI@f\u001e\u0005\u0000\u00073\u000eK\u000f\u0017%\u000e\u0018EV!\u0004\u001fIF", "kkivF");
            Deserializer.I[0x2C ^ 0x22] = I("2(\n&>", "TIiCM");
            Deserializer.I[0x27 ^ 0x28] = I("\u001c\r%(\u001c>\rn \u0012*\n !Ii", "IcNFs");
            Deserializer.I[0x87 ^ 0x97] = I("7\r", "CbkDY");
            Deserializer.I[0xB9 ^ 0xA8] = I("A\u001c\foE\u0015\u0018\u0006+\f\u0000\u0001\u0006:E\u0003\u0010\u0000-\u0000\u0002\u001bC<\r\u0003H\u0002$\t\t\u001f\u0006,E\u0004\u0007\u0016&\u0001\u0007\u001a\n-\u0016\\H", "fhcHe");
            Deserializer.I[0x18 ^ 0xA] = I("\u00057+\b", "cEDei");
            Deserializer.I[0xA0 ^ 0xB3] = I("N\n;(\u0018NL:7\u0010\n\u0005/.\u0010\u001bL,?\u0016\f\t-4U\u001d\u0004,g\u0014\u0005\u0000&0\u0010\rL+(\u0000\u0007\b(5\u001c\f\u001fsg", "ilIGu");
            Deserializer.I[0x58 ^ 0x4C] = I("\u000b\b \u0007\u000f:\u00154B_n", "NpPbl");
            Deserializer.I[0xA2 ^ 0xB7] = I("D\u001d%\b\u001a\u0001\u0018hD\t\u000b\u001e*\u0000UD", "dkDdo");
            Deserializer.I[0xB ^ 0x1D] = I("\u0015", "NtKBL");
            Deserializer.I[0x9 ^ 0x1E] = I("3", "nqzTK");
        }
        
        private Map<EnumFacing, BlockPartFace> parseFacesCheck(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final Map<EnumFacing, BlockPartFace> faces = this.parseFaces(jsonDeserializationContext, jsonObject);
            if (faces.isEmpty()) {
                throw new JsonParseException(Deserializer.I[0x1 ^ 0xC]);
            }
            return faces;
        }
        
        private float parseAngle(final JsonObject jsonObject) {
            final float float1 = JsonUtils.getFloat(jsonObject, Deserializer.I[0x38 ^ 0x30]);
            if (float1 != 0.0f && MathHelper.abs(float1) != 22.5f && MathHelper.abs(float1) != 45.0f) {
                throw new JsonParseException(Deserializer.I[0x9A ^ 0x93] + float1 + Deserializer.I[0x56 ^ 0x5C]);
            }
            return float1;
        }
        
        public BlockPart deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Vector3f position = this.parsePositionFrom(asJsonObject);
            final Vector3f positionTo = this.parsePositionTo(asJsonObject);
            final BlockPartRotation rotation = this.parseRotation(asJsonObject);
            final Map<EnumFacing, BlockPartFace> facesCheck = this.parseFacesCheck(jsonDeserializationContext, asJsonObject);
            if (asJsonObject.has(Deserializer.I["".length()]) && !JsonUtils.isBoolean(asJsonObject, Deserializer.I[" ".length()])) {
                throw new JsonParseException(Deserializer.I["  ".length()]);
            }
            return new BlockPart(position, positionTo, facesCheck, rotation, JsonUtils.getBoolean(asJsonObject, Deserializer.I["   ".length()], " ".length() != 0));
        }
    }
}
