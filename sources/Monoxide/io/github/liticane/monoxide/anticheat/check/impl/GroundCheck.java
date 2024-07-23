package io.github.liticane.monoxide.anticheat.check.impl;

import io.github.liticane.monoxide.util.world.block.BlockUtil;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import io.github.liticane.monoxide.anticheat.check.Check;
import io.github.liticane.monoxide.anticheat.data.PlayerData;

@Check.Info(name = "GroundSpoof")
public class GroundCheck extends Check {

    public GroundCheck(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet<?> event) {
        BlockPos underPlayer = new BlockPos(getData().getPlayer().posX, getData().getPlayer().posY - 1.0D, getData().getPlayer().posZ);

        boolean invalid = !BlockUtil.isCollidingOnGround(underPlayer, 0.8D) && getData().getMovementTracker().isOnGround();

        if(invalid) {
            if(increaseBuffer() > 4) {
                this.flag();
            }
        }
        reduceBuffer(0.05);
    }

}
