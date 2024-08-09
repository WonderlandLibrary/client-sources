package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventPacket;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import net.minecraft.network.play.client.CCloseWindowPacket;
import im.expensive.functions.api.FunctionRegister;

@FunctionRegister(name = "xCarry", type = Category.Misc)
public class xCarry extends Function {

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null) return;

        if (e.getPacket() instanceof CCloseWindowPacket) {
            e.cancel();
        }
    }
}
