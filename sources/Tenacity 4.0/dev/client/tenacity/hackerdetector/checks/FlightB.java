package dev.client.tenacity.hackerdetector.checks;

import dev.client.tenacity.hackerdetector.Category;
import dev.client.tenacity.hackerdetector.Detection;
import dev.client.tenacity.hackerdetector.utils.MovementUtils;
import net.minecraft.entity.player.EntityPlayer;

public class FlightB extends Detection {

    public FlightB() {
        super("Flight B", Category.MOVEMENT);
    }

    @Override
    public boolean runCheck(EntityPlayer player) {
        return player.airTicks > 20 && player.motionY == 0 && MovementUtils.isMoving(player);
    }
}
