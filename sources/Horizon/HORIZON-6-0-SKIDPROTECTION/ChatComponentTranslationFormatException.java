package HORIZON-6-0-SKIDPROTECTION;

public class ChatComponentTranslationFormatException extends IllegalArgumentException
{
    private static final String HorizonCode_Horizon_È = "CL_00001271";
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation component, final String message) {
        super(String.format("Error parsing: %s: %s", component, message));
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation component, final int index) {
        super(String.format("Invalid index %d requested for %s", index, component));
    }
    
    public ChatComponentTranslationFormatException(final ChatComponentTranslation component, final Throwable cause) {
        super(String.format("Error while parsing: %s", component), cause);
    }
}
