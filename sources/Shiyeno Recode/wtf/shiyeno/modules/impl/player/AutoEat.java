package wtf.shiyeno.modules.impl.player;

import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "AutoEat",
        type = Type.Player
)
public class AutoEat extends Function {
    private boolean isEating = false;

    public AutoEat() {
    }

    public void onEvent(Event event) {
        if (mc.player != null && mc.world != null) {
            if (event instanceof EventUpdate) {
                EventUpdate e = (EventUpdate)event;
                mc.gameSettings.keyBindUseItem.pressed = this.isEating;
                if (mc.player.getFoodStats().getFoodLevel() < 15) {
                    int slot = this.findEatSlot();
                    if (slot == -1) {
                        return;
                    }

                    mc.player.inventory.currentItem = slot;
                    this.isEating = true;
                } else {
                    this.isEating = mc.player.getFoodStats().needFood();
                }
            }
        }
    }

    public int findEatSlot() {
        for(int slot = 0; slot < 9; ++slot) {
            ItemStack stack = mc.player.inventory.getStackInSlot(slot);
            if (stack.getUseAction() == UseAction.EAT) {
                return slot;
            }
        }

        return -1;
    }
}