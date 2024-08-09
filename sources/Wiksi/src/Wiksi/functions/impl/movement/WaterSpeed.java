//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

@FunctionRegister(
        name = "WaterSpeed",
        type = Category.Movement
)
public class WaterSpeed extends Function {
    ModeSetting type = new ModeSetting("Тип", "default", new String[]{"Funtime", "Holyworld", "default"});

    public WaterSpeed() {
        this.addSettings(new Setting[]{this.type});
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        String selectedType = (String)this.type.get();
        if (selectedType.equals("default")) {
            this.WATER_DEF();
        } else if (selectedType.equals("Funtime")) {
            this.WATER_FT();
        } else if (selectedType.equals("Holyworld")) {
            this.WATER_HOLY();
        }

    }

    private void WATER_DEF() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive() && player.isInWater()) {
            player.setMotion(player.getMotion().x * 1.1, player.getMotion().y, player.getMotion().z * 1.1);
        }

    }

    private void WATER_FT() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive() && player.isInWater()) {
            player.setMotion(player.getMotion().x * 1.0505, player.getMotion().y, player.getMotion().z * 1.0505);
        }

    }

    private void WATER_HOLY() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null && player.isAlive() && player.isInWater()) {
            player.setMotion(player.getMotion().x * 1.03, player.getMotion().y, player.getMotion().z * 1.03);
        }

    }
}
