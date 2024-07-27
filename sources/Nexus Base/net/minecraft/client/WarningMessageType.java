package net.minecraft.client;

public enum WarningMessageType {
    CRASH("The server attempted to crash you."),
    TROLL("The server attempted to troll you."),
    MALICIOUS("The server attempted to be malicious.");

    final String message;

    WarningMessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}