package net.minecraft.entity.projectile;

import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;

public abstract class EntityThrowable extends Entity implements IProjectile
{
    private int zTile;
    private EntityLivingBase thrower;
    private String throwerName;
    private Block inTile;
    private int yTile;
    private int ticksInGround;
    protected boolean inGround;
    public int throwableShake;
    private int ticksInAir;
    private static final String[] I;
    private int xTile;
    
    public EntityThrowable(final World world, final EntityLivingBase thrower) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.thrower = thrower;
        this.setSize(0.25f, 0.25f);
        this.setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        final float n = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.getInaccuracy()) / 180.0f * 3.1415927f) * n;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.getVelocity(), 1.0f);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort(EntityThrowable.I["".length()], (short)this.xTile);
        nbtTagCompound.setShort(EntityThrowable.I[" ".length()], (short)this.yTile);
        nbtTagCompound.setShort(EntityThrowable.I["  ".length()], (short)this.zTile);
        final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(this.inTile);
        final String s = EntityThrowable.I["   ".length()];
        String string;
        if (resourceLocation == null) {
            string = EntityThrowable.I[0xB7 ^ 0xB3];
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            string = resourceLocation.toString();
        }
        nbtTagCompound.setString(s, string);
        nbtTagCompound.setByte(EntityThrowable.I[0x20 ^ 0x25], (byte)this.throwableShake);
        final String s2 = EntityThrowable.I[0x2E ^ 0x28];
        int n;
        if (this.inGround) {
            n = " ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        nbtTagCompound.setByte(s2, (byte)n);
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getName();
        }
        final String s3 = EntityThrowable.I[0x1F ^ 0x18];
        String throwerName;
        if (this.throwerName == null) {
            throwerName = EntityThrowable.I[0x97 ^ 0x9F];
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            throwerName = this.throwerName;
        }
        nbtTagCompound.setString(s3, throwerName);
    }
    
    static {
        I();
    }
    
    protected abstract void onImpact(final MovingObjectPosition p0);
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort(EntityThrowable.I[0x49 ^ 0x40]);
        this.yTile = nbtTagCompound.getShort(EntityThrowable.I[0x67 ^ 0x6D]);
        this.zTile = nbtTagCompound.getShort(EntityThrowable.I[0x66 ^ 0x6D]);
        if (nbtTagCompound.hasKey(EntityThrowable.I[0xA6 ^ 0xAA], 0x48 ^ 0x40)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString(EntityThrowable.I[0x57 ^ 0x5A]));
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte(EntityThrowable.I[0xC9 ^ 0xC7]) & 164 + 200 - 310 + 201);
        }
        this.throwableShake = (nbtTagCompound.getByte(EntityThrowable.I[0x80 ^ 0x8F]) & 219 + 14 - 84 + 106);
        int inGround;
        if (nbtTagCompound.getByte(EntityThrowable.I[0xD0 ^ 0xC0]) == " ".length()) {
            inGround = " ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            inGround = "".length();
        }
        this.inGround = (inGround != 0);
        this.thrower = null;
        this.throwerName = nbtTagCompound.getString(EntityThrowable.I[0xD5 ^ 0xC4]);
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
        this.thrower = this.getThrower();
    }
    
    @Override
    public void setVelocity(final double motionX, final double motionY, final double motionZ) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            final float n = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(MathHelper.func_181159_b(motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
    }
    
    public EntityThrowable(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.ticksInGround = "".length();
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
    }
    
    @Override
    protected void entityInit() {
    }
    
    public EntityThrowable(final World world) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.setSize(0.25f, 0.25f);
    }
    
    private static void I() {
        (I = new String[0x28 ^ 0x3A])["".length()] = I("\u0019\u0019\u0002\u0006\u000b", "aMkjn");
        EntityThrowable.I[" ".length()] = I("\b\u0002#.!", "qVJBD");
        EntityThrowable.I["  ".length()] = I("0\u001d>( ", "JIWDE");
        EntityThrowable.I["   ".length()] = I("\u001d)\u0016\u0006\"\u0011", "tGBoN");
        EntityThrowable.I[0x89 ^ 0x8D] = I("", "YPeea");
        EntityThrowable.I[0x52 ^ 0x57] = I("\u0015\u0006\u00071+", "fnfZN");
        EntityThrowable.I[0xBC ^ 0xBA] = I("\u001e\u0005\u001d3<\u0002\u0005>", "wkZAS");
        EntityThrowable.I[0x1B ^ 0x1C] = I("\u0018%$+\u000393'+", "wRJNq");
        EntityThrowable.I[0xAA ^ 0xA2] = I("", "qAMUO");
        EntityThrowable.I[0x63 ^ 0x6A] = I(";\u0007\b\u000b(", "CSagM");
        EntityThrowable.I[0x2C ^ 0x26] = I("+\u001d\u0013.\u0015", "RIzBp");
        EntityThrowable.I[0x97 ^ 0x9C] = I(".\u0015\u001b 7", "TArLR");
        EntityThrowable.I[0x70 ^ 0x7C] = I("3;&\u0019\u000e?", "ZUrpb");
        EntityThrowable.I[0x68 ^ 0x65] = I("+\u0018 '\u0007'", "BvtNk");
        EntityThrowable.I[0x6D ^ 0x63] = I("\u001f\r6\b8\u0013", "vcbaT");
        EntityThrowable.I[0xB6 ^ 0xB9] = I("2')>0", "AOHUU");
        EntityThrowable.I[0x89 ^ 0x99] = I("\u001b=!?-\u0007=\u0002", "rSfMB");
        EntityThrowable.I[0x1E ^ 0xF] = I("\u001a\u001543\u0010;\u000373", "ubZVb");
    }
    
    protected float getInaccuracy() {
        return 0.0f;
    }
    
    @Override
    public void setThrowableHeading(double motionX, double motionY, double motionZ, final float n, final float n2) {
        final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= sqrt_double;
        motionY /= sqrt_double;
        motionZ /= sqrt_double;
        motionX += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionY += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionZ += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionX *= n;
        motionY *= n;
        motionZ *= n;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        final float sqrt_double2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        final float n3 = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
        this.rotationYaw = n3;
        this.prevRotationYaw = n3;
        final float n4 = (float)(MathHelper.func_181159_b(motionY, sqrt_double2) * 180.0 / 3.141592653589793);
        this.rotationPitch = n4;
        this.prevRotationPitch = n4;
        this.ticksInGround = "".length();
    }
    
    @Override
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if (this.throwableShake > 0) {
            this.throwableShake -= " ".length();
        }
        if (this.inGround) {
            if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
                this.ticksInGround += " ".length();
                if (this.ticksInGround == 789 + 301 - 233 + 343) {
                    this.setDead();
                }
                return;
            }
            this.inGround = ("".length() != 0);
            this.motionX *= this.rand.nextFloat() * 0.2f;
            this.motionY *= this.rand.nextFloat() * 0.2f;
            this.motionZ *= this.rand.nextFloat() * 0.2f;
            this.ticksInGround = "".length();
            this.ticksInAir = "".length();
            "".length();
            if (2 != 2) {
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
        if (!this.worldObj.isRemote) {
            Entity entity = null;
            final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double n = 0.0;
            final EntityLivingBase thrower = this.getThrower();
            int i = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (i < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity2 = entitiesWithinAABBExcludingEntity.get(i);
                if (entity2.canBeCollidedWith() && (entity2 != thrower || this.ticksInAir >= (0xAB ^ 0xAE))) {
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
        }
        if (rayTraceBlocks != null) {
            if (rayTraceBlocks.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlockState(rayTraceBlocks.getBlockPos()).getBlock() == Blocks.portal) {
                this.func_181015_d(rayTraceBlocks.getBlockPos());
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                this.onImpact(rayTraceBlocks);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        float n3 = 0.99f;
        final float gravityVelocity = this.getGravityVelocity();
        if (this.isInWater()) {
            int j = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (j < (0xA5 ^ 0xA1)) {
                final float n4 = 0.25f;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * n4, this.posY - this.motionY * n4, this.posZ - this.motionZ * n4, this.motionX, this.motionY, this.motionZ, new int["".length()]);
                ++j;
            }
            n3 = 0.8f;
        }
        this.motionX *= n3;
        this.motionY *= n3;
        this.motionZ *= n3;
        this.motionY -= gravityVelocity;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    protected float getGravityVelocity() {
        return 0.03f;
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected float getVelocity() {
        return 1.5f;
    }
    
    public EntityLivingBase getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
            if (this.thrower == null && this.worldObj instanceof WorldServer) {
                try {
                    final Entity entityFromUuid = ((WorldServer)this.worldObj).getEntityFromUuid(UUID.fromString(this.throwerName));
                    if (entityFromUuid instanceof EntityLivingBase) {
                        this.thrower = (EntityLivingBase)entityFromUuid;
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                }
                catch (Throwable t) {
                    this.thrower = null;
                }
            }
        }
        return this.thrower;
    }
}
