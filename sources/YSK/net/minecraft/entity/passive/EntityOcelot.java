package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import com.google.common.base.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityOcelot extends EntityTameable
{
    private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
    private EntityAITempt aiTempt;
    private static final String[] I;
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x8F ^ 0x9D, (byte)"".length());
    }
    
    static {
        I();
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public EntityOcelot createChild(final EntityAgeable entityAgeable) {
        final EntityOcelot entityOcelot = new EntityOcelot(this.worldObj);
        if (this.isTamed()) {
            entityOcelot.setOwnerId(this.getOwnerId());
            entityOcelot.setTamed(" ".length() != 0);
            entityOcelot.setTameSkin(this.getTameSkin());
        }
        return entityOcelot;
    }
    
    @Override
    protected void setupTamedAI() {
        if (this.avoidEntity == null) {
            this.avoidEntity = new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 16.0f, 0.8, 1.33);
        }
        this.tasks.removeTask(this.avoidEntity);
        if (!this.isTamed()) {
            this.tasks.addTask(0x59 ^ 0x5D, this.avoidEntity);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setTameSkin(nbtTagCompound.getInteger(EntityOcelot.I[" ".length()]));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return "".length() != 0;
        }
        if (!this.isTamed()) {
            return "".length() != 0;
        }
        if (!(entityAnimal instanceof EntityOcelot)) {
            return "".length() != 0;
        }
        final EntityOcelot entityOcelot = (EntityOcelot)entityAnimal;
        int n;
        if (!entityOcelot.isTamed()) {
            n = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (this.isInLove() && entityOcelot.isInLove()) {
            n = " ".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (this.worldObj.rand.nextInt("   ".length()) != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getName() {
        String s;
        if (this.hasCustomName()) {
            s = this.getCustomNameTag();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (this.isTamed()) {
            s = StatCollector.translateToLocal(EntityOcelot.I[0x34 ^ 0x3C]);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            s = super.getName();
        }
        return s;
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (this.isOwner(entityPlayer) && !this.worldObj.isRemote && !this.isBreedingItem(currentItem)) {
                final EntityAISit aiSit = this.aiSit;
                int sitting;
                if (this.isSitting()) {
                    sitting = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    sitting = " ".length();
                }
                aiSit.setSitting(sitting != 0);
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
        }
        else if (this.aiTempt.isRunning() && currentItem != null && currentItem.getItem() == Items.fish && entityPlayer.getDistanceSqToEntity(this) < 9.0) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = currentItem;
                itemStack.stackSize -= " ".length();
            }
            if (currentItem.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt("   ".length()) == 0) {
                    this.setTamed(" ".length() != 0);
                    this.setTameSkin(" ".length() + this.worldObj.rand.nextInt("   ".length()));
                    this.setOwnerId(entityPlayer.getUniqueID().toString());
                    this.playTameEffect(" ".length() != 0);
                    this.aiSit.setSitting(" ".length() != 0);
                    this.worldObj.setEntityState(this, (byte)(0x8F ^ 0x88));
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    this.playTameEffect("".length() != 0);
                    this.worldObj.setEntityState(this, (byte)(0x79 ^ 0x7F));
                }
            }
            return " ".length() != 0;
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
    }
    
    public void updateAITasks() {
        if (this.getMoveHelper().isUpdating()) {
            final double speed = this.getMoveHelper().getSpeed();
            if (speed == 0.6) {
                this.setSneaking(" ".length() != 0);
                this.setSprinting("".length() != 0);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else if (speed == 1.33) {
                this.setSneaking("".length() != 0);
                this.setSprinting(" ".length() != 0);
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                this.setSneaking("".length() != 0);
                this.setSprinting("".length() != 0);
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
        }
        else {
            this.setSneaking("".length() != 0);
            this.setSprinting("".length() != 0);
        }
    }
    
    public int getTameSkin() {
        return this.dataWatcher.getWatchableObjectByte(0x5 ^ 0x17);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityOcelot.I["".length()], this.getTameSkin());
    }
    
    @Override
    public void setTamed(final boolean tamed) {
        super.setTamed(tamed);
    }
    
    @Override
    protected Item getDropItem() {
        return Items.leather;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityOcelot.I[0xB1 ^ 0xB7];
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    public EntityOcelot(final World world) {
        super(world);
        this.setSize(0.6f, 0.7f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), this.aiSit);
        this.tasks.addTask("   ".length(), this.aiTempt = new EntityAITempt(this, 0.6, Items.fish, " ".length() != 0));
        this.tasks.addTask(0xA7 ^ 0xA2, new EntityAIFollowOwner(this, 1.0, 10.0f, 5.0f));
        this.tasks.addTask(0x13 ^ 0x15, new EntityAIOcelotSit(this, 0.8));
        this.tasks.addTask(0x34 ^ 0x33, new EntityAILeapAtTarget(this, 0.3f));
        this.tasks.addTask(0x7A ^ 0x72, new EntityAIOcelotAttack(this));
        this.tasks.addTask(0x83 ^ 0x8A, new EntityAIMate(this, 0.8));
        this.tasks.addTask(0x9B ^ 0x91, new EntityAIWander(this, 0.8));
        this.tasks.addTask(0x34 ^ 0x3F, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.targetTasks.addTask(" ".length(), new EntityAITargetNonTamed<Object>(this, EntityChicken.class, (boolean)("".length() != 0), null));
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() == Items.fish) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isNotColliding() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
            final BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
            if (blockPos.getY() < this.worldObj.func_181545_F()) {
                return "".length() != 0;
            }
            final Block block = this.worldObj.getBlockState(blockPos.down()).getBlock();
            if (block == Blocks.grass || block.getMaterial() == Material.leaves) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    protected String getDeathSound() {
        return EntityOcelot.I[0x1C ^ 0x1B];
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        this.aiSit.setSitting("".length() != 0);
        return super.attackEntityFrom(damageSource, n);
    }
    
    private static void I() {
        (I = new String[0x38 ^ 0x31])["".length()] = I("5\u000b\f\u001a8\u0006\u000f", "vjxNA");
        EntityOcelot.I[" ".length()] = I("/\u0013=5 \u001c\u0017", "lrIaY");
        EntityOcelot.I["  ".length()] = I("&<)a(*'e?>9!", "KSKOK");
        EntityOcelot.I["   ".length()] = I("*<\u0011C\f&']\u001d\u001a5!\u0016\u0002\u0018", "GSsmo");
        EntityOcelot.I[0x3A ^ 0x3E] = I("/%$v4#>h52-=", "BJFXW");
        EntityOcelot.I[0xA1 ^ 0xA4] = I("", "uNXae");
        EntityOcelot.I[0xA2 ^ 0xA4] = I("\u000b\u001b'V\n\u0007\u0000k\u0010\u0000\u0012\u0000", "ftExi");
        EntityOcelot.I[0x68 ^ 0x6F] = I("8\u001f\bX-4\u0004D\u001e'!\u0004", "UpjvN");
        EntityOcelot.I[0x47 ^ 0x4F] = I("\b\u0007\u0012\u0003\u0011\u0014G%\u000b\u0011C\u0007\u0007\u0007\u0000", "mifje");
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0f);
    }
    
    @Override
    protected String getLivingSound() {
        String s;
        if (this.isTamed()) {
            if (this.isInLove()) {
                s = EntityOcelot.I["  ".length()];
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else if (this.rand.nextInt(0x91 ^ 0x95) == 0) {
                s = EntityOcelot.I["   ".length()];
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                s = EntityOcelot.I[0x38 ^ 0x3C];
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
        }
        else {
            s = EntityOcelot.I[0x89 ^ 0x8C];
        }
        return s;
    }
    
    public void setTameSkin(final int n) {
        this.dataWatcher.updateObject(0xC ^ 0x1E, (byte)n);
    }
    
    @Override
    protected boolean canDespawn() {
        if (!this.isTamed() && this.ticksExisted > 745 + 235 + 828 + 592) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        if (this.worldObj.rand.nextInt(0x1B ^ 0x1C) == 0) {
            int i = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
            while (i < "  ".length()) {
                final EntityOcelot entityOcelot = new EntityOcelot(this.worldObj);
                entityOcelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                entityOcelot.setGrowingAge(-(4734 + 14445 + 1702 + 3119));
                this.worldObj.spawnEntityInWorld(entityOcelot);
                ++i;
            }
        }
        return onInitialSpawn;
    }
}
