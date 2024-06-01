package com.polarware.module.impl.player;

import com.polarware.component.impl.player.GUIDetectionComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.math.MathUtil;
import com.polarware.util.player.ItemUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.BoundsNumberValue;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import util.time.StopWatch;


@ModuleInfo(name = "module.player.stealer.name", description = "module.player.stealer.description", category = Category.PLAYER)
public class StealerModule extends Module {

    private final BoundsNumberValue delay = new BoundsNumberValue("Delay", this, 100, 150, 0, 500, 50);
    private final BooleanValue ignoreTrash = new BooleanValue("Ignore Trash", this, true);

    private final StopWatch stopwatch = new StopWatch();
    private long nextClick;
    private int lastClick;
    private int lastSteal;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.currentScreen instanceof GuiChest) {
            final ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;

            if (GUIDetectionComponent.inGUI() || !this.stopwatch.finished(this.nextClick)) {
                return;
            }

            this.lastSteal++;

            for (int i = 0; i < container.inventorySlots.size(); i++) {
                final ItemStack stack = container.getLowerChestInventory().getStackInSlot(i);

                if (stack == null || this.lastSteal <= 1) {
                    continue;
                }

                if (this.ignoreTrash.getValue() && !ItemUtil.useful(stack)) {
                    continue;
                }

                this.nextClick = Math.round(MathUtil.getRandom(this.delay.getValue().intValue(), this.delay.getSecondValue().intValue()));
                mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                this.stopwatch.reset();
                this.lastClick = 0;
                if (this.nextClick > 0) return;
            }

            this.lastClick++;

            if (this.lastClick > 1) {
                mc.thePlayer.closeScreen();
            }
        } else {
            this.lastClick = 0;
            this.lastSteal = 0;
        }
    };
}