package wtf.diablo.irc.event;

import best.azura.eventbus.events.CancellableEvent;
import wtf.diablo.auth.DiabloRank;

public final class IrcReceiveMinecraftEvent extends CancellableEvent {
    private final String username;
    private final DiabloRank rank;
    private final String minecraftUsername;
    private final boolean shouldRemove;

    public IrcReceiveMinecraftEvent(final String username, final DiabloRank diabloRank, final String minecraftUsername, final boolean shouldRemove) {
        this.username = username;
        this.rank = diabloRank;
        this.minecraftUsername = minecraftUsername;
        this.shouldRemove = shouldRemove;
    }

    public String getUsername() {
        return this.username;
    }

    public DiabloRank getRank() {
        return this.rank;
    }

    public String getMinecraftUsername() {
        return this.minecraftUsername;
    }

    public boolean shouldRemove() {
        return this.shouldRemove;
    }
}
