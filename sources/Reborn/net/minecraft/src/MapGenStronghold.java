package net.minecraft.src;

import java.util.*;

public class MapGenStronghold extends MapGenStructure
{
    private BiomeGenBase[] allowedBiomeGenBases;
    private boolean ranBiomeCheck;
    private ChunkCoordIntPair[] structureCoords;
    private double field_82671_h;
    private int field_82672_i;
    
    public MapGenStronghold() {
        this.allowedBiomeGenBases = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.desertHills, BiomeGenBase.forestHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.jungle, BiomeGenBase.jungleHills };
        this.structureCoords = new ChunkCoordIntPair[3];
        this.field_82671_h = 32.0;
        this.field_82672_i = 3;
    }
    
    public MapGenStronghold(final Map par1Map) {
        this.allowedBiomeGenBases = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.icePlains, BiomeGenBase.iceMountains, BiomeGenBase.desertHills, BiomeGenBase.forestHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.jungle, BiomeGenBase.jungleHills };
        this.structureCoords = new ChunkCoordIntPair[3];
        this.field_82671_h = 32.0;
        this.field_82672_i = 3;
        for (final Map.Entry var3 : par1Map.entrySet()) {
            if (var3.getKey().equals("distance")) {
                this.field_82671_h = MathHelper.func_82713_a(var3.getValue(), this.field_82671_h, 1.0);
            }
            else if (var3.getKey().equals("count")) {
                this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax(var3.getValue(), this.structureCoords.length, 1)];
            }
            else {
                if (!var3.getKey().equals("spread")) {
                    continue;
                }
                this.field_82672_i = MathHelper.parseIntWithDefaultAndMax(var3.getValue(), this.field_82672_i, 1);
            }
        }
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int par1, final int par2) {
        if (!this.ranBiomeCheck) {
            final Random var3 = new Random();
            var3.setSeed(this.worldObj.getSeed());
            double var4 = var3.nextDouble() * 3.141592653589793 * 2.0;
            int var5 = 1;
            for (int var6 = 0; var6 < this.structureCoords.length; ++var6) {
                final double var7 = (1.25 * var5 + var3.nextDouble()) * this.field_82671_h * var5;
                int var8 = (int)Math.round(Math.cos(var4) * var7);
                int var9 = (int)Math.round(Math.sin(var4) * var7);
                final ArrayList var10 = new ArrayList();
                Collections.addAll(var10, this.allowedBiomeGenBases);
                final ChunkPosition var11 = this.worldObj.getWorldChunkManager().findBiomePosition((var8 << 4) + 8, (var9 << 4) + 8, 112, var10, var3);
                if (var11 != null) {
                    var8 = var11.x >> 4;
                    var9 = var11.z >> 4;
                }
                this.structureCoords[var6] = new ChunkCoordIntPair(var8, var9);
                var4 += 6.283185307179586 * var5 / this.field_82672_i;
                if (var6 == this.field_82672_i) {
                    var5 += 2 + var3.nextInt(5);
                    this.field_82672_i += 1 + var3.nextInt(2);
                }
            }
            this.ranBiomeCheck = true;
        }
        for (final ChunkCoordIntPair var15 : this.structureCoords) {
            if (par1 == var15.chunkXPos && par2 == var15.chunkZPos) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected List getCoordList() {
        final ArrayList var1 = new ArrayList();
        for (final ChunkCoordIntPair var5 : this.structureCoords) {
            if (var5 != null) {
                var1.add(var5.getChunkPosition(64));
            }
        }
        return var1;
    }
    
    @Override
    protected StructureStart getStructureStart(final int par1, final int par2) {
        StructureStrongholdStart var3;
        for (var3 = new StructureStrongholdStart(this.worldObj, this.rand, par1, par2); var3.getComponents().isEmpty() || var3.getComponents().get(0).strongholdPortalRoom == null; var3 = new StructureStrongholdStart(this.worldObj, this.rand, par1, par2)) {}
        return var3;
    }
}
