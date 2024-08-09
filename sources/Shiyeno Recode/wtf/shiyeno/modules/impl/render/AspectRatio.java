package wtf.shiyeno.modules.impl.render;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(name = "AspectRatio", type = Type.Render)
public class AspectRatio extends Function {
    
    public SliderSetting width = new SliderSetting("\u0428\u0438\u0440\u0438\u043d\u0430", 1.0f, 0.6f, 1.6f, 0.1f);

    public AspectRatio() {
        this.addSettings(this.width);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event event) {
    }
}