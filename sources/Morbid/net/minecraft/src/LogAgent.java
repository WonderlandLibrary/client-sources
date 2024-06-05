package net.minecraft.src;

import java.util.logging.*;

public class LogAgent implements ILogAgent
{
    private final Logger serverLogger;
    private final String logFile;
    private final String loggerName;
    private final String loggerPrefix;
    
    public LogAgent(final String par1Str, final String par2Str, final String par3Str) {
        this.serverLogger = Logger.getLogger(par1Str);
        this.loggerName = par1Str;
        this.loggerPrefix = par2Str;
        this.logFile = par3Str;
        this.setupLogger();
    }
    
    private void setupLogger() {
        this.serverLogger.setUseParentHandlers(false);
        for (final Handler var4 : this.serverLogger.getHandlers()) {
            this.serverLogger.removeHandler(var4);
        }
        final LogFormatter var5 = new LogFormatter(this, null);
        final ConsoleHandler var6 = new ConsoleHandler();
        var6.setFormatter(var5);
        this.serverLogger.addHandler(var6);
        try {
            final FileHandler var7 = new FileHandler(this.logFile, true);
            var7.setFormatter(var5);
            this.serverLogger.addHandler(var7);
        }
        catch (Exception var8) {
            this.serverLogger.log(Level.WARNING, "Failed to log " + this.loggerName + " to " + this.logFile, var8);
        }
    }
    
    @Override
    public void logInfo(final String par1Str) {
        this.serverLogger.log(Level.INFO, par1Str);
    }
    
    @Override
    public void logWarning(final String par1Str) {
        this.serverLogger.log(Level.WARNING, par1Str);
    }
    
    @Override
    public void logWarningFormatted(final String par1Str, final Object... par2ArrayOfObj) {
        this.serverLogger.log(Level.WARNING, par1Str, par2ArrayOfObj);
    }
    
    @Override
    public void logWarningException(final String par1Str, final Throwable par2Throwable) {
        this.serverLogger.log(Level.WARNING, par1Str, par2Throwable);
    }
    
    @Override
    public void logSevere(final String par1Str) {
        this.serverLogger.log(Level.SEVERE, par1Str);
    }
    
    @Override
    public void logSevereException(final String par1Str, final Throwable par2Throwable) {
        this.serverLogger.log(Level.SEVERE, par1Str, par2Throwable);
    }
    
    @Override
    public void logFine(final String par1Str) {
        this.serverLogger.log(Level.FINE, par1Str);
    }
    
    static String func_98237_a(final LogAgent par0LogAgent) {
        return par0LogAgent.loggerPrefix;
    }
}
