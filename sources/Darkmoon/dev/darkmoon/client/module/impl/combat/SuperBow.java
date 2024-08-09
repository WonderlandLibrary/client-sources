package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.packet.EventSendPacket;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;

@ModuleAnnotation(name = "SuperBow", category = Category.COMBAT)
public class SuperBow extends Module {
    private final NumberSetting power = new NumberSetting("Power", 50, 10, 100, 5);

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketPlayerDigging) {
            CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
            if (packet.getAction() != CPacketPlayerDigging.Action.RELEASE_USE_ITEM || !mc.player.isBowing()) {
                return;
            }

            mc.player.connection.sendPacketSilent(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));

            for(int i = 0; i < power.getInt(); ++i) {
                mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, mc.player.posY - 1e-10, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch, true));
                mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, mc.player.posY + 1e-10, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch, false));
            }
        }
    }
}
