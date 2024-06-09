package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import org.lwjgl.input.Keyboard;

public class FastBow extends Module {

	public FastBow() {
		super("FastBow", "fastbow", 0, Category.COMBAT, new String[] { "" });
	}

	public void onUpdate() {
		if (this.isToggled()) {
			if (Wrapper.getPlayer().getHealth() > 0 && Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode && Wrapper.getPlayer().inventory.getCurrentItem() != null && Wrapper.getPlayer().inventory.getCurrentItem().getItem() instanceof ItemBow && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed) {
				Minecraft.getMinecraft().playerController.sendUseItem(Wrapper.getPlayer(), Minecraft.getMinecraft().theWorld, Wrapper.getPlayer().inventory.getCurrentItem());
				Wrapper.getPlayer().inventory.getCurrentItem().getItem().onItemRightClick(Wrapper.getPlayer().inventory.getCurrentItem(), Minecraft.getMinecraft().theWorld, Wrapper.getPlayer());
				for (int i = 0; i < 20; i++)
					Wrapper.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer(false));
				Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
				Wrapper.getPlayer().inventory.getCurrentItem().getItem().onPlayerStoppedUsing(Wrapper.getPlayer().inventory.getCurrentItem(), Minecraft.getMinecraft().theWorld, Wrapper.getPlayer(), 10);
			}

		}
	}

}