/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-16611376, name="Sneak")
public class Sneak
extends ToggleableModule {
    public Value<Boolean> clientsided;

    public Sneak() {
        this.clientsided = new Value<Boolean>("client", false, this);
        this.addValue(this.clientsided);
    }

    @EventListener
    public void on(UpdateEvent e) {
        if (((Boolean)this.getValue("client").getValue()).booleanValue()) {
            Sneak.mc.thePlayer.setSneaking(true);
        } else {
            Sneak.mc.thePlayer.sendPacket(new C0BPacketEntityAction(Sneak.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
    }
}

