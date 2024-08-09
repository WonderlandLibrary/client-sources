package net.minecraft.util;

import org.apache.logging.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler
{
    private final Logger logger;

    public DefaultUncaughtExceptionHandler(Logger logger)
    {
        this.logger = logger;
    }

    public void uncaughtException(Thread p_uncaughtException_1_, Throwable p_uncaughtException_2_)
    {
        this.logger.error("Caught previously unhandled exception :", p_uncaughtException_2_);
    }
}
