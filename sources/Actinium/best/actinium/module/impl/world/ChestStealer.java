package best.actinium.module.impl.world;

import best.actinium.event.api.Callback;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.render.ChestGuiEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.TimerUtil;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

@ModuleInfo(
        name = "Chest Stealer",
        description = "Steals stuff from a chest.",
        category = ModuleCategory.WORLD
)
public class ChestStealer extends Module {
    private NumberProperty delay = new NumberProperty("Delay", this, 0, 100, 1000, 1);
    private BooleanProperty silent = new BooleanProperty("Silent",this,false);
    private TimerUtil timer = new TimerUtil();

    @Callback
    public void onSilent(ChestGuiEvent event) {}

    @Callback
    public void onClick(MotionEvent event) {
        if (this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)this.mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                if (container.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasTimeElapsed(this.delay.getValue())) {
                    this.mc.playerController.windowClick(container.windowId, i, 0, 1, this.mc.thePlayer);
                    this.timer.reset();
                }
                ++i;
            }
            if (this.isEmpty()) {
                this.mc.thePlayer.closeScreen();
            }
        }
    }

    private boolean isEmpty() {
        if (this.mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)this.mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() != null) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }
}
