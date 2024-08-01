package wtf.diablo.client.command.api;

import net.minecraft.client.Minecraft;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;

public abstract class AbstractCommand {
    protected final Minecraft mc = Minecraft.getMinecraft();

    private final Command command;

    protected AbstractCommand()
    {
        this.command = this.getClass().getAnnotation(Command.class);
    }

    public String getName()
    {
        return this.command.name();
    }

    public String getDescription()
    {
        return this.command.description();
    }

    public String[] getAliases()
    {
        return this.command.aliases();
    }

    public abstract void execute(final String[] args);

    protected void sendUsage()
    {
        ChatUtil.addChatMessage("Usage: " + this.command.usage());
    }

}
