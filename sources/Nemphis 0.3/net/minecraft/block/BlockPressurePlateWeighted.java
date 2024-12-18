/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted
extends BlockBasePressurePlate {
    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
    private final int field_150068_a;
    private static final String __OBFID = "CL_00000334";

    protected BlockPressurePlateWeighted(String p_i45436_1_, Material p_i45436_2_, int p_i45436_3_) {
        super(p_i45436_2_);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POWER, Integer.valueOf(0)));
        this.field_150068_a = p_i45436_3_;
    }

    @Override
    protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
        int var3 = Math.min(worldIn.getEntitiesWithinAABB(Entity.class, this.getSensitiveAABB(pos)).size(), this.field_150068_a);
        if (var3 > 0) {
            float var4 = (float)Math.min(this.field_150068_a, var3) / (float)this.field_150068_a;
            return MathHelper.ceiling_float_int(var4 * 15.0f);
        }
        return 0;
    }

    @Override
    protected int getRedstoneStrength(IBlockState p_176576_1_) {
        return (Integer)p_176576_1_.getValue(POWER);
    }

    @Override
    protected IBlockState setRedstoneStrength(IBlockState p_176575_1_, int p_176575_2_) {
        return p_176575_1_.withProperty(POWER, Integer.valueOf(p_176575_2_));
    }

    @Override
    public int tickRate(World worldIn) {
        return 10;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWER, Integer.valueOf(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (Integer)state.getValue(POWER);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, POWER);
    }
}

