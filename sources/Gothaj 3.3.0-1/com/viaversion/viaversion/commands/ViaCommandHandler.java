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

public abstract class ViaCommandHandler implements ViaVersionCommand {
   private final Map<String, ViaSubCommand> commandMap = new HashMap<>();

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
      boolean hasPermissions = sender.hasPermission("viaversion.admin");

      for (ViaSubCommand command : this.commandMap.values()) {
         if (sender.hasPermission(command.permission())) {
            hasPermissions = true;
            break;
         }
      }

      if (!hasPermissions) {
         sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use this command!"));
         return false;
      } else if (args.length == 0) {
         this.showHelp(sender);
         return false;
      } else if (!this.hasSubCommand(args[0])) {
         sender.sendMessage(ViaSubCommand.color("&cThis command does not exist."));
         this.showHelp(sender);
         return false;
      } else {
         ViaSubCommand handler = this.getSubCommand(args[0]);
         if (!this.hasPermission(sender, handler.permission())) {
            sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use this command!"));
            return false;
         } else {
            String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
            boolean result = handler.execute(sender, subArgs);
            if (!result) {
               sender.sendMessage("Usage: /viaversion " + handler.usage());
            }

            return result;
         }
      }
   }

   @Override
   public List<String> onTabComplete(ViaCommandSender sender, String[] args) {
      Set<ViaSubCommand> allowed = this.calculateAllowedCommands(sender);
      List<String> output = new ArrayList<>();
      if (args.length == 1) {
         if (!args[0].isEmpty()) {
            for (ViaSubCommand sub : allowed) {
               if (sub.name().toLowerCase().startsWith(args[0].toLowerCase(Locale.ROOT))) {
                  output.add(sub.name());
               }
            }
         } else {
            for (ViaSubCommand subx : allowed) {
               output.add(subx.name());
            }
         }
      } else if (args.length >= 2 && this.getSubCommand(args[0]) != null) {
         ViaSubCommand subx = this.getSubCommand(args[0]);
         if (!allowed.contains(subx)) {
            return output;
         }

         String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
         List<String> tab = subx.onTabComplete(sender, subArgs);
         Collections.sort(tab);
         if (!tab.isEmpty()) {
            String currArg = subArgs[subArgs.length - 1];

            for (String s : tab) {
               if (s.toLowerCase(Locale.ROOT).startsWith(currArg.toLowerCase(Locale.ROOT))) {
                  output.add(s);
               }
            }
         }

         return output;
      }

      return output;
   }

   @Override
   public void showHelp(ViaCommandSender sender) {
      Set<ViaSubCommand> allowed = this.calculateAllowedCommands(sender);
      if (allowed.isEmpty()) {
         sender.sendMessage(ViaSubCommand.color("&cYou are not allowed to use these commands!"));
      } else {
         sender.sendMessage(ViaSubCommand.color("&aViaVersion &c" + Via.getPlatform().getPluginVersion()));
         sender.sendMessage(ViaSubCommand.color("&6Commands:"));

         for (ViaSubCommand cmd : allowed) {
            sender.sendMessage(ViaSubCommand.color(String.format("&2/viaversion %s &7- &6%s", cmd.usage(), cmd.description())));
         }

         allowed.clear();
      }
   }

   private Set<ViaSubCommand> calculateAllowedCommands(ViaCommandSender sender) {
      Set<ViaSubCommand> cmds = new HashSet<>();

      for (ViaSubCommand sub : this.commandMap.values()) {
         if (this.hasPermission(sender, sub.permission())) {
            cmds.add(sub);
         }
      }

      return cmds;
   }

   private boolean hasPermission(ViaCommandSender sender, String permission) {
      return permission == null || sender.hasPermission("viaversion.admin") || sender.hasPermission(permission);
   }

   private void registerDefaults() {
      this.registerSubCommand(new ListSubCmd());
      this.registerSubCommand(new PPSSubCmd());
      this.registerSubCommand(new DebugSubCmd());
      this.registerSubCommand(new DumpSubCmd());
      this.registerSubCommand(new DisplayLeaksSubCmd());
      this.registerSubCommand(new DontBugMeSubCmd());
      this.registerSubCommand(new AutoTeamSubCmd());
      this.registerSubCommand(new ReloadSubCmd());
   }
}
