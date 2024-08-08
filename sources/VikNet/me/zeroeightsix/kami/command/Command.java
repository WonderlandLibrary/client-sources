package me.zeroeightsix.kami.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.KamiMod;
import me.zeroeightsix.kami.command.syntax.SyntaxChunk;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Last Updated 5 August 2019 by hub
 */
public abstract class Command {

    public static Setting<String> commandPrefix = Settings.s("commandPrefix", ".");
    protected String label;
    protected String syntax;
    protected String description;
    protected SyntaxChunk[] syntaxChunks;

    public Command(String label, SyntaxChunk[] syntaxChunks) {
        this.label = label;
        this.syntaxChunks = syntaxChunks;
        this.description = "Descriptionless";
    }

    public static void sendChatMessage(String message) {
        sendRawChatMessage(KamiMod.getInstance().guiManager.getTextColor() + KamiMod.NAME_UNICODE + ChatFormatting.DARK_GRAY.toString() + " \u27ab " + ChatFormatting.RESET.toString() + message);
    }

    public static void sendStringChatMessage(String[] messages) {
        sendChatMessage("");
        for (String s : messages) {
            sendRawChatMessage(s);
        }
    }

    public static void sendRawChatMessage(String message) {
        if (Wrapper.getPlayer() == null) {
            return;
        }
        Wrapper.getPlayer().sendMessage(new ChatMessage(message));
    }

    public static String getCommandPrefix() {
        return commandPrefix.getValue();
    }

    public static char SECTIONSIGN() {
        return '\u00A7';
    }

    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public abstract void call(String[] args);

    public SyntaxChunk[] getSyntaxChunks() {
        return syntaxChunks;
    }

    protected SyntaxChunk getSyntaxChunk(String name) {
        for (SyntaxChunk c : syntaxChunks) {
            if (c.getType().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public static class ChatMessage extends TextComponentBase {

        String text;

        public ChatMessage(String text) {

            Pattern p = Pattern.compile("&[0123456789abcdefrlosmk]");
            Matcher m = p.matcher(text);
            StringBuffer sb = new StringBuffer();

            while (m.find()) {
                String replacement = "\u00A7" + m.group().substring(1);
                m.appendReplacement(sb, replacement);
            }

            m.appendTail(sb);

            this.text = sb.toString();
        }

        public String getUnformattedComponentText() {
            return text;
        }

        @Override
        public ITextComponent createCopy() {
            return new ChatMessage(text);
        }

    }
}
