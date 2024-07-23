package io.github.liticane.monoxide.anticheat.check.impl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import io.github.liticane.monoxide.anticheat.check.Check;
import io.github.liticane.monoxide.anticheat.data.PlayerData;

@Check.Info(name = "Move")
public class MoveCheck extends Check {

    public MoveCheck(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet<?> event) {
        EntityPlayer player = data.getPlayer();

        if (
                !player.onGround
                && player.fallDistance <= 0
                && player.motionY == 0
        ) {
            if (increaseBuffer() > 25) {
                flag();
            }
        } else {
            resetBuffer();
        }

    }

}
