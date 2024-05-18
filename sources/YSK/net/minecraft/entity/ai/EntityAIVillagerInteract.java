package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntityAIVillagerInteract extends EntityAIWatchClosest2
{
    private int interactionDelay;
    private EntityVillager villager;
    
    @Override
    public void updateTask() {
        super.updateTask();
        if (this.interactionDelay > 0) {
            this.interactionDelay -= " ".length();
            if (this.interactionDelay == 0) {
                final InventoryBasic villagerInventory = this.villager.getVillagerInventory();
                int i = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
                while (i < villagerInventory.getSizeInventory()) {
                    final ItemStack stackInSlot = villagerInventory.getStackInSlot(i);
                    ItemStack itemStack = null;
                    if (stackInSlot != null) {
                        final Item item = stackInSlot.getItem();
                        if ((item == Items.bread || item == Items.potato || item == Items.carrot) && stackInSlot.stackSize > "   ".length()) {
                            final int n = stackInSlot.stackSize / "  ".length();
                            final ItemStack itemStack2 = stackInSlot;
                            itemStack2.stackSize -= n;
                            itemStack = new ItemStack(item, n, stackInSlot.getMetadata());
                            "".length();
                            if (4 <= 3) {
                                throw null;
                            }
                        }
                        else if (item == Items.wheat && stackInSlot.stackSize > (0x3E ^ 0x3B)) {
                            final int n2 = stackInSlot.stackSize / "  ".length() / "   ".length() * "   ".length();
                            final int n3 = n2 / "   ".length();
                            final ItemStack itemStack3 = stackInSlot;
                            itemStack3.stackSize -= n2;
                            itemStack = new ItemStack(Items.bread, n3, "".length());
                        }
                        if (stackInSlot.stackSize <= 0) {
                            villagerInventory.setInventorySlotContents(i, null);
                        }
                    }
                    if (itemStack != null) {
                        final EntityItem entityItem = new EntityItem(this.villager.worldObj, this.villager.posX, this.villager.posY - 0.30000001192092896 + this.villager.getEyeHeight(), this.villager.posZ, itemStack);
                        final float n4 = 0.3f;
                        final float rotationYawHead = this.villager.rotationYawHead;
                        final float rotationPitch = this.villager.rotationPitch;
                        entityItem.motionX = -MathHelper.sin(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n4;
                        entityItem.motionZ = MathHelper.cos(rotationYawHead / 180.0f * 3.1415927f) * MathHelper.cos(rotationPitch / 180.0f * 3.1415927f) * n4;
                        entityItem.motionY = -MathHelper.sin(rotationPitch / 180.0f * 3.1415927f) * n4 + 0.1f;
                        entityItem.setDefaultPickupDelay();
                        this.villager.worldObj.spawnEntityInWorld(entityItem);
                        "".length();
                        if (4 < 0) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
        }
    }
    
    public EntityAIVillagerInteract(final EntityVillager villager) {
        super(villager, EntityVillager.class, 3.0f, 0.02f);
        this.villager = villager;
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
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        if (this.villager.canAbondonItems() && this.closestEntity instanceof EntityVillager && ((EntityVillager)this.closestEntity).func_175557_cr()) {
            this.interactionDelay = (0x4E ^ 0x44);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            this.interactionDelay = "".length();
        }
    }
}
