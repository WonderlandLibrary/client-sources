package com.polarware.module.impl.player;


import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.player.nofall.*;
import com.polarware.value.impl.ModeValue;


/**
 * @author Alan
 * @since 23/10/2021
 */

@ModuleInfo(name = "module.player.nofall.name", description = "module.player.nofall.description", category = Category.PLAYER)
public class NoFallModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new SpoofNoFall("Spoof", this))
            .add(new NoGroundNoFall("No Ground", this))
            .add(new RoundNoFall("Round", this))
            .add(new PlaceNoFall("Place", this))
            .add(new PacketNoFall("Packet", this))
            .add(new InvalidNoFall("Invalid", this))
            .add(new ChunkLoadNoFall("Chunk Load", this))
            .add(new ClutchNoFall("Clutch", this))
            .add(new MatrixNoFall("Matrix", this))
            .add(new WatchdogNoFall("Watchdog", this))
            .add(new GrimNoFall("Grim", this))
            .setDefault("Spoof");
}
