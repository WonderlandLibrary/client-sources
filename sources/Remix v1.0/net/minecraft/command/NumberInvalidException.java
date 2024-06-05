package net.minecraft.command;

public class NumberInvalidException extends CommandException
{
    public NumberInvalidException() {
        this("commands.generic.num.invalid", new Object[0]);
    }
    
    public NumberInvalidException(final String p_i1360_1_, final Object... p_i1360_2_) {
        super(p_i1360_1_, p_i1360_2_);
    }
}
