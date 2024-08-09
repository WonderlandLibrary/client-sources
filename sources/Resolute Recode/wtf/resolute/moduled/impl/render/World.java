package wtf.resolute.moduled.impl.render;

import com.google.common.eventbus.Subscribe;

import net.minecraft.potion.Effects;
import wtf.resolute.evented.EventPacket;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import lombok.Getter;
import net.minecraft.network.play.server.SUpdateTimePacket;

@Getter
@ModuleAnontion(name = "WorldHelper", type = Categories.Render,server = "")
public class World extends Module {

    public ModeSetting time = new ModeSetting("Время", "Day", "Day", "Night");
    public static BooleanSetting phisic = new BooleanSetting("Физика Придметов",false);
    public World() {
        addSettings(time,phisic);
    }
    @Subscribe
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof SUpdateTimePacket p) {
            if (time.get().equalsIgnoreCase("Day"))
                p.worldTime = 1000L;
            else
                p.worldTime = 13000L;
        }
    }
}
