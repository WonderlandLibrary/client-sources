/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventKeyUpdate;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import cafe.kagu.kagu.utils.SpoofUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author lavaflowglow
 *
 */
public class ModFly extends Module {
	
	public ModFly() {
		super("Fly", Category.MOVEMENT);
		setSettings(mode);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "Verus", "AirHop", "Vulcan Glide");
	
	private int airhopY = 0;
	
	private int verusFlyTicks = 0;
	private double verusMotion = 0;
	private boolean verusDamage = false;
	
	@Override
	public void onEnable() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		
		switch (mode.getMode()) {
			case "Verus":{
				if (MovementUtils.isTrueOnGround(-2) || MovementUtils.isTrueOnGround(-3.5)) {
					toggle();
					ChatUtils.addChatMessage("Error: Make sure you have at least 3.5 blocks of space above you");
					return;
				}
				NetworkManager networkManager = mc.getNetHandler().getNetworkManager();
				verusFlyTicks = 0;
				verusDamage = false;
				networkManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY + 3.0001, thePlayer.posZ, false));
				networkManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false));
				networkManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, true));
			}break;
			case "AirHop":{
				airhopY = (int)Math.round(thePlayer.posY);
			}break;
		}
	}
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		
		switch (mode.getMode()) {
			case "Verus":{
				setInfo("Verus Damage");
				mc.thePlayer.setSprinting(true);
				if (thePlayer.hurtTime > 0 && !verusDamage) {
					verusFlyTicks = 22;
					verusMotion = 7.5;
					thePlayer.offsetPosition(0, 3, 0);
					verusDamage = true;
				}
				
				if (verusDamage) {
					if (verusFlyTicks > 0) {
						if (MovementUtils.isPlayerMoving())
							MovementUtils.setMotion(verusMotion, thePlayer.rotationYaw);
						verusMotion *= 0.91;
						thePlayer.motionY = -0.0784000015258789;
						thePlayer.onGround = true;
						verusFlyTicks--;
						if (thePlayer.isCollidedHorizontally)
							toggle();
					}else {
						MovementUtils.setMotion(0);
						toggle();
					}
					if (MovementUtils.isTrueOnGround()) {
						toggle();
					}
				}
			}break;
			case "AirHop":{
				setInfo("AirHop");
				if (MovementUtils.isTrueOnGround()) {
					airhopY = (int)Math.round(thePlayer.posY);
				}
				if (!MovementUtils.isPlayerMoving()) {
					airhopY = (int)Math.round(thePlayer.posY);
					return;
				}
				if (thePlayer.posY <= airhopY) {
					thePlayer.jump();
					thePlayer.onGround = true;
					((EventPlayerUpdate)e).setOnGround(true);
					((EventPlayerUpdate)e).setPosY(Math.round(((EventPlayerUpdate)e).getPosY()));
					airhopY = (int)((EventPlayerUpdate)e).getPosY();
					thePlayer.setPosition(thePlayer.posX, ((EventPlayerUpdate)e).getPosY(), thePlayer.posZ);
				}
			}break;
			case "Vanilla":{
				setInfo("Vanilla");
				MovementUtils.setMotion(MovementUtils.isPlayerMoving() ? 1 : 0);
				if (mc.gameSettings.keyBindJump.isKeyDown())
					thePlayer.motionY = 0.4;
				else if (mc.gameSettings.keyBindSneak.isKeyDown())
					thePlayer.motionY = -0.4;
				else
					thePlayer.motionY = 0;
				((EventPlayerUpdate)e).setOnGround(true);
				setInfo(mode.getMode());
			}break;
			case "Vulcan Glide":{
				setInfo("Vulcan Glide");
				thePlayer.motionY = thePlayer.ticksExisted % 3 != 0 ? Math.max(-0.1, thePlayer.motionY) : thePlayer.motionY;
			}break;
		}
		
	};
	
	@EventHandler
	private Handler<EventKeyUpdate> onKeyUpdate = e -> {
		if (e.isPost() || !((EventKeyUpdate)e).isPressed())
			return;
		
		EventKeyUpdate event = (EventKeyUpdate)e;
		GameSettings gameSettings = mc.gameSettings;
		
		switch (mode.getMode()) {
			case "AirHop":{
				if (event.getKeyCode() == gameSettings.keyBindJump.getKeyCode()) {
					airhopY++;
					e.cancel();
				}
				else if (event.getKeyCode() == gameSettings.keyBindSneak.getKeyCode()) {
					airhopY--;
					e.cancel();
				}
			}break;
		}
	};
	
}
