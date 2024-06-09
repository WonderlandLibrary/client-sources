package me.travis.wurstplus.command;

import me.travis.wurstplus.wurstplusMod;
import me.travis.wurstplus.command.syntax.SyntaxChunk;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {
	
	protected String label;
	protected String syntax;
	protected String description;

	protected SyntaxChunk[] syntaxChunks;

	public static Setting<String> commandPrefix = Settings.s("commandPrefix", "`");

	public Command(String label, SyntaxChunk[] syntaxChunks) {
		this.label = label;
		this.syntaxChunks = syntaxChunks;
		this.description = "Descriptionless";
	}

	public static void sendChatMessage(String message){
		sendRawChatMessage("\u00A73[\u00A76" + wurstplusMod.wurstplus_KANJI + "\u00A73] &r" + message);
	}

	public static void sendStringChatMessage(String[] messages) {
		sendChatMessage("");
		for (String s : messages) sendRawChatMessage(s);
    }

	public static void sendRawChatMessage(String message){ // added try case bc if not it dies
		try{Wrapper.getPlayer().sendMessage(new ChatMessage(message));}
		catch(Exception e){}
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static String getCommandPrefix() {
		return commandPrefix.getValue();
	}
	
	public String getLabel() {
		return label;
	}
	
	public abstract void call(String[] args);

    public SyntaxChunk[] getSyntaxChunks() {
        return syntaxChunks;
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

	protected SyntaxChunk getSyntaxChunk(String name){
		for (SyntaxChunk c : syntaxChunks){
			if (c.getType().equals(name))
				return c;
		}
		return null;
	}

	public static char SECTIONSIGN() {
    	return '\u00A7';
	}
}
