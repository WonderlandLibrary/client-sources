package net.minecraft.entity.projectile;

import net.minecraft.entity.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public abstract class EntityFireball extends Entity
{
    private boolean inGround;
    public EntityLivingBase shootingEntity;
    private int xTile;
    private static final String[] I;
    public double accelerationX;
    private int ticksInAir;
    private int zTile;
    private int yTile;
    public double accelerationZ;
    private Block inTile;
    public double accelerationY;
    private int ticksAlive;
    
    @Override
    protected void entityInit() {
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort(EntityFireball.I["".length()], (short)this.xTile);
        nbtTagCompound.setShort(EntityFireball.I[" ".length()], (short)this.yTile);
        nbtTagCompound.setShort(EntityFireball.I["  ".length()], (short)this.zTile);
        final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(this.inTile);
        final String s = EntityFireball.I["   ".length()];
        String string;
        if (resourceLocation == null) {
            string = EntityFireball.I[0xB8 ^ 0xBC];
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            string = resourceLocation.toString();
        }
        nbtTagCompound.setString(s, string);
        final String s2 = EntityFireball.I[0x22 ^ 0x27];
        int n;
        if (this.inGround) {
            n = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        nbtTagCompound.setByte(s2, (byte)n);
        final String s3 = EntityFireball.I[0x38 ^ 0x3E];
        final double[] array = new double["   ".length()];
        array["".length()] = this.motionX;
        array[" ".length()] = this.motionY;
        array["  ".length()] = this.motionZ;
        nbtTagCompound.setTag(s3, this.newDoubleNBTList(array));
    }
    
    @Override
    public void onUpdate() {
        if (this.worldObj.isRemote || ((this.shootingEntity == null || !this.shootingEntity.isDead) && this.worldObj.isBlockLoaded(new BlockPos(this)))) {
            super.onUpdate();
            this.setFire(" ".length());
            if (this.inGround) {
                if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
                    this.ticksAlive += " ".length();
                    if (this.ticksAlive == 36 + 426 - 51 + 189) {
                        this.setDead();
                    }
                    return;
                }
                this.inGround = ("".length() != 0);
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.ticksAlive = "".length();
                this.ticksInAir = "".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                this.ticksInAir += " ".length();
            }
            MovingObjectPosition rayTraceBlocks = this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY, this.posZ), new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ));
            final Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
            Vec3 vec4 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (rayTraceBlocks != null) {
                vec4 = new Vec3(rayTraceBlocks.hitVec.xCoord, rayTraceBlocks.hitVec.yCoord, rayTraceBlocks.hitVec.zCoord);
            }
            Entity entity = null;
            final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double n = 0.0;
            int i = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
            while (i < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity2 = entitiesWithinAABBExcludingEntity.get(i);
                if (entity2.canBeCollidedWith() && (!entity2.isEntityEqual(this.shootingEntity) || this.ticksInAir >= (0x22 ^ 0x3B))) {
                    final float n2 = 0.3f;
                    final MovingObjectPosition calculateIntercept = entity2.getEntityBoundingBox().expand(n2, n2, n2).calculateIntercept(vec3, vec4);
                    if (calculateIntercept != null) {
                        final double squareDistanceTo = vec3.squareDistanceTo(calculateIntercept.hitVec);
                        if (squareDistanceTo < n || n == 0.0) {
                            entity = entity2;
                            n = squareDistanceTo;
                        }
                    }
                }
                ++i;
            }
            if (entity != null) {
                rayTraceBlocks = new MovingObjectPosition(entity);
            }
            if (rayTraceBlocks != null) {
                this.onImpact(rayTraceBlocks);
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) + 90.0f;
            this.rotationPitch = (float)(MathHelper.func_181159_b(sqrt_double, this.motionY) * 180.0 / 3.141592653589793) - 90.0f;
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
            float motionFactor = this.getMotionFactor();
            if (this.isInWater()) {
                int j = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (j < (0x21 ^ 0x25)) {
                    final float n3 = 0.25f;
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * n3, this.posY - this.motionY * n3, this.posZ - this.motionZ * n3, this.motionX, this.motionY, this.motionZ, new int["".length()]);
                    ++j;
                }
                motionFactor = 0.8f;
            }
            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= motionFactor;
            this.motionY *= motionFactor;
            this.motionZ *= motionFactor;
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0, new int["".length()]);
            this.setPosition(this.posX, this.posY, this.posZ);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            this.setDead();
        }
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 265401 + 10920767 - 4491453 + 9034165;
    }
    
    private static void I() {
        (I = new String[0x83 ^ 0x93])["".length()] = I("\r\u0003.\u0000\t", "uWGll");
        EntityFireball.I[" ".length()] = I("\u0000\r\"82", "yYKTW");
        EntityFireball.I["  ".length()] = I("8\u001a;\u001e\u001f", "BNRrz");
        EntityFireball.I["   ".length()] = I("\r\u000b\u0016\u001b#\u0001", "deBrO");
        EntityFireball.I[0x65 ^ 0x61] = I("", "dlaWz");
        EntityFireball.I[0x27 ^ 0x22] = I("\b \u000e\u0006,\u0014 -", "aNItC");
        EntityFireball.I[0xE ^ 0x8] = I("<:\b\u0016\u0010,:\u0015\u001d", "XSzss");
        EntityFireball.I[0x3 ^ 0x4] = I("\u0001\u0016<\u000b+", "yBUgN");
        EntityFireball.I[0x45 ^ 0x4D] = I("!\u001f\"*\u0016", "XKKFs");
        EntityFireball.I[0x91 ^ 0x98] = I(",\u0004;4#", "VPRXF");
        EntityFireball.I[0x62 ^ 0x68] = I("-\f\u0012\u0006\u0014!", "DbFox");
        EntityFireball.I[0xBD ^ 0xB6] = I("\u001c*1$>\u0010", "uDeMR");
        EntityFireball.I[0xCD ^ 0xC1] = I("\u0005\f\u0000\u0011.\t", "lbTxB");
        EntityFireball.I[0x64 ^ 0x69] = I(":\u001c\u0003!)&\u001c ", "SrDSF");
        EntityFireball.I[0x8F ^ 0x81] = I("\u0003*\u0019(\u0014\u0013*\u0004#", "gCkMw");
        EntityFireball.I[0x21 ^ 0x2E] = I("\u00033\u001b\u0006;\u00133\u0006\r", "gZicX");
    }
    
    public EntityFireball(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(n, n2, n3, this.rotationYaw, this.rotationPitch);
        this.setPosition(n, n2, n3);
        final double n7 = MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
        this.accelerationX = n4 / n7 * 0.1;
        this.accelerationY = n5 / n7 * 0.1;
        this.accelerationZ = n6 / n7 * 0.1;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort(EntityFireball.I[0xA9 ^ 0xAE]);
        this.yTile = nbtTagCompound.getShort(EntityFireball.I[0x3C ^ 0x34]);
        this.zTile = nbtTagCompound.getShort(EntityFireball.I[0x62 ^ 0x6B]);
        if (nbtTagCompound.hasKey(EntityFireball.I[0x27 ^ 0x2D], 0x6 ^ 0xE)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString(EntityFireball.I[0x45 ^ 0x4E]));
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte(EntityFireball.I[0x76 ^ 0x7A]) & 251 + 157 - 260 + 107);
        }
        int inGround;
        if (nbtTagCompound.getByte(EntityFireball.I[0x84 ^ 0x89]) == " ".length()) {
            inGround = " ".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            inGround = "".length();
        }
        this.inGround = (inGround != 0);
        if (nbtTagCompound.hasKey(EntityFireball.I[0xA5 ^ 0xAB], 0xAB ^ 0xA2)) {
            final NBTTagList tagList = nbtTagCompound.getTagList(EntityFireball.I[0x41 ^ 0x4E], 0xB ^ 0xD);
            this.motionX = tagList.getDoubleAt("".length());
            this.motionY = tagList.getDoubleAt(" ".length());
            this.motionZ = tagList.getDoubleAt("  ".length());
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            this.setDead();
        }
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    @Override
    public float getCollisionBorderSize() {
        return 1.0f;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(n2)) {
            n2 = 4.0;
        }
        final double n3 = n2 * 64.0;
        if (n < n3 * n3) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityFireball(final World world, final EntityLivingBase shootingEntity, double n, double n2, double n3) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.shootingEntity = shootingEntity;
        this.setSize(1.0f, 1.0f);
        this.setLocationAndAngles(shootingEntity.posX, shootingEntity.posY, shootingEntity.posZ, shootingEntity.rotationYaw, shootingEntity.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        n += this.rand.nextGaussian() * 0.4;
        n2 += this.rand.nextGaussian() * 0.4;
        n3 += this.rand.nextGaussian() * 0.4;
        final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
        this.accelerationX = n / n4 * 0.1;
        this.accelerationY = n2 / n4 * 0.1;
        this.accelerationZ = n3 / n4 * 0.1;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return " ".length() != 0;
    }
    
    static {
        I();
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    protected float getMotionFactor() {
        return 0.95f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        this.setBeenAttacked();
        if (damageSource.getEntity() != null) {
            final Vec3 lookVec = damageSource.getEntity().getLookVec();
            if (lookVec != null) {
                this.motionX = lookVec.xCoord;
                this.motionY = lookVec.yCoord;
                this.motionZ = lookVec.zCoord;
                this.accelerationX = this.motionX * 0.1;
                this.accelerationY = this.motionY * 0.1;
                this.accelerationZ = this.motionZ * 0.1;
            }
            if (damageSource.getEntity() instanceof EntityLivingBase) {
                this.shootingEntity = (EntityLivingBase)damageSource.getEntity();
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityFireball(final World world) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.setSize(1.0f, 1.0f);
    }
}
