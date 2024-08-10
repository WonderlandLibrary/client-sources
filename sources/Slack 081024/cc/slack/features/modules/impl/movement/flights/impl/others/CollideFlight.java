// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.flights.impl.others;

import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;


public class CollideFlight implements IFlight {


    double startY;

    @Override
    public void onEnable() {
        startY = Math.floor(mc.thePlayer.posY);
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.gameSettings.keyBindJump.pressed = false;
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.getBlock() instanceof BlockAir && event.getY() <= startY)
            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1, startY, event.getZ() + 1));
    }

    @Override
    public String toString() {
        return "Collide";
    }
}
