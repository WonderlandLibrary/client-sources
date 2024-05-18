package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;

public class EntityPig extends EntityAnimal
{
    private static final String[] I;
    private final EntityAIControlledByPlayer aiControlledByPlayer;
    
    @Override
    public void fall(final float n, final float n2) {
        super.fall(n, n2);
        if (n > 5.0f && this.riddenByEntity instanceof EntityPlayer) {
            ((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
        }
    }
    
    public boolean getSaddled() {
        if ((this.dataWatcher.getWatchableObjectByte(0x2F ^ 0x3F) & " ".length()) != 0x0) {
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
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public EntityPig createChild(final EntityAgeable entityAgeable) {
        return new EntityPig(this.worldObj);
    }
    
    public EntityAIControlledByPlayer getAIControlledByPlayer() {
        return this.aiControlledByPlayer;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x24 ^ 0x34, (byte)"".length());
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        if (super.interact(entityPlayer)) {
            return " ".length() != 0;
        }
        if (!this.getSaddled() || this.worldObj.isRemote || (this.riddenByEntity != null && this.riddenByEntity != entityPlayer)) {
            return "".length() != 0;
        }
        entityPlayer.mountEntity(this);
        return " ".length() != 0;
    }
    
    public void setSaddled(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(0x22 ^ 0x32, (byte)" ".length());
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0xB8 ^ 0xA8, (byte)"".length());
        }
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() == Items.carrot) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected Item getDropItem() {
        Item item;
        if (this.isBurning()) {
            item = Items.cooked_porkchop;
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            item = Items.porkchop;
        }
        return item;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityPig.I["   ".length()];
    }
    
    public EntityPig(final World world) {
        super(world);
        this.setSize(0.9f, 0.9f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask(" ".length(), new EntityAIPanic(this, 1.25));
        this.tasks.addTask("  ".length(), this.aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3f));
        this.tasks.addTask("   ".length(), new EntityAIMate(this, 1.0));
        this.tasks.addTask(0xA7 ^ 0xA3, new EntityAITempt(this, 1.2, Items.carrot_on_a_stick, (boolean)("".length() != 0)));
        this.tasks.addTask(0x2C ^ 0x28, new EntityAITempt(this, 1.2, Items.carrot, (boolean)("".length() != 0)));
        this.tasks.addTask(0x4C ^ 0x49, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(0xA2 ^ 0xA4, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0x73 ^ 0x74, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(0x57 ^ 0x5F, new EntityAILookIdle(this));
    }
    
    @Override
    public boolean canBeSteered() {
        final ItemStack heldItem = ((EntityPlayer)this.riddenByEntity).getHeldItem();
        if (heldItem != null && heldItem.getItem() == Items.carrot_on_a_stick) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    static {
        I();
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        if (!this.worldObj.isRemote && !this.isDead) {
            final EntityPigZombie entityPigZombie = new EntityPigZombie(this.worldObj);
            entityPigZombie.setCurrentItemOrArmor("".length(), new ItemStack(Items.golden_sword));
            entityPigZombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entityPigZombie.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                entityPigZombie.setCustomNameTag(this.getCustomNameTag());
                entityPigZombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.worldObj.spawnEntityInWorld(entityPigZombie);
            this.setDead();
        }
    }
    
    private static void I() {
        (I = new String[0x21 ^ 0x27])["".length()] = I(";\u0015/\u0006\u0003\r", "htKbo");
        EntityPig.I[" ".length()] = I(":'+4\u0004\f", "iFOPh");
        EntityPig.I["  ".length()] = I("&\u001f4z\u0014\"\u0017x'\u00052", "KpVTd");
        EntityPig.I["   ".length()] = I("\"<\u0011l\u001c&4]1\r6", "OSsBl");
        EntityPig.I[0x7 ^ 0x3] = I("\u001d.\u0001C4\u0019&M\t!\u00115\u000b", "pAcmD");
        EntityPig.I[0xC2 ^ 0xC7] = I("\u0006\u0001:H2\u0002\tv\u00156\u000e\u001e", "knXfB");
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntityPig.I[0x6B ^ 0x6E], 0.15f, 1.0f);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityPig.I["".length()], this.getSaddled());
    }
    
    @Override
    protected String getDeathSound() {
        return EntityPig.I[0x81 ^ 0x85];
    }
    
    @Override
    protected String getLivingSound() {
        return EntityPig.I["  ".length()];
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setSaddled(nbtTagCompound.getBoolean(EntityPig.I[" ".length()]));
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt("   ".length()) + " ".length() + this.rand.nextInt(" ".length() + n);
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < n2) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_porkchop, " ".length());
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                this.dropItem(Items.porkchop, " ".length());
            }
            ++i;
        }
        if (this.getSaddled()) {
            this.dropItem(Items.saddle, " ".length());
        }
    }
}
