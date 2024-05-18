package net.minecraft.src;

class VillageAgressor
{
    public EntityLiving agressor;
    public int agressionTime;
    final Village villageObj;
    
    VillageAgressor(final Village par1Village, final EntityLiving par2EntityLiving, final int par3) {
        this.villageObj = par1Village;
        this.agressor = par2EntityLiving;
        this.agressionTime = par3;
    }
}
