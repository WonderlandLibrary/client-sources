package dev.luvbeeq.baritone.api.command;

import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.utils.IPlayerContext;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * A default implementation of {@link ICommand} which provides easy access to the
 * command's bound {@link IBaritone} instance, {@link IPlayerContext} and an easy
 * way to provide multiple valid command execution names through the default constructor.
 * <p>
 * So basically, you should use it because it provides a small amount of boilerplate,
 * but you're not forced to use it.
 *
 * @author LoganDark
 * @see ICommand
 */
public abstract class Command implements ICommand {

    protected IBaritone baritone;
    protected IPlayerContext ctx;

    /**
     * The names of this command. This is what you put after the command prefix.
     */
    protected final List<String> names;

    /**
     * Creates a new Baritone control command.
     *
     * @param names The names of this command. This is what you put after the command prefix.
     */
    protected Command(IBaritone baritone, String... names) {
        this.names = Stream.of(names)
                .map(s -> s.toLowerCase(Locale.US)).toList();
        this.baritone = baritone;
        this.ctx = baritone.getPlayerContext();
    }

    @Override
    public final List<String> getNames() {
        return this.names;
    }
}
