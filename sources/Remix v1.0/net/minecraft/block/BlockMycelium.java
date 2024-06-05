package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;

public class BlockMycelium extends Block
{
    public static final PropertyBool SNOWY_PROP;
    
    protected BlockMycelium() {
        super(Material.grass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockMycelium.SNOWY_PROP, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final Block var4 = worldIn.getBlockState(pos.offsetUp()).getBlock();
        return state.withProperty(BlockMycelium.SNOWY_PROP, var4 == Blocks.snow || var4 == Blocks.snow_layer);
    }
    
    @Override
    public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.isRemote) {
            if (worldIn.getLightFromNeighbors(pos.offsetUp()) < 4 && worldIn.getBlockState(pos.offsetUp()).getBlock().getLightOpacity() > 2) {
                worldIn.setBlockState(pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
            }
            else if (worldIn.getLightFromNeighbors(pos.offsetUp()) >= 9) {
                for (int var5 = 0; var5 < 4; ++var5) {
                    final BlockPos var6 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                    final IBlockState var7 = worldIn.getBlockState(var6);
                    final Block var8 = worldIn.getBlockState(var6.offsetUp()).getBlock();
                    if (var7.getBlock() == Blocks.dirt && var7.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(var6.offsetUp()) >= 4 && var8.getLightOpacity() <= 2) {
                        worldIn.setBlockState(var6, this.getDefaultState());
                    }
                }
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        super.randomDisplayTick(worldIn, pos, state, rand);
        if (rand.nextInt(10) == 0) {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat(), pos.getY() + 1.1f, pos.getZ() + rand.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockMycelium.SNOWY_PROP });
    }
    
    static {
        SNOWY_PROP = PropertyBool.create("snowy");
    }
}
