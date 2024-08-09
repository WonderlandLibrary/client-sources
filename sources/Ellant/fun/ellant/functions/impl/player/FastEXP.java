package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.TickEvent;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@FunctionRegister(
        name = "FastEXP",
        type = Category.PLAYER, desc = "Быстрое использование опыта"
)
public class FastEXP extends Function {
    final Minecraft mc = Minecraft.getInstance();

    @Subscribe
    public void onEvent(TickEvent e) {
        if (mc.player != null && mc.world != null) {
            ItemStack itemStack = mc.player.getHeldItemMainhand();

            if (itemStack.getItem() == Items.EXPERIENCE_BOTTLE) {
                mc.rightClickDelayTimer = 0;
            }
        }
    }
}