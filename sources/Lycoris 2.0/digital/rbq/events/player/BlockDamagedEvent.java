/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.player;

import net.minecraft.util.BlockPos;
import digital.rbq.events.Cancellable;
import digital.rbq.events.Event;

public final class BlockDamagedEvent
extends Cancellable
implements Event {
    private final BlockPos blockPos;

    public BlockDamagedEvent(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }
}

