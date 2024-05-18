package de.labystudio.modapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.labystudio.chat.Logger;
import de.labystudio.utils.Debug;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import net.minecraft.client.gui.GuiScreen;

public class ModManager
{
    private static ArrayList<Module> modules = new ArrayList();
    private static HashMap<String, GuiScreen> settings = new HashMap();
    private static GuiScreen lastScreen;

    public static ArrayList<Module> getModules()
    {
        return modules;
    }

    public static void updateLastScreen(GuiScreen screen)
    {
        lastScreen = screen;
    }

    public static HashMap<String, GuiScreen> getSettings()
    {
        return settings;
    }

    public static void setSettings(HashMap<String, GuiScreen> settings)
    {
        settings = settings;
    }

    public static GuiScreen getLastScreen()
    {
        return lastScreen;
    }

    public static void pluginMessage(String key, boolean value)
    {
        for (Module module : getModules())
        {
            module.pluginMessage(key, value);
        }
    }

    public static void loadMods()
    {
        try
        {
            ArrayList<String> arraylist = new ArrayList();
            CodeSource codesource = ModManager.class.getProtectionDomain().getCodeSource();

            if (codesource != null)
            {
                URL url = codesource.getLocation();
                JarInputStream jarinputstream = new JarInputStream(url.openStream());
                JarEntry jarentry = null;

                while ((jarentry = jarinputstream.getNextJarEntry()) != null)
                {
                    String s = jarentry.getName();

                    if (s.endsWith(".desc"))
                    {
                        arraylist.add(s);
                    }
                }
            }

            File file1 = null;
            JarFile jarfile = null;

            try
            {
                file1 = new File(codesource.getLocation().toURI());
                jarfile = new JarFile(file1);
            }
            catch (Exception var17)
            {
                ;
            }

            if (file1 == null || jarfile == null || !file1.exists())
            {
                return;
            }

            try
            {
                for (String s2 : arraylist)
                {
                    Debug.debug("[DEBUG] Loading Mod " + s2);
                    JarEntry jarentry1 = jarfile.getJarEntry(s2);
                    InputStream inputstream = jarfile.getInputStream(jarentry1);
                    JsonElement jsonelement = (new JsonParser()).parse((Reader)(new InputStreamReader(inputstream)));

                    if (jsonelement instanceof JsonNull)
                    {
                        Logger.getLogger().info("Skipping " + file1.getName() + " because it\'s " + s2 + " is empty");
                    }
                    else
                    {
                        JsonObject jsonobject = (JsonObject)jsonelement;
                        String s1 = jsonobject.get("main").getAsString();

                        if (s1 != null)
                        {
                            try
                            {
                                try
                                {
                                    URL[] aurl = new URL[] {file1.toURI().toURL()};
                                    ModuleClassLoader moduleclassloader = new ModuleClassLoader(aurl);
                                    Class<?> oclass = moduleclassloader.loadClass(s1, true);
                                    Module module = (Module)oclass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                                    module.onEnable();
                                    modules.add(module);
                                    Logger.getLogger().info("Loaded Mod " + s2);
                                }
                                catch (NoClassDefFoundError noclassdeffounderror)
                                {
                                    noclassdeffounderror.printStackTrace();
                                    Logger.getLogger().info("NoClassDefFoundError: " + s1);
                                }
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                                Logger.getLogger().info("Skipping " + file1.getName() + " because it\'s main class doesn\'t contain a default constructor");
                            }
                        }
                        else
                        {
                            Logger.getLogger().info("Skipping " + file1.getName() + " because it\'s " + s2 + " doesn\'t contain a main entry");
                        }
                    }
                }
            }
            catch (NoSuchMethodError nosuchmethoderror)
            {
                nosuchmethoderror.printStackTrace();
            }
        }
        catch (Exception exception1)
        {
            exception1.printStackTrace();
        }
    }
}
