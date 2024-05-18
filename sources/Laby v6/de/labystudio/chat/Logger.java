package de.labystudio.chat;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
    protected static final Logger INSTANCE = new Logger();
    protected static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm:ss");
    protected static final File logFile = new File("latest.log");
    protected static PrintWriter writer;

    public static final Logger getLogger()
    {
        return INSTANCE;
    }

    public void log(LogLevel level, String message)
    {
        System.out.println("[" + FORMAT.format(new Date(System.currentTimeMillis())) + "] [" + level.toString() + "] " + message);
    }

    public void error(Exception e)
    {
        this.log(LogLevel.ERROR, e.getMessage());
        e.printStackTrace();
    }

    public void error(String error)
    {
        this.log(LogLevel.ERROR, error);
    }

    public void debug(String message)
    {
        this.log(LogLevel.DEBUG, message);
    }

    public void info(String message)
    {
        this.log(LogLevel.INFO, message);
    }
}
