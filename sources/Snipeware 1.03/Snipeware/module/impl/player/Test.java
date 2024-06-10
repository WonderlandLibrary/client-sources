package Snipeware.module.impl.player;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.events.render.EventRender2D;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.TimeHelper;
import Snipeware.value.impl.EnumValue;
import font.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Test extends Module {

	public int rar = -50;

	public TimeHelper timer = new TimeHelper();

	public Test() {
		super("Test", Keyboard.KEY_NONE, ModuleCategory.PLAYER);
	
	}
	
	@Handler
	public void a(EventRender2D event) {

	}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		mc.thePlayer.prevPosY = 0;
	}
	
	@Handler
	public void onMove(final EventMove event) {
		
	}
	
	
	public void onEnable() {

		super.onEnable();
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		super.onDisable();
	}
	

}