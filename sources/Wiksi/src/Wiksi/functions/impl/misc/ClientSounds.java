package src.Wiksi.functions.impl.misc;

import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;

@FunctionRegister(name = "ClientSounds", type = Category.Misc)
public class ClientSounds extends Function {

        public ModeSetting mode = new ModeSetting("Тип", "Обычный", "Обычный", "MineCraft", "Nursultan", "Nursultan1", "Nursultan2", "Nursultan3");
        public SliderSetting volume = new SliderSetting("Громкость", 70.0F, 0.0F, 150.0F, 1.0F);

        public ClientSounds() {
            this.addSettings(mode, volume);
        }

        public String getFileName(boolean var1) {
            switch ((String)this.mode.get()) {
                case "Обычный":
                    return var1 ? "enable" : "disable".toString();
                case "MineCraft":
                    return var1 ? "excoff" : "excon";
                case "Nursultan":
                    return var1 ? "nurikon" : "nurikoff";
                case "Nursultan1":
                    return var1 ? "1on" : "1off";
                case "Nursultan2":
                    return var1 ? "2on" : "2off";
                case "Nursultan3":
                    return var1 ? "3on" : "3off";
                default:
                    return "";
            }
        }
    }
