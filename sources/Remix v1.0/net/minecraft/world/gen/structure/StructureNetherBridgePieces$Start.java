package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.nbt.*;

public static class Start extends Crossing3
{
    public PieceWeight theNetherBridgePieceWeight;
    public List primaryWeights;
    public List secondaryWeights;
    public List field_74967_d;
    
    public Start() {
        this.field_74967_d = Lists.newArrayList();
    }
    
    public Start(final Random p_i2059_1_, final int p_i2059_2_, final int p_i2059_3_) {
        super(p_i2059_1_, p_i2059_2_, p_i2059_3_);
        this.field_74967_d = Lists.newArrayList();
        this.primaryWeights = Lists.newArrayList();
        for (final PieceWeight var7 : StructureNetherBridgePieces.access$100()) {
            var7.field_78827_c = 0;
            this.primaryWeights.add(var7);
        }
        this.secondaryWeights = Lists.newArrayList();
        for (final PieceWeight var7 : StructureNetherBridgePieces.access$200()) {
            var7.field_78827_c = 0;
            this.secondaryWeights.add(var7);
        }
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
    }
}
