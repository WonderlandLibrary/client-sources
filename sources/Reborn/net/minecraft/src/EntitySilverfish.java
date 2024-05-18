package net.minecraft.src;

public class EntitySilverfish extends EntityMob
{
    private int allySummonCooldown;
    
    public EntitySilverfish(final World par1World) {
        super(par1World);
        this.texture = "/mob/silverfish.png";
        this.setSize(0.3f, 0.7f);
        this.moveSpeed = 0.6f;
    }
    
    @Override
    public int getMaxHealth() {
        return 8;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        final double var1 = 8.0;
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, var1);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.silverfish.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.allySummonCooldown <= 0 && (par1DamageSource instanceof EntityDamageSource || par1DamageSource == DamageSource.magic)) {
            this.allySummonCooldown = 20;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    protected void attackEntity(final Entity par1Entity, final float par2) {
        if (this.attackTime <= 0 && par2 < 1.2f && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        }
    }
    
    @Override
    protected void playStepSound(final int par1, final int par2, final int par3, final int par4) {
        this.playSound("mob.silverfish.step", 0.15f, 1.0f);
    }
    
    @Override
    protected int getDropItemId() {
        return 0;
    }
    
    @Override
    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        if (!this.worldObj.isRemote) {
            if (this.allySummonCooldown > 0) {
                --this.allySummonCooldown;
                if (this.allySummonCooldown == 0) {
                    final int var1 = MathHelper.floor_double(this.posX);
                    final int var2 = MathHelper.floor_double(this.posY);
                    final int var3 = MathHelper.floor_double(this.posZ);
                    boolean var4 = false;
                    for (int var5 = 0; !var4 && var5 <= 5 && var5 >= -5; var5 = ((var5 <= 0) ? (1 - var5) : (0 - var5))) {
                        for (int var6 = 0; !var4 && var6 <= 10 && var6 >= -10; var6 = ((var6 <= 0) ? (1 - var6) : (0 - var6))) {
                            for (int var7 = 0; !var4 && var7 <= 10 && var7 >= -10; var7 = ((var7 <= 0) ? (1 - var7) : (0 - var7))) {
                                final int var8 = this.worldObj.getBlockId(var1 + var6, var2 + var5, var3 + var7);
                                if (var8 == Block.silverfish.blockID) {
                                    this.worldObj.destroyBlock(var1 + var6, var2 + var5, var3 + var7, false);
                                    Block.silverfish.onBlockDestroyedByPlayer(this.worldObj, var1 + var6, var2 + var5, var3 + var7, 0);
                                    if (this.rand.nextBoolean()) {
                                        var4 = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (this.entityToAttack == null && !this.hasPath()) {
                final int var1 = MathHelper.floor_double(this.posX);
                final int var2 = MathHelper.floor_double(this.posY + 0.5);
                final int var3 = MathHelper.floor_double(this.posZ);
                final int var9 = this.rand.nextInt(6);
                final int var5 = this.worldObj.getBlockId(var1 + Facing.offsetsXForSide[var9], var2 + Facing.offsetsYForSide[var9], var3 + Facing.offsetsZForSide[var9]);
                if (BlockSilverfish.getPosingIdByMetadata(var5)) {
                    this.worldObj.setBlock(var1 + Facing.offsetsXForSide[var9], var2 + Facing.offsetsYForSide[var9], var3 + Facing.offsetsZForSide[var9], Block.silverfish.blockID, BlockSilverfish.getMetadataForBlockType(var5), 3);
                    this.spawnExplosionParticle();
                    this.setDead();
                }
                else {
                    this.updateWanderPath();
                }
            }
            else if (this.entityToAttack != null && !this.hasPath()) {
                this.entityToAttack = null;
            }
        }
    }
    
    @Override
    public float getBlockPathWeight(final int par1, final int par2, final int par3) {
        return (this.worldObj.getBlockId(par1, par2 - 1, par3) == Block.stone.blockID) ? 10.0f : super.getBlockPathWeight(par1, par2, par3);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            final EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 5.0);
            return var1 == null;
        }
        return false;
    }
    
    @Override
    public int getAttackStrength(final Entity par1Entity) {
        return 1;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}
