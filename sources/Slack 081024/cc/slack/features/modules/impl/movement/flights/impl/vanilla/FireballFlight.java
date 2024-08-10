// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.flights.impl.vanilla;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.Flight;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.BlinkUtil;
import cc.slack.utils.player.InventoryUtil;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.rotations.RotationUtil;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class FireballFlight implements IFlight {

    private boolean sent = false;
    private boolean reset = false;
    private boolean gotVelo = false;

    private float speed = 0f;
    private float yaw = 0f;

    private int fireballSlot = 0;

    private boolean started = false;

    @Override
    public void onEnable() {
        sent = false;
        reset = false;
        gotVelo = false;
        started = false;
        fireballSlot = InventoryUtil.findFireball();
        if (fireballSlot == -1) {
            Slack.getInstance().addNotification("Fireball needed to fly", "", 3000L, Slack.NotificationStyle.WARN);
            Slack.getInstance().getModuleManager().getInstance(Flight.class).setToggle(false);
        }
        fireballSlot -= 36;
    }

    @Override
    public void onDisable() {
        if (sent && !reset) {
            PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }
        BlinkUtil.disable();
    }


    @Override
    public void onUpdate(UpdateEvent event) {
        if (!sent) {
            if (!started && mc.thePlayer.onGround) {
                PacketUtil.send(new C09PacketHeldItemChange(fireballSlot));
                mc.thePlayer.jump();
                MovementUtil.strafe(0f);
                started = true;
                RotationUtil.setClientRotation(new float[]{mc.thePlayer.rotationYaw + 180, Slack.getInstance().getModuleManager().getInstance(Flight.class).fbpitch.getValue()}, 2);

            } else if (started) {
                PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(InventoryUtil.getSlot(fireballSlot).getStack()));
                sent = true;
            }
        } else {
            if (!reset) {
                RotationUtil.setClientRotation(new float[]{mc.thePlayer.rotationYaw + 80, Slack.getInstance().getModuleManager().getInstance(Flight.class).fbpitch.getValue()}, 2);

                PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                reset = true;
            }

            if (gotVelo && mc.thePlayer.onGround) {
                Slack.getInstance().getModuleManager().getInstance(Flight.class).setToggle(false);
            }

            if (gotVelo && mc.thePlayer.hurtTime == 9) {
                MovementUtil.strafe(MovementUtil.getSpeed() * 1.04f);
                speed = MovementUtil.getSpeed();
                yaw = MovementUtil.getDirection();
            } else if (gotVelo && mc.thePlayer.hurtTime > 4 && mc.thePlayer.hurtTime < 9) {
                speed *= 0.988f;
                MovementUtil.strafe(speed, yaw);
            }



        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            if (((S12PacketEntityVelocity) event.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
                gotVelo = true;
                BlinkUtil.disable();
            }
        }
    }

    @Override
    public String toString() {
        return "Fireball Flight";
    }
}
