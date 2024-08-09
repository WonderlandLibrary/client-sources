package ru.FecuritySQ.command.imp;


import net.minecraft.util.text.TextFormatting;
import ru.FecuritySQ.command.Command;
import ru.FecuritySQ.command.CommandAbstract;

@Command(name = "gps", description = "Показывает расстояние от координаты")
public class GPSCommand extends CommandAbstract {

    public static int x, z;
    public static boolean gps = false;

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length > 1) {
            if (args[1].equals("off")) {
                x = 0;
                z = 0;
                gps = false;
            } else {
                gps = true;
                x = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
            }
        } else {
            error();
        }
    }

    @Override
    public void error() {
        sendMessage(TextFormatting.GRAY + "Ошибка в использовании" + TextFormatting.WHITE + ":");
        sendMessage(TextFormatting.WHITE + ".gps " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "x, z" + TextFormatting.GRAY + ">");
        sendMessage(TextFormatting.WHITE + ".gps " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "off" + TextFormatting.GRAY + ">");
    }
}
