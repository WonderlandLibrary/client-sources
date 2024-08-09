package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventPacket;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.StringSetting;
import src.Wiksi.utils.math.StopWatch;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;

@FunctionRegister(name="AutoAuth", type=Category.Player)
public class AutoAuth
        extends Function {
    final StringSetting pass = new StringSetting("Пароль", "", "Авто регистрация - логин");
    StopWatch delay = new StopWatch();
    boolean isLogined = false;

    public AutoAuth() {
        this.addSettings(this.pass);
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
                }
                catch (InterruptedException es) {
                    throw new RuntimeException(es);
                }
                if (-(-((0xFFFFFFEF | 0xFFFFFFFE | 0xFFFFFF97) ^ 1)) != -(-((0x1D | 0xFFFFFFFD | 0x31) ^ 0xFFFFFF87))) {
                    // empty if block
                }
                this.isLogined = false;
            }
            if (message.contains("Зарегистрируйтесь") && !this.isLogined && this.delay.isReached(250L)) {
                AutoAuth.mc.player.sendChatMessage("/register " + (String)this.pass.get());
                this.delay.reset();
            }
            if (message.contains("Войдите в игру") && !this.isLogined && this.delay.isReached(250L)) {
                AutoAuth.mc.player.sendChatMessage("/login " + (String)this.pass.get());
                this.delay.reset();
            }
        }
    }
}
