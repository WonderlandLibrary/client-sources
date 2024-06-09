package me.swezedcode.client.module.modules.Options;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Fight.KillAura;
import me.swezedcode.client.module.modules.Fight.Velocity;
import me.swezedcode.client.module.modules.Motion.Jesus;
import me.swezedcode.client.module.modules.Motion.LongHop;
import me.swezedcode.client.module.modules.Motion.Speed;
import me.swezedcode.client.module.modules.Player.MagicCarpet;
import me.swezedcode.client.module.modules.Player.NoSlow;
import me.swezedcode.client.module.modules.Visual.NameTags;
import me.swezedcode.client.module.modules.Visual.NoScoreBoard;
import me.swezedcode.client.module.modules.Visual.Outline;
import me.swezedcode.client.module.modules.World.InvCleaner;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;

public class MemeNames extends Module {

	public MemeNames() {
		super("MemeNames", Keyboard.KEY_NONE, 0xFFF78181, ModCategory.Options);
	}

	public static boolean enabled = false;

	@Override
	public void onEnable() {
		enabled = true;
	}

	@Override
	public void onDisable() {
		enabled = false;
	}

}
