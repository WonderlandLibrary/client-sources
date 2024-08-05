package studio.dreamys.module.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;
import studio.dreamys.near;
import studio.dreamys.setting.Setting;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Hide extends Module {
    public static final HashMap<String, String> filters = new HashMap<>();

    public Hide() {
        super("Hide", Category.CHAT);

        //register filters
        filters.put("Implosion", "Your Implosion hit (.*) for (.*) damage");
        filters.put("Teleport Blocks", "There are blocks in the way!");
        filters.put("Kill Combo", "\\+(.*) Kill Combo|Your Kill Combo has expired! You reached a (.*) Kill Combo!");
        filters.put("Watchdog", "Blacklisted modifications are a bannable offense!|Staff have banned an additional|\\[WATCHDOG ANNOUNCEMENT]|Watchdog has banned");
        filters.put("Guild EXP", "You earned (.*) GEXP from playing SkyBlock!");
        filters.put("Line Spacing", "(^(\\r\\n|\\n|\\r)$)|(^(\\r\\n|\\n|\\r))|^\\s*$");
        filters.put("Server Join", "You are playing on profile: (.*)|Sending to server (.*)\\.\\.\\.|Warping(.*)\\.\\.\\.");
        filters.put("Create SMP", "You can now create your own Hypixel SMP server!");

        //register each setting
        filters.keySet().forEach(setting -> set(new Setting(setting, this, true)));
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent e) {
        String msg = ChatFormatting.stripFormatting(e.message.getUnformattedText());
        //if chat message
        if (e.type == 0) {
            for (Map.Entry<String, String> filter : filters.entrySet()) {
                if (Pattern.compile(filter.getValue()).matcher(msg).find() && near.settingsManager.getSettingByName(this, filter.getKey()).getValBoolean()) {
                    e.setCanceled(true); //do not display message
                    return; //get out of the loop
                }
            }
        }
    }
}
