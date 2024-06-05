package client.metaware.impl.module.render;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.gui.notis.NotificationType;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.system.TimerUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.network.play.server.S02PacketChat;

import java.util.concurrent.CompletableFuture;

@ModuleInfo(name = "StaffAnalyzer", renderName = "StaffAnalyzer", category = Category.VISUALS)
public class StaffShitalyzer extends Module {

    private final DoubleProperty checkIntervalProperty = new DoubleProperty("Interval", 15, 10, 900, 10, Property.Representation.INT);

    private int staffBans;
    private final TimerUtil timerUtil = new TimerUtil();
    private boolean recievedAPIKey;
    private String apiKey;

    @EventHandler
    private final Listener<PacketEvent> packetInboundListener = event -> {
        if(event.getPacket() instanceof S02PacketChat) {
            if (mc.isSingleplayer() || mc.thePlayer == null || mc.theWorld == null) return;
            S02PacketChat chat = event.getPacket();
            String message = ChatFormatting.stripFormatting(chat.getChatComponent().getFormattedText());
            if(message.startsWith("Your new API key is ")) {
                apiKey = message.replace("Your new API key is ", "");
                recievedAPIKey = true;
            }
        }
    };

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = event -> {
        if(!(mc.currentScreen instanceof GuiIngameMenu) && mc.currentScreen != null || mc.thePlayer == null || mc.theWorld == null) return;
        if(timerUtil.delay(checkIntervalProperty.getValue().longValue() * 1000) && recievedAPIKey) {
            CompletableFuture<HttpUtil.HttpResponse> completableFuture = HttpUtil.asyncHttpsConnection("https://api.hypixel.net/punishmentstats?key=" + apiKey);
            completableFuture.whenCompleteAsync((response, throwable) -> {
                JsonObject object = JsonParser.parseString(response.content()).getAsJsonObject();
                int lastStaffBans = staffBans;
                if(object.has("staff_total"))
                    staffBans = object.get("staff_total").getAsInt();
                int banDifference = staffBans - lastStaffBans;
                if(banDifference >= 0 && lastStaffBans != 0)
                    Metaware.INSTANCE.getNotificationManager().pop("Staff Analyzer", "Staff has banned " + banDifference + " players in the last " + checkIntervalProperty.getValue().intValue() + " seconds.", 3000, NotificationType.WARNING);
            });
            timerUtil.reset();
        }
    };

    public boolean hasRecievedAPIKey() {
        return recievedAPIKey;
    }
}