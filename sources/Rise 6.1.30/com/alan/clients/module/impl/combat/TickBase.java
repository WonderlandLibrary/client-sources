package com.alan.clients.module.impl.combat;

import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.GameEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;

// Could be detected with keep alives possibly

@ModuleInfo(aliases = {"module.combat.tickbase.name"}, description = "module.combat.tickbase.description", category = Category.COMBAT)
public final class TickBase extends Module {
    private final NumberValue lagRange = new NumberValue("Range", this, 8, 5, 15, 0.1);
    private Mode MODE = Mode.NONE;
    private long time, balance;
    private double range, distance;
    Entity target;

    @EventLink
    public Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (MODE.equals(Mode.REDUCING)) {
            return;
        }

        target = TargetComponent.getTarget(20);
        if (target == null) return;

        distance = PlayerUtil.calculatePerfectRangeToEntity(target);
        double range = distance;

        if (range > 3 && balance >= 50 && MODE.equals(Mode.BASING)) {
            balance -= 50;
            mc.timer.elapsedTicks += 1;
        } else {
            if (balance != 0) {
                ChatUtil.display("Balance " + balance + " " + range);
            }
            balance = 0;
            MODE = Mode.NONE;
        }

        if ((/*range < 7 && this.range >= 7 || range < 6 && this.range >= 6 ||*/
                range < lagRange.getValue().doubleValue() && this.range >=
                        lagRange.getValue().doubleValue()) && MODE.equals(Mode.NONE)) {
            MODE = Mode.REDUCING;
            time = System.currentTimeMillis();
            balance = 0;
        }

        this.range = range;
    };

    @EventLink
    public Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (MODE.equals(Mode.REDUCING)) {
//            ThreadUtil.sleep(400);
//            System.out.println("Cancelled");
        }
    };

    @EventLink
    public Listener<GameEvent> onGameEvent = event -> {
        if (target == null || mc.thePlayer == null) return;

        distance = PlayerUtil.calculatePerfectRangeToEntity(target);
    };

    @EventLink
    public Listener<Render3DEvent> onRender3D = event -> {
        if (!MODE.equals(Mode.REDUCING) || target == null) return;

        if (distance <= 4 || System.currentTimeMillis() - time >= ((range / (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.36 : 0.25)) * 25) + 25) {
            mc.timer.timerSpeed = 1;
            MODE = Mode.BASING;
            balance = System.currentTimeMillis() - time;
            return;
        }

        mc.timer.timerSpeed = 0;
    };
}

enum Mode {REDUCING, BASING, NONE}
