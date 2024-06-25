package cc.slack.features.modules.impl.render.hud.arraylist;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;

public interface IArraylist {
   void onRender(RenderEvent var1);

   void onUpdate(UpdateEvent var1);
}
