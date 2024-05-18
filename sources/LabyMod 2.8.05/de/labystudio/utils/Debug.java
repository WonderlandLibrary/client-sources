package de.labystudio.utils;

public class Debug
{
    public static void debug(String message)
    {
        ((SupportLog.PrintStreamLabyMod)System.out).debug(message);
    }
}
