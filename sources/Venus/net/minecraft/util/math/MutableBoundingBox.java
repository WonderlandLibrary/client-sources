/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import com.google.common.base.MoreObjects;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

public class MutableBoundingBox {
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;

    public MutableBoundingBox() {
    }

    public MutableBoundingBox(int[] nArray) {
        if (nArray.length == 6) {
            this.minX = nArray[0];
            this.minY = nArray[1];
            this.minZ = nArray[2];
            this.maxX = nArray[3];
            this.maxY = nArray[4];
            this.maxZ = nArray[5];
        }
    }

    public static MutableBoundingBox getNewBoundingBox() {
        return new MutableBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static MutableBoundingBox func_236990_b_() {
        return new MutableBoundingBox(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static MutableBoundingBox getComponentToAddBoundingBox(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, Direction direction) {
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                return new MutableBoundingBox(n + n4, n2 + n5, n3 - n9 + 1 + n6, n + n7 - 1 + n4, n2 + n8 - 1 + n5, n3 + n6);
            }
            case 2: {
                return new MutableBoundingBox(n + n4, n2 + n5, n3 + n6, n + n7 - 1 + n4, n2 + n8 - 1 + n5, n3 + n9 - 1 + n6);
            }
            case 3: {
                return new MutableBoundingBox(n - n9 + 1 + n6, n2 + n5, n3 + n4, n + n6, n2 + n8 - 1 + n5, n3 + n7 - 1 + n4);
            }
            case 4: {
                return new MutableBoundingBox(n + n6, n2 + n5, n3 + n4, n + n9 - 1 + n6, n2 + n8 - 1 + n5, n3 + n7 - 1 + n4);
            }
        }
        return new MutableBoundingBox(n + n4, n2 + n5, n3 + n6, n + n7 - 1 + n4, n2 + n8 - 1 + n5, n3 + n9 - 1 + n6);
    }

    public static MutableBoundingBox createProper(int n, int n2, int n3, int n4, int n5, int n6) {
        return new MutableBoundingBox(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6), Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6));
    }

    public MutableBoundingBox(MutableBoundingBox mutableBoundingBox) {
        this.minX = mutableBoundingBox.minX;
        this.minY = mutableBoundingBox.minY;
        this.minZ = mutableBoundingBox.minZ;
        this.maxX = mutableBoundingBox.maxX;
        this.maxY = mutableBoundingBox.maxY;
        this.maxZ = mutableBoundingBox.maxZ;
    }

    public MutableBoundingBox(int n, int n2, int n3, int n4, int n5, int n6) {
        this.minX = n;
        this.minY = n2;
        this.minZ = n3;
        this.maxX = n4;
        this.maxY = n5;
        this.maxZ = n6;
    }

    public MutableBoundingBox(Vector3i vector3i, Vector3i vector3i2) {
        this.minX = Math.min(vector3i.getX(), vector3i2.getX());
        this.minY = Math.min(vector3i.getY(), vector3i2.getY());
        this.minZ = Math.min(vector3i.getZ(), vector3i2.getZ());
        this.maxX = Math.max(vector3i.getX(), vector3i2.getX());
        this.maxY = Math.max(vector3i.getY(), vector3i2.getY());
        this.maxZ = Math.max(vector3i.getZ(), vector3i2.getZ());
    }

    public MutableBoundingBox(int n, int n2, int n3, int n4) {
        this.minX = n;
        this.minZ = n2;
        this.maxX = n3;
        this.maxZ = n4;
        this.minY = 1;
        this.maxY = 512;
    }

    public boolean intersectsWith(MutableBoundingBox mutableBoundingBox) {
        return this.maxX >= mutableBoundingBox.minX && this.minX <= mutableBoundingBox.maxX && this.maxZ >= mutableBoundingBox.minZ && this.minZ <= mutableBoundingBox.maxZ && this.maxY >= mutableBoundingBox.minY && this.minY <= mutableBoundingBox.maxY;
    }

    public boolean intersectsWith(int n, int n2, int n3, int n4) {
        return this.maxX >= n && this.minX <= n3 && this.maxZ >= n2 && this.minZ <= n4;
    }

    public void expandTo(MutableBoundingBox mutableBoundingBox) {
        this.minX = Math.min(this.minX, mutableBoundingBox.minX);
        this.minY = Math.min(this.minY, mutableBoundingBox.minY);
        this.minZ = Math.min(this.minZ, mutableBoundingBox.minZ);
        this.maxX = Math.max(this.maxX, mutableBoundingBox.maxX);
        this.maxY = Math.max(this.maxY, mutableBoundingBox.maxY);
        this.maxZ = Math.max(this.maxZ, mutableBoundingBox.maxZ);
    }

    public void offset(int n, int n2, int n3) {
        this.minX += n;
        this.minY += n2;
        this.minZ += n3;
        this.maxX += n;
        this.maxY += n2;
        this.maxZ += n3;
    }

    public MutableBoundingBox func_215127_b(int n, int n2, int n3) {
        return new MutableBoundingBox(this.minX + n, this.minY + n2, this.minZ + n3, this.maxX + n, this.maxY + n2, this.maxZ + n3);
    }

    public void func_236989_a_(Vector3i vector3i) {
        this.offset(vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    public boolean isVecInside(Vector3i vector3i) {
        return vector3i.getX() >= this.minX && vector3i.getX() <= this.maxX && vector3i.getZ() >= this.minZ && vector3i.getZ() <= this.maxZ && vector3i.getY() >= this.minY && vector3i.getY() <= this.maxY;
    }

    public Vector3i getLength() {
        return new Vector3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
    }

    public int getXSize() {
        return this.maxX - this.minX + 1;
    }

    public int getYSize() {
        return this.maxY - this.minY + 1;
    }

    public int getZSize() {
        return this.maxZ - this.minZ + 1;
    }

    public Vector3i func_215126_f() {
        return new BlockPos(this.minX + (this.maxX - this.minX + 1) / 2, this.minY + (this.maxY - this.minY + 1) / 2, this.minZ + (this.maxZ - this.minZ + 1) / 2);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x0", this.minX).add("y0", this.minY).add("z0", this.minZ).add("x1", this.maxX).add("y1", this.maxY).add("z1", this.maxZ).toString();
    }

    public IntArrayNBT toNBTTagIntArray() {
        return new IntArrayNBT(new int[]{this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
    }
}

