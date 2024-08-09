package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventKey;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BindSetting;

@FunctionRegister(name = "ECAutoOpen", type = Category.Player)
public class ECAutoOpen extends Function {
    final BindSetting cordbutton = new BindSetting("Кнопка открытия", -1);
    @Subscribe
    private void onEventKey(EventKey e) {
        if (e.getKey() == cordbutton.get()) {
            mc.player.sendChatMessage("/ec");
        }
    }
    public ECAutoOpen() {
        addSettings(cordbutton);
    }
}


