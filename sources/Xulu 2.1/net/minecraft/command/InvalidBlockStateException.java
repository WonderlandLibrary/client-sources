package net.minecraft.command;

public class InvalidBlockStateException extends CommandException
{
    public InvalidBlockStateException()
    {
        this("commands.generic.blockstate.invalid", new Object[0]);
    }

    public InvalidBlockStateException(String p_i47331_1_, Object... p_i47331_2_)
    {
        super(p_i47331_1_, p_i47331_2_);
    }
}
