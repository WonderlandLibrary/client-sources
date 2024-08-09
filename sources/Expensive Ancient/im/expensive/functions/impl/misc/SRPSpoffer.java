package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventPacket;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CResourcePackStatusPacket;
import net.minecraft.network.play.server.SSendResourcePackPacket;

@FunctionRegister(name = "SRPSpoofer", type = Category.Misc)
public class SRPSpoffer extends Function {

    @Subscribe
    private void onPacket(EventPacket e) {
        if (e.getPacket() instanceof SSendResourcePackPacket s) {
            e.cancel();
        }
        if (e.getPacket() instanceof CResourcePackStatusPacket s) {
            s.action = CResourcePackStatusPacket.Action.SUCCESSFULLY_LOADED;
        }
    }

}
