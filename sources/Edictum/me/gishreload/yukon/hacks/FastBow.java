package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemNameTag;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;


public class FastBow extends Module{
	public  FastBow() {
		super("FastBow", 0, Category.COMBAT);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eFastBow \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eFastBow \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	@Override
	public void onUpdate() {
		if(this.isToggled()){
			if(mc.thePlayer.getHealth() > 0
					&& (mc.thePlayer.onGround || Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
					&& mc.thePlayer.inventory.getCurrentItem() != null
					&& mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
					&& mc.gameSettings.keyBindUseItem.pressed)
				{
					mc.playerController.processRightClick(mc.thePlayer, mc.theWorld,
						mc.thePlayer.inventory.getCurrentItem(), EnumHand.MAIN_HAND);
					mc.thePlayer.inventory
						.getCurrentItem()
						.getItem()
						.onItemRightClick(mc.thePlayer.inventory.getCurrentItem(),
							mc.theWorld, mc.thePlayer, EnumHand.MAIN_HAND);
					for(int i = 0; i < 20; i++)
						mc.thePlayer.connection.sendPacket(new CPacketPlayer(false));
					Minecraft
						.getMinecraft()
						.getConnection()
						.sendPacket(
							new CPacketPlayerDigging(Action.RELEASE_USE_ITEM,
								new BlockPos(0, 0, 0), EnumFacing.DOWN));
					mc.thePlayer.inventory
						.getCurrentItem()
						.getItem()
						.onPlayerStoppedUsing(mc.thePlayer.inventory.getCurrentItem(),
							mc.theWorld, mc.thePlayer, 10);
		}
	    }
	
	super.onUpdate();
	}
	}



