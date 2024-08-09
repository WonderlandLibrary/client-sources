//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.utils.render.ColorUtils;
import java.util.Iterator;
import net.minecraft.entity.Entity;

@FunctionRegister(
        name = "GlowESP",
        type = Category.Render
)
public class GlowESP extends Function {
    public GlowESP() {
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        Iterator var2 = mc.world.getPlayers().iterator();

        while(var2.hasNext()) {
            Entity player = (Entity)var2.next();
            if (player != null) {
                player.setGlowing(true);
                this.setEntityGlowingColor(player, ColorUtils.getColor(0));
            }
        }

    }

    public void onEnable() {
        Iterator var1 = mc.world.getPlayers().iterator();

        while(var1.hasNext()) {
            Entity player = (Entity)var1.next();
            if (player != null) {
                player.setGlowing(true);
                this.setEntityGlowingColor(player, ColorUtils.getColor(0));
            }
        }
    }

    public void onDisable() {
        Iterator var1 = mc.world.getPlayers().iterator();

        while(var1.hasNext()) {
            Entity player = (Entity)var1.next();
            if (player != null) {
                player.setGlowing(false);
            }
        }

    }

    private void setEntityGlowingColor(Entity entity, int color) {
        entity.setGlowing(true);
    }
}
