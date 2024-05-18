package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class EntityMooshroom extends EntityCow
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public EntityMooshroom createChild(final EntityAgeable entityAgeable) {
        return new EntityMooshroom(this.worldObj);
    }
    
    public EntityMooshroom(final World world) {
        super(world);
        this.setSize(0.9f, 1.3f);
        this.spawnableBlock = Blocks.mycelium;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\t)\u0016Z\t\f#\u0011\u0004T\u0017.\u0011\u0015\b", "dFttz");
    }
    
    @Override
    public EntityCow createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.bowl && this.getGrowingAge() >= 0) {
            if (currentItem.stackSize == " ".length()) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, new ItemStack(Items.mushroom_stew));
                return " ".length() != 0;
            }
            if (entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.mushroom_stew)) && !entityPlayer.capabilities.isCreativeMode) {
                entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, " ".length());
                return " ".length() != 0;
            }
        }
        if (currentItem != null && currentItem.getItem() == Items.shears && this.getGrowingAge() >= 0) {
            this.setDead();
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + this.height / 2.0f, this.posZ, 0.0, 0.0, 0.0, new int["".length()]);
            if (!this.worldObj.isRemote) {
                final EntityCow entityCow = new EntityCow(this.worldObj);
                entityCow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                entityCow.setHealth(this.getHealth());
                entityCow.renderYawOffset = this.renderYawOffset;
                if (this.hasCustomName()) {
                    entityCow.setCustomNameTag(this.getCustomNameTag());
                }
                this.worldObj.spawnEntityInWorld(entityCow);
                int i = "".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (i < (0xA1 ^ 0xA4)) {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + this.height, this.posZ, new ItemStack(Blocks.red_mushroom)));
                    ++i;
                }
                currentItem.damageItem(" ".length(), entityPlayer);
                this.playSound(EntityMooshroom.I["".length()], 1.0f, 1.0f);
            }
            return " ".length() != 0;
        }
        return super.interact(entityPlayer);
    }
}
