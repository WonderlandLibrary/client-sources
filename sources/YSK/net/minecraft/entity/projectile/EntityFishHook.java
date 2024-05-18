package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;

public class EntityFishHook extends Entity
{
    private Block inTile;
    private double fishPitch;
    private double fishY;
    private double fishYaw;
    private int ticksCatchableDelay;
    private static final String[] I;
    private double clientMotionZ;
    private static final List<WeightedRandomFishable> JUNK;
    private double clientMotionY;
    private int xTile;
    private double fishZ;
    private int yTile;
    private int fishPosRotationIncrements;
    private float fishApproachAngle;
    private int ticksInGround;
    private static final List<WeightedRandomFishable> TREASURE;
    public int shake;
    private int zTile;
    public EntityPlayer angler;
    private static final List<WeightedRandomFishable> FISH;
    public Entity caughtEntity;
    private boolean inGround;
    private int ticksCaughtDelay;
    private double fishX;
    private double clientMotionX;
    private int ticksCatchable;
    private int ticksInAir;
    
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
    
    private ItemStack getFishingResult() {
        final float nextFloat = this.worldObj.rand.nextFloat();
        final int luckOfSeaModifier = EnchantmentHelper.getLuckOfSeaModifier(this.angler);
        final int lureModifier = EnchantmentHelper.getLureModifier(this.angler);
        final float n = 0.1f - luckOfSeaModifier * 0.025f - lureModifier * 0.01f;
        final float n2 = 0.05f + luckOfSeaModifier * 0.01f - lureModifier * 0.01f;
        final float clamp_float = MathHelper.clamp_float(n, 0.0f, 1.0f);
        final float clamp_float2 = MathHelper.clamp_float(n2, 0.0f, 1.0f);
        if (nextFloat < clamp_float) {
            this.angler.triggerAchievement(StatList.junkFishedStat);
            return WeightedRandom.getRandomItem(this.rand, EntityFishHook.JUNK).getItemStack(this.rand);
        }
        if (nextFloat - clamp_float < clamp_float2) {
            this.angler.triggerAchievement(StatList.treasureFishedStat);
            return WeightedRandom.getRandomItem(this.rand, EntityFishHook.TREASURE).getItemStack(this.rand);
        }
        this.angler.triggerAchievement(StatList.fishCaughtStat);
        return WeightedRandom.getRandomItem(this.rand, EntityFishHook.FISH).getItemStack(this.rand);
    }
    
    public EntityFishHook(final World world) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = (" ".length() != 0);
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public void setPositionAndRotation2(final double fishX, final double fishY, final double fishZ, final float n, final float n2, final int fishPosRotationIncrements, final boolean b) {
        this.fishX = fishX;
        this.fishY = fishY;
        this.fishZ = fishZ;
        this.fishYaw = n;
        this.fishPitch = n2;
        this.fishPosRotationIncrements = fishPosRotationIncrements;
        this.motionX = this.clientMotionX;
        this.motionY = this.clientMotionY;
        this.motionZ = this.clientMotionZ;
    }
    
