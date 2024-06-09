package me.swezedcode.client.module.modules.Options;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.FileUtils;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;

public class Music extends Module {

	public Music() {
		super("Music", Keyboard.KEY_NONE, 0xFF5CD672, ModCategory.Options);
	}
	
	public static float volume = -28.0F;
	
	@EventListener
	public void onPre(EventPreMotionUpdates e) {
		if (!FileUtils.clip.isRunning() && !ModuleUtils.getMod(Radio.class).isToggled()) {
			FileUtils.playSound(FileManager.getDirectory() + "/sound/sound.wav");
			FileUtils.setVolume(volume);
		}
	}

	@Override
	public void onDisable() {
		FileUtils.clip.stop();
	}

}
