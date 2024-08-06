package club.strifeclient.command;

import lombok.Getter;
import net.minecraft.client.Minecraft;

@Getter
public abstract class Command {
    private final String name;
    private final String description;
    private final String[] aliases;

    protected final Minecraft mc = Minecraft.getMinecraft();

    public Command() {
        if(getClass().isAnnotationPresent(CommandInfo.class)) {
            final CommandInfo commandInfo = getClass().getAnnotation(CommandInfo.class);
            name = commandInfo.name();
            description = commandInfo.description();
            aliases = commandInfo.aliases();
        } else throw new Error("Failed to initialize command, missing CommandInfo.");
    }

    public abstract void execute(String[] args, String name);
}
