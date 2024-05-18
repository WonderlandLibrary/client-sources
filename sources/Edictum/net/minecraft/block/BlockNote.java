package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNote extends BlockContainer
{
    private static final List<SoundEvent> INSTRUMENTS = Lists.newArrayList(new SoundEvent[] {SoundEvents.BLOCK_NOTE_HARP, SoundEvents.BLOCK_NOTE_BASEDRUM, SoundEvents.BLOCK_NOTE_SNARE, SoundEvents.BLOCK_NOTE_HAT, SoundEvents.BLOCK_NOTE_BASS});

    public BlockNote()
    {
        super(Material.WOOD);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_)
    {
        boolean flag = p_189540_2_.isBlockPowered(p_189540_3_);
        TileEntity tileentity = p_189540_2_.getTileEntity(p_189540_3_);

        if (tileentity instanceof TileEntityNote)
        {
            TileEntityNote tileentitynote = (TileEntityNote)tileentity;

            if (tileentitynote.previousRedstoneState != flag)
            {
                if (flag)
                {
                    tileentitynote.triggerNote(p_189540_2_, p_189540_3_);
                }

                tileentitynote.previousRedstoneState = flag;
            }
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityNote)
            {
                TileEntityNote tileentitynote = (TileEntityNote)tileentity;
                tileentitynote.changePitch();
                tileentitynote.triggerNote(worldIn, pos);
                playerIn.addStat(StatList.NOTEBLOCK_TUNED);
            }

            return true;
        }
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        if (!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityNote)
            {
                ((TileEntityNote)tileentity).triggerNote(worldIn, pos);
                playerIn.addStat(StatList.NOTEBLOCK_PLAYED);
            }
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityNote();
    }

    private SoundEvent getInstrument(int p_185576_1_)
    {
        if (p_185576_1_ < 0 || p_185576_1_ >= INSTRUMENTS.size())
        {
            p_185576_1_ = 0;
        }

        return (SoundEvent)INSTRUMENTS.get(p_185576_1_);
    }

    public boolean func_189539_a(IBlockState p_189539_1_, World p_189539_2_, BlockPos p_189539_3_, int p_189539_4_, int p_189539_5_)
    {
        float f = (float)Math.pow(2.0D, (double)(p_189539_5_ - 12) / 12.0D);
        p_189539_2_.playSound((EntityPlayer)null, p_189539_3_, this.getInstrument(p_189539_4_), SoundCategory.RECORDS, 3.0F, f);
        p_189539_2_.spawnParticle(EnumParticleTypes.NOTE, (double)p_189539_3_.getX() + 0.5D, (double)p_189539_3_.getY() + 1.2D, (double)p_189539_3_.getZ() + 0.5D, (double)p_189539_5_ / 24.0D, 0.0D, 0.0D, new int[0]);
        return true;
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
