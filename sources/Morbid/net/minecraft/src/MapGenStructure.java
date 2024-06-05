package net.minecraft.src;

import java.util.concurrent.*;
import java.util.*;

public abstract class MapGenStructure extends MapGenBase
{
    protected Map structureMap;
    
    public MapGenStructure() {
        this.structureMap = new HashMap();
    }
    
    @Override
    protected void recursiveGenerate(final World par1World, final int par2, final int par3, final int par4, final int par5, final byte[] par6ArrayOfByte) {
        if (!this.structureMap.containsKey(ChunkCoordIntPair.chunkXZ2Int(par2, par3))) {
            this.rand.nextInt();
            try {
                if (this.canSpawnStructureAtCoords(par2, par3)) {
                    final StructureStart var7 = this.getStructureStart(par2, par3);
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(par2, par3), var7);
                }
            }
            catch (Throwable var9) {
                final CrashReport var8 = CrashReport.makeCrashReport(var9, "Exception preparing structure feature");
                final CrashReportCategory var10 = var8.makeCategory("Feature being prepared");
                var10.addCrashSectionCallable("Is feature chunk", new CallableIsFeatureChunk(this, par2, par3));
                var10.addCrashSection("Chunk location", String.format("%d,%d", par2, par3));
                var10.addCrashSectionCallable("Chunk pos hash", new CallableChunkPosHash(this, par2, par3));
                var10.addCrashSectionCallable("Structure type", new CallableStructureType(this));
                throw new ReportedException(var8);
            }
        }
    }
    
    public boolean generateStructuresInChunk(final World par1World, final Random par2Random, final int par3, final int par4) {
        final int var5 = (par3 << 4) + 8;
        final int var6 = (par4 << 4) + 8;
        boolean var7 = false;
        for (final StructureStart var9 : this.structureMap.values()) {
            if (var9.isSizeableStructure() && var9.getBoundingBox().intersectsWith(var5, var6, var5 + 15, var6 + 15)) {
                var9.generateStructure(par1World, par2Random, new StructureBoundingBox(var5, var6, var5 + 15, var6 + 15));
                var7 = true;
            }
        }
        return var7;
    }
    
    public boolean hasStructureAt(final int par1, final int par2, final int par3) {
        for (final StructureStart var5 : this.structureMap.values()) {
            if (var5.isSizeableStructure() && var5.getBoundingBox().intersectsWith(par1, par3, par1, par3)) {
                for (final StructureComponent var7 : var5.getComponents()) {
                    if (var7.getBoundingBox().isVecInside(par1, par2, par3)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public ChunkPosition getNearestInstance(final World par1World, final int par2, final int par3, final int par4) {
        this.worldObj = par1World;
        this.rand.setSeed(par1World.getSeed());
        final long var5 = this.rand.nextLong();
        final long var6 = this.rand.nextLong();
        final long var7 = (par2 >> 4) * var5;
        final long var8 = (par4 >> 4) * var6;
        this.rand.setSeed(var7 ^ var8 ^ par1World.getSeed());
        this.recursiveGenerate(par1World, par2 >> 4, par4 >> 4, 0, 0, null);
        double var9 = Double.MAX_VALUE;
        ChunkPosition var10 = null;
        for (final StructureStart var12 : this.structureMap.values()) {
            if (var12.isSizeableStructure()) {
                final StructureComponent var13 = var12.getComponents().get(0);
                final ChunkPosition var14 = var13.getCenter();
                final int var15 = var14.x - par2;
                final int var16 = var14.y - par3;
                final int var17 = var14.z - par4;
                final double var18 = var15 + var15 * var16 * var16 + var17 * var17;
                if (var18 >= var9) {
                    continue;
                }
                var9 = var18;
                var10 = var14;
            }
        }
        if (var10 != null) {
            return var10;
        }
        final List var19 = this.getCoordList();
        if (var19 != null) {
            ChunkPosition var20 = null;
            for (final ChunkPosition var14 : var19) {
                final int var15 = var14.x - par2;
                final int var16 = var14.y - par3;
                final int var17 = var14.z - par4;
                final double var18 = var15 + var15 * var16 * var16 + var17 * var17;
                if (var18 < var9) {
                    var9 = var18;
                    var20 = var14;
                }
            }
            return var20;
        }
        return null;
    }
    
    protected List getCoordList() {
        return null;
    }
    
    protected abstract boolean canSpawnStructureAtCoords(final int p0, final int p1);
    
    protected abstract StructureStart getStructureStart(final int p0, final int p1);
}
