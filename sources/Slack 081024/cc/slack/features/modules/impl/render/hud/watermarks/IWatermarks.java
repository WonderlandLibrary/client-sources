package cc.slack.features.modules.impl.render.hud.watermarks;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;

public interface IWatermarks {
    void onRender(RenderEvent event);
    void onUpdate(UpdateEvent event);
}
