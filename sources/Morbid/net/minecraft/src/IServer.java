package net.minecraft.src;

public interface IServer
{
    int getIntProperty(final String p0, final int p1);
    
    String getStringProperty(final String p0, final String p1);
    
    void setProperty(final String p0, final Object p1);
    
    void saveProperties();
    
    String getSettingsFilename();
    
    String getHostname();
    
    int getPort();
    
    String getServerMOTD();
    
    String getMinecraftVersion();
    
    int getCurrentPlayerCount();
    
    int getMaxPlayers();
    
    String[] getAllUsernames();
    
    String getFolderName();
    
    String getPlugins();
    
    String executeCommand(final String p0);
    
    boolean isDebuggingEnabled();
    
    void logInfo(final String p0);
    
    void logWarning(final String p0);
    
    void logSevere(final String p0);
    
    void logDebug(final String p0);
}
