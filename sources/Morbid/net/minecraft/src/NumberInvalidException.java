package net.minecraft.src;

public class NumberInvalidException extends CommandException
{
    public NumberInvalidException() {
        this("commands.generic.num.invalid", new Object[0]);
    }
    
    public NumberInvalidException(final String par1Str, final Object... par2ArrayOfObj) {
        super(par1Str, par2ArrayOfObj);
    }
}
