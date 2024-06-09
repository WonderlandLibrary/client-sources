package me.teus.eclipse.modules.impl.player;

import me.teus.eclipse.events.packet.EventSendPacket;
import me.teus.eclipse.events.player.EventPreUpdate;
import me.teus.eclipse.events.world.EventTick;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.BooleanValue;
import me.teus.eclipse.modules.value.impl.NumberValue;
import me.teus.eclipse.utils.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import xyz.lemon.event.bus.Listener;

import java.util.Arrays;
import java.util.List;

@Info(name = "InvManager", displayName = "InvManager", category = Category.PLAYER)
public class InvCleaner extends Module {
    
    public BooleanValue openInv =  new BooleanValue("Open Inv", false);
    public NumberValue delay = new NumberValue("Delay", 30, 10, 100, 1);
    public TimerUtil timer = new TimerUtil();

    public InvCleaner() {
        addValues(delay, openInv);
    }

    public Listener<EventPreUpdate> eventPreUpdateListener = e -> {
        if ((mc.currentScreen == null || (mc.currentScreen instanceof GuiContainer && ((GuiContainer) mc.currentScreen).inventorySlots == mc.thePlayer.inventoryContainer)) && purgeUnusedArmor() && purgeUnusedTools() && purgeJunk() && manageSword()) {
            if (hotbarHasSpace())
                manageHotbar();
            getBestArmor();
        }
    };

    public Listener<EventSendPacket> eventSendPacketListener = e -> {
        Packet packet = ((EventSendPacket) e).getPacket();
        if (packet instanceof C16PacketClientStatus) {
            C16PacketClientStatus open = (C16PacketClientStatus) packet;
            if (open.getStatus() == EnumState.OPEN_INVENTORY_ACHIEVEMENT)
                inventoryOpen = true;
        }
        if (packet instanceof C0DPacketCloseWindow) {
            inventoryOpen = false;
        }
    };

    public boolean inventoryOpen;
    public List<String> junk = Arrays.asList("stick", "egg", "string", "cake", "mushroom", "flint", "dyePowder", "feather", "chest", "snowball",
            "fish", "enchant", "exp", "shears", "anvil", "torch", "seeds", "leather", "reeds", "skull", "record", "piston", "snow", "poison");

    public static float getSwordStrength(ItemStack stack) {
        return (!(stack.getItem() instanceof ItemSword) ? 0.0F : (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F) + (!(stack.getItem() instanceof ItemSword) ? 0.0F : (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack));
    }

    private boolean manageSword() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();
                if (!stack.getDisplayName().toLowerCase().contains("(right click)") && item instanceof ItemSword && timer.hasTimeElapsed((long) delay.getValue())) {
                    moveToHotbarSlot1(i);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean purgeUnusedArmor() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                if (item instanceof ItemArmor) {
                    if (!isBestArmor(stack) && timer.hasTimeElapsed((long) delay.getValue())) {
                        purge(i);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean purgeUnusedTools() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                if (item instanceof ItemTool) {
                    if (!stack.getDisplayName().toLowerCase().contains("(right click)") && !isBestTool(stack) && timer.hasTimeElapsed((long) delay.getValue())) {
                        purge(i);
                        return false;
                    }
                }
                if (item instanceof ItemSword) {
                    if (!stack.getDisplayName().toLowerCase().contains("(right click)") && !isBestSword(stack) && timer.hasTimeElapsed((long) delay.getValue())) {
                        purge(i);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean purgeJunk() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                for (String shortName : junk) {
                    if (!stack.getDisplayName().toLowerCase().contains("(right click)") && item.getUnlocalizedName().contains(shortName) && timer.hasTimeElapsed((long) delay.getValue())) {
                        purge(i);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void manageHotbar() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                if (!stack.getDisplayName().toLowerCase().contains("(right click)") &&
                        ((item instanceof ItemPickaxe && hotbarNeedsItem(ItemPickaxe.class)) || (item instanceof ItemAxe && hotbarNeedsItem(ItemAxe.class)) || (item instanceof ItemSword && hotbarNeedsItem(ItemSword.class)) ||
                                (item instanceof ItemAppleGold && hotbarNeedsItem(ItemAppleGold.class)) || (item instanceof ItemEnderPearl && hotbarNeedsItem(ItemEnderPearl.class)) || (item instanceof ItemBlock && (((ItemBlock) item).getBlock().isFullCube()) &&
                                !hotbarHasBlocks())) &&
                        !hotbar && timer.hasTimeElapsed((long) delay.getValue())) {
                    moveToHotbar(i);
                    return;
                }
            }
        }
    }

    public boolean hotbarHasSpace() {
        for (int i = 36; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (slot.getStack() == null)
                return true;
        }
        return false;
    }

    public boolean hotbarNeedsItem(Class<?> type) {
        for (int i = 36; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (type.isInstance(slot.getStack()))
                return false;
        }
        return true;
    }

    public boolean hotbarHasBlocks() {
        for (int i = 36; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (slot.getStack() != null && slot.getStack().getItem() instanceof ItemBlock && ((ItemBlock) slot.getStack().getItem()).getBlock().isFullCube())
                return true;
        }
        return false;
    }

    public boolean isBestTool(ItemStack compareStack) {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemTool) {
                ItemTool item = (ItemTool) stack.getItem();
                ItemTool compare = (ItemTool) compareStack.getItem();
                if (item.getClass() == compare.getClass()) {
                    if (compare.getStrVsBlock(stack, preferredBlock(item.getClass())) <= item.getStrVsBlock(compareStack, preferredBlock(compare.getClass())))
                        return false;
                }
            }
        }

        return true;
    }

    public boolean isBestSword(ItemStack compareStack) {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemSword) {
                ItemSword item = (ItemSword) stack.getItem();
                ItemSword compare = (ItemSword) compareStack.getItem();
                if (item.getClass() == compare.getClass()) {
                    if (compare.attackDamage + getSwordStrength(compareStack) <= item.attackDamage + getSwordStrength(stack))
                        return false;
                }
            }
        }

        return true;
    }

    public Block preferredBlock(Class clazz) {
        return clazz == ItemPickaxe.class ? Blocks.cobblestone : clazz == ItemAxe.class ? Blocks.log : Blocks.dirt;
    }

    public boolean isBestArmor(ItemStack compareStack) {
        for (int i = 0; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemArmor) {
                ItemArmor item = (ItemArmor) stack.getItem();
                ItemArmor compare = (ItemArmor) compareStack.getItem();
                if (item.armorType == compare.armorType) {
                    if (getProtection(compareStack) <= getProtection(stack))
                        return false;
                }
            }
        }

        return true;
    }

