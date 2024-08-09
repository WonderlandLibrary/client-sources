package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import net.minecraft.network.play.client.CCloseWindowPacket;
import fun.ellant.functions.api.FunctionRegister;

@FunctionRegister(name = "xCarry", type = Category.PLAYER, desc = "Пон?")
public class xCarry extends Function {

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null) return;

        if (e.getPacket() instanceof CCloseWindowPacket) {
            e.cancel();
        }
    }
}
