package de.labystudio.labymod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.labystudio.utils.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import org.apache.commons.io.IOUtils;

public class ConfigManager
{
    public static File configFile = new File("LabyMod/LabyMod.json");
    public static ModSettings settings;
    public static boolean loaded = false;

    public static void save()
    {
        if (settings != null)
        {
            try
            {
                if (!configFile.exists())
                {
                    loadProperties(true);
                }

                GsonBuilder gsonbuilder = new GsonBuilder();
                Gson gson = gsonbuilder.setPrettyPrinting().create();
                PrintWriter printwriter = new PrintWriter(new FileOutputStream(configFile));
                printwriter.print(gson.toJson((Object)settings));
                printwriter.flush();
                printwriter.close();
            }
            catch (FileNotFoundException var3)
            {
                ;
            }
        }
        else
        {
            LabyMod.getInstance().logger.info("Settings could not be saved.");
        }
    }

    public static void loadProperties(boolean loop)
    {
        try
        {
            loaded = true;
            String s = "{}";

            try
            {
                if (!configFile.exists())
                {
                    configFile.getParentFile().mkdir();
                    configFile.createNewFile();
                    s = (new Gson()).toJson((Object)(new ModSettings()));
                }
                else
                {
                    s = IOUtils.toString((InputStream)(new FileInputStream(configFile)));
                    s = s.replace("" + Color.c + "l", "" + Color.c + "f").replace("" + Color.c + "k", "" + Color.c + "f").replace("" + Color.c + "m", "" + Color.c + "f").replace("" + Color.c + "n", "" + Color.c + "f").replace("" + Color.c + "r", "" + Color.c + "f").replace("\u00c2", "");
                }
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();

                if (loop)
                {
                    delete();
                }

                return;
            }

            if ((ModSettings)(new Gson()).fromJson(s, ModSettings.class) == null)
            {
                s = (new Gson()).toJson((Object)(new ModSettings()));
            }

            settings = (ModSettings)(new Gson()).fromJson(s, ModSettings.class);

            if (settings == null)
            {
                if (loop)
                {
                    delete();
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();

            if (loop)
            {
                delete();
            }
        }
    }

    private static void delete()
    {
        LabyMod.getInstance().logger.info("Settings could not be loaded.");

        if (configFile.exists())
        {
            System.out.println("Delete broken config file..");
            settings = (ModSettings)(new Gson()).fromJson((new Gson()).toJson((Object)(new ModSettings())), ModSettings.class);
            save();
        }
        else
        {
            System.out.println("Can\'t delete the config file?! @ " + configFile.getAbsolutePath());
        }

        loadProperties(false);
    }
}
