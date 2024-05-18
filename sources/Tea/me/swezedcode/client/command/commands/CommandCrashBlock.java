package me.swezedcode.client.command.commands;

import me.swezedcode.client.command.Command;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CommandCrashBlock extends Command {

	@Override
	public void executeMsg(String[] args) {
		if (mc.thePlayer.inventory.getStackInSlot(36) != null) {
			return;
		}
		if (!mc.thePlayer.capabilities.isCreativeMode) {
			error("YOU NEED TO BE CREATIVE DUMB NIGGER");
			return;
		}
		ItemStack localaI = new ItemStack(Blocks.anvil);
		localaI.setItemDamage(16384);
		localaI.setStackDisplayName("§6Lucky blocks");
		localaI.stackSize = 64;
		mc.thePlayer.inventory.armorInventory[0] = localaI;
		msg("The CrashBlock is now in your shoes!");
	}

	@Override
	public String getName() {
		return "block";
	}

}
