package net.minecraft.entity.passive;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EntityChicken extends EntityAnimal
{
    public float destPos;
    public float wingRotDelta;
    public float wingRotation;
    public int timeUntilNextEgg;
    public float field_70884_g;
    public float field_70888_h;
    public boolean chickenJockey;
    private static final String[] I;
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntityChicken.I[0xA9 ^ 0xAD], 0.15f, 1.0f);
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
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt("   ".length()) + this.rand.nextInt(" ".length() + n);
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < n2) {
            this.dropItem(Items.feather, " ".length());
            ++i;
        }
        if (this.isBurning()) {
            this.dropItem(Items.cooked_chicken, " ".length());
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            this.dropItem(Items.chicken, " ".length());
        }
    }
    
    static {
        I();
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.chickenJockey = nbtTagCompound.getBoolean(EntityChicken.I[0x91 ^ 0x94]);
        if (nbtTagCompound.hasKey(EntityChicken.I[0x7A ^ 0x7C])) {
            this.timeUntilNextEgg = nbtTagCompound.getInteger(EntityChicken.I[0xA8 ^ 0xAF]);
        }
    }
    
    public EntityChicken(final World world) {
        super(world);
        this.wingRotDelta = 1.0f;
        this.setSize(0.4f, 0.7f);
        this.timeUntilNextEgg = this.rand.nextInt(2351 + 205 + 2878 + 566) + (2278 + 4512 - 2656 + 1866);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask(" ".length(), new EntityAIPanic(this, 1.4));
        this.tasks.addTask("  ".length(), new EntityAIMate(this, 1.0));
        this.tasks.addTask("   ".length(), new EntityAITempt(this, 1.0, Items.wheat_seeds, (boolean)("".length() != 0)));
        this.tasks.addTask(0x2B ^ 0x2F, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(0xA9 ^ 0xAC, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0x2A ^ 0x2C, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(0xC0 ^ 0xC7, new EntityAILookIdle(this));
    }
    
    @Override
    public float getEyeHeight() {
        return this.height;
    }
    
    @Override
    protected boolean canDespawn() {
        if (this.isChickenJockey() && this.riddenByEntity == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        final float sin = MathHelper.sin(this.renderYawOffset * 3.1415927f / 180.0f);
        final float cos = MathHelper.cos(this.renderYawOffset * 3.1415927f / 180.0f);
        final float n = 0.1f;
        this.riddenByEntity.setPosition(this.posX + n * sin, this.posY + this.height * 0.5f + this.riddenByEntity.getYOffset() + 0.0f, this.posZ - n * cos);
        if (this.riddenByEntity instanceof EntityLivingBase) {
            ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
        }
    }
    
    @Override
    protected Item getDropItem() {
        return Items.feather;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityChicken.I[0x4 ^ 0xC], this.chickenJockey);
        nbtTagCompound.setInteger(EntityChicken.I[0xBB ^ 0xB2], this.timeUntilNextEgg);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    @Override
    protected String getDeathSound() {
        return EntityChicken.I["   ".length()];
    }
    
    @Override
    public EntityChicken createChild(final EntityAgeable entityAgeable) {
        return new EntityChicken(this.worldObj);
    }
    
    private static void I() {
        (I = new String[0x1C ^ 0x16])["".length()] = I("49\u0018]\f1?\u0019\u0018\n7x\n\u001f\u0000)", "YVzso");
        EntityChicken.I[" ".length()] = I(" \n\u0014w\u0007%\f\u00152\u0001#K\u00058\u001d", "MevYd");
        EntityChicken.I["  ".length()] = I("\u001a\u001c&A \u001f\u001a'\u0004&\u0019],\u001a1\u0003", "wsDoC");
        EntityChicken.I["   ".length()] = I("\u001a-\u0003C$\u001f+\u0002\u0006\"\u0019l\t\u00185\u0003", "wBamG");
        EntityChicken.I[0x9F ^ 0x9B] = I("8\u001d\fi6=\u001b\r,0;\\\u001d30%", "UrnGU");
        EntityChicken.I[0x5F ^ 0x5A] = I("-\u0011\t\u0011\u0003\u0007\t/\u0017 \u000b\u0001!\u001c\u0013", "dbJyj");
        EntityChicken.I[0x4 ^ 0x2] = I("\u0002!\u001e4%>\u0012\u0010\u0015!", "GFyxD");
        EntityChicken.I[0xD ^ 0xA] = I("3\u000b\u0016% \u000f8\u0018\u0004$", "vlqiA");
        EntityChicken.I[0x22 ^ 0x2A] = I("\u000e\u001a$\u0003;$\u0002\u0002\u0005\u0018(\n\f\u000e+", "GigkR");
        EntityChicken.I[0x6E ^ 0x67] = I("\u000f6\u0010)\u00073\u0005\u001e\b\u0003", "JQwef");
    }
    
    public boolean isChickenJockey() {
        return this.chickenJockey;
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemStack) {
        if (itemStack != null && itemStack.getItem() == Items.wheat_seeds) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        int experiencePoints;
        if (this.isChickenJockey()) {
            experiencePoints = (0x6 ^ 0xC);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            experiencePoints = super.getExperiencePoints(entityPlayer);
        }
        return experiencePoints;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityChicken.I["  ".length()];
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    protected String getLivingSound() {
        return EntityChicken.I[" ".length()];
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.field_70888_h = this.wingRotation;
        this.field_70884_g = this.destPos;
        final double n = this.destPos;
        int n2;
        if (this.onGround) {
            n2 = -" ".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n2 = (0x1F ^ 0x1B);
        }
        this.destPos = (float)(n + n2 * 0.3);
        this.destPos = MathHelper.clamp_float(this.destPos, 0.0f, 1.0f);
        if (!this.onGround && this.wingRotDelta < 1.0f) {
            this.wingRotDelta = 1.0f;
        }
        this.wingRotDelta *= 0.9;
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        this.wingRotation += this.wingRotDelta * 2.0f;
        if (!this.worldObj.isRemote && !this.isChild() && !this.isChickenJockey() && (this.timeUntilNextEgg -= " ".length()) <= 0) {
            this.playSound(EntityChicken.I["".length()], 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.dropItem(Items.egg, " ".length());
            this.timeUntilNextEgg = this.rand.nextInt(1278 + 849 + 887 + 2986) + (4667 + 1440 - 2186 + 2079);
        }
    }
    
    public void setChickenJockey(final boolean chickenJockey) {
        this.chickenJockey = chickenJockey;
    }
}
