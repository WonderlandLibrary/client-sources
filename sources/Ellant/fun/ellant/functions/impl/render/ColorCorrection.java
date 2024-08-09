package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name = "ColorCorrection", type = Category.RENDER, desc = "")
public class ColorCorrection extends Function {
    @Subscribe
    public void onDisplay(EventDisplay e) {
        GL11.glColor4f(1.1f, 1.1f, 1.1f, 1.0f);
    }
}
