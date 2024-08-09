// DO NOT DISTRIBUTE! ALL RIGHTS RESERVED BY LATTIA CLIENT (MIEP & LATTIA/LAGOON)

package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public final class Rocket extends Module {


    public Rocket() {
        super(Categories.Movement, "Rocket", "Rockets! ");
    }


    @Override
    public void onActivate() {
        super.onActivate();

        assert mc.interactionManager != null;
        assert mc.player != null;
        int currSlot = mc.player.getInventory().selectedSlot;
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == Items.FIREWORK_ROCKET) {
                mc.player.getInventory().selectedSlot = i;
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                mc.player.getInventory().selectedSlot = currSlot;
                break;
            }
        }
        this.toggle();
    }
}
