package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable
{
    public EntityExpBottle(World worldIn)
    {
        super(worldIn);
    }

    public EntityExpBottle(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityExpBottle(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public static void func_189666_a(DataFixer p_189666_0_)
    {
        EntityThrowable.func_189661_a(p_189666_0_, "ThrowableExpBottle");
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.07F;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (!this.worldObj.isRemote)
        {
            this.worldObj.playEvent(2002, new BlockPos(this), 0);
            int i = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);

            while (i > 0)
            {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
            }

            this.setDead();
        }
    }
}
