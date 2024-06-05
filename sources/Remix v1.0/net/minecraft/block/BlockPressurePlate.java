package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.state.*;

public class BlockPressurePlate extends BlockBasePressurePlate
{
    public static final PropertyBool POWERED;
    private final Sensitivity sensitivity;
    
    protected BlockPressurePlate(final Material p_i45693_1_, final Sensitivity p_i45693_2_) {
        super(p_i45693_1_);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPressurePlate.POWERED, false));
        this.sensitivity = p_i45693_2_;
    }
    
    @Override
    protected int getRedstoneStrength(final IBlockState p_176576_1_) {
        return p_176576_1_.getValue(BlockPressurePlate.POWERED) ? 15 : 0;
    }
    
    @Override
    protected IBlockState setRedstoneStrength(final IBlockState p_176575_1_, final int p_176575_2_) {
        return p_176575_1_.withProperty(BlockPressurePlate.POWERED, p_176575_2_ > 0);
    }
    
    @Override
    protected int computeRedstoneStrength(final World worldIn, final BlockPos pos) {
        final AxisAlignedBB var3 = this.getSensitiveAABB(pos);
        List var4 = null;
        switch (SwitchSensitivity.SENSITIVITY_ARRAY[this.sensitivity.ordinal()]) {
            case 1: {
                var4 = worldIn.getEntitiesWithinAABBExcludingEntity(null, var3);
                break;
            }
            case 2: {
                var4 = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, var3);
                break;
            }
            default: {
                return 0;
            }
        }
        if (!var4.isEmpty()) {
            for (final Entity var6 : var4) {
                if (!var6.doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }
        return 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockPressurePlate.POWERED, meta == 1);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((boolean)state.getValue(BlockPressurePlate.POWERED)) ? 1 : 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockPressurePlate.POWERED });
    }
    
    static {
        POWERED = PropertyBool.create("powered");
    }
    
    public enum Sensitivity
    {
        EVERYTHING("EVERYTHING", 0), 
        MOBS("MOBS", 1);
        
        private static final Sensitivity[] $VALUES;
        
        private Sensitivity(final String p_i45417_1_, final int p_i45417_2_) {
        }
        
        static {
            $VALUES = new Sensitivity[] { Sensitivity.EVERYTHING, Sensitivity.MOBS };
        }
    }
    
    static final class SwitchSensitivity
    {
        static final int[] SENSITIVITY_ARRAY;
        
        static {
            SENSITIVITY_ARRAY = new int[Sensitivity.values().length];
            try {
                SwitchSensitivity.SENSITIVITY_ARRAY[Sensitivity.EVERYTHING.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchSensitivity.SENSITIVITY_ARRAY[Sensitivity.MOBS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
