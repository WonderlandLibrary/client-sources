package net.minecraft.entity.passive;

import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntityCow extends EntityAnimal
{
    private static final String[] I;
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntityCow.I["   ".length()], 0.15f, 1.0f);
    }
    
    @Override
    protected String getDeathSound() {
        return EntityCow.I["  ".length()];
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.bucket && !entityPlayer.capabilities.isCreativeMode && !this.isChild()) {
            final ItemStack itemStack = currentItem;
            final int stackSize = itemStack.stackSize;
            itemStack.stackSize = stackSize - " ".length();
            if (stackSize == " ".length()) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
                entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, " ".length(), "".length()), "".length() != 0);
            }
            return " ".length() != 0;
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
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
            if (2 <= -1) {
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
        if (-1 >= 3) {
            throw null;
        }
        while (i < n2) {
            this.dropItem(Items.leather, " ".length());
            ++i;
        }
        final int n3 = this.rand.nextInt("   ".length()) + " ".length() + this.rand.nextInt(" ".length() + n);
        int j = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (j < n3) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_beef, " ".length());
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                this.dropItem(Items.beef, " ".length());
            }
            ++j;
        }
    }
    
    @Override
    protected String getHurtSound() {
        return EntityCow.I[" ".length()];
    }
    
    public EntityCow(final World world) {
        super(world);
        this.setSize(0.9f, 1.3f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask(" ".length(), new EntityAIPanic(this, 2.0));
        this.tasks.addTask("  ".length(), new EntityAIMate(this, 1.0));
        this.tasks.addTask("   ".length(), new EntityAITempt(this, 1.25, Items.wheat, (boolean)("".length() != 0)));
        this.tasks.addTask(0x1E ^ 0x1A, new EntityAIFollowParent(this, 1.25));
        this.tasks.addTask(0xA3 ^ 0xA6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0x1 ^ 0x7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(0xA6 ^ 0xA1, new EntityAILookIdle(this));
    }
    
    @Override
    protected String getLivingSound() {
        return EntityCow.I["".length()];
    }
    
    @Override
    public EntityCow createChild(final EntityAgeable entityAgeable) {
        return new EntityCow(this.worldObj);
    }
    
    static {
        I();
    }
    
    @Override
    public float getEyeHeight() {
        return this.height;
    }
    
    private static void I() {
        (I = new String[0x1A ^ 0x1E])["".length()] = I("\"\u0015\u0013~( \r_#*6", "OzqPK");
        EntityCow.I[" ".length()] = I("\u0007))]\u0002\u00051e\u001b\u0014\u00182", "jFKsa");
        EntityCow.I["  ".length()] = I("\u00066\u0011z\f\u0004.]<\u001a\u0019-", "kYsTo");
        EntityCow.I["   ".length()] = I("7\n\u0014~\u00025\u0012X#\u0015?\u0015", "ZevPa");
    }
    
    @Override
    protected Item getDropItem() {
        return Items.leather;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
}
