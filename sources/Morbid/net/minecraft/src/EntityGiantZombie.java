package net.minecraft.src;

public class EntityGiantZombie extends EntityMob
{
    public EntityGiantZombie(final World par1World) {
        super(par1World);
        this.texture = "/mob/zombie.png";
        this.moveSpeed = 0.5f;
        this.yOffset *= 6.0f;
        this.setSize(this.width * 6.0f, this.height * 6.0f);
    }
    
    @Override
    public int getMaxHealth() {
        return 100;
    }
    
    @Override
    public float getBlockPathWeight(final int par1, final int par2, final int par3) {
        return this.worldObj.getLightBrightness(par1, par2, par3) - 0.5f;
    }
    
    @Override
    public int getAttackStrength(final Entity par1Entity) {
        return 50;
    }
}
