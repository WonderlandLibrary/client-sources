package com.alan.clients.component.impl.player;

import com.alan.clients.Client;
import com.alan.clients.component.Component;
import com.alan.clients.component.impl.render.CapeComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.BackendPacketEvent;
import com.alan.clients.event.impl.other.ServerJoinEvent;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.chat.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.json.JSONArray;
import org.json.JSONObject;
import rip.vantage.commons.packet.impl.client.community.C2SPacketTabIRC;
import rip.vantage.commons.packet.impl.server.community.S2CPacketTabIRC;
import rip.vantage.network.core.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IRCInfoComponent extends Component implements Accessor {
    public static HashMap<String, UserInfo> usersMap = new HashMap<>();

    public static List<String> checked = new ArrayList<>();
    public static List<String> toCheck = new ArrayList<>();
    @EventLink()
    public final Listener<ServerJoinEvent> onServerJoin = event -> {
        usersMap = new HashMap<>();
        checked = new ArrayList<>();
    };

    public static String formatNick(String message, String name) {
        UserInfo userInfo = usersMap.get(name);

        if (userInfo != null) {
            String suffix = " §7(" + name + "§7)§r";

            String formattedNickname = "§" + userInfo.getColorNick() + userInfo.getMinecraftNickname() + " §7(";
            if (!message.contains(formattedNickname)) {
                int nameIndex = message.indexOf(name);
                if (nameIndex > 1 && message.toCharArray()[nameIndex - 2] == '§') {
                    char colorCode = message.toCharArray()[nameIndex - 1];
                    suffix = " §7(" + "§" + colorCode + name + "§7)§r";
                }

                return message.replaceAll(name,
                        "§" + userInfo.getColorNick() + userInfo.getMinecraftNickname() + suffix);
            }
        } else {
            if (!checked.contains(name)) {
                checked.add(name);

                if (!name.isBlank()) {
                    toCheck.add(name);
                }
            }
        }

        return message;
    }

    public static void checkList() {
        if (!toCheck.isEmpty() && toCheck.get(0) != "") {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (String item : toCheck) {
                stringBuilder.append("\"").append(item).append("\"");
            }
            stringBuilder.append("]");

            String result = "[\"" + String.join("\", \"", toCheck) + "\"]";
            Network.getInstance().getClient().sendMessage(new C2SPacketTabIRC(result).export());
            toCheck = new ArrayList<>();
        }
    }

    @EventLink
    public final Listener<TickEvent> onTickleDeezBalls = event -> {
        CapeComponent.loadCapes();
        CapeComponent.updateCapes();
        for (final EntityPlayer player : mc.theWorld.playerEntities) {
            if (!checked.contains(player.getGameProfile().getName())) {
                checked.add(player.getGameProfile().getName());
                toCheck.add(player.getGameProfile().getName());
            }
        }
        checkList();
    };

    @EventLink
    public final Listener<BackendPacketEvent> onBackend = event -> {
        if (event.getPacket() instanceof S2CPacketTabIRC) {
            S2CPacketTabIRC wrapper = (S2CPacketTabIRC) event.getPacket();
//            System.out.println(wrapper.getMessage());

            String message = wrapper.getMessage();
            JSONObject jsonObject = new JSONObject(message);

            for (String key : jsonObject.keySet()) {
                JSONObject user = new JSONObject(jsonObject.get(key).toString());
                String username = user.getString("f");
                UserInfo vantageUser = new UserInfo(key);
                if (!username.equals("") && !username.equals(" ")) {
                    vantageUser.setMinecraftNickname(username);
                    vantageUser.setStatus(UserInfo.UserInfoStatus.Regular);
                    if (user.getBoolean("h")) {
                        vantageUser.setStatus(UserInfo.UserInfoStatus.Developer);
                    }
                    if (user.getBoolean("g")) {
                        vantageUser.setStatus(UserInfo.UserInfoStatus.Admin);
                    }
                    if (user.getBoolean("i")) {
                        vantageUser.setStatus(UserInfo.UserInfoStatus.Gato);
                    }
                    JSONArray z = user.getJSONArray("j");
                    String[] shit = new String[z.length()];
                    for (int i = 0; i < z.length(); i++) {
                        shit[i] = z.getString(i);
                    }
                    vantageUser.setCustomCapeUrlArray(shit);
                    usersMap.put(key, vantageUser);
                }
            }
        }
    };

    public static String getPrefix(String text, String color) {
        String chatColor = Client.INSTANCE.getThemeManager().getTheme().getChatAccentColor().toString();
        return EnumChatFormatting.BOLD + color + text
                + EnumChatFormatting.RESET + chatColor + " » "
                + EnumChatFormatting.RESET;
    }

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> packet = event.getPacket();

        if (packet instanceof S02PacketChat) {
            final S02PacketChat wrapper = ((S02PacketChat) packet);

            for (IChatComponent chatComponent : new ArrayList<>(wrapper.getChatComponent().getSiblings())) {
                if (chatComponent instanceof ChatComponentText) {
                    String newMessage = chatComponent.getFormattedText();
                    wrapper.getChatComponent().getSiblings().remove(chatComponent);

                    for (Map.Entry<String, UserInfo> entry : usersMap.entrySet()) {
                        newMessage = formatNick(newMessage, entry.getKey());
                    }

                    if (newMessage.equals(chatComponent.getFormattedText())) {
                        wrapper.getChatComponent().getSiblings().add(chatComponent);
                    } else {
                        wrapper.getChatComponent().getSiblings().add(new ChatComponentText(newMessage));
                    }
                }
            }

            event.setPacket(wrapper);
        }
    };
}
