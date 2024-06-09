package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketPlayer;


public class InvertoryMove extends Module{
	public InvertoryMove() {
		super("InvMove", 0, Category.PLAYER);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eInvertoryMove \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eInvertoryMove \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	@Override
	public void onUpdate() {
		if(this.isToggled()){
			KeyBinding[] moveKeys = new KeyBinding[] {
	                mc.gameSettings.keyBindForward,
	                mc.gameSettings.keyBindBack,
	                mc.gameSettings.keyBindLeft,
	                mc.gameSettings.keyBindRight,
	                mc.gameSettings.keyBindJump,
	                
	        };
	        if (mc.currentScreen instanceof GuiContainer) {
	            for (KeyBinding bind : moveKeys) {
	                KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
	            }
	        } else if (mc.currentScreen == null) {
	            for (KeyBinding bind : moveKeys) {
	                if (!Keyboard.isKeyDown(bind.getKeyCode())) {
	                    KeyBinding.setKeyBindState(bind.getKeyCode(), false);
		}
	super.onUpdate();
	}
	        }
		}
	}
}



