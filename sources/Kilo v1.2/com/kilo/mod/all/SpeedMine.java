package com.kilo.mod.all;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class SpeedMine extends Module {
	
	public SpeedMine(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Speed", "Mining speed multiplier", Interactable.TYPE.SLIDER, 1, new float[] {1, 5}, true);
		addOption("Packet", "Change to packet mode", Interactable.TYPE.CHECKBOX, false, null, false);
	}
	
	public void onPlayerBlockBreaking(BlockPos pos, EnumFacing face) {
		if (mc.playerController.currentBlock != null) {
			if (!Util.makeBoolean(getOptionValue("packet"))) {
				mc.playerController.curBlockDamageMP+= mc.theWorld.getBlockState(pos).getBlock().getPlayerRelativeBlockHardness(mc.thePlayer, mc.thePlayer.worldObj, pos)*(Util.makeFloat(getOptionValue("speed"))-1);
			} else {
				mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, face));
				mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, face));
			}
		}
	}
}
