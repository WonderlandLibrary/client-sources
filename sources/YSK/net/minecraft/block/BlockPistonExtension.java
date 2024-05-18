package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class BlockPistonExtension extends Block
{
    public static final PropertyBool SHORT;
    public static final PropertyEnum<EnumPistonType> TYPE;
    public static final PropertyDirection FACING;
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    
    @Override
    public void breakBlock(final World world, BlockPos offset, final IBlockState blockState) {
        super.breakBlock(world, offset, blockState);
        offset = offset.offset(blockState.getValue((IProperty<EnumFacing>)BlockPistonExtension.FACING).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if ((blockState2.getBlock() == Blocks.piston || blockState2.getBlock() == Blocks.sticky_piston) && blockState2.getValue((IProperty<Boolean>)BlockPistonBase.EXTENDED)) {
            blockState2.getBlock().dropBlockAsItem(world, offset, blockState2, "".length());
            world.setBlockToAir(offset);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonExtension.FACING, getFacing(n));
        final PropertyEnum<EnumPistonType> type = BlockPistonExtension.TYPE;
        EnumPistonType enumPistonType;
        if ((n & (0x3E ^ 0x36)) > 0) {
            enumPistonType = EnumPistonType.STICKY;
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            enumPistonType = EnumPistonType.DEFAULT;
        }
        return withProperty.withProperty((IProperty<Comparable>)type, enumPistonType);
    }
    
    public BlockPistonExtension() {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockPistonExtension.FACING, EnumFacing.NORTH).withProperty(BlockPistonExtension.TYPE, EnumPistonType.DEFAULT).withProperty((IProperty<Comparable>)BlockPistonExtension.SHORT, (boolean)("".length() != 0)));
        this.setStepSound(BlockPistonExtension.soundTypePiston);
        this.setHardness(0.5f);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        final BlockPos offset = blockToAir.offset(blockState.getValue((IProperty<EnumFacing>)BlockPistonExtension.FACING).getOpposite());
        final IBlockState blockState2 = world.getBlockState(offset);
        if (blockState2.getBlock() != Blocks.piston && blockState2.getBlock() != Blocks.sticky_piston) {
            world.setBlockToAir(blockToAir);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            blockState2.getBlock().onNeighborBlockChange(world, offset, blockState2, block);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockPistonExtension.FACING).getIndex();
        if (blockState.getValue(BlockPistonExtension.TYPE) == EnumPistonType.STICKY) {
            n |= (0x2A ^ 0x22);
        }
        return n;
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockPistonExtension.I["".length()]);
        TYPE = PropertyEnum.create(BlockPistonExtension.I[" ".length()], EnumPistonType.class);
        SHORT = PropertyBool.create(BlockPistonExtension.I["  ".length()]);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        Item item;
        if (world.getBlockState(blockPos).getValue(BlockPistonExtension.TYPE) == EnumPistonType.STICKY) {
            item = Item.getItemFromBlock(Blocks.sticky_piston);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            item = Item.getItemFromBlock(Blocks.piston);
        }
        return item;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockPistonExtension.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xAC ^ 0xAA);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x5C ^ 0x58);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x13 ^ 0x16);
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockPistonExtension.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockPistonExtension.FACING;
        array[" ".length()] = BlockPistonExtension.TYPE;
        array["  ".length()] = BlockPistonExtension.SHORT;
        return new BlockState(this, array);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.applyHeadBounds(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return "".length() != 0;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(".\u000f.\u001c\u0003/", "HnMum");
        BlockPistonExtension.I[" ".length()] = I("?\u0016\u0006\u0011", "Kovtd");
        BlockPistonExtension.I["  ".length()] = I(";\u000f\u001d\u001c$", "HgrnP");
    }
    
    public static EnumFacing getFacing(final int n) {
        final int n2 = n & (0x68 ^ 0x6F);
        EnumFacing front;
        if (n2 > (0x1 ^ 0x4)) {
            front = null;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            front = EnumFacing.getFront(n2);
        }
        return front;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.applyHeadBounds(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.applyCoreBounds(blockState);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void applyCoreBounds(final IBlockState blockState) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[blockState.getValue((IProperty<EnumFacing>)BlockPistonExtension.FACING).ordinal()]) {
            case 1: {
                this.setBlockBounds(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.setBlockBounds(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.setBlockBounds(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                break;
            }
            case 6: {
                this.setBlockBounds(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return "".length() != 0;
    }
    
    public void applyHeadBounds(final IBlockState blockState) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockPistonExtension.FACING);
        if (enumFacing != null) {
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 1: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockPistonExtension.FACING);
            if (enumFacing != null) {
                final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
                final Block block = world.getBlockState(offset).getBlock();
                if (block == Blocks.piston || block == Blocks.sticky_piston) {
                    world.setBlockToAir(offset);
                }
            }
        }
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
    }
    
    public enum EnumPistonType implements IStringSerializable
    {
        private static final EnumPistonType[] ENUM$VALUES;
        
        DEFAULT(EnumPistonType.I["".length()], "".length(), EnumPistonType.I[" ".length()]);
        
        private static final String[] I;
        
        STICKY(EnumPistonType.I["  ".length()], " ".length(), EnumPistonType.I["   ".length()]);
        
        private final String VARIANT;
        
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
                if (4 < 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public String toString() {
            return this.VARIANT;
        }
        
        static {
            I();
            final EnumPistonType[] enum$VALUES = new EnumPistonType["  ".length()];
            enum$VALUES["".length()] = EnumPistonType.DEFAULT;
            enum$VALUES[" ".length()] = EnumPistonType.STICKY;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0x14 ^ 0x10])["".length()] = I("\u000b\f2\u001b0\u0003\u001d", "OItZe");
            EnumPistonType.I[" ".length()] = I("-($\b\u0015/", "CGVet");
            EnumPistonType.I["  ".length()] = I("9#\u001e$\u00043", "jwWgO");
            EnumPistonType.I["   ".length()] = I("\u001a$\u0019,%\u0010", "iPpON");
        }
        
        @Override
        public String getName() {
            return this.VARIANT;
        }
        
        private EnumPistonType(final String s, final int n, final String variant) {
            this.VARIANT = variant;
        }
    }
}
