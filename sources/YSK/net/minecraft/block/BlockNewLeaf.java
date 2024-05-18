package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;

public class BlockNewLeaf extends BlockLeaves
{
    public static final PropertyEnum<BlockPlanks.EnumType> VARIANT;
    private static final String[] I;
    
    public BlockNewLeaf() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty((IProperty<Comparable>)BlockNewLeaf.CHECK_DECAY, " ".length() != 0).withProperty((IProperty<Comparable>)BlockNewLeaf.DECAYABLE, (boolean)(" ".length() != 0)));
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), "".length()));
        list.add(new ItemStack(item, " ".length(), " ".length()));
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState) & "   ".length();
    }
    
    @Override
    protected void dropApple(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (blockState.getValue(BlockNewLeaf.VARIANT) == BlockPlanks.EnumType.DARK_OAK && world.rand.nextInt(n) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.apple, " ".length(), "".length()));
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), " ".length(), blockState.getValue(BlockNewLeaf.VARIANT).getMetadata() - (0x7D ^ 0x79));
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0005\r*\r\n\u001d\u0018", "slXdk");
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockNewLeaf.I["".length()], BlockPlanks.EnumType.class, (com.google.common.base.Predicate<BlockPlanks.EnumType>)new Predicate<BlockPlanks.EnumType>() {
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
            }
            
            public boolean apply(final BlockPlanks.EnumType enumType) {
                if (enumType.getMetadata() >= (0x4A ^ 0x4E)) {
                    return " ".length() != 0;
                }
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
                    if (0 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
    }
    
    @Override
    public BlockPlanks.EnumType getWoodType(final int n) {
        return BlockPlanks.EnumType.byMetadata((n & "   ".length()) + (0x43 ^ 0x47));
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Item.getItemFromBlock(this), " ".length(), blockState.getValue(BlockNewLeaf.VARIANT).getMetadata() - (0x8E ^ 0x8A)));
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockNewLeaf.VARIANT).getMetadata();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockNewLeaf.VARIANT;
        array[" ".length()] = BlockNewLeaf.CHECK_DECAY;
        array["  ".length()] = BlockNewLeaf.DECAYABLE;
        return new BlockState(this, array);
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
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockNewLeaf.VARIANT, this.getWoodType(n));
        final PropertyBool decayable = BlockNewLeaf.DECAYABLE;
        int n2;
        if ((n & (0xB8 ^ 0xBC)) == 0x0) {
            n2 = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)decayable, n2 != 0);
        final PropertyBool check_DECAY = BlockNewLeaf.CHECK_DECAY;
        int n3;
        if ((n & (0x13 ^ 0x1B)) > 0) {
            n3 = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return withProperty2.withProperty((IProperty<Comparable>)check_DECAY, n3 != 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockNewLeaf.VARIANT).getMetadata() - (0x70 ^ 0x74);
        if (!blockState.getValue((IProperty<Boolean>)BlockNewLeaf.DECAYABLE)) {
            n |= (0x79 ^ 0x7D);
        }
        if (blockState.getValue((IProperty<Boolean>)BlockNewLeaf.CHECK_DECAY)) {
            n |= (0xA8 ^ 0xA0);
        }
        return n;
    }
}
