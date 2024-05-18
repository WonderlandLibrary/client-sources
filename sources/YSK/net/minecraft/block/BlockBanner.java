package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class BlockBanner extends BlockContainer
{
    public static final PropertyDirection FACING;
    private static final String[] I;
    public static final PropertyInteger ROTATION;
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (!this.func_181087_e(world, blockPos) && super.canPlaceBlockAt(world, blockPos)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityBanner();
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.banner;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBanner) {
            final ItemStack itemStack = new ItemStack(Items.banner, " ".length(), ((TileEntityBanner)tileEntity).getBaseColor());
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound);
            nbtTagCompound.removeTag(BlockBanner.I["   ".length()]);
            nbtTagCompound.removeTag(BlockBanner.I[0x9A ^ 0x9E]);
            nbtTagCompound.removeTag(BlockBanner.I[0xA5 ^ 0xA0]);
            nbtTagCompound.removeTag(BlockBanner.I[0x14 ^ 0x12]);
            itemStack.setTagInfo(BlockBanner.I[0xA0 ^ 0xA7], nbtTagCompound);
            Block.spawnAsEntity(world, blockPos, itemStack);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        }
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityBanner) {
            final TileEntityBanner tileEntityBanner = (TileEntityBanner)tileEntity;
            final ItemStack itemStack = new ItemStack(Items.banner, " ".length(), ((TileEntityBanner)tileEntity).getBaseColor());
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            TileEntityBanner.func_181020_a(nbtTagCompound, tileEntityBanner.getBaseColor(), tileEntityBanner.func_181021_d());
            itemStack.setTagInfo(BlockBanner.I[0x57 ^ 0x5F], nbtTagCompound);
            Block.spawnAsEntity(world, blockPos, itemStack);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, null);
        }
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(BlockBanner.I["  ".length()]);
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockBanner.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
        ROTATION = PropertyInteger.create(BlockBanner.I[" ".length()], "".length(), 0x66 ^ 0x69);
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean func_181623_g() {
        return " ".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    protected BlockBanner() {
        super(Material.wood);
        final float n = 0.25f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 1.0f, 0.5f + n);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.banner;
    }
    
    private static void I() {
        (I = new String[0x94 ^ 0x9D])["".length()] = I("\u0017\b\u0011+\u001c\u0016", "qirBr");
        BlockBanner.I[" ".length()] = I("5?=\b\u000e.?'", "GPIiz");
        BlockBanner.I["  ".length()] = I("#.\u0012#W(;\u0019 \u001c8t\u0000&\u0010>?Y \u0018'?", "JZwNy");
        BlockBanner.I["   ".length()] = I("+", "SkFhM");
        BlockBanner.I[0x66 ^ 0x62] = I("6", "Owkry");
        BlockBanner.I[0x53 ^ 0x56] = I(">", "DtsoD");
        BlockBanner.I[0x7B ^ 0x7D] = I("\u00060", "oTfTw");
        BlockBanner.I[0x1A ^ 0x1D] = I("8\u0002\u000e\u0005\u0003?\u0000\u0015\u000f\u001c\u0003:\u0000\u0001", "znafh");
        BlockBanner.I[0x56 ^ 0x5E] = I("\u000f\u0006*\b\"\b\u00041\u0002=4>$\f", "MjEkI");
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return " ".length() != 0;
    }
    
    public static class BlockBannerStanding extends BlockBanner
    {
        public BlockBannerStanding() {
            this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockBannerStanding.ROTATION, "".length()));
        }
        
        @Override
        public int getMetaFromState(final IBlockState blockState) {
            return blockState.getValue((IProperty<Integer>)BlockBannerStanding.ROTATION);
        }
        
        @Override
        public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
            if (!world.getBlockState(blockToAir.down()).getBlock().getMaterial().isSolid()) {
                this.dropBlockAsItem(world, blockToAir, blockState, "".length());
                world.setBlockToAir(blockToAir);
            }
            super.onNeighborBlockChange(world, blockToAir, blockState, block);
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
                if (1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected BlockState createBlockState() {
            final IProperty[] array = new IProperty[" ".length()];
            array["".length()] = BlockBannerStanding.ROTATION;
            return new BlockState(this, array);
        }
        
        @Override
        public IBlockState getStateFromMeta(final int n) {
            return this.getDefaultState().withProperty((IProperty<Comparable>)BlockBannerStanding.ROTATION, n);
        }
    }
    
    public static class BlockBannerHanging extends BlockBanner
    {
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
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
                if (3 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
            final EnumFacing enumFacing = blockAccess.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockBannerHanging.FACING);
            final float n = 0.0f;
            final float n2 = 0.78125f;
            final float n3 = 0.0f;
            final float n4 = 1.0f;
            final float n5 = 0.125f;
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                default: {
                    this.setBlockBounds(n3, n, 1.0f - n5, n4, n2, 1.0f);
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    this.setBlockBounds(n3, n, 0.0f, n4, n2, n5);
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    this.setBlockBounds(1.0f - n5, n, n3, 1.0f, n2, n4);
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    this.setBlockBounds(0.0f, n, n3, n5, n2, n4);
                    break;
                }
            }
        }
        
        @Override
        public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
            if (!world.getBlockState(blockToAir.offset(blockState.getValue((IProperty<EnumFacing>)BlockBannerHanging.FACING).getOpposite())).getBlock().getMaterial().isSolid()) {
                this.dropBlockAsItem(world, blockToAir, blockState, "".length());
                world.setBlockToAir(blockToAir);
            }
            super.onNeighborBlockChange(world, blockToAir, blockState, block);
        }
        
        @Override
        public IBlockState getStateFromMeta(final int n) {
            EnumFacing enumFacing = EnumFacing.getFront(n);
            if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
                enumFacing = EnumFacing.NORTH;
            }
            return this.getDefaultState().withProperty((IProperty<Comparable>)BlockBannerHanging.FACING, enumFacing);
        }
        
        @Override
        protected BlockState createBlockState() {
            final IProperty[] array = new IProperty[" ".length()];
            array["".length()] = BlockBannerHanging.FACING;
            return new BlockState(this, array);
        }
        
        @Override
        public int getMetaFromState(final IBlockState blockState) {
            return blockState.getValue((IProperty<EnumFacing>)BlockBannerHanging.FACING).getIndex();
        }
        
        public BlockBannerHanging() {
            this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockBannerHanging.FACING, EnumFacing.NORTH));
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockBannerHanging.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xC2 ^ 0xC4);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x98 ^ 0x9C);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x30 ^ 0x35);
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return BlockBannerHanging.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
    }
}
