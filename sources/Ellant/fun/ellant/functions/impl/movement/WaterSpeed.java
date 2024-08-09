package fun.ellant.functions.impl.movement;

import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
@FunctionRegister(name = "WaterSpeed", type = Category.MOVEMENT, desc = "Ускоряет вас в воде")
public class WaterSpeed extends Function {

    ModeSetting type = new ModeSetting("Тип", "Обычные", "Funtime", "Holyworld", "Обычные");

    public WaterSpeed() {
        addSettings(type);
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        String selectedType = type.get();

        if (selectedType.equals("Обычные")) {
            WATER_DEF();
        } else if (selectedType.equals("Funtime")) {
            WATER_FT();
        } else if (selectedType.equals("Holyworld")) {
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