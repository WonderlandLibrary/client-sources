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
import net.minecraft.util.Util;

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

    public static void setSettings(HashMap<String, GuiScreen> settings1)
    {
        settings = settings1;
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
    		ArrayList<File> filelist = new ArrayList();
    
    		CodeSource codesource = ModManager.class.getProtectionDomain().getCodeSource();
    
    		File f = new File(Util.getOSType() == Util.EnumOS.OSX ? System.getProperty("user.home") + "/Library/Application Support/minecraft/LabyMod/mods-1.8.8" : System.getProperty("user.home") + "/AppData/Roaming/.minecraft/LabyMod/mods-1.8.8");

    		File[] files = f.listFiles();
    		String s;
    		
    		for (int i = 0; i < files.length; i++)
    		{
    			File f2 = files[i];
      
    			URL url = f2.toURL();
    			JarInputStream jarinputstream = new JarInputStream(url.openStream());
    			JarEntry jarentry = null;
    			
    			while ((jarentry = jarinputstream.getNextJarEntry()) != null)
    			{
    				s = jarentry.getName();
    				
    				if (s.endsWith(".desc"))
    				{
    					arraylist.add(s);
    					filelist.add(files[i]);
    				}
    			}
    		}
    		
    		File file1 = null;
    		JarFile jarfile = null;
    		
    		try
    		{
    			int i = 0;
    			for (String s2 : arraylist)
    			{
    				try
    				{
    					file1 = (File)filelist.get(i);
    					jarfile = new JarFile(file1);
    				}
    				catch (Exception var17)
    				{
    					System.out.println("Konnte jar file " + file1 + " nicht öffnen");
    				}
    				
    				if ((file1 == null) || (jarfile == null) || (!file1.exists())) {
    					return;
    				}
    				
    				Debug.debug("[DEBUG] Loading Mod " + s2);
			        JarEntry jarentry1 = jarfile.getJarEntry(s2);
			        InputStream inputstream = jarfile.getInputStream(jarentry1);			
			        JsonElement jsonelement = new JsonParser().parse(new InputStreamReader(inputstream));
			        
			        if ((jsonelement instanceof JsonNull))
			        {
			        	Logger.getLogger().info("Skipping " + file1.getName() + " because it's " + s2 + " is empty");
			        }
			        else
			        {
			        	JsonObject jsonobject = (JsonObject)jsonelement;
			        	String s1 = jsonobject.get("main").getAsString();
			        	if (s1 != null) {
			        		try
			        		{
			        			try
			        			{
			        				URL[] aurl = { file1.toURI().toURL() };
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
			        			Logger.getLogger().info("Skipping " + file1.getName() + " because it's " + s2 + " doesn't contain a main entry");
			        		}
			        		catch (Exception exception)
			        		{
			        			exception.printStackTrace();
			        			Logger.getLogger().info("Skipping " + file1.getName() + " because it's main class doesn't contain a default constructor");
			        		}
			        	}
			        }
			        i++;
    			}
    		}
    		catch (NoSuchMethodError nosuchmethoderror)
    		{
    			nosuchmethoderror.printStackTrace();
    		}
    		return;
    	}
    	catch (Exception exception1)
    	{
    		exception1.printStackTrace();
    	}
    }
}

