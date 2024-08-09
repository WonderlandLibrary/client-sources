/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class SMath {
    static void multiplyMat4xMat4(float[] fArray, float[] fArray2, float[] fArray3) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                fArray[4 * i + j] = fArray2[4 * i + 0] * fArray3[0 + j] + fArray2[4 * i + 1] * fArray3[4 + j] + fArray2[4 * i + 2] * fArray3[8 + j] + fArray2[4 * i + 3] * fArray3[12 + j];
            }
        }
    }

    static void multiplyMat4xVec4(float[] fArray, float[] fArray2, float[] fArray3) {
        fArray[0] = fArray2[0] * fArray3[0] + fArray2[4] * fArray3[1] + fArray2[8] * fArray3[2] + fArray2[12] * fArray3[3];
        fArray[1] = fArray2[1] * fArray3[0] + fArray2[5] * fArray3[1] + fArray2[9] * fArray3[2] + fArray2[13] * fArray3[3];
        fArray[2] = fArray2[2] * fArray3[0] + fArray2[6] * fArray3[1] + fArray2[10] * fArray3[2] + fArray2[14] * fArray3[3];
        fArray[3] = fArray2[3] * fArray3[0] + fArray2[7] * fArray3[1] + fArray2[11] * fArray3[2] + fArray2[15] * fArray3[3];
    }

    static void invertMat4(float[] fArray, float[] fArray2) {
        fArray[0] = fArray2[5] * fArray2[10] * fArray2[15] - fArray2[5] * fArray2[11] * fArray2[14] - fArray2[9] * fArray2[6] * fArray2[15] + fArray2[9] * fArray2[7] * fArray2[14] + fArray2[13] * fArray2[6] * fArray2[11] - fArray2[13] * fArray2[7] * fArray2[10];
        fArray[1] = -fArray2[1] * fArray2[10] * fArray2[15] + fArray2[1] * fArray2[11] * fArray2[14] + fArray2[9] * fArray2[2] * fArray2[15] - fArray2[9] * fArray2[3] * fArray2[14] - fArray2[13] * fArray2[2] * fArray2[11] + fArray2[13] * fArray2[3] * fArray2[10];
        fArray[2] = fArray2[1] * fArray2[6] * fArray2[15] - fArray2[1] * fArray2[7] * fArray2[14] - fArray2[5] * fArray2[2] * fArray2[15] + fArray2[5] * fArray2[3] * fArray2[14] + fArray2[13] * fArray2[2] * fArray2[7] - fArray2[13] * fArray2[3] * fArray2[6];
        fArray[3] = -fArray2[1] * fArray2[6] * fArray2[11] + fArray2[1] * fArray2[7] * fArray2[10] + fArray2[5] * fArray2[2] * fArray2[11] - fArray2[5] * fArray2[3] * fArray2[10] - fArray2[9] * fArray2[2] * fArray2[7] + fArray2[9] * fArray2[3] * fArray2[6];
        fArray[4] = -fArray2[4] * fArray2[10] * fArray2[15] + fArray2[4] * fArray2[11] * fArray2[14] + fArray2[8] * fArray2[6] * fArray2[15] - fArray2[8] * fArray2[7] * fArray2[14] - fArray2[12] * fArray2[6] * fArray2[11] + fArray2[12] * fArray2[7] * fArray2[10];
        fArray[5] = fArray2[0] * fArray2[10] * fArray2[15] - fArray2[0] * fArray2[11] * fArray2[14] - fArray2[8] * fArray2[2] * fArray2[15] + fArray2[8] * fArray2[3] * fArray2[14] + fArray2[12] * fArray2[2] * fArray2[11] - fArray2[12] * fArray2[3] * fArray2[10];
        fArray[6] = -fArray2[0] * fArray2[6] * fArray2[15] + fArray2[0] * fArray2[7] * fArray2[14] + fArray2[4] * fArray2[2] * fArray2[15] - fArray2[4] * fArray2[3] * fArray2[14] - fArray2[12] * fArray2[2] * fArray2[7] + fArray2[12] * fArray2[3] * fArray2[6];
        fArray[7] = fArray2[0] * fArray2[6] * fArray2[11] - fArray2[0] * fArray2[7] * fArray2[10] - fArray2[4] * fArray2[2] * fArray2[11] + fArray2[4] * fArray2[3] * fArray2[10] + fArray2[8] * fArray2[2] * fArray2[7] - fArray2[8] * fArray2[3] * fArray2[6];
        fArray[8] = fArray2[4] * fArray2[9] * fArray2[15] - fArray2[4] * fArray2[11] * fArray2[13] - fArray2[8] * fArray2[5] * fArray2[15] + fArray2[8] * fArray2[7] * fArray2[13] + fArray2[12] * fArray2[5] * fArray2[11] - fArray2[12] * fArray2[7] * fArray2[9];
        fArray[9] = -fArray2[0] * fArray2[9] * fArray2[15] + fArray2[0] * fArray2[11] * fArray2[13] + fArray2[8] * fArray2[1] * fArray2[15] - fArray2[8] * fArray2[3] * fArray2[13] - fArray2[12] * fArray2[1] * fArray2[11] + fArray2[12] * fArray2[3] * fArray2[9];
        fArray[10] = fArray2[0] * fArray2[5] * fArray2[15] - fArray2[0] * fArray2[7] * fArray2[13] - fArray2[4] * fArray2[1] * fArray2[15] + fArray2[4] * fArray2[3] * fArray2[13] + fArray2[12] * fArray2[1] * fArray2[7] - fArray2[12] * fArray2[3] * fArray2[5];
        fArray[11] = -fArray2[0] * fArray2[5] * fArray2[11] + fArray2[0] * fArray2[7] * fArray2[9] + fArray2[4] * fArray2[1] * fArray2[11] - fArray2[4] * fArray2[3] * fArray2[9] - fArray2[8] * fArray2[1] * fArray2[7] + fArray2[8] * fArray2[3] * fArray2[5];
        fArray[12] = -fArray2[4] * fArray2[9] * fArray2[14] + fArray2[4] * fArray2[10] * fArray2[13] + fArray2[8] * fArray2[5] * fArray2[14] - fArray2[8] * fArray2[6] * fArray2[13] - fArray2[12] * fArray2[5] * fArray2[10] + fArray2[12] * fArray2[6] * fArray2[9];
        fArray[13] = fArray2[0] * fArray2[9] * fArray2[14] - fArray2[0] * fArray2[10] * fArray2[13] - fArray2[8] * fArray2[1] * fArray2[14] + fArray2[8] * fArray2[2] * fArray2[13] + fArray2[12] * fArray2[1] * fArray2[10] - fArray2[12] * fArray2[2] * fArray2[9];
        fArray[14] = -fArray2[0] * fArray2[5] * fArray2[14] + fArray2[0] * fArray2[6] * fArray2[13] + fArray2[4] * fArray2[1] * fArray2[14] - fArray2[4] * fArray2[2] * fArray2[13] - fArray2[12] * fArray2[1] * fArray2[6] + fArray2[12] * fArray2[2] * fArray2[5];
        fArray[15] = fArray2[0] * fArray2[5] * fArray2[10] - fArray2[0] * fArray2[6] * fArray2[9] - fArray2[4] * fArray2[1] * fArray2[10] + fArray2[4] * fArray2[2] * fArray2[9] + fArray2[8] * fArray2[1] * fArray2[6] - fArray2[8] * fArray2[2] * fArray2[5];
        float f = fArray2[0] * fArray[0] + fArray2[1] * fArray[4] + fArray2[2] * fArray[8] + fArray2[3] * fArray[12];
        if ((double)f != 0.0) {
            int n = 0;
            while (n < 16) {
                int n2 = n++;
                fArray[n2] = fArray[n2] / f;
            }
        } else {
            Arrays.fill(fArray, 0.0f);
        }
    }

    static void invertMat4FBFA(FloatBuffer floatBuffer, FloatBuffer floatBuffer2, float[] fArray, float[] fArray2) {
        floatBuffer2.get(fArray2);
        SMath.invertMat4(fArray, fArray2);
        floatBuffer.put(fArray);
    }
}

