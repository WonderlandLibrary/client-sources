package net.minecraft.src;

public interface IPlayerUsage
{
    void addServerStatsToSnooper(final PlayerUsageSnooper p0);
    
    void addServerTypeToSnooper(final PlayerUsageSnooper p0);
    
    boolean isSnooperEnabled();
    
    ILogAgent getLogAgent();
}
