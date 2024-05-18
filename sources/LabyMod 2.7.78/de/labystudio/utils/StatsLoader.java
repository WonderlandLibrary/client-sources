package de.labystudio.utils;

import de.labystudio.labymod.Source;
import de.labystudio.labymod.Timings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.commons.io.IOUtils;

public class StatsLoader
{
    public static HashMap<String, ArrayList<String>> stats = new HashMap();

    public static void loadstats()
    {
        Timings.start("Load Game stats config");

        if (stats.isEmpty())
        {
            stats.clear();
            String s = "";
            create();

            try
            {
                s = IOUtils.toString((InputStream)(new FileInputStream(Source.file_stats)));
            }
            catch (FileNotFoundException var2)
            {
                ;
            }
            catch (IOException var3)
            {
                ;
            }

            EntityPlayer.syncPlayerScore();
            stats = (HashMap)Utils.ConvertJsonToObject.getFromJSON(s, HashMap.class);

            if (stats == null)
            {
                stats = new HashMap();
            }

            Timings.stop("Load Game stats config");
        }
    }

    public static void create()
    {
        if (!(new File(Source.file_stats)).exists())
        {
            try
            {
                if (!(new File(Source.file_stats)).getParentFile().exists())
                {
                    (new File(Source.file_stats)).getParentFile().mkdirs();
                }

                (new File(Source.file_stats)).createNewFile();
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
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(Source.file_stats));
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
                String s = (String)list.get(i1);

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
