// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.flights.impl.others;

import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;


public class AirJumpFlight implements IFlight {


    double startY;

    @Override
    public void onEnable() {
        startY = Math.floor(mc.thePlayer.posY);
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.getBlock() instanceof BlockAir && event.getY() <= startY)
            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1, startY, event.getZ() + 1));
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mc.gameSettings.keyBindJump.isPressed() && mc.thePlayer.onGround) {
            event.setY(0.42F);
        }
    }

    @Override
    public String toString() {
        return "Air Jump";
    }
}
