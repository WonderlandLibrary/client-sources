package club.bluezenith.module.modules.misc;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.requests.Request;
import club.bluezenith.core.requests.data.ContentType;
import club.bluezenith.core.requests.data.RequestOption;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.SpawnPlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSkull;
import net.minecraft.network.play.server.S14PacketEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.BlueZenith.scheduledExecutorService;

public class StaffChecker extends Module {

    final BooleanValue leaveIfFound = new BooleanValue("Leave if found", false).setIndex(1);
    List<String> staffIGNs = new ArrayList<>();
    List<String> notified = new ArrayList<>();
    ScheduledFuture checkTask;


    public StaffChecker() {
        super("StaffChecker", ModuleCategory.MISC);

    }
    @Listener
    public void onUpdate(PacketEvent event) {
        if(mc.thePlayer == null) return;
        if(checkTask == null && BlueZenith.getBlueZenith().getClientUser() != null) {
            checkTask = scheduledExecutorService.scheduleWithFixedDelay(() ->
                Request.get("https://service.bluezenith.club/api/v3/bmcstaff", getBlueZenith().getRequestExecutor(), ContentType.QUERY, RequestOption.queryOf("api-key", BlueZenith.getBlueZenith().getClientUser().getApiKey())).appendCallback((response ->
                {
                    final JsonElement element = new JsonParser().parse(response.textResponse);
                    if(element.isJsonObject()) {
                        final JsonObject object = element.getAsJsonObject();
                        if(!object.has("status")) {
                            getBlueZenith().getNotificationPublisher().postError(
                                    displayName,
                                    "API returned an invalid response.\nSee logs for more info.",
                                    3500
                            );
                            System.err.println(response.textResponse);
                            getBlueZenith().getModuleManager().getModule(StaffChecker.class).setState(false);
                            checkTask.cancel(true);
                            checkTask = null;
                        }
                        if(!object.get("status").getAsString().equals("success")) {
                            getBlueZenith().getNotificationPublisher().postError(
                                    displayName,
                                    "Couldn't retrieve staff list\nError: " + object.get("message").getAsString(),
                                    5500
                            );
                            getBlueZenith().getModuleManager().getModule(StaffChecker.class).setState(false);
                            checkTask.cancel(true);
                            checkTask = null;
                        }
                        final JsonArray array = object.getAsJsonArray("list").getAsJsonArray();
                        staffIGNs.clear();
                        array.forEach(jsonElement -> staffIGNs.add(jsonElement.getAsString()));
                        staffIGNs = staffIGNs.stream().distinct().collect(Collectors.toList());
                    } else {
                        getBlueZenith().getNotificationPublisher().postError(
                                displayName,
                                "API returned an invalid response. (2)\nSee logs for more info.\n",
                                3500
                        );
                        System.err.println(response.textResponse);
                        getBlueZenith().getModuleManager().getModule(StaffChecker.class).setState(false);
                        checkTask.cancel(true);
                        checkTask = null;
                    }
                })).run().blockThread(), 1, 1, TimeUnit.MINUTES);
        }
        if(event.packet instanceof S14PacketEntity) {
            if(mc.theWorld == null) return;

            final boolean lobby = mc.thePlayer != null
                    && mc.thePlayer.inventory.getStackInSlot(3) != null
                    && mc.thePlayer.inventory.getStackInSlot(3).getItem() instanceof ItemSkull;

            if(lobby) return;

            final S14PacketEntity s0c = (S14PacketEntity) event.packet;
            final Entity entity = s0c.getEntity(mc.theWorld);
            if(entity instanceof EntityPlayer) {
                final String name = ((EntityPlayer) entity).getGameProfile().getName();
                if(!notified.contains(name) && staffIGNs.contains(name)) {
                    notified.add(name);
                    getBlueZenith().getNotificationPublisher().postWarning(displayName,
                            "Detected a staff member: " + name,
                            3500);
                    if(leaveIfFound.get()) {
                        mc.thePlayer.sendChatMessage("/hub");
                    }
                }
            }
        }
    }


    @Listener
    public void onRespawn(SpawnPlayerEvent event) {
        notified.clear();
    }

}
