package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventKey;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BindSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.StringSetting;

@FunctionRegister(name = "CordDroper", type = Category.PLAYER, desc = "Пишет ваши координаты по нажатию кнопки")
public class CordDroper extends Function {

    public static String friendName = "";
    final BindSetting cordbutton = new BindSetting("Кнопка отправки кординат", -1);
    public ModeSetting mode = new ModeSetting("Кому отправлять", "Глобальный чат", "Глобальный чат", "Другу");

    public StringSetting name = new StringSetting(
            "Ник друга",
            "Nakson_Play",
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
                    mc.player.sendChatMessage("/msg " + friendName + " " + (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ());
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