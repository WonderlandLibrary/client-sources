package net.minecraft.util;

import java.util.*;
import net.minecraft.server.*;
import net.minecraft.scoreboard.*;

public class ChatComponentScore extends ChatComponentStyle
{
    private String value;
    private final String name;
    private static final String[] I;
    private final String objective;
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ChatComponentScore)) {
            return "".length() != 0;
        }
        final ChatComponentScore chatComponentScore = (ChatComponentScore)o;
        if (this.name.equals(chatComponentScore.name) && this.objective.equals(chatComponentScore.objective) && super.equals(o)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public ChatComponentScore createCopy() {
        final ChatComponentScore chatComponentScore = new ChatComponentScore(this.name, this.objective);
        chatComponentScore.setValue(this.value);
        chatComponentScore.setChatStyle(this.getChatStyle().createShallowCopy());
        final Iterator<IChatComponent> iterator = this.getSiblings().iterator();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            chatComponentScore.appendSibling(iterator.next().createCopy());
        }
        return chatComponentScore;
    }
    
    @Override
    public IChatComponent createCopy() {
        return this.createCopy();
    }
    
    @Override
    public String getUnformattedTextForChat() {
        final MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value)) {
            final Scoreboard scoreboard = server.worldServerForDimension("".length()).getScoreboard();
            final ScoreObjective objective = scoreboard.getObjective(this.objective);
            if (scoreboard.entityHasObjective(this.name, objective)) {
                final Score valueFromObjective = scoreboard.getValueFromObjective(this.name, objective);
                final String s = ChatComponentScore.I[" ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = valueFromObjective.getScorePoints();
                this.setValue(String.format(s, array));
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                this.value = ChatComponentScore.I["  ".length()];
            }
        }
        return this.value;
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getObjective() {
        return this.objective;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0x7E ^ 0x79])["".length()] = I("", "CVnVl");
        ChatComponentScore.I[" ".length()] = I("p*", "UNAyv");
        ChatComponentScore.I["  ".length()] = I("", "CUPxH");
        ChatComponentScore.I["   ".length()] = I("\n\u0004\u00063(\u001a\b\u00041\"7\u0002\u0007567\u0006\u0004$p~", "YgiAM");
        ChatComponentScore.I[0x65 ^ 0x61] = I("\r$\u0003\u00016\u0016/\u001f\u0001hE", "bFidU");
        ChatComponentScore.I[0x93 ^ 0x96] = I("ys2+:9:/%+h", "USABX");
        ChatComponentScore.I[0xA8 ^ 0xAE] = I("AR+\r\u0013\u0001\u0017e", "mrXyj");
    }
    
    public String getName() {
        return this.name;
    }
    
    public ChatComponentScore(final String name, final String objective) {
        this.value = ChatComponentScore.I["".length()];
        this.name = name;
        this.objective = objective;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return ChatComponentScore.I["   ".length()] + this.name + (char)(0xB8 ^ 0x9F) + ChatComponentScore.I[0x18 ^ 0x1C] + this.objective + (char)(0xAA ^ 0x8D) + ChatComponentScore.I[0x23 ^ 0x26] + this.siblings + ChatComponentScore.I[0x5 ^ 0x3] + this.getChatStyle() + (char)(0xC ^ 0x71);
    }
}
