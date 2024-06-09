/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventMovementUpdate;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

/**
 * @author DistastefulBannock
 *
 */
public class ModStep extends Module {
	
	public ModStep() {
		super("Step", Category.MOVEMENT);
		setSettings(mode);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "0.38 Motion", "Vulcan", "Hypixel", "Test");
	
	private int ticks = 0;
	private boolean isStepping = false, expandJump = false;
	private float[] movementInput = new float[2];
	private long lastS08Millis = 0;
	
	@Override
	public void onEnable() {
		ticks = 0;
		isStepping = false;
		movementInput[0] = mc.thePlayer.moveForward;
		movementInput[1] = mc.thePlayer.moveStrafing;
	}
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		setInfo(mode.getMode());
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "Vanilla":{
				if (MovementUtils.canStep(1) && MovementUtils.isTrueOnGround()) {
					thePlayer.setPosition(thePlayer.posX, thePlayer.posY + 1f, thePlayer.posZ);
				}
			}break;
			case "0.38 Motion":{
				if (MovementUtils.canStep(1.1) && !MovementUtils.canStep(0.6) && MovementUtils.isTrueOnGround()) {
					thePlayer.motionY = 0.38;
				}
			}break;
			case "Vulcan":{
				if (!isStepping && thePlayer.ticksExisted % 2 == 0 && MovementUtils.isPlayerMoving() && MovementUtils.canStep(1.1) && !MovementUtils.canStep(0.6) && MovementUtils.isTrueOnGround() && thePlayer.motionY < -0.01) {
					isStepping = true;
					ticks = 0;
				}
				ticks++;
				if (isStepping) {
					switch (ticks) {
						case 1:{
							thePlayer.offsetPosition(0, 0.41999998688697815f, 0);
						}break;
						case 2:{
							isStepping = false;
						}break;
					}
				}
			}break;
			case "Hypixel":{
				if (MovementUtils.canStep(1.01) && !MovementUtils.canStep(0.8) && MovementUtils.isTrueOnGround()) {
					ticks = 0;
					isStepping = true;
					expandJump = false;
				}
				else if (MovementUtils.canStep(1.1) && !MovementUtils.canStep(1.01) && MovementUtils.isTrueOnGround()) {
					ticks = 0;
					isStepping = true;
					expandJump = true;
				}
				if (System.currentTimeMillis() - lastS08Millis < 500)
					isStepping = false;
				ticks++;
				if (isStepping) {
					/*
					 * 2022-09-25 12:18:40,900                 [Client thread] INFO  net.minecraft.client.gui.GuiNewChat - [CHAT] [ Kagu ] 0.41999998688697815
2022-09-25 12:18:40,955                 [Client thread] INFO  net.minecraft.client.gui.GuiNewChat - [CHAT] [ Kagu ] 0.33319999363422365
2022-09-25 12:18:41,003                 [Client thread] INFO  net.minecraft.client.gui.GuiNewChat - [CHAT] [ Kagu ] 0.24813599859094576
2022-09-25 12:18:41,056                 [Client thread] INFO  net.minecraft.client.gui.GuiNewChat - [CHAT] [ Kagu ] 0.16477328182606651
2022-09-25 12:18:41,104                 [Client thread] INFO  net.minecraft.client.gui.GuiNewChat - [CHAT] [ Kagu ] 0.08307781780646721
2022-09-25 12:18:41,154                 [Client thread] INFO  net.minecraft.client.gui.GuiNewChat - [CHAT] [ Kagu ] 0.0030162615090425808
					 */
					switch (ticks){
						case 1:{
//							thePlayer.offsetPosition(0, 0.4f, 0);
							thePlayer.onGround = false;
							thePlayer.motionY = 0.42f;
						}break;
						case 4:{
//							thePlayer.offsetPosition(0, 0.0030162615090425808f, 0);
							if (!expandJump)
								thePlayer.motionY *= 0.0;
						}break;
						case 5:{
//							thePlayer.offsetPosition(0, 0.0030162615090425808f, 0);
							thePlayer.motionY = -0.0784000015258789;
						}break;
						case 12:{
							isStepping = false;
						}break;
					}
					e.setPosY(thePlayer.posY);
				}
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventMovementUpdate> onMovementUpdate = e -> {
		switch (mode.getMode()) {
			case "Vulcan":{
				if (isStepping)
					e.cancel();
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
		if (e.isPost())
			return;
		switch (mode.getMode()) {
			case "Hypixel":{
				if (isStepping && e.getPacket() instanceof S08PacketPlayerPosLook) {
					lastS08Millis = System.currentTimeMillis();
					isStepping = false;
				}
			}break;
		}
	};
	
}
