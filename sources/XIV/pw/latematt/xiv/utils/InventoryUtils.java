package pw.latematt.xiv.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Matthew
 */
public class InventoryUtils {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    /* normal stuff */
    public static void shiftClick(Item item) {
        for (int index = 9; index <= 36; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (stack.getItem() == item) {
                MINECRAFT.playerController.windowClick(0, index, 0, 1, MINECRAFT.thePlayer);
                break;
            }
        }
    }

    public static boolean hotbarHas(Item item) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (stack.getItem() == item)
                return true;
        }
        return false;
    }

    public static void useFirst(Item item) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (stack.getItem() == item) {
                int oldItem = MINECRAFT.thePlayer.inventory.currentItem;
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, MINECRAFT.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                MINECRAFT.thePlayer.stopUsingItem();
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void instantUseFirst(Item item) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (stack.getItem() == item) {
                int oldItem = MINECRAFT.thePlayer.inventory.currentItem;
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, MINECRAFT.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                for (int x = 0; x <= 32; x++) {
                    MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer(MINECRAFT.thePlayer.onGround));
                }
                MINECRAFT.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                MINECRAFT.thePlayer.stopUsingItem();
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void dropFirst(Item item) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (stack.getItem() == item) {
                MINECRAFT.playerController.windowClick(0, 36 + index, 1, 4, MINECRAFT.thePlayer);
                break;
            }
        }
    }

    public static int getSlotID(Item item) {
        for (int index = 0; index <= 36; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (stack.getItem() == item) {
                return index;
            }
        }
        return -1;
    }

    public static int countItem(Item item) {
        int counter = 0;
        for (int index = 0; index <= 36; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (stack.getItem() == item)
                counter += stack.stackSize;
        }
        return counter;
    }

    /* potion stuff */
    public static boolean isPotion(ItemStack stack, Potion potion, boolean splash) {
        if (stack == null)
            return false;
        if (!(stack.getItem() instanceof ItemPotion))
            return false;
        ItemPotion potionItem = (ItemPotion) stack.getItem();
        if (splash && !ItemPotion.isSplash(stack.getItemDamage()))
            return false;
        if (potionItem.getEffects(stack) == null)
            return potion == null;
        if (potion == null)
            return false;
        for (Object o : potionItem.getEffects(stack)) {
            PotionEffect effect = (PotionEffect) o;
            if (effect.getPotionID() == potion.getId())
                return true;
        }
        return false;
    }

    public static void shiftClickPotion(Potion effect, boolean splash) {
        for (int index = 9; index <= 36; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null)
                continue;
            if (isPotion(stack, effect, splash)) {
                MINECRAFT.playerController.windowClick(0, index, 0, 1, MINECRAFT.thePlayer);
                break;
            }
        }
    }

    public static boolean hotbarHasPotion(Potion effect, boolean splash) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (isPotion(stack, effect, splash))
                return true;
        }
        return false;
    }

    public static void useFirstPotion(Potion effect, boolean splash) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (isPotion(stack, effect, splash)) {
                int oldItem = MINECRAFT.thePlayer.inventory.currentItem;
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, MINECRAFT.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void instantUseFirstPotion(Potion effect) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (isPotion(stack, effect, false)) {
                int oldItem = MINECRAFT.thePlayer.inventory.currentItem;
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(index));
                MINECRAFT.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, MINECRAFT.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
                for (int x = 0; x <= 32; x++) {
                    MINECRAFT.getNetHandler().addToSendQueue(new C03PacketPlayer(MINECRAFT.thePlayer.onGround));
                }
                MINECRAFT.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                MINECRAFT.thePlayer.stopUsingItem();
                MINECRAFT.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(oldItem));
                break;
            }
        }
    }

    public static void dropFirstPotion(Potion effect, boolean splash) {
        for (int index = 0; index <= 8; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (isPotion(stack, effect, splash)) {
                MINECRAFT.playerController.windowClick(0, 36 + index, 1, 4, MINECRAFT.thePlayer);
                break;
            }
        }
    }

    public static int getPotionSlotID(Potion effect, boolean splash) {
        for (int index = 0; index <= 36; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (isPotion(stack, effect, splash)) {
                return index;
            }
        }
        return -1;
    }

    public static int countPotion(Potion effect, boolean splash) {
        int counter = 0;
        for (int index = 0; index <= 36; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                continue;
            if (isPotion(stack, effect, splash))
                counter += stack.stackSize;
        }
        return counter;
    }

    /* general use */
    public static boolean hotbarIsFull() {
        for (int index = 0; index <= 36; index++) {
            ItemStack stack = MINECRAFT.thePlayer.inventory.getStackInSlot(index);
            if (stack == null)
                return false;
        }
        return true;
    }
}
