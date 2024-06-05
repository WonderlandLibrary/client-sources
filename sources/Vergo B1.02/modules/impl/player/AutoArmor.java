package xyz.vergoclient.modules.impl.player;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.util.main.InventoryUtils;
import xyz.vergoclient.util.main.MovementUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Module implements OnEventInterface {

	public AutoArmor() {
		super("AutoArmor", Category.PLAYER);
	}
	
	public ModeSetting mode = new ModeSetting("Mode", "Inv Only", "When Stopped", "Inv Only", "Silent");
	public NumberSetting tickDelay = new NumberSetting("Delay", 1, 1, 10, 1);
	
	@Override
	public void loadSettings() {
		addSettings(tickDelay, mode);
	}
	
	@Override
	public void onEvent(Event e) {

		if (e instanceof EventTick && e.isPre()) {
			setInfo("");
		}
		else if (e instanceof EventUpdate && e.isPre() && mc.thePlayer.ticksExisted % tickDelay.getValueAsInt() == 0) {
			if (mode.is("When Stopped") && (MovementUtils.isMoving() || mc.currentScreen instanceof GuiContainer)) {
				return;
			}
			else if (mode.is("Inv Only") && !(mc.currentScreen instanceof GuiInventory)) {
				return;
			}
			getBestArmor();
		}
		
	}
	
	public void getBestArmor(){
    	for(int type = 1; type < 5; type++){
    		if(mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()){
    			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
    			if(isBestArmor(item, type)){
    				continue;
    			}else{
    				InventoryUtils.drop(4 + type, mc.thePlayer.inventoryContainer.windowId);
    				return;
    			}
    		}
    		for (int i = 9; i < 45; i++) {
    			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
    				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
    				if(isBestArmor(is, type) && getProtection(is) > 0){
    					InventoryUtils.shiftClick(i, mc.thePlayer.inventoryContainer.windowId);
    					return;
    				}
    			}
            }
        }
    }
    public static boolean isBestArmor(ItemStack stack, int type){
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
    
    public static float getProtection(ItemStack stack){
    	float prot = 0;
    	if ((stack.getItem() instanceof ItemArmor)) {
    		ItemArmor armor = (ItemArmor)stack.getItem();
    		prot += armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.0075D;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack)/100d;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack)/100d;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack)/100d;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack)/50d;   	
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)/100d;
//    		if ((stack.getMaxDamage() - stack.getItemDamage()) / stack.getMaxDamage() < 0.01) {
//    			prot -= 0.01;
//    		}
		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack)/100d;
    	}
	    return prot;
    }
	
}
