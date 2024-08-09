package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.Setting;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;
import wtf.resolute.utiled.player.MoveUtils;

@ModuleAnontion(name = "WaterSpeed", type = Categories.Movement,server = "")
public class WaterSpeed extends Module {

    private static final float SPEED = 1.0010f;

    @Subscribe
    public void onPlayerUpdate(EventDisplay event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive()) {
            if (player.isInWater()) {
                player.setMotion(
                        player.getMotion().x * SPEED,
                        player.getMotion().y,
                        player.getMotion().z * SPEED
                );
            }
        }
    }
}
