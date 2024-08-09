package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@FunctionRegister(name = "AutoEat", type = Category.PLAYER, desc = "Автоматически ест еду")
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
