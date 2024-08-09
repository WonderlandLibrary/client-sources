package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import fun.ellant.Ellant;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.StringSetting;
import net.minecraft.client.Minecraft;

@FunctionRegister(name = "NameProtect", type = Category.RENDER, desc = "Пон?")
public class NameProtect extends Function {

    public static String fakeName = "";

    public StringSetting name = new StringSetting(
            "Заменяемое Имя",
            "dsc.gg/ellantclient",
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
        if (Ellant.getInstance() != null && Ellant.getInstance().getFunctionRegistry().getNameProtect().isState()) {
            input = input.replace(Minecraft.getInstance().session.getUsername(), fakeName);
        }
        return input;
    }
}
