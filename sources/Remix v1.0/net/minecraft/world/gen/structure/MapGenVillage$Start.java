package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.nbt.*;

public static class Start extends StructureStart
{
    private boolean hasMoreThanTwoComponents;
    
    public Start() {
    }
    
    public Start(final World worldIn, final Random p_i2092_2_, final int p_i2092_3_, final int p_i2092_4_, final int p_i2092_5_) {
        super(p_i2092_3_, p_i2092_4_);
        final List var6 = StructureVillagePieces.getStructureVillageWeightedPieceList(p_i2092_2_, p_i2092_5_);
        final StructureVillagePieces.Start var7 = new StructureVillagePieces.Start(worldIn.getWorldChunkManager(), 0, p_i2092_2_, (p_i2092_3_ << 4) + 2, (p_i2092_4_ << 4) + 2, var6, p_i2092_5_);
        this.components.add(var7);
        var7.buildComponent(var7, this.components, p_i2092_2_);
        final List var8 = var7.field_74930_j;
        final List var9 = var7.field_74932_i;
        while (!var8.isEmpty() || !var9.isEmpty()) {
            if (var8.isEmpty()) {
                final int var10 = p_i2092_2_.nextInt(var9.size());
                final StructureComponent var11 = var9.remove(var10);
                var11.buildComponent(var7, this.components, p_i2092_2_);
            }
            else {
                final int var10 = p_i2092_2_.nextInt(var8.size());
                final StructureComponent var11 = var8.remove(var10);
                var11.buildComponent(var7, this.components, p_i2092_2_);
            }
        }
        this.updateBoundingBox();
        int var10 = 0;
        for (final StructureComponent var13 : this.components) {
            if (!(var13 instanceof StructureVillagePieces.Road)) {
                ++var10;
            }
        }
        this.hasMoreThanTwoComponents = (var10 > 2);
    }
    
    @Override
    public boolean isSizeableStructure() {
        return this.hasMoreThanTwoComponents;
    }
    
    @Override
    public void func_143022_a(final NBTTagCompound p_143022_1_) {
        super.func_143022_a(p_143022_1_);
        p_143022_1_.setBoolean("Valid", this.hasMoreThanTwoComponents);
    }
    
    @Override
    public void func_143017_b(final NBTTagCompound p_143017_1_) {
        super.func_143017_b(p_143017_1_);
        this.hasMoreThanTwoComponents = p_143017_1_.getBoolean("Valid");
    }
}
