package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;

import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.SliderSetting;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SUpdateTimePacket;

@Getter
@FunctionRegister(name = "World", type = Category.PLAYER, desc = "Пон?")
public class World extends Function {

    private final SliderSetting timeOfDay = new SliderSetting("Время", 1, 1, 24000, 1);

    public World() {
        addSettings(timeOfDay);
    }
    @Subscribe
    public void onUpdate(EventUpdate e) {
        Minecraft.getInstance().world.setDayTime(timeOfDay.get().intValue());
    }

    @Subscribe
    public void onPacket(EventPacket e) {
        if (e.getPacket() instanceof SUpdateTimePacket) {
            Minecraft.getInstance().world.setDayTime(timeOfDay.get().intValue());
        }
    }
}
