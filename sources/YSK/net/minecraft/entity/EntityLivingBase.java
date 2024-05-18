package net.minecraft.entity;

import net.minecraft.enchantment.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;
import net.minecraft.potion.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;

public abstract class EntityLivingBase extends Entity
{
    public float moveStrafing;
    public float prevSwingProgress;
    protected float movedDistance;
    protected double newRotationPitch;
    private int lastAttackerTime;
    public float jumpMovementFactor;
    protected float randomYawVelocity;
    public int hurtTime;
    public float attackedAtYaw;
    protected int entityAge;
    public int swingProgressInt;
    public float prevRenderYawOffset;
    protected int newPosRotationIncrements;
    public float renderYawOffset;
    private boolean potionsNeedUpdate;
    public int maxHurtTime;
    public float moveForward;
    private int revengeTimer;
    protected double newPosZ;
    protected double newRotationYaw;
    protected int recentlyHit;
    public float limbSwing;
    public float swingProgress;
    protected float lastDamage;
    protected boolean dead;
    protected boolean isJumping;
    private final Map<Integer, PotionEffect> activePotionsMap;
    private BaseAttributeMap attributeMap;
    private float absorptionAmount;
    public int deathTime;
    protected int scoreValue;
    public float prevRotationYawHead;
    protected EntityPlayer attackingPlayer;
    protected float field_70741_aB;
    protected float prevOnGroundSpeedFactor;
    public float cameraPitch;
    public float prevCameraPitch;
    protected double newPosY;
    public int arrowHitTimer;
    protected float onGroundSpeedFactor;
    public float prevLimbSwingAmount;
    private final CombatTracker _combatTracker;
    private EntityLivingBase entityLivingToAttack;
    public boolean isSwingInProgress;
    private static final AttributeModifier sprintingSpeedBoostModifier;
    private int jumpTicks;
    private static final String[] I;
    protected float prevMovedDistance;
    private final ItemStack[] previousEquipment;
    public float rotationYawHead;
    public float field_70770_ap;
    public int maxHurtResistantTime;
    private EntityLivingBase lastAttacker;
    private float landMovementFactor;
    protected double newPosX;
    public float field_70769_ao;
    private static final UUID sprintingSpeedBoostModifierUUID;
    public float limbSwingAmount;
    
    public abstract ItemStack getHeldItem();
    
    protected void markPotionsDirty() {
        this.potionsNeedUpdate = (" ".length() != 0);
    }
    
    protected void handleJumpLava() {
        this.motionY += 0.03999999910593033;
    }
    
