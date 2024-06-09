/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.PLAYER, color=-9558899, name="NoFall")
public class NoFall
extends ToggleableModule {
    private Value<String> mode;

    public NoFall() {
        this.mode = new Value<String>("mode", "GAC", this);
        this.addValue(this.mode);
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        this.renderMode = this.mode.getValue();
        if (!this.mode.getValue().equalsIgnoreCase("GAC") || !NoFall.mc.thePlayer.onGround) {
            // empty if block
        }
        if (this.mode.getValue().equalsIgnoreCase("AAC") && !NoFall.mc.thePlayer.onGround && NoFall.mc.thePlayer.fallDistance > 2.0f) {
            NoFall.mc.thePlayer.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(NoFall.mc.thePlayer.rotationYaw, NoFall.mc.thePlayer.rotationPitch, true));
        }
        if (this.mode.getValue().equalsIgnoreCase("Vanilla") && !NoFall.mc.thePlayer.onGround && NoFall.mc.thePlayer.fallDistance > 2.0f) {
            NoFall.mc.thePlayer.sendPacket(new C03PacketPlayer(true));
        }
    }
}

