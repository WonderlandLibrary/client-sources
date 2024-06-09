package me.swezedcode.client.module.modules.World;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.Tea;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AntiBot extends Module {

	private TimerUtils time;

	public AntiBot() {
		super("AntiBot", Keyboard.KEY_NONE, 0xFFFFFFFF, ModCategory.World);
		setDisplayName(getName() + " §7WatchDog");
	}

	private BooleanValue debugMessage = new BooleanValue(this, "Tell players removed", "", Boolean.valueOf(false));
	public BooleanValue watchdog = new BooleanValue(this, "Watchdog", "watchdog", Boolean.valueOf(true));
	public BooleanValue aac = new BooleanValue(this, "Advanced", "advanced", Boolean.valueOf(false));

	@Override
	public void onDisable() {
		if (watchdog.getValue()) {

		}
	}

	@EventListener
	public void onPre(EventPreMotionUpdates event) {
		if (watchdog.getValue()) {
			setDisplayName(getName() + " §7WatchDog");
			for (final EntityPlayer p : GuiPlayerTabOverlay.getPlayerList()) {
				if (p == null) {
					mc.theWorld.removeEntity(p);
				}
			}
		} else if (aac.getValue()) {
			setDisplayName(getName() + " §7Advanced");
			for (Object o : mc.theWorld.loadedEntityList) {
				Entity entity = (Entity) o;
				if ((entity instanceof EntityPlayer) && (entity != mc.thePlayer)
						&& (entity.getEntityId() > 1070000000)) {
					for (int i = 0; i < 1; i++) {
						if (entity.motionY == 0.0 && !entity.isCollidedVertically && entity.posY % 1.0 != 0.0
								&& entity.posY % 0.5 != 0.0 && entity.posY % 1.5 != 0.0) {
							mc.theWorld.removeEntity(entity);
							entity.setInvisible(true);
							if (debugMessage.getValue()) {
								msg("Antibot just removed an entity with name as " + entity.getName());
							}
						}
					}
				}
			}
		}
	}

}
