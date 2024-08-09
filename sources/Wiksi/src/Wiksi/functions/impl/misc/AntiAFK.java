package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;

import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.functions.settings.impl.StringSetting;

@FunctionRegister(name = "AntiAFK", type = Category.Player)
public class AntiAFK extends Function {

    public ModeSetting vibor = new ModeSetting("Мод", "Прыжок", "Прыжок", "Ротация", "Команда");
    private final SliderSetting Jump_Time = new SliderSetting("Время прыжка", 1f, 1f, 60f, 1f).setVisible(() -> vibor.is("Прыжок"));
    private final SliderSetting Rotation_time = new SliderSetting("Время Поворота", 1f, 1f, 60f, 1f).setVisible(() -> vibor.is("Ротация"));
    private final SliderSetting Rotation = new SliderSetting("Поворот", 10f, 1f, 180f, 10f).setVisible(() -> vibor.is("Ротация"));
    private final SliderSetting Command_time = new SliderSetting("Задержка", 1f, 1f, 60f, 1f).setVisible(() -> vibor.is("Команда"));
    private long lastSentTime = 0;
    public StringSetting name = new StringSetting(
            "Команда",
            "",
            "Укажите текст для команды"
    ).setVisible(() -> vibor.is("Команда"));

    public AntiAFK() {
        addSettings(vibor, Jump_Time, Rotation_time, Rotation, Command_time, name);
    }


    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (e.getType() != EventDisplay.Type.PRE) {
            return;
        }
        if (vibor.is("Команда")) {
            long currentTime = System.currentTimeMillis();
            long delay = this.Command_time.get().longValue() * 1000;

            if (currentTime - lastSentTime >= delay) {
                mc.player.sendChatMessage(name.get());
                lastSentTime = currentTime;

            }
        }
        if (vibor.is("Прыжок")) {
            long currentTime = System.currentTimeMillis();
            long delay = this.Jump_Time.get().longValue() * 1000;
            if (currentTime - lastSentTime >= delay) {
                if (mc.player.isOnGround()) mc.player.jump();
                lastSentTime = currentTime;

            }
        }
        if (vibor.is("Ротация")) {
            long currentTime = System.currentTimeMillis();
            long delay = this.Rotation_time.get().longValue() * 1000;
            if (currentTime - lastSentTime >= delay) {
                mc.player.rotationYaw += Rotation.get();
                lastSentTime = currentTime;
            }


        }
    }
}