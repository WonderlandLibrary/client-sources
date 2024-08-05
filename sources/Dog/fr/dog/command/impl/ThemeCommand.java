package fr.dog.command.impl;

import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.theme.Theme;
import fr.dog.util.player.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class ThemeCommand extends Command {

    public ThemeCommand() {
        super("theme", "th");
    }

    @Override
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");

        if (words.length < 1 || words.length > 3) {
            ChatUtil.display("Invalid arguments! Usage: .theme <list|set> [name]");
            return;
        }

        String option = words[1].toLowerCase();

        switch (option) {
            case "list":
                ChatUtil.display("-- Themes --");
                for (Theme t : Dog.getInstance().getThemeManager()) {
                    ChatComponentText themeComponent = new ChatComponentText("§f[" + Dog.getInstance().getThemeManager().getCurrentTheme().chatFormatting + "Dog Client§f]§8 > §7" + t.getChatFormatting() + t.getName());
                    themeComponent.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ".theme set " + t.getName())));
                    themeComponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(t.getChatFormatting() + t.getName())));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(themeComponent);
                }
                break;
            case "set":
                if (words.length < 3) {
                    ChatUtil.display("Please specify a theme name!");
                    return;
                }
                String themeName = words[2].toLowerCase();
                Theme t = Dog.getInstance().getThemeManager().getThemeByName(themeName);

                if (t != null) {
                    Dog.getInstance().getThemeManager().setCurrentTheme(t);
                    ChatUtil.display("Theme set to: " + t.getName());
                } else {
                    ChatUtil.display("Theme not found: " + themeName);
                }
                break;
            default:
                ChatUtil.display("Invalid option! Usage: .theme <list|set> [name]");
                break;
        }
    }
}
