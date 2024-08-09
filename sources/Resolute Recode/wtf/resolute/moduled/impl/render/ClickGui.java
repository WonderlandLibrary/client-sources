package wtf.resolute.moduled.impl.render;

import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;

@ModuleAnontion(name = "ClickGui", type = Categories.Render,server = "")
public class ClickGui extends Module {

    public final BooleanSetting shader = new BooleanSetting("Шейдер", false);
    public ClickGui() {
        addSettings();
    }

}
