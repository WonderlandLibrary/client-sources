package wtf.resolute.moduled.impl.misc;

import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;

@ModuleAnontion(name = "ClientSounds", type = Categories.Misc,server = "")
public class ClientSounds extends Module {

    public ModeSetting mode = new ModeSetting("Тип", "Обычный", "Обычный");
    public SliderSetting volume = new SliderSetting("Громкость", 100.0f, 0.0f, 150.0f, 1.0f);

    public ClientSounds() {
        addSettings(mode, volume);
    }


    public String getFileName(boolean state) {
        switch (mode.get()) {
            case "Обычный" -> {
                return state ? "enable" : "disable".toString();
            }
            case "Пузырьки" -> {
                return state ? "enableBubbles" : "disableBubbles";
            }
        }
        return "";
    }
}
