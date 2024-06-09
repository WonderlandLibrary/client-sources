package dev.vertic.command;

import dev.vertic.util.player.ChatUtil;
import lombok.Getter;

import java.io.IOException;

@Getter
public abstract class Command implements ChatUtil {

    private final String name;
    private final String[] calls;

    public Command(final String name, final String... calls) {
        this.name = name;
        this.calls = calls;
    }

    public abstract void call(String[] args) throws IOException;

    protected void addChatMessage(final Object message) {
        ChatUtil.addChatMessage(message);
    }

}
