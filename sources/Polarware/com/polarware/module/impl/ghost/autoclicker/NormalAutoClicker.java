package com.polarware.module.impl.ghost.autoclicker;

import com.polarware.module.impl.ghost.AutoClickerModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.event.impl.other.TickEvent;
import com.polarware.util.math.MathUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.BoundsNumberValue;
import net.minecraft.util.MovingObjectPosition;
import util.time.StopWatch;

public class NormalAutoClicker extends Mode<AutoClickerModule> {
    private final BoundsNumberValue cps = new BoundsNumberValue("CPS", this, 8, 14, 1, 20, 1);
    private final BooleanValue rightClick = new BooleanValue("Right Click", this, false);
    private final BooleanValue leftClick = new BooleanValue("Left Click", this, true);
    private final BooleanValue hitSelect = new BooleanValue("Hit Select", this, false);

    private final StopWatch clickStopWatch = new StopWatch();
    private int ticksDown, attackTicks;
    private long nextSwing;

    public NormalAutoClicker(String name, AutoClickerModule parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {

        this.attackTicks++;

        if (clickStopWatch.finished(this.nextSwing) && (!hitSelect.getValue() || ((hitSelect.getValue() && attackTicks >= 10) ||
                (mc.thePlayer.hurtTime > 0 && clickStopWatch.finished(this.nextSwing)))) && mc.currentScreen == null) {
            final long clicks = (long) (Math.round(MathUtil.getRandom(this.cps.getValue().intValue(), this.cps.getSecondValue().intValue())) * 1.5);

            if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                ticksDown++;
            } else {
                ticksDown = 0;
            }

            this.nextSwing = 1000 / clicks;

            if (rightClick.getValue() && mc.gameSettings.keyBindUseItem.isKeyDown() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
                PlayerUtil.sendClick(1, true);

                if (Math.random() > 0.9) {
                    PlayerUtil.sendClick(1, true);
                }
            }

            if (leftClick.getValue() && ticksDown > 1 && (Math.sin(nextSwing) + 1 > Math.random() || Math.random() > 0.25 || clickStopWatch.finished(4 * 50)) && !mc.gameSettings.keyBindUseItem.isKeyDown() && (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)) {
                PlayerUtil.sendClick(0, true);
            }

            this.clickStopWatch.reset();
        }
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        this.attackTicks = 0;
    };
}
