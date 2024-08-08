package lol.point.returnclient.command;

import lol.point.Return;
import lol.point.returnclient.util.exception.CommandException;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Command {

    protected static final Minecraft mc = Return.MC;

    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;

    public Command() {
        final CommandInfo commandInfo = this.getClass().getAnnotation(CommandInfo.class);
        Validate.notNull(commandInfo, "CONFUSED ANNOTAION EXECPTION");
        this.name = commandInfo.name();
        this.description = commandInfo.description();
        this.usage = commandInfo.usage();
        this.aliases = Arrays.asList(commandInfo.aliases());
    }

    public boolean isAlias(final String str) {
        return aliases.stream()
                .anyMatch(s -> s.equalsIgnoreCase(str));
    }

    public abstract void execute(String... args) throws CommandException;

}
