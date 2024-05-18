package net.minecraft.src;

public class EntityCrit2FX extends EntityFX
{
    private Entity theEntity;
    private int currentLife;
    private int maximumLife;
    private String particleName;
    
    public EntityCrit2FX(final World par1World, final Entity par2Entity) {
        this(par1World, par2Entity, "crit");
    }
    
    public EntityCrit2FX(final World par1World, final Entity par2Entity, final String par3Str) {
        super(par1World, par2Entity.posX, par2Entity.boundingBox.minY + par2Entity.height / 2.0f, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
        this.currentLife = 0;
        this.maximumLife = 0;
        this.theEntity = par2Entity;
        this.maximumLife = 3;
        this.particleName = par3Str;
        this.onUpdate();
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
    }
    
    @Override
    public void onUpdate() {
        for (int var1 = 0; var1 < 16; ++var1) {
            final double var2 = this.rand.nextFloat() * 2.0f - 1.0f;
            final double var3 = this.rand.nextFloat() * 2.0f - 1.0f;
            final double var4 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (var2 * var2 + var3 * var3 + var4 * var4 <= 1.0) {
                final double var5 = this.theEntity.posX + var2 * this.theEntity.width / 4.0;
                final double var6 = this.theEntity.boundingBox.minY + this.theEntity.height / 2.0f + var3 * this.theEntity.height / 4.0;
                final double var7 = this.theEntity.posZ + var4 * this.theEntity.width / 4.0;
                this.worldObj.spawnParticle(this.particleName, var5, var6, var7, var2, var3 + 0.2, var4);
            }
        }
        ++this.currentLife;
        if (this.currentLife >= this.maximumLife) {
            this.setDead();
        }
    }
    
    @Override
    public int getFXLayer() {
        return 3;
    }
}
