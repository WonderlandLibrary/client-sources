package cc.slack.features.modules.impl.utilties;

import cc.slack.start.Slack;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.WorldEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.impl.render.Interface;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S1DPacketEntityEffect;

@ModuleInfo(
        name = "AntiStaff",
        category = Category.UTILITIES
)
public class AntiStaff extends Module {

    // Server Mode
    private final ModeValue<String> serverMode = new ModeValue<>("Server", new String[]{"Universocraft", "BlocksMC"});

    // Actions
    private final BooleanValue notifyValue = new BooleanValue("Notification", true);
    private final BooleanValue chatnotifyValue = new BooleanValue("Chat Notification", true);
    private final BooleanValue leaveValue = new BooleanValue("Leave on Detect", false);

    // Staffs Names
    private final String universocraftStaff = "0edx_ 0_Lily 1Kao denila  fxrchus  haaaaaaaaaaax_ iBlackSoulz iMxon_ JuliCarles kvvwro Tauchet wSilv6r _JuPo_";

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});

    // Variables
    private boolean detected = false;
    private String staffs = "";

    public AntiStaff() {
        addSettings(serverMode, notifyValue, chatnotifyValue, leaveValue, displayMode);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onWorld (WorldEvent event) {
        staffs = "";

        switch (serverMode.getValue()) {
            case "Universocraft":
                staffs = staffs + " " + universocraftStaff;
                break;
            case "BlocksMC":
                break;
        }

        detected = false;
    }

    @Listen
    public void onPacket (PacketEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null) return;

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S1DPacketEntityEffect) {
            Entity entity = mc.theWorld.getEntityByID(((S1DPacketEntityEffect) packet).getEntityId());
            if (entity != null && (staffs.contains(entity.getCommandSenderName()) || staffs.contains(entity.getDisplayName().getUnformattedText()))) {
                if (!detected) {
                    if (notifyValue.getValue()){
                        Slack.getInstance().getModuleManager().getInstance(Interface.class).addNotification("AntiStaff:  Staff Detected!", "", 7500L, Slack.NotificationStyle.WARN);
                    }

                    if (chatnotifyValue.getValue()) {
                        mc.thePlayer.sendChatMessage(("%staff% was detected as a staff member!").replace("%staff%", entity.getCommandSenderName()));
                    }
                    if (leaveValue.getValue()) {
                        switch (serverMode.getValue()) {
                            case "Universocraft":
                                mc.thePlayer.sendChatMessage("/hub");
                                break;
                            case "BlocksMC":
                                break;
                        }
                    }

                    detected = true;
                }
            }
        }
        if (packet instanceof S14PacketEntity) {
            Entity entity = ((S14PacketEntity) packet).getEntity(mc.theWorld);

            if (entity != null && (staffs.contains(entity.getCommandSenderName()) || staffs.contains(entity.getDisplayName().getUnformattedText()))) {
                if (!detected) {
                    if (notifyValue.getValue()){
                        Slack.getInstance().getModuleManager().getInstance(Interface.class).addNotification("AntiStaff:  Staff Detected!", "", 1500L, Slack.NotificationStyle.WARN);
                    }

                    if (chatnotifyValue.getValue()) {
                        mc.thePlayer.sendChatMessage(("%staff% was detected as a staff member!").replaceAll("%staff%", entity.getCommandSenderName()));
                    }

                    if (leaveValue.getValue()) {
                        switch (serverMode.getValue()) {
                            case "Universocraft":
                                mc.thePlayer.sendChatMessage("/hub");
                                break;
                            case "BlocksMC":
                                break;
                        }
                    }

                    detected = true;
                }
            }
        }
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return serverMode.getValue().toString();
        }
        return null;
    }

}
