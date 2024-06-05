package net.minecraft.src;

public interface ILogAgent
{
    void logInfo(final String p0);
    
    void logWarning(final String p0);
    
    void logWarningFormatted(final String p0, final Object... p1);
    
    void logWarningException(final String p0, final Throwable p1);
    
    void logSevere(final String p0);
    
    void logSevereException(final String p0, final Throwable p1);
    
    void logFine(final String p0);
}
