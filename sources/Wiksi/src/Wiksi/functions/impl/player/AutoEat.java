package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@FunctionRegister(name = "AutoEat", type = Category.Player)
public class AutoEat extends Function {

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player == null || mc.world == null) return;

        if (mc.player.getFoodStats().getFoodLevel() < 12) {
            eatFood();
        }
    }

    private void eatFood() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            Food food = stack.getItem().getFood();
            if (food != null && food.getHealing() > 0) {
                mc.playerController.processRightClick(mc.player, mc.world, Hand.MAIN_HAND);
                return;
            }
        }
    }
}
