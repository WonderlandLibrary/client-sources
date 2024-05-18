package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;

public class EntityItem extends Entity
{
    private static final Logger logger;
    private String owner;
    private String thrower;
    private int delayBeforeCanPickup;
    private int age;
    private int health;
    private static final String[] I;
    public float hoverStart;
    
    public void setThrower(final String thrower) {
        this.thrower = thrower;
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(0x39 ^ 0x33, 0x48 ^ 0x4D);
    }
    
    @Override
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }
            this.inWater = (" ".length() != 0);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            this.inWater = ("".length() != 0);
        }
        return this.inWater;
    }
    
    @Override
    public void onUpdate() {
        if (this.getEntityItem() == null) {
            this.setDead();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            super.onUpdate();
            if (this.delayBeforeCanPickup > 0 && this.delayBeforeCanPickup != 16701 + 23535 - 23809 + 16340) {
                this.delayBeforeCanPickup -= " ".length();
            }
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0, this.posZ);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            int n;
            if ((int)this.prevPosX == (int)this.posX && (int)this.prevPosY == (int)this.posY && (int)this.prevPosZ == (int)this.posZ) {
                n = "".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            if (n != 0 || this.ticksExisted % (0xB6 ^ 0xAF) == 0) {
                if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
                    this.motionY = 0.20000000298023224;
                    this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.playSound(EntityItem.I["".length()], 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
                }
                if (!this.worldObj.isRemote) {
                    this.searchForOtherItemsNearby();
                }
            }
            float n2 = 0.98f;
            if (this.onGround) {
                n2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - " ".length(), MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98f;
            }
            this.motionX *= n2;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= n2;
            if (this.onGround) {
                this.motionY *= -0.5;
            }
            if (this.age != -(31376 + 9079 - 33118 + 25431)) {
                this.age += " ".length();
            }
            this.handleWaterMovement();
            if (!this.worldObj.isRemote && this.age >= 4225 + 2405 - 1511 + 881) {
                this.setDead();
            }
        }
    }
    
    public void setInfinitePickupDelay() {
        this.delayBeforeCanPickup = 23789 + 16077 - 9616 + 2517;
    }
    
    @Override
    public boolean canAttackWithItem() {
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean cannotPickup() {
        if (this.delayBeforeCanPickup > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.health = (nbtTagCompound.getShort(EntityItem.I[0x45 ^ 0x42]) & 160 + 200 - 273 + 168);
        this.age = nbtTagCompound.getShort(EntityItem.I[0x54 ^ 0x5C]);
        if (nbtTagCompound.hasKey(EntityItem.I[0x17 ^ 0x1E])) {
            this.delayBeforeCanPickup = nbtTagCompound.getShort(EntityItem.I[0x2C ^ 0x26]);
        }
        if (nbtTagCompound.hasKey(EntityItem.I[0x3D ^ 0x36])) {
            this.owner = nbtTagCompound.getString(EntityItem.I[0x95 ^ 0x99]);
        }
        if (nbtTagCompound.hasKey(EntityItem.I[0x83 ^ 0x8E])) {
            this.thrower = nbtTagCompound.getString(EntityItem.I[0x89 ^ 0x87]);
        }
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(EntityItem.I[0x76 ^ 0x79])));
        if (this.getEntityItem() == null) {
            this.setDead();
        }
    }
    
    public ItemStack getEntityItem() {
        final ItemStack watchableObjectItemStack = this.getDataWatcher().getWatchableObjectItemStack(0xCF ^ 0xC5);
        if (watchableObjectItemStack == null) {
            if (this.worldObj != null) {
                EntityItem.logger.error(EntityItem.I[0x20 ^ 0x32] + this.getEntityId() + EntityItem.I[0x3B ^ 0x28]);
            }
            return new ItemStack(Blocks.stone);
        }
        return watchableObjectItemStack;
    }
    
    public void setEntityItemStack(final ItemStack itemStack) {
        this.getDataWatcher().updateObject(0xA ^ 0x0, itemStack);
        this.getDataWatcher().setObjectWatched(0x18 ^ 0x12);
    }
    
    public int getAge() {
        return this.age;
    }
    
    public void setNoPickupDelay() {
        this.delayBeforeCanPickup = "".length();
    }
    
    public void func_174870_v() {
        this.setInfinitePickupDelay();
        this.age = 3030 + 4060 - 5473 + 4382;
    }
    
    public void setPickupDelay(final int delayBeforeCanPickup) {
        this.delayBeforeCanPickup = delayBeforeCanPickup;
    }
    
    public void setDefaultPickupDelay() {
        this.delayBeforeCanPickup = (0x10 ^ 0x1A);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (this.getEntityItem() != null && this.getEntityItem().getItem() == Items.nether_star && damageSource.isExplosion()) {
            return "".length() != 0;
        }
        this.setBeenAttacked();
        this.health -= (int)n;
        if (this.health <= 0) {
            this.setDead();
        }
        return "".length() != 0;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort(EntityItem.I[" ".length()], (byte)this.health);
        nbtTagCompound.setShort(EntityItem.I["  ".length()], (short)this.age);
        nbtTagCompound.setShort(EntityItem.I["   ".length()], (short)this.delayBeforeCanPickup);
        if (this.getThrower() != null) {
            nbtTagCompound.setString(EntityItem.I[0x38 ^ 0x3C], this.thrower);
        }
        if (this.getOwner() != null) {
            nbtTagCompound.setString(EntityItem.I[0x54 ^ 0x51], this.owner);
        }
        if (this.getEntityItem() != null) {
            nbtTagCompound.setTag(EntityItem.I[0x90 ^ 0x96], this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public String getName() {
        String s;
        if (this.hasCustomName()) {
            s = this.getCustomNameTag();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            s = StatCollector.translateToLocal(EntityItem.I[0x2C ^ 0x3D] + this.getEntityItem().getUnlocalizedName());
        }
        return s;
    }
    
    @Override
    protected void dealFireDamage(final int n) {
        this.attackEntityFrom(DamageSource.inFire, n);
    }
    
    public EntityItem(final World world, final double n, final double n2, final double n3, final ItemStack entityItemStack) {
        this(world, n, n2, n3);
        this.setEntityItemStack(entityItemStack);
    }
    
    private boolean combineItems(final EntityItem entityItem) {
        if (entityItem == this) {
            return "".length() != 0;
        }
        if (!entityItem.isEntityAlive() || !this.isEntityAlive()) {
            return "".length() != 0;
        }
        final ItemStack entityItem2 = this.getEntityItem();
        final ItemStack entityItem3 = entityItem.getEntityItem();
        if (this.delayBeforeCanPickup == 16153 + 29835 - 43323 + 30102 || entityItem.delayBeforeCanPickup == 13490 + 6393 - 16697 + 29581) {
            return "".length() != 0;
        }
        if (this.age == -(8847 + 27129 - 34527 + 31319) || entityItem.age == -(10540 + 30634 - 39193 + 30787)) {
            return "".length() != 0;
        }
        if (entityItem3.getItem() != entityItem2.getItem()) {
            return "".length() != 0;
        }
        if (entityItem3.hasTagCompound() ^ entityItem2.hasTagCompound()) {
            return "".length() != 0;
        }
        if (entityItem3.hasTagCompound() && !entityItem3.getTagCompound().equals(entityItem2.getTagCompound())) {
            return "".length() != 0;
        }
        if (entityItem3.getItem() == null) {
            return "".length() != 0;
        }
        if (entityItem3.getItem().getHasSubtypes() && entityItem3.getMetadata() != entityItem2.getMetadata()) {
            return "".length() != 0;
        }
        if (entityItem3.stackSize < entityItem2.stackSize) {
            return entityItem.combineItems(this);
        }
        if (entityItem3.stackSize + entityItem2.stackSize > entityItem3.getMaxStackSize()) {
            return "".length() != 0;
        }
        final ItemStack itemStack = entityItem3;
        itemStack.stackSize += entityItem2.stackSize;
        entityItem.delayBeforeCanPickup = Math.max(entityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
        entityItem.age = Math.min(entityItem.age, this.age);
        entityItem.setEntityItemStack(entityItem3);
        this.setDead();
        return " ".length() != 0;
    }
    
    public EntityItem(final World world) {
        super(world);
        this.health = (0x61 ^ 0x64);
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.setEntityItemStack(new ItemStack(Blocks.air, "".length()));
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    public void setOwner(final String owner) {
        this.owner = owner;
    }
    
    @Override
    public void travelToDimension(final int n) {
        super.travelToDimension(n);
        if (!this.worldObj.isRemote) {
            this.searchForOtherItemsNearby();
        }
    }
    
    private static void I() {
        (I = new String[0x4A ^ 0x5E])["".length()] = I("\u0014%\u0007'\u001e\u000bj\u000f*\u000b\u001c", "fDiCq");
        EntityItem.I[" ".length()] = I("\u001947\u0007\u001b9", "QQVko");
        EntityItem.I["  ".length()] = I(".!\u0001", "oFdtq");
        EntityItem.I["   ".length()] = I("&$38\u0017\u0006\t5?\u0003\u000f", "vMPSb");
        EntityItem.I[0x4D ^ 0x49] = I("&<\u0017\f!\u0017&", "rTecV");
        EntityItem.I[0x61 ^ 0x64] = I("#\u00068#+", "lqVFY");
        EntityItem.I[0xBE ^ 0xB8] = I(".\u001e\u001d\b", "gjxeQ");
        EntityItem.I[0x7D ^ 0x7A] = I("\"7\u0018\n1\u0002", "jRyfE");
        EntityItem.I[0xC ^ 0x4] = I(",-)", "mJLSw");
        EntityItem.I[0x99 ^ 0x90] = I(">\u00180.\u001b\u001e56)\u000f\u0017", "nqSEn");
        EntityItem.I[0x56 ^ 0x5C] = I("<\u0000\u0012\u001c\u0013\u001c-\u0014\u001b\u0007\u0015", "liqwf");
        EntityItem.I[0xA4 ^ 0xAF] = I("#\u0005\"7'", "lrLRU");
        EntityItem.I[0x68 ^ 0x64] = I("\u0003#$\u0002;", "LTJgI");
        EntityItem.I[0x3F ^ 0x32] = I("\";$;$\u0013!", "vSVTS");
        EntityItem.I[0x37 ^ 0x39] = I("2\u001b\u0000\u001e$\u0003\u0001", "fsrqS");
        EntityItem.I[0x80 ^ 0x8F] = I("&\u000e1/", "ozTBb");
        EntityItem.I[0x10 ^ 0x0] = I("*1 \u0016\u00185~>\u001d\u0007", "XPNrw");
        EntityItem.I[0x3B ^ 0x2A] = I("\u0004=\u001d\fM", "mIxac");
        EntityItem.I[0x85 ^ 0x97] = I("\u000b\u0005\u0016\u000fI'\u001f\u0007\u000b\u001d;Q", "Bqsbi");
        EntityItem.I[0x70 ^ 0x63] = I("b\u0003\u0016$E,\u0004W>\u0011'\u0006Hv", "BkwWe");
    }
    
    public EntityItem(final World world, final double n, final double n2, final double n3) {
        super(world);
        this.health = (0xE ^ 0xB);
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.setPosition(n, n2, n3);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.motionY = 0.20000000298023224;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }
    
    public void setNoDespawn() {
        this.age = -(1932 + 3596 - 2623 + 3095);
    }
    
    private void searchForOtherItemsNearby() {
        final Iterator<EntityItem> iterator = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityItem>)EntityItem.class, this.getEntityBoundingBox().expand(0.5, 0.0, 0.5)).iterator();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.combineItems(iterator.next());
        }
    }
    
    public String getThrower() {
        return this.thrower;
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (!this.worldObj.isRemote) {
            final ItemStack entityItem = this.getEntityItem();
            final int stackSize = entityItem.stackSize;
            if (this.delayBeforeCanPickup == 0 && (this.owner == null || 3663 + 2895 - 1865 + 1307 - this.age <= 158 + 110 - 207 + 139 || this.owner.equals(entityPlayer.getName())) && entityPlayer.inventory.addItemStackToInventory(entityItem)) {
                if (entityItem.getItem() == Item.getItemFromBlock(Blocks.log)) {
                    entityPlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (entityItem.getItem() == Item.getItemFromBlock(Blocks.log2)) {
                    entityPlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (entityItem.getItem() == Items.leather) {
                    entityPlayer.triggerAchievement(AchievementList.killCow);
                }
                if (entityItem.getItem() == Items.diamond) {
                    entityPlayer.triggerAchievement(AchievementList.diamonds);
                }
                if (entityItem.getItem() == Items.blaze_rod) {
                    entityPlayer.triggerAchievement(AchievementList.blazeRod);
                }
                if (entityItem.getItem() == Items.diamond && this.getThrower() != null) {
                    final EntityPlayer playerEntityByName = this.worldObj.getPlayerEntityByName(this.getThrower());
                    if (playerEntityByName != null && playerEntityByName != entityPlayer) {
                        playerEntityByName.triggerAchievement(AchievementList.diamondsToYou);
                    }
                }
                if (!this.isSilent()) {
                    this.worldObj.playSoundAtEntity(entityPlayer, EntityItem.I[0x76 ^ 0x66], 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                }
                entityPlayer.onItemPickup(this, stackSize);
                if (entityItem.stackSize <= 0) {
                    this.setDead();
                }
            }
        }
    }
    
    public void setAgeToCreativeDespawnTime() {
        this.age = 1800 + 2729 - 3017 + 3288;
    }
    
    public String getOwner() {
        return this.owner;
    }
}
