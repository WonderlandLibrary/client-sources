package us.dev.direkt.module.internal.movement.speed.internal.modes;

import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.MathHelper;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.player.update.EventPostMotionUpdate;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.internal.movement.LiquidWalk;
import us.dev.direkt.module.internal.movement.Sprint;
import us.dev.direkt.module.internal.movement.Timer;
import us.dev.direkt.module.internal.movement.speed.internal.AbstractSpeedMode;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author Foundry
 */
public class AACMode extends AbstractSpeedMode {
    public AACMode() {
        super("AAC");
    }

    @Listener
    protected Link<EventPostMotionUpdate> onPostMotionUpdate = new Link<>(event -> {
        if (Direkt.getInstance().getModuleManager().getModule(Timer.class).isRunning())
            Wrapper.getMinecraft().getTimer().timerSpeed = 1.1F;
        float localModifier;
        if (Wrapper.getPlayer().moveStrafing == 0) {
            localModifier = 0.087f;
        } else if (!Wrapper.getGameSettings().keyBindForward.isPressed()
                && !Wrapper.getGameSettings().keyBindBack.isPressed()) {
            localModifier = 0.07250f;
        } else {
            localModifier = 0.073750f;
        }
        if (Math.abs(Wrapper.getPlayer().moveForward) + Math.abs(Wrapper.getPlayer().moveStrafing) > 0.1f) {
            if (Wrapper.getPlayer().isCollidedVertically
                    && !LiquidWalk.isOnLiquid(Wrapper.getPlayer().getEntityBoundingBox())
                    && !LiquidWalk.isInLiquid(Wrapper.getPlayer().getEntityBoundingBox())) {
                float v5 = 0.0f;
                float v6 = 0.0f;
                float v7 = Wrapper.getPlayer().rotationYaw;
                if (Wrapper.getPlayer().moveForward < 0.0f) {
                    v7 += 180.0f;
                }
                if (Wrapper.getPlayer().moveStrafing > 0.0f) {
                    v7 -= 90.0f * ((Wrapper.getPlayer().moveForward > 0.0f) ? 0.5f
                            : ((Wrapper.getPlayer().moveForward < 0.0f) ? -0.5f : 1.0f));
                }
                if (Wrapper.getPlayer().moveStrafing < 0.0f) {
                    v7 += 90.0f * ((Wrapper.getPlayer().moveForward > 0.0f) ? 0.5f
                            : ((Wrapper.getPlayer().moveForward < 0.0f) ? -0.5f : 1.0f));
                }
                float v8 = v7 * 0.017453292f;
                if (!Wrapper.getMinecraft().gameSettings.keyBindJump.isPressed()) {
                    v5 = -(MathHelper.sin(v8) * localModifier);
                    v6 = MathHelper.cos(v8) * localModifier;
                }
                Wrapper.getPlayer().motionX += v5;
                Wrapper.getPlayer().motionZ += v6;

            } else
                Wrapper.getMinecraft().getTimer().timerSpeed = 1.0f;
        } else
            Wrapper.getMinecraft().getTimer().timerSpeed = 1.0f;
    });

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        if (((CPacketEntityAction) event.getPacket()).getAction() == CPacketEntityAction.Action.START_SPRINTING
                && !Direkt.getInstance().getModuleManager().getModule(Sprint.class).isRunning()) {
            event.setCancelled(true);
            Wrapper.getPlayer().setSprinting(false);
        }
    }, new PacketFilter<>(CPacketEntityAction.class));
}
