package me.zeroeightsix.kami.module.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FINZ0 on 12 December 2019
 * Updated by hub on 14 December 2019
 */
@Module.Info(name = "ChatTimeStamps", category = Module.Category.CHAT)
public class ChatTimeStamps extends Module {

    private Setting<Boolean> deco = register(Settings.b("Deco", true));

    @EventHandler
    public Listener<ClientChatReceivedEvent> listener = new Listener<>(event -> {

        TextComponentString newTextComponentString = new TextComponentString(ChatFormatting.GRAY + (deco.getValue() ? "<" : "") + new SimpleDateFormat("k:mm").format(new Date()) + (deco.getValue() ? ">" : "") + ChatFormatting.RESET + " ");
        newTextComponentString.appendSibling(event.getMessage());

        event.setMessage(newTextComponentString);

    });

}
