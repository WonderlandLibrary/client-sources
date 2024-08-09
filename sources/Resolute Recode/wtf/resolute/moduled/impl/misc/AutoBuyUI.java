package wtf.resolute.moduled.impl.misc;

import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BindSetting;

@ModuleAnontion(name = "AutoBuyUI", type = Categories.Misc,server = "FT")
public class AutoBuyUI extends Module {

    public BindSetting setting = new BindSetting("Кнопка открытия", -1);

    public AutoBuyUI() {
        addSettings(setting);
    }
}
