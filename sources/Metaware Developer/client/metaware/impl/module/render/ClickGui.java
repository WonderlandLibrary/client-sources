package client.metaware.impl.module.render;

import client.metaware.api.clickgui.FelixClickGui;
import client.metaware.api.gui.gui2.VapeClickGui;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.EnumProperty;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClickGui", renderName = "Click Gui", category = Category.VISUALS, keybind = Keyboard.KEY_RSHIFT)
public class ClickGui extends Module {

    private final EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Whiz);

    public enum Mode{
        Whiz, Vape;
    }

    public FelixClickGui clickGui;
    public VapeClickGui clickGui2;

    @Override
    public void onEnable() {
        super.onEnable();
        switch(mode.getValue()){
            case Whiz:{
                if(clickGui == null)
                    clickGui = new FelixClickGui();

                mc.displayGuiScreen(clickGui);
                break;
            }
            case Vape:{
                if(clickGui2 == null)
                    clickGui2 = new VapeClickGui();

                mc.displayGuiScreen(clickGui2);
                break;
            }
        }
        toggle();
    }
}
