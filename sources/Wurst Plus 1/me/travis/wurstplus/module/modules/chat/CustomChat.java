package me.travis.wurstplus.module.modules.chat;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.travis.wurstplus.event.events.PacketEvent;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import net.minecraft.network.play.client.CPacketChatMessage;

@Module.Info(name = "CustomChat", category = Module.Category.CHAT)
public class CustomChat extends Module {

    private Setting<ChatSetting> mode = register(Settings.e("Mode", ChatSetting.Wurst));
    private Setting<Boolean> commands = register(Settings.b("Commands", false));
    private Setting<Boolean> twoBMode = register(Settings.b("2B Mode", false));

    public String wurstplus_SUFFIX;

    @EventHandler
    public Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (mode.getValue() == ChatSetting.Wurst) {
           wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | wurst+" : " \u2763 \u1E88\u1D1C\u0280\uA731\u1D1B\u002B" );
        } else if (mode.getValue() == ChatSetting.Cuboydgod) {
            wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | cuboydgod" :" \u2763 \u1D04\u1D1C\u0299\u1D0F\u1D05\u0262\u1D0F\u1D05 ");
        } else if (mode.getValue() == ChatSetting.Travisgod) {
            wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | travisgod" :" \u2763 \u1D1B\u0280\u1D00\u1D20\u026A\uA731\u0262\u1D0F\u1D05 ");
        } else if (mode.getValue() == ChatSetting.Tabbottgod) {
            wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | tabbbottgod" : " \u2763 \u1D1B\u1D00\u0299\u0299\u1D0F\u1D1B\u1D1B\u0262\u1D0F\u1D05 ");
        } else if (mode.getValue() == ChatSetting.Chaegod) {
            wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | chaegod" : " \u2763 \u1D04\u029C\u1D00\u1D07\u0262\u1D0F\u1D05 ");
        } else if (mode.getValue() == ChatSetting.Naughtygod) {
            wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | naughtygod" : " \u2763 \u0274\u1D00\u1D1C\u0262\u029C\u1D1B\u0262\u1D0F\u1D05 ");
        } else if (mode.getValue() == ChatSetting.Chadgod) {
            wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | chadgod" : " \u2763 \u1D04\u029C\u1D00\u1D05\u0262\u1D0F\u1D05 ");
        } else if (mode.getValue() == ChatSetting.Haltgod) {
            wurstplus_SUFFIX = (this.twoBMode.getValue() ? " | haltgod" : " \u2763 \u029C\u1D00\u029F\u1D1B\u0262\u1D0F\u1D05 ");
        }

        if (event.getPacket() instanceof CPacketChatMessage) {
            String s = ((CPacketChatMessage) event.getPacket()).getMessage();
            if (s.startsWith("/") && !commands.getValue()) return;
            s += wurstplus_SUFFIX;
            if (s.length() >= 256) s = s.substring(0,256);
            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    });

    public static enum ChatSetting {
        Wurst, Haltgod, Cuboydgod, Travisgod, Tabbottgod, Chaegod, Naughtygod, Chadgod
    }

    public Boolean getCommand() {
        return this.commands.getValue();
    }

}
