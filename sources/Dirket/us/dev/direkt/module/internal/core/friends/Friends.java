package us.dev.direkt.module.internal.core.friends;

import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.StringUtils;
import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.events.game.render.EventRenderNametag;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.internal.core.friends.handler.FriendManager;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.Optional;

/**
 * @author Foundry
 */
@ModData(label = "Friends", aliases = "friend", category = ModCategory.CORE)
public class Friends extends Module {

    public Friends() {
        super.addCommand(new Command(Direkt.getInstance().getCommandManager(), getLabel(), getAliases()) {
            @Executes("add|a")
            public String addFriend(String playerName, Optional<String> playerAlias) {
                if (!playerAlias.isPresent()) {
                    if (Direkt.getInstance().getFriendManager().isFriend(playerName)) {
                        return String.format("%s has already been added as a friend under alias %s.", playerName, Direkt.getInstance().getFriendManager().getAlias(playerName));
                    }
                    Direkt.getInstance().getFriendManager().addFriend(playerName, playerName);
                    return String.format("%s has been added as a friend.", playerName);
                } else {
                    if (Direkt.getInstance().getFriendManager().isFriend(playerName)) {
                        return String.format("%s has already been added as a friend under alias %s.", playerName, Direkt.getInstance().getFriendManager().getAlias(playerName));
                    }
                    Direkt.getInstance().getFriendManager().addFriend(playerName, playerAlias.get());
                    return String.format("%s has been added as a friend under alias %s.", playerName, playerAlias.get());
                }
            }

            @Executes("del|d|remove|r")
            public String addFriend(String playerName) {
                if (!Direkt.getInstance().getFriendManager().isFriend(playerName)) {
                    return String.format("%s has not been added as a friend.", playerName);
                }
                Direkt.getInstance().getFriendManager().removeFriend(playerName);
                return String.format("%s has been removed from friends.", playerName);
            }

            @Executes("list|l")
            public String listFriends() {
                final StringBuilder sb = new StringBuilder("Friends List: " + System.lineSeparator());
                for (FriendManager.Friend f : Direkt.getInstance().getFriendManager().getFriendsList()) {
                    sb.append(f.getName()).append(" : ").append(f.getAlias()).append("" + System.lineSeparator());
                }
                sb.setLength(sb.length() - 1);
                return sb.toString();
            }
        });
    }

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        final CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();
        Direkt.getInstance().getFriendManager().getFriendsList().stream()
                .filter(friend -> StringUtils.containsIgnoreCase(packet.getMessage(), "-" + friend.getAlias()) || StringUtils.containsIgnoreCase(packet.getMessage(), friend.getName()))
                .forEach(friend -> {
                    event.setPacket(new CPacketChatMessage(packet.getMessage().replaceAll("(?i)" + (StringUtils.containsIgnoreCase(packet.getMessage(), "-" + friend.getAlias()) ? "-" + friend.getAlias() : friend.getName()), friend.getName())));
                });
    }, new PacketFilter<>(CPacketChatMessage.class));

    @Listener
    protected Link<EventPreReceivePacket> onPreReceivePacket = new Link<>(event -> {
        final SPacketChat packet = (SPacketChat) event.getPacket();
        Direkt.getInstance().getFriendManager().getFriendsList().stream()
                .filter(friend -> StringUtils.containsIgnoreCase(packet.getChatComponent().getUnformattedText(), friend.getName()) || StringUtils.containsIgnoreCase(packet.getChatComponent().getUnformattedText(), friend.getAlias()))
                .forEach(friend -> {
                    event.setPacket(new SPacketChat(new TextComponentString(packet.getChatComponent().getFormattedText().replaceAll(friend.getName(), friend.getAlias()))));
                });
    }, new PacketFilter<>(SPacketChat.class));

    @Listener
    protected Link<EventRenderNametag> onRenderNametag = new Link<>(event -> {
        /*final String lookupString = event.getEntityName().getUnformattedText().substring(event.getEntityName().getUnformattedText().lastIndexOf("\u00A7")+2);
        if (event.getRenderEntity() instanceof EntityPlayer && Direkt.getInstance().getFriendManager().isFriend(lookupString)) {
            event.setEntityName(new TextComponentString(Direkt.getInstance().getFriendManager().getAlias(lookupString)));
        }*/
    });
}
