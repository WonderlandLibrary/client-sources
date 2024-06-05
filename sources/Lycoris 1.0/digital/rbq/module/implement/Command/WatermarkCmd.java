package digital.rbq.module.implement.Command;

import net.minecraft.util.EnumChatFormatting;
import digital.rbq.module.Command;
import digital.rbq.module.ModuleManager;
import digital.rbq.module.implement.Render.Hud;
import digital.rbq.module.implement.Render.NameProtect;
import digital.rbq.utility.ChatUtils;

/**
 * Created by Admin on 2017/03/10.
 */
@Command.Info(name = "watermark", syntax = {"<name> | <player> <name> "}, help = "Set Watermark name in HUD.")
public class WatermarkCmd extends Command {
    @Override
    public void execute(String[] args) throws Error {
        if (args.length == 1) {
            Hud.name = args[0].replace("&", "§").replace("\\_", "/*/<>/*/").replace("_", " ").replace("/*/<>/*/", "_");
                ChatUtils.sendMessageToPlayer("Watermark name changed to " + EnumChatFormatting.GOLD + Hud.name);
            }
        }
}
