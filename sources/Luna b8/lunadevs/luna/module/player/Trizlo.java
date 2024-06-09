package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Trizlo extends Module {

	private boolean active;

	public Trizlo() {
		super("Trizlo", Keyboard.KEY_NONE, Category.PLAYER, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if(zCore.gsettings().keyBindJump.pressed){
		zCore.p().setPosition( zCore.p().lastTickPosX, zCore.p().serverPosY+zCore.p().lastTickPosY+0.139F, zCore.p().serverPosZ+zCore.p().posZ);
			super.onUpdate();
	}}

	
	@Override
	public void onEnable() {
		active=true;
	}
	@Override
	public void onDisable() {
		active=false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "Jump";
	}

}
