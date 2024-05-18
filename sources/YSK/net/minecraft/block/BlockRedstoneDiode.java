package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.material.*;

public abstract class BlockRedstoneDiode extends BlockDirectional
{
    protected final boolean isRepeaterPowered;
    
    protected boolean shouldBePowered(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.calculateInputStrength(world, blockPos, blockState) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.isRepeaterPowered) {
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (i < length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[i]), this);
                ++i;
            }
        }
        super.onBlockDestroyedByPlayer(world, blockPos, blockState);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    protected void updateState(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.isLocked(world, blockPos, blockState)) {
            final boolean shouldBePowered = this.shouldBePowered(world, blockPos, blockState);
            if (((this.isRepeaterPowered && !shouldBePowered) || (!this.isRepeaterPowered && shouldBePowered)) && !world.isBlockTickPending(blockPos, this)) {
                int n = -" ".length();
                if (this.isFacingTowardsRepeater(world, blockPos, blockState)) {
                    n = -"   ".length();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else if (this.isRepeaterPowered) {
                    n = -"  ".length();
                }
                world.updateBlockTick(blockPos, this, this.getDelay(blockState), n);
            }
        }
    }
    
    @Override
    public boolean isAssociatedBlock(final Block block) {
        return this.isAssociated(block);
    }
    
    protected void notifyNeighbors(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneDiode.FACING);
        final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
        world.notifyBlockOfStateChange(offset, this);
        world.notifyNeighborsOfStateExcept(offset, this, enumFacing);
    }
    
    protected abstract IBlockState getUnpoweredState(final IBlockState p0);
    
    public boolean isLocked(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return "".length() != 0;
    }
    
    protected int calculateInputStrength(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneDiode.FACING);
        final BlockPos offset = blockPos.offset(enumFacing);
        final int redstonePower = world.getRedstonePower(offset, enumFacing);
        if (redstonePower >= (0x5F ^ 0x50)) {
            return redstonePower;
        }
        final IBlockState blockState2 = world.getBlockState(offset);
        final int n = redstonePower;
        int n2;
        if (blockState2.getBlock() == Blocks.redstone_wire) {
            n2 = blockState2.getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return Math.max(n, n2);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (!this.isPowered(blockState)) {
            n = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (blockState.getValue((IProperty<Comparable>)BlockRedstoneDiode.FACING) == enumFacing) {
            n = this.getActiveSignal(blockAccess, blockPos, blockState);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        if (this.shouldBePowered(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, " ".length());
        }
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos) {
        return World.doesBlockHaveSolidTopSurface(world, blockPos.down());
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    protected BlockRedstoneDiode(final boolean isRepeaterPowered) {
        super(Material.circuits);
        this.isRepeaterPowered = isRepeaterPowered;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    protected int getTickDelay(final IBlockState blockState) {
        return this.getDelay(blockState);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        int n;
        if (World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            n = (super.canPlaceBlockAt(world, blockPos) ? 1 : 0);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!this.isLocked(world, blockPos, blockState)) {
            final boolean shouldBePowered = this.shouldBePowered(world, blockPos, blockState);
            if (this.isRepeaterPowered && !shouldBePowered) {
                world.setBlockState(blockPos, this.getUnpoweredState(blockState), "  ".length());
                "".length();
                if (false) {
                    throw null;
                }
            }
            else if (!this.isRepeaterPowered) {
                world.setBlockState(blockPos, this.getPoweredState(blockState), "  ".length());
                if (!shouldBePowered) {
                    world.updateBlockTick(blockPos, this.getPoweredState(blockState).getBlock(), this.getTickDelay(blockState), -" ".length());
                }
            }
        }
    }
    
    protected int getPowerOnSide(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        int n;
        if (this.canPowerSide(block)) {
            if (block == Blocks.redstone_wire) {
                n = blockState.getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n = blockAccess.getStrongPower(blockPos, enumFacing);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    protected abstract IBlockState getPoweredState(final IBlockState p0);
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.notifyNeighbors(world, blockPos, blockState);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (enumFacing.getAxis() != EnumFacing.Axis.Y) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isFacingTowardsRepeater(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing opposite = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneDiode.FACING).getOpposite();
        final BlockPos offset = blockPos.offset(opposite);
        int n;
        if (isRedstoneRepeaterBlockID(world.getBlockState(offset).getBlock())) {
            if (world.getBlockState(offset).getValue((IProperty<Comparable>)BlockRedstoneDiode.FACING) != opposite) {
                n = " ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    protected abstract int getDelay(final IBlockState p0);
    
    protected int getPowerOnSides(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneDiode.FACING);
        final EnumFacing rotateY = enumFacing.rotateY();
        final EnumFacing rotateYCCW = enumFacing.rotateYCCW();
        return Math.max(this.getPowerOnSide(blockAccess, blockPos.offset(rotateY), rotateY), this.getPowerOnSide(blockAccess, blockPos.offset(rotateYCCW), rotateYCCW));
    }
    
    public boolean isAssociated(final Block block) {
        if (block != this.getPoweredState(this.getDefaultState()).getBlock() && block != this.getUnpoweredState(this.getDefaultState()).getBlock()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneDiode.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (this.canBlockStay(world, blockToAir)) {
            this.updateState(world, blockToAir, blockState);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (i < length) {
                world.notifyNeighborsOfStateChange(blockToAir.offset(values[i]), this);
                ++i;
            }
        }
    }
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    public static boolean isRedstoneRepeaterBlockID(final Block block) {
        if (!Blocks.unpowered_repeater.isAssociated(block) && !Blocks.unpowered_comparator.isAssociated(block)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    protected int getActiveSignal(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        return 0x48 ^ 0x47;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.getWeakPower(blockAccess, blockPos, blockState, enumFacing);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    protected boolean isPowered(final IBlockState blockState) {
        return this.isRepeaterPowered;
    }
    
    protected boolean canPowerSide(final Block block) {
        return block.canProvidePower();
    }
}
