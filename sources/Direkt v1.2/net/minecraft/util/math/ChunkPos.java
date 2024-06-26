package net.minecraft.util.math;

import net.minecraft.entity.Entity;

public class ChunkPos {
	/** The X position of this Chunk Coordinate Pair */
	public final int chunkXPos;

	/** The Z position of this Chunk Coordinate Pair */
	public final int chunkZPos;
	private int cachedHashCode = 0;

	public ChunkPos(int x, int z) {
		this.chunkXPos = x;
		this.chunkZPos = z;
	}

	public ChunkPos(BlockPos pos) {
		this.chunkXPos = pos.getX() >> 4;
		this.chunkZPos = pos.getZ() >> 4;
	}

	/**
	 * converts a chunk coordinate pair to an integer (suitable for hashing)
	 */
	public static long chunkXZ2Int(int x, int z) {
		return (x & 4294967295L) | ((z & 4294967295L) << 32);
	}

	@Override
	public int hashCode() {
		if (this.cachedHashCode == 0) {
			int i = (1664525 * this.chunkXPos) + 1013904223;
			int j = (1664525 * (this.chunkZPos ^ -559038737)) + 1013904223;
			this.cachedHashCode = i ^ j;
		}

		return this.cachedHashCode;
	}

	@Override
	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof ChunkPos)) {
			return false;
		} else {
			ChunkPos chunkpos = (ChunkPos) p_equals_1_;
			return (this.chunkXPos == chunkpos.chunkXPos) && (this.chunkZPos == chunkpos.chunkZPos);
		}
	}

	public double getDistanceSq(Entity p_185327_1_) {
		double d0 = (this.chunkXPos * 16) + 8;
		double d1 = (this.chunkZPos * 16) + 8;
		double d2 = d0 - p_185327_1_.posX;
		double d3 = d1 - p_185327_1_.posZ;
		return (d2 * d2) + (d3 * d3);
	}

	public int getCenterXPos() {
		return (this.chunkXPos << 4) + 8;
	}

	public int getCenterZPosition() {
		return (this.chunkZPos << 4) + 8;
	}

	/**
	 * Get the first world X coordinate that belongs to this Chunk
	 */
	public int getXStart() {
		return this.chunkXPos << 4;
	}

	/**
	 * Get the first world Z coordinate that belongs to this Chunk
	 */
	public int getZStart() {
		return this.chunkZPos << 4;
	}

	/**
	 * Get the last world X coordinate that belongs to this Chunk
	 */
	public int getXEnd() {
		return (this.chunkXPos << 4) + 15;
	}

	/**
	 * Get the last world Z coordinate that belongs to this Chunk
	 */
	public int getZEnd() {
		return (this.chunkZPos << 4) + 15;
	}

	/**
	 * Get the World coordinates of the Block with the given Chunk coordinates relative to this chunk
	 */
	public BlockPos getBlock(int x, int y, int z) {
		return new BlockPos((this.chunkXPos << 4) + x, y, (this.chunkZPos << 4) + z);
	}

	/**
	 * Get the coordinates of the Block in the center of this chunk with the given Y coordinate
	 */
	public BlockPos getCenterBlock(int y) {
		return new BlockPos(this.getCenterXPos(), y, this.getCenterZPosition());
	}

	@Override
	public String toString() {
		return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
	}
}
