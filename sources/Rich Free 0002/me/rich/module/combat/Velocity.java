package me.rich.module.combat;

import java.util.ArrayList;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class Velocity extends Feature {

	public Velocity() {
		super("Velocity", 0, Category.COMBAT);
		ArrayList<String> vel = new ArrayList<>();
		vel.add("Cancel");
		vel.add("MatrixVelocity");
		Main.instance.settingsManager.rSetting(new Setting("Velocity Mode", this, "Cancel", vel));
	}

	@EventTarget
	public void minet(EventUpdate event) {
    	String vel = Main.settingsManager.getSettingByName("Velocity Mode").getValString();
    	this.setModuleName("Velocity §7[" + vel + "]");
		if (vel.equalsIgnoreCase("MatrixVelocity")) {
			if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock() == Block.getBlockById(0)) {
				if (mc.player.hurtTime > 0) {
					float ticks = 0.2f;
					mc.player.motionY = -ticks;
					ticks += 1.5f;
				}
			}
		}
	}
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}
