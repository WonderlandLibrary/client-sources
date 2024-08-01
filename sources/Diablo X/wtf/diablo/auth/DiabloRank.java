package wtf.diablo.auth;

import com.mojang.realmsclient.gui.ChatFormatting;

public enum DiabloRank {
    USER("User", ChatFormatting.GREEN, 0),
    STAGING("Staging", ChatFormatting.YELLOW, 1),
    ADMINISTRATOR("Administrator", ChatFormatting.RED, 2),
    DEVELOPER("Developer", ChatFormatting.DARK_RED, 3);

    private final String name;
    private final ChatFormatting chatFormatting;
    private final int rank;

    DiabloRank(final String name, final ChatFormatting chatFormatting, final int rank) {
        this.name = name;
        this.chatFormatting = chatFormatting;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return chatFormatting + name;
    }

    public ChatFormatting getChatFormatting() {
        return chatFormatting;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public final String toString() {
        return getFormattedName();
    }

    public static DiabloRank getRank(final int rank) {
        for (final DiabloRank diabloRank : DiabloRank.values()) {
            if (diabloRank.getRank() == rank) {
                return diabloRank;
            }
        }

        return null;
    }
}
