package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStructure extends BlockContainer
{
    public static final PropertyEnum<TileEntityStructure.Mode> MODE = PropertyEnum.<TileEntityStructure.Mode>create("mode", TileEntityStructure.Mode.class);

    public BlockStructure()
    {
        super(Material.IRON, MapColor.SILVER);
        this.setDefaultState(this.blockState.getBaseState());
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityStructure();
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity instanceof TileEntityStructure ? ((TileEntityStructure)tileentity).func_189701_a(playerIn) : false;
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityStructure)
            {
                TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity;
                tileentitystructure.func_189720_a(placer);
            }
        }
    }

    @Nullable
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return super.getItem(worldIn, pos, state);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 0;
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(MODE, TileEntityStructure.Mode.DATA);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(MODE, TileEntityStructure.Mode.getById(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((TileEntityStructure.Mode)state.getValue(MODE)).getModeId();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {MODE});
    }

    public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_)
    {
        if (!p_189540_2_.isRemote)
        {
            TileEntity tileentity = p_189540_2_.getTileEntity(p_189540_3_);

            if (tileentity instanceof TileEntityStructure)
            {
                TileEntityStructure tileentitystructure = (TileEntityStructure)tileentity;
                boolean flag = p_189540_2_.isBlockPowered(p_189540_3_);
                boolean flag1 = tileentitystructure.func_189722_G();

                if (flag && !flag1)
                {
                    tileentitystructure.func_189723_d(true);
                    this.func_189874_a(tileentitystructure);
                }
                else if (!flag && flag1)
                {
                    tileentitystructure.func_189723_d(false);
                }
            }
        }
    }

    private void func_189874_a(TileEntityStructure p_189874_1_)
    {
        switch (p_189874_1_.func_189700_k())
        {
            case SAVE:
                p_189874_1_.func_189712_b(false);
                break;

            case LOAD:
                p_189874_1_.func_189714_c(false);
                break;

            case CORNER:
                p_189874_1_.func_189706_E();

            case DATA:
        }
    }
}
