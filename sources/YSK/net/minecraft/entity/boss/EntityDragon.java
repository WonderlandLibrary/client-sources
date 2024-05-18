package net.minecraft.entity.boss;

import net.minecraft.entity.monster.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;

public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob
{
    public EntityDragonPart dragonPartBody;
    public EntityDragonPart[] dragonPartArray;
    public int ringBufferIndex;
    public boolean forceNewTarget;
    public double targetY;
    public EntityDragonPart dragonPartWing2;
    private Entity target;
    public float animTime;
    public EntityDragonPart dragonPartHead;
    public double targetZ;
    public EntityDragonPart dragonPartTail3;
    public float prevAnimTime;
    public EntityDragonPart dragonPartTail1;
    public EntityDragonPart dragonPartWing1;
    public EntityDragonPart dragonPartTail2;
    public int deathTicks;
    public EntityEnderCrystal healingEnderCrystal;
    public boolean slowed;
    public double targetX;
    private static final String[] I;
    public double[][] ringBuffer;
    
    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }
    
    private boolean destroyBlocksInAABB(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        int n = "".length();
        int length = "".length();
        int i = floor_double;
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (i <= floor_double4) {
            int j = floor_double2;
            "".length();
            if (2 < -1) {
                throw null;
            }
            while (j <= floor_double5) {
                int k = floor_double3;
                "".length();
                if (3 == 4) {
                    throw null;
                }
                while (k <= floor_double6) {
                    final BlockPos blockToAir = new BlockPos(i, j, k);
                    final Block block = this.worldObj.getBlockState(blockToAir).getBlock();
                    if (block.getMaterial() != Material.air) {
                        if (block != Blocks.barrier && block != Blocks.obsidian && block != Blocks.end_stone && block != Blocks.bedrock && block != Blocks.command_block && this.worldObj.getGameRules().getBoolean(EntityDragon.I[0x17 ^ 0x1F])) {
                            int n2;
                            if (!this.worldObj.setBlockToAir(blockToAir) && length == 0) {
                                n2 = "".length();
                                "".length();
                                if (4 < 3) {
                                    throw null;
                                }
                            }
                            else {
                                n2 = " ".length();
                            }
                            length = n2;
                            "".length();
                            if (4 == 0) {
                                throw null;
                            }
                        }
                        else {
                            n = " ".length();
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        if (length != 0) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * this.rand.nextFloat(), axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * this.rand.nextFloat(), axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * this.rand.nextFloat(), 0.0, 0.0, 0.0, new int["".length()]);
        }
        return n != 0;
    }
    
    @Override
    public void onKillCommand() {
        this.setDead();
    }
    
    @Override
    public Entity[] getParts() {
        return this.dragonPartArray;
    }
    
    static {
        I();
    }
    
    protected boolean attackDragonFrom(final DamageSource damageSource, final float n) {
        return super.attackEntityFrom(damageSource, n);
    }
    
    private void setNewTarget() {
        this.forceNewTarget = ("".length() != 0);
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.worldObj.playerEntities);
        final Iterator<EntityPlayer> iterator = (Iterator<EntityPlayer>)arrayList.iterator();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().isSpectator()) {
                iterator.remove();
            }
        }
        if (this.rand.nextInt("  ".length()) == 0 && !arrayList.isEmpty()) {
            this.target = (EntityPlayer)arrayList.get(this.rand.nextInt(arrayList.size()));
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            int i;
            do {
                this.targetX = 0.0;
                this.targetY = 70.0f + this.rand.nextFloat() * 50.0f;
                this.targetZ = 0.0;
                this.targetX += this.rand.nextFloat() * 120.0f - 60.0f;
                this.targetZ += this.rand.nextFloat() * 120.0f - 60.0f;
                final double n = this.posX - this.targetX;
                final double n2 = this.posY - this.targetY;
                final double n3 = this.posZ - this.targetZ;
                if (n * n + n2 * n2 + n3 * n3 > 100.0) {
                    i = " ".length();
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    continue;
                }
                else {
                    i = "".length();
                }
            } while (i == 0);
            this.target = null;
        }
    }
    
    @Override
    protected void despawnEntity() {
    }
    
    private void attackEntitiesInList(final List<Entity> list) {
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < list.size()) {
            final Entity entity = list.get(i);
            if (entity instanceof EntityLivingBase) {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0f);
                this.applyEnchantments(this, entity);
            }
            ++i;
        }
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    public EntityDragon(final World world) {
        super(world);
        this.ringBuffer = new double[0x71 ^ 0x31]["   ".length()];
        this.ringBufferIndex = -" ".length();
        final EntityDragonPart[] dragonPartArray = new EntityDragonPart[0x26 ^ 0x21];
        dragonPartArray["".length()] = (this.dragonPartHead = new EntityDragonPart(this, EntityDragon.I["".length()], 6.0f, 6.0f));
        dragonPartArray[" ".length()] = (this.dragonPartBody = new EntityDragonPart(this, EntityDragon.I[" ".length()], 8.0f, 8.0f));
        dragonPartArray["  ".length()] = (this.dragonPartTail1 = new EntityDragonPart(this, EntityDragon.I["  ".length()], 4.0f, 4.0f));
        dragonPartArray["   ".length()] = (this.dragonPartTail2 = new EntityDragonPart(this, EntityDragon.I["   ".length()], 4.0f, 4.0f));
        dragonPartArray[0x75 ^ 0x71] = (this.dragonPartTail3 = new EntityDragonPart(this, EntityDragon.I[0x6D ^ 0x69], 4.0f, 4.0f));
        dragonPartArray[0xA1 ^ 0xA4] = (this.dragonPartWing1 = new EntityDragonPart(this, EntityDragon.I[0x82 ^ 0x87], 4.0f, 4.0f));
        dragonPartArray[0x93 ^ 0x95] = (this.dragonPartWing2 = new EntityDragonPart(this, EntityDragon.I[0xB5 ^ 0xB3], 4.0f, 4.0f));
        this.dragonPartArray = dragonPartArray;
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0f, 8.0f);
        this.noClip = (" ".length() != 0);
        this.isImmuneToFire = (" ".length() != 0);
        this.targetY = 100.0;
        this.ignoreFrustumCheck = (" ".length() != 0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return "".length() != 0;
    }
    
    @Override
    public boolean attackEntityFromPart(final EntityDragonPart entityDragonPart, final DamageSource damageSource, float n) {
        if (entityDragonPart != this.dragonPartHead) {
            n = n / 4.0f + 1.0f;
        }
        final float n2 = this.rotationYaw * 3.1415927f / 180.0f;
        final float sin = MathHelper.sin(n2);
        final float cos = MathHelper.cos(n2);
        this.targetX = this.posX + sin * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.targetY = this.posY + this.rand.nextFloat() * 3.0f + 1.0;
        this.targetZ = this.posZ - cos * 5.0f + (this.rand.nextFloat() - 0.5f) * 2.0f;
        this.target = null;
        if (damageSource.getEntity() instanceof EntityPlayer || damageSource.isExplosion()) {
            this.attackDragonFrom(damageSource, n);
        }
        return " ".length() != 0;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (damageSource instanceof EntityDamageSource && ((EntityDamageSource)damageSource).getIsThornsDamage()) {
            this.attackDragonFrom(damageSource, n);
        }
        return "".length() != 0;
    }
    
    @Override
    protected void onDeathUpdate() {
        this.deathTicks += " ".length();
        if (this.deathTicks >= 13 + 166 - 166 + 167 && this.deathTicks <= 180 + 4 - 163 + 179) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (this.rand.nextFloat() - 0.5f) * 8.0f, this.posY + 2.0 + (this.rand.nextFloat() - 0.5f) * 4.0f, this.posZ + (this.rand.nextFloat() - 0.5f) * 8.0f, 0.0, 0.0, 0.0, new int["".length()]);
        }
        final boolean boolean1 = this.worldObj.getGameRules().getBoolean(EntityDragon.I[0x78 ^ 0x71]);
        if (!this.worldObj.isRemote) {
            if (this.deathTicks > 94 + 18 - 108 + 146 && this.deathTicks % (0x4A ^ 0x4F) == 0 && boolean1) {
                int i = 131 + 295 + 427 + 147;
                "".length();
                if (1 < 1) {
                    throw null;
                }
                while (i > 0) {
                    final int xpSplit = EntityXPOrb.getXPSplit(i);
                    i -= xpSplit;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, xpSplit));
                }
            }
            if (this.deathTicks == " ".length()) {
                this.worldObj.playBroadcastSound(566 + 816 - 400 + 36, new BlockPos(this), "".length());
            }
        }
        this.moveEntity(0.0, 0.10000000149011612, 0.0);
        final float n = this.rotationYaw + 20.0f;
        this.rotationYaw = n;
        this.renderYawOffset = n;
        if (this.deathTicks == 38 + 140 - 31 + 53 && !this.worldObj.isRemote) {
            if (boolean1) {
                int j = 1854 + 246 - 1520 + 1420;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (j > 0) {
                    final int xpSplit2 = EntityXPOrb.getXPSplit(j);
                    j -= xpSplit2;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, xpSplit2));
                }
            }
            this.generatePortal(new BlockPos(this.posX, 64.0, this.posZ));
            this.setDead();
        }
    }
    
    public double[] getMovementOffsets(final int n, float n2) {
        if (this.getHealth() <= 0.0f) {
            n2 = 0.0f;
        }
        n2 = 1.0f - n2;
        final int n3 = this.ringBufferIndex - n * " ".length() & (0xA ^ 0x35);
        final int n4 = this.ringBufferIndex - n * " ".length() - " ".length() & (0x14 ^ 0x2B);
        final double[] array = new double["   ".length()];
        final double n5 = this.ringBuffer[n3]["".length()];
        array["".length()] = n5 + MathHelper.wrapAngleTo180_double(this.ringBuffer[n4]["".length()] - n5) * n2;
        final double n6 = this.ringBuffer[n3][" ".length()];
        array[" ".length()] = n6 + (this.ringBuffer[n4][" ".length()] - n6) * n2;
        array["  ".length()] = this.ringBuffer[n3]["  ".length()] + (this.ringBuffer[n4]["  ".length()] - this.ringBuffer[n3]["  ".length()]) * n2;
        return array;
    }
    
    @Override
    protected String getLivingSound() {
        return EntityDragon.I[0xAE ^ 0xA4];
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            final float cos = MathHelper.cos(this.animTime * 3.1415927f * 2.0f);
            if (MathHelper.cos(this.prevAnimTime * 3.1415927f * 2.0f) <= -0.3f && cos >= -0.3f && !this.isSilent()) {
                this.worldObj.playSound(this.posX, this.posY, this.posZ, EntityDragon.I[0x8F ^ 0x88], 5.0f, 0.8f + this.rand.nextFloat() * 0.3f, "".length() != 0);
            }
        }
        this.prevAnimTime = this.animTime;
        if (this.getHealth() <= 0.0f) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (this.rand.nextFloat() - 0.5f) * 8.0f, this.posY + 2.0 + (this.rand.nextFloat() - 0.5f) * 4.0f, this.posZ + (this.rand.nextFloat() - 0.5f) * 8.0f, 0.0, 0.0, 0.0, new int["".length()]);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            this.updateDragonEnderCrystal();
            final float n = 0.2f / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0f + 1.0f) * (float)Math.pow(2.0, this.motionY);
            if (this.slowed) {
                this.animTime += n * 0.5f;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                this.animTime += n;
            }
            this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
            if (this.isAIDisabled()) {
                this.animTime = 0.5f;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                if (this.ringBufferIndex < 0) {
                    int i = "".length();
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                    while (i < this.ringBuffer.length) {
                        this.ringBuffer[i]["".length()] = this.rotationYaw;
                        this.ringBuffer[i][" ".length()] = this.posY;
                        ++i;
                    }
                }
                if ((this.ringBufferIndex += " ".length()) == this.ringBuffer.length) {
                    this.ringBufferIndex = "".length();
                }
                this.ringBuffer[this.ringBufferIndex]["".length()] = this.rotationYaw;
                this.ringBuffer[this.ringBufferIndex][" ".length()] = this.posY;
                if (this.worldObj.isRemote) {
                    if (this.newPosRotationIncrements > 0) {
                        final double n2 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
                        final double n3 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
                        final double n4 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
                        this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw) / this.newPosRotationIncrements);
                        this.rotationPitch += (float)((this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
                        this.newPosRotationIncrements -= " ".length();
                        this.setPosition(n2, n3, n4);
                        this.setRotation(this.rotationYaw, this.rotationPitch);
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                }
                else {
                    final double n5 = this.targetX - this.posX;
                    final double n6 = this.targetY - this.posY;
                    final double n7 = this.targetZ - this.posZ;
                    final double n8 = n5 * n5 + n6 * n6 + n7 * n7;
                    if (this.target != null) {
                        this.targetX = this.target.posX;
                        this.targetZ = this.target.posZ;
                        final double n9 = this.targetX - this.posX;
                        final double n10 = this.targetZ - this.posZ;
                        double n11 = 0.4000000059604645 + Math.sqrt(n9 * n9 + n10 * n10) / 80.0 - 1.0;
                        if (n11 > 10.0) {
                            n11 = 10.0;
                        }
                        this.targetY = this.target.getEntityBoundingBox().minY + n11;
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        this.targetX += this.rand.nextGaussian() * 2.0;
                        this.targetZ += this.rand.nextGaussian() * 2.0;
                    }
                    if (this.forceNewTarget || n8 < 100.0 || n8 > 22500.0 || this.isCollidedHorizontally || this.isCollidedVertically) {
                        this.setNewTarget();
                    }
                    final double n12 = n6 / MathHelper.sqrt_double(n5 * n5 + n7 * n7);
                    final float n13 = 0.6f;
                    this.motionY += MathHelper.clamp_double(n12, -n13, n13) * 0.10000000149011612;
                    this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
                    double wrapAngleTo180_double = MathHelper.wrapAngleTo180_double(180.0 - MathHelper.func_181159_b(n5, n7) * 180.0 / 3.141592653589793 - this.rotationYaw);
                    if (wrapAngleTo180_double > 50.0) {
                        wrapAngleTo180_double = 50.0;
                    }
                    if (wrapAngleTo180_double < -50.0) {
                        wrapAngleTo180_double = -50.0;
                    }
                    final Vec3 normalize = new Vec3(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ).normalize();
                    final Vec3 normalize2 = new Vec3(MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f), this.motionY, -MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f)).normalize();
                    float n14 = ((float)normalize2.dotProduct(normalize) + 0.5f) / 1.5f;
                    if (n14 < 0.0f) {
                        n14 = 0.0f;
                    }
                    this.randomYawVelocity *= 0.8f;
                    final float n15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0f + 1.0f;
                    double n16 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0 + 1.0;
                    if (n16 > 40.0) {
                        n16 = 40.0;
                    }
                    this.randomYawVelocity += (float)(wrapAngleTo180_double * (0.699999988079071 / n16 / n15));
                    this.rotationYaw += this.randomYawVelocity * 0.1f;
                    final float n17 = (float)(2.0 / (n16 + 1.0));
                    this.moveFlying(0.0f, -1.0f, 0.06f * (n14 * n17 + (1.0f - n17)));
                    if (this.slowed) {
                        this.moveEntity(this.motionX * 0.800000011920929, this.motionY * 0.800000011920929, this.motionZ * 0.800000011920929);
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        this.moveEntity(this.motionX, this.motionY, this.motionZ);
                    }
                    final float n18 = 0.8f + 0.15f * (((float)new Vec3(this.motionX, this.motionY, this.motionZ).normalize().dotProduct(normalize2) + 1.0f) / 2.0f);
                    this.motionX *= n18;
                    this.motionZ *= n18;
                    this.motionY *= 0.9100000262260437;
                }
                this.renderYawOffset = this.rotationYaw;
                final EntityDragonPart dragonPartHead = this.dragonPartHead;
                final EntityDragonPart dragonPartHead2 = this.dragonPartHead;
                final float n19 = 3.0f;
                dragonPartHead2.height = n19;
                dragonPartHead.width = n19;
                final EntityDragonPart dragonPartTail1 = this.dragonPartTail1;
                final EntityDragonPart dragonPartTail2 = this.dragonPartTail1;
                final float n20 = 2.0f;
                dragonPartTail2.height = n20;
                dragonPartTail1.width = n20;
                final EntityDragonPart dragonPartTail3 = this.dragonPartTail2;
                final EntityDragonPart dragonPartTail4 = this.dragonPartTail2;
                final float n21 = 2.0f;
                dragonPartTail4.height = n21;
                dragonPartTail3.width = n21;
                final EntityDragonPart dragonPartTail5 = this.dragonPartTail3;
                final EntityDragonPart dragonPartTail6 = this.dragonPartTail3;
                final float n22 = 2.0f;
                dragonPartTail6.height = n22;
                dragonPartTail5.width = n22;
                this.dragonPartBody.height = 3.0f;
                this.dragonPartBody.width = 5.0f;
                this.dragonPartWing1.height = 2.0f;
                this.dragonPartWing1.width = 4.0f;
                this.dragonPartWing2.height = 3.0f;
                this.dragonPartWing2.width = 4.0f;
                final float n23 = (float)(this.getMovementOffsets(0x79 ^ 0x7C, 1.0f)[" ".length()] - this.getMovementOffsets(0x98 ^ 0x92, 1.0f)[" ".length()]) * 10.0f / 180.0f * 3.1415927f;
                final float cos2 = MathHelper.cos(n23);
                final float n24 = -MathHelper.sin(n23);
                final float n25 = this.rotationYaw * 3.1415927f / 180.0f;
                final float sin = MathHelper.sin(n25);
                final float cos3 = MathHelper.cos(n25);
                this.dragonPartBody.onUpdate();
                this.dragonPartBody.setLocationAndAngles(this.posX + sin * 0.5f, this.posY, this.posZ - cos3 * 0.5f, 0.0f, 0.0f);
                this.dragonPartWing1.onUpdate();
                this.dragonPartWing1.setLocationAndAngles(this.posX + cos3 * 4.5f, this.posY + 2.0, this.posZ + sin * 4.5f, 0.0f, 0.0f);
                this.dragonPartWing2.onUpdate();
                this.dragonPartWing2.setLocationAndAngles(this.posX - cos3 * 4.5f, this.posY + 2.0, this.posZ - sin * 4.5f, 0.0f, 0.0f);
                if (!this.worldObj.isRemote && this.hurtTime == 0) {
                    this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.getEntityBoundingBox().expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0)));
                    this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.getEntityBoundingBox().expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0)));
                    this.attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.getEntityBoundingBox().expand(1.0, 1.0, 1.0)));
                }
                final double[] movementOffsets = this.getMovementOffsets(0xC4 ^ 0xC1, 1.0f);
                final double[] movementOffsets2 = this.getMovementOffsets("".length(), 1.0f);
                final float sin2 = MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f - this.randomYawVelocity * 0.01f);
                final float cos4 = MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f - this.randomYawVelocity * 0.01f);
                this.dragonPartHead.onUpdate();
                this.dragonPartHead.setLocationAndAngles(this.posX + sin2 * 5.5f * cos2, this.posY + (movementOffsets2[" ".length()] - movementOffsets[" ".length()]) * 1.0 + n24 * 5.5f, this.posZ - cos4 * 5.5f * cos2, 0.0f, 0.0f);
                int j = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (j < "   ".length()) {
                    Entity entity = null;
                    if (j == 0) {
                        entity = this.dragonPartTail1;
                    }
                    if (j == " ".length()) {
                        entity = this.dragonPartTail2;
                    }
                    if (j == "  ".length()) {
                        entity = this.dragonPartTail3;
                    }
                    final double[] movementOffsets3 = this.getMovementOffsets((0xBB ^ 0xB7) + j * "  ".length(), 1.0f);
                    final float n26 = this.rotationYaw * 3.1415927f / 180.0f + this.simplifyAngle(movementOffsets3["".length()] - movementOffsets["".length()]) * 3.1415927f / 180.0f * 1.0f;
                    final float sin3 = MathHelper.sin(n26);
                    final float cos5 = MathHelper.cos(n26);
                    final float n27 = 1.5f;
                    final float n28 = (j + " ".length()) * 2.0f;
                    entity.onUpdate();
                    entity.setLocationAndAngles(this.posX - (sin * n27 + sin3 * n28) * cos2, this.posY + (movementOffsets3[" ".length()] - movementOffsets[" ".length()]) * 1.0 - (n28 + n27) * n24 + 1.5, this.posZ + (cos3 * n27 + cos5 * n28) * cos2, 0.0f, 0.0f);
                    ++j;
                }
                if (!this.worldObj.isRemote) {
                    this.slowed = (this.destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox()) | this.destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox()));
                }
            }
        }
    }
    
    private void collideWithEntities(final List<Entity> list) {
        final double n = (this.dragonPartBody.getEntityBoundingBox().minX + this.dragonPartBody.getEntityBoundingBox().maxX) / 2.0;
        final double n2 = (this.dragonPartBody.getEntityBoundingBox().minZ + this.dragonPartBody.getEntityBoundingBox().maxZ) / 2.0;
        final Iterator<Entity> iterator = list.iterator();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if (entity instanceof EntityLivingBase) {
                final double n3 = entity.posX - n;
                final double n4 = entity.posZ - n2;
                final double n5 = n3 * n3 + n4 * n4;
                entity.addVelocity(n3 / n5 * 4.0, 0.20000000298023224, n4 / n5 * 4.0);
            }
        }
    }
    
    private void generatePortal(final BlockPos blockPos) {
        int i = -" ".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i <= (0x1E ^ 0x3E)) {
            int j = -(0x7 ^ 0x3);
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (j <= (0x1E ^ 0x1A)) {
                int k = -(0x59 ^ 0x5D);
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (k <= (0xB4 ^ 0xB0)) {
                    final double n = j * j + k * k;
                    if (n <= 12.25) {
                        final BlockPos add = blockPos.add(j, i, k);
                        if (i < 0) {
                            if (n <= 6.25) {
                                this.worldObj.setBlockState(add, Blocks.bedrock.getDefaultState());
                                "".length();
                                if (2 <= 0) {
                                    throw null;
                                }
                            }
                        }
                        else if (i > 0) {
                            this.worldObj.setBlockState(add, Blocks.air.getDefaultState());
                            "".length();
                            if (-1 >= 0) {
                                throw null;
                            }
                        }
                        else if (n > 6.25) {
                            this.worldObj.setBlockState(add, Blocks.bedrock.getDefaultState());
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                        else {
                            this.worldObj.setBlockState(add, Blocks.end_portal.getDefaultState());
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        this.worldObj.setBlockState(blockPos, Blocks.bedrock.getDefaultState());
        this.worldObj.setBlockState(blockPos.up(), Blocks.bedrock.getDefaultState());
        final BlockPos up = blockPos.up("  ".length());
        this.worldObj.setBlockState(up, Blocks.bedrock.getDefaultState());
        this.worldObj.setBlockState(up.west(), Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.EAST));
        this.worldObj.setBlockState(up.east(), Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.WEST));
        this.worldObj.setBlockState(up.north(), Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.SOUTH));
        this.worldObj.setBlockState(up.south(), Blocks.torch.getDefaultState().withProperty((IProperty<Comparable>)BlockTorch.FACING, EnumFacing.NORTH));
        this.worldObj.setBlockState(blockPos.up("   ".length()), Blocks.bedrock.getDefaultState());
        this.worldObj.setBlockState(blockPos.up(0xAD ^ 0xA9), Blocks.dragon_egg.getDefaultState());
    }
    
    private float simplifyAngle(final double n) {
        return (float)MathHelper.wrapAngleTo180_double(n);
    }
    
    @Override
    protected String getHurtSound() {
        return EntityDragon.I[0xB7 ^ 0xBC];
    }
    
    private static void I() {
        (I = new String[0x9E ^ 0x92])["".length()] = I("*\u0010\u001b\f", "BuzhC");
        EntityDragon.I[" ".length()] = I("%8'\u0000", "GWCyO");
        EntityDragon.I["  ".length()] = I("$8\u001f'", "PYvKX");
        EntityDragon.I["   ".length()] = I("\u0017\u000f?\u000e", "cnVbh");
        EntityDragon.I[0x2 ^ 0x6] = I("=.\u0000\r", "IOiaX");
        EntityDragon.I[0x5F ^ 0x5A] = I("\u0010\u001f\u001e3", "gvpTW");
        EntityDragon.I[0x95 ^ 0x93] = I("2\b\u00054", "EakSN");
        EntityDragon.I[0x76 ^ 0x71] = I("\u001e9\u0001`#\u001d2\u0006<\"\u00017\u0004!(]!\n !\u0000", "sVcNF");
        EntityDragon.I[0xCE ^ 0xC6] = I("*73$\u0003.=7\n\u001f ", "GXQcq");
        EntityDragon.I[0x7F ^ 0x76] = I("&\u0002>.\r\u000e\u0002\u001c5", "BmsAo");
        EntityDragon.I[0x66 ^ 0x6C] = I("\u000b\u0004 _\u0006\b\u000f'\u0003\u0007\u0014\n%\u001e\rH\f0\u001e\u0014\n", "fkBqc");
        EntityDragon.I[0x38 ^ 0x33] = I("?\b$^\u001d<\u0003#\u0002\u001c \u0006!\u001f\u0016|\u000f/\u0004", "RgFpx");
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
    }
    
    private void updateDragonEnderCrystal() {
        if (this.healingEnderCrystal != null) {
            if (this.healingEnderCrystal.isDead) {
                if (!this.worldObj.isRemote) {
                    this.attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource(null), 10.0f);
                }
                this.healingEnderCrystal = null;
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else if (this.ticksExisted % (0x3B ^ 0x31) == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0f);
            }
        }
        if (this.rand.nextInt(0x71 ^ 0x7B) == 0) {
            final float n = 32.0f;
            final List<Entity> entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityEnderCrystal.class, this.getEntityBoundingBox().expand(n, n, n));
            EntityEnderCrystal healingEnderCrystal = null;
            double n2 = Double.MAX_VALUE;
            final Iterator<EntityEnderCrystal> iterator = entitiesWithinAABB.iterator();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityEnderCrystal entityEnderCrystal = iterator.next();
                final double distanceSqToEntity = entityEnderCrystal.getDistanceSqToEntity(this);
                if (distanceSqToEntity < n2) {
                    n2 = distanceSqToEntity;
                    healingEnderCrystal = entityEnderCrystal;
                }
            }
            this.healingEnderCrystal = healingEnderCrystal;
        }
    }
}
