package fun.ellant.functions.impl.render;

import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;

@FunctionRegister(name = "ClientSounds", type = Category.RENDER, desc = "Изменяет звук включения функций")
public class ClientSounds extends Function {

    public ModeSetting mode = new ModeSetting("Тип", "Тип 1", "Тип 1", "Тип 2");
    public SliderSetting volume = new SliderSetting("Громкость", 70.0f, 0.0f, 100.0f, 1.0f);

    public ClientSounds() {
        addSettings(mode, volume);
    }


    public String getFileName(boolean state) {
        switch (mode.get()) {
            case "Тип 1" -> {
                return state ? "enable" : "disable".toString();
            }
            case "Тип 2" -> {
                return state ? "enable2" : "disable2";
            }

        }
        return "";
    }
}