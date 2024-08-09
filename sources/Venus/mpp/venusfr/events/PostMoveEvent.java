/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

public class PostMoveEvent {
    private double horizontalMove;

    public double getHorizontalMove() {
        return this.horizontalMove;
    }

    public void setHorizontalMove(double d) {
        this.horizontalMove = d;
    }

    public PostMoveEvent(double d) {
        this.horizontalMove = d;
    }
}

