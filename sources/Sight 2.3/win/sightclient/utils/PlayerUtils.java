package win.sightclient.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

public class PlayerUtils {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public static int getEmptyHotbarSlot() {
        for (int k = 0; k < 9; ++k) {
            if (mc.thePlayer.inventory.mainInventory[k] == null) {
                return k;
            }
        }
		return -1;
	}
	
    public static boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }
    
    public static Block getBlockAtPos(BlockPos inBlockPos) {
        IBlockState s = mc.theWorld.getBlockState(inBlockPos);
        return s.getBlock();
    }
    
    public static boolean isBad(final ItemStack item) {
    	return !(item.getItem() instanceof ItemArmor || item.getItem() instanceof ItemTool || item.getItem() instanceof ItemBlock || item.getItem() instanceof ItemSword || item.getItem() instanceof ItemEnderPearl || item.getItem() instanceof ItemFood || (item.getItem() instanceof ItemPotion && !isBadPotion(item))) && !item.getDisplayName().toLowerCase().contains(EnumChatFormatting.GRAY +"(right click)");
    }
    
	public static void damagePlayer(int type) {
		if (type == 0) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
			for (int i = 0; i < 49; i++) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0514865, mc.thePlayer.posZ, false));
		        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0618865, mc.thePlayer.posZ, false));
		        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-12, mc.thePlayer.posZ, false));
	        }
	        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
		} else if (type == 1) {
			if (mc.thePlayer.onGround) {
				final double offset = 0.4122222218322211111111F;
				final NetHandlerPlayClient netHandler = mc.getNetHandler();
				final EntityPlayerSP player = mc.thePlayer;
				final double x = player.posX;
				final double y = player.posY;
				final double z = player.posZ;
				for (int i = 0; i < 52; i++) {
					netHandler.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + offset, z, false));
					netHandler.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.000002737272, z, false));
					netHandler.addToSendQueueNoEvent(new C03PacketPlayer(false));
				}
				netHandler.addToSendQueueNoEvent(new C03PacketPlayer(true));
			}
		}
	}
    
    public static boolean isBadPotion(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
