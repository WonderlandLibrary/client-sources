package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;

public abstract class BlockBasePressurePlate extends Block
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public int tickRate(final World world) {
        return 0x8E ^ 0x9A;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return this.canBePlacedOn(world, blockPos.down());
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.375f, 0.0f, 1.0f, 0.625f, 1.0f);
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    protected void updateNeighbors(final World world, final BlockPos blockPos) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.down(), this);
    }
    
    protected BlockBasePressurePlate(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote) {
            final int redstoneStrength = this.getRedstoneStrength(blockState);
            if (redstoneStrength == 0) {
                this.updateState(world, blockPos, blockState, redstoneStrength);
            }
        }
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return this.getRedstoneStrength(blockState);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!this.canBePlacedOn(world, blockToAir.down())) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(" 5\n67?z\u0007>11?", "RTdRX");
        BlockBasePressurePlate.I[" ".length()] = I("9\t\u00146#&F\u0019>%(\u0003", "KhzRL");
    }
    
    protected AxisAlignedBB getSensitiveAABB(final BlockPos blockPos) {
        return new AxisAlignedBB(blockPos.getX() + 0.125f, blockPos.getY(), blockPos.getZ() + 0.125f, blockPos.getX() + " ".length() - 0.125f, blockPos.getY() + 0.25, blockPos.getZ() + " ".length() - 0.125f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState0(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.getRedstoneStrength(blockState) > 0) {
            this.updateNeighbors(world, blockPos);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    protected void setBlockBoundsBasedOnState0(final IBlockState blockState) {
        int n;
        if (this.getRedstoneStrength(blockState) > 0) {
            n = " ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        if (n != 0) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.03125f, 0.9375f);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.0625f, 0.9375f);
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return " ".length();
    }
    
    protected abstract IBlockState setRedstoneStrength(final IBlockState p0, final int p1);
    
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected BlockBasePressurePlate(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(" ".length() != 0);
    }
    
    protected abstract int computeRedstoneStrength(final World p0, final BlockPos p1);
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    protected void updateState(final World world, final BlockPos blockPos, IBlockState setRedstoneStrength, final int n) {
        final int computeRedstoneStrength = this.computeRedstoneStrength(world, blockPos);
        int n2;
        if (n > 0) {
            n2 = " ".length();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        int n4;
        if (computeRedstoneStrength > 0) {
            n4 = " ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        if (n != computeRedstoneStrength) {
            setRedstoneStrength = this.setRedstoneStrength(setRedstoneStrength, computeRedstoneStrength);
            world.setBlockState(blockPos, setRedstoneStrength, "  ".length());
            this.updateNeighbors(world, blockPos);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
        if (n5 == 0 && n3 != 0) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, BlockBasePressurePlate.I["".length()], 0.3f, 0.5f);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (n5 != 0 && n3 == 0) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, BlockBasePressurePlate.I[" ".length()], 0.3f, 0.6f);
        }
        if (n5 != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            final int redstoneStrength = this.getRedstoneStrength(blockState);
            if (redstoneStrength > 0) {
                this.updateState(world, blockPos, blockState, redstoneStrength);
            }
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    private boolean canBePlacedOn(final World world, final BlockPos blockPos) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockPos) && !(world.getBlockState(blockPos).getBlock() instanceof BlockFence)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public boolean func_181623_g() {
        return " ".length() != 0;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (enumFacing == EnumFacing.UP) {
            n = this.getRedstoneStrength(blockState);
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    protected abstract int getRedstoneStrength(final IBlockState p0);
}
