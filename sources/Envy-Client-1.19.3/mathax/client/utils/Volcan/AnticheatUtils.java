package mathax.client.utils.Volcan;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Formatting;

//todo
// - Add Anticheat Detection (Through Plugin Checking and Chat Alerts)
// - Add RubberBand Mitigation (BruteForce / Silent)
// - Add MovementCorrection (To Simplify adding the correction to other modules)
// - Add Support to Keep Track of other player Setbacks and show it on NameTags
// - Add Setback Logging with Command to Output the Logs (For debugging purposes)
public class AnticheatUtils {

    @EventHandler
    public static boolean getSetback(PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) {
            return true;
        }
        else {
            return false;
        }
    }
}
