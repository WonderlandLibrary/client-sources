//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@FunctionRegister(
        name = "FastEXP",
        type = Category.Player
)
public class FastEXP extends Function {
    public FastEXP() {
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        this.fastEXP();
    }

    public void fastEXP() {
        if (mc.player != null) {
            ItemStack mainhandItem = mc.player.getHeldItemMainhand();
            if (!mainhandItem.isEmpty() && mainhandItem.getItem() == Items.EXPERIENCE_BOTTLE) {
                mc.rightClickDelayTimer = 0;
                return;
            }

            ItemStack offhandItem = mc.player.getHeldItemOffhand();
            if (!offhandItem.isEmpty() && offhandItem.getItem() == Items.EXPERIENCE_BOTTLE) {
                mc.rightClickDelayTimer = 0;
            }
        }

    }
}
