/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

public class Vector2f {
    public static final Vector2f ZERO = new Vector2f(0.0f, 0.0f);
    public static final Vector2f ONE = new Vector2f(1.0f, 1.0f);
    public static final Vector2f UNIT_X = new Vector2f(1.0f, 0.0f);
    public static final Vector2f NEGATIVE_UNIT_X = new Vector2f(-1.0f, 0.0f);
    public static final Vector2f UNIT_Y = new Vector2f(0.0f, 1.0f);
    public static final Vector2f NEGATIVE_UNIT_Y = new Vector2f(0.0f, -1.0f);
    public static final Vector2f MAX = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
    public static final Vector2f MIN = new Vector2f(Float.MIN_VALUE, Float.MIN_VALUE);
    public float x;
    public float y;

    public Vector2f(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public boolean equals(Vector2f vector2f) {
        return this.x == vector2f.x && this.y == vector2f.y;
    }
}

