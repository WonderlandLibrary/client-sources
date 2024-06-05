// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import gg.childtrafficking.smokex.utils.player.ChatUtils;
import java.io.File;
import gg.childtrafficking.smokex.gui.font.CFontRenderer;
import java.awt.Font;
import java.awt.Desktop;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Font", description = "Changes the custom font used by the client", usage = ".font folder | .font <NAME>", aliases = {})
public class FontCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        if (args.length < 1) {
            this.printUsage();
        }
        else if (args[0].equalsIgnoreCase("folder")) {
            final File location = SmokeXClient.getInstance().getFontDirectory();
            if (location != null) {
                try {
                    Desktop.getDesktop().open(location);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (args[0].equalsIgnoreCase("default")) {
            try {
                SmokeXClient.getInstance().fontRenderer = new CFontRenderer(Font.createFont(0, this.getClass().getResourceAsStream("/default.ttf")).deriveFont(20.0f));
                SmokeXClient.getInstance().font = "default";
            }
            catch (final Exception e2) {
                e2.printStackTrace();
            }
        }
        else {
            try {
                SmokeXClient.getInstance().fontRenderer = new CFontRenderer(Font.createFont(0, new File(SmokeXClient.getInstance().getFontDirectory(), args[0])).deriveFont(20.0f));
                ChatUtils.addChatMessage("Successfully Loaded Font");
                SmokeXClient.getInstance().font = args[0];
            }
            catch (final Exception e2) {
                ChatUtils.addChatMessage("Couldn't find font");
                e2.printStackTrace();
            }
        }
    }
}
