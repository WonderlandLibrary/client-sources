package xyz.vergoclient.util.main;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InventoryUtils {
	
    public static void switchToSlot(int slot){
    	Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot - 1;
    	Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(slot - 1));
    }
	
    public static void shiftClick(int slot, int windowId){
    	Minecraft.getMinecraft().playerController.windowClick(windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
    }
    
    public static void shiftClick(int slot){
    	shiftClick(slot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }

    public static void drop(int slot, int windowId){
    	Minecraft.getMinecraft().playerController.windowClick(windowId, slot, 1, 4, Minecraft.getMinecraft().thePlayer);
    }
    
    public static void drop(int slot){
    	shiftClick(slot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }
    
    public static void click(int slot, int windowId){
    	Minecraft.getMinecraft().playerController.windowClick(windowId, slot, 0, 0, Minecraft.getMinecraft().thePlayer);
    }
    
    public static void click(int slot){
    	shiftClick(slot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }
    
    // I found these on github
    
    public static void swap(int slot1, int hotbarSlot, int windowId){
    	
    	if (hotbarSlot == slot1 - 36) {
    		return;
    	}
    	
    	Minecraft.getMinecraft().playerController.windowClick(windowId, slot1, hotbarSlot, 2, Minecraft.getMinecraft().thePlayer);
    	
    }
    
    public static void swap(int slot1, int hotbarSlot){
    	swap(slot1, hotbarSlot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }
    
    public static float getDamage(ItemStack stack) {
    	float damage = 0;
    	Item item = stack.getItem();
    	if(item instanceof ItemTool){
    		ItemTool tool = (ItemTool)item;
    		damage += tool.getDamage();
    	}
    	if(item instanceof ItemSword){
    		ItemSword sword = (ItemSword)item;
    		damage += sword.getDamage();
    	}
    	damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + 
    			EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 1.0f;
        return damage;
    }
    
    public static boolean isBestPickaxe(ItemStack stack){
     	Item item = stack.getItem();
    	if(!(item instanceof ItemPickaxe))
    		return false;
    	float value = getToolEffect(stack);
    	for (int i = 9; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe){                	
                	return false;
                }
                	
            }
        }
    	return true;
    }
    
    public static boolean isBestShovel(ItemStack stack){
    	Item item = stack.getItem();
    	if(!(item instanceof ItemSpade))
    		return false;
    	float value = getToolEffect(stack);
    	for (int i = 9; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getToolEffect(is) > value && is.getItem() instanceof ItemSpade){                	
                	return false;
                }
                	
            }
        }
    	return true;
    }
    public static boolean isBestAxe(ItemStack stack){
    	Item item = stack.getItem();
    	if(!(item instanceof ItemAxe))
    		return false;
    	float value = getToolEffect(stack);
    	for (int i = 9; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)){                
                	return false;
                }
                	
            }
        }
    	return true;
    }
    public static float getToolEffect(ItemStack stack){
    	Item item = stack.getItem();
    	if(!(item instanceof ItemTool))
    		return 0;
    	String name = item.getUnlocalizedName();
    	ItemTool tool = (ItemTool)item;
    	float value = 1;
    	if(item instanceof ItemPickaxe){
    		value = tool.getStrVsBlock(stack, Blocks.stone);
    		if(name.toLowerCase().contains("gold")){
    			value -= 5;
    		}
    	}else if(item instanceof ItemSpade){
    		value = tool.getStrVsBlock(stack, Blocks.dirt);
    		if(name.toLowerCase().contains("gold")){
    			value -= 5;
    		}
    	}else if(item instanceof ItemAxe){
    		value = tool.getStrVsBlock(stack, Blocks.log);
    		if(name.toLowerCase().contains("gold")){
    			value -= 5;
    		}
    	}else
    		return 1f;
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D;
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)/100d;
    	return value;
    }
    public static boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if(potion.getEffects(stack) == null)
            	return true;
            for (final Object o : potion.getEffects(stack)) {
                final PotionEffect effect = (PotionEffect) o;
                if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                	return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isBestWeapon(ItemStack stack){
    	float damage = getDamage(stack);
    	for (int i = 9; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getDamage(is) > damage && is.getItem() instanceof ItemSword)
                	return false;
            }
        }
    	
    	if (stack.getItem() instanceof ItemSword) {
    		return true;
    	}
    	
    	return false;
    	
    }
    
    public static Slot getBestSwordSlot(){
    	
    	Slot bestSword = null;
    	for (int i = 9; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            	Slot slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i);
                if(slot.getStack().getItem() instanceof ItemSword)
                	if (isBestWeapon(slot.getStack())) {
                		bestSword = slot;
                	}
                	
            }
        }
    	
    	return bestSword;
    	
    }
    
    public static Slot getBestPickaxeSlot(){
    	
    	Slot bestPickaxe = null;
    	for (int i = 9; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            	Slot slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i);
                if(slot.getStack().getItem() instanceof ItemPickaxe)
                	if (isBestPickaxe(slot.getStack())) {
                		bestPickaxe = slot;
                	}
                	
            }
        }
    	
    	return bestPickaxe;
    	
    }
    
    public static Slot getBestAxeSlot(){
    	
    	Slot bestAxe = null;
    	for (int i = 9; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            	Slot slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i);
                if(slot.getStack().getItem() instanceof ItemAxe)
                	if (isBestAxe(slot.getStack())) {
                		bestAxe = slot;
                	}
                	
            }
        }
    	
    	return bestAxe;
    	
    }
    
    public static boolean isInventoryFull() {
    	for (ItemStack stack : Minecraft.getMinecraft().thePlayer.inventory.mainInventory) {
    		if (stack == null || stack.getItem() == null) {
    			return false;
    		}
    	}
    	return true;
    }
    
}
