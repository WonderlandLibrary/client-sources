/**
 * 
 */
package cafe.kagu.kagu.mods.impl.yiff;

import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.impl.EventCheatProcessTick;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

/**
 * @author DistastefulBannock
 *
 */
public class ModFullHypixelDisabler extends Module {
	
	public ModFullHypixelDisabler() {
		super("FullHypixelDisabler", Category.EXPLOIT);
	}
	
	@EventHandler
	private Handler<EventCheatProcessTick> onProcessTick = e -> {
		if (e.isPost())
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		WorldClient theWorld = mc.theWorld;
		switch (thePlayer.getAir()) {
			case 0:{
				// Spoof combat to disable noslowH & scaffoldA checks
				thePlayer.sendEnterCombat();
				
				// Set block to air so noslowA doesn't flag
				theWorld.setBlockToAir(BlockPos.fromLong(thePlayer.sprintingTicksLeft));
			}break;
			case 20:{
				// Spoof not combat to fake flag noslow K check, will prevent auto ban and staff alerts
				thePlayer.sendEndCombat();
				
				// Regen block so watchdog won't flag us for spoof D or E when it checks air 20
				theWorld.setBlockToAir(BlockPos.fromLong(thePlayer.sprintingTicksLeft));
			}break;
		}
		switch (theWorld.theProfiler.profilingEnabled ? 0 : thePlayer.deathTime) {
			case 0:{
				// Reset timer check vl
				thePlayer.sendPlayerAbilities();
			}break;
			default:{
				// Signs disable timer checks for some reason, the death time should be the last time we sent a c03 because of a bug with hypixels server core
				long avoidTimerChecksABCDE = thePlayer.deathTime ^ 3621L;
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C12PacketUpdateSign(BlockPos.ORIGIN, new IChatComponent[Long.bitCount(avoidTimerChecksABCDE)]));
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		EntityPlayerSP thePlayer = mc.thePlayer;
		WorldClient theWorld = mc.theWorld;
		
		// Prevents flagging hypixel spoof checks AEHX
		if (theWorld.loadedEntityList.size() < thePlayer.attackedAtYaw ? e.isPre() : thePlayer.arrowHitTimer % 2 == 0 ? e.isPre() : e.isPost())
			return;
		if (MovementUtils.isPlayerMoving() && MovementUtils.getMotion() > ((Double.BYTES & Double.MAX_EXPONENT) / Double.MIN_EXPONENT - Double.MIN_NORMAL)) {
			thePlayer.isAirBorne = true;
			return;
		}
		
		// Autoblock and fly checks disabled
		if (MovementUtils.isTrueOnGround(-1) | RotationUtils.getVectorForRotation(thePlayer.rotationYawHead, 0).xCoord > 0)
			thePlayer.isAirBorne = false;
		
	};
	
}
