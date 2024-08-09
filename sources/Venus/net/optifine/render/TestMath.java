/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import java.util.Random;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;

public class TestMath {
    static Random random = new Random();

    public static void main(String[] stringArray) {
        int n = 1000000;
        TestMath.dbg("Test math: " + n);
        for (int i = 0; i < 1000000; ++i) {
            TestMath.testMatrix4f_mulTranslate();
            TestMath.testMatrix4f_mulScale();
            TestMath.testMatrix4f_mulQuaternion();
            TestMath.testMatrix3f_mulQuaternion();
            TestMath.testVector4f_transform();
            TestMath.testVector3f_transform();
        }
        TestMath.dbg("Done");
    }

    private static void testMatrix4f_mulTranslate() {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setRandom(random);
        Matrix4f matrix4f2 = matrix4f.copy();
        float f = random.nextFloat();
        float f2 = random.nextFloat();
        float f3 = random.nextFloat();
        matrix4f.mul(Matrix4f.makeTranslate(f, f2, f3));
        matrix4f2.mulTranslate(f, f2, f3);
        if (!matrix4f2.equals(matrix4f)) {
            TestMath.dbg("*** DIFFERENT ***");
            TestMath.dbg(matrix4f.toString());
            TestMath.dbg(matrix4f2.toString());
        }
    }

    private static void testMatrix4f_mulScale() {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setRandom(random);
        Matrix4f matrix4f2 = matrix4f.copy();
        float f = random.nextFloat();
        float f2 = random.nextFloat();
        float f3 = random.nextFloat();
        matrix4f.mul(Matrix4f.makeScale(f, f2, f3));
        matrix4f2.mulScale(f, f2, f3);
        if (!matrix4f2.equals(matrix4f)) {
            TestMath.dbg("*** DIFFERENT ***");
            TestMath.dbg(matrix4f.toString());
            TestMath.dbg(matrix4f2.toString());
        }
    }

    private static void testMatrix4f_mulQuaternion() {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setRandom(random);
        Matrix4f matrix4f2 = matrix4f.copy();
        Quaternion quaternion = new Quaternion(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
        matrix4f.mul(new Matrix4f(quaternion));
        matrix4f2.mul(quaternion);
        if (!matrix4f2.equals(matrix4f)) {
            TestMath.dbg("*** DIFFERENT ***");
            TestMath.dbg(matrix4f.toString());
            TestMath.dbg(matrix4f2.toString());
        }
    }

    private static void testMatrix3f_mulQuaternion() {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.setRandom(random);
        Matrix3f matrix3f2 = matrix3f.copy();
        Quaternion quaternion = new Quaternion(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
        matrix3f.mul(new Matrix3f(quaternion));
        matrix3f2.mul(quaternion);
        if (!matrix3f2.equals(matrix3f)) {
            TestMath.dbg("*** DIFFERENT ***");
            TestMath.dbg(matrix3f.toString());
            TestMath.dbg(matrix3f2.toString());
        }
    }

    private static void testVector3f_transform() {
        Vector3f vector3f = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
        Vector3f vector3f2 = vector3f.copy();
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.setRandom(random);
        vector3f.transform(matrix3f);
        float f = matrix3f.getTransformX(vector3f2.getX(), vector3f2.getY(), vector3f2.getZ());
        float f2 = matrix3f.getTransformY(vector3f2.getX(), vector3f2.getY(), vector3f2.getZ());
        float f3 = matrix3f.getTransformZ(vector3f2.getX(), vector3f2.getY(), vector3f2.getZ());
        vector3f2 = new Vector3f(f, f2, f3);
        if (!vector3f2.equals(vector3f)) {
            TestMath.dbg("*** DIFFERENT ***");
            TestMath.dbg(vector3f.toString());
            TestMath.dbg(vector3f2.toString());
        }
    }

    private static void testVector4f_transform() {
        Vector4f vector4f = new Vector4f(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
        Vector4f vector4f2 = new Vector4f(vector4f.getX(), vector4f.getY(), vector4f.getZ(), vector4f.getW());
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setRandom(random);
        vector4f.transform(matrix4f);
        float f = matrix4f.getTransformX(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), vector4f2.getW());
        float f2 = matrix4f.getTransformY(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), vector4f2.getW());
        float f3 = matrix4f.getTransformZ(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), vector4f2.getW());
        float f4 = matrix4f.getTransformW(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), vector4f2.getW());
        vector4f2 = new Vector4f(f, f2, f3, f4);
        if (!vector4f2.equals(vector4f)) {
            TestMath.dbg("*** DIFFERENT ***");
            TestMath.dbg(vector4f.toString());
            TestMath.dbg(vector4f2.toString());
        }
    }

    private static void dbg(String string) {
        System.out.println(string);
    }
}

