package com.alan.clients.module.impl.combat.criticals;

import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.init.Blocks;
import rip.vantage.commons.util.time.StopWatch;

public final class WatchdogCriticals extends Mode<Criticals> {
    private final NumberValue delay = new NumberValue("Delay", this, 500, 0, 1000, 50);

    private final double[] VALUES = new double[]{0.0625D, 0.015625D};
    private final StopWatch stopwatch = new StopWatch();

    private boolean attacked;
    private int ticks;

    private int swing;
    public WatchdogCriticals(String name, Criticals parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {







                    //    ChatUtil.display("velocity offseted");
                    if (mc.thePlayer.onGround) {
                        //    ChatUtil.display("velocity offseted");
                        ticks++;

                        switch (ticks) {
                            case 1: {
                                //  mc.thePlayer.motionY = 0.03495F;
                                // mc.thePlayer.motionY = 0.03495F;

                                event.setPosY(event.getPosY() + VALUES[0]);
                                //   ChatUtil.display("1");
                                //    ChatUtil.display("1");
                                break;
                            }

                            case 2: {

                                event.setPosY(event.getPosY() + VALUES[1]);
                                //     ChatUtil.display("2");
                                event.setPosY(event.getPosY() + VALUES[1]);
                                //       ChatUtil.display("2");
                                attacked = false;
                                break;
                            }
                        }

                        event.setOnGround(false);
                    } else {
                        attacked = false;
                        ticks = 0;
                    }




        };

        @EventLink
        public final Listener<AttackEvent> onAttackEvent = event -> {
            if (mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && stopwatch.finished(delay.getValue().longValue())) {
                if (mc.thePlayer.onGround && !mc.thePlayer.isOnLadder() && stopwatch.finished(delay.getValue().longValue())) {
                    mc.thePlayer.onCriticalHit(event.getTarget());

// ChatUtil.display("attack");
                    stopwatch.reset();

                }
            }
            }
            ;
        }


