/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.commons.lang3.tuple.Triple;

public final class TransformationMatrix {
    private final Matrix4f matrix;
    private boolean decomposed;
    @Nullable
    private Vector3f translation;
    @Nullable
    private Quaternion rotationLeft;
    @Nullable
    private Vector3f scale;
    @Nullable
    private Quaternion rotationRight;
    private static final TransformationMatrix IDENTITY = Util.make(TransformationMatrix::lambda$static$0);

    public TransformationMatrix(@Nullable Matrix4f matrix4f) {
        this.matrix = matrix4f == null ? TransformationMatrix.IDENTITY.matrix : matrix4f;
    }

    public TransformationMatrix(@Nullable Vector3f vector3f, @Nullable Quaternion quaternion, @Nullable Vector3f vector3f2, @Nullable Quaternion quaternion2) {
        this.matrix = TransformationMatrix.composeVanilla(vector3f, quaternion, vector3f2, quaternion2);
        this.translation = vector3f != null ? vector3f : new Vector3f();
        this.rotationLeft = quaternion != null ? quaternion : Quaternion.ONE.copy();
        this.scale = vector3f2 != null ? vector3f2 : new Vector3f(1.0f, 1.0f, 1.0f);
        this.rotationRight = quaternion2 != null ? quaternion2 : Quaternion.ONE.copy();
        this.decomposed = true;
    }

    public static TransformationMatrix identity() {
        return IDENTITY;
    }

    public TransformationMatrix composeVanilla(TransformationMatrix transformationMatrix) {
        Matrix4f matrix4f = this.getMatrix();
        matrix4f.mul(transformationMatrix.getMatrix());
        return new TransformationMatrix(matrix4f);
    }

    @Nullable
    public TransformationMatrix inverseVanilla() {
        if (this == IDENTITY) {
            return this;
        }
        Matrix4f matrix4f = this.getMatrix();
        return matrix4f.invert() ? new TransformationMatrix(matrix4f) : null;
    }

    private void decompose() {
        if (!this.decomposed) {
            Pair<Matrix3f, Vector3f> pair = TransformationMatrix.affine(this.matrix);
            Triple<Quaternion, Vector3f, Quaternion> triple = pair.getFirst().svdDecompose();
            this.translation = pair.getSecond();
            this.rotationLeft = triple.getLeft();
            this.scale = triple.getMiddle();
            this.rotationRight = triple.getRight();
            this.decomposed = true;
        }
    }

    private static Matrix4f composeVanilla(@Nullable Vector3f vector3f, @Nullable Quaternion quaternion, @Nullable Vector3f vector3f2, @Nullable Quaternion quaternion2) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        if (quaternion != null) {
            matrix4f.mul(new Matrix4f(quaternion));
        }
        if (vector3f2 != null) {
            matrix4f.mul(Matrix4f.makeScale(vector3f2.getX(), vector3f2.getY(), vector3f2.getZ()));
        }
        if (quaternion2 != null) {
            matrix4f.mul(new Matrix4f(quaternion2));
        }
        if (vector3f != null) {
            matrix4f.m03 = vector3f.getX();
            matrix4f.m13 = vector3f.getY();
            matrix4f.m23 = vector3f.getZ();
        }
        return matrix4f;
    }

    public static Pair<Matrix3f, Vector3f> affine(Matrix4f matrix4f) {
        matrix4f.mul(1.0f / matrix4f.m33);
        Vector3f vector3f = new Vector3f(matrix4f.m03, matrix4f.m13, matrix4f.m23);
        Matrix3f matrix3f = new Matrix3f(matrix4f);
        return Pair.of(matrix3f, vector3f);
    }

    public Matrix4f getMatrix() {
        return this.matrix.copy();
    }

    public Quaternion getRotationLeft() {
        this.decompose();
        return this.rotationLeft.copy();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            TransformationMatrix transformationMatrix = (TransformationMatrix)object;
            return Objects.equals(this.matrix, transformationMatrix.matrix);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.matrix);
    }

    private static TransformationMatrix lambda$static$0() {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        TransformationMatrix transformationMatrix = new TransformationMatrix(matrix4f);
        transformationMatrix.getRotationLeft();
        return transformationMatrix;
    }
}

