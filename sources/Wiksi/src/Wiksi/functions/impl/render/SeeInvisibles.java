package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.SliderSetting;
import net.minecraft.entity.player.PlayerEntity;

@FunctionRegister(name = "SeeInvisibles", type = Category.Render)
public class SeeInvisibles extends Function {


    @Subscribe
    private void onUpdate(EventUpdate e) {
        for (PlayerEntity playerEntity : SeeInvisibles.mc.world.getPlayers()) {
            if (playerEntity == SeeInvisibles.mc.player || !playerEntity.isInvisible()) continue;
            playerEntity.setInvisible(false);
        }
    }
}

