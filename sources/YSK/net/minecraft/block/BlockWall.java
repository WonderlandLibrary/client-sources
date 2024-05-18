package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockWall extends Block
{
    public static final PropertyEnum<EnumType> VARIANT;
    public static final PropertyBool EAST;
    private static final String[] I;
    public static final PropertyBool WEST;
    public static final PropertyBool SOUTH;
    public static final PropertyBool UP;
    public static final PropertyBool NORTH;
    
    static {
        I();
        UP = PropertyBool.create(BlockWall.I["".length()]);
        NORTH = PropertyBool.create(BlockWall.I[" ".length()]);
        EAST = PropertyBool.create(BlockWall.I["  ".length()]);
        SOUTH = PropertyBool.create(BlockWall.I["   ".length()]);
        WEST = PropertyBool.create(BlockWall.I[0xAB ^ 0xAF]);
        VARIANT = PropertyEnum.create(BlockWall.I[0x4A ^ 0x4F], EnumType.class);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockWall.VARIANT).getMetadata();
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + BlockWall.I[0x1E ^ 0x18] + EnumType.NORMAL.getUnlocalizedName() + BlockWall.I[0xBE ^ 0xB9]);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final boolean canConnectTo = this.canConnectTo(blockAccess, blockPos.north());
        final boolean canConnectTo2 = this.canConnectTo(blockAccess, blockPos.south());
        final boolean canConnectTo3 = this.canConnectTo(blockAccess, blockPos.west());
        final boolean canConnectTo4 = this.canConnectTo(blockAccess, blockPos.east());
        float n = 0.25f;
        float n2 = 0.75f;
        float n3 = 0.25f;
        float n4 = 0.75f;
        float n5 = 1.0f;
        if (canConnectTo) {
            n3 = 0.0f;
        }
        if (canConnectTo2) {
            n4 = 1.0f;
        }
        if (canConnectTo3) {
            n = 0.0f;
        }
        if (canConnectTo4) {
            n2 = 1.0f;
        }
        if (canConnectTo && canConnectTo2 && !canConnectTo3 && !canConnectTo4) {
            n5 = 0.8125f;
            n = 0.3125f;
            n2 = 0.6875f;
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else if (!canConnectTo && !canConnectTo2 && canConnectTo3 && canConnectTo4) {
            n5 = 0.8125f;
            n3 = 0.3125f;
            n4 = 0.6875f;
        }
        this.setBlockBounds(n, 0.0f, n3, n2, n5, n4);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x82 ^ 0x84];
        array["".length()] = BlockWall.UP;
        array[" ".length()] = BlockWall.NORTH;
        array["  ".length()] = BlockWall.EAST;
        array["   ".length()] = BlockWall.WEST;
        array[0x43 ^ 0x47] = BlockWall.SOUTH;
        array[0x9B ^ 0x9E] = BlockWall.VARIANT;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        int n;
        if (enumFacing == EnumFacing.DOWN) {
            n = (super.shouldSideBeRendered(blockAccess, blockPos, enumFacing) ? 1 : 0);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return "".length() != 0;
    }
    
    public BlockWall(final Block block) {
        super(block.blockMaterial);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockWall.UP, "".length() != 0).withProperty((IProperty<Comparable>)BlockWall.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockWall.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockWall.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockWall.WEST, "".length() != 0).withProperty(BlockWall.VARIANT, EnumType.NORMAL));
        this.setHardness(block.blockHardness);
        this.setResistance(block.blockResistance / 3.0f);
        this.setStepSound(block.stepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final PropertyBool up = BlockWall.UP;
        int n;
        if (blockAccess.isAirBlock(blockPos.up())) {
            n = "".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return blockState.withProperty((IProperty<Comparable>)up, n != 0).withProperty((IProperty<Comparable>)BlockWall.NORTH, this.canConnectTo(blockAccess, blockPos.north())).withProperty((IProperty<Comparable>)BlockWall.EAST, this.canConnectTo(blockAccess, blockPos.east())).withProperty((IProperty<Comparable>)BlockWall.SOUTH, this.canConnectTo(blockAccess, blockPos.south())).withProperty((IProperty<Comparable>)BlockWall.WEST, this.canConnectTo(blockAccess, blockPos.west()));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockWall.VARIANT).getMetadata();
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
        return this.getDefaultState().withProperty(BlockWall.VARIANT, EnumType.byMetadata(n));
    }
    
    public boolean canConnectTo(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos).getBlock();
        int n;
        if (block == Blocks.barrier) {
            n = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (block != this && !(block instanceof BlockFenceGate)) {
            if (block.blockMaterial.isOpaque() && block.isFullCube()) {
                if (block.blockMaterial != Material.gourd) {
                    n = " ".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
            }
            else {
                n = "".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        this.maxY = 1.5;
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumType[] values;
        final int length = (values = EnumType.values()).length;
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[0x6F ^ 0x67])["".length()] = I("\u0001&", "tVUij");
        BlockWall.I[" ".length()] = I("\u001c\u000e5\u001a$", "raGnL");
        BlockWall.I["  ".length()] = I("\u0006\u0004\u0012\u0007", "ceasD");
        BlockWall.I["   ".length()] = I("\u0016\u0005\u0017\u0001\u0011", "ejbuy");
        BlockWall.I[0xB9 ^ 0xBD] = I("$\u000b*\u0011", "SnYeC");
        BlockWall.I[0xBB ^ 0xBE] = I("?\"\u0007\u001e\r'7", "ICuwl");
        BlockWall.I[0x75 ^ 0x73] = I("\u007f", "QHxfF");
        BlockWall.I[0x5D ^ 0x5A] = I("l)\u0019\u001c\u001c", "BGxqy");
    }
    
    public enum EnumType implements IStringSerializable
    {
        private String unlocalizedName;
        private static final EnumType[] ENUM$VALUES;
        private static final EnumType[] META_LOOKUP;
        
        MOSSY(EnumType.I["   ".length()], " ".length(), " ".length(), EnumType.I[0x6D ^ 0x69], EnumType.I[0x2D ^ 0x28]), 
        NORMAL(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], EnumType.I["  ".length()]);
        
        private final int meta;
        private final String name;
        private static final String[] I;
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType["  ".length()];
            enum$VALUES["".length()] = EnumType.NORMAL;
            enum$VALUES[" ".length()] = EnumType.MOSSY;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
        
        private static void I() {
            (I = new String[0x39 ^ 0x3F])["".length()] = I(">\u000e<8,<", "pAnum");
            EnumType.I[" ".length()] = I("\u0006\b\u0001\u0001\r\u0000\u0014\u0017\f\u000f\u0000", "egcca");
            EnumType.I["  ".length()] = I("\"\u000e'\u0019) ", "LaUtH");
            EnumType.I["   ".length()] = I("\u0019+\u0000'\u001a", "TdStC");
            EnumType.I[0x8B ^ 0x8F] = I("\u001f\u000e58!-\u0002)):\u001e\u00045?7\u001c\u0004", "raFKX");
            EnumType.I[0x18 ^ 0x1D] = I("\u001c\u0001\u00142\t", "qngAp");
        }
    }
}
