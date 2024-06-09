// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.utilities;

import java.util.Iterator;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.client.Minecraft;

public class InventoryUtils
{
    private static final Minecraft MINECRAFT;
    
    static {
        MINECRAFT = Minecraft.getMinecraft();
    }
    
    public static void shiftClick(final Item item) {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && stack.getItem() == item) {
                InventoryUtils.MINECRAFT.playerController.windowClick(0, index, 0, 1, InventoryUtils.MINECRAFT.thePlayer);
                break;
            }
        }
    }
    
    public static boolean hotbarHas(final Item item) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                return true;
            }
        }
        return false;
    }
    
    public static void useFirst(final Item item) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                final int oldItem = InventoryUtils.MINECRAFT.thePlayer.inventory.currentItem;
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(InventoryUtils.MINECRAFT.thePlayer.inventory.getCurrentItem()));
                InventoryUtils.MINECRAFT.thePlayer.stopUsingItem();
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }
    
    public static void instantUseFirst(final Item item) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                final int oldItem = InventoryUtils.MINECRAFT.thePlayer.inventory.currentItem;
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(InventoryUtils.MINECRAFT.thePlayer.inventory.getCurrentItem()));
                for (int x = 0; x <= 32; ++x) {
                    InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer(InventoryUtils.MINECRAFT.thePlayer.onGround));
                }
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                InventoryUtils.MINECRAFT.thePlayer.stopUsingItem();
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }
    
    public static void dropFirst(final Item item) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                InventoryUtils.MINECRAFT.playerController.windowClick(0, 36 + index, 1, 4, InventoryUtils.MINECRAFT.thePlayer);
                break;
            }
        }
    }
    
    public static int getSlotID(final Item item) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                return index;
            }
        }
        return -1;
    }
    
    public static int countItem(final Item item) {
        int counter = 0;
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && stack.getItem() == item) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }
    
    public static boolean isPotion(final ItemStack stack, final Potion potion, final boolean splash) {
        if (stack == null) {
            return false;
        }
        if (!(stack.getItem() instanceof ItemPotion)) {
            return false;
        }
        final ItemPotion potionItem = (ItemPotion)stack.getItem();
        if (splash && !ItemPotion.isSplash(stack.getItemDamage())) {
            return false;
        }
        if (potionItem.getEffects(stack) == null) {
            return potion == null;
        }
        if (potion == null) {
            return false;
        }
        for (final Object o : potionItem.getEffects(stack)) {
            final PotionEffect effect = (PotionEffect)o;
            if (effect.getPotionID() == potion.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public static void shiftClickPotion(final Potion effect, final boolean splash) {
        for (int index = 9; index <= 36; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack != null && isPotion(stack, effect, splash)) {
                InventoryUtils.MINECRAFT.playerController.windowClick(0, index, 0, 1, InventoryUtils.MINECRAFT.thePlayer);
                break;
            }
        }
    }
    
    public static boolean hotbarHasPotion(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                return true;
            }
        }
        return false;
    }
    
    public static void useFirstPotion(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                final int oldItem = InventoryUtils.MINECRAFT.thePlayer.inventory.currentItem;
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(InventoryUtils.MINECRAFT.thePlayer.inventory.getCurrentItem()));
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }
    
    public static void instantUseFirstPotion(final Potion effect) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, false)) {
                final int oldItem = InventoryUtils.MINECRAFT.thePlayer.inventory.currentItem;
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(InventoryUtils.MINECRAFT.thePlayer.inventory.getCurrentItem()));
                for (int x = 0; x <= 32; ++x) {
                    InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer(InventoryUtils.MINECRAFT.thePlayer.onGround));
                }
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                InventoryUtils.MINECRAFT.thePlayer.stopUsingItem();
                InventoryUtils.MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }
    
    public static void dropFirstPotion(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 8; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                InventoryUtils.MINECRAFT.playerController.windowClick(0, 36 + index, 1, 4, InventoryUtils.MINECRAFT.thePlayer);
                break;
            }
        }
    }
    
    public static int getPotionSlotID(final Potion effect, final boolean splash) {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                return index;
            }
        }
        return -1;
    }
    
    public static int countPotion(final Potion effect, final boolean splash) {
        int counter = 0;
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack != null && isPotion(stack, effect, splash)) {
                counter += stack.stackSize;
            }
        }
        return counter;
    }
    
    public static boolean hotbarIsFull() {
        for (int index = 0; index <= 36; ++index) {
            final ItemStack stack = InventoryUtils.MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
}
