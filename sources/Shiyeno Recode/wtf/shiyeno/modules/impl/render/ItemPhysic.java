package wtf.shiyeno.modules.impl.render;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;

@FunctionAnnotation(
        name = "ItemPhysic",
        type = Type.Render
)
public class ItemPhysic extends Function {
    public ItemPhysic() {
        this.addSettings(new Setting[0]);
    }

    public void onEvent(Event event) {
    }
}