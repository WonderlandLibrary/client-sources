package xyz.vergoclient.modules.impl.player;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class AutoTool extends Module implements OnEventInterface {

	public AutoTool() {
		super("Autotool", Category.PLAYER);
	}

	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventSendPacket && e.isPre()) {
			
			Packet p = ((EventSendPacket)e).packet;
			
			try {
				if (p instanceof C07PacketPlayerDigging && mc.gameSettings.keyBindAttack.isKeyDown() && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
					autotool(mc.objectMouseOver.getBlockPos());
				}
			} catch (Exception e2) {
				
			}
			
			if (p instanceof C02PacketUseEntity && ((C02PacketUseEntity)p).getAction() == Action.ATTACK && Vergo.config.modScaffold.isDisabled()) {
				
		        int bestWeapon = -1;
		        
		        for (int i = 0; i < 9; i++) {
		        	
		            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
		            
		            if (itemStack != null && itemStack.getItem() != null && InventoryUtils.isBestWeapon(itemStack)) {
		            	
		                bestWeapon = i;
		                
		            }
		            
		        }
		        
		        if (bestWeapon < 0) {
		            return;
		        }
		        
		        if (mc.thePlayer.inventory.currentItem == bestWeapon) {
		        	return;
		        }
		        
		        mc.thePlayer.inventory.currentItem = bestWeapon;
		        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestWeapon));
		        
			}
			
		}
	}
	
	public static void autotool(BlockPos position) {
    	
        Block block = mc.theWorld.getBlockState(position).getBlock();
        
        int item = getStrongestItem(block);
        if (item < 0) {
            return;
        }
        float strength = getStrengthAgainstBlock(block, mc.thePlayer.inventory.mainInventory[item]);
        if (mc.thePlayer.getHeldItem() != null && getStrengthAgainstBlock(block, mc.thePlayer.getHeldItem()) >= strength) {
            return;
        }
        
        if (mc.thePlayer.inventory.currentItem == item) {
        	return;
        }
        
        mc.thePlayer.inventory.currentItem = item;
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(item));
        
    }

    private static int getStrongestItem(Block block) {
    	
        float strength = Float.NEGATIVE_INFINITY;
        int strongest = -1;
        
        for (int i = 0; i < 9; i++) {
        	
            float itemStrength;
            
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            
            if (itemStack != null && itemStack.getItem() != null && (itemStrength = getStrengthAgainstBlock(block, itemStack)) > strength && itemStrength != 1.0f) {
            	
                strongest = i;
                strength = itemStrength;
                
            }
            
        }
        
        return strongest;
        
    }

    public static float getStrengthAgainstBlock(Block block, ItemStack item) {
        float strength = item.getStrVsBlock(block);
        if (!EnchantmentHelper.getEnchantments(item).containsKey(Enchantment.efficiency.effectId) || strength == 1.0f) {
            return strength;
        }
        int enchantLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, item);
        return strength + (float)(enchantLevel * enchantLevel + 1);
    }
	
}
