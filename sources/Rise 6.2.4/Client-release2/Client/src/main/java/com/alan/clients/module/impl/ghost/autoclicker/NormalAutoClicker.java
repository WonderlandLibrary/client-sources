package com.alan.clients.module.impl.ghost.autoclicker;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.module.impl.ghost.AutoClicker;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.BoundsNumberValue;
import net.minecraft.util.MovingObjectPosition;
import rip.vantage.commons.util.time.StopWatch;

public class NormalAutoClicker extends Mode<AutoClicker> {
    private final BoundsNumberValue cps = new BoundsNumberValue("CPS", this, 8, 14, 1, 20, 0.1);
    private final BooleanValue rightClick = new BooleanValue("Right Click", this, false);
    private final BooleanValue leftClick = new BooleanValue("Left Click", this, true);
    private final BooleanValue hitSelect = new BooleanValue("Hit Select", this, false);
    private final BooleanValue breakBlocks = new BooleanValue("Break Blocks", this, true);
    private final BooleanValue butterFly = new BooleanValue("ButterFly", this, true);

    private final StopWatch clickStopWatch = new StopWatch();
    private int ticksDown, attackTicks;
    private long nextSwing;

    public NormalAutoClicker(String name, AutoClicker parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        this.attackTicks++;

        if (clickStopWatch.finished(this.nextSwing) && (!hitSelect.getValue() || attackTicks >= 10 || mc.thePlayer.hurtTime > 0 && clickStopWatch.finished(this.nextSwing)) && mc.currentScreen == null) {
            final long clicks = (long) (this.cps.getRandomBetween().longValue() * 1.5);

            if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                ticksDown++;
            } else {
                ticksDown = 0;
            }

            if (this.nextSwing >= 50 * 2 && butterFly.getValue()) {
                this.nextSwing = (long) (Math.random() * 100);
            } else {
                this.nextSwing = 1000 / clicks;
            }

            if (rightClick.getValue() && mc.gameSettings.keyBindUseItem.isKeyDown() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
                PlayerUtil.sendClick(1, true);

                if (Math.random() > 0.9) {
                    PlayerUtil.sendClick(1, true);
                }
            }

            if (leftClick.getValue() && ticksDown > 1 && !mc.gameSettings.keyBindUseItem.isKeyDown() && (!breakBlocks.getValue() || mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)) {
                PlayerUtil.sendClick(0, true);
            } else if (!breakBlocks.getValue()) {
                mc.playerController.curBlockDamageMP = 0;
            }

            this.clickStopWatch.reset();
        }
    };

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        this.attackTicks = 0;
    };
}
