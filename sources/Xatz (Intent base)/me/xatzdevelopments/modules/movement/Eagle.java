package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.events.listeners.EventUsingItem;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.MoveUtils;
import me.xatzdevelopments.util.PlayerUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Eagle extends Module{
	public Eagle() {
		super("Eagle", 0, Category.MOVEMENT, "Doesnt slow your movement");
	}
	
	public void onEvent(Event e) {
				if(mc.thePlayer != null && mc.theWorld != null) {
					ItemStack i = mc.thePlayer.getCurrentEquippedItem();
					BlockPos Bp = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1D, mc.thePlayer.posZ);
					if(i != null) {
						if(i.getItem() instanceof ItemBlock) {
							mc.gameSettings.keyBindSneak.pressed = false;
							if(mc.theWorld.getBlockState(Bp).getBlock() == Blocks.air) {
								mc.gameSettings.keyBindSneak.pressed = true;
							}
							
						}
			        }
		       }			
	       }
	   }
 