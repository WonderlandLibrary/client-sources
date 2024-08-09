//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;

@FunctionRegister(
        name = "Chams",
        type = Category.Render
)
public class Chams extends Function {
    public static Chams chams;
    public boolean state;

    public Chams() {
    }

    @Subscribe
    public void onRender(EventDisplay e) {
    }
}
