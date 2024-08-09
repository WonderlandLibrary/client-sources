package wtf.shiyeno.modules.impl.player;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(
        name = "ItemScroller",
        type = Type.Player
)
public class ItemScroller extends Function {
    public SliderSetting delay = new SliderSetting("Задержка", 80.0F, 0.0F, 1000.0F, 1.0F);

    public ItemScroller() {
        this.addSettings(new Setting[]{this.delay});
    }

    public void onEvent(Event event) {
    }
}