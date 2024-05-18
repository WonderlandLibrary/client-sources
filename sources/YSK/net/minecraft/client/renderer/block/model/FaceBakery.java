package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import org.lwjgl.util.vector.*;

public class FaceBakery
{
    private static final float field_178418_a;
    private static final String[] I;
    private static final float field_178417_b;
    private static final String __OBFID;
    
    public void func_178409_a(final int[] array, final EnumFacing enumFacing, final BlockFaceUV blockFaceUV, final TextureAtlasSprite textureAtlasSprite) {
        int i = "".length();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (i < (0xA5 ^ 0xA1)) {
            this.func_178401_a(i, array, enumFacing, blockFaceUV, textureAtlasSprite);
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000f<3vW|@^r^|", "LplFg");
    }
    
    private float[] getPositionsDiv16(final Vector3f vector3f, final Vector3f vector3f2) {
        final float[] array = new float[EnumFacing.values().length];
        array[EnumFaceDirection.Constants.WEST_INDEX] = vector3f.x / 16.0f;
        array[EnumFaceDirection.Constants.DOWN_INDEX] = vector3f.y / 16.0f;
        array[EnumFaceDirection.Constants.NORTH_INDEX] = vector3f.z / 16.0f;
        array[EnumFaceDirection.Constants.EAST_INDEX] = vector3f2.x / 16.0f;
        array[EnumFaceDirection.Constants.UP_INDEX] = vector3f2.y / 16.0f;
        array[EnumFaceDirection.Constants.SOUTH_INDEX] = vector3f2.z / 16.0f;
        return array;
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
    
    private void storeVertexData(final int[] array, final int n, final int n2, final Vector3f vector3f, final int n3, final TextureAtlasSprite textureAtlasSprite, final BlockFaceUV blockFaceUV) {
        final int n4 = n * (0xE ^ 0x9);
        array[n4] = Float.floatToRawIntBits(vector3f.x);
        array[n4 + " ".length()] = Float.floatToRawIntBits(vector3f.y);
        array[n4 + "  ".length()] = Float.floatToRawIntBits(vector3f.z);
        array[n4 + "   ".length()] = n3;
        array[n4 + (0x15 ^ 0x11)] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedU(blockFaceUV.func_178348_a(n2)));
        array[n4 + (0x94 ^ 0x90) + " ".length()] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedV(blockFaceUV.func_178346_b(n2)));
    }
    
    static {
        I();
        __OBFID = FaceBakery.I["".length()];
        field_178418_a = 1.0f / (float)Math.cos(0.39269909262657166) - 1.0f;
        field_178417_b = 1.0f / (float)Math.cos(0.7853981633974483) - 1.0f;
    }
    
