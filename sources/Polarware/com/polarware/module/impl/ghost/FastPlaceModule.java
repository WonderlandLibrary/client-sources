package com.polarware.module.impl.ghost;

import com.polarware.component.impl.player.SlotComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.impl.NumberValue;
import net.minecraft.item.ItemBlock;

/**
 * @author Alan
 * @since 29/01/2021
 */

@ModuleInfo(name = "module.ghost.fastplace.name", description = "module.ghost.fastplace.description", category = Category.GHOST)
public class FastPlaceModule extends Module {

    private final NumberValue delay = new NumberValue("Delay", this, 0, 0, 3, 1);

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (SlotComponent.getItemStack() != null && SlotComponent.getItemStack().getItem() instanceof ItemBlock) {
            mc.rightClickDelayTimer = Math.min(mc.rightClickDelayTimer, this.delay.getValue().intValue());
        }
    };
}