package net.minecraft.src;

public class EntityMagmaCube extends EntitySlime
{
    public EntityMagmaCube(final World par1World) {
        super(par1World);
        this.texture = "/mob/lava.png";
        this.isImmuneToFire = true;
        this.landMovementFactor = 0.2f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting > 0 && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.getSlimeSize() * 3;
    }
    
    @Override
    public int getBrightnessForRender(final float par1) {
        return 15728880;
    }
    
    @Override
    public float getBrightness(final float par1) {
        return 1.0f;
    }
    
    @Override
    protected String getSlimeParticle() {
        return "flame";
    }
    
    @Override
    protected EntitySlime createInstance() {
        return new EntityMagmaCube(this.worldObj);
    }
    
    @Override
    protected int getDropItemId() {
        return Item.magmaCream.itemID;
    }
    
    @Override
    protected void dropFewItems(final boolean par1, final int par2) {
        final int var3 = this.getDropItemId();
        if (var3 > 0 && this.getSlimeSize() > 1) {
            int var4 = this.rand.nextInt(4) - 2;
            if (par2 > 0) {
                var4 += this.rand.nextInt(par2 + 1);
            }
            for (int var5 = 0; var5 < var4; ++var5) {
                this.dropItem(var3, 1);
            }
        }
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }
    
    @Override
    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }
    
    @Override
    protected void func_70808_l() {
        this.field_70813_a *= 0.9f;
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.42f + this.getSlimeSize() * 0.1f;
        this.isAirBorne = true;
    }
    
    @Override
    protected void fall(final float par1) {
    }
    
    @Override
    protected boolean canDamagePlayer() {
        return true;
    }
    
    @Override
    protected int getAttackStrength() {
        return super.getAttackStrength() + 2;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected String getJumpSound() {
        return (this.getSlimeSize() > 1) ? "mob.magmacube.big" : "mob.magmacube.small";
    }
    
    @Override
    public boolean handleLavaMovement() {
        return false;
    }
    
    @Override
    protected boolean makesSoundOnLand() {
        return true;
    }
}
