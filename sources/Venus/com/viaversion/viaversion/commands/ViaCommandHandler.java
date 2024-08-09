/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
    public void registerSubCommand(ViaSubCommand viaSubCommand) {
        Preconditions.checkArgument(viaSubCommand.name().matches("^[a-z0-9_-]{3,15}$"), viaSubCommand.name() + " is not a valid sub-command name.");
        Preconditions.checkArgument(!this.hasSubCommand(viaSubCommand.name()), "ViaSubCommand " + viaSubCommand.name() + " does already exists!");
        this.commandMap.put(viaSubCommand.name().toLowerCase(Locale.ROOT), viaSubCommand);
    }

    @Override
    public boolean hasSubCommand(String string) {
        return this.commandMap.containsKey(string.toLowerCase(Locale.ROOT));
    }

    @Override
    public ViaSubCommand getSubCommand(String string) {
        return this.commandMap.get(string.toLowerCase(Locale.ROOT));
    }

    @Override
    public boolean onCommand(ViaCommandSender viaCommandSender, String[] stringArray) {
        if (stringArray.length == 0) {
            this.showHelp(viaCommandSender);
            return true;
        }
        if (!this.hasSubCommand(stringArray[0])) {
            viaCommandSender.sendMessage(ViaSubCommand.color("&cThis command does not exist."));
            this.showHelp(viaCommandSender);
            return true;
        }
        ViaSubCommand viaSubCommand = this.getSubCommand(stringArray[0]);
        if (!this.hasPermission(viaCommandSender, viaSubCommand.permission())) {
            viaCommandSender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use this command!"));
            return true;
        }
        String[] stringArray2 = Arrays.copyOfRange(stringArray, 1, stringArray.length);
        boolean bl = viaSubCommand.execute(viaCommandSender, stringArray2);
        if (!bl) {
            viaCommandSender.sendMessage("Usage: /viaversion " + viaSubCommand.usage());
        }
        return bl;
    }

    @Override
    public List<String> onTabComplete(ViaCommandSender viaCommandSender, String[] stringArray) {
        Set<ViaSubCommand> set = this.calculateAllowedCommands(viaCommandSender);
        ArrayList<String> arrayList = new ArrayList<String>();
        if (stringArray.length == 1) {
            if (!stringArray[0].isEmpty()) {
                for (ViaSubCommand viaSubCommand : set) {
                    if (!viaSubCommand.name().toLowerCase().startsWith(stringArray[0].toLowerCase(Locale.ROOT))) continue;
                    arrayList.add(viaSubCommand.name());
                }
            } else {
                for (ViaSubCommand viaSubCommand : set) {
                    arrayList.add(viaSubCommand.name());
                }
            }
        } else if (stringArray.length >= 2 && this.getSubCommand(stringArray[0]) != null) {
            ViaSubCommand viaSubCommand = this.getSubCommand(stringArray[0]);
            if (!set.contains(viaSubCommand)) {
                return arrayList;
            }
            String[] stringArray2 = Arrays.copyOfRange(stringArray, 1, stringArray.length);
            List<String> list = viaSubCommand.onTabComplete(viaCommandSender, stringArray2);
            Collections.sort(list);
            return list;
        }
        return arrayList;
    }

    @Override
    public void showHelp(ViaCommandSender viaCommandSender) {
        Set<ViaSubCommand> set = this.calculateAllowedCommands(viaCommandSender);
        if (set.isEmpty()) {
            viaCommandSender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use these commands!"));
            return;
        }
        viaCommandSender.sendMessage(ViaSubCommand.color("&aViaVersion &c" + Via.getPlatform().getPluginVersion()));
        viaCommandSender.sendMessage(ViaSubCommand.color("&6Commands:"));
        for (ViaSubCommand viaSubCommand : set) {
            viaCommandSender.sendMessage(ViaSubCommand.color(String.format("&2/viaversion %s &7- &6%s", viaSubCommand.usage(), viaSubCommand.description())));
        }
        set.clear();
    }

    private Set<ViaSubCommand> calculateAllowedCommands(ViaCommandSender viaCommandSender) {
        HashSet<ViaSubCommand> hashSet = new HashSet<ViaSubCommand>();
        for (ViaSubCommand viaSubCommand : this.commandMap.values()) {
            if (!this.hasPermission(viaCommandSender, viaSubCommand.permission())) continue;
            hashSet.add(viaSubCommand);
        }
        return hashSet;
    }

    private boolean hasPermission(ViaCommandSender viaCommandSender, String string) {
        return string == null || viaCommandSender.hasPermission(string);
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

