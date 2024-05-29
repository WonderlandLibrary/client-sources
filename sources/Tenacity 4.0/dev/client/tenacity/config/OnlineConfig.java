package dev.client.tenacity.config;

public class OnlineConfig extends Configuration {

    private final String author, description, clientVersion, shareCode;
    private final boolean verified;
    private final int votes;

    public OnlineConfig(String name, String author, String description, String clientVersion, String shareCode, boolean verified, int votes) {
        super(name);
        this.verified = verified;
        this.author = author;
        this.description = description;
        this.clientVersion = clientVersion;
        this.shareCode = shareCode;
        this.votes = votes;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getShareCode() {
        return shareCode;
    }

    public boolean isVerified() {
        return verified;
    }

    public int getVotes() {
        return votes;
    }

}
