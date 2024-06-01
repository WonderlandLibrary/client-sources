package com.polarware.module.impl.ghost;

import com.polarware.component.impl.player.SlotComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.value.impl.BooleanValue;
import net.minecraft.item.ItemBlock;

/**
 * @author Alan
 * @since 29/01/2021
 */

@ModuleInfo(name = "module.ghost.safewalk.name", description = "module.ghost.safewalk.description", category = Category.GHOST)
public class SafeWalkModule extends Module {

    private final BooleanValue blocksOnly = new BooleanValue("Blocks Only", this, false);
    private final BooleanValue backwardsOnly = new BooleanValue("Backwards Only", this, false);

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        mc.thePlayer.safeWalk = mc.thePlayer.onGround && (!mc.gameSettings.keyBindForward.isKeyDown() || !backwardsOnly.getValue()) &&
                ((SlotComponent.getItemStack() != null && SlotComponent.getItemStack().getItem() instanceof ItemBlock) ||
                        !this.blocksOnly.getValue());
    };
}