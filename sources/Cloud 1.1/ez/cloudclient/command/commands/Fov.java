package ez.cloudclient.command.commands;

import ez.cloudclient.command.Command;
import ez.cloudclient.util.MessageUtil;
import ez.cloudclient.util.NumberUtil;

public class Fov extends Command {

    public Fov() {
        super("FOV", "Change client Fov", "fov");
    }

    @Override
    protected void call(String[] args) {
        if (args.length > 0) {
            if (NumberUtil.isNumeric(args[0])) {
                if ((Float.parseFloat(args[0]) >= 150)) {
                    MessageUtil.sendMessage("Max 150, FOV Set to 150", MessageUtil.Color.RED);
                    mc.gameSettings.fovSetting = 150f;
                } else if ((Float.parseFloat(args[0]) < 10)) {
                    MessageUtil.sendMessage("Min 10, FOV Set to 10", MessageUtil.Color.RED);
                    mc.gameSettings.fovSetting = 10f;
                } else {
                    mc.gameSettings.fovSetting = Float.parseFloat(args[0]);
                    MessageUtil.sendMessage("Successfully set FOV to: " + mc.gameSettings.fovSetting, MessageUtil.Color.GREEN);
                }
            } else {
                switch (args[0].toLowerCase()) {
                    case "max":
                    case "m":
                        mc.gameSettings.fovSetting = 150f;
                        MessageUtil.sendMessage("Successfully set FOV to: " + mc.gameSettings.fovSetting, MessageUtil.Color.GREEN);
                        break;
                    case "normal":
                    case "n":
                    case "medium":
                        mc.gameSettings.fovSetting = 70f;
                        MessageUtil.sendMessage("Successfully set FOV to: " + mc.gameSettings.fovSetting, MessageUtil.Color.GREEN);
                        break;
                    case "quakepro":
                    case "quake":
                    case "q":
                        mc.gameSettings.fovSetting = 110f;
                        MessageUtil.sendMessage("Successfully set FOV to: " + mc.gameSettings.fovSetting, MessageUtil.Color.GREEN);
                        break;
                    case "low":
                    case "l":
                        mc.gameSettings.fovSetting = 30f;
                        MessageUtil.sendMessage("Successfully set FOV to: " + mc.gameSettings.fovSetting, MessageUtil.Color.GREEN);
                        break;
                    default:
                        MessageUtil.sendMessage("Invalid arguments, enter a number between 10 and 150 inclusive or a preset: Low, Normal, QuakePro, Max.", MessageUtil.Color.RED);
                        break;
                }
            }
        }
    }
}
