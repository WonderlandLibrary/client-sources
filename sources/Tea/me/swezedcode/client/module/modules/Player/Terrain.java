package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.init.Blocks;

public class Terrain extends Module {

	public Terrain() {
		super("Terrain", Keyboard.KEY_NONE, 0xFF612BCC, ModCategory.Motion);
	}

	@EventListener
	public void onPre(EventPreMotionUpdates e) {
		Blocks.ice.slipperiness = 0.4F;
		Blocks.packed_ice.slipperiness = 0.4F;
	}

	@Override
	public void onDisable() {
		Blocks.ice.slipperiness = 0.89F;
		Blocks.packed_ice.slipperiness = 0.89F;
	}

}
