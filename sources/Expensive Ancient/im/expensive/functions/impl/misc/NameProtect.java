package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.StringSetting;
import net.minecraft.client.Minecraft;

@FunctionRegister(name = "NameProtect", type = Category.Misc)
public class NameProtect extends Function {

    public static String fakeName = "";

    public StringSetting name = new StringSetting(
            "Заменяемое Имя",
            "dedinside",
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
        if (Expensive.getInstance() != null && Expensive.getInstance().getFunctionRegistry().getNameProtect().isState()) {
            input = input.replace(Minecraft.getInstance().session.getUsername(), fakeName);
        }
        return input;
    }
}
