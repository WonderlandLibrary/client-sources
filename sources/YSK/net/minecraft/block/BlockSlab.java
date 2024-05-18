package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public abstract class BlockSlab extends Block
{
    public static final PropertyEnum<EnumBlockHalf> HALF;
    private static final String[] I;
    
    public abstract String getUnlocalizedName(final int p0);
    
    public BlockSlab(final Material material) {
        super(material);
        if (this.isDouble()) {
            this.fullBlock = (" ".length() != 0);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(44 + 199 - 133 + 145);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
        IBlockState withProperty2;
        if (this.isDouble()) {
            withProperty2 = withProperty;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (enumFacing != EnumFacing.DOWN && (enumFacing == EnumFacing.UP || n2 <= 0.5)) {
            withProperty2 = withProperty;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            withProperty2 = withProperty.withProperty(BlockSlab.HALF, EnumBlockHalf.TOP);
        }
        return withProperty2;
    }
    
    public abstract Object getVariant(final ItemStack p0);
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return super.getDamageValue(world, blockPos) & (0x7E ^ 0x79);
    }
    
    public abstract boolean isDouble();
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("02*\u0013", "XSFuF");
    }
    
    @Override
    public boolean isFullCube() {
        return this.isDouble();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            final IBlockState blockState = blockAccess.getBlockState(blockPos);
            if (blockState.getBlock() == this) {
                if (blockState.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
                    this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
                }
            }
        }
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public abstract IProperty<?> getVariantProperty();
    
    static {
        I();
        HALF = PropertyEnum.create(BlockSlab.I["".length()], EnumBlockHalf.class);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        if (this.isDouble()) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return this.isDouble();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        int n;
        if (this.isDouble()) {
            n = "  ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n;
    }
    
    protected static boolean isSlab(final Block block) {
        if (block != Blocks.stone_slab && block != Blocks.wooden_slab && block != Blocks.stone_slab2) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return "".length() != 0;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (this.isDouble()) {
            return super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
        }
        if (enumFacing != EnumFacing.UP && enumFacing != EnumFacing.DOWN && !super.shouldSideBeRendered(blockAccess, blockPos, enumFacing)) {
            return "".length() != 0;
        }
        final BlockPos offset = blockPos.offset(enumFacing.getOpposite());
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final IBlockState blockState2 = blockAccess.getBlockState(offset);
        int n;
        if (isSlab(blockState.getBlock()) && blockState.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        int n3;
        if (isSlab(blockState2.getBlock()) && blockState2.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
            n3 = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        int n4;
        if (n3 != 0) {
            if (enumFacing == EnumFacing.DOWN) {
                n4 = " ".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.UP && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing)) {
                n4 = " ".length();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else if (isSlab(blockState.getBlock()) && n2 != 0) {
                n4 = "".length();
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            else {
                n4 = " ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
        }
        else if (enumFacing == EnumFacing.UP) {
            n4 = " ".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.DOWN && super.shouldSideBeRendered(blockAccess, blockPos, enumFacing)) {
            n4 = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (isSlab(blockState.getBlock()) && n2 == 0) {
            n4 = "".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        return n4 != 0;
    }
    
    public enum EnumBlockHalf implements IStringSerializable
    {
        private static final EnumBlockHalf[] ENUM$VALUES;
        private static final String[] I;
        
        TOP(EnumBlockHalf.I["".length()], "".length(), EnumBlockHalf.I[" ".length()]);
        
        private final String name;
        
        BOTTOM(EnumBlockHalf.I["  ".length()], " ".length(), EnumBlockHalf.I["   ".length()]);
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
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
                if (2 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final EnumBlockHalf[] enum$VALUES = new EnumBlockHalf["  ".length()];
            enum$VALUES["".length()] = EnumBlockHalf.TOP;
            enum$VALUES[" ".length()] = EnumBlockHalf.BOTTOM;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0xA9 ^ 0xAD])["".length()] = I("\u001e\u00031", "JLaRG");
            EnumBlockHalf.I[" ".length()] = I("\u0010=\u0015", "dRedP");
            EnumBlockHalf.I["  ".length()] = I("#:#\u001d+,", "auwId");
            EnumBlockHalf.I["   ".length()] = I("\u0012<.=%\u001d", "pSZIJ");
        }
        
        private EnumBlockHalf(final String s, final int n, final String name) {
            this.name = name;
        }
    }
}
