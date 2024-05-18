package mods.worldeditcui.debug;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import mods.worldeditcui.InitialisationFactory;
import mods.worldeditcui.WorldEditCUI;
import mods.worldeditcui.exceptions.InitialisationException;
import mods.worldeditcui.util.ConsoleLogFormatter;

public class CUIDebug implements InitialisationFactory
{
    protected WorldEditCUI controller;
    protected File debugFile;
    protected boolean debugMode = false;
    protected static final Logger logger = Logger.getLogger("WorldEditCUI");

    public CUIDebug(WorldEditCUI controller)
    {
        this.controller = controller;
    }

    public void initialise() throws InitialisationException
    {
        ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter();
        ConsoleHandler consolehandler = new ConsoleHandler();
        consolehandler.setFormatter(consolelogformatter);
        logger.setUseParentHandlers(false);
        logger.addHandler(consolehandler);

        try
        {
            this.debugFile = new File("worldeditcui.debug.log");
            this.debugMode = this.controller.getConfiguration().isDebugMode();

            if (this.debugMode)
            {
                FileHandler filehandler = new FileHandler(this.debugFile.getAbsolutePath());
                filehandler.setFormatter(consolelogformatter);
                logger.addHandler(filehandler);
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace(System.err);
            throw new InitialisationException();
        }
    }

    public void debug(String message)
    {
        if (this.debugMode)
        {
            logger.info("WorldEditCUI Debug - " + message);
        }
    }

    public void info(String message)
    {
        logger.info(message);
    }

    public void info(String message, Throwable e)
    {
        logger.log(Level.INFO, message, e);
    }
}
