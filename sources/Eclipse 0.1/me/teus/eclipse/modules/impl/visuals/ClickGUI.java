package me.teus.eclipse.modules.impl.visuals;

import me.teus.eclipse.events.render.EventRender2D;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.ui.ClickGui.DropDown;
import org.lwjgl.input.Keyboard;
import xyz.lemon.event.bus.Listener;

import java.awt.*;

@Info(name = "ClickGUI", displayName = "ClickGUI", category = Category.VISUALS, bind = Keyboard.KEY_RSHIFT) //if you not gonna put bind it will just be NONE (0)
public class ClickGUI extends Module {

    public ClickGUI(){
        setColor(new Color(128, 238, 154));
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new DropDown());
        this.toggle();
    }

}
