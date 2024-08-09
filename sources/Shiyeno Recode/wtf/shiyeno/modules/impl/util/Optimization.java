package wtf.shiyeno.modules.impl.util;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;

@FunctionAnnotation(
        name = "Optimization",
        type = Type.Util
)
public class Optimization extends Function {
    public final MultiBoxSetting optimizeSelection = new MultiBoxSetting("Оптимизировать", new BooleanOption[]{new BooleanOption("Освещение", true), new BooleanOption("Партиклы", true), new BooleanOption("Подсветка клиента", false)});

    public Optimization() {
        this.addSettings(new Setting[]{this.optimizeSelection});
    }

    public void onEvent(Event event) {
    }
}