package me.finz0.osiris.module.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.modules.misc.Notifications;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VisualRange extends Module {
    public VisualRange() {
        super("VisualRange", Category.CHAT, "Sends a client side message when someone enters your render distance");
    }
    List<Entity> knownPlayers = new ArrayList<>();;
    List<Entity> players;

    public void onUpdate(){
        if(mc.player == null) return;
        players = mc.world.loadedEntityList.stream().filter(e-> e instanceof EntityPlayer).collect(Collectors.toList());
        try {
            for (Entity e : players) {
                if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName())) {
                    if (!knownPlayers.contains(e)) {
                        knownPlayers.add(e);
                        Command.sendClientMessage(ChatFormatting.GREEN+e.getName() + ChatFormatting.RED+ " entered visual range.");
                        if(ModuleManager.isModuleEnabled("Notifications"))
                            Notifications.sendNotification(ChatFormatting.GREEN+e.getName()+ ChatFormatting.RED+ " entered visual range.", TrayIcon.MessageType.INFO);
                    }
                }
            }
        } catch(Exception e){} // ez no crasherino
            try {
                for (Entity e : knownPlayers) {
                    if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName())) {
                        if (!players.contains(e)) {
                            knownPlayers.remove(e);;
                        }
                    }
                }
            } catch(Exception e){} // ez no crasherino pt.2
    }

    public void onDisable(){
        knownPlayers.clear();
    }
}
