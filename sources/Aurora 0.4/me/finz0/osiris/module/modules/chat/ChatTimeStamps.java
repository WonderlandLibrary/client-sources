package me.finz0.osiris.module.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatTimeStamps extends Module {
    public ChatTimeStamps() {
        super("ChatTimeStamps", Category.CHAT);
        ArrayList<String> formats = new ArrayList<>();
        formats.add("H24:mm");
        formats.add("H12:mm");
        formats.add("H12:mm a");
        formats.add("H24:mm:ss");
        formats.add("H12:mm:ss");
        formats.add("H12:mm:ss a");
        ArrayList<String> deco = new ArrayList<>(); deco.add("< >"); deco.add("[ ]"); deco.add("{ }"); deco.add(" ");
        ArrayList<String> colors = new ArrayList<>();
        for(ChatFormatting cf : ChatFormatting.values()){
            colors.add(cf.getName());
        }

        rSetting(format = new Setting("Format", this, "H12:mm", formats, "ChatTimeStampsFormat"));
        rSetting(color = new Setting("Color", this, ChatFormatting.AQUA.getName(), colors, "ChatTimeStampsColor"));
        rSetting(decoration = new Setting("Deco", this, "< >", deco, "ChatTimeStampsDeco"));
        rSetting(space = new Setting("Space", this, true, "ChatTimeStampsSpace"));
    }

    Setting format;
    Setting color;
    Setting decoration;
    Setting space;

    @EventHandler
    private Listener<ClientChatReceivedEvent> listener = new Listener<>(event -> {
        String decoLeft = decoration.getValString().equalsIgnoreCase(" ") ? "" : decoration.getValString().split(" ")[0];
        String decoRight = decoration.getValString().equalsIgnoreCase(" ") ? "" : decoration.getValString().split(" ")[1];
        if(space.getValBoolean()) decoRight += " ";
        String dateFormat = format.getValString().replace("H24", "k").replace("H12", "h");
        String date = new SimpleDateFormat(dateFormat).format(new Date());
        TextComponentString time = new TextComponentString(ChatFormatting.getByName(color.getValString()) + decoLeft + date + decoRight + ChatFormatting.RESET);
        event.setMessage(time.appendSibling(event.getMessage()));
    });

    public void onEnable(){
        AuroraMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }

}
