/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class SMath {
    static void multiplyMat4xMat4(float[] matOut, float[] matA, float[] matB) {
        for (int co2 = 0; co2 < 4; ++co2) {
            for (int ro2 = 0; ro2 < 4; ++ro2) {
                matOut[4 * co2 + ro2] = matA[4 * co2 + 0] * matB[0 + ro2] + matA[4 * co2 + 1] * matB[4 + ro2] + matA[4 * co2 + 2] * matB[8 + ro2] + matA[4 * co2 + 3] * matB[12 + ro2];
            }
        }
    }

    static void multiplyMat4xVec4(float[] vecOut, float[] matA, float[] vecB) {
        vecOut[0] = matA[0] * vecB[0] + matA[4] * vecB[1] + matA[8] * vecB[2] + matA[12] * vecB[3];
        vecOut[1] = matA[1] * vecB[0] + matA[5] * vecB[1] + matA[9] * vecB[2] + matA[13] * vecB[3];
        vecOut[2] = matA[2] * vecB[0] + matA[6] * vecB[1] + matA[10] * vecB[2] + matA[14] * vecB[3];
        vecOut[3] = matA[3] * vecB[0] + matA[7] * vecB[1] + matA[11] * vecB[2] + matA[15] * vecB[3];
    }

    static void invertMat4(float[] matOut, float[] m2) {
        matOut[0] = m2[5] * m2[10] * m2[15] - m2[5] * m2[11] * m2[14] - m2[9] * m2[6] * m2[15] + m2[9] * m2[7] * m2[14] + m2[13] * m2[6] * m2[11] - m2[13] * m2[7] * m2[10];
        matOut[1] = -m2[1] * m2[10] * m2[15] + m2[1] * m2[11] * m2[14] + m2[9] * m2[2] * m2[15] - m2[9] * m2[3] * m2[14] - m2[13] * m2[2] * m2[11] + m2[13] * m2[3] * m2[10];
        matOut[2] = m2[1] * m2[6] * m2[15] - m2[1] * m2[7] * m2[14] - m2[5] * m2[2] * m2[15] + m2[5] * m2[3] * m2[14] + m2[13] * m2[2] * m2[7] - m2[13] * m2[3] * m2[6];
        matOut[3] = -m2[1] * m2[6] * m2[11] + m2[1] * m2[7] * m2[10] + m2[5] * m2[2] * m2[11] - m2[5] * m2[3] * m2[10] - m2[9] * m2[2] * m2[7] + m2[9] * m2[3] * m2[6];
        matOut[4] = -m2[4] * m2[10] * m2[15] + m2[4] * m2[11] * m2[14] + m2[8] * m2[6] * m2[15] - m2[8] * m2[7] * m2[14] - m2[12] * m2[6] * m2[11] + m2[12] * m2[7] * m2[10];
        matOut[5] = m2[0] * m2[10] * m2[15] - m2[0] * m2[11] * m2[14] - m2[8] * m2[2] * m2[15] + m2[8] * m2[3] * m2[14] + m2[12] * m2[2] * m2[11] - m2[12] * m2[3] * m2[10];
        matOut[6] = -m2[0] * m2[6] * m2[15] + m2[0] * m2[7] * m2[14] + m2[4] * m2[2] * m2[15] - m2[4] * m2[3] * m2[14] - m2[12] * m2[2] * m2[7] + m2[12] * m2[3] * m2[6];
        matOut[7] = m2[0] * m2[6] * m2[11] - m2[0] * m2[7] * m2[10] - m2[4] * m2[2] * m2[11] + m2[4] * m2[3] * m2[10] + m2[8] * m2[2] * m2[7] - m2[8] * m2[3] * m2[6];
        matOut[8] = m2[4] * m2[9] * m2[15] - m2[4] * m2[11] * m2[13] - m2[8] * m2[5] * m2[15] + m2[8] * m2[7] * m2[13] + m2[12] * m2[5] * m2[11] - m2[12] * m2[7] * m2[9];
        matOut[9] = -m2[0] * m2[9] * m2[15] + m2[0] * m2[11] * m2[13] + m2[8] * m2[1] * m2[15] - m2[8] * m2[3] * m2[13] - m2[12] * m2[1] * m2[11] + m2[12] * m2[3] * m2[9];
        matOut[10] = m2[0] * m2[5] * m2[15] - m2[0] * m2[7] * m2[13] - m2[4] * m2[1] * m2[15] + m2[4] * m2[3] * m2[13] + m2[12] * m2[1] * m2[7] - m2[12] * m2[3] * m2[5];
        matOut[11] = -m2[0] * m2[5] * m2[11] + m2[0] * m2[7] * m2[9] + m2[4] * m2[1] * m2[11] - m2[4] * m2[3] * m2[9] - m2[8] * m2[1] * m2[7] + m2[8] * m2[3] * m2[5];
        matOut[12] = -m2[4] * m2[9] * m2[14] + m2[4] * m2[10] * m2[13] + m2[8] * m2[5] * m2[14] - m2[8] * m2[6] * m2[13] - m2[12] * m2[5] * m2[10] + m2[12] * m2[6] * m2[9];
        matOut[13] = m2[0] * m2[9] * m2[14] - m2[0] * m2[10] * m2[13] - m2[8] * m2[1] * m2[14] + m2[8] * m2[2] * m2[13] + m2[12] * m2[1] * m2[10] - m2[12] * m2[2] * m2[9];
        matOut[14] = -m2[0] * m2[5] * m2[14] + m2[0] * m2[6] * m2[13] + m2[4] * m2[1] * m2[14] - m2[4] * m2[2] * m2[13] - m2[12] * m2[1] * m2[6] + m2[12] * m2[2] * m2[5];
        matOut[15] = m2[0] * m2[5] * m2[10] - m2[0] * m2[6] * m2[9] - m2[4] * m2[1] * m2[10] + m2[4] * m2[2] * m2[9] + m2[8] * m2[1] * m2[6] - m2[8] * m2[2] * m2[5];
        float det = m2[0] * matOut[0] + m2[1] * matOut[4] + m2[2] * matOut[8] + m2[3] * matOut[12];
        if ((double)det != 0.0) {
            int i2 = 0;
            while (i2 < 16) {
                float[] arrf = matOut;
                int n2 = i2++;
                arrf[n2] = arrf[n2] / det;
            }
        } else {
            Arrays.fill(matOut, 0.0f);
        }
    }

    static void invertMat4FBFA(FloatBuffer fbInvOut, FloatBuffer fbMatIn, float[] faInv, float[] faMat) {
        fbMatIn.get(faMat);
        SMath.invertMat4(faInv, faMat);
        fbInvOut.put(faInv);
    }
}

