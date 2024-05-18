package de.labystudio.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;

public class StatsLoader
{
    public static HashMap<String, ArrayList<String>> stats = new HashMap();

    public static void loadstats()
    {
        if (stats.isEmpty())
        {
            stats.clear();
            String s = "";
            create();

            try
            {
                s = IOUtils.toString((InputStream)(new FileInputStream("LabyMod/minigames_stats.json")));
            }
            catch (FileNotFoundException var3)
            {
                ;
            }
            catch (IOException var4)
            {
                ;
            }

            try
            {
                stats = (HashMap)Utils.ConvertJsonToObject.getFromJSON(s, HashMap.class);
            }
            catch (Exception exception)
            {
                (new File("LabyMod/minigames_stats.json")).delete();
                exception.printStackTrace();
            }

            if (stats == null)
            {
                stats = new HashMap();
            }
        }
    }

    public static void create()
    {
        if (!(new File("LabyMod/minigames_stats.json")).exists())
        {
            try
            {
                if (!(new File("LabyMod/minigames_stats.json")).getParentFile().exists())
                {
                    (new File("LabyMod/minigames_stats.json")).getParentFile().mkdirs();
                }

                (new File("LabyMod/minigames_stats.json")).createNewFile();
            }
            catch (IOException var1)
            {
                ;
            }
        }
    }

    public static void savestats()
    {
        create();
        String s = Utils.ConvertJsonToObject.toJSON(stats);

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream("LabyMod/minigames_stats.json"));
            printwriter.print(s);
            printwriter.flush();
            printwriter.close();
        }
        catch (FileNotFoundException var2)
        {
            ;
        }
    }

    public static boolean isHighScore(int i, ArrayList<String> list)
    {
        if (list.isEmpty())
        {
            return true;
        }
        else
        {
            for (int i1 = 0; i1 < list.size(); ++i1)
            {
                String s = (String)list.get(i);

                try
                {
                    int j = Integer.parseInt(s);

                    if (i1 <= j)
                    {
                        return false;
                    }
                }
                catch (Exception var5)
                {
                    ;
                }
            }

            return true;
        }
    }
}
