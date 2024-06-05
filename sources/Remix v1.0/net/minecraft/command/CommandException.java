package net.minecraft.command;

public class CommandException extends Exception
{
    private final Object[] errorObjects;
    
    public CommandException(final String p_i1359_1_, final Object... p_i1359_2_) {
        super(p_i1359_1_);
        this.errorObjects = p_i1359_2_;
    }
    
    public Object[] getErrorOjbects() {
        return this.errorObjects;
    }
}
