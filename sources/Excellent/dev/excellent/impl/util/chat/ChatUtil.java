package dev.excellent.impl.util.chat;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.ITheme;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.util.text.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.Color;

@UtilityClass
public class ChatUtil implements IMinecraft, ITheme {

    public void addText(ITextComponent text) {
        if (mc.player == null) return;
        mc.ingameGUI.getChatGUI().printChatMessage(text);
    }

    public void addText(String text) {
        if (mc.player == null) return;
        IFormattableTextComponent component = new StringTextComponent("");
        component.appendString("§7[")
                .append(genGradientText(Excellent.getInst().getInfo().getNamespace(),
                        Excellent.getInst().getThemeManager().getTheme().getFirstColor(),
                        Excellent.getInst().getThemeManager().getTheme().getSecondColor()))
                .appendString("§7] §r")
                .appendString(replaceColorCodesInString(text));

        mc.ingameGUI.getChatGUI().printChatMessage(component);
    }

    public void addText(final Object message, final Object... objects) {
        if (mc.player == null) return;
        if (message == null) {
            addText("Object is null");
            return;
        }
        final String format = String.format(message.toString(), objects);
        addText(format);
    }

    public void sendText(String text) {
        if (mc.player == null) return;
        mc.player.sendChatMessage(text
                .replaceAll("§", "&")
                .replaceAll("%s", RandomStringUtils.randomAlphabetic(3))
        );
    }

    public void sendText(final Object message, final Object... objects) {
        if (mc.player == null) return;
        final String format = String.format(message.toString(), objects);
        sendText(format);
    }

    public String replaceColorCodesInString(String text) {
        if (text == null) return "";
        String str = text;
        str = str
                .replaceAll("&4", String.valueOf(TextFormatting.DARK_RED))
                .replaceAll("&c", String.valueOf(TextFormatting.RED))
                .replaceAll("&6", String.valueOf(TextFormatting.GOLD))
                .replaceAll("&e", String.valueOf(TextFormatting.YELLOW))
                .replaceAll("&2", String.valueOf(TextFormatting.DARK_GREEN))
                .replaceAll("&a", String.valueOf(TextFormatting.GREEN))
                .replaceAll("&b", String.valueOf(TextFormatting.AQUA))
                .replaceAll("&3", String.valueOf(TextFormatting.DARK_AQUA))
                .replaceAll("&1", String.valueOf(TextFormatting.DARK_BLUE))
                .replaceAll("&9", String.valueOf(TextFormatting.BLUE))
                .replaceAll("&d", String.valueOf(TextFormatting.LIGHT_PURPLE))
                .replaceAll("&5", String.valueOf(TextFormatting.DARK_PURPLE))
                .replaceAll("&f", String.valueOf(TextFormatting.WHITE))
                .replaceAll("&7", String.valueOf(TextFormatting.GRAY))
                .replaceAll("&8", String.valueOf(TextFormatting.DARK_GRAY))
                .replaceAll("&0", String.valueOf(TextFormatting.BLACK))

                .replaceAll("&k", String.valueOf(TextFormatting.OBFUSCATED))
                .replaceAll("&m", String.valueOf(TextFormatting.STRIKETHROUGH))
                .replaceAll("&o", String.valueOf(TextFormatting.ITALIC))
                .replaceAll("&l", String.valueOf(TextFormatting.BOLD))
                .replaceAll("&n", String.valueOf(TextFormatting.UNDERLINE))
                .replaceAll("&r", String.valueOf(TextFormatting.RESET));
        return str;
    }

    public String removeColorCodes(String text) {
        String str = text;
        String[] colorcodes = new String[]{
                "4", "c", "6", "e", "2", "a", "b", "3", "1", "9", "d",
                "5", "f", "7", "8", "0", "k", "m", "o", "l", "n", "r"};
        for (String c : colorcodes) {
            str = str.replaceAll("§" + c, "");
        }
        return str.trim();
    }

    public IFormattableTextComponent genGradientText(String text, Color color1, Color color2) {
        IFormattableTextComponent gradientComponent = new StringTextComponent("");
        Color[] color = ColorUtil.genGradientForText(color1, color2, text.length());
        int i = 0;
        for (char ch : text.toCharArray()) {
            IFormattableTextComponent component = new StringTextComponent(String.valueOf(ch));
            Style style = new Style(net.minecraft.util.text.Color.fromInt(color[i].getRGB()), false, false, false, false, false, null, null, null, null);
            component.setStyle(style);
            gradientComponent.append(component);
            i++;
        }
        return gradientComponent;
    }

}