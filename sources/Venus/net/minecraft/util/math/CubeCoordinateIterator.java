/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

public class CubeCoordinateIterator {
    private int startX;
    private int startY;
    private int startZ;
    private int xWidth;
    private int yHeight;
    private int zWidth;
    private int totalAmount;
    private int currentAmount;
    private int x;
    private int y;
    private int z;

    public CubeCoordinateIterator(int n, int n2, int n3, int n4, int n5, int n6) {
        this.startX = n;
        this.startY = n2;
        this.startZ = n3;
        this.xWidth = n4 - n + 1;
        this.yHeight = n5 - n2 + 1;
        this.zWidth = n6 - n3 + 1;
        this.totalAmount = this.xWidth * this.yHeight * this.zWidth;
    }

    public boolean hasNext() {
        if (this.currentAmount == this.totalAmount) {
            return true;
        }
        this.x = this.currentAmount % this.xWidth;
        int n = this.currentAmount / this.xWidth;
        this.y = n % this.yHeight;
        this.z = n / this.yHeight;
        ++this.currentAmount;
        return false;
    }

    public int getX() {
        return this.startX + this.x;
    }

    public int getY() {
        return this.startY + this.y;
    }

    public int getZ() {
        return this.startZ + this.z;
    }

    public int numBoundariesTouched() {
        int n = 0;
        if (this.x == 0 || this.x == this.xWidth - 1) {
            ++n;
        }
        if (this.y == 0 || this.y == this.yHeight - 1) {
            ++n;
        }
        if (this.z == 0 || this.z == this.zWidth - 1) {
            ++n;
        }
        return n;
    }
}

