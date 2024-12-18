/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.util.MathHelper;
import shadersmod.client.SMath;
import shadersmod.client.Shaders;

public class ClippingHelperShadow
extends ClippingHelper {
    private static ClippingHelperShadow instance = new ClippingHelperShadow();
    float[] frustumTest = new float[6];
    float[][] shadowClipPlanes = new float[10][4];
    int shadowClipPlaneCount;
    float[] matInvMP = new float[16];
    float[] vecIntersection = new float[4];

    @Override
    public boolean isBoxInFrustum(double x1, double y1, double z1, double x2, double y2, double z2) {
        for (int index = 0; index < this.shadowClipPlaneCount; ++index) {
            float[] plane = this.shadowClipPlanes[index];
            if (!(this.dot4(plane, x1, y1, z1) <= 0.0) || !(this.dot4(plane, x2, y1, z1) <= 0.0) || !(this.dot4(plane, x1, y2, z1) <= 0.0) || !(this.dot4(plane, x2, y2, z1) <= 0.0) || !(this.dot4(plane, x1, y1, z2) <= 0.0) || !(this.dot4(plane, x2, y1, z2) <= 0.0) || !(this.dot4(plane, x1, y2, z2) <= 0.0) || !(this.dot4(plane, x2, y2, z2) <= 0.0)) continue;
            return false;
        }
        return true;
    }

    private double dot4(float[] plane, double x2, double y2, double z2) {
        return (double)plane[0] * x2 + (double)plane[1] * y2 + (double)plane[2] * z2 + (double)plane[3];
    }

    private double dot3(float[] vecA, float[] vecB) {
        return (double)vecA[0] * (double)vecB[0] + (double)vecA[1] * (double)vecB[1] + (double)vecA[2] * (double)vecB[2];
    }

    public static ClippingHelper getInstance() {
        instance.init();
        return instance;
    }

    private void normalizePlane(float[] plane) {
        float length = MathHelper.sqrt_float(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
        float[] arrf = plane;
        arrf[0] = arrf[0] / length;
        float[] arrf2 = plane;
        arrf2[1] = arrf2[1] / length;
        float[] arrf3 = plane;
        arrf3[2] = arrf3[2] / length;
        float[] arrf4 = plane;
        arrf4[3] = arrf4[3] / length;
    }

    private void normalize3(float[] plane) {
        float length = MathHelper.sqrt_float(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
        if (length == 0.0f) {
            length = 1.0f;
        }
        float[] arrf = plane;
        arrf[0] = arrf[0] / length;
        float[] arrf2 = plane;
        arrf2[1] = arrf2[1] / length;
        float[] arrf3 = plane;
        arrf3[2] = arrf3[2] / length;
    }

    private void assignPlane(float[] plane, float a2, float b2, float c2, float d2) {
        float length = (float)Math.sqrt(a2 * a2 + b2 * b2 + c2 * c2);
        plane[0] = a2 / length;
        plane[1] = b2 / length;
        plane[2] = c2 / length;
        plane[3] = d2 / length;
    }

    private void copyPlane(float[] dst, float[] src) {
        dst[0] = src[0];
        dst[1] = src[1];
        dst[2] = src[2];
        dst[3] = src[3];
    }

    private void cross3(float[] out, float[] a2, float[] b2) {
        out[0] = a2[1] * b2[2] - a2[2] * b2[1];
        out[1] = a2[2] * b2[0] - a2[0] * b2[2];
        out[2] = a2[0] * b2[1] - a2[1] * b2[0];
    }

    private void addShadowClipPlane(float[] plane) {
        this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], plane);
    }

    private float length(float x2, float y2, float z2) {
        return (float)Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2);
    }

    private float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
        return this.length(x1 - x2, y1 - y2, z1 - z2);
    }

    private void makeShadowPlane(float[] shadowPlane, float[] positivePlane, float[] negativePlane, float[] vecSun) {
        this.cross3(this.vecIntersection, positivePlane, negativePlane);
        this.cross3(shadowPlane, this.vecIntersection, vecSun);
        this.normalize3(shadowPlane);
        float dotPN = (float)this.dot3(positivePlane, negativePlane);
        float dotSN = (float)this.dot3(shadowPlane, negativePlane);
        float disSN = this.distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * dotSN, negativePlane[1] * dotSN, negativePlane[2] * dotSN);
        float disPN = this.distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * dotPN, negativePlane[1] * dotPN, negativePlane[2] * dotPN);
        float k1 = disSN / disPN;
        float dotSP = (float)this.dot3(shadowPlane, positivePlane);
        float disSP = this.distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * dotSP, positivePlane[1] * dotSP, positivePlane[2] * dotSP);
        float disNP = this.distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * dotPN, positivePlane[1] * dotPN, positivePlane[2] * dotPN);
        float k2 = disSP / disNP;
        shadowPlane[3] = positivePlane[3] * k1 + negativePlane[3] * k2;
    }

    public void init() {
        float[] matPrj = this.field_178625_b;
        float[] matMdv = this.field_178626_c;
        float[] matMP = this.clippingMatrix;
        System.arraycopy(Shaders.faProjection, 0, matPrj, 0, 16);
        System.arraycopy(Shaders.faModelView, 0, matMdv, 0, 16);
        SMath.multiplyMat4xMat4(matMP, matMdv, matPrj);
        this.assignPlane(this.frustum[0], matMP[3] - matMP[0], matMP[7] - matMP[4], matMP[11] - matMP[8], matMP[15] - matMP[12]);
        this.assignPlane(this.frustum[1], matMP[3] + matMP[0], matMP[7] + matMP[4], matMP[11] + matMP[8], matMP[15] + matMP[12]);
        this.assignPlane(this.frustum[2], matMP[3] + matMP[1], matMP[7] + matMP[5], matMP[11] + matMP[9], matMP[15] + matMP[13]);
        this.assignPlane(this.frustum[3], matMP[3] - matMP[1], matMP[7] - matMP[5], matMP[11] - matMP[9], matMP[15] - matMP[13]);
        this.assignPlane(this.frustum[4], matMP[3] - matMP[2], matMP[7] - matMP[6], matMP[11] - matMP[10], matMP[15] - matMP[14]);
        this.assignPlane(this.frustum[5], matMP[3] + matMP[2], matMP[7] + matMP[6], matMP[11] + matMP[10], matMP[15] + matMP[14]);
        float[] vecSun = Shaders.shadowLightPositionVector;
        float test0 = (float)this.dot3(this.frustum[0], vecSun);
        float test1 = (float)this.dot3(this.frustum[1], vecSun);
        float test2 = (float)this.dot3(this.frustum[2], vecSun);
        float test3 = (float)this.dot3(this.frustum[3], vecSun);
        float test4 = (float)this.dot3(this.frustum[4], vecSun);
        float test5 = (float)this.dot3(this.frustum[5], vecSun);
        this.shadowClipPlaneCount = 0;
        if (test0 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0]);
            if (test0 > 0.0f) {
                if (test2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[2], vecSun);
                }
                if (test3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[3], vecSun);
                }
                if (test4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[4], vecSun);
                }
                if (test5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[5], vecSun);
                }
            }
        }
        if (test1 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1]);
            if (test1 > 0.0f) {
                if (test2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[2], vecSun);
                }
                if (test3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[3], vecSun);
                }
                if (test4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[4], vecSun);
                }
                if (test5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[5], vecSun);
                }
            }
        }
        if (test2 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2]);
            if (test2 > 0.0f) {
                if (test0 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[0], vecSun);
                }
                if (test1 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[1], vecSun);
                }
                if (test4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[4], vecSun);
                }
                if (test5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[5], vecSun);
                }
            }
        }
        if (test3 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3]);
            if (test3 > 0.0f) {
                if (test0 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[0], vecSun);
                }
                if (test1 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[1], vecSun);
                }
                if (test4 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[4], vecSun);
                }
                if (test5 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[5], vecSun);
                }
            }
        }
        if (test4 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4]);
            if (test4 > 0.0f) {
                if (test0 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[0], vecSun);
                }
                if (test1 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[1], vecSun);
                }
                if (test2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[2], vecSun);
                }
                if (test3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[3], vecSun);
                }
            }
        }
        if (test5 >= 0.0f) {
            this.copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5]);
            if (test5 > 0.0f) {
                if (test0 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[0], vecSun);
                }
                if (test1 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[1], vecSun);
                }
                if (test2 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[2], vecSun);
                }
                if (test3 < 0.0f) {
                    this.makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[3], vecSun);
                }
            }
        }
    }
}

