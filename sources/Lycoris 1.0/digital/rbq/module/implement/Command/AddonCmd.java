package digital.rbq.module.implement.Command;

import digital.rbq.Lycoris;
import digital.rbq.addon.AddonManager;
import digital.rbq.addon.api.LycorisAddon;
import digital.rbq.addon.LycorisAPI;
import digital.rbq.addon.api.command.AddonCommand;
import digital.rbq.addon.api.module.AddonModule;
import digital.rbq.module.Command;
import digital.rbq.utility.ChatUtils;

import java.util.List;

@Command.Info(name = "addon", syntax = { "list", "reload", "enable <id>", "disable <id>" }, help = "Manage Addons")
public class AddonCmd extends Command {

    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                List<LycorisAddon> fluxAddonList = Lycoris.INSTANCE.api.getAddonManager().getLycorisAddonList();
                ChatUtils.sendOutputMessage(String.format("[Lycoris API] \247a%d Addon(s) has been loaded.", fluxAddonList.size()));
                for (LycorisAddon fluxAddon : fluxAddonList) {
                    ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2472[%s] \2477ID: %d, Author: %s, Enabled: %s", fluxAddon.getAPIName(), fluxAddonList.indexOf(fluxAddon), fluxAddon.getAuthor(), Lycoris.INSTANCE.api.getAddonManager().getEnabledLycorisAddonList().contains(fluxAddon)));
                    for (AddonModule module : fluxAddon.getModules())
                        ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2472[%s] \2477Registered Module: %s", fluxAddon.getAPIName(), module.getName()));
                    for (AddonCommand command : fluxAddon.getCommands())
                        ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2472[%s] \2477Registered Command: %s", fluxAddon.getAPIName(), command.getName()));
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                ChatUtils.sendOutputMessage("[Lycoris API] \2477Reloading Lycoris API...");
                AddonManager.reload();
            } else {
                syntaxError();
            }
        } else if (args.length == 2) {
            List<LycorisAddon> fluxAddonList = Lycoris.INSTANCE.api.getAddonManager().getLycorisAddonList();
            try {
                int id = Integer.parseInt(args[1]);
                LycorisAddon targetAddon = fluxAddonList.get(id);
                if (targetAddon != null) {
                    if (args[0].equalsIgnoreCase("enable")) {
                        if (Lycoris.INSTANCE.api.getAddonManager().getEnabledLycorisAddonList().contains(targetAddon)) {
                            ChatUtils.sendErrorToPlayer(String.format("[Lycoris API] \2477%s has already enabled!", targetAddon.getAPIName()));
                        } else {
                            AddonManager.getEnabledAddonsName().add(targetAddon.getAPIName());
                            LycorisAPI.FLUX_API.saveAddons();
                            Lycoris.INSTANCE.api = new LycorisAPI();
                            ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2477%s enabled successfully!", targetAddon.getAPIName()));
                        }
                    } else if (args[0].equalsIgnoreCase("disable")) {
                        if (!Lycoris.INSTANCE.api.getAddonManager().getEnabledLycorisAddonList().contains(targetAddon)) {
                            ChatUtils.sendErrorToPlayer(String.format("[Lycoris API] \2477%s has already disabled!", targetAddon.getAPIName()));
                        } else {
                            AddonManager.getEnabledAddonsName().remove(targetAddon.getAPIName());
                            LycorisAPI.FLUX_API.saveAddons();
                            Lycoris.INSTANCE.api = new LycorisAPI();
                            ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2477%s disabled successfully!", targetAddon.getAPIName()));
                        }
                    } else {
                        syntaxError();
                    }
                } else {
                    syntaxError();
                }
            } catch (Exception e) {
                syntaxError();
                e.printStackTrace();
            }

        } else {
            syntaxError();
        }
    }
}
