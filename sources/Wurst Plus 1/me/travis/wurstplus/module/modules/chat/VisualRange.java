package me.travis.wurstplus.module.modules.chat;

import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

// t r a v i s

@Module.Info(name="VisualRange", category=Module.Category.CHAT)
public class VisualRange
extends Module {
    private Setting<Boolean> publicChat = this.register(Settings.b("PublicChat", false));
    private Setting<Boolean> leaving = this.register(Settings.b("Leaving", true));
    private List<String> knownPlayers;

    @Override
    public void onUpdate() {
        if (VisualRange.mc.player == null) {
            return;
        }
        ArrayList<String> tickPlayerList = new ArrayList<String>();
        for (Entity entity : VisualRange.mc.world.getLoadedEntityList()) {
            if (!(entity instanceof EntityPlayer)) continue;
            tickPlayerList.add(entity.getName());
        }
        if (tickPlayerList.size() > 0) {
            for (String playerName : tickPlayerList) {
                if (playerName.equals(VisualRange.mc.player.getName()) || this.knownPlayers.contains(playerName)) continue;
                this.knownPlayers.add(playerName);
                if (this.publicChat.getValue().booleanValue()) {
                    VisualRange.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("I see " + playerName + ", how you doing?"));
                } else if (Friends.isFriend(playerName)) {
                    Command.sendChatMessage("\u00A76" + playerName + "\u00a7r" + " is here! hia");
                } else {
                    Command.sendChatMessage("\u00A73" + playerName + "\u00a7r" + " is here! yuk");
                }
                return;
            }
        }
        if (this.knownPlayers.size() > 0) {
            for (String playerName : this.knownPlayers) {
                if (tickPlayerList.contains(playerName)) continue;
                this.knownPlayers.remove(playerName);
                if (this.leaving.getValue().booleanValue()) {
                    if (this.publicChat.getValue().booleanValue()) {
                        VisualRange.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("Can't see " + playerName + " anymore :("));
                    } else if (Friends.isFriend(playerName)) {
                        Command.sendChatMessage("\u00A76" + playerName + "\u00a7r" + " has left, I hope they're ok :)");
                    } else {
                        Command.sendChatMessage("\u00A73" + playerName + "\u00a7r" + " has gone, rather upsetting if you ask me");
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onEnable() {
        this.knownPlayers = new ArrayList<String>();
    }
}

