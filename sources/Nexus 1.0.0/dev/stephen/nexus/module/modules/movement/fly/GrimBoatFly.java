package dev.stephen.nexus.module.modules.movement.fly;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.movement.Fly;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.MoveUtils;
import net.minecraft.entity.vehicle.BoatEntity;

public class GrimBoatFly extends SubMode<Fly> {
    public GrimBoatFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    private boolean grimBoatSend;

    @Override
    public void onEnable() {
        grimBoatSend = false;
        super.onEnable();
    }
    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.hasVehicle() && mc.player.getVehicle() instanceof BoatEntity boat) {
            mc.player.setYaw(boat.getYaw());
            mc.player.setPitch(90f);
            grimBoatSend = true;
        }

        if (grimBoatSend && mc.player.hasVehicle()) {
            mc.options.sneakKey.setPressed(true);
        }

        if (grimBoatSend && !mc.player.hasVehicle()) {
            MoveUtils.setMotionY(1.8);
            grimBoatSend = false;
            mc.options.sneakKey.setPressed(false);
            getParentModule().toggle();
        }
    };
}
