package net.minecraft.block;

import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;

public class BlockOldLeaf extends BlockLeaves
{
    private static final String[] I;
    public static final PropertyEnum<BlockPlanks.EnumType> VARIANT;
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockOldLeaf.VARIANT;
        array[" ".length()] = BlockOldLeaf.CHECK_DECAY;
        array["  ".length()] = BlockOldLeaf.DECAYABLE;
        return new BlockState(this, array);
    }
    
    @Override
    protected int getSaplingDropChance(final IBlockState blockState) {
        int saplingDropChance;
        if (blockState.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
            saplingDropChance = (0x4A ^ 0x62);
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            saplingDropChance = super.getSaplingDropChance(blockState);
        }
        return saplingDropChance;
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockOldLeaf.I["".length()], BlockPlanks.EnumType.class, (com.google.common.base.Predicate<BlockPlanks.EnumType>)new Predicate<BlockPlanks.EnumType>() {
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
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
                    if (3 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final BlockPlanks.EnumType enumType) {
                if (enumType.getMetadata() < (0x6 ^ 0x2)) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        });
    }
    
    public BlockOldLeaf() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty((IProperty<Comparable>)BlockOldLeaf.CHECK_DECAY, " ".length() != 0).withProperty((IProperty<Comparable>)BlockOldLeaf.DECAYABLE, (boolean)(" ".length() != 0)));
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        final BlockPlanks.EnumType enumType = blockState.getValue(BlockOldLeaf.VARIANT);
        int n;
        if (enumType == BlockPlanks.EnumType.SPRUCE) {
            n = ColorizerFoliage.getFoliageColorPine();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else if (enumType == BlockPlanks.EnumType.BIRCH) {
            n = ColorizerFoliage.getFoliageColorBirch();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n = super.getRenderColor(blockState);
        }
        return n;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockOldLeaf.VARIANT).getMetadata();
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Item.getItemFromBlock(this), " ".length(), blockState.getValue(BlockOldLeaf.VARIANT).getMetadata()));
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockOldLeaf.VARIANT).getMetadata();
        if (!blockState.getValue((IProperty<Boolean>)BlockOldLeaf.DECAYABLE)) {
            n |= (0x43 ^ 0x47);
        }
        if (blockState.getValue((IProperty<Boolean>)BlockOldLeaf.CHECK_DECAY)) {
            n |= (0xB6 ^ 0xBE);
        }
        return n;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u00156\u0018\f.\r#", "cWjeO");
    }
    
    @Override
    protected void dropApple(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (blockState.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK && world.rand.nextInt(n) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.apple, " ".length(), "".length()));
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), " ".length(), blockState.getValue(BlockOldLeaf.VARIANT).getMetadata());
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.OAK.getMetadata()));
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.SPRUCE.getMetadata()));
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.BIRCH.getMetadata()));
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.JUNGLE.getMetadata()));
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final BlockPlanks.EnumType enumType = blockState.getValue(BlockOldLeaf.VARIANT);
            if (enumType == BlockPlanks.EnumType.SPRUCE) {
                return ColorizerFoliage.getFoliageColorPine();
            }
            if (enumType == BlockPlanks.EnumType.BIRCH) {
                return ColorizerFoliage.getFoliageColorBirch();
            }
        }
        return super.colorMultiplier(blockAccess, blockPos, n);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockOldLeaf.VARIANT, this.getWoodType(n));
        final PropertyBool decayable = BlockOldLeaf.DECAYABLE;
        int n2;
        if ((n & (0x8B ^ 0x8F)) == 0x0) {
            n2 = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)decayable, n2 != 0);
        final PropertyBool check_DECAY = BlockOldLeaf.CHECK_DECAY;
        int n3;
        if ((n & (0x3F ^ 0x37)) > 0) {
            n3 = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return withProperty2.withProperty((IProperty<Comparable>)check_DECAY, n3 != 0);
    }
    
    @Override
    public BlockPlanks.EnumType getWoodType(final int n) {
        return BlockPlanks.EnumType.byMetadata((n & "   ".length()) % (0x99 ^ 0x9D));
    }
}
