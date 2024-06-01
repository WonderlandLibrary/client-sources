package com.polarware.module.impl.player;

import com.polarware.component.impl.player.SlotComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.other.BlockDamageEvent;
import com.polarware.util.player.SlotUtil;
import net.minecraft.util.BlockPos;

/**
 * @author Alan (made good code)
 * @since 24/06/2023
 */

@ModuleInfo(name = "module.player.autotool.name", description = "module.player.autotool.description", category = Category.PLAYER)
public class AutoToolModule extends Module {

    private int slot, lastSlot = -1;
    private int blockBreak;
    private BlockPos blockPos;

    @EventLink(Priority.VERY_HIGH)
    public final Listener<BlockDamageEvent> onBlockDamage = event -> {
        blockBreak = 3;
        blockPos = event.getBlockPos();
    };

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        switch (mc.objectMouseOver.typeOfHit) {
            case BLOCK:
                if (blockPos != null && blockBreak > 0) {
                    slot = SlotUtil.findTool(blockPos);
                } else {
                    slot = -1;
                }
                break;

            case ENTITY:
                slot = SlotUtil.findSword();
                break;

            default:
                slot = -1;
                break;
        }

        if (lastSlot != -1) {
            SlotComponent.setSlot(lastSlot);
        } else if (slot != -1) {
            SlotComponent.setSlot(slot);
        }

        lastSlot = slot;
        blockBreak--;
    };

}