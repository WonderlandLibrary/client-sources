package me.zeroeightsix.kami.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 26 October 2019 by hub
 * Updated 23 November 2019 by hub
 * Updated by Viktisen April 2020
 */
@Module.Info(name = "VisualRange", description = "Reports Players in VisualRange", category = Module.Category.MISC)
public class VisualRange extends Module {

    private Setting<Boolean> LOGonRender = register(Settings.b("Log On Render ", false));
    private Setting<Boolean> publicChat = register(Settings.b("PublicChat", false));
    private Setting<Boolean> leaving = register(Settings.b("Leaving", false));
    private Setting<Boolean> sound = register(Settings.b("sound", false));

    private List<String> knownPlayers;

    @Override
    public void onUpdate() {

        if (mc.player == null) {
            return;
        }

        List<String> tickPlayerList = new ArrayList<>();

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                tickPlayerList.add(entity.getName());
            }
        }

        if (tickPlayerList.size() > 0) {
            for (String playerName : tickPlayerList) {
                if (playerName.equals(mc.player.getName())) {
                    continue;
                }
                if (!knownPlayers.contains(playerName)) {
                    knownPlayers.add(playerName);
                    if (publicChat.getValue()) {
                        mc.player.connection.sendPacket(new CPacketChatMessage("" + playerName + " Hittades, tack vare VikNet. "));
                    } else {
                        if (Friends.isFriend(playerName)) {
                            sendNotification(" " + ChatFormatting.GRAY.toString() + playerName + ChatFormatting.GRAY.toString() + " \u00A7aEn v\u00e4n hittades.");
                        } else {
                            sendNotification(" " + ChatFormatting.GRAY.toString() + playerName + ChatFormatting.GRAY.toString() + " \u00A74En fiende hittades.");
                        }
                        if (sound.getValue())
                            mc.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                    }
                    if (LOGonRender.getValue())
                        mc.player.world.sendQuittingDisconnectingPacket();
                    {
                    }
                    return;
                }
            }
        }

        if (knownPlayers.size() > 0) {
            for (String playerName : knownPlayers) {
                if (!tickPlayerList.contains(playerName)) {
                    knownPlayers.remove(playerName);
                    if (leaving.getValue()) {
                        if (publicChat.getValue()) {
                            mc.player.connection.sendPacket(new CPacketChatMessage("Jag kan inte se: " + playerName + " l\u00e4ngre! "));
                        } else {
                            if (Friends.isFriend(playerName)) {
                                sendNotification("[VisualRange] " + ChatFormatting.GREEN.toString() + playerName + ChatFormatting.RESET.toString() + " left my Render Distance!");
                            } else {
                                sendNotification("[VisualRange] " + ChatFormatting.RED.toString() + playerName + ChatFormatting.RESET.toString() + " left my Render Distance!");
                            }
                        }
                    }
                    return;
                }
            }
        }

    }

    private void sendNotification(String s) {
        Command.sendChatMessage(s);
    }

    @Override
    public void onEnable() {
        this.knownPlayers = new ArrayList<>();
    }

}
