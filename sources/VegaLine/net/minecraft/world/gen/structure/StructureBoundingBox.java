/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.structure;

import com.google.common.base.MoreObjects;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;

public class StructureBoundingBox {
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;

    public StructureBoundingBox() {
    }

    public StructureBoundingBox(int[] coords) {
        if (coords.length == 6) {
            this.minX = coords[0];
            this.minY = coords[1];
            this.minZ = coords[2];
            this.maxX = coords[3];
            this.maxY = coords[4];
            this.maxZ = coords[5];
        }
    }

    public static StructureBoundingBox getNewBoundingBox() {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static StructureBoundingBox getComponentToAddBoundingBox(int structureMinX, int structureMinY, int structureMinZ, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, EnumFacing facing) {
        switch (facing) {
            case NORTH: {
                return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ - zMax + 1 + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMin);
            }
            case SOUTH: {
                return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMax - 1 + zMin);
            }
            case WEST: {
                return new StructureBoundingBox(structureMinX - zMax + 1 + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + zMin, structureMinY + yMax - 1 + yMin, structureMinZ + xMax - 1 + xMin);
            }
            case EAST: {
                return new StructureBoundingBox(structureMinX + zMin, structureMinY + yMin, structureMinZ + xMin, structureMinX + zMax - 1 + zMin, structureMinY + yMax - 1 + yMin, structureMinZ + xMax - 1 + xMin);
            }
        }
        return new StructureBoundingBox(structureMinX + xMin, structureMinY + yMin, structureMinZ + zMin, structureMinX + xMax - 1 + xMin, structureMinY + yMax - 1 + yMin, structureMinZ + zMax - 1 + zMin);
    }

    public static StructureBoundingBox createProper(int p_175899_0_, int p_175899_1_, int p_175899_2_, int p_175899_3_, int p_175899_4_, int p_175899_5_) {
        return new StructureBoundingBox(Math.min(p_175899_0_, p_175899_3_), Math.min(p_175899_1_, p_175899_4_), Math.min(p_175899_2_, p_175899_5_), Math.max(p_175899_0_, p_175899_3_), Math.max(p_175899_1_, p_175899_4_), Math.max(p_175899_2_, p_175899_5_));
    }

    public StructureBoundingBox(StructureBoundingBox structurebb) {
        this.minX = structurebb.minX;
        this.minY = structurebb.minY;
        this.minZ = structurebb.minZ;
        this.maxX = structurebb.maxX;
        this.maxY = structurebb.maxY;
        this.maxZ = structurebb.maxZ;
    }

    public StructureBoundingBox(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
        this.minX = xMin;
        this.minY = yMin;
        this.minZ = zMin;
        this.maxX = xMax;
        this.maxY = yMax;
        this.maxZ = zMax;
    }

    public StructureBoundingBox(Vec3i vec1, Vec3i vec2) {
        this.minX = Math.min(vec1.getX(), vec2.getX());
        this.minY = Math.min(vec1.getY(), vec2.getY());
        this.minZ = Math.min(vec1.getZ(), vec2.getZ());
        this.maxX = Math.max(vec1.getX(), vec2.getX());
        this.maxY = Math.max(vec1.getY(), vec2.getY());
        this.maxZ = Math.max(vec1.getZ(), vec2.getZ());
    }

    public StructureBoundingBox(int xMin, int zMin, int xMax, int zMax) {
        this.minX = xMin;
        this.minZ = zMin;
        this.maxX = xMax;
        this.maxZ = zMax;
        this.minY = 1;
        this.maxY = 512;
    }

    public boolean intersectsWith(StructureBoundingBox structurebb) {
        return this.maxX >= structurebb.minX && this.minX <= structurebb.maxX && this.maxZ >= structurebb.minZ && this.minZ <= structurebb.maxZ && this.maxY >= structurebb.minY && this.minY <= structurebb.maxY;
    }

    public boolean intersectsWith(int minXIn, int minZIn, int maxXIn, int maxZIn) {
        return this.maxX >= minXIn && this.minX <= maxXIn && this.maxZ >= minZIn && this.minZ <= maxZIn;
    }

    public void expandTo(StructureBoundingBox sbb) {
        this.minX = Math.min(this.minX, sbb.minX);
        this.minY = Math.min(this.minY, sbb.minY);
        this.minZ = Math.min(this.minZ, sbb.minZ);
        this.maxX = Math.max(this.maxX, sbb.maxX);
        this.maxY = Math.max(this.maxY, sbb.maxY);
        this.maxZ = Math.max(this.maxZ, sbb.maxZ);
    }

    public void offset(int x, int y, int z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }

    public boolean isVecInside(Vec3i vec) {
        return vec.getX() >= this.minX && vec.getX() <= this.maxX && vec.getZ() >= this.minZ && vec.getZ() <= this.maxZ && vec.getY() >= this.minY && vec.getY() <= this.maxY;
    }

    public Vec3i getLength() {
        return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
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

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x0", this.minX).add("y0", this.minY).add("z0", this.minZ).add("x1", this.maxX).add("y1", this.maxY).add("z1", this.maxZ).toString();
    }

    public NBTTagIntArray toNBTTagIntArray() {
        return new NBTTagIntArray(new int[]{this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
    }
}

