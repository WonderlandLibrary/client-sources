package me.xatzdevelopments.xatz.client.modules;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.AbstractPacket;
import net.minecraft.network.play.server.S02PacketChat;

public class NBTViewer extends Module {


	public NBTViewer() {
		super("NBTViewer", Keyboard.KEY_NONE, Category.RENDER,
				"Shows you all the NBT data on an item/book compound by hovering over it.");
	}

	

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {

		super.onUpdate();
	}
}