    private void func_178407_a(final Vector3f vector3f, final BlockPartRotation blockPartRotation) {
        if (blockPartRotation != null) {
            final Matrix4f matrixIdentity = this.getMatrixIdentity();
            final Vector3f vector3f2 = new Vector3f(0.0f, 0.0f, 0.0f);
            switch (FaceBakery$1.field_178399_b[blockPartRotation.axis.ordinal()]) {
                case 1: {
                    Matrix4f.rotate(blockPartRotation.angle * 0.017453292f, new Vector3f(1.0f, 0.0f, 0.0f), matrixIdentity, matrixIdentity);
                    vector3f2.set(0.0f, 1.0f, 1.0f);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    Matrix4f.rotate(blockPartRotation.angle * 0.017453292f, new Vector3f(0.0f, 1.0f, 0.0f), matrixIdentity, matrixIdentity);
                    vector3f2.set(1.0f, 0.0f, 1.0f);
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    Matrix4f.rotate(blockPartRotation.angle * 0.017453292f, new Vector3f(0.0f, 0.0f, 1.0f), matrixIdentity, matrixIdentity);
                    vector3f2.set(1.0f, 1.0f, 0.0f);
                    break;
                }
            }
            if (blockPartRotation.rescale) {
                if (Math.abs(blockPartRotation.angle) == 22.5f) {
                    vector3f2.scale(FaceBakery.field_178418_a);
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else {
                    vector3f2.scale(FaceBakery.field_178417_b);
                }
                Vector3f.add(vector3f2, new Vector3f(1.0f, 1.0f, 1.0f), vector3f2);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                vector3f2.set(1.0f, 1.0f, 1.0f);
            }
            this.rotateScale(vector3f, new Vector3f((ReadableVector3f)blockPartRotation.origin), matrixIdentity, vector3f2);
        }
    }
    
    private void func_178408_a(final int[] array, final EnumFacing enumFacing) {
        final int[] array2 = new int[array.length];
        System.arraycopy(array, "".length(), array2, "".length(), array.length);
        final float[] array3 = new float[EnumFacing.values().length];
        array3[EnumFaceDirection.Constants.WEST_INDEX] = 999.0f;
        array3[EnumFaceDirection.Constants.DOWN_INDEX] = 999.0f;
        array3[EnumFaceDirection.Constants.NORTH_INDEX] = 999.0f;
        array3[EnumFaceDirection.Constants.EAST_INDEX] = -999.0f;
        array3[EnumFaceDirection.Constants.UP_INDEX] = -999.0f;
        array3[EnumFaceDirection.Constants.SOUTH_INDEX] = -999.0f;
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < (0xBF ^ 0xBB)) {
            final int n = (0x5A ^ 0x5D) * i;
            final float intBitsToFloat = Float.intBitsToFloat(array2[n]);
            final float intBitsToFloat2 = Float.intBitsToFloat(array2[n + " ".length()]);
            final float intBitsToFloat3 = Float.intBitsToFloat(array2[n + "  ".length()]);
            if (intBitsToFloat < array3[EnumFaceDirection.Constants.WEST_INDEX]) {
                array3[EnumFaceDirection.Constants.WEST_INDEX] = intBitsToFloat;
            }
            if (intBitsToFloat2 < array3[EnumFaceDirection.Constants.DOWN_INDEX]) {
                array3[EnumFaceDirection.Constants.DOWN_INDEX] = intBitsToFloat2;
            }
            if (intBitsToFloat3 < array3[EnumFaceDirection.Constants.NORTH_INDEX]) {
                array3[EnumFaceDirection.Constants.NORTH_INDEX] = intBitsToFloat3;
            }
            if (intBitsToFloat > array3[EnumFaceDirection.Constants.EAST_INDEX]) {
                array3[EnumFaceDirection.Constants.EAST_INDEX] = intBitsToFloat;
            }
            if (intBitsToFloat2 > array3[EnumFaceDirection.Constants.UP_INDEX]) {
                array3[EnumFaceDirection.Constants.UP_INDEX] = intBitsToFloat2;
            }
            if (intBitsToFloat3 > array3[EnumFaceDirection.Constants.SOUTH_INDEX]) {
                array3[EnumFaceDirection.Constants.SOUTH_INDEX] = intBitsToFloat3;
            }
            ++i;
        }
        final EnumFaceDirection facing = EnumFaceDirection.getFacing(enumFacing);
        int j = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (j < (0x34 ^ 0x30)) {
            final int n2 = (0xF ^ 0x8) * j;
            final EnumFaceDirection.VertexInformation func_179025_a = facing.func_179025_a(j);
            final float n3 = array3[func_179025_a.field_179184_a];
            final float n4 = array3[func_179025_a.field_179182_b];
            final float n5 = array3[func_179025_a.field_179183_c];
            array[n2] = Float.floatToRawIntBits(n3);
            array[n2 + " ".length()] = Float.floatToRawIntBits(n4);
            array[n2 + "  ".length()] = Float.floatToRawIntBits(n5);
            int k = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
            while (k < (0x18 ^ 0x1C)) {
                final int n6 = (0x1A ^ 0x1D) * k;
                final float intBitsToFloat4 = Float.intBitsToFloat(array2[n6]);
                final float intBitsToFloat5 = Float.intBitsToFloat(array2[n6 + " ".length()]);
                final float intBitsToFloat6 = Float.intBitsToFloat(array2[n6 + "  ".length()]);
                if (MathHelper.epsilonEquals(n3, intBitsToFloat4) && MathHelper.epsilonEquals(n4, intBitsToFloat5) && MathHelper.epsilonEquals(n5, intBitsToFloat6)) {
                    array[n2 + (0x68 ^ 0x6C)] = array2[n6 + (0x53 ^ 0x57)];
                    array[n2 + (0x9B ^ 0x9F) + " ".length()] = array2[n6 + (0xB7 ^ 0xB3) + " ".length()];
                }
                ++k;
            }
            ++j;
        }
    }
    
