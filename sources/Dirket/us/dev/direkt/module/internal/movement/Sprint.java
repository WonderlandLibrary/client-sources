package us.dev.direkt.module.internal.movement;

import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.SyslogAppender;
import org.lwjgl.opengl.EXTPackedDepthStencil;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.util.client.MovementUtils;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Sprint", category = ModCategory.MOVEMENT)
public class Sprint extends ToggleableModule {

	@Listener
	protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
/*		Wrapper.getSendQueue().getPlayerInfoMap().stream()
		.forEach(e -> Wrapper.sendChatMessage("/ban " + e.getGameProfile().getName()));*/
/*		Wrapper.getSendQueue().getPlayerInfoMap().stream()
		.filter(e -> e.getDisplayName() != null)
		.forEach(e -> {
			e.g
		});*/
		//System.out.println(Double.MAX_VALUE);
		//Wrapper.sendChatMessage("/tp import");
    	//System.out.println(0.42500 * (0 + Wrapper.getPlayer().stepHeight));
		//System.out.println(Wrapper.getPlayer().posY);
		//System.out.println(Wrapper.getPlayer().isSprinting() ? (Wrapper.getPlayer().moveForward < 0.0F ? 180.0F : (Wrapper.getPlayer().moveStrafing > 0.0F ? -(90.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F)) : ( Wrapper.getPlayer().moveStrafing < 0.0F ? (90.0F * (Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : 1.0F)) : 0))) * (Wrapper.getPlayer().stepHeight + ((9 + 2) + 2 / 13)) / 2 * (10 / (1 * 10) - 1 ) + (1 + 3 - 2 + 5 - 6.789) : 0);
		Wrapper.getPlayer().setSprinting(MovementUtils.isMoving(Wrapper.getPlayer())
				&& Wrapper.getPlayer().getFoodStats().getFoodLevel() > 6);
		if(Wrapper.getGameSettings().keyBindBack.isKeyDown() && Wrapper.onGround()) {
			Wrapper.getPlayer().motionX *= 1.25F;
			Wrapper.getPlayer().motionZ *= 1.25F;
		}
	});
	
}