    public boolean has(Item item, int count) { //WIP
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (slot.getStack() != null && (slot.getStack().getItem().equals(item)))
                count -= slot.getStack().stackSize;
        }
        return count >= 0;
    }

    public void moveToHotbar(int slot) {
        if (!openInv.isEnabled())
            openInvPacket();

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);

        if (!openInv.isEnabled())
            closeInvPacket();
    }

    public void moveToHotbarSlot1(int slot) {
        if (!openInv.isEnabled())
            openInvPacket();

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 2, mc.thePlayer);

        if (!openInv.isEnabled())
            closeInvPacket();
    }

    public void purge(int slot) {
        if (!openInv.isEnabled())
            openInvPacket();

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);

        if (!openInv.isEnabled())
            closeInvPacket();
    }

    public void openInvPacket() {
        if (!inventoryOpen)
            mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT));

        inventoryOpen = true;
    }

    public void closeInvPacket() {
        if (inventoryOpen)
            mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));

        inventoryOpen = false;
    }

    public void getBestArmor(){
        for(int type = 1; type < 5; type++){
            if(mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()){
                ItemStack item = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                if(isBestArmor(item, type)){
                    continue;
                }else{
                    if (!(mc.currentScreen instanceof GuiInventory)) {
                        timer.reset();
                        C16PacketClientStatus p = new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT);
                        mc.thePlayer.sendQueue.addToSendQueue(p);
                    }
                    drop(4 + type);
                }
            }
            for (int i = 9; i < 45; i++) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if(isBestArmor(is, type) && getProtection(is) > 0){
                        shiftClick(i);
                    }
                }
            }
        }
    }
    public boolean isBestArmor(ItemStack stack, int type){
        float prot = getProtection(stack);
        String strType = "";
        if(type == 1){
            strType = "helmet";
        }else if(type == 2){
            strType = "chestplate";
        }else if(type == 3){
            strType = "leggings";
        }else if(type == 4){
            strType = "boots";
        }
        if(!stack.getUnlocalizedName().contains(strType)){
            return false;
        }
        for (int i = 5; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getProtection(is) > prot && is.getUnlocalizedName().contains(strType))
                    return false;
            }
        }
        return true;
    }

    public float getProtection(ItemStack stack){
        float prot = 0;
        if ((stack.getItem() instanceof ItemArmor)) {
            ItemArmor armor = (ItemArmor)stack.getItem();
            prot += armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075D;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack)/100d;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack)/100d;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack)/100d;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)/50d;
            prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)/100d;
        }
        return prot;
    }

    public void shiftClick(int slot){
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
    }

    public void drop(int slot){
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
    }
}
