package net.minecraft.src;

public class ChatLine
{
    private final int updateCounterCreated;
    private final String lineString;
    private final int chatLineID;
    
    public ChatLine(final int par1, final String par2Str, final int par3) {
        this.lineString = par2Str;
        this.updateCounterCreated = par1;
        this.chatLineID = par3;
    }
    
    public String getChatLineString() {
        return this.lineString;
    }
    
    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }
    
    public int getChatLineID() {
        return this.chatLineID;
    }
}
