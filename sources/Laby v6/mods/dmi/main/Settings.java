package mods.dmi.main;

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

public class Settings
{
    public static File configFile = new File("config/dmi_config.json");
    public static DMISettings settings;
    public static boolean loaded = false;
    public static boolean need_Update = false;

    public static void save()
    {
        if (settings != null)
        {
            try
            {
                if (!configFile.exists())
                {
                    loadProperties();
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
            System.out.print("DMI Settings could not be saved.");
        }
    }

    public static void loadProperties()
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
                    s = (new Gson()).toJson((Object)(new DMISettings()));
                }
                else
                {
                    s = IOUtils.toString((InputStream)(new FileInputStream(configFile)));
                    s = s.replace("" + Color.c + "l", "" + Color.c + "f").replace("" + Color.c + "k", "" + Color.c + "f").replace("" + Color.c + "m", "" + Color.c + "f").replace("" + Color.c + "n", "" + Color.c + "f").replace("" + Color.c + "r", "" + Color.c + "f").replace("\u00c2", "");
                }
            }
            catch (IOException var2)
            {
                ;
            }

            if ((DMISettings)(new Gson()).fromJson(s, DMISettings.class) == null)
            {
                s = (new Gson()).toJson((Object)(new DMISettings()));
            }

            settings = (DMISettings)(new Gson()).fromJson(s, DMISettings.class);

            if (settings == null)
            {
                System.out.print("DMI Settings could not be loaded.");
                return;
            }
        }
        catch (Exception var3)
        {
            System.out.print("DMI Settings could not be loaded.");
        }
    }
}
