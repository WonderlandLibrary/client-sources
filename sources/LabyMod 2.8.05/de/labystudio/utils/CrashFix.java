package de.labystudio.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CrashFix
{
    public static boolean fixOptifineCrash()
    {
		return false;
    }

    public static boolean deleteDirectory(File directory)
    {
        if (directory.exists())
        {
            File[] afile = directory.listFiles();

            if (null != afile)
            {
                for (int i = 0; i < afile.length; ++i)
                {
                    if (afile[i].isDirectory())
                    {
                        deleteDirectory(afile[i]);
                    }
                    else
                    {
                        afile[i].delete();
                    }
                }
            }
        }

        return directory.delete();
    }
}
