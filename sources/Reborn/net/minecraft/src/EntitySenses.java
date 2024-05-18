package net.minecraft.src;

import java.util.*;

public class EntitySenses
{
    EntityLiving entityObj;
    List seenEntities;
    List unseenEntities;
    
    public EntitySenses(final EntityLiving par1EntityLiving) {
        this.seenEntities = new ArrayList();
        this.unseenEntities = new ArrayList();
        this.entityObj = par1EntityLiving;
    }
    
    public void clearSensingCache() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }
    
    public boolean canSee(final Entity par1Entity) {
        if (this.seenEntities.contains(par1Entity)) {
            return true;
        }
        if (this.unseenEntities.contains(par1Entity)) {
            return false;
        }
        this.entityObj.worldObj.theProfiler.startSection("canSee");
        final boolean var2 = this.entityObj.canEntityBeSeen(par1Entity);
        this.entityObj.worldObj.theProfiler.endSection();
        if (var2) {
            this.seenEntities.add(par1Entity);
        }
        else {
            this.unseenEntities.add(par1Entity);
        }
        return var2;
    }
}
