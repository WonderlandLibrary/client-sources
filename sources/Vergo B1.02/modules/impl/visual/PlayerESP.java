package xyz.vergoclient.modules.impl.visual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventPlayerRender;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.modules.impl.miscellaneous.AntiBot;
import xyz.vergoclient.settings.ModeSetting;

import java.util.Arrays;

public class PlayerESP extends Module implements OnEventInterface {

	public PlayerESP() {
		super("PlayerESP", Category.VISUAL);

	}
	
	public ModeSetting mode = new ModeSetting("Mode", "Outline");
	
	@Override
	public void loadSettings() {
		mode.modes.clear();
		mode.modes.addAll(Arrays.asList("Outline"));

		addSettings(mode);
	}
	
	@Override
	public void onEvent(Event e) {
		if (mode.is("Outline")) {
			doTheFunny(e);
		}
	}

	private Vec3 getVec3(final EntityPlayer var0) {
		final float timer = mc.timer.renderPartialTicks;
		final double x = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * timer;
		final double y = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * timer;
		final double z = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * timer;
		return new Vec3(x, y, z);
	}
    
    private void doTheFunny(Event e) {
		int offset = 0;

		if (e instanceof EventPlayerRender && e.isPre()) {
//			ChatUtils.addChatMessage(positions.size());

			for (Object ent : mc.theWorld.loadedEntityList) {
				if (ent instanceof EntityPlayer && (Vergo.config.modAntibot.isDisabled() || !AntiBot.isBot(((EntityPlayer) ent)))) {

					EntityPlayer player = (EntityPlayer) ent;
				}
			}

		}
	}

}
