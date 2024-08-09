package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CConfirmTeleportPacket;

@FunctionRegister(name = "PortalGodMode", type = Category.Player)
public class PortalGodMode extends Function {

    @Subscribe
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof CConfirmTeleportPacket) {
            e.cancel();
        }
    }
}
