package net.minecraft.src;

public abstract class EntityCreature extends EntityLiving
{
    private PathEntity pathToEntity;
    protected Entity entityToAttack;
    protected boolean hasAttacked;
    protected int fleeingTick;
    
    public EntityCreature(final World par1World) {
        super(par1World);
        this.hasAttacked = false;
        this.fleeingTick = 0;
    }
    
    protected boolean isMovementCeased() {
        return false;
    }
    
    @Override
    protected void updateEntityActionState() {
        this.worldObj.theProfiler.startSection("ai");
        if (this.fleeingTick > 0) {
            --this.fleeingTick;
        }
        this.hasAttacked = this.isMovementCeased();
        final float var1 = 16.0f;
        if (this.entityToAttack == null) {
            this.entityToAttack = this.findPlayerToAttack();
            if (this.entityToAttack != null) {
                this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var1, true, false, false, true);
            }
        }
        else if (this.entityToAttack.isEntityAlive()) {
            final float var2 = this.entityToAttack.getDistanceToEntity(this);
            if (this.canEntityBeSeen(this.entityToAttack)) {
                this.attackEntity(this.entityToAttack, var2);
            }
        }
        else {
            this.entityToAttack = null;
        }
        this.worldObj.theProfiler.endSection();
        if (!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0)) {
            this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var1, true, false, false, true);
        }
        else if (!this.hasAttacked && ((this.pathToEntity == null && this.rand.nextInt(180) == 0) || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100) {
            this.updateWanderPath();
        }
        final int var3 = MathHelper.floor_double(this.boundingBox.minY + 0.5);
        final boolean var4 = this.isInWater();
        final boolean var5 = this.handleLavaMovement();
        this.rotationPitch = 0.0f;
        if (this.pathToEntity != null && this.rand.nextInt(100) != 0) {
            this.worldObj.theProfiler.startSection("followpath");
            Vec3 var6 = this.pathToEntity.getPosition(this);
            final double var7 = this.width * 2.0f;
            while (var6 != null && var6.squareDistanceTo(this.posX, var6.yCoord, this.posZ) < var7 * var7) {
                this.pathToEntity.incrementPathIndex();
                if (this.pathToEntity.isFinished()) {
                    var6 = null;
                    this.pathToEntity = null;
                }
                else {
                    var6 = this.pathToEntity.getPosition(this);
                }
            }
            this.isJumping = false;
            if (var6 != null) {
                final double var8 = var6.xCoord - this.posX;
                final double var9 = var6.zCoord - this.posZ;
                final double var10 = var6.yCoord - var3;
                final float var11 = (float)(Math.atan2(var9, var8) * 180.0 / 3.141592653589793) - 90.0f;
                float var12 = MathHelper.wrapAngleTo180_float(var11 - this.rotationYaw);
                this.moveForward = this.moveSpeed;
                if (var12 > 30.0f) {
                    var12 = 30.0f;
                }
                if (var12 < -30.0f) {
                    var12 = -30.0f;
                }
                this.rotationYaw += var12;
                if (this.hasAttacked && this.entityToAttack != null) {
                    final double var13 = this.entityToAttack.posX - this.posX;
                    final double var14 = this.entityToAttack.posZ - this.posZ;
                    final float var15 = this.rotationYaw;
                    this.rotationYaw = (float)(Math.atan2(var14, var13) * 180.0 / 3.141592653589793) - 90.0f;
                    var12 = (var15 - this.rotationYaw + 90.0f) * 3.1415927f / 180.0f;
                    this.moveStrafing = -MathHelper.sin(var12) * this.moveForward * 1.0f;
                    this.moveForward = MathHelper.cos(var12) * this.moveForward * 1.0f;
                }
                if (var10 > 0.0) {
                    this.isJumping = true;
                }
            }
            if (this.entityToAttack != null) {
                this.faceEntity(this.entityToAttack, 30.0f, 30.0f);
            }
            if (this.isCollidedHorizontally && !this.hasPath()) {
                this.isJumping = true;
            }
            if (this.rand.nextFloat() < 0.8f && (var4 || var5)) {
                this.isJumping = true;
            }
            this.worldObj.theProfiler.endSection();
        }
        else {
            super.updateEntityActionState();
            this.pathToEntity = null;
        }
    }
    
    protected void updateWanderPath() {
        this.worldObj.theProfiler.startSection("stroll");
        boolean var1 = false;
        int var2 = -1;
        int var3 = -1;
        int var4 = -1;
        float var5 = -99999.0f;
        for (int var6 = 0; var6 < 10; ++var6) {
            final int var7 = MathHelper.floor_double(this.posX + this.rand.nextInt(13) - 6.0);
            final int var8 = MathHelper.floor_double(this.posY + this.rand.nextInt(7) - 3.0);
            final int var9 = MathHelper.floor_double(this.posZ + this.rand.nextInt(13) - 6.0);
            final float var10 = this.getBlockPathWeight(var7, var8, var9);
            if (var10 > var5) {
                var5 = var10;
                var2 = var7;
                var3 = var8;
                var4 = var9;
                var1 = true;
            }
        }
        if (var1) {
            this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, var2, var3, var4, 10.0f, true, false, false, true);
        }
        this.worldObj.theProfiler.endSection();
    }
    
    protected void attackEntity(final Entity par1Entity, final float par2) {
    }
    
    public float getBlockPathWeight(final int par1, final int par2, final int par3) {
        return 0.0f;
    }
    
    protected Entity findPlayerToAttack() {
        return null;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        return super.getCanSpawnHere() && this.getBlockPathWeight(var1, var2, var3) >= 0.0f;
    }
    
    public boolean hasPath() {
        return this.pathToEntity != null;
    }
    
    public void setPathToEntity(final PathEntity par1PathEntity) {
        this.pathToEntity = par1PathEntity;
    }
    
    public Entity getEntityToAttack() {
        return this.entityToAttack;
    }
    
    public void setTarget(final Entity par1Entity) {
        this.entityToAttack = par1Entity;
    }
    
    @Override
    public float getSpeedModifier() {
        float var1 = super.getSpeedModifier();
        if (this.fleeingTick > 0 && !this.isAIEnabled()) {
            var1 *= 2.0f;
        }
        return var1;
    }
}
