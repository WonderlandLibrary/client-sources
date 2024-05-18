// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

import java.util.Collection;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Events.EventChatSend;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Collection.Cmds.VClipCmd;
import net.minecraft.client.network.badlion.Mod.Collection.Cmds.ToggleCmd;
import net.minecraft.client.network.badlion.Mod.Collection.Cmds.AuraCmd;
import net.minecraft.client.network.badlion.Mod.Collection.Cmds.Mods;
import net.minecraft.client.network.badlion.Mod.Collection.Cmds.HelpCmd;
import net.minecraft.client.network.badlion.Mod.Collection.Cmds.FriendCmd;
import net.minecraft.client.network.badlion.Mod.Collection.Cmds.BindCmd;
import net.minecraft.client.network.badlion.Mod.Cmd;
import java.util.TreeMap;

public class CmdMgr
{
    private final TreeMap<String, Cmd> cmds;
    
    public CmdMgr() {
        this.cmds = new TreeMap<String, Cmd>();
        this.add(new BindCmd());
        this.add(new FriendCmd());
        this.add(new HelpCmd());
        this.add(new Mods());
        this.add(new AuraCmd());
        this.add(new ToggleCmd());
        this.add(new VClipCmd());
        EventManager.register(this);
    }
    
    private void add(final Cmd cmd) {
        this.cmds.put(cmd.getCmdName(), cmd);
    }
    
    @EventTarget
    public void onChatSend(final EventChatSend event) {
        if (!Badlion.getWinter().legit.isEnabled()) {
            final String message = event.message;
            if (message.startsWith(".")) {
                event.setCancelled(true);
                final String input = message.substring(1);
                final String commandName = input.split(" ")[0];
                String[] args;
                if (input.contains(" ")) {
                    args = input.substring(input.indexOf(" ") + 1).split(" ");
                }
                else {
                    args = new String[0];
                }
                final Cmd cmd = this.getCommandByName(commandName);
                if (cmd != null) {
                    try {
                        cmd.execute(args);
                    }
                    catch (Cmd.SyntaxError e) {
                        if (e.getMessage() != null) {
                            ChatUtils.sendMessageToPlayer(new StringBuilder().append(e.getMessage()).toString());
                        }
                        else {
                            ChatUtils.sendMessageToPlayer("");
                        }
                        cmd.printSyntax();
                    }
                    catch (Cmd.Error e2) {
                        ChatUtils.sendMessageToPlayer(e2.getMessage());
                    }
                    catch (Exception ex) {}
                }
                else {
                    ChatUtils.sendMessageToPlayer("\"." + commandName + "\" is not a valid command.");
                }
            }
        }
    }
    
    public Cmd getCommandByName(final String name) {
        return this.cmds.get(name);
    }
    
    public Collection<Cmd> getCmds() {
        return this.cmds.values();
    }
    
    public int countCommands() {
        return this.cmds.size();
    }
}