    public int handleHookRetraction() {
        if (this.worldObj.isRemote) {
            return "".length();
        }
        int n = "".length();
        if (this.caughtEntity != null) {
            final double n2 = this.angler.posX - this.posX;
            final double n3 = this.angler.posY - this.posY;
            final double n4 = this.angler.posZ - this.posZ;
            final double n5 = MathHelper.sqrt_double(n2 * n2 + n3 * n3 + n4 * n4);
            final double n6 = 0.1;
            final Entity caughtEntity = this.caughtEntity;
            caughtEntity.motionX += n2 * n6;
            final Entity caughtEntity2 = this.caughtEntity;
            caughtEntity2.motionY += n3 * n6 + MathHelper.sqrt_double(n5) * 0.08;
            final Entity caughtEntity3 = this.caughtEntity;
            caughtEntity3.motionZ += n4 * n6;
            n = "   ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (this.ticksCatchable > 0) {
            final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.getFishingResult());
            final double n7 = this.angler.posX - this.posX;
            final double n8 = this.angler.posY - this.posY;
            final double n9 = this.angler.posZ - this.posZ;
            final double n10 = MathHelper.sqrt_double(n7 * n7 + n8 * n8 + n9 * n9);
            final double n11 = 0.1;
            entityItem.motionX = n7 * n11;
            entityItem.motionY = n8 * n11 + MathHelper.sqrt_double(n10) * 0.08;
            entityItem.motionZ = n9 * n11;
            this.worldObj.spawnEntityInWorld(entityItem);
            this.angler.worldObj.spawnEntityInWorld(new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5, this.angler.posZ + 0.5, this.rand.nextInt(0x69 ^ 0x6F) + " ".length()));
            n = " ".length();
        }
        if (this.inGround) {
            n = "  ".length();
        }
        this.setDead();
        this.angler.fishEntity = null;
        return n;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.fishPosRotationIncrements > 0) {
            final double n = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
            final double n2 = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
            final double n3 = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
            this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.fishYaw - this.rotationYaw) / this.fishPosRotationIncrements);
            this.rotationPitch += (float)((this.fishPitch - this.rotationPitch) / this.fishPosRotationIncrements);
            this.fishPosRotationIncrements -= " ".length();
            this.setPosition(n, n2, n3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            if (!this.worldObj.isRemote) {
                final ItemStack currentEquippedItem = this.angler.getCurrentEquippedItem();
                if (this.angler.isDead || !this.angler.isEntityAlive() || currentEquippedItem == null || currentEquippedItem.getItem() != Items.fishing_rod || this.getDistanceSqToEntity(this.angler) > 1024.0) {
                    this.setDead();
                    this.angler.fishEntity = null;
                    return;
                }
                if (this.caughtEntity != null) {
                    if (!this.caughtEntity.isDead) {
                        this.posX = this.caughtEntity.posX;
                        this.posY = this.caughtEntity.getEntityBoundingBox().minY + this.caughtEntity.height * 0.8;
                        this.posZ = this.caughtEntity.posZ;
                        return;
                    }
                    this.caughtEntity = null;
                }
            }
            if (this.shake > 0) {
                this.shake -= " ".length();
            }
            if (this.inGround) {
                if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
                    this.ticksInGround += " ".length();
                    if (this.ticksInGround == 276 + 772 - 93 + 245) {
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
                if (4 <= 0) {
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
            double n4 = 0.0;
            int i = "".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (i < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity2 = entitiesWithinAABBExcludingEntity.get(i);
                if (entity2.canBeCollidedWith() && (entity2 != this.angler || this.ticksInAir >= (0x2F ^ 0x2A))) {
                    final float n5 = 0.3f;
                    final MovingObjectPosition calculateIntercept = entity2.getEntityBoundingBox().expand(n5, n5, n5).calculateIntercept(vec3, vec4);
                    if (calculateIntercept != null) {
                        final double squareDistanceTo = vec3.squareDistanceTo(calculateIntercept.hitVec);
                        if (squareDistanceTo < n4 || n4 == 0.0) {
                            entity = entity2;
                            n4 = squareDistanceTo;
                        }
                    }
                }
                ++i;
            }
            if (entity != null) {
                rayTraceBlocks = new MovingObjectPosition(entity);
            }
            if (rayTraceBlocks != null) {
                if (rayTraceBlocks.entityHit != null) {
                    if (rayTraceBlocks.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.angler), 0.0f)) {
                        this.caughtEntity = rayTraceBlocks.entityHit;
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                    }
                }
                else {
                    this.inGround = (" ".length() != 0);
                }
            }
            if (!this.inGround) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                final float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(MathHelper.func_181159_b(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
                this.rotationPitch = (float)(MathHelper.func_181159_b(this.motionY, sqrt_double) * 180.0 / 3.141592653589793);
                "".length();
                if (2 < 1) {
                    throw null;
                }
                while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                    this.prevRotationPitch -= 360.0f;
                }
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                    this.prevRotationPitch += 360.0f;
                }
                "".length();
                if (false) {
                    throw null;
                }
                while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                    this.prevRotationYaw -= 360.0f;
                }
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                    this.prevRotationYaw += 360.0f;
                }
                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
                float n6 = 0.92f;
                if (this.onGround || this.isCollidedHorizontally) {
                    n6 = 0.5f;
                }
                final int n7 = 0xC1 ^ 0xC4;
                double n8 = 0.0;
                int j = "".length();
                "".length();
                if (4 < 0) {
                    throw null;
                }
                while (j < n7) {
                    final AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
                    final double n9 = entityBoundingBox.maxY - entityBoundingBox.minY;
                    if (this.worldObj.isAABBInMaterial(new AxisAlignedBB(entityBoundingBox.minX, entityBoundingBox.minY + n9 * j / n7, entityBoundingBox.minZ, entityBoundingBox.maxX, entityBoundingBox.minY + n9 * (j + " ".length()) / n7, entityBoundingBox.maxZ), Material.water)) {
                        n8 += 1.0 / n7;
                    }
                    ++j;
                }
                if (!this.worldObj.isRemote && n8 > 0.0) {
                    final WorldServer worldServer = (WorldServer)this.worldObj;
                    int n10 = " ".length();
                    final BlockPos up = new BlockPos(this).up();
                    if (this.rand.nextFloat() < 0.25f && this.worldObj.canLightningStrike(up)) {
                        n10 = "  ".length();
                    }
                    if (this.rand.nextFloat() < 0.5f && !this.worldObj.canSeeSky(up)) {
                        --n10;
                    }
                    if (this.ticksCatchable > 0) {
                        this.ticksCatchable -= " ".length();
                        if (this.ticksCatchable <= 0) {
                            this.ticksCaughtDelay = "".length();
                            this.ticksCatchableDelay = "".length();
                            "".length();
                            if (1 < -1) {
                                throw null;
                            }
                        }
                    }
                    else if (this.ticksCatchableDelay > 0) {
                        this.ticksCatchableDelay -= n10;
                        if (this.ticksCatchableDelay <= 0) {
                            this.motionY -= 0.20000000298023224;
                            this.playSound(EntityFishHook.I["".length()], 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                            final float n11 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
                            worldServer.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX, n11 + 1.0f, this.posZ, (int)(1.0f + this.width * 20.0f), this.width, 0.0, this.width, 0.20000000298023224, new int["".length()]);
                            worldServer.spawnParticle(EnumParticleTypes.WATER_WAKE, this.posX, n11 + 1.0f, this.posZ, (int)(1.0f + this.width * 20.0f), this.width, 0.0, this.width, 0.20000000298023224, new int["".length()]);
                            this.ticksCatchable = MathHelper.getRandomIntegerInRange(this.rand, 0xA ^ 0x0, 0x49 ^ 0x57);
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                        else {
                            this.fishApproachAngle += (float)(this.rand.nextGaussian() * 4.0);
                            final float n12 = this.fishApproachAngle * 0.017453292f;
                            final float sin = MathHelper.sin(n12);
                            final float cos = MathHelper.cos(n12);
                            final double n13 = this.posX + sin * this.ticksCatchableDelay * 0.1f;
                            final double n14 = MathHelper.floor_double(this.getEntityBoundingBox().minY) + 1.0f;
                            final double n15 = this.posZ + cos * this.ticksCatchableDelay * 0.1f;
                            final Block block = worldServer.getBlockState(new BlockPos((int)n13, (int)n14 - " ".length(), (int)n15)).getBlock();
                            if (block == Blocks.water || block == Blocks.flowing_water) {
                                if (this.rand.nextFloat() < 0.15f) {
                                    worldServer.spawnParticle(EnumParticleTypes.WATER_BUBBLE, n13, n14 - 0.10000000149011612, n15, " ".length(), sin, 0.1, cos, 0.0, new int["".length()]);
                                }
                                final float n16 = sin * 0.04f;
                                final float n17 = cos * 0.04f;
                                worldServer.spawnParticle(EnumParticleTypes.WATER_WAKE, n13, n14, n15, "".length(), n17, 0.01, -n16, 1.0, new int["".length()]);
                                worldServer.spawnParticle(EnumParticleTypes.WATER_WAKE, n13, n14, n15, "".length(), -n17, 0.01, n16, 1.0, new int["".length()]);
                                "".length();
                                if (3 == 1) {
                                    throw null;
                                }
                            }
                        }
                    }
                    else if (this.ticksCaughtDelay > 0) {
                        this.ticksCaughtDelay -= n10;
                        float n18 = 0.15f;
                        if (this.ticksCaughtDelay < (0x30 ^ 0x24)) {
                            n18 += (float)(((0xAD ^ 0xB9) - this.ticksCaughtDelay) * 0.05);
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else if (this.ticksCaughtDelay < (0xA1 ^ 0x89)) {
                            n18 += (float)(((0xA6 ^ 0x8E) - this.ticksCaughtDelay) * 0.02);
                            "".length();
                            if (2 == 4) {
                                throw null;
                            }
                        }
                        else if (this.ticksCaughtDelay < (0x95 ^ 0xA9)) {
                            n18 += (float)(((0x8 ^ 0x34) - this.ticksCaughtDelay) * 0.01);
                        }
                        if (this.rand.nextFloat() < n18) {
                            final float n19 = MathHelper.randomFloatClamp(this.rand, 0.0f, 360.0f) * 0.017453292f;
                            final float randomFloatClamp = MathHelper.randomFloatClamp(this.rand, 25.0f, 60.0f);
                            final double n20 = this.posX + MathHelper.sin(n19) * randomFloatClamp * 0.1f;
                            final double n21 = MathHelper.floor_double(this.getEntityBoundingBox().minY) + 1.0f;
                            final double n22 = this.posZ + MathHelper.cos(n19) * randomFloatClamp * 0.1f;
                            final Block block2 = worldServer.getBlockState(new BlockPos((int)n20, (int)n21 - " ".length(), (int)n22)).getBlock();
                            if (block2 == Blocks.water || block2 == Blocks.flowing_water) {
                                worldServer.spawnParticle(EnumParticleTypes.WATER_SPLASH, n20, n21, n22, "  ".length() + this.rand.nextInt("  ".length()), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0, new int["".length()]);
                            }
                        }
                        if (this.ticksCaughtDelay <= 0) {
                            this.fishApproachAngle = MathHelper.randomFloatClamp(this.rand, 0.0f, 360.0f);
                            this.ticksCatchableDelay = MathHelper.getRandomIntegerInRange(this.rand, 0xB9 ^ 0xAD, 0x3C ^ 0x6C);
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.ticksCaughtDelay = MathHelper.getRandomIntegerInRange(this.rand, 0xC5 ^ 0xA1, 341 + 573 - 118 + 104);
                        this.ticksCaughtDelay -= EnchantmentHelper.getLureModifier(this.angler) * (0x7E ^ 0x6A) * (0x87 ^ 0x82);
                    }
                    if (this.ticksCatchable > 0) {
                        this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2;
                    }
                }
                this.motionY += 0.03999999910593033 * (n8 * 2.0 - 1.0);
                if (n8 > 0.0) {
                    n6 *= 0.9;
                    this.motionY *= 0.8;
                }
                this.motionX *= n6;
                this.motionY *= n6;
                this.motionZ *= n6;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }
    
    public EntityFishHook(final World world, final EntityPlayer angler) {
        super(world);
        this.xTile = -" ".length();
        this.yTile = -" ".length();
        this.zTile = -" ".length();
        this.ignoreFrustumCheck = (" ".length() != 0);
        this.angler = angler;
        (this.angler.fishEntity = this).setSize(0.25f, 0.25f);
        this.setLocationAndAngles(angler.posX, angler.posY + angler.getEyeHeight(), angler.posZ, angler.rotationYaw, angler.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        final float n = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }
    
    @Override
    public void setDead() {
        super.setDead();
        if (this.angler != null) {
            this.angler.fishEntity = null;
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort(EntityFishHook.I[" ".length()], (short)this.xTile);
        nbtTagCompound.setShort(EntityFishHook.I["  ".length()], (short)this.yTile);
        nbtTagCompound.setShort(EntityFishHook.I["   ".length()], (short)this.zTile);
        final ResourceLocation resourceLocation = Block.blockRegistry.getNameForObject(this.inTile);
        final String s = EntityFishHook.I[0x1F ^ 0x1B];
        String string;
        if (resourceLocation == null) {
            string = EntityFishHook.I[0xC4 ^ 0xC1];
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            string = resourceLocation.toString();
        }
        nbtTagCompound.setString(s, string);
        nbtTagCompound.setByte(EntityFishHook.I[0x6C ^ 0x6A], (byte)this.shake);
        final String s2 = EntityFishHook.I[0x34 ^ 0x33];
        int n;
        if (this.inGround) {
            n = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        nbtTagCompound.setByte(s2, (byte)n);
    }
    
    static {
        I();
        final WeightedRandomFishable[] array = new WeightedRandomFishable[0x8D ^ 0x86];
        array["".length()] = new WeightedRandomFishable(new ItemStack(Items.leather_boots), 0x42 ^ 0x48).setMaxDamagePercent(0.9f);
        array[" ".length()] = new WeightedRandomFishable(new ItemStack(Items.leather), 0x60 ^ 0x6A);
        array["  ".length()] = new WeightedRandomFishable(new ItemStack(Items.bone), 0x32 ^ 0x38);
        array["   ".length()] = new WeightedRandomFishable(new ItemStack(Items.potionitem), 0x29 ^ 0x23);
        array[0x71 ^ 0x75] = new WeightedRandomFishable(new ItemStack(Items.string), 0xB0 ^ 0xB5);
        array[0xBC ^ 0xB9] = new WeightedRandomFishable(new ItemStack(Items.fishing_rod), "  ".length()).setMaxDamagePercent(0.9f);
        array[0x7C ^ 0x7A] = new WeightedRandomFishable(new ItemStack(Items.bowl), 0x89 ^ 0x83);
        array[0x73 ^ 0x74] = new WeightedRandomFishable(new ItemStack(Items.stick), 0x61 ^ 0x64);
        array[0x4C ^ 0x44] = new WeightedRandomFishable(new ItemStack(Items.dye, 0x97 ^ 0x9D, EnumDyeColor.BLACK.getDyeDamage()), " ".length());
        array[0x87 ^ 0x8E] = new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 0xB3 ^ 0xB9);
        array[0x8 ^ 0x2] = new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 0x5E ^ 0x54);
        JUNK = Arrays.asList(array);
        final WeightedRandomFishable[] array2 = new WeightedRandomFishable[0xBB ^ 0xBD];
        array2["".length()] = new WeightedRandomFishable(new ItemStack(Blocks.waterlily), " ".length());
        array2[" ".length()] = new WeightedRandomFishable(new ItemStack(Items.name_tag), " ".length());
        array2["  ".length()] = new WeightedRandomFishable(new ItemStack(Items.saddle), " ".length());
        array2["   ".length()] = new WeightedRandomFishable(new ItemStack(Items.bow), " ".length()).setMaxDamagePercent(0.25f).setEnchantable();
        array2[0x90 ^ 0x94] = new WeightedRandomFishable(new ItemStack(Items.fishing_rod), " ".length()).setMaxDamagePercent(0.25f).setEnchantable();
        array2[0x2C ^ 0x29] = new WeightedRandomFishable(new ItemStack(Items.book), " ".length()).setEnchantable();
        TREASURE = Arrays.asList(array2);
        final WeightedRandomFishable[] array3 = new WeightedRandomFishable[0x58 ^ 0x5C];
        array3["".length()] = new WeightedRandomFishable(new ItemStack(Items.fish, " ".length(), ItemFishFood.FishType.COD.getMetadata()), 0x91 ^ 0xAD);
        array3[" ".length()] = new WeightedRandomFishable(new ItemStack(Items.fish, " ".length(), ItemFishFood.FishType.SALMON.getMetadata()), 0x40 ^ 0x59);
        array3["  ".length()] = new WeightedRandomFishable(new ItemStack(Items.fish, " ".length(), ItemFishFood.FishType.CLOWNFISH.getMetadata()), "  ".length());
        array3["   ".length()] = new WeightedRandomFishable(new ItemStack(Items.fish, " ".length(), ItemFishFood.FishType.PUFFERFISH.getMetadata()), 0x94 ^ 0x99);
        FISH = Arrays.asList(array3);
    }
    
    public EntityFishHook(final World world, final double n, final double n2, final double n3, final EntityPlayer angler) {
        this(world);
        this.setPosition(n, n2, n3);
        this.ignoreFrustumCheck = (" ".length() != 0);
        this.angler = angler;
        angler.fishEntity = this;
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.clientMotionX = n;
        this.motionY = n2;
        this.clientMotionY = n2;
        this.motionZ = n3;
        this.clientMotionZ = n3;
    }
    
    public void handleHookCasting(double motionX, double motionY, double motionZ, final float n, final float n2) {
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
    
    public static List<WeightedRandomFishable> func_174855_j() {
        return EntityFishHook.FISH;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort(EntityFishHook.I[0x11 ^ 0x19]);
        this.yTile = nbtTagCompound.getShort(EntityFishHook.I[0xBF ^ 0xB6]);
        this.zTile = nbtTagCompound.getShort(EntityFishHook.I[0x69 ^ 0x63]);
        if (nbtTagCompound.hasKey(EntityFishHook.I[0xBA ^ 0xB1], 0x35 ^ 0x3D)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString(EntityFishHook.I[0x9F ^ 0x93]));
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte(EntityFishHook.I[0x1C ^ 0x11]) & 116 + 206 - 68 + 1);
        }
        this.shake = (nbtTagCompound.getByte(EntityFishHook.I[0x1D ^ 0x13]) & 251 + 229 - 231 + 6);
        int inGround;
        if (nbtTagCompound.getByte(EntityFishHook.I[0x14 ^ 0x1B]) == " ".length()) {
            inGround = " ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            inGround = "".length();
        }
        this.inGround = (inGround != 0);
    }
    
    private static void I() {
        (I = new String[0x2F ^ 0x3F])["".length()] = I("\u00041<5\u0000\u001b~!!\u0003\u0017#:", "vPRQo");
        EntityFishHook.I[" ".length()] = I("\u001f\u001c0\u00062", "gHYjW");
        EntityFishHook.I["  ".length()] = I("2;+\u0005&", "KoBiC");
        EntityFishHook.I["   ".length()] = I("\u001c#\u0005\"2", "fwlNW");
        EntityFishHook.I[0xA3 ^ 0xA7] = I("\u001d\r<\u000f\u000b\u0011", "tchfg");
        EntityFishHook.I[0x79 ^ 0x7C] = I("", "TcXJI");
        EntityFishHook.I[0x2E ^ 0x28] = I("4\u000e\u0004\u0005\t", "Gfenl");
        EntityFishHook.I[0x1E ^ 0x19] = I(":\f\u0002\u0005\u0002&\f!", "SbEwm");
        EntityFishHook.I[0x37 ^ 0x3F] = I("\u000b\u0006\u0006\u0007+", "sRokN");
        EntityFishHook.I[0x6 ^ 0xF] = I("=,'\u000b\u0016", "DxNgs");
        EntityFishHook.I[0x6E ^ 0x64] = I("+\u00153\u001e6", "QAZrS");
        EntityFishHook.I[0x52 ^ 0x59] = I(">\u000b\u001c\u001a:2", "WeHsV");
        EntityFishHook.I[0x2C ^ 0x20] = I("\u0013 \u0018\r\u000b\u001f", "zNLdg");
        EntityFishHook.I[0xF ^ 0x2] = I("\f/\u0002>\t\u0000", "eAVWe");
        EntityFishHook.I[0x4B ^ 0x45] = I("* $:\u0011", "YHEQt");
        EntityFishHook.I[0x76 ^ 0x79] = I("\"%*\u0001.>%\t", "KKmsA");
    }
}
