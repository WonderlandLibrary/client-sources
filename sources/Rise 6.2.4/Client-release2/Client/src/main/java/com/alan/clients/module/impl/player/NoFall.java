package com.alan.clients.module.impl.player;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.player.nofall.*;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.player.nofall.name"}, description = "module.player.nofall.description", category = Category.PLAYER)
public class NoFall extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new SpoofNoFall("Spoof", this))
            .add(new FlagNoFall("Flag", this))
            .add(new NoGroundNoFall("No Ground", this))
            .add(new RoundNoFall("Round", this))
            .add(new PlaceNoFall("Place", this))
            .add(new PacketNoFall("Packet", this))
            .add(new InvalidNoFall("Invalid", this))
            .add(new ChunkLoadNoFall("Chunk Load", this))
            .add(new ClutchNoFall("Clutch", this))
            .add(new MatrixNoFall("Matrix", this))
            .add(new Watchdog2NoFall("Watchdog", this))
            .add(new WatchdogNoFall("Watchdog Blink", this))
            .add(new VulcanAndVerusNoFall("Watchdog Packet", this))
            .setDefault("Spoof");
}
