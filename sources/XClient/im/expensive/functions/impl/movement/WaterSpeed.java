package im.expensive.functions.impl.movement;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
@FunctionRegister(name = "WaterSpeed", type = Category.MOVEMENT)
public class WaterSpeed extends Function {

    ModeSetting type = new ModeSetting("Режим", "FT", "FT", "HW", "UNIVERSAL");

    public WaterSpeed() {
        addSettings(type);
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        String selectedType = type.get();

        if (selectedType.equals("UNIVERSAL")) {
            WATER_DEF();
        } else if (selectedType.equals("FT")) {
            WATER_FT();
        } else if (selectedType.equals("HW")) {
            WATER_HOLY();
        }
    }
    private void WATER_DEF() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive()) {
            if (player.isInWater()) {
                player.setMotion(player.getMotion().x * (double) 1.1, player.getMotion().y, player.getMotion().z * (double) 1.1);
            }
        }
    }
    private void WATER_FT() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive()) {
            if (player.isInWater()) {
                player.setMotion(player.getMotion().x * (double) 1.0505, player.getMotion().y, player.getMotion().z * (double) 1.0505);
            }
        }
    }
    private void WATER_HOLY() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive()) {
            if (player.isInWater()) {
                player.setMotion(player.getMotion().x * (double) 1.03, player.getMotion().y, player.getMotion().z * (double) 1.03);
            }
        }
    }
}