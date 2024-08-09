package wtf.shiyeno.modules.impl.render;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(
        name = "HitColor",
        type = Type.Render
)
public class HitColor extends Function {
    public SliderSetting intensivity = new SliderSetting("Интенсивность", 0.3F, 0.1F, 1.0F, 0.1F);

    public HitColor() {
        this.addSettings(new Setting[]{this.intensivity});
    }

    public void onEvent(Event event) {
    }
}