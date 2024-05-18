package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class EntityArrow extends Entity implements IProjectile
{
    private int ticksInAir;
    public int canBePickedUp;
    private static final String[] I;
    public Entity shootingEntity;
    public int arrowShake;
    private int inData;
    private Block inTile;
    private int knockbackStrength;
    private int ticksInGround;
    private int yTile;
    private boolean inGround;
    private int xTile;
    private int zTile;
    private double damage;
    
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
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = "".length();
        }
    }
    
    @Override
    public boolean canAttackWithItem() {
        return "".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            final float n = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float)(MathHelper.func_181159_b(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
        final BlockPos blockPos = new BlockPos(this.xTile, this.yTile, this.zTile);
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, blockPos);
            final AxisAlignedBB collisionBoundingBox = block.getCollisionBoundingBox(this.worldObj, blockPos, blockState);
            if (collisionBoundingBox != null && collisionBoundingBox.isVecInside(new Vec3(this.posX, this.posY, this.posZ))) {
                this.inGround = (" ".length() != 0);
            }
        }
        if (this.arrowShake > 0) {
            this.arrowShake -= " ".length();
        }
        if (this.inGround) {
            final int metaFromState = block.getMetaFromState(blockState);
            if (block == this.inTile && metaFromState == this.inData) {
                this.ticksInGround += " ".length();
                if (this.ticksInGround >= 193 + 1020 - 103 + 90) {
                    this.setDead();
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
            }
            else {
                this.inGround = ("".length() != 0);
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.ticksInGround = "".length();
                this.ticksInAir = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
        }
        else {
            this.ticksInAir += " ".length();
            MovingObjectPosition rayTraceBlocks = this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY, this.posZ), new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ), "".length() != 0, " ".length() != 0, "".length() != 0);
            final Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
            Vec3 vec4 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            if (rayTraceBlocks != null) {
                vec4 = new Vec3(rayTraceBlocks.hitVec.xCoord, rayTraceBlocks.hitVec.yCoord, rayTraceBlocks.hitVec.zCoord);
            }
            Entity entity = null;
            final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double n3 = 0.0;
            int i = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (i < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity2 = entitiesWithinAABBExcludingEntity.get(i);
                if (entity2.canBeCollidedWith() && (entity2 != this.shootingEntity || this.ticksInAir >= (0x87 ^ 0x82))) {
                    final float n4 = 0.3f;
                    final MovingObjectPosition calculateIntercept = entity2.getEntityBoundingBox().expand(n4, n4, n4).calculateIntercept(vec3, vec4);
                    if (calculateIntercept != null) {
                        final double squareDistanceTo = vec3.squareDistanceTo(calculateIntercept.hitVec);
                        if (squareDistanceTo < n3 || n3 == 0.0) {
                            entity = entity2;
                            n3 = squareDistanceTo;
                        }
                    }
                }
                ++i;
            }
            if (entity != null) {
                rayTraceBlocks = new MovingObjectPosition(entity);
            }
            if (rayTraceBlocks != null && rayTraceBlocks.entityHit != null && rayTraceBlocks.entityHit instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)rayTraceBlocks.entityHit;
                if (entityPlayer.capabilities.disableDamage || (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityPlayer))) {
                    rayTraceBlocks = null;
                }
            }
            if (rayTraceBlocks != null) {
                if (rayTraceBlocks.entityHit != null) {
                    int ceiling_double_int = MathHelper.ceiling_double_int(MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ) * this.damage);
                    if (this.getIsCritical()) {
                        ceiling_double_int += this.rand.nextInt(ceiling_double_int / "  ".length() + "  ".length());
                    }
                    DamageSource damageSource;
                    if (this.shootingEntity == null) {
                        damageSource = DamageSource.causeArrowDamage(this, this);
                        "".length();
                        if (4 < 3) {
                            throw null;
                        }
                    }
                    else {
                        damageSource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }
                    if (this.isBurning() && !(rayTraceBlocks.entityHit instanceof EntityEnderman)) {
                        rayTraceBlocks.entityHit.setFire(0x6D ^ 0x68);
                    }
                    if (rayTraceBlocks.entityHit.attackEntityFrom(damageSource, ceiling_double_int)) {
                        if (rayTraceBlocks.entityHit instanceof EntityLivingBase) {
                            final EntityLivingBase entityLivingBase = (EntityLivingBase)rayTraceBlocks.entityHit;
                            if (!this.worldObj.isRemote) {
                                entityLivingBase.setArrowCountInEntity(entityLivingBase.getArrowCountInEntity() + " ".length());
                            }
                            if (this.knockbackStrength > 0) {
                                final float sqrt_double2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                                if (sqrt_double2 > 0.0f) {
                                    rayTraceBlocks.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579 / sqrt_double2, 0.1, this.motionZ * this.knockbackStrength * 0.6000000238418579 / sqrt_double2);
                                }
                            }
                            if (this.shootingEntity instanceof EntityLivingBase) {
                                EnchantmentHelper.applyThornEnchantments(entityLivingBase, this.shootingEntity);
                                EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entityLivingBase);
                            }
                            if (this.shootingEntity != null && rayTraceBlocks.entityHit != this.shootingEntity && rayTraceBlocks.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x1 ^ 0x7, 0.0f));
                            }
                        }
                        this.playSound(EntityArrow.I["".length()], 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                        if (!(rayTraceBlocks.entityHit instanceof EntityEnderman)) {
                            this.setDead();
                            "".length();
                            if (1 == 3) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.motionX *= -0.10000000149011612;
                        this.motionY *= -0.10000000149011612;
                        this.motionZ *= -0.10000000149011612;
                        this.rotationYaw += 180.0f;
                        this.prevRotationYaw += 180.0f;
                        this.ticksInAir = "".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                }
                else {
                    final BlockPos blockPos2 = rayTraceBlocks.getBlockPos();
                    this.xTile = blockPos2.getX();
                    this.yTile = blockPos2.getY();
                    this.zTile = blockPos2.getZ();
                    final IBlockState blockState2 = this.worldObj.getBlockState(blockPos2);
                    this.inTile = blockState2.getBlock();
                    this.inData = this.inTile.getMetaFromState(blockState2);
                    this.motionX = (float)(rayTraceBlocks.hitVec.xCoord - this.posX);
                    this.motionY = (float)(rayTraceBlocks.hitVec.yCoord - this.posY);
                    this.motionZ = (float)(rayTraceBlocks.hitVec.zCoord - this.posZ);
                    final float sqrt_double3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / sqrt_double3 * 0.05000000074505806;
                    this.posY -= this.motionY / sqrt_double3 * 0.05000000074505806;
                    this.posZ -= this.motionZ / sqrt_double3 * 0.05000000074505806;
                    this.playSound(EntityArrow.I[" ".length()], 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
                    this.inGround = (" ".length() != 0);
                    this.arrowShake = (0x3A ^ 0x3D);
                    this.setIsCritical("".length() != 0);
                    if (this.inTile.getMaterial() != Material.air) {
                        this.inTile.onEntityCollidedWithBlock(this.worldObj, blockPos2, blockState2, this);
                    }
                }
            }
            if (this.getIsCritical()) {
                int j = "".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                while (j < (0x40 ^ 0x44)) {
                    this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * j / 4.0, this.posY + this.motionY * j / 4.0, this.posZ + this.motionZ * j / 4.0, -this.motionX, -this.motionY + 0.2, -this.motionZ, new int["".length()]);
                    ++j;
                }
            }
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            final float sqrt_double4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, sqrt_double4) * 180.0 / 3.141592653589793);
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            "".length();
            if (2 == 4) {
                throw null;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                this.prevRotationYaw -= 360.0f;
            }
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                this.prevRotationYaw += 360.0f;
            }
            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
            float n5 = 0.99f;
            final float n6 = 0.05f;
            if (this.isInWater()) {
                int k = "".length();
                "".length();
                if (false == true) {
                    throw null;
                }
                while (k < (0x90 ^ 0x94)) {
                    final float n7 = 0.25f;
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * n7, this.posY - this.motionY * n7, this.posZ - this.motionZ * n7, this.motionX, this.motionY, this.motionZ, new int["".length()]);
                    ++k;
                }
                n5 = 0.6f;
            }
            if (this.isWet()) {
                this.extinguish();
            }
            this.motionX *= n5;
            this.motionY *= n5;
            this.motionZ *= n5;
            this.motionY -= n6;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }
    
    public boolean getIsCritical() {
        if ((this.dataWatcher.getWatchableObjectByte(0xD6 ^ 0xC6) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityArrow(final World world) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
    }
    
    public EntityArrow(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.setPosition(n, n2, n3);
    }
    
    public EntityArrow(final World world, final EntityLivingBase shootingEntity, final EntityLivingBase entityLivingBase, final float n, final float n2) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = shootingEntity;
        if (shootingEntity instanceof EntityPlayer) {
            this.canBePickedUp = " ".length();
        }
        this.posY = shootingEntity.posY + shootingEntity.getEyeHeight() - 0.10000000149011612;
        final double n3 = entityLivingBase.posX - shootingEntity.posX;
        final double n4 = entityLivingBase.getEntityBoundingBox().minY + entityLivingBase.height / 3.0f - this.posY;
        final double n5 = entityLivingBase.posZ - shootingEntity.posZ;
        final double n6 = MathHelper.sqrt_double(n3 * n3 + n5 * n5);
        if (n6 >= 1.0E-7) {
            this.setLocationAndAngles(shootingEntity.posX + n3 / n6, this.posY, shootingEntity.posZ + n5 / n6, (float)(MathHelper.func_181159_b(n5, n3) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(MathHelper.func_181159_b(n4, n6) * 180.0 / 3.141592653589793)));
            this.setThrowableHeading(n3, n4 + (float)(n6 * 0.20000000298023224), n5, n, n2);
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort(EntityArrow.I["  ".length()], (short)this.xTile);
        nbtTagCompound.setShort(EntityArrow.I["   ".length()], (short)this.yTile);
        nbtTagCompound.setShort(EntityArrow.I[0x8A ^ 0x8E], (short)this.zTile);
        nbtTagCompound.setShort(EntityArrow.I[0x18 ^ 0x1D], (short)this.ticksInGround);
        final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(this.inTile);
        final String s = EntityArrow.I[0x8E ^ 0x88];
        String string;
        if (resourceLocation == null) {
            string = EntityArrow.I[0x95 ^ 0x92];
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            string = resourceLocation.toString();
        }
        nbtTagCompound.setString(s, string);
        nbtTagCompound.setByte(EntityArrow.I[0xAE ^ 0xA6], (byte)this.inData);
        nbtTagCompound.setByte(EntityArrow.I[0x89 ^ 0x80], (byte)this.arrowShake);
        final String s2 = EntityArrow.I[0x4E ^ 0x44];
        int n;
        if (this.inGround) {
            n = " ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        nbtTagCompound.setByte(s2, (byte)n);
        nbtTagCompound.setByte(EntityArrow.I[0x78 ^ 0x73], (byte)this.canBePickedUp);
        nbtTagCompound.setDouble(EntityArrow.I[0x5C ^ 0x50], this.damage);
    }
    
    static {
        I();
    }
    
    public void setDamage(final double damage) {
        this.damage = damage;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.0f;
    }
    
    @Override
    public void setPositionAndRotation2(final double n, final double n2, final double n3, final float n4, final float n5, final int n6, final boolean b) {
        this.setPosition(n, n2, n3);
        this.setRotation(n4, n5);
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0) {
            int n;
            if (this.canBePickedUp != " ".length() && (this.canBePickedUp != "  ".length() || !entityPlayer.capabilities.isCreativeMode)) {
                n = "".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            int length = n;
            if (this.canBePickedUp == " ".length() && !entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.arrow, " ".length()))) {
                length = "".length();
            }
            if (length != 0) {
                this.playSound(EntityArrow.I[0xDD ^ 0xC0], 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                entityPlayer.onItemPickup(this, " ".length());
                this.setDead();
            }
        }
    }
    
    public EntityArrow(final World world, final EntityLivingBase shootingEntity, final float n) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = shootingEntity;
        if (shootingEntity instanceof EntityPlayer) {
            this.canBePickedUp = " ".length();
        }
        this.setSize(0.5f, 0.5f);
        this.setLocationAndAngles(shootingEntity.posX, shootingEntity.posY + shootingEntity.getEyeHeight(), shootingEntity.posZ, shootingEntity.rotationYaw, shootingEntity.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, n * 1.5f, 1.0f);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort(EntityArrow.I[0x78 ^ 0x75]);
        this.yTile = nbtTagCompound.getShort(EntityArrow.I[0xCE ^ 0xC0]);
        this.zTile = nbtTagCompound.getShort(EntityArrow.I[0xA ^ 0x5]);
        this.ticksInGround = nbtTagCompound.getShort(EntityArrow.I[0x27 ^ 0x37]);
        if (nbtTagCompound.hasKey(EntityArrow.I[0x28 ^ 0x39], 0x54 ^ 0x5C)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString(EntityArrow.I[0xBB ^ 0xA9]));
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte(EntityArrow.I[0xAB ^ 0xB8]) & 208 + 11 - 113 + 149);
        }
        this.inData = (nbtTagCompound.getByte(EntityArrow.I[0xA2 ^ 0xB6]) & 206 + 246 - 449 + 252);
        this.arrowShake = (nbtTagCompound.getByte(EntityArrow.I[0x7 ^ 0x12]) & 115 + 104 - 189 + 225);
        int inGround;
        if (nbtTagCompound.getByte(EntityArrow.I[0xB2 ^ 0xA4]) == " ".length()) {
            inGround = " ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            inGround = "".length();
        }
        this.inGround = (inGround != 0);
        if (nbtTagCompound.hasKey(EntityArrow.I[0x43 ^ 0x54], 0x7D ^ 0x1E)) {
            this.damage = nbtTagCompound.getDouble(EntityArrow.I[0x95 ^ 0x8D]);
        }
        if (nbtTagCompound.hasKey(EntityArrow.I[0x49 ^ 0x50], 0x51 ^ 0x32)) {
            this.canBePickedUp = nbtTagCompound.getByte(EntityArrow.I[0x75 ^ 0x6F]);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (nbtTagCompound.hasKey(EntityArrow.I[0x4D ^ 0x56], 0xFC ^ 0x9F)) {
            int canBePickedUp;
            if (nbtTagCompound.getBoolean(EntityArrow.I[0x91 ^ 0x8D])) {
                canBePickedUp = " ".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                canBePickedUp = "".length();
            }
            this.canBePickedUp = canBePickedUp;
        }
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(0xA6 ^ 0xB6, (byte)"".length());
    }
    
    @Override
    public void setThrowableHeading(double motionX, double motionY, double motionZ, final float n, final float n2) {
        final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= sqrt_double;
        motionY /= sqrt_double;
        motionZ /= sqrt_double;
        final double n3 = motionX;
        final double nextGaussian = this.rand.nextGaussian();
        int length;
        if (this.rand.nextBoolean()) {
            length = -" ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            length = " ".length();
        }
        motionX = n3 + nextGaussian * length * 0.007499999832361937 * n2;
        final double n4 = motionY;
        final double nextGaussian2 = this.rand.nextGaussian();
        int length2;
        if (this.rand.nextBoolean()) {
            length2 = -" ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            length2 = " ".length();
        }
        motionY = n4 + nextGaussian2 * length2 * 0.007499999832361937 * n2;
        final double n5 = motionZ;
        final double nextGaussian3 = this.rand.nextGaussian();
        int length3;
        if (this.rand.nextBoolean()) {
            length3 = -" ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            length3 = " ".length();
        }
        motionZ = n5 + nextGaussian3 * length3 * 0.007499999832361937 * n2;
        motionX *= n;
        motionY *= n;
        motionZ *= n;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        final float sqrt_double2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        final float n6 = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
        this.rotationYaw = n6;
        this.prevRotationYaw = n6;
        final float n7 = (float)(MathHelper.func_181159_b(motionY, sqrt_double2) * 180.0 / 3.141592653589793);
        this.rotationPitch = n7;
        this.prevRotationPitch = n7;
        this.ticksInGround = "".length();
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x58 ^ 0x46])["".length()] = I("8\b%\u001c\u001e'G)\u0017\u0006\"\u0000?", "JiKxq");
        EntityArrow.I[" ".length()] = I(">*6\u0012\u001f!e:\u0019\u0007$\",", "LKXvp");
        EntityArrow.I["  ".length()] = I("\u00190\u0013\u001c+", "adzpN");
        EntityArrow.I["   ".length()] = I("5'#9\t", "LsJUl");
        EntityArrow.I[0x58 ^ 0x5C] = I("\u000b\u001e0'\u0011", "qJYKt");
        EntityArrow.I[0x36 ^ 0x33] = I("%\u0010\u0015\f", "Iysil");
        EntityArrow.I[0xBB ^ 0xBD] = I("\u001b\t60\u000b\u0017", "rgbYg");
        EntityArrow.I[0xA7 ^ 0xA0] = I("", "LEMQz");
        EntityArrow.I[0xBC ^ 0xB4] = I("1\u0007(\u0017\"9", "XilvV");
        EntityArrow.I[0x14 ^ 0x1D] = I(">\u0018-('", "MpLCB");
        EntityArrow.I[0x25 ^ 0x2F] = I("\u0011;2:\u0017\r;\u0011", "xUuHx");
        EntityArrow.I[0xCB ^ 0xC0] = I("(&'\u0019\f(", "XODry");
        EntityArrow.I[0x6B ^ 0x67] = I("\u001e\u000f\u0003\u0010\u0001\u001f", "znnqf");
        EntityArrow.I[0xAF ^ 0xA2] = I("\u0010'.\u0003\u0017", "hsGor");
        EntityArrow.I[0xAA ^ 0xA4] = I("\u00018\f\u0003&", "xleoC");
        EntityArrow.I[0xAE ^ 0xA1] = I("\u0017\u0016!\r#", "mBHaF");
        EntityArrow.I[0x93 ^ 0x83] = I("\u0007<\u001f\u0014", "kUyqT");
        EntityArrow.I[0x4 ^ 0x15] = I("\u000e\u0018.\u0003\b\u0002", "gvzjd");
        EntityArrow.I[0x41 ^ 0x53] = I("\u0004*?96\b", "mDkPZ");
        EntityArrow.I[0x97 ^ 0x84] = I("\u0018#,=-\u0014", "qMxTA");
        EntityArrow.I[0x43 ^ 0x57] = I("\f,\u001637\u0004", "eBRRC");
        EntityArrow.I[0x14 ^ 0x1] = I("+\u001b.:\r", "XsOQh");
        EntityArrow.I[0x5 ^ 0x13] = I("\u001c'+7>\u0000'\b", "uIlEQ");
        EntityArrow.I[0xAC ^ 0xBB] = I("\u0000%>*\u0014\u0001", "dDSKs");
        EntityArrow.I[0x63 ^ 0x7B] = I("\u000b7\u0007\u0013\u0016\n", "oVjrq");
        EntityArrow.I[0x94 ^ 0x8D] = I("5/\b\u001a\u00115", "EFkqd");
        EntityArrow.I[0xAA ^ 0xB0] = I("\u0014#$\u001c0\u0014", "dJGwE");
        EntityArrow.I[0x0 ^ 0x1B] = I("\u001f\u001d\u00143\u0007\u001d", "oquJb");
        EntityArrow.I[0x98 ^ 0x84] = I("\u0014\u0001.?.\u0016", "dmOFK");
        EntityArrow.I[0xDC ^ 0xC1] = I("89\u001c >'v\u0002+!", "JXrDQ");
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    public void setIsCritical(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x8E ^ 0x9E);
        if (b) {
            this.dataWatcher.updateObject(0x99 ^ 0x89, (byte)(watchableObjectByte | " ".length()));
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x76 ^ 0x66, (byte)(watchableObjectByte & -"  ".length()));
        }
    }
    
    public void setKnockbackStrength(final int knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
    }
    
    public double getDamage() {
        return this.damage;
    }
}
