package net.minecraft.src;

public class CommandNotFoundException extends CommandException
{
    public CommandNotFoundException() {
        this("commands.generic.notFound", new Object[0]);
    }
    
    public CommandNotFoundException(final String par1Str, final Object... par2ArrayOfObj) {
        super(par1Str, par2ArrayOfObj);
    }
}
