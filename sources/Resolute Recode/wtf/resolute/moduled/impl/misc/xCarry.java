package wtf.resolute.moduled.impl.misc;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventPacket;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import net.minecraft.network.play.client.CCloseWindowPacket;
import wtf.resolute.moduled.ModuleAnontion;

@ModuleAnontion(name = "xCarry", type = Categories.Misc,server = "")
public class xCarry extends Module {

    @Subscribe
    public void onPacket(EventPacket e) {
        if (mc.player == null) return;

        if (e.getPacket() instanceof CCloseWindowPacket) {
            e.cancel();
        }
    }
}
