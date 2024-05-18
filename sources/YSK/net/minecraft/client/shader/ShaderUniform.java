package net.minecraft.client.shader;

import java.nio.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.*;

public class ShaderUniform
{
    private final String shaderName;
    private final FloatBuffer uniformFloatBuffer;
    private final int uniformCount;
    private final IntBuffer uniformIntBuffer;
    private boolean dirty;
    private int uniformLocation;
    private final int uniformType;
    private final ShaderManager shaderManager;
    private static final Logger logger;
    private static final String[] I;
    
    public void set(final float n, final float n2, final float n3) {
        this.uniformFloatBuffer.position("".length());
        this.uniformFloatBuffer.put("".length(), n);
        this.uniformFloatBuffer.put(" ".length(), n2);
        this.uniformFloatBuffer.put("  ".length(), n3);
        this.markDirty();
    }
    
    public void set(final float n) {
        this.uniformFloatBuffer.position("".length());
        this.uniformFloatBuffer.put("".length(), n);
        this.markDirty();
    }
    
    public void set(final float n, final float n2, final float n3, final float n4) {
        this.uniformFloatBuffer.position("".length());
        this.uniformFloatBuffer.put(n);
        this.uniformFloatBuffer.put(n2);
        this.uniformFloatBuffer.put(n3);
        this.uniformFloatBuffer.put(n4);
        this.uniformFloatBuffer.flip();
        this.markDirty();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public static int parseType(final String s) {
        int length = -" ".length();
        if (s.equals(ShaderUniform.I["".length()])) {
            length = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (s.equals(ShaderUniform.I[" ".length()])) {
            length = (0x49 ^ 0x4D);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (s.startsWith(ShaderUniform.I["  ".length()])) {
            if (s.endsWith(ShaderUniform.I["   ".length()])) {
                length = (0x58 ^ 0x50);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else if (s.endsWith(ShaderUniform.I[0x44 ^ 0x40])) {
                length = (0xB9 ^ 0xB0);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else if (s.endsWith(ShaderUniform.I[0x66 ^ 0x63])) {
                length = (0x22 ^ 0x28);
            }
        }
        return length;
    }
    
    private static void I() {
        (I = new String[0x81 ^ 0x93])["".length()] = I("(,\u0019", "ABmHu");
        ShaderUniform.I[" ".length()] = I("7\"\u001a%1", "QNuDE");
        ShaderUniform.I["  ".length()] = I("5%\u0007$; ", "XDsVR");
        ShaderUniform.I["   ".length()] = I("z\u0010S", "Hhaax");
        ShaderUniform.I[0xAA ^ 0xAE] = I("A\u0014U", "rlfXa");
        ShaderUniform.I[0x8B ^ 0x8E] = I("s+s", "GSGZt");
        ShaderUniform.I[0x1B ^ 0x1D] = I("7+\u000b$\u001b\u0010(L1\u0011\u0016e\u0001#\u0018\u000e \u0006b\u0003\u000b1\nb\u0015B1\r-Y\u0011(\u0003.\u0018B3\u0003.\u0001\u0007e\u00030\u0006\u0003<Bj\u0011\u001a5\u0007!\u0000\u0007!B", "bEbBt");
        ShaderUniform.I[0x97 ^ 0x90] = I("NM2\r-B", "bmUbY");
        ShaderUniform.I[0x17 ^ 0x1F] = I("}do\u000f\":%=/+3d", "TJOFE");
        ShaderUniform.I[0x3A ^ 0x33] = I("\u0012\"\u0002\u001785!E\u0004'+#\n\u0015w$-\u0007\u001d2#`K\u0013\"3l\u001f\b'\"l\u001d\u0010;2)KY", "GLkqW");
        ShaderUniform.I[0x16 ^ 0x1C] = I("aU\n\u0002a&\u001a\u0017Q", "HucqA");
        ShaderUniform.I[0xBA ^ 0xB1] = I("7P\u00024\u0002?\u0014T!\u0017&\u0015Zu'1\u001e\u001b'\u00078\u0017Z", "VptUn");
        ShaderUniform.I[0x46 ^ 0x4A] = I("<9\u001e.*\u001b:Y=5\u00058\u0016,e\n6\u001b$ \r{W*0\u001dw\u0014'0\u0007#W>$\u0005\"\u0012hm", "iWwHE");
        ShaderUniform.I[0xBF ^ 0xB2] = I("yE#:W", "PeJIw");
        ShaderUniform.I[0x66 ^ 0x68] = I("S9 \u0018W\u001a9o\u0018\u001f\u0016w=\r\u0019\u00142o\u0003\u0011Sfo\u0018\u0018ScaL>\u00149 \u001e\u001e\u001d0a", "sWOlw");
        ShaderUniform.I[0x72 ^ 0x7D] = I(",?\u0002\u000f\t\u000b<E\u001c\u0016\u0015>\n\rF\u001a0\u0007\u0005\u0003\u001d}K\u000b\u0013\rq\b\u0006\u0013\u0017%K\u001f\u0007\u0015$\u000eIN", "yQkif");
        ShaderUniform.I[0xAA ^ 0xBA] = I("mx\u001d\u0001j", "DXtrJ");
        ShaderUniform.I[0x9D ^ 0x8C] = I("\u001f\n\u0011D*\u001fE\u0011\f&Q\u0017\u0004\n$\u0014E\n\u0002c@E\u0011\u000bcEKE-$\u001f\n\u0017\r-\u0016K", "qeedC");
    }
    
    private void uploadFloatMatrix() {
        switch (this.uniformType) {
            case 8: {
                OpenGlHelper.glUniformMatrix2(this.uniformLocation, " ".length() != 0, this.uniformFloatBuffer);
                "".length();
                if (1 < 1) {
                    throw null;
                }
                break;
            }
            case 9: {
                OpenGlHelper.glUniformMatrix3(this.uniformLocation, " ".length() != 0, this.uniformFloatBuffer);
                "".length();
                if (-1 == 1) {
                    throw null;
                }
                break;
            }
            case 10: {
                OpenGlHelper.glUniformMatrix4(this.uniformLocation, " ".length() != 0, this.uniformFloatBuffer);
                break;
            }
        }
    }
    
    public void set(final int n, final int n2, final int n3, final int n4) {
        this.uniformIntBuffer.position("".length());
        if (this.uniformType >= 0) {
            this.uniformIntBuffer.put("".length(), n);
        }
        if (this.uniformType >= " ".length()) {
            this.uniformIntBuffer.put(" ".length(), n2);
        }
        if (this.uniformType >= "  ".length()) {
            this.uniformIntBuffer.put("  ".length(), n3);
        }
        if (this.uniformType >= "   ".length()) {
            this.uniformIntBuffer.put("   ".length(), n4);
        }
        this.markDirty();
    }
    
    public void set(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12, final float n13, final float n14, final float n15, final float n16) {
        this.uniformFloatBuffer.position("".length());
        this.uniformFloatBuffer.put("".length(), n);
        this.uniformFloatBuffer.put(" ".length(), n2);
        this.uniformFloatBuffer.put("  ".length(), n3);
        this.uniformFloatBuffer.put("   ".length(), n4);
        this.uniformFloatBuffer.put(0x15 ^ 0x11, n5);
        this.uniformFloatBuffer.put(0x85 ^ 0x80, n6);
        this.uniformFloatBuffer.put(0x1D ^ 0x1B, n7);
        this.uniformFloatBuffer.put(0x79 ^ 0x7E, n8);
        this.uniformFloatBuffer.put(0x66 ^ 0x6E, n9);
        this.uniformFloatBuffer.put(0x32 ^ 0x3B, n10);
        this.uniformFloatBuffer.put(0x4F ^ 0x45, n11);
        this.uniformFloatBuffer.put(0xAE ^ 0xA5, n12);
        this.uniformFloatBuffer.put(0x2 ^ 0xE, n13);
        this.uniformFloatBuffer.put(0x75 ^ 0x78, n14);
        this.uniformFloatBuffer.put(0xAC ^ 0xA2, n15);
        this.uniformFloatBuffer.put(0xBC ^ 0xB3, n16);
        this.markDirty();
    }
    
    public void set(final Matrix4f matrix4f) {
        this.set(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m03, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m13, matrix4f.m20, matrix4f.m21, matrix4f.m22, matrix4f.m23, matrix4f.m30, matrix4f.m31, matrix4f.m32, matrix4f.m33);
    }
    
    public void upload() {
        if (!this.dirty) {}
        this.dirty = ("".length() != 0);
        if (this.uniformType <= "   ".length()) {
            this.uploadInt();
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else if (this.uniformType <= (0x0 ^ 0x7)) {
            this.uploadFloat();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            if (this.uniformType > (0x28 ^ 0x22)) {
                ShaderUniform.logger.warn(ShaderUniform.I[0xBC ^ 0xB5] + this.uniformType + ShaderUniform.I[0xB1 ^ 0xBB] + ShaderUniform.I[0x51 ^ 0x5A]);
                return;
            }
            this.uploadFloatMatrix();
        }
    }
    
    private void uploadFloat() {
        switch (this.uniformType) {
            case 4: {
                OpenGlHelper.glUniform1(this.uniformLocation, this.uniformFloatBuffer);
                "".length();
                if (false == true) {
                    throw null;
                }
                break;
            }
            case 5: {
                OpenGlHelper.glUniform2(this.uniformLocation, this.uniformFloatBuffer);
                "".length();
                if (0 < 0) {
                    throw null;
                }
                break;
            }
            case 6: {
                OpenGlHelper.glUniform3(this.uniformLocation, this.uniformFloatBuffer);
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 7: {
                OpenGlHelper.glUniform4(this.uniformLocation, this.uniformFloatBuffer);
                "".length();
                if (2 == 3) {
                    throw null;
                }
                break;
            }
            default: {
                ShaderUniform.logger.warn(ShaderUniform.I[0x1B ^ 0x14] + this.uniformCount + ShaderUniform.I[0x87 ^ 0x97] + ShaderUniform.I[0x6E ^ 0x7F]);
                break;
            }
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void set(final float[] array) {
        if (array.length < this.uniformCount) {
            ShaderUniform.logger.warn(ShaderUniform.I[0x68 ^ 0x6E] + this.uniformCount + ShaderUniform.I[0x13 ^ 0x14] + array.length + ShaderUniform.I[0x87 ^ 0x8F]);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            this.uniformFloatBuffer.position("".length());
            this.uniformFloatBuffer.put(array);
            this.uniformFloatBuffer.position("".length());
            this.markDirty();
        }
    }
    
    public void set(final float n, final float n2) {
        this.uniformFloatBuffer.position("".length());
        this.uniformFloatBuffer.put("".length(), n);
        this.uniformFloatBuffer.put(" ".length(), n2);
        this.markDirty();
    }
    
    public String getShaderName() {
        return this.shaderName;
    }
    
    public ShaderUniform(final String shaderName, final int uniformType, final int uniformCount, final ShaderManager shaderManager) {
        this.shaderName = shaderName;
        this.uniformCount = uniformCount;
        this.uniformType = uniformType;
        this.shaderManager = shaderManager;
        if (uniformType <= "   ".length()) {
            this.uniformIntBuffer = BufferUtils.createIntBuffer(uniformCount);
            this.uniformFloatBuffer = null;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            this.uniformIntBuffer = null;
            this.uniformFloatBuffer = BufferUtils.createFloatBuffer(uniformCount);
        }
        this.uniformLocation = -" ".length();
        this.markDirty();
    }
    
    private void markDirty() {
        this.dirty = (" ".length() != 0);
        if (this.shaderManager != null) {
            this.shaderManager.markDirty();
        }
    }
    
    public void func_148092_b(final float n, final float n2, final float n3, final float n4) {
        this.uniformFloatBuffer.position("".length());
        if (this.uniformType >= (0x4 ^ 0x0)) {
            this.uniformFloatBuffer.put("".length(), n);
        }
        if (this.uniformType >= (0x9F ^ 0x9A)) {
            this.uniformFloatBuffer.put(" ".length(), n2);
        }
        if (this.uniformType >= (0x14 ^ 0x12)) {
            this.uniformFloatBuffer.put("  ".length(), n3);
        }
        if (this.uniformType >= (0xC2 ^ 0xC5)) {
            this.uniformFloatBuffer.put("   ".length(), n4);
        }
        this.markDirty();
    }
    
    public void setUniformLocation(final int uniformLocation) {
        this.uniformLocation = uniformLocation;
    }
    
    private void uploadInt() {
        switch (this.uniformType) {
            case 0: {
                OpenGlHelper.glUniform1(this.uniformLocation, this.uniformIntBuffer);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                OpenGlHelper.glUniform2(this.uniformLocation, this.uniformIntBuffer);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 2: {
                OpenGlHelper.glUniform3(this.uniformLocation, this.uniformIntBuffer);
                "".length();
                if (2 < 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                OpenGlHelper.glUniform4(this.uniformLocation, this.uniformIntBuffer);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            default: {
                ShaderUniform.logger.warn(ShaderUniform.I[0xBA ^ 0xB6] + this.uniformCount + ShaderUniform.I[0x25 ^ 0x28] + ShaderUniform.I[0x81 ^ 0x8F]);
                break;
            }
        }
    }
}
