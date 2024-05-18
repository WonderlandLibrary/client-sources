package net.minecraft.client.resources.model;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.lwjgl.util.vector.*;

public enum ModelRotation
{
    X180_Y0(ModelRotation.I[0x8D ^ 0x85], 0x67 ^ 0x6F, 173 + 107 - 232 + 132, "".length()), 
    X180_Y270(ModelRotation.I[0xAF ^ 0xA4], 0x8 ^ 0x3, 71 + 32 - 102 + 179, 164 + 220 - 262 + 148);
    
    private final int combinedXY;
    
    X270_Y90(ModelRotation.I[0x13 ^ 0x1E], 0x83 ^ 0x8E, 68 + 113 + 7 + 82, 0x49 ^ 0x13), 
    X180_Y180(ModelRotation.I[0x5B ^ 0x51], 0x2 ^ 0x8, 161 + 68 - 54 + 5, 55 + 27 + 1 + 97), 
    X90_Y180(ModelRotation.I[0x8A ^ 0x8C], 0x5C ^ 0x5A, 0x75 ^ 0x2F, 138 + 126 - 89 + 5);
    
    private static final Map<Integer, ModelRotation> mapRotations;
    
    X270_Y180(ModelRotation.I[0x78 ^ 0x76], 0x77 ^ 0x79, 48 + 165 + 16 + 41, 98 + 162 - 117 + 37), 
    X270_Y0(ModelRotation.I[0xBA ^ 0xB6], 0xCD ^ 0xC1, 58 + 17 + 131 + 64, "".length());
    
    private final int quartersX;
    
    X180_Y90(ModelRotation.I[0xE ^ 0x7], 0x6D ^ 0x64, 162 + 106 - 103 + 15, 0x78 ^ 0x22), 
    X0_Y0(ModelRotation.I["".length()], "".length(), "".length(), "".length()), 
    X0_Y270(ModelRotation.I["   ".length()], "   ".length(), "".length(), 114 + 107 - 116 + 165), 
    X0_Y180(ModelRotation.I["  ".length()], "  ".length(), "".length(), 142 + 13 - 45 + 70), 
    X90_Y270(ModelRotation.I[0xAD ^ 0xAA], 0x4F ^ 0x48, 0x1B ^ 0x41, 160 + 111 - 262 + 261), 
    X90_Y0(ModelRotation.I[0x7 ^ 0x3], 0xBE ^ 0xBA, 0x2E ^ 0x74, "".length());
    
    private static final String[] I;
    private final int quartersY;
    private static final ModelRotation[] ENUM$VALUES;
    
    X0_Y90(ModelRotation.I[" ".length()], " ".length(), "".length(), 0xF0 ^ 0xAA), 
    X90_Y90(ModelRotation.I[0x66 ^ 0x63], 0xBE ^ 0xBB, 0x4 ^ 0x5E, 0x16 ^ 0x4C), 
    X270_Y270(ModelRotation.I[0x69 ^ 0x66], 0x2C ^ 0x23, 212 + 209 - 344 + 193, 183 + 142 - 63 + 8);
    
    private final Matrix4f matrix4d;
    
    static {
        I();
        final ModelRotation[] enum$VALUES = new ModelRotation[0x3C ^ 0x2C];
        enum$VALUES["".length()] = ModelRotation.X0_Y0;
        enum$VALUES[" ".length()] = ModelRotation.X0_Y90;
        enum$VALUES["  ".length()] = ModelRotation.X0_Y180;
        enum$VALUES["   ".length()] = ModelRotation.X0_Y270;
        enum$VALUES[0x9E ^ 0x9A] = ModelRotation.X90_Y0;
        enum$VALUES[0x5D ^ 0x58] = ModelRotation.X90_Y90;
        enum$VALUES[0x27 ^ 0x21] = ModelRotation.X90_Y180;
        enum$VALUES[0x43 ^ 0x44] = ModelRotation.X90_Y270;
        enum$VALUES[0x48 ^ 0x40] = ModelRotation.X180_Y0;
        enum$VALUES[0xAD ^ 0xA4] = ModelRotation.X180_Y90;
        enum$VALUES[0x47 ^ 0x4D] = ModelRotation.X180_Y180;
        enum$VALUES[0x88 ^ 0x83] = ModelRotation.X180_Y270;
        enum$VALUES[0x74 ^ 0x78] = ModelRotation.X270_Y0;
        enum$VALUES[0x66 ^ 0x6B] = ModelRotation.X270_Y90;
        enum$VALUES[0x4B ^ 0x45] = ModelRotation.X270_Y180;
        enum$VALUES[0xA4 ^ 0xAB] = ModelRotation.X270_Y270;
        ENUM$VALUES = enum$VALUES;
        mapRotations = Maps.newHashMap();
        final ModelRotation[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < length) {
            final ModelRotation modelRotation = values[i];
            ModelRotation.mapRotations.put(modelRotation.combinedXY, modelRotation);
            ++i;
        }
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
    
    public EnumFacing rotateFace(final EnumFacing enumFacing) {
        EnumFacing enumFacing2 = enumFacing;
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < this.quartersX) {
            enumFacing2 = enumFacing2.rotateAround(EnumFacing.Axis.X);
            ++i;
        }
        if (enumFacing2.getAxis() != EnumFacing.Axis.Y) {
            int j = "".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
            while (j < this.quartersY) {
                enumFacing2 = enumFacing2.rotateAround(EnumFacing.Axis.Y);
                ++j;
            }
        }
        return enumFacing2;
    }
    
