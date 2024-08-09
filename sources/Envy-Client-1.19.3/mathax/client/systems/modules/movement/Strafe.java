package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.algorithms.extra.MovementUtils;
import mathax.client.utils.player.MoveUtilV;
import net.minecraft.item.Items;

public class Strafe extends Module {

    public Strafe() {
        super(Categories.Movement, Items.AIR, "Allows you to strafe", "");
    }

    @EventHandler
    public void onTick() {
        MoveUtilV.strafe();
    }
}
