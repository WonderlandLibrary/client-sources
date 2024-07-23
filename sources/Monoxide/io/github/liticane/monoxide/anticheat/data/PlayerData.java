package io.github.liticane.monoxide.anticheat.data;

import io.github.liticane.monoxide.anticheat.check.Check;
import io.github.liticane.monoxide.anticheat.check.CheckStorage;
import io.github.liticane.monoxide.anticheat.data.tracker.impl.AimTracker;
import io.github.liticane.monoxide.anticheat.data.tracker.impl.MovementTracker;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S18PacketEntityTeleport;

import java.util.List;

@Getter
@Setter
public class PlayerData {

    private final EntityPlayer player;

    private final MovementTracker movementTracker;
    private final AimTracker aimTracker;

    private final List<Check> checks;

    private int ticksExisted;

    private long lastTeleport;

    private boolean ground, lastGround;

    public PlayerData(EntityPlayer player) {
        this.player = player;

        this.movementTracker = new MovementTracker(this);
        this.aimTracker = new AimTracker(this);

        this.checks = CheckStorage.getInstance().loadChecks(this);
    }

    public void handle(Packet<?> packet) {
        if (packet instanceof S18PacketEntityTeleport) {
            lastTeleport = System.currentTimeMillis();
        }

        movementTracker.handle(packet);
        aimTracker.handle(packet);

        checks.forEach(check -> {
            check.handle(packet);
        });
    }

    public void updateTicks() {
        ticksExisted++;
    }

}