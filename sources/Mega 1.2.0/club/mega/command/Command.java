package club.mega.command;

import club.mega.interfaces.MinecraftInterface;
import club.mega.util.ChatUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Command implements MinecraftInterface {

    private final Info annotation = getClass().getAnnotation(Info.class);
    private final String name = annotation.name();
    private final String description = annotation.description();
    private final String usage = annotation.usage();
    private final String[] aliases = annotation.aliases();

    public void execute(final String[] args) {}

    public void usage() {
        ChatUtil.sendMessage(getUsage());
    }

    public String name() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAliases() {
        return aliases;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String name();
        String description();
        String usage();
        String[] aliases();
    }

}
