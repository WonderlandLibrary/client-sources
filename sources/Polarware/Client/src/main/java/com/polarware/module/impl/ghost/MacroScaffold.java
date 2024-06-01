package com.polarware.module.impl.ghost;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.LegitClickEvent;
import com.polarware.event.impl.other.TickEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.BoundsNumberValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemBlock;
//todo make it automaticly rotate and have like a raycast and shit
@ModuleInfo(name = "Macro Scaffold", description = "Bud macro scaffold", category = Category.GHOST)
public class MacroScaffold extends Module {
    private final BooleanValue shouldsneak = new BooleanValue("Sneak", this, false);
    private final BooleanValue groundOnly = new BooleanValue("Only on ground", this, false,() -> !shouldsneak.getValue());
    private final BooleanValue blocksOnly = new BooleanValue("Only when holding blocks", this, false,() -> !shouldsneak.getValue());
    private final BooleanValue backwardsOnly = new BooleanValue("Only when moving backwards", this, false,() -> !shouldsneak.getValue());
    private final BooleanValue onlyOnSneak = new BooleanValue("Only on Sneak", this, true,() -> !shouldsneak.getValue());
    private final BoundsNumberValue length = new BoundsNumberValue("Drag Click Length", this, 17, 18, 1, 50, 1);
    private final BoundsNumberValue delay = new BoundsNumberValue("Delay Between Dragging", this, 6, 6, 1, 20, 1);

    private int nextLength, nextDelay;

    private boolean sneaked;
    private int ticksOverEdge;
    @EventLink
    public final Listener<LegitClickEvent> lefishonshoclate = event -> {
        if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            if (nextLength < 0) {
                nextDelay--;

                if (nextDelay < 0) {
                    nextDelay = delay.getRandomBetween().intValue();
                    nextLength = length.getRandomBetween().intValue();
                }
            } else if (Math.random() < 0.95) {
                nextLength--;
                PlayerUtil.sendClick(1, true);
            }
        }
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if(!shouldsneak.getValue()) {
            return;
        }
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
            mc.gameSettings.keyBindSprint.setPressed(false);
        }

        if (sneaked) {
            ticksOverEdge++;
        } else {
            ticksOverEdge = 0;
        }
    };

    @Override
    protected void onDisable() {
        PlayerUtil.sendClick(1, false);
        if (sneaked) {
            sneaked = false;
        }
    }

    @EventLink()
    public final Listener<MoveInputEvent> onMove = event -> {
        if(shouldsneak.getValue()) {
            event.setSneak((sneaked && (mc.gameSettings.keyBindSneak.isKeyDown() || !onlyOnSneak.getValue())) ||
                    (mc.gameSettings.keyBindSneak.isKeyDown() && !onlyOnSneak.getValue()));
            if (sneaked && ticksOverEdge <= 2) {
                event.setSneakSlowDownMultiplier(0.2);
            }
        }
    };
}
