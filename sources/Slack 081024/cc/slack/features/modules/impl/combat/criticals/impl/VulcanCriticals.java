package cc.slack.features.modules.impl.combat.criticals.impl;

import cc.slack.start.Slack;
import cc.slack.events.impl.player.AttackEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.combat.Criticals;
import cc.slack.features.modules.impl.combat.criticals.ICriticals;
import cc.slack.features.modules.impl.movement.LongJump;
import cc.slack.features.modules.impl.movement.Speed;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.other.TimeUtil;

public class VulcanCriticals implements ICriticals {

    private final TimeUtil timer = new TimeUtil();


    @Override
    public void onUpdate(UpdateEvent event) {
        if (timer.hasReached(50) && !Slack.getInstance().getModuleManager().getInstance(LongJump.class).isToggle() && !Slack.getInstance().getModuleManager().getInstance(Speed.class).isToggle() && !mc.thePlayer.isEating()) {
            mc.timer.timerSpeed = 1.0F;
            timer.reset();
        }
    }

    @Override
    public void onAttack(AttackEvent event) {
        timer.reset();
        if (!Slack.getInstance().getModuleManager().getInstance(Speed.class).isToggle()) {
            if (mc.thePlayer.onGround) {
                if (Slack.getInstance().getModuleManager().getInstance(Criticals.class).vulcanTimer.getValue()) {
                    mc.timer.timerSpeed = 0.30f;
                }
            }
            PacketUtil.sendCriticalPacket(0.01, true);
            PacketUtil.sendCriticalPacket(0.003, false);
        }

    }

    @Override
    public String toString() {
        return "Vulcan";
    }
}
