package com.alan.clients.module.impl.player;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.player.antivoid.*;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.player.antivoid.name"}, description = "module.player.antivoid.description", category = Category.PLAYER)
public class AntiVoid extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new PacketAntiVoid("Packet", this))
            .add(new PositionAntiVoid("Position", this))
            .add(new VulcanAntiVoid("Vulcan", this))
            .add(new CollisionAntiVoid("Collision", this))
            .add(new WatchdogAntiVoid("Watchdog", this))
            .add(new BlinkAntiVoid("Blink", this))
            .setDefault("Packet");
}