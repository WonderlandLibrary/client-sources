package club.strifeclient.module.implementations.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.Client;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.module.implementations.movement.Speed;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.util.player.ChatUtil;
import club.strifeclient.util.player.MovementUtil;
import club.strifeclient.util.system.Stopwatch;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;

import java.util.function.Supplier;

@ModuleInfo(name = "Criticals", description = "Always do critical hits.", category = Category.COMBAT)
public final class Criticals extends Module {

    private final ModeSetting<CriticalsMode> modeSetting = new ModeSetting<>("Criticals", CriticalsMode.WATCHDOG);

    private KillAura killAura;
    private Speed speed;

    private final Stopwatch stopwatch = new Stopwatch();

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
        if (killAura == null)
            killAura = Client.INSTANCE.getModuleManager().getModule(KillAura.class);
        if (speed == null)
            speed = Client.INSTANCE.getModuleManager().getModule(Speed.class);
        if (!killAura.getMultiTargets().isEmpty())
            killAura.getMultiTargets().forEach(target -> crit(e, target));
        else crit(e, getTarget());
    };

    private void crit(final MotionEvent e, final EntityLivingBase target) {
        if (canCrit(target)) {
            if (mc.thePlayer.ticksExisted % 11 == 0) return;
            e.y += Math.random() / 1000.0F;
            e.ground = false;
            ChatUtil.sendMessageWithPrefix("crit");
        }
    }

    public EntityLivingBase getTarget() {
        if (killAura.isEnabled()) {
            if (killAura.getNextTarget() != null)
                return killAura.getNextTarget();
        } else {
            final MovingObjectPosition over = mc.objectMouseOver;
            if (over != null && over.entityHit instanceof EntityLivingBase)
                return (EntityLivingBase) over.entityHit;
        }
        return null;
    }

    public boolean canCrit(final EntityLivingBase target) {
        return !speed.isEnabled() && !MovementUtil.isInLiquid() && mc.thePlayer.onGround && target != null;
    }

    @Override
    public Supplier<Object> getSuffix() {
        return () -> modeSetting.getValue().getName();
    }

    public enum CriticalsMode implements SerializableEnum {
        WATCHDOG("Watchdog");
        final String name;
        CriticalsMode(final String name) {
            this.name = name;
        }
        @Override
        public String getName() {
            return name;
        }
    }

}
