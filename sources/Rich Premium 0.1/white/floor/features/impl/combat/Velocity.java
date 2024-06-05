package white.floor.features.impl.combat;

import java.util.ArrayList;

import clickgui.setting.Setting;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class Velocity extends Feature {

    public Velocity() {
        super("Velocity", "Anti - KnockBack",0, Category.COMBAT);
        ArrayList<String> vel = new ArrayList<>();
        vel.add("Cancel");
        vel.add("MatrixVelocity");
        Main.instance.settingsManager.rSetting(new Setting("Velocity Mode", this, "Cancel", vel));
    }

    @EventTarget
    public void minet(EventUpdate event) {
        String vel = Main.settingsManager.getSettingByName("Velocity Mode").getValString();
        this.setModuleName("Velocity "+ TextFormatting.GRAY +"[" + vel + "]");
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
    }

    public void onDisable() {
        super.onDisable();
    }
}
