package client.metaware.impl.module.misc;

import client.metaware.Metaware;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@ModuleInfo(renderName = "Panic", name = "Panic", category = Category.EXPLOIT)
public class Panic extends Module {

    public Property<Boolean> close = new Property<>("Close", false);

    public void onEnable() {
        super.onEnable();
        toggled(false);
        Metaware.INSTANCE.shutdownClient();

        for(Module module : Metaware.INSTANCE.getModuleManager().getModules()){
            module.setKey(Keyboard.KEY_NONE);
            module.toggled(false);
        }
        Display.setTitle("Minecraft 1.8");
        toggled(false);
        if(close.getValue()){
            mc.shutdown();
        }
    }
}
