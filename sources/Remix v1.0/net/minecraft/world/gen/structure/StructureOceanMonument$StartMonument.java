package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import java.util.*;

public static class StartMonument extends StructureStart
{
    private Set field_175791_c;
    private boolean field_175790_d;
    
    public StartMonument() {
        this.field_175791_c = Sets.newHashSet();
    }
    
    public StartMonument(final World worldIn, final Random p_i45607_2_, final int p_i45607_3_, final int p_i45607_4_) {
        super(p_i45607_3_, p_i45607_4_);
        this.field_175791_c = Sets.newHashSet();
        this.func_175789_b(worldIn, p_i45607_2_, p_i45607_3_, p_i45607_4_);
    }
    
    private void func_175789_b(final World worldIn, final Random p_175789_2_, final int p_175789_3_, final int p_175789_4_) {
        p_175789_2_.setSeed(worldIn.getSeed());
        final long var5 = p_175789_2_.nextLong();
        final long var6 = p_175789_2_.nextLong();
        final long var7 = p_175789_3_ * var5;
        final long var8 = p_175789_4_ * var6;
        p_175789_2_.setSeed(var7 ^ var8 ^ worldIn.getSeed());
        final int var9 = p_175789_3_ * 16 + 8 - 29;
        final int var10 = p_175789_4_ * 16 + 8 - 29;
        final EnumFacing var11 = EnumFacing.Plane.HORIZONTAL.random(p_175789_2_);
        this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(p_175789_2_, var9, var10, var11));
        this.updateBoundingBox();
        this.field_175790_d = true;
    }
    
    @Override
    public void generateStructure(final World worldIn, final Random p_75068_2_, final StructureBoundingBox p_75068_3_) {
        if (!this.field_175790_d) {
            this.components.clear();
            this.func_175789_b(worldIn, p_75068_2_, this.func_143019_e(), this.func_143018_f());
        }
        super.generateStructure(worldIn, p_75068_2_, p_75068_3_);
    }
    
    @Override
    public boolean func_175788_a(final ChunkCoordIntPair p_175788_1_) {
        return !this.field_175791_c.contains(p_175788_1_) && super.func_175788_a(p_175788_1_);
    }
    
    @Override
    public void func_175787_b(final ChunkCoordIntPair p_175787_1_) {
        super.func_175787_b(p_175787_1_);
        this.field_175791_c.add(p_175787_1_);
    }
    
    @Override
    public void func_143022_a(final NBTTagCompound p_143022_1_) {
        super.func_143022_a(p_143022_1_);
        final NBTTagList var2 = new NBTTagList();
        for (final ChunkCoordIntPair var4 : this.field_175791_c) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.setInteger("X", var4.chunkXPos);
            var5.setInteger("Z", var4.chunkZPos);
            var2.appendTag(var5);
        }
        p_143022_1_.setTag("Processed", var2);
    }
    
    @Override
    public void func_143017_b(final NBTTagCompound p_143017_1_) {
        super.func_143017_b(p_143017_1_);
        if (p_143017_1_.hasKey("Processed", 9)) {
            final NBTTagList var2 = p_143017_1_.getTagList("Processed", 10);
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
                this.field_175791_c.add(new ChunkCoordIntPair(var4.getInteger("X"), var4.getInteger("Z")));
            }
        }
    }
}
