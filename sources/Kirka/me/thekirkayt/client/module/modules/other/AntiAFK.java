/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.other;

import java.util.Random;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

@Module.Mod
public class AntiAFK
extends Module {
    public int ticks;
    private BlockPos block;
    private Random random;
    private BlockPos nextBlock;

    @EventTarget
    public void moveEntity(UpdateEvent event) {
        if (ClientUtils.player().isDead) {
            ClientUtils.player().respawnPlayer();
        }
        ++this.ticks;
        if (this.ticks == 10 && ClientUtils.player().onGround) {
            ClientUtils.player().jump();
        }
        if (this.ticks == 25 && ClientUtils.player().onGround) {
            ClientUtils.player().jump();
        }
        if (this.ticks == 55 && ClientUtils.player().onGround) {
            ClientUtils.player().jump();
        }
        if (this.ticks >= 65 && ClientUtils.player().onGround) {
            ClientUtils.player().jump();
            this.ticks = 0;
        }
    }

    @EventTarget
    public void moveEntity(MoveEvent event) {
    }

    public void onDisabled() {
        super.disable();
        this.ticks = 0;
    }
}

