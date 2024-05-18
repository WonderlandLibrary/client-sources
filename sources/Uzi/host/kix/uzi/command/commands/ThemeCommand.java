package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.module.modules.render.Overlay;
import host.kix.uzi.utilities.minecraft.Logger;

import java.util.Objects;

/**
 * Created by Kix on 6/10/2017.
 * Made for the Uzi Universal project.
 */
public class ThemeCommand extends Command {

    public ThemeCommand() {
        super("Theme", "Allows the user to spice up the UI.", "th", "thetheme", "hudtheme");
    }

    @Override
    public void dispatch(String message) {
        String[] args = message.split(" ");
        if (args.length <= 1) {
            Logger.logToChat("Set, Help");
        } else {
            if (args[1].equalsIgnoreCase("set")) {
                Uzi.getInstance().getThemeManager().getContents()
                        .stream()
                        .filter(theme -> args[2].equalsIgnoreCase(theme.getLabel()))
                        .forEach(theme -> {
                            Overlay.getThemeHandler().setTheme(theme);
                        });
            } else if (args[1].equalsIgnoreCase("help")) {
                StringBuilder themes = new StringBuilder("Themes (" + Uzi.getInstance().getThemeManager().getContents().size() + "): ");
                Uzi.getInstance().getThemeManager().getContents()
                        .forEach(theme -> themes.append("\2477").append(theme.getLabel()).append("\247r, "));
                Logger.logToChat(themes.toString().substring(0, themes.length() - 2));
            }
        }
    }
}
