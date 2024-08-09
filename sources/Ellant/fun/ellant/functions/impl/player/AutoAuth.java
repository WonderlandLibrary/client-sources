package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.StringSetting;
import fun.ellant.utils.math.StopWatch;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;

@FunctionRegister(name = "AutoAuth", type = Category.PLAYER, desc = "Пон?")

public class AutoAuth extends Function {
    final StringSetting pass = new StringSetting("Пароль", "123456", "Авто регистрация - логин");
    StopWatch delay = new StopWatch();
    boolean isLogined = false;

    public AutoAuth() {
        this.addSettings(new Setting[]{this.pass});
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        IPacket<?> packet = eventPacket.getPacket();
        if (packet instanceof SChatPacket) {
            SChatPacket chatPacket = (SChatPacket)packet;
            String message = chatPacket.getChatComponent().getString();
            if (message.contains("Успешная авторизация!")) {
                this.isLogined = true;

                try {
                    Thread.sleep(500L);
                } catch (InterruptedException var6) {
                    throw new RuntimeException(var6);
                }

                this.isLogined = false;
            }

            if (message.contains("Зарегистрируйтесь") && !this.isLogined && this.delay.isReached(250L)) {
                mc.player.sendChatMessage("/register " + (String)this.pass.get());
                this.delay.reset();
            }

            if (message.contains("Войдите в игру") && !this.isLogined && this.delay.isReached(250L)) {
                mc.player.sendChatMessage("/login " + (String)this.pass.get());
                this.delay.reset();
            }
        }

    }
}