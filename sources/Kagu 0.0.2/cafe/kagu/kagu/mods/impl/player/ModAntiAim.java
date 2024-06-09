/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import java.util.concurrent.ThreadLocalRandom;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.SpoofUtils;
import net.minecraft.item.ItemBow;

/**
 * @author lavaflowglow
 *
 */
public class ModAntiAim extends Module {
	
	public ModAntiAim() {
		super("AntiAim", Category.PLAYER);
		setSettings(yawSetting, pitchSetting);
	}
	
	private ModeSetting yawSetting = new ModeSetting("Yaw", "Fast spin", "Fast spin", "Slow spin", "180 + jitter", "Random");
	private ModeSetting pitchSetting = new ModeSetting("Pitch", "Down", "Down", "Up", "Middle", "Random");
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		mc.thePlayer.setLastReportedPitch(mc.thePlayer.getLastReportedPitch() + 1);
		mc.thePlayer.setLastReportedPitch(mc.thePlayer.getLastReportedYaw() + 1);
		
		if (mc.thePlayer.isUsingItem() && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow)
			return;
		
		// Yaw setting
		switch (yawSetting.getMode()) {
			case "Fast spin":{
				e.setRotationYaw(System.currentTimeMillis() % 360);
			}break;
			case "Slow spin":{
				e.setRotationYaw(mc.thePlayer.ticksExisted * 2);
			}break;
			case "180 + jitter":{
				e.setRotationYaw((float) (mc.thePlayer.rotationYaw + 180 + ((mc.thePlayer.ticksExisted % 6 > 3 ? 60 : -60) * Math.max(Math.random(), 0.6))));
			}break;
			case "Random":{
				e.setRotationYaw(ThreadLocalRandom.current().nextInt(360) + ThreadLocalRandom.current().nextFloat());
			}break;
		}
		
		// Pitch setting
		switch (pitchSetting.getMode()) {
			case "Down":{
				e.setRotationPitch(90);
			}break;
			case "Up":{
				e.setRotationPitch(-90);
			}break;
			case "Middle":{
				e.setRotationPitch(0);
			}break;
			case "Random":{
				e.setRotationPitch((ThreadLocalRandom.current().nextInt(180) + ThreadLocalRandom.current().nextFloat()) - 90);
			}break;
		}
		
		// Set the spoofed rotations so they actually render
		SpoofUtils.setSpoofedYaw(e.getRotationYaw());
		SpoofUtils.setSpoofedPitch(e.getRotationPitch());
		
		// Set the info
		setInfo(yawSetting.getMode(), pitchSetting.getMode());
		
	};
	
}
