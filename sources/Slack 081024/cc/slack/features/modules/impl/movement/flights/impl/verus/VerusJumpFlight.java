// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.flights.impl.verus;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;

public class VerusJumpFlight implements IFlight {

    double startY;

    @Override
    public void onEnable() {
        startY = Math.floor(mc.thePlayer.posY);
    }

    @Override
    public void onPacket(PacketEvent event) {
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
        mc.gameSettings.keyBindJump.pressed = false;
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.getBlock() instanceof BlockAir && event.getY() <= startY)
            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1, startY, event.getZ() + 1));
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onMotion(MotionEvent event) {

    }

    @Override
    public String toString() {
        return "Verus Jump";
    }
}
