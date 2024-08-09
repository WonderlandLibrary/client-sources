package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventKey;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BindSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.StringSetting;

@FunctionRegister(name = "CordDroper", type = Category.Misc)
public class CordDroper extends Function {

    public static String friendName = "";
    final BindSetting cordbutton = new BindSetting("Кнопка отправки", -1);
    public ModeSetting mode = new ModeSetting("Кому отправлять", "Глобальный чат", "Глобальный чат", "Другу");

    public StringSetting name = new StringSetting(
            "Ник друга",
            "",
            "Укажите здесь ник друга"
    ).setVisible(() -> mode.is("Другу"));

    @Subscribe
    private void onEventKey(EventKey e) {
        switch (mode.get()) {
            case "Глобальный чат" -> {
                if (e.getKey() == cordbutton.get()) {
                    mc.player.sendChatMessage("!" + (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ());
                }
            }
            case "Другу" -> {
                if (e.getKey() == cordbutton.get()) {
                    mc.player.sendChatMessage("/m " + friendName + " " + (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ());
                }
            }
        }
    }

    public CordDroper() {
        addSettings(cordbutton, mode, name);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        friendName = name.get();
    }
}