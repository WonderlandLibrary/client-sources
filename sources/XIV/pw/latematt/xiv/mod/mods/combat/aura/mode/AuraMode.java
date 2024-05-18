package pw.latematt.xiv.mod.mods.combat.aura.mode;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import pw.latematt.xiv.event.events.MotionUpdateEvent;
import pw.latematt.xiv.mod.mods.combat.aura.KillAura;

/**
 * @author Matthew
 */
public abstract class AuraMode {
    private String name;
    protected final KillAura killAura;
    protected final Minecraft mc = Minecraft.getMinecraft();

    public AuraMode(String name, KillAura killAura) {
        this.name = name;
        this.killAura = killAura;
    }

    public String getName() {
        return name;
    }

    public abstract void onPreMotionUpdate(MotionUpdateEvent event);

    public abstract void onPostMotionUpdate(MotionUpdateEvent event);

    public abstract void onMotionPacket(C03PacketPlayer packet);

    public abstract boolean isAttacking();

    public abstract void onDisabled();
}
