package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import static src.Wiksi.utils.client.IMinecraft.mc;

@FunctionRegister(name = "ElytraFlyHw", type = Category.Movement)
public class ElytraFlyHw {
    private final ModeSetting mod = new ModeSetting("Мод", "Holy", "Holy");

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (this.mod.is("Holy")) {
            float SPEED = 0.065F;
            Minecraft.getInstance();
            PlayerEntity player = mc.player;
            if (player != null && player.isAlive()) {
                player.setMotion(player.getMotion().x, player.getMotion().y + 0.06499999761581421D, player.getMotion().z);
            }
        }
    }
}

