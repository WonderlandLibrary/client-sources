package net.minecraft.client.renderer.culling;

import java.nio.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class ClippingHelperImpl extends ClippingHelper
{
    private FloatBuffer modelviewMatrixBuffer;
    private FloatBuffer projectionMatrixBuffer;
    private FloatBuffer field_78564_h;
    private static ClippingHelperImpl instance;
    
    public static ClippingHelper getInstance() {
        ClippingHelperImpl.instance.init();
        return ClippingHelperImpl.instance;
    }
    
    public void init() {
        this.projectionMatrixBuffer.clear();
        this.modelviewMatrixBuffer.clear();
        this.field_78564_h.clear();
        GlStateManager.getFloat(2672 + 1809 - 3411 + 1913, this.projectionMatrixBuffer);
        GlStateManager.getFloat(1894 + 527 - 119 + 680, this.modelviewMatrixBuffer);
        final float[] projectionMatrix = this.projectionMatrix;
        final float[] modelviewMatrix = this.modelviewMatrix;
        this.projectionMatrixBuffer.flip().limit(0x2E ^ 0x3E);
        this.projectionMatrixBuffer.get(projectionMatrix);
        this.modelviewMatrixBuffer.flip().limit(0xB2 ^ 0xA2);
        this.modelviewMatrixBuffer.get(modelviewMatrix);
        this.clippingMatrix["".length()] = modelviewMatrix["".length()] * projectionMatrix["".length()] + modelviewMatrix[" ".length()] * projectionMatrix[0x99 ^ 0x9D] + modelviewMatrix["  ".length()] * projectionMatrix[0x5E ^ 0x56] + modelviewMatrix["   ".length()] * projectionMatrix[0x2 ^ 0xE];
        this.clippingMatrix[" ".length()] = modelviewMatrix["".length()] * projectionMatrix[" ".length()] + modelviewMatrix[" ".length()] * projectionMatrix[0x7F ^ 0x7A] + modelviewMatrix["  ".length()] * projectionMatrix[0xAD ^ 0xA4] + modelviewMatrix["   ".length()] * projectionMatrix[0xAA ^ 0xA7];
        this.clippingMatrix["  ".length()] = modelviewMatrix["".length()] * projectionMatrix["  ".length()] + modelviewMatrix[" ".length()] * projectionMatrix[0x30 ^ 0x36] + modelviewMatrix["  ".length()] * projectionMatrix[0xD ^ 0x7] + modelviewMatrix["   ".length()] * projectionMatrix[0x86 ^ 0x88];
        this.clippingMatrix["   ".length()] = modelviewMatrix["".length()] * projectionMatrix["   ".length()] + modelviewMatrix[" ".length()] * projectionMatrix[0xA9 ^ 0xAE] + modelviewMatrix["  ".length()] * projectionMatrix[0xCC ^ 0xC7] + modelviewMatrix["   ".length()] * projectionMatrix[0x3A ^ 0x35];
        this.clippingMatrix[0x3B ^ 0x3F] = modelviewMatrix[0x37 ^ 0x33] * projectionMatrix["".length()] + modelviewMatrix[0x9A ^ 0x9F] * projectionMatrix[0x70 ^ 0x74] + modelviewMatrix[0x7E ^ 0x78] * projectionMatrix[0x62 ^ 0x6A] + modelviewMatrix[0x81 ^ 0x86] * projectionMatrix[0x0 ^ 0xC];
        this.clippingMatrix[0x26 ^ 0x23] = modelviewMatrix[0xC4 ^ 0xC0] * projectionMatrix[" ".length()] + modelviewMatrix[0x33 ^ 0x36] * projectionMatrix[0x31 ^ 0x34] + modelviewMatrix[0x59 ^ 0x5F] * projectionMatrix[0x13 ^ 0x1A] + modelviewMatrix[0x8F ^ 0x88] * projectionMatrix[0x2A ^ 0x27];
        this.clippingMatrix[0x13 ^ 0x15] = modelviewMatrix[0xAE ^ 0xAA] * projectionMatrix["  ".length()] + modelviewMatrix[0x4D ^ 0x48] * projectionMatrix[0x7B ^ 0x7D] + modelviewMatrix[0x17 ^ 0x11] * projectionMatrix[0xBA ^ 0xB0] + modelviewMatrix[0x35 ^ 0x32] * projectionMatrix[0x1F ^ 0x11];
        this.clippingMatrix[0xE ^ 0x9] = modelviewMatrix[0x5D ^ 0x59] * projectionMatrix["   ".length()] + modelviewMatrix[0x43 ^ 0x46] * projectionMatrix[0x1B ^ 0x1C] + modelviewMatrix[0x7B ^ 0x7D] * projectionMatrix[0x75 ^ 0x7E] + modelviewMatrix[0xB0 ^ 0xB7] * projectionMatrix[0x82 ^ 0x8D];
        this.clippingMatrix[0x86 ^ 0x8E] = modelviewMatrix[0xB3 ^ 0xBB] * projectionMatrix["".length()] + modelviewMatrix[0x13 ^ 0x1A] * projectionMatrix[0x66 ^ 0x62] + modelviewMatrix[0x36 ^ 0x3C] * projectionMatrix[0x1A ^ 0x12] + modelviewMatrix[0x41 ^ 0x4A] * projectionMatrix[0x4C ^ 0x40];
        this.clippingMatrix[0x41 ^ 0x48] = modelviewMatrix[0x7B ^ 0x73] * projectionMatrix[" ".length()] + modelviewMatrix[0x61 ^ 0x68] * projectionMatrix[0x71 ^ 0x74] + modelviewMatrix[0x52 ^ 0x58] * projectionMatrix[0x12 ^ 0x1B] + modelviewMatrix[0xBC ^ 0xB7] * projectionMatrix[0x59 ^ 0x54];
        this.clippingMatrix[0x4B ^ 0x41] = modelviewMatrix[0xAF ^ 0xA7] * projectionMatrix["  ".length()] + modelviewMatrix[0xA7 ^ 0xAE] * projectionMatrix[0x3A ^ 0x3C] + modelviewMatrix[0x84 ^ 0x8E] * projectionMatrix[0x99 ^ 0x93] + modelviewMatrix[0xCD ^ 0xC6] * projectionMatrix[0x3E ^ 0x30];
        this.clippingMatrix[0x2D ^ 0x26] = modelviewMatrix[0x6B ^ 0x63] * projectionMatrix["   ".length()] + modelviewMatrix[0x1A ^ 0x13] * projectionMatrix[0x44 ^ 0x43] + modelviewMatrix[0x9E ^ 0x94] * projectionMatrix[0xA7 ^ 0xAC] + modelviewMatrix[0x83 ^ 0x88] * projectionMatrix[0x25 ^ 0x2A];
        this.clippingMatrix[0x84 ^ 0x88] = modelviewMatrix[0x9C ^ 0x90] * projectionMatrix["".length()] + modelviewMatrix[0x1E ^ 0x13] * projectionMatrix[0x8D ^ 0x89] + modelviewMatrix[0x3D ^ 0x33] * projectionMatrix[0xAD ^ 0xA5] + modelviewMatrix[0x88 ^ 0x87] * projectionMatrix[0x83 ^ 0x8F];
        this.clippingMatrix[0x8A ^ 0x87] = modelviewMatrix[0x2D ^ 0x21] * projectionMatrix[" ".length()] + modelviewMatrix[0x9D ^ 0x90] * projectionMatrix[0x9D ^ 0x98] + modelviewMatrix[0x4B ^ 0x45] * projectionMatrix[0xB5 ^ 0xBC] + modelviewMatrix[0xBD ^ 0xB2] * projectionMatrix[0x82 ^ 0x8F];
        this.clippingMatrix[0x16 ^ 0x18] = modelviewMatrix[0x5E ^ 0x52] * projectionMatrix["  ".length()] + modelviewMatrix[0x2F ^ 0x22] * projectionMatrix[0x18 ^ 0x1E] + modelviewMatrix[0xC9 ^ 0xC7] * projectionMatrix[0x2E ^ 0x24] + modelviewMatrix[0x91 ^ 0x9E] * projectionMatrix[0x72 ^ 0x7C];
        this.clippingMatrix[0xBF ^ 0xB0] = modelviewMatrix[0x8D ^ 0x81] * projectionMatrix["   ".length()] + modelviewMatrix[0x85 ^ 0x88] * projectionMatrix[0xA6 ^ 0xA1] + modelviewMatrix[0xA6 ^ 0xA8] * projectionMatrix[0x25 ^ 0x2E] + modelviewMatrix[0x6A ^ 0x65] * projectionMatrix[0x86 ^ 0x89];
        final float[] array = this.frustum["".length()];
        array["".length()] = this.clippingMatrix["   ".length()] - this.clippingMatrix["".length()];
        array[" ".length()] = this.clippingMatrix[0xB0 ^ 0xB7] - this.clippingMatrix[0x1B ^ 0x1F];
        array["  ".length()] = this.clippingMatrix[0x27 ^ 0x2C] - this.clippingMatrix[0xCA ^ 0xC2];
        array["   ".length()] = this.clippingMatrix[0x57 ^ 0x58] - this.clippingMatrix[0x7C ^ 0x70];
        this.normalize(array);
        final float[] array2 = this.frustum[" ".length()];
        array2["".length()] = this.clippingMatrix["   ".length()] + this.clippingMatrix["".length()];
        array2[" ".length()] = this.clippingMatrix[0x65 ^ 0x62] + this.clippingMatrix[0xA0 ^ 0xA4];
        array2["  ".length()] = this.clippingMatrix[0x85 ^ 0x8E] + this.clippingMatrix[0xB6 ^ 0xBE];
        array2["   ".length()] = this.clippingMatrix[0x4 ^ 0xB] + this.clippingMatrix[0x91 ^ 0x9D];
        this.normalize(array2);
        final float[] array3 = this.frustum["  ".length()];
        array3["".length()] = this.clippingMatrix["   ".length()] + this.clippingMatrix[" ".length()];
        array3[" ".length()] = this.clippingMatrix[0x6E ^ 0x69] + this.clippingMatrix[0x56 ^ 0x53];
        array3["  ".length()] = this.clippingMatrix[0xA8 ^ 0xA3] + this.clippingMatrix[0xA7 ^ 0xAE];
        array3["   ".length()] = this.clippingMatrix[0x17 ^ 0x18] + this.clippingMatrix[0xBC ^ 0xB1];
        this.normalize(array3);
        final float[] array4 = this.frustum["   ".length()];
        array4["".length()] = this.clippingMatrix["   ".length()] - this.clippingMatrix[" ".length()];
        array4[" ".length()] = this.clippingMatrix[0x75 ^ 0x72] - this.clippingMatrix[0xB0 ^ 0xB5];
        array4["  ".length()] = this.clippingMatrix[0x92 ^ 0x99] - this.clippingMatrix[0x5F ^ 0x56];
        array4["   ".length()] = this.clippingMatrix[0x82 ^ 0x8D] - this.clippingMatrix[0xCA ^ 0xC7];
        this.normalize(array4);
        final float[] array5 = this.frustum[0x98 ^ 0x9C];
        array5["".length()] = this.clippingMatrix["   ".length()] - this.clippingMatrix["  ".length()];
        array5[" ".length()] = this.clippingMatrix[0x47 ^ 0x40] - this.clippingMatrix[0x37 ^ 0x31];
        array5["  ".length()] = this.clippingMatrix[0x91 ^ 0x9A] - this.clippingMatrix[0x37 ^ 0x3D];
        array5["   ".length()] = this.clippingMatrix[0x91 ^ 0x9E] - this.clippingMatrix[0x45 ^ 0x4B];
        this.normalize(array5);
        final float[] array6 = this.frustum[0xA9 ^ 0xAC];
        array6["".length()] = this.clippingMatrix["   ".length()] + this.clippingMatrix["  ".length()];
        array6[" ".length()] = this.clippingMatrix[0x57 ^ 0x50] + this.clippingMatrix[0x3D ^ 0x3B];
        array6["  ".length()] = this.clippingMatrix[0xB0 ^ 0xBB] + this.clippingMatrix[0x3E ^ 0x34];
        array6["   ".length()] = this.clippingMatrix[0xC8 ^ 0xC7] + this.clippingMatrix[0xE ^ 0x0];
        this.normalize(array6);
    }
    
    private void normalize(final float[] array) {
        final float sqrt_float = MathHelper.sqrt_float(array["".length()] * array["".length()] + array[" ".length()] * array[" ".length()] + array["  ".length()] * array["  ".length()]);
        final int length = "".length();
        array[length] /= sqrt_float;
        final int length2 = " ".length();
        array[length2] /= sqrt_float;
        final int length3 = "  ".length();
        array[length3] /= sqrt_float;
        final int length4 = "   ".length();
        array[length4] /= sqrt_float;
    }
    
    static {
        ClippingHelperImpl.instance = new ClippingHelperImpl();
    }
    
    public ClippingHelperImpl() {
        this.projectionMatrixBuffer = GLAllocation.createDirectFloatBuffer(0x8B ^ 0x9B);
        this.modelviewMatrixBuffer = GLAllocation.createDirectFloatBuffer(0x3E ^ 0x2E);
        this.field_78564_h = GLAllocation.createDirectFloatBuffer(0x2D ^ 0x3D);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
