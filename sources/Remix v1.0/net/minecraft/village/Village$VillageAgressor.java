package net.minecraft.village;

import net.minecraft.entity.*;

class VillageAgressor
{
    public EntityLivingBase agressor;
    public int agressionTime;
    
    VillageAgressor(final EntityLivingBase p_i1674_2_, final int p_i1674_3_) {
        this.agressor = p_i1674_2_;
        this.agressionTime = p_i1674_3_;
    }
}
