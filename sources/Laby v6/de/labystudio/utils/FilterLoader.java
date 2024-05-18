package de.labystudio.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

public class FilterLoader
{
    public static ArrayList<String> filters = new ArrayList();
    public static boolean enabled = true;

    public static void loadFilters()
    {
        if (filters.isEmpty())
        {
            filters.clear();
            String s = "";
            create();

            try
            {
                s = IOUtils.toString((InputStream)(new FileInputStream("LabyMod/filters.json")));
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
                filters = (ArrayList)Utils.ConvertJsonToObject.getFromJSON(s, ArrayList.class);
            }
            catch (Exception exception)
            {
                (new File("LabyMod/filters.json")).delete();
                exception.printStackTrace();
            }

            if (filters == null)
            {
                filters = new ArrayList();
            }
        }
    }

    public static void create()
    {
        if (!(new File("LabyMod/filters.json")).exists())
        {
            try
            {
                if (!(new File("LabyMod/filters.json")).getParentFile().exists())
                {
                    (new File("LabyMod/filters.json")).getParentFile().mkdirs();
                }

                (new File("LabyMod/filters.json")).createNewFile();
            }
            catch (IOException var1)
            {
                ;
            }
        }
    }

    public static void saveFilters()
    {
        FriendsLoader.create();
        String s = Utils.ConvertJsonToObject.toJSON(filters);

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream("LabyMod/filters.json"));
            printwriter.print(s);
            printwriter.flush();
            printwriter.close();
        }
        catch (FileNotFoundException var2)
        {
            ;
        }
    }
}
