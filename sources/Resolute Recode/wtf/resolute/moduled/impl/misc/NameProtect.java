package wtf.resolute.moduled.impl.misc;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.StringSetting;
import net.minecraft.client.Minecraft;

@ModuleAnontion(name = "NameProtect", type = Categories.Misc,server = "")
public class NameProtect extends Module {

    public static String fakeName = "";

    public StringSetting name = new StringSetting(
            "Заменяемое Имя",
            "Resolute",
            "Укажите текст для замены вашего игрового ника"
    );

    public NameProtect() {
        addSettings(name);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        fakeName = name.get();
    }

    public static String getReplaced(String input) {
        if (ResoluteInfo.getInstance() != null && ResoluteInfo.getInstance().getFunctionRegistry().getNameProtect().isState()) {
            input = input.replace(Minecraft.getInstance().session.getUsername(), fakeName);
        }
        return input;
    }
}
