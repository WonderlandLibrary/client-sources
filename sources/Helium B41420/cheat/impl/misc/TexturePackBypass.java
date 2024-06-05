package rip.helium.cheat.impl.misc;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.SendPacketEvent;

public class TexturePackBypass extends Cheat {

    public TexturePackBypass() {
        super("TexturePackBypass", "no stinky pack", CheatCategory.MISC);
    }

    @Collect
    public void packetEvent(SendPacketEvent e) {
        if (e.getPacket() instanceof C19PacketResourcePackStatus) {
            final C19PacketResourcePackStatus p = (C19PacketResourcePackStatus)e.getPacket();
            p.field_179719_b = C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED;
        }
    }

}
