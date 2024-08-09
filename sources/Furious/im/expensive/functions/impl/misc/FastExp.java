package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.TickEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@FunctionRegister(
        name = "FastEXP",
        type = Category.Miscellaneous
)
public class FastExp extends Function {
    @Subscribe
    public void onEvent(TickEvent e) {
        if (mc.player != null && mc.world != null) {
            ItemStack itemStack = mc.player.getHeldItemMainhand();

            if (itemStack.getItem() == Items.EXPERIENCE_BOTTLE) {
                mc.rightClickDelayTimer = (int) 0.15f;
            }
        }
    }
}