package me.Emir.Karaguc.ui.credits;

public class Developper {
    private final String name;
    private final String discordName;

    public Developper(String name, String discordName) {
        this.name = name;
        this.discordName = discordName;
    }

    public String getName() {
        return this.name;
    }
    public String getDiscordName() {
        return this.discordName;
    }
}