    public int getLastAttackerTime() {
        return this.lastAttackerTime;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public int getAge() {
        return this.entityAge;
    }
    
    public EntityLivingBase getLastAttacker() {
        return this.lastAttacker;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(0x84 ^ 0x83, "".length());
        this.dataWatcher.addObject(0x6A ^ 0x62, (byte)"".length());
        this.dataWatcher.addObject(0x5 ^ 0xC, (byte)"".length());
        this.dataWatcher.addObject(0x95 ^ 0x93, 1.0f);
    }
    
    public void renderBrokenItemStack(final ItemStack itemStack) {
        this.playSound(EntityLivingBase.I[0x16 ^ 0x1], 0.8f, 0.8f + this.worldObj.rand.nextFloat() * 0.4f);
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < (0x82 ^ 0x87)) {
            final Vec3 rotateYaw = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f);
            final Vec3 addVector = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f).addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
            final World worldObj = this.worldObj;
            final EnumParticleTypes item_CRACK = EnumParticleTypes.ITEM_CRACK;
            final double xCoord = addVector.xCoord;
            final double yCoord = addVector.yCoord;
            final double zCoord = addVector.zCoord;
            final double xCoord2 = rotateYaw.xCoord;
            final double n = rotateYaw.yCoord + 0.05;
            final double zCoord2 = rotateYaw.zCoord;
            final int[] array = new int[" ".length()];
            array["".length()] = Item.getIdFromItem(itemStack.getItem());
            worldObj.spawnParticle(item_CRACK, xCoord, yCoord, zCoord, xCoord2, n, zCoord2, array);
            ++i;
        }
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    public boolean isChild() {
        return "".length() != 0;
    }
    
    protected void onDeathUpdate() {
        this.deathTime += " ".length();
        if (this.deathTime == (0x15 ^ 0x1)) {
            if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.canDropLoot() && this.worldObj.getGameRules().getBoolean(EntityLivingBase.I["   ".length()])) {
                int i = this.getExperiencePoints(this.attackingPlayer);
                "".length();
                if (false) {
                    throw null;
                }
                while (i > 0) {
                    final int xpSplit = EntityXPOrb.getXPSplit(i);
                    i -= xpSplit;
                    this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, xpSplit));
                }
            }
            this.setDead();
            int j = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (j < (0x5F ^ 0x4B)) {
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int["".length()]);
                ++j;
            }
        }
    }
    
    protected void addRandomDrop() {
    }
    
    static {
        I();
        sprintingSpeedBoostModifierUUID = UUID.fromString(EntityLivingBase.I["".length()]);
        sprintingSpeedBoostModifier = new AttributeModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID, EntityLivingBase.I[" ".length()], 0.30000001192092896, "  ".length()).setSaved("".length() != 0);
    }
    
    public boolean isOnSameTeam(final EntityLivingBase entityLivingBase) {
        return this.isOnTeam(entityLivingBase.getTeam());
    }
    
    public boolean isEntityUndead() {
        if (this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this.isServerWorld()) {
            if (!this.isInWater() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
                if (!this.isInLava() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
                    float n3 = 0.91f;
                    if (this.onGround) {
                        n3 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - " ".length(), MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
                    }
                    final float n4 = 0.16277136f / (n3 * n3 * n3);
                    float jumpMovementFactor;
                    if (this.onGround) {
                        jumpMovementFactor = this.getAIMoveSpeed() * n4;
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    else {
                        jumpMovementFactor = this.jumpMovementFactor;
                    }
                    this.moveFlying(n, n2, jumpMovementFactor);
                    float n5 = 0.91f;
                    if (this.onGround) {
                        n5 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - " ".length(), MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91f;
                    }
                    if (this.isOnLadder()) {
                        final float n6 = 0.15f;
                        this.motionX = MathHelper.clamp_double(this.motionX, -n6, n6);
                        this.motionZ = MathHelper.clamp_double(this.motionZ, -n6, n6);
                        this.fallDistance = 0.0f;
                        if (this.motionY < -0.15) {
                            this.motionY = -0.15;
                        }
                        int n7;
                        if (this.isSneaking() && this instanceof EntityPlayer) {
                            n7 = " ".length();
                            "".length();
                            if (4 < 4) {
                                throw null;
                            }
                        }
                        else {
                            n7 = "".length();
                        }
                        if (n7 != 0 && this.motionY < 0.0) {
                            this.motionY = 0.0;
                        }
                    }
                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                    if (this.isCollidedHorizontally && this.isOnLadder()) {
                        this.motionY = 0.2;
                    }
                    if (this.worldObj.isRemote && (!this.worldObj.isBlockLoaded(new BlockPos((int)this.posX, "".length(), (int)this.posZ)) || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, "".length(), (int)this.posZ)).isLoaded())) {
                        if (this.posY > 0.0) {
                            this.motionY = -0.1;
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else {
                            this.motionY = 0.0;
                            "".length();
                            if (1 < -1) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.motionY -= 0.08;
                    }
                    this.motionY *= 0.9800000190734863;
                    this.motionX *= n5;
                    this.motionZ *= n5;
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else {
                    final double posY = this.posY;
                    this.moveFlying(n, n2, 0.02f);
                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                    this.motionX *= 0.5;
                    this.motionY *= 0.5;
                    this.motionZ *= 0.5;
                    this.motionY -= 0.02;
                    if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + posY, this.motionZ)) {
                        this.motionY = 0.30000001192092896;
                        "".length();
                        if (0 < -1) {
                            throw null;
                        }
                    }
                }
            }
            else {
                final double posY2 = this.posY;
                float n8 = 0.8f;
                float n9 = 0.02f;
                float n10 = EnchantmentHelper.getDepthStriderModifier(this);
                if (n10 > 3.0f) {
                    n10 = 3.0f;
                }
                if (!this.onGround) {
                    n10 *= 0.5f;
                }
                if (n10 > 0.0f) {
                    n8 += (0.54600006f - n8) * n10 / 3.0f;
                    n9 += (this.getAIMoveSpeed() * 1.0f - n9) * n10 / 3.0f;
                }
                this.moveFlying(n, n2, n9);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= n8;
                this.motionY *= 0.800000011920929;
                this.motionZ *= n8;
                this.motionY -= 0.02;
                if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579 - this.posY + posY2, this.motionZ)) {
                    this.motionY = 0.30000001192092896;
                }
            }
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        final double n11 = this.posX - this.prevPosX;
        final double n12 = this.posZ - this.prevPosZ;
        float n13 = MathHelper.sqrt_double(n11 * n11 + n12 * n12) * 4.0f;
        if (n13 > 1.0f) {
            n13 = 1.0f;
        }
        this.limbSwingAmount += (n13 - this.limbSwingAmount) * 0.4f;
        this.limbSwing += this.limbSwingAmount;
    }
    
    public float getAIMoveSpeed() {
        return this.landMovementFactor;
    }
    
    public void onDeath(final DamageSource damageSource) {
        final Entity entity = damageSource.getEntity();
        final EntityLivingBase func_94060_bK = this.func_94060_bK();
        if (this.scoreValue >= 0 && func_94060_bK != null) {
            func_94060_bK.addToPlayerScore(this, this.scoreValue);
        }
        if (entity != null) {
            entity.onKillEntity(this);
        }
        this.dead = (" ".length() != 0);
        this.getCombatTracker().reset();
        if (!this.worldObj.isRemote) {
            int n = "".length();
            if (entity instanceof EntityPlayer) {
                n = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
            }
            if (this.canDropLoot() && this.worldObj.getGameRules().getBoolean(EntityLivingBase.I[0x2A ^ 0x32])) {
                int n2;
                if (this.recentlyHit > 0) {
                    n2 = " ".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                this.dropFewItems(n2 != 0, n);
                int n3;
                if (this.recentlyHit > 0) {
                    n3 = " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                this.dropEquipment(n3 != 0, n);
                if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025f + n * 0.01f) {
                    this.addRandomDrop();
                }
            }
        }
        this.worldObj.setEntityState(this, (byte)"   ".length());
    }
    
    @Override
    public Vec3 getLook(final float n) {
        if (n == 1.0f) {
            return this.getVectorForRotation(this.rotationPitch, this.rotationYawHead);
        }
        return this.getVectorForRotation(this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * n, this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * n);
    }
    
    protected int decreaseAirSupply(final int n) {
        final int respiration = EnchantmentHelper.getRespiration(this);
        int n2;
        if (respiration > 0 && this.rand.nextInt(respiration + " ".length()) > 0) {
            n2 = n;
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n2 = n - " ".length();
        }
        return n2;
    }
    
    @Override
    public void func_181013_g(final float renderYawOffset) {
        this.renderYawOffset = renderYawOffset;
    }
    
    public abstract ItemStack getCurrentArmor(final int p0);
    
    @Override
    public abstract void setCurrentItemOrArmor(final int p0, final ItemStack p1);
    
    protected void dropFewItems(final boolean b, final int n) {
    }
    
    public void setAbsorptionAmount(float absorptionAmount) {
        if (absorptionAmount < 0.0f) {
            absorptionAmount = 0.0f;
        }
        this.absorptionAmount = absorptionAmount;
    }
    
    private static void I() {
        (I = new String[0x27 ^ 0x3])["".length()] = I("ulX\u0005y\u0001b.i\u000b\u0002i/i{\u0000k)iw{kYivu\u001f+r\u007fzmXsw\u0007", "CZjDO");
        EntityLivingBase.I[" ".length()] = I("\u001f\u0014(=\u00148\r43Z?\u0014?1\u001el\u00065;\t8", "LdZTz");
        EntityLivingBase.I["  ".length()] = I(";?5\u001040\u0013-\r3#/\u0001\u0018)2\u0002*\u001a1", "WVCyZ");
        EntityLivingBase.I["   ".length()] = I("78\u0015\u001e\u0006\u001f87\u0005", "SWXqd");
        EntityLivingBase.I[0x9A ^ 0x9E] = I("\u0000\u000f\u000e<\u0001", "HjoPG");
        EntityLivingBase.I[0x8D ^ 0x88] = I("!\u0001;',\u0001", "idZKX");
        EntityLivingBase.I[0x42 ^ 0x44] = I("\u0005\u0014#\u0010&$\f4", "MaQdr");
        EntityLivingBase.I[0xA6 ^ 0xA1] = I("\u00060$\u001777\u0011?\u000e\u0010=17\u000e\u0005", "NEVcu");
        EntityLivingBase.I[0xA7 ^ 0xAF] = I("2-\r9\"\"!\u0001(", "vHlMJ");
        EntityLivingBase.I[0x9F ^ 0x96] = I("97*:7\b!0:+986 +\f", "xUYUE");
        EntityLivingBase.I[0x50 ^ 0x5A] = I("$&%>?\u0007'%)%", "eRQLV");
        EntityLivingBase.I[0x6F ^ 0x64] = I("\u0018/&.\u001d<\t4!\u000e:8!", "YLRGk");
        EntityLivingBase.I[0x25 ^ 0x29] = I("\u0013.!;\u0005\"8;;\u0019\u0013!=!\u0019&", "RLRTw");
        EntityLivingBase.I[0x3B ^ 0x36] = I("+\u0005\u001b80\b\u0004\u001b/*", "jqoJY");
        EntityLivingBase.I[0x31 ^ 0x3F] = I("\f\u0017$>\u0002/\u0016$)\u0018", "McPLk");
        EntityLivingBase.I[0xF ^ 0x0] = I("\r-\u001b\u0011\u0003)\u000b\t\u001e\u0010/:\u001c", "LNoxu");
        EntityLivingBase.I[0x33 ^ 0x23] = I("&\u0012\u0010\u001f\u0000\u00024\u0002\u0010\u0013\u0004\u0005\u0017", "gqdvv");
        EntityLivingBase.I[0x82 ^ 0x93] = I("\u001c7;/$", "TRZCb");
        EntityLivingBase.I[0x75 ^ 0x67] = I("\u00037\u001b-/", "KRzAi");
        EntityLivingBase.I[0x21 ^ 0x32] = I("-\u001f;::\r", "ezZVN");
        EntityLivingBase.I[0x37 ^ 0x23] = I("%\u001d\u0005&\u0003\u0004\u0005\u0012", "mhwRW");
        EntityLivingBase.I[0x61 ^ 0x74] = I("\r79\u0011\u001b\u001d;5\u0000", "IRXes");
        EntityLivingBase.I[0xAC ^ 0xBA] = I("26;\u0007/\u0003\u0017 \u001e\b\t7(\u001e\u001d", "zCIsm");
        EntityLivingBase.I[0x5F ^ 0x48] = I("9\u001b9\u0006<&T5\u00106*\u0011", "KzWbS");
        EntityLivingBase.I[0x8B ^ 0x93] = I("\u0006\">:\u0018.\"\u001c!", "bMsUz");
        EntityLivingBase.I[0x29 ^ 0x30] = I("$\u0010\u0019/W-\u0014\u0001>\u000b\"\u001dZ\"\f1\u0005", "CqtJy");
        EntityLivingBase.I[0x98 ^ 0x82] = I("\u0014\u0007.\u0006d\u001d\u00036\u00178\u0012\nm\u0007#\u0016", "sfCcJ");
        EntityLivingBase.I[0x87 ^ 0x9C] = I("+\t71L\"\r/ \u0010-\u0004t<\u0017>\u001ct2\u0003 \u0004t6\u000b+", "LhZTb");
        EntityLivingBase.I[0x17 ^ 0xB] = I("$\u0012\u000b\u0011B-\u0016\u0013\u0000\u001e\"\u001fH\u001c\u00191\u0007H\u0012\r/\u001fH\u0007\u0001\"\u001f\n", "Csftl");
        EntityLivingBase.I[0x64 ^ 0x79] = I("!\u0015\u0004=5<\u0002\u000b", "IpeYa");
        EntityLivingBase.I[0x76 ^ 0x68] = I("\u0010\u000b!(&!\u0002*,(\u0011", "bjOOC");
        EntityLivingBase.I[0xBC ^ 0xA3] = I(".\r", "OdwON");
        EntityLivingBase.I[0x34 ^ 0x14] = I("%\"\u0014\t\u0010", "KGcHy");
        EntityLivingBase.I[0xAB ^ 0x8A] = I("(2\u00072", "BGjBW");
        EntityLivingBase.I[0xE6 ^ 0xC4] = I("'?\u00071,?", "SMfGI");
        EntityLivingBase.I[0x16 ^ 0x35] = I("\u00162\u0007\u0019", "fGtqo");
    }
    
    public void setLastAttacker(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.lastAttacker = (EntityLivingBase)entity;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            this.lastAttacker = null;
        }
        this.lastAttackerTime = this.ticksExisted;
    }
    
    protected float applyPotionDamageCalculations(final DamageSource damageSource, float n) {
        if (damageSource.isDamageAbsolute()) {
            return n;
        }
        if (this.isPotionActive(Potion.resistance) && damageSource != DamageSource.outOfWorld) {
            n = n * ((0xDC ^ 0xC5) - (this.getActivePotionEffect(Potion.resistance).getAmplifier() + " ".length()) * (0x21 ^ 0x24)) / 25.0f;
        }
        if (n <= 0.0f) {
            return 0.0f;
        }
        int enchantmentModifierDamage = EnchantmentHelper.getEnchantmentModifierDamage(this.getInventory(), damageSource);
        if (enchantmentModifierDamage > (0x7D ^ 0x69)) {
            enchantmentModifierDamage = (0x1 ^ 0x15);
        }
        if (enchantmentModifierDamage > 0 && enchantmentModifierDamage <= (0x39 ^ 0x2D)) {
            n = n * ((0x96 ^ 0x8F) - enchantmentModifierDamage) / 25.0f;
        }
        return n;
    }
    
    public void sendEnterCombat() {
    }
    
    protected boolean canDropLoot() {
        int n;
        if (this.isChild()) {
            n = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        this.onGroundSpeedFactor = 0.0f;
        this.fallDistance = 0.0f;
    }
    
    public EntityLivingBase getAITarget() {
        return this.entityLivingToAttack;
    }
    
    public final float getMaxHealth() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
    }
    
    protected void updateAITick() {
        this.motionY += 0.03999999910593033;
    }
    
    public abstract ItemStack getEquipmentInSlot(final int p0);
    
    public EntityLivingBase(final World world) {
        super(world);
        this._combatTracker = new CombatTracker(this);
        this.activePotionsMap = (Map<Integer, PotionEffect>)Maps.newHashMap();
        this.previousEquipment = new ItemStack[0x4F ^ 0x4A];
        this.maxHurtResistantTime = (0x7E ^ 0x6A);
        this.jumpMovementFactor = 0.02f;
        this.potionsNeedUpdate = (" ".length() != 0);
        this.applyEntityAttributes();
        this.setHealth(this.getMaxHealth());
        this.preventEntitySpawning = (" ".length() != 0);
        this.field_70770_ap = (float)((Math.random() + 1.0) * 0.009999999776482582);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.field_70769_ao = (float)Math.random() * 12398.0f;
        this.rotationYaw = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.rotationYawHead = this.rotationYaw;
        this.stepHeight = 0.6f;
    }
    
    protected float func_110146_f(final float n, float n2) {
        this.renderYawOffset += MathHelper.wrapAngleTo180_float(n - this.renderYawOffset) * 0.3f;
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.renderYawOffset);
        int n3;
        if (wrapAngleTo180_float >= -90.0f && wrapAngleTo180_float < 90.0f) {
            n3 = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n3 = " ".length();
        }
        final int n4 = n3;
        if (wrapAngleTo180_float < -75.0f) {
            wrapAngleTo180_float = -75.0f;
        }
        if (wrapAngleTo180_float >= 75.0f) {
            wrapAngleTo180_float = 75.0f;
        }
        this.renderYawOffset = this.rotationYaw - wrapAngleTo180_float;
        if (wrapAngleTo180_float * wrapAngleTo180_float > 2500.0f) {
            this.renderYawOffset += wrapAngleTo180_float * 0.2f;
        }
        if (n4 != 0) {
            n2 *= -1.0f;
        }
        return n2;
    }
    
    public boolean isPlayerSleeping() {
        return "".length() != 0;
    }
    
    public final int getArrowCountInEntity() {
        return this.dataWatcher.getWatchableObjectByte(0x3D ^ 0x34);
    }
    
    public void dismountEntity(final Entity entity) {
        double posX = entity.posX;
        double n = entity.getEntityBoundingBox().minY + entity.height;
        double posZ = entity.posZ;
        final int length = " ".length();
        int i = -length;
        "".length();
        if (true != true) {
            throw null;
        }
        while (i <= length) {
            int j = -length;
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (j < length) {
                if (i != 0 || j != 0) {
                    final int n2 = (int)(this.posX + i);
                    final int n3 = (int)(this.posZ + j);
                    if (this.worldObj.func_147461_a(this.getEntityBoundingBox().offset(i, 1.0, j)).isEmpty()) {
                        if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, (int)this.posY, n3))) {
                            this.setPositionAndUpdate(this.posX + i, this.posY + 1.0, this.posZ + j);
                            return;
                        }
                        if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, (int)this.posY - " ".length(), n3)) || this.worldObj.getBlockState(new BlockPos(n2, (int)this.posY - " ".length(), n3)).getBlock().getMaterial() == Material.water) {
                            posX = this.posX + i;
                            n = this.posY + 1.0;
                            posZ = this.posZ + j;
                        }
                    }
                }
                ++j;
            }
            ++i;
        }
        this.setPositionAndUpdate(posX, n, posZ);
    }
    
    protected void collideWithEntity(final Entity entity) {
        entity.applyEntityCollision(this);
    }
    
    public void clearActivePotions() {
        final Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final PotionEffect potionEffect = this.activePotionsMap.get(iterator.next());
            if (!this.worldObj.isRemote) {
                iterator.remove();
                this.onFinishedPotionEffect(potionEffect);
            }
        }
    }
    
    public float getAbsorptionAmount() {
        return this.absorptionAmount;
    }
    
    public void setRevengeTarget(final EntityLivingBase entityLivingToAttack) {
        this.entityLivingToAttack = entityLivingToAttack;
        this.revengeTimer = this.ticksExisted;
    }
    
    protected void updatePotionMetadata() {
        if (this.activePotionsMap.isEmpty()) {
            this.resetPotionEffectMetadata();
            this.setInvisible("".length() != 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            final int calcPotionLiquidColor = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
            final DataWatcher dataWatcher = this.dataWatcher;
            final int n = 0x80 ^ 0x88;
            int n2;
            if (PotionHelper.getAreAmbient(this.activePotionsMap.values())) {
                n2 = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            dataWatcher.updateObject(n, (byte)n2);
            this.dataWatcher.updateObject(0x75 ^ 0x72, calcPotionLiquidColor);
            this.setInvisible(this.isPotionActive(Potion.invisibility.id));
        }
    }
    
    protected void updatePotionEffects() {
        final Iterator<Integer> iterator = this.activePotionsMap.keySet().iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final PotionEffect potionEffect = this.activePotionsMap.get(iterator.next());
            if (!potionEffect.onUpdate(this)) {
                if (this.worldObj.isRemote) {
                    continue;
                }
                iterator.remove();
                this.onFinishedPotionEffect(potionEffect);
                "".length();
                if (4 == 0) {
                    throw null;
                }
                continue;
            }
            else {
                if (potionEffect.getDuration() % (194 + 231 - 144 + 319) != 0) {
                    continue;
                }
                this.onChangedPotionEffect(potionEffect, "".length() != 0);
            }
        }
        if (this.potionsNeedUpdate) {
            if (!this.worldObj.isRemote) {
                this.updatePotionMetadata();
            }
            this.potionsNeedUpdate = ("".length() != 0);
        }
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(0xC5 ^ 0xC2);
        int n;
        if (this.dataWatcher.getWatchableObjectByte(0xBD ^ 0xB5) > 0) {
            n = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        if (watchableObjectInt > 0) {
            "".length();
            int nextBoolean;
            if (!this.isInvisible()) {
                nextBoolean = (this.rand.nextBoolean() ? 1 : 0);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                int n3;
                if (this.rand.nextInt(0x76 ^ 0x79) == 0) {
                    n3 = " ".length();
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                nextBoolean = n3;
            }
            if (n2 != 0) {
                final int n4 = nextBoolean;
                int n5;
                if (this.rand.nextInt(0x1D ^ 0x18) == 0) {
                    n5 = " ".length();
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else {
                    n5 = "".length();
                }
                nextBoolean = (n4 & n5);
            }
            if (nextBoolean != 0 && watchableObjectInt > 0) {
                final double n6 = (watchableObjectInt >> (0x64 ^ 0x74) & 3 + 189 - 87 + 150) / 255.0;
                final double n7 = (watchableObjectInt >> (0x26 ^ 0x2E) & 135 + 144 - 76 + 52) / 255.0;
                final double n8 = (watchableObjectInt >> "".length() & 99 + 231 - 275 + 200) / 255.0;
                final World worldObj = this.worldObj;
                EnumParticleTypes enumParticleTypes;
                if (n2 != 0) {
                    enumParticleTypes = EnumParticleTypes.SPELL_MOB_AMBIENT;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    enumParticleTypes = EnumParticleTypes.SPELL_MOB;
                }
                worldObj.spawnParticle(enumParticleTypes, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, n6, n7, n8, new int["".length()]);
            }
        }
    }
    
    @Override
    protected void setBeenAttacked() {
        int velocityChanged;
        if (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
            velocityChanged = " ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            velocityChanged = "".length();
        }
        this.velocityChanged = (velocityChanged != 0);
    }
    
    @Override
    public void setRotationYawHead(final float rotationYawHead) {
        this.rotationYawHead = rotationYawHead;
    }
    
    protected void updateEntityActionState() {
    }
    
    public int getRevengeTimer() {
        return this.revengeTimer;
    }
    
    public void addPotionEffect(final PotionEffect potionEffect) {
        if (this.isPotionApplicable(potionEffect)) {
            if (this.activePotionsMap.containsKey(potionEffect.getPotionID())) {
                this.activePotionsMap.get(potionEffect.getPotionID()).combine(potionEffect);
                this.onChangedPotionEffect(this.activePotionsMap.get(potionEffect.getPotionID()), " ".length() != 0);
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                this.activePotionsMap.put(potionEffect.getPotionID(), potionEffect);
                this.onNewPotionEffect(potionEffect);
            }
        }
    }
    
    protected void onNewPotionEffect(final PotionEffect potionEffect) {
        this.potionsNeedUpdate = (" ".length() != 0);
        if (!this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    @Override
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    public boolean isOnTeam(final Team team) {
        int n;
        if (this.getTeam() != null) {
            n = (this.getTeam().isSameTeam(team) ? 1 : 0);
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void onEntityUpdate() {
        this.prevSwingProgress = this.swingProgress;
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection(EntityLivingBase.I["  ".length()]);
        final boolean b = this instanceof EntityPlayer;
        if (this.isEntityAlive()) {
            if (this.isEntityInsideOpaqueBlock()) {
                this.attackEntityFrom(DamageSource.inWall, 1.0f);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else if (b && !this.worldObj.getWorldBorder().contains(this.getEntityBoundingBox())) {
                final double n = this.worldObj.getWorldBorder().getClosestDistance(this) + this.worldObj.getWorldBorder().getDamageBuffer();
                if (n < 0.0) {
                    this.attackEntityFrom(DamageSource.inWall, Math.max(" ".length(), MathHelper.floor_double(-n * this.worldObj.getWorldBorder().getDamageAmount())));
                }
            }
        }
        if (this.isImmuneToFire() || this.worldObj.isRemote) {
            this.extinguish();
        }
        int n2;
        if (b && ((EntityPlayer)this).capabilities.disableDamage) {
            n2 = " ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        if (this.isEntityAlive()) {
            if (this.isInsideOfMaterial(Material.water)) {
                if (!this.canBreatheUnderwater() && !this.isPotionActive(Potion.waterBreathing.id) && n3 == 0) {
                    this.setAir(this.decreaseAirSupply(this.getAir()));
                    if (this.getAir() == -(0x85 ^ 0x91)) {
                        this.setAir("".length());
                        int i = "".length();
                        "".length();
                        if (4 <= 2) {
                            throw null;
                        }
                        while (i < (0x71 ^ 0x79)) {
                            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextFloat() - this.rand.nextFloat()), this.posY + (this.rand.nextFloat() - this.rand.nextFloat()), this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()), this.motionX, this.motionY, this.motionZ, new int["".length()]);
                            ++i;
                        }
                        this.attackEntityFrom(DamageSource.drown, 2.0f);
                    }
                }
                if (!this.worldObj.isRemote && this.isRiding() && this.ridingEntity instanceof EntityLivingBase) {
                    this.mountEntity(null);
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
            }
            else {
                this.setAir(41 + 153 - 5 + 111);
            }
        }
        if (this.isEntityAlive() && this.isWet()) {
            this.extinguish();
        }
        this.prevCameraPitch = this.cameraPitch;
        if (this.hurtTime > 0) {
            this.hurtTime -= " ".length();
        }
        if (this.hurtResistantTime > 0 && !(this instanceof EntityPlayerMP)) {
            this.hurtResistantTime -= " ".length();
        }
        if (this.getHealth() <= 0.0f) {
            this.onDeathUpdate();
        }
        if (this.recentlyHit > 0) {
            this.recentlyHit -= " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            this.attackingPlayer = null;
        }
        if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
            this.lastAttacker = null;
        }
        if (this.entityLivingToAttack != null) {
            if (!this.entityLivingToAttack.isEntityAlive()) {
                this.setRevengeTarget(null);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else if (this.ticksExisted - this.revengeTimer > (0x4 ^ 0x60)) {
                this.setRevengeTarget(null);
            }
        }
        this.updatePotionEffects();
        this.prevMovedDistance = this.movedDistance;
        this.prevRenderYawOffset = this.renderYawOffset;
        this.prevRotationYawHead = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.worldObj.theProfiler.endSection();
    }
    
    public boolean canBreatheUnderwater() {
        return "".length() != 0;
    }
    
    protected String getFallSoundString(final int n) {
        String s;
        if (n > (0x9C ^ 0x98)) {
            s = EntityLivingBase.I[0x27 ^ 0x3C];
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            s = EntityLivingBase.I[0x34 ^ 0x28];
        }
        return s;
    }
    
    @Override
    public Vec3 getLookVec() {
        return this.getLook(1.0f);
    }
    
    public void heal(final float n) {
        final float health = this.getHealth();
        if (health > 0.0f) {
            this.setHealth(health + n);
        }
    }
    
    @Override
    public boolean isEntityAlive() {
        if (!this.isDead && this.getHealth() > 0.0f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void kill() {
        this.attackEntityFrom(DamageSource.outOfWorld, 4.0f);
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        if (this.ridingEntity != null && entity == null) {
            if (!this.worldObj.isRemote) {
                this.dismountEntity(this.ridingEntity);
            }
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            super.mountEntity(entity);
        }
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == "  ".length()) {
            this.limbSwingAmount = 1.5f;
            this.hurtResistantTime = this.maxHurtResistantTime;
            final int n = 0x7B ^ 0x71;
            this.maxHurtTime = n;
            this.hurtTime = n;
            this.attackedAtYaw = 0.0f;
            if (this.getHurtSound() != null) {
                this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.attackEntityFrom(DamageSource.generic, 0.0f);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (b == "   ".length()) {
            if (this.getDeathSound() != null) {
                this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.setHealth(0.0f);
            this.onDeath(DamageSource.generic);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public final void setArrowCountInEntity(final int n) {
        this.dataWatcher.updateObject(0x5A ^ 0x53, (byte)n);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setFloat(EntityLivingBase.I[0x49 ^ 0x4D], this.getHealth());
        nbtTagCompound.setShort(EntityLivingBase.I[0xA8 ^ 0xAD], (short)Math.ceil(this.getHealth()));
        nbtTagCompound.setShort(EntityLivingBase.I[0x35 ^ 0x33], (short)this.hurtTime);
        nbtTagCompound.setInteger(EntityLivingBase.I[0xD ^ 0xA], this.revengeTimer);
        nbtTagCompound.setShort(EntityLivingBase.I[0xCF ^ 0xC7], (short)this.deathTime);
        nbtTagCompound.setFloat(EntityLivingBase.I[0x17 ^ 0x1E], this.getAbsorptionAmount());
        final ItemStack[] inventory;
        final int length = (inventory = this.getInventory()).length;
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < length) {
            final ItemStack itemStack = inventory[i];
            if (itemStack != null) {
                this.attributeMap.removeAttributeModifiers(itemStack.getAttributeModifiers());
            }
            ++i;
        }
        nbtTagCompound.setTag(EntityLivingBase.I[0x6B ^ 0x61], SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));
        final ItemStack[] inventory2;
        final int length2 = (inventory2 = this.getInventory()).length;
        int j = "".length();
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (j < length2) {
            final ItemStack itemStack2 = inventory2[j];
            if (itemStack2 != null) {
                this.attributeMap.applyAttributeModifiers(itemStack2.getAttributeModifiers());
            }
            ++j;
        }
        if (!this.activePotionsMap.isEmpty()) {
            final NBTTagList list = new NBTTagList();
            final Iterator<PotionEffect> iterator = this.activePotionsMap.values().iterator();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().writeCustomPotionEffectToNBT(new NBTTagCompound()));
            }
            nbtTagCompound.setTag(EntityLivingBase.I[0x2E ^ 0x25], list);
        }
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        final PotionEffect activePotionEffect = this.getActivePotionEffect(Potion.jump);
        float n3;
        if (activePotionEffect != null) {
            n3 = activePotionEffect.getAmplifier() + " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n3 = 0.0f;
        }
        final int ceiling_float_int = MathHelper.ceiling_float_int((n - 3.0f - n3) * n2);
        if (ceiling_float_int > 0) {
            this.playSound(this.getFallSoundString(ceiling_float_int), 1.0f, 1.0f);
            this.attackEntityFrom(DamageSource.fall, ceiling_float_int);
            final Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ))).getBlock();
            if (block.getMaterial() != Material.air) {
                final Block.SoundType stepSound = block.stepSound;
                this.playSound(stepSound.getStepSound(), stepSound.getVolume() * 0.5f, stepSound.getFrequency() * 0.75f);
            }
        }
    }
    
    public boolean isPotionActive(final int n) {
        return this.activePotionsMap.containsKey(n);
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return "".length() != 0;
    }
    
    public boolean isPotionActive(final Potion potion) {
        return this.activePotionsMap.containsKey(potion.id);
    }
    
    protected float getSoundVolume() {
        return 1.0f;
    }
    
    public BaseAttributeMap getAttributeMap() {
        if (this.attributeMap == null) {
            this.attributeMap = new ServersideAttributeMap();
        }
        return this.attributeMap;
    }
    
    public PotionEffect getActivePotionEffect(final Potion potion) {
        return this.activePotionsMap.get(potion.id);
    }
    
    protected String getDeathSound() {
        return EntityLivingBase.I[0xB3 ^ 0xA9];
    }
    
    protected float applyArmorCalculations(final DamageSource damageSource, float n) {
        if (!damageSource.isUnblockable()) {
            final float n2 = n * ((0xA5 ^ 0xBC) - this.getTotalArmorValue());
            this.damageArmor(n);
            n = n2 / 25.0f;
        }
        return n;
    }
    
    @Override
    public abstract ItemStack[] getInventory();
    
    protected float getSoundPitch() {
        float n;
        if (this.isChild()) {
            n = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.5f;
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f;
        }
        return n;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (this.worldObj.isRemote) {
            return "".length() != 0;
        }
        this.entityAge = "".length();
        if (this.getHealth() <= 0.0f) {
            return "".length() != 0;
        }
        if (damageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
            return "".length() != 0;
        }
        if ((damageSource == DamageSource.anvil || damageSource == DamageSource.fallingBlock) && this.getEquipmentInSlot(0xB4 ^ 0xB0) != null) {
            this.getEquipmentInSlot(0x5 ^ 0x1).damageItem((int)(n * 4.0f + this.rand.nextFloat() * n * 2.0f), this);
            n *= 0.75f;
        }
        this.limbSwingAmount = 1.5f;
        int n2 = " ".length();
        if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0f) {
            if (n <= this.lastDamage) {
                return "".length() != 0;
            }
            this.damageEntity(damageSource, n - this.lastDamage);
            this.lastDamage = n;
            n2 = "".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            this.lastDamage = n;
            this.hurtResistantTime = this.maxHurtResistantTime;
            this.damageEntity(damageSource, n);
            final int n3 = 0xB0 ^ 0xBA;
            this.maxHurtTime = n3;
            this.hurtTime = n3;
        }
        this.attackedAtYaw = 0.0f;
        final Entity entity = damageSource.getEntity();
        if (entity != null) {
            if (entity instanceof EntityLivingBase) {
                this.setRevengeTarget((EntityLivingBase)entity);
            }
            if (entity instanceof EntityPlayer) {
                this.recentlyHit = (0x59 ^ 0x3D);
                this.attackingPlayer = (EntityPlayer)entity;
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else if (entity instanceof EntityWolf && ((EntityWolf)entity).isTamed()) {
                this.recentlyHit = (0x52 ^ 0x36);
                this.attackingPlayer = null;
            }
        }
        if (n2 != 0) {
            this.worldObj.setEntityState(this, (byte)"  ".length());
            if (damageSource != DamageSource.drown) {
                this.setBeenAttacked();
            }
            if (entity != null) {
                double n4 = entity.posX - this.posX;
                double n5 = entity.posZ - this.posZ;
                "".length();
                if (3 == 0) {
                    throw null;
                }
                while (n4 * n4 + n5 * n5 < 1.0E-4) {
                    n4 = (Math.random() - Math.random()) * 0.01;
                    n5 = (Math.random() - Math.random()) * 0.01;
                }
                this.attackedAtYaw = (float)(MathHelper.func_181159_b(n5, n4) * 180.0 / 3.141592653589793 - this.rotationYaw);
                this.knockBack(entity, n, n4, n5);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else {
                this.attackedAtYaw = (int)(Math.random() * 2.0) * (72 + 141 - 110 + 77);
            }
        }
        if (this.getHealth() <= 0.0f) {
            final String deathSound = this.getDeathSound();
            if (n2 != 0 && deathSound != null) {
                this.playSound(deathSound, this.getSoundVolume(), this.getSoundPitch());
            }
            this.onDeath(damageSource);
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            final String hurtSound = this.getHurtSound();
            if (n2 != 0 && hurtSound != null) {
                this.playSound(hurtSound, this.getSoundVolume(), this.getSoundPitch());
            }
        }
        return " ".length() != 0;
    }
    
    protected void damageEntity(final DamageSource damageSource, float n) {
        if (!this.isEntityInvulnerable(damageSource)) {
            n = this.applyArmorCalculations(damageSource, n);
            final float applyPotionDamageCalculations;
            n = (applyPotionDamageCalculations = this.applyPotionDamageCalculations(damageSource, n));
            n = Math.max(n - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (applyPotionDamageCalculations - n));
            if (n != 0.0f) {
                final float health = this.getHealth();
                this.setHealth(health - n);
                this.getCombatTracker().trackDamage(damageSource, health, n);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - n);
            }
        }
    }
    
    protected void damageArmor(final float n) {
    }
    
    protected boolean isPlayer() {
        return "".length() != 0;
    }
    
    @Override
    public void setSprinting(final boolean sprinting) {
        super.setSprinting(sprinting);
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (entityAttribute.getModifier(EntityLivingBase.sprintingSpeedBoostModifierUUID) != null) {
            entityAttribute.removeModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
        if (sprinting) {
            entityAttribute.applyModifier(EntityLivingBase.sprintingSpeedBoostModifier);
        }
    }
    
    @Override
    public void performHurtAnimation() {
        final int n = 0x25 ^ 0x2F;
        this.maxHurtTime = n;
        this.hurtTime = n;
        this.attackedAtYaw = 0.0f;
    }
    
    protected float getJumpUpwardsMotion() {
        return 0.42f;
    }
    
    public boolean isOnLadder() {
        final Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY), MathHelper.floor_double(this.posZ))).getBlock();
        if ((block == Blocks.ladder || block == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).isSpectator())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void swingItem() {
        if (!this.isSwingInProgress || this.swingProgressInt >= this.getArmSwingAnimationEnd() / "  ".length() || this.swingProgressInt < 0) {
            this.swingProgressInt = -" ".length();
            this.isSwingInProgress = (" ".length() != 0);
            if (this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S0BPacketAnimation(this, "".length()));
            }
        }
    }
    
    public float getSwingProgress(final float n) {
        float n2 = this.swingProgress - this.prevSwingProgress;
        if (n2 < 0.0f) {
            ++n2;
        }
        return this.prevSwingProgress + n2 * n;
    }
    
    protected void resetPotionEffectMetadata() {
        this.dataWatcher.updateObject(0x26 ^ 0x2E, (byte)"".length());
        this.dataWatcher.updateObject(0x19 ^ 0x1E, "".length());
    }
    
    protected void applyEntityAttributes() {
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
    }
    
    public void onItemPickup(final Entity entity, final int n) {
        if (!entity.isDead && !this.worldObj.isRemote) {
            final EntityTracker entityTracker = ((WorldServer)this.worldObj).getEntityTracker();
            if (entity instanceof EntityItem) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
            if (entity instanceof EntityArrow) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
            if (entity instanceof EntityXPOrb) {
                entityTracker.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
            }
        }
    }
    
    public void removePotionEffectClient(final int n) {
        this.activePotionsMap.remove(n);
    }
    
    public boolean attackEntityAsMob(final Entity lastAttacker) {
        this.setLastAttacker(lastAttacker);
        return "".length() != 0;
    }
    
    private int getArmSwingAnimationEnd() {
        int n;
        if (this.isPotionActive(Potion.digSpeed)) {
            n = (0x60 ^ 0x66) - (" ".length() + this.getActivePotionEffect(Potion.digSpeed).getAmplifier()) * " ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (this.isPotionActive(Potion.digSlowdown)) {
            n = (0x74 ^ 0x72) + (" ".length() + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * "  ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n = (0x5D ^ 0x5B);
        }
        return n;
    }
    
    public boolean isServerWorld() {
        int n;
        if (this.worldObj.isRemote) {
            n = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void onKillCommand() {
        this.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
    }
    
    @Override
    public boolean canBePushed() {
        int n;
        if (this.isDead) {
            n = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    protected void jump() {
        this.motionY = this.getJumpUpwardsMotion();
        if (this.isPotionActive(Potion.jump)) {
            this.motionY += (this.getActivePotionEffect(Potion.jump).getAmplifier() + " ".length()) * 0.1f;
        }
        if (this.isSprinting()) {
            final float n = this.rotationYaw * 0.017453292f;
            this.motionX -= MathHelper.sin(n) * 0.2f;
            this.motionZ += MathHelper.cos(n) * 0.2f;
        }
        this.isAirBorne = (" ".length() != 0);
    }
    
    public void setHealth(final float n) {
        this.dataWatcher.updateObject(0x1F ^ 0x19, MathHelper.clamp_float(n, 0.0f, this.getMaxHealth()));
    }
    
    protected void onChangedPotionEffect(final PotionEffect potionEffect, final boolean b) {
        this.potionsNeedUpdate = (" ".length() != 0);
        if (b && !this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
            Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    public void setJumping(final boolean isJumping) {
        this.isJumping = isJumping;
    }
    
    public Collection<PotionEffect> getActivePotionEffects() {
        return this.activePotionsMap.values();
    }
    
    public void knockBack(final Entity entity, final float n, final double n2, final double n3) {
        if (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
            this.isAirBorne = (" ".length() != 0);
            final float sqrt_double = MathHelper.sqrt_double(n2 * n2 + n3 * n3);
            final float n4 = 0.4f;
            this.motionX /= 2.0;
            this.motionY /= 2.0;
            this.motionZ /= 2.0;
            this.motionX -= n2 / sqrt_double * n4;
            this.motionY += n4;
            this.motionZ -= n3 / sqrt_double * n4;
            if (this.motionY > 0.4000000059604645) {
                this.motionY = 0.4000000059604645;
            }
        }
    }
    
    public void setAIMoveSpeed(final float landMovementFactor) {
        this.landMovementFactor = landMovementFactor;
    }
    
    public void removePotionEffect(final int n) {
        final PotionEffect potionEffect = this.activePotionsMap.remove(n);
        if (potionEffect != null) {
            this.onFinishedPotionEffect(potionEffect);
        }
    }
    
    public EntityLivingBase func_94060_bK() {
        EntityLivingBase entityLivingBase;
        if (this._combatTracker.func_94550_c() != null) {
            entityLivingBase = this._combatTracker.func_94550_c();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else if (this.attackingPlayer != null) {
            entityLivingBase = this.attackingPlayer;
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (this.entityLivingToAttack != null) {
            entityLivingBase = this.entityLivingToAttack;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            entityLivingBase = null;
        }
        return entityLivingBase;
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
        if (!this.isInWater()) {
            this.handleWaterMovement();
        }
        if (!this.worldObj.isRemote && this.fallDistance > 3.0f && b) {
            final IBlockState blockState = this.worldObj.getBlockState(blockPos);
            final Block block2 = blockState.getBlock();
            final float n2 = MathHelper.ceiling_float_int(this.fallDistance - 3.0f);
            if (block2.getMaterial() != Material.air) {
                double n3 = Math.min(0.2f + n2 / 15.0f, 10.0f);
                if (n3 > 2.5) {
                    n3 = 2.5;
                }
                final int n4 = (int)(150.0 * n3);
                final WorldServer worldServer = (WorldServer)this.worldObj;
                final EnumParticleTypes block_DUST = EnumParticleTypes.BLOCK_DUST;
                final double posX = this.posX;
                final double posY = this.posY;
                final double posZ = this.posZ;
                final int n5 = n4;
                final double n6 = 0.0;
                final double n7 = 0.0;
                final double n8 = 0.0;
                final double n9 = 0.15000000596046448;
                final int[] array = new int[" ".length()];
                array["".length()] = Block.getStateId(blockState);
                worldServer.spawnParticle(block_DUST, posX, posY, posZ, n5, n6, n7, n8, n9, array);
            }
        }
        super.updateFallState(n, b, block, blockPos);
    }
    
    public Team getTeam() {
        return this.worldObj.getScoreboard().getPlayersTeam(this.getUniqueID().toString());
    }
    
    public CombatTracker getCombatTracker() {
        return this._combatTracker;
    }
    
    protected String getHurtSound() {
        return EntityLivingBase.I[0x1 ^ 0x18];
    }
    
    @Override
    public void setPositionAndRotation2(final double newPosX, final double newPosY, final double newPosZ, final float n, final float n2, final int newPosRotationIncrements, final boolean b) {
        this.newPosX = newPosX;
        this.newPosY = newPosY;
        this.newPosZ = newPosZ;
        this.newRotationYaw = n;
        this.newRotationPitch = n2;
        this.newPosRotationIncrements = newPosRotationIncrements;
    }
    
    public int getTotalArmorValue() {
        int length = "".length();
        final ItemStack[] inventory;
        final int length2 = (inventory = this.getInventory()).length;
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < length2) {
            final ItemStack itemStack = inventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                length += ((ItemArmor)itemStack.getItem()).damageReduceAmount;
            }
            ++i;
        }
        return length;
    }
    
    protected void onFinishedPotionEffect(final PotionEffect potionEffect) {
        this.potionsNeedUpdate = (" ".length() != 0);
        if (!this.worldObj.isRemote) {
            Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
        }
    }
    
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        return "".length();
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            final int arrowCountInEntity = this.getArrowCountInEntity();
            if (arrowCountInEntity > 0) {
                if (this.arrowHitTimer <= 0) {
                    this.arrowHitTimer = (0x34 ^ 0x20) * ((0x9E ^ 0x80) - arrowCountInEntity);
                }
                this.arrowHitTimer -= " ".length();
                if (this.arrowHitTimer <= 0) {
                    this.setArrowCountInEntity(arrowCountInEntity - " ".length());
                }
            }
            int i = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (i < (0x80 ^ 0x85)) {
                final ItemStack itemStack = this.previousEquipment[i];
                final ItemStack equipmentInSlot = this.getEquipmentInSlot(i);
                if (!ItemStack.areItemStacksEqual(equipmentInSlot, itemStack)) {
                    ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S04PacketEntityEquipment(this.getEntityId(), i, equipmentInSlot));
                    if (itemStack != null) {
                        this.attributeMap.removeAttributeModifiers(itemStack.getAttributeModifiers());
                    }
                    if (equipmentInSlot != null) {
                        this.attributeMap.applyAttributeModifiers(equipmentInSlot.getAttributeModifiers());
                    }
                    final ItemStack[] previousEquipment = this.previousEquipment;
                    final int n = i;
                    ItemStack copy;
                    if (equipmentInSlot == null) {
                        copy = null;
                        "".length();
                        if (0 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        copy = equipmentInSlot.copy();
                    }
                    previousEquipment[n] = copy;
                }
                ++i;
            }
            if (this.ticksExisted % (0xF ^ 0x1B) == 0) {
                this.getCombatTracker().reset();
            }
        }
        this.onLivingUpdate();
        final double n2 = this.posX - this.prevPosX;
        final double n3 = this.posZ - this.prevPosZ;
        final float n4 = (float)(n2 * n2 + n3 * n3);
        float n5 = this.renderYawOffset;
        float n6 = 0.0f;
        this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
        float n7 = 0.0f;
        if (n4 > 0.0025000002f) {
            n7 = 1.0f;
            n6 = (float)Math.sqrt(n4) * 3.0f;
            n5 = (float)MathHelper.func_181159_b(n3, n2) * 180.0f / 3.1415927f - 90.0f;
        }
        if (this.swingProgress > 0.0f) {
            n5 = this.rotationYaw;
        }
        if (!this.onGround) {
            n7 = 0.0f;
        }
        this.onGroundSpeedFactor += (n7 - this.onGroundSpeedFactor) * 0.3f;
        this.worldObj.theProfiler.startSection(EntityLivingBase.I[0xBA ^ 0xA7]);
        final float func_110146_f = this.func_110146_f(n5, n6);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLivingBase.I[0x63 ^ 0x7D]);
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
            this.prevRotationYaw -= 360.0f;
        }
        "".length();
        if (false) {
            throw null;
        }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
            this.prevRotationYaw += 360.0f;
        }
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset < -180.0f) {
            this.prevRenderYawOffset -= 360.0f;
        }
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0f) {
            this.prevRenderYawOffset += 360.0f;
        }
        "".length();
        if (-1 == 2) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
            this.prevRotationPitch -= 360.0f;
        }
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
            this.prevRotationPitch += 360.0f;
        }
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (this.rotationYawHead - this.prevRotationYawHead < -180.0f) {
            this.prevRotationYawHead -= 360.0f;
        }
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (this.rotationYawHead - this.prevRotationYawHead >= 180.0f) {
            this.prevRotationYawHead += 360.0f;
        }
        this.worldObj.theProfiler.endSection();
        this.movedDistance += func_110146_f;
    }
    
    public final float getHealth() {
        return this.dataWatcher.getWatchableObjectFloat(0x52 ^ 0x54);
    }
    
    protected boolean isMovementBlocked() {
        if (this.getHealth() <= 0.0f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void dropEquipment(final boolean b, final int n) {
    }
    
    public void sendEndCombat() {
    }
    
    public IAttributeInstance getEntityAttribute(final IAttribute attribute) {
        return this.getAttributeMap().getAttributeInstance(attribute);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.setAbsorptionAmount(nbtTagCompound.getFloat(EntityLivingBase.I[0x96 ^ 0x9A]));
        if (nbtTagCompound.hasKey(EntityLivingBase.I[0x41 ^ 0x4C], 0x3F ^ 0x36) && this.worldObj != null && !this.worldObj.isRemote) {
            SharedMonsterAttributes.func_151475_a(this.getAttributeMap(), nbtTagCompound.getTagList(EntityLivingBase.I[0x28 ^ 0x26], 0x2A ^ 0x20));
        }
        if (nbtTagCompound.hasKey(EntityLivingBase.I[0x35 ^ 0x3A], 0x4C ^ 0x45)) {
            final NBTTagList tagList = nbtTagCompound.getTagList(EntityLivingBase.I[0x90 ^ 0x80], 0x30 ^ 0x3A);
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                final PotionEffect customPotionEffectFromNBT = PotionEffect.readCustomPotionEffectFromNBT(tagList.getCompoundTagAt(i));
                if (customPotionEffectFromNBT != null) {
                    this.activePotionsMap.put(customPotionEffectFromNBT.getPotionID(), customPotionEffectFromNBT);
                }
                ++i;
            }
        }
        if (nbtTagCompound.hasKey(EntityLivingBase.I[0x52 ^ 0x43], 0xEC ^ 0x8F)) {
            this.setHealth(nbtTagCompound.getFloat(EntityLivingBase.I[0x58 ^ 0x4A]));
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            final NBTBase tag = nbtTagCompound.getTag(EntityLivingBase.I[0xB2 ^ 0xA1]);
            if (tag == null) {
                this.setHealth(this.getMaxHealth());
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else if (tag.getId() == (0x8C ^ 0x89)) {
                this.setHealth(((NBTTagFloat)tag).getFloat());
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else if (tag.getId() == "  ".length()) {
                this.setHealth(((NBTTagShort)tag).getShort());
            }
        }
        this.hurtTime = nbtTagCompound.getShort(EntityLivingBase.I[0x9D ^ 0x89]);
        this.deathTime = nbtTagCompound.getShort(EntityLivingBase.I[0x53 ^ 0x46]);
        this.revengeTimer = nbtTagCompound.getInteger(EntityLivingBase.I[0xAE ^ 0xB8]);
    }
    
    public void onLivingUpdate() {
        if (this.jumpTicks > 0) {
            this.jumpTicks -= " ".length();
        }
        if (this.newPosRotationIncrements > 0) {
            final double n = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
            final double n2 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
            final double n3 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
            this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw) / this.newPosRotationIncrements);
            this.rotationPitch += (float)((this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
            this.newPosRotationIncrements -= " ".length();
            this.setPosition(n, n2, n3);
            this.setRotation(this.rotationYaw, this.rotationPitch);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (!this.isServerWorld()) {
            this.motionX *= 0.98;
            this.motionY *= 0.98;
            this.motionZ *= 0.98;
        }
        if (Math.abs(this.motionX) < 0.005) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) < 0.005) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) < 0.005) {
            this.motionZ = 0.0;
        }
        this.worldObj.theProfiler.startSection(EntityLivingBase.I[0xA2 ^ 0xBD]);
        if (this.isMovementBlocked()) {
            this.isJumping = ("".length() != 0);
            this.moveStrafing = 0.0f;
            this.moveForward = 0.0f;
            this.randomYawVelocity = 0.0f;
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else if (this.isServerWorld()) {
            this.worldObj.theProfiler.startSection(EntityLivingBase.I[0x4F ^ 0x6F]);
            this.updateEntityActionState();
            this.worldObj.theProfiler.endSection();
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLivingBase.I[0x7 ^ 0x26]);
        if (this.isJumping) {
            if (this.isInWater()) {
                this.updateAITick();
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            else if (this.isInLava()) {
                this.handleJumpLava();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else if (this.onGround && this.jumpTicks == 0) {
                this.jump();
                this.jumpTicks = (0x34 ^ 0x3E);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
        }
        else {
            this.jumpTicks = "".length();
        }
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLivingBase.I[0x31 ^ 0x13]);
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        this.randomYawVelocity *= 0.9f;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLivingBase.I[0x26 ^ 0x5]);
        if (!this.worldObj.isRemote) {
            this.collideWithNearbyEntities();
        }
        this.worldObj.theProfiler.endSection();
    }
    
    public Random getRNG() {
        return this.rand;
    }
    
    public boolean isPotionApplicable(final PotionEffect potionEffect) {
        if (this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            final int potionID = potionEffect.getPotionID();
            if (potionID == Potion.regeneration.id || potionID == Potion.poison.id) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    protected void collideWithNearbyEntities() {
        final List<Entity> entitiesInAABBexcluding = this.worldObj.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224), (Predicate<? super Entity>)Predicates.and((Predicate)EntitySelectors.NOT_SPECTATING, (Predicate)new Predicate<Entity>(this) {
            final EntityLivingBase this$0;
            
            public boolean apply(final Entity entity) {
                return entity.canBePushed();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
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
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }));
        if (!entitiesInAABBexcluding.isEmpty()) {
            int i = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (i < entitiesInAABBexcluding.size()) {
                this.collideWithEntity(entitiesInAABBexcluding.get(i));
                ++i;
            }
        }
    }
    
    public boolean canEntityBeSeen(final Entity entity) {
        if (this.worldObj.rayTraceBlocks(new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ), new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)) == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void updateArmSwingProgress() {
        final int armSwingAnimationEnd = this.getArmSwingAnimationEnd();
        if (this.isSwingInProgress) {
            this.swingProgressInt += " ".length();
            if (this.swingProgressInt >= armSwingAnimationEnd) {
                this.swingProgressInt = "".length();
                this.isSwingInProgress = ("".length() != 0);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
        }
        else {
            this.swingProgressInt = "".length();
        }
        this.swingProgress = this.swingProgressInt / armSwingAnimationEnd;
    }
}
