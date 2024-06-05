package net.minecraft.src;

public class EntityHugeExplodeFX extends EntityFX
{
    private int timeSinceStart;
    private int maximumTime;
    
    public EntityHugeExplodeFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.timeSinceStart = 0;
        this.maximumTime = 0;
        this.maximumTime = 8;
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
    }
    
    @Override
    public void onUpdate() {
        for (int var1 = 0; var1 < 6; ++var1) {
            final double var2 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            final double var3 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            final double var4 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            this.worldObj.spawnParticle("largeexplode", var2, var3, var4, this.timeSinceStart / this.maximumTime, 0.0, 0.0);
        }
        ++this.timeSinceStart;
        if (this.timeSinceStart == this.maximumTime) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 1;
    }
}
