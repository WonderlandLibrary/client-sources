package net.minecraft.entity.ai;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;

public class EntitySenses
{
    EntityLiving entityObj;
    List seenEntities;
    List unseenEntities;
    
    public EntitySenses(final EntityLiving p_i1672_1_) {
        this.seenEntities = Lists.newArrayList();
        this.unseenEntities = Lists.newArrayList();
        this.entityObj = p_i1672_1_;
    }
    
    public void clearSensingCache() {
        this.seenEntities.clear();
        this.unseenEntities.clear();
    }
    
    public boolean canSee(final Entity p_75522_1_) {
        if (this.seenEntities.contains(p_75522_1_)) {
            return true;
        }
        if (this.unseenEntities.contains(p_75522_1_)) {
            return false;
        }
        this.entityObj.worldObj.theProfiler.startSection("canSee");
        final boolean var2 = this.entityObj.canEntityBeSeen(p_75522_1_);
        this.entityObj.worldObj.theProfiler.endSection();
        if (var2) {
            this.seenEntities.add(p_75522_1_);
        }
        else {
            this.unseenEntities.add(p_75522_1_);
        }
        return var2;
    }
}
