package best.actinium.component.componets;

import best.actinium.Actinium;
import best.actinium.component.Component;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.render.Render2DEvent;
import best.actinium.module.impl.visual.HudModule;
import best.actinium.util.render.ChatUtil;
import best.actinium.util.render.ColorUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
public class ColorComponent extends Component {
    private HudModule hud;
    @Getter
    @Setter
    public static Color color1 = Color.white, color2 = Color.white , color = Color.white;
    private int index;

    @Callback
    public void onRender(Render2DEvent event) {
        hud = Actinium.INSTANCE.getModuleManager().get(HudModule.class);
        color1 = hud.color.is("Blend") ? hud.color1.getColor() : hud.color1.getColor().darker().darker().darker();
        color2 = hud.color.is("Blend") ? hud.color2.getColor() : hud.color2.getColor().darker().darker().darker();
        color = ColorUtil.interpolateColorsBackAndForth(15, 3 + (index * 20), color1,color2, false);
        index++;
    }
    //i will finish this tomorrow i am tired its ass
}
