package white.floor.features.impl.misc;

import clickgui.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketDisconnect;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;

public class AutoLeave extends Feature {

    public Setting distance;

    public AutoLeave() {
        super("AutoLeave", "", 0, Category.MISC);
        Main.settingsManager.rSetting(distance = new Setting("Distance to Entity", this, 15, 5, 50));
    }

    @EventTarget
    public void autoleave(EventUpdate eventUpdate) {
        for (EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (entityPlayer != mc.player) {
                if (mc.player.getDistanceToEntity(entityPlayer) <= distance.getValDouble()) {
                    mc.player.connection.handleDisconnect(new SPacketDisconnect());
                }
            }
        }
    }
}