    public static ModelRotation getModelRotation(final int n, final int n2) {
        return ModelRotation.mapRotations.get(combineXY(MathHelper.normalizeAngle(n, 12 + 59 + 232 + 57), MathHelper.normalizeAngle(n2, 187 + 101 + 11 + 61)));
    }
    
    private ModelRotation(final String s, final int n, final int n2, final int n3) {
        this.combinedXY = combineXY(n2, n3);
        this.matrix4d = new Matrix4f();
        final Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        Matrix4f.rotate(-n2 * 0.017453292f, new Vector3f(1.0f, 0.0f, 0.0f), matrix4f, matrix4f);
        this.quartersX = MathHelper.abs_int(n2 / (0xE9 ^ 0xB3));
        final Matrix4f matrix4f2 = new Matrix4f();
        matrix4f2.setIdentity();
        Matrix4f.rotate(-n3 * 0.017453292f, new Vector3f(0.0f, 1.0f, 0.0f), matrix4f2, matrix4f2);
        this.quartersY = MathHelper.abs_int(n3 / (0x9F ^ 0xC5));
        Matrix4f.mul(matrix4f2, matrix4f, this.matrix4d);
    }
    
    private static void I() {
        (I = new String[0x32 ^ 0x22])["".length()] = I("(~)1a", "pNvhQ");
        ModelRotation.I[" ".length()] = I("\u0000],#Xh", "Xmsza");
        ModelRotation.I["  ".length()] = I(")f\u0010\u0000\\If", "qVOYm");
        ModelRotation.I["   ".length()] = I("+@\u000e\u0001\\D@", "spQXn");
        ModelRotation.I[0x5E ^ 0x5A] = I("\u0010SX,2x", "Hjhsk");
        ModelRotation.I[0x96 ^ 0x93] = I("\fUD\u0013\u0012m\\", "TltLK");
        ModelRotation.I[0x8F ^ 0x89] = I("!pI5>HqI", "yIyjg");
        ModelRotation.I[0x33 ^ 0x34] = I("\u0019X@30sV@", "Aapli");
        ModelRotation.I[0x65 ^ 0x6D] = I("+w@H6*v", "sFxxi");
        ModelRotation.I[0x84 ^ 0x8D] = I("\fYqq0\rQy", "ThIAo");
        ModelRotation.I[0xB5 ^ 0xBF] = I("\u0000FNu\u0019\u0001FNu", "XwvEF");
        ModelRotation.I[0x4 ^ 0xF] = I("\u0012|Jg\u0017\u0013\u007fEg", "JMrWH");
        ModelRotation.I[0x99 ^ 0x95] = I("9yAT\u000e8{", "aKvdQ");
        ModelRotation.I[0xAE ^ 0xA3] = I(">fyc3?m~", "fTNSl");
        ModelRotation.I[0xB5 ^ 0xBB] = I("1DUh\u00150GZh", "ivbXJ");
        ModelRotation.I[0x87 ^ 0x88] = I("/`\\V3.`\\V", "wRkfl");
    }
    
    public Matrix4f getMatrix4d() {
        return this.matrix4d;
    }
    
    public int rotateVertex(final EnumFacing enumFacing, final int n) {
        int n2 = n;
        if (enumFacing.getAxis() == EnumFacing.Axis.X) {
            n2 = (n + this.quartersX) % (0x1F ^ 0x1B);
        }
        EnumFacing rotateAround = enumFacing;
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < this.quartersX) {
            rotateAround = rotateAround.rotateAround(EnumFacing.Axis.X);
            ++i;
        }
        if (rotateAround.getAxis() == EnumFacing.Axis.Y) {
            n2 = (n2 + this.quartersY) % (0x67 ^ 0x63);
        }
        return n2;
    }
    
    private static int combineXY(final int n, final int n2) {
        return n * (189 + 36 + 106 + 29) + n2;
    }
}
