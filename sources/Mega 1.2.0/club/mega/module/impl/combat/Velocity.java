package club.mega.module.impl.combat;

import club.mega.event.impl.EventPacket;
import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.ListSetting;
import club.mega.module.setting.impl.NumberSetting;
import club.mega.util.AuraUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Velocity", description = "Removes Velocity", category = Category.COMBAT)
public class Velocity extends Module {

    private final ListSetting mode = new ListSetting("Mode", this, new String[]{"Vanilla", "Intave"});
    private final NumberSetting amount = new NumberSetting("Amount", this, 0, 100, 0, 1, () -> mode.is("vanilla"));

    @Handler
    public final void packet(final EventPacket event) {
        if (!mode.is("vanilla"))
            return;

        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
            packet.setMotionX(amount.getAsInt() * packet.getMotionX() / 100);
            packet.setMotionY(amount.getAsInt() * packet.getMotionY() / 100);
            packet.setMotionZ(amount.getAsInt() * packet.getMotionZ() / 100);
        }
    }

    @Handler
    public final void tick(final EventTick event) {
        if (!mode.is("intave"))
            return;

        if (AuraUtil.getTarget() instanceof EntityPlayer) {
            if (MC.thePlayer.hurtTime != 0) {
                if (MC.thePlayer.getHealth() < 16) {
                    if (!MC.thePlayer.onGround && MC.thePlayer.hurtTime != 0) {
                        MC.thePlayer.setSprinting(MC.thePlayer.ticksExisted % 27 == 0);
                    }
                }
                if (!(AuraUtil.getTarget().getHeldItem().getItem() instanceof ItemFishingRod)) {
                    if (MC.thePlayer.hurtTime != 0 && MC.thePlayer.onGround && MC.thePlayer.ticksExisted % 16 == 0) {
                        MC.thePlayer.jump();
                    }
                }

            }
            if (MC.thePlayer.hurtTime != 0 && !MC.thePlayer.isInWater() && MC.thePlayer.ticksExisted % 65 == 0 && MC.thePlayer.motionY < .32) {
                float yaw = (float) Math.toRadians(KillAura.getInstance().isToggled() ? KillAura.getInstance().getRots()[0] : MC.thePlayer.rotationYaw);
                MC.thePlayer.motionX = -Math.sin(yaw) * 0.21;
                MC.thePlayer.motionZ = Math.cos(yaw) * 0.21;
                MC.gameSettings.keyBindSprint.pressed = true;
            } else {
                MC.gameSettings.keyBindSprint.pressed = false;
            }
        }
    }
}
