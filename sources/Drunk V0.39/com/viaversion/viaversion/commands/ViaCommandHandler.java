/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.commands;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.command.ViaVersionCommand;
import com.viaversion.viaversion.commands.defaultsubs.AutoTeamSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DebugSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DisplayLeaksSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DontBugMeSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.DumpSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.HelpSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.ListSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.PPSSubCmd;
import com.viaversion.viaversion.commands.defaultsubs.ReloadSubCmd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class ViaCommandHandler
implements ViaVersionCommand {
    private final Map<String, ViaSubCommand> commandMap = new HashMap<String, ViaSubCommand>();

    protected ViaCommandHandler() {
        this.registerDefaults();
    }

    @Override
    public void registerSubCommand(ViaSubCommand command) {
        Preconditions.checkArgument(command.name().matches("^[a-z0-9_-]{3,15}$"), command.name() + " is not a valid sub-command name.");
        Preconditions.checkArgument(!this.hasSubCommand(command.name()), "ViaSubCommand " + command.name() + " does already exists!");
        this.commandMap.put(command.name().toLowerCase(Locale.ROOT), command);
    }

    @Override
    public boolean hasSubCommand(String name) {
        return this.commandMap.containsKey(name.toLowerCase(Locale.ROOT));
    }

    @Override
    public ViaSubCommand getSubCommand(String name) {
        return this.commandMap.get(name.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean onCommand(ViaCommandSender sender, String[] args) {
        if (args.length == 0) {
            this.showHelp(sender);
            return false;
        }
        if (!this.hasSubCommand(args[0])) {
            sender.sendMessage(ViaSubCommand.color("&cThis command does not exist."));
            this.showHelp(sender);
            return false;
        }
        ViaSubCommand handler = this.getSubCommand(args[0]);
        if (!this.hasPermission(sender, handler.permission())) {
            sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use this command!"));
            return false;
        }
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        boolean result = handler.execute(sender, subArgs);
        if (result) return result;
        sender.sendMessage("Usage: /viaversion " + handler.usage());
        return result;
    }

    @Override
    public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
        Set<ViaSubCommand> allowed = this.calculateAllowedCommands(sender);
        ArrayList<String> output = new ArrayList<String>();
        if (args.length == 1) {
            if (!args[0].isEmpty()) {
                Iterator<ViaSubCommand> iterator = allowed.iterator();
                while (iterator.hasNext()) {
                    ViaSubCommand sub = iterator.next();
                    if (!sub.name().toLowerCase().startsWith(args[0].toLowerCase(Locale.ROOT))) continue;
                    output.add(sub.name());
                }
                return output;
            }
            Iterator<ViaSubCommand> iterator = allowed.iterator();
            while (iterator.hasNext()) {
                ViaSubCommand sub = iterator.next();
                output.add(sub.name());
            }
            return output;
        }
        if (args.length < 2) return output;
        if (this.getSubCommand(args[0]) == null) return output;
        ViaSubCommand sub = this.getSubCommand(args[0]);
        if (!allowed.contains(sub)) {
            return output;
        }
        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        List<String> tab = sub.onTabComplete(sender, subArgs);
        Collections.sort(tab);
        return tab;
    }

    @Override
    public void showHelp(ViaCommandSender sender) {
        Set<ViaSubCommand> allowed = this.calculateAllowedCommands(sender);
        if (allowed.isEmpty()) {
            sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use these commands!"));
            return;
        }
        sender.sendMessage(ViaSubCommand.color("&aViaVersion &c" + Via.getPlatform().getPluginVersion()));
        sender.sendMessage(ViaSubCommand.color("&6Commands:"));
        Iterator<ViaSubCommand> iterator = allowed.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                allowed.clear();
                return;
            }
            ViaSubCommand cmd = iterator.next();
            sender.sendMessage(ViaSubCommand.color(String.format("&2/viaversion %s &7- &6%s", cmd.usage(), cmd.description())));
        }
    }

    private Set<ViaSubCommand> calculateAllowedCommands(ViaCommandSender sender) {
        HashSet<ViaSubCommand> cmds = new HashSet<ViaSubCommand>();
        Iterator<ViaSubCommand> iterator = this.commandMap.values().iterator();
        while (iterator.hasNext()) {
            ViaSubCommand sub = iterator.next();
            if (!this.hasPermission(sender, sub.permission())) continue;
            cmds.add(sub);
        }
        return cmds;
    }

    private boolean hasPermission(ViaCommandSender sender, String permission) {
        if (permission == null) return true;
        if (sender.hasPermission(permission)) return true;
        return false;
    }

    private void registerDefaults() {
        this.registerSubCommand(new ListSubCmd());
        this.registerSubCommand(new PPSSubCmd());
        this.registerSubCommand(new DebugSubCmd());
        this.registerSubCommand(new DumpSubCmd());
        this.registerSubCommand(new DisplayLeaksSubCmd());
        this.registerSubCommand(new DontBugMeSubCmd());
        this.registerSubCommand(new AutoTeamSubCmd());
        this.registerSubCommand(new HelpSubCmd());
        this.registerSubCommand(new ReloadSubCmd());
    }
}

