package pw.latematt.xiv.mod.mods.render;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.StringUtils;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.MouseClickEvent;
import pw.latematt.xiv.event.events.RenderStringEvent;
import pw.latematt.xiv.event.events.SendPacketEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;
import pw.latematt.xiv.value.Value;

import java.util.Objects;
import java.util.regex.Matcher;

/**
 * @author Matthew
 */
public class NameProtect extends Mod implements Listener<RenderStringEvent>, CommandHandler {
    private final Value<Boolean> tab = new Value<>("nameprotect_tab", true);
    private final Value<Boolean> scoreboard = new Value<>("nameprotect_scoreboard", true);
    private final Value<Boolean> nametag = new Value<>("nameprotect_nametag", true);
    private final Value<Boolean> chat = new Value<>("nameprotect_chat", true);
    private final Value<Boolean> nameComplete = new Value<>("nameprotect_name_complete", true);
    private final Value<Boolean> middleClickFriends = new Value<>("nameprotect_middle_click_friends", true);
    private final Listener sendPacketEvent, mouseClickListener;

    public NameProtect() {
        super("NameProtect", ModType.RENDER);
        Command.newCommand().cmd("nameprotect").description("Base command for NameProtect mod.").arguments("<action>").aliases("np").handler(this).build();

        sendPacketEvent = new Listener<SendPacketEvent>() {
            @Override
            public void onEventCalled(SendPacketEvent event) {
                if (!nameComplete.getValue())
                    return;
                if (event.getPacket() instanceof C01PacketChatMessage) {
                    C01PacketChatMessage message = (C01PacketChatMessage) event.getPacket();
                    for (Object o : mc.ingameGUI.getTabList().getPlayerList()) {
                        NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) o;
                        String mcName = StringUtils.stripControlCodes(mc.ingameGUI.getTabList().getPlayerName(playerInfo));
                        if (XIV.getInstance().getFriendManager().isFriend(mcName)) {
                            String alias = XIV.getInstance().getFriendManager().getContents().get(mcName);
                            message.setMessage(message.getMessage().replaceAll("(?i)" + Matcher.quoteReplacement("-" + alias), mcName));
                        }
                    }
                }
            }
        };

        mouseClickListener = new Listener<MouseClickEvent>() {
            @Override
            public void onEventCalled(MouseClickEvent event) {
                if (!middleClickFriends.getValue())
                    return;
                if (event.getButton() == 2 && mc.thePlayer != null) {
                    if (mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) mc.objectMouseOver.entityHit;
                        if (XIV.getInstance().getFriendManager().isFriend(player.getName()))
                            XIV.getInstance().getFriendManager().remove(player.getName());
                        else
                            XIV.getInstance().getFriendManager().add(player.getName(), player.getName());
                    }
                }
            }
        };
        setEnabled(true);
    }

    @Override
    public void onEventCalled(RenderStringEvent event) {
        if (Objects.equals(event.getState(), RenderStringEvent.State.CHAT) && !chat.getValue())
            return;
        if (Objects.equals(event.getState(), RenderStringEvent.State.TAB) && !tab.getValue())
            return;
        if (Objects.equals(event.getState(), RenderStringEvent.State.SCOREBOARD) && !scoreboard.getValue())
            return;
        if (Objects.equals(event.getState(), RenderStringEvent.State.NAMETAG) && !nametag.getValue())
            return;

        event.setString(protect(event.getString(), !Objects.equals(event.getState(), RenderStringEvent.State.NAMETAG)));
    }

    public String protect(String string, boolean color) {
        string = XIV.getInstance().getFriendManager().replace(string, color);
        return string;
    }

    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");
        if (arguments.length >= 2) {
            String action = arguments[1];
            switch (action.toLowerCase()) {
                case "tab":
                    if (arguments.length >= 3) {
                        tab.setValue(arguments[2].equalsIgnoreCase("-d") || Boolean.parseBoolean(arguments[2]));
                    } else {
                        tab.setValue(!tab.getValue());
                    }
                    ChatLogger.print(String.format("NameProtect will %s protect in tab.", (tab.getValue() ? "now" : "no longer")));
                    break;
                case "chat":
                    if (arguments.length >= 3) {
                        chat.setValue(arguments[2].equalsIgnoreCase("-d") || Boolean.parseBoolean(arguments[2]));
                    } else {
                        chat.setValue(!chat.getValue());
                    }
                    ChatLogger.print(String.format("NameProtect will %s protect in chat.", (chat.getValue() ? "now" : "no longer")));
                    break;
                case "nametag":
                    if (arguments.length >= 3) {
                        nametag.setValue(arguments[2].equalsIgnoreCase("-d") || Boolean.parseBoolean(arguments[2]));
                    } else {
                        nametag.setValue(!nametag.getValue());
                    }
                    ChatLogger.print(String.format("NameProtect will %s protect in nametags.", (nametag.getValue() ? "now" : "no longer")));
                    break;
                case "scoreboard":
                    if (arguments.length >= 3) {
                        scoreboard.setValue(arguments[2].equalsIgnoreCase("-d") || Boolean.parseBoolean(arguments[2]));
                    } else {
                        scoreboard.setValue(!scoreboard.getValue());
                    }
                    ChatLogger.print(String.format("NameProtect will %s protect in scoreboard.", (scoreboard.getValue() ? "now" : "no longer")));
                    break;
                case "middleclickfriends":
                case "middlecf":
                case "mcf":
                    if (arguments.length >= 3) {
                        middleClickFriends.setValue(arguments[2].equalsIgnoreCase("-d") || Boolean.parseBoolean(arguments[2]));
                    } else {
                        middleClickFriends.setValue(!middleClickFriends.getValue());
                    }
                    ChatLogger.print(String.format("NameProtect will %s friend players you middle click.", (middleClickFriends.getValue() ? "now" : "no longer")));
                    break;
                case "namecomplete":
                case "dashnames":
                    if (arguments.length >= 3) {
                        nameComplete.setValue(arguments[2].equalsIgnoreCase("-d") || Boolean.parseBoolean(arguments[2]));
                    } else {
                        nameComplete.setValue(!nameComplete.getValue());
                    }
                    ChatLogger.print(String.format("NameProtect will %s complete names in sent chat messages.", (nameComplete.getValue() ? "now" : "no longer")));
                    break;
                default:
                    ChatLogger.print("Invalid action, valid: chat, nametag, scoreboard, tab, middleclickfriends, namecomplete");
                    break;
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: nameprotect <action>");
        }
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this, sendPacketEvent, mouseClickListener);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this, sendPacketEvent, mouseClickListener);
    }
}
