package me.finz0.osiris.module.modules.chat;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class AutoReply extends Module {
    public AutoReply() {
        super("AutoReply", Category.CHAT, "Reply to whispers");
    }

    private static String reply = "fuck off";

    @EventHandler
    private Listener<ClientChatReceivedEvent> listener = new Listener<>(event ->{
        if( event.getMessage().getUnformattedText().contains("whispers: ") && !event.getMessage().getUnformattedText().startsWith(mc.player.getName())){
            mc.player.sendChatMessage("/r " + reply);
        }
    });

    public static String getReply(){
        return reply;
    }

    public static void setReply(String r){
        reply = r;
    }

    public void onEnable(){
        AuroraMod.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        AuroraMod.EVENT_BUS.unsubscribe(this);
    }
}
