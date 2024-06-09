package me.teus.eclipse.modules.impl.visuals;

import me.teus.eclipse.events.render.EventRender2D;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.impl.visuals.HudModes.EclipseDefault;
import me.teus.eclipse.modules.impl.visuals.HudModes.Huzuni;
import me.teus.eclipse.modules.value.impl.BooleanValue;
import me.teus.eclipse.modules.value.impl.ModeValue;
import me.teus.eclipse.modules.value.impl.NumberValue;
import xyz.lemon.event.bus.Listener;

import java.awt.*;

@Info(name = "HUD", displayName = "HUD", category = Category.VISUALS, bind = 0)
public class HUD extends Module {

    public ModeValue hudModes = new ModeValue("Hud Style", "Huzuni", "Bestest", "Huzuni");
    public NumberValue testNum = new NumberValue("Test Num", 5, 0, 10, 1);
    public BooleanValue testBool = new BooleanValue("Test Bool", true);

    public HUD(){
        setColor(new Color(231, 126, 96));
        addValues(hudModes, testNum, testBool);
    }

    private final Listener<EventRender2D> eventRender2DListener = eventRender2D -> {
        switch (hudModes.getMode()) {
            case "Bestest":
                EclipseDefault.draw();
                break;
            case "Huzuni":
                Huzuni.draw();
                break;
        }
    };

}
