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
import org.apache.commons.io.IOUtils;

public class FilterLoader
{
    public static ArrayList<String> filters = new ArrayList();
    public static boolean enabled = true;

    public static void loadFilters()
    {
        Timings.start("Load Filter Config");

        if (filters.isEmpty())
        {
            filters.clear();
            String s = "";
            create();

            try
            {
                s = IOUtils.toString((InputStream)(new FileInputStream(Source.file_filters)));
            }
            catch (FileNotFoundException var2)
            {
                ;
            }
            catch (IOException var3)
            {
                ;
            }

            filters = (ArrayList)Utils.ConvertJsonToObject.getFromJSON(s, ArrayList.class);

            if (filters == null)
            {
                filters = new ArrayList();
            }

            Timings.stop("Load Filter Config");
        }
    }

    public static void create()
    {
        if (!(new File(Source.file_filters)).exists())
        {
            try
            {
                if (!(new File(Source.file_filters)).getParentFile().exists())
                {
                    (new File(Source.file_filters)).getParentFile().mkdirs();
                }

                (new File(Source.file_filters)).createNewFile();
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
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(Source.file_filters));
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
