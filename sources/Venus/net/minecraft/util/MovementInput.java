/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.math.vector.Vector2f;

public class MovementInput {
    public static float moveStrafe;
    public float moveForward;
    public boolean forwardKeyDown;
    public boolean backKeyDown;
    public boolean leftKeyDown;
    public boolean rightKeyDown;
    public boolean jump;
    public boolean sneaking;

    public void tickMovement(boolean bl) {
    }

    public Vector2f getMoveVector() {
        return new Vector2f(moveStrafe, this.moveForward);
    }

    public boolean isMovingForward() {
        return this.moveForward > 1.0E-5f;
    }
}

