package cc.slack.features.commands;

import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.impl.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class CMDManager {

    private final List<CMD> commands = new ArrayList<>();
    private final String prefix = ".";

    public void initialize() {
        try {
            addCommands(
                    new bindCMD(),
                    new ConfigCMD(),
                    new setCMD(),
                    new ToggleCMD(),
                    new ignCMD(),
                    new showCMD(),
                    new hideCMD(),
                    new friendCMD(),
                    new panicCMD(),
                    new sneakCMD(),
                    new HelpCMD()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCommands(CMD... cmds) {
        commands.addAll(Arrays.asList(cmds));
    }
}
