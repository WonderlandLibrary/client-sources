package win.sightclient.utils.minecraft;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class ItemUtils {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public static int getSwordSlot() {
		if (mc.thePlayer == null) {
			return -1;
		}
		
		int bestSword = -1;
		float bestDamage = 1F;
		
		for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
    	    	if (item != null) {
    	    		if (item.getItem() instanceof ItemSword) {
    	    			ItemSword is = (ItemSword) item.getItem();
    	    			float damage = is.getDamageVsEntity();
    	    	    	damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, item) * 1.26F + 
    	    	    			EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, item) * 0.01f;
    			    	if (damage > bestDamage) {
    			    		bestDamage = damage;
    			    		bestSword = i;
    			    	}
    	    		}
    	    	}
            }
		}
		return bestSword;
	}
	
	public static int getEmptyHotbarSlot() {
        for (int k = 0; k < 9; ++k) {
            if (mc.thePlayer.inventory.mainInventory[k] == null) {
                return k;
            }
        }
		return -1;
	}
	
	public static int getPickaxeSlot() {
		if (mc.thePlayer == null) {
			return -1;
		}
		
		int bestSword = -1;
		float bestDamage = 1F;
		
		for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
    	    	if (item != null) {
    	    		if (item.getItem() instanceof ItemPickaxe) {
    	    			ItemPickaxe is = (ItemPickaxe) item.getItem();
    	    			float damage = is.getStrVsBlock(item, Block.getBlockById(4));
    			    	if (damage > bestDamage) {
    			    		bestDamage = damage;
    			    		bestSword = i;
    			    	}
    	    		}
    	    	}
            }
	    }
		return bestSword;
	}
	
	public static int getAxeSlot() {
		if (mc.thePlayer == null) {
			return -1;
		}
		
		int bestSword = -1;
		float bestDamage = 1F;
		
		for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
    	    	if (item != null) {
    	    		if (item.getItem() instanceof ItemAxe) {
    	    			ItemAxe is = (ItemAxe) item.getItem();
    	    			float damage = is.getStrVsBlock(item, Block.getBlockById(17));
    			    	if (damage > bestDamage) {
    			    		bestDamage = damage;
    			    		bestSword = i;
    			    	}
    	    		}
    	    	}
            }
	    }
		return bestSword;
	}
	
	public static int getShovelSlot() {
		if (mc.thePlayer == null) {
			return -1;
		}
		
		int bestSword = -1;
		float bestDamage = 1F;
		
		for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
    	    	if (item != null) {
    	    		if (item.getItem() instanceof ItemTool) {
    	    			ItemTool is = (ItemTool) item.getItem();
    	    			if (isShovel(is)) {
    		    			float damage = is.getStrVsBlock(item, Block.getBlockById(3));
    				    	if (damage > bestDamage) {
    				    		bestDamage = damage;
    				    		bestSword = i;
    				    	}
    	    			}
    	    		}
    	    	}
            }
	    }
		return bestSword;
	}
	
	public static boolean isShovel(Item is) {
		return Item.getItemById(256) == is || Item.getItemById(269) == is || Item.getItemById(273) == is || Item.getItemById(277) == is || Item.getItemById(284) == is;
	}

	public static void shiftClick(int k) {
		Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, k, 0, 1, Minecraft.getMinecraft().thePlayer);
	}
}
