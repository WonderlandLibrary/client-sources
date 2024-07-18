package com.alan.clients.module.impl.movement.teleport;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.movement.Teleport;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.value.Mode;
import net.minecraft.util.Vec3;

public final class WatchdogBedWarsTeleport extends Mode<Teleport> {

    public WatchdogBedWarsTeleport(String name, Teleport parent) {
        super(name, parent);
    }

    public Vec3 position = new Vec3(0, 0, 0);

    @Override
    public void onEnable() {
        position = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);

        ChatUtil.display("Die -> Fly to where you want to teleport -> Toggle");
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        event.setPosY(position.yCoord + 3);
        event.setPosX(position.xCoord);
        event.setPosZ(position.zCoord);
        event.setOnGround(false);
    };
}
