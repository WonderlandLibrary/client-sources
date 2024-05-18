package net.minecraft.util;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.command.*;

public class ChatComponentProcessor
{
    private static final String[] I;
    
    public static IChatComponent processComponent(final ICommandSender commandSender, final IChatComponent chatComponent, final Entity entity) throws CommandException {
        IChatComponent matchEntitiesToChatComponent;
        if (chatComponent instanceof ChatComponentScore) {
            final ChatComponentScore chatComponentScore = (ChatComponentScore)chatComponent;
            String s = chatComponentScore.getName();
            if (PlayerSelector.hasArguments(s)) {
                final List<Entity> matchEntities = PlayerSelector.matchEntities(commandSender, s, (Class<? extends Entity>)Entity.class);
                if (matchEntities.size() != " ".length()) {
                    throw new EntityNotFoundException();
                }
                s = matchEntities.get("".length()).getName();
            }
            ChatComponentScore chatComponentScore2;
            if (entity != null && s.equals(ChatComponentProcessor.I["".length()])) {
                chatComponentScore2 = new ChatComponentScore(entity.getName(), chatComponentScore.getObjective());
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                chatComponentScore2 = new ChatComponentScore(s, chatComponentScore.getObjective());
            }
            matchEntitiesToChatComponent = chatComponentScore2;
            ((ChatComponentScore)matchEntitiesToChatComponent).setValue(chatComponentScore.getUnformattedTextForChat());
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (chatComponent instanceof ChatComponentSelector) {
            matchEntitiesToChatComponent = PlayerSelector.matchEntitiesToChatComponent(commandSender, ((ChatComponentSelector)chatComponent).getSelector());
            if (matchEntitiesToChatComponent == null) {
                matchEntitiesToChatComponent = new ChatComponentText(ChatComponentProcessor.I[" ".length()]);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
        }
        else if (chatComponent instanceof ChatComponentText) {
            matchEntitiesToChatComponent = new ChatComponentText(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            if (!(chatComponent instanceof ChatComponentTranslation)) {
                return chatComponent;
            }
            final Object[] formatArgs = ((ChatComponentTranslation)chatComponent).getFormatArgs();
            int i = "".length();
            "".length();
            if (3 == 0) {
                throw null;
            }
            while (i < formatArgs.length) {
                final Object o = formatArgs[i];
                if (o instanceof IChatComponent) {
                    formatArgs[i] = processComponent(commandSender, (IChatComponent)o, entity);
                }
                ++i;
            }
            matchEntitiesToChatComponent = new ChatComponentTranslation(((ChatComponentTranslation)chatComponent).getKey(), formatArgs);
        }
        final ChatStyle chatStyle = chatComponent.getChatStyle();
        if (chatStyle != null) {
            matchEntitiesToChatComponent.setChatStyle(chatStyle.createShallowCopy());
        }
        final Iterator<IChatComponent> iterator = chatComponent.getSiblings().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            matchEntitiesToChatComponent.appendSibling(processComponent(commandSender, iterator.next(), entity));
        }
        return matchEntitiesToChatComponent;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u007f", "UCwHk");
        ChatComponentProcessor.I[" ".length()] = I("", "OVLLO");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
