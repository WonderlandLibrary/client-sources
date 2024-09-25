package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.ClickType;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class AutoArmor extends Module{
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	private static String[] modes = {"Normal", "OpenInv","FakeInv"};
	public static ModeValue autoarmormode = new ModeValue("Inv-Mode", "Normal", modes);
	
	private NumberValue<Integer> delay = new NumberValue<>("Delay", 100, 0, 500);
	private NumberValue<Integer> itemdelay = new NumberValue<>("Item-Delay", 40, 0, 100);
	
	private BooleanValue nomove = new BooleanValue("NoMove", false);
	
	private TimeHelper timer = new TimeHelper();
	
	public AutoArmor() {
		super("AutoArmor", "AutoArmor", Category.PLAYER, Keyboard.KEY_O);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}
	
	public void getBestArmor(){
    	for(int type = 1; type < 5; type++){
    		if(mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()){
    			ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
    			if(isBestArmor(is, type)){
    				continue;
    			}else{
    				if (!(mc.currentScreen instanceof GuiInventory))
    		    		if(autoarmormode.getObject() == 2){
    		    			C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
    						mc.thePlayer.connection.sendPacket(p);
    		    		}
    				drop(4 + type);
    				if(autoarmormode.getObject() == 2){
						mc.getConnection().sendPacket(new C0DPacketCloseWindow());
					}
    			}
    		}
    		for (int i = 9; i < 45; i++) {
    			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
    				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
    				if(isBestArmor(is, type) && getProtection(is) > 0){
    					is.itemdelay++;
    					if (is.itemdelay >= itemdelay.getObject()) {
    						is.itemdelay = 0;
    						if (!(mc.currentScreen instanceof GuiInventory))
    				    		if(autoarmormode.getObject() == 2){
    				    			C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
    								mc.thePlayer.connection.sendPacket(p);
    				    		}
    						shiftClick(i);
    						if(autoarmormode.getObject() == 2){
    							mc.getConnection().sendPacket(new C0DPacketCloseWindow());
    						}
    						timer.setLastMS();
    						if(delay.getObject() > 0)
    							break;
    					}
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
    public void shiftClick(int slot){
    	mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, ClickType.QUICK_MOVE, mc.thePlayer);
    }

    public void drop(int slot){
    	mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, ClickType.THROW, mc.thePlayer);
    }
    public static float getProtection(ItemStack stack){
    	float prot = 0;
    	if ((stack.getItem() instanceof ItemArmor)) {
    		ItemArmor armor = (ItemArmor)stack.getItem();
    		if (armor.getUnlocalizedName().contains("Diamond")) {
    			prot += 100;
    		}
    		if (armor.getUnlocalizedName().contains("Iron")) {
    			prot += 70;
    		}
    		if (armor.getUnlocalizedName().contains("Gold")) {
    			prot += 50;
    		}
    		if (armor.getUnlocalizedName().contains("Chain")) {
    			prot += 30;
    		}
    		if (armor.getUnlocalizedName().contains("Leather")) {
    			prot += 20;
    		}
    		prot += 100 - armor.damageReduceAmount;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.9D;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) *0.35D;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) * 0.4D;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) * 0.75D;
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) * 0.8D;   	
    		prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) * 0.15D;   	
    	}
	    return prot;
    }
	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (autoarmormode.getObject() == 0) {
			setDisplayName(getName() + ChatFormatting.WHITE + " Normal");
		}else if (autoarmormode.getObject() == 1) {
			setDisplayName(getName() + ChatFormatting.WHITE + " OpenInv");
		}else if (autoarmormode.getObject() == 2) {
			setDisplayName(getName() + ChatFormatting.WHITE + " FakeInv");
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (!e.isPre()) return;
			if (nomove.getObject() && (mc.thePlayer.motionX != 0 || mc.thePlayer.motionZ != 0))
				return;
			
			if(Client.instance.moduleManager.invCleaner.isEnabled())
	    		return;
	    	long delay = this.delay.getObject();
	        if(autoarmormode.getObject() == 1 && !(mc.currentScreen instanceof GuiInventory)){
	        	return;
	        }
	        if(mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChat){
	        	if(timer.hasTimeReached(delay)){
	        		getBestArmor();
	        	}
	        }
		}
	}
}
