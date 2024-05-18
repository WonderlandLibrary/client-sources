package de.labystudio.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

public class DynamicLightSourceContainer
{
    private final IDynamicLightSource lightSource;
    private int prevX;
    private int prevY;
    private int prevZ;
    private int x;
    private int y;
    private int z;

    public DynamicLightSourceContainer(IDynamicLightSource light)
    {
        this.lightSource = light;
        this.x = this.y = this.z = this.prevX = this.prevY = this.prevZ = 0;
    }

    public boolean onUpdate()
    {
        Entity entity = this.lightSource.getAttachmentEntity();

        if (!entity.isEntityAlive())
        {
            return true;
        }
        else
        {
            if (this.hasEntityMoved(entity))
            {
                entity.worldObj.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(this.x, this.y, this.z));
                entity.worldObj.checkLightFor(EnumSkyBlock.BLOCK, new BlockPos(this.prevX, this.prevY, this.prevZ));
            }

            return false;
        }
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getZ()
    {
        return this.z;
    }

    public IDynamicLightSource getLightSource()
    {
        return this.lightSource;
    }

    private boolean hasEntityMoved(Entity ent)
    {
        int i = MathHelper.floor_double(ent.posX);
        int j = MathHelper.floor_double(ent.posY);
        int k = MathHelper.floor_double(ent.posZ);

        if (i == this.x && j == this.y && k == this.z)
        {
            return false;
        }
        else
        {
            this.prevX = this.x;
            this.prevY = this.y;
            this.prevZ = this.z;
            this.x = i;
            this.y = j;
            this.z = k;
            return true;
        }
    }

    public boolean equals(Object o)
    {
        if (o instanceof DynamicLightSourceContainer)
        {
            DynamicLightSourceContainer dynamiclightsourcecontainer = (DynamicLightSourceContainer)o;

            if (dynamiclightsourcecontainer.lightSource == this.lightSource)
            {
                return true;
            }
        }

        return false;
    }

    public int hashCode()
    {
        return this.lightSource.getAttachmentEntity().getEntityId();
    }
}
