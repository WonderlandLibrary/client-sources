package com.polarware.module.impl.player;


import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.player.antivoid.*;
import com.polarware.value.impl.ModeValue;


/**
 * @author Alan
 * @since 23/10/2021
 */

@ModuleInfo(name = "module.player.antivoid.name", description = "module.player.antivoid.description", category = Category.PLAYER)
public class AntiVoidModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new PacketAntiVoid("Packet", this))
            .add(new PolarAntiVoid("Polar", this))
            .add(new PositionAntiVoid("Position", this))
            .add(new BlinkAntiVoid("Blink", this))
            .add(new BlinkAntiVoid("Watchdog", this))
            .add(new VulcanAntiVoid("Vulcan", this))
            .add(new CollisionAntiVoid("Collision", this))
            .setDefault("Packet");
}