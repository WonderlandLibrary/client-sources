/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.matrix;

import com.google.common.collect.Queues;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;

public class MatrixStack {
    Deque<Entry> freeEntries = new ArrayDeque<Entry>();
    private final Deque<Entry> stack = Util.make(Queues.newArrayDeque(), MatrixStack::lambda$new$0);

    public void translate(double d, double d2, double d3) {
        Entry entry = this.stack.getLast();
        entry.matrix.mulTranslate((float)d, (float)d2, (float)d3);
    }

    public void scale(float f, float f2, float f3) {
        Entry entry = this.stack.getLast();
        entry.matrix.mulScale(f, f2, f3);
        if (f == f2 && f2 == f3) {
            if (f > 0.0f) {
                return;
            }
            entry.normal.mul(-1.0f);
        }
        float f4 = 1.0f / f;
        float f5 = 1.0f / f2;
        float f6 = 1.0f / f3;
        float f7 = MathHelper.fastInvCubeRoot(f4 * f5 * f6);
        entry.normal.mul(Matrix3f.makeScaleMatrix(f7 * f4, f7 * f5, f7 * f6));
    }

    public void rotate(Quaternion quaternion) {
        Entry entry = this.stack.getLast();
        entry.matrix.mul(quaternion);
        entry.normal.mul(quaternion);
    }

    public void push() {
        Entry entry = this.stack.getLast();
        Entry entry2 = this.freeEntries.pollLast();
        if (entry2 == null) {
            entry2 = new Entry(entry.matrix.copy(), entry.normal.copy());
        } else {
            entry2.matrix.set(entry.matrix);
            entry2.normal.set(entry.normal);
        }
        this.stack.addLast(entry2);
    }

    public void pop() {
        Entry entry = this.stack.removeLast();
        if (entry != null) {
            this.freeEntries.add(entry);
        }
    }

    public Entry getLast() {
        return this.stack.getLast();
    }

    public boolean clear() {
        return this.stack.size() == 1;
    }

    public String toString() {
        return this.getLast().toString();
    }

    private static void lambda$new$0(ArrayDeque arrayDeque) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.setIdentity();
        arrayDeque.add(new Entry(matrix4f, matrix3f));
    }

    public static final class Entry {
        private final Matrix4f matrix;
        private final Matrix3f normal;

        private Entry(Matrix4f matrix4f, Matrix3f matrix3f) {
            this.matrix = matrix4f;
            this.normal = matrix3f;
        }

        public Matrix4f getMatrix() {
            return this.matrix;
        }

        public Matrix3f getNormal() {
            return this.normal;
        }

        public String toString() {
            return this.matrix.toString() + this.normal.toString();
        }
    }
}

