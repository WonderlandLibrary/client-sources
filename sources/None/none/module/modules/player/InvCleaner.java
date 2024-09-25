package none.module.modules.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.potion.MobEffects;
import net.minecraft.potion.PotionEffect;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.module.modules.world.Scaffold;
import none.utils.ClickType;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class InvCleaner extends Module{
	
	private NumberValue<Integer> delay = new NumberValue<>("Delay", 100, 0, 500);
	private NumberValue<Integer> BLOCKCAP = new NumberValue<>("Blocks-Limit", 128, 64, 1000);
	
	private static String[] modes = {"Normal", "OpenInv","FakeInv"};
	public static ModeValue invcleanermode = new ModeValue("Inv-Mode", "Normal", modes);
	
	private BooleanValue nomove = new BooleanValue("NoMove", false);
	private BooleanValue ARCHERY = new BooleanValue("Bow", false);
	private BooleanValue FOOD = new BooleanValue("Food", false);
	private BooleanValue SWORD = new BooleanValue("Sword", false);
	private NumberValue<Integer> itemdelay = new NumberValue<>("Item-Delay", 40, 0, 100);
	
	public static int weaponSlot = 36, pickaxeSlot = 40, axeSlot = 41, shovelSlot = 42;
    private TimeHelper timer = new TimeHelper();
    private ArrayList<Integer> whitelistedItems = new ArrayList<>();
	
    public static boolean drop;
    
	public InvCleaner() {
		super("InvCleaner", "InvCleaner", Category.PLAYER, Keyboard.KEY_I);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}
	
	public void shiftClick(int slot){
    	mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, ClickType.QUICK_MOVE, mc.thePlayer);
    }
    public void swap(int slot1, int hotbarSlot){
    	mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, ClickType.SWAP, mc.thePlayer);
    }
    public void drop(int slot){
    	mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, ClickType.THROW, mc.thePlayer);
    }
    public boolean isBestWeapon(ItemStack stack){
    	float damage = getDamage(stack);
    	for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getDamage(is) > damage && (is.getItem() instanceof ItemSword || !SWORD.getObject()))
                	return false;
            }
        }
    	if((stack.getItem() instanceof ItemSword || !SWORD.getObject())){
    		return true;
    	}else{
    		return false;
    	}
    	
    }

    public void getBestWeapon(int slot){
    	for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(isBestWeapon(is) && getDamage(is) > 0 && (is.getItem() instanceof ItemSword || !SWORD.getObject())){
                	if (!(mc.currentScreen instanceof GuiInventory))
        				if(invcleanermode.getObject() == 2){
        					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
        					mc.thePlayer.connection.sendPacket(p);
        				}
                	swap(i, slot - 36);
                	if(invcleanermode.getObject() == 2){
                		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                	}
            		timer.setLastMS();
                	break;
                }
            }
        }
    }
    
    private float getDamage(ItemStack stack) {
    	float damage = 0;
    	Item item = stack.getItem();
    	if(item instanceof ItemTool){
    		ItemTool tool = (ItemTool)item;
    		damage += tool.getStrVsBlock(stack);
    	}
    	if(item instanceof ItemSword){
    		ItemSword sword = (ItemSword)item;
    		damage += sword.getDamageVsEntity();
    	}
    	damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + 
    			EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f + 
    			EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack) * 0.2f + 
    			EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) * 0.1f +
    			EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, stack) * 0.3f;
        return damage;
    }
    public boolean shouldDrop(ItemStack stack, int slot){
    	if(stack.getDisplayName().toLowerCase().contains("(right click)")){
    		return false;
    	}
    	if(stack.getDisplayName().toLowerCase().contains("§k||")){
    		return false;
    	}
    	if((slot == weaponSlot && isBestWeapon(mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack())) ||
    			(slot == pickaxeSlot && isBestPickaxe(mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack()) && pickaxeSlot >= 0) ||
    			(slot == axeSlot && isBestAxe(mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack()) && axeSlot >= 0) ||
    			(slot == shovelSlot && isBestShovel(mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack()) && shovelSlot >= 0) ){
    		return false;
    	}
    	if(stack.getItem() instanceof ItemArmor){
    		for(int type = 1; type < 5; type++){
    			if(mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()){
        			ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
        			if(AutoArmor.isBestArmor(is, type)){
        				continue;
        			}
        		}
    			if(AutoArmor.isBestArmor(stack, type)){
    				return false;
    			}
    		}
    	}
    	if (stack.getItem() instanceof ItemBlock &&
    			(getBlockCount() > BLOCKCAP.getObject() || 
//    			(
    					Scaffold.getBlacklistedBlocks().contains(((ItemBlock)stack.getItem()).getBlock()))) {
    		return true;
    	}
    	if(stack.getItem() instanceof ItemPotion){
    		if(isBadPotion(stack)){
    			return true;
    		}
    	}
    	if(stack.getItem() instanceof ItemFood && FOOD.getObject() && !(stack.getItem() instanceof ItemAppleGold)){
    		return true;
    	}
    	if(stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor){
    		return true;
    	}
    	if((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow")) && ARCHERY.getObject()){
    		return true;
    	}
    	
    	if(((stack.getItem().getUnlocalizedName().contains("tnt")) ||
                        (stack.getItem().getUnlocalizedName().contains("stick")) ||
                        (stack.getItem().getUnlocalizedName().contains("egg")) ||
                        (stack.getItem().getUnlocalizedName().contains("string")) ||
                        (stack.getItem().getUnlocalizedName().contains("cake")) ||
                        (stack.getItem().getUnlocalizedName().contains("mushroom")) ||
                        (stack.getItem().getUnlocalizedName().contains("flint")) ||
                        (stack.getItem().getUnlocalizedName().contains("coal")) ||
                        (stack.getItem().getUnlocalizedName().contains("sulphur")) ||
                        (stack.getItem().getUnlocalizedName().contains("dyePowder")) ||
                        (stack.getItem().getUnlocalizedName().contains("feather")) ||
                        (stack.getItem().getUnlocalizedName().contains("bucket")) ||
                        (stack.getItem().getUnlocalizedName().contains("chest") && !stack.getDisplayName().toLowerCase().contains("collect")) ||
                        (stack.getItem().getUnlocalizedName().contains("snow")) ||
                        (stack.getItem().getUnlocalizedName().contains("fish")) ||
                        (stack.getItem().getUnlocalizedName().contains("enchant")) ||
                        (stack.getItem().getUnlocalizedName().contains("exp")) ||
                        (stack.getItem().getUnlocalizedName().contains("shears")) ||
                        (stack.getItem().getUnlocalizedName().contains("anvil")) ||
                        (stack.getItem().getUnlocalizedName().contains("torch")) ||
                        (stack.getItem().getUnlocalizedName().contains("seeds")) ||
                        (stack.getItem().getUnlocalizedName().contains("leather")) ||
                        (stack.getItem().getUnlocalizedName().contains("reeds")) ||
                        (stack.getItem().getUnlocalizedName().contains("skull")) ||
                        (stack.getItem().getUnlocalizedName().contains("record")) ||
                        (stack.getItem().getUnlocalizedName().contains("snowball")) ||
                        (stack.getItem() instanceof ItemGlassBottle) ||
                        (stack.getItem().getUnlocalizedName().contains("piston")))){
    		return true;
    	}            
    	
    	return false;
    }
    
    public boolean shouldDrop(ItemStack stack){
    	if(stack.getDisplayName().toLowerCase().contains("(right click)")){
    		return false;
    	}
    	if(stack.getDisplayName().toLowerCase().contains("§k||")){
    		return false;
    	}
    	if(stack.getItem() instanceof ItemArmor){
    		for(int type = 1; type < 5; type++){
    			if(mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()){
        			ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
        			if(AutoArmor.isBestArmor(is, type)){
        				continue;
        			}
        		}
    			if(AutoArmor.isBestArmor(stack, type)){
    				return false;
    			}
    		}
    	}
    	if (stack.getItem() instanceof ItemBlock &&
    			(getBlockCount() > BLOCKCAP.getObject() || 
//    			(
    					Scaffold.getBlacklistedBlocks().contains(((ItemBlock)stack.getItem()).getBlock()))) {
    		return true;
    	}
    	if(stack.getItem() instanceof ItemPotion){
    		if(isBadPotion(stack)){
    			return true;
    		}
    	}
    	if(stack.getItem() instanceof ItemFood && FOOD.getObject() && !(stack.getItem() instanceof ItemAppleGold)){
    		return true;
    	}
    	if(stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemArmor){
    		return true;
    	}
    	if((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow")) && ARCHERY.getObject()){
    		return true;
    	}
    	
    	if(((stack.getItem().getUnlocalizedName().contains("tnt")) ||
                        (stack.getItem().getUnlocalizedName().contains("stick")) ||
                        (stack.getItem().getUnlocalizedName().contains("egg")) ||
                        (stack.getItem().getUnlocalizedName().contains("string")) ||
                        (stack.getItem().getUnlocalizedName().contains("cake")) ||
                        (stack.getItem().getUnlocalizedName().contains("mushroom")) ||
                        (stack.getItem().getUnlocalizedName().contains("flint")) ||
                        (stack.getItem().getUnlocalizedName().contains("coal")) ||
                        (stack.getItem().getUnlocalizedName().contains("sulphur")) ||
                        (stack.getItem().getUnlocalizedName().contains("dyePowder")) ||
                        (stack.getItem().getUnlocalizedName().contains("feather")) ||
                        (stack.getItem().getUnlocalizedName().contains("bucket")) ||
                        (stack.getItem().getUnlocalizedName().contains("chest") && !stack.getDisplayName().toLowerCase().contains("collect")) ||
                        (stack.getItem().getUnlocalizedName().contains("snow")) ||
                        (stack.getItem().getUnlocalizedName().contains("fish")) ||
                        (stack.getItem().getUnlocalizedName().contains("enchant")) ||
                        (stack.getItem().getUnlocalizedName().contains("exp")) ||
                        (stack.getItem().getUnlocalizedName().contains("shears")) ||
                        (stack.getItem().getUnlocalizedName().contains("anvil")) ||
                        (stack.getItem().getUnlocalizedName().contains("torch")) ||
                        (stack.getItem().getUnlocalizedName().contains("seeds")) ||
                        (stack.getItem().getUnlocalizedName().contains("leather")) ||
                        (stack.getItem().getUnlocalizedName().contains("reeds")) ||
                        (stack.getItem().getUnlocalizedName().contains("skull")) ||
                        (stack.getItem().getUnlocalizedName().contains("record")) ||
                        (stack.getItem().getUnlocalizedName().contains("snowball")) ||
                        (stack.getItem() instanceof ItemGlassBottle) ||
                        (stack.getItem().getUnlocalizedName().contains("piston")))){
    		return true;
    	}            
    	
    	return false;
    }
    public ArrayList<Integer>getWhitelistedItem(){
    	return whitelistedItems;
    }
    
    public boolean getNoDropItem() {
    	Minecraft mc = Minecraft.getMinecraft();
    	boolean drop = true;
    	for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(shouldDrop(is, i)){
                	drop = true;
                }else {
                	drop = false;
                }
            }
        }
    	return drop;
    }
    
    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && !Scaffold.getBlacklistedBlocks().contains(((ItemBlock) item).getBlock())) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }
    
    private void getBestPickaxe(int slot){
    	for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				
				if(isBestPickaxe(is) && pickaxeSlot != i){
					if(!isBestWeapon(is))
					if(!mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getHasStack()){	
						if (!(mc.currentScreen instanceof GuiInventory))
            				if(invcleanermode.getObject() == 2){
            					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
            					mc.thePlayer.connection.sendPacket(p);
            				}
						swap(i, pickaxeSlot - 36);
						if(invcleanermode.getObject() == 2){
                    		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                    	}
						timer.setLastMS();
    					if(delay.getObject() > 0)
    						return;
					}else if(!isBestPickaxe(mc.thePlayer.inventoryContainer.getSlot(pickaxeSlot).getStack())){
						if (!(mc.currentScreen instanceof GuiInventory))
            				if(invcleanermode.getObject() == 2){
            					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
            					mc.thePlayer.connection.sendPacket(p);
            				}
						swap(i, pickaxeSlot - 36);
						if(invcleanermode.getObject() == 2){
                    		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                    	}
						timer.setLastMS();
    					if(delay.getObject() > 0)
    						return;
					}
				
				}
			}
    	}
    }
    private void getBestShovel(int slot){
    	for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				
				if(isBestShovel(is) && shovelSlot != i){
					if(!isBestWeapon(is))
					if(!mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getHasStack()){
						if (!(mc.currentScreen instanceof GuiInventory))
            				if(invcleanermode.getObject() == 2){
            					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
            					mc.thePlayer.connection.sendPacket(p);
            				}
						swap(i, shovelSlot - 36);
						if(invcleanermode.getObject() == 2){
                    		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                    	}
						timer.setLastMS();
    					if(delay.getObject() > 0)
    						return;
					}else if(!isBestShovel(mc.thePlayer.inventoryContainer.getSlot(shovelSlot).getStack())){	
						if (!(mc.currentScreen instanceof GuiInventory))
            				if(invcleanermode.getObject() == 2){
            					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
            					mc.thePlayer.connection.sendPacket(p);
            				}
						swap(i, shovelSlot - 36);
						if(invcleanermode.getObject() == 2){
                    		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                    	}
						timer.setLastMS();
    					if(delay.getObject() > 0)
    						return;
					}
				
				}
			}
    	}
    }
    private void getBestAxe(int slot){

    	for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				
				if(isBestAxe(is) && axeSlot != i){
					if(!isBestWeapon(is))
					if(!mc.thePlayer.inventoryContainer.getSlot(axeSlot).getHasStack()){
						if (!(mc.currentScreen instanceof GuiInventory))
            				if(invcleanermode.getObject() == 2){
            					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
            					mc.thePlayer.connection.sendPacket(p);
            				}
						swap(i, axeSlot - 36);
						if(invcleanermode.getObject() == 2){
                    		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                    	}
						timer.setLastMS();
    					if(delay.getObject() > 0)
    						return;
					}else if(!isBestAxe(mc.thePlayer.inventoryContainer.getSlot(axeSlot).getStack())){
						if (!(mc.currentScreen instanceof GuiInventory))
            				if(invcleanermode.getObject() == 2){
            					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
            					mc.thePlayer.connection.sendPacket(p);
            				}
						swap(i, axeSlot - 36);
						if(invcleanermode.getObject() == 2){
                    		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                    	}
						timer.setLastMS();
    					if(delay.getObject() > 0)
    						return;
					}
				
				}
			}
    	}
    }
    private boolean isBestPickaxe(ItemStack stack){
     	Item item = stack.getItem();
    	if(!(item instanceof ItemPickaxe))
    		return false;
    	float value = getToolEffect(stack);
    	for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe){                	
                	return false;
                }
                	
            }
        }
    	return true;
    }
    private boolean isBestShovel(ItemStack stack){
    	Item item = stack.getItem();
    	if(!(item instanceof ItemSpade))
    		return false;
    	float value = getToolEffect(stack);
    	for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getToolEffect(is) > value && is.getItem() instanceof ItemSpade){                	
                	return false;
                }
                	
            }
        }
    	return true;
    }
    private boolean isBestAxe(ItemStack stack){
    	Item item = stack.getItem();
    	if(!(item instanceof ItemAxe))
    		return false;
    	float value = getToolEffect(stack);
    	for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if(getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)){                
                	return false;
                }
                	
            }
        }
    	return true;
    }
    private float getToolEffect(ItemStack stack){
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
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.75D;
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) * 0.7D;
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack) * 0.5D;
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack) * 0.4D;
    	return value;
    }
    
    private boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
            	if (potion.getEffects(stack).isEmpty()) {
            		return true;
            	}
                for (final PotionEffect effect : potion.getEffects(stack)) {
                    if (effect.getPotion() == MobEffects.harm || effect.getPotion() == MobEffects.moveSlowdown || effect.getPotion() == MobEffects.weakness) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    boolean invContainsType(int type){
    	
    	for(int i = 9; i < 45; i++){
    		if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				Item item = is.getItem();
				if(item instanceof ItemArmor){
					ItemArmor armor = (ItemArmor)item;
					if(type == armor.armorType){
						return true;
					}	
				}
    		}
    	}
    	return false;
    }
    public void getBestArmor(){
    	for(int type = 1; type < 5; type++){
    		if(mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()){
    			ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
    			if(AutoArmor.isBestArmor(is, type)){
    				continue;
    			}else{
    				if (!(mc.currentScreen instanceof GuiInventory))
        				if(invcleanermode.getObject() == 2){
        					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
        					mc.thePlayer.connection.sendPacket(p);
        				}
    				drop(4 + type);
    				if(invcleanermode.getObject() == 2){
                		mc.getConnection().sendPacket(new C0DPacketCloseWindow());
                	}
    			}
    		}
    		for (int i = 9; i < 45; i++) {
    			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
    				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
    				is.itemdelay++;
    				if(AutoArmor.isBestArmor(is, type) && AutoArmor.getProtection(is) > 0){
    					if (is.itemdelay >= itemdelay.getObject()) {    						
    						if (!(mc.currentScreen instanceof GuiInventory))
                				if(invcleanermode.getObject() == 2){
                					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
                					mc.thePlayer.connection.sendPacket(p);
                				}
    						shiftClick(i);
    						if(invcleanermode.getObject() == 2){
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

	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (invcleanermode.getObject() == 0) {
			setDisplayName(getName() + ChatFormatting.WHITE + " Normal");
		}else if(invcleanermode.getObject() == 1){
        	setDisplayName(getName() + ChatFormatting.WHITE + " OpenInv");
        }else if (invcleanermode.getObject() == 2) {
        	setDisplayName(getName() + ChatFormatting.WHITE + " FakeInv");
        }
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (!e.isPre()) return;
			if (nomove.getObject() && (mc.thePlayer.motionX != 0 || mc.thePlayer.motionZ != 0))
				return;
			
			Module armor = Client.instance.moduleManager.autoArmor;
            long Adelay = delay.getObject();
            int Amode = AutoArmor.autoarmormode.getObject();
            if(timer.hasTimeReached(Adelay) && armor.isEnabled()){
            	 if(!(Amode == 1) || mc.currentScreen instanceof GuiInventory){
                     if(mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChat){
                    	 getBestArmor();
                     }
                 }
            }
            if( armor.isEnabled())
            for(int type = 1; type < 5; type++){
        		if(mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()){
        			ItemStack is = mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
        			if(!AutoArmor.isBestArmor(is, type)){
        				return;
        			}
        		}else if(invContainsType(type-1)){
        			return;
        		}
            }
			if(invcleanermode.getSelected().equalsIgnoreCase("OpenInv") && !(mc.currentScreen instanceof GuiInventory)){
            	drop = false;
				return;
            }
			
			
			if(mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChat){
				drop = getNoDropItem();
				if(timer.hasTimeReached(delay.getObject()) && weaponSlot >= 36){         

            		if (!mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getHasStack()){
            			getBestWeapon(weaponSlot);
                	
            		}else{
            			if(!isBestWeapon(mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack())){           			
                			getBestWeapon(weaponSlot);
                		}
            		}
            	}
            	if(timer.hasTimeReached(delay.getObject()) && pickaxeSlot >= 36){
            		getBestPickaxe(pickaxeSlot);
            	}
            	if(timer.hasTimeReached(delay.getObject()) && shovelSlot >= 36){
            		getBestShovel(shovelSlot);
            	}
            	if(timer.hasTimeReached(delay.getObject()) && axeSlot >= 36){
            		getBestAxe(axeSlot);
            	}
            	if(timer.hasTimeReached(delay.getObject()))
                for (int i = 9; i < 45; i++) {
                    if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        is.itemdelay++;
                        if(shouldDrop(is, i)){
                        	if (is.itemdelay >= itemdelay.getObject()) {
                        		if (!(mc.currentScreen instanceof GuiInventory))
                    				if(invcleanermode.getObject() == 2){
                    					C0BPacketEntityAction p = new C0BPacketEntityAction(mc.thePlayer, Action.OPEN_INVENTORY);
                    					mc.thePlayer.connection.sendPacket(p);
                    				}                     			
                        		drop(i);
                        		if(invcleanermode.getObject() == 2){
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
	}
}
