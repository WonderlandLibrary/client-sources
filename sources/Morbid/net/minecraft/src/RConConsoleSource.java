package net.minecraft.src;

public class RConConsoleSource implements ICommandSender
{
    public static final RConConsoleSource consoleBuffer;
    private StringBuffer buffer;
    
    static {
        consoleBuffer = new RConConsoleSource();
    }
    
    public RConConsoleSource() {
        this.buffer = new StringBuffer();
    }
    
    public void resetLog() {
        this.buffer.setLength(0);
    }
    
    public String getChatBuffer() {
        return this.buffer.toString();
    }
    
    @Override
    public String getCommandSenderName() {
        return "Rcon";
    }
    
    @Override
    public void sendChatToPlayer(final String par1Str) {
        this.buffer.append(par1Str);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int par1, final String par2Str) {
        return true;
    }
    
    @Override
    public String translateString(final String par1Str, final Object... par2ArrayOfObj) {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(0, 0, 0);
    }
}
