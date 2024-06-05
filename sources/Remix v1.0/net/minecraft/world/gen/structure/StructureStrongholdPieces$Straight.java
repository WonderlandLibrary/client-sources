package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Straight extends Stronghold
{
    private boolean expandsX;
    private boolean expandsZ;
    
    public Straight() {
    }
    
    public Straight(final int p_i45573_1_, final Random p_i45573_2_, final StructureBoundingBox p_i45573_3_, final EnumFacing p_i45573_4_) {
        super(p_i45573_1_);
        this.coordBaseMode = p_i45573_4_;
        this.field_143013_d = this.getRandomDoor(p_i45573_2_);
        this.boundingBox = p_i45573_3_;
        this.expandsX = (p_i45573_2_.nextInt(2) == 0);
        this.expandsZ = (p_i45573_2_.nextInt(2) == 0);
    }
    
    public static Straight func_175862_a(final List p_175862_0_, final Random p_175862_1_, final int p_175862_2_, final int p_175862_3_, final int p_175862_4_, final EnumFacing p_175862_5_, final int p_175862_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175862_0_, var7) == null) ? new Straight(p_175862_6_, p_175862_1_, var7, p_175862_5_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Left", this.expandsX);
        p_143012_1_.setBoolean("Right", this.expandsZ);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.expandsX = p_143011_1_.getBoolean("Left");
        this.expandsZ = p_143011_1_.getBoolean("Right");
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        if (this.expandsX) {
            this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
        }
        if (this.expandsZ) {
            this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 6);
        this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 1, 2, 1, Blocks.torch.getDefaultState());
        this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 3, 2, 1, Blocks.torch.getDefaultState());
        this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 1, 2, 5, Blocks.torch.getDefaultState());
        this.func_175809_a(worldIn, p_74875_3_, p_74875_2_, 0.1f, 3, 2, 5, Blocks.torch.getDefaultState());
        if (this.expandsX) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        if (this.expandsZ) {
            this.func_175804_a(worldIn, p_74875_3_, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        return true;
    }
}
