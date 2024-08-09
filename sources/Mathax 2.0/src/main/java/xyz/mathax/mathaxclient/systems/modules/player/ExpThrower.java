package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.item.Items;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;

public class ExpThrower extends Module {
    public ExpThrower(Category category) {
        super(category, "EXP Thrower", "Automatically throws XP bottles from your hotbar.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        FindItemResult exp = InvUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);
        if (!exp.found()) {
            return;
        }

        Rotations.rotate(mc.player.getYaw(), 90, () -> {
            if (exp.getHand() != null) {
                mc.interactionManager.interactItem(mc.player, exp.getHand());
            } else {
                InvUtils.swap(exp.slot(), true);
                mc.interactionManager.interactItem(mc.player, exp.getHand());
                InvUtils.swapBack();
            }
        });
    }
}