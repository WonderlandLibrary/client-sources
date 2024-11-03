package dev.stephen.nexus.module.modules.movement.fly;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.world.EventBlockShape;
import dev.stephen.nexus.module.modules.movement.Fly;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.util.shape.VoxelShapes;

public class AirWalkFly extends SubMode<Fly> {
    public AirWalkFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventBlockShape> eventBlockShapeListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getPos().getY() < mc.player.getBlockY()) {
            event.setShape(VoxelShapes.fullCube());
        }
    };
}
