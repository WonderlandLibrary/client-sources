package digital.rbq.addon.api.command;

import com.soterdev.SoterObfuscator;
import lombok.Getter;
import digital.rbq.addon.LycorisAPI;
import digital.rbq.addon.api.LycorisAddon;
import digital.rbq.module.Command;
import digital.rbq.utility.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class AddonCommandManager {
    @Getter
    public List<Command> addonCommands = new ArrayList<>();

    public AddonCommandManager() {
        for (LycorisAddon fluxAddon : LycorisAPI.FLUX_API.getAddonManager().getEnabledLycorisAddonList()) {
            for (AddonCommand command : fluxAddon.getCommands()) {
                try {
                    addonCommands.add(command.getNativeCommand());
                    ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2472[Command] \247a[%s] \2477Loaded!", command.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                    ChatUtils.sendErrorToPlayer(String.format("[Lycoris API] \2472[Command] \247a[%s] \2477Error: %s", command.getName(), e.getMessage()));
                }
            }
        }
    }
}
