package net.minecraft.profiler;

public interface IPlayerUsage
{
    void addServerTypeToSnooper(final PlayerUsageSnooper p0);
    
    boolean isSnooperEnabled();
    
    void addServerStatsToSnooper(final PlayerUsageSnooper p0);
}
