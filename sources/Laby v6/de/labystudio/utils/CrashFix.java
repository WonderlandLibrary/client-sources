package de.labystudio.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CrashFix
{
    public static boolean fixOptifineCrash()
    {
    	File file = new File("optionsof.txt");
        if (!file.exists()) 
        {
        	return false;
        }
        try
        {
        	BufferedReader br = new BufferedReader(new FileReader(file));
        	try
        	{
        		StringBuilder sb = new StringBuilder();
        		String line = br.readLine();
        		ArrayList<String> all = new ArrayList();
        		while (line != null)
        		{
        			all.add(line.replace("ofShowCapes:true", "ofShowCapes:false"));
        			line = br.readLine();
        		}
        		BufferedWriter output = null;
        		try
        		{
        			output = new BufferedWriter(new FileWriter(file));
        			for (String outLine : all)
        			{
        				output.write(outLine);
        				output.newLine();
        			}
        		}
        		catch (IOException e) 
        		{
        			
        		}
        		finally
        		{
        			boolean bool1;
        			boolean bool2;
        			if (output != null)
        			{
        				output.close();
        				return true;
        			}
        		}
        	}
        	finally
        	{
        		br.close();
        	}
        }
        catch (Exception error)
        {
        	error.printStackTrace();
        }
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
