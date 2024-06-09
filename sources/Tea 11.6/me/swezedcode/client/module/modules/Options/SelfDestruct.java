package me.swezedcode.client.module.modules.Options;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.darkmagician6.eventapi.EventListener;
import com.google.common.collect.Lists;

import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public class SelfDestruct extends Module {

	public static boolean shouldDestruct = false;

	public SelfDestruct() {
		super("SelfDestruct", Keyboard.KEY_NONE, 0xFFFFFFFF, ModCategory.Options);
	}
	
	public void onEnable() {
		shouldDestruct = true;
		this.setToggled(false);
	}
	
	@Override
	public void onDisable() {
		
	}

}
