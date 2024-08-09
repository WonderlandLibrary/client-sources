package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CConfirmTeleportPacket;

@FunctionRegister(name = "PortalGodMode", type = Category.PLAYER, desc = "Пон?")
public class PortalGodMode extends Function {

    @Subscribe
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof CConfirmTeleportPacket) {
            e.cancel();
        }
    }
}
