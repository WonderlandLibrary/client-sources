package lol.point.returnclient.module.impl.visual;

import lol.point.returnclient.events.impl.render.EventGlowEntity;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.StringSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "ESP",
        description = "outlines entities",
        category = Category.RENDER
)
public class ESP extends Module {

    private final StringSetting mode = new StringSetting("Mode", new String[]{"Glow"});

    public String getSuffix() {
        return mode.value;
    }

    @Subscribe
    private final Listener<EventGlowEntity> onGlow = new Listener<>(eventGlowEntity -> {
        if (mode.is("Glow")) {
            eventGlowEntity.setCancelled(true);
        }
    });

}
