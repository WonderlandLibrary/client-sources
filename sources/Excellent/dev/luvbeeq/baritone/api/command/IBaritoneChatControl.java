package dev.luvbeeq.baritone.api.command;

import dev.luvbeeq.baritone.api.Settings;

import java.util.UUID;

/**
 * @author Brady
 * @since 9/26/2019
 */
public interface IBaritoneChatControl {

    /**
     * In certain cases chat components need to execute commands for you. For example, the paginator automatically runs
     * commands when you click the forward and back arrows to show you the previous/next page.
     * <p>
     * If the prefix is changed in the meantime, then the command will go to chat. That's no good. So here's a permanent
     * prefix that forces a command to run, regardless of the current prefix, chat/prefix control being enabled, etc.
     * <p>
     * If used right (by both developers and users), it should be impossible to expose a command accidentally to the
     * server. As a rule of thumb, if you have a clickable chat component, always use this prefix. If you're suggesting
     * a command (a component that puts text into your text box, or something else), use {@link Settings#prefix}.
     */
    String FORCE_COMMAND_PREFIX = String.format("<<%s>>", UUID.randomUUID().toString());
}
