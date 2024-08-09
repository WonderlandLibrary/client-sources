package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;

@FunctionRegister(name = "AutoSphera", type = Category.Player)
public class AutoSphera extends Function {
    public AutoSphera() {
    }

    @Subscribe
    public void onEvent(EventUpdate event) {
        if (event instanceof EventUpdate) {
            for (PlayerEntity player : mc.world.getPlayers()) {
                ItemStack mainHandStack = player.getHeldItemOffhand();
                if (!mainHandStack.isEmpty() && mainHandStack.getItem() instanceof SkullItem) {
                    mc.player.rotationYaw = this.rotations(player)[0];
                    mc.player.rotationPitch = this.rotations(player)[1];
                    break; // Перестает перебирать игроков после того, как найдет одного с головой игрока
                }
            }
        }

    }

    public float[] rotations(Entity entity) {
        double x = entity.getPosX() - mc.player.getPosX();
        double y = entity.getPosY() - mc.player.getPosY() - 1.5;
        double z = entity.getPosZ() - mc.player.getPosZ();

        double u = Math.sqrt(x * x + z * z);
        float u2 = (float) (Math.atan2(z, x) * 57.29577951308232 - 90.0);
        float u3 = (float) (-Math.atan2(y, u) * 57.29577951308232);
        return new float[]{u2, u3};

    }
}