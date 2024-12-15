package com.alan.clients.module.impl.ghost;

import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.BoundsNumberValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;

@ModuleInfo(aliases = {"module.ghost.legitscaffold.name", "Eagle"}, description = "module.ghost.eagle.description", category = Category.GHOST)
public class Eagle extends Module {
    private final BoundsNumberValue length = new BoundsNumberValue("Length", this, 1,1, 1, 4, 1);
    private final NumberValue slow = new NumberValue("Sneak speed multiplier", this, 0.3, 0.2, 1, 0.05);
    private final BooleanValue groundOnly = new BooleanValue("Only on ground", this, false);
    private final BooleanValue blocksOnly = new BooleanValue("Only when holding blocks", this, false);
    private final BooleanValue backwardsOnly = new BooleanValue("Only when moving backwards", this, false);
    private final BooleanValue onlyOnSneak = new BooleanValue("Only on Sneak", this, true);

    private boolean sneaked;
    private int ticksOverEdge, sneakTicks;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.getHeldItem() != null && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) &&
                blocksOnly.getValue()) {
            if (sneaked) {
                sneaked = false;
            }
            return;
        }

        if ((mc.thePlayer.onGround || !groundOnly.getValue()) &&
                (PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof BlockAir) &&
                (!mc.gameSettings.keyBindForward.isKeyDown() || !backwardsOnly.getValue())) {
            if (!sneaked) {
                sneaked = true;
            }
        } else if (sneaked) {
            sneaked = false;
        }

        if (sneaked) {
            sneakTicks = length.getRandomBetween().intValue();
            mc.gameSettings.keyBindSprint.setPressed(false);
        }

        if (sneaked) {
            ticksOverEdge++;
        } else {
            ticksOverEdge = 0;
        }
    };

    @Override
    public void onDisable() {
        if (sneaked) {
            sneaked = false;
        }
    }

    @EventLink
    public final Listener<MoveInputEvent> onMove = event -> {
        if (getComponent(Slot.class).getItemStack() != null && getComponent(Slot.class).getItemStack().getItem() instanceof ItemBlock) {
            event.setSneak(((sneakTicks > 0) && (mc.gameSettings.keyBindSneak.isKeyDown() || !onlyOnSneak.getValue())) ||
                    (mc.gameSettings.keyBindSneak.isKeyDown() && !onlyOnSneak.getValue()));
        }

        sneakTicks--;

        if (sneakTicks > 0 && ticksOverEdge <= 2) {
            event.setSneakSlowDownMultiplier(slow.getValue().doubleValue());
        }
    };
}