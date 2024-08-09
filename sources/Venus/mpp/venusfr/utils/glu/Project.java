/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.glu;

import com.ibm.icu.impl.duration.impl.Utils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Project
extends Utils {
    private static final float[] IDENTITY_MATRIX = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    private static final FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer finalMatrix = BufferUtils.createFloatBuffer(16);
    private static final FloatBuffer tempMatrix = BufferUtils.createFloatBuffer(16);
    private static final float[] in = new float[4];
    private static final float[] out = new float[4];

    private static void __gluMakeIdentityf(FloatBuffer floatBuffer) {
        int n = floatBuffer.position();
        floatBuffer.put(IDENTITY_MATRIX);
        floatBuffer.position(n);
    }

    private static void __gluMultMatrixVecf(FloatBuffer floatBuffer, float[] fArray, float[] fArray2) {
        for (int i = 0; i < 4; ++i) {
            fArray2[i] = fArray[0] * floatBuffer.get(floatBuffer.position() + i) + fArray[1] * floatBuffer.get(floatBuffer.position() + 4 + i) + fArray[2] * floatBuffer.get(floatBuffer.position() + 8 + i) + fArray[3] * floatBuffer.get(floatBuffer.position() + 12 + i);
        }
    }

    private static boolean __gluInvertMatrixf() {
        int n;
        FloatBuffer floatBuffer = tempMatrix;
        for (n = 0; n < 16; ++n) {
            floatBuffer.put(n, finalMatrix.get(n + finalMatrix.position()));
        }
        Project.__gluMakeIdentityf(finalMatrix);
        for (n = 0; n < 4; ++n) {
            float f;
            int n2;
            int n3;
            int n4 = n;
            for (n3 = n + 1; n3 < 4; ++n3) {
                if (!(Math.abs(floatBuffer.get(n3 * 4 + n)) > Math.abs(floatBuffer.get(n * 4 + n)))) continue;
                n4 = n3;
            }
            if (n4 != n) {
                for (n2 = 0; n2 < 4; ++n2) {
                    f = floatBuffer.get(n * 4 + n2);
                    floatBuffer.put(n * 4 + n2, floatBuffer.get(n4 * 4 + n2));
                    floatBuffer.put(n4 * 4 + n2, f);
                    f = finalMatrix.get(n * 4 + n2);
                    finalMatrix.put(n * 4 + n2, finalMatrix.get(n4 * 4 + n2));
                    finalMatrix.put(n4 * 4 + n2, f);
                }
            }
            if (floatBuffer.get(n * 4 + n) == 0.0f) {
                return true;
            }
            f = floatBuffer.get(n * 4 + n);
            for (n2 = 0; n2 < 4; ++n2) {
                floatBuffer.put(n * 4 + n2, floatBuffer.get(n * 4 + n2) / f);
                finalMatrix.put(n * 4 + n2, finalMatrix.get(n * 4 + n2) / f);
            }
            for (n3 = 0; n3 < 4; ++n3) {
                if (n3 == n) continue;
                f = floatBuffer.get(n3 * 4 + n);
                for (n2 = 0; n2 < 4; ++n2) {
                    floatBuffer.put(n3 * 4 + n2, floatBuffer.get(n3 * 4 + n2) - floatBuffer.get(n * 4 + n2) * f);
                    finalMatrix.put(n3 * 4 + n2, finalMatrix.get(n3 * 4 + n2) - finalMatrix.get(n * 4 + n2) * f);
                }
            }
        }
        return false;
    }

    private static void __gluMultMatricesf(FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                finalMatrix.put(finalMatrix.position() + i * 4 + j, floatBuffer.get(floatBuffer.position() + i * 4) * floatBuffer2.get(floatBuffer2.position() + j) + floatBuffer.get(floatBuffer.position() + i * 4 + 1) * floatBuffer2.get(floatBuffer2.position() + 4 + j) + floatBuffer.get(floatBuffer.position() + i * 4 + 2) * floatBuffer2.get(floatBuffer2.position() + 8 + j) + floatBuffer.get(floatBuffer.position() + i * 4 + 3) * floatBuffer2.get(floatBuffer2.position() + 12 + j));
            }
        }
    }

    public static void gluPerspective(float f, float f2, float f3, float f4) {
        float f5 = (float)((double)(f / 2.0f) * Math.PI / 180.0);
        float f6 = f4 - f3;
        float f7 = MathHelper.sin(f5);
        if (f6 == 0.0f || f7 == 0.0f || f2 == 0.0f) {
            return;
        }
        float f8 = MathHelper.cos(f5) / f7;
        Project.__gluMakeIdentityf(matrix);
        matrix.put(0, f8 / f2);
        matrix.put(5, f8);
        matrix.put(10, -(f4 + f3) / f6);
        matrix.put(11, -1.0f);
        matrix.put(14, -2.0f * f3 * f4 / f6);
        matrix.put(15, 0.0f);
        GL11.glMultMatrixf(matrix);
    }

    public static boolean gluProject(float f, float f2, float f3, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, IntBuffer intBuffer, FloatBuffer floatBuffer3) {
        float[] fArray = new float[]{f, f2, f3, 1.0f};
        float[] fArray2 = new float[4];
        Project.__gluMultMatrixVecf(floatBuffer, fArray, fArray2);
        Project.__gluMultMatrixVecf(floatBuffer2, fArray2, fArray);
        if (fArray[3] == 0.0f) {
            return true;
        }
        float f4 = 1.0f / fArray[3];
        fArray[0] = (fArray[0] * f4 + 1.0f) * 0.5f;
        fArray[1] = (fArray[1] * f4 + 1.0f) * 0.5f;
        fArray[2] = fArray[2] * f4;
        int n = intBuffer.position();
        int n2 = intBuffer.get(n);
        int n3 = intBuffer.get(n + 1);
        int n4 = intBuffer.get(n + 2);
        int n5 = intBuffer.get(n + 3);
        floatBuffer3.put(0, fArray[0] * (float)n4 + (float)n2);
        floatBuffer3.put(1, fArray[1] * (float)n5 + (float)n3);
        floatBuffer3.put(2, fArray[2]);
        return false;
    }

    public static boolean gluUnProject(float f, float f2, float f3, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, IntBuffer intBuffer, FloatBuffer floatBuffer3) {
        float[] fArray = in;
        float[] fArray2 = out;
        Project.__gluMultMatricesf(floatBuffer, floatBuffer2);
        if (!Project.__gluInvertMatrixf()) {
            return true;
        }
        fArray[0] = f;
        fArray[1] = f2;
        fArray[2] = f3;
        fArray[3] = 1.0f;
        fArray[0] = (fArray[0] - (float)intBuffer.get(intBuffer.position())) / (float)intBuffer.get(intBuffer.position() + 2);
        fArray[1] = (fArray[1] - (float)intBuffer.get(intBuffer.position() + 1)) / (float)intBuffer.get(intBuffer.position() + 3);
        fArray[0] = fArray[0] * 2.0f - 1.0f;
        fArray[1] = fArray[1] * 2.0f - 1.0f;
        fArray[2] = fArray[2] * 2.0f - 1.0f;
        Project.__gluMultMatrixVecf(finalMatrix, fArray, fArray2);
        if ((double)fArray2[3] == 0.0) {
            return true;
        }
        fArray2[3] = 1.0f / fArray2[3];
        floatBuffer3.put(floatBuffer3.position(), fArray2[0] * fArray2[3]);
        floatBuffer3.put(floatBuffer3.position() + 1, fArray2[1] * fArray2[3]);
        floatBuffer3.put(floatBuffer3.position() + 2, fArray2[2] * fArray2[3]);
        return false;
    }

    public static void gluPickMatrix(float f, float f2, float f3, float f4, IntBuffer intBuffer) {
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return;
        }
        GL11.glTranslatef(((float)intBuffer.get(intBuffer.position() + 2) - 2.0f * (f - (float)intBuffer.get(intBuffer.position()))) / f3, ((float)intBuffer.get(intBuffer.position() + 3) - 2.0f * (f2 - (float)intBuffer.get(intBuffer.position() + 1))) / f4, 0.0f);
        GL11.glScalef((float)intBuffer.get(intBuffer.position() + 2) / f3, (float)intBuffer.get(intBuffer.position() + 3) / f4, 1.0f);
    }
}

