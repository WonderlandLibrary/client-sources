/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3i
 */
package net.ccbluex.liquidbounce.utils.particles;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class Vec3 {
    public double zCoord;
    public double xCoord;
    public double yCoord;

    public Vec3(Vec3i vec3i) {
        this(vec3i.func_177958_n(), vec3i.func_177956_o(), vec3i.func_177952_p());
    }

    public Vec3 rotateYaw(float f) {
        float f2 = MathHelper.func_76134_b((float)f);
        float f3 = MathHelper.func_76126_a((float)f);
        double d = this.xCoord * (double)f2 + this.zCoord * (double)f3;
        double d2 = this.yCoord;
        double d3 = this.zCoord * (double)f2 - this.xCoord * (double)f3;
        return new Vec3(d, d2, d3);
    }

    public Vec3(double d, double d2, double d3) {
        if (d == -0.0) {
            d = 0.0;
        }
        if (d2 == -0.0) {
            d2 = 0.0;
        }
        if (d3 == -0.0) {
            d3 = 0.0;
        }
        this.xCoord = d;
        this.yCoord = d2;
        this.zCoord = d3;
    }

    public double squareDistanceTo(Vec3 vec3) {
        double d = vec3.xCoord - this.xCoord;
        double d2 = vec3.yCoord - this.yCoord;
        double d3 = vec3.zCoord - this.zCoord;
        return d * d + d2 * d2 + d3 * d3;
    }

    public Vec3 subtractReverse(Vec3 vec3) {
        return new Vec3(vec3.xCoord - this.xCoord, vec3.yCoord - this.yCoord, vec3.zCoord - this.zCoord);
    }

    public String toString() {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }

    public Vec3 addVector(double d, double d2, double d3) {
        return new Vec3(this.xCoord + d, this.yCoord + d2, this.zCoord + d3);
    }

    public Vec3 getIntermediateWithZValue(Vec3 vec3, double d) {
        double d2 = vec3.xCoord - this.xCoord;
        double d3 = vec3.yCoord - this.yCoord;
        double d4 = vec3.zCoord - this.zCoord;
        if (d4 * d4 < (double)1.0E-7f) {
            return null;
        }
        double d5 = (d - this.zCoord) / d4;
        return d5 >= 0.0 && d5 <= 1.0 ? new Vec3(this.xCoord + d2 * d5, this.yCoord + d3 * d5, this.zCoord + d4 * d5) : null;
    }

    public Vec3 normalize() {
        double d = MathHelper.func_76133_a((double)(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord));
        return d < 1.0E-4 ? new Vec3(0.0, 0.0, 0.0) : new Vec3(this.xCoord / d, this.yCoord / d, this.zCoord / d);
    }

    public Vec3 subtract(double d, double d2, double d3) {
        return this.addVector(-d, -d2, -d3);
    }

    public Vec3 crossProduct(Vec3 vec3) {
        return new Vec3(this.yCoord * vec3.zCoord - this.zCoord * vec3.yCoord, this.zCoord * vec3.xCoord - this.xCoord * vec3.zCoord, this.xCoord * vec3.yCoord - this.yCoord * vec3.xCoord);
    }

    public double dotProduct(Vec3 vec3) {
        return this.xCoord * vec3.xCoord + this.yCoord * vec3.yCoord + this.zCoord * vec3.zCoord;
    }

    public double distanceTo(Vec3 vec3) {
        double d = vec3.xCoord - this.xCoord;
        double d2 = vec3.yCoord - this.yCoord;
        double d3 = vec3.zCoord - this.zCoord;
        return MathHelper.func_76133_a((double)(d * d + d2 * d2 + d3 * d3));
    }

    public Vec3 add(Vec3 vec3) {
        return this.addVector(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public double lengthVector() {
        return MathHelper.func_76133_a((double)(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord));
    }

    public Vec3 subtract(Vec3 vec3) {
        return this.subtract(vec3.xCoord, vec3.yCoord, vec3.zCoord);
    }

    public Vec3 getIntermediateWithXValue(Vec3 vec3, double d) {
        double d2 = vec3.xCoord - this.xCoord;
        double d3 = vec3.yCoord - this.yCoord;
        double d4 = vec3.zCoord - this.zCoord;
        if (d2 * d2 < (double)1.0E-7f) {
            return null;
        }
        double d5 = (d - this.xCoord) / d2;
        return d5 >= 0.0 && d5 <= 1.0 ? new Vec3(this.xCoord + d2 * d5, this.yCoord + d3 * d5, this.zCoord + d4 * d5) : null;
    }

    public Vec3 getIntermediateWithYValue(Vec3 vec3, double d) {
        double d2 = vec3.xCoord - this.xCoord;
        double d3 = vec3.yCoord - this.yCoord;
        double d4 = vec3.zCoord - this.zCoord;
        if (d3 * d3 < (double)1.0E-7f) {
            return null;
        }
        double d5 = (d - this.yCoord) / d3;
        return d5 >= 0.0 && d5 <= 1.0 ? new Vec3(this.xCoord + d2 * d5, this.yCoord + d3 * d5, this.zCoord + d4 * d5) : null;
    }

    public Vec3 rotatePitch(float f) {
        float f2 = MathHelper.func_76134_b((float)f);
        float f3 = MathHelper.func_76126_a((float)f);
        double d = this.xCoord;
        double d2 = this.yCoord * (double)f2 + this.zCoord * (double)f3;
        double d3 = this.zCoord * (double)f2 - this.yCoord * (double)f3;
        return new Vec3(d, d2, d3);
    }
}