    private void func_178401_a(final int n, final int[] array, final EnumFacing enumFacing, final BlockFaceUV blockFaceUV, final TextureAtlasSprite textureAtlasSprite) {
        final int n2 = (0x67 ^ 0x60) * n;
        float intBitsToFloat = Float.intBitsToFloat(array[n2]);
        float intBitsToFloat2 = Float.intBitsToFloat(array[n2 + " ".length()]);
        float intBitsToFloat3 = Float.intBitsToFloat(array[n2 + "  ".length()]);
        if (intBitsToFloat < -0.1f || intBitsToFloat >= 1.1f) {
            intBitsToFloat -= MathHelper.floor_float(intBitsToFloat);
        }
        if (intBitsToFloat2 < -0.1f || intBitsToFloat2 >= 1.1f) {
            intBitsToFloat2 -= MathHelper.floor_float(intBitsToFloat2);
        }
        if (intBitsToFloat3 < -0.1f || intBitsToFloat3 >= 1.1f) {
            intBitsToFloat3 -= MathHelper.floor_float(intBitsToFloat3);
        }
        float n3 = 0.0f;
        float n4 = 0.0f;
        switch (FaceBakery$1.field_178400_a[enumFacing.ordinal()]) {
            case 1: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat3) * 16.0f;
                "".length();
                if (2 >= 3) {
                    throw null;
                }
                break;
            }
            case 2: {
                n3 = intBitsToFloat * 16.0f;
                n4 = intBitsToFloat3 * 16.0f;
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                break;
            }
            case 3: {
                n3 = (1.0f - intBitsToFloat) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                break;
            }
            case 4: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 5: {
                n3 = intBitsToFloat3 * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 6: {
                n3 = (1.0f - intBitsToFloat3) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
        }
        final int n5 = blockFaceUV.func_178345_c(n) * (0x11 ^ 0x16);
        array[n5 + (0xB5 ^ 0xB1)] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedU(n3));
        array[n5 + (0xB3 ^ 0xB7) + " ".length()] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedV(n4));
    }
    
    private void fillVertexData(final int[] array, final int n, final EnumFacing enumFacing, final BlockPartFace blockPartFace, final float[] array2, final TextureAtlasSprite textureAtlasSprite, final ModelRotation modelRotation, final BlockPartRotation blockPartRotation, final boolean b, final boolean b2) {
        final EnumFacing rotateFace = modelRotation.rotateFace(enumFacing);
        int faceShadeColor;
        if (b2) {
            faceShadeColor = this.getFaceShadeColor(rotateFace);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            faceShadeColor = -" ".length();
        }
        final int n2 = faceShadeColor;
        final EnumFaceDirection.VertexInformation func_179025_a = EnumFaceDirection.getFacing(enumFacing).func_179025_a(n);
        final Vector3f vector3f = new Vector3f(array2[func_179025_a.field_179184_a], array2[func_179025_a.field_179182_b], array2[func_179025_a.field_179183_c]);
        this.func_178407_a(vector3f, blockPartRotation);
        this.storeVertexData(array, this.rotateVertex(vector3f, enumFacing, n, modelRotation, b), n, vector3f, n2, textureAtlasSprite, blockPartFace.blockFaceUV);
    }
    
    public static EnumFacing getFacingFromVertexData(final int[] array) {
        final Vector3f vector3f = new Vector3f(Float.intBitsToFloat(array["".length()]), Float.intBitsToFloat(array[" ".length()]), Float.intBitsToFloat(array["  ".length()]));
        final Vector3f vector3f2 = new Vector3f(Float.intBitsToFloat(array[0x18 ^ 0x1F]), Float.intBitsToFloat(array[0x1A ^ 0x12]), Float.intBitsToFloat(array[0x43 ^ 0x4A]));
        final Vector3f vector3f3 = new Vector3f(Float.intBitsToFloat(array[0x3E ^ 0x30]), Float.intBitsToFloat(array[0x80 ^ 0x8F]), Float.intBitsToFloat(array[0x73 ^ 0x63]));
        final Vector3f vector3f4 = new Vector3f();
        final Vector3f vector3f5 = new Vector3f();
        final Vector3f vector3f6 = new Vector3f();
        Vector3f.sub(vector3f, vector3f2, vector3f4);
        Vector3f.sub(vector3f3, vector3f2, vector3f5);
        Vector3f.cross(vector3f5, vector3f4, vector3f6);
        final float n = (float)Math.sqrt(vector3f6.x * vector3f6.x + vector3f6.y * vector3f6.y + vector3f6.z * vector3f6.z);
        final Vector3f vector3f7 = vector3f6;
        vector3f7.x /= n;
        final Vector3f vector3f8 = vector3f6;
        vector3f8.y /= n;
        final Vector3f vector3f9 = vector3f6;
        vector3f9.z /= n;
        EnumFacing enumFacing = null;
        float n2 = 0.0f;
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing2 = values[i];
            final Vec3i directionVec = enumFacing2.getDirectionVec();
            final float dot = Vector3f.dot(vector3f6, new Vector3f((float)directionVec.getX(), (float)directionVec.getY(), (float)directionVec.getZ()));
            if (dot >= 0.0f && dot > n2) {
                n2 = dot;
                enumFacing = enumFacing2;
            }
            ++i;
        }
        if (n2 < 0.719f) {
            if (enumFacing != EnumFacing.EAST && enumFacing != EnumFacing.WEST && enumFacing != EnumFacing.NORTH && enumFacing != EnumFacing.SOUTH) {
                enumFacing = EnumFacing.UP;
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                enumFacing = EnumFacing.NORTH;
            }
        }
        EnumFacing up;
        if (enumFacing == null) {
            up = EnumFacing.UP;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            up = enumFacing;
        }
        return up;
    }
    
    private float getFaceBrightness(final EnumFacing enumFacing) {
        switch (FaceBakery$1.field_178400_a[enumFacing.ordinal()]) {
            case 1: {
                return 0.5f;
            }
            case 2: {
                return 1.0f;
            }
            case 3:
            case 4: {
                return 0.8f;
            }
            case 5:
            case 6: {
                return 0.6f;
            }
            default: {
                return 1.0f;
            }
        }
    }
    
    private Matrix4f getMatrixIdentity() {
        final Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        return matrix4f;
    }
    
    private int getFaceShadeColor(final EnumFacing enumFacing) {
        final int clamp_int = MathHelper.clamp_int((int)(this.getFaceBrightness(enumFacing) * 255.0f), "".length(), 3 + 56 + 111 + 85);
        return -(6072693 + 790017 - 1602864 + 11517370) | clamp_int << (0xAF ^ 0xBF) | clamp_int << (0x2E ^ 0x26) | clamp_int;
    }
    
    public int rotateVertex(final Vector3f vector3f, final EnumFacing enumFacing, final int n, final ModelRotation modelRotation, final boolean b) {
        if (modelRotation == ModelRotation.X0_Y0) {
            return n;
        }
        this.rotateScale(vector3f, new Vector3f(0.5f, 0.5f, 0.5f), modelRotation.getMatrix4d(), new Vector3f(1.0f, 1.0f, 1.0f));
        return modelRotation.rotateVertex(enumFacing, n);
    }
    
    private int[] makeQuadVertexData(final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final float[] array, final ModelRotation modelRotation, final BlockPartRotation blockPartRotation, final boolean b, final boolean b2) {
        final int[] array2 = new int[0xBC ^ 0xA0];
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < (0xF ^ 0xB)) {
            this.fillVertexData(array2, i, enumFacing, blockPartFace, array, textureAtlasSprite, modelRotation, blockPartRotation, b, b2);
            ++i;
        }
        return array2;
    }
    
    private void rotateScale(final Vector3f vector3f, final Vector3f vector3f2, final Matrix4f matrix4f, final Vector3f vector3f3) {
        final Vector4f vector4f = new Vector4f(vector3f.x - vector3f2.x, vector3f.y - vector3f2.y, vector3f.z - vector3f2.z, 1.0f);
        Matrix4f.transform(matrix4f, vector4f, vector4f);
        final Vector4f vector4f2 = vector4f;
        vector4f2.x *= vector3f3.x;
        final Vector4f vector4f3 = vector4f;
        vector4f3.y *= vector3f3.y;
        final Vector4f vector4f4 = vector4f;
        vector4f4.z *= vector3f3.z;
        vector3f.set(vector4f.x + vector3f2.x, vector4f.y + vector3f2.y, vector4f.z + vector3f2.z);
    }
    
    public BakedQuad makeBakedQuad(final Vector3f vector3f, final Vector3f vector3f2, final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ModelRotation modelRotation, final BlockPartRotation blockPartRotation, final boolean b, final boolean b2) {
        final int[] quadVertexData = this.makeQuadVertexData(blockPartFace, textureAtlasSprite, enumFacing, this.getPositionsDiv16(vector3f, vector3f2), modelRotation, blockPartRotation, b, b2);
        final EnumFacing facingFromVertexData = getFacingFromVertexData(quadVertexData);
        if (b) {
            this.func_178409_a(quadVertexData, facingFromVertexData, blockPartFace.blockFaceUV, textureAtlasSprite);
        }
        if (blockPartRotation == null) {
            this.func_178408_a(quadVertexData, facingFromVertexData);
        }
        return new BakedQuad(quadVertexData, blockPartFace.tintIndex, facingFromVertexData, textureAtlasSprite);
    }
    
    static final class FaceBakery$1
    {
        static final int[] field_178399_b;
        private static final String[] I;
        private static final String __OBFID;
        static final int[] field_178400_a;
        
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
                if (2 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("-\u00071rd^{\\vlW", "nKnBT");
        }
        
        static {
            I();
            __OBFID = FaceBakery$1.I["".length()];
            field_178399_b = new int[EnumFacing.Axis.values().length];
            try {
                FaceBakery$1.field_178399_b[EnumFacing.Axis.X.ordinal()] = " ".length();
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                FaceBakery$1.field_178399_b[EnumFacing.Axis.Y.ordinal()] = "  ".length();
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                FaceBakery$1.field_178399_b[EnumFacing.Axis.Z.ordinal()] = "   ".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            field_178400_a = new int[EnumFacing.values().length];
            try {
                FaceBakery$1.field_178400_a[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.SOUTH.ordinal()] = (0xBC ^ 0xB8);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.WEST.ordinal()] = (0x88 ^ 0x8D);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                FaceBakery$1.field_178400_a[EnumFacing.EAST.ordinal()] = (0x33 ^ 0x35);
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
        }
    }
}
