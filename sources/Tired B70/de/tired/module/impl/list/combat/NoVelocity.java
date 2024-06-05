package de.tired.module.impl.list.combat;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.event.events.VelocityEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S1BPacketEntityAttach;

@ModuleAnnotation(name = "NoVelocity", category = ModuleCategory.COMBAT, clickG = "Reduces Velocity")

public class NoVelocity extends Module {

    private final ModeSetting modeSetting = new ModeSetting("VelocityMode", this, new String[]{"MatrixBackTP", "Cancel", "MatrixHorizontal", "Reduce", "Hawk"});

    public NumberSetting reduceStrenght = new NumberSetting("reduceStrenght", this, .5, .1, 1, .01, () -> modeSetting.getValue().equalsIgnoreCase("Reduce"));

    private int matrixTicks;

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        boolean didPush;
        switch (modeSetting.getValue()) {
            case "MatrixBackTP":

                if (MC.thePlayer.hurtTime != 0) {
                    matrixTicks++;
                } else {
                    matrixTicks = 0;
                }

                if (matrixTicks > 6) {
                    final float yaw = MC.thePlayer.rotationYaw;
                    MC.thePlayer.motionX = -Math.sin(Math.toRadians(yaw)) * .07;
                    MC.thePlayer.motionZ = Math.cos(Math.toRadians(yaw)) * .07;
                    didPush = true;
                } else {
                    didPush = false;
                }

                if (didPush) {
                    MC.thePlayer.motionY *= (MC.thePlayer.motionY) * .6F;
                }
                break;

            case "MatrixHorizontal": {
                final float yaw = MC.thePlayer.rotationYaw;
                double stator = 0.02;
                switch (MC.thePlayer.hurtTime) {
                    case 1:
                        stator += 0.1;
                        break;
                    case 2:
                        stator += 0.004;
                        break;
                }
                if (MC.thePlayer.hurtTime != 1 && MC.thePlayer.hurtTime != 2 && MC.thePlayer.hurtTime != 0) {
                    final double x = -Math.sin(yaw) * stator;
                    final double z = Math.cos(yaw) * stator;
                    MC.thePlayer.motionX = x;
                    MC.thePlayer.motionZ = z;

                }
                break;
            }

            case "Hawk":
                if (MC.thePlayer.hurtTime != 0 && !MC.thePlayer.onGround) {

                  sendPacket(new S1BPacketEntityAttach(1, MC.thePlayer, MC.thePlayer));

                }
                break;
        }

    }

    @EventTarget
    public void onVelocity(VelocityEvent e) {

        if (modeSetting.getValue().equalsIgnoreCase("Reduce")) {
            if (MC.thePlayer.hurtTime != 0) {
                e.setFlag(true);
                e.setSprint(true);
                e.setMotion(reduceStrenght.getValue());
            }
        }

    }

    @EventTarget
    public void onPacket(PacketEvent e) {
        if ("Cancel".equals(modeSetting.getValue())) {
            if (e.getPacket() instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity velocity = (S12PacketEntityVelocity) e.getPacket();
                if (velocity.getEntityID() == MC.thePlayer.getEntityId()) {
                    e.setCancelled(true);
                }
            }
        }

    }

    @Override
    public void onState() {
        matrixTicks = 0;
    }

    @Override
    public void onUndo() {
        matrixTicks = 0;

    }
}
