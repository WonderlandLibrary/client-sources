package pw.latematt.xiv.mod.mods.misc;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.ReadPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Matthew
 */
public class NoRotationSet extends Mod implements Listener<ReadPacketEvent> {
    public NoRotationSet() {
        super("NoRotationSet", ModType.MISCELLANEOUS);
    }

    @Override
    public void onEventCalled(ReadPacketEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook poslook = (S08PacketPlayerPosLook) event.getPacket();
            if (mc.thePlayer.rotationYaw != -180 && mc.thePlayer.rotationPitch != 0) {
                poslook.field_148936_d = mc.thePlayer.rotationYaw;
                poslook.field_148937_e = mc.thePlayer.rotationPitch;
            }
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
