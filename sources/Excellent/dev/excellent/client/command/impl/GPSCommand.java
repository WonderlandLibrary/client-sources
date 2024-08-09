package dev.excellent.client.command.impl;

import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;
import lombok.Setter;
import net.minecraft.util.text.TextFormatting;

public class GPSCommand extends Command {
    public GPSCommand() {
        super("", "gps");
    }

    public static int x, z;
    @Setter
    public static boolean active = false;

    @Override
    public void execute(String[] args) {
        if (args.length == 1)
            usage(TextFormatting.RED + """
                                        
                    .gps x z
                    .gps off\s""");

        // set
        if (args.length == 3) {
            active = true;
            x = Integer.parseInt(args[1]);
            z = Integer.parseInt(args[2]);
            ChatUtil.addText(TextFormatting.RED + "Метка установлена");
        }
        // off
        else if (args[1].equalsIgnoreCase("off") && args.length == 2) {
            x = z = 0;
            active = false;
            ChatUtil.addText(TextFormatting.RED + "Метка выключена");
        } else usage(TextFormatting.RED + """
                                
                .gps x z
                .gps off\s""");
    }
}