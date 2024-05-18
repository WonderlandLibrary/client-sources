package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.events.SoulSandSlowdownEvent;

public class BlockSoulSand extends Block
{


    public BlockSoulSand()
    {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        float var4 = 0.125F;
        return new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)((float)(pos.getY() + 1) - var4), (double)(pos.getZ() + 1));
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        final SoulSandSlowdownEvent event = new SoulSandSlowdownEvent();
        XIV.getInstance().getListenerManager().call(event);

        if (event.isCancelled()) {
            return;
        }

        entityIn.motionX *= 0.4D;
        entityIn.motionZ *= 0.4D;
    }
}
