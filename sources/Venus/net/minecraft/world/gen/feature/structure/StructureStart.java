/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class StructureStart<C extends IFeatureConfig> {
    public static final StructureStart<?> DUMMY = new StructureStart<MineshaftConfig>(Structure.field_236367_c_, 0, 0, MutableBoundingBox.getNewBoundingBox(), 0, 0L){

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, MineshaftConfig mineshaftConfig) {
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManager, int n, int n2, Biome biome, IFeatureConfig iFeatureConfig) {
            this.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, n, n2, biome, (MineshaftConfig)iFeatureConfig);
        }
    };
    private final Structure<C> structure;
    protected final List<StructurePiece> components = Lists.newArrayList();
    protected MutableBoundingBox bounds;
    private final int chunkPosX;
    private final int chunkPosZ;
    private int references;
    protected final SharedSeedRandom rand;

    public StructureStart(Structure<C> structure, int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
        this.structure = structure;
        this.chunkPosX = n;
        this.chunkPosZ = n2;
        this.references = n3;
        this.rand = new SharedSeedRandom();
        this.rand.setLargeFeatureSeed(l, n, n2);
        this.bounds = mutableBoundingBox;
    }

    public abstract void func_230364_a_(DynamicRegistries var1, ChunkGenerator var2, TemplateManager var3, int var4, int var5, Biome var6, C var7);

    public MutableBoundingBox getBoundingBox() {
        return this.bounds;
    }

    public List<StructurePiece> getComponents() {
        return this.components;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void func_230366_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos) {
        List<StructurePiece> list = this.components;
        synchronized (list) {
            if (!this.components.isEmpty()) {
                MutableBoundingBox mutableBoundingBox2 = this.components.get((int)0).boundingBox;
                Vector3i vector3i = mutableBoundingBox2.func_215126_f();
                BlockPos blockPos = new BlockPos(vector3i.getX(), mutableBoundingBox2.minY, vector3i.getZ());
                Iterator<StructurePiece> iterator2 = this.components.iterator();
                while (iterator2.hasNext()) {
                    StructurePiece structurePiece = iterator2.next();
                    if (!structurePiece.getBoundingBox().intersectsWith(mutableBoundingBox) || structurePiece.func_230383_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos, blockPos)) continue;
                    iterator2.remove();
                }
                this.recalculateStructureSize();
            }
        }
    }

    protected void recalculateStructureSize() {
        this.bounds = MutableBoundingBox.getNewBoundingBox();
        for (StructurePiece structurePiece : this.components) {
            this.bounds.expandTo(structurePiece.getBoundingBox());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CompoundNBT write(int n, int n2) {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (this.isValid()) {
            compoundNBT.putString("id", Registry.STRUCTURE_FEATURE.getKey(this.getStructure()).toString());
            compoundNBT.putInt("ChunkX", n);
            compoundNBT.putInt("ChunkZ", n2);
            compoundNBT.putInt("references", this.references);
            compoundNBT.put("BB", this.bounds.toNBTTagIntArray());
            ListNBT listNBT = new ListNBT();
            List<StructurePiece> list = this.components;
            synchronized (list) {
                for (StructurePiece structurePiece : this.components) {
                    listNBT.add(structurePiece.write());
                }
            }
            compoundNBT.put("Children", listNBT);
            return compoundNBT;
        }
        compoundNBT.putString("id", "INVALID");
        return compoundNBT;
    }

    protected void func_214628_a(int n, Random random2, int n2) {
        int n3 = n - n2;
        int n4 = this.bounds.getYSize() + 1;
        if (n4 < n3) {
            n4 += random2.nextInt(n3 - n4);
        }
        int n5 = n4 - this.bounds.maxY;
        this.bounds.offset(0, n5, 0);
        for (StructurePiece structurePiece : this.components) {
            structurePiece.offset(0, n5, 0);
        }
    }

    protected void func_214626_a(Random random2, int n, int n2) {
        int n3 = n2 - n + 1 - this.bounds.getYSize();
        int n4 = n3 > 1 ? n + random2.nextInt(n3) : n;
        int n5 = n4 - this.bounds.minY;
        this.bounds.offset(0, n5, 0);
        for (StructurePiece structurePiece : this.components) {
            structurePiece.offset(0, n5, 0);
        }
    }

    public boolean isValid() {
        return !this.components.isEmpty();
    }

    public int getChunkPosX() {
        return this.chunkPosX;
    }

    public int getChunkPosZ() {
        return this.chunkPosZ;
    }

    public BlockPos getPos() {
        return new BlockPos(this.chunkPosX << 4, 0, this.chunkPosZ << 4);
    }

    public boolean isRefCountBelowMax() {
        return this.references < this.getMaxRefCount();
    }

    public void incrementRefCount() {
        ++this.references;
    }

    public int getRefCount() {
        return this.references;
    }

    protected int getMaxRefCount() {
        return 0;
    }

    public Structure<?> getStructure() {
        return this.structure;
    }
}

