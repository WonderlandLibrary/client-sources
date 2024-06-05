package argo.saj;

public final class InvalidSyntaxException extends Exception
{
    private final int column;
    private final int row;
    
    InvalidSyntaxException(final String par1Str, final ThingWithPosition par2ThingWithPosition) {
        super("At line " + par2ThingWithPosition.getRow() + ", column " + par2ThingWithPosition.getColumn() + ":  " + par1Str);
        this.column = par2ThingWithPosition.getColumn();
        this.row = par2ThingWithPosition.getRow();
    }
    
    InvalidSyntaxException(final String par1Str, final Throwable par2Throwable, final ThingWithPosition par3ThingWithPosition) {
        super("At line " + par3ThingWithPosition.getRow() + ", column " + par3ThingWithPosition.getColumn() + ":  " + par1Str, par2Throwable);
        this.column = par3ThingWithPosition.getColumn();
        this.row = par3ThingWithPosition.getRow();
    }
}
