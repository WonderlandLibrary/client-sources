package us.dev.direkt.command.internal.movement;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author BFCE
 */
public class Jump extends Command {

    private boolean offGround;

    public Jump() {
        super(Direkt.getInstance().getCommandManager(), "jump");
    }

    @Executes
    public void run() {
        Direkt.getInstance().getEventManager().register(this);
        offGround = false;
    }

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        float direction = Wrapper.getPlayer().rotationYaw + (
                Wrapper.getPlayer().moveForward < 0.0F ? 180 : 0) + (
                Wrapper.getPlayer().moveStrafing > 0.0F ? -90.0F * (Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : 1.0F) : 0.0F) - (
                Wrapper.getPlayer().moveStrafing < 0.0F ? -90.0F * (Wrapper.getPlayer().moveForward > 0.0F ? 0.5F : Wrapper.getPlayer().moveForward < 0.0F ? -0.5F : 1.0F) : 0.0F);
        float xDir = (float) Math.cos((direction + 90.0F) * Math.PI / 180.0D);
        float zDir = (float) Math.sin((direction + 90.0F) * Math.PI / 180.0D);

        Wrapper.getPlayer().setSprinting(true);
        Wrapper.getPlayer().onGround = true;
        if (Wrapper.getPlayer().isCollidedVertically) {
            Wrapper.getPlayer().jump();
        } else {
            offGround = true;
            Wrapper.getPlayer().motionX = (xDir * 0.36D);
            Wrapper.getPlayer().motionZ = (zDir * 0.36D);
        }
        if (offGround && Wrapper.getPlayer().isCollidedVertically) {
            Wrapper.getPlayer().setVelocity(0.0D, 0.0D, 0.0D);
            Direkt.getInstance().getEventManager().unregister(this);
        }
    });

}
